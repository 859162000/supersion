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

import extend.dto.AutoETL_View;
import extend.dto.ReportModel_Table;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForCV")
@IEntity(navigationName="条件视图结点",description="条件视图结点")
public class AutoETL_ActivityNodeForCV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoViewID", nullable = false)
	@IColumn(description="源视图", isNullable = false)
	private AutoETL_View autoETL_SourceView;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTargetViewID", nullable = false)
	@IColumn(description="目标表", isNullable = false)
	private ReportModel_Table autoETL_TargetTable;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@Column(name = "cacheLine", nullable = false)
	@IColumn(description="缓存行数", isNullable = false)
	private Integer cacheLine;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForCV")
	private Set<AutoETL_ActivityNodeRelationV> autoETL_ActivityNodeRelationVs = new HashSet<AutoETL_ActivityNodeRelationV>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForCV")
	private Set<AutoETL_ActivityNodeFieldViewV> autoETL_ActivityNodeFieldViewVs = new HashSet<AutoETL_ActivityNodeFieldViewV>(0);

	
	@Id
	@Column(name = "autoActivityNodeForCVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForCVID;

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_SourceView(AutoETL_View autoETL_SourceView) {
		this.autoETL_SourceView = autoETL_SourceView;
	}

	public AutoETL_View getAutoETL_SourceView() {
		return autoETL_SourceView;
	}

	public void setCacheLine(String cacheLine) {
		this.cacheLine = TypeParse.parseInt(cacheLine);
	}

	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setAutoActivityNodeForCVID(String autoActivityNodeForCVID) {
		this.autoActivityNodeForCVID = autoActivityNodeForCVID;
	}

	public String getAutoActivityNodeForCVID() {
		return autoActivityNodeForCVID;
	}

	public void setAutoETL_TargetTable(ReportModel_Table autoETL_TargetTable) {
		this.autoETL_TargetTable = autoETL_TargetTable;
	}

	public ReportModel_Table getAutoETL_TargetTable() {
		return autoETL_TargetTable;
	}

	public void setAutoETL_ActivityNodeRelationVs(
			Set<AutoETL_ActivityNodeRelationV> autoETL_ActivityNodeRelationVs) {
		this.autoETL_ActivityNodeRelationVs = autoETL_ActivityNodeRelationVs;
	}

	public Set<AutoETL_ActivityNodeRelationV> getAutoETL_ActivityNodeRelationVs() {
		return autoETL_ActivityNodeRelationVs;
	}

	public void setAutoETL_ActivityNodeFieldViewVs(
			Set<AutoETL_ActivityNodeFieldViewV> autoETL_ActivityNodeFieldViewVs) {
		this.autoETL_ActivityNodeFieldViewVs = autoETL_ActivityNodeFieldViewVs;
	}

	public Set<AutoETL_ActivityNodeFieldViewV> getAutoETL_ActivityNodeFieldViewVs() {
		return autoETL_ActivityNodeFieldViewVs;
	}

}

