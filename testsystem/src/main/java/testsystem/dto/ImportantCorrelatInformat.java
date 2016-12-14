package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 高管及重要关联人信息表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ImportantCorrelatInformat")
public class ImportantCorrelatInformat implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;		
	

	@Column(name = "relationshipType", length = 50)
	@IColumn(description="关系人类型")
	private String relationshipType;
	
	@Column(name = "name", length = 50)
	@IColumn(description="姓名")
	private String name;

	@Id
	@Column(name = "countryCodes", length = 50)
	@IColumn(description="国别代码")
	private String countryCodes;
	
	
	@Column(name = "identityCardNumber", length = 50)
	@IColumn(description="身份证号码")
	private String identityCardNumber;
	
	
	@Column(name = "passportNumber", length = 50)
	@IColumn(description="护照号码")
	private String passportNumber;

	@Column(name = "issueDate", length = 50)
	@IColumn(description="签发日期")
	private String issueDate;
	
	@Column(name = "expirationDate", length = 50)
	@IColumn(description="到期日期")
	private String expirationDate;

	@Column(name = "documentType", length = 50)
	@IColumn(description="证件类型")
	private String documentType;
					
	@Column(name = "documentCode", length = 50)
	@IColumn(description="证件号码")
	private String documentCode;
	
	
	@Column(name = "updateDate", length = 50)
	@IColumn(description="更新信息日期")
	private String updateDate;


	public String getRelationshipType() {
		return relationshipType;
	}


	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCountryCodes() {
		return countryCodes;
	}


	public void setCountryCodes(String countryCodes) {
		this.countryCodes = countryCodes;
	}


	public String getIdentityCardNumber() {
		return identityCardNumber;
	}


	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}


	public String getPassportNumber() {
		return passportNumber;
	}


	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}


	public String getIssueDate() {
		return issueDate;
	}


	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}


	public String getExpirationDate() {
		return expirationDate;
	}


	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}


	public String getDocumentType() {
		return documentType;
	}


	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}


	public String getDocumentCode() {
		return documentCode;
	}


	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}


	public String getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}


	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}


	public String getDtDate() {
		return dtDate;
	}

					

}
