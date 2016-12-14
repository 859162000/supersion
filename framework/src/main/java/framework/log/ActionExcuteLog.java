package framework.log;

import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;

public class ActionExcuteLog extends MethodFilterInterceptor{
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
	

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {

		if(excludeActionSet != null){
			String actionName = arg0.getProxy().getActionName();
			if(excludeActionSet.contains(actionName)){
				return arg0.invoke();
			}
		}

		if(actionExcuteService != null){
			IObjectResultExecute objectResultExecute = (IObjectResultExecute)FrameworkFactory.CreateClass(actionExcuteService);
			objectResultExecute.objectResultExecute();
		}
		
		String result = arg0.invoke();
		
		ApplicationManager.getActionExcuteLog().info(arg0.getProxy().getActionName());

		return result;
	}
}