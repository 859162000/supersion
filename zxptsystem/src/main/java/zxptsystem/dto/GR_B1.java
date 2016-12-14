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
@Table(name="GR_B1")
@IEntity(description="身份信息")
public class GR_B1 implements Serializable{
	
	private static final long serialVersionUID = 2023349322022006803L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="Sex", length=10, nullable=true)
	@IColumn(description="性别", isNullable=true)
	private String Sex;
	
	@Column(name="BirthDate", length=20, nullable=true)
	@IColumn(description="出生日期", isNullable=true)
	private String BirthDate;
	
	@Column(name="MarriageStatus", length=10, nullable=true)
	@IColumn(description="婚姻状况", isNullable=true)
	private String MarriageStatus;
	
	@Column(name="MobileNo", length=30, nullable=true)
	@IColumn(description="手机号码", isNullable=true)
	private String MobileNo;
	
	@Column(name="WorkTelephone", length=30, nullable=true)
	@IColumn(description="单位电话", isNullable=true)
	private String WorkTelephone;
	
	@Column(name="HomePhone", length=30, nullable=true)
	@IColumn(description="住宅电话", isNullable=true)
	private String HomePhone;
	
	@Column(name="EduBackground", length=50, nullable=true)
	@IColumn(description="学历", isNullable=true)
	private String EduBackground;
	
	@Column(name="Degree", length=50, nullable=true)
	@IColumn(description="学位", isNullable=true)
	private String Degree;
	
	@Column(name="ConAddress", length=200, nullable=true)
	@IColumn(description="通讯地址", isNullable=true)
	private String ConAddress;
	
	@Column(name="PermAddress", length=200, nullable=true)
	@IColumn(description="户籍地址", isNullable=true)
	private String PermAddress;
	
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
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(String birthDate) {
		BirthDate = birthDate;
	}
	public String getMarriageStatus() {
		return MarriageStatus;
	}
	public void setMarriageStatus(String marriageStatus) {
		MarriageStatus = marriageStatus;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getWorkTelephone() {
		return WorkTelephone;
	}
	public void setWorkTelephone(String workTelephone) {
		WorkTelephone = workTelephone;
	}
	public String getHomePhone() {
		return HomePhone;
	}
	public void setHomePhone(String homePhone) {
		HomePhone = homePhone;
	}
	public String getEduBackground() {
		return EduBackground;
	}
	public void setEduBackground(String eduBackground) {
		EduBackground = eduBackground;
	}
	public String getDegree() {
		return Degree;
	}
	public void setDegree(String degree) {
		Degree = degree;
	}
	public String getConAddress() {
		return ConAddress;
	}
	public void setConAddress(String conAddress) {
		ConAddress = conAddress;
	}
	public String getPermAddress() {
		return PermAddress;
	}
	public void setPermAddress(String permAddress) {
		PermAddress = permAddress;
	}
	
}
