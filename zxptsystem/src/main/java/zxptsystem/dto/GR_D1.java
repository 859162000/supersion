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
@Table(name="GR_D1")
@IEntity(description="信贷交易信息明细-资产处置信息")
public class GR_D1 implements Serializable{

	private static final long serialVersionUID = 3508385652264696067L;
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@Column(name="AssetsManaCompany", length=200,nullable=true)
	@IColumn(description="资产管理公司",isNullable=true)
	private String AssetsManaCompany;
	
	@Column(name="DebtAcceptDate", length=20,nullable=true)
	@IColumn(description="债务接收日期",isNullable=true)
	private String DebtAcceptDate;
	
	@Column(name="ReceDebtMoney", length=20,nullable=true)
	@IColumn(description="接收的债权金额",isNullable=true)
	private String ReceDebtMoney;
	
	@Column(name="LDateOfRepayment", length=20,nullable=true)
	@IColumn(description="最近一次还款日期",isNullable=true)
	private String LDateOfRepayment;
	
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


	public String getAssetsManaCompany() {
		return AssetsManaCompany;
	}


	public void setAssetsManaCompany(String assetsManaCompany) {
		AssetsManaCompany = assetsManaCompany;
	}


	public String getDebtAcceptDate() {
		return DebtAcceptDate;
	}


	public void setDebtAcceptDate(String debtAcceptDate) {
		DebtAcceptDate = debtAcceptDate;
	}


	public String getReceDebtMoney() {
		return ReceDebtMoney;
	}


	public void setReceDebtMoney(String receDebtMoney) {
		ReceDebtMoney = receDebtMoney;
	}


	public String getLDateOfRepayment() {
		return LDateOfRepayment;
	}


	public void setLDateOfRepayment(String lDateOfRepayment) {
		LDateOfRepayment = lDateOfRepayment;
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
