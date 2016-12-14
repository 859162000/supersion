package zxptsystem.dto.condition;

import zxptsystem.dto.EIS_PERCustomernBasicInfo;

public class GRZXCreditReportInfo_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String strCreditReportType;

	private String strQueryCause;

	private String strCreditType;
	
	private EIS_PERCustomernBasicInfo strCustomerID;
	
	private String strVerifyType;

	public String getStrCreditReportType() {
		return strCreditReportType;
	}

	public void setStrCreditReportType(String strCreditReportType) {
		this.strCreditReportType = strCreditReportType;
	}

	public String getStrQueryCause() {
		return strQueryCause;
	}

	public void setStrQueryCause(String strQueryCause) {
		this.strQueryCause = strQueryCause;
	}

	public String getStrVerifyType() {
		return strVerifyType;
	}

	public void setStrVerifyType(String strVerifyType) {
		this.strVerifyType = strVerifyType;
	}

	public String getStrCreditType() {
		return strCreditType;
	}

	public void setStrCreditType(String strCreditType) {
		this.strCreditType = strCreditType;
	}

	public void setStrCustomerID(EIS_PERCustomernBasicInfo strCustomerID) {
		this.strCustomerID = strCustomerID;
	}

	public EIS_PERCustomernBasicInfo getStrCustomerID() {
		return strCustomerID;
	}
	
	
}
