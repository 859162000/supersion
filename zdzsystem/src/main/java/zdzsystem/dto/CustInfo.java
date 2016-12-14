package zdzsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CustInfo")
@IEntity(description= "客户信息表")
public class CustInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoID;

	@Column(name = "custname", length = 100)
	@IColumn(description="客户姓名")
	private String custname;
	
	@Column(name = "custcode", length = 10)
	@IColumn(description="客户证件类型")
	private String custcode;
	
	
	@Column(name = "custnum", length = 100)
	@IColumn(description="客户证件号码")
	private String custnum;


	public String getAutoID() {
		return autoID;
	}


	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}


	public String getCustname() {
		return custname;
	}


	public void setCustname(String custname) {
		this.custname = custname;
	}


	public String getCustcode() {
		return custcode;
	}


	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}


	public String getCustnum() {
		return custnum;
	}


	public void setCustnum(String custnum) {
		this.custnum = custnum;
	}
	

}
