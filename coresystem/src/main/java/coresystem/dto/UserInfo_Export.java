package coresystem.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Table(name = "UserInfo")
@IEntity(description="用户")
public class UserInfo_Export implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strUserCode", length = 50)
	@IColumn(description="登录代码")
	private String strUserCode;

	@Column(name = "strUserName", length = 50, nullable = false)
	@IColumn(description="用户名称", isKeyName=true, isNullable = false)
	private String strUserName;
	
	@Column(name = "strPassword", length = 50, nullable = false)
	@IColumn(description="密码", isNullable = false,isSpecialCharCheck=true)
	private String strPassword;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	@IColumn(description="所属机构", isNullable = false)
	private InstInfo instInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private RoleInfo roleInfo;
	
	@Column(name = "strAllowState", length = 1)
	@IColumn(tagMethodName="getAllowStateTag",description="审批状态", isSingleTagHidden = true)
	private String strAllowState;
	
	public static Map<String,String> getAllowStateTag(){
		return ShowContext.getInstance().getShowEntityMap().get("allowState");
	}
	
	@Column(name = "strLockState", length = 50)
	@IColumn(tagMethodName="getLockStateTag",description="锁定状态", isSingleTagHidden = true)
	private String strLockState;
	
	public static Map<String,String> getLockStateTag(){
		return ShowContext.getInstance().getShowEntityMap().get("lockState");
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updatePasswordTime",length = 50,nullable=false)
	@IColumn(description="密码修改日期", isNullable = false, isSingleTagHidden=true)
	private Date updatePasswordTime;
	
	@IColumn(description="初次创建时间", isSingleTagHidden = true)
	@Column(name = "createTime")
	private Timestamp createTime;

	@IColumn(description="最近一次登陆时间", isSingleTagHidden = true)
	@Column(name = "lastLoginTime")
	private Timestamp lastLoginTime;
	
	
	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	

	public void setStrAllowState(String strAllowState) {
		this.strAllowState = strAllowState;
	}

	public String getStrAllowState() {
		return strAllowState;
	}

	public void setUpdatePasswordTime(Date updatePasswordTime) {
		this.updatePasswordTime = updatePasswordTime;
	}

	public void setUpdatePasswordTime(String updatePasswordTime) {
		this.updatePasswordTime = TypeParse.parseDate(updatePasswordTime);
	}

	public Date getUpdatePasswordTime() {
		return updatePasswordTime;
	}

	public void setStrLockState(String strLockState) {
		this.strLockState = strLockState;
	}

	public String getStrLockState() {
		return strLockState;
	}

	public void setCreateTime(String createTime) {
		this.createTime = TypeParse.parseTimestamp(createTime);
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = TypeParse.parseTimestamp(lastLoginTime);
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}
}

