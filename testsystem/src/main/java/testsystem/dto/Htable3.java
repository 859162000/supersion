package testsystem.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Htable2 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HTABLE3")
public class Htable3 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Column(name = "field2", length = 50)
	private String field2;
	
	@Column(name = "field3")
	private int field3;
	
	@Column(name = "field4", precision=18, scale=2)
	private double field4;
	
    @Temporal(TemporalType.DATE)
	@Column(name = "field5")
	private Date field5;

	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField2() {
		return field2;
	}
	public void setField1(long field1) {
		this.field1 = field1;
	}
	public long getField1() {
		return field1;
	}
	public void setField3(int field3) {
		this.field3 = field3;
	}
	public int getField3() {
		return field3;
	}
	public void setField4(double field4) {
		this.field4 = field4;
	}
	public double getField4() {
		return field4;
	}
	public void setField5(Date field5) {
		this.field5 = field5;
	}
	public Date getField5() {
		return field5;
	}
	
	@Id
	@Column(name = "field1")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long field1;
}