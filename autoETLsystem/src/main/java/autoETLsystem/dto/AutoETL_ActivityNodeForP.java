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

import extend.dto.AutoETL_Procedure;
import extend.dto.ReportModel_Table;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForP")
@IEntity(navigationName="存储过程传输数据",description="存储过程传输数据")
public class AutoETL_ActivityNodeForP implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoProcedureID", nullable = false)
	private AutoETL_Procedure autoETL_Procedure;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoTargetTableID", nullable = false)
	@IColumn(description="目标表", isNullable = false)
	private ReportModel_Table autoETL_TargetTable;
	
	@Column(name = "cacheLine", nullable = false)
	@IColumn(description="缓存行数", isNullable = false)
	private Integer cacheLine;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Id
	@Column(name = "autoActivityNodeForSqlID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForSqlID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;

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

	public void setCacheLine(String cacheLine) {
		this.cacheLine = TypeParse.parseInt(cacheLine);
	}

	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setAutoActivityNodeForSqlID(String autoActivityNodeForSqlID) {
		this.autoActivityNodeForSqlID = autoActivityNodeForSqlID;
	}

	public String getAutoActivityNodeForSqlID() {
		return autoActivityNodeForSqlID;
	}

	public void setAutoETL_Procedure(AutoETL_Procedure autoETL_Procedure) {
		this.autoETL_Procedure = autoETL_Procedure;
	}

	public AutoETL_Procedure getAutoETL_Procedure() {
		return autoETL_Procedure;
	}

	public void setAutoETL_TargetTable(ReportModel_Table autoETL_TargetTable) {
		this.autoETL_TargetTable = autoETL_TargetTable;
	}

	public ReportModel_Table getAutoETL_TargetTable() {
		return autoETL_TargetTable;
	}

}

