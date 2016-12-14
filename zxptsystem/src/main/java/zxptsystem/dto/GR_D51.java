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
@Table(name="GR_D51")
@IEntity(description="信贷交易信息明细-准贷记卡-呆账业务")
public class GR_D51 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6940971481380665483L;

	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="CardDate", length=20,nullable=true)
	@IColumn(description="发卡日期",isNullable=true)
	private String CardDate;

	@Column(name="CardInstName", length=200,nullable=true)
	@IColumn(description="发卡机构名称",isNullable=true)
	private String CardInstName;

	@Column(name="Currency", length=50,nullable=true)
	@IColumn(description="币种",isNullable=true)
	private String Currency;

	@Column(name="CreditMoney", length=255,nullable=true)
	@IColumn(description="授信额度描述",isNullable=true)
	private String CreditMoney;

	@Column(name="ShareAmount", length=50,nullable=true)
	@IColumn(description="共享额度",isNullable=true)
	private String ShareAmount;
	
	@Column(name="BusiNoDesc", length=255,nullable=true)
	@IColumn(description="业务号描述",isNullable=true)
	private String BusiNoDesc;

	@Column(name="GuarWayDesc", length=255,nullable=true)
	@IColumn(description="担保方式描述",isNullable=true)
	private String GuarWayDesc;

	@Column(name="StateDeadline", length=20,nullable=true)
	@IColumn(description="状态截止日",isNullable=true)
	private String StateDeadline;
	
	@Column(name="accountState", length=20,nullable=true)
	@IColumn(description="账户状态",isNullable=true)
	private String accountState;

	@Column(name="BalanceDesc", length=255,nullable=true)
	@IColumn(description="余额描述",isNullable=true)
	private String BalanceDesc;

	@Column(name="LoanNo", length=10,nullable=true)
	@IColumn(description="准贷记卡序号",isNullable=true)
	private String LoanNo;

	public String getLoanNo() {
		return LoanNo;
	}

	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}
	
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

	public String getCardDate() {
		return CardDate;
	}

	public void setCardDate(String cardDate) {
		CardDate = cardDate;
	}

	public String getCardInstName() {
		return CardInstName;
	}

	public void setCardInstName(String cardInstName) {
		CardInstName = cardInstName;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getCreditMoney() {
		return CreditMoney;
	}

	public void setCreditMoney(String creditMoney) {
		CreditMoney = creditMoney;
	}
	
	public String getShareAmount() {
		return ShareAmount;
	}

	public void setShareAmount(String shareAmount) {
		ShareAmount = shareAmount;
	}

	public String getBusiNoDesc() {
		return BusiNoDesc;
	}

	public void setBusiNoDesc(String busiNoDesc) {
		BusiNoDesc = busiNoDesc;
	}

	public String getGuarWayDesc() {
		return GuarWayDesc;
	}

	public void setGuarWayDesc(String guarWayDesc) {
		GuarWayDesc = guarWayDesc;
	}

	public String getStateDeadline() {
		return StateDeadline;
	}

	public void setStateDeadline(String stateDeadline) {
		StateDeadline = stateDeadline;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}
	
	public String getBalanceDesc() {
		return BalanceDesc;
	}

	public void setBalanceDesc(String balanceDesc) {
		BalanceDesc = balanceDesc;
	}



}
