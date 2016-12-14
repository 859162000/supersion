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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_Workflow")
@IEntity(navigationName="工作流",description="工作流")
public class AutoETL_Workflow implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strWorkflowName",length = 50,nullable=false)
	@IColumn(description="工作流",isKeyName=true, isNullable = false)
	private String strWorkflowName;
	
	@IColumn(tagMethodName="getParamValueTypeTag",description="参数类型", isNullable = false)
	@Column(name = "strParamValueType", nullable = false)
	private String strParamValueType;
	
	public static Map<String,String> getParamValueTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("paramValueType");
	}
	
	@IColumn(description="开始时点", isNullable = false)
	@Column(name = "startTime", nullable = false)
	private Integer startTime;
	
	@IColumn(description="结束时点", isNullable = false)
	@Column(name = "endTime", nullable = false)
	private Integer endTime;
	
	@IColumn(description="失败等待分钟", isNullable = false)
	@Column(name = "errorWaitTime", nullable = false)
	private Integer errorWaitTime;
	
	@IColumn(description="执行中等待分钟", isNullable = false)
	@Column(name = "proceseWaitTime", nullable = false)
	private Integer proceseWaitTime;
	
	@IColumn(tagMethodName="getEffectiveTypeTag",description="状态", isNullable = false)
	@Column(name = "strEffectiveType", nullable = false)
	private String strEffectiveType;
	
	public static Map<String,String> getEffectiveTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("effectiveType");
	}
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_ActivityNode> autoETL_ActivityNodes = new HashSet<AutoETL_ActivityNode>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVs = new HashSet<AutoETL_WorkflowParamMV>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowPFPV> autoETL_WorkflowPFPVs = new HashSet<AutoETL_WorkflowPFPV>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowPP> autoETL_WorkflowPPs = new HashSet<AutoETL_WorkflowPP>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowPSDV> autoETL_WorkflowPSDVs = new HashSet<AutoETL_WorkflowPSDV>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowLog> autoETL_WorkflowLogs = new HashSet<AutoETL_WorkflowLog>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowSQLParam> autoETL_WorkflowSQLParams = new HashSet<AutoETL_WorkflowSQLParam>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_Workflow")
	private Set<AutoETL_WorkflowPFOPV> autoETL_WorkflowPFOPVs = new HashSet<AutoETL_WorkflowPFOPV>(0);
	
	
	@Id
	@Column(name = "autoWorkflowID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowID;
	
	public String getStrWorkflowName() {
		return strWorkflowName;
	}

	public void setStrWorkflowName(String strWorkflowName) {
		this.strWorkflowName = strWorkflowName;
	}

	public String getStrParamValueType() {
		return strParamValueType;
	}

	public void setStrParamValueType(String strParamValueType) {
		this.strParamValueType = strParamValueType;
	}

	public String getStrEffectiveType() {
		return strEffectiveType;
	}

	public void setStrEffectiveType(String strEffectiveType) {
		this.strEffectiveType = strEffectiveType;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_ActivityNodes(Set<AutoETL_ActivityNode> autoETL_ActivityNodes) {
		this.autoETL_ActivityNodes = autoETL_ActivityNodes;
	}

	public Set<AutoETL_ActivityNode> getAutoETL_ActivityNodes() {
		return autoETL_ActivityNodes;
	}

	public void setAutoETL_WorkflowLogs(Set<AutoETL_WorkflowLog> autoETL_WorkflowLogs) {
		this.autoETL_WorkflowLogs = autoETL_WorkflowLogs;
	}

	public Set<AutoETL_WorkflowLog> getAutoETL_WorkflowLogs() {
		return autoETL_WorkflowLogs;
	}

	public void setAutoETL_WorkflowParamMVs(Set<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVs) {
		this.autoETL_WorkflowParamMVs = autoETL_WorkflowParamMVs;
	}

	public Set<AutoETL_WorkflowParamMV> getAutoETL_WorkflowParamMVs() {
		return autoETL_WorkflowParamMVs;
	}

	public void setAutoETL_WorkflowPFPVs(Set<AutoETL_WorkflowPFPV> autoETL_WorkflowPFPVs) {
		this.autoETL_WorkflowPFPVs = autoETL_WorkflowPFPVs;
	}

	public Set<AutoETL_WorkflowPFPV> getAutoETL_WorkflowPFPVs() {
		return autoETL_WorkflowPFPVs;
	}

	public void setAutoETL_WorkflowPPs(Set<AutoETL_WorkflowPP> autoETL_WorkflowPPs) {
		this.autoETL_WorkflowPPs = autoETL_WorkflowPPs;
	}

	public Set<AutoETL_WorkflowPP> getAutoETL_WorkflowPPs() {
		return autoETL_WorkflowPPs;
	}

	public void setAutoETL_WorkflowPSDVs(Set<AutoETL_WorkflowPSDV> autoETL_WorkflowPSDVs) {
		this.autoETL_WorkflowPSDVs = autoETL_WorkflowPSDVs;
	}

	public Set<AutoETL_WorkflowPSDV> getAutoETL_WorkflowPSDVs() {
		return autoETL_WorkflowPSDVs;
	}


	public void setAutoWorkflowID(String autoWorkflowID) {
		this.autoWorkflowID = autoWorkflowID;
	}

	public String getAutoWorkflowID() {
		return autoWorkflowID;
	}

	public void setStartTime(String startTime) {
		this.startTime = TypeParse.parseInt(startTime);
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = TypeParse.parseInt(endTime);;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setErrorWaitTime(String errorWaitTime) {
		this.errorWaitTime = TypeParse.parseInt(errorWaitTime);;
	}

	public Integer getErrorWaitTime() {
		return errorWaitTime;
	}

	public void setProceseWaitTime(String proceseWaitTime) {
		this.proceseWaitTime = TypeParse.parseInt(proceseWaitTime);;
	}

	public Integer getProceseWaitTime() {
		return proceseWaitTime;
	}

	public void setAutoETL_WorkflowSQLParams(
			Set<AutoETL_WorkflowSQLParam> autoETL_WorkflowSQLParams) {
		this.autoETL_WorkflowSQLParams = autoETL_WorkflowSQLParams;
	}

	public Set<AutoETL_WorkflowSQLParam> getAutoETL_WorkflowSQLParams() {
		return autoETL_WorkflowSQLParams;
	}

	public Set<AutoETL_WorkflowPFOPV> getAutoETL_WorkflowPFOPVs() {
		return autoETL_WorkflowPFOPVs;
	}

	public void setAutoETL_WorkflowPFOPVs(
			Set<AutoETL_WorkflowPFOPV> autoETLWorkflowPFOPVs) {
		autoETL_WorkflowPFOPVs = autoETLWorkflowPFOPVs;
	}

}

