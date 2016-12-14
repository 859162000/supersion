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
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForFTPTra")
@IEntity(navigationName="FTP传输活动结点",description="FTP传输活动结点")
public class AutoETL_ActivityNodeForFTPTra implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFTPTransID", nullable = false)
	private AutoETL_FTPTrans autoETL_FTPTrans;

	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Column(name="strParamRule",length = 200)
	@IColumn(description="参数规则",isSpecialCharCheck=true)
	private String strParamRule;

	@IColumn(tagMethodName="getParamPosition",description="参数存在顺序", isNullable = false)
	@Column(name = "strParamPosition", nullable = false)
	private String strParamPosition;
	
	public static Map<String,String> getParamPosition(){
		return ShowContext.getInstance().getShowEntityMap().get("paramPosition");
	}
	
	@Column(name="strFileName",length = 100, nullable = false)
	@IColumn(description="下载/上传文件名")
	private String strFileName;

	@IColumn(tagMethodName="getFTPTraType",description="FTP传输类型", isNullable = false)
	@Column(name = "strFTPTraType", nullable = true)
	private String strFTPTraType;
	
	public static Map<String,String> getFTPTraType(){
		return ShowContext.getInstance().getShowEntityMap().get("FTPTraType");
	}
	
	
	@Id
	@Column(name = "autoActivityNodeForFTPTraID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForFTPTraID;


	public String getStrParamPosition() {
		return strParamPosition;
	}

	public void setStrParamPosition(String strParamPosition) {
		this.strParamPosition = strParamPosition;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoActivityNodeForFTPTraID(
			String autoActivityNodeForFTPTraID) {
		this.autoActivityNodeForFTPTraID = autoActivityNodeForFTPTraID;
	}

	public String getAutoActivityNodeForFTPTraID() {
		return autoActivityNodeForFTPTraID;
	}

	public void setAutoETL_FTPTrans(AutoETL_FTPTrans autoETL_FTPTrans) {
		this.autoETL_FTPTrans = autoETL_FTPTrans;
	}

	public AutoETL_FTPTrans getAutoETL_FTPTrans() {
		return autoETL_FTPTrans;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setStrParamRule(String strParamRule) {
		this.strParamRule = strParamRule;
	}

	public String getStrParamRule() {
		return strParamRule;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public String getStrFTPTraType() {
		return strFTPTraType;
	}

	public void setStrFTPTraType(String strFTPTraType) {
		this.strFTPTraType = strFTPTraType;
	}
	
}

