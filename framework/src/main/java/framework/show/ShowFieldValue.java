package framework.show;

public class ShowFieldValue {
	
    private	ShowField showField;
    private Object fieldValue;
    private Object tag;
    private boolean readOnly;
    private boolean selectChange;
    private String checkMessage;
    private String showColor;
    
    public ShowFieldValue(){
    	
    }
    public ShowFieldValue(ShowField showField,Object fieldValue,Object tag){
    	this.showField = showField;
    	this.fieldValue = fieldValue;
    	this.tag = tag;
    }

	public void setShowField(ShowField showField) {
		this.showField = showField;
	}
	public ShowField getShowField() {
		return showField;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}
	public Object getTag() {
		return tag;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setSelectChange(boolean selectChange) {
		this.selectChange = selectChange;
	}
	public boolean isSelectChange() {
		return selectChange;
	}
	public void setCheckMessage(String checkMessage) {
		this.checkMessage = checkMessage;
	}
	public String getCheckMessage() {
		return checkMessage;
	}
	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}
	public String getShowColor() {
		return showColor;
	}
}
