package report.service.expression.log;

import java.util.Map;

import report.service.expression.ExpressionContextKey;

public class ExpressionSqlLogFormatter extends BaseExpressionLogFormatter {

	@Override
	public String format(Object obj, Map<Object, Object> params) {
		if(obj!=null && params!=null)
		{
     		formatterBuilder.append("SQL表达式：[");
			formatterBuilder.append(obj.toString());
			formatterBuilder.append("]参数：[日期=");
			formatterBuilder.append(params.get(ExpressionContextKey.DTDATE_PARAM_KEY));
			formatterBuilder.append("]参数：[机构=");
			formatterBuilder.append(params.get(ExpressionContextKey.INSTCODE_PARAM_KEY));
			formatterBuilder.append("] 计算值：");
			formatterBuilder.append(params.get(ExpressionContextKey.DATA_PARAM_KEY));
		}
		return formatterBuilder.toString();
	}

	@Override
	public String format(Object obj, Object[] params) {
		// TODO Auto-generated method stub
		return "";
	}

}
