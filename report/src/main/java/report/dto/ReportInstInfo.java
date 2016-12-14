package report.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

import extend.dto.Suit;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "ReportInstInfo")
@IEntity(description="报送机构管理")
public class ReportInstInfo implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 7677870361438860669L;

	@Id
	@Column(name = "strReportInstCode", length = 50)
	@IColumn(description="机构代码")
	private String strReportInstCode;
	
	@Column(name = "strReportInstName", length = 50, nullable = false)
	@IColumn(description="机构名称", isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	private String strReportInstName;
	
	@Column(name = "strReportContact", length = 50)
	@IColumn(description="联系人")
	private String strReportContact;
	
	@Column(name = "strReportContactTel", length = 50)
	@IColumn(description="联系电话")
	private String strReportContactTel;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportSuit")
	@IColumn(description="所属主题", isNullable = false)
	private Suit suit;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "reportInstInfo")
	private Set<ReportInstSubInfo> reportInstSubInfos = new HashSet<ReportInstSubInfo>(0);

	public String getStrReportInstCode() {
		return strReportInstCode;
	}

	public void setStrReportInstCode(String strReportInstCode) {
		this.strReportInstCode = strReportInstCode;
	}

	public String getStrReportInstName() {
		return strReportInstName;
	}

	public void setStrReportInstName(String strReportInstName) {
		this.strReportInstName = strReportInstName;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public void setReportInstSubInfos(Set<ReportInstSubInfo> reportInstSubInfos) {
		this.reportInstSubInfos = reportInstSubInfos;
	}

	public Set<ReportInstSubInfo> getReportInstSubInfos() {
		return reportInstSubInfos;
	}

	public void setStrReportContact(String strReportContact) {
		this.strReportContact = strReportContact;
	}

	public String getStrReportContact() {
		return strReportContact;
	}

	public void setStrReportContactTel(String strReportContactTel) {
		this.strReportContactTel = strReportContactTel;
	}

	public String getStrReportContactTel() {
		return strReportContactTel;
	}
	
	@Column(name = "orgTypeCode", length = 50, nullable = true)
	@IColumn(description="机构类码", isNullable = true)
	private String orgTypeCode;

	@Column(name = "adminRegionCode", length = 50, nullable = true)
	@IColumn(description="地区码", isNullable = true)
	private String adminRegionCode;

	
	
	public String getOrgTypeCode()
	{
		return this.orgTypeCode;
	}
	public void setOrgTypeCode(String orgTypeCode)
	{
		this.orgTypeCode=orgTypeCode;
	}
	public String getAdminRegionCode()
	{
		return this.adminRegionCode;
	}
	public void setAdminRegionCode(String adminRegCode)
	{
		this.adminRegionCode=adminRegCode;
	}
	

}

