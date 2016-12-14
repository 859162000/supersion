package report.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import framework.interfaces.IColumn;

@Entity
@Table(name = "InstInfoSet")
public class InstInfoSet implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -8141980567128750393L;

	@Id
	@Column(name = "strInstSetCode", length = 50)
	@IColumn(description="机构集代码")
	private String strInstSetCode;
	
	public String getStrInstSetCode() {
		return strInstSetCode;
	}

	public void setStrInstSetCode(String strInstSetCode) {
		this.strInstSetCode = strInstSetCode;
	}

	public String getStrInstSetName() {
		return strInstSetName;
	}

	public void setStrInstSetName(String strInstSetName) {
		this.strInstSetName = strInstSetName;
	}

	@Column(name = "strInstSetName", length = 50)
	@IColumn(description="机构集名称", isSpecialCharCheck=true)
	private String strInstSetName;
	

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "strInstSetCode")
	private Set<InstInstSet> instInfoAndList = new HashSet<InstInstSet>(0);
	
	
	
	// 用于指定显示列表相关参数,显示列表为userRoles属性,显示关联表的roleInfo列
	@Transient
	@IColumn(isIdListField = true, target="instInfoAndList", mappedBy = "instInfo")
	private String[] strInstCodeIdList;

	public String[] getStrInstCodeIdList() {
		return strInstCodeIdList;
	}

	public void setStrInstCodeIdList(String[] strInstCodeIdList) {
		this.strInstCodeIdList = strInstCodeIdList;
	}

	public Set<InstInstSet> getInstInfoAndList() {
		return instInfoAndList;
	}

	public void setInstInfoAndList(Set<InstInstSet> instInfoAndList) {
		this.instInfoAndList = instInfoAndList;
	}


}

