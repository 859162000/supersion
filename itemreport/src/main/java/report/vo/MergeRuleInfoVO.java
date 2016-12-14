package report.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="MergeItemInfo")
public class MergeRuleInfoVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -3005252838857575087L;
	@Column
	private String id;
	@Column
	private String strMergeSummaryid;
	@Column
	private String strItemCode;
	@Column
	private String strPropertyCode;
	@Column
	private String currencyType;
	@Column
	private String strExtendDimension1;
	@Column
	private String strExtendDimension2;
	@Column
	private String strFreq;
	@Column
	private Date startdate;
	@Column
	private Date enddate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStrMergeSummaryid() {
		return strMergeSummaryid;
	}
	public void setStrMergeSummaryid(String strMergeSummaryid) {
		this.strMergeSummaryid = strMergeSummaryid;
	}
	public String getStrItemCode() {
		return strItemCode;
	}
	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}
	public String getStrPropertyCode() {
		return strPropertyCode;
	}
	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getStrExtendDimension1() {
		return strExtendDimension1;
	}
	public void setStrExtendDimension1(String strExtendDimension1) {
		this.strExtendDimension1 = strExtendDimension1;
	}
	public String getStrExtendDimension2() {
		return strExtendDimension2;
	}
	public void setStrExtendDimension2(String strExtendDimension2) {
		this.strExtendDimension2 = strExtendDimension2;
	}
	public String getStrFreq() {
		return strFreq;
	}
	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
}
