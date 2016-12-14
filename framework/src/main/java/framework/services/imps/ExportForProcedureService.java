package framework.services.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IFileHandlerForText;
import framework.services.interfaces.imps.FileHandlerForText;

public class ExportForProcedureService extends SingleObjectExportDataService {
    
	private String sessionFactory;
	
	private String strProcedureName;

	private String[] strProcedureParamNames;
	
	private String strExportDataType;
	
	private String strDataSegType;
	
	private String defaultDaoBeanId; 
	
	private String strDatabaseType; 
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		if(strProcedureName==null){
			strProcedureName=RequestManager.getTName();
			strProcedureName=strProcedureName.substring(strProcedureName.lastIndexOf(".")+1);
		}
		
		this.initDao();

		String callprcedure = "";
		Map<String,Object> procedureParam=new LinkedHashMap<String,Object>();
		if(strProcedureParamNames!=null){
			//当类型为oracle时，添加rt游标参数
			List<String> list = new ArrayList<String>();  
	        for (int i=0; i<strProcedureParamNames.length; i++) {  
	            list.add(strProcedureParamNames[i]);  
	        }  
			if(this.getStrDatabaseType()!=null && this.getStrDatabaseType().equals("oracle")){
				list.add(2, "rt");
				strProcedureParamNames =  list.toArray(new String[1]);
			}
			
			Object tObject=RequestManager.getTOject();
			
			for(int i =0;i<strProcedureParamNames.length;i++){
				Field field = ReflectOperation.getReflectFieldIgnorCase(tObject.getClass(), strProcedureParamNames[i]);
				if(field == null){
					procedureParam.put(strProcedureParamNames[i], "");
				}
				else{
					if(ReflectOperation.isBaseType(field.getType())){
						procedureParam.put(strProcedureParamNames[i], ReflectOperation.getFieldValue(tObject, field.getName()));
					}
					else{
						Object foreignObject = ReflectOperation.getFieldValue(tObject, field.getName());
						procedureParam.put(strProcedureParamNames[i], ReflectOperation.getPrimaryKeyValue(foreignObject));
					}
				}
				if(!StringUtils.isBlank(callprcedure)){
					callprcedure += ",";
				}
				callprcedure += "?";
			}
		}
		if(callprcedure.equals("")){
			callprcedure += "?,?";
		}
		else{
			callprcedure += ",?,?";
		}
		
		procedureParam.put("FIRSTRESULT", 0);
		procedureParam.put("PAGECOUNT", 100000);
		
		String prcedureString = "{call " + strProcedureName +"(" + callprcedure + ")}";

		List<Map<String,Object>> objectSList= (List<Map<String,Object>>)this.getBaseObjectDao().paramObjectResultExecute(new Object[]{prcedureString,procedureParam,sessionFactory});

		List<Object> objectDList=new ArrayList<Object>();
		int i=0;
		List<Field> fieldList = new ArrayList<Field>();
		for (Map<String,Object> objectMap : objectSList) {
			Object objectD=Class.forName(RequestManager.getTName()).newInstance();
			for (Map.Entry<String,Object> entry : objectMap.entrySet()) {
				if(entry.getKey().equals("TOTALCOUNT")){
					if(i == 0){
					}
				}
				else{
					Field field=null;
					for (Field fieldTemp : ReflectOperation.getReflectFields(objectD.getClass())) {
						if(fieldTemp.getName().toUpperCase().equals(entry.getKey().toUpperCase())){
							field=fieldTemp;
							break;
						}
					}
					if(i == 0 && field != null){
						fieldList.add(field);
					}
					if(entry.getValue() != null){

						if(ReflectOperation.isBaseType(ReflectOperation.getFieldByName(objectD.getClass(), field.getName()).getType())){
							ReflectOperation.setFieldValue(objectD, field.getName(), entry.getValue());
						}else{
							Object foreignObject=ReflectOperation.getFieldByName(objectD.getClass(), field.getName()).getType().newInstance();
							ReflectOperation.setFieldValue(foreignObject, ReflectOperation.getPrimaryKeyField(foreignObject.getClass()).getName(), entry.getValue());
							ReflectOperation.setFieldValue(objectD,field.getName(),foreignObject);
						}
						
					}
				}
			}
			objectDList.add(objectD);
			i++;
		}
		
		
		if(StringUtils.isBlank(strExportDataType) || "1".equals(strExportDataType)){
			this.setServiceResult(objectDList);
			if(this.getFileName()!=null){
				ExportData(this.getFileName());
			}else{
				ExportData();
			}
			
		}
		else if("2".equals(strExportDataType)){
			IFileHandlerForText fileHandlerForText = new FileHandlerForText();
			this.setServiceResult(fileHandlerForText.GetDownloadResultFromCacheTextFile(objectDList, fieldList, this.getFileName(), strDataSegType, null, this.getStrDateFormat()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initDao() throws Exception{
		
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
		
		if(dataBaseType!=null && dataBaseType.equals("sqlserver") || dataBaseType.equals("mySql")){
			this.defaultDaoBeanId = "sqlserverProcedureListMapDao";
		}
		else if(dataBaseType!=null && dataBaseType.equals("oracle")){
			this.defaultDaoBeanId = "oracleProcedureListMapDao";
			this.setStrDatabaseType("oracle");
		}
		else if(dataBaseType!=null && dataBaseType.equals("db2")){
			this.defaultDaoBeanId = "db2ProcedureListMapDao";
		}
		
		if(super.getBaseDaoObject() == null){
			if(super.getBaseDaoObject() == null)
				super.setBaseObjectDao((IParamObjectResultExecute) FrameworkFactory.CreateBean(this.defaultDaoBeanId)) ;
		}
	}

	public String getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(String sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getStrProcedureName() {
		return strProcedureName;
	}

	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
	}

	public String[] getStrProcedureParamNames() {
		return strProcedureParamNames;
	}

	public void setStrProcedureParamNames(String[] strProcedureParamNames) {
		this.strProcedureParamNames = strProcedureParamNames;
	}

	public String getStrExportDataType() {
		return strExportDataType;
	}

	public void setStrExportDataType(String strExportDataType) {
		this.strExportDataType = strExportDataType;
	}

	public String getStrDataSegType() {
		return strDataSegType;
	}

	public void setStrDataSegType(String strDataSegType) {
		this.strDataSegType = strDataSegType;
	}

	public String getDefaultDaoBeanId() {
		return defaultDaoBeanId;
	}

	public void setDefaultDaoBeanId(String defaultDaoBeanId) {
		this.defaultDaoBeanId = defaultDaoBeanId;
	}

	public String getStrDatabaseType() {
		return strDatabaseType;
	}

	public void setStrDatabaseType(String strDatabaseType) {
		this.strDatabaseType = strDatabaseType;
	}

	
}

