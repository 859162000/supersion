package autoETLsystem.dto;

import java.util.HashSet;
import java.util.Map;
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

import extend.dto.AutoETL_DataSource;
import extend.dto.AutoETL_Procedure;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNode")
@IEntity(navigationName="结点管理",description="结点管理")
public class AutoETL_ActivityNode implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;
	
	@Column(name="intOrderGroup",length = 50)
	@IColumn(description="顺序组")
	private String strOrderGroup;
	
	@Column(name="intOrder",nullable=false)
	@IColumn(description="顺序", isNullable = false)
	private Integer intOrder;
	
	@Column(name="strActivityNodeName",length = 50,nullable=false)
	@IColumn(description="结点名称",isKeyName=true, isNullable = false)
	private String strActivityNodeName;
	
	@IColumn(tagMethodName="getActivityNodeTypeTag",description="结点类型", isNullable = false)
	@Column(name = "strActivityNodeType", nullable = false)
	private String strActivityNodeType;
	
	public static Map<String,String> getActivityNodeTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeType");
	}
	
	@IColumn(tagMethodName="getStartConditionTypeTag",description="启动条件", isNullable = false)
	@Column(name = "strStartConditionType", nullable = false)
	private String strStartConditionType;
	
	public static Map<String,String> getStartConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("startConditionType");
	}

	@IColumn(tagMethodName="getRepeatTypeTag",description="执行次数", isNullable = false)
	@Column(name = "strRepeatType", nullable = false)
	private String strRepeatType;
	
	public static Map<String,String> getRepeatTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("repeatType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoProcedureID")
	@IColumn(description="存储过程")
	private AutoETL_Procedure autoETL_Procedure;

	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_DataSourceID")
	@IColumn(description="SQL启动条件源会话工厂")
	private AutoETL_DataSource autoETL_DataSource;
	
	@Column(name="strSqlCondition",length = 4000)
	@IColumn(description="SQL启动条件")
	private String strSqlCondition;

	
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeProcP> autoETL_ActivityNodeProcPs = new HashSet<AutoETL_ActivityNodeProcP>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForCT> autoETL_ActivityNodeForCTs = new HashSet<AutoETL_ActivityNodeForCT>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForCV> autoETL_ActivityNodeForCVs = new HashSet<AutoETL_ActivityNodeForCV>(0);

	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForDTS> autoETL_ActivityNodeForDTSs = new HashSet<AutoETL_ActivityNodeForDTS>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForFile> autoETL_ActivityNodeForFiles = new HashSet<AutoETL_ActivityNodeForFile>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForFTPTra> autoETL_ActivityNodeForFTPTras = new HashSet<AutoETL_ActivityNodeForFTPTra>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForJava> autoETL_ActivityNodeForNETs = new HashSet<AutoETL_ActivityNodeForJava>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForPro> autoETL_ActivityNodeForPros = new HashSet<AutoETL_ActivityNodeForPro>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForP> autoETL_ActivityNodeForPs = new HashSet<AutoETL_ActivityNodeForP>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeParam> autoETL_ActivityNodeParams = new HashSet<AutoETL_ActivityNodeParam>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeLog> autoETL_ActivityNodeLogs = new HashSet<AutoETL_ActivityNodeLog>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForSqlEx> autoETL_ActivityNodeForSqlExs = new HashSet<AutoETL_ActivityNodeForSqlEx>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForSql> autoETL_ActivityNodeForSqls = new HashSet<AutoETL_ActivityNodeForSql>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForExcel> autoETL_ActivityNodeForExcels = new HashSet<AutoETL_ActivityNodeForExcel>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForView> autoETL_ActivityNodeForViews = new HashSet<AutoETL_ActivityNodeForView>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForCheck> autoETL_ActivityNodeForChecks = new HashSet<AutoETL_ActivityNodeForCheck>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_WorkflowAE> autoETL_WorkflowAEs = new HashSet<AutoETL_WorkflowAE>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForKettle> autoETL_ActivityNodeForKettles = new HashSet<AutoETL_ActivityNodeForKettle>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForMail> autoETL_ActivityNodeForMails = new HashSet<AutoETL_ActivityNodeForMail>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForMsg> autoETL_ActivityNodeForMsgs = new HashSet<AutoETL_ActivityNodeForMsg>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForLogicPCheck> autoETL_ActivityNodeForLogicPChecks = new HashSet<AutoETL_ActivityNodeForLogicPCheck>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForTotalTableState> autoETL_ActivityNodeForTotalTableStates = new HashSet<AutoETL_ActivityNodeForTotalTableState>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNode")
	private Set<AutoETL_ActivityNodeForWarning> autoETL_ActivityNodeForWarnings = new HashSet<AutoETL_ActivityNodeForWarning>(0);
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Id
	@Column(name = "autoActivityNodeID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeID;
	
	@IColumn(tagMethodName="getEffectiveTypeTag",description="状态", isNullable = false)
	@Column(name = "strEffectiveType", nullable = false, length = 2)
	private String strEffectiveType;
	
	public static Map<String,String> getEffectiveTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("effectiveType");
	}

	public String getStrEffectiveType() {
		return strEffectiveType;
	}

	public void setStrEffectiveType(String strEffectiveType) {
		this.strEffectiveType = strEffectiveType;
	}
	
	public String getStrActivityNodeType() {
		return strActivityNodeType;
	}


	public void setStrActivityNodeType(String strActivityNodeType) {
		this.strActivityNodeType = strActivityNodeType;
	}


	public String getStrActivityNodeName() {
		return strActivityNodeName;
	}


	public void setStrActivityNodeName(String strActivityNodeName) {
		this.strActivityNodeName = strActivityNodeName;
	}


	public String getStrStartConditionType() {
		return strStartConditionType;
	}


	public void setStrStartConditionType(String strStartConditionType) {
		this.strStartConditionType = strStartConditionType;
	}


	public String getStrRepeatType() {
		return strRepeatType;
	}


	public void setStrRepeatType(String strRepeatType) {
		this.strRepeatType = strRepeatType;
	}

	public Integer getIntOrder() {
		return intOrder;
	}


	public void setIntOrder(String intOrder) {
		this.intOrder =TypeParse.parseInt(intOrder);
	}


	public AutoETL_Workflow getAutoETL_Workflow() {
		return autoETL_Workflow;
	}


	public void setAutoETL_Workflow(AutoETL_Workflow autoETLWorkflow) {
		autoETL_Workflow = autoETLWorkflow;
	}


	public String getStrDiscription() {
		return strDiscription;
	}


	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}


	public void setAutoETL_ActivityNodeProcPs(
			Set<AutoETL_ActivityNodeProcP> autoETL_ActivityNodeProcPs) {
		this.autoETL_ActivityNodeProcPs = autoETL_ActivityNodeProcPs;
	}


	public Set<AutoETL_ActivityNodeProcP> getAutoETL_ActivityNodeProcPs() {
		return autoETL_ActivityNodeProcPs;
	}


	public void setAutoETL_ActivityNodeForCTs(
			Set<AutoETL_ActivityNodeForCT> autoETL_ActivityNodeForCTs) {
		this.autoETL_ActivityNodeForCTs = autoETL_ActivityNodeForCTs;
	}


	public Set<AutoETL_ActivityNodeForCT> getAutoETL_ActivityNodeForCTs() {
		return autoETL_ActivityNodeForCTs;
	}


	public void setAutoETL_ActivityNodeForDTSs(
			Set<AutoETL_ActivityNodeForDTS> autoETL_ActivityNodeForDTSs) {
		this.autoETL_ActivityNodeForDTSs = autoETL_ActivityNodeForDTSs;
	}


	public Set<AutoETL_ActivityNodeForDTS> getAutoETL_ActivityNodeForDTSs() {
		return autoETL_ActivityNodeForDTSs;
	}


	public void setAutoETL_ActivityNodeForFiles(
			Set<AutoETL_ActivityNodeForFile> autoETL_ActivityNodeForFiles) {
		this.autoETL_ActivityNodeForFiles = autoETL_ActivityNodeForFiles;
	}


	public Set<AutoETL_ActivityNodeForFile> getAutoETL_ActivityNodeForFiles() {
		return autoETL_ActivityNodeForFiles;
	}


	public void setAutoETL_ActivityNodeForFTPTras(
			Set<AutoETL_ActivityNodeForFTPTra> autoETL_ActivityNodeForFTPTras) {
		this.autoETL_ActivityNodeForFTPTras = autoETL_ActivityNodeForFTPTras;
	}


	public Set<AutoETL_ActivityNodeForFTPTra> getAutoETL_ActivityNodeForFTPTras() {
		return autoETL_ActivityNodeForFTPTras;
	}


	public void setAutoETL_ActivityNodeForNETs(
			Set<AutoETL_ActivityNodeForJava> autoETL_ActivityNodeForNETs) {
		this.autoETL_ActivityNodeForNETs = autoETL_ActivityNodeForNETs;
	}


	public Set<AutoETL_ActivityNodeForJava> getAutoETL_ActivityNodeForNETs() {
		return autoETL_ActivityNodeForNETs;
	}


	public void setAutoETL_ActivityNodeForPros(
			Set<AutoETL_ActivityNodeForPro> autoETL_ActivityNodeForPros) {
		this.autoETL_ActivityNodeForPros = autoETL_ActivityNodeForPros;
	}


	public Set<AutoETL_ActivityNodeForPro> getAutoETL_ActivityNodeForPros() {
		return autoETL_ActivityNodeForPros;
	}


	public void setAutoETL_ActivityNodeParams(
			Set<AutoETL_ActivityNodeParam> autoETL_ActivityNodeParams) {
		this.autoETL_ActivityNodeParams = autoETL_ActivityNodeParams;
	}


	public Set<AutoETL_ActivityNodeParam> getAutoETL_ActivityNodeParams() {
		return autoETL_ActivityNodeParams;
	}


	public void setAutoETL_ActivityNodeLogs(Set<AutoETL_ActivityNodeLog> autoETL_ActivityNodeLogs) {
		this.autoETL_ActivityNodeLogs = autoETL_ActivityNodeLogs;
	}


	public Set<AutoETL_ActivityNodeLog> getAutoETL_ActivityNodeLogs() {
		return autoETL_ActivityNodeLogs;
	}


	public void setAutoActivityNodeID(String autoActivityNodeID) {
		this.autoActivityNodeID = autoActivityNodeID;
	}


	public String getAutoActivityNodeID() {
		return autoActivityNodeID;
	}


	public void setAutoETL_Procedure(AutoETL_Procedure autoETL_Procedure) {
		this.autoETL_Procedure = autoETL_Procedure;
	}


	public AutoETL_Procedure getAutoETL_Procedure() {
		return autoETL_Procedure;
	}


	public void setAutoETL_ActivityNodeForSqlExs(
			Set<AutoETL_ActivityNodeForSqlEx> autoETL_ActivityNodeForSqlExs) {
		this.autoETL_ActivityNodeForSqlExs = autoETL_ActivityNodeForSqlExs;
	}


	public Set<AutoETL_ActivityNodeForSqlEx> getAutoETL_ActivityNodeForSqlExs() {
		return autoETL_ActivityNodeForSqlExs;
	}


	public void setAutoETL_ActivityNodeForSqls(
			Set<AutoETL_ActivityNodeForSql> autoETL_ActivityNodeForSqls) {
		this.autoETL_ActivityNodeForSqls = autoETL_ActivityNodeForSqls;
	}


	public Set<AutoETL_ActivityNodeForSql> getAutoETL_ActivityNodeForSqls() {
		return autoETL_ActivityNodeForSqls;
	}


	public void setAutoETL_ActivityNodeForExcels(
			Set<AutoETL_ActivityNodeForExcel> autoETL_ActivityNodeForExcels) {
		this.autoETL_ActivityNodeForExcels = autoETL_ActivityNodeForExcels;
	}


	public Set<AutoETL_ActivityNodeForExcel> getAutoETL_ActivityNodeForExcels() {
		return autoETL_ActivityNodeForExcels;
	}


	public void setAutoETL_ActivityNodeForPs(
			Set<AutoETL_ActivityNodeForP> autoETL_ActivityNodeForPs) {
		this.autoETL_ActivityNodeForPs = autoETL_ActivityNodeForPs;
	}


	public Set<AutoETL_ActivityNodeForP> getAutoETL_ActivityNodeForPs() {
		return autoETL_ActivityNodeForPs;
	}


	public void setStrOrderGroup(String strOrderGroup) {
		this.strOrderGroup = strOrderGroup;
	}


	public String getStrOrderGroup() {
		return strOrderGroup;
	}


	public void setAutoETL_ActivityNodeForCVs(
			Set<AutoETL_ActivityNodeForCV> autoETL_ActivityNodeForCVs) {
		this.autoETL_ActivityNodeForCVs = autoETL_ActivityNodeForCVs;
	}


	public Set<AutoETL_ActivityNodeForCV> getAutoETL_ActivityNodeForCVs() {
		return autoETL_ActivityNodeForCVs;
	}


	public void setAutoETL_ActivityNodeForViews(
			Set<AutoETL_ActivityNodeForView> autoETL_ActivityNodeForViews) {
		this.autoETL_ActivityNodeForViews = autoETL_ActivityNodeForViews;
	}


	public Set<AutoETL_ActivityNodeForView> getAutoETL_ActivityNodeForViews() {
		return autoETL_ActivityNodeForViews;
	}



	public void setStrSqlCondition(String strSqlCondition) {
		this.strSqlCondition = strSqlCondition;
	}


	public String getStrSqlCondition() {
		return strSqlCondition;
	}


	public void setAutoETL_DataSource(AutoETL_DataSource autoETL_DataSource) {
		this.autoETL_DataSource = autoETL_DataSource;
	}


	public AutoETL_DataSource getAutoETL_DataSource() {
		return autoETL_DataSource;
	}
	


	public void setAutoETL_WorkflowAEs(Set<AutoETL_WorkflowAE> autoETL_WorkflowAEs) {
		this.autoETL_WorkflowAEs = autoETL_WorkflowAEs;
	}


	public Set<AutoETL_WorkflowAE> getAutoETL_WorkflowAEs() {
		return autoETL_WorkflowAEs;
	}

	public Set<AutoETL_ActivityNodeForCheck> getAutoETL_ActivityNodeForChecks() {
		return autoETL_ActivityNodeForChecks;
	}

	public void setAutoETL_ActivityNodeForChecks(
			Set<AutoETL_ActivityNodeForCheck> autoETLActivityNodeForChecks) {
		autoETL_ActivityNodeForChecks = autoETLActivityNodeForChecks;
	}

	public Set<AutoETL_ActivityNodeForKettle> getAutoETL_ActivityNodeForKettles() {
		return autoETL_ActivityNodeForKettles;
	}

	public void setAutoETL_ActivityNodeForKettles(
			Set<AutoETL_ActivityNodeForKettle> autoETLActivityNodeForKettles) {
		autoETL_ActivityNodeForKettles = autoETLActivityNodeForKettles;
	}

	public Set<AutoETL_ActivityNodeForLogicPCheck> getAutoETL_ActivityNodeForLogicPChecks() {
		return autoETL_ActivityNodeForLogicPChecks;
	}

	public void setAutoETL_ActivityNodeForLogicPChecks(
			Set<AutoETL_ActivityNodeForLogicPCheck> autoETLActivityNodeForLogicPChecks) {
		autoETL_ActivityNodeForLogicPChecks = autoETLActivityNodeForLogicPChecks;
	}

	public Set<AutoETL_ActivityNodeForTotalTableState> getAutoETL_ActivityNodeForTotalTableStates() {
		return autoETL_ActivityNodeForTotalTableStates;
	}

	public void setAutoETL_ActivityNodeForTotalTableStates(
			Set<AutoETL_ActivityNodeForTotalTableState> autoETLActivityNodeForTotalTableStates) {
		autoETL_ActivityNodeForTotalTableStates = autoETLActivityNodeForTotalTableStates;
	}

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Set<AutoETL_ActivityNodeForMail> getAutoETL_ActivityNodeForMails() {
		return autoETL_ActivityNodeForMails;
	}

	public void setAutoETL_ActivityNodeForMails(
			Set<AutoETL_ActivityNodeForMail> autoETL_ActivityNodeForMails) {
		this.autoETL_ActivityNodeForMails = autoETL_ActivityNodeForMails;
	}

	public Set<AutoETL_ActivityNodeForMsg> getAutoETL_ActivityNodeForMsgs() {
		return autoETL_ActivityNodeForMsgs;
	}

	public void setAutoETL_ActivityNodeForMsgs(Set<AutoETL_ActivityNodeForMsg> autoETL_ActivityNodeForMsgs) {
		this.autoETL_ActivityNodeForMsgs = autoETL_ActivityNodeForMsgs;
	}

	public Set<AutoETL_ActivityNodeForWarning> getAutoETL_ActivityNodeForWarnings() {
		return autoETL_ActivityNodeForWarnings;
	}

	public void setAutoETL_ActivityNodeForWarnings(Set<AutoETL_ActivityNodeForWarning> autoETL_ActivityNodeForWarnings) {
		this.autoETL_ActivityNodeForWarnings = autoETL_ActivityNodeForWarnings;
	}
	
}

