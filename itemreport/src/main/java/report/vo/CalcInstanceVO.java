package report.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：CalcInstanceVO</P>
 * *********************************<br>
 * <P>类描述：计算实例</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-14 下午1:47:08<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-14 下午1:47:08<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Table(name="CalculationExampleInfo")
public class CalcInstanceVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -4590471547264834702L;
	/** 实例名称 **/
	@Column
	private String strCalculationName;
	/** 描述 **/
	@Column
	private String strDescription;
	/** 精度位数 **/
	@Column
	private Integer intPrecise;
	/** 计算舍入方式 **/
	@Column
	private String strCalRoundMethod;
	
	public String getStrCalculationName() {
		return strCalculationName;
	}
	public void setStrCalculationName(String strCalculationName) {
		this.strCalculationName = strCalculationName;
	}
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	public Integer getIntPrecise() {
		return intPrecise;
	}
	public void setIntPrecise(Integer intPrecise) {
		this.intPrecise = intPrecise;
	}
	public String getStrCalRoundMethod() {
		return strCalRoundMethod;
	}
	public void setStrCalRoundMethod(String strCalRoundMethod) {
		this.strCalRoundMethod = strCalRoundMethod;
	}
	
}
