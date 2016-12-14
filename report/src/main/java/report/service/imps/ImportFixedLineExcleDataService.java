package report.service.imps;

import java.io.File;
import java.io.FileInputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.security.SecurityContext;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class ImportFixedLineExcleDataService implements IObjectResultExecute  {

	private File dataFile;
	
	private String fileName;
	
	private Map<String,String> importRelaMap;
	
	private Map<String,String> jcDefaultValueMap;
	
	private Map<String,String> mxDefaultValueMap;
	
	private Map<String,String> jcExtendValueMap; 
	
	private Map<String,String> mxExtendValueMap; 
	
	private Map<String,String> imprtCheckMap;
	
	private boolean jxOverWrite;
	
	private boolean mxOverWrite;
	
	private String mxTypeCell;
	
	private Map<String,String> mxTypeCellRela;
	
	private Map<String,String> mxLogicPrimaryKey;
	
	private ArrayList<Object> insertList=new ArrayList<Object>();
	private ArrayList<Object> updateList=new ArrayList<Object>();
	private Map<String,String> importRelaMap_jc = new HashMap<String,String>();
	private Map<String,String> importRelaMap_mx = new HashMap<String,String>();
	
	public Object objectResultExecute(){
		try{
			if(dataFile == null){
				dataFile = RequestManager.getUploadFile();
			}
			if(fileName == null){
				fileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
			}
			if(dataFile == null || fileName == null){
				return new MessageResult("请选择文件");
			}
			importRelaMap_mx.putAll(importRelaMap);
			for(Map.Entry<String, String> entry : importRelaMap.entrySet()){
				if(ReflectOperation.getOneToManyField(entry.getKey().toString()).size()>0){
					importRelaMap_jc.put(entry.getKey(), entry.getValue());
					importRelaMap_mx.remove(entry.getKey());
					break;
				}
			}
			ArrayList<Object> objectList = null;
			try{
				objectList = getDataFromExcel(); // 获取数据
			}catch (Exception e) {
				return new MessageResult(e.getMessage());
			}
			if(objectList==null){
				return new MessageResult("导入失败，只支持03Excel");
			}
		
			MessageResult messageResult = (MessageResult)checkData(objectList); // 数据校验
			
			if(messageResult.isSuccess()){
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{updateList,null});
				
				IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				singleObjectSaveAllDao.paramVoidResultExecute(new Object[]{insertList,null});
				
				List<Object> objectAllList=new ArrayList<Object>();
				objectAllList.addAll(updateList);
				objectAllList.addAll(insertList);
				
				totalJCCheckStatus(objectAllList);//统计基础段的状态
				
				totalTaskModelInstCheckStatus(objectAllList);//统计任务层状态
			}
			
			return new MessageResult("导入成功");
		}catch (Exception e) {
			if(null!=e.getMessage()){
				return new MessageResult("导入失败+\r\n"+e.getMessage());
			}else{
				return new MessageResult("导入失败");
			}			
		}
	}
	private ArrayList<Object> getDataFromExcel() throws Exception{

		ArrayList<Object> dataObjectList = new ArrayList<Object>(); // 数据对象列表
		ArrayList<String> fileNameList = new ArrayList<String>(); // 文件名列表
		ArrayList<String> inputStreamList = new ArrayList<String>();  // 文件流列表
		ArrayList<String> keyList = new ArrayList<String>(); // key 列表
		for(String jc_key : importRelaMap_jc.keySet()){
			keyList.add(jc_key);
		}
		
		if(fileName.endsWith(".zip")){
			List<File> fileList = SmallTools.unZipFiles(dataFile, "");
			for (File file : fileList) {
				fileNameList.add(file.getName()); // 解压zip文件  问题
				inputStreamList.add(file.getPath()); // 形成流
			}
			//SmallTools.delFileList(fileList); // 清空临时文件
		}
		else{
			fileNameList.add(fileName);
			inputStreamList.add(dataFile.getPath());
		}
		
		for (int i = 0; i < fileNameList.size(); i++) {
			String fileName = fileNameList.get(i); // 文件路径
			if(fileName.endsWith(".xls")){
				
				ArrayList<String> singleKeyList = new ArrayList<String>();
				singleKeyList.addAll(keyList);
				ArrayList<Object> singleDataObjectList = new ArrayList<Object>(); 
				FileInputStream fis = new FileInputStream(new File(inputStreamList.get(i)));				
				HSSFWorkbook wwb = new HSSFWorkbook(fis);
				Sheet sheet = wwb.getSheetAt(0);
				if(mxTypeCell!=null){
					String[] mxTypeCellArr = mxTypeCell.split("-");
					Cell cellValue = sheet.getRow(Integer.parseInt(mxTypeCellArr[0])).getCell(Integer.parseInt(mxTypeCellArr[1]));
					if(cellValue.getStringCellValue() == null || cellValue.getStringCellValue().equals("")){
						throw new NullPointerException("文件："+fileName+", sheet页:"+sheet.getSheetName()+", 第"+(Integer.parseInt(mxTypeCellArr[0])+1)+"行，第"+(Integer.parseInt(mxTypeCellArr[1])+1)+"列，信息记录类型不能为空");
					}
					String mxTable = cellValue.getStringCellValue().substring(0,2);
					singleKeyList.add(mxTypeCellRela.get(mxTable));
				}else{
					for(String mx_key : importRelaMap_mx.keySet()){
						singleKeyList.add(mx_key);
					}
				}
				for (int j = 0; j < singleKeyList.size(); j++) {
					String dtoName = singleKeyList.get(j);

					Object object  = Class.forName(dtoName).newInstance();
					String strFieldIndexs = importRelaMap.get(dtoName);
					String[] strFieldIndex = strFieldIndexs.split(",");
					for (int k = 0; k < strFieldIndex.length; k++) {
						String[] fieldIndex = strFieldIndex[k].split(":");
						String fieldName = fieldIndex[0]; 
						String[] index = fieldIndex[1].split("-");
						int rowIndex = Integer.parseInt(index[0]);
						int cellIndex = Integer.parseInt(index[1]);
						String type = "MONEY";
						if(index.length>2){
							type =  index[2];
						}
						Cell cell = sheet.getRow(rowIndex).getCell(cellIndex);
						String fieldValue = getCellStr(cell, type);
						ReflectOperation.setFieldValue(object, fieldName, fieldValue);
					}
					
					//添加默认值
					if(importRelaMap_jc.containsKey(dtoName)){
						if(jcDefaultValueMap!=null){
							for (Map.Entry<String,String> entry: jcDefaultValueMap.entrySet()) {
								ReflectOperation.setFieldValue(object, entry.getKey(), entry.getValue());
							}
						}
						//添加扩展字段
						if(jcExtendValueMap!=null){
							addExtendFieldValue(jcExtendValueMap, object, singleDataObjectList);
						}
					}else{
						if(mxDefaultValueMap!=null){
							for (Map.Entry<String,String> entry: mxDefaultValueMap.entrySet()) {
								ReflectOperation.setFieldValue(object, entry.getKey(), entry.getValue());
							}
						}
						if(mxExtendValueMap!=null){
							addExtendFieldValue(mxExtendValueMap, object, singleDataObjectList);
						}
					}
					singleDataObjectList.add(object);
				}
				fis.close();
				
				boolean jcIsExsit = false;
				for(Object object : singleDataObjectList){
					if(importRelaMap_jc.containsKey(object.getClass().getName())){
						String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(object.getClass().getName());
						String[] primaryKeys = primaryKey.split(",");

						boolean isExsit = false;
						for(Object dataObject : dataObjectList){
							if(dataObject.getClass().getName().equals(object.getClass().getName())){
								boolean isSameObject = true;
								for (String strFieldName : primaryKeys) {
									if(!ReflectOperation.getFieldValue(dataObject, strFieldName).equals(ReflectOperation.getFieldValue(object, strFieldName))){
										isSameObject = false;
										break;
									}
								}
								if(isSameObject){
									isExsit = true;
									break;//可能有问题
								}
							}
						}
						
						if(!isExsit){
							dataObjectList.add(object);
						}else{
							jcIsExsit = true;
							break;
						}
					}else{
						dataObjectList.add(object);
					}
				}
				
				if(jcIsExsit){
					continue;
				}
			}
			else if(fileName.endsWith(".xlsx")){
				return null; // 问题
			}
			else{
				return null; // 问题
			}
		}
		List<File> delList=new ArrayList<File>();
		for(String s :inputStreamList){
			delList.add(new File(s));
		}
		SmallTools.delFileList(delList);
		return dataObjectList;
	}
	
	private MessageResult checkData(ArrayList<Object> objectList) throws Exception{
		Object jcObject = null;
		
		//判读逻辑主键是否重复
		for (Object obj : objectList) {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(obj.getClass());
			if(importRelaMap_jc.containsKey(obj.getClass().getName())){
				String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(obj.getClass().getName());
				String[] primaryKeys = primaryKey.split(",");
				for (String strFieldName : primaryKeys) {
					detachedCriteria.add(Restrictions.eq(strFieldName, ReflectOperation.getFieldValue(obj, strFieldName)));
				}
				jcObject = obj;
				List<Object> objList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(objList.size()>0){
					jcObject = objList.get(0);
				}
			}else{
				if(mxLogicPrimaryKey!=null){
					String entryKey = obj.getClass().getName();
					String entryValue = mxLogicPrimaryKey.get(entryKey);
					String[] entryValues=entryValue.split(",");
					for (String str : entryValues) {
						if(str.equals("FOREIGNID")){
							detachedCriteria.add(Restrictions.eq("FOREIGNID", jcObject));
						}else{
							detachedCriteria.add(Restrictions.eq(str, ReflectOperation.getFieldValue(obj, str)));
						}
					}
					
					ReflectOperation.setFieldValue(obj, "FOREIGNID", jcObject);
				}
			}
			List<Object> objList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(objList.size()>0){
				if(mxOverWrite){
					String RPTSubmitStatus="";
					if(importRelaMap_jc.containsKey(objList.get(0).getClass().getName())){
						RPTSubmitStatus=ReflectOperation.getFieldValue(objList.get(0), "RPTSubmitStatus").toString();
					}else{
						RPTSubmitStatus=ReflectOperation.getFieldValue(ReflectOperation.getFieldValue(objList.get(0), "FOREIGNID"), "RPTSubmitStatus").toString();
					}
					if(RPTSubmitStatus.equals("1")){
						ReflectOperation.CopyColumnFieldValue(obj, objList.get(0));
						updateList.add(objList.get(0));
					}
				}
				else{
					insertList.add(obj);
				}
			}else{
				String fieldName = ReflectOperation.getPrimaryKeyField(obj.getClass().getName()).getName();
				ReflectOperation.setFieldValue(obj, fieldName, UUID.randomUUID().toString().replaceAll("-", ""));
				insertList.add(obj);
			}
		}

		//调用校验类及实例
		for (Object obj : updateList) {
			String[] strCheckClass=new String[]{};
			String checkInstance="";
			if(imprtCheckMap!=null){
				for (Map.Entry<String, String> entry: imprtCheckMap.entrySet()) {
					if(entry.getKey().equals(obj.getClass().getName())){
						strCheckClass=entry.getValue().split(",");
					}
				}
			}
			
			for(int k=0;k<strCheckClass.length;k++){
				if(strCheckClass[k].indexOf("-")>0){
					checkInstance=strCheckClass[k].split("-")[1];
					strCheckClass[k]=strCheckClass[k].split("-")[0];
				}
			}
			
			Object currentTObject = RequestManager.getTOject();
			String currentTName = RequestManager.getTName();
			String defaultCheckInstance = LogicParamManager.getDefaultCheckInstance();
			RequestManager.setTOject(obj);
			RequestManager.setTName(obj.getClass().getName());
			
			LogicParamManager.setDefaultCheckInstance(checkInstance);
			
			boolean flag=false;
			if(strCheckClass != null){
				for(String str : strCheckClass){
					ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
					MessageResult currentResult = check.Check();
					if(!currentResult.isSuccess()) {
						flag=true;
					}
				}
			}
			
			if(flag){
				ReflectOperation.setFieldValue(obj, "RPTCheckType", "3");
			}else{
				ReflectOperation.setFieldValue(obj, "RPTCheckType", "2");
			}
			RequestManager.setTOject(currentTObject);
			RequestManager.setTName(currentTName);
			LogicParamManager.setDefaultCheckInstance(defaultCheckInstance);
		}
		
		for (Object obj : insertList) {
			String[] strCheckClass=new String[]{};
			String checkInstance="";
			if(imprtCheckMap!=null){
				for (Map.Entry<String, String> entry: imprtCheckMap.entrySet()) {
					if(entry.getKey().equals(obj.getClass().getName())){
						strCheckClass=entry.getValue().split(",");
					}
				}
			}
			
			for(int k=0;k<strCheckClass.length;k++){
				if(strCheckClass[k].indexOf("-")>0){
					checkInstance=strCheckClass[k].split("-")[1];
					strCheckClass[k]=strCheckClass[k].split("-")[0];
				}
			}
			
			Object currentTObject = RequestManager.getTOject();
			String currentTName = RequestManager.getTName();
			String defaultCheckInstance = LogicParamManager.getDefaultCheckInstance();
			RequestManager.setTOject(obj);
			RequestManager.setTName(obj.getClass().getName());
			
			LogicParamManager.setDefaultCheckInstance(checkInstance);
			
			boolean flag=false;
			if(strCheckClass != null){
				for(String str : strCheckClass){
					ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
					MessageResult currentResult = check.Check();
					if(!currentResult.isSuccess()) {
						flag=true;
					}
				}
			}
			
			if(flag){
				ReflectOperation.setFieldValue(obj, "RPTCheckType", "3");
			}else{
				ReflectOperation.setFieldValue(obj, "RPTCheckType", "2");
			}
			RequestManager.setTOject(currentTObject);
			RequestManager.setTName(currentTName);
			LogicParamManager.setDefaultCheckInstance(defaultCheckInstance);
		}
		
		return new MessageResult(true, "导入成功!");
		
	}
	
	private void addExtendFieldValue(Map<String, String> defaultValueMap, Object object, ArrayList<Object> dataObjectList) throws Exception{
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),id,null});
		
		for(Map.Entry<String,String> defaultValue : defaultValueMap.entrySet()){
			if(defaultValue.getValue().endsWith("SystemDate")){
				Field field = ReflectOperation.getReflectField(object.getClass(), defaultValue.getKey());
				if(field.getType().equals(java.util.Date.class) || field.getType().equals(java.sql.Date.class)){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					ReflectOperation.setFieldValue(object,defaultValue.getKey(),simpleDateFormat.format(new Date()));
				}
				else if(field.getType().equals(Timestamp.class)){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ReflectOperation.setFieldValue(object,defaultValue.getKey(),simpleDateFormat.format(new Date()));
				}
			}
			else if(defaultValue.getValue().endsWith("LoginUserInfo")){
				if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
					ReflectOperation.setFieldValue(object,defaultValue.getKey(),SecurityContext.getInstance().getLoginInfo().getTag());
				}
			}
			else if(defaultValue.getValue().endsWith("ObjectKeyEmptyToNull")){
				Object objectForNull = ReflectOperation.getFieldValue(object,defaultValue.getKey());
				if(objectForNull != null){
					Object objectPrimaryKey = ReflectOperation.getPrimaryKeyValue(objectForNull);
					if(objectPrimaryKey == null || objectPrimaryKey.toString().equals("")){
						ReflectOperation.setFieldNullValue(objectForNull, defaultValue.getKey(), objectForNull.getClass());
					}
				}
			}else if(defaultValue.getValue().endsWith("instInfo")){
				ReflectOperation.setFieldValue(object,defaultValue.getKey(),taskModelInst.getInstInfo());
			}
			else if(defaultValue.getValue().endsWith("dtDate")){
				ReflectOperation.setFieldValue(object,defaultValue.getKey(),TypeParse.parseString(taskModelInst.getTaskFlow().getDtTaskDate(),"yyyy-MM-dd"));
			}
			else if(defaultValue.getValue().endsWith("FOREIGNID")){
				String foreignName = (String) (importRelaMap_jc.keySet().toArray())[0];
				for (Object jc_Object : dataObjectList) {
					if(jc_Object.getClass().getName().equals(foreignName)){
						ReflectOperation.setFieldValue(object, defaultValue.getKey(), jc_Object);
						break;
					}
				}
			}
			else{
				ReflectOperation.setFieldValue(object,defaultValue.getKey(),defaultValue.getValue());
			}
		}
	}
	public String getCellStr(Cell cell, String type){
		String cellValue = "";
		try{
			if(type.equals("MONEY")){
			    DecimalFormat format = new DecimalFormat("#0.00");
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
				    cellValue =  format.format(cell.getNumericCellValue());
				}else{
			    	cellValue = format.parseObject(cell.getStringCellValue()).toString();
			    }
			}
			else if(type.equals("DATE")){
				SimpleDateFormat format =  new SimpleDateFormat("yyyyMMdd");
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
					if(DateUtil.isCellDateFormatted(cell)){
						cellValue = format.format(cell.getDateCellValue());
					}
				}else{
					//cellValue = format.parse(cell.getStringCellValue()).toString();
					cellValue = cell.getStringCellValue(); //20151102 update
				}
			}
			else if(type.equals("DOWNLIST")){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				if(cellValue.indexOf("-")>0){
					cellValue = cellValue.split("-")[0];
				}
			}else{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
			}
		}catch (Exception e) {
			return cellValue;
		}
		return cellValue;
	}

	
	public void totalTaskModelInstCheckStatus(List<?> Object_JCListTemp) throws Exception{

		if(Object_JCListTemp.size() > 0){
			String strDtoName= Object_JCListTemp.get(0).getClass().getName();
			String strTableName=strDtoName.substring(strDtoName.indexOf("AutoDTO_")+8);
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strDtoName));
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
			List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			detachedCriteria.add(Restrictions.eq("dtTaskDate", ReflectOperation.getFieldValue(Object_JCListTemp.get(0), "dtDate")));	
			
			List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			List<TaskModelInst>  taskModelInstList=new ArrayList<TaskModelInst>();
			
			detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			detachedCriteria.add(Restrictions.eq("instInfo", ReflectOperation.getFieldValue(Object_JCListTemp.get(0), "instInfo")));
			
			detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
			detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
		    taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		    
		    if(taskModelInstList.size()>0){
		    	TaskModelInst taskModelInst=taskModelInstList.get(0);
		    	
		    	int checkSuccess=0;
		    	int checkfalse=0;
		    	int unCheck=0;
				int unAllCheckSuccess=0;
		
				unCheck = findByDetachedCriteria("RPTCheckType", "1", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				checkSuccess = findByDetachedCriteria("RPTCheckType", "2", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				checkfalse = findByDetachedCriteria("RPTCheckType", "3", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unAllCheckSuccess = findByDetachedCriteria("RPTCheckType", "4", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				if(checkSuccess==0 && checkfalse==0 && unCheck>=0 && unAllCheckSuccess==0){
					taskModelInst.setStrCheckState("1");
				}else if(checkSuccess>0 && checkfalse==0 && unCheck==0 && unAllCheckSuccess==0){
					taskModelInst.setStrCheckState("2");
				}
				else if(checkSuccess==0 && checkfalse >0 && unCheck==0 && unAllCheckSuccess==0){
					taskModelInst.setStrCheckState("3");
				}else{
					taskModelInst.setStrCheckState("4");
				}
			
				IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
				singleObjectUpdateDao.paramVoidResultExecute(new Object[]{taskModelInst,null});
		    }
		}
	}
	
	@SuppressWarnings("unused")
	public Integer findByDetachedCriteria(String status, String statusValue, DetachedCriteria detachedCriteria, String strTableName, 
			TaskModelInst taskModelInst, IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
		detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
		return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	}
	
	public void totalJCCheckStatus(List<?> Object_JCListTemp) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		for (Object object_Jc : Object_JCListTemp) {

			int checkSuccess=0;
			int checkfalse=0;
			int unCheck=0;
			List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_Jc.getClass());
			
			for (Field field : listObjectMx) {
				DetachedCriteria detachedCriteria = null;
				unCheck += findByDetachedCriteriaForJC("RPTCheckType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				checkSuccess += findByDetachedCriteriaForJC("RPTCheckType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				checkfalse += findByDetachedCriteriaForJC("RPTCheckType","3", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			}
			
			if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").toString().equals("2")){
				checkSuccess+=1;
			}
			else{
				checkfalse+=1;
			}

			String SubRPTCheckType="";
			if(checkSuccess==0 && checkfalse==0 && unCheck>0){
				SubRPTCheckType = "1";
			}else if(checkSuccess>0 && checkfalse==0 && unCheck==0){
				SubRPTCheckType = "2";
			}
			else if(checkSuccess==0 && checkfalse>0 && unCheck==0 ){
				SubRPTCheckType = "3";
			}
			else{
				SubRPTCheckType = "4";
			}
			
			ReflectOperation.setFieldValue(object_Jc, "RPTCheckType", SubRPTCheckType);

			if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("1")){
				unCheck+=1;
			}else if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("2")){
				checkSuccess+=1;
			}
			else if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("3")){
				checkfalse+=1;
			}
		
			IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
			singleObjectUpdateDao.paramVoidResultExecute(new Object[]{object_Jc,null});
		}
	}
	
	  public Integer findByDetachedCriteriaForJC(String status, String statusValue, DetachedCriteria detachedCriteria, Object object_Jc,Type type,IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
		    detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(type));
			detachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
			detachedCriteria.add(Restrictions.eq(status, statusValue));
			return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	   }
	
	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getImportRelaMap() {
		return importRelaMap;
	}

	public void setImportRelaMap(Map<String, String> importRelaMap) {
		this.importRelaMap = importRelaMap;
	}

	public Map<String, String> getJcDefaultValueMap() {
		return jcDefaultValueMap;
	}

	public void setJcDefaultValueMap(Map<String, String> jcDefaultValueMap) {
		this.jcDefaultValueMap = jcDefaultValueMap;
	}

	public Map<String, String> getMxDefaultValueMap() {
		return mxDefaultValueMap;
	}

	public void setMxDefaultValueMap(Map<String, String> mxDefaultValueMap) {
		this.mxDefaultValueMap = mxDefaultValueMap;
	}

	public Map<String, String> getJcExtendValueMap() {
		return jcExtendValueMap;
	}

	public void setJcExtendValueMap(Map<String, String> jcExtendValueMap) {
		this.jcExtendValueMap = jcExtendValueMap;
	}

	public Map<String, String> getMxExtendValueMap() {
		return mxExtendValueMap;
	}

	public void setMxExtendValueMap(Map<String, String> mxExtendValueMap) {
		this.mxExtendValueMap = mxExtendValueMap;
	}

	public Map<String, String> getImprtCheckMap() {
		return imprtCheckMap;
	}

	public void setImprtCheckMap(Map<String, String> imprtCheckMap) {
		this.imprtCheckMap = imprtCheckMap;
	}

	public boolean isJxOverWrite() {
		return jxOverWrite;
	}

	public void setJxOverWrite(boolean jxOverWrite) {
		this.jxOverWrite = jxOverWrite;
	}

	public boolean isMxOverWrite() {
		return mxOverWrite;
	}

	public void setMxOverWrite(boolean mxOverWrite) {
		this.mxOverWrite = mxOverWrite;
	}

	public String getMxTypeCell() {
		return mxTypeCell;
	}

	public void setMxTypeCell(String mxTypeCell) {
		this.mxTypeCell = mxTypeCell;
	}

	public Map<String, String> getMxTypeCellRela() {
		return mxTypeCellRela;
	}

	public void setMxTypeCellRela(Map<String, String> mxTypeCellRela) {
		this.mxTypeCellRela = mxTypeCellRela;
	}

	public void setMxLogicPrimaryKey(Map<String,String> mxLogicPrimaryKey) {
		this.mxLogicPrimaryKey = mxLogicPrimaryKey;
	}

	public Map<String,String> getMxLogicPrimaryKey() {
		return mxLogicPrimaryKey;
	}

}
