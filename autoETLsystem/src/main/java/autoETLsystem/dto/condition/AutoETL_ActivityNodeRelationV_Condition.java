package autoETLsystem.dto.condition;

import framework.interfaces.ICondition;

public class AutoETL_ActivityNodeRelationV_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(order=1,isVisible=false)
	private Integer intOrder;

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}
}

