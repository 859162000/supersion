package coresystem.actions.imps;

import com.opensymphony.xwork2.ActionSupport;

// 设置命名空间和actionName到request对象
public class NotNeedPasswordAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userid;

	public String execute() throws Exception {
		return "success";
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
