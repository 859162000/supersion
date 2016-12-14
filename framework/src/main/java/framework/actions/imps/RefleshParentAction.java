package framework.actions.imps;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class RefleshParentAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String redirectActionName;
	
	public void setRedirectActionName(String redirectActionName) {
		this.redirectActionName = redirectActionName;
	}

	public String getRedirectActionName() {
		return redirectActionName;
	}
	
	private String redirectActionNamespace;
	
	public void setRedirectActionNamespace(String redirectActionNamespace) {
		this.redirectActionNamespace = redirectActionNamespace;
	}

	public String getRedirectActionNamespace() {
		return redirectActionNamespace;
	}

	@Override
	public String execute() throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		StringBuffer str = new StringBuffer("<script>");
		if (redirectActionName == null) {
			str.append("window.parent.location.reload();</script>");
		} 
		else {
			if(redirectActionNamespace != null){
				redirectActionNamespace += "/";
			}
			else{
				redirectActionNamespace = "";
			}
			String hrefStr = "";
			
			hrefStr = "window.parent.location.href";
			
			str.append(hrefStr + "='" + ServletActionContext.getRequest().getContextPath() + redirectActionNamespace + redirectActionName + ".action';</script>");
		}
		
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		out.print(str.toString());
		out.close();

		return super.execute();
	}
}
