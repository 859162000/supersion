package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;


import report.dao.imps.ItemDataCacheManger;
import report.dto.BindDetailFieldInfo;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.RptSubmitInfo;

import report.dto.ItemBindInfo;

import report.dto.TaskRptInst;
import report.helper.DoubleUtil;

import coresystem.dto.InstInfo;
import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;

import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;

import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
/*
 * 报表导出
 *  主要功能：根据当前DTO创建出SQL语句和连接供DAO执行	SingleObjectDownloadService
 */
import framework.show.ShowContext;

public class SingleObjectExportReportDataService extends BaseObjectDaoResultService{
	
	private String error = "下载失败";
	private String XLSX =".xlsx";
	private String XLS =".xls";
	String dimension1 = "";
	String dimension2 = "";
	
	// 根据当前DTO取出查询条件并组装DetachedCriteria
	DetachedCriteria detachedCriteria = null;
	IParamObjectResultExecute dao = null;
	List<Object> objectList = null;
	Map<String,Integer> colorMap;	
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		super.initDao();
		// 取当前TaskRptInst
		Object oldID = RequestManager.getId();
		RequestManager.setId(RequestManager.getLevelIdValue());
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskRptInst taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(null);
		RequestManager.setId(oldID);
		if(taskRptInst == null) return;
		
		String temFilePath = exportReportData(taskRptInst);
		if(temFilePath==null){
			return;
		}
		
