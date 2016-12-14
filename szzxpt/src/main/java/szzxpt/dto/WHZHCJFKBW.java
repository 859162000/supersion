package szzxpt.dto;

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

import coresystem.dto.UserInfo;
import framework.interfaces.IColumn;

@Entity
@Table(name = "WHZHCJFKBW")
public class WHZHCJFKBW  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="导入时间")
	@Column(name = "dtInportTime",nullable=true)
	private Timestamp dtInportTime;
	
	@IColumn(description="导入人员")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;

	@IColumn(description="导入文件名称")
	@Column(name = "strBWCCWJM", length = 200)
	private String strBWCCWJM;
	
	@IColumn(description="错误描述")
	@Column(name = "strDiscription", length = 1000)
	private String strDiscription;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Timestamp getDtInportTime() {
		return dtInportTime;
	}

	public void setDtInportTime(Timestamp dtInportTime) {
		this.dtInportTime = dtInportTime;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStrBWCCWJM() {
		return strBWCCWJM;
	}

	public void setStrBWCCWJM(String strBWCCWJM) {
		this.strBWCCWJM = strBWCCWJM;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	
}
