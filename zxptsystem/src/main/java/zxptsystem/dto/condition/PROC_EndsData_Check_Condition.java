package zxptsystem.dto.condition;

import report.dto.ReportInstInfo;
import framework.interfaces.ICondition;

public class PROC_EndsData_Check_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(description="报送机构")
	private ReportInstInfo DJJGDM;
	
	private String SJJZRQ;

	public ReportInstInfo getDJJGDM() {
		return DJJGDM;
	}

	public void setDJJGDM(ReportInstInfo dJJGDM) {
		DJJGDM = dJJGDM;
	}

	public String getSJJZRQ() {
		return SJJZRQ;
	}

	public void setSJJZRQ(String sJJZRQ) {
		SJJZRQ = sJJZRQ;
	}
}
