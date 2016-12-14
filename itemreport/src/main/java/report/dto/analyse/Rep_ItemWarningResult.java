package report.dto.analyse;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import report.dto.CurrencyType;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import coresystem.dto.InstInfo;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：Rep_ItemWarningResult</P>
 * *********************************<br>
 * <P>类描述：预警结果</P>
 * 创建人：王川<br>
 * 创建时间：2016-9-2 下午2:37:04<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-2 下午2:37:04<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_ItemWarningResult")
@IEntity(navigationName="预警结果",description="预警结果")
public class Rep_ItemWarningResult implements Serializable {
	
	/**  **/
	private static final long serialVersionUID = -2582963948287916058L;

	/** uuid **/
	@Id
	@Column(name="uuid",length=32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String uuid;
	
	@IColumn(description="预警规则",isSingleTagHidden=true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="warnRuleId",nullable = false)
	private Rep_ItemWarning warningRule;
	
	@IColumn(description="预警规则",isListShow=true)
	@Transient
	private String strWarRule="";
	
	/** 预警值 0:绿色正常 1:黄色预警  2:红色预警 **/
	@IColumn(description="预警结果")
	@Column(name="warningVal",length=10)
	private String warningVal;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "modelId", nullable = false)
	private Rep_AnalyseModel model;
	
	@Column(name = "dtDate")
	@IColumn(description="数据时间")
	private String dtDate;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strItemCode", nullable = false)
	private ItemInfo itemInfo;
	
	@Column(name = "strValue", length = 50, nullable = false)
	@IColumn(description="值", isNullable = false)
	private String strValue;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strPropertyCode", nullable = false)
	private ItemProperty itemProperty;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currencyType", nullable = false)
	private CurrencyType currencyType;
	
	@Column(name = "strFreq",length=50)
	@IColumn(tagMethodName="getStrFreqTag", description="频度")
	private String strFreq;
	public static Map<String,String> getStrFreqTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	
	@Column(name = "strExtendDimension1", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag2",description="扩展维度1",isSingleTagHidden=true)
	private String strExtendDimension1;
	
	public static Map<String,String> getExtendPropertyTag1(){
		return ShowContext.getInstance().getShowEntityMap().get("extendProperty1");
	}
	
	@Column(name = "strExtendDimension2", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag2",description="扩展维度2",isSingleTagHidden=true)
	private String strExtendDimension2;
	
	public static Map<String,String> getExtendPropertyTag2(){
		return ShowContext.getInstance().getShowEntityMap().get("extendProperty2");
	}
	
	public Rep_ItemWarningResult(){
		
	}

	public Rep_ItemWarningResult(ItemData itemData){
		this.dtDate = TypeParse.parseString(itemData.getDtDate(),"yyyy-MM-dd") ;
		this.itemInfo = itemData.getItemInfo();
		this.instInfo = itemData.getInstInfo();
		this.currencyType = itemData.getCurrencyType();
		this.itemProperty = itemData.getItemProperty();
		this.strFreq = itemData.getStrFreq();
		this.strExtendDimension1 = itemData.getStrExtendDimension1();
		this.strExtendDimension2 = itemData.getStrExtendDimension2();
		this.strValue = itemData.getStrValue();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Rep_AnalyseModel getModel() {
		return model;
	}

	public void setModel(Rep_AnalyseModel model) {
		this.model = model;
	}

	public String getWarningVal() {
		return warningVal;
	}

	public void setWarningVal(String warningVal) {
		this.warningVal = warningVal;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public ItemProperty getItemProperty() {
		return itemProperty;
	}

	public void setItemProperty(ItemProperty itemProperty) {
		this.itemProperty = itemProperty;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
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

	public Rep_ItemWarning getWarningRule() {
		return warningRule;
	}

	public void setWarningRule(Rep_ItemWarning warningRule) {
		this.warningRule = warningRule;
	}

	public String getStrWarRule() {
		if(null != warningRule){
			strWarRule ="正常【"+warningRule.getBestDown()+"-"+warningRule.getBestUp()+"】、";
			strWarRule +="预警【"+warningRule.getCriticalDown()+"-"+warningRule.getCriticalUp()+"】、";
			strWarRule +="高危【"+warningRule.getWorstDown()+"-"+warningRule.getWorstUp()+"】";
		}
		return strWarRule;
	}
	
}
