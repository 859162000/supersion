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
@Table(name="GR_E10")
@IEntity(description="公共信息明细-电信缴费记录")
public class GR_E10 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6743472936221956621L;


	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;
	
	@Column(name="TeleOperators", length=200,nullable=true)
	@IColumn(description="电信运营商",isNullable=true)
	private String TeleOperators;

	@Column(name="BusType", length=50,nullable=true)
	@IColumn(description="业务类型",isNullable=true)
	private String BusType;

	@Column(name="BusInauDate", length=20,nullable=true)
	@IColumn(description="业务开通日期",isNullable=true)
	private String BusInauDate;

	@Column(name="CurrPayStatus", length=50,nullable=true)
	@IColumn(description="当前缴费状态",isNullable=true)
	private String CurrPayStatus;

	@Column(name="CurrArrearsAmount", length=20,nullable=true)
	@IColumn(description="当前欠费金额",isNullable=true)
	private String CurrArrearsAmount;

	@Column(name="CurrArrearsMonth", length=100,nullable=true)
	@IColumn(description="当前欠费月数",isNullable=true)
	private String CurrArrearsMonth;

	@Column(name="JournalEntry", length=20,nullable=true)
	@IColumn(description="记账年月",isNullable=true)
	private String JournalEntry;

	@Column(name="Re_24MonPayRecord", length=50,nullable=true)
	@IColumn(description="最近24个月缴费记录",isNullable=true)
	private String Re_24MonPayRecord;

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

	public String getTeleOperators() {
		return TeleOperators;
	}

	public void setTeleOperators(String teleOperators) {
		TeleOperators = teleOperators;
	}

	public String getBusType() {
		return BusType;
	}

	public void setBusType(String busType) {
		BusType = busType;
	}

	public String getBusInauDate() {
		return BusInauDate;
	}

	public void setBusInauDate(String busInauDate) {
		BusInauDate = busInauDate;
	}

	public String getCurrPayStatus() {
		return CurrPayStatus;
	}

	public void setCurrPayStatus(String currPayStatus) {
		CurrPayStatus = currPayStatus;
	}

	public String getCurrArrearsAmount() {
		return CurrArrearsAmount;
	}

	public void setCurrArrearsAmount(String currArrearsAmount) {
		CurrArrearsAmount = currArrearsAmount;
	}

	public String getCurrArrearsMonth() {
		return CurrArrearsMonth;
	}

	public void setCurrArrearsMonth(String currArrearsMonth) {
		CurrArrearsMonth = currArrearsMonth;
	}

	public String getJournalEntry() {
		return JournalEntry;
	}

	public void setJournalEntry(String journalEntry) {
		JournalEntry = journalEntry;
	}

	public String getRe_24MonPayRecord() {
		return Re_24MonPayRecord;
	}

	public void setRe_24MonPayRecord(String re_24MonPayRecord) {
		Re_24MonPayRecord = re_24MonPayRecord;
	}



}
