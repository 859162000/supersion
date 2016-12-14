package framework.services.imps;

import java.util.List;
import java.util.Map;

import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public abstract class BaseService implements IObjectResultExecute {

	private String defaultLogicDaoBeanId;
	
	public void setDefaultLogicDaoBeanId(String defaultLogicDaoBeanId) {
		this.defaultLogicDaoBeanId = defaultLogicDaoBeanId;
	}
	
	public String getDefaultLogicDaoBeanId() {
		return defaultLogicDaoBeanId;
	}
	
	private String strProcedureName;
	
	public String getStrProcedureName() {
		return strProcedureName;
	}

	public void setStrProcedureName(String strProcedureName) {
		this.strProcedureName = strProcedureName;
	}

	private String[] strProcedureParamNames;
	
	public String[] getStrProcedureParamNames() {
		return strProcedureParamNames;
	}

	public void setStrProcedureParamNames(String[] strProcedureParamNames) {
		this.strProcedureParamNames = strProcedureParamNames;
	}
	
	private Map<String,String> defaultValueMap;
	
	public void setDefaultValueMap(Map<String,String> defaultValueMap) {
		this.defaultValueMap = defaultValueMap;
	}
	
	private Map<String,String> fieldValueMap;
	
	public void setFieldValueMap(Map<String,String> fieldValueMap) {
		this.fieldValueMap = fieldValueMap;
	}
	
	private String defaultCheckInstance;
	
	public void setDefaultCheckInstance(String defaultCheckInstance) {
		this.defaultCheckInstance = defaultCheckInstance;
	}
	
	public Map<String, String> getDefaultValueMap() {
		return defaultValueMap;
	}

	public Map<String, String> getFieldValueMap() {
		return fieldValueMap;
	}

	public String getDefaultCheckInstance() {
		return defaultCheckInstance;
	}

	public List<String> getDefaultAddClassList() {
		return defaultAddClassList;
	}

	public List<String> getDefaultTranslateClassList() {
		return defaultTranslateClassList;
	}

	public List<String> getDefaultProceseClassList() {
		return defaultProceseClassList;
	}

	private Object serviceResult;
	
	public void setServiceResult(Object serviceResult) {
		this.serviceResult = serviceResult;
	}

	public Object getServiceResult() {
		return serviceResult;
	}
	
    private List<String> defaultAddClassList;
    
    public void setDefaultAddClassList(List<String> defaultAddClassList) {
		this.defaultAddClassList = defaultAddClassList;
	}
	
	private List<String> defaultCheckClassList;
	
	public void setDefaultCheckClassList(List<String> defaultCheckClassList) {
		this.defaultCheckClassList = defaultCheckClassList;
	}
	
	public List<String> getDefaultCheckClassList(){
		return this.defaultCheckClassList;
	}
	
	private List<String> defaultTranslateClassList;
	
	public void setDefaultTranslateClassList(List<String> defaultTranslateClassList) {
		this.defaultTranslateClassList = defaultTranslateClassList;
	}
	
	private List<String> defaultProceseClassList;
	
	public void setDefaultProceseClassList(List<String> defaultProceseClassList) {
		this.defaultProceseClassList = defaultProceseClassList;
	}
	
	public void init() throws Exception{
		LogicParamManager.setDefaultLogicDaoBeanId(defaultLogicDaoBeanId);
		LogicParamManager.setDefaultValueMap(defaultValueMap);
		LogicParamManager.setFieldValueMap(fieldValueMap);
		LogicParamManager.setDefaultCheckInstance(defaultCheckInstance);
		LogicParamManager.setStrProcedureName(strProcedureName);
		LogicParamManager.setStrProcedureParamNames(strProcedureParamNames);
	}
	
	public void extendErrorResult() throws Exception{
		
	}
	
	public void initSuccessResult() throws Exception{
		
	}

	public Object objectResultExecute() throws Exception{
		
		init();
		
		MessageResult messageResult = new MessageResult();
		
		if(this.defaultAddClassList != null){
			for(String str : this.defaultAddClassList){
				IAdd add = (IAdd)FrameworkFactory.CreateClass(str);
				add.Add();
			}
		}
		
		if(this.defaultCheckClassList != null){
			for(String str : this.defaultCheckClassList){
				ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
				MessageResult currentResult = check.Check();
				if(!currentResult.isSuccess()) {
					messageResult = currentResult;
					break;
				}
			}
		}

		if(messageResult.isSuccess()){
			if(this.defaultTranslateClassList != null){
				for(String str : this.defaultTranslateClassList){
					ITranslate translate = (ITranslate)FrameworkFactory.CreateClass(str);
					translate.Translate();
				}
			}
			
			initSuccessResult(); // 调用子类覆盖的函数，需要时取出数据库数据
			
			if(this.defaultProceseClassList != null){
				for(String str : this.defaultProceseClassList){
					IProcese procese = (IProcese)FrameworkFactory.CreateClass(str);
					this.setServiceResult(procese.Procese(this.serviceResult));
				}
			}
			
			return this.serviceResult;
		}
		else{
			this.serviceResult = messageResult;
			extendErrorResult();
			return this.serviceResult;
		}
	}
}
