package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import report.dto.ReportInstInfo;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="企业征信统计排名")
public class PROC_QYZXTJPM implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "AUTOID", length = 32)
	@IColumn(description="自动ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String AUTOID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JRJGDM", nullable = false)
	@IColumn(description="报送机构", isNullable = false)
	private ReportInstInfo JRJGDM;
	
	@Column(name = "InstInfo", length = 50)
	@IColumn(description="机构代码")
	private String InstInfo;
	
	@Column(name = "TOTALN")
	@IColumn(description="报文总计")
	private String TOTALN;
	
	@Column(name = "SUCCESSN")
	@IColumn(description="回执成功总计")
	private String SUCCESSN;
	
	@Column(name = "RATIO")
	@IColumn(description="回执成功占比")
	private String RATIO;

	public String getAUTOID() {
		return AUTOID;
	}

	public void setAUTOID(String aUTOID) {
		AUTOID = aUTOID;
	}

	public String getInstInfo() {
		return InstInfo;
	}

	public void setInstInfo(String instInfo) {
		InstInfo = instInfo;
	}

	public String getTOTALN() {
		return TOTALN;
	}

	public void setTOTALN(String tOTALN) {
		TOTALN = tOTALN;
	}

	public String getSUCCESSN() {
		return SUCCESSN;
	}

	public void setSUCCESSN(String sUCCESSN) {
		SUCCESSN = sUCCESSN;
	}

	public String getRATIO() {
		return RATIO;
	}

	public void setRATIO(String rATIO) {
		RATIO = rATIO;
	}

	public void setJRJGDM(ReportInstInfo jRJGDM) {
		JRJGDM = jRJGDM;
	}

	public ReportInstInfo getJRJGDM() {
		return JRJGDM;
	}
}
