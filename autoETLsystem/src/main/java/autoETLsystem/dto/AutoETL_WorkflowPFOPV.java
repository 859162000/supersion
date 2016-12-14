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

import extend.dto.AutoETL_Param;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_WorkflowPFOPV")
@IEntity(navigationName="文件夹路径参数",description="文件夹路径参数")
public class AutoETL_WorkflowPFOPV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="strPathType",length = 50,nullable=false)
	@IColumn(tagMethodName="getPathTypeTag",description="路径类型", isNullable = false)
	private String strPathType;
	
	public static Map<String,String> getPathTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("pathType");
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFTPID")
	@IColumn(description="FTP路径")
	private AutoETL_FTP autoETL_FTP;
	
	@Column(name="strValue",length = 50)
	@IColumn(description="本地文件夹路径参数取值",isSpecialCharCheck=true)
	private String strValue;                                                                                                 

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID", nullable = false)
	private AutoETL_Param autoETL_Param;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Id
	@Column(name = "autoWorkflowPFOPVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowPFOPVID;

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


	public void setAutoETL_FTP(AutoETL_FTP autoETL_FTP) {
		this.autoETL_FTP = autoETL_FTP;
	}


	public AutoETL_FTP getAutoETL_FTP() {
		return autoETL_FTP;
	}

	public void setStrPathType(String strPathType) {
		this.strPathType = strPathType;
	}


	public String getStrPathType() {
		return strPathType;
	}


	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}


	public void setAutoETL_Param(AutoETL_Param autoETLParam) {
		autoETL_Param = autoETLParam;
	}


	public String getAutoWorkflowPFOPVID() {
		return autoWorkflowPFOPVID;
	}


	public void setAutoWorkflowPFOPVID(String autoWorkflowPFOPVID) {
		this.autoWorkflowPFOPVID = autoWorkflowPFOPVID;
	}
	
}

