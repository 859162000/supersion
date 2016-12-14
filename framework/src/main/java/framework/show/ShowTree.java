package framework.show;

import java.util.ArrayList;
import java.util.List;

// 返回给ShowTree.jsp页面用于显示的对象
public class ShowTree {

	private List<ShowTreeNode> treeValue;
	
	private String rootURL;

    private String namespace;

    private String selectActionName;

	public ShowTree(){
		this.setTreeValue(new ArrayList<ShowTreeNode>());
	}

	public void setSelectActionName(String selectActionName) {
		this.selectActionName = selectActionName;
	}

	public String getSelectActionName() {
		return selectActionName;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setTreeValue(List<ShowTreeNode> treeValue) {
		this.treeValue = treeValue;
	}

	public List<ShowTreeNode> getTreeValue() {
		return treeValue;
	}

	public void setRootURL(String rootURL) {
		this.rootURL = rootURL;
	}

	public String getRootURL() {
		return rootURL;
	}
}
