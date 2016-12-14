package framework.actions.imps;

import framework.interfaces.RequestManager;

public class BaseSTModelDrivenValidateAction extends BaseSTModelDrivenAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
    public String execute() throws Exception {
		
		String result = super.execute();
		
		if(!this.isSuccessResult()){
			RequestManager.setInputName(INPUT);
			result = INPUT;
		}
        
        return result;
	}
}
