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

import extend.dto.AutoETL_Procedure;
import extend.dto.ReportModel_Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForProcTC")
@IEntity(navigationName="存储过程传输及校验",description="存储过程传输及校验")
public class AutoETL_ActivityNodeForProceTranslateWithCheck  implements java.io.Serializable{

	/**
	 * 
	 */
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
	
	@Column(name="strCheckName",length = 200, nullable = false)
	@IColumn(description="实例名称", isNullable = false)
	private String strCheckName;
	
	@IColumn(tagMethodName="getCheckSourceTag",description="实例来源", isNullable = false)
	@Column(name = "strInstanceSourceType", nullable = false)
	private String strInstanceSourceType;
	
	public static Map<String,String> getCheckSourceTag(){
		return ShowContext.getInstance().getShowEntityMap().get("checkInstanceSource");
	}
	
	@IColumn(tagMethodName="getActivityNodeErrorTag",description="校验错误时结点状态", isNullable = false)
	@Column(name = "strActivityNodeErrorType", nullable = false)
	private String strActivityNodeErrorType;
	
	public static Map<String,String> getActivityNodeErrorTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeErrorType");
	}

	@Column(name="strEntityClass",length = 200)
	@IColumn(description="实体类")
	private String strEntityClass;
	
	@Column(name="strCheckClass",length = 200)
	@IColumn(description="校验类")
	private String strCheckClass;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Id
	@Column(name = "autoActivityNodeForProcTCID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForProcTCID;

	public AutoETL_Procedure getAutoETL_Procedure() {
		return autoETL_Procedure;
	}

	public void setAutoETL_Procedure(AutoETL_Procedure autoETLProcedure) {
		autoETL_Procedure = autoETLProcedure;
	}

	public ReportModel_Table getAutoETL_TargetTable() {
		return autoETL_TargetTable;
	}

	public void setAutoETL_TargetTable(ReportModel_Table autoETLTargetTable) {
		autoETL_TargetTable = autoETLTargetTable;
	}

	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setCacheLine(Integer cacheLine) {
		this.cacheLine = cacheLine;
	}

	public String getStrCheckName() {
		return strCheckName;
	}

	public void setStrCheckName(String strCheckName) {
		this.strCheckName = strCheckName;
	}

	public String getStrInstanceSourceType() {
		return strInstanceSourceType;
	}

	public void setStrInstanceSourceType(String strInstanceSourceType) {
		this.strInstanceSourceType = strInstanceSourceType;
	}

	public String getStrActivityNodeErrorType() {
		return strActivityNodeErrorType;
	}

	public void setStrActivityNodeErrorType(String strActivityNodeErrorType) {
		this.strActivityNodeErrorType = strActivityNodeErrorType;
	}

	public String getStrEntityClass() {
		return strEntityClass;
	}

	public void setStrEntityClass(String strEntityClass) {
		this.strEntityClass = strEntityClass;
	}

	public String getStrCheckClass() {
		return strCheckClass;
	}

	public void setStrCheckClass(String strCheckClass) {
		this.strCheckClass = strCheckClass;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETLActivityNode) {
		autoETL_ActivityNode = autoETLActivityNode;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public String getAutoActivityNodeForProcTCID() {
		return autoActivityNodeForProcTCID;
	}

	public void setAutoActivityNodeForProcTCID(String autoActivityNodeForProcTCID) {
		this.autoActivityNodeForProcTCID = autoActivityNodeForProcTCID;
	}
	
	

}
