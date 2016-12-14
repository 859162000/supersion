package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Htable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HTABLE")
public class Htable implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "field1", length = 50)
	private String field1;
	
	@Column(name = "field2", length = 50, nullable = false)
	private String field2;
	
	@Column(name = "field3", length = 50)
	private String field3;
	
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
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField3() {
		return field3;
	}
}