package testsystem.dto;

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
@Table(name = "HRoleFunctionInstTest")
public class HRoleFunctionInstTest {
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private HRoleInfo roleInfo;
	
	@IColumn(tagMethodName="getFunctionTag",description="ģ��")
	@Column(name = "strFunctionCode", nullable = false)
	private String strFunctionCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private HInstInfo instInfo;
	
	@Transient
	@IColumn(isIdListField = true, target="instInfo")
	private String[] instInfoIdList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	

	public static Map<String,String> getFunctionTag() throws Exception{
		return SecurityContext.getInstance().getFunctionMap();
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setRoleInfo(HRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public HRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setInstInfo(HInstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public HInstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfoIdList(String[] instInfoIdList) {
		this.instInfoIdList = instInfoIdList;
	}

	public String[] getInstInfoIdList() {
		return instInfoIdList;
	}

	public void setStrFunctionCode(String strFunctionCode) {
		this.strFunctionCode = strFunctionCode;
	}

	public String getStrFunctionCode() {
		return strFunctionCode;
	}
}
