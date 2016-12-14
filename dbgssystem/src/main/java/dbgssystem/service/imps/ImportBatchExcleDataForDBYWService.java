package dbgssystem.service.imps;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.helper.HelpTool;

import dbgssystem.dto.AutoDTO_DB_DBXX_JC;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import framework.helper.SmallTools;
import report.service.interfaces.imp.ForReportParserExcelPoi;
import report.service.interfaces.imp.ForReportParserExcelPoi.Excel2003Reader;
import report.service.interfaces.imp.ForReportParserExcelPoi.Excel2007Reader;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
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
import framework.show.ShowField;
import framework.show.ShowInstance;

/**
 * 担保业务批量导入excle数据
 * @author xiajieli
 *
 */
public class ImportBatchExcleDataForDBYWService implements IObjectResultExecute  {

	private File dataFile;
	
	private String fileName;

	private Map<String,String> sheetNameMapFor07;
	
	private Map<String,String> imprtCheckMap;
	
	private Map<String,String> jcDefaultValueMap;
	
	private Map<String,String> mxDefaultValueMap;
	
	private Map<String,String> jcExtendValueMap;
	
	private Map<String,String> mxExtendValueMap;
	
	private Map<String,String> mxLogicPrimaryKey;
	
	private boolean jxOverWrite;
	
	private boolean mxOverWrite;
	
	private String afterIgnorSeg; // 外键与显示名分隔符
	
	private Integer startLine; 
	
	private Integer startCol; 
	
	private String showInstanceName;

	private ArrayList<Object> insertList=new ArrayList<Object>();
	
	private ArrayList<Object> updateList=new ArrayList<Object>();
	
	private Map<String,String> importDtoName_jc = new HashMap<String,String>();
	
	private Map<String,String> importDtoName_mx = new HashMap<String,String>();
	
	private String FileTempDirectory; //文件临时存放目录
	
