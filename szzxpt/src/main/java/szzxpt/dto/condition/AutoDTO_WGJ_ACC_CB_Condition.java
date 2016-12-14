package szzxpt.dto.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class AutoDTO_WGJ_ACC_CB_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String RPTFeedbackType;

	private String RPTSubmitStatus;

	private String RPTVerifyType;

	private String RPTSendType;

	private String RPTCheckType;
	
	private String ACTIONTYPE;
	
	private String accountNO;
	
	private String CURRENCY_code;
	
	@ICondition(target="deal_date",description="开始发生日期",comparison=Comparison.GE)
	private Date startDate;
	
	@ICondition(target="deal_date",description="结束发生日期",comparison=Comparison.LE)
	private Date endDate;

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
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

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String rPTCheckType) {
		RPTCheckType = rPTCheckType;
	}

	public String getACTIONTYPE() {
		return ACTIONTYPE;
	}

	public void setACTIONTYPE(String aCTIONTYPE) {
		ACTIONTYPE = aCTIONTYPE;
	}

	public String getAccountNO() {
		return accountNO;
	}

	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
	}

	public String getCURRENCY_code() {
		return CURRENCY_code;
	}

	public void setCURRENCY_code(String cURRENCYCode) {
		CURRENCY_code = cURRENCYCode;
	}

	public String getStartDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return startDate==null?null:format.format(startDate);
	}

	public void setStartDate(String startDate) {
		this.startDate =TypeParse.parseDate(startDate);
	}

	public String getEndDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return endDate==null?null:format.format(endDate);
	}

	public void setEndDate(String endDate) {
		this.endDate = TypeParse.parseDate(endDate);
	}

}

