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
@Table(name="GR_E7")
@IEntity(description="公共信息明细-执业资格记录")
public class GR_E7 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7624710664458516208L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="PraQualName", length=100,nullable=true)
	@IColumn(description="执业资格名称",isNullable=true)
	private String PraQualName;

	@Column(name="Rank", length=100,nullable=true)
	@IColumn(description="等级",isNullable=true)
	private String Rank;

	@Column(name="GetDate", length=20,nullable=true)
	@IColumn(description="获得日期",isNullable=true)
	private String GetDate;

	@Column(name="DueDate", length=20,nullable=true)
	@IColumn(description="到期日期",isNullable=true)
	private String DueDate;

	@Column(name="RevoDate", length=20,nullable=true)
	@IColumn(description="吊销日期",isNullable=true)
	private String RevoDate;

	@Column(name="Authority", length=200,nullable=true)
	@IColumn(description="颁发机构",isNullable=true)
	private String Authority;

	@Column(name="InstLocation", length=200,nullable=true)
	@IColumn(description="机构所在地",isNullable=true)
	private String InstLocation;

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

	public String getPraQualName() {
		return PraQualName;
	}

	public void setPraQualName(String praQualName) {
		PraQualName = praQualName;
	}

	public String getRank() {
		return Rank;
	}

	public void setRank(String rank) {
		Rank = rank;
	}

	public String getGetDate() {
		return GetDate;
	}

	public void setGetDate(String getDate) {
		GetDate = getDate;
	}

	public String getDueDate() {
		return DueDate;
	}

	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}

	public String getRevoDate() {
		return RevoDate;
	}

	public void setRevoDate(String revoDate) {
		RevoDate = revoDate;
	}

	public String getAuthority() {
		return Authority;
	}

	public void setAuthority(String authority) {
		Authority = authority;
	}

	public String getInstLocation() {
		return InstLocation;
	}

	public void setInstLocation(String instLocation) {
		InstLocation = instLocation;
	}



}
