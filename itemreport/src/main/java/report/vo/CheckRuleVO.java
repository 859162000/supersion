package report.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="CheckRule")
public class CheckRuleVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -5829674558563005085L;
	@Column
	private String autoCalculationRuleID;
	@Column
	private String strCalculationRuleName;	
	@Column
	private String strItemCode;
	@Column
	private String strPropertyCode;
	@Column
	private String currencyType;
	@Column
	private Date startdate;
	@Column
	private Date enddate;
	@Column
	private String strExtendDimension1;
	@Column
	private String strExtendDimension2;
	@Column
	private String strDescription;
	@Column
	private Integer intOrder;
	@Column
	private String strExpression;
	@Column
	private String strFreq;
	@Column
    private Double dblTolerance;
	@Column
	private String compareType;
	@Column
	private String source;
	private List<CheckRuleInfoVO> ruleInfoList;
	public List<CheckRuleInfoVO> getRuleInfoList() {
		return ruleInfoList;
	}
	public void setRuleInfoList(List<CheckRuleInfoVO> ruleInfoList) {
		this.ruleInfoList = ruleInfoList;
	}
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
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	public Integer getIntOrder() {
		return intOrder;
	}
	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}
	public String getStrExpression() {
		return strExpression;
	}
	public void setStrExpression(String strExpression) {
		this.strExpression = strExpression;
	}
	public String getStrFreq() {
		return strFreq;
	}
	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}
	public Double getDblTolerance() {
		return dblTolerance;
	}
	public void setDblTolerance(Double dblTolerance) {
		this.dblTolerance = dblTolerance;
	}
	public String getCompareType() {
		return compareType;
	}
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
