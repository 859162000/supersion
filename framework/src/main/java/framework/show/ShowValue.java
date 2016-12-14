package framework.show;

public class ShowValue {
	
	private String value;
	private Integer width;
	
    private boolean titleField;
    private boolean imageField;
    private boolean splitField;
    
    private String singleTag;
    private Object tag;
	private String key;
	private String showColor;
    
    private String postFieldName;
    
    public void setShowColor(String showColor)
    {
    	this.showColor=showColor;
    }
    public String getShowColor()
    {
    	return this.showColor;
    }
    
	
	public ShowValue(){
		this.width = 80;
	}
	
	public ShowValue(String value){
		this.width = 80;
		this.value = value;
	}
	
	public ShowValue(String value,Integer width,boolean titleField,boolean imageField,boolean splitField){
		if(width != null){
			this.width = width;
		}
		else{
			this.width = 80;
		}
		this.value = value;
		this.titleField = titleField;
		this.imageField = imageField;
		this.splitField = splitField;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getWidth() {
		return width;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setTitleField(boolean titleField) {
		this.titleField = titleField;
	}

	public boolean isTitleField() {
		return titleField;
	}

	public void setImageField(boolean imageField) {
		this.imageField = imageField;
	}

	public boolean isImageField() {
		return imageField;
	}

	public void setSplitField(boolean splitField) {
		this.splitField = splitField;
	}

	public boolean isSplitField() {
		return splitField;
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

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
