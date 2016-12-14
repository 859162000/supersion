package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 集团基本情况表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BasicSituationGroup")
public class BasicSituationGroup implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "customerName", length = 50)
	@IColumn(description="客户名称")
	private String customerName;
	
	@Id
	@Column(name = "customerCode", length = 50)
	@IColumn(description="客户代码/供应链编码")
	private String customerCode;

	
	@Column(name = "registrationNo", length = 50)
	@IColumn(description="工商注册编号")
	private String registrationNo;
	
	@Column(name = "groupNum", length = 50)
	@IColumn(description="集团成员数")
	private String groupNum;
	
	
	@Column(name = "creditType", length = 50)
	@IColumn(description="授信类型")
	private String creditType;
	

	@Column(name = "totalAssets", length = 50)
	@IColumn(description="资产总额")
	private String totalAssets;
	
	@Column(name = "totalLiabilities", length = 50)
	@IColumn(description="负债总额")
	private String totalLiabilities;
	
	@Column(name = "balanceType", length = 50)
	@IColumn(description="资产负债表类型")
	private String balanceType;
	
	@Column(name = "balanceDate", length = 50)
	@IColumn(description="资产负债表日期")
	private String balanceDate;
	
	@Column(name = "riskWarnSignal", length = 50)
	@IColumn(description="风险预警信号")
	private String riskWarnSignal;
	
	@Column(name = "interestEvent", length = 50)
	@IColumn(description="关注事件")
	private String interestEvent;
	
	@Column(name = "creditEateEesult", length = 50)
	@IColumn(description="信用评级结果")
	private String creditEateEesult;
	

	@Column(name = "registeredCountryOrRegion", length = 50)
	@IColumn(description="注册国家或地区")
	private String registeredCountryOrRegion;
					
	@Column(name = "countryCode", length = 50)
	@IColumn(description="国别代码")
	private String countryCode;
	
	
	@Column(name = "domesticRegisteAddress", length = 50)
	@IColumn(description="国内注册地址")
	private String domesticRegisteAddress;

					

	@Column(name = "administrativeDivisionCode", length = 50)
	@IColumn(description="行政区划代码")
	private String administrativeDivisionCode;
	
	@Column(name = "UpdateRegistrationDate", length = 50)
	@IColumn(description="更新注册信息日期")
	private String UpdateRegistrationDate;
	

	@Column(name = "officeAddress", length = 50)
	@IColumn(description="办公地址")
	private String officeAddress;
	
	
	@Column(name = "administrativeCode", length = 50)
	@IColumn(description="行政区划代码")
	private String administrativeCode;
	
	@Column(name = "updateOfficeDate", length = 50)
	@IColumn(description="更新办公地址日期")
	private String updateOfficeDate;

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

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
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

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getRiskWarnSignal() {
		return riskWarnSignal;
	}

	public void setRiskWarnSignal(String riskWarnSignal) {
		this.riskWarnSignal = riskWarnSignal;
	}

	public String getInterestEvent() {
		return interestEvent;
	}

	public void setInterestEvent(String interestEvent) {
		this.interestEvent = interestEvent;
	}

	public String getCreditEateEesult() {
		return creditEateEesult;
	}

	public void setCreditEateEesult(String creditEateEesult) {
		this.creditEateEesult = creditEateEesult;
	}

	public String getRegisteredCountryOrRegion() {
		return registeredCountryOrRegion;
	}

	public void setRegisteredCountryOrRegion(String registeredCountryOrRegion) {
		this.registeredCountryOrRegion = registeredCountryOrRegion;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDomesticRegisteAddress() {
		return domesticRegisteAddress;
	}

	public void setDomesticRegisteAddress(String domesticRegisteAddress) {
		this.domesticRegisteAddress = domesticRegisteAddress;
	}

	public String getAdministrativeDivisionCode() {
		return administrativeDivisionCode;
	}

	public void setAdministrativeDivisionCode(String administrativeDivisionCode) {
		this.administrativeDivisionCode = administrativeDivisionCode;
	}

	public String getUpdateRegistrationDate() {
		return UpdateRegistrationDate;
	}

	public void setUpdateRegistrationDate(String updateRegistrationDate) {
		UpdateRegistrationDate = updateRegistrationDate;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getAdministrativeCode() {
		return administrativeCode;
	}

	public void setAdministrativeCode(String administrativeCode) {
		this.administrativeCode = administrativeCode;
	}

	public String getUpdateOfficeDate() {
		return updateOfficeDate;
	}

	public void setUpdateOfficeDate(String updateOfficeDate) {
		this.updateOfficeDate = updateOfficeDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}
	
}
