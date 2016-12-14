package coresystem.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import framework.interfaces.IColumn;

@Entity
@Table(name = "RoleInfo")
public class RoleInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strRoleCode", length = 50)
	@IColumn(description="角色代码")
	private String strRoleCode;
	
	@Column(name = "strRoleName", length = 50, nullable = false)
	@IColumn(description="角色名称", isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	private String strRoleName;
	
	@IColumn(description="用户")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleInfo")
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	@IColumn(description="功能权限")
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "roleInfo")
	private Set<RoleFunction> roleFunctions = new HashSet<RoleFunction>(0);

	@IColumn(description="机构数据权限")
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "roleInfo")
	private Set<RoleClassInst> roleClassInsts = new HashSet<RoleClassInst>(0);

	public void setStrRoleCode(String strRoleCode) {
		this.strRoleCode = strRoleCode;
	}

	public String getStrRoleCode() {
		return strRoleCode;
	}

	public void setStrRoleName(String strRoleName) {
		this.strRoleName = strRoleName;
	}

	public String getStrRoleName() {
		return strRoleName;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setRoleFunctions(Set<RoleFunction> roleFunctions) {
		this.roleFunctions = roleFunctions;
	}

	public Set<RoleFunction> getRoleFunctions() {
		return roleFunctions;
	}

	public void setRoleClassInsts(Set<RoleClassInst> roleClassInsts) {
		this.roleClassInsts = roleClassInsts;
	}

	public Set<RoleClassInst> getRoleClassInsts() {
		return roleClassInsts;
	}
}
