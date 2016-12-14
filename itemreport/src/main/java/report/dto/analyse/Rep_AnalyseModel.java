package report.dto.analyse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.interfaces.ITab;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：AnalyseModel</P>
 * *********************************<br>
 * <P>类描述：分析模型</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午10:28:17<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午10:28:17<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="Rep_AnalyseModel")
@IEntity(navigationName="分析-模型",description="分析-模型")
public class Rep_AnalyseModel implements Serializable {
	
	/**  **/
	private static final long serialVersionUID = -1509747607879879518L;

	/** uuid **/
	@Id
	@Column(name="uuid",length=32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String uuid;
	
	/** 模型名称 **/
	@IColumn(description="模型名称 ",isKeyName=true,isSpecialCharCheck=true)
	@Column(name="modelName",length=200,nullable=false)
	private String modelName;
	
	/** 分析类型  0普通分析  1预警分析 **/
	@IColumn(description="分析类型 ",tagMethodName="getOptModelType")
	@Column(name="modelType",length=2,nullable=false)
	private String modelType;
	
	public static Map<String,String> getOptModelType(){
		return ShowContext.getInstance().getShowEntityMap().get("modelType");
	}
	
	/** 分析维度类型 **/
	@IColumn(description="分析维度1",tagMethodName="getOptDimensionsType")
	@Column(name="dimensionType1",length=20,nullable=false)
	private String dimensionType1;
	
	@Transient
	private Map<String,String> key1Map = new HashMap<String, String>();
	
	@IColumn(description="分析维度2",tagMethodName="getOptDimensionsType")
	@Column(name="dimensionType2",length=20)
	private String dimensionType2;
	
	@Transient
	private Map<String,String> key2Map = new HashMap<String, String>();
	
	public static Map<String,String> getOptDimensionsType(){
		return ShowContext.getInstance().getShowEntityMap().get("dimensionType");
	}
	
	/** 时间函数 **/
	@IColumn(description="时间函数",tagMethodName="getOptTimeFunc")
	@Column(name="timeFunc",length=20)
	private String timeFunc;
	public static Map<String,String> getOptTimeFunc(){
		return ShowContext.getInstance().getShowEntityMap().get("timeFunc");
	}
	
	/** >= 数据开始时间 **/
	@IColumn(description="数据开始时间")
	@Column(name="startDate",length=10)
	private String startDate;
	
	/** <= 数据结束时间  **/
	@IColumn(description="数据结束时间")
	@Column(name="endDate",length=10)
	private String endDate;
	
	/** 频度 **/
	@IColumn(tagMethodName="getStrFreqTag", description="频度")
	@Column(name="freq",length=20)
	private String freq;
	public static Map<String,String> getStrFreqTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	
	@IColumn(tagMethodName="getStrChartTypeTag", description="图形")
	@Column(name="strChartType",length=20)
	private String strChartType;
	public static Map<String,String> getStrChartTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("charType");
	}
	
	/** 创建时间 **/
	@IColumn(description="创建时间",isSingleTagHidden=true,isSystemDate=true)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",length=30)
	private Date createTime;
	
	/** 创建人 **/
	@IColumn(description="创建人",isSingleTagHidden=true,isLoginTag=true)
	@Column(name="createUser",length=50)
	private String createUser;
	
	@Transient
	private List<String> dtDates = new ArrayList<String>();
	
	@ITab(name="分析-指标")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
	private Set<Rep_AnalyseModel_Items> items = new HashSet<Rep_AnalyseModel_Items>();
	
	@Transient
	private List<String> itemIds = new ArrayList<String>();
	
	@ITab(name="分析-机构")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
	private Set<Rep_AnalyseModel_Orgs> orgs = new HashSet<Rep_AnalyseModel_Orgs>();
	
	@Transient
	private List<String> orgIds = new ArrayList<String>();
	
	@ITab(name="分析-币种")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
	private Set<Rep_AnalyseModel_Currs> currs = new HashSet<Rep_AnalyseModel_Currs>();
	
	@Transient
	private List<String> currIds = new ArrayList<String>();
	
