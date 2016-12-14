package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CheckFieldEffectiveValues")
@IEntity(navigationName="有效性值校验",description="有效性值校验")
public class CheckFieldEffectiveValues implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -5877576501593273703L;

	@Id
	@Column(name = "checkFieldEffectiveValuesID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldEffectiveValuesID;
	
	@Column(name="value",length =35,nullable=false)
	@IColumn(description="范围值",isKeyName=true)
	private String value;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldEffectiveID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckFieldEffective checkFieldEffective;

	public String getCheckFieldEffectiveValuesID() {
		return checkFieldEffectiveValuesID;
	}

	public void setCheckFieldEffectiveValuesID(String checkFieldEffectiveValuesID) {
		this.checkFieldEffectiveValuesID = checkFieldEffectiveValuesID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setCheckFieldEffective(CheckFieldEffective checkFieldEffective) {
		this.checkFieldEffective = checkFieldEffective;
	}

	public CheckFieldEffective getCheckFieldEffective() {
		return checkFieldEffective;
	}
}
