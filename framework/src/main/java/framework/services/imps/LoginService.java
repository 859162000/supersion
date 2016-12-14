package framework.services.imps;


import org.apache.struts2.ServletActionContext;

import framework.interfaces.IObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

// 简单登录服务，只判断xml中配置的管理员用户
public class LoginService implements IObjectResultExecute {
	String userCode;
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		String strUserCode = RequestManager.getActionUsercode();
		String strPassword = RequestManager.getActionPassword();
		
		if(strUserCode != null && strUserCode.equals(userCode)
				&& strPassword != null && strPassword.equals(password)) {
			ServletActionContext.getContext().getSession().clear();
			return new MessageResult();
		}
		else
			return new MessageResult(false,"用户名或密码错误");
		
		
		
	}
	
}
