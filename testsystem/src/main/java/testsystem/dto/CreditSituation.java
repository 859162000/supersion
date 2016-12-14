package testsystem.dto;


import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import framework.interfaces.IColumn;
import framework.security.SecurityContext;
import framework.show.ShowContext;

/**
 * ÊÚÐÅÇé¿ö
 * creditSituation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CreditSituation")
public class CreditSituation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@IColumn(description="Êý¾ÝÊ±¼ä")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@IColumn(tagMethodName="getProductTag",description="¿Í»§ÀàÐÍ")
	@Column(name = "customerType", length = 50)
	private String customerType;
	
	public static Map<String,String> getProductTag() throws Exception{
		
		return ShowContext.getInstance().getShowEntityMap().get("product");
	}
	
	@IColumn(description="¿Í»§Ãû³Æ")
	@Column(name = "customerName", length = 50)
	private String customerName;
	
	@Id
	@IColumn(description="¿Í»§´úÂë")
	@Column(name = "customerCode", length = 50)
	private String customerCode;
	
	@Column(name = "customerNationalCode", length = 50)
	@IColumn(description="¿Í»§¹ú¼®´úÂë")
	private String customerNationalCode;
	
	@Column(name = "creditBankCode", length = 50)
	@IColumn(description="ÊÚÐÅÒøÐÐ´úÂë")
	private String creditBankCode;
	
	@Column(name = "creditBankName", length = 50)
	@IColumn(description="ÊÚÐÅÒøÐÐÃû³Æ")
	private String creditBankName;
	
	@Column(name = "creditNumber", length = 50)
	@IColumn(description="ÊÚÐÅºÅÂë")
	private String creditNumber;
	
	@Column(name = "creditFacility", length = 50)
	@IColumn(description="ÊÚÐÅ¶î¶È")
	private String creditFacility;
	
	@Column(name = "loancreditFacility", length = 50)
	@IColumn(description="´û¿îÊÚÐÅ¶î¶È")
	private String loancreditFacility;
	
	
	@Column(name = "creditStartDate", length = 50)
	@IColumn(description="ÊÚÐÅÆðÊ¼ÈÕÆÚ")
	private String creditStartDate;
	
	@Column(name = "creditEndDate", length = 50)
	@IColumn(description="ÊÚÐÅµ½ÆÚÈÕÆÚ")
	private String creditEndDate;
	
	@Column(name = "outstandLoan", length = 50)
	@IColumn(description="´û¿îÓà¶î")
	private String outstandLoan;
	
	@Column(name = "holdBondBalance", length = 50)
	@IColumn(description="³ÖÓÐÕ®¾íÓà¶î")
	private String holdBondBalance;
	
	@Column(name = "holdEquityBalance", length = 50)
	@IColumn(description="³ÖÓÐ¹ÉÈ¨Óà¶î")
	private String holdEquityBalance;
	
	@Column(name = "CreditRiskAssetsBalance", length = 50)
	@IColumn(description="ÆäËü±íÄÚÐÅÓÃ·çÏÕ×Ê²úÓà¶î")
	private String CreditRiskAssetsBalance;
	
	@Column(name = "offBusinessBalance", length = 50)
	@IColumn(description="±íÍâÒµÎñÓà¶î")
	private String offBusinessBalance;

	@Column(name = "businessOnCreditBalance", length = 50)
	@IColumn(description="ÏÖÓÐÒµÎñÓà¶îÕ¼ÓÃÊÚÐÅ¶î¶È")
	private String businessOnCreditBalance;
	
	@Column(name = "loanBalanceOnCreditLoan", length = 50)
	@IColumn(description="´û¿îÓà¶îÕ¼ÓÃ´û¿îÊÚÐÅ¶î")
	private String loanBalanceOnCreditLoan;
	
	@Column(name = "fairUseCredit", length = 50)
	@IColumn(description="ÉÐ¿ÉÊ¹ÓÃÊÚÐÅ¶î¶È")
	private String fairUseCredit ;
	
	@Column(name = "faiUseCreditLoans", length = 50)
	@IColumn(description="ÉÐ¿ÉÊ¹ÓÃ´û¿îÊÚÐÅ¶î¶È")
	private String faiUseCreditLoans;
	
	@Column(name = "affiliatedPartyCredit", length = 50)
	@IColumn(description="¹ØÁª·½ÊÚÐÅ±êÊ¶")
	private String affiliatedPartyCredit;
	

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerNationalCode(String customerNationalCode) {
		this.customerNationalCode = customerNationalCode;
	}
	public String getCustomerNationalCode() {
		return customerNationalCode;
	}
	public void setCreditBankCode(String creditBankCode) {
		this.creditBankCode = creditBankCode;
	}
	public String getCreditBankCode() {
		return creditBankCode;
	}
	public void setCreditBankName(String creditBankName) {
		this.creditBankName = creditBankName;
	}
	public String getCreditBankName() {
		return creditBankName;
	}
	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}
	public String getCreditNumber() {
		return creditNumber;
	}
	public void setCreditFacility(String creditFacility) {
		this.creditFacility = creditFacility;
	}
	public String getCreditFacility() {
		return creditFacility;
	}
	public void setLoancreditFacility(String loancreditFacility) {
		this.loancreditFacility = loancreditFacility;
	}
	public String getLoancreditFacility() {
		return loancreditFacility;
	}
	public void setCreditStartDate(String creditStartDate) {
		this.creditStartDate = creditStartDate;
	}
	public String getCreditStartDate() {
		return creditStartDate;
	}
	public void setCreditEndDate(String creditEndDate) {
		this.creditEndDate = creditEndDate;
	}
	public String getCreditEndDate() {
		return creditEndDate;
	}
	public void setHoldBondBalance(String holdBondBalance) {
		this.holdBondBalance = holdBondBalance;
	}
	public String getHoldBondBalance() {
		return holdBondBalance;
	}
	public void setOutstandLoan(String outstandLoan) {
		this.outstandLoan = outstandLoan;
	}
	public String getOutstandLoan() {
		return outstandLoan;
	}
	public void setHoldEquityBalance(String holdEquityBalance) {
		this.holdEquityBalance = holdEquityBalance;
	}
	public String getHoldEquityBalance() {
		return holdEquityBalance;
	}
	public void setCreditRiskAssetsBalance(String creditRiskAssetsBalance) {
		CreditRiskAssetsBalance = creditRiskAssetsBalance;
	}
	public String getCreditRiskAssetsBalance() {
		return CreditRiskAssetsBalance;
	}
	public void setOffBusinessBalance(String offBusinessBalance) {
		this.offBusinessBalance = offBusinessBalance;
	}
	public String getOffBusinessBalance() {
		return offBusinessBalance;
	}
	public void setBusinessOnCreditBalance(String businessOnCreditBalance) {
		this.businessOnCreditBalance = businessOnCreditBalance;
	}
	public String getBusinessOnCreditBalance() {
		return businessOnCreditBalance;
	}
	public void setLoanBalanceOnCreditLoan(String loanBalanceOnCreditLoan) {
		this.loanBalanceOnCreditLoan = loanBalanceOnCreditLoan;
	}
	public String getLoanBalanceOnCreditLoan() {
		return loanBalanceOnCreditLoan;
	}
	public void setFairUseCredit(String fairUseCredit) {
		this.fairUseCredit = fairUseCredit;
	}
	public String getFairUseCredit() {
		return fairUseCredit;
	}
	public void setFaiUseCreditLoans(String faiUseCreditLoans) {
		this.faiUseCreditLoans = faiUseCreditLoans;
	}
	public String getFaiUseCreditLoans() {
		return faiUseCreditLoans;
	}
	public void setAffiliatedPartyCredit(String affiliatedPartyCredit) {
		this.affiliatedPartyCredit = affiliatedPartyCredit;
	}
	public String getAffiliatedPartyCredit() {
		return affiliatedPartyCredit;
	}
	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}
	public String getDtDate() {
		return dtDate;
	}
	
}