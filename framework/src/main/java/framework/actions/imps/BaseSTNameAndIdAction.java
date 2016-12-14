package framework.actions.imps;

import framework.interfaces.RequestManager;

public class BaseSTNameAndIdAction extends BaseSTAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String execute() throws Exception {
		
		RequestManager.setId(this.id);
		
		return super.execute();

	}
}