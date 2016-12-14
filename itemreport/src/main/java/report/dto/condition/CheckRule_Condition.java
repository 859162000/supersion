package report.dto.condition;

import framework.interfaces.ICondition;
import report.dto.CurrencyType;
import report.dto.ItemInfo;



public class CheckRule_Condition implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5728750343054258060L;
    private String strCalculationRuleName;
	
	@ICondition(isASC = true,order=2)
	private CurrencyType currencyType;
	
	@ICondition(target="itemInfo", targetField="strItemCode", isASC = true,order=1)
	private ItemInfo itemInfo1;

	@ICondition(target="itemInfo", targetField="strItemName")
	private ItemInfo itemInfo2;
	
	@ICondition(target="itemProperty", targetField="strPropertyCode", isASC = true,order=3,isVisible=false)
	private ItemInfo itemProperty;
	
	@ICondition(isASC = true,order=4,isVisible=false)
	private Integer intOrder;

	private String strExtendDimension1;
	
	private String strExtendDimension2;
	
	
	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	public String getStrExtendDimension1() {
		return strExtendDimension1;
	}
	public void setStrExtendDimension1(String strExtendDimension1) {
		this.strExtendDimension1 = strExtendDimension1;
	}
	public String getStrExtendDimension2() {
		return strExtendDimension2;
	}
	public void setStrExtendDimension2(String strExtendDimension2) {
		this.strExtendDimension2 = strExtendDimension2;
	}
	public void setStrCalculationRuleName(String strCalculationRuleName) {
		this.strCalculationRuleName = strCalculationRuleName;
	}
	public String getStrCalculationRuleName() {
		return strCalculationRuleName;
	}
	public ItemInfo getItemInfo1() {
		return itemInfo1;
	}
	public void setItemInfo1(ItemInfo itemInfo1) {
		this.itemInfo1 = itemInfo1;
	}
	public ItemInfo getItemInfo2() {
		return itemInfo2;
	}
	public void setItemInfo2(ItemInfo itemInfo2) {
		this.itemInfo2 = itemInfo2;
	}
	public ItemInfo getItemProperty() {
		return itemProperty;
	}
	public void setItemProperty(ItemInfo itemProperty) {
		this.itemProperty = itemProperty;
	}
	public Integer getIntOrder() {
		return intOrder;
	}
	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}
	
	
}
