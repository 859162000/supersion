package zdzsystem.dto.condition;

import java.util.Date;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;
import framework.helper.TypeParse;

public class AutoDTO_ZDZ_JRJJFKCXHTNR_Condition  implements java.io.Serializable {

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

	private String RPTCheckType;

	public String getRPTCheckType() {
		return RPTCheckType;
	}

	public void setRPTCheckType(String in) {
		RPTCheckType = in;
	}

	private Date dtDate;

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		dtDate = TypeParse.parseDate(in);
	}

	private AutoDTO_ZDZ_CXQQNR BDHM;

	public AutoDTO_ZDZ_CXQQNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_CXQQNR bDHM) {
		BDHM = bDHM;
	}

}
