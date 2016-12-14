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
@Table(name = "RptInfoSet")
public class RptInfoSet implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 6491917633675182813L;

	@Id
	@Column(name = "strRptSetCode", length = 50)
	@IColumn(description="报表集代码", isNullable = false)
	private String strRptSetCode;

	@Column(name = "strRptSetName", length = 50)
	@IColumn(description="报表集名称", isNullable = false)
	private String strRptSetName;
	

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "strRptSetCode")
	private Set<RptRptSet> rptInfoAndList = new HashSet<RptRptSet>(0);
	
	// 用于指定显示列表相关参数,显示列表为userRoles属性,显示关联表的roleInfo列
	@Transient
	@IColumn(isIdListField = true, target="rptInfoAndList", mappedBy = "rptInfo")
	private String[] strRptCodeIdList;



	public String getStrRptSetCode() {
		return strRptSetCode;
	}



	public void setStrRptSetCode(String strRptSetCode) {
		this.strRptSetCode = strRptSetCode;
	}



	public String getStrRptSetName() {
		return strRptSetName;
	}



	public void setStrRptSetName(String strRptSetName) {
		this.strRptSetName = strRptSetName;
	}



	public Set<RptRptSet> getRptInfoAndList() {
		return rptInfoAndList;
	}



	public void setRptInfoAndList(Set<RptRptSet> rptInfoAndList) {
		this.rptInfoAndList = rptInfoAndList;
	}



	public String[] getStrRptCodeIdList() {
		return strRptCodeIdList;
	}



	public void setStrRptCodeIdList(String[] strRptCodeIdList) {
		this.strRptCodeIdList = strRptCodeIdList;
	}


}

