package zxptsystem.dto.condition;

import framework.interfaces.ICondition;

public class QYZXDownload_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String strReportType;
	
	@ICondition(order=1,isVisible=false)
	private String strReportCode;
	
	private String YWFSRQ;
	
	public void setStrReportCode(String strReportCode) {
		this.strReportCode = strReportCode;
	}

	public String getStrReportCode() {
		return strReportCode;
	}
	
	public void setStrJRJGCode(String strJRJGCode) {
		this.strJRJGCode = strJRJGCode;
	}

	public String getStrJRJGCode() {
		return strJRJGCode;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public String getStrReportType() {
		return strReportType;
	}

	public void setYWFSRQ(String yWFSRQ) {
		YWFSRQ = yWFSRQ;
	}

	public String getYWFSRQ() {
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//return YWFSRQ==null?null:format.format(YWFSRQ);
		return YWFSRQ;
	}

	private String strJRJGCode;
}
