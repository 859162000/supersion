package coresystem.actions.imps;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import framework.actions.imps.BaseSTModelDrivenAction;
import framework.services.interfaces.MessageResult;

public class LoginAction extends BaseSTModelDrivenAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String execute() throws Exception {
		String loginUrl="";
		if(null!=ServletActionContext.getContext().getSession().get("loginUrl")){
			loginUrl = ServletActionContext.getContext().getSession().get("loginUrl").toString();
		}
		
		ServletActionContext.getContext().getSession().clear();
		String referer = ServletActionContext.getRequest().getHeader("Referer");
		ServletActionContext.getContext().getSession().put("loginUrl", loginUrl);
		if(null!=referer && !referer.equals("")){
			ServletActionContext.getContext().getSession().put("loginUrl", referer);
		}
		String result = super.execute();
		
		MessageResult messageResult = (MessageResult)this.getServiceResult();
		if(messageResult.isSuccess() && !StringUtils.isBlank(messageResult.getMessage())){
			result = "successAlert";
		}
		if(!messageResult.isSuccess()){
			ActionContext.getContext().getSession().put("firstLogin", false);
		}
		else{
			ActionContext.getContext().getSession().put("firstLogin", true);
		}
		return result;
	}
}
