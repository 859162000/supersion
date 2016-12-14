package zxptsystem.dto.condition;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class AutoDTO_GRZXLSYQJLSC_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(comparison=Comparison.GE)
	private String QSYQYF;
	
	@ICondition(comparison=Comparison.LE)
	private String JSYQYF;
	
	private String XM;
	
	private String YWH;
	
	private String RPTCheckType;
	
	private String RPTSubmitStatus;
	
	private String RPTVerifyType;
	
	private String RPTSendType;
	
	private String RPTFeedbackType;


	public String getQSYQYF() {
		return QSYQYF;
	}

	public void setQSYQYF(String qSYQYF) {
		QSYQYF = qSYQYF;
	}

	public String getJSYQYF() {
		return JSYQYF;
	}

	public void setJSYQYF(String jSYQYF) {
		JSYQYF = jSYQYF;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
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

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String rPTVerifyType) {
		RPTVerifyType = rPTVerifyType;
	}
	
	

}
