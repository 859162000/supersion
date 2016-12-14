package framework.services.interfaces;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

public class ShowParamManager {
	
	@SuppressWarnings("unchecked")
	private static Map<String,Object> getRequest(){
		return (Map<String,Object>)ServletActionContext.getContext().get("request");
	}
	
	private static final String SHOW_SHOWINSTANCENAME = "show_showInstanceName";
	
	private static final String SHOW_OPERATIONMAP = "show_operationMap";
	
	private static final String SHOW_LINKEDMAP = "show_linkedMap";
	
    private static final String SHOW_FIRSTRESULT = "show_firstResult";
	
	private static final String SHOW_PAGESIZE = "show_pageSize";
	
	private static final String SHOW_TYPEFIELDNAME = "show_typeFieldName";
	
	private static final String SHOW_TYPEFIELDVALUETYPE = "show_typeFieldValueMap";
	
	private static final String SHOW_TYPEFIELDNAMEANDVALUETYPE = "show_typeFieldNameAndValueMap";
	
	private static final String SHOW_LEVELFIELDTYPE = "show_levelFieldMap";
	
	private static final String SHOW_ISSHOWLEVELOBJECT = "show_isShowLevelObject";
	
	private static final String SHOW_TREEROOTNAME = "show_treeRootName";
	
	private static final String SHOW_TREEROOTURL = "show_treeRootUrl";
	
	private static final String SHOW_TREECONSTRUCTTYPE = "show_treeConstructType";
	
	private static final String SHOW_TREEIMAGEURLLIST = "show_treeImageUrlList";
	
	private static final String SHOW_TREEFOREIGNFIELDMAP = "show_treeForeignFieldMap";
	
	private static final String SHOW_TREESERVICEMAP = "show_treeServiceMap";
	
	public static String getShowInstanceName() {
		return (String)getRequest().get(SHOW_SHOWINSTANCENAME);
	}
	
	public static void setShowInstanceName(String showInstanceName) {
		getRequest().put(SHOW_SHOWINSTANCENAME, showInstanceName);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getOperationMap() {
		return (Map<String,String>)getRequest().get(SHOW_OPERATIONMAP);
	}
	
	public static void setOperationMap(Map<String,String> operationMap) {
		getRequest().put(SHOW_OPERATIONMAP, operationMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getLinkedMap() {
		return (Map<String,String>)getRequest().get(SHOW_LINKEDMAP);
	}
	
	public static void setLinkedMap(Map<String,String> linkedMap) {
		getRequest().put(SHOW_LINKEDMAP, linkedMap);
	}

	public static Integer getFirstResult() {
		return (Integer)getRequest().get(SHOW_FIRSTRESULT);
	}
	
	public static void setFirstResult(int firstResult) {
		getRequest().put(SHOW_FIRSTRESULT, firstResult);
	}
	
	public static Integer getPageSize() {
		return (Integer)getRequest().get(SHOW_PAGESIZE);
	}
	
	public static void setPageSize(int pageSize) {
		getRequest().put(SHOW_PAGESIZE, pageSize);
	}
	
	public static String getTypeFieldName() {
		return (String)getRequest().get(SHOW_TYPEFIELDNAME);
	}
	
	public static void setTypeFieldName(String typeFieldName) {
		getRequest().put(SHOW_TYPEFIELDNAME, typeFieldName);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getTypeFieldValueMap() {
		return (Map<String,String>)getRequest().get(SHOW_TYPEFIELDVALUETYPE);
	}
	
	public static void setTypeFieldValueMap(Map<String,String> typeFieldValueMap) {
		getRequest().put(SHOW_TYPEFIELDVALUETYPE, typeFieldValueMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getTypeFieldNameAndValueMap() {
		return (Map<String,String>)getRequest().get(SHOW_TYPEFIELDNAMEANDVALUETYPE);
	}
	
	public static void setTypeFieldNameAndValueMap(Map<String,String> typeFieldNameAndValueMap) {
		getRequest().put(SHOW_TYPEFIELDNAMEANDVALUETYPE, typeFieldNameAndValueMap);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getLevelFieldMap() {
		return (Map<String,String>)getRequest().get(SHOW_LEVELFIELDTYPE);
	}
	
	public static void setLevelFieldMap(Map<String,String> levelFieldMap) {
		getRequest().put(SHOW_LEVELFIELDTYPE, levelFieldMap);
	}
	
	public static boolean getIsShowLevelObject() {
		return (Boolean)getRequest().get(SHOW_ISSHOWLEVELOBJECT);
	}
	
	public static void setIsShowLevelObject(boolean isShowLevelObject) {
		getRequest().put(SHOW_ISSHOWLEVELOBJECT, isShowLevelObject);
	}

	public static String getShowTreeRootName() {
		return (String)getRequest().get(SHOW_TREEROOTNAME);
	}
	
	public static void setShowTreeRootName(String rootName) {
		getRequest().put(SHOW_TREEROOTNAME, rootName);
	}
	
	public static String getShowTreeRootUrl() {
		return (String)getRequest().get(SHOW_TREEROOTURL);
	}
	
	public static void setShowTreeRootUrl(String rootUrl) {
		getRequest().put(SHOW_TREEROOTURL, rootUrl);
	}
	
	public static String getConstructType() {
		return (String)getRequest().get(SHOW_TREECONSTRUCTTYPE);
	}
	
	public static void setConstructType(String constructType) {
		getRequest().put(SHOW_TREECONSTRUCTTYPE, constructType);
	}

	@SuppressWarnings("unchecked")
	public static List<String> getShowTreeImage() {
		return (List<String>)getRequest().get(SHOW_TREEIMAGEURLLIST);
	}
	
	public static void setShowTreeImageUrlList(List<String> imageList) {
		getRequest().put(SHOW_TREEIMAGEURLLIST, imageList);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getForeignFieldMap() {
		return (Map<String,String>)getRequest().get(SHOW_TREEFOREIGNFIELDMAP);
	}
	
	public static void setForeignFieldMap(Map<String,String> foreignFieldMap) {
		getRequest().put(SHOW_TREEFOREIGNFIELDMAP, foreignFieldMap);
	}

	@SuppressWarnings("unchecked")
	public static Map<String,String> getTreeServiceMap() {
		return (Map<String,String>)getRequest().get(SHOW_TREESERVICEMAP);
	}
	
	public static void setTreeServiceMap(Map<String,String> treeServiceMap) {
		getRequest().put(SHOW_TREESERVICEMAP, treeServiceMap);
	}

}
