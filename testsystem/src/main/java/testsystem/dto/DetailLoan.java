package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 贷款明细表
 * detailLoan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DetailLoan")
public class DetailLoan implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "loanCustomerName", length = 50)
	@IColumn(description="贷款客户名称")
	private String loanCustomerName;
	
	@Id
	@Column(name = "loanCustomerCode", length = 50)
	@IColumn(description="贷款客户代码")
	private String loanCustomerCode;
	
	
	@Column(name = "loansCode", length = 50)
	@IColumn(description="贷款发放行代码")
	private String loansCode;
	
	@Column(name = "creditNum", length = 50)
	@IColumn(description="授信号码")
	private String creditNum;
	
	@Column(name = "loanContractNumber", length = 50)
	@IColumn(description="贷款合同号")
	private String loanContractNumber;
	
	@Column(name = "promissoryNoteNo", length = 50)
	@IColumn(description="借据号")
	private String promissoryNoteNo;
	
	@Column(name = "issueDate", length = 50)
	@IColumn(description="发放日期")
	private String issueDate;
	
	@Column(name = "dueDate", length = 50)
	@IColumn(description="到期日期")
	private String dueDate;
	
	@Column(name = "payment", length = 50)
	@IColumn(description="发放金额")
	private String payment;
	
	@Column(name = "loanBalance", length = 50)
	@IColumn(description="贷款余额")
	private String loanBalance;
	
	@Column(name = "fiveClass", length = 50)
	@IColumn(description="五级分类")
	private String fiveClass;
	
	@Column(name = "loanType", length = 50)
	@IColumn(description="贷款类型")
	private String loanType;
	
	@Column(name = "loanServices", length = 50)
	@IColumn(description="贷款业务种类")
	private String loanServices;
	
	@Column(name = "toIndustry", length = 50)
	@IColumn(description="投向行业")
	private String toIndustry ;
	
	@Column(name = "CurrencyCode", length = 50)
	@IColumn(description="币种代码")
	private String CurrencyCode ;
	
	@Column(name = "guaranteeWay", length = 50)
	@IColumn(description="担保方式")
	private String guaranteeWay;

	@Column(name = "inBalance", length = 50)
	@IColumn(description="欠本余额")
	private String inBalance;
	
	@Column(name = "inDays", length = 50)
	@IColumn(description="欠本天数")
	private String inDays;
	
	@Column(name = "interestBalance", length = 50)
	@IColumn(description="欠息余额")
	private String interestBalance ;
	
	@Column(name = "interestDays", length = 50)
	@IColumn(description="欠息天数")
	private String interestDays;
	
	@Column(name = "repayment", length = 50)
	@IColumn(description="本期还款")
	private String repayment;
	

	@Column(name = "principalWay", length = 50)
	@IColumn(description="还本方式")
	private String principalWay;
	
	@Column(name = "interestMethod", length = 50)
	@IColumn(description="还息方式")
	private String interestMethod;
	

	@Column(name = "nextServiceDate", length = 50)
	@IColumn(description="下期还本日期")
	private String nextServiceDate;
	
	@Column(name = "nextPrincipalAmount", length = 50)
	@IColumn(description="下期还本金额")
	private String nextPrincipalAmount;
	
	@Column(name = "nextInterestDate", length = 50)
	@IColumn(description="下期还息日期")
	private String nextInterestDate;
	
	@Column(name = "nextInterestAmount", length = 50)
	@IColumn(description="下期还息金额")
	private String nextInterestAmount;
	
	@Column(name = "loanTypes", length = 50)
	@IColumn(description="贷款发放类型")
	private String loanTypes;
	
	@Column(name = "impairment", length = 50)
	@IColumn(description="减值准备")
	private String impairment;
	
	@Column(name = "industrialStructureTypes", length = 50)
	@IColumn(description="产业结构调整类型")
	private String industrialStructureTypes;
	
	@Column(name = "industrialTransformationUpgrad", length = 50)
	@IColumn(description="工业转型升级标识")
	private String industrialTransformationUpgrad;
	
	@Column(name = "strategicEmergIndustryType", length = 50)
	@IColumn(description="战略新兴产业类型")
	private String strategicEmergIndustryType;
	
	@Column(name = "syndicatedLoanIdentification", length = 50)
	@IColumn(description="银团贷款标识")
	private String syndicatedLoanIdentification;
	
	@Column(name = "payments", length = 50)
	@IColumn(description="支付方式")
	private String payments;
	
	public String getLoanCustomerName() {
		return loanCustomerName;
	}

	public void setLoanCustomerName(String loanCustomerName) {
		this.loanCustomerName = loanCustomerName;
	}

	public String getLoanCustomerCode() {
		return loanCustomerCode;
	}

	public void setLoanCustomerCode(String loanCustomerCode) {
		this.loanCustomerCode = loanCustomerCode;
	}

	public String getLoansCode() {
		return loansCode;
	}

	public void setLoansCode(String loansCode) {
		this.loansCode = loansCode;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public String getLoanContractNumber() {
		return loanContractNumber;
	}

	public void setLoanContractNumber(String loanContractNumber) {
		this.loanContractNumber = loanContractNumber;
	}

	public String getPromissoryNoteNo() {
		return promissoryNoteNo;
	}

	public void setPromissoryNoteNo(String promissoryNoteNo) {
		this.promissoryNoteNo = promissoryNoteNo;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(String loanBalance) {
		this.loanBalance = loanBalance;
	}

	public String getFiveClass() {
		return fiveClass;
	}

	public void setFiveClass(String fiveClass) {
		this.fiveClass = fiveClass;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanServices() {
		return loanServices;
	}

	public void setLoanServices(String loanServices) {
		this.loanServices = loanServices;
	}

	public String getToIndustry() {
		return toIndustry;
	}

	public void setToIndustry(String toIndustry) {
		this.toIndustry = toIndustry;
	}

	public String getCurrencyCode() {
		return CurrencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

	public String getGuaranteeWay() {
		return guaranteeWay;
	}

	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}

	public String getInBalance() {
		return inBalance;
	}

	public void setInBalance(String inBalance) {
		this.inBalance = inBalance;
	}

	public String getInDays() {
		return inDays;
	}

	public void setInDays(String inDays) {
		this.inDays = inDays;
	}

	public String getInterestBalance() {
		return interestBalance;
	}

	public void setInterestBalance(String interestBalance) {
		this.interestBalance = interestBalance;
	}

	public String getInterestDays() {
		return interestDays;
	}

	public void setInterestDays(String interestDays) {
		this.interestDays = interestDays;
	}

	public String getRepayment() {
		return repayment;
	}

	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}

	public String getPrincipalWay() {
		return principalWay;
	}

	public void setPrincipalWay(String principalWay) {
		this.principalWay = principalWay;
	}

	public String getInterestMethod() {
		return interestMethod;
	}

	public void setInterestMethod(String interestMethod) {
		this.interestMethod = interestMethod;
	}

	public String getNextServiceDate() {
		return nextServiceDate;
	}

	public void setNextServiceDate(String nextServiceDate) {
		this.nextServiceDate = nextServiceDate;
	}

	public String getNextPrincipalAmount() {
		return nextPrincipalAmount;
	}

	public void setNextPrincipalAmount(String nextPrincipalAmount) {
		this.nextPrincipalAmount = nextPrincipalAmount;
	}

	public String getNextInterestDate() {
		return nextInterestDate;
	}

	public void setNextInterestDate(String nextInterestDate) {
		this.nextInterestDate = nextInterestDate;
	}

	public String getNextInterestAmount() {
		return nextInterestAmount;
	}

	public void setNextInterestAmount(String nextInterestAmount) {
		this.nextInterestAmount = nextInterestAmount;
	}

	public String getLoanTypes() {
		return loanTypes;
	}

	public void setLoanTypes(String loanTypes) {
		this.loanTypes = loanTypes;
	}

	public String getImpairment() {
		return impairment;
	}

	public void setImpairment(String impairment) {
		this.impairment = impairment;
	}

	public String getIndustrialStructureTypes() {
		return industrialStructureTypes;
	}

	public void setIndustrialStructureTypes(String industrialStructureTypes) {
		this.industrialStructureTypes = industrialStructureTypes;
	}

	public String getIndustrialTransformationUpgrad() {
		return industrialTransformationUpgrad;
	}

	public void setIndustrialTransformationUpgrad(
			String industrialTransformationUpgrad) {
		this.industrialTransformationUpgrad = industrialTransformationUpgrad;
	}

	public String getStrategicEmergIndustryType() {
		return strategicEmergIndustryType;
	}

	public void setStrategicEmergIndustryType(String strategicEmergIndustryType) {
		this.strategicEmergIndustryType = strategicEmergIndustryType;
	}

	public String getSyndicatedLoanIdentification() {
		return syndicatedLoanIdentification;
	}

	public void setSyndicatedLoanIdentification(String syndicatedLoanIdentification) {
		this.syndicatedLoanIdentification = syndicatedLoanIdentification;
	}

	public String getPayments() {
		return payments;
	}

	public void setPayments(String payments) {
		this.payments = payments;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}


	
}