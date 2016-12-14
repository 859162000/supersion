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
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "ItemData")
@SecondaryTable(name = "ItemDataCheckResult") 
public class ItemData implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 7569646644932410980L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate")
	@IColumn(description="数据时间")
	private Date dtDate;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strPropertyCode", nullable = false)
	private ItemProperty itemProperty;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyType", nullable = false)
	private CurrencyType currencyType;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strItemCode", nullable = false)
	private ItemInfo itemInfo;
	
	@Column(name = "strValue", length = 50, nullable = false)
	@IColumn(description="值", isNullable = false)
	private String strValue;
	
	@Column(name = "value1", length = 50)
	private String value1;
	
	@Column(name = "strExtendDimension1", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag2",description="扩展维度1")
	private String strExtendDimension1;
	
	public static Map<String,String> getExtendPropertyTag1(){
		return ShowContext.getInstance().getShowEntityMap().get("extendProperty1");
	}
	
	@Column(name = "strExtendDimension2", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag2",description="扩展维度2")
	private String strExtendDimension2;
	
	public static Map<String,String> getExtendPropertyTag2(){
		return ShowContext.getInstance().getShowEntityMap().get("extendProperty2");
	}
	
	@Column(name = "strCheckState", length = 50, nullable = false)
	@IColumn(tagMethodName="getCheckResultTag",description="校验状态", isNullable = false)
	private String strCheckState;
	
	public static Map<String,String> getCheckResultTag(){
		return ShowContext.getInstance().getShowEntityMap().get("checkResult");
	}
	
	@Column(table="ItemDataCheckResult",name = "strCheckResult", length = 4000)
	@IColumn(description="校验结果")
	private String strCheckResult;
	
	@Column(table="ItemDataCheckResult",name = "strSumCheckResult", length = 4000)
	@IColumn(description="总分校验结果")
	private String strSumCheckResult;

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
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setDtDate(Date dtDate) {
		this.dtDate =dtDate;
	}
	
	public void setDtDate(String dtDate) {
		this.dtDate = TypeParse.parseDate(dtDate);
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setItemProperty(ItemProperty itemProperty) {
		this.itemProperty = itemProperty;
	}

	public ItemProperty getItemProperty() {
		return itemProperty;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setStrCheckState(String strCheckState) {
		this.strCheckState = strCheckState;
	}

	public String getStrCheckState() {
		return strCheckState;
	}

	public void setStrCheckResult(String strCheckResult) {
		this.strCheckResult = strCheckResult;
	}

	public String getStrCheckResult() {
		return strCheckResult;
	}
	
	public void setStrSumCheckResult(String strSumCheckResult) {
		this.strSumCheckResult = strSumCheckResult;
	}

	public String getStrSumCheckResult() {
		return strSumCheckResult;
	}

	public void setStrExtendDimension1(String strExtendDimension1) {
		this.strExtendDimension1 = strExtendDimension1;
	}

	public String getStrExtendDimension1() {
		return strExtendDimension1;
	}

	public void setStrExtendDimension2(String strExtendDimension2) {
		this.strExtendDimension2 = strExtendDimension2;
	}

	public String getStrExtendDimension2() {
		return strExtendDimension2;
	}
	public String getAllCheckMsg()
	{
		
		return getAllCheckMsg(this.strCheckResult,this.strSumCheckResult);
	}
	public static String getAllCheckMsg(String strCheckResult,String strSumCheckResult)
	{
		if(!StringUtils.isBlank(strCheckResult)&& !StringUtils.isBlank(strSumCheckResult))
		{
			return "逻辑及总分校验错误\r\n===============================\r\n逻辑校验错误\r\n\r\n"
			          +strCheckResult
			          +"\r\n===============================\r\n总分校验错误\r\n\r\n"
			          +strSumCheckResult;
		}
		return (strCheckResult==null?"":strCheckResult)+(strSumCheckResult==null?"":strSumCheckResult);
	}

	
}
