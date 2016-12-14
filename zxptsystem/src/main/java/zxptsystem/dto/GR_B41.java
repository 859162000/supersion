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
@Table(name="GR_B41")
@IEntity(description="职业信息1")
public class GR_B41 implements Serializable {

	private static final long serialVersionUID = -2796796853029717969L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号", isNullable=false)
	private GR_A1 ReportNo;
	
	@Column(name="Company", length=200, nullable=true)
	@IColumn(description="工作单位", isNullable=true)
	private String Company;

	@Column(name="ComAddress", length=200, nullable=true)
	@IColumn(description="单位地址", isNullable=true)
	private String ComAddress;

	@Column(name="Profession", length=100, nullable=true)
	@IColumn(description="职业", isNullable=true)
	private String Profession;

	@Column(name="Industry", length=100, nullable=true)
	@IColumn(description="行业", isNullable=true)
	private String Industry;

	@Column(name="Duty", length=100, nullable=true)
	@IColumn(description="职务", isNullable=true)
	private String Duty;

	@Column(name="ProTitle", length=100, nullable=true)
	@IColumn(description="职称", isNullable=true)
	private String ProTitle;

	@Column(name="EnterComYear", length=20, nullable=true)
	@IColumn(description="进入本单位年份", isNullable=true)
	private String EnterComYear;

	@Column(name="InforUpdated", length=20, nullable=true)
	@IColumn(description="信息更新日期", isNullable=true)
	private String InforUpdated;

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

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getComAddress() {
		return ComAddress;
	}

	public void setComAddress(String comAddress) {
		ComAddress = comAddress;
	}

	public String getProfession() {
		return Profession;
	}

	public void setProfession(String profession) {
		Profession = profession;
	}

	public String getIndustry() {
		return Industry;
	}

	public void setIndustry(String industry) {
		Industry = industry;
	}

	public String getDuty() {
		return Duty;
	}

	public void setDuty(String duty) {
		Duty = duty;
	}

	public String getProTitle() {
		return ProTitle;
	}

	public void setProTitle(String proTitle) {
		ProTitle = proTitle;
	}

	public String getEnterComYear() {
		return EnterComYear;
	}

	public void setEnterComYear(String enterComYear) {
		EnterComYear = enterComYear;
	}

	public String getInforUpdated() {
		return InforUpdated;
	}

	public void setInforUpdated(String inforUpdated) {
		InforUpdated = inforUpdated;
	}

	
	
	
}
