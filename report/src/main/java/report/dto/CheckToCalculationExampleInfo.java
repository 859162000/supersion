package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import framework.interfaces.IEntity;
import framework.interfaces.IColumn;

@Entity
@Table(name = "CheckToCalculationExampleInfo")
@IEntity(navigationName="校验计算实例",description="校验计算实例")
public class CheckToCalculationExampleInfo implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 2378293511932192235L;

	@Id
	@Column(name="strCalculationName",length = 50,nullable=false)
	@IColumn(description="实例名称",isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	private String strCalculationName;
	
	@Column(name="strDescription",length = 200)
	@IColumn(description="描述")
	private String strDescription;
	

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
}
