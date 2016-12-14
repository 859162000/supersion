package report.dto.condition;

import report.dto.ItemInfo;
import framework.interfaces.ICondition;

public class ItemsCalculation_Condition implements java.io.Serializable{
	

	private static final long serialVersionUID = 5807459674485787441L;
	@ICondition(order=1)
	private Integer intOrder;

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}
	
	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	private ItemInfo itemInfo;
}
