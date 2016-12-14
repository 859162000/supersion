package coresystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "InnerInstInfo")
@IEntity(description="采集机构管理")
public class InnerInstInfo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoID;

	@Column(name = "innerInstCode", length = 50, nullable = false)
    @IColumn(description="采集机构代码", isNullable = false)
	private String innerInstCode;
	
	@Column(name = "innerInstName", length = 50, nullable = false)
	@IColumn(description="采集机构名称", isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	private String innerInstName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="管理机构")
	private InstInfo strInstCode;

	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String in) {
		autoID = in;
	}

	public String getInnerInstCode() {
		return innerInstCode;
	}

	public void setInnerInstCode(String innerInstCode) {
		this.innerInstCode = innerInstCode;
	}

	public String getInnerInstName() {
		return innerInstName;
	}

	public void setInnerInstName(String innerInstName) {
		this.innerInstName = innerInstName;
	}

	public void setStrInstCode(InstInfo strInstCode) {
		this.strInstCode = strInstCode;
	}

	public InstInfo getStrInstCode() {
		return strInstCode;
	}

}

