package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="CheckInstanceRule")
public class CheckReVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -6697837299876451316L;
	@Column 
	private String autoCheckInstanceRuleID;
	@Column 
	private String autoCheckInstanceID;
	@Column
	private String autoCalculationRuleID;
	private CheckRuleVO checkRule;
	public String getAutoCheckInstanceRuleID() {
		return autoCheckInstanceRuleID;
	}
	public void setAutoCheckInstanceRuleID(String autoCheckInstanceRuleID) {
		this.autoCheckInstanceRuleID = autoCheckInstanceRuleID;
	}
	public String getAutoCheckInstanceID() {
		return autoCheckInstanceID;
	}
	public void setAutoCheckInstanceID(String autoCheckInstanceID) {
		this.autoCheckInstanceID = autoCheckInstanceID;
	}
	public String getAutoCalculationRuleID() {
		return autoCalculationRuleID;
	}
	public void setAutoCalculationRuleID(String autoCalculationRuleID) {
		this.autoCalculationRuleID = autoCalculationRuleID;
	}
	public CheckRuleVO getCheckRule() {
		return checkRule;
	}
	public void setCheckRule(CheckRuleVO checkRule) {
		this.checkRule = checkRule;
	}
}
