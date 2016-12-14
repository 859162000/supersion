package autoETLsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import extend.dto.ReportModel_Field;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_FieldValue")
@IEntity(navigationName="字段值",description="字段值")
public class AutoETL_FieldValue implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="strFieldValue",length = 50,nullable=false)
	@IColumn(description="字段值",isKeyName=true, isNullable = false)
	private String strFieldValue;
	
	@Column(name="strFieldMappingValue",length = 50,nullable=false)
	@IColumn(description="映射值", isNullable = false)
	private String strFieldMappingValue;

	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AutoFieldID", nullable = false)
	private ReportModel_Field autoETL_Field;

	
	@Id
	@Column(name = "autoFieldValueID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoFieldValueID;
	
	
	public String getStrFieldValue() {
		return strFieldValue;
	}

	public void setStrFieldValue(String strFieldValue) {
		this.strFieldValue = strFieldValue;
	}

	public String getStrFieldMappingValue() {
		return strFieldMappingValue;
	}

	public void setStrFieldMappingValue(String strFieldMappingValue) {
		this.strFieldMappingValue = strFieldMappingValue;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoFieldValueID(String autoFieldValueID) {
		this.autoFieldValueID = autoFieldValueID;
	}

	public String getAutoFieldValueID() {
		return autoFieldValueID;
	}

	public void setAutoETL_Field(ReportModel_Field autoETL_Field) {
		this.autoETL_Field = autoETL_Field;
	}

	public ReportModel_Field getAutoETL_Field() {
		return autoETL_Field;
	}
}





