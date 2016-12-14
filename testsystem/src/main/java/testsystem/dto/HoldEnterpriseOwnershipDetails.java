package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 持有企业股权明细表
 * holdEnterpriseOwnershipDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HoldEnterpriseOwnershipDetails")
public class HoldEnterpriseOwnershipDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Column(name = "bankholdCompanyName", length = 50)
	@IColumn(description="银行持股企业名称")
	private String bankholdCompanyName;
	
	@Id
	@Column(name = "bankholdCompanyCode", length = 50)
	@IColumn(description="银行持股企业代码")
	private String bankholdCompanyCode;
	
	
	@Column(name = "holdInstCode", length = 50)
	@IColumn(description="持有行机构代码")
	private String holdInstCode;
	
	
	@Column(name = "creditNum", length = 50)
	@IColumn(description="授信号码")
	private String creditNum;
	
	@Column(name = "bookBalance", length = 50)
	@IColumn(description="账面余额")
	private String bookBalance;
	
	public String getBankholdCompanyName() {
		return bankholdCompanyName;
	}

	public void setBankholdCompanyName(String bankholdCompanyName) {
		this.bankholdCompanyName = bankholdCompanyName;
	}

	public String getBankholdCompanyCode() {
		return bankholdCompanyCode;
	}

	public void setBankholdCompanyCode(String bankholdCompanyCode) {
		this.bankholdCompanyCode = bankholdCompanyCode;
	}

	public String getHoldInstCode() {
		return holdInstCode;
	}

	public void setHoldInstCode(String holdInstCode) {
		this.holdInstCode = holdInstCode;
	}

	public String getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(String creditNum) {
		this.creditNum = creditNum;
	}

	public String getBookBalance() {
		return bookBalance;
	}

	public void setBookBalance(String bookBalance) {
		this.bookBalance = bookBalance;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	
	

	
}