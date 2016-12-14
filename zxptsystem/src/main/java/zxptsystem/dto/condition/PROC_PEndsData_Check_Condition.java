package zxptsystem.dto.condition;

import report.dto.ReportInstInfo;

public class PROC_PEndsData_Check_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private ReportInstInfo p_REPORTINST;
	
	private String p_ENDMONTH;

	public ReportInstInfo getP_REPORTINST() {
		return p_REPORTINST;
	}

	public void setP_REPORTINST(ReportInstInfo pREPORTINST) {
		p_REPORTINST = pREPORTINST;
	}

	public String getP_ENDMONTH() {
		return p_ENDMONTH;
	}

	public void setP_ENDMONTH(String pENDMONTH) {
		p_ENDMONTH = pENDMONTH;
	}
	
}
