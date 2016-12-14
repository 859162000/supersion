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

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_WorkflowAE")
@IEntity(navigationName="执行结点",description="执行结点")
public class AutoETL_WorkflowAE implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_ActivityNodeID")
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@IColumn(isIdListField = true, target="autoETL_ActivityNode")
	private String[] autoETL_ActivityNodeIdList;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	public AutoETL_Workflow getAutoETL_Workflow() {
		return autoETL_Workflow;
	}

	public void setAutoETL_Workflow(AutoETL_Workflow autoETLWorkflow) {
		autoETL_Workflow = autoETLWorkflow;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAutoETL_ActivityNodeIdList(
			String[] autoETL_ActivityNodeIdList) {
		this.autoETL_ActivityNodeIdList = autoETL_ActivityNodeIdList;
	}

	public String[] getAutoETL_ActivityNodeIdList() {
		return autoETL_ActivityNodeIdList;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

}

