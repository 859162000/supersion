package framework.show;

public class ShowTreeNode {
	private String objKey;
	private String treeNodeID;
	private String parentID;
	private String showName;
	private String URL;
	private String normalImageURL;
	private String expandImageURL;
	
	public void setObjKey(String objKey) {
		this.objKey = objKey;
	}
	public String getObjKey() {
		return objKey;
	}
	public void setTreeNodeID(String treeID) {
		this.treeNodeID = treeID;
	}
	public String getTreeNodeID() {
		return treeNodeID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getParentID() {
		return parentID;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getShowName() {
		return showName;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getURL() {
		return URL;
	}
	public void setNormalImageURL(String normalImageURL) {
		this.normalImageURL = normalImageURL;
	}
	public String getNormalImageURL() {
		return normalImageURL;
	}
	public void setExpandImageURL(String expandImageURL) {
		this.expandImageURL = expandImageURL;
	}
	public String getExpandImageURL() {
		return expandImageURL;
	}
}
