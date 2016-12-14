package testsystem.dto;

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

import framework.interfaces.IColumn;

@Entity
@Table(name = "HUserRole")
public class HUserRole {
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode", nullable = false)
	private HUserInfo userInfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private HRoleInfo roleInfo;
	
	@Transient
	@IColumn(isIdListField = true, target="roleInfo")
	private String[] roleInfoIdList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setUserInfo(HUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public HUserInfo getUserInfo() {
		return userInfo;
	}

	public void setRoleInfo(HRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public HRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfoIdList(String[] roleInfoIdList) {
		this.roleInfoIdList = roleInfoIdList;
	}

	public String[] getRoleInfoIdList() {
		return roleInfoIdList;
	}
}
