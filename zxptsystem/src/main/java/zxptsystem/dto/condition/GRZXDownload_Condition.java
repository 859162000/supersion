package zxptsystem.dto.condition;

import framework.interfaces.ICondition;

public class GRZXDownload_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String strGrReportType;

	@ICondition(order = 1, isVisible = false)
	private String strGrReportCode;

	private String strGrJRJGCode;
	
	private String strGRSJFSNY;
	
	public void setStrGRSJFSNY(String strGRSJFSNY) {
		this.strGRSJFSNY = strGRSJFSNY;
	}

	public String getStrGRSJFSNY() {
		return strGRSJFSNY;
	}

	public void setStrGrReportCode(String strGrReportCode) {
		this.strGrReportCode = strGrReportCode;
	}

	public String getStrGrReportCode() {
		return strGrReportCode;
	}

	public void setStrGrJRJGCode(String strGrJRJGCode) {
		this.strGrJRJGCode = strGrJRJGCode;
	}

	public String getStrGrJRJGCode() {
		return strGrJRJGCode;
	}

	public String getStrGrReportType() {
		return strGrReportType;
	}

	public void setStrGrReportType(String strGrReportType) {
		this.strGrReportType = strGrReportType;
	}

}
