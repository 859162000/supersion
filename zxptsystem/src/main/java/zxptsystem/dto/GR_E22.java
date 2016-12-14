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
@Table(name="GR_E22")
@IEntity(description="公共信息明细-法院民事判决和强制执行记录_强制执行记录")
public class GR_E22 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8289259712758720062L;
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="ExecuCourt", length=200,nullable=true)
	@IColumn(description="执行法院",isNullable=true)
	private String ExecuCourt;

	@Column(name="ExecuSummary", length=255,nullable=true)
	@IColumn(description="执行案由",isNullable=true)
	private String ExecuSummary;

	@Column(name="FilingDate", length=20,nullable=true)
	@IColumn(description="立案日期",isNullable=true)
	private String FilingDate;

	@Column(name="ClosedWay", length=20,nullable=true)
	@IColumn(description="结案方式",isNullable=true)
	private String ClosedWay;

	@Column(name="CaseStatus", length=50,nullable=true)
	@IColumn(description="案件状态",isNullable=true)
	private String CaseStatus;

	@Column(name="ClosedDate", length=20,nullable=true)
	@IColumn(description="结案日期",isNullable=true)
	private String ClosedDate;

	@Column(name="ApObjOfExecu", length=100,nullable=true)
	@IColumn(description="申请执行标的",isNullable=true)
	private String ApObjOfExecu;

	@Column(name="ApObjOfExecuValue", length=20,nullable=true)
	@IColumn(description="申请执行标的价值",isNullable=true)
	private String ApObjOfExecuValue;

	@Column(name="AlreObjOfExecu", length=100,nullable=true)
	@IColumn(description="已执行标的",isNullable=true)
	private String AlreObjOfExecu;

	@Column(name="AlreObjOfExecuMoney", length=20,nullable=true)
	@IColumn(description="已执行标的金额",isNullable=true)
	private String AlreObjOfExecuMoney;

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

	public String getExecuCourt() {
		return ExecuCourt;
	}

	public void setExecuCourt(String execuCourt) {
		ExecuCourt = execuCourt;
	}

	public String getExecuSummary() {
		return ExecuSummary;
	}

	public void setExecuSummary(String execuSummary) {
		ExecuSummary = execuSummary;
	}

	public String getFilingDate() {
		return FilingDate;
	}

	public void setFilingDate(String filingDate) {
		FilingDate = filingDate;
	}

	public String getClosedWay() {
		return ClosedWay;
	}

	public void setClosedWay(String closedWay) {
		ClosedWay = closedWay;
	}

	public String getCaseStatus() {
		return CaseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		CaseStatus = caseStatus;
	}

	public String getClosedDate() {
		return ClosedDate;
	}

	public void setClosedDate(String closedDate) {
		ClosedDate = closedDate;
	}

	public String getApObjOfExecu() {
		return ApObjOfExecu;
	}

	public void setApObjOfExecu(String apObjOfExecu) {
		ApObjOfExecu = apObjOfExecu;
	}

	public String getApObjOfExecuValue() {
		return ApObjOfExecuValue;
	}

	public void setApObjOfExecuValue(String apObjOfExecuValue) {
		ApObjOfExecuValue = apObjOfExecuValue;
	}

	public String getAlreObjOfExecu() {
		return AlreObjOfExecu;
	}

	public void setAlreObjOfExecu(String alreObjOfExecu) {
		AlreObjOfExecu = alreObjOfExecu;
	}

	public String getAlreObjOfExecuMoney() {
		return AlreObjOfExecuMoney;
	}

	public void setAlreObjOfExecuMoney(String alreObjOfExecuMoney) {
		AlreObjOfExecuMoney = alreObjOfExecuMoney;
	}

	

}
