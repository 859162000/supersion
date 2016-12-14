package report.dto;

public class ItemRuleDetail implements java.io.Serializable {

	/**  **/
	private static final long serialVersionUID = -7503908756049122600L;
	private String strItemType;
	private String strItemValue;
	private String ruleID;

	public ItemRuleDetail() {
		this.strItemType = "";
		this.strItemValue = "";
		this.ruleID = "";
	}

	public ItemRuleDetail(String strItemType, String strItemValue, String ruleID) {
		this.strItemType = strItemType;
		this.strItemValue = strItemValue;
		this.ruleID = ruleID;
	}

	public String getStrItemType() {
		return this.strItemType;
	}

	public String getStrItemValue() {
		return this.strItemValue;
	}

	public String getRuleID() {
		return this.ruleID;
	}

}
