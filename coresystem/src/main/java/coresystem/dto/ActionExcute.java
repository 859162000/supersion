package coresystem.dto;

import java.sql.Timestamp;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.security.SecurityContext;

@Entity
@Table(name = "ActionExcute")
public class ActionExcute  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@IColumn(isListShow=false,description="事件名称")
	@Column(name = "actionName", length = 200)
	private String actionName;
	
	@Transient
	@IColumn(isListShow=true,description="操作用户")
	private String actionUserName;
	
	@IColumn(description="时间")
	@Column(name = "actionTime")
	private Timestamp actionTime;

	@IColumn(isListShow=false,description="事件名称",isKeyName=true)
	@Column(name = "actionShowName", length = 200)
	private String actionShowName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actionExcute")
	private Set<ActionUpdateExcute> actionUpdateExcutes = new HashSet<ActionUpdateExcute>(0);

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

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionShowName(String actionShowName) {
		this.actionShowName = actionShowName;
	}

	public String getActionShowName() {
		return actionShowName;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = TypeParse.parseTimestamp(actionTime);
	}

	public Timestamp getActionTime() {
		return actionTime;
	}

	public void setActionUserName(String actionUserName) {
	}

	public String getActionUserName() throws Exception {
		if(this.userInfo == null){
			return SecurityContext.getInstance().getSysUserCode();
		}
		else{
			return this.userInfo.getStrUserName();
		}
	}

	public void setActionUpdateExcutes(Set<ActionUpdateExcute> actionUpdateExcutes) {
		this.actionUpdateExcutes = actionUpdateExcutes;
	}

	public Set<ActionUpdateExcute> getActionUpdateExcutes() {
		return actionUpdateExcutes;
	}
}
