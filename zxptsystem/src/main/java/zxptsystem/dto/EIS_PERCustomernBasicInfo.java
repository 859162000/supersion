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
@Table(name = "EIS_PERCustomernBasicInfo")
@IEntity(description= "个人客户信息")
public class EIS_PERCustomernBasicInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "strCustomerID", length = 100)
	@IColumn(description="授权客户代码",isSingleTagHidden=true)
	private String strCustomerID;
	
	@Column(name = "strCustomerName", length = 100, nullable = false)
	@IColumn(description="授权客户名称",isKeyName=true,isNullable=false)
	private String strCustomerName;
		
	@IColumn(tagMethodName="getCertTypeTag",description="证件类型",isNullable=false)
	@Column(name = "strCertType", nullable =true)
	private String strCertType;
	
	public static Map<String,String> getCertTypeTag() {
		try {
			return HelpTool.getSystemConstVal("GRZXCertType");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "strCertNo", length = 50, nullable = false)
	@IColumn(description="证件号码",isNullable=false)
	private String strCertNo;
	
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
	
	@Column(name = "grzxQueryAcount", length = 10, nullable = true)
	@IColumn(description="查询次数",isSingleTagHidden=true,isNullable = true)
	private String grzxQueryAcount;

	public String getGrzxQueryAcount() {
		return grzxQueryAcount;
	}

	public void setGrzxQueryAcount(String grzxQueryAcount) {
		this.grzxQueryAcount = grzxQueryAcount;
	}

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

	public String getStrCertType() {
		return strCertType;
	}

	public void setStrCertType(String strCertType) {
		this.strCertType = strCertType;
	}

	public String getStrCertNo() {
		return strCertNo;
	}

	public void setStrCertNo(String strCertNo) {
		this.strCertNo = strCertNo;
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
}
