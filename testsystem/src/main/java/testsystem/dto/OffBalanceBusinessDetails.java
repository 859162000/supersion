package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 表外业务明细表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OffBalanceBusinessDetails")
public class OffBalanceBusinessDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "offBusinessCustomerName", length = 50)
	@IColumn(description="表外业务客户名称")
	private String offBusinessCustomerName;
	
	@Id
	@Column(name = "offBusinessCustomerCode", length = 50)
	@IColumn(description="表外业务客户代码")
	private String offBusinessCustomerCode;
	
	
	@Column(name = "undertakeBankCode", length = 50)
	@IColumn(description="承办银行机构代码")
	private String undertakeBankCode;
	
	@Column(name = "offSheetBusinessTypes", length = 50)
	@IColumn(description="表外业务类型")
	private String offSheetBusinessTypes;
	
	
	@Column(name = "creditNum", length = 50)
	@IColumn(description="授信号码")
	private String creditNum;
	

	@Column(name = "contractNo", length = 50)
	@IColumn(description="合同号")
	private String contractNo;
	
	@Column(name = "businessNum", length = 50)
	@IColumn(description="业务号码")
	private String businessNum;
	
	@Column(name = "bussinessBanlance", length = 50)
	@IColumn(description="业务余额")
	private String bussinessBanlance;
	
	@Column(name = "currencyCode", length = 50)
	@IColumn(description="币种代码")
	private String currencyCode;
	
	@Column(name = "occurrenceDate", length = 50)
	@IColumn(description="发生日期")
	private String occurrenceDate;
	
	@Column(name = "expirationDate", length = 50)
	@IColumn(description="到期日期")
	private String expirationDate;
	
	@Column(name = "depositAmount", length = 50)
	@IColumn(description="保证金金额")
	private String depositAmount;
	

	@Column(name = "adjustIndustStructType", length = 50)
	@IColumn(description="产业结构调整类型")
	private String adjustIndustStructType;
					
	@Column(name = "identificatIndustTrans", length = 50)
	@IColumn(description="工业转型升级标识")
	private String identificatIndustTrans;
	
	
	@Column(name = "strategicEmergindustrytype", length = 50)
	@IColumn(description="战略新兴产业类型")
	private String strategicEmergindustrytype;


	public String getOffBusinessCustomerName() {
		return offBusinessCustomerName;
	}

	public void setOffBusinessCustomerName(String offBusinessCustomerName) {
		this.offBusinessCustomerName = offBusinessCustomerName;
	}

	public String getOffBusinessCustomerCode() {
		return offBusinessCustomerCode;
	}

	public void setOffBusinessCustomerCode(String offBusinessCustomerCode) {
		this.offBusinessCustomerCode = offBusinessCustomerCode;
	}

	public String getUndertakeBankCode() {
		return undertakeBankCode;
	}

	public void setUndertakeBankCode(String undertakeBankCode) {
		this.undertakeBankCode = undertakeBankCode;
	}

	public String getOffSheetBusinessTypes() {
		return offSheetBusinessTypes;
	}

	public void setOffSheetBusinessTypes(String offSheetBusinessTypes) {
		this.offSheetBusinessTypes = offSheetBusinessTypes;
	}
	

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	
	public String getBussinessBanlance() {
		return bussinessBanlance;
	}

	public void setBussinessBanlance(String bussinessBanlance) {
		this.bussinessBanlance = bussinessBanlance;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getOccurrenceDate() {
		return occurrenceDate;
	}

	public void setOccurrenceDate(String occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}
	
	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getAdjustIndustStructType() {
		return adjustIndustStructType;
	}

	public void setAdjustIndustStructType(String adjustIndustStructType) {
		this.adjustIndustStructType = adjustIndustStructType;
	}

	public void setIdentificatIndustTrans(String identificatIndustTrans) {
		this.identificatIndustTrans = identificatIndustTrans;
	}

	public String getIdentificatIndustTrans() {
		return identificatIndustTrans;
	}
	

	public String getStrategicEmergindustrytype() {
		return strategicEmergindustrytype;
	}

	public void setStrategicEmergindustrytype(String strategicEmergindustrytype) {
		this.strategicEmergindustrytype = strategicEmergindustrytype;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	

	
}
