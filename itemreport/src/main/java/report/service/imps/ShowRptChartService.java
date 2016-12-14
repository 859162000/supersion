package report.service.imps;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import report.dto.ChartType;
import report.dto.ECharts;
import report.dto.ItemData;
import report.dto.RptChartCond;
import report.helper.DoubleUtil;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Radar;
import com.github.abel533.echarts.series.gauge.Detail;

import coresystem.dto.InstInfo;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;
import framework.show.ShowContext;

@SuppressWarnings("unchecked")
public class ShowRptChartService extends BaseService {
	
	@Override
	public void initSuccessResult() throws Exception {
		RptChartCond rcd=(RptChartCond)RequestManager.getTOject();
		if(rcd!=null)
		{
			List<ItemData> lstItemData=getData(rcd);
			Map<String,Map<String,Double>> sumData=sumData(rcd,lstItemData);
			markChartData(rcd,sumData);
		}
	}
	
	private Map<String,Map<String,Double>> sumData(RptChartCond rc,List<ItemData> itemDatas) throws ParseException
	{
		Map<String,Map<String,Double>> sumData=new HashMap<String,Map<String,Double>>();
		String dimsKey=null;
		for(ItemData it:itemDatas)
		{
		   Map<String,Double> dimsData=sumData.get(it.getItemInfo().getStrItemCode());
		   
		   Double curVal=DoubleUtil.parse(it.getStrValue());
		   if(curVal.compareTo(Double.MAX_VALUE)==0)
		   {
			   curVal=0.0;
		   }
		   if(dimsData==null)
		   {
			   Map<String,Double> dims=new HashMap<String,Double>();
			   sumData.put(it.getItemInfo().getStrItemCode(), dims);
			   dimsData=dims;
		   }
			if ("01".equals(rc.getDimensions())) {
				dimsKey = it.getInstInfo().getStrInstCode();
			} else if ("02".equals(rc.getDimensions())) {
				dimsKey = it.getStrExtendDimension1();
			} else if ("04".equals(rc.getDimensions())) {
				dimsKey = it.getStrExtendDimension2();
			} else {
				dimsKey = TypeParse.parseString(it.getDtDate(), "yyyy-MM-dd");
			}
			Double dblDim = dimsData.get(dimsKey);
			if (dblDim == null) {
				dblDim = 0.0;
			}
			dimsData.put(dimsKey, dblDim + curVal);
		}
		return sumData;	
	}
	
