package framework.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import framework.security.LoginInfo;

public class SessionManager {
	
	private static Map<String,Object> getSession(){
		return ServletActionContext.getContext().getSession();
	}
	
	private static final String SEG = "-";
	
	private static final String LOGININFO = "loginInfo";
	
	private static final String ACTION_TCONDITION = "action_tCondition";
	
	private static final String ACTION_CURRENTPAGE = "action_currentPage";
	
	public static final String SERVICE_DEFAULTLEVEL = "0";
	
	public static final String SERVICE_WINDOWID = "service_windowId";
	
	private static final String ACTION_PREVIOUSLEVEL = "action_previousLevel";

	private static final String ACTION_CURRENTLEVEL = "action_currentLevel";

	private static final String ACTION_LEVELTNAME = "action_levelTName";
	
	private static final String ACTION_LEVELIDVALUE = "action_levelIdValue";
	
	private static final String ACTION_TFIRSTACTIONNAME = "action_tFirstActionName";
	
	//private static final String ACTION_CURFIRSTACTIONID = "action_CurFirstActionID";
	
	//private static final String ACTION_CURFIRSTACTIONTYPE = "action_CurFirstActionType";
	
	private static final String ACTION_TREETYPENAME = "action_treeTypeName";
	
	private static final String ACTION_TREEIDVALUE = "action_treeIdValue";
	
	private static final String ACTION_WINDOWID = "action_windowId";
	private static final String WINDOWID_URL_STACK="windowId_url_stack";
	
	public static LoginInfo getLoginInfo(){
		return (LoginInfo)getSession().get(LOGININFO);
	}
	
	public static void setLoginInfo(LoginInfo loginInfo){
		getSession().put(LOGININFO, loginInfo);
	}

	public static Object getTCondition(String tName) {
		return getSession().get(ACTION_TCONDITION + SEG + tName);
	}
	
	public static void setTCondition(Object tCondition, String tName) {
		getSession().put(ACTION_TCONDITION + SEG + tName, tCondition);
	}
	
	public static Integer getCurrentPage(String tName) {
		return (Integer)getSession().get(ACTION_CURRENTPAGE + SEG + tName);
	}
	
	public static void setCurrentPage(int currentPage, String tName) {
		getSession().put(ACTION_CURRENTPAGE + SEG + tName, currentPage);
	}

	@SuppressWarnings("unchecked")
	public static String getPreviousLevel(String currentLevel) {
		
		String windowId = SERVICE_WINDOWID;
		if(!StringUtils.isBlank(RequestManager.getWindowId())){
			windowId = SERVICE_WINDOWID + SEG +RequestManager.getWindowId();
		}
		return (String)((Map<String,String>)getSession().get(windowId)).get(ACTION_PREVIOUSLEVEL + SEG + currentLevel);
	}
	
	@SuppressWarnings("unchecked")
	public static String getPreviousLevel() {
		String windowId = SERVICE_WINDOWID;
		if(!StringUtils.isBlank(RequestManager.getWindowId())){
			windowId = SERVICE_WINDOWID + SEG +RequestManager.getWindowId();
		}
		return (String)((Map<String,String>)getSession().get(windowId)).get(ACTION_PREVIOUSLEVEL + SEG + getCurrentLevel());
	}
	
	@SuppressWarnings("unchecked")
	public static void setPreviousLevel(String currentLevel,String previousLevel) {
		
		String windowId = SERVICE_WINDOWID;
		if(!StringUtils.isBlank(RequestManager.getWindowId())){
			windowId = SERVICE_WINDOWID + SEG +RequestManager.getWindowId();
		}
		Map<String,String> windowLevelMap = null;
		if(getSession().get(windowId) == null){
			windowLevelMap = new HashMap<String,String>();
		}
		else{
			windowLevelMap = (Map<String,String>)getSession().get(windowId);
			getSession().remove(windowId);
		}
		windowLevelMap.put(ACTION_PREVIOUSLEVEL + SEG + currentLevel, previousLevel);

		getSession().put(windowId,windowLevelMap);
	}
	
	@SuppressWarnings("unchecked")
	public static String getCurrentLevel() {
		
		String windowId = SERVICE_WINDOWID;
		if(!StringUtils.isBlank(RequestManager.getWindowId())){
			windowId = SERVICE_WINDOWID + SEG +RequestManager.getWindowId();
		}
		return (String)((Map<String,String>)getSession().get(windowId)).get(ACTION_CURRENTLEVEL);
	}
	
