package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：CalcRuleInfoVO</P>
 * *********************************<br>
 * <P>类描述：计算规则详情</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-14 下午2:11:16<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-14 下午2:11:16<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@XStreamAlias("CalcRuleInfo")
@Table(name="ItemsCalculation")
public class CalcRuleInfoVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -6900463217243569933L;
	/** uuid **/
	@Column(name = "autoItemsCalculationID")
	private String autoItemsCalculationID;
	/** 类型 **/
	@Column(name = "strItemType")
	private String strItemType;
	/** 规则Id **/
	@Column(name = "autoCalculationRuleID")
	private String autoCalculationRuleID;
	/** 优先级 **/
	@Column(name = "intOrder")
	private int intOrder;
	/** 值 **/
	@Column(name = "strItemValue")
	private String strItemValue;
	public String getAutoItemsCalculationID() {
		return autoItemsCalculationID;
	}
	public void setAutoItemsCalculationID(String autoItemsCalculationID) {
		this.autoItemsCalculationID = autoItemsCalculationID;
	}
	public String getStrItemType() {
		return strItemType;
	}
	public void setStrItemType(String strItemType) {
		this.strItemType = strItemType;
	}
	public String getAutoCalculationRuleID() {
		return autoCalculationRuleID;
	}
	public void setAutoCalculationRuleID(String autoCalculationRuleID) {
		this.autoCalculationRuleID = autoCalculationRuleID;
	}
	public int getIntOrder() {
		return intOrder;
	}
	public void setIntOrder(int intOrder) {
		this.intOrder = intOrder;
	}
	public String getStrItemValue() {
		return strItemValue;
	}
	public void setStrItemValue(String strItemValue) {
		this.strItemValue = strItemValue;
	}
	
}
