package framework.actions.imps;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import framework.interfaces.ApplicationManager;

public class ShowModelAlertMessage extends BaseAction{

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
		if(this.message.toUpperCase().indexOf("SCRIPT") > -1){
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
	
	@Override
	public String execute() throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		StringBuffer str = new StringBuffer("<script>alert('" + message + "');");
		if (message.indexOf("成功")<=0) {
			str.append("history.back(); </script>");
		} 
		else {
			str.append("\n window.parent.RemoveBlockadeWindowDiv('true1') \n  </script>");
		}
		
		ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
		out.print(str.toString());
		out.close();

		return super.execute();
	}
}
