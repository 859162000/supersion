package report.dto;

import java.math.BigDecimal;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;


@Entity
@Table(name = "CheckBasic")
@IEntity(navigationName="基本校验")
public class CheckBasic implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -7603730230978641603L;

	@Id
	@Column(name = "checkBasicID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkBasicID;
	
	@Column(name="name",length =35,nullable=true)
	@IColumn(description="字段名",isKeyName=true)
	private String name;
	
	@Column(name="discription",length =35,nullable=true)
	@IColumn(description="描述")
	private String discription;
	
	@IColumn(tagMethodName="getEmptyCheckTag",description="空校验")
	@Column(name = "emptyCheck", nullable = true,length=10)
	private String emptyCheck;
	
	public static Map<String,String> getEmptyCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("emptyCheckTag");
	}
	
	@IColumn(tagMethodName="getChnCheckTag",description="中文字符")
	@Column(name = "chnCheck", nullable = true,length=10)
	private String chnCheck;
	
	public static Map<String,String> getChnCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("chnCheckTag");
	}
	
	@Column(name="startLength",length =35,nullable=true)
	@IColumn(description="开始长度")
	private Integer startLength;
	
	@Column(name="endLength",length =35,nullable=true)
	@IColumn(description="结束长度")
	private Integer endLength;
	
	@Column(name="valueDecimalLength",length =35,nullable=true)
	@IColumn(description="小数位数")
	private Integer valueDecimalLength;
	
	@Column(name="compareValue",length =35,nullable=true)
	@IColumn(description="对比值")
	private Double compareValue;
	
	@Column(name="ignoreVal", nullable = true,length=10)
	@IColumn(description="忽略值")
	private String ignoreVal;
	
	@IColumn(tagMethodName="getNumCheckTag",description="不能全为数字")
	@Column(name = "numCheck", nullable = true,length=10)
	private String numCheck;
	
	public static Map<String,String> getNumCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("numCheckTag");
	}
	

	@IColumn(tagMethodName="getRepeatLetterTag",description="重复字母校验")
	@Column(name = "repeatLetter", nullable = true,length=10)
	private String repeatLetter;
	
	public static Map<String,String> getRepeatLetterTag(){
		return ShowContext.getInstance().getShowEntityMap().get("repeatLetterTag");
	}
	
	@IColumn(tagMethodName="getErrorcharCheckTag",description="特殊字符校验")
	@Column(name = "errorcharCheck", nullable = true,length=10)
	private String errorcharCheck;
	
	public static Map<String,String> getErrorcharCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("errorcharCheckTag");
	}
	
	@IColumn(tagMethodName="getUpperCheckTag",description="大写字母校验")
	@Column(name = "upperCheck", nullable = true,length=10)
	private String upperCheck;
	
	public static Map<String,String> getUpperCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("upperCheckTag");
	}
		
	@IColumn(tagMethodName="getDigitalCheckTag",description="数字校验")
	@Column(name = "digitalCheck", nullable = true,length=10)
	private String digitalCheck;
	
	public static Map<String,String> getDigitalCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("digitalCheckTag");
	}
	
	@IColumn(tagMethodName="getDateCheckTag",description="日期校验")
	@Column(name = "dateCheck", nullable = true,length=10)
	private String dateCheck;
	
	public static Map<String,String> getDateCheckTag(){
		return ShowContext.getInstance().getShowEntityMap().get("dateCheckTag");
	}
	
	@Column(name="valueMax",length =35,nullable=true)
	@IColumn(description="最大值")
	private BigDecimal valueMax;
	
	@Column(name="valueMin",length =35,nullable=true)
	@IColumn(description="最小值")
	private BigDecimal valueMin;
	
	@IColumn(tagMethodName="getNumAndBeginNumTag",description="不全为数字且不为数字开头")
	@Column(name = "numAndBeginNum", nullable = true,length=10)
	private String numAndBeginNum;
	
	public static Map<String,String> getNumAndBeginNumTag(){
		return ShowContext.getInstance().getShowEntityMap().get("numAndBeginNumTag");
	}
	
	@Column(name="notEqualValue",length =35,nullable=true)
	@IColumn(description="不等值")
	private String notEqualValue;
	
	@IColumn(tagMethodName="getdateCheckForSixBitTag",description="六位日期校验")
	@Column(name = "dateCheckForSixBit", nullable = true,length=6)
	private String dateCheckForSixBit;
	
	public static Map<String,String> getdateCheckForSixBitTag(){
		return ShowContext.getInstance().getShowEntityMap().get("dateCheckForSixBit");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTableNode checkTableNode;
	
	public String getEmptyCheck() {
		return emptyCheck;
	}

	public void setEmptyCheck(String emptyCheck) {
		this.emptyCheck = emptyCheck;
	}

	public void setCheckBasicID(String checkBasicID) {
		this.checkBasicID = checkBasicID;
	}

	public String getCheckBasicID() {
		return checkBasicID;
	}


	public void setChnCheck(String chnCheck) {
		this.chnCheck = chnCheck;
	}


	public String getChnCheck() {
		return chnCheck;
	}

	public void setIgnoreVal(String ignoreVal) {
		this.ignoreVal = ignoreVal;
	}
	public String getIgnoreVal() {
		return ignoreVal;
	}

	public void setNumCheck(String numCheck) {
		this.numCheck = numCheck;
	}

	public String getNumCheck() {
		return numCheck;
	}

	public void setRepeatLetter(String repeatLetter) {
		this.repeatLetter = repeatLetter;
	}

	public String getRepeatLetter() {
		return repeatLetter;
	}

	public void setErrorcharCheck(String errorcharCheck) {
		this.errorcharCheck = errorcharCheck;
	}

	public String getErrorcharCheck() {
		return errorcharCheck;
	}

	public void setUpperCheck(String upperCheck) {
		this.upperCheck = upperCheck;
	}

	public String getUpperCheck() {
		return upperCheck;
	}

	public void setDigitalCheck(String digitalCheck) {
		this.digitalCheck = digitalCheck;
	}

	public String getDigitalCheck() {
		return digitalCheck;
	}

	public void setNumAndBeginNum(String numAndBeginNum) {
		this.numAndBeginNum = numAndBeginNum;
	}

	public String getNumAndBeginNum() {
		return numAndBeginNum;
	}

	public void setDateCheck(String dateCheck) {
		this.dateCheck = dateCheck;
	}

	public String getDateCheck() {
		return dateCheck;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setDiscription(String discription) {
		this.discription = discription;
	}


	public String getDiscription() {
		return discription;
	}

	public void setStartLength(String startLength) {
		this.startLength = TypeParse.parseInt(startLength);
	}

	public Integer getStartLength() {
		return startLength;
	}

	public void setEndLength(String endLength) {
		this.endLength = TypeParse.parseInt(endLength);
	}

	public Integer getEndLength() {
		return endLength;
	}

	public void setValueDecimalLength(String valueDecimalLength) {
		this.valueDecimalLength = TypeParse.parseInt(valueDecimalLength);
	}

	public Integer getValueDecimalLength() {
		return valueDecimalLength;
	}

	public void setCompareValue(String compareValue) {
		this.compareValue = TypeParse.parseDouble(compareValue);
	}

	public Double getCompareValue() {
		return compareValue;
	}

	public void setCheckTableNode(CheckTableNode checkTableNode) {
		this.checkTableNode = checkTableNode;
	}

	public CheckTableNode getCheckTableNode() {
		return checkTableNode;
	}

	public void setValueMax(String valueMax) {
		this.valueMax = TypeParse.parseBigDecimal(valueMax);
	}

	public BigDecimal getValueMax() {
		return valueMax;
	}

	public void setValueMin(String valueMin) {
		this.valueMin = TypeParse.parseBigDecimal(valueMin);
	}

	public BigDecimal getValueMin() {
		return valueMin;
	}

	public void setNotEqualValue(String notEqualValue) {
		this.notEqualValue = notEqualValue;
	}

	public String getNotEqualValue() {
		return notEqualValue;
	}

	public void setDateCheckForSixBit(String dateCheckForSixBit) {
		this.dateCheckForSixBit = dateCheckForSixBit;
	}

	public String getDateCheckForSixBit() {
		return dateCheckForSixBit;
	}
}
