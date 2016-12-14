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
@Table(name="GR_D34")
@IEntity(description="信贷交易信息明细-贷款信息-已结清贷款")
public class GR_D34 implements Serializable{
	
	private static final long serialVersionUID = -9029790468343238991L;


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

	@Column(name="LoanInstName", length=50,nullable=true)
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

	@Column(name="RepaymentFreqDesc", length=20,nullable=true)
	@IColumn(description="还款频率描述",isNullable=true)
	private String RepaymentFreqDesc;

	@Column(name="ExpireDate", length=20,nullable=true)
	@IColumn(description="到期日期",isNullable=true)
	private String ExpireDate;

	@Column(name="StateDeadline", length=20,nullable=true)
	@IColumn(description="状态截止月",isNullable=true)
	private String StateDeadline;

	@Column(name="Square", length=10,nullable=true)
	@IColumn(description="结清",isNullable=true)
	private String Square;

	@Column(name="OverdueBeginDate", length=50,nullable=true)
	@IColumn(description="逾期起始月",isNullable=true)
	private String OverdueBeginDate;

	@Column(name="OverdueEndDate", length=50,nullable=true)
	@IColumn(description="逾期截止月）",isNullable=true)
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

	public String getRepaymentFreqDesc() {
		return RepaymentFreqDesc;
	}

	public void setRepaymentFreqDesc(String repaymentFreqDesc) {
		RepaymentFreqDesc = repaymentFreqDesc;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}


	
	
	
	
	
}
