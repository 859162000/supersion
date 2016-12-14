package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zxptsystem.dto.condition.PROC_GRZXZLKP_Condition;
import zxptsystem.dto.condition.PROC_QYZXZLKP_Condition;
import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.ExportForProcedureService;
import framework.services.interfaces.IFileHandlerForText;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.imps.FileHandlerForText;

/**
 * 征信质量考评（通过查询条件作为参数调用存储过程返回数据） 
 * @author xiajieli
 *
 */
public class ZXSJZLKPForProcedureService extends ExportForProcedureService{

	private String sessionFactory;
	
	private String strDatabaseType; 
	
	private String defaultDaoBeanId; 
	
	private String strProcedureName;
	
	private String strExportDataType;
	
	private String strDataSegType;
	
	private String[] strProcedureParamNames;
	
	private String strZXSJZLKType;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		if(strZXSJZLKType.equals("1")){//企业征信质量考评
			PROC_QYZXZLKP_Condition pROC_QYZXZLKP_Condition = (PROC_QYZXZLKP_Condition)RequestManager.getTOject();
			
			MessageResult messageResult = new MessageResult();
			if(StringUtils.isBlank(pROC_QYZXZLKP_Condition.getDJJGDM().getStrReportInstCode())){
				messageResult.getMessageSet().add("报送机构不能为空");
			}
			if(StringUtils.isBlank(pROC_QYZXZLKP_Condition.getSJJZRQ())){
				messageResult.getMessageSet().add("数据截止日期不能为空");
			}
			if(HelpTool.getSystemParam("qyzx_strVersion")==null || HelpTool.getSystemParam("qyzx_strVersion").equals("")){
				messageResult.getMessageSet().add("请在系统参数管理下配置企业征信版本号");
			}
			if (messageResult.getMessageSet().size() > 0) {
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				this.setServiceResult(messageResult);
			}
			else{//导出数据
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
					if(this.getStrDatabaseType()!=null && this.getStrDatabaseType().equals("oracle")){
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

				String p_VERSION="";//企业征信版本号
				if(HelpTool.getSystemParam("qyzx_strVersion")!=null && !HelpTool.getSystemParam("qyzx_strVersion").equals("")){
					p_VERSION=HelpTool.getSystemParam("qyzx_strVersion").getStrParamValue();
				}
				String strSJJZRQ=pROC_QYZXZLKP_Condition.getSJJZRQ().toString();
				
				procedureParam.put(strProcedureParamNames[0],p_VERSION);
				procedureParam.put(strProcedureParamNames[1],pROC_QYZXZLKP_Condition.getDJJGDM().getStrReportInstCode());
				procedureParam.put(strProcedureParamNames[2],strSJJZRQ);
				if(this.getStrDatabaseType()!=null && this.getStrDatabaseType().equals("oracle")){
					procedureParam.put(strProcedureParamNames[3],"rt");
				}
				procedureParam.put("FIRSTRESULT", ShowParamManager.getFirstResult());
				procedureParam.put("PAGECOUNT", ShowParamManager.getPageSize());
				
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
				
				if(pROC_QYZXZLKP_Condition.getSJJZRQ()!=null && !pROC_QYZXZLKP_Condition.getSJJZRQ().equals("")){
					strSJJZRQ=TypeParse.parseString(TypeParse.parseDate(pROC_QYZXZLKP_Condition.getSJJZRQ()), "yyyyMMdd");
				}
				
				if(StringUtils.isBlank(strExportDataType) || "1".equals(strExportDataType)){//excle
					this.setServiceResult(objectDList);
					ExportData();
				}
				else if("2".equals(strExportDataType)){//文本文件
					String strFileName = pROC_QYZXZLKP_Condition.getDJJGDM().getStrReportInstCode() + "B" + strSJJZRQ + ".txt";
					this.setFileName(strFileName);
					
					IFileHandlerForText fileHandlerForText = new FileHandlerForText();
					this.setServiceResult(fileHandlerForText.GetDownloadResultFromCacheTextFile(objectDList, fieldList, this.getFileName(), strDataSegType, null, this.getStrDateFormat()));
				}
			}
		}
		else if(strZXSJZLKType.equals("2")){//个人征信质量考评
			PROC_GRZXZLKP_Condition pROC_GRZXZLKP_Condition = (PROC_GRZXZLKP_Condition)RequestManager.getTOject();  
			
			MessageResult messageResult = new MessageResult();

			if(StringUtils.isBlank(pROC_GRZXZLKP_Condition.getP_REPORTINST().getStrReportInstCode())){
				messageResult.getMessageSet().add("报送机构不能为空");
			}
			if(StringUtils.isBlank(pROC_GRZXZLKP_Condition.getP_ENDMONTH())){
				messageResult.getMessageSet().add("数据截止日期不能为空");
			}
			if (messageResult.getMessageSet().size() > 0) {
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				this.setServiceResult(messageResult);
			}
			else{//导出数据到文本
				String strFileName = "JRJG" + pROC_GRZXZLKP_Condition.getP_REPORTINST().getStrReportInstCode() + pROC_GRZXZLKP_Condition.getP_ENDMONTH()+ ".txt";
				this.setFileName(strFileName);
				super.setStrProcedureName(this.strProcedureName);
				super.setStrProcedureParamNames(this.strProcedureParamNames);
				super.setStrExportDataType("2");
				super.setStrDataSegType(",");
				super.initSuccessResult();
			}
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

	public String getStrDatabaseType() {
		return strDatabaseType;
	}

	public void setStrDatabaseType(String strDatabaseType) {
		this.strDatabaseType = strDatabaseType;
	}

	public String getDefaultDaoBeanId() {
		return defaultDaoBeanId;
	}

	public void setDefaultDaoBeanId(String defaultDaoBeanId) {
		this.defaultDaoBeanId = defaultDaoBeanId;
	}

	public String getStrProcedureName() {
		return strProcedureName;
	}

	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
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

	public String[] getStrProcedureParamNames() {
		return strProcedureParamNames;
	}

	public void setStrProcedureParamNames(String[] strProcedureParamNames) {
		this.strProcedureParamNames = strProcedureParamNames;
	}

	public String getStrZXSJZLKType() {
		return strZXSJZLKType;
	}

	public void setStrZXSJZLKType(String strZXSJZLKType) {
		this.strZXSJZLKType = strZXSJZLKType;
	}

}
