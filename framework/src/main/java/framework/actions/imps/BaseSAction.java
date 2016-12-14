package framework.actions.imps;

import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

// 需要Service协作的action,负责service的创建、执行和返回
public class BaseSAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String defaultServiceBeanId;
	
	public void setDefaultServiceBeanId(String defaultServiceBeanId) {
		this.defaultServiceBeanId = defaultServiceBeanId;
	}
	
	protected String getDefaultServiceBeanId() {
		return defaultServiceBeanId;
	}

	private IObjectResultExecute baseService;
	
    protected IObjectResultExecute getBaseService() {
		return baseService;
	}

    protected void setBaseService(IObjectResultExecute baseService) {
		this.baseService = baseService;
	}

	private Object serviceResult;
	
	public Object getServiceResult() {
		return serviceResult;
	}
	
	protected void setServiceResult(Object serviceResult) {
		this.serviceResult = serviceResult;
	}
	
    protected void initAction(){
		
	}

	protected void initService() throws Exception{
		if(this.baseService == null){
			this.baseService = (IObjectResultExecute)FrameworkFactory.CreateBean(this.defaultServiceBeanId);
		    if(baseService == null){
		    	this.baseService = (IObjectResultExecute)FrameworkFactory.CreateClass(this.defaultServiceBeanId);
		    }
		}
	}
	
	protected boolean isSuccessResult(){
		boolean isSuccessResult = true;
		if(this.serviceResult != null){
        	if(serviceResult.getClass().getName().equals(MessageResult.class.getName())){
    			MessageResult messageResult = (MessageResult)serviceResult;
    			if(!messageResult.isSuccess()){
    				isSuccessResult = false;
    			}
    		}
        }
		return isSuccessResult;
	}

	@Override
    public String execute() throws Exception {
		
		String result = super.execute();
		if(!result.equals(SUCCESS)){
			return result;
		}
		
		initAction();
		
		initService();
		
		if(this.baseService != null){
 			this.serviceResult = this.baseService.objectResultExecute();
			if(!this.isSuccessResult()){
				result = ERROR;
			}
			LogicParamManager.setServiceResult(this.serviceResult);
		}

        return result;
	}
}
