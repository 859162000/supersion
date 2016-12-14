package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import coresystem.dto.InstInfo;

import framework.interfaces.IColumn;

/**
 * 集团客户授信拆分情况
 * GroupCustomerCredit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "GroupCustomerCredit")
public class GroupCustomerCredit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "MemberCustomerName", length = 50)
	@IColumn(description="成员单位的客户名称")
	private String MemberCustomerName;
	
	@Id
	@Column(name = "MemberCustomerCode", length = 50)
	@IColumn(description="成员单位的客户代码")
	private String MemberCustomerCode;
	
	
	@Column(name = "creditNum", length = 50)
	@IColumn(description="授信号码")
	private String creditNum;
	
	
	@Column(name = "LineCredit", length = 50)
	@IColumn(description="授信额度")
	private String LineCredit;
	
	@Column(name = "loanCredit", length = 50)
	@IColumn(description="贷款授信额度")
	private String loanCredit;
	
	@Column(name = "balanceLoans", length = 50)
	@IColumn(description="贷款余额")
	private String balanceLoans;
	
	@Column(name = "holdBondBalance", length = 50)
	@IColumn(description="持有债券余额")
	private String holdBondBalance;
	
	@Column(name = "shareholdBalance", length = 50)
	@IColumn(description="持有股权余额")
	private String shareholdBalance;
	
	@Column(name = "creditRiskAssetsBalance", length = 50)
	@IColumn(description="其他表内信用风险资产余额")
	private String creditRiskAssetsBalance;
	
	@Column(name = "balanceBusiness", length = 50)
	@IColumn(description="表外业务余额")
	private String balanceBusiness;
	
	@Column(name = "businessCreditBalance", length = 50)
	@IColumn(description="现有业务余额占用授信额度")
	private String businessCreditBalance;
	
	@Column(name = "loansOccupyCreditLines", length = 50)
	@IColumn(description="贷款余额占用贷款授信额度")
	private String loansOccupyCreditLines;
	
	@Column(name = "creditUse", length = 50)
	@IColumn(description="尚可使用授信额度")
	private String creditUse;
	
	@Column(name = "creditLoanUse", length = 50)
	@IColumn(description="尚可使用贷款授信额度")
	private String creditLoanUse ;
	
	@IColumn(description="客户类型")
	@Column(name = "customerTypes", length = 50)
	private String customerTypes;

	public String getMemberCustomerName() {
		return MemberCustomerName;
	}

	public void setMemberCustomerName(String memberCustomerName) {
		MemberCustomerName = memberCustomerName;
	}

	public String getMemberCustomerCode() {
		return MemberCustomerCode;
	}

	public void setMemberCustomerCode(String memberCustomerCode) {
		MemberCustomerCode = memberCustomerCode;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public String getLineCredit() {
		return LineCredit;
	}

	public void setLineCredit(String lineCredit) {
		LineCredit = lineCredit;
	}

	public String getLoanCredit() {
		return loanCredit;
	}

	public void setLoanCredit(String loanCredit) {
		this.loanCredit = loanCredit;
	}

	public String getBalanceLoans() {
		return balanceLoans;
	}

	public void setBalanceLoans(String balanceLoans) {
		this.balanceLoans = balanceLoans;
	}

	public String getHoldBondBalance() {
		return holdBondBalance;
	}

	public void setHoldBondBalance(String holdBondBalance) {
		this.holdBondBalance = holdBondBalance;
	}

	public String getShareholdBalance() {
		return shareholdBalance;
	}

	public void setShareholdBalance(String shareholdBalance) {
		this.shareholdBalance = shareholdBalance;
	}

	public String getCreditRiskAssetsBalance() {
		return creditRiskAssetsBalance;
	}

	public void setCreditRiskAssetsBalance(String creditRiskAssetsBalance) {
		this.creditRiskAssetsBalance = creditRiskAssetsBalance;
	}

	public String getBalanceBusiness() {
		return balanceBusiness;
	}

	public void setBalanceBusiness(String balanceBusiness) {
		this.balanceBusiness = balanceBusiness;
	}

	public String getBusinessCreditBalance() {
		return businessCreditBalance;
	}

	public void setBusinessCreditBalance(String businessCreditBalance) {
		this.businessCreditBalance = businessCreditBalance;
	}

	public String getLoansOccupyCreditLines() {
		return loansOccupyCreditLines;
	}

	public void setLoansOccupyCreditLines(String loansOccupyCreditLines) {
		this.loansOccupyCreditLines = loansOccupyCreditLines;
	}

	public String getCreditUse() {
		return creditUse;
	}

	public void setCreditUse(String creditUse) {
		this.creditUse = creditUse;
	}

	public String getCreditLoanUse() {
		return creditLoanUse;
	}

	public void setCreditLoanUse(String creditLoanUse) {
		this.creditLoanUse = creditLoanUse;
	}

	

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setCustomerTypes(String customerTypes) {
		this.customerTypes = customerTypes;
	}

	public String getCustomerTypes() {
		return customerTypes;
	}
}