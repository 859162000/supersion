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

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_WorkflowPFPV")
@IEntity(navigationName="文件路径参数",description="文件路径参数")
public class AutoETL_WorkflowPFPV implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="strFileNameSeg",length = 50,nullable=false)
	@IColumn(description="文件名参数分隔符", isNullable = false,isSpecialCharCheck=true)
	private String strFileNameSeg;
	
	@Column(name="strPathType",length = 50,nullable=false)
	@IColumn(tagMethodName="getPathTypeTag",description="路径类型", isNullable = false)
	private String strPathType;
	
	public static Map<String,String> getPathTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("pathType");
	}
	
	@Column(name="strOrderType",length = 50,nullable=false)
	@IColumn(tagMethodName="getOrderTypeTag",description="参数顺序类型", isNullable = false)
	private String strOrderType;
	
	public static Map<String,String> getOrderTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("orderType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFTPID")
	@IColumn(description="FTP路径")
	private AutoETL_FTP autoETL_FTP;
	
	@Column(name="strValue",length = 50)
	@IColumn(description="本地文件夹路径参数取值",isSpecialCharCheck=true)
	private String strValue;                                                                                                 


	@IColumn(tagMethodName="getFileTypeTag",description="文件类型", isNullable = false)
	@Column(name = "strFileType",length = 50, nullable = false)
	private String strFileType;
	
	public static Map<String,String> getFileTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("fileType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_Workflow autoETL_Workflow;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Id
	@Column(name = "autoWorkflowPFPVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowPFPVID;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_WorkflowPFPV")
	private Set<AutoETL_WorkflowPFPVDetail> autoETL_WorkflowPFPVDetails = new HashSet<AutoETL_WorkflowPFPVDetail>(0);
	

	public String getStrValue() {
		return strValue;
	}


	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}


	public String getStrFileType() {
		return strFileType;
	}


	public void setStrFileType(String strFileType) {
		this.strFileType = strFileType;
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


	public void setAutoWorkflowPFPVID(String autoWorkflowPFPVID) {
		this.autoWorkflowPFPVID = autoWorkflowPFPVID;
	}


	public String getAutoWorkflowPFPVID() {
		return autoWorkflowPFPVID;
	}


	public void setAutoETL_FTP(AutoETL_FTP autoETL_FTP) {
		this.autoETL_FTP = autoETL_FTP;
	}


	public AutoETL_FTP getAutoETL_FTP() {
		return autoETL_FTP;
	}

	public void setStrFileNameSeg(String strFileNameSeg) {
		this.strFileNameSeg = strFileNameSeg;
	}


	public String getStrFileNameSeg() {
		return strFileNameSeg;
	}


	public void setStrPathType(String strPathType) {
		this.strPathType = strPathType;
	}


	public String getStrPathType() {
		return strPathType;
	}


	public void setStrOrderType(String strOrderType) {
		this.strOrderType = strOrderType;
	}


	public String getStrOrderType() {
		return strOrderType;
	}


	public void setAutoETL_WorkflowPFPVDetails(
			Set<AutoETL_WorkflowPFPVDetail> autoETL_WorkflowPFPVDetails) {
		this.autoETL_WorkflowPFPVDetails = autoETL_WorkflowPFPVDetails;
	}


	public Set<AutoETL_WorkflowPFPVDetail> getAutoETL_WorkflowPFPVDetails() {
		return autoETL_WorkflowPFPVDetails;
	}

}

