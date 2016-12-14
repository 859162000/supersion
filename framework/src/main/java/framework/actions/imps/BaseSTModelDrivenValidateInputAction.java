package framework.actions.imps;

import framework.helper.ExceptionLog;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

public class BaseSTModelDrivenValidateInputAction extends BaseSTModelDrivenAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void validate(){
		if(RequestManager.getInputName() != null){
			MessageResult messageResult = (MessageResult)LogicParamManager.getServiceResult();
			
			for(String errorMessage : messageResult.getMessageSet()){
				this.addActionError(errorMessage);
			}
			
			for(ErrorField errorField : messageResult.getErrorFieldList()){
				this.addFieldError(errorField.getFieldName(), errorField.getMessage());
			}

			try {
				this.setBaseService(null);
				this.initService();
				if(this.getBaseService() != null){
					this.setServiceResult(this.getBaseService().objectResultExecute());
				}
			} 
			catch (Exception ex) {
				ExceptionLog.CreateLog(ex);
			}
		}
	}
	
	/*
	public String getInputName(){
		if(RequestManager.getInputName() != null){
			return RequestManager.getInputName();
		}
		else{
			return INPUT;
		}
	}

	@InputConfig(methodName="getInputName") 
	@Override
    public String execute() throws Exception {
		return super.execute();
	}
	*/
}
