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
@Table(name="GR_E1")
@IEntity(description="公共信息明细-欠税记录")
public class GR_E1 implements Serializable {

	private static final long serialVersionUID = -4245717898023865028L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;

	@Column(name="CompTaxAuthority", length=200,nullable=true)
	@IColumn(description="主管税务机关",isNullable=true)
	private String CompTaxAuthority;

	@Column(name="TaxArrearsTotal", length=20,nullable=true)
	@IColumn(description="欠税总额",isNullable=true)
	private String TaxArrearsTotal;

	@Column(name="TaxArrearsDate", length=20,nullable=true)
	@IColumn(description="欠税统计时间",isNullable=true)
	private String TaxArrearsDate;

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

	public String getCompTaxAuthority() {
		return CompTaxAuthority;
	}

	public void setCompTaxAuthority(String compTaxAuthority) {
		CompTaxAuthority = compTaxAuthority;
	}

	public String getTaxArrearsTotal() {
		return TaxArrearsTotal;
	}

	public void setTaxArrearsTotal(String taxArrearsTotal) {
		TaxArrearsTotal = taxArrearsTotal;
	}

	public String getTaxArrearsDate() {
		return TaxArrearsDate;
	}

	public void setTaxArrearsDate(String taxArrearsDate) {
		TaxArrearsDate = taxArrearsDate;
	}



}
