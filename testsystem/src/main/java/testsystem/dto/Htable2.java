package testsystem.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import framework.helper.TypeParse;

/**
 * Htable2 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HTABLE2")
public class Htable2 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Column(name = "field2", length = 50)
	private String field2;
	
	@Column(name = "field3")
	private Integer field3;
	
	@Column(name = "field4", precision=18, scale=2)
	private Double field4;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "field5")
	private Date field5;
	
	@Column(name = "field6")
	private byte field6;
	
	@Column(name = "field7")
	private short field7;
	
	@Column(name = "field66666666666666")
	private Timestamp field66666666666666;
	
	@Id
	@Column(name = "field1")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long field1;
	
    public Long getField1() {
		return field1;
	}

	public void setField1(Long field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public Integer getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = TypeParse.parseInt(field3);
	}

	public Double getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = TypeParse.parseDouble(field4);
	}

	public Date getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = TypeParse.parseDate(field5);
	}

	public void setField66666666666666(String field66666666666666) {
		this.field66666666666666 = TypeParse.parseTimestamp(field66666666666666);
	}

	public Timestamp getField66666666666666() {
		return field66666666666666;
	}

	public void setField6(byte field6) {
		this.field6 = field6;
	}

	public byte getField6() {
		return field6;
	}

	public void setField7(String field7) {
		this.field7 = TypeParse.parseInt(field7).shortValue();
	}

	public short getField7() {
		return field7;
	}
}