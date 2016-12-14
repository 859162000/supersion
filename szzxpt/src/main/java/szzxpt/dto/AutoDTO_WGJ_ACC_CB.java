package szzxpt.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


import extend.helper.HelpTool;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "WGJ_ACC_CB")
@IEntity(description= "账户收支余信息")
public class AutoDTO_WGJ_ACC_CB implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "extend5", length = 250, nullable = true)
	@IColumn(description="扩展字段5")
	private String extend5;

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String in) {
		extend5 = in;
	}

	@Column(name = "extend4", length = 250, nullable = true)
	@IColumn(description="扩展字段4")
	private String extend4;

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String in) {
		extend4 = in;
	}

	@Column(name = "extend3", length = 250, nullable = true)
	@IColumn(description="扩展字段3")
	private String extend3;

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String in) {
		extend3 = in;
	}

	@Column(name = "extend2", length = 250, nullable = true)
	@IColumn(description="扩展字段2")
	private String extend2;

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String in) {
		extend2 = in;
	}

	@Column(name = "extend1", length = 250, nullable = true)
	@IColumn(description="扩展字段1")
	private String extend1;

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String in) {
		extend1 = in;
	}

	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden=true)
	@Column(name = "RPTFeedbackType", nullable =true)
	private String RPTFeedbackType;

	public static Map<String,String> getRPTFeedbackTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String in) {
		RPTFeedbackType = in;
	}

	@IColumn(tagMethodName="getRPTSubmitStatusTag",description="提交状态")
	@Column(name = "RPTSubmitStatus", nullable =true)
	private String RPTSubmitStatus;

	public static Map<String,String> getRPTSubmitStatusTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSubmitStatus");
	}

	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String in) {
		RPTSubmitStatus = in;
	}

	@IColumn(tagMethodName="getRPTVerifyTypeTag",description="审核状态")
	@Column(name = "RPTVerifyType", nullable =true)
	private String RPTVerifyType;

	public static Map<String,String> getRPTVerifyTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTVerifyType");
	}

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String in) {
		RPTVerifyType = in;
	}

	@Column(name = "lastUpdateDate", nullable = true)
	@IColumn(description="最后修改时间", isNullable = false)
	private Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String in) {
		lastUpdateDate = TypeParse.parseTimestamp(in);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operationUser")
	private coresystem.dto.UserInfo operationUser;

	public coresystem.dto.UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(coresystem.dto.UserInfo in) {
		operationUser = in;
	}

	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态")
	@Column(name = "RPTSendType", nullable =true)
	private String RPTSendType;

	public static Map<String,String> getRPTSendTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSendType");
	}

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}

	@IColumn(tagMethodName="getRPTCheckTypeTag",description="校验状态")
	@Column(name = "RPTCheckType", nullable =true)
	private String RPTCheckType;

	public static Map<String,String> getRPTCheckTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTCheckType");
	}

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instInfo")
	private coresystem.dto.InstInfo instInfo;

	public coresystem.dto.InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(coresystem.dto.InstInfo in) {
		instInfo = in;
	}

	@Column(name = "dtDate", nullable = true)
	@IColumn(description="数据时间", isNullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	@Column(name = "XZXH", length = 100, nullable = true)
	@IColumn(description="下载序号",isSingleTagHidden=true)
	private String XZXH;

	public String getXZXH() {
		return XZXH;
	}

	public void setXZXH(String in) {
		XZXH = in;
	}
	
	@IColumn(tagMethodName="getACTIONTYPETag",description="操作类型")
	@Column(name = "ACTIONTYPE", nullable =true)
	private String ACTIONTYPE;

	public static Map<String,String> getACTIONTYPETag() {
		try {
			return HelpTool.getSystemConstVal("CZLX");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getACTIONTYPE() {
		return ACTIONTYPE;
	}

	public void setACTIONTYPE(String in) {
		ACTIONTYPE = in;
	}

	@Column(name = "ACTIONDESC", length = 250, nullable = true)
	@IColumn(description="删除原因")
	private String ACTIONDESC;

	public String getACTIONDESC() {
		return ACTIONDESC;
	}

	public void setACTIONDESC(String in) {
		ACTIONDESC = in;
	}

	@Column(name = "accountNO", length = 100, nullable = true)
	@IColumn(description="账号")
	private String accountNO;

	public String getAccountNO() {
		return accountNO;
	}

	public void setAccountNO(String in) {
		accountNO = in;
	}

	@Column(name = "BRANCH_CODE", length = 50, nullable = true)
	@IColumn(description="金融机构标识码")
	private String BRANCH_CODE;

	public String getBRANCH_CODE() {
		return BRANCH_CODE;
	}

	public void setBRANCH_CODE(String in) {
		BRANCH_CODE = in;
	}

	@Column(name = "deal_date", length = 50, nullable = true)
	@IColumn(description="发生日期")
	private String deal_date;

	public String getDeal_date() {
		return deal_date;
	}

	public void setDeal_date(String in) {
		deal_date = in;
	}

	@IColumn(tagMethodName="getCURRENCY_codeTag",description="币种")
	@Column(name = "CURRENCY_code", nullable =true)
	private String CURRENCY_code;

	public static Map<String,String> getCURRENCY_codeTag() {
		try {
			return HelpTool.getSystemConstVal("WHZH_BZ");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getCURRENCY_code() {
		return CURRENCY_code;
	}

	public void setCURRENCY_code(String in) {
		CURRENCY_code = in;
	}

	@Column(name = "credit", length = 50, nullable = true)
	@IColumn(description="当日贷方发生额")
	private String credit;

	public String getCredit() {
		return credit;
	}

	public void setCredit(String in) {
		credit = in;
	}

	@Column(name = "debit", length = 50, nullable = true)
	@IColumn(description="当日借方发生额")
	private String debit;

	public String getDebit() {
		return debit;
	}

	public void setDebit(String in) {
		debit = in;
	}

	@Column(name = "balance", length = 50, nullable = true)
	@IColumn(description="账户余额")
	private String balance;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String in) {
		balance = in;
	}
	
	@Column(name = "REMARK", length = 500, nullable = true)
	@IColumn(description="备注")
	private String REMARK;

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String in) {
		REMARK = in;
	}

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

}

