package report.service.expression.log;

import java.util.Map;
import report.dao.imps.ConstCacheTypeKey;
import report.dao.imps.ItemDataCacheManger;
import report.dto.CalRule;
import report.dto.ItemInfo;

public class ExpressionCalRuleFormatter extends BaseExpressionLogFormatter {

	@Override
	public String format(Object obj, Map<Object, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String format(Object obj, Object[] params) throws Exception{
		if(obj!=null && params!=null && params.length==1)
		{
			
			ItemDataCacheManger cacheManager = ItemDataCacheManger.getInsance();
			
			CalRule cr=(CalRule)obj;
			ItemInfo itemInfo = cacheManager.getItem(cr.getStrItemCode());
			formatterBuilder.append("第["+cr.getIntOrder().toString()+"]组校验规则，");
			formatterBuilder.append("规则名称[");
			formatterBuilder.append(cr.getStrCalculationRuleName());
			formatterBuilder.append("]\r\n");
			formatterBuilder.append("指标代码[");
			formatterBuilder.append(cr.getStrItemCode());
			formatterBuilder.append("]");
			formatterBuilder.append(itemInfo.getStrItemName());
			formatterBuilder.append(" 币种[");
			formatterBuilder.append(cacheManager.getConstData("currencyType", cr.getCurrencyType()));
			
			formatterBuilder.append("] 属性[");
			formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.ItemPropertyConstType, cr.getStrPropertyCode()));
			
			formatterBuilder.append("] 期数[本期]");
			
			formatterBuilder.append(" 频度[ ");
			formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.FreqConstType, cr.getStrFreq()));
			
			//20161103 Liaoxl repair 暂时没有用到这两个属性，所以不启用 
/*			formatterBuilder.append("] 扩展维度1[ ");
			formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.Ext1ConstType, cr.getStrExtendDimension1()));
			formatterBuilder.append("] 扩展维度2[");
			formatterBuilder.append(cacheManager.getConstData(ConstCacheTypeKey.Ext1ConstType, cr.getStrExtendDimension2()));*/
			
			formatterBuilder.append("]\r\n");
			
			formatterBuilder.append("母项取值：");
			formatterBuilder.append(params[0]);
			formatterBuilder.append("\r\n");
			formatterBuilder.append("计算符： "+cr.getCompareType());
			//formatterBuilder.append("\r\n规则表达式： ");
			//formatterBuilder.append("\r\n-------------------------------------------------------------");
			
		}
		return formatterBuilder.toString();
	}

}