	@ITab(name="分析-指标属性")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
	private Set<Rep_AnalyseModel_ItemPros> itemPros = new HashSet<Rep_AnalyseModel_ItemPros>();
	
	@Transient
	private List<String> itemProIds = new ArrayList<String>();
	
	@ITab(name="分析-扩展维度1")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
	private Set<Rep_AnalyseModel_Dimension1> dimension1s = new HashSet<Rep_AnalyseModel_Dimension1>();
	
	@Transient
	private List<String> dimension1Types = new ArrayList<String>();
	
	@ITab(name="分析-扩展维度2")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
	private Set<Rep_AnalyseModel_Dimension2> dimension2s = new HashSet<Rep_AnalyseModel_Dimension2>();
	
	@Transient
	private List<String> dimension2Types = new ArrayList<String>();
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getDimensionType1() {
		return dimensionType1;
	}

	public void setDimensionType1(String dimensionType1) {
		this.dimensionType1 = dimensionType1;
	}

	public String getDimensionType2() {
		return dimensionType2;
	}

	public void setDimensionType2(String dimensionType2) {
		this.dimensionType2 = dimensionType2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = TypeParse.parseDate(createTime);
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Set<Rep_AnalyseModel_Items> getItems() {
		return items;
	}

	public void setItems(Set<Rep_AnalyseModel_Items> items) {
		this.items = items;
	}

	public Set<Rep_AnalyseModel_Orgs> getOrgs() {
		return orgs;
	}

	public void setOrgs(Set<Rep_AnalyseModel_Orgs> orgs) {
		this.orgs = orgs;
	}

	public Set<Rep_AnalyseModel_Currs> getCurrs() {
		return currs;
	}

	public void setCurrs(Set<Rep_AnalyseModel_Currs> currs) {
		this.currs = currs;
	}

	public Set<Rep_AnalyseModel_ItemPros> getItemPros() {
		return itemPros;
	}

	public void setItemPros(Set<Rep_AnalyseModel_ItemPros> itemPros) {
		this.itemPros = itemPros;
	}

	public Set<Rep_AnalyseModel_Dimension1> getDimension1s() {
		return dimension1s;
	}

	public void setDimension1s(Set<Rep_AnalyseModel_Dimension1> dimension1s) {
		this.dimension1s = dimension1s;
	}

	public Set<Rep_AnalyseModel_Dimension2> getDimension2s() {
		return dimension2s;
	}

	public void setDimension2s(Set<Rep_AnalyseModel_Dimension2> dimension2s) {
		this.dimension2s = dimension2s;
	}

	public List<String> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}

	public List<String> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}

	public List<String> getCurrIds() {
		return currIds;
	}

	public void setCurrIds(List<String> currIds) {
		this.currIds = currIds;
	}

	public List<String> getItemProIds() {
		return itemProIds;
	}

	public void setItemProIds(List<String> itemProIds) {
		this.itemProIds = itemProIds;
	}
	
	public List<String> getDimension1Types() {
		return dimension1Types;
	}

	public void setDimension1Types(List<String> dimension1Types) {
		this.dimension1Types = dimension1Types;
	}

	public List<String> getDimension2Types() {
		return dimension2Types;
	}

	public void setDimension2Types(List<String> dimension2Types) {
		this.dimension2Types = dimension2Types;
	}

	public boolean isMuiltSelect(String d){
		return d.equals(dimensionType1)||d.equals(dimensionType2);
	}

	public List<String> getDtDates() {
		return dtDates;
	}

	public void setDtDates(List<String> dtDates) {
		this.dtDates = dtDates;
	}

	public String getStrChartType() {
		return strChartType;
	}

	public void setStrChartType(String strChartType) {
		this.strChartType = strChartType;
	}

	public Map<String, String> getKey1Map() {
		return key1Map;
	}

	public void setKey1Map(Map<String, String> key1Map) {
		this.key1Map = key1Map;
	}

	public Map<String, String> getKey2Map() {
		return key2Map;
	}

	public void setKey2Map(Map<String, String> key2Map) {
		this.key2Map = key2Map;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getTimeFunc() {
		return timeFunc;
	}

	public void setTimeFunc(String timeFunc) {
		this.timeFunc = timeFunc;
	}
	
}
