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
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_WorkflowParamMV")
@IEntity(navigationName="手动工作流参数",description="手动工作流参数")
public class AutoETL_WorkflowParamMV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strValue",length = 4000)
	@IColumn(description="值",isSpecialCharCheck=true)
	private String strValue;

	
	@Column(name="intGroup",nullable=false)
	@IColumn(description="组", isNullable = false)
	private Integer intGroup;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID", nullable = false)
	private AutoETL_Param autoETL_Param;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;
	
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;


	@Id
	@Column(name = "autoWorkflowParamMVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowParamMVID;

	public String getStrValue() {
		return strValue;
	}


	public void setStrValue(String strValue) {
		this.strValue = strValue;
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


	public void setAutoWorkflowParamMVID(String autoWorkflowParamMVID) {
		this.autoWorkflowParamMVID = autoWorkflowParamMVID;
	}


	public String getAutoWorkflowParamMVID() {
		return autoWorkflowParamMVID;
	}


	public void setIntGroup(String intGroup) {
		this.intGroup =TypeParse.parseInt(intGroup);
	}


	public Integer getIntGroup() {
		return intGroup;
	}


	public void setAutoETL_Param(AutoETL_Param autoETL_Param) {
		this.autoETL_Param = autoETL_Param;
	}


	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

}

