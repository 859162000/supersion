package report.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import coresystem.dto.InstInfo;
import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;


@IEntity(description="指标分析条件",navigationName="指标分析条件")
public class RptChartCond implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3138959792154422658L;
		@IColumn(description="开始日期")
	    private Date startDate;
		public Date getStartDate()
		{
			return this.startDate;
		}
		public void setStartDate(String startDate)
		{
			this.startDate=TypeParse.parseDate(startDate);
		}
		
		
		
		@IColumn(description="结束日期")
		private Date endDate;
		public Date getEndDate()
		{
			return this.endDate;
		}
		public void setEndDate(String endDate)
		{
			this.endDate=TypeParse.parseDate(endDate);
		}
		
		//@IColumn(isIdListField=true,description="分析指标",target="")
		private String[] itemIdList;
		public String[] getItemIdList()
		{
			return itemIdList;
		}
		
		public void setItemIdList(String[] itemIdList)
		{
			this.itemIdList=itemIdList;
		}
		
		@IColumn(description="指标属性")
		private ItemProperty itemProperty;
		public ItemProperty getItemProperty()
		{   
			return this.itemProperty;
		}
		public void setItemProperty(ItemProperty itemProperty)
		{
			this.itemProperty=itemProperty;
		}
		@IColumn(description="币种")
		private CurrencyType currencyType;
		public void setCurrencyType(CurrencyType currencyType) {
			this.currencyType = currencyType;
		}

		public CurrencyType getCurrencyType() {
			return currencyType;
		}
		@IColumn(description="机构")
		private InstInfo instInfo;
		public InstInfo getInstInfo()
		{   
			return this.instInfo;
		}
		public void setInstInfo(InstInfo instInfo)
		{
			this.instInfo=instInfo;
		}
		
		
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
		@IColumn(tagMethodName="getOptDimensionsType",description="分析维度")
		private String dimensions;
		
		public String getDimensions()
		{
			return this.dimensions;
		}
		public void setDimensions(String dimensions)
		{
			this.dimensions=dimensions;
		}
		public static Map<String,String> getOptDimensionsType(){
			return ShowContext.getInstance().getShowEntityMap().get("optDimensionType");
		}
				
		
		@IColumn(tagMethodName="getStrChartTypeTag", description="展示图形")
		private String strChartType;
		public void setStrChartType(String strChartType) {
			this.strChartType = strChartType;
		}

		public String getStrChartType() {
			return strChartType;
		}
		
		public static Map<String,String> getStrChartTypeTag(){
			return ShowContext.getInstance().getShowEntityMap().get("charType");
		}
		@IColumn(description="主题")
		private Suit suit;
		public Suit getSuit() {
			return suit;
		}
		public void setSuit(Suit suit) {
			this.suit = suit;
		}
		
		private List<ItemInfo> itemInfos;
		public List<ItemInfo> getItemInfos() {
			return itemInfos;
		}
		public void setItemInfos(List<ItemInfo> itemInfos) {
			this.itemInfos = itemInfos;
		}
}
