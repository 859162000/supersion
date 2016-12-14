package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;

@Entity
@Table(name = "ExampleInfoRule")
public class ExampleInfoRule implements java.io.Serializable{
	
	
	/**  **/
	private static final long serialVersionUID = 3412066227530316895L;

	@Id
	@Column(name = "autoExampleRuleID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoExampleRuleID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoCalculationID")
	@IColumn(description="实例名称")
	private CalculationExampleInfo calculationExampleInfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoCalculationRuleID")
	@IColumn(description="规则名称")
	private CalculationRule calculationRule;
	
	@Transient
	@IColumn(isIdListField = true, target="calculationRule")
	private String[] calculationRuleIdList;

	public String getAutoExampleRuleID() {
		return autoExampleRuleID;
	}

	public void setAutoExampleRuleID(String autoExampleRuleID) {
		this.autoExampleRuleID = autoExampleRuleID;
	}

	public CalculationExampleInfo getCalculationExampleInfo() {
		return calculationExampleInfo;
	}

	public void setCalculationExampleInfo(
			CalculationExampleInfo calculationExampleInfo) {
		this.calculationExampleInfo = calculationExampleInfo;
	}

	public CalculationRule getCalculationRule() {
		return calculationRule;
	}

	public void setCalculationRule(CalculationRule calculationRule) {
		this.calculationRule = calculationRule;
	}

	public String[] getCalculationRuleIdList() {
		return calculationRuleIdList;
	}

	public void setCalculationRuleIdList(String[] calculationRuleIdList) {
		this.calculationRuleIdList = calculationRuleIdList;
	}
	
}
