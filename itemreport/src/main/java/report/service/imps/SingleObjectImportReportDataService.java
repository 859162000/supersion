package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dao.imps.ItemDataCacheManger;
import report.dto.CurrencyType;
import report.dto.ItemBindInfo;
import report.dto.ItemData;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.RptDto;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;
import coresystem.dto.InstInfo;
import extend.dto.ReportModel_Table;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

// 主要功能：根据当前DTO创建出SQL语句和连接供DAO执行
public class SingleObjectImportReportDataService extends BaseVoidDaoResultService {

	private ItemTemplate itemTemplate;
	@Override
    public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		File file = RequestManager.getUploadFile();
		
		if(file == null) {
			setServiceResult(new MessageResult(false, "文件不存在"));
			return;
		}

		String oldPath = file.getPath(); //文件存放路径
		InputStream inputStream = new FileInputStream(file);
		
		if(inputStream == null) {
			setServiceResult(new MessageResult(false, "文件无法读取"));
			return;
		}
		
        ImportData(oldPath);
	}
	
	private void ImportData(String path) throws Exception { // 导入报表
				
		// 取当前TaskRptInst
		Object oldID = RequestManager.getId();
		RequestManager.setId(RequestManager.getLevelIdValue());
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskRptInst taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(null);
		RequestManager.setId(oldID);
		if(taskRptInst == null) return;
		if("2".equals(taskRptInst.getSubmitStatus())||
				"2".equals(taskRptInst.getSubmitStatus()))
		{
			setServiceResult(new MessageResult(false, "已提交或审核通过的报表不能继续导入数据"));
			return ;
		}
		itemTemplate= new ItemTemplate(taskRptInst);
		ItemDataCacheManger cacheManager=ItemDataCacheManger.getInsance();
		
		// 取TaskRptInst的日期，机构作为参数
		
		Date taskDate = taskRptInst.getTaskFlow().getDtTaskDate();
		InstInfo taskInstInfo = taskRptInst.getInstInfo();
		
		String strFreq=taskRptInst.getTaskFlow().getStrFreq();
		String strDate=TypeParse.parseString(taskDate, "yyyy-MM-dd");
		
		// 取rptTaskInst的rptInfo及其明细表、指标信息
		for(RptDto rptDto : taskRptInst.getRptInfo().getRptDto()) {
			// 取出各明细表
			ReportModel_Table table = rptDto.getTable();
			table.getStrTableName();
		}
		
		Integer nRptUnit = 1; // 单位
		if(taskRptInst.getRptInfo().getIntRptUnit() != null)
			nRptUnit = taskRptInst.getRptInfo().getIntRptUnit();
		
		Integer nRptPrecise = 2; // 精度
		if(taskRptInst.getRptInfo().getIntPrecise() != null)
			nRptPrecise = taskRptInst.getRptInfo().getIntPrecise();
		
		// 根据当前DTO取出查询条件并组装DetachedCriteria
		DetachedCriteria detachedCriteria = null;
		IParamObjectResultExecute dao = null;
		IParamVoidResultExecute dao2 = null;
		List<Object> objectList = null;
		
		
		String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
		String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
		String tempFilePath =strRootPath + strTmpRootPath + "/" +taskRptInst.getRptInfo().getStrRptPath()+".xls";
		
		File tempFile = new File(tempFilePath);
		if(!tempFile.exists()) {
			setServiceResult(new MessageResult(false, "模板文件不存在"));
			return ;
		}
		
		
		Workbook wwb,tempWorkbook; // 导入文件，模板文件
		FileInputStream ipt = new FileInputStream(path);
		FileInputStream tempIpt = new FileInputStream(tempFile.getPath());
		
		FormulaEvaluator evaluator = null;
		
		try{
			POIFSFileSystem fs = new POIFSFileSystem(ipt);
			wwb = new HSSFWorkbook(fs);
			
			POIFSFileSystem tempFs = new POIFSFileSystem(tempIpt);
			tempWorkbook = new HSSFWorkbook(tempFs);

			wwb.setForceFormulaRecalculation(true);
			evaluator = wwb.getCreationHelper().createFormulaEvaluator();
			
		}catch (Exception ex) {
			ExceptionLog.CreateLog(ex);
			wwb = new XSSFWorkbook(ipt);  // 创建excel文件对象 
			tempWorkbook = new XSSFWorkbook(tempIpt);  // 创建excel文件对象
			
			wwb.setForceFormulaRecalculation(true);
			evaluator = wwb.getCreationHelper().createFormulaEvaluator();
		}
		
		
		List listToSave = new ArrayList();
	    Sheet sheet = wwb.getSheetAt(0);
	    Sheet tempSheet = tempWorkbook.getSheetAt(0);
	    int skew = 0; // 偏移行数
	    
	    
	    // 保存数据,遍历数据excel的行列
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			
			Row rowList = sheet.getRow(i); // 当前行
			Row tempRowList = tempSheet.getRow(i-skew); // 当前行对应的模板行
			if(rowList == null || tempRowList == null) continue;
			
			for (int j = 0; j < rowList.getLastCellNum(); j++) {
				Date date=taskDate;
				InstInfo instInfo=taskInstInfo;
				//StringBuffer cellValue = new StringBuffer();
				HSSFCell cell = (HSSFCell) rowList.getCell(j);
				HSSFCell tempCell = (HSSFCell) tempRowList.getCell(j);
				String comm = "";
				if(cell !=null && tempCell !=null && tempCell.getCellComment() != null){
					switch (tempCell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							if(DateUtil.isCellDateFormatted(cell)){
								SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
								cell.setCellValue(format.format(cell.getDateCellValue()));
							}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							break;
						default:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							break;
					}
					
					comm = tempCell.getCellComment().getString().toString();
				}
				

				String cellValue=comm; // 将单元格的值设置为模板的注解
				
				// 解析模板中的指标，并设置值
				// 指标格式:[指标][ItemCode;Property]
				int nPos = cellValue.indexOf(ItemTemplate.Start);
				if(nPos > -1) {
					
					String strVal = null;
					if(tempCell.getCellType()==Cell.CELL_TYPE_FORMULA){
						strVal = String.valueOf(evaluator.evaluate(cell).getNumberValue());  
					}
					else{
						strVal = evaluator.evaluate(cell).getStringValue();
					}
					
					ItemBindInfo itemBindInfo=itemTemplate.getItemBindInfo(cellValue, instInfo.getStrInstCode(), strDate, strFreq,true);
					if(!instInfo.getStrInstCode().equals(itemBindInfo.instCode)){
						instInfo = new InstInfo();
						instInfo.setStrInstCode(itemBindInfo.instCode);
					}
					
					// 在当前报表中找出对应指标值
					ItemInfo itemInfo =cacheManager.getItem(itemBindInfo.itemCode);
					
					itemInfo.setStrItemCode(itemBindInfo.itemCode);
					ItemProperty itemProperty = new ItemProperty();
					itemProperty.setStrPropertyCode(itemBindInfo.property);
					CurrencyType currencyType = new CurrencyType();
					currencyType.setStrCurrencyCode(itemBindInfo.currency);
					
					detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.ItemData"));
					detachedCriteria.add(Restrictions.eq("instInfo", instInfo));
					detachedCriteria.add(Restrictions.eq("dtDate", date));
					detachedCriteria.add(Restrictions.eq("itemInfo", itemInfo));
					detachedCriteria.add(Restrictions.eq("itemProperty", itemProperty));
					detachedCriteria.add(Restrictions.eq("currencyType", currencyType));
					if(!StringUtils.isBlank(itemBindInfo.ext1)){
						detachedCriteria.add(Restrictions.eq("strExtendDimension1", itemBindInfo.ext1));
					}
					if(!StringUtils.isBlank(itemBindInfo.ext2)){
						detachedCriteria.add(Restrictions.eq("strExtendDimension2", itemBindInfo.ext2));
					}
					
					if(!StringUtils.isBlank(itemBindInfo.freq))
					{
						detachedCriteria.add(Restrictions.eq("strFreq", itemBindInfo.freq));
					}
					dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			    	objectList = (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			    	
			    	ItemData it ;
			    	if(objectList.size() >0)  // 指标已经有值
			    		 it = (ItemData) objectList.get(0);
		    		else
		    		{
		    			it = new ItemData();
		    			it.setInstInfo(instInfo);
		    			it.setDtDate(date);
		    			it.setItemInfo(itemInfo);
		    			it.setItemProperty(itemProperty);
		    			it.setCurrencyType(currencyType);
		    			it.setStrExtendDimension1(itemBindInfo.ext1);
		    			it.setStrExtendDimension2(itemBindInfo.ext2);
		    			it.setStrFreq(itemBindInfo.freq);
		    			it.setStrCheckState("1");
		    		}

   	    		
		    		if(!StringUtils.isBlank(strVal))
		    		{
		    			// 进行类型校验
						boolean bCheckOK = true;
						if(it.getItemInfo().getStrDataType().equals(ItemDataType.Amount.toString()) // 金额
								|| it.getItemInfo().getStrDataType().equals(ItemDataType.Num.toString())	// 数值
								|| it.getItemInfo().getStrDataType().equals(ItemDataType.Percent.toString())) { 	//百分比
							
							Double v=DoubleUtil.parse(strVal);
							if(v.equals(Double.MAX_VALUE)){
								// 记录校验结果
								it.setStrCheckState("3");
								it.setStrCheckResult("数值格式错误");
								bCheckOK = false;
							}
							else {
								it.setStrCheckState("1"); // 设置为未校验
								it.setStrCheckResult("");
							}
						}
						else if(it.getItemInfo().getStrDataType().equals(ItemDataType.Date.toString())) {
							if(TypeParse.parseDate(strVal).equals(TypeParse.maxDate())){
								// 记录校验结果
								it.setStrCheckState("3");
								it.setStrCheckResult("日期格式错误");
								bCheckOK = false;
							}
							else {
								it.setStrCheckState("1");
								it.setStrCheckResult("");
							}
						}
						

						// 换算金额指标单位
						if(nRptUnit != 0 && it.getItemInfo().getStrDataType().equals("2") && bCheckOK) {
			    			Double dVal = DoubleUtil.parse(strVal);
			    			dVal *= nRptUnit;
			    			strVal = String.format("%." + nRptPrecise + "f", dVal);
			    		}
						else if(it.getItemInfo().getStrDataType().equals("5") && bCheckOK)	//百分比数据
						{
							Double dVal = DoubleUtil.parse(strVal);
							dVal *= 100;
							strVal = String.format("%." + nRptPrecise + "f", dVal);
						}
						
						if(bCheckOK) {
							it.setStrValue(strVal);
							
							// 添加到新增列表中
							listToSave.add(it);
						}
						else {
							String strSaveErr = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("saveErrorItem");
							if(strSaveErr!= null && strSaveErr.equals("true")) {
								it.setStrValue(strVal);
								
								// 添加到新增列表中
								listToSave.add(it);
							}
						}

		    		}
                }

                StringBuilder detailCellValue=new StringBuilder(comm);
				int nPos1 = detailCellValue.indexOf(ItemTemplate.DetailStart); // 明细位置
				if(nPos1 > -1) { // 字段格式:[字段][TableName;FieldName]
						
					List object = new ArrayList<Object>();
					Row oldRowList = tempSheet.getRow(i-skew);
					
					int nTabStart = detailCellValue.indexOf("\n") + 1;
					int nTabEnd = detailCellValue.indexOf("\n", nTabStart);
					String strTableName = detailCellValue.substring(nTabStart, nTabEnd);
					if(strTableName.indexOf(":") > -1)
						strTableName = strTableName.substring(strTableName.indexOf(":") + 1);// 取":"后页面部分
					//String strDTOPackage = ReflectOperation.getAutoDtoPackage("sessionFactory");
			    	
					// 根据条件查询出表数据
					Class<?> type;
					try {
						type = Class.forName(strTableName);
					} catch (Exception e) {
						setServiceResult(new MessageResult(false, "DTO："+strTableName+" 不存在!"));
						return;
					}

			    	Object obj = type.newInstance();
					if(ReflectOperation.getFieldByName(type, "instInfo") != null)
						ReflectOperation.setFieldValue(obj, "instInfo",  instInfo);
					if(ReflectOperation.getFieldByName(type, "dtDate") != null)
						ReflectOperation.setFieldValue(obj, "dtDate",  date);
					if(ReflectOperation.getFieldByName(type, "RPTCheckType") != null)
						ReflectOperation.setFieldValue(obj, "RPTCheckType",  '0');
			    	
					detachedCriteria = DetachedCriteria.forClass(type);
					if(ReflectOperation.getFieldByName(type, "instInfo") != null)
						detachedCriteria.add(Restrictions.eq("instInfo",  instInfo));
					if(ReflectOperation.getFieldByName(type, "dtDate") != null)
						detachedCriteria.add(Restrictions.eq("dtDate",  date));
					if(ReflectOperation.getFieldByName(type, "RPTCheckType") != null)
						ReflectOperation.setFieldValue(obj, "RPTCheckType",  '0');

					dao2 = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
			    	dao2.paramVoidResultExecute(new Object[]{detachedCriteria, null});

		    		while(j < rowList.getLastCellNum()){ // 处理当前行的每个字段
		    			
		    			detailCellValue.setLength(0); // 重取本列模板绑定信息
						if(oldRowList.getCell(j) != null && oldRowList.getCell(j).getCellComment() != null){
							 detailCellValue.append(oldRowList.getCell(j).getCellComment().getString());
						}

						cell = (HSSFCell) rowList.getCell(j); // 当前导入文件中的单元格
						if(cell !=null){
							switch (cell.getCellType()) {
								case Cell.CELL_TYPE_NUMERIC:
									if(DateUtil.isCellDateFormatted(cell)){
										SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
										cell.setCellValue(format.format(cell.getDateCellValue()));
									}else{
										cell.setCellType(Cell.CELL_TYPE_STRING);
									}
									break;
								default:
									cell.setCellType(Cell.CELL_TYPE_STRING);
									break;
							}
						}
						
						
						if(detailCellValue.indexOf("[字段]") > -1) { // 处理字段,取出字段名
			    			int nFieldStart = detailCellValue.indexOf("\n字段:")+1;
			    			//int nFieldEnd = cellValue.indexOf("]", nFieldStart-1);
			    			String strfield = detailCellValue.substring(nFieldStart);
			    			if(strfield.indexOf(":") > -1)
			    				strfield = strfield.substring(strfield.indexOf(":") + 1);// 取":"后页面部分
			    			
			    			String strCell = cell.toString();
							if(ReflectOperation.getFieldByName(type, strfield) == null) {
								setServiceResult(new MessageResult(false, "DTO："+strTableName+" 不存在字段："+strfield));
								return;
							}
			    			Field field = type.getDeclaredField(strfield);

							String tagMethodName = null; // 检查是否对应取列表方法
			    			if(field.isAnnotationPresent(IColumn.class)) {
								IColumn iColumn = field.getAnnotation(IColumn.class);
								if(!StringUtils.isBlank(iColumn.tagMethodName())) {
									tagMethodName = iColumn.tagMethodName();
								}
							}


							if(!StringUtils.isBlank(tagMethodName)) { // 有列表方法对应,调用取得列表map
								if(!"".equals(strCell)){
									Method method = Class.forName(RequestManager.getTName()).getDeclaredMethod(tagMethodName);
									Map<String,String> tagMap =  (Map<String,String>)method.invoke(Class.forName(RequestManager.getTName()));
				
									Set<String> kset = tagMap.keySet(); 
									for(String ks:kset){     
										if(strCell.equals(tagMap.get(ks))){
											strCell = ks;
										} 
									}
									ReflectOperation.setFieldValue(obj, strfield, strCell);
									ReflectOperation.setFieldNullValue(obj, ReflectOperation.getPrimaryKeyField(type).getName(), String.class);
							    	//object.add(obj);
								}
							}else{ // 没有列表方法
								if(field.isAnnotationPresent(JoinColumn.class)){	// 有关联表，则取关联表的关键名
									if(strCell != null && !strCell.equals("")){
										String beanID = "singleObjectFindByCriteriaDao";
										dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
										Field keyNameField = ReflectOperation.getKeyNameField(field.getType());
										
										detachedCriteria = DetachedCriteria.forClass(field.getType());
										detachedCriteria.add(Restrictions.eq(keyNameField.getName(), strCell.toString()));
			
										Object tObject = dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										
										if(tObject != null && ((ArrayList<Object>)tObject).size()>0){
											ReflectOperation.setFieldValue(obj, strfield, ((ArrayList<Object>)tObject).get(0));
											ReflectOperation.setFieldNullValue(obj, ReflectOperation.getPrimaryKeyField(type).getName(), String.class);
									    	//object.add(obj);
										}else{
											setServiceResult(new MessageResult(false, "错误内容："+(i+1)+"行"+(j+1)+"列：”"+strCell+"“,在关联表"+strfield+"中不存在"));
											return;
										}
									}else{
										JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
										if(!joinColumn.nullable()){
											setServiceResult(new MessageResult(false, "错误内容："+(i+1)+"行"+(j+1)+"列：”"+strCell+"“,在"+type.getName()+"中不能为空"));
											return;
										}
									}
								}else{
									 if(field.isAnnotationPresent(Column.class)){
										 Column Column = field.getAnnotation(Column.class);
										if(!Column.nullable() &&( strCell==null || "".equals(strCell.trim()))){
											setServiceResult(new MessageResult(false, "错误内容："+(i+1)+"行"+(j+1)+"列：”"+strCell+"“,在"+type.getName()+"中不能为空"));
											return;
										}
										ReflectOperation.setFieldValue(obj, strfield, strCell);
										//ReflectOperation.setFieldNullValue(obj, ReflectOperation.getPrimaryKeyField(type).getName(), String.class);
								    	//object.add(obj);
									 }
								}
							}

			    		}
						
		    			j++;
						if(j == rowList.getLastCellNum()) { // 行末时决定是否表数据结束
							object.add(obj); // 行结束时将对象存入列表
							
					    	Row row = sheet.getRow(i+1);
					    	if(row != null){
						    	cell = (HSSFCell) row.getCell(0);
						    	if(cell != null && cell.getCellComment() != null) {
						    		String isEnd = cell.getCellComment().getString().toString();
						    		if(isEnd.indexOf("[表结束]") > -1) {
						    			break;
						    		}
						    		else {
						    			skew++;
						    		}
						    	}
						    	else {
					    			skew++;
						    	}
						    	
						    	i++;//进入下一行
						    	j=0;
						    	
						    	obj = type.newInstance();
						    	Row tempRow = tempSheet.getRow(i+1-skew);
						    	tempCell = (HSSFCell) tempRow.getCell(0);
						    	
								ReflectOperation.setFieldValue(obj, "instInfo",  instInfo);
								ReflectOperation.setFieldValue(obj, "dtDate",  date);
						    	rowList = sheet.getRow(i+0);
					    	}
						}
			    	}
		    		
		    		if(object.size()>0)
		    		{
		    			IParamVoidResultExecute dao1 = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				    	dao1.paramVoidResultExecute(new Object[]{object,null});
		    		}
		    		
					
					
			    	nPos1 = -1;
				}
			}
		}
		
		// 执行指标批量更新和新增
		IParamVoidResultExecute daoSaveAll = (IParamVoidResultExecute)FrameworkFactory.CreateBean("itemDataCacheDao");
		daoSaveAll.paramVoidResultExecute(new Object[] {listToSave, null});
	}
}
