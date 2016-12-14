package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.Date;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


@IEntity(description="个人征信-贷款信息数据查询")
public class VIEW_GR_LOANDATA implements java.io.Serializable{

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
	
	@Column(name = "YWH", length = 100)
	@IColumn(description="业务号")
	private String YWH;
	
	@Column(name = "XM", length = 100)
	@IColumn(description="姓名")
	private String XM;
	
	@Column(name = "ZJLX", length = 255)
	@IColumn(description="证件类型")
	private String ZJLX;
	
	@Column(name = "ZJHM", length = 50)
	@IColumn(description="证件号码")
	private String ZJHM;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "JSYHKRQ", length = 50)
	@IColumn(description="结算、应还款日期")
	private Date JSYHKRQ;
	
	@Column(name = "DYHKZT", length = 50)
	@IColumn(description="当月还款状态")
	private String DYHKZT;
	
	
	@Column(name = "ZHXGRQ", length = 50)
	@IColumn(description="最后修改日期")
	private Timestamp ZHXGRQ;
	
	@Column(name = "CZYID", length = 50)
	@IColumn(description="操作员ID")
	private String CZYID;

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

	public Date getJSYHKRQ() {
		return JSYHKRQ;
	}

	public void setJSYHKRQ(String jSYHKRQ) {
		JSYHKRQ =TypeParse.parseDate(jSYHKRQ) ;
	}

	public String getDYHKZT() {
		return DYHKZT;
	}

	public void setDYHKZT(String dYHKZT) {
		DYHKZT = dYHKZT;
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

	public void setAUTOID(String aUTOID) {
		AUTOID = aUTOID;
	}

	public String getAUTOID() {
		return AUTOID;
	}

	public void setZHXGRQ(String zHXGRQ) {
		ZHXGRQ =TypeParse.parseTimestamp(zHXGRQ);
	}

	public Timestamp getZHXGRQ() {
		return ZHXGRQ;
	}

}
