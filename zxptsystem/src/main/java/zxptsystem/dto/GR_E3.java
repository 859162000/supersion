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
@Table(name="GR_E3")
@IEntity(description="公共信息明细-行政处罚记录")
public class GR_E3 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4755597943205928784L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="PunishInst", length=200,nullable=true)
	@IColumn(description="处罚机构",isNullable=true)
	private String PunishInst;

	@Column(name="PunishContent", length=255,nullable=true)
	@IColumn(description="处罚内容",isNullable=true)
	private String PunishContent;

	@Column(name="PunishMoney", length=20,nullable=true)
	@IColumn(description="处罚金额",isNullable=true)
	private String PunishMoney;

	@Column(name="EffectDate", length=20,nullable=true)
	@IColumn(description="生效日期",isNullable=true)
	private String EffectDate;

	@Column(name="Deadline", length=20,nullable=true)
	@IColumn(description="截止日期",isNullable=true)
	private String Deadline;

	@Column(name="AdminReviewResult", length=255,nullable=true)
	@IColumn(description="行政复议结果",isNullable=true)
	private String AdminReviewResult;

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

	public String getPunishInst() {
		return PunishInst;
	}

	public void setPunishInst(String punishInst) {
		PunishInst = punishInst;
	}

	public String getPunishContent() {
		return PunishContent;
	}

	public void setPunishContent(String punishContent) {
		PunishContent = punishContent;
	}

	public String getPunishMoney() {
		return PunishMoney;
	}

	public void setPunishMoney(String punishMoney) {
		PunishMoney = punishMoney;
	}

	public String getEffectDate() {
		return EffectDate;
	}

	public void setEffectDate(String effectDate) {
		EffectDate = effectDate;
	}

	public String getDeadline() {
		return Deadline;
	}

	public void setDeadline(String deadline) {
		Deadline = deadline;
	}

	public String getAdminReviewResult() {
		return AdminReviewResult;
	}

	public void setAdminReviewResult(String adminReviewResult) {
		AdminReviewResult = adminReviewResult;
	}



}
