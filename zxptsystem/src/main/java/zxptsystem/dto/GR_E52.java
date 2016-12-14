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
@Table(name="GR_E52")
@IEntity(description="公共信息明细-养老保险记录_养老保险金发放记录")
public class GR_E52 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3064641375761913558L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="DistriPlace", length=200,nullable=true)
	@IColumn(description="发放地",isNullable=true)
	private String DistriPlace;

	@Column(name="RetireCategories", length=10,nullable=true)
	@IColumn(description="离退休类别",isNullable=true)
	private String RetireCategories;

	@Column(name="RetireMonth", length=20,nullable=true)
	@IColumn(description="离退休月份",isNullable=true)
	private String RetireMonth;

	@Column(name="WorkMonth", length=20,nullable=true)
	@IColumn(description="参加工作月份",isNullable=true)
	private String WorkMonth;

	@Column(name="CurrMonRealPension", length=10,nullable=true)
	@IColumn(description="本月实发养老金",isNullable=true)
	private String CurrMonRealPension;

	@Column(name="StopResult", length=50,nullable=true)
	@IColumn(description="停发原因",isNullable=true)
	private String StopResult;

	@Column(name="OrigiUnitName", length=200,nullable=true)
	@IColumn(description="原单位名称",isNullable=true)
	private String OrigiUnitName;

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

	public String getDistriPlace() {
		return DistriPlace;
	}

	public void setDistriPlace(String distriPlace) {
		DistriPlace = distriPlace;
	}

	public String getRetireCategories() {
		return RetireCategories;
	}

	public void setRetireCategories(String retireCategories) {
		RetireCategories = retireCategories;
	}

	public String getRetireMonth() {
		return RetireMonth;
	}

	public void setRetireMonth(String retireMonth) {
		RetireMonth = retireMonth;
	}

	public String getWorkMonth() {
		return WorkMonth;
	}

	public void setWorkMonth(String workMonth) {
		WorkMonth = workMonth;
	}

	public String getCurrMonRealPension() {
		return CurrMonRealPension;
	}

	public void setCurrMonRealPension(String currMonRealPension) {
		CurrMonRealPension = currMonRealPension;
	}

	public String getStopResult() {
		return StopResult;
	}

	public void setStopResult(String stopResult) {
		StopResult = stopResult;
	}

	public String getOrigiUnitName() {
		return OrigiUnitName;
	}

	public void setOrigiUnitName(String origiUnitName) {
		OrigiUnitName = origiUnitName;
	}

	public String getInforUpdated() {
		return InforUpdated;
	}

	public void setInforUpdated(String inforUpdated) {
		InforUpdated = inforUpdated;
	}



}
