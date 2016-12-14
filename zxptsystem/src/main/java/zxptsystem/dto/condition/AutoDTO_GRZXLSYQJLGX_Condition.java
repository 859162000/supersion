package zxptsystem.dto.condition;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class AutoDTO_GRZXLSYQJLGX_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(target="YQYF",description="起始逾期月份",comparison=Comparison.GE)
	private String startYQYF;
	
	@ICondition(target="YQYF",description="结束逾期月份",comparison=Comparison.LE)
	private String endYQYF;
	
	private String XM;
	
	private String YWH;
	
	private String CZLX;
	
	private String RPTCheckType;
	
	private String RPTSubmitStatus;
	
	private String RPTVerifyType;
	
	private String RPTSendType;
	
	private String RPTFeedbackType;


	public String getStartYQYF() {
		return startYQYF;
	}

	public void setStartYQYF(String startYQYF) {
		this.startYQYF = startYQYF;
	}

	public String getEndYQYF() {
		return endYQYF;
	}

	public void setEndYQYF(String endYQYF) {
		this.endYQYF = endYQYF;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getYWH() {
		return YWH;
	}

	public void setYWH(String yWH) {
		YWH = yWH;
	}

	public String getCZLX() {
		return CZLX;
	}

	public void setCZLX(String cZLX) {
		CZLX = cZLX;
	}

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

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String rPTVerifyType) {
		RPTVerifyType = rPTVerifyType;
	}
	
	
	
}
