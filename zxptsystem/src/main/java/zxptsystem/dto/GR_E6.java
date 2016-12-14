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
@Table(name="GR_E6")
@IEntity(description="公共信息明细-低保救助记录")
public class GR_E6 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7111537756383064159L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="PersCategory", length=20,nullable=true)
	@IColumn(description="人员类别",isNullable=true)
	private String PersCategory;

	@Column(name="Location", length=200,nullable=true)
	@IColumn(description="所在地",isNullable=true)
	private String Location;

	@Column(name="WorkUnit", length=200,nullable=true)
	@IColumn(description="工作单位",isNullable=true)
	private String WorkUnit;

	@Column(name="FamMonEarning", length=10,nullable=true)
	@IColumn(description="家庭月收入",isNullable=true)
	private String FamMonEarning;

	@Column(name="ApplyDate", length=20,nullable=true)
	@IColumn(description="申请日期",isNullable=true)
	private String ApplyDate;

	@Column(name="ApproveDate", length=20,nullable=true)
	@IColumn(description="批准日期",isNullable=true)
	private String ApproveDate;

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

	public String getPersCategory() {
		return PersCategory;
	}

	public void setPersCategory(String persCategory) {
		PersCategory = persCategory;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getWorkUnit() {
		return WorkUnit;
	}

	public void setWorkUnit(String workUnit) {
		WorkUnit = workUnit;
	}

	public String getFamMonEarning() {
		return FamMonEarning;
	}

	public void setFamMonEarning(String famMonEarning) {
		FamMonEarning = famMonEarning;
	}

	public String getApplyDate() {
		return ApplyDate;
	}

	public void setApplyDate(String applyDate) {
		ApplyDate = applyDate;
	}

	public String getApproveDate() {
		return ApproveDate;
	}

	public void setApproveDate(String approveDate) {
		ApproveDate = approveDate;
	}

	public String getInforUpdated() {
		return InforUpdated;
	}

	public void setInforUpdated(String inforUpdated) {
		InforUpdated = inforUpdated;
	}

	

}
