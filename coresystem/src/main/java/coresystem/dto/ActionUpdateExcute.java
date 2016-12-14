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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.security.SecurityContext;

@Entity
@Table(name = "ActionUpdateExcute")
public class ActionUpdateExcute  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@Transient
	@IColumn(isListShow=true,description="操作用户")
	private String actionUserName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@Column(name = "updateTShowName", length = 200)
	@IColumn(description="对象名称")
	private String updateTShowName;
	
	@Column(name = "updateId", length = 200)
	@IColumn(description="数据ID")
	private String updateId;

	@Column(name = "updateTName", length = 100)
	private String updateTName;

	@Column(name = "updateFieldName", length = 50)
	private String updateFieldName;
	
	@Column(name = "updateFieldShowName", length = 200)
	@IColumn(description="字段名称")
	private String updateFieldShowName;	
	
	@Column(name = "strPreviousValue", length = 4000)
	@IColumn(description="修改前值")
	private String strPreviousValue;
	
	@Column(name = "strUpdateValue", length = 4000)
	@IColumn(description="修改后值")
	private String strUpdateValue;
	
	@IColumn(description="修改时间")
	@Column(name = "actionTime")
	private Timestamp actionTime;
	
	@Column(name = "linkUrl", length = 100)
	private String linkUrl;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "actionExcuteId")
	private ActionExcute actionExcute;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateTName() {
		return updateTName;
	}

	public void setUpdateTName(String updateTName) {
		this.updateTName = updateTName;
	}

	public String getUpdateTShowName() {
		return updateTShowName;
	}

	public void setUpdateTShowName(String updateTShowName) {
		this.updateTShowName = updateTShowName;
	}

	public String getStrPreviousValue() {
		return strPreviousValue;
	}

	public void setStrPreviousValue(String strPreviousValue) {
		this.strPreviousValue = strPreviousValue;
	}

	public String getStrUpdateValue() {
		return strUpdateValue;
	}

	public void setStrUpdateValue(String strUpdateValue) {
		this.strUpdateValue = strUpdateValue;
	}

	public Timestamp getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = TypeParse.parseTimestamp(actionTime);
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
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

	public void setUpdateFieldName(String updateFieldName) {
		this.updateFieldName = updateFieldName;
	}

	public String getUpdateFieldName() {
		return updateFieldName;
	}

	public void setUpdateFieldShowName(String updateFieldShowName) {
		this.updateFieldShowName = updateFieldShowName;
	}

	public String getUpdateFieldShowName() {
		return updateFieldShowName;
	}

	public void setActionExcute(ActionExcute actionExcute) {
		this.actionExcute = actionExcute;
	}

	public ActionExcute getActionExcute() {
		return actionExcute;
	}


}
