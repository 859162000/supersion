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
import extend.dto.AutoETL_ViewField;
import extend.dto.ReportModel_Field;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeFieldViewV")
@IEntity(navigationName="设置视图字段映射",description="设置视图字段映射")
public class AutoETL_ActivityNodeFieldViewV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoSourceViewFieldID")
	@IColumn(description="源视图字段")
	private AutoETL_ViewField autoETL_SourceViewField;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTargetViewFieldID", nullable = false)
	@IColumn(description="目标字段", isNullable = false)
	private ReportModel_Field reportModel_TagetField;
	
	@Column(name="strValue",length = 4000)
	@IColumn(description="目标值")
	private String strValue;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeForCVID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV;

	@Id
	@Column(name = "autoActivityNodeViewVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeViewVID;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeFieldViewV")
	private Set<AutoETL_ActivityNodeFieldViCa> autoETL_ActivityNodeFieldViCas = new HashSet<AutoETL_ActivityNodeFieldViCa>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeFieldViewV")
	private Set<AutoETL_ActivityNodeFieldVVCa> autoETL_ActivityNodeFieldVVCas = new HashSet<AutoETL_ActivityNodeFieldVVCa>(0);
		
	public void setAutoActivityNodeViewVID(String autoActivityNodeViewVID) {
		this.autoActivityNodeViewVID = autoActivityNodeViewVID;
	}

	public String getAutoActivityNodeViewVID() {
		return autoActivityNodeViewVID;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setAutoETL_ActivityNodeForCV(AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV) {
		this.autoETL_ActivityNodeForCV = autoETL_ActivityNodeForCV;
	}

	public AutoETL_ActivityNodeForCV getAutoETL_ActivityNodeForCV() {
		return autoETL_ActivityNodeForCV;
	}

	public void setAutoETL_SourceViewField(AutoETL_ViewField autoETL_SourceViewField) {
		this.autoETL_SourceViewField = autoETL_SourceViewField;
	}

	public AutoETL_ViewField getAutoETL_SourceViewField() {
		return autoETL_SourceViewField;
	}

	public void setAutoETL_ActivityNodeFieldViCas(
			Set<AutoETL_ActivityNodeFieldViCa> autoETL_ActivityNodeFieldViCas) {
		this.autoETL_ActivityNodeFieldViCas = autoETL_ActivityNodeFieldViCas;
	}

	public Set<AutoETL_ActivityNodeFieldViCa> getAutoETL_ActivityNodeFieldViCas() {
		return autoETL_ActivityNodeFieldViCas;
	}

	public void setAutoETL_ActivityNodeFieldVVCas(
			Set<AutoETL_ActivityNodeFieldVVCa> autoETL_ActivityNodeFieldVVCas) {
		this.autoETL_ActivityNodeFieldVVCas = autoETL_ActivityNodeFieldVVCas;
	}

	public Set<AutoETL_ActivityNodeFieldVVCa> getAutoETL_ActivityNodeFieldVVCas() {
		return autoETL_ActivityNodeFieldVVCas;
	}

	public void setReportModel_TagetField(ReportModel_Field reportModel_TagetField) {
		this.reportModel_TagetField = reportModel_TagetField;
	}

	public ReportModel_Field getReportModel_TagetField() {
		return reportModel_TagetField;
	}

}


