package report.dto.condition;

import extend.dto.ReportModel_Field;
import framework.interfaces.ICondition;

public class DetailedFieldInfo_Condition implements java.io.Serializable {

	/**  **/
	private static final long serialVersionUID = 7552260556457932318L;

	private ReportModel_Field autoRelationFieldID;

	private String strConditionType;

	private String strConditionJoinType;

	@ICondition(order = 1)
	private Integer intOrder;

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}

	public ReportModel_Field getAutoRelationFieldID() {
		return autoRelationFieldID;
	}

	public void setAutoRelationFieldID(ReportModel_Field autoRelationFieldID) {
		this.autoRelationFieldID = autoRelationFieldID;
	}

	public String getStrConditionType() {
		return strConditionType;
	}

	public void setStrConditionType(String strConditionType) {
		this.strConditionType = strConditionType;
	}

	public String getStrConditionJoinType() {
		return strConditionJoinType;
	}

	public void setStrConditionJoinType(String strConditionJoinType) {
		this.strConditionJoinType = strConditionJoinType;
	}

}
