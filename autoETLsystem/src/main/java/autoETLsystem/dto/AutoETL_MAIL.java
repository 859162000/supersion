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

/**
 * 项目名称：autoETLsystem<br>
 * *********************************<br>
 * <P>类名称：AutoETL_MAIL</P>
 * *********************************<br>
 * <P>类描述：邮件服务器信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-4-22 上午9:35:29<br>
 * 修改人：王川<br>
 * 修改时间：2016-4-22 上午9:35:29<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name = "AutoETL_MAIL")
@IEntity(navigationName="MAIL",description="MAIL")
public class AutoETL_MAIL implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -6607211931769160200L;

	@Id
	@Column(name = "autoMAILID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoMAILID;
	
	@Column(name="strServerUrl",length = 50,nullable=false)
	@IColumn(description="MAIL服务器",isKeyName=true, isNullable = false)
	private String strServerUrl;
	
	@Column(name="strUserID",length = 50,nullable=false)
	@IColumn(description="用户名", isSpecialCharCheck=true, isNullable = false)
	private String strUserID;
	
	@Column(name="strPassword",length = 50,nullable=false)
	@IColumn(description="密码", isSpecialCharCheck=true, isNullable = false)
	private String strPassword;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_MAIL")
	private Set<AutoETL_ActivityNodeForMail> autoETL_ActivityNodeForMails = new HashSet<AutoETL_ActivityNodeForMail>(0);
	
	
	public String getAutoMAILID() {
		return autoMAILID;
	}

	public void setAutoMAILID(String autoMAILID) {
		this.autoMAILID = autoMAILID;
	}
	
	public String getStrServerUrl() {
		return strServerUrl;
	}

	public void setStrServerUrl(String strServerUrl) {
		this.strServerUrl = strServerUrl;
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

	public Set<AutoETL_ActivityNodeForMail> getAutoETL_ActivityNodeForMails() {
		return autoETL_ActivityNodeForMails;
	}

	public void setAutoETL_ActivityNodeForMails(
			Set<AutoETL_ActivityNodeForMail> autoETL_ActivityNodeForMails) {
		this.autoETL_ActivityNodeForMails = autoETL_ActivityNodeForMails;
	}

	
}




