package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="ItemsCheck")
public class CheckRuleInfoVO implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 5141053655821694175L;
	@Column
	private String autoItemsCalculationID;
	@Column
	private String strItemType;
	@Column
	private String autoCalculationRuleID;
	@Column
	private Integer intOrder;
	@Column
	private String strItemValue;
	
	public String getAutoItemsCalculationID() {
		return autoItemsCalculationID;
	}
	public void setAutoItemsCalculationID(String autoItemsCalculationID) {
		this.autoItemsCalculationID = autoItemsCalculationID;
	}
	public String getStrItemType() {
		return strItemType;
	}
	public void setStrItemType(String strItemType) {
		this.strItemType = strItemType;
	}
	public String getAutoCalculationRuleID() {
		return autoCalculationRuleID;
	}
	public void setAutoCalculationRuleID(String autoCalculationRuleID) {
		this.autoCalculationRuleID = autoCalculationRuleID;
	}
	public Integer getIntOrder() {
		return intOrder;
	}
	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}
	public String getStrItemValue() {
		return strItemValue;
	}
	public void setStrItemValue(String strItemValue) {
		this.strItemValue = strItemValue;
	}
	
}
