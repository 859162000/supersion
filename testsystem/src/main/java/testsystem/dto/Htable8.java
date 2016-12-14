package testsystem.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.UserInfo;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;

@Entity
@Table(name = "HTABLE8")
public class Htable8 implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Column(name = "field2", length = 50)
	private String field2;

	@Id
	@Column(name = "field1", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String field1;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "field5")
	@IColumn(isSystemDate=true)
	private Date field5;
	
	@Column(name = "field6")
	@IColumn(isSystemDate=true)
	private Timestamp field6;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	@IColumn(isLoginTag=true)
	private UserInfo userInfo;
	
	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField1() {
		return field1;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField2() {
		return field2;
	}
	
	public Date getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = TypeParse.parseDate(field5);
	}
	
	public void setField6(String field6) {
		this.field6 = TypeParse.parseTimestamp(field6);
	}

	public Timestamp getField6() {
		return field6;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
}
