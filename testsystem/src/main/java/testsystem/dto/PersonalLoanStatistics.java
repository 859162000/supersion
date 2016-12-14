package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 个人贷款违约情况统计
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PersonalLoanStatistics")
public class PersonalLoanStatistics implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "borrowersName", length = 50)
	@IColumn(description="借款人姓名")
	private String borrowersName;
	
	@Column(name = "validCertificateType", length = 50)
	@IColumn(description="有效身份证件类型")
	private String validCertificateType;
	
	@Id
	@Column(name = "IDNumber", length = 50)
	@IColumn(description="证件号码")
	private String IDNumber;
	
	@Column(name = "loansLineCode", length = 50)
	@IColumn(description="贷款发放行代码")
	private String loansLineCode;
	
	
	@Column(name = "loanContractNumber", length = 50)
	@IColumn(description="贷款合同号")
	private String loanContractNumber;
	
	
	@Column(name = "promissoryNoteNo", length = 50)
	@IColumn(description="借据号	")
	private String promissoryNoteNo;
	
	@Column(name = "loansVarieties", length = 50)
	@IColumn(description="贷款品种")
	private String loansVarieties;
	
	@Column(name = "guaranteeWay", length = 50)
	@IColumn(description="担保方式 ")
	private String guaranteeWay;
	
	@Column(name = "payment", length = 50)
	@IColumn(description="发放金额")
	private String payment;
	
	@Column(name = "loanBalance", length = 50)
	@IColumn(description="贷款余额")
	private String loanBalance;
	
	@Column(name = "issueDate", length = 50)
	@IColumn(description="发放日期")
	private String issueDate;

	@Column(name = "expirationDate", length = 50)
	@IColumn(description="到期日期")
	private String expirationDate;
	
	@Column(name = "defaultAmount", length = 50)
	@IColumn(description="违约金额")
	private String defaultAmount;

	
	@Column(name = "defaultDays", length = 50)
	@IColumn(description="违约天数")
	private String defaultDays;

	
	
	@Column(name = "fiveGradeClass", length = 50)
	@IColumn(description="五级分类")
	private String fiveGradeClass;

	
	@Column(name = "repaymentMethod", length = 50)
	@IColumn(description="还款方式")
	private String repaymentMethod;

	
	@Column(name = "recentRepaymentAmount", length = 50)
	@IColumn(description="最近一次还款金额")
	private String recentRepaymentAmount;

	
	@Column(name = "lastDateRepayment", length = 50)
	@IColumn(description="最近一次还款日期")
	private String lastDateRepayment;

	@Column(name = "customerAddress", length = 50)
	@IColumn(description="客户住址")
	private String customerAddress;
	
	@Column(name = "addressCodes", length = 50)
	@IColumn(description="住址行政区划代码")
	private String addressCodes;
	
	@Column(name = "otherDocumentTypes", length = 50)
	@IColumn(description="客户其他证件类型")
	private String otherDocumentTypes;
	
	@Column(name = "otherCustomerIDNumber", length = 50)
	@IColumn(description="客户其他证件号码")
	private String otherCustomerIDNumber;
	
	@Column(name = "fullName", length = 50)
	@IColumn(description="姓名")
	private String fullName;
	
	@Column(name = "documentType", length = 50)
	@IColumn(description="证件类型")
	private String documentType;
	
	@Column(name = "IDNumbers", length = 50)
	@IColumn(description="证件号码")
	private String IDNumbers;
	
	@Column(name = "schoolName", length = 50)
	@IColumn(description="学校名称")
	private String schoolName;
	
	@Column(name = "schoolAddress", length = 50)
	@IColumn(description="学校地址")
	private String schoolAddress;
	
	@Column(name = "schoolCode", length = 50)
	@IColumn(description="学校行政区划代码")
	private String schoolCode;
	
	@Column(name = "studentIDNo", length = 50)
	@IColumn(description="学生证号")
	private String studentIDNo;
	
	@Column(name = "loansHomeAddress", length = 50)
	@IColumn(description="贷款时家庭住址")
	private String loansHomeAddress;
	
	@Column(name = "loansHomeAddCodes", length = 50)
	@IColumn(description="贷款时家庭住址行政区划代码	")
	private String loansHomeAddCodes;
	
	@Column(name = "studentLoanType", length = 50)
	@IColumn(description="助学贷款类型")
	private String studentLoanType;

	public String getBorrowersName() {
		return borrowersName;
	}

	public void setBorrowersName(String borrowersName) {
		this.borrowersName = borrowersName;
	}

	public String getValidCertificateType() {
		return validCertificateType;
	}

	public void setValidCertificateType(String validCertificateType) {
		this.validCertificateType = validCertificateType;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getLoansLineCode() {
		return loansLineCode;
	}

	public void setLoansLineCode(String loansLineCode) {
		this.loansLineCode = loansLineCode;
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

	public String getLoansVarieties() {
		return loansVarieties;
	}

	public void setLoansVarieties(String loansVarieties) {
		this.loansVarieties = loansVarieties;
	}

	public String getGuaranteeWay() {
		return guaranteeWay;
	}

	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
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

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getDefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(String defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public String getDefaultDays() {
		return defaultDays;
	}

	public void setDefaultDays(String defaultDays) {
		this.defaultDays = defaultDays;
	}

	public String getFiveGradeClass() {
		return fiveGradeClass;
	}

	public void setFiveGradeClass(String fiveGradeClass) {
		this.fiveGradeClass = fiveGradeClass;
	}

	public String getRepaymentMethod() {
		return repaymentMethod;
	}

	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}

	public String getRecentRepaymentAmount() {
		return recentRepaymentAmount;
	}

	public void setRecentRepaymentAmount(String recentRepaymentAmount) {
		this.recentRepaymentAmount = recentRepaymentAmount;
	}

	public String getLastDateRepayment() {
		return lastDateRepayment;
	}

	public void setLastDateRepayment(String lastDateRepayment) {
		this.lastDateRepayment = lastDateRepayment;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getAddressCodes() {
		return addressCodes;
	}

	public void setAddressCodes(String addressCodes) {
		this.addressCodes = addressCodes;
	}

	public String getOtherDocumentTypes() {
		return otherDocumentTypes;
	}

	public void setOtherDocumentTypes(String otherDocumentTypes) {
		this.otherDocumentTypes = otherDocumentTypes;
	}

	public String getOtherCustomerIDNumber() {
		return otherCustomerIDNumber;
	}

	public void setOtherCustomerIDNumber(String otherCustomerIDNumber) {
		this.otherCustomerIDNumber = otherCustomerIDNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getIDNumbers() {
		return IDNumbers;
	}

	public void setIDNumbers(String iDNumbers) {
		IDNumbers = iDNumbers;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getStudentIDNo() {
		return studentIDNo;
	}

	public void setStudentIDNo(String studentIDNo) {
		this.studentIDNo = studentIDNo;
	}

	public String getLoansHomeAddress() {
		return loansHomeAddress;
	}

	public void setLoansHomeAddress(String loansHomeAddress) {
		this.loansHomeAddress = loansHomeAddress;
	}

	public String getLoansHomeAddCodes() {
		return loansHomeAddCodes;
	}

	public void setLoansHomeAddCodes(String loansHomeAddCodes) {
		this.loansHomeAddCodes = loansHomeAddCodes;
	}

	public String getStudentLoanType() {
		return studentLoanType;
	}

	public void setStudentLoanType(String studentLoanType) {
		this.studentLoanType = studentLoanType;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}


	
}

