package report.dto;

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

@Entity
@Table(name = "RptRptSet")
public class RptRptSet implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -3097546365317908057L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	private RptInfo rptInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptSetCode", nullable = false)
	private RptInfoSet strRptSetCode;

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


	public void setRptInfo(RptInfo rptInfo) {
		this.rptInfo = rptInfo;
	}

	public RptInfo getRptInfo() {
		return rptInfo;
	}

	public void setStrRptSetCode(RptInfoSet strRptSetCode) {
		this.strRptSetCode = strRptSetCode;
	}

	public RptInfoSet getStrRptSetCode() {
		return strRptSetCode;
	}

}

