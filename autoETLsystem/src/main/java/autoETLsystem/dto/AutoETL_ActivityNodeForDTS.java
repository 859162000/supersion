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
@Table(name = "AutoETL_ActivityNodeForDTS")
@IEntity(navigationName="DTS活动结点",description="DTS活动结点")
public class AutoETL_ActivityNodeForDTS implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strUserID",length = 50)
	@IColumn(description="用户名")
	private String strUserID;
	
	@Column(name="strPassword",length = 50)
	@IColumn(description="密码")
	private String strPassword;
	
	@Column(name="strPath",length = 200,nullable=false)
	@IColumn(description="路径", isNullable = false,isSpecialCharCheck=true)
	private String strPath;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	

	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@Id
	@Column(name = "autoActivityNodeForDTSID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForDTSID;
	

	public String getStrUserID() {
		return strUserID;
	}


	public void setStrUserID(String strUserID) {
		this.strUserID = strUserID;
	}


	public String getStrPassword() {
		return strPassword;
	}


	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}


	public String getStrPath() {
		return strPath;
	}


	public void setStrPath(String strPath) {
		this.strPath = strPath;
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


	public void setAutoActivityNodeForDTSID(String autoActivityNodeForDTSID) {
		this.autoActivityNodeForDTSID = autoActivityNodeForDTSID;
	}


	public String getAutoActivityNodeForDTSID() {
		return autoActivityNodeForDTSID;
	}



}

