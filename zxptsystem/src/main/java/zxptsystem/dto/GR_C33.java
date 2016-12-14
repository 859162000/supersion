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
@Table(name="GR_C33")
@IEntity(description="授信及负债信息概要-未销户准贷记卡信息汇总")
public class GR_C33 implements Serializable{
	
private static final long serialVersionUID = 3508385652264696067L;
	
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@Column(name="CardCorpInstCount", length=10,nullable=true)
	@IColumn(description="发卡法人机构数",isNullable=true)
	private String CardCorpInstCount;
	
	@Column(name="CardInstCount", length=10,nullable=true)
	@IColumn(description="发卡机构数",isNullable=true)
	private String CardInstCount;
	
	@Column(name="Accounts", length=10,nullable=true)
	@IColumn(description="账户数",isNullable=true)
	private String Accounts;
	
	@Column(name="CreditTotal", length=20,nullable=true)
	@IColumn(description="授信总额",isNullable=true)
	private String CreditTotal;
	
	@Column(name="SBMaxCreditAmount", length=20,nullable=true)
	@IColumn(description="单家行最高授信额",isNullable=true)
	private String SBMaxCreditAmount;
	
	@Column(name="SBMinCreditAmount", length=20,nullable=true)
	@IColumn(description="单家行最低授信额",isNullable=true)
	private String SBMinCreditAmount;
	
	@Column(name="OverdraftBalance", length=20,nullable=true)
	@IColumn(description="透支余额",isNullable=true)
	private String OverdraftBalance;
	
	@Column(name="Re_6_MAUsedAmount", length=20,nullable=true)
	@IColumn(description="最近6个月平均透支余额",isNullable=true)
	private String Re_6_MAUsedAmount;
	
	
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


	public String getCardCorpInstCount() {
		return CardCorpInstCount;
	}


	public void setCardCorpInstCount(String cardCorpInstCount) {
		CardCorpInstCount = cardCorpInstCount;
	}


	public String getCardInstCount() {
		return CardInstCount;
	}


	public void setCardInstCount(String cardInstCount) {
		CardInstCount = cardInstCount;
	}


	public String getAccounts() {
		return Accounts;
	}


	public void setAccounts(String accounts) {
		Accounts = accounts;
	}


	public String getCreditTotal() {
		return CreditTotal;
	}


	public void setCreditTotal(String creditTotal) {
		CreditTotal = creditTotal;
	}


	public String getSBMaxCreditAmount() {
		return SBMaxCreditAmount;
	}


	public void setSBMaxCreditAmount(String sBMaxCreditAmount) {
		SBMaxCreditAmount = sBMaxCreditAmount;
	}


	public String getSBMinCreditAmount() {
		return SBMinCreditAmount;
	}


	public void setSBMinCreditAmount(String sBMinCreditAmount) {
		SBMinCreditAmount = sBMinCreditAmount;
	}


	public String getOverdraftBalance() {
		return OverdraftBalance;
	}


	public void setOverdraftBalance(String overdraftBalance) {
		OverdraftBalance = overdraftBalance;
	}


	public String getRe_6_MAUsedAmount() {
		return Re_6_MAUsedAmount;
	}


	public void setRe_6_MAUsedAmount(String re_6MAUsedAmount) {
		Re_6_MAUsedAmount = re_6MAUsedAmount;
	}


	public GR_A1 getReportNo() {
		return ReportNo;
	}


	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}
	
	

}
