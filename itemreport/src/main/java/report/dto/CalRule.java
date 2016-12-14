package report.dto;

import java.util.Date;

public class CalRule implements java.io.Serializable {


	/**  **/
	private static final long serialVersionUID = 1847673717964070106L;
	private String autoCalculationRuleID;
	private String strCalculationRuleName;
	private String strItemCode;
	private String strPropertyCode;
	private String currencyType;
	private Date startdate;
	private Date enddate;
	private String strExtendDimension1;
	private String strExtendDimension2;
	private String strFreq;
	private Integer intOrder;
	private String strExpression;
	private String compareType;
	private String strCalRoundMethod;
	private Integer intPrecise;
	private Double dblTolerance;

	public CalRule(String autoCalculationRuleID, String strCalculationRuleName, String strItemCode, String strPropertyCode, String currencyType, Date startdate, Date enddate, String strExtendDimension1, String strExtendDimension2, Integer intOrder, String strExpression, String freq, String strCalRoundMethod, Integer intPrecise) {
		this.autoCalculationRuleID = autoCalculationRuleID;
		this.strCalculationRuleName = strCalculationRuleName;
		this.strItemCode = strItemCode;
		this.strPropertyCode = strPropertyCode;
		this.currencyType = currencyType;
		this.startdate = startdate;
		this.enddate = enddate;
		this.strExtendDimension1 = strExtendDimension1;
		this.strExtendDimension2 = strExtendDimension2;
		this.intOrder = intOrder;
		this.strExpression = strExpression;
		this.compareType = "=";
		this.strFreq = freq;
		this.strCalRoundMethod = strCalRoundMethod;
		this.intPrecise = intPrecise;
		this.dblTolerance = 0.0;
	}

	public CalRule(String autoCalculationRuleID, String strCalculationRuleName, String strItemCode, String strPropertyCode, String currencyType, Date startdate, Date enddate, String strExtendDimension1, String strExtendDimension2, Integer intOrder, String strExpression, String compareType, String freq, Double dblTolerance) {
		this.autoCalculationRuleID = autoCalculationRuleID;
		this.strCalculationRuleName = strCalculationRuleName;
		this.strItemCode = strItemCode;
		this.strPropertyCode = strPropertyCode;
		this.currencyType = currencyType;
		this.startdate = startdate;
		this.enddate = enddate;
		this.strExtendDimension1 = strExtendDimension1;
		this.strExtendDimension2 = strExtendDimension2;
		this.intOrder = intOrder;
		this.strExpression = strExpression;
		this.compareType = compareType;
		this.strFreq = freq;
		this.dblTolerance = dblTolerance;
		this.intPrecise = null;
		this.strCalRoundMethod = null;

	}

	public String getAutoCalculationRuleID() {
		return this.autoCalculationRuleID;
	}

	public String getStrCalculationRuleName() {
		return this.strCalculationRuleName;
	}

	public String getStrItemCode() {
		return this.strItemCode;
	}

	public String getStrPropertyCode() {
		return this.strPropertyCode;
	}

	public String getCurrencyType() {
		return this.currencyType;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public String getStrExtendDimension1() {
		return this.strExtendDimension1;
	}

	public String getStrExtendDimension2() {
		return this.strExtendDimension2;
	}

	public Integer getIntOrder() {
		return this.intOrder;
	}

	public String getStrExpression() {
		return this.strExpression;
	}

	public void setStrExpression(String expression) {
		this.strExpression = expression;
	}

	public String getCompareType() {
		return this.compareType;
	}

	public String getStrFreq() {
		return this.strFreq;
	}

	public Double getDblTolerance() {
		return this.dblTolerance;
	}

	public String getStrCalRoundMethod() {
		return this.strCalRoundMethod;
	}

	public Integer getIntPrecise() {
		return this.intPrecise;
	}

	public String toString() {
		return String.format("autoCalculationRuleID:%s strCalculationRuleName:%s strItemCode:%s strPropertyCode:%s currencyType:%s startdate:%tF enddate:%tF strExtendDimension1:%s strExtendDimension2:%s intOrder:%d strExpression:%s compareType:%s strFreq:%s dblTolerance:%f intPrecise:%d strCalRoundMethod:%s", autoCalculationRuleID, strCalculationRuleName, strItemCode, strPropertyCode,
				currencyType, startdate, enddate, strExtendDimension1, strExtendDimension2, intOrder, strExpression, compareType, strFreq, dblTolerance, intPrecise, strCalRoundMethod);

	}

}
