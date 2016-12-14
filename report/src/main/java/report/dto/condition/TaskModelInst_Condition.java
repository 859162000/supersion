package report.dto.condition;



public class TaskModelInst_Condition implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4961017072331983023L;

    /* 保持与征信一致
	@ICondition(target="reportModel_Table", targetField="strTableName", order=1, isASC=true, isVisible=true)
	private ReportModel_Table reportModel_Table;

	
	@ICondition(target="reportModel_Table", targetField="strChinaName")
	private ReportModel_Table reportModel_Table1;
	
	@ICondition(order=2,isASC=true,isVisible=true)
	private String strCheckState;
	
	private String SubmitStatus;
	
	private String strAllowState;
	*/
	private String RPTSendType;
	
	private String RPTFeedbackType;
	/*
	public void setReportModel_Table(ReportModel_Table reportModel_Table) {
		this.reportModel_Table = reportModel_Table;
	}

	public ReportModel_Table getReportModel_Table() {
		return reportModel_Table;
	}

	public void setReportModel_Table1(ReportModel_Table reportModel_Table1) {
		this.reportModel_Table1 = reportModel_Table1;
	}

	public ReportModel_Table getReportModel_Table1() {
		return reportModel_Table1;
	}

	public void setStrAllowState(String strAllowState) {
		this.strAllowState = strAllowState;
	}

	public String getStrAllowState() {
		return strAllowState;
	}

	public void setStrCheckState(String strCheckState) {
		this.strCheckState = strCheckState;
	}

	public String getStrCheckState() {
		return strCheckState;
	}

	public void setSubmitStatus(String submitStatus) {
		SubmitStatus = submitStatus;
	}

	public String getSubmitStatus() {
		return SubmitStatus;
	}
    */
	public void setRPTSendType(String rPTSendType) {
		RPTSendType = rPTSendType;
	}

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	
}
