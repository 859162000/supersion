package report.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.bytecode.javassist.FieldHandled;
import org.hibernate.bytecode.javassist.FieldHandler;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "CalculationRule")
public class CalculationRule implements java.io.Serializable, FieldHandled{
	
	
	/**  **/
	private static final long serialVersionUID = 615099678366805047L;

	@Id
	@Column(name = "autoCalculationRuleID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoCalculationRuleID;
	
	@Column(name="strCalculationRuleName",length = 50,nullable=false)
	@IColumn(description="规则名称",isKeyName=true, isNullable = false)
	private String strCalculationRuleName;	
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "calculationRule")
	private Set<ExampleInfoRule> exampleInfoRules = new HashSet<ExampleInfoRule>(0);
	
	
	@Transient
	@IColumn(isIdListField = true, target="exampleInfoRules", mappedBy = "calculationExampleInfo")
	private String[] calculationRuleIdList;

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
	
	@Temporal(TemporalType.DATE)
	@Column(name="startdate")
	@IColumn(description="开始时间")
	private Date startdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="enddate")
	@IColumn(description="结束时间")
	private Date enddate;
	
	// 0 系统 、1 自定义
	@Column(name="source", length = 5)
	@IColumn(description="来源",tagMethodName="")
	private String source = "0";
	
	public static Map<String,String> getPropertySource(){
		return ShowContext.getInstance().getShowEntityMap().get("source");
	}
	
	@Column(name = "strExtendDimension1", length = 50)
	@IColumn(tagMethodName="getExtendPropertyTag1",description="扩展维度1")
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
	
	@Column(name="strDescription",length = 200)
	@IColumn(description="描述",isKeyName=true)
	private String strDescription;
	
	@Column(name = "intOrder")
	@IColumn(description="计算优先级（值越小越先计算）")
	private Integer intOrder;
	
	@Column(name = "strExpression")
	//@Lob
	@Type(type="text") 
	@IColumn(description="表达式")
	@Basic(fetch = FetchType.LAZY)
	private String strExpression;
	
	private FieldHandler fieldHandler;
	
	public FieldHandler getFieldHandler() {  
        return fieldHandler;  
    }  
  
    public void setFieldHandler(FieldHandler fieldHandler) {  
        this.fieldHandler = fieldHandler;  
    }  
	
	public String getStrExpression()
	{
		if (fieldHandler != null) {  
            return (String) fieldHandler.readObject(this, "strExpression", strExpression);  
         }  
		return null; 
	}
	
	public void setStrExpression(String strExpression)
	{
		this.strExpression=strExpression;
	}
	@Column(name = "intPrecise",nullable=true)
	@IColumn(description="精度位数",isNullable=true)
	private Integer intPrecise;
	/*public void setIntPrecise(Integer intPrecise) {
		//this.intPrecise =TypeParse.parseInt(intPrecise) ;
		this.intPrecise=intPrecise;
	}*/
	
	public void setIntPrecise(String intPrecise) {
		this.intPrecise = TypeParse.parseInt(intPrecise);
	}

	public Integer getIntPrecise() {
		return intPrecise;
	}
	
	@Column(name = "strCalRoundMethod",length=50,nullable=true)
	@IColumn(description="计算舍入方式",tagMethodName="getStrCalRoundMethodTag",isNullable=true)
	private String strCalRoundMethod;
	public void setStrCalRoundMethod(String strRoundMethod) {
		//this.intPrecise =TypeParse.parseInt(intPrecise) ;
		this.strCalRoundMethod=strRoundMethod;
	}

	public String getStrCalRoundMethod() {
		return strCalRoundMethod;
	}
	public static Map<String,String> getStrCalRoundMethodTag(){
		return ShowContext.getInstance().getShowEntityMap().get("calRoundMehod");
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
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "calculationRule")
	private Set<ItemsCalculation> itemsCalculations = new HashSet<ItemsCalculation>(0);

	public String getAutoCalculationRuleID() {
		return autoCalculationRuleID;
	}

	public void setAutoCalculationRuleID(String autoCalculationRuleID) {
		this.autoCalculationRuleID = autoCalculationRuleID;
	}	
	
	public void setIntOrder(String strOrder) {
		this.intOrder = TypeParse.parseInt(strOrder);
	}

	public Integer getIntOrder() {
		return intOrder;
	}
	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public void setItemsCalculations(Set<ItemsCalculation> itemsCalculations) {
		this.itemsCalculations = itemsCalculations;
	}

	public Set<ItemsCalculation> getItemsCalculations() {
		return itemsCalculations;
	}

	public void setStrCalculationRuleName(String strCalculationRuleName) {
		this.strCalculationRuleName = strCalculationRuleName;
	}

	public String getStrCalculationRuleName() {
		return strCalculationRuleName;
	}

	public void setExampleInfoRules(Set<ExampleInfoRule> exampleInfoRules) {
		this.exampleInfoRules = exampleInfoRules;
	}

	public Set<ExampleInfoRule> getExampleInfoRules() {
		return exampleInfoRules;
	}

	public void setCalculationRuleIdList(String[] calculationRuleIdList) {
		this.calculationRuleIdList = calculationRuleIdList;
	}

	public String[] getCalculationRuleIdList() {
		return calculationRuleIdList;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemProperty(ItemProperty itemProperty) {
		this.itemProperty = itemProperty;
	}

	public ItemProperty getItemProperty() {
		return itemProperty;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
