package framework.actions.imps;

import framework.interfaces.RequestManager;

// 需要页面传回数据的action,实现getModel提供DTO对象供struts设置表单数据
public class LoginAction extends BaseSTNameAndIdAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String strUserCode;
    
    public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}
    
	private String strPassword;

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

    @Override
    public String execute() throws Exception {

		RequestManager.setActionUsercode(strUserCode);
		RequestManager.setActionPassword(strPassword);
		
		String ret = super.execute();
		
		return ret;
	}

	
	
}
