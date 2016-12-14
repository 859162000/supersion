package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 实际控制人表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ActualController")
public class ActualController implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
							

	@Column(name = "actualControllerType", length = 50)
	@IColumn(description="实际控制人类型")
	private String actualControllerType;
	
	@Id
	@Column(name = "countryCode", length = 50)
	@IColumn(description="国别代码")
	private String countryCode;

	
	@Column(name = "actualControllerName", length = 50)
	@IColumn(description="实际控制人名称")
	private String actualControllerName;
	
	
	@Column(name = "actualControllerCustomerCode", length = 50)
	@IColumn(description="实际控制人的客户代码")
	private String actualControllerCustomerCode;
	
	
	@Column(name = "actualControllerIDType", length = 50)
	@IColumn(description="实际控制人证件类型")
	private String actualControllerIDType;

	@Column(name = "actualControllerIDCode", length = 50)
	@IColumn(description="实际控制人证件代码")
	private String actualControllerIDCode;
	
	
	@Column(name = "registrationCode", length = 50)
	@IColumn(description="登记注册代码")
	private String registrationCode;
	
	public String getActualControllerType() {
		return actualControllerType;
	}

	public void setActualControllerType(String actualControllerType) {
		this.actualControllerType = actualControllerType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getActualControllerName() {
		return actualControllerName;
	}

	public void setActualControllerName(String actualControllerName) {
		this.actualControllerName = actualControllerName;
	}

	public String getActualControllerCustomerCode() {
		return actualControllerCustomerCode;
	}

	public void setActualControllerCustomerCode(String actualControllerCustomerCode) {
		this.actualControllerCustomerCode = actualControllerCustomerCode;
	}

	public String getActualControllerIDType() {
		return actualControllerIDType;
	}

	public void setActualControllerIDType(String actualControllerIDType) {
		this.actualControllerIDType = actualControllerIDType;
	}

	public String getActualControllerIDCode() {
		return actualControllerIDCode;
	}

	public void setActualControllerIDCode(String actualControllerIDCode) {
		this.actualControllerIDCode = actualControllerIDCode;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}


	

}
