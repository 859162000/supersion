package framework.actions.imps;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import framework.interfaces.RequestManager;

// 设置命名空间和actionName到request对象
public class BaseAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String getActionName(){
		return ActionContext.getContext().getName();
	}
	
	protected String getNamespace(){
		String servletPath = ServletActionContext.getRequest().getPathInfo();
		if(servletPath==null){
			servletPath = ServletActionContext.getRequest().getServletPath();
		}
		int endIndex = servletPath.lastIndexOf("/");
		return servletPath.substring(0,endIndex).replace("/", "");
	}


	public String execute() throws Exception {
		ServletActionContext.getResponse().addHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
		RequestManager.setNamespace(getNamespace());
		RequestManager.setActionName(getActionName());
		
		return SUCCESS;
	}
}
