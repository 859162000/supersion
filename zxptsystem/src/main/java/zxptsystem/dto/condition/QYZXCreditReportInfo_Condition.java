package zxptsystem.dto.condition;

import zxptsystem.dto.EIS_ENTCustomernBasicInfo;

public class QYZXCreditReportInfo_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String strOrgCode;
	
	private EIS_ENTCustomernBasicInfo strCustomerID;
	
	private String strQueryCause;

	private String strVerifyType;
	
	public String getStrVerifyType() {
		return strVerifyType;
	}

	public void setStrVerifyType(String strVerifyType) {
		this.strVerifyType = strVerifyType;
	}

	public void setStrQueryCause(String strQueryCause) {
		this.strQueryCause = strQueryCause;
	}

	public String getStrQueryCause() {
		return strQueryCause;
	}

	public void setStrCustomerID(EIS_ENTCustomernBasicInfo strCustomerID) {
		this.strCustomerID = strCustomerID;
	}

	public EIS_ENTCustomernBasicInfo getStrCustomerID() {
		return strCustomerID;
	}

	public void setStrOrgCode(String strOrgCode) {
		this.strOrgCode = strOrgCode;
	}

	public String getStrOrgCode() {
		return strOrgCode;
	}
	
	
}
