package framework.reportCheck;

public class CheckFieldCondition {
	private String conditionType;
	private String conditionSql;
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionSql(String conditionSql) {
		this.conditionSql = conditionSql;
	}
	public String getConditionSql() {
		return conditionSql;
	}
}
