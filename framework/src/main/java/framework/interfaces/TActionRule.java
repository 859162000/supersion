package framework.interfaces;

import org.apache.commons.lang.xwork.StringUtils;

public class TActionRule {
	
	private static final String SELECTWAY = "Select";
	private static final String GETWAY = "Get";
	private static final String POSTWAY = "Post";
	private static final String UPLOADWAY = "Upload";
	private static final String CLIENT_WAY= "Client";
	private static final String AJAX_POST_WAY= "AjaxPost";
	
	private static final String SEG = "-";
	
	private static final String SHOWLIST = "ShowList";
	private static final String SHOWSAVE = "ShowSave";
	private static final String SHOWUPDATE = "ShowUpdate";
	public static final String SHOWCHECKUPDATE = "ShowCheckUpdate";
	private static final String SHOWUPDATELIST = "ShowUpdateList";
	public static final String SHOWSAVEORUPDATE = "ShowSaveOrUpdate";
	private static final String SHOWLISTFORTREE = "ShowListForTree";
	private static final String ONETOONESHOWSAVE = "OneToOneShowSave";
	private static final String ONETOONESHOWUPDATE = "OneToOneShowUpdate";
	
	private static final String LEVEL = "Level";

	public static String getSEG(){
		return SEG;
	}
	
	public static String getSelectWay(){
		return SELECTWAY;
	}
	
	public static String getGetWay(){
		return GETWAY;
	}
	
	public static String getPostWay(){
		return POSTWAY;
	}
	
	public static String getUploadWay(){
		return UPLOADWAY;
	}
	
	public static String getTName(String actionName){
		int startIndex = actionName.indexOf(SEG) + 1;
		String tName = actionName.substring(startIndex);
		tName = tName.replace(".action", "");
		if(tName.indexOf("?") > -1){
			tName = tName.substring(0,tName.indexOf("?"));
		}
		
		return tName;
	}
	
	public static String getAction(String actionName){
		int startIndex = actionName.indexOf(SEG);
		String action = actionName.substring(0,startIndex);
		return action;
	}

	public static String getCurrentLevelShowList(String tName){
		return getCurrentLevelActionName(SHOWLIST,tName);
	}

	public static String getCurrentLevelShowSave(String tName){
		return getCurrentLevelActionName(SHOWSAVE,tName);
	}
	
	public static String getCurrentLevelShowUpdate(String tName){
		return getCurrentLevelActionName(SHOWUPDATE,tName);
	}
	
	public static String getCurrentLevelShowCheckUpdate(String tName){
		return getCurrentLevelActionName(SHOWCHECKUPDATE,tName);
	}
	
	public static String getCurrentLevelOneToOneShowSave(String tName){
		return getCurrentLevelActionName(ONETOONESHOWSAVE,tName);
	}
	
	public static String getCurrentLevelOneToOneShowUpdate(String tName){
		return getCurrentLevelActionName(ONETOONESHOWUPDATE,tName);
	}

	public static String getCurrentLevelShowUpdateList(String tName){
		return getCurrentLevelActionName(SHOWUPDATELIST,tName);
	}
	
	public static String getCurrentLevelShowSaveOrUpdate(String tName){
		return getCurrentLevelActionName(SHOWSAVEORUPDATE,tName);
	}
	public static String getCurrentLevelShowListForTree(String tName){
		return getCurrentLevelActionName(SHOWLISTFORTREE,tName);
	}

	public static String getOperationType(String operation){
		return operation.substring(0,operation.indexOf(SEG));
	}
	
	public static String getCurrentLevelOperationActionName(String operation, String tName){
		String[] operationList = operation.split(SEG);
		if(operationList.length == 3){ // 如：Get-ShowListLevel2-autoETLsystem.dto.AutoETL_Database
			if(operationList[1].indexOf("/") > -1){ // 去掉命名空间
				return operationList[1].substring(operationList[1].indexOf("/") + 1) + SEG + operationList[2];
			}
			else{
				return operationList[1] + SEG + operationList[2];
			}
		}
		else if(operationList.length == 2){ //如：Get-ShowUpdate
			operation = operationList[1];
			if(operationList[1].indexOf("/") > -1){
				operation = operationList[1].substring(operationList[1].indexOf("/") + 1);
			}
		}
		return getCurrentLevelActionName(operation,tName);
	}
	
	public static String getNamespace(String operation){
		String[] operationList = operation.split(SEG);
		if(operationList[1].indexOf("/") > -1){
			return operationList[1].substring(0,operationList[1].indexOf("/"));
		}
		else{
			return RequestManager.getNamespace();
		}
	}
	
	public static String getConditionClassName(String tName) throws ClassNotFoundException{
		String tSimpleName = Class.forName(tName).getSimpleName();
		int endIndex = tName.indexOf(tSimpleName);
		String condtionName = tName.substring(0,endIndex) + "condition." + tSimpleName + "_Condition";
		return condtionName;
	}
	
