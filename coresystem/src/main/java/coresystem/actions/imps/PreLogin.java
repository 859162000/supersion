package coresystem.actions.imps;

import org.apache.struts2.ServletActionContext;

import framework.actions.imps.BaseAction;

public class PreLogin extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String legalCode;	
	private String isMultiLegal;
	
	@Override
	public String execute() throws Exception {
		super.execute();
		this.setLegalCode(this.getActionName().split("-")[1]);
		String loginUrl = ServletActionContext.getRequest().getRequestURL().toString();
		ServletActionContext.getContext().getSession().put("loginUrl", loginUrl);
		isMultiLegal="1";
		return SUCCESS;
	}

	public void setLegalCode(String legalCode) {
		this.legalCode = legalCode;
	}

	public String getLegalCode() {
		return legalCode;
	}

	public void setIsMultiLegal(String isMultiLegal) {
		this.isMultiLegal = isMultiLegal;
	}

	public String getIsMultiLegal() {
		return isMultiLegal;
	}

}
