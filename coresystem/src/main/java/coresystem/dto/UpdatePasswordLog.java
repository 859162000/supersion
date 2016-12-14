package coresystem.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;

@Entity
@Table(name = "UpdatePasswordLog")
public class UpdatePasswordLog  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@IColumn(description="密码")
	@Column(name = "strPassword", length = 50)
	private String strPassword;
	
	@IColumn(description="修改时间")
	@Column(name = "actionTime")
	private Timestamp actionTime;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = TypeParse.parseTimestamp(actionTime);
	}

	public Timestamp getActionTime() {
		return actionTime;
	}

}
