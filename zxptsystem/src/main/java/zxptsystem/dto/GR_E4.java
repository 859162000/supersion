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
@Table(name="GR_E4")
@IEntity(description="公共信息明细-住房公积金缴存记录")
public class GR_E4 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1188170305092772248L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="PayPlace", length=200,nullable=true)
	@IColumn(description="参缴地",isNullable=true)
	private String PayPlace;

	@Column(name="PayDate", length=20,nullable=true)
	@IColumn(description="参缴日期",isNullable=true)
	private String PayDate;

	@Column(name="FirstPayMonth", length=20,nullable=true)
	@IColumn(description="初缴月份",isNullable=true)
	private String FirstPayMonth;

	@Column(name="ClosedPayMonth", length=20,nullable=true)
	@IColumn(description="缴至月份",isNullable=true)
	private String ClosedPayMonth;

	@Column(name="PayStatus", length=10,nullable=true)
	@IColumn(description="缴费状态",isNullable=true)
	private String PayStatus;

	@Column(name="MonDepAmount", length=20,nullable=true)
	@IColumn(description="月缴存额",isNullable=true)
	private String MonDepAmount;

	@Column(name="PersDepRatio", length=10,nullable=true)
	@IColumn(description="个人缴存比例",isNullable=true)
	private String PersDepRatio;

	@Column(name="UnitDepRatio", length=10,nullable=true)
	@IColumn(description="单位缴存比例",isNullable=true)
	private String UnitDepRatio;

	@Column(name="PayUnit", length=200,nullable=true)
	@IColumn(description="缴费单位",isNullable=true)
	private String PayUnit;

	@Column(name="InforUpdated", length=20,nullable=true)
	@IColumn(description="信息更新日期",isNullable=true)
	private String InforUpdated;

	public GR_A1 getReportNo() {
		return ReportNo;
	}

	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}

	public String getPayPlace() {
		return PayPlace;
	}

	public void setPayPlace(String payPlace) {
		PayPlace = payPlace;
	}

	public String getPayDate() {
		return PayDate;
	}

	public void setPayDate(String payDate) {
		PayDate = payDate;
	}

	public String getFirstPayMonth() {
		return FirstPayMonth;
	}

	public void setFirstPayMonth(String firstPayMonth) {
		FirstPayMonth = firstPayMonth;
	}

	public String getClosedPayMonth() {
		return ClosedPayMonth;
	}

	public void setClosedPayMonth(String closedPayMonth) {
		ClosedPayMonth = closedPayMonth;
	}

	public String getPayStatus() {
		return PayStatus;
	}

	public void setPayStatus(String payStatus) {
		PayStatus = payStatus;
	}

	public String getMonDepAmount() {
		return MonDepAmount;
	}

	public void setMonDepAmount(String monDepAmount) {
		MonDepAmount = monDepAmount;
	}

	public String getPersDepRatio() {
		return PersDepRatio;
	}

	public void setPersDepRatio(String persDepRatio) {
		PersDepRatio = persDepRatio;
	}

	public String getUnitDepRatio() {
		return UnitDepRatio;
	}

	public void setUnitDepRatio(String unitDepRatio) {
		UnitDepRatio = unitDepRatio;
	}

	public String getPayUnit() {
		return PayUnit;
	}

	public void setPayUnit(String payUnit) {
		PayUnit = payUnit;
	}

	public String getInforUpdated() {
		return InforUpdated;
	}

	public void setInforUpdated(String inforUpdated) {
		InforUpdated = inforUpdated;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}



}
