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

import zxptsystem.dto.GR_A1;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name="GR_D52")
@IEntity(description="信贷交易信息明细-准贷记卡-未销户准贷记卡账户")
public class GR_D52 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8495635597796482260L;


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

	@Column(name="BusiNoDesc", length=255,nullable=true)
	@IColumn(description="业务号描述",isNullable=true)
	private String BusiNoDesc;

	@Column(name="GuarWayDesc", length=255,nullable=true)
	@IColumn(description="担保方式描述",isNullable=true)
	private String GuarWayDesc;

	@Column(name="StateDeadline", length=20,nullable=true)
	@IColumn(description="状态截至日",isNullable=true)
	private String StateDeadline;

	@Column(name="ShareAmount", length=50,nullable=true)
	@IColumn(description="共享额度",isNullable=true)
	private String ShareAmount;

	@Column(name="accountState", length=20,nullable=true)
	@IColumn(description="账户状态",isNullable=true)
	private String accountState;
	
	@Column(name="OverdraftBalance", length=20,nullable=true)
	@IColumn(description="透支余额",isNullable=true)
	private String OverdraftBalance;

	@Column(name="Re_6_MAOverdAmount", length=20,nullable=true)
	@IColumn(description="最近6个月平均透支余额",isNullable=true)
	private String Re_6_MAOverdAmount;

	@Column(name="MaximumOverdraftBalance", length=20,nullable=true)
	@IColumn(description="最大透支余额",isNullable=true)
	private String MaximumOverdraftBalance;

	@Column(name="StatementDate", length=20,nullable=true)
	@IColumn(description="账单日",isNullable=true)
	private String StatementDate;

	@Column(name="CurrMonthRealRepay", length=50,nullable=true)
	@IColumn(description="本月实还款",isNullable=true)
	private String CurrMonthRealRepay;

	@Column(name="LDateOfRepayment", length=50,nullable=true)
	@IColumn(description="最近一次还款日期",isNullable=true)
	private String LDateOfRepayment;

	@Column(name="Overd_180_DOutsBal", length=50,nullable=true)
	@IColumn(description="透支180天以上未付余额",isNullable=true)
	private String Overd_180_DOutsBal;

	@Column(name="LoanBeginDate", length=20,nullable=true)
	@IColumn(description="贷款起始月",isNullable=true)
	private String LoanBeginDate;

	@Column(name="LoanEndDate", length=50,nullable=true)
	@IColumn(description="贷款截止月",isNullable=true)
	private String LoanEndDate;

	@Column(name="RepaymentState_24M", length=24,nullable=true)
	@IColumn(description="24个月还款状态",isNullable=true)
	private String RepaymentState_24M;

	@Column(name="A_60OverdBeginDate", length=20,nullable=true)
	@IColumn(description="60天以上透支记录起始月",isNullable=true)
	private String A_60OverdBeginDate;

	@Column(name="A_60OverdEndDate", length=20,nullable=true)
	@IColumn(description="60天以上透支记录截止月",isNullable=true)
	private String A_60OverdEndDate;

	@Column(name="OverdraftMonth", length=20,nullable=true)
	@IColumn(description="透支月份",isNullable=true)
	private String OverdraftMonth;

	@Column(name="OverdraftLastMonths", length=20,nullable=true)
	@IColumn(description="透支持续月数",isNullable=true)
	private String OverdraftLastMonths;

	@Column(name="OverdraftMoney", length=20,nullable=true)
	@IColumn(description="透支金额",isNullable=true)
	private String OverdraftMoney;

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

	@Column(name="SpeTransDetaiRecord", length=255,nullable=true)
	@IColumn(description="明细记录",isNullable=true)
	private String SpeTransDetaiRecord;

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
	@IColumn(description="准贷记卡序号",isNullable=true)
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

	public String getStateDeadline() {
		return StateDeadline;
	}

	public void setStateDeadline(String stateDeadline) {
		StateDeadline = stateDeadline;
	}

	public String getShareAmount() {
		return ShareAmount;
	}

	public void setShareAmount(String shareAmount) {
		ShareAmount = shareAmount;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getOverdraftBalance() {
		return OverdraftBalance;
	}

	public void setOverdraftBalance(String overdraftBalance) {
		OverdraftBalance = overdraftBalance;
	}

	public String getRe_6_MAOverdAmount() {
		return Re_6_MAOverdAmount;
	}

	public void setRe_6_MAOverdAmount(String re_6MAOverdAmount) {
		Re_6_MAOverdAmount = re_6MAOverdAmount;
	}

	public String getMaximumOverdraftBalance() {
		return MaximumOverdraftBalance;
	}

	public void setMaximumOverdraftBalance(String maximumOverdraftBalance) {
		MaximumOverdraftBalance = maximumOverdraftBalance;
	}

	public String getStatementDate() {
		return StatementDate;
	}

	public void setStatementDate(String statementDate) {
		StatementDate = statementDate;
	}

	public String getCurrMonthRealRepay() {
		return CurrMonthRealRepay;
	}

	public void setCurrMonthRealRepay(String currMonthRealRepay) {
		CurrMonthRealRepay = currMonthRealRepay;
	}

	public String getLDateOfRepayment() {
		return LDateOfRepayment;
	}

	public void setLDateOfRepayment(String lDateOfRepayment) {
		LDateOfRepayment = lDateOfRepayment;
	}

	public String getOverd_180_DOutsBal() {
		return Overd_180_DOutsBal;
	}

	public void setOverd_180_DOutsBal(String overd_180DOutsBal) {
		Overd_180_DOutsBal = overd_180DOutsBal;
	}

	public String getLoanBeginDate() {
		return LoanBeginDate;
	}

	public void setLoanBeginDate(String loanBeginDate) {
		LoanBeginDate = loanBeginDate;
	}

	public String getLoanEndDate() {
		return LoanEndDate;
	}

	public void setLoanEndDate(String loanEndDate) {
		LoanEndDate = loanEndDate;
	}

	public String getRepaymentState_24M() {
		return RepaymentState_24M;
	}

	public void setRepaymentState_24M(String repaymentState_24M) {
		RepaymentState_24M = repaymentState_24M;
	}

	public String getA_60OverdBeginDate() {
		return A_60OverdBeginDate;
	}

	public void setA_60OverdBeginDate(String a_60OverdBeginDate) {
		A_60OverdBeginDate = a_60OverdBeginDate;
	}

	public String getA_60OverdEndDate() {
		return A_60OverdEndDate;
	}

	public void setA_60OverdEndDate(String a_60OverdEndDate) {
		A_60OverdEndDate = a_60OverdEndDate;
	}

	public String getOverdraftMonth() {
		return OverdraftMonth;
	}

	public void setOverdraftMonth(String overdraftMonth) {
		OverdraftMonth = overdraftMonth;
	}

	public String getOverdraftLastMonths() {
		return OverdraftLastMonths;
	}

	public void setOverdraftLastMonths(String overdraftLastMonths) {
		OverdraftLastMonths = overdraftLastMonths;
	}

	public String getOverdraftMoney() {
		return OverdraftMoney;
	}

	public void setOverdraftMoney(String overdraftMoney) {
		OverdraftMoney = overdraftMoney;
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

	public String getSpeTransDetaiRecord() {
		return SpeTransDetaiRecord;
	}

	public void setSpeTransDetaiRecord(String speTransDetaiRecord) {
		SpeTransDetaiRecord = speTransDetaiRecord;
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