	private Map<String,String> getCategory(RptChartCond rc,Map<String,Map<String,Double>> sumData) throws Exception
	{
		Map<String,String> returnData=new LinkedHashMap<String,String>();
		if("01".equals(rc.getDimensions()))
		{
			
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria= DetachedCriteria.forClass(InstInfo.class);
			detachedCriteria.addOrder(Order.asc("strInstCode"));
			List<InstInfo> objectList = (List<InstInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			for(InstInfo ii:objectList)
			{
				returnData.put(ii.getStrInstCode(), ii.getStrInstName());
			}
	    	
		}
		else if("02".equals(rc.getDimensions()))
		{	
			return ShowContext.getInstance().getShowEntityMap().get("extendProperty1");
		}
		else if("04".equals(rc.getDimensions()))
		{
			return ShowContext.getInstance().getShowEntityMap().get("extendProperty2");
		}
		else
		{
			List<String> lstDate=new ArrayList<String>();
			for(Map<String,Double> vals:sumData.values())
			{
				lstDate.addAll(vals.keySet());
				
			}
			Collections.sort(lstDate);
			for(String s:lstDate)
			{
				returnData.put(s,s);
			}
		}
		return returnData;
		
	}
	
	private void markChartData(RptChartCond rc,Map<String,Map<String,Double>> sumData) throws Exception
	{
		List<String> legends=new ArrayList<String>();
		for(String itemCode:rc.getItemIdList())
		{
			legends.add(itemCode);
		}
		Map<String,String> category=getCategory(rc,sumData);
		
		ECharts ec=null;
		//折线
		if(ChartType.LINE.equals(rc.getStrChartType()))
		{
			ec=makeLineChart(rc,sumData,legends,category);
		}
		//柱状
		else if(ChartType.BAR.equals(rc.getStrChartType()))
		{
			ec=makeBarChart(rc,sumData,legends,category);
		}
		//饼图
		else if(ChartType.PIE.equals(rc.getStrChartType()))
		{
			ec=makePieChart(rc,sumData,legends,category);
		}
		//雷达
		else if(ChartType.RADAR.equals(rc.getStrChartType()))
		{
			ec=makeRadarChart(rc,sumData,legends,category);
		}
		//仪表盘
		else if(ChartType.GAUGE.equals(rc.getStrChartType()))
		{
			ec=makeGaugeChart(rc,sumData,legends,category);
		}
	    this.setServiceResult(ec);
	}
	

	//柱状图
    private ECharts makeBarChart(RptChartCond rc,Map<String,Map<String,Double>> sumData,List<String> legends,Map<String,String> category)
	{
		Option option = new Option();
		option.tooltip().trigger(Trigger.axis);
		option.legend().data().addAll(legends);
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage).padding(20);
		option.calculable(true);
		CategoryAxis a = new CategoryAxis();
		a.data().addAll(category.values());
        
        option.xAxis(a);
        option.yAxis(new ValueAxis().scale(true));
        
        for(String str:legends)
        {
        	Bar bar = new Bar(str);
        	
        	bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
            bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
	        List<Object> datas=new ArrayList<Object>();
	        
	        for(String cName:category.keySet())
	        {
	        	Map<String,Double> seriaData=sumData.get(str);
	        	Object data="-";
	        	if(seriaData!=null)
	        	{
	        		data=seriaData.get(cName);
	        		if(data==null)
	        		{
	        			data="-";
	        		}
	        	}
	        	datas.add(data);
	        }
	        bar.data().addAll(datas);
	        option.series().add(bar);
        }
        String formatJson=GsonUtil.format(option);
        
        ECharts ec=new ECharts(ChartType.BAR ,formatJson,new String[]{ChartType.LINE});
        return ec;
	}
    
    
    //仪表盘图
    private ECharts makeGaugeChart(RptChartCond rc,Map<String,Map<String,Double>> sumData,List<String> legends,Map<String,String> category)
	{
		Option option = new Option();
		option.tooltip().formatter("{a} <br/>{b} : {c}%");
	    option.toolbox().show(true).feature(Tool.mark, Tool.restore, Tool.saveAsImage);
	   
	 	 List<Gauge> gaugeList=new ArrayList<Gauge>();
	     int gaugeCnt=0;
	     for(String str:legends)
	     {   
	    	Gauge g=new Gauge(str).detail(new Detail().formatter("{value}%"));
	    	
	    	gaugeList.add(g);
	    	int x=gaugeCnt%4*25+15;
	     	int y=gaugeCnt/4*50+30;
	     	g.center(Integer.toString(x)+"%", Integer.toString(y)+"%");
	     	g.radius("50%");
	    	 boolean hasVal=false;
	  		
	  		Double legendsData=0.0;
	        for(String cName:category.keySet())
	        {
	        	Map<String,Double> seriaData=sumData.get(str);
	        	
	        	if(seriaData!=null)
	        	{
	        		Double data=seriaData.get(cName);
	        		if(data!=null)
	        		{
	        			legendsData=legendsData+data;
	        			hasVal=true;
	        		}	
	        	}
	        	
	        }
	        if(hasVal)
	        {
	        	g.data(new Data(str, legendsData));
	        }
	        else
	        {
	        	g.data(new Data(str, "-"));
	        }
	        gaugeCnt++;
	     }
     	
        option.series().addAll(gaugeList);
        String formatJson=GsonUtil.format(option);
        
        ECharts ec=new ECharts(ChartType.GAUGE ,formatJson);
        return ec;
	}
    
