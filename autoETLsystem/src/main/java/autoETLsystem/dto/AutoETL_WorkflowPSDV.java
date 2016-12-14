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
@Table(name = "AutoETL_WorkflowPSDV")
@IEntity(navigationName="系统时间工作流参数",description="系统时间工作流参数")
public class AutoETL_WorkflowPSDV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strValue",length = 50,nullable = false)
	@IColumn(description="值", isNullable = false)
	private Integer strValue;

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
	@Column(name = "autoWorkflowPSDVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowPSDVID;

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


	public void setAutoWorkflowPSDVID(String autoWorkflowPSDVID) {
		this.autoWorkflowPSDVID = autoWorkflowPSDVID;
	}


	public String getAutoWorkflowPSDVID() {
		return autoWorkflowPSDVID;
	}


	public void setAutoETL_Param(AutoETL_Param autoETL_Param) {
		this.autoETL_Param = autoETL_Param;
	}


	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

	public void setStrValue(String strValue) {
		this.strValue = TypeParse.parseInt(strValue);
	}


	public Integer getStrValue() {
		return strValue;
	}



}

