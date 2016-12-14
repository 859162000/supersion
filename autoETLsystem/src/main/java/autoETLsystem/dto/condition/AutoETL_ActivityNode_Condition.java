package autoETLsystem.dto.condition;

import framework.interfaces.ICondition;

public class AutoETL_ActivityNode_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(order=1)
	private String strOrderGroup;
	
	@ICondition(order=2,isVisible=false)
	private Integer intOrder;

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}
	
	public void setStrActivityNodeType(String strActivityNodeType) {
		this.strActivityNodeType = strActivityNodeType;
	}

	public String getStrActivityNodeType() {
		return strActivityNodeType;
	}

	public void setStrActivityNodeName(String strActivityNodeName) {
		this.strActivityNodeName = strActivityNodeName;
	}

	public String getStrActivityNodeName() {
		return strActivityNodeName;
	}

	public void setStrOrderGroup(String strOrderGroup) {
		this.strOrderGroup = strOrderGroup;
	}

	public String getStrOrderGroup() {
		return strOrderGroup;
	}

	private String strActivityNodeType;
	
	private String strActivityNodeName;
	

}

