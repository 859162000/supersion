package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "HCustomerRole")
public class HCustomerRole {
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strCustomerCode", nullable = false)
	private HCustomerInfo customerInfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private HRoleInfo roleInfo;

	
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


	public void setRoleInfo(HRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public HRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setCustomerInfo(HCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public HCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

}