		 DownloadResult downloadResult = new FileHandler().GetStreamResultFromPath(temFilePath, 1024*1024*5);
		 if(downloadResult.getInputStream() == null){
			 this.setServiceResult(new MessageResult(false,error));
		 }
		 else{
			 this.setServiceResult(downloadResult);
		 }
	}

	
	/**
	 * 导出报表   表格
	 */
	public String exportReportData(TaskRptInst taskRptInst) throws Exception {

		// 取TaskRptInst的日期，机构作为参数
		Date date = taskRptInst.getTaskFlow().getDtTaskDate();
		dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		String formworkName = taskRptInst.getRptInfo().getStrRptPath();
		String suit = taskRptInst.getRptInfo().getSuit().getStrSuitCode();
		
		if(suit.equals("01"))
		{
			//根据系统参数IsExcelExportPreData的设定，判断使用哪个模板
			Boolean IsExcelExportPreData = false;	
			
			SystemParam systemParam = HelpTool.getSystemParam("IsExcelExportPreData");
			if(systemParam!=null && systemParam.getStrEnable().equals("1"))
			{
				IsExcelExportPreData=Boolean.valueOf(systemParam.getStrParamValue().trim().toLowerCase());
			}
			
			if(IsExcelExportPreData)
				formworkName += ItemTemplate.SHOW_PRE_PERIOD_FILE_FLAG; 
		}

		
		String formworkPath = existFormwork(formworkName); // 判断模板是否存在
		
		String fileName =  taskRptInst.getStrShowRptCode()+"_"+taskRptInst.getRptInfo().getStrRptName()+"_"+((taskRptInst.getTaskFlow().getDtTaskDate().toString()).replace("-", ""))+"_"+taskRptInst.getInstInfo().getStrInstName();

		String temFilePath = "Download/tmp/"+fileName+XLS; // 下载生成文件路径
		
		if(formworkPath.length()>0){
			boolean xlsExist = formworkPath.endsWith(XLS); 
			if(xlsExist){
				to03Excel(date,taskRptInst, formworkPath, temFilePath);
			}else{
				temFilePath = temFilePath.replace(XLS, XLSX);
				to07Excel(date,taskRptInst, formworkPath, temFilePath);
			}
		}else{
			setServiceResult(new MessageResult(false, "模板文件："+formworkName+" 不存在!"));
			return null;
		}
		return temFilePath;
	}

	public void to03Excel(Date date,TaskRptInst taskRptInst, String formworkPath, String temFilePath) throws Exception{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(formworkPath));
		Workbook wwb= new HSSFWorkbook(fs);
		
		writeExcelData(wwb, date, taskRptInst, temFilePath, false);
	}
	
	public void to07Excel(Date date,TaskRptInst taskRptInst, String formworkPath, String temFilePath) throws Exception{
		FileInputStream ipt = new FileInputStream(formworkPath);
		Workbook wwb = new XSSFWorkbook(ipt);  // 创建excel文件对象 
		
		writeExcelData(wwb, date, taskRptInst, temFilePath, true); // 额外调用
	}
	
	private void initColorMap()
	{
		colorMap=new HashMap<String,Integer>();
		colorMap.put("#99CCFF",44 );
		colorMap.put("#FF8080",29 );
		colorMap.put("#CCCCFF", 31);
		colorMap.put("#CCFFFF",41 );
		colorMap.put("#CC99FF",46 );
		colorMap.put("#CCFFCC",42 );
		colorMap.put("#FFCC99",47 );
		colorMap.put("#C8C8C8",73 );
		colorMap.put("#FF0000", 2);
		colorMap.put("#00FF00", 3);
		colorMap.put("FFFF00", 5);
		
	}
	
	/**
	 * <p>方法描述:Excel数据写入 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param wwb
	 * @param date
	 * @param taskRptInst
	 * @param temFilePath
	 * @param b07
	 * @throws Exception
	 *
	 * <p>创建人：王川</p>
	 *
	 * <p>创建时间：2016-6-22 上午9:41:53</p>
	 *
	 */
	@SuppressWarnings({ "unchecked" })
	public void writeExcelData(Workbook wwb, Date date,TaskRptInst taskRptInst, String temFilePath, boolean b07) throws Exception{

		InstInfo instInfo = taskRptInst.getInstInfo();
		Sheet sheet = wwb.getSheetAt(0);
		ItemDataCacheManger dataCache=ItemDataCacheManger.getInsance();
		
		
		String strFreq=taskRptInst.getTaskFlow().getStrFreq();
		String strDate=TypeParse.parseString(date, "yyyy-MM-dd");
		
		ItemTemplate itemTemplate=new ItemTemplate(taskRptInst);
		IParamObjectResultExecute  dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Class.forName("report.dto.RptSubmitInfo"));
		detachedCriteria.add(Restrictions.eq("taskRptInstId",taskRptInst.getId()));
		List<RptSubmitInfo> rptSubmitInfoList = (List<RptSubmitInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		RptSubmitInfo rptSubmitInfo = rptSubmitInfoList.size()>0?rptSubmitInfoList.get(0):new RptSubmitInfo();
		
		if(StringUtils.isBlank(rptSubmitInfo.getStrRptDate()))
			rptSubmitInfo.setStrRptDate(strDate);
		
		if(StringUtils.isBlank(rptSubmitInfo.getStrBankName()))
			rptSubmitInfo.setStrBankName(instInfo.getStrInstName());
			
		
		initColorMap();
		// 先将所有注解转到单元格中
		for(int k=0; k<=sheet.getLastRowNum(); k++) {
			Row row = sheet.getRow(k);
			if(row == null) continue;
			for(int l=0; l<=row.getLastCellNum(); l++) {
				Cell c1 =  row.getCell(l);
				if(c1!=null)
				{
						if(c1.getCellType()!=Cell.CELL_TYPE_FORMULA)
						{
							if(c1.getCellComment() != null){
								String comment = c1.getCellComment().getString().toString();
								c1.setCellValue("myComment:" + comment); // 将批注转成单元格内容
								c1.setCellComment(null);
							}
							else if(c1 != null && c1.getCellType() == Cell.CELL_TYPE_STRING){ // 0是number类型
								String cellValue = c1.getStringCellValue();
								
								cellValue = cellValue.replace(ItemTemplate.BankName,rptSubmitInfo.getStrBankName() )
								                     .replace(ItemTemplate.RptDate, new SimpleDateFormat("yyyy年M月d日").format( new SimpleDateFormat("yyyy-MM-dd").parse(rptSubmitInfo.getStrRptDate())))
													 .replace(ItemTemplate.Reporter, rptSubmitInfo.getStrReporter())
													 .replace(ItemTemplate.Checker, rptSubmitInfo.getStrChecker())
													 .replace(ItemTemplate.Manager, rptSubmitInfo.getStrManager());

								c1.setCellValue(cellValue);
							}
						}
						else
						{
							c1.setCellComment(null);
						}
				}
			}
		}
		// 保存数据 
		for (int curRow = 0; curRow <= sheet.getLastRowNum(); curRow++) { // 每行
			Row rowList = sheet.getRow(curRow);
			if(rowList == null) continue;
			
			for (int curCol = 0; curCol <= rowList.getLastCellNum(); curCol++) { // 每列
				
				String cellValue = ""; // 取得批注值
				Cell cell = rowList.getCell(curCol);
				if(cell != null) {
					try {
						if(cell.getCellType()!=Cell.CELL_TYPE_FORMULA)
						{
							cellValue = cell.getStringCellValue();
						}
						
					
					} catch(Exception ex) {
						cellValue = "";
					}
					
					if(cellValue.startsWith("myComment:"))
						cellValue = cellValue.replace("myComment:", "");
					else
						cellValue = "";

				}
				
				// 检查内容是否有明细行结束符和批注   
				if(cellValue.contains(ItemTemplate.DetailEnd)) {
					cellValue = cellValue.replace(ItemTemplate.DetailEnd, "");
					CreationHelper factory = wwb.getCreationHelper();
					ClientAnchor anchor = factory.createClientAnchor();
					anchor.setCol1(curCol);
					anchor.setCol2(curCol + 1);
					anchor.setRow1(curRow);
					anchor.setRow2(curRow + 1);

					Drawing drawing = sheet.createDrawingPatriarch();
					Comment comment = drawing.createCellComment(anchor);
					RichTextString str = factory.createRichTextString(ItemTemplate.DetailEnd);
					comment.setString(str);
					comment.setAuthor("");
					cell.setCellComment(comment);
					cell.setCellValue(cellValue);
				}
				//[1]、定长表指标值填充
				// 解析模板中的指标，并设置值;		指标格式:[指标][ItemCode;Property]
				int nPos = cellValue.indexOf(itemTemplate.Start);
				if(nPos > -1) {
					//将备注信息转换为带维度值的指标实例变量
					ItemBindInfo itemBindInfo=itemTemplate.getItemBindInfo(cellValue, instInfo.getStrInstCode(), strDate, strFreq,true);
					//获取指标值信息
					String val =itemTemplate.getItemVal(itemBindInfo);
		            
					ItemInfo itemInfo=dataCache.getItem(itemBindInfo.itemCode);
					if(itemInfo!=null)
					{
						if(ItemDataType.Amount.toString().equals(itemInfo.getStrDataType())||
							ItemDataType.Num.toString().equals(itemInfo.getStrDataType()))
						{
							if(StringUtils.isBlank(val))
							{
								val="0";
							}
							cell.setCellValue(DoubleUtil.parse(val));	//添加内容
						}
						else if(ItemDataType.Percent.toString().equals(itemInfo.getStrDataType())){
							if(StringUtils.isBlank(val))
							{
								val="0";
							}
							else
							{
								val=val.replace("%", "");
							}
								
							cell.setCellValue(DoubleUtil.parse(val)/100);	//添加内容

						}
						else
						{
							cell.setCellValue(val);
						}
					}
					else
					{
						cell.setCellValue(val);	//添加内容
					}
					CellStyle styleItem = cell.getCellStyle();
					
					if(!StringUtils.isBlank(itemBindInfo.color)) {
						CellStyle style = wwb.createCellStyle();
						style.cloneStyleFrom(styleItem);

						style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
						
						if(colorMap.containsKey(itemBindInfo.color))
						{
							style.setFillForegroundColor(colorMap.get(itemBindInfo.color).shortValue());
						}
						else
							style.setFillForegroundColor((short)1);
						
						cell.setCellStyle(style);
					}

				} // 指标替换结束
				
				//[2] 、 明细表值填充
				// 处理字段
				int nPos1 = cellValue.indexOf(ItemTemplate.DetailStart); // 明细位置
				if(nPos1 > -1) { // 字段格式:[字段][TableName;FieldName]
					
			    	//int detailCurRow = curRow;	// 表的第一行
			    	Row firstLineRow = sheet.getRow(curRow); // 模板上原来的批注内容
			    	
			    	List<BindDetailFieldInfo> fieldBindInfo=itemTemplate.getDetailBindInfoWithExcel(firstLineRow);
			    	
					int maxrow = itemTemplate.getDetailMaxRow();
					
					//无明细记录时，清空Excel中的批注值
					if(maxrow==0){
						for(;curCol <= rowList.getLastCellNum();curCol++){
							cell = rowList.getCell(curCol);
							if(cell==null || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
								continue;
							}
							cellValue = cell.getStringCellValue();
							if(cellValue.indexOf(ItemTemplate.DetailStart)>-1){
								cell.setCellValue("");
							}
						}
					}
					//将明细记录填入表格中
					else{
						int detailCurRow=0;
				    	String fieldVal;
				    	Cell curCell;
				    	
				    	CellStyle style2 = wwb.createCellStyle();
				    					    	
						for(;detailCurRow<maxrow;detailCurRow++)
						{
							
							if(detailCurRow>0)
							{
								sheet.shiftRows(curRow, sheet.getLastRowNum() + 100, 1); // 本行之后的下移一行
				    			sheet.createRow(curRow);
				    			rowList = sheet.getRow(curRow);
							}
							
							for(BindDetailFieldInfo field:fieldBindInfo)
							{
								
								fieldVal=itemTemplate.getDetailFieldVal(field,detailCurRow);
								curCell=rowList.getCell(field.startIndex);
								if(curCell==null)
								{
									curCell=rowList.createCell(field.startIndex);
								}
								
								CellStyle style = firstLineRow.getCell(field.startIndex).getCellStyle();
								style.setAlignment(CellStyle.ALIGN_CENTER);
								
								curCell.setCellType(Cell.CELL_TYPE_STRING);
								
//				    			CellStyle style2 = wwb.createCellStyle();

				    			 
								style2.cloneStyleFrom(style);
								curCell.setCellStyle(style2);
	
								try{
									if(!StringUtils.isBlank(fieldVal)&&fieldVal.matches("^-?\\d*[.]?\\d*$"))
										curCell.setCellValue(Double.parseDouble(fieldVal));
									else
										curCell.setCellValue(fieldVal);
								}
								catch(Exception ex){
									curCell.setCellValue(fieldVal);
								}

							}
							curRow++;
						}
						if(maxrow>0)
						{
							curRow--;
						}
				    	nPos1 = -1;
					}
				}
			}
		}
		
		//判断是否为1104报表，是的话添加报送口径
		String suit = taskRptInst.getRptInfo().getSuit().getStrSuitCode();
		if(suit.equals("03")||suit.equals("07")||suit.equals("14"))
		{
			String reportCaliber = null;
			Cell cellreport = sheet.getRow(1).getCell(0);

			detachedCriteria=DetachedCriteria.forClass(Class.forName("extend.dto.SystemParam"));
			detachedCriteria.add(Restrictions.eq("strEnable", "1"));
			detachedCriteria.add(Restrictions.like("strItemCode","CBRC_ReportCaliber",MatchMode.START));
			List<SystemParam> SystemParamList = (List<SystemParam>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		
			if(null!=SystemParamList)
			{
				if(SystemParamList.size()==1)
				{
					for(SystemParam sp:SystemParamList){
							reportCaliber=sp.getStrParamValue().trim();
							break;
						}
				}
				else if(SystemParamList.size()>1)
				{
					for(SystemParam sp:SystemParamList){
						if(sp.getStrItemCode().equals("CBRC_ReportCaliber"+"_"+suit)){
							reportCaliber=sp.getStrParamValue().trim();
							break;
						}
					}
				}
			}
				
			if(StringUtils.isBlank(reportCaliber)){
				reportCaliber = "报送口径：境内汇总数据";
			}
			
			cellreport.setCellValue(reportCaliber);
		}

		wwb.setForceFormulaRecalculation(true);	//使输出的Excel文件上的计算公式自动计算
		temFilePath = ServletActionContext.getServletContext().getRealPath(temFilePath); // 生成文件临时路径
        FileOutputStream out = new FileOutputStream(temFilePath);
		wwb.write(out);
		out.close();
	}

	/** 
	 * 是否存在模板
	 */
	public String existFormwork(String formworkName) throws Exception{
		boolean xlsExist = formworkName.endsWith(XLS);
		boolean xlsxExist = formworkName.endsWith(XLSX);
		
		if(!xlsExist && !xlsxExist){
			formworkName += XLS;
		}
		String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
		String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
		String formworkPath =strRootPath + strTmpRootPath + "/" +formworkName;
		File xlsxFile = new File(formworkPath);
		
		if(!xlsxFile.exists()){
			formworkPath = formworkPath.replace(XLS, XLSX);
			File xlsFile = new File(formworkPath);
			if(!xlsFile.exists()){
				error = "模板不存在!";
				return "";
			}
		}
		
		return formworkPath;
	}
}







