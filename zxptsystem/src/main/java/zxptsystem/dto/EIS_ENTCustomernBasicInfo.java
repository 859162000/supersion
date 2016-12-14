package zxptsystem.dto;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import extend.helper.HelpTool;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "EIS_ENTCustomernBasicInfo")
@IEntity(description= "企业客户信息")
public class EIS_ENTCustomernBasicInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strCustomerID", length = 9)
	@IColumn(description="授权客户代码",isSingleTagHidden=true)
	private String strCustomerID;
	
	@Column(name = "strCustomerName", length = 100, nullable = false)
	@IColumn(description="授权客户名称",isKeyName=true)
	private String strCustomerName;
	
	@Column(name = "strCUSCreditInstitutionsCode", length = 18, nullable = true)
	@IColumn(description="客户机构信用代码")
	private String strCUSCreditInstitutionsCode;
	
	@Column(name = "strInCode", length = 50, nullable = true)
	@IColumn(description="中征码")
	private String strInCode;
	
	@Column(name = "strOrganizationCode", length = 10, nullable = true)
	@IColumn(description="组织机构代码")
	private String strOrganizationCode;
		
	@IColumn(tagMethodName="getRegistrationTypeTag",description="登记注册类型")
	@Column(name = "strRegistrationType", nullable =true,length=2)
	private String strRegistrationType;
	
	public static Map<String,String> getRegistrationTypeTag() {
		try {
			return HelpTool.getSystemConstVal("RegistrationType");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "strRegistrationNo", length = 50, nullable = true)
	@IColumn(description="登记注册号")
	private String strRegistrationNo;
	
	@Column(name = "strTaxpayerIdentLandNo", length = 20, nullable = true)
	@IColumn(description="纳税人识别号（地税）")
	private String strTaxpayerIdentLandNo;
	
	@Column(name = "strTaxpayerIdentStateNo", length = 20, nullable = true)
	@IColumn(description="纳税人识别号（国税）")
	private String strTaxpayerIdentStateNo;
	
	@Column(name = "AuthorizationIntegrity", nullable = true,length = 3)
	@IColumn(description="授权信息完整度",isSingleTagHidden=true)
	private String AuthorizationIntegrity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInnerInstCode")
	@IColumn(description="机构编码",isSingleTagHidden=true)
	private InstInfo strInnerInstCode;
	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name = "strUserID")
	@IColumn(description="用户ID",isSingleTagHidden=true)
	private UserInfo strUserID;
	
	@Column(name = "remark", length = 255, nullable = true)
	@IColumn(description="备注")
	private String remark;
	
	public String getStrCustomerID() {
		return strCustomerID;
	}
	public void setStrCustomerID(String strCustomerID) {
		this.strCustomerID = strCustomerID;
	}
	public String getStrCustomerName() {
		return strCustomerName;
	}
	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}

	public String getStrCUSCreditInstitutionsCode() {
		return strCUSCreditInstitutionsCode;
	}
	public void setStrCUSCreditInstitutionsCode(String strCUSCreditInstitutionsCode) {
		this.strCUSCreditInstitutionsCode = strCUSCreditInstitutionsCode;
	}
	public String getStrInCode() {
		return strInCode;
	}
	public void setStrInCode(String strInCode) {
		this.strInCode = strInCode;
	}
	public String getStrOrganizationCode() {
		return strOrganizationCode;
	}
	public void setStrOrganizationCode(String strOrganizationCode) {
		if(null!=strOrganizationCode){
			this.strOrganizationCode = strOrganizationCode.toUpperCase();	
		}
	}

	public String getStrRegistrationNo() {
		return strRegistrationNo;
	}
	public void setStrRegistrationNo(String strRegistrationNo) {
		this.strRegistrationNo = strRegistrationNo;
	}
	public String getStrTaxpayerIdentLandNo() {
		return strTaxpayerIdentLandNo;
	}
	public void setStrTaxpayerIdentLandNo(String strTaxpayerIdentLandNo) {
		this.strTaxpayerIdentLandNo = strTaxpayerIdentLandNo;
	}
	public String getStrTaxpayerIdentStateNo() {
		return strTaxpayerIdentStateNo;
	}
	public void setStrTaxpayerIdentStateNo(String strTaxpayerIdentStateNo) {
		this.strTaxpayerIdentStateNo = strTaxpayerIdentStateNo;
	}
	public String getAuthorizationIntegrity() {
		return AuthorizationIntegrity;
	}
	
	public void setAuthorizationIntegrity(String authorizationIntegrity) {
		AuthorizationIntegrity = authorizationIntegrity;
	}
	public InstInfo getStrInnerInstCode() {
		return strInnerInstCode;
	}
	public void setStrInnerInstCode(InstInfo strInnerInstCode) {
		this.strInnerInstCode = strInnerInstCode;
	}
	public UserInfo getStrUserID() {
		return strUserID;
	}
	public void setStrUserID(UserInfo strUserID) {
		this.strUserID = strUserID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setStrRegistrationType(String strRegistrationType) {
		this.strRegistrationType = strRegistrationType;
	}
	public String getStrRegistrationType() {
		return strRegistrationType;
	}	
}
