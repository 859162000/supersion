package zxptsystem.dto.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class VIEW_GR_LOANDATA_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String JRJGDM;
	
	private String YWH;
	
	private String XM;
	
	private String ZJHM;
	
	@ICondition(description = "起始结算、应还款日期",target = "JSYHKRQ",comparison=Comparison.GE)
	private Date startDate;
	
	@ICondition(description = "终止结算、应还款日期",target = "JSYHKRQ",comparison=Comparison.LE)
	private Date endDate;

	public String getJRJGDM() {
		return JRJGDM;
	}

	public void setJRJGDM(String jRJGDM) {
		JRJGDM = jRJGDM;
	}

	public String getYWH() {
		return YWH;
	}

	public void setYWH(String yWH) {
		YWH = yWH;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}

	public void setStartDate(String startDate) {
		this.startDate = TypeParse.parseDate(startDate);
	}

	public String getStartDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return startDate==null?null:format.format(startDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = TypeParse.parseDate(endDate);;
	}

	public String getEndDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return endDate==null?null:format.format(endDate);
	}
}
