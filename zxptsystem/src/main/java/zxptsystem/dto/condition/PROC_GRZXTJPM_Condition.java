package zxptsystem.dto.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import report.dto.ReportInstInfo;

import framework.helper.TypeParse;
import framework.interfaces.ICondition;


public class PROC_GRZXTJPM_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(target = "TOTALN", description = "开始时间")
	private Date  BEGINDATE;
	
	@ICondition(target = "TOTALN", description = "结束时间")
	private Date ENDDATE;
	
	@ICondition(target = "JRJGDM", description = "报送机构")
	private ReportInstInfo JRJGDM;

	public String getBEGINDATE() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return BEGINDATE==null?null:format.format(BEGINDATE);
	}

	public void setBEGINDATE(String bEGINDATE) {
		BEGINDATE =TypeParse.parseDate(bEGINDATE);
	}

	public String getENDDATE() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return ENDDATE==null?null:format.format(ENDDATE);
	}

	public void setENDDATE(String eNDDATE) {
		ENDDATE =TypeParse.parseDate(eNDDATE);
	}

	public void setJRJGDM(ReportInstInfo jRJGDM) {
		JRJGDM = jRJGDM;
	}

	public ReportInstInfo getJRJGDM() {
		return JRJGDM;
	}
}
