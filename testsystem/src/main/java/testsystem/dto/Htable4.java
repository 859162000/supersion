package testsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.helper.TypeParse;

/**
 * Htable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HTABLE4")
public class Htable4 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "field1", length = 50)
	private String field1;
	
	@Column(name = "field2")
	private Integer field2;
	
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField1() {
		return field1;
	}

	public void setField2(String field2) {
		this.field2 = TypeParse.parseInt(field2);
	}

	public Integer getField2() {
		return field2;
	}
	
}