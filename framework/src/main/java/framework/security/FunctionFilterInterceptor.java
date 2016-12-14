package framework.security;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

public class FunctionFilterInterceptor extends MethodFilterInterceptor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Set<String> excludeActionSet;
	
	public void setExcludeActions(String excludeActions) {
		excludeActionSet = TextParseUtil.commaDelimitedStringToSet(excludeActions);
	}
	
	private String loginPage;
	
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	
	private boolean doFilter;
	
	public void setDoFilter(boolean doFilter) {
		this.doFilter = doFilter;
	}

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		
		String actionName = arg0.getProxy().getActionName();
		
		if(excludeActionSet != null){
			if(excludeActionSet.contains(actionName)){
				return arg0.invoke();
			}
		}
		
		if(actionName.equals("Login")) return arg0.invoke();
		if(actionName.startsWith("PreLogin-")) return arg0.invoke();
		
		if(SecurityContext.getInstance().getLoginInfo() == null){
			if(loginPage != null){
				HttpServletResponse response = ServletActionContext.getResponse();
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				StringBuffer str = new StringBuffer("<script>");
				str.append("window.top.location.href='" + ServletActionContext.getRequest().getContextPath() + loginPage + "';</script>");
				ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
				out.print(str.toString());
				out.close();
				
			}
			return "login";
		}
		else{
			
			if(!doFilter){
				return arg0.invoke();
			}
			
			if(actionName.indexOf("?") > -1){
				actionName = actionName.substring(0,actionName.indexOf("?"));
			}
			
			if(actionName.indexOf(".action") > -1){
				actionName = actionName.substring(0,actionName.indexOf(".action"));
			}
			
			if(SecurityContext.getInstance().getLoginInfo().getActionNameSet().contains(arg0.getProxy().getNamespace().substring(1) + "/" + actionName)){
				return arg0.invoke();
			}
			else { // 检查有通配符的权限
				for(String configAction : SecurityContext.getInstance().getLoginInfo().getActionNameSet()) {
					int position = configAction.indexOf("*");
					if(position > -1) {
						if(position == 0) { // 左*
							String actionName1 = configAction.substring(1);
							if((arg0.getProxy().getNamespace().substring(1) + "/" + actionName).endsWith(actionName1)) {
								return arg0.invoke();
							}
						}
						else if(position == configAction.length() -1){ // 右*
							String actionName2 = configAction.substring(0,configAction.indexOf("*"));
							if((arg0.getProxy().getNamespace().substring(1) + "/" + actionName).startsWith(actionName2)) {
								return arg0.invoke();
							}
						}
						else{
							String actionName1 = configAction.substring(0,configAction.indexOf("*"));
							String actionName2 = configAction.substring(configAction.indexOf("*") + 1);
							if((arg0.getProxy().getNamespace().substring(1) + "/" + actionName).startsWith(actionName1) && (arg0.getProxy().getNamespace().substring(1) + "/" + actionName).endsWith(actionName2)) {
								return arg0.invoke();
							}
						}
					}
				}
			}
			System.out.println("无权限："+actionName);
			return "permission";
		}
	}

}
