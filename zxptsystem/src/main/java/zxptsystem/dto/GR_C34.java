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
@Table(name="GR_C34")
@IEntity(description="授信及负债信息概要-对外担保信息汇总")
public class GR_C34 implements Serializable {

private static final long serialVersionUID = 3508385652264696067L;
	
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	@Column(name="GuarAmount", length=10,nullable=true)
	@IColumn(description="担保笔数",isNullable=true)
	private String GuarAmount;
	
	@Column(name="GuarMoney", length=20,nullable=true)
	@IColumn(description="担保金额",isNullable=true)
	private String GuarMoney;
	
	@Column(name="GuarPrilBalance", length=20,nullable=true)
	@IColumn(description="担保本金余额",isNullable=true)
	private String GuarPrilBalance;
	
	
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


	public String getGuarAmount() {
		return GuarAmount;
	}


	public void setGuarAmount(String guarAmount) {
		GuarAmount = guarAmount;
	}


	public String getGuarMoney() {
		return GuarMoney;
	}


	public void setGuarMoney(String guarMoney) {
		GuarMoney = guarMoney;
	}


	public String getGuarPrilBalance() {
		return GuarPrilBalance;
	}


	public void setGuarPrilBalance(String guarPrilBalance) {
		GuarPrilBalance = guarPrilBalance;
	}


	public GR_A1 getReportNo() {
		return ReportNo;
	}


	public void setReportNo(GR_A1 reportNo) {
		ReportNo = reportNo;
	}
	
	
}
