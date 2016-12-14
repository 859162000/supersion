package framework.services.imps;

import java.util.Map;

import framework.helper.FrameworkFactory;
import framework.interfaces.RequestManager;

public abstract class BaseDTService extends BaseDService{

    private BaseTShowService baseTShowService;
	
	public BaseDTService(){
		baseTShowService = new BaseTShowService();
	}

	public void setShowInstanceName(String showInstanceName) {
		this.baseTShowService.setShowInstanceName(showInstanceName);
	}
	
	public void setOperationMap(Map<String,String> operationMap) {
		this.baseTShowService.setOperationMap(operationMap);
	}
	public void getOperationMap() {
		this.baseTShowService.getOperationMap();
	}
	
	public void setDefaultLogicDaoBeanId(String defaultLogicDaoBeanId) {
		this.baseTShowService.setDefaultLogicDaoBeanId(defaultLogicDaoBeanId);
	}
	
	public void setDefaultValueMap(Map<String,String> defaultValueMap) {
		this.baseTShowService.setDefaultValueMap(defaultValueMap);
	}
	
	public void setFieldValueMap(Map<String,String> fieldValueMap) {
		this.baseTShowService.setFieldValueMap(fieldValueMap);
	}
	
	public void setShowLevelObject(boolean showLevelObject) {
		this.baseTShowService.setShowLevelObject(showLevelObject);
	}
	
	public void setDefaultCheckInstance(String defaultCheckInstance) {
		this.baseTShowService.setDefaultCheckInstance(defaultCheckInstance);
	}
	
	public void setLevelFieldMap(Map<String,String> levelFieldMap) {
		this.baseTShowService.setLevelFieldMap(levelFieldMap);
	}
	
	public void setStrProcedureName(String strProcedureName) {
		this.baseTShowService.setStrProcedureName(strProcedureName);
	}

	public void setStrProcedureParamNames(String[] strProcedureParamNames) {
		this.baseTShowService.setStrProcedureParamNames(strProcedureParamNames);
	}
	
	private Map<String,String> daoClassMap;
	
	public void setDaoClassMap(Map<String,String> daoClassMap) {
		this.daoClassMap = daoClassMap;
	}
	
	public void setSuccessMessage(String successMessage) {
		this.baseTShowService.setSuccessMessage(successMessage);
	}

	public String getSuccessMessage() {
		return this.baseTShowService.getSuccessMessage();
	}
	
	public void setErrorMessage(String errorMessage) {
		this.baseTShowService.setErrorMessage(errorMessage);
	}

	public String getErrorMessage() {
		return this.baseTShowService.getErrorMessage();
	}

	@Override
    public void init() throws Exception{
		baseTShowService.init();
	}
	
	@Override
	protected void initDao() throws Exception{
		
		if(this.getBaseDaoObject() == null){
			if(this.daoClassMap != null){
				if(this.daoClassMap.containsKey(RequestManager.getTName())){
					this.setBaseDaoObject(FrameworkFactory.CreateClass(RequestManager.getTName()));
				}
			}
		}
		super.initDao();
	}
}
