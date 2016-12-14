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
@Table(name="GR_B3")
@IEntity(description="居住地址")
public class GR_B3 implements Serializable {

	private static final long serialVersionUID = 2898337176773839732L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号", isNullable=false)
	private GR_A1 ReportNo;
	
	@Column(name="LiveAddress", length=200, nullable=true)
	@IColumn(description="居住地址", isNullable=true)
	private String LiveAddress;
	
	@Column(name="LiveCondition", length=20, nullable=true)
	@IColumn(description="居住状况", isNullable=true)
	private String LiveCondition;
	
	@Column(name="InformUpdated", length=20, nullable=true)
	@IColumn(description="信息更新日期", isNullable=true)
	private String InformUpdated;

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

	public String getLiveAddress() {
		return LiveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		LiveAddress = liveAddress;
	}

	public String getLiveCondition() {
		return LiveCondition;
	}

	public void setLiveCondition(String liveCondition) {
		LiveCondition = liveCondition;
	}

	public String getInformUpdated() {
		return InformUpdated;
	}

	public void setInformUpdated(String informUpdated) {
		InformUpdated = informUpdated;
	}
	
}
