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
@Table(name = "Reportmodel_tableSet")
public class Reportmodel_tableSet implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -4100563757993522740L;

	@Id
	@Column(name = "strReportmodel_tableSetCode", length = 50)
	@IColumn(description="明细表集代码", isNullable = false)
	private String strReportmodel_tableSetCode;
	
	@Column(name = "strReportmodel_tableSetName", length = 50)
	@IColumn(description="明细表集名称", isNullable = false)
	private String strReportmodel_tableSetName;
	

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "strReportmodel_tableSetCode")
	private Set<TableTableSet> Reportmodel_tableAndList = new HashSet<TableTableSet>(0);
	
	
	
	// 用于指定显示列表相关参数,显示列表为userRoles属性,显示关联表的roleInfo列
	@Transient
	@IColumn(isIdListField = true, target="Reportmodel_tableAndList", mappedBy = "Reportmodel_table")
	private String[] strReportmodel_tableIdList;



	public String getStrReportmodel_tableSetCode() {
		return strReportmodel_tableSetCode;
	}



	public void setStrReportmodel_tableSetCode(String strReportModelTableSetCode) {
		strReportmodel_tableSetCode = strReportModelTableSetCode;
	}



	public String getStrReportmodel_tableSetName() {
		return strReportmodel_tableSetName;
	}



	public void setStrReportmodel_tableSetName(String strReportModelTableSetName) {
		strReportmodel_tableSetName = strReportModelTableSetName;
	}



	public Set<TableTableSet> getReportmodel_tableAndList() {
		return Reportmodel_tableAndList;
	}



	public void setReportmodel_tableAndList(
			Set<TableTableSet> reportModelTableAndList) {
		Reportmodel_tableAndList = reportModelTableAndList;
	}



	public String[] getStrReportmodel_tableIdList() {
		return strReportmodel_tableIdList;
	}



	public void setStrReportmodel_tableIdList(String[] strReportModelTableIdList) {
		strReportmodel_tableIdList = strReportModelTableIdList;
	}

}

