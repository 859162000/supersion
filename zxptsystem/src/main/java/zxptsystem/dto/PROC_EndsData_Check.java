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

@IEntity(description="企业征信业务统计")
public class PROC_EndsData_Check implements java.io.Serializable {

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
	
	@Column(name = "DKKBM", length = 50)
	@IColumn(description="中征码/自然人证件类型")
	private String DKKBM;
	
	@Column(name = "ZZJGDM", length = 50)
	@IColumn(description="组织机构代码/自然人证件号码")
	private String ZZJGDM;
	
	@Column(name = "JKRMC", length = 50)
	@IColumn(description="企业名称/自然人姓名")
	private String JKRMC;
	
	@Column(name = "DKHTBS", length = 50)
	@IColumn(description="贷款合同笔数")
	private String DKHTBS;
	
	@Column(name = "DKFSE", length = 50)
	@IColumn(description="贷款发生额")
	private String DKFSE;
	
	@Column(name = "DKYE", length = 50)
	@IColumn(description="贷款余额")
	private String DKYE;
	
	@Column(name = "JJBS", length = 50)
	@IColumn(description="借据笔数")
	private String JJBS;
	
	@Column(name = "BLBS", length = 50)
	@IColumn(description="保理笔数")
	private String BLBS;
	
	@Column(name = "BLFSE", length = 50)
	@IColumn(description="保理发生额")
	private String BLFSE;
	
	@Column(name = "BLYE", length = 50)
	@IColumn(description="保理余额")
	private String BLYE;
	
	@Column(name = "PJTXBS", length = 50)
	@IColumn(description="票据贴现笔数")
	private String PJTXBS;
	
	@Column(name = "PJTXFSE", length = 50)
	@IColumn(description="票据贴现发生额")
	private String PJTXFSE;
	
	@Column(name = "PJTXYE", length = 50)
	@IColumn(description="票据贴现余额")
	private String PJTXYE;
	
	@Column(name = "MYRZXYBS", length = 50)
	@IColumn(description="贸易融资协议笔数")
	private String MYRZXYBS;
	
	@Column(name = "MYRZFSE", length = 50)
	@IColumn(description="贸易融资发生额")
	private String MYRZFSE;
	
	@Column(name = "MYRZYE", length = 50)
	@IColumn(description="贸易融资余额")
	private String MYRZYE;
	
	@Column(name = "MYRZYWBS", length = 50)
	@IColumn(description="贸易融资业务笔数")
	private String MYRZYWBS;
			
	@Column(name = "XYZBS", length = 50)
	@IColumn(description="信用证笔数")
	private String XYZBS;
	
	@Column(name = "XYZFSE", length = 50)
	@IColumn(description="信用证发生额")
	private String XYZFSE;
	
	@Column(name = "XYZYE", length = 50)
	@IColumn(description="信用证余额")
	private String XYZYE;
	
	@Column(name = "BHBS", length = 50)
	@IColumn(description="保函笔数")
	private String BHBS;
	
	@Column(name = "BHFSE", length = 50)
	@IColumn(description="保函发生额")
	private String BHFSE;
	
	@Column(name = "BHYE", length = 50)
	@IColumn(description="保函余额")
	private String BHYE;
	
	@Column(name = "CDHPBS", length = 50)
	@IColumn(description="承兑汇票笔数")
	private String CDHPBS;
	
	@Column(name = "CDHPFSE", length = 50)
	@IColumn(description="承兑汇票发生额")
	private String CDHPFSE;
	
	@Column(name = "CDHPYE", length = 50)
	@IColumn(description="承兑汇票余额")
	private String CDHPYE;
	
	@Column(name = "GKSXBS", length = 50)
	@IColumn(description="公开授信笔数")
	private String GKSXBS;
	
	@Column(name = "GKSXFSE", length = 50)
	@IColumn(description="公开授信发生额")
	private String GKSXFSE;
	
	@Column(name = "BZHTBS", length = 50)
	@IColumn(description="保证合同笔数")
	private String BZHTBS;
	
	@Column(name = "BZFSE", length = 50)
	@IColumn(description="保证发生额")
	private String BZFSE;
	
	@Column(name = "BZYE", length = 50)
	@IColumn(description="保证余额")
	private String BZYE;
	
	@Column(name = "DYHTBS", length = 50)
	@IColumn(description="抵押合同笔数")
	private String DYHTBS;
	
