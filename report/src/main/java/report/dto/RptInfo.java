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
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.bytecode.javassist.FieldHandled;
import org.hibernate.bytecode.javassist.FieldHandler;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "RptInfo")
@SecondaryTable(name = "RptInfoFormat") 
public class RptInfo implements java.io.Serializable, FieldHandled {
	
	/**  **/
	private static final long serialVersionUID = -5340664517582721976L;

	@Column(name = "strBCode", length = 50)
	@IColumn(description="报表代码", isNullable = false, isSpecialCharCheck=false)
	
    private String strBCode;
	
	@Id
	@Column(name = "strRptCode", length = 32)
	@IColumn(description="主键",isSingleTagHidden=true)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String strRptCode;
	
	@Column(name = "strRptVersion", length = 50)
	@IColumn(description="版本号",isNullable = false, isSpecialCharCheck=false)
	private String strRptVersion;
	
	@Column(name = "strBusiTypeCode", length = 50)
	@IColumn(description="业务类别",isNullable = true,tagMethodName="getBusiTypeTag")
	private String strBusiTypeCode;
	
	public void setStrBusiTypeCode(String strBusiTypeCode)
	{
		this.strBusiTypeCode=strBusiTypeCode;
	}
	
	public String getStrBusiTypeCode()
	{
		return this.strBusiTypeCode;
	}
	public static Map<String,String> getBusiTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("busiType");
	}
	
	@Column(name = "strTime", length = 50)
	@IColumn(description="批次",isNullable = true,tagMethodName="getLotTag")
	private String strTime;
	
	public void setStrTime(String strTime)
	{
		this.strTime=strTime;
	}
	
	public String getStrTime()
	{
		return this.strTime;
	}
	public static Map<String,String> getLotTag(){
		return ShowContext.getInstance().getShowEntityMap().get("lot");
	}
	
	
	
	@Column(name = "strRptName", length = 200, nullable = false)
	@IColumn(description="报表名称", isKeyName=false, isNullable = false, isSpecialCharCheck=true)
	private String strRptName;
	
	@Column(name = "intMaxLine")
	@IColumn(description="明细最大显示行数")
	private Integer intMaxLine;
	
	@Column(name = "strRptPath", length = 260)
	@IColumn(description="模板名称",isSingleTagHidden=true, isSpecialCharCheck=true)
	private String strRptPath;
	
	@Column(name = "intRptUnit")
	@IColumn(tagMethodName="getUnitResultTag", description="报表单位", isNullable = false)
	private Integer intRptUnit;
	
	public static Map<String,String> getUnitResultTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportUnit");
	}
	
	@Column(table="RptInfoFormat",name = "intPrecise")
	@IColumn(description="报表精度位数")
	private Integer intPrecise;
	
	@Column(name = "strFreq")
	@IColumn(tagMethodName="getCheckResultTag", description="报表频度")
	private String strFreq;
	
	public static Map<String,String> getCheckResultTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	
	@Column(table="RptInfoFormat", name = "strIsThousands", columnDefinition="char(1) default '0'")
	@IColumn(tagMethodName="getBooleanTag",description="使用千分位",isNullable=false)
	private String  strIsThousands;
	
	public String getStrIsThousands()
	{
		return this.strIsThousands;
	}
	
	public void setStrIsThousands(String isThousands)
	{
		this.strIsThousands=isThousands;
	}
	public static Map<String,String> getBooleanTag(){
		return ShowContext.getInstance().getShowEntityMap().get("bool");
	}
	@Column(table="RptInfoFormat",name = "strNegativeRedDisplay",  columnDefinition="char(1) default '0'")
	@IColumn(tagMethodName="getBoolTag",description="负数红色显示",isNullable=false)
	private String strNegativeRedDisplay;
	public String getStrNegativeRedDisplay()
	{
		return this.strNegativeRedDisplay;
	}
	
	public void setStrNegativeRedDisplay(String negativeDisStyle)
	{
		this.strNegativeRedDisplay=negativeDisStyle;
	}
	public static Map<String,String> getBoolTag(){
		return ShowContext.getInstance().getShowEntityMap().get("bool");
	}
	
	@Column(table="RptInfoFormat",name = "strNegativeDisplayMinus",columnDefinition="char(1) default '1'")
	@IColumn(tagMethodName="getBoolTag",description="负数显示负号",isNullable=false)
	private String strNegativeDisplayMinus;
	public String getStrNegativeDisplayMinus()
	{
		return this.strNegativeDisplayMinus;
	}
	
	public void setStrNegativeDisplayMinus(String negativeDisStyle)
	{
		this.strNegativeDisplayMinus=negativeDisStyle;
	}
	
	
	/*@OneToOne(fetch = FetchType.EAGER,optional = true, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "strRptCode", referencedColumnName="strRptFormatId") 
	private RptInfoFormat rptFormat;
	
	public void setRptFormat(RptInfoFormat rptFormat)
	{
		this.rptFormat=rptFormat;
	}
	
	public RptInfoFormat getRptFormat()
	{
		if(rptFormat==null)
		{
			rptFormat=new RptInfoFormat();
		}
		return rptFormat;
	}
	
	@Transient
	@Column(table="RptInfoFormat" )
	@IColumn(tagMethodName="getBooleanTag",description="使用千分位",isListShow=true)
	private String  strIsThousands;
	
	public String getStrIsThousands()
	{
		return this.getRptFormat().getStrIsThousands();
	}
	
	public void setStrIsThousands(String isThousands)
	{
		this.getRptFormat().setStrIsThousands(isThousands);
	}
	public static Map<String,String> getBooleanTag(){
		return ShowContext.getInstance().getShowEntityMap().get("bool");
	}
	@Transient
	@IColumn(tagMethodName="getBooleanTag",description="负数红色显示",isListShow=true)
	private String strNegativeRedDisplay;
	public String getStrNegativeRedDisplay()
	{
		return this.getRptFormat().getStrNegativeRedDisplay();
	}
	
	public void setStrNegativeRedDisplay(String negativeDisStyle)
	{
		this.getRptFormat().setStrNegativeRedDisplay(negativeDisStyle);
	}
	
	
	@Transient
	@IColumn(tagMethodName="getBooleanTag",description="负数显示负号",isListShow=true)
	private String strNegativeDisplayMinus;
	public String getStrNegativeDisplayMinus()
	{
		return this.getRptFormat().getStrNegativeDisplayMinus();
	}
	
	public void setStrNegativeDisplayMinus(String negativeDisStyle)
	{
		this.getRptFormat().setStrNegativeDisplayMinus(negativeDisStyle);
	}
	
	*/
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;
	
	@Column(name = "strCheckInst", length = 50)
	@IColumn(description="校验实例")
	private String strCheckInst;
	
	@Column(name = "strCalcInst", length = 50)
	@IColumn(description="计算实例")
	private String strCalcInst;
	
	@Column(name = "strMergeInst", length = 50)
	@IColumn(description="汇总实例")
	private String strMergeInst;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "startDate",nullable = false,length = 16)
	@IColumn(description = "开始时间")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "endDate",nullable = false,length = 16)
	@IColumn(description = "结束时间")
	private Date endDate;
	
	@Column(name = "remark")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@IColumn(description="填报说明")
	private String remark;
	
	@Transient
	private FieldHandler fieldHandler;
	
	public FieldHandler getFieldHandler() {  
        return fieldHandler;  
    }  
  
    public void setFieldHandler(FieldHandler fieldHandler) {  
        this.fieldHandler = fieldHandler;  
    }  
	
	public String getRemark()
	{
		if (fieldHandler != null) {  
            return (String) fieldHandler.readObject(this, "remark", remark);  
         }  
		return null; 
	}
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "rptInfo")
	private Set<TaskRptInst> taskRptInsts = new HashSet<TaskRptInst>(0);
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "rptInfo")
	private Set<RptDto> rptDto = new HashSet<RptDto>(0);
	
	//@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "rptInfo")
	//private Set<RptItem> rptItems = new HashSet<RptItem>(0);
	@Transient
	@IColumn(description = "报表名称", isKeyName=true)
	private String descName;
	
	public String getDescName()
	{
		return this.strBCode+"_"+this.strRptName+"_"+this.strRptVersion;
	}
	public void setDescName(String descName)
	{
		this.descName=descName;
	}
	public void setStrRptCode(String strRptCode) {
		//strRptCode = UUID.randomUUID().toString().replace("-", "");
		this.strRptCode = strRptCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = TypeParse.parseDate(startDate);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = TypeParse.parseDate(endDate);
	}

	public String getStrBCode() {
		return strBCode;
	}

	public void setStrBCode(String strBCode) {
		this.strBCode = strBCode;
	}

	public String getStrRptVersion() {
		return strRptVersion;
	}

	public void setStrRptVersion(String strRptVersion) {
		this.strRptVersion = strRptVersion;
	}

