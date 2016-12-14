package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 个人违约贷款担保情况统计
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PersonalLoanGuarantee")
public class PersonalLoanGuarantee implements java.io.Serializable {

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
	
	@Column(name = "certificateType", length = 50)
	@IColumn(description="有效身份证件类型")
	private String certificateType;
	
	@Id
	@Column(name = "IDNumber", length = 50)
	@IColumn(description="证件号码")
	private String IDNumber;
	
	
	@Column(name = "loanContractNumber", length = 50)
	@IColumn(description="贷款合同号	")
	private String loanContractNumber;
	
	
	@Column(name = "guarantyContractNo", length = 50)
	@IColumn(description="担保合同号")
	private String guarantyContractNo;
	

	@Column(name = "guarantyContractType", length = 50)
	@IColumn(description="担保合同类型")
	private String guarantyContractType;
	
	@Column(name = "collateralType", length = 50)
	@IColumn(description="押品类型")
	private String collateralType;
	
	@Column(name = "productName", length = 50)
	@IColumn(description="押品名称 ")
	private String productName;
	
	@Column(name = "collateralManageName", length = 50)
	@IColumn(description="押品权属人（或保证人）名称")
	private String collateralManageName;
	
	@Column(name = "securityCode", length = 50)
	@IColumn(description="押品代码")
	private String securityCode;


	@Column(name = "collateralManageType", length = 50)
	@IColumn(description="押品权属人（或保证人）类型")
	private String collateralManageType;
	
	@Column(name = "collateralDocumentType", length = 50)
	@IColumn(description="押品权属人（或保证人）证件类型")
	private String collateralDocumentType;

	
	@Column(name = "collateralIDCode", length = 50)
	@IColumn(description="押品权属人（或保证人）证件代码")
	private String collateralIDCode;

	
	
	@Column(name = "collateralAssessmentValue", length = 50)
	@IColumn(description="押品评估价值（或保证金额）")
	private String collateralAssessmentValue;

	
	@Column(name = "collateralThirdParty", length = 50)
	@IColumn(description="押品权属人是否第三方")
	private String collateralThirdParty;

	
	@Column(name = "initialValuationDate", length = 50)
	@IColumn(description="首次估值日期")
	private String initialValuationDate;

	
	@Column(name = "latestDate", length = 50)
	@IColumn(description="最新估值日期")
	private String latestDate;

	@Column(name = "valuationDate", length = 50)
	@IColumn(description="估值到期日期")
	private String valuationDate;
	
	@Column(name = "guarantorCapacityLimit", length = 50)
	@IColumn(description="保证人保证能力上限")
	private String guarantorCapacityLimit;
	
	@Column(name = "approvalMortgageRate", length = 50)
	@IColumn(description="审批抵质押率")
	private String approvalMortgageRate;
	
	@Column(name = "pledgeRate", length = 50)
	@IColumn(description="实际抵质押率")
	private String pledgeRate;

	public String getBorrowersName() {
		return borrowersName;
	}

	public void setBorrowersName(String borrowersName) {
		this.borrowersName = borrowersName;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getLoanContractNumber() {
		return loanContractNumber;
	}

	public void setLoanContractNumber(String loanContractNumber) {
		this.loanContractNumber = loanContractNumber;
	}

	public String getGuarantyContractNo() {
		return guarantyContractNo;
	}

	public void setGuarantyContractNo(String guarantyContractNo) {
		this.guarantyContractNo = guarantyContractNo;
	}

	public String getGuarantyContractType() {
		return guarantyContractType;
	}

	public void setGuarantyContractType(String guarantyContractType) {
		this.guarantyContractType = guarantyContractType;
	}

	public String getCollateralType() {
		return collateralType;
	}

	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCollateralManageName() {
		return collateralManageName;
	}

	public void setCollateralManageName(String collateralManageName) {
		this.collateralManageName = collateralManageName;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCollateralManageType() {
		return collateralManageType;
	}

	public void setCollateralManageType(String collateralManageType) {
		this.collateralManageType = collateralManageType;
	}

	public String getCollateralDocumentType() {
		return collateralDocumentType;
	}

	public void setCollateralDocumentType(String collateralDocumentType) {
		this.collateralDocumentType = collateralDocumentType;
	}

	public String getCollateralIDCode() {
		return collateralIDCode;
	}

	public void setCollateralIDCode(String collateralIDCode) {
		this.collateralIDCode = collateralIDCode;
	}

	public String getCollateralAssessmentValue() {
		return collateralAssessmentValue;
	}

	public void setCollateralAssessmentValue(String collateralAssessmentValue) {
		this.collateralAssessmentValue = collateralAssessmentValue;
	}

	public String getCollateralThirdParty() {
		return collateralThirdParty;
	}

	public void setCollateralThirdParty(String collateralThirdParty) {
		this.collateralThirdParty = collateralThirdParty;
	}

	public String getInitialValuationDate() {
		return initialValuationDate;
	}

	public void setInitialValuationDate(String initialValuationDate) {
		this.initialValuationDate = initialValuationDate;
	}

	public String getLatestDate() {
		return latestDate;
	}

	public void setLatestDate(String latestDate) {
		this.latestDate = latestDate;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getGuarantorCapacityLimit() {
		return guarantorCapacityLimit;
	}

	public void setGuarantorCapacityLimit(String guarantorCapacityLimit) {
		this.guarantorCapacityLimit = guarantorCapacityLimit;
	}

	public String getApprovalMortgageRate() {
		return approvalMortgageRate;
	}

	public void setApprovalMortgageRate(String approvalMortgageRate) {
		this.approvalMortgageRate = approvalMortgageRate;
	}

	public String getPledgeRate() {
		return pledgeRate;
	}

	public void setPledgeRate(String pledgeRate) {
		this.pledgeRate = pledgeRate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}
	
	

}

