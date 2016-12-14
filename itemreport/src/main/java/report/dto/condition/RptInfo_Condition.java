package report.dto.condition;

import framework.interfaces.ICondition;

public class RptInfo_Condition  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2221767237537929742L;
	/**
	 * 
	 */
	
	@ICondition(order = 1, isASC = true)
	private String strBCode;
	private String strRptName;
	private String strRptVersion;
	
	public String getStrBCode() {
		return strBCode;
	}

	public void setStrBCode(String strBCode) {
		this.strBCode = strBCode;
	}

	public String getStrRptVersion() {
		return strRptVersion;
	}

	public void setStrRptVersion(String strRptVersion) {
		this.strRptVersion = strRptVersion;
	}
	
	public void setStrRptName(String strRptName) {
		
		this.strRptName = strRptName;
	}

	public String getStrRptName() {
		return strRptName;
	}


}
