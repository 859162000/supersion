package zxptsystem.dto.condition;

import coresystem.dto.InstInfo;
import framework.interfaces.ICondition;

public class AutoDTO_QYZXPLXDYWSJSC_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(target="instInfo", targetField="strInstName",description="金融机构名称（行内）", isVisible=false)
	private InstInfo instInfo1;

	@ICondition(target="instInfo", targetField="strInstCode",description="金融机构代码（行内）", isVisible=false)
	private InstInfo instInfo2;

	@ICondition(order=1)
	private String strJRJGCode;
	
	@ICondition(order=2)
	private String strDeleteBusiType;

	@ICondition(order=3)
	private String strDKKCode;

	@ICondition(order=4)
	private String strZHTBH;

	@ICondition(order=5)
	private String RPTCheckType;

	@ICondition(order=6)
	private String RPTSubmitStatus;

	@ICondition(order=7)
	private String RPTSendType;

	@ICondition(order=8)
	private String RPTFeedbackType;

	@ICondition(isVisible=false)
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

	public String getStrDKKCode() {
		return strDKKCode;
	}

	public void setStrDKKCode(String strDKKCode) {
		this.strDKKCode = strDKKCode;
	}

	public String getStrDeleteBusiType() {
		return strDeleteBusiType;
	}

	public void setStrDeleteBusiType(String strDeleteBusiType) {
		this.strDeleteBusiType = strDeleteBusiType;
	}

	public String getStrZHTBH() {
		return strZHTBH;
	}

	public void setStrZHTBH(String strZHTBH) {
		this.strZHTBH = strZHTBH;
	}
	
	
}
