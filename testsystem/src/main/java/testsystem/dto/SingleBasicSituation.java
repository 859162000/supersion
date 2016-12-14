package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 单一法人基本情况 表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SingleBasicSituation")
public class SingleBasicSituation implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	
	@Column(name = "sing_CustomerName", length = 50)
	@IColumn(description="客户名称 ")
	private String sing_CustomerName;
	
	@Id
	@Column(name = "customerCode", length = 50)
	@IColumn(description="客户代码")
	private String customerCode;
	
	
	@Column(name = "organizationCode", length = 50)
	@IColumn(description="组织机构代码")
	private String organizationCode;
	
	@Column(name = "organizeRegistre", length = 50)
	@IColumn(description="组织机构登记/年检/更新日期")
	private String organizeRegistrate;
	
	@Column(name = "registrationCode", length = 50)
	@IColumn(description="登记注册代码")
	private String registrationCode;
	

	@Column(name = "registration", length = 50)
	@IColumn(description="登记注册/年检/更新日期")
	private String registration;
	
	@Column(name = "registeredCountry", length = 50)
	@IColumn(description="注册国家或地区")
	private String registeredCountry;
	
	@Column(name = "countryCode", length = 50)
	@IColumn(description="国别代码 ")
	private String countryCode;
	
	@Column(name = "registeredAddress", length = 50)
	@IColumn(description="注册地址")
	private String registeredAddress;
	
	@Column(name = "adminDivisionCode", length = 50)
	@IColumn(description="行政区划代码")
	private String adminDivisionCode;
	
	@Column(name = "corporationCode", length = 50)
	@IColumn(description="上市公司标志-国别代码-上市公司代码")
	private String corporationCode;
	
	@Column(name = "riskWarningSignals", length = 50)
	@IColumn(description="风险预警信号 ")
	private String riskWarningSignals;
	

	@Column(name = "interestEvent", length = 50)
	@IColumn(description="关注事件")
	private String interestEvent;
					
	@Column(name = "probability", length = 50)
	@IColumn(description="违约概率 ")
	private String probability;
	
	
	@Column(name = "creditRateResults", length = 50)
	@IColumn(description="信用评级结果")
	private String creditRateResults;
	
	@Column(name = "totalAssets", length = 50)
	@IColumn(description="资产总额")
	private String totalAssets;
	
	@Column(name = "totalLiabilitie", length = 50)
	@IColumn(description="负债总额")
	private String totalLiabilitie;
	
	@Column(name = "preTaxProfit", length = 50)
	@IColumn(description="税前利润")
	private String preTaxProfit;
	
	@Column(name = "mainBusinessIncome", length = 50)
	@IColumn(description="主营业务收入")
	private String mainBusinessIncome;
	
	@Column(name = "stock", length = 50)
	@IColumn(description="存货")
	private String stock;

	@Column(name = "accountsReceivable", length = 50)
	@IColumn(description="应收账款")
	private String accountsReceivable;

	@Column(name = "otherReceivables", length = 50)
	@IColumn(description="其他应收款")
	private String otherReceivables;

	@Column(name = "totalCurrentAssets", length = 50)
	@IColumn(description="流动资产合计")
	private String totalCurrentAssets;
	
	@Column(name = "totalLiabilities", length = 50)
	@IColumn(description="流动负债合计")
	private String totalLiabilities;

	
	@Column(name = "financialStateType", length = 50)
	@IColumn(description="财务报表类型 ")
	private String financialStateType;
	
	@Column(name = "financialStateDate", length = 50)
	@IColumn(description="财务报表日期")
	private String financialStateDate;
	
	@Column(name = "customersType", length = 50)
	@IColumn(description="客户类型")
	private String customersType;
	
	@Column(name = "industryCode", length = 50)
	@IColumn(description="客户所属行业代码")
	private String industryCode;
	
	@Column(name = "creditCardNumber", length = 50)
	@IColumn(description="贷款卡号")
	private String creditCardNumber;
	
	@Column(name = "environmentRiskClass", length = 50)
	@IColumn(description="环境和社会风险分类")
	private String environmentRiskClass;
	
	@Column(name = "loanType", length = 50)
	@IColumn(description="贷款类型")
	private String loanType;


	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizeRegistrate() {
		return organizeRegistrate;
	}

	public void setOrganizeRegistrate(String organizeRegistrate) {
		this.organizeRegistrate = organizeRegistrate;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getRegisteredCountry() {
		return registeredCountry;
	}

	public void setRegisteredCountry(String registeredCountry) {
		this.registeredCountry = registeredCountry;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getAdminDivisionCode() {
		return adminDivisionCode;
	}

	public void setAdminDivisionCode(String adminDivisionCode) {
		this.adminDivisionCode = adminDivisionCode;
	}

	public String getCorporationCode() {
		return corporationCode;
	}

	public void setCorporationCode(String corporationCode) {
		this.corporationCode = corporationCode;
	}

	public String getRiskWarningSignals() {
		return riskWarningSignals;
	}

	public void setRiskWarningSignals(String riskWarningSignals) {
		this.riskWarningSignals = riskWarningSignals;
	}

	public String getInterestEvent() {
		return interestEvent;
	}

	public void setInterestEvent(String interestEvent) {
		this.interestEvent = interestEvent;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public String getCreditRateResults() {
		return creditRateResults;
	}

	public void setCreditRateResults(String creditRateResults) {
		this.creditRateResults = creditRateResults;
	}

	public String getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}

	public String getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(String totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public String getPreTaxProfit() {
		return preTaxProfit;
	}

	public void setPreTaxProfit(String preTaxProfit) {
		this.preTaxProfit = preTaxProfit;
	}

	public String getMainBusinessIncome() {
		return mainBusinessIncome;
	}

	public void setMainBusinessIncome(String mainBusinessIncome) {
		this.mainBusinessIncome = mainBusinessIncome;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getAccountsReceivable() {
		return accountsReceivable;
	}

	public void setAccountsReceivable(String accountsReceivable) {
		this.accountsReceivable = accountsReceivable;
	}

	public String getOtherReceivables() {
		return otherReceivables;
	}

	public void setOtherReceivables(String otherReceivables) {
		this.otherReceivables = otherReceivables;
	}

	public String getTotalCurrentAssets() {
		return totalCurrentAssets;
	}

	public void setTotalCurrentAssets(String totalCurrentAssets) {
		this.totalCurrentAssets = totalCurrentAssets;
	}

	public String getFinancialStateType() {
		return financialStateType;
	}

	public void setFinancialStateType(String financialStateType) {
		this.financialStateType = financialStateType;
	}

	public String getFinancialStateDate() {
		return financialStateDate;
	}

	public void setFinancialStateDate(String financialStateDate) {
		this.financialStateDate = financialStateDate;
	}

	public String getCustomersType() {
		return customersType;
	}

	public void setCustomersType(String customersType) {
		this.customersType = customersType;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getEnvironmentRiskClass() {
		return environmentRiskClass;
	}

	public void setEnvironmentRiskClass(String environmentRiskClass) {
		this.environmentRiskClass = environmentRiskClass;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setSing_CustomerName(String sing_CustomerName) {
		this.sing_CustomerName = sing_CustomerName;
	}

	public String getSing_CustomerName() {
		return sing_CustomerName;
	}

	public void setTotalLiabilitie(String totalLiabilitie) {
		this.totalLiabilitie = totalLiabilitie;
	}

	public String getTotalLiabilitie() {
		return totalLiabilitie;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanType() {
		return loanType;
	}


	



	

	
}
