package autoETLsystem.dto.condition;

import autoETLsystem.dto.AutoETL_ActivityNode;
import framework.interfaces.ICondition;

public class AutoETL_ActivityNodeLog_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(order=1,isVisible=false)
	private Integer intOrder;

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}
	
	public void setStrResultType(String strResultType) {
		this.strResultType = strResultType;
	}

	public String getStrResultType() {
		return strResultType;
	}

	public void setStrActivityNodeExcuteType(String strActivityNodeExcuteType) {
		this.strActivityNodeExcuteType = strActivityNodeExcuteType;
	}

	public String getStrActivityNodeExcuteType() {
		return strActivityNodeExcuteType;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	private String strResultType;
	
	private String strActivityNodeExcuteType;
	
	@ICondition(target="autoETL_ActivityNode", targetField="strActivityNodeName")
	private AutoETL_ActivityNode autoETL_ActivityNode;
}
