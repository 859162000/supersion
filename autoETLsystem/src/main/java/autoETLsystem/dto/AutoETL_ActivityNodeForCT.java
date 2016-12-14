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

import extend.dto.ReportModel_Table;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForCT")
@IEntity(navigationName="条件数据表结点",description="条件数据表结点")
public class AutoETL_ActivityNodeForCT implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTableID", nullable = false)
	@IColumn(description="源表", isNullable = false)
	private ReportModel_Table autoETL_SourceTable;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTargetTableID", nullable = false)
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
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForCT")
	private Set<AutoETL_ActivityNodeRelationF> autoETL_ActivityNodeRelationFs = new HashSet<AutoETL_ActivityNodeRelationF>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForCT")
	private Set<AutoETL_ActivityNodeFieldV> autoETL_ActivityNodeFieldVs = new HashSet<AutoETL_ActivityNodeFieldV>(0);

	@Id
	@Column(name = "autoActivityNodeForCTID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForCTID;

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

	public void setAutoETL_ActivityNodeRelationFs(
			Set<AutoETL_ActivityNodeRelationF> autoETL_ActivityNodeRelationFs) {
		this.autoETL_ActivityNodeRelationFs = autoETL_ActivityNodeRelationFs;
	}

	public Set<AutoETL_ActivityNodeRelationF> getAutoETL_ActivityNodeRelationFs() {
		return autoETL_ActivityNodeRelationFs;
	}

	public void setAutoETL_ActivityNodeFieldVs(
			Set<AutoETL_ActivityNodeFieldV> autoETL_ActivityNodeFieldVs) {
		this.autoETL_ActivityNodeFieldVs = autoETL_ActivityNodeFieldVs;
	}

	public Set<AutoETL_ActivityNodeFieldV> getAutoETL_ActivityNodeFieldVs() {
		return autoETL_ActivityNodeFieldVs;
	}

	public void setAutoActivityNodeForCTID(String autoActivityNodeForCTID) {
		this.autoActivityNodeForCTID = autoActivityNodeForCTID;
	}

	public String getAutoActivityNodeForCTID() {
		return autoActivityNodeForCTID;
	}

	public void setCacheLine(String cacheLine) {
		this.cacheLine = TypeParse.parseInt(cacheLine);
	}

	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setAutoETL_SourceTable(ReportModel_Table autoETL_SourceTable) {
		this.autoETL_SourceTable = autoETL_SourceTable;
	}

	public ReportModel_Table getAutoETL_SourceTable() {
		return autoETL_SourceTable;
	}

	public void setAutoETL_TargetTable(ReportModel_Table autoETL_TargetTable) {
		this.autoETL_TargetTable = autoETL_TargetTable;
	}

	public ReportModel_Table getAutoETL_TargetTable() {
		return autoETL_TargetTable;
	}

}

