package framework.security;

import java.util.HashSet;
import java.util.Set;

public class Function implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Function(){
		this.actionNameSet = new HashSet<String>();
	}
	
    private String functionCode;
    private String functionName;
    private String functionParentId;
    
    private Set<String> actionNameSet;
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setActionNameSet(Set<String> actionNameSet) {
		this.actionNameSet = actionNameSet;
	}
	public Set<String> getActionNameSet() {
		return actionNameSet;
	}
	public void setFunctionParentId(String functionParentId) {
		this.functionParentId = functionParentId;
	}
	public String getFunctionParentId() {
		return functionParentId;
	}
}