	@Column(name = "DYFSE", length = 50)
	@IColumn(description="抵押发生额")
	private String DYFSE;
	
	@Column(name = "DYYE", length = 50)
	@IColumn(description="抵押余额")
	private String DYYE;
	
	@Column(name = "ZYHTBS", length = 50)
	@IColumn(description="质押合同笔数")
	private String ZYHTBS;
	
	@Column(name = "ZYFSE", length = 50)
	@IColumn(description="质押发生额")
	private String ZYFSE;
	
	@Column(name = "ZYYE", length = 50)
	@IColumn(description="质押余额")
	private String ZYYE;
	
	@Column(name = "DIANKUANBS", length = 50)
	@IColumn(description="垫款笔数")
	private String DIANKUANBS;
	
	@Column(name = "DIANKUANFSE", length = 50)
	@IColumn(description="垫款发生额")
	private String DIANKUANFSE;
	
	@Column(name = "DIANKUANYE", length = 50)
	@IColumn(description="垫款余额")
	private String DIANKUANYE;
	
	@Column(name = "BNQXYE", length = 50)
	@IColumn(description="表内欠息余额")
	private String BNQXYE;
	
	@Column(name = "BWQXYE", length = 50)
	@IColumn(description="表外欠息余额")
	private String BWQXYE;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DJJGDM", nullable = false)
	@IColumn(description="顶级机构代码", isNullable = false)
	private ReportInstInfo DJJGDM;
	
	@Column(name = "SJJZRQ", length = 50)
	@IColumn(description="数据截止日期")
	private String SJJZRQ;
	
	public String getAUTOID() {
		return AUTOID;
	}

