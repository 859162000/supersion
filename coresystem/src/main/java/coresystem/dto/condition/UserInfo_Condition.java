package coresystem.dto.condition;

import coresystem.dto.InstInfo;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class UserInfo_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(comparison=Comparison.EQ)
	private String strUserCode;
	
	private String strUserName;

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	@ICondition(target="instInfo", targetField="strInstName")
	private InstInfo instInfo1;
	
	@ICondition(target="instInfo", targetField="strInstCode")
	private InstInfo instInfo2;

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setInstInfo1(InstInfo instInfo1) {
		this.instInfo1 = instInfo1;
	}

	public InstInfo getInstInfo1() {
		return instInfo1;
	}

	public void setInstInfo2(InstInfo instInfo2) {
		this.instInfo2 = instInfo2;
	}

	public InstInfo getInstInfo2() {
		return instInfo2;
	}
	
}


