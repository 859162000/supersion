package framework.actions.imps;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import framework.interfaces.ApplicationManager;

public class ShowMessageAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public void setMessage(String message) throws UnsupportedEncodingException {
		if(ApplicationManager.getCharsetTypeValue() == null){
			this.message = new String(message.getBytes("ISO8859-1"),"UTF-8");
		}
		else if(ApplicationManager.getCharsetTypeValue().equals("1")){
			this.message = message;
		}
		if(this.message.toUpperCase().indexOf("<SCRIPT>") > -1){
			this.message = "invalid script message";
		}
	}

	public String getMessage() {
		return message;
	}
	
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
	
	private boolean refreshParent;
	
	public void setRefreshParent(boolean refreshParent) {
		this.refreshParent = refreshParent;
	}
	
	private boolean showConfirm;

	@Override
	public String execute() throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		
		String startMessage = "";
		//特殊字符处理：
		message=message.replace("'", "\\'");
		if(showConfirm){
			startMessage = "<script>var conRes = confirm('" + message + "');";
		}
		else{
			startMessage = "<script>var conRes = true; alert('" + message + "');";
		}
		
		StringBuffer str = new StringBuffer(startMessage);
		if (redirectActionName == null) {
			str.append("history.back();</script>");
		} 
		else {
			if(redirectActionNamespace != null){
				redirectActionNamespace += "/";
			}
			else{
				redirectActionNamespace = "";
			}
			String hrefStr = "";
			
			hrefStr = "if(conRes == false) {history.back();} else{";
			
			if(refreshParent){
				hrefStr += "window.parent.location.href";
			}
			else{
				hrefStr += "window.location.href";
			}
			
			if(redirectActionName.indexOf(".action") == -1){
				redirectActionName += ".action";
			}
			
			str.append(hrefStr + "='" + ServletActionContext.getRequest().getContextPath() + redirectActionNamespace + redirectActionName + "';}</script>");
		}
		
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		out.print(str.toString());
		out.close();

		return super.execute();
	}

	public void setShowConfirm(boolean showConfirm) {
		this.showConfirm = showConfirm;
	}

	public boolean isShowConfirm() {
		return showConfirm;
	}

}
