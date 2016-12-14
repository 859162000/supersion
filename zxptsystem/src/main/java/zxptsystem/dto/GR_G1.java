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
@Table(name="GR_G1")
@IEntity(description="查询记录-查询记录汇总")
public class GR_G1 implements Serializable {

	private static final long serialVersionUID = 1075650390211100209L;

	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="Re_1_MLAQInstAmount", length=10,nullable=true)
	@IColumn(description="最近一个月内的贷款审批的查询机构数",isNullable=true)
	private String Re_1_MLAQInstAmount;

	@Column(name="Re_1_MCAQInstAmount", length=10,nullable=true)
	@IColumn(description="最近一个月内的信用卡审批的查询机构数",isNullable=true)
	private String Re_1_MCAQInstAmount;

	@Column(name = "Re_1_MLAQFreq", length = 10, nullable = true)
	@IColumn(description = "最近一个月内的贷款审批的查询次数", isNullable = true)
	private String Re_1_MLAQFreq;

	@Column(name = "Re_1_MCAQFreq", length = 10, nullable = true)
	@IColumn(description = "最近一个月内的信用卡审批的查询次数", isNullable = true)
	private String Re_1_MCAQFreq;

	@Column(name="Re_1_MOQFreq", length=10,nullable=true)
	@IColumn(description="最近一个月内的本人查询的查询次数",isNullable=true)
	private String Re_1_MOQFreq;
	
	@Column(name="Re_2_YMALQFreq", length=10,nullable=true)
	@IColumn(description="最近两年内的贷后管理查询次数",isNullable=true)
	private String Re_2_YMALQFreq;

	@Column(name = "Re_2_YGQEQFreq", length = 10, nullable = true)
	@IColumn(description = "最近两年内的担保资格审查查询次数", isNullable = true)
	private String Re_2_YGQEQFreq;

	@Column(name = "Re_2_YSMRNEQFreq", length = 10, nullable = true)
	@IColumn(description = "最近两年内的特约商户实名审查的查询次数", isNullable = true)
	private String Re_2_YSMRNEQFreq;

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

	public String getRe_1_MLAQInstAmount() {
		return Re_1_MLAQInstAmount;
	}

	public void setRe_1_MLAQInstAmount(String re_1MLAQInstAmount) {
		Re_1_MLAQInstAmount = re_1MLAQInstAmount;
	}

	public String getRe_1_MCAQInstAmount() {
		return Re_1_MCAQInstAmount;
	}

	public void setRe_1_MCAQInstAmount(String re_1MCAQInstAmount) {
		Re_1_MCAQInstAmount = re_1MCAQInstAmount;
	}

	public String getRe_1_MLAQFreq() {
		return Re_1_MLAQFreq;
	}

	public void setRe_1_MLAQFreq(String re_1MLAQFreq) {
		Re_1_MLAQFreq = re_1MLAQFreq;
	}

	public String getRe_1_MCAQFreq() {
		return Re_1_MCAQFreq;
	}

	public void setRe_1_MCAQFreq(String re_1MCAQFreq) {
		Re_1_MCAQFreq = re_1MCAQFreq;
	}

	public String getRe_1_MOQFreq() {
		return Re_1_MOQFreq;
	}

	public void setRe_1_MOQFreq(String re_1MOQFreq) {
		Re_1_MOQFreq = re_1MOQFreq;
	}

	public String getRe_2_YMALQFreq() {
		return Re_2_YMALQFreq;
	}

	public void setRe_2_YMALQFreq(String re_2YMALQFreq) {
		Re_2_YMALQFreq = re_2YMALQFreq;
	}

	public String getRe_2_YGQEQFreq() {
		return Re_2_YGQEQFreq;
	}

	public void setRe_2_YGQEQFreq(String re_2YGQEQFreq) {
		Re_2_YGQEQFreq = re_2YGQEQFreq;
	}

	public String getRe_2_YSMRNEQFreq() {
		return Re_2_YSMRNEQFreq;
	}

	public void setRe_2_YSMRNEQFreq(String re_2YSMRNEQFreq) {
		Re_2_YSMRNEQFreq = re_2YSMRNEQFreq;
	}
}
