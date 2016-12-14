package zxptsystem.dto.condition;

import framework.interfaces.ICondition;

public class JGXXDownload_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String strJgReportType;

	@ICondition(order = 1, isVisible = false)
	private String strJgReportCode;

	private String strJgJRJGCode;
	
	private String XXGXRQ;

	public void setStrJgReportCode(String strJgReportCode) {
		this.strJgReportCode = strJgReportCode;
	}

	public String getStrJgReportCode() {
		return strJgReportCode;
	}

	public void setStrJgJRJGCode(String strJgJRJGCode) {
		this.strJgJRJGCode = strJgJRJGCode;
	}

	public String getStrJgJRJGCode() {
		return strJgJRJGCode;
	}

	public String getStrJgReportType() {
		return strJgReportType;
	}

	public void setStrJgReportType(String strJgReportType) {
		this.strJgReportType = strJgReportType;
	}

	public void setXXGXRQ(String xXGXRQ) {
		XXGXRQ = xXGXRQ;
	}

	public String getXXGXRQ() {
		return XXGXRQ;
	}

	/*public void setXXGXRQ(String xXGXRQ) {
		XXGXRQ = TypeParse.parseDate(xXGXRQ);
	}

	public String getXXGXRQ() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return XXGXRQ==null?null:format.format(XXGXRQ);
	}*/

}
