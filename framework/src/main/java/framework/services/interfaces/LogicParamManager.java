package framework.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

public class LogicParamManager {
	
	@SuppressWarnings("unchecked")
	private static Map<String,Object> getRequest(){
		return (Map<String,Object>)ServletActionContext.getContext().get("request");
	}
	
	private static final String LOGIC_DEFAULTLOGICDAOBEANID = "logic_defaultLogicDaoBeanId";
	
	private static final String LOGIC_SERVICERESULT = "logic_serviceResult";

	private static final String LOGIC_DETACHEDCRITERIA = "logic_detachedCriteria";
	
	private static final String LOGIC_SAVEOBJECTLIST = "logic_saveObjectList";
	
	private static final String LOGIC_TOTALCOUNT= "logic_totalCount";
	
	private static final String LOGIC_SQLQUERY = "logic_sqlQuery";
	
	private static final String LOGIC_PROCEDUREPARAM = "logic_procedureParam";

	private static final String LOGIC_DEPENDTTABLELIST = "logic_dependTableList";
	
	private static final String LOGIC_DEFAULTVALUEMAP = "logic_defaultValueMap";
	
	private static final String LOGIC_FIELDVALUEMAP = "logic_fieldValueMap";
	
	private static final String LOGIC_DEFAULTCHECKINSTANCE = "logic_defaultCheckInstance";
	
	private static final String LOGIC_TREEDATASECURITY = "logic_treeDataSecurity";
	
	private static final String LOGIC_PRIMARYKAY = "logic_primaryKey";
	
	private static final String LOGIC_STRPROCEDURENAME = "logic_strProcedureName";
	
	private static final String LOGIC_STRPROCEDUREPARAMNAMES = "logic_strProcedureParamNames";
	
	private static final String LOGIC_DEFAULTCOMPONENTADDCLASSLIST = "logic_defaultComponentAddClassList";
	
	private static final String LOGIC_DEFAULTCOMPONENTCHECKCLASSLIST = "logic_defaultComponentCheckClassList";
	
	private static final String LOGIC_DEFAULTCOMPONENTTRANSLATECLASSLIST = "logic_defaultComponentTranslateClassList";
	
	private static final String LOGIC_DEFAULTCOMPONENTPROCESECLASSLIST = "logic_defaultComponentProceseClassList";
	
	private static final String LOGIC_DTOSERVICESBEANMAP = "logic_dtoServicesBeanMap";
	
	private static final String LOGIC_DTOSERVICESBEANDEFAULTSHOWINSTANCE = "logic_dtoServicesBeanDefaultShowInstance";
	private static final String LOGIC_DTO_DEFAULT_VALUE="logic_dtoDefaultValue";
	private static final String LOGIC_DTO_ADD_MAP="logic_dtoAddMap";
	private static final String LOGIC_DTO_CHECK_MAP="logic_dtoCheckMap";
	private static final String LOGIC_DTO_TRANSLATE_MAP="logic_dtoTranslateMap";
	private static final String LOGIC_DTO_PROCESE_MAP="logic_dtoProceseMap";
	private static final String LOGIC_DTO_SHOWINSTANCE_MAP="logic_dtoShowInstanceMap";
	private static final String LOGIC_DTO_CHECK_INSTANCE_MAP="logic_dtoCheckInstanceMap";
	private static final String LOGIC_DTO_LIST_SERVICE_MAP="logic_dtoListSerice";
	
	
	@SuppressWarnings("unchecked")
	public static List<String> getDefaultComponentAddClassList() {
		return (List<String>)getRequest().get(LOGIC_DEFAULTCOMPONENTADDCLASSLIST);
	}
	
	public static void setDefaultComponentAddClassList(List<String> defaultComponentAddClassList) {
		getRequest().put(LOGIC_DEFAULTCOMPONENTADDCLASSLIST, defaultComponentAddClassList);
	}
	@SuppressWarnings("unchecked")
	public static List<String> getDefaultComponentCheckClassList() {
		return (List<String>)getRequest().get(LOGIC_DEFAULTCOMPONENTCHECKCLASSLIST);
	}
	
	public static void setDefaultComponentCheckClassList(List<String> defaultComponentCheckClassList) {
		getRequest().put(LOGIC_DEFAULTCOMPONENTCHECKCLASSLIST, defaultComponentCheckClassList);
	}
	@SuppressWarnings("unchecked")
	public static List<String> getDefaultComponentTranslateClassList() {
		return (List<String>)getRequest().get(LOGIC_DEFAULTCOMPONENTTRANSLATECLASSLIST);
	}
	
