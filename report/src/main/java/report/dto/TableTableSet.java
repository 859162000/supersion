package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import extend.dto.ReportModel_Table;
import framework.interfaces.IColumn;

@Entity
@Table(name = "TableTableSet")
public class TableTableSet implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 2730204019846347228L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTableID", nullable = false)
	private ReportModel_Table Reportmodel_table;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportmodel_tableSetCode", nullable = false)
	private Reportmodel_tableSet strReportmodel_tableSetCode;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStrReportmodel_tableSetCode(
			Reportmodel_tableSet strReportmodel_tableSetCode) {
		this.strReportmodel_tableSetCode = strReportmodel_tableSetCode;
	}

	public Reportmodel_tableSet getStrReportmodel_tableSetCode() {
		return strReportmodel_tableSetCode;
	}

	public void setReportmodel_table(ReportModel_Table reportModel_Table) {
		Reportmodel_table = reportModel_Table;
	}

	public ReportModel_Table getReportmodel_table() {
		return Reportmodel_table;
	}
	

}

