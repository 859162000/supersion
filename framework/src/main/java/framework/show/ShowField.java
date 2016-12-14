package framework.show;

public class ShowField {

	private boolean listVisible;
	private boolean updateReadOnly;
    private String singleTag;
	private String fieldName;
    private String fieldShowName;
    private String fieldTargetPrimaryKey;
    private String fieldTargetPrimaryKeyName;
    private String paramName;
    private Integer width;
    private Integer height;
    private boolean errorSpace;
    private boolean titleField;
    private boolean imageField;
	private boolean splitField;
    private int intOrder;
    private boolean thousandth;
    private int reportUnitCode;/*reportUnitCode 1:元、 2：十元、 3：百元、 4：千元、 5：万元、 6 ：十万元、 7：百万元、8：千万元  9、亿元*/
    private boolean encrypt;/* 加密  */
    private int autoFill; //自动补全小数位数
    private boolean multipleSelect; // 查询条件的下拉框转换成下拉多选框
    private String fieldShowNamePrefix; // 在新增、修改界面字段前面的标识符号 
    
    public ShowField(String fieldName,String paramName,String fieldShowName,String singleTag){
    	this.fieldName = fieldName;
    	this.paramName = paramName;
    	this.fieldShowName = fieldShowName;
    	this.singleTag = singleTag;
    }

    public ShowField(){
    	
    }
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldShowName(String fieldShowName) {
		this.fieldShowName = fieldShowName;
	}
	public String getFieldShowName() {
		return fieldShowName;
	}

	public void setFieldTargetPrimaryKey(String fieldTargetPrimaryKey) {
		this.fieldTargetPrimaryKey = fieldTargetPrimaryKey;
	}
	public String getFieldTargetPrimaryKey() {
		return fieldTargetPrimaryKey;
	}
	public void setFieldTargetPrimaryKeyName(String fieldTargetPrimaryKeyName) {
		this.fieldTargetPrimaryKeyName = fieldTargetPrimaryKeyName;
	}
	public String getFieldTargetPrimaryKeyName() {
		return fieldTargetPrimaryKeyName;
	}
	
	public ShowField Copy() {
		ShowField showField = new ShowField();
		showField.setListVisible(this.listVisible);
		showField.setUpdateReadOnly(this.updateReadOnly);
		showField.setFieldName(this.fieldName);
		showField.setFieldShowName(this.fieldShowName);
		showField.setFieldTargetPrimaryKey(this.fieldTargetPrimaryKey);
		showField.setFieldTargetPrimaryKeyName(this.fieldTargetPrimaryKeyName);
		showField.setParamName(this.paramName);
		showField.setSingleTag(this.singleTag);
		showField.setWidth(this.width);
		showField.setHeight(this.height);
		showField.setErrorSpace(this.errorSpace);
		showField.setTitleField(this.titleField);
		showField.setImageField(this.imageField);
		showField.setSplitField(this.splitField);
		showField.setIntOrder(this.intOrder);
		showField.setThousandth(thousandth);
		showField.setReportUnitCode(this.reportUnitCode);
		showField.setEncrypt(encrypt);
		showField.setAutoFill(autoFill);
		showField.setMultipleSelect(this.multipleSelect);
		showField.setFieldShowNamePrefix(this.fieldShowNamePrefix);
		
		return showField;
	}

	public void setListVisible(boolean listVisible) {
		this.listVisible = listVisible;
	}
	public boolean isListVisible() {
		return listVisible;
	}
	public void setUpdateReadOnly(boolean updateReadOnly) {
		this.updateReadOnly = updateReadOnly;
	}
	public boolean isUpdateReadOnly() {
		return updateReadOnly;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamName() {
		return paramName;
	}
	public void setSingleTag(String singleTag) {
		this.singleTag = singleTag;
	}
	public String getSingleTag() {
		return singleTag;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getWidth() {
		return width;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getHeight() {
		return height;
	}
	public void setErrorSpace(boolean errorSpace) {
		this.errorSpace = errorSpace;
	}
	public boolean isErrorSpace() {
		return errorSpace;
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
	public void setIntOrder(int intOrder) {
		this.intOrder = intOrder;
	}
	public int getIntOrder() {
		return intOrder;
	}
   public boolean isThousandth() {
		return thousandth;
	}
	public void setThousandth(boolean thousandth) {
		this.thousandth = thousandth;
	}
	public void setReportUnitCode(int reportUnitCode) {
		this.reportUnitCode = reportUnitCode;
	}
	public int getReportUnitCode() {
		return reportUnitCode;
	}

	public boolean isEncrypt() {
		return encrypt;
	}
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
	public void setAutoFill(int autoFill) {
		this.autoFill = autoFill;
	}
	public int getAutoFill() {
		return autoFill;
	}
	public boolean isMultipleSelect() {
		return multipleSelect;
	}
	public void setMultipleSelect(boolean multipleSelect) {
		this.multipleSelect = multipleSelect;
	}
	public String getFieldShowNamePrefix() {
		return fieldShowNamePrefix;
	}
	public void setFieldShowNamePrefix(String fieldShowNamePrefix) {
		this.fieldShowNamePrefix = fieldShowNamePrefix;
	}
	
}