	public void setAUTOID(String aUTOID) {
		AUTOID = aUTOID;
	}

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}

	public String getZZJGDM() {
		return ZZJGDM;
	}

	public void setZZJGDM(String zZJGDM) {
		ZZJGDM = zZJGDM;
	}

	public String getJKRMC() {
		return JKRMC;
	}

	public void setJKRMC(String jKRMC) {
		JKRMC = jKRMC;
	}

	public String getDKHTBS() {
		return DKHTBS;
	}

	public void setDKHTBS(String dKHTBS) {
		DKHTBS = dKHTBS;
	}

	public String getDKFSE() {
		return DKFSE;
	}

	public void setDKFSE(String dKFSE) {
		DKFSE = dKFSE;
	}

	public String getDKYE() {
		return DKYE;
	}

	public void setDKYE(String dKYE) {
		DKYE = dKYE;
	}

	public String getJJBS() {
		return JJBS;
	}

	public void setJJBS(String jJBS) {
		JJBS = jJBS;
	}

	public String getBLBS() {
		return BLBS;
	}

	public void setBLBS(String bLBS) {
		BLBS = bLBS;
	}

	public String getBLFSE() {
		return BLFSE;
	}

	public void setBLFSE(String bLFSE) {
		BLFSE = bLFSE;
	}

	public String getBLYE() {
		return BLYE;
	}

	public void setBLYE(String bLYE) {
		BLYE = bLYE;
	}

	public String getPJTXBS() {
		return PJTXBS;
	}

	public void setPJTXBS(String pJTXBS) {
		PJTXBS = pJTXBS;
	}

	public String getPJTXFSE() {
		return PJTXFSE;
	}

	public void setPJTXFSE(String pJTXFSE) {
		PJTXFSE = pJTXFSE;
	}

	public String getPJTXYE() {
		return PJTXYE;
	}

	public void setPJTXYE(String pJTXYE) {
		PJTXYE = pJTXYE;
	}

	public String getMYRZXYBS() {
		return MYRZXYBS;
	}

	public void setMYRZXYBS(String mYRZXYBS) {
		MYRZXYBS = mYRZXYBS;
	}

	public String getMYRZFSE() {
		return MYRZFSE;
	}

	public void setMYRZFSE(String mYRZFSE) {
		MYRZFSE = mYRZFSE;
	}

	public String getMYRZYE() {
		return MYRZYE;
	}

	public void setMYRZYE(String mYRZYE) {
		MYRZYE = mYRZYE;
	}

	public String getMYRZYWBS() {
		return MYRZYWBS;
	}

	public void setMYRZYWBS(String mYRZYWBS) {
		MYRZYWBS = mYRZYWBS;
	}

	public String getXYZBS() {
		return XYZBS;
	}

	public void setXYZBS(String xYZBS) {
		XYZBS = xYZBS;
	}

	public String getXYZFSE() {
		return XYZFSE;
	}

	public void setXYZFSE(String xYZFSE) {
		XYZFSE = xYZFSE;
	}

	public String getXYZYE() {
		return XYZYE;
	}

	public void setXYZYE(String xYZYE) {
		XYZYE = xYZYE;
	}

	public String getBHBS() {
		return BHBS;
	}

	public void setBHBS(String bHBS) {
		BHBS = bHBS;
	}

	public String getBHFSE() {
		return BHFSE;
	}

	public void setBHFSE(String bHFSE) {
		BHFSE = bHFSE;
	}

	public String getBHYE() {
		return BHYE;
	}

	public void setBHYE(String bHYE) {
		BHYE = bHYE;
	}

	public String getCDHPBS() {
		return CDHPBS;
	}

	public void setCDHPBS(String cDHPBS) {
		CDHPBS = cDHPBS;
	}

	public String getCDHPFSE() {
		return CDHPFSE;
	}

	public void setCDHPFSE(String cDHPFSE) {
		CDHPFSE = cDHPFSE;
	}

	public String getCDHPYE() {
		return CDHPYE;
	}

	public void setCDHPYE(String cDHPYE) {
		CDHPYE = cDHPYE;
	}

	public String getGKSXBS() {
		return GKSXBS;
	}

	public void setGKSXBS(String gKSXBS) {
		GKSXBS = gKSXBS;
	}

	public String getGKSXFSE() {
		return GKSXFSE;
	}

	public void setGKSXFSE(String gKSXFSE) {
		GKSXFSE = gKSXFSE;
	}

	public String getBZHTBS() {
		return BZHTBS;
	}

	public void setBZHTBS(String bZHTBS) {
		BZHTBS = bZHTBS;
	}

	public String getBZFSE() {
		return BZFSE;
	}

	public void setBZFSE(String bZFSE) {
		BZFSE = bZFSE;
	}

	public String getBZYE() {
		return BZYE;
	}

	public void setBZYE(String bZYE) {
		BZYE = bZYE;
	}

	public String getDYHTBS() {
		return DYHTBS;
	}

	public void setDYHTBS(String dYHTBS) {
		DYHTBS = dYHTBS;
	}

	public String getDYFSE() {
		return DYFSE;
	}

	public void setDYFSE(String dYFSE) {
		DYFSE = dYFSE;
	}

	public String getDYYE() {
		return DYYE;
	}

	public void setDYYE(String dYYE) {
		DYYE = dYYE;
	}

	public String getZYHTBS() {
		return ZYHTBS;
	}

	public void setZYHTBS(String zYHTBS) {
		ZYHTBS = zYHTBS;
	}

	public String getZYFSE() {
		return ZYFSE;
	}

	public void setZYFSE(String zYFSE) {
		ZYFSE = zYFSE;
	}

	public String getZYYE() {
		return ZYYE;
	}

	public void setZYYE(String zYYE) {
		ZYYE = zYYE;
	}

	public String getDIANKUANBS() {
		return DIANKUANBS;
	}

	public void setDIANKUANBS(String dIANKUANBS) {
		DIANKUANBS = dIANKUANBS;
	}

	public String getDIANKUANFSE() {
		return DIANKUANFSE;
	}

	public void setDIANKUANFSE(String dIANKUANFSE) {
		DIANKUANFSE = dIANKUANFSE;
	}

	public String getDIANKUANYE() {
		return DIANKUANYE;
	}

	public void setDIANKUANYE(String dIANKUANYE) {
		DIANKUANYE = dIANKUANYE;
	}

	public String getBNQXYE() {
		return BNQXYE;
	}

	public void setBNQXYE(String bNQXYE) {
		BNQXYE = bNQXYE;
	}

	public String getBWQXYE() {
		return BWQXYE;
	}

	public void setBWQXYE(String bWQXYE) {
		BWQXYE = bWQXYE;
	}

	public ReportInstInfo getDJJGDM() {
		return DJJGDM;
	}

	public void setDJJGDM(ReportInstInfo dJJGDM) {
		DJJGDM = dJJGDM;
	}

	public String getSJJZRQ() {
		return SJJZRQ;
	}

	public void setSJJZRQ(String sJJZRQ) {
		SJJZRQ = sJJZRQ;
	}


	
}
