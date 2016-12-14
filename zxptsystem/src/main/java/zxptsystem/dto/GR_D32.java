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
@Table(name="GR_D32")
@IEntity(description="信贷交易信息明细-贷款信息-未结清贷款")
public class GR_D32 implements Serializable{
	
	
	private static final long serialVersionUID = 7613466086052842196L;

	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;

	@Column(name="DistriDate", length=20,nullable=true)
	@IColumn(description="发放日期",isNullable=true)
	private String DistriDate;

	@Column(name="LoanInstName", length=200,nullable=true)
	@IColumn(description="贷款机构名称",isNullable=true)
	private String LoanInstName;

	@Column(name="ContractMoney", length=20,nullable=true)
	@IColumn(description="合同金额",isNullable=true)
	private String ContractMoney;

	@Column(name="CurrDesc", length=50,nullable=true)
	@IColumn(description="币种描述",isNullable=true)
	private String CurrDesc;

	@Column(name="LoanType", length=20,nullable=true)
	@IColumn(description="贷款种类",isNullable=true)
	private String LoanType;

	@Column(name="BusiNoDesc", length=50,nullable=true)
	@IColumn(description="业务号描述",isNullable=true)
	private String BusiNoDesc;

	@Column(name="GuarWayDesc", length=50,nullable=true)
	@IColumn(description="担保方式描述",isNullable=true)
	private String GuarWayDesc;

	@Column(name="RepayPeriodDesc", length=50,nullable=true)
	@IColumn(description="还款期数描述",isNullable=true)
	private String RepayPeriodDesc;

	@Column(name="RepayFreqDesc", length=20,nullable=true)
	@IColumn(description="还款频率描述",isNullable=true)
	private String RepayFreqDesc;

	@Column(name="ExpireDate", length=20,nullable=true)
	@IColumn(description="到期日期",isNullable=true)
	private String ExpireDate;

	@Column(name="StateDeadline", length=20,nullable=true)
	@IColumn(description="状态截止日",isNullable=true)
	private String StateDeadline;

	@Column(name="accountState", length=20,nullable=true)
	@IColumn(description="账户状态",isNullable=true)
	private String accountState;
	
	@Column(name="FiveLevelClassifi", length=50,nullable=true)
	@IColumn(description="五级分类",isNullable=true)
	private String FiveLevelClassifi;

	@Column(name="PrinciBalance", length=20,nullable=true)
	@IColumn(description="本金余额",isNullable=true)
	private String PrinciBalance;

	@Column(name="RemRepayPeriod", length=20,nullable=true)
	@IColumn(description="剩余还款期数",isNullable=true)
	private String RemRepayPeriod;

	@Column(name="CurrMonthRepay", length=20,nullable=true)
	@IColumn(description="本月应还款",isNullable=true)
	private String CurrMonthRepay;

	@Column(name="RepaymentDate", length=20,nullable=true)
	@IColumn(description="应还款日",isNullable=true)
	private String RepaymentDate;

	@Column(name="CurrMonRealRepay", length=20,nullable=true)
	@IColumn(description="本月实还款",isNullable=true)
	private String CurrMonRealRepay;

	@Column(name="LDateOfRepayment", length=20,nullable=true)
	@IColumn(description="最近一次还款日期",isNullable=true)
	private String LDateOfRepayment;

	@Column(name="CurOverduePeriod", length=20,nullable=true)
	@IColumn(description="当前逾期期数",isNullable=true)
	private String CurOverduePeriod;

	@Column(name="CurrOverdueMoney", length=20,nullable=true)
	@IColumn(description="当前逾期金额",isNullable=true)
	private String CurrOverdueMoney;

	@Column(name="Overd30_60NonRPri", length=20,nullable=true)
	@IColumn(description="逾期31-60天未还本金",isNullable=true)
	private String Overd30_60NonRPri;

	@Column(name="Overd61_90NonRLPri", length=20,nullable=true)
	@IColumn(description="逾期61-90天未还本金",isNullable=true)
	private String Overd61_90NonRLPri;

	@Column(name="Overd91_180NonRLPri", length=20,nullable=true)
	@IColumn(description="逾期91-180天未还本金",isNullable=true)
	private String Overd91_180NonRLPri;

	@Column(name="OverdA_180NonRLPri", length=20,nullable=true)
	@IColumn(description="逾期180天以上未还本金",isNullable=true)
	private String OverdA_180NonRLPri;

	@Column(name="LoanBeginDate", length=20,nullable=true)
	@IColumn(description="贷款起始月",isNullable=true)
	private String LoanBeginDate;

	@Column(name="LoanEndDate", length=20,nullable=true)
	@IColumn(description="贷款截止月",isNullable=true)
	private String LoanEndDate;

	@Column(name="RepaymentState_24M", length=24,nullable=true)
	@IColumn(description="24个月还款状态",isNullable=true)
	private String RepaymentState_24M;

	@Column(name="OverdueBeginDate", length=20,nullable=true)
	@IColumn(description="逾期起始月",isNullable=true)
	private String OverdueBeginDate;

