package report.service.expression.parser;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import framework.helper.SmallTools;
import framework.helper.TypeParse;

import report.dao.imps.ItemDataCacheManger;
import report.dto.CalRoundMethod;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.Period;
import report.dto.SimpleItemData;
import report.helper.DoubleUtil;
import report.service.expression.ExpressionContextKey;
import report.service.expression.ExpressionKey;

public class ItemWordCal extends BaseWordCal {

	private Map<String, String> nameValues;
	private Set<String> keyWords;

	private static final String NULL = "null";
	private static final String NOVALUE = "-1";

	public ItemWordCal() {
		nameValues = new HashMap<String, String>();
		keyWords=new HashSet<String>();
		keyWords.add(ExpressionKey.ITEM);
	}

	@Override
	protected void calWordVal(Word w) throws Exception {
		Map<String,Object> expParam=w.getExpParam();
		for(String key:expParam.keySet())
		{
			nameValues.put(key,(String) expParam.get(key));
		}
		adjustData();
		SimpleItemData dataVal = ItemDataCacheManger.getInsance().getItemData(
				nameValues.get(ItemExpParamKey.INSTCODE_NAME),
				nameValues.get(ItemExpParamKey.DTDATE_NAME),
				nameValues.get(ItemExpParamKey.ITEMCODE_NAME),
				nameValues.get(ItemExpParamKey.CURRENCY_NAME),
				nameValues.get(ItemExpParamKey.PROPERTY_NAME),
				nameValues.get(ItemExpParamKey.EXT1_NAME),
				nameValues.get(ItemExpParamKey.EXT2_NAME),
				nameValues.get(ItemExpParamKey.FREQ_NAME));
		
		result = dataVal.StrValue;
		
		
		format(result);
		w.value = result;
		
		w.setRuntimeParam(nameValues);
		if(expressionFormatter!=null)
		{
			Map<Object,Object> logParams=new HashMap<Object,Object>();
			logParams.putAll(context);
			logParams.putAll(nameValues);
			logParams.put(ExpressionContextKey.DATA_PARAM_KEY,result);
			w.description=expressionFormatter.format(w.word, logParams);
			//expressionLog.log(strParams, logParams, "itemExpressionFormatter");
		}
		

	}

	

	private void adjustData() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (StringUtils.isBlank(nameValues.get(ItemExpParamKey.INSTCODE_NAME))
				|| nameValues.get(ItemExpParamKey.INSTCODE_NAME)
						.equalsIgnoreCase(NULL)
				|| nameValues.get(ItemExpParamKey.INSTCODE_NAME)
						.equalsIgnoreCase(NOVALUE)) {
			if (context.containsKey(ExpressionContextKey.INSTCODE_PARAM_KEY)) {
				nameValues.put(ItemExpParamKey.INSTCODE_NAME, context.get(
						ExpressionContextKey.INSTCODE_PARAM_KEY).toString());

			} else
				throw new Exception("指标表达式计算缺少机构参数");
		}
		if (NOVALUE.equals(nameValues.get(ItemExpParamKey.EXT1_NAME))) {
			nameValues.put(ItemExpParamKey.EXT1_NAME, "");
		}

		if (NOVALUE.equals(nameValues.get(ItemExpParamKey.EXT2_NAME))) {
			nameValues.put(ItemExpParamKey.EXT2_NAME, "");
		}
		if (NOVALUE.equals(nameValues.get(ItemExpParamKey.FREQ_NAME))) {
			nameValues.put(ItemExpParamKey.FREQ_NAME, "");
		}

		if (StringUtils.isBlank(nameValues.get(ItemExpParamKey.DTDATE_NAME))
				|| nameValues.get(ItemExpParamKey.DTDATE_NAME)
						.equalsIgnoreCase(NULL)) {

			if (context.containsKey(ExpressionContextKey.DTDATE_PARAM_KEY)) {
				Object objDate = context
						.get(ExpressionContextKey.DTDATE_PARAM_KEY);
				if (objDate instanceof Date) {				
					nameValues.put(ItemExpParamKey.DTDATE_NAME, sdf.format((Date) objDate));
				} else{
					nameValues.put(ItemExpParamKey.DTDATE_NAME, objDate.toString());
				}			
			} else {
				throw new Exception("指标表达式计算缺少数据日期参数");
			}
		}

