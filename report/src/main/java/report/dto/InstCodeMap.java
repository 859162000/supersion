package report.dto;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "InstCodeMap")
public class InstCodeMap implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 7127142736619802799L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportInstCode", nullable = false)
	private ReportInstInfo reportInstInfo;

	@IColumn(tagMethodName="getContrastType",description="对照类型", isNullable = false)
	@Column(name = "strContrastType", nullable = false)
	private String strContrastType;

	public static Map<String,String> getContrastType(){
		return ShowContext.getInstance().getShowEntityMap().get("ContrastType");
	}

	@Column(name = "strInstCode", length = 50, nullable = false)
	@IColumn(description="机构编码", isKeyName=false, isNullable = false)
	private String strInstCode;
	
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

	public void setStrInstCode(String strInstCode) {
		this.strInstCode = strInstCode;
	}

	public String getStrInstCode() {
		return strInstCode;
	}


	public void setStrContrastType(String strContrastType) {
		this.strContrastType = strContrastType;
	}

	public String getStrContrastType() {
		return strContrastType;
	}

	public void setReportInstInfo(ReportInstInfo reportInstInfo) {
		this.reportInstInfo = reportInstInfo;
	}

	public ReportInstInfo getReportInstInfo() {
		return reportInstInfo;
	}

	
}
