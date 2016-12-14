package report.dto;

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
import framework.interfaces.IColumn;

@Entity
@Table(name = "ItemsCalculation")
public class ItemsCalculation implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -6678688465822578333L;

	@Id
	@Column(name = "autoItemsCalculationID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoItemsCalculationID;
	
	@IColumn(description="项类型", isNullable = false)
	@Column(name = "strItemType", nullable = false)
	private String strItemType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoCalculationRuleID")
	@IColumn(isSingleTagHidden=true)
	private CalculationRule calculationRule;

	@Column(name="intOrder",length = 50,nullable=false)
	@IColumn(description="顺序", isNullable = false)
	private Integer intOrder;
	
	@Column(name="strItemValue",length = 4000,nullable=false)
	@IColumn(description="项值", isNullable = false)
	private String strItemValue;

	@Transient
	private String strItemTypeDesc;
	@Transient
	private String strItemCodeName;
	@Transient
	private String itemCode;
	@Transient
	private String curr;
	@Transient
	private String property;
	@Transient
	private String extend1;
	@Transient
	private String extend2;
	@Transient
	private String time;
	@Transient
	private String freq;
	@Transient	
	private String acctableName;
	@Transient
	private String calcField;
	@Transient
	private String calcType;
	@Transient
	private String whereCondition;
	@Transient
	private String instName;
	@Transient
	private String dtDate;
	@Transient
	private String instInfo;
	@Transient
	private String extend2Name;
	@Transient
	private String extend1Name;
	@Transient
	private String freqName;
	@Transient
	private String proName;
	@Transient
	private String currName;
	@Transient
	private String timeName;

	
	public String getExtend2Name() {
		return extend2Name;
	}

	public void setExtend2Name(String extend2Name) {
		this.extend2Name = extend2Name;
	}

	public String getExtend1Name() {
		return extend1Name;
	}

	public void setExtend1Name(String extend1Name) {
		this.extend1Name = extend1Name;
	}

	public String getFreqName() {
		return freqName;
	}

	public void setFreqName(String freqName) {
		this.freqName = freqName;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCurrName() {
		return currName;
	}

	public void setCurrName(String currName) {
		this.currName = currName;
	}

	@Transient
	private String autoCalculationRuleID;
	public ItemsCalculation()
	{
		
	}
	
	public ItemsCalculation(String strItemType,String strItemValue,String autoCalculationRuleID)
	{
		this.strItemType=strItemType;
		this.strItemValue=strItemValue;
		this.autoCalculationRuleID=autoCalculationRuleID;
	}
	
	public String getAutoCalculationRuleID()
	{
		return this.autoCalculationRuleID;
	}
	public String getAcctableName() {
		return acctableName;
	}

	public void setAcctableName(String acctableName) {
		this.acctableName = acctableName;
	}

	public String getCalcField() {
		return calcField;
	}

	public void setCalcField(String calcField) {
		this.calcField = calcField;
	}

	public String getCalcType() {
		return calcType;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public String getWhereCondition() {
		return whereCondition;
	}

	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public void setAutoItemsCalculationID(String autoItemsCalculationID) {
		this.autoItemsCalculationID = autoItemsCalculationID;
	}

	public String getAutoItemsCalculationID() {
		return autoItemsCalculationID;
	}

	public void setStrItemType(String strItemType) {
		this.strItemType = strItemType;
	}

	public String getStrItemType() {
		return strItemType;
	}

	public void setCalculationRule(CalculationRule calculationRule) {
		this.calculationRule = calculationRule;
	}

	public CalculationRule getCalculationRule() {
		return calculationRule;
	}

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}

	public void setStrItemValue(String strItemValue) {
		this.strItemValue = strItemValue;
	}

	public String getStrItemValue() {
		return strItemValue;
	}

	public void setStrItemTypeDesc(String strItemTypeDesc) {
		this.strItemTypeDesc = strItemTypeDesc;
	}

	public String getStrItemTypeDesc() {
		return strItemTypeDesc;
	}

	public void setStrItemCodeName(String strItemCodeName) {
		this.strItemCodeName = strItemCodeName;
	}

	public String getStrItemCodeName() {
		return strItemCodeName;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getFreq() {
		return freq;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getInstName() {
		return instName;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setInstInfo(String instInfo) {
		this.instInfo = instInfo;
	}

	public String getInstInfo() {
		return instInfo;
	}

	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}

	public String getTimeName() {
		return timeName;
	}

}

