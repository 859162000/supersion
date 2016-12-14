package autoETLsystem.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_FTP")
@IEntity(navigationName="FTP",description="FTP")
public class AutoETL_FTP implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	
	@Column(name="strServerPath",length = 50,nullable=false)
	@IColumn(description="FTP服务器",isKeyName=true, isNullable = false,isSpecialCharCheck=true)
	private String strServerPath;
	
	@Column(name="strUserID",length = 50,nullable=false)
	@IColumn(description="用户名", isNullable = false)
	private String strUserID;
	
	@Column(name="strPassword",length = 50,nullable=false)
	@IColumn(description="密码", isNullable = false)
	private String strPassword;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;


	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_FTP")
	private Set<AutoETL_FTPTrans> autoETL_FTPTranss = new HashSet<AutoETL_FTPTrans>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_FTP")
	private Set<AutoETL_WorkflowPFPV> autoETL_WorkflowPFPVs = new HashSet<AutoETL_WorkflowPFPV>(0);

	@Id
	@Column(name = "autoFTPID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoFTPID;
	
	
	public String getStrServerPath() {
		return strServerPath;
	}

	public void setStrServerPath(String strServerPath) {
		this.strServerPath = strServerPath;
	}

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

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_FTPTranss(Set<AutoETL_FTPTrans> autoETL_FTPTranss) {
		this.autoETL_FTPTranss = autoETL_FTPTranss;
	}

	public Set<AutoETL_FTPTrans> getAutoETL_FTPTranss() {
		return autoETL_FTPTranss;
	}

	public void setAutoFTPID(String autoFTPID) {
		this.autoFTPID = autoFTPID;
	}

	public String getAutoFTPID() {
		return autoFTPID;
	}

	public void setAutoETL_WorkflowPFPVs(Set<AutoETL_WorkflowPFPV> autoETL_WorkflowPFPVs) {
		this.autoETL_WorkflowPFPVs = autoETL_WorkflowPFPVs;
	}

	public Set<AutoETL_WorkflowPFPV> getAutoETL_WorkflowPFPVs() {
		return autoETL_WorkflowPFPVs;
	}
	
}




