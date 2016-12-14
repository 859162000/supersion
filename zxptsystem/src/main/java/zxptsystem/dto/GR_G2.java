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
@Table(name="GR_G2")
@IEntity(description="查询记录-信贷审批查询记录信息明细")
public class GR_G2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4055967131399815561L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="QueryDate", length=20,nullable=true)
	@IColumn(description="查询日期",isNullable=true)
	private String QueryDate;

	@Column(name="QueryOperator", length=50,nullable=true)
	@IColumn(description="查询操作员",isNullable=true)
	private String QueryOperator;

	@Column(name="QueryResult", length=255,nullable=true)
	@IColumn(description="查询原因",isNullable=true)
	private String QueryResult;

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

	public String getQueryDate() {
		return QueryDate;
	}

	public void setQueryDate(String queryDate) {
		QueryDate = queryDate;
	}

	public String getQueryOperator() {
		return QueryOperator;
	}

	public void setQueryOperator(String queryOperator) {
		QueryOperator = queryOperator;
	}

	public String getQueryResult() {
		return QueryResult;
	}

	public void setQueryResult(String queryResult) {
		QueryResult = queryResult;
	}

	

}
