package framework.interfaces;

public class ActionJumpResult {
	private String windowId;
	private String actionName;
	private String actionNamespace;
	private String idValue;
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionNamespace(String actionNamespace) {
		this.actionNamespace = actionNamespace;
	}
	public String getActionNamespace() {
		return actionNamespace;
	}
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}
	public String getIdValue() {
		return idValue;
	}
	public void setWindowId(String windowId) {
		this.windowId = windowId;
	}
	public String getWindowId() {
		return windowId;
	}
	
}
