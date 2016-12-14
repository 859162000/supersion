package zxptsystem.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name="GR_D43")
@IEntity(description="信贷交易信息明细-贷记卡-已销户贷记卡")
public class GR_D43 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8339051601267260426L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="CardDate", length=20,nullable=true)
	@IColumn(description="发卡日期",isNullable=true)
	private String CardDate;

	@Column(name="CardInstName", length=200,nullable=true)
	@IColumn(description="发卡机构名称",isNullable=true)
	private String CardInstName;

	@Column(name="Currency", length=50,nullable=true)
	@IColumn(description="币种",isNullable=true)
	private String Currency;

	@Column(name="CreditMoney", length=255,nullable=true)
	@IColumn(description="授信额度描述",isNullable=true)
	private String CreditMoney;

	@Column(name="ShareAmount", length=50,nullable=true)
	@IColumn(description="共享额度",isNullable=true)
	private String ShareAmount;
	
	@Column(name="BusiNoDesc", length=255,nullable=true)
	@IColumn(description="业务号描述",isNullable=true)
	private String BusiNoDesc;

	@Column(name="GuarWayDesc", length=255,nullable=true)
	@IColumn(description="担保方式描述",isNullable=true)
	private String GuarWayDesc;

	@Column(name="StateClosMonth", length=20,nullable=true)
	@IColumn(description="状态截止月",isNullable=true)
	private String StateClosMonth;

	@Column(name="AccountCancel", length=10,nullable=true)
	@IColumn(description="销户",isNullable=true)
	private String AccountCancel;

	@Column(name="OverdueBeginDate", length=50,nullable=true)
	@IColumn(description="逾期起始月",isNullable=true)
	private String OverdueBeginDate;

	@Column(name="OverdueEndDate", length=50,nullable=true)
	@IColumn(description="逾期截止月",isNullable=true)
	private String OverdueEndDate;

	@Column(name="OverdueMonth", length=20,nullable=true)
	@IColumn(description="逾期月份",isNullable=true)
	private String OverdueMonth;

	@Column(name="OverdueLastMonths", length=10,nullable=true)
	@IColumn(description="逾期持续月数",isNullable=true)
	private String OverdueLastMonths;

	@Column(name="OverdueMoney", length=20,nullable=true)
	@IColumn(description="逾期金额",isNullable=true)
	private String OverdueMoney;

	@Column(name="SpeTransype", length=20,nullable=true)
	@IColumn(description="特殊交易类型",isNullable=true)
	private String SpeTransype;

	@Column(name="SpeTransOccurDate", length=20,nullable=true)
	@IColumn(description="发生日期",isNullable=true)
	private String SpeTransOccurDate;

	@Column(name="SpeTransChangMonths", length=10,nullable=true)
	@IColumn(description="变更月数",isNullable=true)
	private String SpeTransChangMonths;

	@Column(name="SpeTransOccurMoney", length=20,nullable=true)
	@IColumn(description="发生金额",isNullable=true)
	private String SpeTransOccurMoney;

	@Column(name="SpeTransDetRecord", length=255,nullable=true)
	@IColumn(description="明细记录",isNullable=true)
	private String SpeTransDetRecord;

	@Column(name="CardInstExplain", length=255,nullable=true)
	@IColumn(description="发卡机构说明",isNullable=true)
	private String CardInstExplain;

	@Column(name="AddDate1", length=20,nullable=true)
	@IColumn(description="添加日期",isNullable=true)
	private String AddDate1;

	@Column(name="OneselfStatement", length=255,nullable=true)
	@IColumn(description="本人声明",isNullable=true)
	private String OneselfStatement;

	@Column(name="AddDate2", length=20,nullable=true)
	@IColumn(description="添加日期",isNullable=true)
	private String AddDate2;

	@Column(name="ObjectionLabel", length=255,nullable=true)
	@IColumn(description="异议标注",isNullable=true)
	private String ObjectionLabel;

	@Column(name="AddDate3", length=20,nullable=true)
	@IColumn(description="添加日期",isNullable=true)
	private String AddDate3;

	@Column(name="LoanNo", length=10,nullable=true)
	@IColumn(description="贷记卡序号",isNullable=true)
	private String LoanNo;

	public String getLoanNo() {
		return LoanNo;
	}

	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}
	
	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public GR_A1 getReportNo() {
		return ReportNo;
	}

	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}

	public String getCardDate() {
		return CardDate;
	}

	public void setCardDate(String cardDate) {
		CardDate = cardDate;
	}

	public String getCardInstName() {
		return CardInstName;
	}

	public void setCardInstName(String cardInstName) {
		CardInstName = cardInstName;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getCreditMoney() {
		return CreditMoney;
	}

	public void setCreditMoney(String creditMoney) {
		CreditMoney = creditMoney;
	}

	public String getShareAmount() {
		return ShareAmount;
	}

	public void setShareAmount(String shareAmount) {
		ShareAmount = shareAmount;
	}

	public String getBusiNoDesc() {
		return BusiNoDesc;
	}

	public void setBusiNoDesc(String busiNoDesc) {
		BusiNoDesc = busiNoDesc;
	}

	public String getGuarWayDesc() {
		return GuarWayDesc;
	}

	public void setGuarWayDesc(String guarWayDesc) {
		GuarWayDesc = guarWayDesc;
	}

	public String getStateClosMonth() {
		return StateClosMonth;
	}

	public void setStateClosMonth(String stateClosMonth) {
		StateClosMonth = stateClosMonth;
	}

	public String getAccountCancel() {
		return AccountCancel;
	}

	public void setAccountCancel(String accountCancel) {
		AccountCancel = accountCancel;
	}

	public String getOverdueBeginDate() {
		return OverdueBeginDate;
	}

	public void setOverdueBeginDate(String overdueBeginDate) {
		OverdueBeginDate = overdueBeginDate;
	}

	public String getOverdueEndDate() {
		return OverdueEndDate;
	}

	public void setOverdueEndDate(String overdueEndDate) {
		OverdueEndDate = overdueEndDate;
	}

	public String getOverdueMonth() {
		return OverdueMonth;
	}

	public void setOverdueMonth(String overdueMonth) {
		OverdueMonth = overdueMonth;
	}

	public String getOverdueLastMonths() {
		return OverdueLastMonths;
	}

	public void setOverdueLastMonths(String overdueLastMonths) {
		OverdueLastMonths = overdueLastMonths;
	}

	public String getOverdueMoney() {
		return OverdueMoney;
	}

	public void setOverdueMoney(String overdueMoney) {
		OverdueMoney = overdueMoney;
	}

	public String getSpeTransype() {
		return SpeTransype;
	}

	public void setSpeTransype(String speTransype) {
		SpeTransype = speTransype;
	}

	public String getSpeTransOccurDate() {
		return SpeTransOccurDate;
	}

	public void setSpeTransOccurDate(String speTransOccurDate) {
		SpeTransOccurDate = speTransOccurDate;
	}

	public String getSpeTransChangMonths() {
		return SpeTransChangMonths;
	}

	public void setSpeTransChangMonths(String speTransChangMonths) {
		SpeTransChangMonths = speTransChangMonths;
	}

	public String getSpeTransOccurMoney() {
		return SpeTransOccurMoney;
	}

	public void setSpeTransOccurMoney(String speTransOccurMoney) {
		SpeTransOccurMoney = speTransOccurMoney;
	}

	public String getSpeTransDetRecord() {
		return SpeTransDetRecord;
	}

	public void setSpeTransDetRecord(String speTransDetRecord) {
		SpeTransDetRecord = speTransDetRecord;
	}

	public String getCardInstExplain() {
		return CardInstExplain;
	}

	public void setCardInstExplain(String cardInstExplain) {
		CardInstExplain = cardInstExplain;
	}

	public String getAddDate1() {
		return AddDate1;
	}

	public void setAddDate1(String addDate1) {
		AddDate1 = addDate1;
	}

	public String getOneselfStatement() {
		return OneselfStatement;
	}

	public void setOneselfStatement(String oneselfStatement) {
		OneselfStatement = oneselfStatement;
	}

	public String getAddDate2() {
		return AddDate2;
	}

	public void setAddDate2(String addDate2) {
		AddDate2 = addDate2;
	}

	public String getObjectionLabel() {
		return ObjectionLabel;
	}

	public void setObjectionLabel(String objectionLabel) {
		ObjectionLabel = objectionLabel;
	}

	public String getAddDate3() {
		return AddDate3;
	}

	public void setAddDate3(String addDate3) {
		AddDate3 = addDate3;
	}



}
