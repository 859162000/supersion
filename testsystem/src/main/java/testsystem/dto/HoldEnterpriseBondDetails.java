package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;
/**
 * 持有企业债券明细表
 * holdEnterpriseBondDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HoldEnterpriseBondDetails")
public class HoldEnterpriseBondDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "banksHoldbondsName", length = 50)
	@IColumn(description="银行持债企业名称")
	private String banksHoldbondsName;
	
	@Id
	@Column(name = "banksHoldbondsCode", length = 50)
	@IColumn(description="银行持债企业代码")
	private String banksHoldbondsCode;
	
	
	@Column(name = "bankholdInstCode", length = 50)
	@IColumn(description="持有行机构代码")
	private String bankholdInstCode;
	
	
	@Column(name = "creditNum", length = 50)
	@IColumn(description="授信号码")
	private String creditNum;
	
	@Column(name = "bondCode", length = 50)
	@IColumn(description="债券代码")
	private String bondCode;
	
	@Column(name = "bondType", length = 50)
	@IColumn(description="债券类型")
	private String bondType;
	
	@Column(name = "bondValue", length = 50)
	@IColumn(description="债券面值")
	private String bondValue;
	
	@Column(name = "bookBalance", length = 50)
	@IColumn(description="账面余额")
	private String bookBalance;
	
	@Column(name = "issuingDate", length = 50)
	@IColumn(description="发行日期")
	private String issuingDate;
	
	@Column(name = "expiryDate", length = 50)
	@IColumn(description="到期兑付日期")
	private String expiryDate;
	
	@Column(name = "accountType", length = 50)
	@IColumn(description="账户类型")
	private String accountType;
	
	@Column(name = "internalRate", length = 50)
	@IColumn(description="内部评级")
	private String internalRate;
	
	@Column(name = "externalRate", length = 50)
	@IColumn(description="外部评级")
	private String externalRate;

	public String getBanksHoldbondsName() {
		return banksHoldbondsName;
	}

	public void setBanksHoldbondsName(String banksHoldbondsName) {
		this.banksHoldbondsName = banksHoldbondsName;
	}

	public String getBanksHoldbondsCode() {
		return banksHoldbondsCode;
	}

	public void setBanksHoldbondsCode(String banksHoldbondsCode) {
		this.banksHoldbondsCode = banksHoldbondsCode;
	}

	public String getBankholdInstCode() {
		return bankholdInstCode;
	}

	public void setBankholdInstCode(String bankholdInstCode) {
		this.bankholdInstCode = bankholdInstCode;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public String getBondCode() {
		return bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondType() {
		return bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	public String getBondValue() {
		return bondValue;
	}

	public void setBondValue(String bondValue) {
		this.bondValue = bondValue;
	}

	public String getBookBalance() {
		return bookBalance;
	}

	public void setBookBalance(String bookBalance) {
		this.bookBalance = bookBalance;
	}

	public String getIssuingDate() {
		return issuingDate;
	}

	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getInternalRate() {
		return internalRate;
	}

	public void setInternalRate(String internalRate) {
		this.internalRate = internalRate;
	}

	public String getExternalRate() {
		return externalRate;
	}

	public void setExternalRate(String externalRate) {
		this.externalRate = externalRate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}
	
	
}