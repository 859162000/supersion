package framework.services.imps;

import framework.services.interfaces.LogicParamManager;

public class SingleObjectDeleteService extends BaseVoidDaoResultService{

	private String[] dependTableList;
	 
    public String[] getDependTableList() {
		return this.dependTableList;
	}

	public void setDependTableList(String[] depList) {
		this.dependTableList = depList;
		LogicParamManager.setDependTableList(depList);
	}
	
}
