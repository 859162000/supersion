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

import framework.interfaces.IColumn;

@Entity
@Table(name = "HInstInfo")
public class HInstInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strInstCode", length = 50)
	@IColumn(description="机构代码")
	private String strInstCode;
	
	@Column(name = "strInstName", length = 50, nullable = false)
	@IColumn(description="机构名称", isKeyName=true)
	private String strInstName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "instInfo")
	private Set<HUserInfo> userInfos = new HashSet<HUserInfo>(0);
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "instInfo")
	private Set<HRoleFunctionInst> roleFunctionInsts = new HashSet<HRoleFunctionInst>(0);

	public void setStrInstCode(String strInstCode) {
		this.strInstCode = strInstCode;
	}

	public String getStrInstCode() {
		return strInstCode;
	}

	public void setStrInstName(String strInstName) {
		this.strInstName = strInstName;
	}

	public String getStrInstName() {
		return strInstName;
	}

	public void setUserInfos(Set<HUserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public Set<HUserInfo> getUserInfos() {
		return userInfos;
	}

	public void setRoleFunctionInsts(Set<HRoleFunctionInst> roleFunctionInsts) {
		this.roleFunctionInsts = roleFunctionInsts;
	}

	public Set<HRoleFunctionInst> getRoleFunctionInsts() {
		return roleFunctionInsts;
	}
	
}

