package framework.show;

public class ShowHeaderName {
	private String showName;
	private Integer width;
	
    private String postFieldName;
    
    private String singleTag;
    private Object tag;
    private boolean readOnly;
	
	public ShowHeaderName(){
		this.width = 80;
	}
	
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getShowName() {
		return showName;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getWidth() {
		return width;
	}

	public void setPostFieldName(String postFieldName) {
		this.postFieldName = postFieldName;
	}

	public String getPostFieldName() {
		return postFieldName;
	}

	public void setSingleTag(String singleTag) {
		this.singleTag = singleTag;
	}

	public String getSingleTag() {
		return singleTag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public Object getTag() {
		return tag;
	}
	public boolean getReadOnly()
	{
		return this.readOnly;
	}
	public void setReadOnly(boolean readOnly)
	{
		this.readOnly=readOnly;
	}
}
