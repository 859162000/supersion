package testsystem.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;

/**
 * HReportCheckTest1 entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HReportCheckTest1")
public class HReportCheckTest1 implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name = "field1", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String field1;
	
	@IColumn(description="数据时间")
	@Temporal(TemporalType.DATE)
	@Column(name = "field2",nullable=false)
	private Date field2;
	
	@IColumn(description="唯一属性1")
	@Column(name = "field3", length = 50)
	private String field3;
	
	@IColumn(description="唯一属性2")
	@Column(name = "field4", length = 50)
	private String field4;
	
	@IColumn(description="空校验")
	@Column(name = "field5", length = 50)
	private String field5;
	
	@IColumn(description="数字校验")
	@Column(name = "field6", length = 50)
	private String field6;
	
	@IColumn(description="日期校验")
	@Column(name = "field7", length = 50)
	private String field7;
	
	@IColumn(description="大写字母校验")
	@Column(name = "field8", length = 50)
	private String field8;
	
	@IColumn(description="汉字校验")
	@Column(name = "field9", length = 50)
	private String field9;
	
	@IColumn(description="起始长度校验")
	@Column(name = "field10", length = 50)
	private String field10;
	
	@IColumn(description="结束长度校验")
	@Column(name = "field11", length = 50)
	private String field11;
	
	@IColumn(description="小数位数校验")
	@Column(name = "field12", length = 50)
	private String field12;

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public Date getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = TypeParse.parseDate(field2);
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public String getField7() {
		return field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public String getField8() {
		return field8;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	public String getField9() {
		return field9;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public String getField10() {
		return field10;
	}

	public void setField10(String field10) {
		this.field10 = field10;
	}

	public String getField11() {
		return field11;
	}

	public void setField11(String field11) {
		this.field11 = field11;
	}

	public String getField12() {
		return field12;
	}

	public void setField12(String field12) {
		this.field12 = field12;
	}
}