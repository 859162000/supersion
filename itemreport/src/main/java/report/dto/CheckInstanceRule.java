package report.dto;

import java.io.Serializable;

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
@Table(name = "CheckInstanceRule")
public class CheckInstanceRule implements  Serializable{
	
	
	/**  **/
	private static final long serialVersionUID = -8826353252280793254L;

	@Id 
	@Column(name = "autoCheckInstanceRuleID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoCheckInstanceRuleID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoCheckInstanceID")
	@IColumn(description="实例名称")
	private CheckInstance autoCheckInstanceID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoCalculationRuleID")
	@IColumn(description="规则名称")
	private CheckRule checkRule;
	
	@Transient
	@IColumn(isIdListField = true, target="checkRule")
	private String[] calculationRuleIdList;
	
	public CheckInstance getAutoCheckInstanceID() {
		return autoCheckInstanceID;
	}

	public void setAutoCheckInstanceID(CheckInstance autoCheckInstanceID) {
		this.autoCheckInstanceID = autoCheckInstanceID;
	}
	public String[] getCalculationRuleIdList() {
		return calculationRuleIdList;
	}

	public void setCalculationRuleIdList(String[] calculationRuleIdList) {
		this.calculationRuleIdList = calculationRuleIdList;
	}

	public String getAutoCheckInstanceRuleID() {
		return autoCheckInstanceRuleID;
	}
	
	public void setAutoCheckInstanceRuleID(String autoCheckInstanceRuleID) {
		this.autoCheckInstanceRuleID = autoCheckInstanceRuleID;
	}

	public void setCheckRule(CheckRule checkRule) {
		this.checkRule = checkRule;
	}

	public CheckRule getCheckRule() {
		return checkRule;
	}	
}