	@SuppressWarnings("unchecked")
	public static void setCurrentLevel(String currentLevel) {
		
		String windowId = SERVICE_WINDOWID;
		if(!StringUtils.isBlank(RequestManager.getWindowId())){
			windowId = SERVICE_WINDOWID + SEG +RequestManager.getWindowId();
		}
		Map<String,String> windowLevelMap = null;
		if(getSession().get(windowId) == null){
			windowLevelMap = new HashMap<String,String>();
		}
		else{
			windowLevelMap = (Map<String,String>)getSession().get(windowId);
			getSession().remove(windowId);
		}
		windowLevelMap.put(ACTION_CURRENTLEVEL, currentLevel);
		getSession().put(windowId,windowLevelMap);
	}
	
	public static String getCurrentLevelTName() {
		return getLevelTName(getCurrentLevel());
	}
	
	public static String getPreviousLevelTName() {
		return getLevelTName(getPreviousLevel());
	}
	
	public static String getLevelTName(String level) {
		return (String)getSession().get(ACTION_LEVELTNAME + SEG + level);
	}
	
	public static void setLevelTName(String level, String tName) {
		getSession().put(ACTION_LEVELTNAME + SEG + level, tName);	}
	
	public static String getLevelIdValue(String level) {
		return (String)getSession().get(ACTION_LEVELIDVALUE + SEG + level);
	}
	
	public static void setLevelIdValue(String level, String idValue) {
		getSession().put(ACTION_LEVELIDVALUE + SEG + level, idValue);
	}
	
	public static String getTreeTypeName(String tName) {
		return (String)getSession().get(ACTION_TREETYPENAME + SEG + tName);
	}
	
	public static void setTreeTypeName(String tName,String typeName) {
		getSession().put(ACTION_TREETYPENAME + SEG + tName, typeName);
	}
	
	public static String getTreeIdValue(String tName) {
		return (String)getSession().get(ACTION_TREEIDVALUE + SEG + tName);
	}
	
	public static void setTreeIdValue(String tName, String idValue) {
		getSession().put(ACTION_TREEIDVALUE + SEG + tName, idValue);
	}
	
	public static String getFirstActionName(String tName) {
		return (String)getSession().get(ACTION_TFIRSTACTIONNAME + SEG + tName+RequestManager.getWindowId());
	}
	
	public static String setFirstActionName(String tName,String action) {
		return (String)getSession().put(ACTION_TFIRSTACTIONNAME + SEG + tName+RequestManager.getWindowId(), action);
	}
	
	public static String getWindowId(String tName) {
		return (String)getSession().get(ACTION_WINDOWID + SEG + tName);
	}
	
	public static void setWindowId(String tName,String windowId) {
		getSession().put(ACTION_WINDOWID + SEG + tName, windowId);
	}
	public static void pushWinwodIdAction(String windowId,String actionName)
	{
		Map<String,Stack<String>> map=(Map<String,Stack<String>>) getSession().get(WINDOWID_URL_STACK);
		if(map==null)
		{
			map=new HashMap<String,Stack<String>>();
			getSession().put(WINDOWID_URL_STACK, map);
		}
		Stack<String> s=map.get(windowId);
		if(s==null)
		{
			s=new Stack<String>();
			map.put(windowId, s);
		}
		if(actionName.toUpperCase().startsWith("SHOW"))
		{
			s.push(actionName);
		}
		
		
	}
	public static String popWinwodIdAction(String windowId)
	{
		Map<String,Stack<String>> map=(Map<String,Stack<String>>) getSession().get(WINDOWID_URL_STACK);
		String actionName="";
		if(map!=null)
		{
			Stack<String> s=map.get(windowId);
			if(s!=null)
			{
				if(!s.isEmpty())
				{
					actionName=s.pop();
				}
				
			}
			
		}
		return actionName;		
	}

	/*public static String getCurFirstActionID() {
		return (String)getSession().get(ACTION_CURFIRSTACTIONID);
	}
	
	public static void setCurFirstActionID(String id) {
		getSession().put(ACTION_CURFIRSTACTIONID, id);
	}

	public static String getCurFirstActionType() {
		return (String)getSession().get(ACTION_CURFIRSTACTIONTYPE);
	}
	
	public static void setCurFirstActionType(String type) {
		getSession().put(ACTION_CURFIRSTACTIONTYPE, type);
	}*/

}
