package zxptsystem.dto.condition;

import coresystem.dto.InstInfo;
import framework.interfaces.ICondition;

public class AutoDTO_JGXXJZCYXXSC_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(target="instInfo", targetField="strInstName",description="金融机构名称（行内）", isVisible=false)
	private InstInfo instInfo1;
	
	@ICondition(target="instInfo", targetField="strInstCode",description="金融机构代码（行内）", isVisible=false)
	private InstInfo instInfo2;

	@ICondition(isVisible=false)
	private String strZYGXRZJLX;

	@ICondition(order=1)
	private String strZYGXRZJHM;

	@ICondition(order=2)
	private String strJZGX;

	@ICondition(order=3)
	private String strJZCYZJHM;

	@ICondition(order=4)
	private String RPTCheckType;

	@ICondition(order=5)
	private String RPTSubmitStatus;

	@ICondition(order=6)
	private String RPTSendType;

	@ICondition(order=7)
	private String RPTFeedbackType;

	@ICondition(isVisible=false)
	private String strJZCYZJLX;

	@ICondition(isVisible=false)
	private String strXXGXRQ;

	@ICondition(isVisible=false)
	private String RPTVerifyType;

	public String getStrJZGX() {
		return strJZGX;
	}

	public void setStrJZGX(String strJZGX) {
		this.strJZGX = strJZGX;
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

	public String getStrZYGXRZJLX() {
		return strZYGXRZJLX;
	}

	public void setStrZYGXRZJLX(String strZYGXRZJLX) {
		this.strZYGXRZJLX = strZYGXRZJLX;
	}

	public String getStrZYGXRZJHM() {
		return strZYGXRZJHM;
	}

	public void setStrZYGXRZJHM(String strZYGXRZJHM) {
		this.strZYGXRZJHM = strZYGXRZJHM;
	}

	public String getStrJZCYZJLX() {
		return strJZCYZJLX;
	}

	public void setStrJZCYZJLX(String strJZCYZJLX) {
		this.strJZCYZJLX = strJZCYZJLX;
	}

	public String getStrJZCYZJHM() {
		return strJZCYZJHM;
	}

	public void setStrJZCYZJHM(String strJZCYZJHM) {
		this.strJZCYZJHM = strJZCYZJHM;
	}

	public String getStrXXGXRQ() {
		return strXXGXRQ;
	}

	public void setStrXXGXRQ(String strXXGXRQ) {
		this.strXXGXRQ = strXXGXRQ;
	}
	
	
}
