package zdzsystem.dto.condition;

import javax.persistence.Column;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;

public class AutoDTO_ZDZ_GYQYXQXX_Condition  implements java.io.Serializable {

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

	private String RPTSendType;

	public String getRPTSendType() {
		return RPTSendType;
	}

	public void setRPTSendType(String in) {
		RPTSendType = in;
	}

	@Column(name = "RPTCheckType", nullable =true)
	private String RPTCheckType;

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	private AutoDTO_ZDZ_CXQQNR BDHM;

	public AutoDTO_ZDZ_CXQQNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_CXQQNR bDHM) {
		BDHM = bDHM;
	}

	private String CCXH;

	public String getCCXH() {
		return CCXH;
	}

	public void setCCXH(String in) {
		CCXH = in;
	}

	private String XH;

	public String getXH() {
		return XH;
	}

	public void setXH(String in) {
		XH = in;
	}

}
