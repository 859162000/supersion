package report.service.imps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.CalculationExampleInfo;
import report.dto.CalculationRule;
import report.dto.CurrencyType;
import report.dto.ExampleInfoRule;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.ItemRuleDetail;
import report.dto.ItemsCalculation;
import report.dto.Period;
import report.dto.RptInfo;
import report.helper.CellInfo;
import report.helper.ExcelCellInfo;
import report.helper.WordAnalyClass;
import report.service.expression.ExpressionContextKey;
import report.service.expression.interfaces.IExpressionBuilder;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;


public class WebOfficeUploadFileService extends BaseService{
	public static int Word_Type_Delimiter=1;
	public static int Word_Type_Const=2;
	public static int Word_Type_KeyWord=3;
	public static int Word_Type_Cell=4;
	public static String System_="System_";
	
	static int[] Delimiter=new int[]{'(',')',':',',','+','-','*','/','"','>','<','=','!'};
	static String[] KeyWord=new String[]{"IF","ABS","SUM","ROUND","MAX","OR","AND","COUNTIF","MONTH","SUMIF","MIN","AVERAGE","COUNT"};
	
	@Override
	public void initSuccessResult() throws Exception {			
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		
		String path = RequestManager.getReportCheckParam().get("path");
		if(path.startsWith("/"))
		{
			path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/")+path;
		}
		else
		{
			path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/")+"/"+path;
		}
		
		File file = new File(path);	
		
		InputStream in = null;
		OutputStream out = null;
		String re = "success";
		String re1="";
		try {
			int bufferSize = 1024*1024;
			in = new BufferedInputStream(new FileInputStream(RequestManager.getUploadFile()), bufferSize);
			out = new BufferedOutputStream(new FileOutputStream(file), bufferSize);
			byte[] buffer = new byte[bufferSize];
			while (in.read(buffer)>0) {
				out.write(buffer);
			}	
		} catch (Exception e) {
			re = "fail";
		} finally {
			if(in!=null)
			{
				in.close();
			}
			if(out!=null)
			{
				out.close();
			}
			
		}
		
		if(re.equals("fail")) {
			response.getWriter().write(re);
			return;
		}
		
		// 处理单元格，转换成html
		String htmPath = path.replace(".xls", ".htm");

		File htmFile = new File(htmPath);
		htmFile.delete(); // 先删除旧htm文件
		
		// 删除旧临时excel,并生成新的临时excel文件
		String tmpExcel = path.replace(".xls", "_tmp.xls");
		htmFile = new File(tmpExcel);
		htmFile.delete();
		HelpTool.copyFile(new File(path), new File(tmpExcel));
		
		// 中间excel文件中，将注解转换到单元格
		
		try {
			re1 = excelCommentToCellValue(tmpExcel);
			processShowPrePeriodData(path);
		}catch (Exception e) {
			re = "fail";
			ExceptionLog.CreateLog(e);
			response.getWriter().write(re);
			return;
		}		
		if(re.equals("fail")) {
			response.getWriter().write(re);
			return;
		}
		String os = System.getProperty("os.name").toLowerCase();
		if(!os.startsWith("windows")){      
			response.getWriter().write("html_fail");
			return;
		} 
			
		excelToHtml(htmPath, tmpExcel);
		if(re.equals("success") && !re1.equals("")){
			re=re1;
		}
		response.getWriter().write(re);
	}
	private String excelCommentToCellValue(String tmpExcel) throws Exception{
		boolean isExistFormula=false;
		String re1="";
		List<ExcelCellInfo> CellsList=new ArrayList<ExcelCellInfo>();
		String CalcInstName = RequestManager.getReportCheckParam().get("CalcInstName");
		FileInputStream ipt = new FileInputStream(tmpExcel);
		FileOutputStream opt = null;
		try {
			Workbook workbook = new HSSFWorkbook(ipt);
			int numberSheet = workbook.getNumberOfSheets();
			for (int i = 0; i < numberSheet; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				tag1:for (Row row : sheet) {
					for (Cell cell : row) {
						Comment comment =  cell.getCellComment(); // 获取批注
						if (comment != null) {
							String cont = comment.getString().getString().replace("[表结束]", "");
							if(!cont.equals("")) {
								cont = cont.replace("[指标]\n", "[指标][");
								cont = cont.replace("[字段]\n", "[字段][");
								cont = cont.replace("\n", ";");
								cont += "]";
							}else{
								break tag1;
							}
							////////////////////////ZHOUQIN//////////////////////
					        int rIndex=cell.getRowIndex()+1;
							int cIndex=cell.getColumnIndex()+1;
							String cells =IntToMoreChar(cIndex)+rIndex;
							
							ExcelCellInfo excelCellInfo = new ExcelCellInfo();
							excelCellInfo.setStrCell(cells);
							excelCellInfo.setStrComment(cont);
							excelCellInfo.setFormula(false);
							
					        if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
					        	isExistFormula=true;
					        	excelCellInfo.setFormula(true);
					        	excelCellInfo.setStrFormula(cell.getCellFormula().replaceAll("<>", "!="));
					        }					        
					        CellsList.add(excelCellInfo);					        
					        ////////////////////////ZHOUQIN//////////////////////
							cell.setCellValue(cont);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							
							CellStyle cellStyle1 = cell.getCellStyle();
					        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					        cell.setCellStyle(cellStyle1);
						}
					}
				}
				
				// //////////////////////ZHOUQIN//////////////////////
				if (isExistFormula) {
					if (!CalcInstName.equals("")) {
						List<ExcelCellInfo> unParseList = ParseExcelFormula(CellsList);
						Cell2ItemCode(CellsList);
						SaveFormula(sheet,CellsList, CalcInstName);
						if (unParseList.size() > 0) {
							re1 = "exist_unParseFormula:";
							for (ExcelCellInfo excelCellInfo : unParseList) {
								re1 += excelCellInfo.getStrCell() + ",";
							}
							re1 = re1.substring(0, re1.length() - 1);
							re1 += "\r\n";
							re1 += "计算公式未转换，请手工录入！";
						}
					} else {
						re1 = "miss_CalcInstName";
					}
				}
				isExistFormula = false;
			}
			
	        ////////////////////////ZHOUQIN//////////////////////
			opt = new FileOutputStream(tmpExcel);
			workbook.write(opt);
		}
		finally {
			ipt.close();
			if(opt != null) opt.close();
		}
		return re1;
	}
	private void processShowPrePeriodData(String path) throws Exception
	{
/*		Map<String,String>  showParam=ShowContext.getInstance().getShowEntityMap().get("reportShowPrePeriodDataParam");
		String isShow=showParam.get("isShow");
		String suitcode=showParam.get("suitCode");*/
		
		Boolean isShowFlag = false;
		String arraySuitCode[] = null;
		SystemParam systemParam = HelpTool.getSystemParam("IsShowPrePeriodData");
		if(systemParam!=null && systemParam.getStrEnable().equals("1"))
		{
			isShowFlag=Boolean.valueOf(systemParam.getStrParamValue().split("\\|")[0].trim().toLowerCase());
			
			if(isShowFlag)
			{
				arraySuitCode = systemParam.getStrParamValue().split("\\|");
			}
		}
		
		
//		if(!StringUtils.isBlank(isShow)&&"1".equals(isShow))
		if(isShowFlag)
		{
			String[] tempPath=path.split("/");
			String rptPath=tempPath[tempPath.length-1].replace(".xls", "");
			
			IParamObjectResultExecute criteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RptInfo.class);
			detachedCriteria.add(Restrictions.eq("strRptPath", rptPath));
			List<RptInfo> rptInfoList= (List<RptInfo>)criteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			if(rptInfoList.size()>0)
			{
			
				RptInfo ri=rptInfoList.get(0);
				
//				if(ri.getSuit().getStrSuitCode().equals(suitcode))
				if(SmallTools.arrayUtilsContains(arraySuitCode, ri.getSuit().getStrSuitCode()))
				{
					String htmPath = path.replace(".xls", ItemTemplate.SHOW_PRE_PERIOD_FILE_FLAG+".htm");

					File htmFile = new File(htmPath);
					htmFile.delete(); // 先删除旧htm文件
					
					// 删除旧临时excel,并生成新的临时excel文件
					String tmpExcel = path.replace(".xls", ItemTemplate.SHOW_PRE_PERIOD_FILE_FLAG+".xls");
					htmFile = new File(tmpExcel);
					htmFile.delete();
					HelpTool.copyFile(new File(path), new File(tmpExcel));

					// 中间excel文件中，将注解转换到单元格
					FileInputStream ipt = new FileInputStream(tmpExcel);
					FileOutputStream opt = null;
					try {
						Workbook workbook = new HSSFWorkbook(ipt); // 创建工作簿
						
						int numberSheet = workbook.getNumberOfSheets(); // 获取当前工作簿sheet数量
						for (int i = 0; i < numberSheet; i++) {
							AtomicInteger startRow=new AtomicInteger(-1);
							AtomicInteger startCol=new AtomicInteger(-1);
							AtomicInteger endRow=new AtomicInteger(-1);
							AtomicInteger endCol=new AtomicInteger(-1);
							Sheet sheet = workbook.getSheetAt(i); 
							getStartEndRowColInfo(startRow,startCol,endRow,endCol, sheet);
							addPrePeriodBindInfo(startRow.intValue(),startCol.intValue(),endRow.intValue(),endCol.intValue(), sheet);
							
						}
						
						opt = new FileOutputStream(tmpExcel);
						workbook.write(opt);
				
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						throw ex;
					}
					finally {
						ipt.close();
						if(opt != null) opt.close();
					}
					
					
					
					excelCommentToCellValue(tmpExcel);
					excelToHtml(htmPath, tmpExcel);

				}
				
				
			}
			
		}
		
	}
	
	
	private void excelToHtml(String htmPath, String tmpExcel) {
		ComThread.InitSTA();
		ActiveXComponent app = new ActiveXComponent("Excel.Application"); // 启动excel
		try
		{
		   app.setProperty("Visible", new Variant(false));
		   Dispatch workbooks = app.getProperty("Workbooks").toDispatch();
		   Dispatch workbook = Dispatch.invoke(workbooks, "Open", Dispatch.Method, new Object[] { tmpExcel, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
		   Dispatch currentSheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();
		   Dispatch PublishObjects = Dispatch.get(workbook, "PublishObjects").toDispatch();
		   

		   // 发布
		   Dispatch.invoke(PublishObjects, "Add", Dispatch.Method, new Object[] {new Variant(1), htmPath, Dispatch.get(currentSheet, "name").toString(), new Variant(""), new Variant(0), new Variant("")}, new int[1]);
		   Dispatch.invoke(PublishObjects, "Publish", Dispatch.Method, new Object[] {}, new int[1]);
		   Variant f = new Variant(false);
		   Dispatch.call(workbook, "Close", f);
		}
		catch (Exception e)
		{
		   ExceptionLog.CreateLog(e);
		   e.printStackTrace();
		}
		finally
		{
			app.invoke("Quit", new Variant[] {});
			ComThread.Release();
			ComThread.quitMainSTA();  
		}
	}
	private void addPrePeriodBindInfo(Integer startRow,Integer startCol,Integer endRow,Integer endCol,Sheet sheet) {
		int lastRow=sheet.getLastRowNum();
		Workbook wb=sheet.getWorkbook();
		//处理标题
		
		Row curRow=sheet.getRow(startRow-2);
		int colNum=1;
		for (int intCol=startCol;intCol<=endCol;intCol++) {
			Cell curCell=curRow.getCell(intCol);
			if(curCell!=null)
			{
				Cell newCell = getNewCell(endCol+colNum++, wb, curCell);
				if(curCell.getCellType()==Cell.CELL_TYPE_STRING)
				{
					newCell.setCellValue(curCell.getStringCellValue());
				}
				
			}
			
			
		}
		
		curRow=sheet.getRow(startRow-1);
		if(curRow!=null)
		{
			colNum=1;
			for (int intCol=startCol;intCol<=endCol;intCol++) {
				Cell curCell=curRow.getCell(intCol);
				if(curCell!=null)
				{
					Cell newCell = getNewCell(endCol+colNum++, wb, curCell);
					if(curCell.getCellType()==Cell.CELL_TYPE_STRING)
					{
						newCell.setCellValue(curCell.getStringCellValue()+"(上期)");
					}
					
				}
				
			}
		}
		
		
		
		for (int intRow=startRow;intRow<=lastRow;intRow++) {
			curRow=sheet.getRow(intRow);
			if(curRow!=null)
			{
				colNum=1;
				for (int intCol=startCol;intCol<=endCol;intCol++) {
					Cell curCell=curRow.getCell(intCol);
					if(curCell!=null)
					{
						Cell newCell = getNewCell(endCol+colNum++, wb,curCell);
						Comment comment =  curCell.getCellComment(); // 获取批注
						if (comment != null) {
							String comm = comment.getString().getString();
							int nPos = comm.indexOf(ItemTemplate.Start);
							if(nPos > -1) {
								 Drawing patr = sheet.createDrawingPatriarch();
							        // 定义注释的大小和位置，详见文档
							     Comment newComment = patr.createCellComment(new HSSFClientAnchor(0, 0 ,0,0,(short)3, 3 ,(short)5, 20));
							        // 设置注释内容
							     String newComm=comm.replace("期数:"+Period.CurPeriod.toString(), "期数:"+Period.PrePeriod.toString()).replace("可编辑:1", "可编辑:2");
							     newComment.setString(new HSSFRichTextString(newComm));
							     newCell.setCellComment(newComment);
							  
							}
							
						}
						sheet.setColumnWidth(newCell.getColumnIndex(), sheet.getColumnWidth(intCol));
					}
				}
			}
		}
	}
	private Cell getNewCell(Integer colIndex, Workbook wb, Cell curCell) {
		Cell newCell=curCell.getRow().createCell(colIndex);
		CellStyle newCellStyle=wb.createCellStyle(); 
		newCellStyle.cloneStyleFrom(curCell.getCellStyle());
		newCell.setCellStyle(newCellStyle);
		
		return newCell;
	}
	
	
	private void getStartEndRowColInfo(AtomicInteger startRow,AtomicInteger startCol,AtomicInteger endRow,AtomicInteger endCol,Sheet sheet) {
		int lastRow=sheet.getLastRowNum();
		
		for (int intRow=sheet.getFirstRowNum();intRow<lastRow;intRow++) {
			Row curRow=sheet.getRow(intRow);
			int lastCol=curRow.getLastCellNum();
			for (int intCol=curRow.getFirstCellNum();intCol<lastCol;intCol++) {
				Cell curCell=curRow.getCell(intCol);
				Comment comment =  curCell.getCellComment(); // 获取批注
				if (comment != null) {
					String cont = comment.getString().getString();
					int nPos = cont.indexOf(ItemTemplate.Start);
					if(nPos > -1) {
					   if(startRow.intValue()==-1)
					   {
						   startRow.set(intRow);
						   startCol.set(intCol);
						   endRow.set(intRow);
						   endCol.set(intCol);
					   }
					   else if(startRow.intValue()==intRow)
					   {
						   endRow.set(intRow);
						   endCol.set(intCol);
					   }
					   else if(startRow.intValue()!=intRow)
					   {
						   return;
					   }
					  
					}
					
					
				}
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void SaveFormula(Sheet sheet,List<ExcelCellInfo> cellsList,String CalcInstName) {

		CalculationExampleInfo calculationExampleInfo = new CalculationExampleInfo();
		calculationExampleInfo.setStrCalculationName(CalcInstName);
		
		IParamVoidResultExecute deleteDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		
		DetachedCriteria detachedCriteria =null; 
		
		try {
			Level1:for(ExcelCellInfo excelCellInfo:cellsList){
				//20160623 Liaoxl repair 只有指标报表的指标的Excel公式才需要自动保存到计算规则表里，不定长报表部分的指标的Excel公式不需要保存
				//if(excelCellInfo.isFormula()){
				if(excelCellInfo.isFormula() && excelCellInfo.getStrItemCode() != null){
					ItemInfo itemInfo = new ItemInfo();
					itemInfo.setStrItemCode(excelCellInfo.getStrItemCode());
					
					detachedCriteria=DetachedCriteria.forClass(CalculationRule.class);				
					detachedCriteria.add(Restrictions.eq("itemInfo", itemInfo));
					List<CalculationRule> CalculationRuleList = (List<CalculationRule>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(CalculationRuleList.size()>0){
						detachedCriteria = DetachedCriteria.forClass(ExampleInfoRule.class);
						//删除ExampleInfoRule
						for(CalculationRule calculationRule:CalculationRuleList){
							if(!calculationRule.getStrCalculationRuleName().startsWith(System_)){	//20160525 Zhouqin repair 只删除System开头的计算公式，因为有些Excel公式系统不能完全识别，然后人工改正确后，公式名改为不以System开头的了，这种公式就不能删除
								continue Level1;
							}
							detachedCriteria.add(Restrictions.eq("calculationExampleInfo", calculationExampleInfo));
							detachedCriteria.add(Restrictions.eq("calculationRule", calculationRule));
							List<ExampleInfoRule> exampleInfoRuleList = (List<ExampleInfoRule>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(exampleInfoRuleList.size()>0){
								deleteDao.paramVoidResultExecute(new Object[]{detachedCriteria, null});
								
								//删除ItemsCalculation
								detachedCriteria = DetachedCriteria.forClass(ItemsCalculation.class);
								detachedCriteria.add(Restrictions.eq("calculationRule", calculationRule));
								deleteDao.paramVoidResultExecute(new Object[]{detachedCriteria, null});
								
								//删除CalculationRule							
								detachedCriteria = DetachedCriteria.forClass(CalculationRule.class);
								detachedCriteria.add(Restrictions.eq("autoCalculationRuleID", calculationRule.getAutoCalculationRuleID()));
								deleteDao.paramVoidResultExecute(new Object[]{detachedCriteria, null});
							}
							
						}
					}
					
					//保存CalculationRule
					CalculationRule newCalculationRule = new CalculationRule();
					newCalculationRule.setStrCalculationRuleName(System_+excelCellInfo.getStrCell()+"_"+itemInfo.getStrItemCode());
					newCalculationRule.setStartdate("2000-01-01");
					newCalculationRule.setEnddate("2050-01-01");
					newCalculationRule.setIntOrder("1");
					//newCalculationRule.setStrExpression("");
					newCalculationRule.setStrExtendDimension1(excelCellInfo.getStrExt1());
					newCalculationRule.setStrExtendDimension2(excelCellInfo.getStrExt2());
					newCalculationRule.setStrFreq(excelCellInfo.getStrFreq());
					CurrencyType currencyType=new CurrencyType();
					currencyType.setStrCurrencyCode(excelCellInfo.getStrCurrency());
					newCalculationRule.setCurrencyType(currencyType);
					newCalculationRule.setItemInfo(itemInfo);
					ItemProperty itemProperty=new ItemProperty();
					itemProperty.setStrPropertyCode(excelCellInfo.getStrProp());
					newCalculationRule.setItemProperty(itemProperty);				
					singleObjectSaveDao.paramVoidResultExecute(new Object[]{newCalculationRule,null});
					
					//String autoId=newCalculationRule.getAutoCalculationRuleID();
					//保存ExampleInfoRule
					ExampleInfoRule newExampleInfoRule = new ExampleInfoRule();
					newExampleInfoRule.setCalculationRule(newCalculationRule);
					newExampleInfoRule.setCalculationExampleInfo(calculationExampleInfo);
					singleObjectSaveDao.paramVoidResultExecute(new Object[]{newExampleInfoRule,null});
					//保存ItemsCalculation
					List<Object> objectList=new ArrayList<Object>();
					List<WordAnalyClass> wordAnalyClassList = excelCellInfo.getWordAnalyClass();
					int intOrder=1;
					
					boolean noItemCellFlag = false;	//20160525 Liaoxl add 如果Excel计算公式中存在没有绑定指标的单元格时，就把此标志设置为true
					
					//20160525 Liaoxl repair 当整个Excel公式太过复杂，程序无法解析时wordAnalyClassList为null
					if(wordAnalyClassList == null){
						newCalculationRule.setStrDescription("此计算公式无法识别");
						System.out.println("================="+newCalculationRule.getStrCalculationRuleName()+" 此计算公式无法识别，请手工输入========================");
					}
					else{
						for(WordAnalyClass wordAnalyClass:wordAnalyClassList){
							ItemsCalculation newItemsCalculation=new ItemsCalculation();
							newItemsCalculation.setIntOrder(intOrder);
							if(wordAnalyClass.getWordType()==Word_Type_Delimiter || wordAnalyClass.getWordType()==Word_Type_KeyWord){
								newItemsCalculation.setStrItemType("symbol");
								newItemsCalculation.setStrItemValue(wordAnalyClass.getWordValue().toLowerCase());
							}else if(wordAnalyClass.getWordType()==Word_Type_Const){
								newItemsCalculation.setStrItemValue(wordAnalyClass.getWordValue());
								newItemsCalculation.setStrItemType("const");
							}else if(wordAnalyClass.getWordType()==Word_Type_Cell){
								
								Object obj = GetItemInfoByCellInfo(sheet,cellsList,wordAnalyClass.getStrCell());
								//20160525 Liaoxl repair 针对系统自动转换Excel公式时遇到的公式中部分单元格没有启用（灰色的，没有绑定指标）的情况进行修改
								//修改思路：如果指标项取到的值为null，就说明此单元格没有绑定指标，直接跳过，但这样最终的表达式可能会出现错误，只能保存到系统里后手工调整
								if(obj == null){
									noItemCellFlag = true;
									continue;
								}
								else if(obj instanceof ExcelCellInfo){
									ExcelCellInfo info = (ExcelCellInfo)obj;
									String period= info.getStrPeriod().trim().equals("")?"-1":info.getStrPeriod().trim();
									String extend1= info.getStrExt1().trim().equals("")?"-1":info.getStrExt1().trim();
									String extend2= info.getStrExt2().trim().equals("")?"-1":info.getStrExt2().trim();
									String instinfo= info.getInstInfo().trim().equals("")?"-1":info.getInstInfo().trim();
									String curr= info.getStrCurrency().trim().equals("")?"-1":info.getStrCurrency().trim();
									String prop= info.getStrProp().trim().equals("")?"-1":info.getStrProp().trim();
									String freq= info.getStrFreq().trim().equals("")?"-1":info.getStrFreq().trim();
									String v="Time="+period
										+"&ItemCode="+info.getStrItemCode()
										+"&Extend1="+extend1
										+"&Extend2="+extend2
										+"&dtDate="+info.getDtDate()
										+"&instInfo="+instinfo
										+"&Curr="+curr
										+"&Property="+prop
										+"&Freq="+freq;
									newItemsCalculation.setStrItemType("item");
									newItemsCalculation.setStrItemValue(v);
								}
								else{
									newItemsCalculation.setStrItemType("const");
									newItemsCalculation.setStrItemValue(obj.toString());
								}
							}						
							
							newItemsCalculation.setCalculationRule(newCalculationRule);
							objectList.add(newItemsCalculation);
							intOrder++;
						}
					}
					
					//20160525 Liaoxl add
					if(noItemCellFlag == true){
						newCalculationRule.setStrDescription("此计算公式存在缺失的计算项");
						System.out.println("================="+newCalculationRule.getStrCalculationRuleName()+" 此计算公式存在缺失的计算项========================");
					}
					//20160525 Liaoxl add end
					
					singleObjectSaveAllDao.paramVoidResultExecute(new Object[] {objectList, null});
					//计算strExpression
					List<ItemRuleDetail> itemRuleDetailList = CovertToExpression(objectList);	
					IExpressionBuilder builder=(IExpressionBuilder)FrameworkFactory.CreateBean("jsExpressionBuilder");
					Map<String,Object> context=new HashMap<String,Object>();
					context.put(ExpressionContextKey.DATA_PARAM_KEY,itemRuleDetailList);
					builder.setContext(context);
					String exp=builder.build();
					newCalculationRule.setStrExpression(exp);
					singleObjectUpdateDao.paramVoidResultExecute(new Object[]{ newCalculationRule, null});
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	private List<ItemRuleDetail> CovertToExpression(List<Object> itemsCalculationList){
		List<ItemRuleDetail> itemRuleDetailList = new ArrayList<ItemRuleDetail>();
		for(Object o :itemsCalculationList){
			try {
				ItemRuleDetail ird= new ItemRuleDetail(ReflectOperation.getFieldValue(o, "strItemType").toString()
						,ReflectOperation.getFieldValue(o, "strItemValue").toString()
						,ReflectOperation.getFieldValue(o, "intOrder").toString());
				itemRuleDetailList.add(ird);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return itemRuleDetailList;
	}
	private Object GetItemInfoByCellInfo(Sheet sheet,List<ExcelCellInfo> excelCellInfoList, String cell){
		ExcelCellInfo ret=null;
		for(ExcelCellInfo obj :excelCellInfoList){
			if(obj.getStrCell().equals(cell)){
				ret=obj;
				break;
			}
		}
		if(null == ret){
			CellReference cf = new CellReference(cell);
			Cell c = sheet.getRow(cf.getRow()).getCell(cf.getCol());
			if(null != c){
				if(c.getCellType()==Cell.CELL_TYPE_NUMERIC){
					return c.getNumericCellValue()+0+"";
				}
				if(c.getCellType()==Cell.CELL_TYPE_STRING){
					return c.getStringCellValue();
				}
				if(c.getCellType()==Cell.CELL_TYPE_BOOLEAN){
					return c.getBooleanCellValue()+"";
				}
			}
		}
		return ret;
	}
	
	
	private List<ExcelCellInfo> ParseExcelFormula(List<ExcelCellInfo> list){
		List<ExcelCellInfo> unParseList=new ArrayList<ExcelCellInfo>();
		for(ExcelCellInfo cell:list){
			if(cell.isFormula()){
				List<WordAnalyClass> data =String2List(cell.getStrFormula());
				boolean isS=IsSimpleExpression(data);
				if(isS){
					SplitColon(data);
					Translate(data);
					cell.setWordAnalyClass(data);
				}else{
					unParseList.add(cell);
				}
			}
			
		}
		return unParseList;
	}
	
	public List<WordAnalyClass> String2List(String exps){
		List<WordAnalyClass> data = new ArrayList<WordAnalyClass>();
		Queue<String> tmpQueue = new LinkedList<String>();
		for(char sc:exps.toCharArray()){
			if(isDelimiter(sc)){				
				if(!tmpQueue.isEmpty()){
					String word="";
					while(!tmpQueue.isEmpty()){
						word+=tmpQueue.poll();
					}
					if(isKeyWord(word)){
						data.add(new WordAnalyClass(Word_Type_KeyWord,word));
					}else if(IsDigial(word)){						
						data.add(new WordAnalyClass(Word_Type_Const,word));
					}else{						
						data.add(new WordAnalyClass(Word_Type_Cell,word));
					}					
				}		
				data.add(new WordAnalyClass(Word_Type_Delimiter,sc+""));
			}else{
				tmpQueue.add(""+sc);
			}
		}
		if(!tmpQueue.isEmpty()){
			String word="";
			while(!tmpQueue.isEmpty()){
				word+=tmpQueue.poll();
			}
			if(isKeyWord(word)){
				data.add(new WordAnalyClass(Word_Type_KeyWord,word));
			}else if(IsDigial(word)){						
				data.add(new WordAnalyClass(Word_Type_Const,word));
			}else{						
				data.add(new WordAnalyClass(Word_Type_Cell,word));
			}	
		}
		return data;
	}
	
	public boolean IsSimpleExpression(List<WordAnalyClass> data){
		boolean ret=true;
		int lBracket =0;
		Stack<WordAnalyClass> tmp =new Stack<WordAnalyClass>();
		for(WordAnalyClass wac :data){
			tmp.push(wac);
			if(wac.getWordType()==Word_Type_Delimiter && wac.getWordValue().equals(")")){
				lBracket--;				
				if(lBracket==0 && !isSimpleEx(tmp)){
					ret=false;
					break;
				}
			}else{
				if(wac.getWordType()==Word_Type_Delimiter && wac.getWordValue().equals("(")){
					lBracket++;
				}
			}
		}		
		return ret;
	}
	
	public static String IntToMoreChar(int value)
    {
        String rtn = "";
        List<Integer> iList = new ArrayList<Integer>();
        //To single Int
        while(value/26 !=0 || value%26 !=0)
        {
            iList.add(value % 26);
            value/=26;
        }
        //Change 0 To 26
        for (int j=0;j<iList.size()-1;j++)
        {
            if(iList.get(j)==0)
            {
            	iList.set(j+1, iList.get(j+1)-1);
            	iList.set(j, 26);
            }
        }
        //Remove 0 at last
        if(iList.get(iList.size() - 1) == 0)
        {
            iList.remove(iList.get(iList.size() - 1));
        }
        //To String
        for(int j=iList.size()-1; j >=0;j--)
        {
            char c =(char)(iList.get(j)+64);
            rtn += c;
        }
        return rtn;
    }
	
	public boolean isDelimiter(char c){
		boolean flag=false;
		for(int dc:Delimiter){
			if(dc==c){
				flag = true;
				break;
			}
		}
		return flag;
	}
	public boolean isKeyWord(String c){
		boolean flag=false;
		for(String dc:KeyWord){
			if(dc.equals(c)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	public boolean IsDigial(String v){
		boolean flag=true;
		try{
			if(v.charAt(0)>57 || v.charAt(0)<48){
				flag=false;
			}else{
				Double.valueOf(v);
			}			
		}catch(NumberFormatException nfe){
			flag=false;
		}	
		return flag;
	}
	
	/*
	 * 遇到右括号会进这个方法，但右括号不传进来
	 * 
	 */
	public boolean isSimpleEx(Stack<WordAnalyClass> st){
		int count=0;
		while(!st.empty()){
			WordAnalyClass wac=st.pop();
			if(wac.getWordType()==Word_Type_KeyWord && isKeyWord(wac.getWordValue())){
				count++;				
			}
		}		
		return count>1?false:true;
	}
	
	public void SplitColon(List<WordAnalyClass> data){
		Stack<WordAnalyClass> tmpStack = new Stack<WordAnalyClass>();
		boolean IsColon=false;
		for(WordAnalyClass wac:data){
			if(IsColon){//如果上一个是冒号
				WordAnalyClass preWac = tmpStack.peek();//取得冒号前一个单词
				WordAnalyClass nxtWac = wac;//取得冒号后一个单词
				//判断是同行还是同列
				CellInfo preCell = new CellInfo(preWac.getWordValue());
				CellInfo nxtCell = new CellInfo(nxtWac.getWordValue());
				
				if(preCell.getCollIndex().equals(nxtCell.getCollIndex())){
					//同列
					int tRow=preCell.getRowIndex()+1;
					while(tRow<=nxtCell.getRowIndex()){
						String newCell=preCell.getCollIndex()+tRow;
						tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,","));
						tmpStack.push(new WordAnalyClass(Word_Type_Cell,newCell));
						tRow++;
					}
				}else if(preCell.getRowIndex()==nxtCell.getRowIndex()){
					//同行					
					int tCell=MoreCharToInt(preCell.getCollIndex())+1;
					int lstCell=MoreCharToInt(nxtCell.getCollIndex());
					while(tCell<=lstCell){
						String chr=IntToMoreChar(tCell);
						String newRow=chr+preCell.getRowIndex();
						tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,","));
						tmpStack.push(new WordAnalyClass(Word_Type_Cell,newRow));
						tCell++;
					}
				}else{
					//不同行不同列
					int tCell=MoreCharToInt(preCell.getCollIndex());
					int lstCell=MoreCharToInt(nxtCell.getCollIndex());
					boolean isFirstRow=true;
					while(tCell<=lstCell){
						String chr=IntToMoreChar(tCell);
						int tRow=preCell.getRowIndex();
						if(isFirstRow){
							tRow++;
							isFirstRow =!isFirstRow;
						}
						
						while(tRow<=nxtCell.getRowIndex()){
							String newCell=chr+tRow;
							tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,","));
							tmpStack.push(new WordAnalyClass(Word_Type_Cell,newCell));
							tRow++;
						}						
						tCell++;
					}
				}
				IsColon=false;
				continue;
			}
			if(wac.getWordType()==Word_Type_Delimiter && wac.getWordValue().equals(":")){
				IsColon=true;
			}else{
				tmpStack.push(wac);
			}
		}		
		
		data.clear();
		
		while(!tmpStack.empty()){
			data.add(tmpStack.pop());
		}
		
		Collections.reverse(data);
	}
	
	public int MoreCharToInt(String value){
		int rtn = 0;
		int powIndex = 0;
		for (int i = value.length() - 1; i >= 0; i--){
			int tmpInt = value.charAt(i)-64;
			rtn += (int)Math.pow(26, powIndex) * tmpInt;
			powIndex++;
		}
		return rtn;
	}
	
	public void Translate(List<WordAnalyClass> data){
		Stack<WordAnalyClass> tmpStack = new Stack<WordAnalyClass>();
		Stack<WordAnalyClass> tmpStack1 = new Stack<WordAnalyClass>();
		int lBracket=0;
		for(WordAnalyClass wac : data){
			tmpStack.push(wac);
			
			if(wac.getWordType()==Word_Type_Delimiter && wac.getWordValue().equals("(")){
				lBracket++;
			}else if(wac.getWordType()==Word_Type_Delimiter && wac.getWordValue().equals(")")){
				lBracket--;
				
				if(lBracket==0){
					WordAnalyClass curWac=null;				
					while(!tmpStack.empty() && !isKeyWord(tmpStack.peek().getWordValue())){
						tmpStack1.add(tmpStack.pop());
					}
					if(!tmpStack.empty()){
						curWac=tmpStack.pop();
						
						if(curWac.getWordValue().equals("SUM")){
							while(!tmpStack1.empty()){
								WordAnalyClass wac2 = tmpStack1.pop();
								if(wac2.getWordType()==Word_Type_Delimiter && wac2.getWordValue().equals(",")){
									wac2.setWordValue("+");
								}
								tmpStack.add(wac2);
							}
						}else if(curWac.getWordValue().equals("IF")){
							tmpStack.push(curWac);
							int point=0;
							while(!tmpStack1.empty()){
								curWac=tmpStack1.pop();
								if(point==0){
									if(curWac.getWordType()==Word_Type_Delimiter && curWac.getWordValue().equals("=")){
										if(tmpStack.peek().getWordType()==Word_Type_Delimiter 
												&&(tmpStack.peek().getWordValue().equals("!") 
														|| tmpStack.peek().getWordValue().equals("<")
														|| tmpStack.peek().getWordValue().equals(">"))){											
											curWac.setWordValue(tmpStack.pop().getWordValue()+curWac.getWordValue());
										}else{
											curWac.setWordValue("==");
										}
									}
								}
								
								if(curWac.getWordType()==Word_Type_Delimiter && curWac.getWordValue().equals(",")){
									if(point==0){
										tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,")"));
										tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,"{"));
									}else{
										tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,"}"));
										tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,"ELSE"));
										tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,"{"));
									}
									point++;
								}else{
									tmpStack.push(curWac);
								}
							}
							tmpStack.pop();
							tmpStack.push(new WordAnalyClass(Word_Type_Delimiter,"}"));
						}else{
							tmpStack.push(curWac);
							while(!tmpStack1.empty()){
								tmpStack.push(tmpStack1.pop());
							}
						}
					}else{
						while(!tmpStack1.empty()){
							tmpStack.push(tmpStack1.pop());
						}
					}
				}
			}
		}
		
		data.clear();
		
		while(!tmpStack.empty()){
			data.add(tmpStack.pop());
		}
		
		Collections.reverse(data);
	}
	
	private void Cell2ItemCode(List<ExcelCellInfo> list){
		for(ExcelCellInfo info :list){
			if(info.isFormula() && null!=info.getWordAnalyClass()){
				List<WordAnalyClass> wordAnalyList= info.getWordAnalyClass();
				for(WordAnalyClass wac: wordAnalyList){
					if(wac.getWordType()==Word_Type_Cell){
						String subCell=wac.getWordValue();
						wac.setWordValue(Cell2ItemCode(list,subCell));
					}
				}
			}			
		}
	}
	private String Cell2ItemCode(List<ExcelCellInfo> list,String cell){
		String result="";
		for(ExcelCellInfo info :list){
			if(info.getStrCell().equals(cell)){
				result=info.getStrItemCode();
				break;
			}
		}
		return result;
	}
}
