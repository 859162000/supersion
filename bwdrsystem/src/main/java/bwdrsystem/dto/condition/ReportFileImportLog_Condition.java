package bwdrsystem.dto.condition;

import java.util.Date;

import coresystem.dto.UserInfo;
import framework.interfaces.ICondition;

public class ReportFileImportLog_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@ICondition(description="报文数据日期", order=1)
	private Date dtDate;

	@ICondition(description="报文导入日期", order=2)
	private Date importDate;
	
	@ICondition(description="报文导入人员", order=3)
	private UserInfo operator;
	
	@ICondition(description="报文名称", order=4)
	private String reportName;
	
	@ICondition(description="报文类型", order=5)
	private String reportType;
	
	@ICondition(description="报送机构", order=6)
	private String strReportInstCode;

	
	
	
	
	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public UserInfo getOperator() {
		return operator;
	}

	public void setOperator(UserInfo operator) {
		this.operator = operator;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getStrReportInstCode() {
		return strReportInstCode;
	}

	public void setStrReportInstCode(String strReportInstCode) {
		this.strReportInstCode = strReportInstCode;
	}




}
