package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

/**
 * 关联集团信息表
 * OffBalanceBusinessDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AssociatGroupInformate")
public class AssociatGroupInformate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@IColumn(description="数据时间")
	@Column(name = "dtDate", length = 50)
	private String dtDate;
	
	@Id
	@Column(name = "associationGroupCode", length = 50)
	@IColumn(description="关联集团代码")
	private String associationGroupCode;
	
	@Column(name = "associationGroupName", length = 50)
	@IColumn(description="关联集团名称")
	private String associationGroupName;

	
	@Column(name = "associationGroupType", length = 50)
	@IColumn(description="关联关系类型")
	private String associationGroupType;

	public String getAssociationGroupCode() {
		return associationGroupCode;
	}

	public void setAssociationGroupCode(String associationGroupCode) {
		this.associationGroupCode = associationGroupCode;
	}

	public String getAssociationGroupName() {
		return associationGroupName;
	}

	public void setAssociationGroupName(String associationGroupName) {
		this.associationGroupName = associationGroupName;
	}

	public String getAssociationGroupType() {
		return associationGroupType;
	}

	public void setAssociationGroupType(String associationGroupType) {
		this.associationGroupType = associationGroupType;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}
	
}
