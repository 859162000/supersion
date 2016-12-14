package report.dto.analyse;

import java.io.Serializable;

import framework.helper.TypeParse;

import report.dto.ItemData;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：Rep_ItemDataVO</P>
 * *********************************<br>
 * <P>类描述：指标结果集VO</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-12 下午12:44:19<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-12 下午12:44:19<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class Rep_ItemDataVO implements Serializable {

	/**  **/
	private static final long serialVersionUID = -3193950562230209995L;

	/** ID **/
	private String id;

	/** 01时间  **/
	private String dtDate;

	/** 02指标 **/
	private String itemId;

	/** 03机构 **/
	private String orgId;

	/** 04币种 **/
	private String currId;

	/** 05指标属性 **/
	private String itemProId;

	/** 06扩展维度1 **/
	private String dimension1Type;

	/** 07扩展维度2 **/
	private String dimension2Type;
	
	private String _blank="";
	
	private String freq;
	/** 值 **/
	private String strValue;
	
	public Rep_ItemDataVO(){}
	
	public Rep_ItemDataVO(ItemData data){
		this.id = data.getId();
		this.dtDate = TypeParse.parseString(data.getDtDate(), "yyyy-MM-dd");
		this.itemId = data.getItemInfo().getStrItemCode();
		this.orgId = data.getInstInfo().getStrInstCode();
		this.currId = data.getCurrencyType().getStrCurrencyCode();
		this.itemProId = data.getItemProperty().getStrPropertyCode();
		this.dimension1Type = data.getStrExtendDimension1();
		this.dimension2Type = data.getStrExtendDimension2();
		this.freq = data.getStrFreq();
		this.strValue = data.getStrValue();
		_blank = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCurrId() {
		return currId;
	}

	public void setCurrId(String currId) {
		this.currId = currId;
	}

	public String getItemProId() {
		return itemProId;
	}

	public void setItemProId(String itemProId) {
		this.itemProId = itemProId;
	}

	public String getDimension1Type() {
		return dimension1Type;
	}

	public void setDimension1Type(String dimension1Type) {
		this.dimension1Type = dimension1Type;
	}

	public String getDimension2Type() {
		return dimension2Type;
	}

	public void setDimension2Type(String dimension2Type) {
		this.dimension2Type = dimension2Type;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String get_blank() {
		return _blank;
	}

	public void set_blank(String _blank) {
		this._blank = _blank;
	}

}
