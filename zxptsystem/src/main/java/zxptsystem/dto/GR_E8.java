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
@Table(name="GR_E8")
@IEntity(description="公共信息明细-行政奖励记录")
public class GR_E8 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7004215760821340762L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="RewardOrg", length=200,nullable=true)
	@IColumn(description="奖励机构",isNullable=true)
	private String RewardOrg;

	@Column(name="RewardContent", length=255,nullable=true)
	@IColumn(description="奖励内容",isNullable=true)
	private String RewardContent;

	@Column(name="EffectDate", length=20,nullable=true)
	@IColumn(description="生效日期",isNullable=true)
	private String EffectDate;

	@Column(name="DeadLine", length=20,nullable=true)
	@IColumn(description="截止日期",isNullable=true)
	private String DeadLine;

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

	public String getRewardOrg() {
		return RewardOrg;
	}

	public void setRewardOrg(String rewardOrg) {
		RewardOrg = rewardOrg;
	}

	public String getRewardContent() {
		return RewardContent;
	}

	public void setRewardContent(String rewardContent) {
		RewardContent = rewardContent;
	}

	public String getEffectDate() {
		return EffectDate;
	}

	public void setEffectDate(String effectDate) {
		EffectDate = effectDate;
	}

	public String getDeadLine() {
		return DeadLine;
	}

	public void setDeadLine(String deadLine) {
		DeadLine = deadLine;
	}



}
