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

import zxptsystem.dto.GR_A1;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name="GR_A2")
@IEntity(description="报告头查询信息")
public class GR_A2 implements Serializable{
	
	private static final long serialVersionUID = -3714262256861552271L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = true)
	@IColumn(description="报告编号" , isNullable = true)
	private GR_A1 ReportNo;
	
	@Column(name="QueryINDName", length=30, nullable=true)
	@IColumn(description="被查询者姓名", isNullable=true)
	private String QueryINDName;
	
	@Column(name="QueryINDIDType", length=50, nullable=true)
	@IColumn(description="被查询者证件类型", isNullable=true)
	private String QueryINDIDType;
	
	@Column(name="QueryINDID", length=50, nullable=true)
	@IColumn(description="被查询者证件号码", isNullable=true)
	private String QueryINDID;
	
	@Column(name="QueryOps", length=50, nullable=true)
	@IColumn(description="查询操作员", isNullable=true)
	private String QueryOps;
	
	@Column(name="QyeryReason", length=255, nullable=true)
	@IColumn(description="查询原因", isNullable=true)
	private String QyeryReason;

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
	public String getQueryINDName() {
		return QueryINDName;
	}
	public void setQueryINDName(String queryINDName) {
		QueryINDName = queryINDName;
	}
	public String getQueryINDIDType() {
		return QueryINDIDType;
	}
	public void setQueryINDIDType(String queryINDIDType) {
		QueryINDIDType = queryINDIDType;
	}
	public String getQueryINDID() {
		return QueryINDID;
	}
	public void setQueryINDID(String queryINDID) {
		QueryINDID = queryINDID;
	}
	public String getQueryOps() {
		return QueryOps;
	}
	public void setQueryOps(String queryOps) {
		QueryOps = queryOps;
	}
	public String getQyeryReason() {
		return QyeryReason;
	}
	public void setQyeryReason(String qyeryReason) {
		QyeryReason = qyeryReason;
	}
	
	
	
	
	
	
	
	
}
