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
@Table(name="GR_E51")
@IEntity(description="公共信息明细-养老保险记录_养老保险金缴存记录")
public class GR_E51 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3400293228466117165L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="InsuredPlace", length=200,nullable=true)
	@IColumn(description="参保地",isNullable=true)
	private String InsuredPlace;

	@Column(name="InsuredDate", length=20,nullable=true)
	@IColumn(description="参保日期",isNullable=true)
	private String InsuredDate;

	@Column(name="AccumPayMonth", length=10,nullable=true)
	@IColumn(description="累计缴费月数",isNullable=true)
	private String AccumPayMonth;

	@Column(name="WorkMonth", length=20,nullable=true)
	@IColumn(description="参加工作月份",isNullable=true)
	private String WorkMonth;

	@Column(name="PayStatus", length=50,nullable=true)
	@IColumn(description="缴费状态",isNullable=true)
	private String PayStatus;

	@Column(name="PersPayBase", length=20,nullable=true)
	@IColumn(description="个人缴费基数",isNullable=true)
	private String PersPayBase;

	@Column(name="CurMonPayMoney", length=20,nullable=true)
	@IColumn(description="本月缴费金额",isNullable=true)
	private String CurMonPayMoney;

	@Column(name="InforUpdated", length=20,nullable=true)
	@IColumn(description="信息更新日期",isNullable=true)
	private String InforUpdated;

	@Column(name="PayUnit", length=200,nullable=true)
	@IColumn(description="缴费单位",isNullable=true)
	private String PayUnit;

	@Column(name="InterrPayResult", length=50,nullable=true)
	@IColumn(description="中断或终止缴费原因",isNullable=true)
	private String InterrPayResult;

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

	public String getInsuredPlace() {
		return InsuredPlace;
	}

	public void setInsuredPlace(String insuredPlace) {
		InsuredPlace = insuredPlace;
	}

	public String getInsuredDate() {
		return InsuredDate;
	}

	public void setInsuredDate(String insuredDate) {
		InsuredDate = insuredDate;
	}

	public String getAccumPayMonth() {
		return AccumPayMonth;
	}

	public void setAccumPayMonth(String accumPayMonth) {
		AccumPayMonth = accumPayMonth;
	}

	public String getWorkMonth() {
		return WorkMonth;
	}

	public void setWorkMonth(String workMonth) {
		WorkMonth = workMonth;
	}

	public String getPayStatus() {
		return PayStatus;
	}

	public void setPayStatus(String payStatus) {
		PayStatus = payStatus;
	}

	public String getPersPayBase() {
		return PersPayBase;
	}

	public void setPersPayBase(String persPayBase) {
		PersPayBase = persPayBase;
	}

	public String getCurMonPayMoney() {
		return CurMonPayMoney;
	}

	public void setCurMonPayMoney(String curMonPayMoney) {
		CurMonPayMoney = curMonPayMoney;
	}

	public String getInforUpdated() {
		return InforUpdated;
	}

	public void setInforUpdated(String inforUpdated) {
		InforUpdated = inforUpdated;
	}

	public String getPayUnit() {
		return PayUnit;
	}

	public void setPayUnit(String payUnit) {
		PayUnit = payUnit;
	}

	public String getInterrPayResult() {
		return InterrPayResult;
	}

	public void setInterrPayResult(String interrPayResult) {
		InterrPayResult = interrPayResult;
	}



}
