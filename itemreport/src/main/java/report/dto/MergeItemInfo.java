package report.dto;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "MergeItemInfo")
public class MergeItemInfo implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 2827897934249321661L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strMergeSummaryid", nullable = false)
	@IColumn(isNullable = false)
	private MergeSummary mergeSummary;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strItemCode", nullable = false)
	private ItemInfo itemInfo;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strPropertyCode")
	private ItemProperty itemProperty;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyType")
	private CurrencyType currencyType;

	@Column(name = "strExtendDimension1", length = 50)
	@IColumn(tagMethodName = "getExtendPropertyTag1", description = "扩展维度1")
	private String strExtendDimension1;

	public static Map<String, String> getExtendPropertyTag1() {
		return ShowContext.getInstance().getShowEntityMap().get(
				"extendProperty1");
	}

	@Column(name = "strExtendDimension2", length = 50)
	@IColumn(tagMethodName = "getExtendPropertyTag2", description = "扩展维度2")
	private String strExtendDimension2;

	public static Map<String, String> getExtendPropertyTag2() {
		return ShowContext.getInstance().getShowEntityMap().get(
				"extendProperty2");
	}
	
	@Column(name = "strFreq",length=50)
	@IColumn(tagMethodName="getStrFreqTag", description="频度")
	private String strFreq;
	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getStrFreq() {
		return strFreq;
	}
	
	public static Map<String,String> getStrFreqTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="startdate")
	@IColumn(description="开始日期")
	private Date startdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="enddate")
	@IColumn(description="结束日期")
	private Date enddate;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MergeSummary getMergeSummary() {
		return mergeSummary;
	}

	public void setMergeSummary(MergeSummary mergeSummary) {
		this.mergeSummary = mergeSummary;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public ItemProperty getItemProperty() {
		return itemProperty;
	}

	public void setItemProperty(ItemProperty itemProperty) {
		this.itemProperty = itemProperty;
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

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	
	public void setStartdate(String startdate) {
		this.startdate =TypeParse.parseDate(startdate);
	}
	
	public Date getStartdate() {
		return startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = TypeParse.parseDate(enddate);
	}

	public Date getEnddate() {
		return enddate;
	}

}
