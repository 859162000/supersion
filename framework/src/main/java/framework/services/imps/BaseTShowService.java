package framework.services.imps;

import java.util.Map;

import framework.interfaces.RequestManager;
import framework.services.interfaces.ShowParamManager;

public class BaseTShowService extends BaseService{
	
	private String showInstanceName;
	
	private Map<String,String> operationMap;

	public Map<String, String> getOperationMap() {
		return operationMap;
	}

	public void setShowInstanceName(String showInstanceName) {
		this.showInstanceName = showInstanceName;
	}

	public void setOperationMap(Map<String,String> operationMap) {
		this.operationMap = operationMap;
	}
	
    private String successMessage;
	
    public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}
	
	private String errorMessage;
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
    private Map<String,String> levelFieldMap;
	
	public void setLevelFieldMap(Map<String,String> levelFieldMap) {
		this.levelFieldMap = levelFieldMap;
	} 
	
	private boolean showLevelObject;

	@Override
    public void init() throws Exception{
		super.init();
		ShowParamManager.setShowInstanceName(this.showInstanceName);
		ShowParamManager.setOperationMap(operationMap);
		ShowParamManager.setLevelFieldMap(levelFieldMap);
		ShowParamManager.setIsShowLevelObject(showLevelObject);
	}

	@Override
	public void initSuccessResult() throws Exception {
		this.setServiceResult(RequestManager.getTOject());
	}

	public void setShowLevelObject(boolean showLevelObject) {
		this.showLevelObject = showLevelObject;
	}

	public boolean isShowLevelObject() {
		return showLevelObject;
	}
}
