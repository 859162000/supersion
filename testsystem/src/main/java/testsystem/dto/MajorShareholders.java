package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 重要股东及主要关联企业
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MajorShareholders")
public class MajorShareholders implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "dtDate", length = 50)
	@IColumn(description="数据时间")
	private String dtDate;
	
	@Column(name = "associationType", length = 50)
	@IColumn(description="关联类型")
	private String associationType;
	
	@Column(name = "shareholdersName", length = 50)
	@IColumn(description="股东/关联企业名称")
	private String shareholdersName;
	
	
	@Column(name = "shareholdersType", length = 50)
	@IColumn(description="股东/关联企业类型")
	private String shareholdersType;
	
	@Column(name = "shareholderCertifType", length = 50)
	@IColumn(description="股东/关联企业证件类型")
	private String shareholderCertifType;
	
	@Id
	@Column(name = "shareholdersIdCode", length = 50)
	@IColumn(description="股东/关联企业证件代码")
	private String shareholdersIdCode;
	

	@Column(name = "registrationCode", length = 50)
	@IColumn(description="登记注册代码")
	private String registrationCode;
	
	@Column(name = "shareholdersCustomerCode", length = 50)
	@IColumn(description="股东/关联企业客户代码")
	private String shareholdersCustomerCode;
	
	@Column(name = "countryCode", length = 50)
	@IColumn(description="国别代码 ")
	private String countryCode;
	
	@Column(name = "shareholdRatio", length = 50)
	@IColumn(description="持股比例")
	private String shareholdRatio;
	
	@Column(name = "shareholderCorrespondDate", length = 50)
	@IColumn(description="股东结构对应日期")
	private String shareholderCorrespondDate;
	
	@Column(name = "updateDate", length = 50)
	@IColumn(description="更新信息日期")
	private String updateDate;

	@Column(name = "actualController", length = 50)
	@IColumn(description="实际控制人标识")
	private String actualController;

	public String getAssociationType() {
		return associationType;
	}

	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	public String getShareholdersName() {
		return shareholdersName;
	}

	public void setShareholdersName(String shareholdersName) {
		this.shareholdersName = shareholdersName;
	}

	public String getShareholdersType() {
		return shareholdersType;
	}

	public void setShareholdersType(String shareholdersType) {
		this.shareholdersType = shareholdersType;
	}

	public String getShareholderCertifType() {
		return shareholderCertifType;
	}

	public void setShareholderCertifType(String shareholderCertifType) {
		this.shareholderCertifType = shareholderCertifType;
	}

	public String getShareholdersIdCode() {
		return shareholdersIdCode;
	}

	public void setShareholdersIdCode(String shareholdersIdCode) {
		this.shareholdersIdCode = shareholdersIdCode;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public String getShareholdersCustomerCode() {
		return shareholdersCustomerCode;
	}

	public void setShareholdersCustomerCode(String shareholdersCustomerCode) {
		this.shareholdersCustomerCode = shareholdersCustomerCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getShareholdRatio() {
		return shareholdRatio;
	}

	public void setShareholdRatio(String shareholdRatio) {
		this.shareholdRatio = shareholdRatio;
	}

	public String getShareholderCorrespondDate() {
		return shareholderCorrespondDate;
	}

	public void setShareholderCorrespondDate(String shareholderCorrespondDate) {
		this.shareholderCorrespondDate = shareholderCorrespondDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getActualController() {
		return actualController;
	}

	public void setActualController(String actualController) {
		this.actualController = actualController;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}
	
	
}

