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
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;

public class ShowProcedureStatisticalService extends SingleObjectShowListService {

    private String sessionFactory;
	
	private String strProcedureName;

	private String[] strProcedureParamNames;
	
	private String defaultDaoBeanId; 
	
	private String strDatabaseType; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		if(RequestManager.isFirstShowData() != null && RequestManager.isFirstShowData()){
			this.setServiceResult(new ArrayList<Object>());
			return;
		}
		
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
			if(strDatabaseType!=null && strDatabaseType.equals("oracle")){
				list.add(2, "rt");
				strProcedureParamNames =  list.toArray(new String[1]);
			}
			
			Object tObject=RequestManager.getTOject();
			
			for(int i =0;i<strProcedureParamNames.length;i++){
				Field field = ReflectOperation.getReflectFieldIgnorCase(tObject.getClass(), strProcedureParamNames[i]);
				if(field == null){
					procedureParam.put(strProcedureParamNames[i], null);
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
		
		procedureParam.put("FIRSTRESULT", ShowParamManager.getFirstResult());
		procedureParam.put("PAGECOUNT", ShowParamManager.getPageSize());
		
		String prcedureString = "{call " + strProcedureName +"(" + callprcedure + ")}";

		List<Map<String,Object>> objectSList= (List<Map<String,Object>>)this.getBaseObjectDao().paramObjectResultExecute(new Object[]{prcedureString,procedureParam,sessionFactory});
		
		int totalCount = 0;
		List<Object> objectDList=new ArrayList<Object>();
		int i=0;
		for (Map<String,Object> objectMap : objectSList) {
			Object objectD=Class.forName(RequestManager.getTName()).newInstance();
			for (Map.Entry<String,Object> entry : objectMap.entrySet()) {
				if(entry.getKey().equals("TOTALCOUNT")){
					if(i == 0){
						Object objTotalCount = entry.getValue();
						String strTotalCount = objTotalCount.toString();
						totalCount = Integer.parseInt(strTotalCount);
					}
				}
				else{
					if(entry.getValue() != null){
						Field field=null;
						for (Field fieldTemp : ReflectOperation.getReflectFields(objectD.getClass())) {
							if(fieldTemp.getName().toUpperCase().equals(entry.getKey().toUpperCase())){
								field=fieldTemp;
								break;
							}
						}

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
		this.setServiceResult(objectDList);

		LogicParamManager.setTotalCount(totalCount);
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
			strDatabaseType="oracle";
		}
		else if(dataBaseType!=null && dataBaseType.equals("db2")){
			this.defaultDaoBeanId = "db2ProcedureListMapDao";
		}
		
		if(super.getBaseDaoObject() == null){
			if(super.getBaseDaoObject() == null)
				super.setBaseObjectDao((IParamObjectResultExecute) FrameworkFactory.CreateBean(this.defaultDaoBeanId)) ;
		}
	}

	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
	}

	public String StrProcedureName() {
		return strProcedureName;
	}



	public void setSessionFactory(String sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	public String[] getStrProcedureParamNames() {
		return strProcedureParamNames;
	}



	public void setStrProcedureParamNames(String[] strProcedureParamNames) {
		this.strProcedureParamNames = strProcedureParamNames;
	}



	public String getStrProcedureName() {
		return strProcedureName;
	}



	public String getSessionFactory() {
		return sessionFactory;
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
