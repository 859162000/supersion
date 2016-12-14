package framework.services.imps;

import framework.reportCheck.CheckContext;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class InitXMLConfiguration extends BaseTShowService{

	@Override
	public Object objectResultExecute() throws Exception {
		
		super.init();
		
		CheckContext.Init();
		SecurityContext.Init();
		ShowContext.Init();
		
		return new MessageResult(this.getSuccessMessage());
	}
}