	@Column(name="OverdueEndDate", length=20,nullable=true)
	@IColumn(description="逾期截止月",isNullable=true)
	private String OverdueEndDate;

	@Column(name="OverdueMonth", length=20,nullable=true)
	@IColumn(description="逾期月份",isNullable=true)
	private String OverdueMonth;

	@Column(name="OverdueLastMonths", length=20,nullable=true)
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

	@Column(name="LoanInstExplain", length=255,nullable=true)
	@IColumn(description="贷款机构说明",isNullable=true)
	private String LoanInstExplain;

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
	@IColumn(description="贷款序号",isNullable=true)
	private String LoanNo;

	public String getLoanNo() {
		return LoanNo;
	}

	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}
	
	public String getDistriDate() {
		return DistriDate;
	}

	public void setDistriDate(String distriDate) {
		DistriDate = distriDate;
	}

	public String getLoanInstName() {
		return LoanInstName;
	}

	public void setLoanInstName(String loanInstName) {
		LoanInstName = loanInstName;
	}

	public String getContractMoney() {
		return ContractMoney;
	}

	public void setContractMoney(String contractMoney) {
		ContractMoney = contractMoney;
	}

	public String getCurrDesc() {
		return CurrDesc;
	}

	public void setCurrDesc(String currDesc) {
		CurrDesc = currDesc;
	}

	public String getLoanType() {
		return LoanType;
	}

	public void setLoanType(String loanType) {
		LoanType = loanType;
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

	public String getRepayPeriodDesc() {
		return RepayPeriodDesc;
	}

	public void setRepayPeriodDesc(String repayPeriodDesc) {
		RepayPeriodDesc = repayPeriodDesc;
	}

	public String getRepayFreqDesc() {
		return RepayFreqDesc;
	}

	public void setRepayFreqDesc(String repayFreqDesc) {
		RepayFreqDesc = repayFreqDesc;
	}

	public String getExpireDate() {
		return ExpireDate;
	}

	public void setExpireDate(String expireDate) {
		ExpireDate = expireDate;
	}

	public String getStateDeadline() {
		return StateDeadline;
	}

	public void setStateDeadline(String stateDeadline) {
		StateDeadline = stateDeadline;
	}
	
	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getFiveLevelClassifi() {
		return FiveLevelClassifi;
	}

	public void setFiveLevelClassifi(String fiveLevelClassifi) {
		FiveLevelClassifi = fiveLevelClassifi;
	}

	public String getPrinciBalance() {
		return PrinciBalance;
	}

	public void setPrinciBalance(String princiBalance) {
		PrinciBalance = princiBalance;
	}

	public String getRemRepayPeriod() {
		return RemRepayPeriod;
	}

	public void setRemRepayPeriod(String remRepayPeriod) {
		RemRepayPeriod = remRepayPeriod;
	}

	public String getCurrMonthRepay() {
		return CurrMonthRepay;
	}

	public void setCurrMonthRepay(String currMonthRepay) {
		CurrMonthRepay = currMonthRepay;
	}

	public String getRepaymentDate() {
		return RepaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		RepaymentDate = repaymentDate;
	}

	public String getCurrMonRealRepay() {
		return CurrMonRealRepay;
	}

	public void setCurrMonRealRepay(String currMonRealRepay) {
		CurrMonRealRepay = currMonRealRepay;
	}

	public String getLDateOfRepayment() {
		return LDateOfRepayment;
	}

	public void setLDateOfRepayment(String lDateOfRepayment) {
		LDateOfRepayment = lDateOfRepayment;
	}

	public String getCurOverduePeriod() {
		return CurOverduePeriod;
	}

	public void setCurOverduePeriod(String curOverduePeriod) {
		CurOverduePeriod = curOverduePeriod;
	}

	public String getCurrOverdueMoney() {
		return CurrOverdueMoney;
	}

	public void setCurrOverdueMoney(String currOverdueMoney) {
		CurrOverdueMoney = currOverdueMoney;
	}

	public String getOverd30_60NonRPri() {
		return Overd30_60NonRPri;
	}

	public void setOverd30_60NonRPri(String overd30_60NonRPri) {
		Overd30_60NonRPri = overd30_60NonRPri;
	}

	public String getOverd61_90NonRLPri() {
		return Overd61_90NonRLPri;
	}

	public void setOverd61_90NonRLPri(String overd61_90NonRLPri) {
		Overd61_90NonRLPri = overd61_90NonRLPri;
	}

	public String getOverd91_180NonRLPri() {
		return Overd91_180NonRLPri;
	}

	public void setOverd91_180NonRLPri(String overd91_180NonRLPri) {
		Overd91_180NonRLPri = overd91_180NonRLPri;
	}

	public String getOverdA_180NonRLPri() {
		return OverdA_180NonRLPri;
	}

	public void setOverdA_180NonRLPri(String overdA_180NonRLPri) {
		OverdA_180NonRLPri = overdA_180NonRLPri;
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

	public String getLoanInstExplain() {
		return LoanInstExplain;
	}

	public void setLoanInstExplain(String loanInstExplain) {
		LoanInstExplain = loanInstExplain;
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
	
	
	
}
