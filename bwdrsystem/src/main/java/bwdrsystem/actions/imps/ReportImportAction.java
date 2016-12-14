package bwdrsystem.actions.imps;

import framework.actions.imps.BaseSTModelDrivenConditionFileAction;
import framework.interfaces.RequestManager;

public class ReportImportAction extends BaseSTModelDrivenConditionFileAction {

	private static final long serialVersionUID = -7199344359916119524L;
	
	private String id;
	
	@Override
	public String execute() throws Exception {
		RequestManager.setId(this.id);
		return super.execute();
	}



	public final void setId(String id) {
		this.id = id;
	}
}
