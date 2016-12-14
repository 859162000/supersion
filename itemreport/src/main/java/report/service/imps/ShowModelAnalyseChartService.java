package report.service.imps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import report.dto.ChartType;
import report.dto.ECharts;
import report.dto.ItemData;
import report.dto.analyse.Enum_AnalyseDimension;
import report.dto.analyse.Rep_AnalyseModel;
import report.dto.analyse.Rep_ItemDataVO;
import report.dto.analyse.Rep_ItemWarning;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Radar;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ShowModelAnalyseChartService</P>
 * *********************************<br>
 * <P>类描述：模型图形分析</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-12 下午1:27:55<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-12 下午1:27:55<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class ShowModelAnalyseChartService extends RepModelAnalyseService {
	// [绿, '#00ff83'],  [黄, '#ffca00'], [红, '#d93a3a']
	private static String GREEN = "#00ff83";
	private static String YELLOW = "#ffca00";
	private static String RED = "#d93a3a";
	
	//预警区间指标值排序用 实现比较接口
	class Cri implements Comparable<Cri>{
		String name;
		Double value;
		public Cri(){
			
		}
		public Cri(String name,Double value){
			this.name = name;
			this.value = value;
		}
		@Override
		public int compareTo(Cri o) {
			if(this.value>o.value){
				return 1;
			}
			if(this.value<o.value)
				return -1;
			if(this.value==o.value){
				return this.name.compareTo(o.name);
			}
			return 0;
		}
	}

	@Override
	public void initSuccessResult() throws Exception {
		Rep_AnalyseModel model = (Rep_AnalyseModel) RequestManager.getTOject();
		if (model != null) {
			List<ItemData> itemDatas = getData(model);
			Map<String, Rep_ItemDataVO> dataMap = buidData(model, itemDatas);
			markChartData(model, dataMap);
		}
	}

	/**
	 * <p>方法描述: 根据分析维度构建数据</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param model
	 * @param itemDatas
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-12 上午11:17:01</p>
	 */
	private Map<String, Rep_ItemDataVO> buidData(Rep_AnalyseModel model, List<ItemData> itemDatas) throws Exception {
		Map<String, Rep_ItemDataVO> map = new HashMap<String, Rep_ItemDataVO>();
		Set<String> dateSet = new HashSet<String>();
		Enum_AnalyseDimension key1 = Enum_AnalyseDimension.getAnalyseDimension(model.getDimensionType1());
		Enum_AnalyseDimension key2 = Enum_AnalyseDimension.getAnalyseDimension(model.getDimensionType2());
		for (ItemData data : itemDatas) {
			Rep_ItemDataVO dataVO = new Rep_ItemDataVO(data);
			dateSet.add(dataVO.getDtDate());
			//只有当指标为空白或者 和频度相同时才 放入 cacheMap中
			if(StringUtils.isBlank(data.getStrFreq())||data.getStrFreq().equals(model.getFreq())){
				map.put(ReflectOperation.getFieldValue(dataVO, key1.name()) + "_" + ReflectOperation.getFieldValue(dataVO, key2.name()), dataVO);
			}
		}
		List<String> dateList = new ArrayList<String>(dateSet);
		Collections.sort(dateList);
		model.getDtDates().addAll(dateList);
		return map;
	}

	/**
	 * <p>方法描述:数据图形 生成</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param model
	 * @param dataMap
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-5 上午10:21:53</p>
	 */
	private void markChartData(Rep_AnalyseModel model, Map<String, Rep_ItemDataVO> dataMap) throws Exception {
		//X 复数坐标
		List<String> legends_x = new ArrayList<String>();
		if (("01").equals(model.getDimensionType1())) {
			if (model.getDtDates().isEmpty()) {
				model.getDtDates().add(model.getStartDate());
				model.getDtDates().add(model.getEndDate());
			}
			for (String date : model.getDtDates()) {
				model.getKey1Map().put(date, date);
			}
			legends_x.addAll(model.getDtDates());
		}
		if (("02").equals(model.getDimensionType1())) {
			legends_x.addAll(model.getItemIds());
		}
		if (("03").equals(model.getDimensionType1())) {
			legends_x.addAll(model.getOrgIds());
		}
		if (("04").equals(model.getDimensionType1())) {
			legends_x.addAll(model.getCurrIds());
		}
		if (("05").equals(model.getDimensionType1())) {
			legends_x.addAll(model.getItemProIds());
		}
		if (("06").equals(model.getDimensionType1())) {
			legends_x.addAll(model.getDimension1Types());
		}
		if (("07").equals(model.getDimensionType1())) {
			legends_x.addAll(model.getDimension2Types());
		}
		//X_Z 复数坐标
		List<String> legends_z = new ArrayList<String>();
		if (("01").equals(model.getDimensionType2())) {
			if (model.getDtDates().isEmpty()) {
				model.getDtDates().add(model.getStartDate());
				model.getDtDates().add(model.getEndDate());
			}
			for (String date : model.getDtDates()) {
				model.getKey2Map().put(date, date);
			}
			legends_z.addAll(model.getDtDates());
		}
		if (("02").equals(model.getDimensionType2())) {
			legends_z.addAll(model.getItemIds());
		}
		if (("03").equals(model.getDimensionType2())) {
			legends_z.addAll(model.getOrgIds());
		}
		if (("04").equals(model.getDimensionType2())) {
			legends_z.addAll(model.getCurrIds());
		}
		if (("05").equals(model.getDimensionType2())) {
			legends_z.addAll(model.getItemProIds());
		}
		if (("06").equals(model.getDimensionType2())) {
			legends_z.addAll(model.getDimension1Types());
		}
		if (("07").equals(model.getDimensionType2())) {
			legends_z.addAll(model.getDimension2Types());
		}

		ECharts ec = null;
		if (StringUtils.isBlank(model.getStrChartType())) {
			model.setStrChartType(ChartType.LINE);
		}
		//折线
		if (ChartType.LINE.equals(model.getStrChartType())) {
			ec = makeLineChart(model, dataMap, legends_x, legends_z);
		}
		//柱状
		else if (ChartType.BAR.equals(model.getStrChartType())) {
			ec = makeBarChart(model, dataMap, legends_x, legends_z);
		}

		//饼图
		else if (ChartType.PIE.equals(model.getStrChartType())) {
			ec = makePieChart(model, dataMap, legends_x, legends_z);
		}
		//雷达
		else if (ChartType.RADAR.equals(model.getStrChartType())) {
			ec = makeRadarChart(model, dataMap, legends_x, legends_z);
		}
		//仪表盘
		else if (ChartType.GAUGE.equals(model.getStrChartType())) {
			ec = makeGaugeChart(model, dataMap, legends_x, legends_z);
		}
		this.setServiceResult(ec);
	}

	private List<String> getkeyNames(List<String> legends, Map<String, String> keyMap) {
		List<String> list = new ArrayList<String>();
		for (String legend : legends) {
			String keyName = keyMap.get(legend);
			list.add(StringUtils.isBlank(keyName) ? legend : keyName);
		}
		return list;
	}

	private String getkeyName(String legend, Map<String, String> keyMap) {
		String keyName = keyMap.get(legend);
		return StringUtils.isBlank(keyName) ? legend : keyName;
	}

	//折线图
	private ECharts makeLineChart(Rep_AnalyseModel model, Map<String, Rep_ItemDataVO> dataMap, List<String> legends_x, List<String> legends_z) {
		Option option = new Option();
		option.tooltip().trigger(Trigger.axis);
		option.legend().data().addAll(getkeyNames(legends_z, model.getKey2Map()));
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.stack, Magic.tiled), Tool.restore, Tool.saveAsImage).padding(20);
		option.calculable(true);
		CategoryAxis _x = new CategoryAxis();
		_x.data().addAll(getkeyNames(legends_x, model.getKey1Map()));
		option.xAxis(_x);
		option.yAxis(new ValueAxis().scale(true));

		if (legends_z.isEmpty()) {
			Line line = new Line("");
			line.smooth(true).itemStyle().normal().areaStyle().typeDefault();
			List<Object> datas = new ArrayList<Object>();
			for (String x : legends_x) {
				Rep_ItemDataVO vo = dataMap.get(x + "_");
				String data = "-";
				if (vo != null) {
					data = vo.getStrValue();
					if (StringUtils.isBlank(data)) {
						data = "-";
					}
				}
				datas.add(data);
			}
			line.data().addAll(datas);
			option.series().add(line);
		}
		for (String z : legends_z) {
			Line line = new Line(getkeyName(z, model.getKey2Map()));
			line.smooth(true).itemStyle().normal().areaStyle().typeDefault();
			List<Object> datas = new ArrayList<Object>();
			for (String x : legends_x) {
				Rep_ItemDataVO vo = dataMap.get(x + "_" + z);
				String data = "-";
				if (vo != null) {
					data = vo.getStrValue();
					if (StringUtils.isBlank(data)) {
						data = "-";
					}
				}
				datas.add(data);
			}
			line.data().addAll(datas);
			option.series().add(line);
		}
		String formatJson = GsonUtil.format(option);
		ECharts ec = new ECharts(ChartType.LINE, formatJson, new String[] { ChartType.BAR });
		return ec;
	}

	//柱状图
	private ECharts makeBarChart(Rep_AnalyseModel model, Map<String, Rep_ItemDataVO> dataMap, List<String> legends_x, List<String> legends_z) {
		Option option = new Option();
		option.tooltip().trigger(Trigger.axis);
		option.legend().data().addAll(getkeyNames(legends_z, model.getKey2Map()));
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.stack, Magic.tiled), Tool.restore, Tool.saveAsImage).padding(20);
		option.calculable(true);
		CategoryAxis _x = new CategoryAxis();
		_x.data().addAll(getkeyNames(legends_x, model.getKey1Map()));
		option.xAxis(_x);
		option.yAxis(new ValueAxis().scale(true));
		if (legends_z.isEmpty()) {
			Bar bar = new Bar("");
			List<Object> datas = new ArrayList<Object>();
			for (String x : legends_x) {
				Rep_ItemDataVO vo = dataMap.get(x + "_");
				String data = "-";
				if (vo != null) {
					data = vo.getStrValue();
					if (StringUtils.isBlank(data)) {
						data = "-";
					}
				}
				datas.add(data);
			}
			bar.data().addAll(datas);
			option.series().add(bar);
		}
		for (String z : legends_z) {
			Bar bar = new Bar(getkeyName(z, model.getKey2Map()));
			List<Object> datas = new ArrayList<Object>();
			for (String x : legends_x) {
				Rep_ItemDataVO vo = dataMap.get(x + "_" + z);
				String data = "-";
				if (vo != null) {
					data = vo.getStrValue();
					if (StringUtils.isBlank(data)) {
						data = "-";
					}
				}
				datas.add(data);
			}
			bar.data().addAll(datas);
			option.series().add(bar);
		}
		String formatJson = GsonUtil.format(option);
		ECharts ec = new ECharts(ChartType.BAR, formatJson, new String[] { ChartType.LINE });
		return ec;
	}

	//雷达图
	private ECharts makeRadarChart(Rep_AnalyseModel model, Map<String, Rep_ItemDataVO> dataMap, List<String> legends_x, List<String> legends_z) {
		Option option = new Option();
		option.tooltip().trigger(Trigger.axis);
		option.legend().data().addAll(getkeyNames(legends_x, model.getKey1Map()));
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage).padding(20);
		option.calculable(true);

		Polar polar = new Polar();
		List<Data> categoryData = new ArrayList<Data>();
		for (String _z : legends_z) {
			categoryData.add(new Data().text(getkeyName(_z, model.getKey2Map())));
		}
		polar.indicator().addAll(categoryData);
		option.polar(polar);
		Radar radar = new Radar();
		List<Data> radarDataList = new ArrayList<Data>();

		if (legends_z.isEmpty()) {
			Data radarData = new Data();
			List<Object> radarDataVal = new ArrayList<Object>();
			for (String x : legends_x) {
				Rep_ItemDataVO vo = dataMap.get(x + "_");
				String data = "-";
				if (vo != null) {
					data = vo.getStrValue();
					if (StringUtils.isBlank(data)) {
						data = "-";
					}
				}
				radarDataVal.add(data);
			}
			radarData.value(radarDataVal.toArray());
			radarDataList.add(radarData);
		} else {
			for (String x : legends_x) {
				Data radarData = new Data(getkeyName(x, model.getKey1Map()));
				List<Object> radarDataVal = new ArrayList<Object>();
				for (String z : legends_z) {
					Rep_ItemDataVO vo = dataMap.get(x + "_" + z);
					String data = "-";
					if (vo != null) {
						data = vo.getStrValue();
						if (StringUtils.isBlank(data)) {
							data = "-";
						}
					}
					radarDataVal.add(data);
				}
				radarData.value(radarDataVal.toArray());
				radarDataList.add(radarData);
			}
		}

		radar.data().addAll(radarDataList);
		option.series().add(radar);

		String formatJson = GsonUtil.format(option);
		ECharts ec = new ECharts(ChartType.RADAR, formatJson);
		return ec;
	}

	//饼图
	private ECharts makePieChart(Rep_AnalyseModel model, Map<String, Rep_ItemDataVO> dataMap, List<String> legends_x, List<String> legends_z) {
		String height = "400px;";
		Option option = new Option();
		option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
		option.legend().data().addAll(getkeyNames(legends_z, model.getKey2Map()));
		option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage).padding(20);
		option.calculable(true);
		if (legends_z.isEmpty()) {
			Pie pie = new Pie("指标占比");
			List<PieData> datas = new ArrayList<PieData>();
			for (String _x : legends_x) {
				Rep_ItemDataVO vo = dataMap.get(_x + "_");
				PieData pieData = new PieData(getkeyName(_x, model.getKey1Map()), "-");
				String data = "-";
				if (vo != null) {
					data = vo.getStrValue();
					if (StringUtils.isBlank(data)) {
						data = "-";
					}
				}
				pieData.setValue(data);
				datas.add(pieData);
			}
			pie.data().addAll(datas);
			option.series().add(pie);

		} else {
			int num = (legends_x.size() - 1) / 5 + 1;
			if (num > 2) {
				height = num * 200 + "px;";
			}
			int pieCnt = 0;
			for (String _x : legends_x) {
				Pie pie = new Pie(getkeyName(_x, model.getKey1Map()));
				pie.radius(55);
				int x = pieCnt % 5 * 20 + 10;
				int y = (pieCnt / 5 + 1) * 150;
				pie.center(Integer.toString(x) + "%", y);
				List<Data> datas = new ArrayList<Data>();
				for (String _z : legends_z) {
					Rep_ItemDataVO vo = dataMap.get(_x + "_" + _z);
					Data pieData = new Data(getkeyName(_z, model.getKey2Map()), "-");
					String data = "-";
					if (vo != null) {
						data = vo.getStrValue();
						if (StringUtils.isBlank(data)) {
							data = "-";
						}
					}
					pieData.setValue(data);
					datas.add(pieData);
				}
				pie.data().addAll(datas);
				option.series().add(pie);
				pieCnt++;
			}

		}

		String formatJson = GsonUtil.format(option);
		ECharts ec = new ECharts(ChartType.PIE, formatJson);
		ec.setHeight(height);
		return ec;
	}

	//仪表盘图 [绿, '#00ff83'],  [黄, '#ffca00'], [红, '#d93a3a']
	private ECharts makeGaugeChart(Rep_AnalyseModel model, Map<String, Rep_ItemDataVO> dataMap, List<String> legends_x, List<String> legends_z) throws Exception {
		//预警指标
		Map<String, Rep_ItemWarning> wnMap = warningListToMap(getItemWarning(model));
		String height = "400px;";
		Option option = new Option();
		option.tooltip().formatter("{a} <br/>{b} : {c}%");
		option.toolbox().show(true).feature(Tool.mark, Tool.restore, Tool.saveAsImage);
		int min = 0;
		int max = 100;
		List<Gauge> gaugeList = new ArrayList<Gauge>();
		int gaugeCnt = 0;
		if (legends_z.isEmpty()) {
			for (String _x : legends_x) {
				Gauge g = new Gauge(getkeyName(_x, model.getKey1Map()));
				g.axisLine().lineStyle().width(12);
				g.min(min);
				g.max(max);
				g.axisLine().lineStyle().color(new Object[]{new Object[]{0.4, GREEN},new Object[]{0.8, YELLOW},new Object[]{1.0, RED}});
				int x = gaugeCnt % 4 * 25 + 15;
				int y = gaugeCnt / 4 * 50 + 30;
				g.center(Integer.toString(x) + "%", Integer.toString(y) + "%");
				g.radius("50%");
				Rep_ItemDataVO vo = dataMap.get(_x + "_");
				String data = "-";
				if (null != vo){
					data = vo.getStrValue();
					//如果有预警指标设置
					Rep_ItemWarning warn = wnMap.get(vo.getItemId());
					if(null != warn){
						GLineStyle gStyle = getGLineStyle(warn, data);
						g.axisLine().lineStyle().color(gStyle.colors);
						g.min(gStyle.min);
						g.max(gStyle.max);
					}
				}
				if (StringUtils.isBlank(data)) 
					data = "-";
				g.data(new Data(_x, data));
				gaugeList.add(g);
				gaugeCnt++;
			}

		} else {
			int num = (legends_x.size() * legends_z.size() - 1) / 4 + 1;
			if (num > 2) {
				height = (num + 1) * 150 + "px;";
			}
			for (String _x : legends_x) {
				for (String _z : legends_z) {
					Gauge g = new Gauge(getkeyName(_x, model.getKey1Map()));
					g.axisLine().lineStyle().width(12);
					g.min(min);
					g.max(max);
					g.axisLine().lineStyle().color(new Object[]{new Object[]{0.4, GREEN},new Object[]{0.8, YELLOW},new Object[]{1.0, RED}});
					gaugeList.add(g);
					g.radius(80);
					int x = gaugeCnt % 4 * 25 + 13;
					int y = (gaugeCnt / 4 + 1) * 150;
					g.center(Integer.toString(x) + "%", y);
					Rep_ItemDataVO vo = dataMap.get(_x + "_" + _z);
					String data = "-";
					if (null != vo){
						data = vo.getStrValue();
						//如果有预警指标设置
						Rep_ItemWarning warn = wnMap.get(vo.getItemId());
						if(null != warn){
							GLineStyle gStyle = getGLineStyle(warn, data);
							g.axisLine().lineStyle().color(gStyle.colors);
							g.min(gStyle.min);
							g.max(gStyle.max);
						}
					}
					if (StringUtils.isBlank(data)) 
						data = "-";
					g.data(new Data(getkeyName(_z, model.getKey2Map()), data));
					gaugeCnt++;
				}
			}
		}
		option.series().addAll(gaugeList);
		String formatJson = GsonUtil.format(option);
		ECharts ec = new ECharts(ChartType.GAUGE, formatJson);
		ec.setHeight(height);
		return ec;
	}
	
	public GLineStyle getGLineStyle(Rep_ItemWarning warn,String data){
		int min = 0;
		int max = 100;
		Object[] colors = null;
		if(warn != null ){
			Object[] green = new Object[]{0.4, GREEN};
			Object[] yellow = new Object[]{0.8, YELLOW};
			Object[] red = new Object[]{1.0, RED};
			//正向指标
			if ("Z".equals(warn.getItemType())) {
				//区间下限
				min = warn.getWorstDown().intValue();
				if (min > warn.getWorstUp()) {
					min--;
				}
				//区间上限
				max = warn.getBestUp().intValue();
				if (max < warn.getBestUp()) {
					max++;
				}
				if (isNum(data)) {
					Double d = Double.parseDouble(data);
					if (d < warn.getWorstDown()) {
						min = d.intValue();
						if (min > d) {
							min--;
						}
					}
					if (d > warn.getBestUp()) {
						max = d.intValue();
						if (max < d) {
							max++;
						}
					}
				}
				int juli = (max -min)==0?10000:max-min;
				red[0] = (warn.getWorstUp()-min)/juli;
				yellow[0] = (warn.getCriticalUp()-min)/juli;
				green[0] = 1.0;
				colors = new Object[]{red,yellow,green};
			}
			//负向指标
			if ("F".equals(warn.getItemType())) {
				//区间下限
				min = warn.getBestDown().intValue();
				if (min > warn.getBestUp()) {
					min--;
				}
				//区间上限
				max = warn.getWorstUp().intValue();
				if (max <warn.getWorstUp()) {
					max++;
				}
				if (isNum(data)) {
					Double d = Double.parseDouble(data);
					if (d < warn.getBestDown()) {
						min = d.intValue();
						if (min>d) {
							min--;
						}
					}
					if (d > warn.getWorstUp()) {
						max = d.intValue();
						if (max<d) {
							max++;
						}
					}
				}
				int juli = (max -min)==0?10000:max-min;
				green[0] = (warn.getBestUp()-min)/juli;
				yellow[0] = (warn.getCriticalUp()-min)/juli;
				red[0] = 1.0;
				colors = new Object[]{green,yellow,red};
			}
			//区间指标 [取值取最值]
			if ("Q".equals(warn.getItemType())) {
				List<Cri> arr = new ArrayList<Cri>();
				arr.add(new Cri("AD",warn.getWorstDown()));
				arr.add(new Cri("AU",warn.getWorstUp()));
				arr.add(new Cri("BD",warn.getCriticalDown()));
				arr.add(new Cri("BU",warn.getCriticalUp()));
				arr.add(new Cri("CD",warn.getBestDown()));
				arr.add(new Cri("CU",warn.getBestUp()));
				
				if (isNum(data)) {
					Double d = Double.parseDouble(data);
					arr.add(new Cri("D",d));
				}
				Collections.sort(arr);
				Cri cmin = arr.get(0);
				min = cmin.value.intValue();
				if(min>cmin.value)
					min--;
				Cri cmax = arr.get(arr.size()-1);
				max = cmax.value.intValue();
				if(max<cmax.value)
					max++;
				int juli = (max -min)==0?10000:max-min;
				List<Object[]> co = new ArrayList<Object[]>();
				for(Cri cri:arr){
					if("AD".equals(cri.name)||"AU".equals(cri.name)){
						co.add(new Object[]{(cri.value-min)/juli,RED});
					}
					if("BU".equals(cri.name)||"BD".equals(cri.name)){
						co.add(new Object[]{(cri.value-min)/juli,YELLOW});
					}
					if("CU".equals(cri.name)||"CD".equals(cri.name)){
						co.add(new Object[]{(cri.value-min)/juli,GREEN});
					}
				}
				colors = co.toArray();
			}
		}
		return new GLineStyle(min, max, colors);
	}
	
	class GLineStyle{
		int min=0;
		int max=100;
		Object[] colors;
		GLineStyle(){}
		GLineStyle(int min,int max,Object[] colors){
			this.min = min;
			this.max = max;
			this.colors = colors;
		}
	}
	
	public static boolean isNum(String num){
		if(StringUtils.isBlank(num))
			return false;
		String eL = "^[+,-]?\\d+(\\.\\d+)?$";
		Pattern p = Pattern.compile(eL);  
        Matcher m = p.matcher(num);  
        return m.matches();
	}
	

}
