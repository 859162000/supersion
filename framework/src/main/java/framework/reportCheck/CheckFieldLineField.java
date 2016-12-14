package framework.reportCheck;

import org.apache.commons.lang.xwork.StringUtils;

public class CheckFieldLineField {
	private String name;
	private String discription;
	private String joinType;
	private String value;
	private String valueRule;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getDiscription() {
		if(StringUtils.isBlank(discription)){
			return this.name;
		}
		return discription;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValueRule(String valueRule) {
		this.valueRule = valueRule;
	}
	public String getValueRule() {
		return valueRule;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getJoinType() {
		return joinType;
	}

}
