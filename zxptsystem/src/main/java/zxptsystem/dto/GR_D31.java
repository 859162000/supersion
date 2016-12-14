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
@Table(name="GR_D31")
@IEntity(description="信贷交易信息明细-贷款信息-呆账贷款")
public class GR_D31 implements Serializable{
	
	private static final long serialVersionUID = 3508385652264696067L;
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
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
	
	@Column(name="BusiNoDesc", length=20,nullable=true)
	@IColumn(description="业务号描述",isNullable=true)
	private String BusiNoDesc;
	
	@Column(name="GuarWayDesc", length=255,nullable=true)
	@IColumn(description="担保方式描述",isNullable=true)
	private String GuarWayDesc;
	
	@Column(name="RepayPeriodDesc", length=255,nullable=true)
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
	
	@Column(name="BalanceDesc", length=20,nullable=true)
	@IColumn(description="余额描述",isNullable=true)
	private String BalanceDesc;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;

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
	
	public String getBalanceDesc() {
		return BalanceDesc;
	}

	public void setBalanceDesc(String balanceDesc) {
		BalanceDesc = balanceDesc;
	}

	public GR_A1 getReportNo() {
		return ReportNo;
	}

	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}
	
	
	
}
