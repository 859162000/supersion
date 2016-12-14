package framework.reportCheck;

import org.apache.commons.lang.xwork.StringUtils;

public class CheckFieldCaseWhenField {
	private String name;
	private String discription;
	private String joinType;
	private String whenValueRule;
	private String whenValue;
	private String whenValueScope;
	private String whenCompareType;
	private String caseCompareType;
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setWhenValueRule(String whenValueRule) {
		this.whenValueRule = whenValueRule;
	}
	public String getWhenValueRule() {
		return whenValueRule;
	}
	public void setWhenValue(String whenValue) {
		this.whenValue = whenValue;
	}
	public String getWhenValue() {
		return whenValue;
	}
	public void setWhenValueScope(String whenValueScope) {
		this.whenValueScope = whenValueScope;
	}
	public String getWhenValueScope() {
		return whenValueScope;
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
	public void setWhenCompareType(String whenCompareType) {
		this.whenCompareType = whenCompareType;
	}
	public String getWhenCompareType() {
		return whenCompareType;
	}
	public String getCaseCompareType() {
		return caseCompareType;
	}
	public void setCaseCompareType(String caseCompareType) {
		this.caseCompareType = caseCompareType;
	}
	
	
}
