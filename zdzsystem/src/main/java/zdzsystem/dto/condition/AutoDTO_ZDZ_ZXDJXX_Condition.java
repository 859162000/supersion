package zdzsystem.dto.condition;

import java.util.Map;

import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;
import framework.show.ShowContext;

public class AutoDTO_ZDZ_ZXDJXX_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String BatchNumber;
	
	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String in) {
		BatchNumber = in;
	}
	
	private String RPTSubmitStatus;

	public static Map<String,String> getRPTSubmitStatusTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTSubmitStatus");
	}

	public String getRPTSubmitStatus() {
		return RPTSubmitStatus;
	}

	public void setRPTSubmitStatus(String in) {
		RPTSubmitStatus = in;
	}

	private String RPTVerifyType;

	public static Map<String,String> getRPTVerifyTypeTag() {
		return ShowContext.getInstance().getShowEntityMap().get("RPTVerifyType");
	}

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

	private coresystem.dto.InstInfo instInfo;

	public coresystem.dto.InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(coresystem.dto.InstInfo in) {
		instInfo = in;
	}

	private AutoDTO_ZDZ_KZQQJTNR BDHM;

	public AutoDTO_ZDZ_KZQQJTNR getBDHM() {
		return BDHM;
	}

	public void setBDHM(AutoDTO_ZDZ_KZQQJTNR bDHM) {
		BDHM = bDHM;
	}

	private String CCXH;

	public String getCCXH() {
		return CCXH;
	}

	public void setCCXH(String in) {
		CCXH = in;
	}

	private String CSXH;

	public String getCSXH() {
		return CSXH;
	}

	public void setCSXH(String in) {
		CSXH = in;
	}

	private String DJWH;

	public String getDJWH() {
		return DJWH;
	}

	public void setDJWH(String in) {
		DJWH = in;
	}

}
