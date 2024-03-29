package zdzsystem.dto.condition;

import java.util.Map;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;
import framework.show.ShowContext;

public class AutoDTO_ZDZ_ZJWLXX_Condition  implements java.io.Serializable {

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

	public static Map<String,String> getRPTSendTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSendType");
	}

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

	private String WLXH;

	public String getWLXH() {
		return WLXH;
	}

	public void setWLXH(String in) {
		WLXH = in;
	}

	private String JYLSH;

	public String getJYLSH() {
		return JYLSH;
	}

	public void setJYLSH(String in) {
		JYLSH = in;
	}

	private String ZCKZH;

	public String getZCKZH() {
		return ZCKZH;
	}

	public void setZCKZH(String in) {
		ZCKZH = in;
	}

	private String JYDSZJHM;

	public String getJYDSZJHM() {
		return JYDSZJHM;
	}

	public void setJYDSZJHM(String in) {
		JYDSZJHM = in;
	}

}
