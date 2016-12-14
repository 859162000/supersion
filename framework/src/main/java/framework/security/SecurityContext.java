package framework.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import framework.helper.DomXMLOperation;
import framework.helper.ReflectOperation;
import framework.interfaces.SessionManager;

public class SecurityContext {
	
	private static SecurityContext securityContext = null; 
	
	private Map<String,String> functionMap;
	
	private String sysUserCode;
	private String sysUserInitPWD;

	public Map<String,String> getFunctionMap() {
		return functionMap;
	}
	
	private List<Function> functionList;
	
	private Set<Function> functionSet;

	public Set<Function> getFunctionSet() {
		return functionSet;
	}
	
    private Map<String, Map<String,String>> dataSecurityMap;
	
	public Map<String, Map<String,String>> getDataSecurityMap() {
		return dataSecurityMap;
	}
	
	private Map<String, Map<String,String>> dataSecurityShowMap;
	
	public Map<String, Map<String,String>> getDataSecurityShowMap() {
		return dataSecurityShowMap;
	}
	
	public static void Init() throws Exception{  
		securityContext = null;
    }
	
	private SecurityContext() throws Exception { 
		if(securityLocation != null){
        	initSecurity();
	    }
		if(dataSecurityLocation != null){
        	initDataSecurity();
	    }
    }  
  
	private void initSecurity(){
		functionMap = new LinkedHashMap<String,String>();
        functionSet = new HashSet<Function>();
        functionList = new ArrayList<Function>();
        
    	for(int f=0;f<securityLocation.length;f++){ 
    		if(securityLocation[f].equals("")) continue;
    		
    		Element securityRootElement = DomXMLOperation.getElementByName(securityLocation[f],"securityRoot");
           	Element[] functionElements = DomXMLOperation.getElementsByName(securityRootElement,"function");
        	for(int i=0;i<functionElements.length;i++){
        		Function function = new Function();
        		
           		Element functionCodeElement = DomXMLOperation.getElementByName(functionElements[i],"functionCode");
           		Element functionNameElement = DomXMLOperation.getElementByName(functionElements[i],"functionName");
           		Element functionParentIdElement = DomXMLOperation.getElementByName(functionElements[i],"functionParentId");
           		Element[] actionNamesElement = DomXMLOperation.getElementsByName(functionElements[i],"actionName");
           		
           		function.setFunctionCode(DomXMLOperation.getElementValue(functionCodeElement));
           		function.setFunctionName(DomXMLOperation.getElementValue(functionNameElement));
           		function.setFunctionParentId(DomXMLOperation.getElementValue(functionParentIdElement));

           		for(int j=0;j<actionNamesElement.length;j++){
           			function.getActionNameSet().add(DomXMLOperation.getElementValue(actionNamesElement[j]));
           		}
           		
           		functionList.add(function);
           		functionSet.add(function);
           		functionMap.put(function.getFunctionCode(), function.getFunctionName());
        	}
        	
        	// 解析配置的系统用户信息
        	Element systemUser = DomXMLOperation.getElementByName(securityRootElement,"systemuser");
        	Element systemUserCode = DomXMLOperation.getElementByName(systemUser,"usercode");
        	if(systemUserCode != null)
        		setSysUserCode(DomXMLOperation.getElementValue(systemUserCode));
        	Element initPwd = DomXMLOperation.getElementByName(systemUser,"initpassword");
        	if(initPwd != null)
        		setSysUserInitPWD(DomXMLOperation.getElementValue(initPwd));
    	}
	}
	
	private void initDataSecurity() throws Exception{
		dataSecurityMap = new LinkedHashMap<String, Map<String,String>>();
		dataSecurityShowMap = new LinkedHashMap<String, Map<String,String>>();
		for(int f=0;f<dataSecurityLocation.length;f++){
			if(dataSecurityLocation[f].equals("")) continue;
			
    		Element dataSecurityRootElement = DomXMLOperation.getElementByName(dataSecurityLocation[f],"dataSecurityRoot");
           	Element[] dataSecurityMapElements = DomXMLOperation.getElementsByName(dataSecurityRootElement,"dataSecurityMap");
        	for(int i=0;i<dataSecurityMapElements.length;i++){
        		
        		Element dataSecurityNameElement = DomXMLOperation.getElementByName(dataSecurityMapElements[i],"dataSecurityName");
        		Element[] dataSecurityElements = DomXMLOperation.getElementsByName(dataSecurityMapElements[i],"dataSecurity");
        		Map<String,String> dataSecuritys = new LinkedHashMap<String,String>();
        		Map<String,String> dataSecurityShows = new LinkedHashMap<String,String>();
        		for(int j=0;j<dataSecurityElements.length;j++){
        			Element classNameElement = DomXMLOperation.getElementByName(dataSecurityElements[j],"className");
               		Element fieldNameElement = DomXMLOperation.getElementByName(dataSecurityElements[j],"fieldName");
               		dataSecuritys.put(DomXMLOperation.getElementValue(classNameElement), DomXMLOperation.getElementValue(fieldNameElement));
               		dataSecurityShows.put(DomXMLOperation.getElementValue(classNameElement), ReflectOperation.getDefaultShowInstance(DomXMLOperation.getElementValue(classNameElement)).getShowEntityName());
        		}
        		
        		if(dataSecurityMap.get(DomXMLOperation.getElementValue(dataSecurityNameElement)) != null){
        			Map<String, String> tempMap = dataSecurityMap.get(DomXMLOperation.getElementValue(dataSecurityNameElement));
        			for(Map.Entry<String, String> entry : tempMap.entrySet()){
        				dataSecuritys.put(entry.getKey(), entry.getValue());
        			}
        		}
        		dataSecurityMap.put(DomXMLOperation.getElementValue(dataSecurityNameElement), dataSecuritys);
        		
        		if(dataSecurityShowMap.get(DomXMLOperation.getElementValue(dataSecurityNameElement)) != null){
        			Map<String, String> tempMap = dataSecurityShowMap.get(DomXMLOperation.getElementValue(dataSecurityNameElement));
        			for(Map.Entry<String, String> entry : tempMap.entrySet()){
        				dataSecurityShows.put(entry.getKey(), entry.getValue());
        			}
        		}
        		dataSecurityShowMap.put(DomXMLOperation.getElementValue(dataSecurityNameElement), dataSecurityShows);
        	}
    	}
	}
	
