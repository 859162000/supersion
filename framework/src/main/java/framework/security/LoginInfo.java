package framework.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import framework.show.ShowContext;
import framework.show.ShowNavigation;

public class LoginInfo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginInfo(){
		functionSet = new HashSet<Function>();
		dataSecuritySet = new HashSet<DataSecurity>();
	}

	private List<ShowNavigation> showNavigationList;
	
    private Set<Function> functionSet;
    
    private Set<DataSecurity> dataSecuritySet;
    
    private Object tag;
    
    private Object legalInfo;
    
    private String sessionFactory;
    
    private boolean isAdministrator;

    
	public void setFunctionSet(Set<Function> functionSet) {
		this.functionSet = functionSet;
	}

	public Set<Function> getFunctionSet() {
		return functionSet;
	}
	
	public void setDataSecuritySet(Set<DataSecurity> dataSecuritySet) {
		this.dataSecuritySet = dataSecuritySet;
	}

	public Set<DataSecurity> getDataSecuritySet() {
		return dataSecuritySet;
	}
	
	public void setTag(Object tag) {
		this.tag = tag;
	}

	public Object getTag() {
		return tag;
	}
	
	private Set<String> actionNameSet;

	public Set<String> getActionNameSet() {
		if(actionNameSet == null){
			actionNameSet = new HashSet<String>();
			for(Function function : functionSet){
				for(String actionName : function.getActionNameSet()){
					actionNameSet.add(actionName);
				}
			}
		}
		return actionNameSet;
	}

	public List<ShowNavigation> getShowNavigationList() {
		if(showNavigationList == null){
			showNavigationList = new ArrayList<ShowNavigation>();
			for(ShowNavigation showNavigation : ShowContext.getInstance().getShowNavigationList()){
				if(getActionNameSet().contains(showNavigation.getNavigationUrl())){
					showNavigationList.add(showNavigation);
				}
			}
		}
		
		return showNavigationList;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setLegalInfo(Object legalInfo) {
		this.legalInfo = legalInfo;
	}

	public Object getLegalInfo() {
		return legalInfo;
	}

	public void setSessionFactory(String sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getSessionFactory() {
		return sessionFactory;
	}	
}
