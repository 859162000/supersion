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

@IEntity(description="个人征信质量考评")
public class PROC_GRZXZLKP implements java.io.Serializable {

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
	
	
	@Column(name = "JRJGDM", length = 50)
	@IColumn(description="金融机构代码")
	private String JRJGDM;
	
	@Column(name = "YWH", length = 50)
	@IColumn(description="业务号")
	private String YWH;
	
	@Column(name = "KHRQ", length = 50)
	@IColumn(description="开户日期")
	private String KHRQ;
	
	@Column(name = "YWZLXF", length = 50)
	@IColumn(description="业务种类细分")
	private String YWZLXF;
	
	@Column(name = "YE", length = 50)
	@IColumn(description="余额")
	private String YE;
	
	@Column(name = "XM", length = 50)
	@IColumn(description="姓名")
	private String XM;
	
	@Column(name = "ZJLX", length = 50)
	@IColumn(description="证件类型")
	private String ZJLX;
	
	@Column(name = "ZJHM", length = 50)
	@IColumn(description="证件号码")
	private String ZJHM;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "p_REPORTINST", nullable = false)
	@IColumn(description="报送机构", isNullable = false)
	private ReportInstInfo p_REPORTINST;
	
	@Column(name = "p_ENDMONTH", length = 50)
	@IColumn(description="数据截止日期")
	private String p_ENDMONTH;

	public String getAUTOID() {
		return AUTOID;
	}

	public void setAUTOID(String aUTOID) {
		AUTOID = aUTOID;
	}

	public String getJRJGDM() {
		return JRJGDM;
	}

	public void setJRJGDM(String jRJGDM) {
		JRJGDM = jRJGDM;
	}

	public String getYWH() {
		return YWH;
	}

	public void setYWH(String yWH) {
		YWH = yWH;
	}

	public String getKHRQ() {
		return KHRQ;
	}

	public void setKHRQ(String kHRQ) {
		KHRQ = kHRQ;
	}

	public String getYWZLXF() {
		return YWZLXF;
	}

	public void setYWZLXF(String yWZLXF) {
		YWZLXF = yWZLXF;
	}

	public String getYE() {
		return YE;
	}

	public void setYE(String yE) {
		YE = yE;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String zJLX) {
		ZJLX = zJLX;
	}

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}

	public ReportInstInfo getP_REPORTINST() {
		return p_REPORTINST;
	}

	public void setP_REPORTINST(ReportInstInfo pREPORTINST) {
		p_REPORTINST = pREPORTINST;
	}

	public String getP_ENDMONTH() {
		return p_ENDMONTH;
	}

	public void setP_ENDMONTH(String pENDMONTH) {
		p_ENDMONTH = pENDMONTH;
	}
	
}