	public static String getServiceBeanName(String tName, String action) throws ClassNotFoundException{
		String tSimpleName;
		Class<?> type = null;
		try{
			type = Class.forName(tName);
		}
		catch(Exception ex){
			
		}
		if(type == null){
			tSimpleName = tName;
		}
		else{
			tSimpleName = Class.forName(tName).getSimpleName();
		}
		String serviceBeanName = tSimpleName.substring(0,1).toLowerCase() + tSimpleName.substring(1) + action + "Service";
		return serviceBeanName;
	}
	
	public static String getDefualtBeanName(String defaultServiceBeanId) throws ClassNotFoundException{
		String tName = "";
		if(RequestManager.getTName() != null){
			tName = RequestManager.getTName() + SEG;
		}
    	if(SessionManager.getCurrentLevel() == null || SessionManager.getCurrentLevel().equals(tName + SessionManager.SERVICE_DEFAULTLEVEL)){
    		return defaultServiceBeanId;
    	}
    	else{
    		return defaultServiceBeanId.substring(0,defaultServiceBeanId.indexOf("Service")) + LEVEL + "Service";
    	}
	}
	
	public static String getCurrentLevelActionName(String rule, String tName){
		if(SessionManager.getCurrentLevel() == null || SessionManager.getCurrentLevel().equals(tName + SEG + SessionManager.SERVICE_DEFAULTLEVEL) || rule.indexOf(LEVEL) > -1){
			return rule + SEG + tName;
		}
		else{
			return rule + LEVEL + SessionManager.getCurrentLevel() + SEG + tName;
		}
	}
	
	public static String getPreviousLevelActionName(String rule, String tName){
		if(SessionManager.getPreviousLevel() == null || SessionManager.getPreviousLevel().equals(tName + SEG + SessionManager.SERVICE_DEFAULTLEVEL)){
			return rule + SEG + tName;
		}
		else{
			return rule + LEVEL + SessionManager.getPreviousLevel() + SEG + tName;
		}
	}
	
    public static void initActionName(){
		
    	String actionName = RequestManager.getActionName();
    	
    	//原条件：!RequestManager.getTName().equals(SessionManager.getCurrentLevelTName()) // 跳转(jumpByType)时不记录级别
		// 树展示自身数据时需要记录级别
    	
		if(actionName.indexOf(LEVEL) > -1){ // action名中含有"Level"串
			if(!RequestManager.getTName().equals(SessionManager.getCurrentLevelTName()))
			{
				String level = actionName.substring(actionName.indexOf(LEVEL));
				String currentLevel = level.substring(LEVEL.length(),level.indexOf(SEG));
				if(SessionManager.getPreviousLevel(currentLevel) == null){
					SessionManager.setPreviousLevel(currentLevel,SessionManager.getCurrentLevel());
				}

				SessionManager.setCurrentLevel(currentLevel);
				if(RequestManager.getLevelIdValue() != null){
					SessionManager.setLevelIdValue(SessionManager.getCurrentLevel(), RequestManager.getLevelIdValue());
				}
			}
		}
		else{ // action名中没有level作为0级
			SessionManager.setCurrentLevel(RequestManager.getTName() + SEG + SessionManager.SERVICE_DEFAULTLEVEL);
		}
		
		if(actionName.indexOf(SHOWLISTFORTREE) > -1){ 
			if(!StringUtils.isBlank(RequestManager.getTypeName())){
				SessionManager.setTreeIdValue(RequestManager.getTName(),RequestManager.getLevelIdValue());
				SessionManager.setTreeTypeName(RequestManager.getTName(),RequestManager.getTypeName());
			}
		}

		SessionManager.setLevelTName(SessionManager.getCurrentLevel(), RequestManager.getTName());
		
		// 为每个T设置一个首页action，用于返回
		if(SessionManager.getFirstActionName(RequestManager.getTName()) == null
				|| actionName.indexOf(SHOWLIST) > -1) // 当有showList action时重设DTO首页
		{
			if(actionName.indexOf("?") > -1)
				actionName = actionName.substring(0, actionName.indexOf("?"));
			
			if(actionName.contains(getCurrentLevelShowList(RequestManager.getTName()))
					|| actionName.contains(getCurrentLevelShowUpdateList(RequestManager.getTName()))
					|| actionName.contains(getCurrentLevelShowSave(RequestManager.getTName()))
					|| actionName.contains(getCurrentLevelShowUpdate(RequestManager.getTName()))
					|| actionName.contains(getCurrentLevelShowCheckUpdate(RequestManager.getTName()))
					|| actionName.contains(getCurrentLevelShowSaveOrUpdate(RequestManager.getTName()))
					|| actionName.contains(getCurrentLevelShowListForTree(RequestManager.getTName()))){
				SessionManager.setFirstActionName(RequestManager.getTName(), actionName);
				//SessionManager.setCurFirstActionID(RequestManager.getLevelIdValue());
				//SessionManager.setCurFirstActionType(RequestManager.getTypeName());
			}
		}
	}

}
