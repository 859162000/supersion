package report.dto;

public class SimpleItemData implements java.io.Serializable {

	/**  **/
	private static final long serialVersionUID = 9155609305376237110L;
	public String StrValue;
	public String value1;
	public String CheckState;
	public String CheckResult;

	public SimpleItemData() {
		StrValue = "";
		value1 = "";
		CheckState = "";
		CheckResult = "";
	}

	public SimpleItemData(String strValue, String value1, String checkState, String checkResult) {
		this.StrValue = strValue;
		this.value1 = value1;
		this.CheckState = checkState;
		this.CheckResult = checkResult;
	}
}
