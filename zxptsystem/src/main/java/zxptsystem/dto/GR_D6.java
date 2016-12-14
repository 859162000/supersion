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
@Table(name="GR_D6")
@IEntity(description="信贷交易信息明细-担保信息-对外担保信息")
public class GR_D6 implements Serializable{

	private static final long serialVersionUID = -3652855477791373794L;
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="GuarLoanDistriInst", length=200,nullable=true)
	@IColumn(description="担保贷款发放机构",isNullable=true)
	private String GuarLoanDistriInst;

	@Column(name="GuarLoanContraMoney", length=20,nullable=true)
	@IColumn(description="担保贷款合同金额",isNullable=true)
	private String GuarLoanContraMoney;

	@Column(name="GuarLoanDistriDate", length=20,nullable=true)
	@IColumn(description="担保贷款发放日期",isNullable=true)
	private String GuarLoanDistriDate;

	@Column(name="GuarLoanExpireDate", length=20,nullable=true)
	@IColumn(description="担保贷款到期日期",isNullable=true)
	private String GuarLoanExpireDate;

	@Column(name="GuarMoney", length=20,nullable=true)
	@IColumn(description="担保金额",isNullable=true)
	private String GuarMoney;

	@Column(name="GuarLoanPrilBalance", length=20,nullable=true)
	@IColumn(description="担保贷款本金余额",isNullable=true)
	private String GuarLoanPrilBalance;

	@Column(name="GuarLoanFLevCla", length=20,nullable=true)
	@IColumn(description="担保贷款五级分类",isNullable=true)
	private String GuarLoanFLevCla;

	@Column(name="BalanceDate", length=20,nullable=true)
	@IColumn(description="应还款日",isNullable=true)
	private String BalanceDate;

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

	public String getGuarLoanDistriInst() {
		return GuarLoanDistriInst;
	}

	public void setGuarLoanDistriInst(String guarLoanDistriInst) {
		GuarLoanDistriInst = guarLoanDistriInst;
	}

	public String getGuarLoanContraMoney() {
		return GuarLoanContraMoney;
	}

	public void setGuarLoanContraMoney(String guarLoanContraMoney) {
		GuarLoanContraMoney = guarLoanContraMoney;
	}

	public String getGuarLoanDistriDate() {
		return GuarLoanDistriDate;
	}

	public void setGuarLoanDistriDate(String guarLoanDistriDate) {
		GuarLoanDistriDate = guarLoanDistriDate;
	}

	public String getGuarLoanExpireDate() {
		return GuarLoanExpireDate;
	}

	public void setGuarLoanExpireDate(String guarLoanExpireDate) {
		GuarLoanExpireDate = guarLoanExpireDate;
	}

	public String getGuarMoney() {
		return GuarMoney;
	}

	public void setGuarMoney(String guarMoney) {
		GuarMoney = guarMoney;
	}

	public String getGuarLoanPrilBalance() {
		return GuarLoanPrilBalance;
	}

	public void setGuarLoanPrilBalance(String guarLoanPrilBalance) {
		GuarLoanPrilBalance = guarLoanPrilBalance;
	}

	public String getGuarLoanFLevCla() {
		return GuarLoanFLevCla;
	}

	public void setGuarLoanFLevCla(String guarLoanFLevCla) {
		GuarLoanFLevCla = guarLoanFLevCla;
	}

	public String getBalanceDate() {
		return BalanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		BalanceDate = balanceDate;
	}



}
