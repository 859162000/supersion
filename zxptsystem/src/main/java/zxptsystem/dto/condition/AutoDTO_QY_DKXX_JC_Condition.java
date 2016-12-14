package zxptsystem.dto.condition;

import framework.interfaces.ICondition;

public class AutoDTO_QY_DKXX_JC_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private String JRJGDM;
	private String DKYWHM;
	
	private String RPTCheckType;

	private String RPTSubmitStatus;

	private String RPTVerifyType;

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

	private String RPTSendType;
	
	private String RPTFeedbackType;
	
	private String XXJLCZLX;



	public String getJRJGDM() {
		return JRJGDM;
	}

	public void setJRJGDM(String jRJGDM) {
		JRJGDM = jRJGDM;
	}
	public String getDKYWHM() {
		return DKYWHM;
	}

	public void setDKYWHM(String dKYWHM) {
		DKYWHM = dKYWHM;
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

	public String getXXJLCZLX() {
		return XXJLCZLX;
	}

	public void setXXJLCZLX(String xXJLCZLX) {
		XXJLCZLX = xXJLCZLX;
	}


}
