package zxptsystem.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="机构基本信息-客户信息数据查询")
public class VIEW_JG_CUSTOMERINFO implements java.io.Serializable {

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
	
	@Column(name = "JGDM", length = 50)
	@IColumn(description="机构代码")
	private String JGDM;
	
	@Column(name = "KHH", length = 100)
	@IColumn(description="客户号")
	private String KHH;
	
	@Column(name = "JGZWMC", length = 200)
	@IColumn(description="机构中文名称")
	private String JGZWMC;

	@Column(name = "KHLX", length = 255)
	@IColumn(description="客户类型")
	private String KHLX;
	
	@Column(name = "DKKBM", length = 50)
	@IColumn(description="中征码")
	private String DKKBM;
	
	@Column(name = "KHXKZHZH", length = 50)
	@IColumn(description="开户许可证核准号")
	private String KHXKZHZH;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "XXGXRQ", length = 50)
	@IColumn(description="信息更新日期")
	private Date XXGXRQ;
	
	@Column(name = "ZHCZRQ", length = 50)
	@IColumn(description="最后操作日期")
	private Timestamp ZHCZRQ;
	
	@Column(name = "CZYID", length = 50)
	@IColumn(description="操作员ID")
	private String CZYID;

	public String getAUTOID() {
		return AUTOID;
	}

	public void setAUTOID(String aUTOID) {
		AUTOID = aUTOID;
	}

	public String getJGDM() {
		return JGDM;
	}

	public void setJGDM(String jGDM) {
		JGDM = jGDM;
	}

	public String getKHH() {
		return KHH;
	}

	public void setKHH(String kHH) {
		KHH = kHH;
	}

	public String getJGZWMC() {
		return JGZWMC;
	}

	public void setJGZWMC(String jGZWMC) {
		JGZWMC = jGZWMC;
	}

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String kHLX) {
		KHLX = kHLX;
	}

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}

	public String getKHXKZHZH() {
		return KHXKZHZH;
	}

	public void setKHXKZHZH(String kHXKZHZH) {
		KHXKZHZH = kHXKZHZH;
	}

	public Date getXXGXRQ() {
		return XXGXRQ;
	}

	public void setXXGXRQ(String xXGXRQ) {
		XXGXRQ =TypeParse.parseDate(xXGXRQ);
	}

	public Timestamp getZHCZRQ() {
		return ZHCZRQ;
	}

	public void setZHCZRQ(String zHCZRQ) {
		ZHCZRQ =TypeParse.parseTimestamp(zHCZRQ);
	}

	public String getCZYID() {
		return CZYID;
	}

	public void setCZYID(String cZYID) {
		CZYID = cZYID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
