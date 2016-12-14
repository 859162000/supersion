package extend.dto.condition;

import framework.interfaces.ICondition;

public class SystemCodeValue_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String strCode;
    
	private String strValue;

	@ICondition(order=1)
	private Integer strOrder;

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public Integer getStrOrder() {
		return strOrder;
	}

	public void setStrOrder(Integer strOrder) {
		this.strOrder = strOrder;
	}

	
	
}

