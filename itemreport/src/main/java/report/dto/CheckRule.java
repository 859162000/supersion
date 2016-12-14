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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.bytecode.javassist.FieldHandled;
import org.hibernate.bytecode.javassist.FieldHandler;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "CheckRule")
public class CheckRule implements java.io.Serializable, FieldHandled {
	
	
	/**  **/
	private static final long serialVersionUID = -8265844211075567994L;

	@Id
	@Column(name = "autoCalculationRuleID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoCalculationRuleID;
	
	@Column(name="strCalculationRuleName",length = 50,nullable=false)
	@IColumn(description="规则名称",isKeyName=true, isNullable = false)
	private String strCalculationRuleName;	
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkRule")
	private Set<CheckInstanceRule> exampleInfoRules = new HashSet<CheckInstanceRule>(0);
	
	
	@Transient
	@IColumn(isIdListField = true, target="exampleInfoRules", mappedBy = "autoCheckInstanceID")
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
	@Column(name="source",length = 5)
	@IColumn(description="来源",tagMethodName="")
	private String source = "0";
	
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
	@IColumn(description="校验组别")
	private Integer intOrder;
	
	
	@Column(name = "strExpression")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@IColumn(description="表达式")
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
	
	@Column(name="dblTolerance",length = 18,precision=6,nullable=true)
	@IColumn(description="容差范围",isNullable = true)
    private Double dblTolerance;
	
	public Double getDblTolerance()
	{
		return this.dblTolerance;
	}
	/*public void setDblTolerance(Double dblTolerance)
	{
		this.dblTolerance=dblTolerance;
	}*/
	
	
	@Column(name="enforced ",length = 1)
	@IColumn(description="是否强制校验（0：强制校验，1：非强制校验）",isNullable=false, tagMethodName="getEnforcedTag")
	private String enforced = "0" ;
	
	public static Map<String,String> getEnforcedTag(){
		return ShowContext.getInstance().getShowEntityMap().get("itemCheckEnforced");
	}
	
	public String getEnforced(){
		return this.enforced;
	}
	
	public void setEnforced(String enforced){
		this.enforced = enforced;
	}
	
	/* 暂时不启用这两个字段
	@Column(name="strSpecial ",length = 20)
	@IColumn(description="特殊校验",isNullable=true)
	private String strSpecial ;
	
	
	@Column(name="scopeOfApplication ",length = 20)
	@IColumn(description="适用对象",isNullable=true,tagMethodName="getScopeOfApplicationTag")
	private String scopeOfApplication ;
	
	public String getStrSpecial(){
		return this.strSpecial;
	}
	
	public void setStrSpecial(String strSpecial){
		this.strSpecial = strSpecial;
	}
	
	public String getScopeOfApplication(){
		return this.scopeOfApplication;
	}
	
	public void setScopeOfApplication(String scopeOfApplication){
		this.scopeOfApplication = scopeOfApplication;
	}*/
	
	
	public void setDblTolerance(String dblTolerance)
	{
		this.dblTolerance=TypeParse.parseDouble(dblTolerance);
	}
	
	@Column(name = "compareType")
	@IColumn(tagMethodName="getCompareTypeTag",description="比较类型",isSpecialCharCheck=true)
	private String compareType;
	
	public static Map<String,String> getCompareTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("compareType");
	}
	
	public String getCompareType()
	{
		return compareType;
	}
	public void setCompareType(String compareType)
	{
		this.compareType=compareType;
	}
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "calculationRule")
	private Set<ItemsCheck> itemsCalculations = new HashSet<ItemsCheck>(0);

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

	public void setItemsCalculations(Set<ItemsCheck> itemsCalculations) {
		this.itemsCalculations = itemsCalculations;
	}

	public Set<ItemsCheck> getItemsCalculations() {
		return itemsCalculations;
	}

	public void setStrCalculationRuleName(String strCalculationRuleName) {
		this.strCalculationRuleName = strCalculationRuleName;
	}

	public String getStrCalculationRuleName() {
		return strCalculationRuleName;
	}

	public void setExampleInfoRules(Set<CheckInstanceRule> exampleInfoRules) {
		this.exampleInfoRules = exampleInfoRules;
	}

	public Set<CheckInstanceRule> getExampleInfoRules() {
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