    //雷达图
    private ECharts makeRadarChart(RptChartCond rc,Map<String,Map<String,Double>> sumData,List<String> legends,Map<String,String> category)
	{
		Option option = new Option();
        option.tooltip().trigger(Trigger.axis);
        
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage).padding(20);
        option.calculable(true);
        if(StringUtils.isBlank(rc.getDimensions()))
        {
        	option.legend().data("指标分析");
         	Polar polar = new Polar();
         	List<Data> legendData=new ArrayList<Data>();
             for(String cName:legends)
             {
            	 legendData.add(new Data().text(cName));
             }
             polar.indicator().addAll(legendData);
             option.polar(polar);
         	
         	
             Radar radar = new Radar();
             
             Data radarData=new Data("指标");
             List<Object> radarDataVal=new ArrayList<Object>(); 
             for(String str:legends)
             {   
            	 boolean hasVal=false;
          		
          		Double legendsData=0.0;
      	        for(String cName:category.keySet())
      	        {
      	        	Map<String,Double> seriaData=sumData.get(str);
      	        	
      	        	if(seriaData!=null)
      	        	{
      	        		Double data=seriaData.get(cName);
      	        		if(data!=null)
      	        		{
      	        			legendsData=legendsData+data;
      	        			hasVal=true;
      	        		}
      	        	}
      	        }
      	        if(hasVal)
      	        {
      	        	radarDataVal.add(legendsData);	
      	        }
      	        else
      	        {
      	        	radarDataVal.add("-");
      	        }
         		radarData.value(radarDataVal.toArray());
             }
         	radar.data(radarData);
            option.series().add(radar);
        }
        else
        {
        	option.legend().data().addAll(legends);
         	Polar polar = new Polar();
         	List<Data> categoryData=new ArrayList<Data>();
             for(String cName:category.keySet())
             {
             	categoryData.add(new Data().text(cName));
             }
             polar.indicator().addAll(categoryData);
             option.polar(polar);
         	
             Radar radar = new Radar();
             List<Data> radarDataList=new ArrayList<Data>();
             
             for(String str:legends)
             {   
         		Data radarData=new Data(str);
         		List<Object> radarDataVal=new ArrayList<Object>(); 
         		
         		for(String cName:category.keySet())
     	        {
     	        	Map<String,Double> seriaData=sumData.get(str);
     	        	
     	        	if(seriaData!=null)
     	        	{
     	        		Double data=seriaData.get(cName);
     	        		if(data!=null)
     	        		{
     	        			radarDataVal.add(data);
     	        		}
     	        		else
     	        		{
     	        			radarDataVal.add("-");
     	        		}
     	        	}
     	        	
     	        }
         		radarData.value(radarDataVal.toArray());
         		radarDataList.add(radarData);
     	       
             }
         	radar.data().addAll(radarDataList);
             option.series().add(radar);
        }
       
