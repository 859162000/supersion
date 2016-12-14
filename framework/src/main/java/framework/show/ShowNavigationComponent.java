package framework.show;

import java.util.ArrayList;
import java.util.List;

public class ShowNavigationComponent implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String url;
	private String ico;
	private String des;
	private String treeIco;
	private String treeExpandIco;
	private String parentId;
	private int level;
	
	private List<ShowNavigationComponent> showNavigationComponentList;
	
	public ShowNavigationComponent(){
		showNavigationComponentList = new ArrayList<ShowNavigationComponent>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public void setShowNavigationComponentList(
			List<ShowNavigationComponent> showNavigationComponentList) {
		this.showNavigationComponentList = showNavigationComponentList;
	}
	public List<ShowNavigationComponent> getShowNavigationComponentList() {
		return showNavigationComponentList;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setTreeIco(String treeIco) {
		this.treeIco = treeIco;
	}

	public String getTreeIco() {
		return treeIco;
	}

	public void setTreeExpandIco(String treeExpandIco) {
		this.treeExpandIco = treeExpandIco;
	}

	public String getTreeExpandIco() {
		return treeExpandIco;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
