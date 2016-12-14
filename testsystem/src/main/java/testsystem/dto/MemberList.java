package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 成员名单表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MemberList")
public class MemberList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Id
	@Column(name = "countryCode", length = 50)
	@IColumn(description="国别代码")
	private String countryCode;
	
	@Column(name = "memberUnitName", length = 50)
	@IColumn(description="成员单位的名称")
	private String memberUnitName;

	
	@Column(name = "memberUnitCustomerCode", length = 50)
	@IColumn(description="成员单位的客户代码")
	private String memberUnitCustomerCode;
	
	@Column(name = "organizationCode", length = 50)
	@IColumn(description="组织机构代码")
	private String organizationCode;
	
	
	@Column(name = "registrationCode", length = 50)
	@IColumn(description="登记注册代码")
	private String registrationCode;

	@Column(name = "memberType", length = 50)
	@IColumn(description="成员类型")
	private String memberType;
	
	@Column(name = "parentCompanyLogo", length = 50)
	@IColumn(description="母公司标识")
	private String parentCompanyLogo;
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMemberUnitName() {
		return memberUnitName;
	}

	public void setMemberUnitName(String memberUnitName) {
		this.memberUnitName = memberUnitName;
	}

	public String getMemberUnitCustomerCode() {
		return memberUnitCustomerCode;
	}

	public void setMemberUnitCustomerCode(String memberUnitCustomerCode) {
		this.memberUnitCustomerCode = memberUnitCustomerCode;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getParentCompanyLogo() {
		return parentCompanyLogo;
	}

	public void setParentCompanyLogo(String parentCompanyLogo) {
		this.parentCompanyLogo = parentCompanyLogo;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}



}
