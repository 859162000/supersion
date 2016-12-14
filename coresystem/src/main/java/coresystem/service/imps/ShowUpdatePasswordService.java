package coresystem.service.imps;

import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;

public class ShowUpdatePasswordService extends BaseTShowService{
	
	@Override
	public void initSuccessResult() throws Exception {
		this.setServiceResult(Class.forName(RequestManager.getTName()).newInstance());
	}
}