        String formatJson=GsonUtil.format(option);
        ECharts ec=new ECharts(ChartType.RADAR ,formatJson);
        return ec;
	}
    
    
    
    
    
	//饼图
	private ECharts makePieChart(RptChartCond rc,Map<String,Map<String,Double>> sumData,List<String> legends,Map<String,String> category)
	{
		Option option = new Option();
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().data().addAll(legends);
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage).padding(20);
        option.calculable(true);
        
        if(StringUtils.isBlank(rc.getDimensions()))
        {
        	Pie pie = new Pie("指标占比");
        	
	        List<PieData> datas=new ArrayList<PieData>();
	        
        	for(String str:legends)
            {   boolean hasVal=false;
        		PieData pieData=new PieData(str,"-");
        		Double legendsData=0.0;
    	        for(String cName:category.keySet())
    	        {
    	        	Map<String,Double> seriaData=sumData.get(str);
    	        	if(seriaData!=null)
    	        	{
    	        		Double data=seriaData.get(cName);
    	        		if(data!=null)
    	        		{
    	        			legendsData=legendsData+data;
    	        			hasVal=true;
    	        		}
    	        	}
    	        }
    	        if(hasVal)
    	        {
    	        	pieData.setValue(legendsData);	
    	        }
    	        datas.add(pieData);
            }
        	pie.data().addAll(datas);
	        option.series().add(pie);
            
        }
        else
        {
        	int pieCnt=0;
        	for(String str:legends)
            {
            	Pie pie = new Pie(str);
            	pie.radius(55);
            	int x=pieCnt%5*20+15;
            	int y=pieCnt/5*40+30;
            	pie.center(Integer.toString(x)+"%", Integer.toString(y)+"%");
    	        List<Data> datas=new ArrayList<Data>();
    	        pie.itemStyle().normal().label().formatter("function(params){return '分析维度:'+params.name+'\r\n'+'指标:'+params.seriesName;}");
    	        for(String cName:category.keySet())
    	        {
    	        	Map<String,Double> seriaData=sumData.get(str);
    	        	Data pieData=new Data(cName,"-");
    	        	if(seriaData!=null)
    	        	{
    	        		Double data=seriaData.get(cName);
    	        		if(data!=null)
    	        		{
    	        			pieData.setValue(data);
    	        		}
    	        	}
    	        	datas.add(pieData);
    	        }
    	        pie.data().addAll(datas);
    	        option.series().add(pie);
    	        pieCnt++;
            }
            
        }
        
        String formatJson=GsonUtil.format(option);
        
        ECharts ec=new ECharts(ChartType.PIE ,formatJson);
        return ec;
	}
	
	//折线图
	private ECharts makeLineChart(RptChartCond rc,Map<String,Map<String,Double>> sumData,List<String> legends,Map<String,String> category)
	{
		Option option = new Option();
        option.tooltip().trigger(Trigger.axis);
        option.legend().data().addAll(legends);
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled), Tool.restore, Tool.saveAsImage).padding(20);
        option.calculable(true);
        CategoryAxis a=new CategoryAxis().boundaryGap(true);
        a.data().addAll(category.values());
        option.xAxis(a);
        option.yAxis(new ValueAxis().scale(true));
        
        for(String str:legends)
        {
        	Line l1 = new Line(str);
	        l1.smooth(true).itemStyle().normal().areaStyle().typeDefault();
	        List<Object> datas=new ArrayList<Object>();
	        for(String cName:category.keySet())
	        {
	        	Map<String,Double> seriaData=sumData.get(str);
	        	Object data="-";
	        	if(seriaData!=null)
	        	{
	        		data=seriaData.get(cName);
	        		if(data==null)
	        		{
	        			data="-";
	        		}
	        	}
	        	datas.add(data);
	        }
	        l1.data().addAll(datas);
	        option.series().add(l1);
        }
        String formatJson=GsonUtil.format(option);
        
        ECharts ec=new ECharts(ChartType.LINE,formatJson,new String[]{ChartType.BAR});
        return ec;
	}
	
	
	private List<ItemData> getData(RptChartCond rc) throws Exception
	{
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria= DetachedCriteria.forClass(ItemData.class);
		detachedCriteria.add(Restrictions.between("dtDate", rc.getStartDate(), rc.getEndDate()));
		detachedCriteria.add(Restrictions.in("itemInfo.strItemCode", rc.getItemIdList()));
		detachedCriteria.add(Restrictions.eq("itemProperty", rc.getItemProperty()));
		detachedCriteria.add(Restrictions.eq("currencyType", rc.getCurrencyType()));
		if(rc.getInstInfo()!=null&& !StringUtils.isBlank(rc.getInstInfo().getStrInstCode()))
		{
			detachedCriteria.add(Restrictions.eq("instInfo",rc.getInstInfo()));
		}
		if(!StringUtils.isBlank(rc.getStrFreq()))
		{
			detachedCriteria.add(Restrictions.eq("strFreq",rc.getStrFreq()));
		}
    	List<ItemData> objectList = (List<ItemData>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
    	return objectList;
	}

}
