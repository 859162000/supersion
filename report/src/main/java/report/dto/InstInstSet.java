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

import coresystem.dto.InstInfo;
import framework.interfaces.IColumn;

@Entity
@Table(name = "InstInstSet")
public class InstInstSet implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 6085144698885917486L;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstSetCode", nullable = false)
	private InstInfoSet strInstSetCode;

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

	


	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	

	public InstInfoSet getStrInstSetCode() {
		return strInstSetCode;
	}

	public void setStrInstSetCode(InstInfoSet strInstSetCode) {
		this.strInstSetCode = strInstSetCode;
	}

	

}

