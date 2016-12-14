package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：CalcReVO</P>
 * *********************************<br>
 * <P>类描述：计算实例规则关系</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-14 下午1:49:18<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-14 下午1:49:18<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@XStreamAlias("CalcRe")
@Table(name="ExampleInfoRule")
public class CalcReVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = 5477872831520045488L;
	/** uuid **/
	@Column(name="autoExampleRuleID")
	private String autoExampleRuleID;
	/** 实例Id **/
	@Column(name="autoCalculationID")
	private String autoCalculationID;
	/** 规则Id **/
	@Column(name="autoCalculationRuleID")
	private String autoCalculationRuleID;
	/** 计算规则 **/
	private CalcRuleVO calcRule;
	public String getAutoExampleRuleID() {
		return autoExampleRuleID;
	}
	public void setAutoExampleRuleID(String autoExampleRuleID) {
		this.autoExampleRuleID = autoExampleRuleID;
	}
	public String getAutoCalculationID() {
		return autoCalculationID;
	}
	public void setAutoCalculationID(String autoCalculationID) {
		this.autoCalculationID = autoCalculationID;
	}
	public String getAutoCalculationRuleID() {
		return autoCalculationRuleID;
	}
	public void setAutoCalculationRuleID(String autoCalculationRuleID) {
		this.autoCalculationRuleID = autoCalculationRuleID;
	}
	public CalcRuleVO getCalcRule() {
		return calcRule;
	}
	public void setCalcRule(CalcRuleVO calcRule) {
		this.calcRule = calcRule;
	}
}
