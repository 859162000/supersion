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
@Table(name="GR_C24")
@IEntity(description="逾期及违约信息概要-逾期（透支）信息汇总")
public class GR_C24 implements Serializable {
	private static final long serialVersionUID = 3508385652264696067L;
	

	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@Column(name="LoanOverdueAmount", length=10,nullable=true)
	@IColumn(description="贷款逾期笔数",isNullable=true)
	private String LoanOverdueAmount;
	
	@Column(name="LoanOverdueMonths", length=10,nullable=true)
	@IColumn(description="贷款逾期月份数",isNullable=true)
	private String LoanOverdueMonths;
	
	@Column(name="LMTopOverdueTotal", length=20,nullable=true)
	@IColumn(description="贷款逾期单月最高逾期总额",isNullable=true)
	private String LMTopOverdueTotal;
	
	@Column(name="LMaxOverdueMonths", length=10,nullable=true)
	@IColumn(description="贷款逾期最长逾期月数",isNullable=true)
	private String LMaxOverdueMonths;
	
	@Column(name="CCOverdueAccounts", length=10,nullable=true)
	@IColumn(description="贷记卡逾期账户数",isNullable=true)
	private String CCOverdueAccounts;
	
	@Column(name="CCOverdueMonths", length=10,nullable=true)
	@IColumn(description="贷记卡逾期月份数",isNullable=true)
	private String CCOverdueMonths;
	
	@Column(name="CCMTopOverdueTotal", length=20,nullable=true)
	@IColumn(description="贷记卡逾期单月最高逾期总额",isNullable=true)
	private String CCMTopOverdueTotal;
	
	@Column(name="CCMOverdueMonths", length=10,nullable=true)
	@IColumn(description="贷记卡逾期最长逾期月数",isNullable=true)
	private String CCMOverdueMonths;
	
	@Column(name="QCCA60_OAccounts", length=10,nullable=true)
	@IColumn(description="准贷记卡60天以上透支账户数",isNullable=true)
	private String QCCA60_OAccounts;
	
	@Column(name="QCCA60_OMonths", length=10,nullable=true)
	@IColumn(description="准贷记卡60天以上透支月份数",isNullable=true)
	private String QCCA60_OMonths;
	
	@Column(name="QCCA60_MMOBalance", length=20,nullable=true)
	@IColumn(description="准贷记卡60天以上透支单月最高透支余额",isNullable=true)
	private String QCCA60_MMOBalance;
	
	@Column(name="QCCA60_LOMonth", length=10,nullable=true)
	@IColumn(description="准贷记卡60天以上透支最长透支月数",isNullable=true)
	private String QCCA60_LOMonth;
	
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



	public String getLoanOverdueAmount() {
		return LoanOverdueAmount;
	}



	public void setLoanOverdueAmount(String loanOverdueAmount) {
		LoanOverdueAmount = loanOverdueAmount;
	}



	public String getLoanOverdueMonths() {
		return LoanOverdueMonths;
	}



	public void setLoanOverdueMonths(String loanOverdueMonths) {
		LoanOverdueMonths = loanOverdueMonths;
	}



	public String getLMTopOverdueTotal() {
		return LMTopOverdueTotal;
	}



	public void setLMTopOverdueTotal(String lMTopOverdueTotal) {
		LMTopOverdueTotal = lMTopOverdueTotal;
	}



	public String getLMaxOverdueMonths() {
		return LMaxOverdueMonths;
	}



	public void setLMaxOverdueMonths(String lMaxOverdueMonths) {
		LMaxOverdueMonths = lMaxOverdueMonths;
	}



	public String getCCOverdueAccounts() {
		return CCOverdueAccounts;
	}



	public void setCCOverdueAccounts(String cCOverdueAccounts) {
		CCOverdueAccounts = cCOverdueAccounts;
	}



	public String getCCOverdueMonths() {
		return CCOverdueMonths;
	}



	public void setCCOverdueMonths(String cCOverdueMonths) {
		CCOverdueMonths = cCOverdueMonths;
	}



	public String getCCMTopOverdueTotal() {
		return CCMTopOverdueTotal;
	}



	public void setCCMTopOverdueTotal(String cCMTopOverdueTotal) {
		CCMTopOverdueTotal = cCMTopOverdueTotal;
	}



	public String getCCMOverdueMonths() {
		return CCMOverdueMonths;
	}



	public void setCCMOverdueMonths(String cCMOverdueMonths) {
		CCMOverdueMonths = cCMOverdueMonths;
	}



	public String getQCCA60_OAccounts() {
		return QCCA60_OAccounts;
	}



	public void setQCCA60_OAccounts(String qCCA60OAccounts) {
		QCCA60_OAccounts = qCCA60OAccounts;
	}



	public String getQCCA60_OMonths() {
		return QCCA60_OMonths;
	}



	public void setQCCA60_OMonths(String qCCA60OMonths) {
		QCCA60_OMonths = qCCA60OMonths;
	}



	public String getQCCA60_MMOBalance() {
		return QCCA60_MMOBalance;
	}



	public void setQCCA60_MMOBalance(String qCCA60MMOBalance) {
		QCCA60_MMOBalance = qCCA60MMOBalance;
	}



	public String getQCCA60_LOMonth() {
		return QCCA60_LOMonth;
	}



	public void setQCCA60_LOMonth(String qCCA60LOMonth) {
		QCCA60_LOMonth = qCCA60LOMonth;
	}



	public GR_A1 getReportNo() {
		return ReportNo;
	}



	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}


	
}