//	public void setIntMaxLine(Integer intMaxLine) {
//		this.intMaxLine = intMaxLine;
//	}

	/*public void setIntRptUnit(Integer intRptUnit) {
		this.intRptUnit = intRptUnit;
	}*/

//	public void setIntPrecise(Integer intPrecise) {
//		this.intPrecise = intPrecise;
//	}

	public String getStrRptCode() {
		return strRptCode;
	}

	public void setStrRptName(String strRptName) {
		
		this.strRptName = strRptName;
	}

	public String getStrRptName() {
		return strRptName;
	}

	public void setTaskRptInsts(Set<TaskRptInst> taskRptInsts) {
		this.taskRptInsts = taskRptInsts;
	}

	public Set<TaskRptInst> getTaskRptInsts() {
		return taskRptInsts;
	}

	public void setRptDto(Set<RptDto> rptDto) {
		this.rptDto = rptDto;
	}

	public Set<RptDto> getRptDto() {
		return rptDto;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	//public void setRptItems(Set<RptItem> rptItems) {
	//	this.rptItems = rptItems;
	//}

	//public Set<RptItem> getRptItems() {
	//	return rptItems;
	//}

	public void setIntMaxLine(String intMaxLine) {
		this.intMaxLine = TypeParse.parseInt(intMaxLine);
	}

	public Integer getIntMaxLine() {
		return intMaxLine;
	}

	public void setStrRptPath(String strRptPath) {
		this.strRptPath = strRptPath;
	}

	public String getStrRptPath() {
		return strRptPath;
	}

	public void setIntRptUnit(String intRptUnit) {
		this.intRptUnit =TypeParse.parseInt(intRptUnit) ;
	}

	public Integer getIntRptUnit() {
		return intRptUnit;
	}

	public void setIntPrecise(String intPrecise) {
		this.intPrecise =TypeParse.parseInt(intPrecise) ;
	}

	public Integer getIntPrecise() {
		return intPrecise;
	}

	public void setStrCheckInst(String strCheckInst) {
		this.strCheckInst = strCheckInst;
	}

	public String getStrCheckInst() {
		return strCheckInst;
	}

	public void setStrCalcInst(String strCalcInst) {
		this.strCalcInst = strCalcInst;
	}

	public String getStrCalcInst() {
		return strCalcInst;
	}

	public void setStrMergeInst(String strMergeInst) {
		this.strMergeInst = strMergeInst;
	}

	public String getStrMergeInst() {
		return strMergeInst;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getStrFreq() {
		return strFreq;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
