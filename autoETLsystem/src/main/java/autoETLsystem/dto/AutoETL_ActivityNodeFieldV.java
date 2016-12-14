package autoETLsystem.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import extend.dto.ReportModel_Field;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeFieldV")
@IEntity(navigationName="设置映射",description="设置映射")
public class AutoETL_ActivityNodeFieldV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoRelationFieldID")
	@IColumn(description="源表字段")
	private ReportModel_Field autoETL_SourceField;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFieldID", nullable = false)
	@IColumn(description="目标字段", isNullable = false)
	private ReportModel_Field autoETL_TargetField;
	
	@Column(name="strValue",length = 4000)
	@IColumn(description="目标值")
	private String strValue;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeForCTID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeForCT autoETL_ActivityNodeForCT;

	@Id
	@Column(name = "autoActivityNodeFieldVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeFieldVID;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeFieldV")
	private Set<AutoETL_ActivityNodeFieldVCa> autoETL_ActivityNodeFieldVCas = new HashSet<AutoETL_ActivityNodeFieldVCa>(0);

	public AutoETL_ActivityNodeForCT getAutoETL_ActivityNodeForCT() {
		return autoETL_ActivityNodeForCT;
	}

	public void setAutoETL_ActivityNodeForCT(
			AutoETL_ActivityNodeForCT autoETLActivityNodeForCT) {
		autoETL_ActivityNodeForCT = autoETLActivityNodeForCT;
	}

	public void setAutoActivityNodeFieldVID(String autoActivityNodeFieldVID) {
		this.autoActivityNodeFieldVID = autoActivityNodeFieldVID;
	}

	public String getAutoActivityNodeFieldVID() {
		return autoActivityNodeFieldVID;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setAutoETL_SourceField(ReportModel_Field autoETL_SourceField) {
		this.autoETL_SourceField = autoETL_SourceField;
	}

	public ReportModel_Field getAutoETL_SourceField() {
		return autoETL_SourceField;
	}

	public void setAutoETL_TargetField(ReportModel_Field autoETL_TargetField) {
		this.autoETL_TargetField = autoETL_TargetField;
	}

	public ReportModel_Field getAutoETL_TargetField() {
		return autoETL_TargetField;
	}

	public void setAutoETL_ActivityNodeFieldVCas(
			Set<AutoETL_ActivityNodeFieldVCa> autoETL_ActivityNodeFieldVCas) {
		this.autoETL_ActivityNodeFieldVCas = autoETL_ActivityNodeFieldVCas;
	}

	public Set<AutoETL_ActivityNodeFieldVCa> getAutoETL_ActivityNodeFieldVCas() {
		return autoETL_ActivityNodeFieldVCas;
	}
}


