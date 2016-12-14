package report.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：CalcRuleVO</P>
 * *********************************<br>
 * <P>类描述：计算规则</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-14 下午2:11:52<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-14 下午2:11:52<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Table(name="CalculationRule")
public class CalcRuleVO implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -8915240693044199784L;
	/** 规则Id **/
	@Column
	private String autoCalculationRuleID;
	/** 规则名称 **/
	@Column
	private String strCalculationRuleName;
	/** 描述 **/
	@Column
	private String strDescription;
	/** 计算优先级 **/
	@Column
	private int intOrder;
	/** 计算表达式 **/
	@Column
	private String strExpression;
	/** 有效-开始时间 **/
	@Column
	private Date startdate;
	/** 有效-结束时间 **/
	@Column
	private Date enddate;
	/** 精度 **/
	@Column
	private int intPrecise;
	/** 舍入方式 **/
	@Column
	private String strCalRoundMethod;
	/** 指标code **/
	@Column
	private String strItemCode;
	/** 指标类型 **/
	@Column
	private String strPropertyCode;
	/** 指标币种 **/
	@Column
	private String currencyType;
	/** 指标频度 **/
	@Column
	private String strFreq;
	/** 指标扩展属性1 **/
	@Column
	private String strExtendDimension1;
	/** 指标扩展属性2 **/
	@Column
	private String strExtendDimension2;
	@Column
	private String source;
	/** 计算规则详情 **/
	private List<CalcRuleInfoVO> ruleInfoList;
	public String getAutoCalculationRuleID() {
		return autoCalculationRuleID;
	}
	public void setAutoCalculationRuleID(String autoCalculationRuleID) {
		this.autoCalculationRuleID = autoCalculationRuleID;
	}
	public String getStrCalculationRuleName() {
		return strCalculationRuleName;
	}
	public void setStrCalculationRuleName(String strCalculationRuleName) {
		this.strCalculationRuleName = strCalculationRuleName;
	}
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	public int getIntOrder() {
		return intOrder;
	}
	public void setIntOrder(int intOrder) {
		this.intOrder = intOrder;
	}
	public String getStrExpression() {
		return strExpression;
	}
	public void setStrExpression(String strExpression) {
		this.strExpression = strExpression;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getIntPrecise() {
		return intPrecise;
	}
	public void setIntPrecise(int intPrecise) {
		this.intPrecise = intPrecise;
	}
	public String getStrCalRoundMethod() {
		return strCalRoundMethod;
	}
	public void setStrCalRoundMethod(String strCalRoundMethod) {
		this.strCalRoundMethod = strCalRoundMethod;
	}
	public String getStrItemCode() {
		return strItemCode;
	}
	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}
	public String getStrPropertyCode() {
		return strPropertyCode;
	}
	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getStrFreq() {
		return strFreq;
	}
	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
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
	public List<CalcRuleInfoVO> getRuleInfoList() {
		return ruleInfoList;
	}
	public void setRuleInfoList(List<CalcRuleInfoVO> ruleInfoList) {
		this.ruleInfoList = ruleInfoList;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
