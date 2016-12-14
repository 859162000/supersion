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
@Table(name = "HCustomerInfo")
public class HCustomerInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strCustomerCode", length = 50)
	@IColumn(description="客户代码")
	private String strCustomerCode;
	
	@Column(name = "strCustomerName", length = 50, nullable = false)
	@IColumn(description="客户名称", isKeyName=true)
	private String strCustomerName;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "customerInfo")
	private Set<HCustomerRole> customerRoles = new HashSet<HCustomerRole>(0);

	public String getStrCustomerCode() {
		return strCustomerCode;
	}

	public void setStrCustomerCode(String strCustomerCode) {
		this.strCustomerCode = strCustomerCode;
	}

	public String getStrCustomerName() {
		return strCustomerName;
	}

	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}

	public Set<HCustomerRole> getCustomerRoles() {
		return customerRoles;
	}

	public void setCustomerRoles(Set<HCustomerRole> customerRoles) {
		this.customerRoles = customerRoles;
	}

}

