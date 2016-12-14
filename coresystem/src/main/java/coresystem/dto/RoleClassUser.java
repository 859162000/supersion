package coresystem.dto;

import java.util.Map;

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
import framework.security.SecurityContext;


@Entity
@Table(name = "RoleClassUser")
public class RoleClassUser  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private RoleInfo roleInfo;
	
	@IColumn(tagMethodName="getClassTag",description="表", isNullable = false)
	@Column(name = "strClassCode", nullable = false)
	private String strClassCode;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode", nullable = false)
	private UserInfo userInfo;

	@Transient
	@IColumn(isIdListField = true, target="userInfo")
	private String[] userInfoIdList;
	
	@Transient
	@IColumn(isIdListField = true, target="strClassCode")
	private String[] strClassIdList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	

	public static Map<String,String> getClassTag() throws Exception{
		return SecurityContext.getInstance().getDataSecurityShowMap().get("coresystem.dto.RoleClassUser");
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String[] getUserInfoIdList() {
		return userInfoIdList;
	}

	public void setUserInfoIdList(String[] userInfoIdList) {
		this.userInfoIdList = userInfoIdList;
	}

	public void setStrClassCode(String strClassCode) {
		this.strClassCode = strClassCode;
	}

	public String getStrClassCode() {
		return strClassCode;
	}

	public void setStrClassIdList(String[] strClassIdList) {
		this.strClassIdList = strClassIdList;
	}

	public String[] getStrClassIdList() {
		return strClassIdList;
	}
	
}
