package coresystem.service.imps;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.IObjectResultExecute;
import framework.services.interfaces.MessageResult;

public class LogoutService implements IObjectResultExecute {

	public Object objectResultExecute() throws Exception {
		String loginUrl="/CoreSystem/Login.jsp";
		if(null!=ServletActionContext.getContext().getSession().get("loginUrl")){
			if(!ServletActionContext.getContext().getSession().get("loginUrl").toString().equals("")){
				loginUrl = ServletActionContext.getContext().getSession().get("loginUrl").toString();
			}			
		}

		ServletActionContext.getRequest().getSession().invalidate();
		MessageResult messageResult = new MessageResult();
		messageResult.setMessage(loginUrl);
		return messageResult;
	}

}
