package zxptsystem.dto.condition;

public class AutoDTO_QY_HKXX_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String JJBH;

	private String RPTSendType;
	
	private String RPTFeedbackType;

	public String getJJBH() {
		return JJBH;
	}

	public void setJJBH(String jJBH) {
		JJBH = jJBH;
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

}
