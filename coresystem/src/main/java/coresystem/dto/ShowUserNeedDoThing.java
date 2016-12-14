package coresystem.dto;

import framework.interfaces.IColumn;

public class ShowUserNeedDoThing  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@IColumn(description="名称",isListShow=true)
	private String thingName; 
	
	@IColumn(description="条数",isListShow=true)
	private Integer count;
	
	@IColumn(description="连接",isListShow=true)
	private String linkedUrl;

	public void setThingName(String thingName) {
		this.thingName = thingName;
	}

	public String getThingName() {
		return thingName;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setLinkedUrl(String linkedUrl) {
		this.linkedUrl = linkedUrl;
	}

	public String getLinkedUrl() {
		return linkedUrl;
	}
}