	public static void setDefaultComponentTranslateClassList(List<String> defaultComponentTranslateClassList) {
		getRequest().put(LOGIC_DEFAULTCOMPONENTTRANSLATECLASSLIST, defaultComponentTranslateClassList);
	}
	@SuppressWarnings("unchecked")
	public static List<String> getDefaultComponentProceseClassList() {
		return (List<String>)getRequest().get(LOGIC_DEFAULTCOMPONENTPROCESECLASSLIST);
	}
	
	public static void setDefaultComponentProceseClassList(List<String> defaultComponentProceseClassList) {
		getRequest().put(LOGIC_DEFAULTCOMPONENTPROCESECLASSLIST, defaultComponentProceseClassList);
	}
	
	public static void setDtoServicesBeanMap(Map<String,String> dtoServicesBeanMap) {
		getRequest().put(LOGIC_DTOSERVICESBEANMAP, dtoServicesBeanMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getDtoServicesBeanMap() {
		return (Map<String,String>)getRequest().get(LOGIC_DTOSERVICESBEANMAP);
	}
	
	
	public static String getDefaultLogicDaoBeanId() {
		return (String)getRequest().get(LOGIC_DEFAULTLOGICDAOBEANID);
	}
	
	public static void setDefaultLogicDaoBeanId(String defaultLogicDaoBeanId) {
		getRequest().put(LOGIC_DEFAULTLOGICDAOBEANID, defaultLogicDaoBeanId);
	}

	public static Object getServiceResult() {
		return getRequest().get(LOGIC_SERVICERESULT);
	}
	
	public static void setServiceResult(Object serviceResult) {
		getRequest().put(LOGIC_SERVICERESULT, serviceResult);
	}

	public static DetachedCriteria getDetachedCriteria() {
		return (DetachedCriteria)getRequest().get(LOGIC_DETACHEDCRITERIA);
	}
	
	public static void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		getRequest().put(LOGIC_DETACHEDCRITERIA, detachedCriteria);
	}
	
	public static Object getSaveObjectList() {
		return getRequest().get(LOGIC_SAVEOBJECTLIST);
	}
	
	public static void setSaveObjectList(Object saveObjectList) {
		getRequest().put(LOGIC_SAVEOBJECTLIST, saveObjectList);
	}
	
	public static Integer getTotalCount() {
		return (Integer)getRequest().get(LOGIC_TOTALCOUNT);
	}
	
	public static void setTotalCount(int totalCount) {
		getRequest().put(LOGIC_TOTALCOUNT, totalCount);
	}
	
	public static String getSqlQuery() {
		return (String)getRequest().get(LOGIC_SQLQUERY);
	}
	
	public static void setSqlQuery(String sqlQuery) {
		getRequest().put(LOGIC_SQLQUERY, sqlQuery);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getProcedureParam() {
		return (Map<String,Object>)getRequest().get(LOGIC_PROCEDUREPARAM);
	}
	
	public static void setProcedureParam(Map<String,Object> procedureParam) {
		getRequest().put(LOGIC_PROCEDUREPARAM, procedureParam);
	}

	public static Object getDependTableList() {
		return getRequest().get(LOGIC_DEPENDTTABLELIST);
	}
	
	public static void setDependTableList(Object depList) {
		getRequest().put(LOGIC_DEPENDTTABLELIST, depList);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getDefaultValueMap() {
		return (Map<String,String>)getRequest().get(LOGIC_DEFAULTVALUEMAP);
	}
	
	public static void setDefaultValueMap(Map<String,String> defaultValueMap) {
		getRequest().put(LOGIC_DEFAULTVALUEMAP, defaultValueMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getFieldValueMap() {
		return (Map<String,String>)getRequest().get(LOGIC_FIELDVALUEMAP);
	}
	
	public static void setFieldValueMap(Map<String,String> fieldValueMap) {
		getRequest().put(LOGIC_FIELDVALUEMAP, fieldValueMap);
	}
	
	public static String getDefaultCheckInstance() {
		return (String)getRequest().get(LOGIC_DEFAULTCHECKINSTANCE);
	}
	
	public static void setDefaultCheckInstance(String defaultCheckInstance) {
		getRequest().put(LOGIC_DEFAULTCHECKINSTANCE, defaultCheckInstance);
	}
	
	@SuppressWarnings("unchecked")
	public static Set<Object> getTreeDataSecurity() {
		return (Set<Object>)getRequest().get(LOGIC_TREEDATASECURITY);
	}
	
	public static void setTreeDataSecurity(Set<Object> valueSet) {
		getRequest().put(LOGIC_TREEDATASECURITY, valueSet);
	}
	
	public static String[] getLogicPrimaryKey() {
		return (String[])getRequest().get(LOGIC_PRIMARYKAY);
	}
	
	public static void setLogicPrimaryKey(String[] logicPrimaryKey) {
		getRequest().put(LOGIC_PRIMARYKAY, logicPrimaryKey);
	}
	
	public static String getStrProcedureName() {
		return (String)getRequest().get(LOGIC_STRPROCEDURENAME);
	}
	
	public static void setStrProcedureName(String strProcedureName) {
		getRequest().put(LOGIC_STRPROCEDURENAME, strProcedureName);
	}
	
	public static String[] getStrProcedureParamNames() {
		return (String[])getRequest().get(LOGIC_STRPROCEDUREPARAMNAMES);
	}
	
	public static void setStrProcedureParamNames(String[] strProcedureParamNames) {
		getRequest().put(LOGIC_STRPROCEDUREPARAMNAMES, strProcedureParamNames);
	}
	
	public static void setDtoServicesBeanDefaultShowInstance(String dtoServicesBeanShowInstance) {
		getRequest().put(LOGIC_DTOSERVICESBEANDEFAULTSHOWINSTANCE, dtoServicesBeanShowInstance);
	}
	
	@SuppressWarnings("unchecked")
	public static String getDtoServicesBeanDefaultShowInstance() {
		return (String)getRequest().get(LOGIC_DTOSERVICESBEANDEFAULTSHOWINSTANCE);
	}
	
	public static Map<String,Map<String,String>> getDtoDefaultValue()
	{
		return (Map<String,Map<String,String>>)getRequest().get(LOGIC_DTO_DEFAULT_VALUE);
	}
	public static void setDtoDefaultValue(Map<String,Map<String,String>> dtoDefaultValue)
	{
		getRequest().put(LOGIC_DTO_DEFAULT_VALUE, dtoDefaultValue);
	}
	public static Map<String,List<String>> getDtoAddMap()
	{
		return (Map<String,List<String>>)getRequest().get(LOGIC_DTO_ADD_MAP);
	}
	public static void setDtoAddMap(Map<String,List<String>> dtoAddMap)
	{
		getRequest().put(LOGIC_DTO_ADD_MAP, dtoAddMap);
	}
	public static Map<String,List<String>> getDtoCheckMap()
	{
		return (Map<String,List<String>>)getRequest().get(LOGIC_DTO_CHECK_MAP);
	}
	public static void setDtoCheckMap(Map<String,List<String>> dtoCheckMap)
	{
		getRequest().put(LOGIC_DTO_CHECK_MAP, dtoCheckMap);
	}
	public static Map<String,List<String>> getDtoTranslateMap()
	{
		return (Map<String,List<String>>)getRequest().get(LOGIC_DTO_TRANSLATE_MAP);
	}
	public static void setDtoTranslateMap(Map<String,List<String>> dtoTranslateMap)
	{
		getRequest().put(LOGIC_DTO_TRANSLATE_MAP, dtoTranslateMap);
	}
	public static Map<String,List<String>> getDtoProceseMap()
	{
		return (Map<String,List<String>>)getRequest().get(LOGIC_DTO_PROCESE_MAP);
	}
	public static void setDtoProceseMap(Map<String,List<String>> dtoProceseMap)
	{
		getRequest().put(LOGIC_DTO_PROCESE_MAP, dtoProceseMap);
	}
	public static Map<String,String> getDtoShowInstanceMap()
	{
		return (Map<String,String>)getRequest().get(LOGIC_DTO_SHOWINSTANCE_MAP);
	}
	public static void setDtoShowInstanceMap(Map<String,String> dtoShowInstanceMap)
	{
		getRequest().put(LOGIC_DTO_SHOWINSTANCE_MAP, dtoShowInstanceMap);
	}
	
	public static Map<String,String> getDtoCheckInstanceMap()
	{
		return (Map<String,String>)getRequest().get(LOGIC_DTO_CHECK_INSTANCE_MAP);
	}
	public static void setDtoCheckInstanceMap(Map<String,String> dtoCheckInstanceMap)
	{
		getRequest().put(LOGIC_DTO_CHECK_INSTANCE_MAP, dtoCheckInstanceMap);
	}
	
	public static Map<String,String> getDtoListServiceMap()
	{
		return (Map<String,String>)getRequest().get(LOGIC_DTO_LIST_SERVICE_MAP);
	}
	public static void setDtoListServiceMap(Map<String,String> dtoListServiceMap)
	{
		getRequest().put(LOGIC_DTO_LIST_SERVICE_MAP, dtoListServiceMap);
	}
}
