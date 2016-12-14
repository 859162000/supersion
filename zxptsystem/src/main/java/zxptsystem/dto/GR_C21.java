package zxptsystem.dto;


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
@Table(name="GR_C21")
@IEntity(description="逾期及违约信息概要-呆账信息汇总")
public class GR_C21  implements java.io.Serializable {

	private static final long serialVersionUID = 3508385652264696067L;
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@Column(name="StrokeCount", length=10, nullable=true)
	@IColumn(description="笔数",isNullable=true)
	private String StrokeCount;
	
	@Column(name="Balance", length=20,nullable=true)
	@IColumn(description="余额",isNullable=true)
	private String Balance;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getStrokeCount() {
		return StrokeCount;
	}

	public void setStrokeCount(String strokeCount) {
		StrokeCount = strokeCount;
	}

	public String getBalance() {
		return Balance;
	}

	public void setBalance(String balance) {
		Balance = balance;
	}

	public GR_A1 getReportNo() {
		return ReportNo;
	}

	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}

	


}
