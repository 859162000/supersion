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
@Table(name="GR_E9")
@IEntity(description="公共信息明细-车辆交易和抵押记录")
public class GR_E9 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2508081238144511565L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="EngineNo", length=20,nullable=true)
	@IColumn(description="发动机号",isNullable=true)
	private String EngineNo;

	@Column(name="LicensePlateNo", length=20,nullable=true)
	@IColumn(description="车牌号码",isNullable=true)
	private String LicensePlateNo;

	@Column(name="Brand", length=10,nullable=true)
	@IColumn(description="品牌",isNullable=true)
	private String Brand;

	@Column(name="VehicleType", length=100,nullable=true)
	@IColumn(description="车辆类型",isNullable=true)
	private String VehicleType;

	@Column(name="UseCharacter", length=100,nullable=true)
	@IColumn(description="使用性质",isNullable=true)
	private String UseCharacter;

	@Column(name="VehicleStatus", length=10,nullable=true)
	@IColumn(description="车辆状态",isNullable=true)
	private String VehicleStatus;

	@Column(name="MortgageTag", length=10,nullable=true)
	@IColumn(description="抵押标记",isNullable=true)
	private String MortgageTag;

	@Column(name="InforUpdated", length=20,nullable=true)
	@IColumn(description="信息更新日期",isNullable=true)
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

	public String getEngineNo() {
		return EngineNo;
	}

	public void setEngineNo(String engineNo) {
		EngineNo = engineNo;
	}

	public String getLicensePlateNo() {
		return LicensePlateNo;
	}

	public void setLicensePlateNo(String licensePlateNo) {
		LicensePlateNo = licensePlateNo;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public String getVehicleType() {
		return VehicleType;
	}

	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}

	public String getUseCharacter() {
		return UseCharacter;
	}

	public void setUseCharacter(String useCharacter) {
		UseCharacter = useCharacter;
	}

	public String getVehicleStatus() {
		return VehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		VehicleStatus = vehicleStatus;
	}

	public String getMortgageTag() {
		return MortgageTag;
	}

	public void setMortgageTag(String mortgageTag) {
		MortgageTag = mortgageTag;
	}

	public String getInforUpdated() {
		return InforUpdated;
	}

	public void setInforUpdated(String inforUpdated) {
		InforUpdated = inforUpdated;
	}

	

}
