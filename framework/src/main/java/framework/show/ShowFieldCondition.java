package framework.show;

import framework.interfaces.ICondition.Comparison;

public class ShowFieldCondition {
	
	private String fieldName;
	private String target;
	private String targetField;
    private boolean visible;
    private int order;
    private boolean ASC;
    private Comparison comparison;
    
    private String fieldShowName;
    private String paramName;
    private Object tag;
    private String singleTag;
    private boolean multipleSelect;
    
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

	public void setTag(Object tag) {
		this.tag = tag;
	}
	public Object getTag() {
		return tag;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamName() {
		return paramName;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTarget() {
		return target;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isVisible() {
		return visible;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	public int getOrder() {
		return order;
	}
	public void setASC(boolean aSC) {
		ASC = aSC;
	}
	public boolean isASC() {
		return ASC;
	}
	public void setComparison(Comparison comparison) {
		this.comparison = comparison;
	}
	public Comparison getComparison() {
		return comparison;
	}
	public void setSingleTag(String singleTag) {
		this.singleTag = singleTag;
	}
	public String getSingleTag() {
		return singleTag;
	}
	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}
	public String getTargetField() {
		return targetField;
	}
	public boolean isMultipleSelect() {
		return multipleSelect;
	}
	public void setMultipleSelect(boolean multipleSelect) {
		this.multipleSelect = multipleSelect;
	}
	
}
