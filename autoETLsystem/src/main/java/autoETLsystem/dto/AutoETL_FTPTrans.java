package autoETLsystem.dto;

import java.util.HashSet;
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

@Entity
@Table(name = "AutoETL_FTPTrans")
@IEntity(navigationName="FTPTrans",description="FTPTrans")
public class AutoETL_FTPTrans implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="strLocalPath",length = 200,nullable=false)
	@IColumn(description="本地路径",isKeyName=true, isNullable = false,isSpecialCharCheck=true)
	private String strLocalPath;
	
	@Column(name="strDataFile",length = 500,nullable=false)
	@IColumn(description="数据文件", isNullable = false)
	private String strDataFile;
	
	@Column(name="strFlagFile",length = 500,nullable=false)
	@IColumn(description="标识文件", isNullable = false)
	private String strFlagFile;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AutoFTPID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_FTP autoETL_FTP;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_FTPTrans")
	private Set<AutoETL_ActivityNodeForFTPTra> autoETL_ActivityNodeForFTPTras = new HashSet<AutoETL_ActivityNodeForFTPTra>(0);

	
	@Id
	@Column(name = "autoFTPTransID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoFTPTransID;

	public String getStrLocalPath() {
		return strLocalPath;
	}

	public void setStrLocalPath(String strLocalPath) {
		this.strLocalPath = strLocalPath;
	}

	public String getStrDataFile() {
		return strDataFile;
	}

	public void setStrDataFile(String strDataFile) {
		this.strDataFile = strDataFile;
	}

	public String getStrFlagFile() {
		return strFlagFile;
	}

	public void setStrFlagFile(String strFlagFile) {
		this.strFlagFile = strFlagFile;
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

	public void setAutoFTPTransID(String autoFTPTransID) {
		this.autoFTPTransID = autoFTPTransID;
	}

	public String getAutoFTPTransID() {
		return autoFTPTransID;
	}

	public void setAutoETL_ActivityNodeForFTPTras(
			Set<AutoETL_ActivityNodeForFTPTra> autoETL_ActivityNodeForFTPTras) {
		this.autoETL_ActivityNodeForFTPTras = autoETL_ActivityNodeForFTPTras;
	}

	public Set<AutoETL_ActivityNodeForFTPTra> getAutoETL_ActivityNodeForFTPTras() {
		return autoETL_ActivityNodeForFTPTras;
	}

	
	
}




