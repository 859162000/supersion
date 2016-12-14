package zxptsystem.dto.condition;

import coresystem.dto.InstInfo;
import framework.interfaces.ICondition;

public class AutoDTO_GRZXSJSC_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(target="instInfo", targetField="strInstName",description="金融机构名称（行内）", isVisible=false)
	private InstInfo instInfo1;
	
	@ICondition(target="instInfo", targetField="strInstCode",description="金融机构代码（行内）", isVisible=false)
	private InstInfo instInfo2;

	@ICondition(order=1)
	private String strJRJGCode;

	@ICondition(order=2)
	private String strYWCode;

	@ICondition(order=3)
	private String QSJSYHKRQ;

	@ICondition(order=4)
	private String JSJSYHKRQ;

	@ICondition(order=5)
	private String RPTCheckType;

	@ICondition(order=6)
	private String RPTSubmitStatus;

	@ICondition(isVisible=false)
	private String RPTVerifyType;

	@ICondition(order=7)
	private String RPTSendType;

	@ICondition(order=8)
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

	public InstInfo getInstInfo1() {
		return instInfo1;
	}

	public void setInstInfo1(InstInfo instInfo1) {
		this.instInfo1 = instInfo1;
	}

	public InstInfo getInstInfo2() {
		return instInfo2;
	}

	public void setInstInfo2(InstInfo instInfo2) {
		this.instInfo2 = instInfo2;
	}

	public String getStrJRJGCode() {
		return strJRJGCode;
	}

	public void setStrJRJGCode(String strJRJGCode) {
		this.strJRJGCode = strJRJGCode;
	}

	public String getStrYWCode() {
		return strYWCode;
	}

	public void setStrYWCode(String strYWCode) {
		this.strYWCode = strYWCode;
	}

	public String getQSJSYHKRQ() {
		return QSJSYHKRQ;
	}

	public void setQSJSYHKRQ(String qSJSYHKRQ) {
		QSJSYHKRQ = qSJSYHKRQ;
	}

	public String getJSJSYHKRQ() {
		return JSJSYHKRQ;
	}

	public void setJSJSYHKRQ(String jSJSYHKRQ) {
		JSJSYHKRQ = jSJSYHKRQ;
	}
	
	
}
