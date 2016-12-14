package framework.show;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ShowSaveOrUpdate implements Serializable{
	
	public ShowSaveOrUpdate(){
		this.setShowFieldValueList(new ArrayList<ShowFieldValue>());
		this.setShowLevelFieldValueList(new ArrayList<ShowFieldValue>());
	}
	
	public ShowSaveOrUpdate(String navigationName){
		this();
		this.navigationName = navigationName;
	}
	
	private List<ShowFieldValue> showLevelFieldValueList;
	
	private List<ShowFieldValue> showFieldValueList;
	
	private String namespace;
	
	private String tName;

	private String showUpdateListActionName;
	
	private List<ShowOperation> operationList;
	
	private String id;
	
	private String navigationName;
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public void setShowFieldValueList(List<ShowFieldValue> showFieldValueList) {
		this.showFieldValueList = showFieldValueList;
	}

	public List<ShowFieldValue> getShowFieldValueList() {
		return showFieldValueList;
	}
	public void setShowUpdateListActionName(String showUpdateListActionName) {
		this.showUpdateListActionName = showUpdateListActionName;
	}

	public String getShowUpdateListActionName() {
		return showUpdateListActionName;
	}

	public void setOperationList(List<ShowOperation> operationList) {
		this.operationList = operationList;
	}

	public List<ShowOperation> getOperationList() {
		return operationList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}

	public String getNavigationName() {
		return navigationName;
	}

	public void setShowLevelFieldValueList(List<ShowFieldValue> showLevelFieldValueList) {
		this.showLevelFieldValueList = showLevelFieldValueList;
	}

	public List<ShowFieldValue> getShowLevelFieldValueList() {
		return showLevelFieldValueList;
	}
	public String getTName()
	{
		return this.tName;
	}
	public void setTName(String tName)
	{
		this.tName=tName;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}
	
	
}
