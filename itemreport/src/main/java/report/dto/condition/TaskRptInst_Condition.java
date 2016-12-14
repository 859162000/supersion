package report.dto.condition;

import report.dto.RptInfo;
import framework.interfaces.ICondition;

public class TaskRptInst_Condition implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2031057004535965990L;

	// 报表代码
	@ICondition(target = "rptInfo" , targetField = "strBCode" , isASC = true,order=2)
	private RptInfo rptInfo1;
	
	// 报表批次
	@ICondition(target = "rptInfo" , targetField = "strTime" , isASC = true,order=1,isVisible=false)
	private RptInfo rptInfo2;
	
	@ICondition( isASC = true,order=3)
	private String strCheckState;
	@ICondition(isASC = true,order=4)
	private String SubmitStatus;
	@ICondition( isASC = true,order=5)
	private String strAllowState;
	
	
	public RptInfo getRptInfo1() {
		return rptInfo1;
	}
	public void setRptInfo1(RptInfo rptInfo1) {
		this.rptInfo1 = rptInfo1;
	}
	public RptInfo getRptInfo2() {
		return rptInfo2;
	}
	public void setRptInfo2(RptInfo rptInfo2) {
		this.rptInfo2 = rptInfo2;
	}
	public String getSubmitStatus() {
		return SubmitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		SubmitStatus = submitStatus;
	}

	public String getStrCheckState() {
		return strCheckState;
	}

	public void setStrCheckState(String strCheckState) {
		this.strCheckState = strCheckState;
	}

	public String getStrAllowState() {
		return strAllowState;
	}

	public void setStrAllowState(String strAllowState) {
		this.strAllowState = strAllowState;
	}

	
	
}
