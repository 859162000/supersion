package testsystem.dto;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@IEntity(description="用户")
@Entity
@Table(name = "HUserInfo")
public class HUserInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strUserCode", length = 50)
	@IColumn(description="登录代码")
	private String strUserCode;

	@Column(name = "strUserName", length = 50, nullable = false)
	@IColumn(description="用户名称", isKeyName=true)
	private String strUserName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strWorkGroupCode", nullable = false)
	private HWorkGroup workGroup;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private HInstInfo instInfo;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "userInfo")
	private Set<HUserRole> userRoles = new HashSet<HUserRole>(0);

	@Transient
	@IColumn(isIdListField = true, target="userRoles", mappedBy = "roleInfo")
	private String[] roleInfoIdList;
	
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

	public void setInstInfo(HInstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public HInstInfo getInstInfo() {
		return instInfo;
	}

	public void setWorkGroup(HWorkGroup workGroup) {
		this.workGroup = workGroup;
	}

	public HWorkGroup getWorkGroup() {
		return workGroup;
	}

	public void setUserRoles(Set<HUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<HUserRole> getUserRoles() {
		return userRoles;
	}

	public void setRoleInfoIdList(String[] roleInfoIdList) {
		this.roleInfoIdList = roleInfoIdList;
	}

	public String[] getRoleInfoIdList() {
		return roleInfoIdList;
	}

}

