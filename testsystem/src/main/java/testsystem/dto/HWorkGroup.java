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
@Table(name = "HWorkGroup")
public class HWorkGroup implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strWorkGroupCode", length = 50)
	@IColumn(description="工作组代码")
	private String strWorkGroupCode;
	
	@Column(name = "strWorkGroupName", length = 50, nullable = false)
	@IColumn(description="工作组名称", isKeyName=true)
	private String strWorkGroupName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "workGroup")
	private Set<HUserInfo> userInfos = new HashSet<HUserInfo>(0);


	public void setUserInfos(Set<HUserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public Set<HUserInfo> getUserInfos() {
		return userInfos;
	}

	public void setStrWorkGroupCode(String strWorkGroupCode) {
		this.strWorkGroupCode = strWorkGroupCode;
	}

	public String getStrWorkGroupCode() {
		return strWorkGroupCode;
	}

	public void setStrWorkGroupName(String strWorkGroupName) {
		this.strWorkGroupName = strWorkGroupName;
	}

	public String getStrWorkGroupName() {
		return strWorkGroupName;
	}
	
}

