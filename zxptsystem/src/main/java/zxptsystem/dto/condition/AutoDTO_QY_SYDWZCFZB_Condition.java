package zxptsystem.dto.condition;

import framework.interfaces.ICondition;

public class AutoDTO_QY_SYDWZCFZB_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(description="业务发生日期")
	private String extend2;	

	private String RPTCheckType;	

	private String RPTSendType;
	
	private String RPTFeedbackType;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String rPTCheckType) {
		RPTCheckType = rPTCheckType;
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
