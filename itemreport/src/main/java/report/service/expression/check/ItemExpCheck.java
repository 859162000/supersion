package report.service.expression.check;

import java.util.Map;

import report.service.expression.ExpressionKey;
import report.service.expression.interfaces.IWordCheck;

import report.service.expression.parser.ItemExpParamKey;
import report.service.expression.parser.Word;

public class ItemExpCheck  implements
		IWordCheck {
	
	private StringBuilder msgBuilder;
	
	public ItemExpCheck()
	{
		msgBuilder=new StringBuilder();
	}

	@Override
	public String getCheckMsg() {
		// TODO Auto-generated method stub
		return msgBuilder.append("\r\n").toString();
	}

	@Override
	public boolean check(Word w) throws Exception {
		msgBuilder.setLength(0);
		if(ExpressionKey.ITEM.equals(w.wordType))
		{
			Map<String,Object> expParam=w.getExpParam();
			
			Object objItemCode=expParam.get(ItemExpParamKey.ITEMCODE_NAME);
			if(objItemCode==null ||"".equals(objItemCode))
			{
				msgBuilder.append("缺少指标代码参数");
				
			}
			Object objProperty=expParam.get(ItemExpParamKey.PROPERTY_NAME);
			if(objProperty==null ||"".equals(objProperty))
			{
				msgBuilder.append(",缺少属性代码参数");
				
			}
			Object objcurrency=expParam.get(ItemExpParamKey.CURRENCY_NAME);
			if(objcurrency==null ||"".equals(objcurrency))
			{
				msgBuilder.append(",缺少币种代码参数");
				
			}
			
			Object objTime=expParam.get(ItemExpParamKey.TIME_NAME);
			if(objTime==null ||"".equals(objTime))
			{
				msgBuilder.append(",缺少期数参数");
				
			}
			if(msgBuilder.length()>0)
			{
				msgBuilder.insert(0, String.format("表达式%s检验错误:", w.word));
				msgBuilder.append("。");
				return false;
			}
			
			
		}
		return true;
		
	}

}
