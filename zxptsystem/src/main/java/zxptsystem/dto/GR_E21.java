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
@Table(name="GR_E21")
@IEntity(description="公共信息明细-法院民事判决和强制执行记录_民事判决记录")
public class GR_E21 implements Serializable {
	private static final long serialVersionUID = 5014832147196465015L;

	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="FilingCourt", length=200,nullable=true)
	@IColumn(description="立案法院",isNullable=true)
	private String FilingCourt;

	@Column(name="Summary", length=255,nullable=true)
	@IColumn(description="案由",isNullable=true)
	private String Summary;

	@Column(name="FilingDate", length=20,nullable=true)
	@IColumn(description="立案日期",isNullable=true)
	private String FilingDate;

	@Column(name="ClosedWay", length=255,nullable=true)
	@IColumn(description="结案方式",isNullable=true)
	private String ClosedWay;

	@Column(name="DecisionResult", length=255,nullable=true)
	@IColumn(description="判决/调解结果",isNullable=true)
	private String DecisionResult;

	@Column(name="DecisEffecDate", length=20,nullable=true)
	@IColumn(description="判决/调解生效日期",isNullable=true)
	private String DecisEffecDate;

	@Column(name="ObjOfAction", length=255,nullable=true)
	@IColumn(description="诉讼标的",isNullable=true)
	private String ObjOfAction;

	@Column(name="ObjOfActionMoney", length=20,nullable=true)
	@IColumn(description="诉讼标的金额",isNullable=true)
	private String ObjOfActionMoney;

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

	public String getFilingCourt() {
		return FilingCourt;
	}

	public void setFilingCourt(String filingCourt) {
		FilingCourt = filingCourt;
	}

	public String getSummary() {
		return Summary;
	}

	public void setSummary(String summary) {
		Summary = summary;
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

	public String getDecisionResult() {
		return DecisionResult;
	}

	public void setDecisionResult(String decisionResult) {
		DecisionResult = decisionResult;
	}

	public String getDecisEffecDate() {
		return DecisEffecDate;
	}

	public void setDecisEffecDate(String decisEffecDate) {
		DecisEffecDate = decisEffecDate;
	}

	public String getObjOfAction() {
		return ObjOfAction;
	}

	public void setObjOfAction(String objOfAction) {
		ObjOfAction = objOfAction;
	}

	public String getObjOfActionMoney() {
		return ObjOfActionMoney;
	}

	public void setObjOfActionMoney(String objOfActionMoney) {
		ObjOfActionMoney = objOfActionMoney;
	}



}
