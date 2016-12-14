package autoETLsystem.dto;

import java.util.Map;

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
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeFieldVC")
@IEntity(navigationName="映射值",description="映射值")
public class AutoETL_ActivityNodeFieldVC implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoRelationFieldID", nullable = false)
	@IColumn(description="源表字段", isNullable = false)
	private ReportModel_Field autoETL_SourceFields;
	
	@Column(name="autoActivityNodeFieldValueID",length = 50,nullable=false)
	@IColumn(description="字段值", isNullable = false)
	private String autoActivityNodeFieldValueID;

	@IColumn(tagMethodName="getConditionTypeTag",description="条件类型")
	@Column(name = "strConditionType")
	private String strConditionType;
	
	public static Map<String,String> getConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("conditionType");
	}
	
	@Column(name="strValue",length = 4000)
	@IColumn(description="字段值")
	private String strValue;
	

	@Id
	@Column(name = "autoActivityNodeFieldVCID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeFieldVCID;


	public String getAutoActivityNodeFieldValueID() {
		return autoActivityNodeFieldValueID;
	}

	public void setAutoActivityNodeFieldValueID(String autoActivityNodeFieldValueID) {
		this.autoActivityNodeFieldValueID = autoActivityNodeFieldValueID;
	}

	public String getStrConditionType() {
		return strConditionType;
	}

	public void setStrConditionType(String strConditionType) {
		this.strConditionType = strConditionType;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public void setAutoActivityNodeFieldVCID(String autoActivityNodeFieldVCID) {
		this.autoActivityNodeFieldVCID = autoActivityNodeFieldVCID;
	}

	public String getAutoActivityNodeFieldVCID() {
		return autoActivityNodeFieldVCID;
	}

	public void setAutoETL_SourceFields(ReportModel_Field autoETL_SourceFields) {
		this.autoETL_SourceFields = autoETL_SourceFields;
	}

	public ReportModel_Field getAutoETL_SourceFields() {
		return autoETL_SourceFields;
	}

}


