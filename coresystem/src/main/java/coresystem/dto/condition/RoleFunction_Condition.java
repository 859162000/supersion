package coresystem.dto.condition;

import coresystem.dto.RoleInfo;
import framework.interfaces.ICondition;

public class RoleFunction_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(order=1,isASC=false)
	private RoleInfo roleInfo;
	
	@ICondition(order=2,isASC=false)
	private String strFunctionCode;
	
	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setStrFunctionCode(String strFunctionCode) {
		this.strFunctionCode = strFunctionCode;
	}

	public String getStrFunctionCode() {
		return strFunctionCode;
	}
}
