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
import coresystem.dto.InstInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "ReportInstSubInfo")
@IEntity(description="报送机构集管理")
public class ReportInstSubInfo implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 7598570401698701644L;

	@Id
	@Column(name = "strReportInstSubCode", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String strReportInstSubCode;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@IColumn(isIdListField = true, target="instInfo")
	private String[] instInfoIdList;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportInstCode")
	@IColumn(description="所属报送机构")
	private ReportInstInfo reportInstInfo;
	
	public void setStrReportInstSubCode(String strReportInstSubCode) {
		this.strReportInstSubCode = strReportInstSubCode;
	}

	public String getStrReportInstSubCode() {
		return strReportInstSubCode;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfoIdList(String[] instInfoIdList) {
		this.instInfoIdList = instInfoIdList;
	}

	public String[] getInstInfoIdList() {
		return instInfoIdList;
	}

	public void setReportInstInfo(ReportInstInfo reportInstInfo) {
		this.reportInstInfo = reportInstInfo;
	}

	public ReportInstInfo getReportInstInfo() {
		return reportInstInfo;
	}	
	
}

