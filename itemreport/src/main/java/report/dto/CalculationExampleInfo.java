package report.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import framework.helper.TypeParse;
import org.hibernate.annotations.Cascade;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "CalculationExampleInfo")
public class CalculationExampleInfo implements java.io.Serializable{
	

	/**  **/
	private static final long serialVersionUID = -4769166489096248700L;

	@Id
	@Column(name="strCalculationName",length = 50,nullable=false)
	@IColumn(description="实例名称",isKeyName=true, isNullable = false)
	private String strCalculationName;
	
	@Column(name="strDescription",length = 200)
	@IColumn(description="描述")
	private String strDescription;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "calculationExampleInfo")
	private Set<ExampleInfoRule> exampleInfoRules = new HashSet<ExampleInfoRule>(0);
	public void setExampleInfoRules(Set<ExampleInfoRule> exampleInfoRules) {
		this.exampleInfoRules = exampleInfoRules;
	}

	public Set<ExampleInfoRule> getExampleInfoRules() {
		return exampleInfoRules;
	}
	
	
	
	
	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public void setStrCalculationName(String strCalculationName) {
		this.strCalculationName = strCalculationName;
	}
					
	public String getStrCalculationName() {
		return strCalculationName;
	}
	
	@Column(name = "intPrecise",nullable=false)
	@IColumn(description="精度位数",isNullable=false)
	private Integer intPrecise;
	public void setIntPrecise(String intPrecise) {
		this.intPrecise =TypeParse.parseInt(intPrecise);
	}

	public Integer getIntPrecise() {
		return intPrecise;
	}
	
	@Column(name = "strCalRoundMethod",length=50, nullable=false)
	@IColumn(description="计算舍入方式",tagMethodName="getStrCalRoundMethodTag",isNullable=false)
	private String strCalRoundMethod;
	public void setStrCalRoundMethod(String strRoundMethod) {
		//this.intPrecise =TypeParse.parseInt(intPrecise) ;
		this.strCalRoundMethod=strRoundMethod;
	}

	public String getStrCalRoundMethod() {
		return strCalRoundMethod;
	}
	public static Map<String,String> getStrCalRoundMethodTag(){
		return ShowContext.getInstance().getShowEntityMap().get("calRoundMehod");
	}
}
