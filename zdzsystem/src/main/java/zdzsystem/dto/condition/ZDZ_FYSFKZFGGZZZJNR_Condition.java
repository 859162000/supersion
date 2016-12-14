package zdzsystem.dto.condition;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;

public class ZDZ_FYSFKZFGGZZZJNR_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String BatchNumber;
	
	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String in) {
		BatchNumber = in;
	}
	
	private String RPTSubmitStatus;

	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String in) {
		RPTSubmitStatus = in;
	}

	private String RPTVerifyType;

	public String getRPTVerifyType() {
		return RPTVerifyType;
	}

	public void setRPTVerifyType(String in) {
		RPTVerifyType = in;
	}

	private Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String in) {
		lastUpdateDate = TypeParse.parseTimestamp(in);
	}

	private String RPTSendType;

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}

	private String RPTCheckType;

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	private coresystem.dto.InstInfo instInfo;

	public coresystem.dto.InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(coresystem.dto.InstInfo in) {
		instInfo = in;
	}

	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}
	
	private AutoDTO_ZDZ_KZQQJTNR BDHM;

	public AutoDTO_ZDZ_KZQQJTNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_KZQQJTNR bDHM) {
		BDHM = bDHM;
	}

	private String XH;

	public String getXH() {
		return XH;
	}

	public void setXH(String in) {
		XH = in;
	}

	private String GZZBM;

	public String getGZZBM() {
		return GZZBM;
	}

	public void setGZZBM(String in) {
		GZZBM = in;
	}

	@Column(name = "GZZWJGS", length = 50, nullable = true)
	@IColumn(description="工作证格式")
	private String GZZWJGS;

	public String getGZZWJGS() {
		return GZZWJGS;
	}

	public void setGZZWJGS(String in) {
		GZZWJGS = in;
	}
	
	private byte[] GZZ;

	public byte[] getGZZ() {
		return GZZ;
	}

	public void setGZZ(byte[] gZZ) {
		GZZ = gZZ;
	}
	
}