    synchronized public static SecurityContext getInstance() throws Exception{  
        if (securityContext == null)  {
        	securityContext = new SecurityContext();  
        }
        return securityContext;  
    }  
    
    public void setFunctionCodeSet(Set<String> functionCodeSet){
    	Set<Function> maxFunctionSet = new HashSet<Function>();
		for(String loginFunctionCode : functionCodeSet){
			for(Function maxFunction : functionSet){
				if(maxFunction.getFunctionCode().equals(loginFunctionCode)){
					maxFunctionSet.add(maxFunction);
				}
			}
		}
    	
    	LoginInfo loginInfo = getLoginInfo();
    	if(loginInfo == null){
    		loginInfo = new LoginInfo();
    	}
    	loginInfo.setFunctionSet(maxFunctionSet);
    	SessionManager.setLoginInfo(loginInfo);
    }
    
    public void addFunctionCodeSet(String functionCode){
    	LoginInfo loginInfo = getLoginInfo();
    	if(loginInfo == null){
    		loginInfo = new LoginInfo();
    	}
    	for(Function maxFunction : functionSet){
			if(maxFunction.getFunctionCode().equals(functionCode)){
				loginInfo.getFunctionSet().add(maxFunction);
			}
		}
    	SessionManager.setLoginInfo(loginInfo);
    }
   
    public void addDataSecurity(String className,String fieldName,String data){
    	LoginInfo loginInfo = getLoginInfo();
    	if(loginInfo == null){
    		loginInfo = new LoginInfo();
    	}
    	boolean isAdded = false;
    	DataSecurity addDs=null;
    	try {
			
    	for(DataSecurity tempDataSecurity : loginInfo.getDataSecuritySet()){
    		if(tempDataSecurity.getClassName().equals(className) && (tempDataSecurity.getFieldName()!=null && tempDataSecurity.getFieldName().equals(fieldName))){
    			addDs=tempDataSecurity;
    			//tempDataSecurity.getDataSet().add(data);
    			isAdded = true;
    			break;
    		}
    	}
    	if(isAdded&& addDs!=null)
    	{
    		addDs.getDataSet().add(data);
    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(!isAdded){
        	DataSecurity dataSecurity = new DataSecurity();
        	dataSecurity.setClassName(className);
        	dataSecurity.setFieldName(fieldName);
        	dataSecurity.getDataSet().add(data);
        	loginInfo.getDataSecuritySet().add(dataSecurity);
    	}
    	SessionManager.setLoginInfo(loginInfo);
    }
    
    public void addDataSecurity(String dataSecurityName,String data) throws Exception{
    	for(Map.Entry<String, String> entry : SecurityContext.getInstance().getDataSecurityMap().get(dataSecurityName).entrySet()){
    		addDataSecurity(entry.getKey(),entry.getValue(),data);
    	}
    }
    
    public void initLoginInfo() {
		SessionManager.setLoginInfo(null);
	}
    
	public LoginInfo getLoginInfo() {
		return SessionManager.getLoginInfo();
	}

	private static String[] securityLocation;
	
	public static void setSecurityLocation(String[] securityLocation) {
		SecurityContext.securityLocation = securityLocation;
	}
	
	private static String[] dataSecurityLocation;
	
	public static void setDataSecurityLocation(String[] dataSecurityLocation) {
		SecurityContext.dataSecurityLocation = dataSecurityLocation;
	}

	public void setSysUserCode(String sysUserCode) {
		this.sysUserCode = sysUserCode;
	}

	public String getSysUserCode() {
		return sysUserCode;
	}

	public void setSysUserInitPWD(String sysUserInitPWD) {
		this.sysUserInitPWD = sysUserInitPWD;
	}

	public String getSysUserInitPWD() {
		return sysUserInitPWD;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}
}
