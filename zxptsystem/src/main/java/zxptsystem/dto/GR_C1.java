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
@Table(name="GR_C1")
@IEntity(description="信息提示")
public class GR_C1 implements Serializable {

	private static final long serialVersionUID = 79082041489888076L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReportNo", nullable = false)
	@IColumn(description="报告编号" , isNullable = false)
	private GR_A1 ReportNo;

	@Column(name="HouseLoanAmount", length=10, nullable=true)
	@IColumn(description="个人住房贷款笔数", isNullable=true)
	private String HouseLoanAmount;

	@Column(name="BusiHouseLoanAmount", length=10, nullable=true)
	@IColumn(description="个人商用房（包括商住两用）贷款笔数", isNullable=true)
	private String BusiHouseLoanAmount;
	
	@Column(name="OtherLoanAmount", length=10, nullable=true)
	@IColumn(description="其他贷款笔数", isNullable=true)
	private String OtherLoanAmount;

	@Column(name="FirstLoanProMonth", length=10, nullable=true)
	@IColumn(description="首笔贷款发放月份", isNullable=true)
	private String FirstLoanProMonth;

	@Column(name="CreditCardAccounts", length=10, nullable=true)
	@IColumn(description="贷记卡账户数", isNullable=true)
	private String CreditCardAccounts;

	@Column(name="FirstCCProMonth", length=10, nullable=true)
	@IColumn(description="首张贷记卡发卡月份", isNullable=true)
	private String FirstCCProMonth;

	@Column(name="QCCAccounts", length=10, nullable=true)
	@IColumn(description="准贷记卡账户数", isNullable=true)
	private String QCCAccounts;

	@Column(name="FQCCProMonth", length=10, nullable=true)
	@IColumn(description="首张准贷记卡发卡月份", isNullable=true)
	private String FQCCProMonth;

	@Column(name="PerStateAmount", length=10, nullable=true)
	@IColumn(description="本人声明数目", isNullable=true)
	private String PerStateAmount;

	@Column(name="ObjLabelAmount", length=10, nullable=true)
	@IColumn(description="异议标注数目", isNullable=true)
	private String ObjLabelAmount;

	@Column(name="ChinaCreditGrade", length=10, nullable=true)
	@IColumn(description="中征信评分", isNullable=true)
	private String ChinaCreditGrade;

	@Column(name="GradeMonth", length=10, nullable=true)
	@IColumn(description="评分月份", isNullable=true)
	private String GradeMonth;

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

	public String getHouseLoanAmount() {
		return HouseLoanAmount;
	}

	public void setHouseLoanAmount(String houseLoanAmount) {
		HouseLoanAmount = houseLoanAmount;
	}
	
	public String getBusiHouseLoanAmount() {
		return BusiHouseLoanAmount;
	}

	public void setBusiHouseLoanAmount(String busiHouseLoanAmount) {
		BusiHouseLoanAmount = busiHouseLoanAmount;
	}

	public String getOtherLoanAmount() {
		return OtherLoanAmount;
	}

	public void setOtherLoanAmount(String otherLoanAmount) {
		OtherLoanAmount = otherLoanAmount;
	}

	public String getFirstLoanProMonth() {
		return FirstLoanProMonth;
	}

	public void setFirstLoanProMonth(String firstLoanProMonth) {
		FirstLoanProMonth = firstLoanProMonth;
	}

	public String getCreditCardAccounts() {
		return CreditCardAccounts;
	}

	public void setCreditCardAccounts(String creditCardAccounts) {
		CreditCardAccounts = creditCardAccounts;
	}

	public String getFirstCCProMonth() {
		return FirstCCProMonth;
	}

	public void setFirstCCProMonth(String firstCCProMonth) {
		FirstCCProMonth = firstCCProMonth;
	}

	public String getQCCAccounts() {
		return QCCAccounts;
	}

	public void setQCCAccounts(String qCCAccounts) {
		QCCAccounts = qCCAccounts;
	}

	public String getFQCCProMonth() {
		return FQCCProMonth;
	}

	public void setFQCCProMonth(String fQCCProMonth) {
		FQCCProMonth = fQCCProMonth;
	}

	public String getPerStateAmount() {
		return PerStateAmount;
	}

	public void setPerStateAmount(String perStateAmount) {
		PerStateAmount = perStateAmount;
	}

	public String getObjLabelAmount() {
		return ObjLabelAmount;
	}

	public void setObjLabelAmount(String objLabelAmount) {
		ObjLabelAmount = objLabelAmount;
	}

	public String getChinaCreditGrade() {
		return ChinaCreditGrade;
	}

	public void setChinaCreditGrade(String chinaCreditGrade) {
		ChinaCreditGrade = chinaCreditGrade;
	}

	public String getGradeMonth() {
		return GradeMonth;
	}

	public void setGradeMonth(String gradeMonth) {
		GradeMonth = gradeMonth;
	}

	
	
}
