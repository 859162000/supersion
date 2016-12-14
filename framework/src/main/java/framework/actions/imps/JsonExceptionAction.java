package framework.actions.imps;

import framework.services.interfaces.MessageResult;

public class JsonExceptionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 782386464785952803L;
   
	public Object getServiceResult() {
		return  new MessageResult(false,"系统出错啦");
	}
	
	
	

}
