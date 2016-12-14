package zxptsystem.dto.condition;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class AutoDTO_GR_GRXX_JC_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(target = "JSYHKRQ", description = "开始时间",comparison=Comparison.GE)
	private String startDate;

	@ICondition(target = "JSYHKRQ", description = "结束时间",comparison=Comparison.LE)
	private String endDate;

	private String XM;
	
	private String YWH;
	
	private String YWZLXF;
	
	private String ZHZT;
	
	private String ZJHM;
	
	private String HKPL;
	
	private String RPTCheckType;
	
	private String RPTSubmitStatus;
	
	private String RPTVerifyType;
	
	private String RPTSendType;
	
	private String RPTFeedbackType;

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String rPTCheckType) {
		RPTCheckType = rPTCheckType;
	}
	
	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String rPTSubmitStatus) {
		RPTSubmitStatus = rPTSubmitStatus;
	}

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String rPTVerifyType) {
		RPTVerifyType = rPTVerifyType;
	}


	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String rPTSendType) {
		RPTSendType = rPTSendType;
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}
	
	public String getYWH() {
		return YWH;
	}

	public String getZHZT() {
		return ZHZT;
	}

	public void setZHZT(String zHZT) {
		ZHZT = zHZT;
	}

	public void setYWH(String yWH) {
		YWH = yWH;
	}

	public String getYWZLXF() {
		return YWZLXF;
	}

	public void setYWZLXF(String yWZLXF) {
		YWZLXF = yWZLXF;
	}


	public String getXM() {
		return XM;
	}

	public String getHKPL() {
		return HKPL;
	}

	public void setHKPL(String hKPL) {
		HKPL = hKPL;
	}

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
