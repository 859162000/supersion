package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * ¶Ô¹«¿Í»§µ£±£Çé¿öÍ³¼Æ
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CustomerGuaranteeState")
public class CustomerGuaranteeState implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="Êý¾ÝÊ±¼ä")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "customerName", length = 50)
	@IColumn(description="¿Í»§Ãû³Æ")
	private String customerName;
	
	@Id
	@Column(name = "customerCode", length = 50)
	@IColumn(description="¿Í»§´úÂë")
	private String customerCode;
	
	
	@Column(name = "loansBusinessContractNo", length = 50)
	@IColumn(description="´û¿î»ò±íÍâÒµÎñºÏÍ¬ºÅ")
	private String loansBusinessContractNo;
	
	@Column(name = "guarantyContractNo", length = 50)
	@IColumn(description="µ£±£ºÏÍ¬ºÅ")
	private String guarantyContractNo;
	
	
	@Column(name = "guarantyContractType", length = 50)
	@IColumn(description="µ£±£ºÏÍ¬ÀàÐÍ")
	private String guarantyContractType;
	

	@Column(name = "collateralType", length = 50)
	@IColumn(description="ÑºÆ·ÀàÐÍ")
	private String collateralType;
	
	@Column(name = "productName", length = 50)
	@IColumn(description="ÑºÆ·Ãû³Æ")
	private String productName;
	
	@Column(name = "securityCode", length = 50)
	@IColumn(description="ÑºÆ·´úÂë ")
	private String securityCode;
	
	@Column(name = "collateralName", length = 50)
	@IColumn(description="ÑºÆ·È¨ÊôÈË£¨»ò±£Ö¤ÈË£©Ãû³Æ")
	private String collateralName;
	
	@Column(name = "collateralTypes", length = 50)
	@IColumn(description="ÑºÆ·È¨ÊôÈË£¨»ò±£Ö¤ÈË£©ÀàÐÍ")
	private String collateralTypes;
	
	@Column(name = "collateralDocumentType", length = 50)
	@IColumn(description="ÑºÆ·È¨ÊôÈË£¨»ò±£Ö¤ÈË£©Ö¤¼þÀàÐÍ")
	private String collateralDocumentType;

	@Column(name = "collateralIDCode", length = 50)
	@IColumn(description="ÑºÆ·È¨ÊôÈË£¨»ò±£Ö¤ÈË£©Ö¤¼þ´úÂë")
	private String collateralIDCode;
	
	@Column(name = "collateralAssessValue", length = 50)
	@IColumn(description="ÑºÆ·ÆÀ¹À¼ÛÖµ£¨»ò±£Ö¤½ð¶î£©")
	private String collateralAssessValue;

	
	@Column(name = "collateralThirdParty", length = 50)
	@IColumn(description="ÑºÆ·È¨ÊôÈËÊÇ·ñµÚÈý·½")
	private String collateralThirdParty;

	
	
	@Column(name = "initialValuationDate", length = 50)
	@IColumn(description="Ê×´Î¹ÀÖµÈÕÆÚ")
	private String initialValuationDate;

	
	@Column(name = "latestValuationDate", length = 50)
	@IColumn(description="×îÐÂ¹ÀÖµÈÕÆÚ")
	private String latestValuationDate;

	
	@Column(name = "valuationDate", length = 50)
	@IColumn(description="¹ÀÖµµ½ÆÚÈÕÆÚ")
	private String valuationDate;

	
	@Column(name = "guarantorCapacityLimit", length = 50)
	@IColumn(description="±£Ö¤ÈË±£Ö¤ÄÜÁ¦ÉÏÏÞ")
	private String guarantorCapacityLimit;

	@Column(name = "approvalMortgageRate", length = 50)
	@IColumn(description="ÉóÅúµÖÖÊÑºÂÊ")
	private String approvalMortgageRate;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getLoansBusinessContractNo() {
		return loansBusinessContractNo;
	}

	public void setLoansBusinessContractNo(String loansBusinessContractNo) {
		this.loansBusinessContractNo = loansBusinessContractNo;
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

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCollateralName() {
		return collateralName;
	}

	public void setCollateralName(String collateralName) {
		this.collateralName = collateralName;
	}

	public String getCollateralTypes() {
		return collateralTypes;
	}

	public void setCollateralTypes(String collateralTypes) {
		this.collateralTypes = collateralTypes;
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

	public String getCollateralAssessValue() {
		return collateralAssessValue;
	}

	public void setCollateralAssessValue(String collateralAssessValue) {
		this.collateralAssessValue = collateralAssessValue;
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

	public String getLatestValuationDate() {
		return latestValuationDate;
	}

	public void setLatestValuationDate(String latestValuationDate) {
		this.latestValuationDate = latestValuationDate;
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

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	
}

