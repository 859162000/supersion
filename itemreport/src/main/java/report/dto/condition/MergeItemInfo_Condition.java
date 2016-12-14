package report.dto.condition;

import framework.interfaces.ICondition;
import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.MergeSummary;

public class MergeItemInfo_Condition implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5020836617954571439L;

	private MergeSummary mergeSummary;

	@ICondition(target="itemInfo", targetField="strItemCode",isASC = true,order=1)
	private ItemInfo itemInfo1;
	
/*	@ICondition(target="itemInfo", targetField="strItemName")
	private ItemInfo itemInfo2;*/
	@ICondition(isASC = true,order=2)
	private ItemProperty itemProperty;
	@ICondition(isASC = true,order=3)
	private CurrencyType currencyType;
	
	private String strExtendDimension1;
	
	private String strExtendDimension2;

	public MergeSummary getMergeSummary() {
		return mergeSummary;
	}

	public void setMergeSummary(MergeSummary mergeSummary) {
		this.mergeSummary = mergeSummary;
	}

	public ItemInfo getItemInfo1() {
		return itemInfo1;
	}

	public void setItemInfo1(ItemInfo itemInfo1) {
		this.itemInfo1 = itemInfo1;
	}

	/*public ItemInfo getItemInfo2() {
		return itemInfo2;
	}

	public void setItemInfo2(ItemInfo itemInfo2) {
		this.itemInfo2 = itemInfo2;
	}*/

	public ItemProperty getItemProperty() {
		return itemProperty;
	}

	public void setItemProperty(ItemProperty itemProperty) {
		this.itemProperty = itemProperty;
	}

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
	
}
