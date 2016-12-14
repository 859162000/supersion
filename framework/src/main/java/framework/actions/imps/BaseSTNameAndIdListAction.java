package framework.actions.imps;

import framework.interfaces.RequestManager;

public class BaseSTNameAndIdListAction extends BaseSTModelDrivenConditionAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] idList;
	
	public void setIdList(String[] idList) {
		this.idList = idList;
	}
	
	@Override
	public String execute() throws Exception {
	
		RequestManager.setIdList(this.idList);
		
		return super.execute();
	}
}
