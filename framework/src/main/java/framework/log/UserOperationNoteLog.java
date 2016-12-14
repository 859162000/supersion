package framework.log;

import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.services.interfaces.LogicParamManager;

public class UserOperationNoteLog extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<String> excludeActionSet;
	
	public void setExcludeActions(String excludeActions) {
		excludeActionSet = TextParseUtil.commaDelimitedStringToSet(excludeActions);
	}
	
	private String actionExcuteService;
	
	public void setActionExcuteService(String actionExcuteService) {
		this.actionExcuteService = actionExcuteService;
	}
	
	private Set<String> excutebeforeupdateActionSet;
	
	
	public void setExcutebeforeupdateActions(String excutebeforeupdateActions) {
		this.excutebeforeupdateActionSet = TextParseUtil.commaDelimitedStringToSet(excutebeforeupdateActions);
	}

	private Set<String> beforeActionSet;
	public void setBeforeActions(String beforeActions){
		beforeActionSet= TextParseUtil.commaDelimitedStringToSet(beforeActions);
	}
	
	private String beforeupdateService;
	
	public void setBeforeupdateService(String beforeupdateService) {
		this.beforeupdateService = beforeupdateService;
	}
	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {

		boolean isExcutearg=false;
		
		String result="";
		try
		{
			if(excludeActionSet != null){
			String actionName = arg0.getProxy().getActionName();
			if(actionName.indexOf("-") > -1){
				actionName = actionName.substring(0,actionName.indexOf("-"));
			}
			if(actionName.indexOf("Level") > -1){
				actionName = actionName.substring(0,actionName.indexOf("Level"));
			}
			if(excludeActionSet.contains(actionName)){
				isExcutearg=true;
				return arg0.invoke();
			}
		}
		if(beforeActionSet !=null){
			String actionName = arg0.getProxy().getActionName();
			if(actionName.indexOf("-") > -1){
				actionName = actionName.substring(0,actionName.indexOf("-"));
			}
			if(actionName.indexOf("Level") > -1){
				actionName = actionName.substring(0,actionName.indexOf("Level"));
			}
			if(excutebeforeupdateActionSet !=null){
				if(excutebeforeupdateActionSet.contains(actionName))
				{
					if(beforeupdateService != null){
						IObjectResultExecute objectResultExecute = (IObjectResultExecute)FrameworkFactory.CreateClass(beforeupdateService);
						objectResultExecute.objectResultExecute();
					}
				}
			}
			if(!beforeActionSet.contains(actionName)){
				isExcutearg=true;
				result= arg0.invoke();
			}
		}
		
		if(actionExcuteService != null){
			IObjectResultExecute objectResultExecute = (IObjectResultExecute)FrameworkFactory.CreateClass(actionExcuteService);
			objectResultExecute.objectResultExecute();
		}
		if(beforeActionSet !=null){
			String actionName = arg0.getProxy().getActionName();
			if(actionName.indexOf("-") > -1){
				actionName = actionName.substring(0,actionName.indexOf("-"));
			}
			if(actionName.indexOf("Level") > -1){
				actionName = actionName.substring(0,actionName.indexOf("Level"));
			}
			if(beforeActionSet.contains(actionName)){
				isExcutearg=true;
				result= arg0.invoke();
			}
		}
		
		ApplicationManager.getActionExcuteLog().info(arg0.getProxy().getActionName());

		return result;
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			if(!isExcutearg){

				return arg0.invoke();
			}
			else{
				return result;
			}
		}
	}
}