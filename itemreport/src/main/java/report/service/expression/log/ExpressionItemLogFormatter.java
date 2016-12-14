package report.service.expression.log;

import java.util.Date;
import java.util.Map;

import report.dao.imps.ConstCacheTypeKey;
import report.dao.imps.ItemDataCacheManger;
import report.dto.ItemInfo;
import report.dto.Period;
import report.service.expression.ExpressionContextKey;
import report.service.expression.parser.ItemExpParamKey;
import framework.helper.ExceptionLog;
import framework.helper.TypeParse;

public class ExpressionItemLogFormatter extends BaseExpressionLogFormatter {

	@Override
	public String format(Object obj, Map<Object, Object> params) {
		formatterBuilder.setLength(0);
		if (obj != null && params != null) {
			try {
				ItemDataCacheManger cacheManager = ItemDataCacheManger.getInsance();
				ItemInfo itemInfo = cacheManager.getItem((String) params.get(ItemExpParamKey.ITEMCODE_NAME));
				formatterBuilder.append("指标代码[");
				formatterBuilder.append(params.get(ItemExpParamKey.ITEMCODE_NAME));
				formatterBuilder.append("]");
				formatterBuilder.append(itemInfo.getStrItemName());
				formatterBuilder.append(" 币种[");
				formatterBuilder.append(cacheManager.getConstData("currencyType", params.get(ItemExpParamKey.CURRENCY_NAME).toString()));
				
				formatterBuilder.append("] 属性[");
				formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.ItemPropertyConstType, params.get(ItemExpParamKey.PROPERTY_NAME).toString()));
				
				
				//20161103 Liaoxl repair 暂时没有用到这两个属性，所以不启用 
/*				formatterBuilder.append("] 扩展维度1[ ");
				formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.Ext1ConstType, params.get(ItemExpParamKey.EXT1_NAME).toString()));
				formatterBuilder.append("] 扩展维度2[");
				formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.Ext2ConstType, params.get(ItemExpParamKey.EXT2_NAME).toString()));*/

				formatterBuilder.append("] 期数[");
				
				switch(TypeParse.parseInt(params.get(ItemExpParamKey.TIME_NAME).toString()))
				{
					case 1:
						formatterBuilder.append("本期");
						break;
					case 2:
						formatterBuilder.append("上期");
						break;
					case 3:
						formatterBuilder.append("去年同期");
						break;
					case 4:
						formatterBuilder.append("年初");
						break;
					case 5:
						formatterBuilder.append("上年末");
						break;
					default:
						formatterBuilder.append("本期");
				}
				
				
				formatterBuilder.append("] 频度[ ");
				formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.FreqConstType, params.get(ItemExpParamKey.FREQ_NAME).toString()));

				formatterBuilder.append("] [日期=");
				Object objDate = params.get(ItemExpParamKey.DTDATE_NAME);
				if (objDate == null) {
					objDate = params.get(ExpressionContextKey.DTDATE_PARAM_KEY);
				}

				if (objDate instanceof Date) {
					formatterBuilder.append(TypeParse.parseString((Date) objDate, "yyyy-MM-dd"));

				} else
					formatterBuilder.append(objDate);
				
								
				Object instCode = params.get(ItemExpParamKey.INSTCODE_NAME);
				if (instCode == null) {
					instCode = params.get(ExpressionContextKey.INSTCODE_PARAM_KEY);
				}

				formatterBuilder.append("] [机构编码=");
				formatterBuilder.append(instCode);
				formatterBuilder.append("] 取值：");
				formatterBuilder.append(params.get(ExpressionContextKey.DATA_PARAM_KEY));

			} catch (Exception ex) {
				ExceptionLog.CreateLog(ex);
			}

		}
		return formatterBuilder.toString();
	}

	@Override
	public String format(Object obj, Object[] params) {
		return "";
	}

}
