package report.actions.imps;

import framework.actions.imps.BaseSAction;
import framework.interfaces.RequestManager;

@SuppressWarnings("serial")
public class ReportsSummaryAction extends BaseSAction {
	
	private Object serviceParam;
	
	public void setServiceParam(Object serviceParam) {
		this.serviceParam = serviceParam;
	}

	public Object getServiceParam() {
		return serviceParam;
	}
	
	@Override
    public String execute() throws Exception {
    	
		RequestManager.setTOject(this.getServiceParam());
		
		String ret = super.execute();
		
		return ret;
	}


}
