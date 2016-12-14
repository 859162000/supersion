package report.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;

import extend.dto.ReportModel_Table;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoTaskModelInst")
public class AutoTaskModelInst implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -863575743402973558L;

	@IColumn(isNullable = false,isSingleTagHidden=true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strTaskID", nullable = false)
	private AutoTaskFlow autoTaskFlow;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strModelCode", nullable = false)
	private ReportModel_Table reportModel_Table;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@Transient
	@IColumn(isIdListField = true, target="reportModel_Table")
	private String[] reportModel_TableIdList;
	
	@Transient
	@IColumn(isIdListField = true, target="instInfo")
	private String[] instInfoIdList;

	@IColumn(tagMethodName="getSubmitStatusTag",description="提交状态",isSingleTagHidden = true)
	@Column(name = "SubmitStatus", length = 1)
	private String SubmitStatus;
	
	
	public static Map<String,String> getSubmitStatusTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTSubmitStatus");
	}
	
	@IColumn(tagMethodName="getCheckTypeTag",description="校验状态",isSingleTagHidden = true)
	@Column(name = "strCheckState",length=1)
	private String strCheckState;
	
	public static Map<String,String> getCheckTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTCheckType");
	}
	
	@IColumn(tagMethodName="getVerifyTypeTag",description="审核状态",isSingleTagHidden = true)
	@Column(name = "strAllowState",length=1)
	private String strAllowState;
	
	public static Map<String,String> getVerifyTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTVerifyType");
	}
	
	@IColumn(tagMethodName="getRPTSendTypeTag",description="报送状态",isSingleTagHidden = true)
	@Column(name = "RPTSendType",length=1)
	private String RPTSendType;
	
	public static Map<String,String> getRPTSendTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTSendType");
	}
	
	@IColumn(tagMethodName="getRPTFeedbackTypeTag",description="回执状态",isSingleTagHidden = true)
	@Column(name = "RPTFeedbackType",length=1)
	private String RPTFeedbackType;
	
	public static Map<String,String> getRPTFeedbackTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("RPTFeedbackType");
	}
	
	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}
	
	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}
	
	public String getSubmitStatus() {
		return SubmitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		SubmitStatus = submitStatus;
	}

	public String getStrCheckState() {
		return strCheckState;
	}

	public void setStrCheckState(String strCheckState) {
		this.strCheckState = strCheckState;
	}

	public String getStrAllowState() {
		return strAllowState;
	}

	public void setStrAllowState(String strAllowState) {
		this.strAllowState = strAllowState;
	}
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setReportModel_Table(ReportModel_Table reportModel_Table) {
		this.reportModel_Table = reportModel_Table;
	}

	public ReportModel_Table getReportModel_Table() {
		return reportModel_Table;
	}

	public void setReportModel_TableIdList(String[] reportModel_TableIdList) {
		this.reportModel_TableIdList = reportModel_TableIdList;
	}

	public String[] getReportModel_TableIdList() {
		return reportModel_TableIdList;
	}

	public void setInstInfoIdList(String[] instInfoIdList) {
		this.instInfoIdList = instInfoIdList;
	}

	public String[] getInstInfoIdList() {
		return instInfoIdList;
	}


	public void setRPTSendType(String rPTSendType) {
		RPTSendType = rPTSendType;
	}


	public String getRPTSendType() {
		return RPTSendType;
	}


	public void setAutoTaskFlow(AutoTaskFlow autoTaskFlow) {
		this.autoTaskFlow = autoTaskFlow;
	}


	public AutoTaskFlow getAutoTaskFlow() {
		return autoTaskFlow;
	}
}

