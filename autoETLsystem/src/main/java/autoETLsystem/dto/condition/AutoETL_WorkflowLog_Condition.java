package autoETLsystem.dto.condition;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;
import autoETLsystem.dto.AutoETL_Workflow;

public class AutoETL_WorkflowLog_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(target="dtDate",description="开始时间",comparison=Comparison.GE)
	private String startDate;
	
	@ICondition(target="dtDate",description="结束时间",comparison=Comparison.LE)
	private String endDate;
	
	private String strOperationType;
	
	private String strParam;
	
	private AutoETL_Workflow autoETL_Workflow;
	@ICondition(order=1,isASC=false,isVisible=false)
	private String dtDate;
	
	public AutoETL_Workflow getAutoETL_Workflow() {
		return autoETL_Workflow;
	}
	public void setAutoETL_Workflow(AutoETL_Workflow autoETLWorkflow) {
		autoETL_Workflow = autoETLWorkflow;
	}
	public String getDtDate() {
		return dtDate;
	}
	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}
	public String getStrOperationType() {
		return strOperationType;
	}
	public void setStrParam(String strParam) {
		this.strParam = strParam;
	}
	public String getStrParam() {
		return strParam;
	}
}
