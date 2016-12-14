package zxptsystem.dto.condition;

import framework.interfaces.ICondition;

public class OverRideGRZXDownload_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String strGrReportType;

	@ICondition(order = 1, isVisible = false)
	private String strGrReportCode;

	private String strGrJRJGCode;
	
	private String strGRSJFSNY;
	
	private String beginGRSJFSNY;
	
	private String endGRSJFSNY;
	
	public String getBeginGRSJFSNY() {
		return beginGRSJFSNY;
	}

	public void setBeginGRSJFSNY(String beginGRSJFSNY) {
		this.beginGRSJFSNY = beginGRSJFSNY;
	}

	public String getEndGRSJFSNY() {
		return endGRSJFSNY;
	}

	public void setEndGRSJFSNY(String endGRSJFSNY) {
		this.endGRSJFSNY = endGRSJFSNY;
	}

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
