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
@Table(name = "RptDto")
public class RptDto implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 102982770549432506L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	private RptInfo rptInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTableID", nullable = false)
	private ReportModel_Table table;
	
	@Column(name = "startRow", nullable = false)
	@IColumn(description="起始行", isNullable = false)
	private Integer startRow;
	
	@Column(name = "startColumn", nullable = false)
	@IColumn(description="起始列", isNullable = false)
	private Integer startColumn;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	public void setRptInfo(RptInfo rptInfo) {
		this.rptInfo = rptInfo;
	}

	public RptInfo getRptInfo() {
		return rptInfo;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartColumn(Integer startColumn) {
		this.startColumn = startColumn;
	}

	public Integer getStartColumn() {
		return startColumn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTable(ReportModel_Table table) {
		this.table = table;
	}

	public ReportModel_Table getTable() {
		return table;
	}
}
