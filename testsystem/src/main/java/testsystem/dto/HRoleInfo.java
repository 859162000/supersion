package testsystem.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import framework.interfaces.IColumn;

@Entity
@Table(name = "HRoleInfo")
public class HRoleInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strRoleCode", length = 50)
	@IColumn(description="角色代码")
	private String strRoleCode;
	
	@Column(name = "strRoleName", length = 50, nullable = false)
	@IColumn(description="角色名称", isKeyName=true)
	private String strRoleName;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleInfo")
	private Set<HUserRole> userRoles = new HashSet<HUserRole>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleInfo")
	private Set<HCustomerRole> customerRoles = new HashSet<HCustomerRole>(0);
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "roleInfo")
	private Set<HRoleFunctionInst> roleFunctionInsts = new HashSet<HRoleFunctionInst>(0);

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

	public void setUserRoles(Set<HUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<HUserRole> getUserRoles() {
		return userRoles;
	}

	public void setCustomerRoles(Set<HCustomerRole> customerRoles) {
		this.customerRoles = customerRoles;
	}

	public Set<HCustomerRole> getCustomerRoles() {
		return customerRoles;
	}

	public void setRoleFunctionInsts(Set<HRoleFunctionInst> roleFunctionInsts) {
		this.roleFunctionInsts = roleFunctionInsts;
	}

	public Set<HRoleFunctionInst> getRoleFunctionInsts() {
		return roleFunctionInsts;
	}
	
}