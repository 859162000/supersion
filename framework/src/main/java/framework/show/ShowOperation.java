package framework.show;

public class ShowOperation {
	private String operationNamespace;
	private String operationName;
	private String operationAction;
	private String operationType;
	private String width;
	private String imageUrl;
	private String color;
	
	public ShowOperation(){
		this.width = "80";
	}
	
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationAction(String operationAction) {
		this.operationAction = operationAction;
	}
	public String getOperationAction() {
		return operationAction;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationNamespace(String operationNamespace) {
		this.operationNamespace = operationNamespace;
	}
	public String getOperationNamespace() {
		return operationNamespace;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getWidth() {
		return width;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
}
