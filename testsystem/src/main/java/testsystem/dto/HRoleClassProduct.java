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
import framework.show.ShowContext;


@Entity
@Table(name = "HRoleClassProduct")
public class HRoleClassProduct {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private HRoleInfo roleInfo;
	
	@IColumn(tagMethodName="getClassTag",description="±í")
	@Column(name = "strClassCode", nullable = false)
	private String strClassCode;
	
	@IColumn(tagMethodName="getProductTag",description="²úÆ·")
	@Column(name = "strProductCode", nullable = false)
	private String strProductCode;
	
	@Transient
	@IColumn(isIdListField = true, target="strProductCode")
	private String[] productCodeList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	

	public static Map<String,String> getClassTag() throws Exception{
		return SecurityContext.getInstance().getDataSecurityShowMap().get("inst");
	}
	
	public static Map<String,String> getProductTag(){
		return ShowContext.getInstance().getShowEntityMap().get("product");
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

	public void setStrClassCode(String strClassCode) {
		this.strClassCode = strClassCode;
	}

	public String getStrClassCode() {
		return strClassCode;
	}

	public void setStrProductCode(String strProductCode) {
		this.strProductCode = strProductCode;
	}

	public String getStrProductCode() {
		return strProductCode;
	}

	public void setProductCodeList(String[] productCodeList) {
		this.productCodeList = productCodeList;
	}

	public String[] getProductCodeList() {
		return productCodeList;
	}
}
