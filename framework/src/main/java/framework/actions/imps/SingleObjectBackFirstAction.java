package framework.actions.imps;

import org.apache.commons.lang.xwork.StringUtils;

import framework.interfaces.ActionJumpResult;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;

public class SingleObjectBackFirstAction extends BaseSTAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean backPreviousFirst;

	public void setBackPreviousFirst(boolean backPreviousFirst) {
		this.backPreviousFirst = backPreviousFirst;
	}
	
	@Override
    public String execute() throws Exception {
		
		initWindowId();
		
		ActionJumpResult actionJumpResult = new ActionJumpResult();
		actionJumpResult.setActionNamespace(getNamespace());

		if(backPreviousFirst){ // 返回到上一级DTO的首页面
			String previousFirstActionName = SessionManager.getFirstActionName(SessionManager.getPreviousLevelTName());
			if(previousFirstActionName != null && (previousFirstActionName.indexOf(TActionRule.SHOWSAVEORUPDATE) > -1 || previousFirstActionName.indexOf(TActionRule.SHOWCHECKUPDATE) > -1)){
				//由于层级需要在SingleObjectShowSaveOrUpdateService修改了actionName，这里需要还原actionName
				String type = SessionManager.getPreviousLevelTName();
				String previousPTName = SessionManager.getLevelTName(SessionManager.getPreviousLevel(SessionManager.getPreviousLevel()));
				String firstActionName = SessionManager.getFirstActionName(SessionManager.getPreviousLevelTName());
				if(previousFirstActionName.indexOf(TActionRule.SHOWSAVEORUPDATE) > -1){
					firstActionName = firstActionName.replaceAll(type, previousPTName);
				}
				String id = SessionManager.getLevelIdValue(SessionManager.getPreviousLevel());
				actionJumpResult.setActionName(firstActionName + "?id=" + id + "&type=" + type);
				actionJumpResult.setWindowId(RequestManager.getWindowId());
			}
			else{
				actionJumpResult.setActionName(SessionManager.getFirstActionName(SessionManager.getPreviousLevelTName()));
				actionJumpResult.setWindowId(RequestManager.getWindowId());
			}
		}
		else{ // 返回到本级DTO的首页面
			String tName = getTName();
			String actionURL = "";
			if(SessionManager.getFirstActionName(tName).equals(TActionRule.getCurrentLevelShowList(tName)) || SessionManager.getFirstActionName(tName).equals(TActionRule.getCurrentLevelShowListForTree(tName))){
				actionURL = SessionManager.getFirstActionName(tName);

				
				// 返回到本级DTO的ShowList页面
				actionJumpResult.setActionName(actionURL);
			}
			else{ // 其它情况，返回上一级的首页面
				String actionName=SessionManager.getFirstActionName(SessionManager.getPreviousLevelTName());
				if(StringUtils.isBlank(actionName))
				{
					actionName=SessionManager.popWinwodIdAction(RequestManager.getWindowId());
					actionName=SessionManager.popWinwodIdAction(RequestManager.getWindowId());
				}
				actionJumpResult.setActionName(actionName);
			}
		}
		
		
		this.setServiceResult(actionJumpResult);
		
		return SUCCESS;
	}
}
