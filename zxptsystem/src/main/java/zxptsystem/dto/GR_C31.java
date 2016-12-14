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
@Table(name="GR_C31")
@IEntity(description="授信及负债信息概要-未结清贷款信息汇总")
public class GR_C31 implements Serializable {

	private static final long serialVersionUID = 3508385652264696067L;

	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@Column(name="LoanCorpInstCount", length=10,nullable=true)
	@IColumn(description="贷款法人机构数",isNullable=true)
	private String LoanCorpInstCount;
	
	@Column(name="LoanInstCount", length=10,nullable=true)
	@IColumn(description="贷款机构数",isNullable=true)
	private String LoanInstCount;
	
	@Column(name="StrokeCount", length=10,nullable=true)
	@IColumn(description="笔数",isNullable=true)
	private String StrokeCount;
	
	@Column(name="ContractTotal", length=20,nullable=true)
	@IColumn(description="合同总额",isNullable=true)
	private String ContractTotal;
	
	@Column(name="Balance", length=20,nullable=true)
	@IColumn(description="余额",isNullable=true)
	private String Balance;
	
	@Column(name="Re_6_MARepayment", length=20,nullable=true)
	@IColumn(description="最近6个月平均应还款",isNullable=true)
	private String Re_6_MARepayment;
	
	
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


	public String getLoanCorpInstCount() {
		return LoanCorpInstCount;
	}


	public void setLoanCorpInstCount(String loanCorpInstCount) {
		LoanCorpInstCount = loanCorpInstCount;
	}


	public String getLoanInstCount() {
		return LoanInstCount;
	}


	public void setLoanInstCount(String loanInstCount) {
		LoanInstCount = loanInstCount;
	}


	public String getStrokeCount() {
		return StrokeCount;
	}


	public void setStrokeCount(String strokeCount) {
		StrokeCount = strokeCount;
	}


	public String getContractTotal() {
		return ContractTotal;
	}


	public void setContractTotal(String contractTotal) {
		ContractTotal = contractTotal;
	}


	public String getBalance() {
		return Balance;
	}


	public void setBalance(String balance) {
		Balance = balance;
	}


	public String getRe_6_MARepayment() {
		return Re_6_MARepayment;
	}


	public void setRe_6_MARepayment(String re_6MARepayment) {
		Re_6_MARepayment = re_6MARepayment;
	}


	public GR_A1 getReportNo() {
		return ReportNo;
	}


	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}

	
	
	
}