		if (!StringUtils.isBlank(nameValues.get(ItemExpParamKey.TIME_NAME))
				&& !nameValues.get(ItemExpParamKey.TIME_NAME).equals(Period.CurPeriod.toString())) {

			if (context.containsKey(ExpressionContextKey.RPT_FREQ_PARAM_KEY)) 
			{
				//去年同期
				if(nameValues.get(ItemExpParamKey.TIME_NAME).equals(Period.LastYearSameTime.toString()))
				{
					String rptFreq = (String) context.get(ExpressionContextKey.RPT_FREQ_PARAM_KEY);
					Date realDate = SmallTools.getLastYearSameTime(TypeParse.parseDate(nameValues.get(ItemExpParamKey.DTDATE_NAME)), rptFreq);
					nameValues.put(ItemExpParamKey.DTDATE_NAME, sdf.format(realDate));
				}
				//年初
				else if(nameValues.get(ItemExpParamKey.TIME_NAME).equals(Period.FirstDayofYear.toString()))
				{
					 Calendar calendar = Calendar.getInstance();
			    	 calendar.setTime(TypeParse.parseDate(nameValues.get(ItemExpParamKey.DTDATE_NAME)));
			    	 int currentYear = calendar.get(Calendar.YEAR);  
			    	 calendar.clear();
			    	 calendar.set(Calendar.YEAR, currentYear);  	 
			    	 nameValues.put(ItemExpParamKey.DTDATE_NAME, sdf.format(calendar.getTime()));
				}
				//上年末
				else if(nameValues.get(ItemExpParamKey.TIME_NAME).equals(Period.LastYearEnd.toString()))
				{
					 Calendar calendar = Calendar.getInstance();
			    	 calendar.setTime(TypeParse.parseDate(nameValues.get(ItemExpParamKey.DTDATE_NAME)));
			    	 int currentYear = calendar.get(Calendar.YEAR);  
			    	 calendar.clear();
			    	 calendar.set(Calendar.YEAR, currentYear);  	
			    	 calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			    	 nameValues.put(ItemExpParamKey.DTDATE_NAME, sdf.format(calendar.getTime()));
				}
				//上期(当期数不为本期、去年同期、年初和上年末时，系统默认为上期)
				else
				{
					String rptFreq = (String) context.get(ExpressionContextKey.RPT_FREQ_PARAM_KEY);
					Date preDate = SmallTools.getFreqDate(TypeParse.parseDate(nameValues.get(ItemExpParamKey.DTDATE_NAME)), rptFreq);
					nameValues.put(ItemExpParamKey.DTDATE_NAME, sdf.format(preDate));
				}

			} 
			else 
			{
				throw new Exception("指标表达式计算缺少报表频率参数");
			}

		} else
			nameValues.put(ItemExpParamKey.TIME_NAME, Period.CurPeriod.toString());
	}

	private void format(String val) throws Exception {
		String itemCode = nameValues.get(ItemExpParamKey.ITEMCODE_NAME);
		ItemInfo itemInfo = ItemDataCacheManger.getInsance().getItem(itemCode);
		if (itemInfo != null) 
		{
			if(StringUtils.isBlank(val) &&
					(ItemDataType.Amount.toString().equals(itemInfo.getStrDataType())||
							ItemDataType.Num.toString().equals(itemInfo.getStrDataType())||
							ItemDataType.Percent.toString().equals(itemInfo.getStrDataType())||
							ItemDataType.Ratio.toString().equals(itemInfo.getStrDataType())||
							ItemDataType.Seq.toString().equals(itemInfo.getStrDataType())))
			{
				val="0";
			}

			try{
				String itemDataType = itemInfo.getStrDataType();
				if (ItemDataType.Amount.toString().equals(itemDataType) || ItemDataType.Percent.toString().equals(itemDataType)) 
				{
					if (CalRoundMethod.FirstRound.toString().equals(context.get(ExpressionContextKey.CALROUNDMETHOD_PARAM_KEY))) 
					{
						Object objPrecise = context.get(ExpressionContextKey.PRECISE_PARAM_KEY);
						Integer intPrecise = 2;
						if (objPrecise != null) {
							intPrecise = (Integer) objPrecise;
						}
						
						Object objRptUnit=context.get(ExpressionContextKey.RptUnit);
						Integer intRptUnit=1;	//默认报表单位为元
						if(objRptUnit!=null)
						{
							intRptUnit=(Integer)objRptUnit;
						}
						
						Double dblVal=TypeParse.parseDouble(val);

						if(itemDataType.equals("2"))
						{
//							val=DoubleUtil.format(dblVal, intPrecise);
							
							
							BigDecimal b = new BigDecimal(val);
							b = b.divide(new BigDecimal(intRptUnit));
							
							val=DoubleUtil.format(TypeParse.parseDouble(b.toString()), intPrecise);
							
							b=new BigDecimal(val);
							val= b.multiply(new BigDecimal(intRptUnit)).toString();
							
						}
									
						else
						{
							
							BigDecimal b1 = new BigDecimal(val);
							
							//Liaoxl 注意：这里不需要先放大一百倍了，因为这是指标间计算，百分比指标的数据在ItemData表里的值已经是被放大100倍的了，所以不需要再乘以100了
							//val = b1.multiply(new BigDecimal(100)).toString();	//先放大100倍再进行精度截取，然后除以100恢复回去
							
							val = DoubleUtil.format(Double.parseDouble(val), intPrecise);
							
							b1 = new BigDecimal(val);
							val = b1.divide(new BigDecimal(100)).toString();
						}

					}
					else if(itemDataType.equals("5")){
						BigDecimal b1 = new BigDecimal(val);
						val = b1.divide(new BigDecimal(100)).toString();					
					}
					
					if(val.indexOf("E")>0 || val.indexOf("e")>0)	//判断是否为科学计数法			
						val = (new BigDecimal(val)).toPlainString();

				}
			}
			catch(Exception ex)
			{
				if(val.equals("NaN"))
					val = "0";
				else
					this.result = val;
			}
			
		}
		
		this.result = val;
	}
	

	@Override
	protected Set<String> getWordKey() {
		return keyWords;
	}

}