	public Object objectResultExecute() throws Exception {
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
			
			if(!fileName.endsWith(".zip")){
				return new MessageResult("导入失败，只能导入zip格式的文件");
			}
			
			importDtoName_mx.putAll(sheetNameMapFor07);
			for(Map.Entry<String, String> entry : sheetNameMapFor07.entrySet()){
				if(ReflectOperation.getOneToManyField(entry.getKey().toString()).size()>0){
					importDtoName_jc.put(entry.getKey(), entry.getValue());
					importDtoName_mx.remove(entry.getKey());
					break;
				}
			}
			
			ArrayList<List<Object>> objectAllList = new ArrayList<List<Object>>();
			try{
				objectAllList = getDataFromExcel(); // 获取数据
			}catch (Exception e) {
				if(e.getMessage()!=null){
					if(e.getMessage().contains("not-null property references a null or transient value")){
						return new MessageResult("导入失败,请先导入基础段");
					}else if(e.getMessage().contains("系统找不到指定的路径")){
						return new MessageResult("系统找不到指定的路径");
					}
				}
				return new MessageResult("导入失败"+e.getMessage());
			}
			finally{
				File deleteFile=new File(FileTempDirectory);
				if(deleteFile.exists()){
					HelpTool.deleteFile(deleteFile); //删除目录
				}
			}
			if(objectAllList.equals(null)){
				return new MessageResult("导入失败");
			}
			
			if(objectAllList.size()>0){
				for (List<Object> objectList : objectAllList) {
					
				    List<AutoDTO_DB_DBXX_JC> autoDTO_DB_DBXX_JCList=totalJCObjectList(objectList); //统计基础段list
					
					totalJCCheckStatus(autoDTO_DB_DBXX_JCList);//统计基础段的状态
					
					totalTaskModelInstCheckStatus(autoDTO_DB_DBXX_JCList);//统计任务层状态
				}
			}
			
			return new MessageResult("导入成功");
			
		}catch(Exception ex){
			return new MessageResult("导入失败");
		}
	}

	/*
	 * 从excle获取数据
	 */
	private ArrayList<List<Object>> getDataFromExcel() throws Exception {
		
		ArrayList<List<Object>> insertDataList=new ArrayList<List<Object>>(); //数据对象列表
		
		if(fileName.endsWith(".zip")){
			SmallTools.unZipFiles(dataFile, FileTempDirectory,"GBK");
			Iterator<String> iterator=null;
			
			iterator = sheetNameMapFor07.keySet().iterator();
			String baseDtoName="";
			
			while(iterator.hasNext()){
				ArrayList<Object> currentObjectList = null;
				String dtoName = iterator.next();
				if(baseDtoName.equals("")){
					baseDtoName=RequestManager.getTName();
				}
				RequestManager.setTName(dtoName);
				String fileNameTemp=null;
				fileNameTemp = sheetNameMapFor07.get(dtoName);
				String sheetName = fileNameTemp.split("-")[1].split("\\.")[0];
				//open file
				ShowInstance showInstance = ReflectOperation.getShowInstance(dtoName, showInstanceName);
				ArrayList<ShowField> showFields = new ArrayList<ShowField>();
				for(ShowField showField : showInstance.getShowFieldList()){
					if(showField.isListVisible()) { 
						showFields.add(showField); //添加显示字段
					}
				}
				
				File dateFile = new File(FileTempDirectory);
				if (!dateFile.exists() && !dateFile.isDirectory()) {
					dateFile.mkdirs();
				}
				
				String filePath=FileTempDirectory+File.separator+fileName.split("\\.")[0]+File.separator+fileNameTemp;
				File pathFile = new File(filePath);
				if(!pathFile.exists()){
					filePath=FileTempDirectory+File.separator+fileNameTemp;
					pathFile = new File(filePath);
					if(!pathFile.exists()){
						continue;
					}
				}
				String message="";
				ForReportParserExcelPoi forReportParserExcelPoi = new ForReportParserExcelPoi();
				if(fileNameTemp.toLowerCase().endsWith(".xlsx")){
					Excel2007Reader excel07 = forReportParserExcelPoi.new Excel2007Reader();
					excel07.process(showFields, "sessionFactory", dtoName, filePath, sheetName, false, true, afterIgnorSeg, startLine, startCol);
					currentObjectList = excel07.insertDataList;
			        message = forReportParserExcelPoi.error.toString();    
				}
				else if(fileNameTemp.toLowerCase().endsWith(".xls")){
					Excel2003Reader excel03 = forReportParserExcelPoi.new Excel2003Reader();
			        excel03.process(showFields, "sessionFactory", dtoName, filePath, sheetName, false, true, afterIgnorSeg, startLine, startCol);
			        currentObjectList = excel03.insertDataList;
			        message = forReportParserExcelPoi.error.toString();
				}else{
					message = "文件类型不正确";
				}
			       
		        MessageResult messageResult =new MessageResult();
		        if((message==null || message.equals("")) && currentObjectList.size()>0){
		        	//添加默认值
		        	for (Object object : currentObjectList) {
		        		if(importDtoName_jc.containsKey(dtoName)){
		        			if(jcDefaultValueMap!=null){
			        			for (Map.Entry<String,String> entry: jcDefaultValueMap.entrySet()) {
			        				ReflectOperation.setFieldValue(object, entry.getKey(), entry.getValue());
			        			}
			        		}
		        			
		        			//添加扩展字段
							if(jcExtendValueMap!=null){
								addExtendFieldValue(jcExtendValueMap, object);
							}
		        		}else{
		        			if(mxDefaultValueMap!=null){
								for (Map.Entry<String,String> entry: mxDefaultValueMap.entrySet()) {
									ReflectOperation.setFieldValue(object, entry.getKey(), entry.getValue());
								}
							}
							if(mxExtendValueMap!=null){
								addExtendFieldValue(mxExtendValueMap, object);
							}
		        		}
					}
		        	
		        	messageResult = (MessageResult)checkData(currentObjectList); // 数据校验		        
		        	
		        	//保存数据
		        	if(messageResult.isSuccess()){
		        		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{updateList,null});
						
						IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
						singleObjectSaveAllDao.paramVoidResultExecute(new Object[]{insertList,null});
						
		        	}
		        	
		        }else{		        	
		        	if(message==null || message.equals("")){
		        		messageResult.setSuccess(true);
		        	}else{
		        		messageResult.setSuccess(false);
		        		messageResult.setMessage(message);
		        	}
		        }
		        if(!messageResult.isSuccess()){
		        	break;
		        }
		        insertDataList.add(currentObjectList);
			}
			RequestManager.setTName(baseDtoName);
		}
		 
		return insertDataList;
	}
	
	
	
	
	
	
	
	
	/*
	 * 校验数据
	 */
	private MessageResult checkData(ArrayList<Object> objectList) throws Exception{
		
		//判读逻辑主键是否重复,重复及覆盖，不重复及新增
		for (Object obj : objectList) {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(obj.getClass());
			if(importDtoName_jc.containsKey(obj.getClass().getName())){
				String primaryKey = ShowContext.getInstance().getShowEntityMap().get("DBGSSystemLogicPrimaryKey").get(obj.getClass().getName());
				String[] primaryKeys = primaryKey.split(",");
				for (String strFieldName : primaryKeys) {
					detachedCriteria.add(Restrictions.eq(strFieldName, ReflectOperation.getFieldValue(obj, strFieldName)));
				}
				
				List<Object> objList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(objList.size()>0){
					if(jxOverWrite){
						if(importDtoName_jc.containsKey(objList.get(0).getClass().getName())){
							String RPTSubmitStatus=ReflectOperation.getFieldValue(objList.get(0), "RPTSubmitStatus").toString();
							
							if(RPTSubmitStatus.equals("1")){
								ReflectOperation.CopyColumnFieldValue(obj, objList.get(0));
								updateList.add(objList.get(0));
							}
						}
					}
				}else{
					String fieldName = ReflectOperation.getPrimaryKeyField(obj.getClass().getName()).getName();
					ReflectOperation.setFieldValue(obj, fieldName, UUID.randomUUID().toString().replaceAll("-", ""));
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
		
		return new MessageResult(true);
	}
	
	/*
	 * 添加扩展字段值
	 */
	private void addExtendFieldValue(Map<String, String> defaultValueMap, Object object) throws Exception{
		
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
				String DBYWBH=null;
				String DBJGDM=null;
				if(ReflectOperation.getFieldValue(object, "extend3")!=null){
					DBJGDM=ReflectOperation.getFieldValue(object, "extend3").toString();
				}
				if(ReflectOperation.getFieldValue(object, "extend4")!=null){
					DBYWBH=ReflectOperation.getFieldValue(object, "extend4").toString();
				}
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				
				String baseDtoName="";
				for(Map.Entry<String, String> entry:importDtoName_jc.entrySet()){
					if(ReflectOperation.getOneToManyField(entry.getKey().toString()).size()>0){
						baseDtoName=entry.getKey();
					}
				}
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDtoName));
				detachedCriteria.add(Restrictions.eq("DBJGDM", DBJGDM));
				detachedCriteria.add(Restrictions.eq("DBYWBH", DBYWBH));
				List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(objectList.size()==1){
					ReflectOperation.setFieldValue(object, "FOREIGNID", objectList.get(0));
				}
			}
			else{
				ReflectOperation.setFieldValue(object,defaultValue.getKey(),defaultValue.getValue());
			}
		}
	}
	
	/*
	 * 统计基础段list
	 */
	private List<AutoDTO_DB_DBXX_JC> totalJCObjectList(List<Object> objectList) throws Exception {
		String DBJGDM="";
		String DBYWBH="";
		List<String> DBJGDMList=new ArrayList<String>();
		List<String> DBYWBHList=new ArrayList<String>();
		for (Object object : objectList) {
			List<Field> listObjectMx=ReflectOperation.getOneToManyField(object.getClass());
			if(listObjectMx.size()>0){//基础段
				DBJGDM=ReflectOperation.getFieldValue(object, "DBJGDM").toString();
				DBJGDMList.add(DBJGDM);
				DBYWBH=ReflectOperation.getFieldValue(object, "DBYWBH").toString();
				DBYWBHList.add(DBYWBH);
			}
			else{//明细段
				DBJGDM=ReflectOperation.getFieldValue(object, "extend3").toString();
				DBJGDMList.add(DBJGDM);
				DBYWBH=ReflectOperation.getFieldValue(object, "extend4").toString();
				DBYWBHList.add(DBYWBH);
			}
		}
		
		DBJGDMList=HelpTool.removeDuplicate(DBJGDMList);
		DBYWBHList=HelpTool.removeDuplicate(DBYWBHList);
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteriaTemp = DetachedCriteria.forClass(AutoDTO_DB_DBXX_JC.class);
		detachedCriteriaTemp.add(Restrictions.in("DBJGDM", DBJGDMList));
		detachedCriteriaTemp.add(Restrictions.in("DBYWBH", DBYWBHList));
		List<AutoDTO_DB_DBXX_JC> autoDTO_DB_DBXX_JCList = (List<AutoDTO_DB_DBXX_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteriaTemp,null});
		return autoDTO_DB_DBXX_JCList;
	}

	/*
	 * 统计基础段校验状态
	 */
	public void totalJCCheckStatus(List<?> objectList) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		for (Object object_Jc : objectList) {

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
	/*
	 * 统计任务层校验状态
	 */
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
	
	public Integer findByDetachedCriteria(String status, String statusValue, DetachedCriteria detachedCriteria, String strTableName, 
			TaskModelInst taskModelInst, IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
		detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
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
	
	public Map<String, String> getSheetNameMapFor07() {
		return sheetNameMapFor07;
	}

	public void setSheetNameMapFor07(Map<String, String> sheetNameMapFor07) {
		this.sheetNameMapFor07 = sheetNameMapFor07;
	}

	public Map<String, String> getImprtCheckMap() {
		return imprtCheckMap;
	}


	public void setImprtCheckMap(Map<String, String> imprtCheckMap) {
		this.imprtCheckMap = imprtCheckMap;
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


	public Map<String, String> getMxLogicPrimaryKey() {
		return mxLogicPrimaryKey;
	}


	public void setMxLogicPrimaryKey(Map<String, String> mxLogicPrimaryKey) {
		this.mxLogicPrimaryKey = mxLogicPrimaryKey;
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


	public String getAfterIgnorSeg() {
		return afterIgnorSeg;
	}


	public void setAfterIgnorSeg(String afterIgnorSeg) {
		this.afterIgnorSeg = afterIgnorSeg;
	}


	public Integer getStartLine() {
		return startLine;
	}


	public void setStartLine(Integer startLine) {
		this.startLine = startLine;
	}


	public Integer getStartCol() {
		return startCol;
	}


	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}


	public String getShowInstanceName() {
		return showInstanceName;
	}


	public void setShowInstanceName(String showInstanceName) {
		this.showInstanceName = showInstanceName;
	}


	public ArrayList<Object> getInsertList() {
		return insertList;
	}


	public void setInsertList(ArrayList<Object> insertList) {
		this.insertList = insertList;
	}


	public ArrayList<Object> getUpdateList() {
		return updateList;
	}


	public void setUpdateList(ArrayList<Object> updateList) {
		this.updateList = updateList;
	}

	
	public Map<String, String> getImportDtoName_jc() {
		return importDtoName_jc;
	}

	public void setImportDtoName_jc(Map<String, String> importDtoNameJc) {
		importDtoName_jc = importDtoNameJc;
	}

	public Map<String, String> getImportDtoName_mx() {
		return importDtoName_mx;
	}

	public void setImportDtoName_mx(Map<String, String> importDtoNameMx) {
		importDtoName_mx = importDtoNameMx;
	}

	public String getFileTempDirectory() {
		return FileTempDirectory;
	}


	public void setFileTempDirectory(String fileTempDirectory) {
		FileTempDirectory = fileTempDirectory;
	}


}
