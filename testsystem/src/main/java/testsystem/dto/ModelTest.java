package testsystem.dto;

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

import coresystem.dto.InstInfo;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;

/**
 * Htable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ModelTest")
public class ModelTest implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate", nullable = false)
	@IColumn(description="数据时间")
	private Date dtDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@Column(name = "strValue1", length = 50)
	@IColumn(description="值1")
	private String strValue1;
	
	@Column(name = "strValue2", length = 50)
	@IColumn(description="值2")
	private String strValue2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = TypeParse.parseDate(dtDate);;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public String getStrValue1() {
		return strValue1;
	}

	public void setStrValue1(String strValue1) {
		this.strValue1 = strValue1;
	}

	public String getStrValue2() {
		return strValue2;
	}

	public void setStrValue2(String strValue2) {
		this.strValue2 = strValue2;
	}

}