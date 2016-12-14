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

import extend.dto.AutoETL_Param;
import extend.dto.AutoETL_Procedure;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_WorkflowPP")
@IEntity(navigationName="存储过程工作流参数",description="存储过程工作流参数")
public class AutoETL_WorkflowPP implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoProcedureID")
	private AutoETL_Procedure autoETL_Procedure;
	
	@IColumn(isIdListField = true, target="autoETL_Procedure")
	private String[] autoETL_ProcedureIdList;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	private AutoETL_Param autoETL_Param;
	
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;

	@Id
	@Column(name = "autoWorkflowPPID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowPPID;

	public AutoETL_Procedure getAutoETL_Procedure() {
		return autoETL_Procedure;
	}

	public void setAutoETL_Procedure(AutoETL_Procedure autoETLProcedure) {
		autoETL_Procedure = autoETLProcedure;
	}

	public String[] getAutoETL_ProcedureIdList() {
		return autoETL_ProcedureIdList;
	}

	public void setAutoETL_ProcedureIdList(String[] autoETLProcedureIdList) {
		autoETL_ProcedureIdList = autoETLProcedureIdList;
	}

	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

	public void setAutoETL_Param(AutoETL_Param autoETLParam) {
		autoETL_Param = autoETLParam;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

	public void setAutoETL_ParamIdList(String[] autoETLParamIdList) {
		autoETL_ParamIdList = autoETLParamIdList;
	}

	public AutoETL_Workflow getAutoETL_Workflow() {
		return autoETL_Workflow;
	}

	public void setAutoETL_Workflow(AutoETL_Workflow autoETLWorkflow) {
		autoETL_Workflow = autoETLWorkflow;
	}

	public String getAutoWorkflowPPID() {
		return autoWorkflowPPID;
	}

	public void setAutoWorkflowPPID(String autoWorkflowPPID) {
		this.autoWorkflowPPID = autoWorkflowPPID;
	}




}

