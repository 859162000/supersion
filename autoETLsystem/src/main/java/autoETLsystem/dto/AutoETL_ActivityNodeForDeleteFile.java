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

import extend.dto.AutoETL_Param;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForDFile")
@IEntity(navigationName="删除文件",description="删除文件")
public class AutoETL_ActivityNodeForDeleteFile implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name="strFileNameSeg",length = 50,nullable=false)
	@IColumn(description="文件名参数分隔符", isNullable = false,isSpecialCharCheck=true)
	private String strFileNameSeg;
	
	@Column(name="strOrderType",length = 50,nullable=false)
	@IColumn(tagMethodName="getOrderTypeTag",description="参数顺序类型", isNullable = false)
	private String strOrderType;
	
	public static Map<String,String> getOrderTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("orderType");
	}
	
	@Column(name="strValue",length = 50)
	@IColumn(description="文件夹路径",isSpecialCharCheck=true)
	private String strValue;                                                                                                 


	@IColumn(tagMethodName="getFileTypeTag",description="文件类型", isNullable = false)
	@Column(name = "strFileType",length = 50, nullable = false)
	private String strFileType;
	
	public static Map<String,String> getFileTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("fileType");
	}
	
	@Column(name = "strFreq", nullable=false)
	@IColumn(tagMethodName="getStrFreqTag", description="频度", isNullable = false)
	private String strFreq;
	
	public static Map<String,String> getStrFreqTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamId", nullable = false)
	@IColumn(isNullable = false,description="关联参数")
	private AutoETL_Param autoETL_Param;

	@Column(name="strFreqValue",length = 50, nullable = false)
	@IColumn(isNullable = false, description="频度值")
	private String strFreqValue;  

	@IColumn(tagMethodName="getServiceType",description="服务器类型", isNullable = false)
	@Column(name = "strServiceType", nullable = true)
	private String strServiceType;
	
	public static Map<String,String> getServiceType(){
		return ShowContext.getInstance().getShowEntityMap().get("serviceType");
	} 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFTPID")
	@IColumn(description="FTP路径")
	private AutoETL_FTP autoETL_FTP;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	
	@Id
	@Column(name = "autoWorkflowPFPVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowPFPVID;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_ActivityNodeForDeleteFile")
	private Set<AutoETL_ActivityNodeForDeleteFileDetail> autoETL_ActivityNodeForDeleteFileDetails = new HashSet<AutoETL_ActivityNodeForDeleteFileDetail>(0);
	
	public String getStrFileNameSeg() {
		return strFileNameSeg;
	}

	public void setStrFileNameSeg(String strFileNameSeg) {
		this.strFileNameSeg = strFileNameSeg;
	}

	public String getStrOrderType() {
		return strOrderType;
	}

	public void setStrOrderType(String strOrderType) {
		this.strOrderType = strOrderType;
	}

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

	public String getAutoWorkflowPFPVID() {
		return autoWorkflowPFPVID;
	}

	public void setAutoWorkflowPFPVID(String autoWorkflowPFPVID) {
		this.autoWorkflowPFPVID = autoWorkflowPFPVID;
	}

	public Set<AutoETL_ActivityNodeForDeleteFileDetail> getAutoETL_ActivityNodeForDeleteFileDetails() {
		return autoETL_ActivityNodeForDeleteFileDetails;
	}

	public void setAutoETL_ActivityNodeForDeleteFileDetails(
			Set<AutoETL_ActivityNodeForDeleteFileDetail> autoETLActivityNodeForDeleteFileDetails) {
		autoETL_ActivityNodeForDeleteFileDetails = autoETLActivityNodeForDeleteFileDetails;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getStrFreq() {
		return strFreq;
	}

	public void setAutoETL_Param(AutoETL_Param autoETL_Param) {
		this.autoETL_Param = autoETL_Param;
	}

	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

	public String getStrFreqValue() {
		return strFreqValue;
	}

	public void setStrFreqValue(String strFreqValue) {
		this.strFreqValue = strFreqValue;
	}

	public String getStrServiceType() {
		return strServiceType;
	}

	public void setStrServiceType(String strServiceType) {
		this.strServiceType = strServiceType;
	}


	public void setAutoETL_FTP(AutoETL_FTP autoETL_FTP) {
		this.autoETL_FTP = autoETL_FTP;
	}


	public AutoETL_FTP getAutoETL_FTP() {
		return autoETL_FTP;
	}


}

