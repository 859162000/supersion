package framework.interfaces;

import java.io.File;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

@SuppressWarnings("unchecked")
public class RequestManager {
	
	private static Map<String,Object> getRequest(){
		return (Map<String,Object>)ServletActionContext.getContext().get("request");
	}

	private static final String ACTION_NAMESPACE = "action_namespace";
	
	private static final String ACTION_ACTIONNAME = "action_actionName";
	
	private static final String ACTION_WINDOWID = "action_windowId";
	
	private static final String ACTION_TNAME = "action_tName";
	
	private static final String ACTION_TOBJECT = "action_tObject";
	
	private static final String ACTION_ID = "action_id";
	
	private static final String ACTION_IDLIST = "action_idList";
	
	private static final String ACTION_INPUTNAME = "action_inputName";
	
	private static final String ACTION_UPLOADFILE = "action_uploadFile";
	
	private static final String ACTION_CURRENTPAGE = "action_currentPage";
	
	private static final String ACTION_TYPENANME = "action_typeName";
	
	private static final String ACTION_LEVELIDVALUE = "action_idLevelValue";
	
	private static final String ACTION_REPORTCHECKPARAM = "action_reportCheckParam";
	
	private static final String ACTION_USERCODE = "action_userCode";
	private static final String ACTION_PASSWORD = "action_password";
	
	private static final String ACTION_AUTODTOTABLENAME = "action_autodtoTableName";
	
	private static final String ACTION_FIRSTSHOWDATA = "action_firstShowData";
	
	public static String getNamespace() {
		return (String)getRequest().get(ACTION_NAMESPACE);
	}
	
	public static void setNamespace(String namespace) {
		getRequest().put(ACTION_NAMESPACE, namespace);
	}
	
	public static String getActionName() {
		return (String)getRequest().get(ACTION_ACTIONNAME);
	}
	
	public static void setActionName(String actionName) {
		getRequest().put(ACTION_ACTIONNAME, actionName);
	}
	
	public static String getWindowId() {
		return (String)getRequest().get(ACTION_WINDOWID);
	}
	
	public static void setWindowId(String windowId) {
		getRequest().put(ACTION_WINDOWID, windowId);
	}
	
	public static String getTName() {
		return (String)getRequest().get(ACTION_TNAME);
	}
	
	public static void setTName(String tName) {
		getRequest().put(ACTION_TNAME, tName);
	}
	
	public static Object getTOject() {
		return getRequest().get(ACTION_TOBJECT);
	}
	
	public static void setTOject(Object tObject) {
		getRequest().put(ACTION_TOBJECT, tObject);
	}
	
	public static Object getId() {
		return getRequest().get(ACTION_ID);
	}
	
	public static void setId(Object id) {
		getRequest().put(ACTION_ID, id);
	}
	
	public static String getTypeName() {
		return (String)getRequest().get(ACTION_TYPENANME);
	}
	
	public static void setTypeName(String type) {
		getRequest().put(ACTION_TYPENANME, type);
	}
	
	public static Object getIdList() {
		return getRequest().get(ACTION_IDLIST);
	}
	
	public static void setIdList(Object idList) {
		getRequest().put(ACTION_IDLIST, idList);
	}
	
	public static String getInputName() {
		return (String)getRequest().get(ACTION_INPUTNAME);
	}
	
	public static void setInputName(String inputName) {
		getRequest().put(ACTION_INPUTNAME, inputName);
	}
	
	public static File getUploadFile() {
		return (File)getRequest().get(ACTION_UPLOADFILE);
	}
	
	public static void setUploadFile(File uploadFile) {
		getRequest().put(ACTION_UPLOADFILE, uploadFile);
	}
	
	public static String getLevelIdValue() {
		return (String)getRequest().get(ACTION_LEVELIDVALUE);
	}
	
	public static void setLevelIdValue(Object levelIdValue) {
		getRequest().put(ACTION_LEVELIDVALUE, levelIdValue);
	}

	public static Integer getCurrentPage() {
		return (Integer)getRequest().get(ACTION_CURRENTPAGE);
	}
	
	public static void setCurrentPage(int currentPage) {
		getRequest().put(ACTION_CURRENTPAGE, currentPage);
	}
	
	public static Map<String,String> getReportCheckParam() {
		return (Map<String,String>)getRequest().get(ACTION_REPORTCHECKPARAM);
	}
	
	public static void setReportCheckParam(Map<String,String> checkParam) {
		getRequest().put(ACTION_REPORTCHECKPARAM, checkParam);
	}

	public static String getActionUsercode() {
		return (String)getRequest().get(ACTION_USERCODE);
	}
	
	public static void setActionUsercode(String strUserCode) {
		getRequest().put(ACTION_USERCODE, strUserCode);
	}

	public static String getActionPassword() {
		return (String)getRequest().get(ACTION_PASSWORD);
	}
	
	public static void setActionPassword(String strActionPassword) {
		getRequest().put(ACTION_PASSWORD, strActionPassword);
	}
	
	public static String getActionAutoDTOTableName() {
		return (String)getRequest().get(ACTION_AUTODTOTABLENAME);
	}
	
	public static void setActionAutoDTOTableName(String strActionAutoDTOTableName) {
		getRequest().put(ACTION_AUTODTOTABLENAME, strActionAutoDTOTableName);
	}

	public static Boolean isFirstShowData() {
		return (Boolean)getRequest().get(ACTION_FIRSTSHOWDATA);
	}
	
	public static void setFirstShowData(Boolean firstShowData) {
		getRequest().put(ACTION_FIRSTSHOWDATA, firstShowData);
	}
	
}
