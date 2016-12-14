package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zxptsystem.dto.condition.PROC_EndsData_Check_Condition;

import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;

/**
 * 通过存储过程显示企业征信业务统计
 * @author xiajieli
 *
 */
public class ShowProcedureForQYZXEndsData_CheckService extends SingleObjectShowListService {

    private String sessionFactory;
	
	private String strProcedureName;

	private String[] strProcedureParamNames;
	
	private String defaultDaoBeanId; 
	
	private String strDatabaseType; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		//先校验是否选择条件参数
		if(HelpTool.getSystemParam("qyzx_strVersion")==null || HelpTool.getSystemParam("qyzx_strVersion").equals("")){
			messageResult.getMessageSet().add("请在系统参数管理下配置企业征信版本号");
		}
		if (messageResult.getMessageSet().size() > 0) {
			messageResult.setSuccess(false);
			messageResult.AlertTranslate();
			this.setServiceResult(messageResult);
		}else{
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
				
				List<String> list = new ArrayList<String>();  
		        for (int i=0; i<strProcedureParamNames.length; i++) {  
		            list.add(strProcedureParamNames[i]);  
		        }  
				if(strDatabaseType!=null && strDatabaseType.equals("oracle")){
					list.add(strProcedureParamNames.length, "rt");
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

			PROC_EndsData_Check_Condition pROC_EndsData_Check_Condition=(PROC_EndsData_Check_Condition)RequestManager.getTOject();
			String p_VERSION="";//企业征信版本号
			if(HelpTool.getSystemParam("qyzx_strVersion")!=null && !HelpTool.getSystemParam("qyzx_strVersion").equals("")){
				p_VERSION=HelpTool.getSystemParam("qyzx_strVersion").getStrParamValue();
			}
			String strSJJZRQ="";
			if(pROC_EndsData_Check_Condition.getSJJZRQ()!=null && !pROC_EndsData_Check_Condition.getSJJZRQ().equals("")){
				strSJJZRQ=TypeParse.parseString(TypeParse.parseDate(pROC_EndsData_Check_Condition.getSJJZRQ()), "yyyyMMdd");
			}
			
			procedureParam.put(strProcedureParamNames[0],p_VERSION);
			procedureParam.put(strProcedureParamNames[1],pROC_EndsData_Check_Condition.getDJJGDM().getStrReportInstCode());
			procedureParam.put(strProcedureParamNames[2],strSJJZRQ);
			if(strDatabaseType!=null && strDatabaseType.equals("oracle")){
				procedureParam.put(strProcedureParamNames[3],"rt");
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
							
								if(!entry.getValue().getClass().equals(String.class)){
									String v = String.valueOf(entry.getValue());
									ReflectOperation.setFieldValue(objectD, field.getName(), v);
								}
								else{
									ReflectOperation.setFieldValue(objectD, field.getName(), entry.getValue());
								}						
								
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

	public void setDefaultDaoBeanId(String defaultDaoBeanId) {
		this.defaultDaoBeanId = defaultDaoBeanId;
	}

}
