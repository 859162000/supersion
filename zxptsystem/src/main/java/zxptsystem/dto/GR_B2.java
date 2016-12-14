package zxptsystem.dto;

import java.io.Serializable;

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
@Table(name="GR_B2")
@IEntity(description="配偶信息")
public class GR_B2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 436949422625309998L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="Name", length=10, nullable=true)
	@IColumn(description="姓名", isNullable=true)
	private String Name;
	
	@Column(name="IDType", length=20, nullable=true)
	@IColumn(description="证件类型", isNullable=true)
	private String IDType;
	
	@Column(name="IDNo", length=30, nullable=true)
	@IColumn(description="证件号码", isNullable=true)
	private String IDNo;
	
	@Column(name="Company", length=200, nullable=true)
	@IColumn(description="工作单位", isNullable=true)
	private String Company;

	@Column(name="MobileNo", length=30, nullable=true)
	@IColumn(description="联系电话", isNullable=true)
	private String MobileNo;
	
	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public GR_A1 getReportNo() {
		return ReportNo;
	}

	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getIDType() {
		return IDType;
	}

	public void setIDType(String iDType) {
		IDType = iDType;
	}

	public String getIDNo() {
		return IDNo;
	}

	public void setIDNo(String iDNo) {
		IDNo = iDNo;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	
	
}
