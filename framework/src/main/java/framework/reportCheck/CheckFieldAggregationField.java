package framework.reportCheck;

import org.apache.commons.lang.xwork.StringUtils;

public class CheckFieldAggregationField {
	
	private String discription;
	private String joinType;
	private String value;
	
	private String procedureName;
	private String procedureParam;
	private String procedureDiscription;
	private String procedureResultField;
	private String procedureSplitField;
	
	
	public String getProcedureSplitField() {
		return procedureSplitField;
	}
	public void setProcedureSplitField(String procedureSplitField) {
		this.procedureSplitField = procedureSplitField;
	}
	
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}
	public String getProcedureParam() {
		return procedureParam;
	}
	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}
	public String getProcedureDiscription() {
		if(StringUtils.isBlank(procedureDiscription)){
			return this.procedureName;
		}
		return procedureDiscription;
	}
	public void setProcedureResultField(String procedureResultField) {
		this.procedureResultField = procedureResultField;
	}
	public String getProcedureResultField() {
		return procedureResultField;
	}
	
}
