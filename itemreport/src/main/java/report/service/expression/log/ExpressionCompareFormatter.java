package report.service.expression.log;

import java.util.HashMap;
import java.util.Map;

import report.helper.DoubleUtil;


public class ExpressionCompareFormatter extends BaseExpressionLogFormatter {

	private Map<Object,String> useCompareType;
	
	public ExpressionCompareFormatter()
	{
		useCompareType=new HashMap<Object,String>();
		useCompareType.put("=","等于");
		useCompareType.put(">","大于");
		useCompareType.put(">=","大于等于");
		useCompareType.put("<","小于");
		useCompareType.put("<=","小于等于");
		useCompareType.put("!=","不等于");//(new String[]{"=",">",">=","<","<=","!="});
	}
	
	@Override
	public String format(Object obj, Map<Object, Object> params) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String format(Object obj, Object[] params) {
		if(obj!=null&& params!=null && params.length>3)
		{
			if(useCompareType.containsKey(obj))
			{
				Double leftVal=(Double)params[0];
				Double rightVal=(Double)params[1];
				Double dblTolerance=(Double)params[3];
				boolean isSumCheck=false;
				if(params.length==5)
				{
					isSumCheck=Boolean.valueOf(params[4].toString());
				}
				if(isSumCheck)
				{
					formatterBuilder.append(String.format("母项取值：%.2f\r\n汇总值：%.2f\r\n差值：%.2f\r\n",leftVal,rightVal,leftVal-rightVal));
					formatterBuilder.append("汇总子项\r\n------------------------------------------------------------------");
					
				}
				else
				{
					formatterBuilder.append(" 子项取值："+DoubleUtil.format(rightVal,(Integer)params[2]));
					//formatterBuilder.append(" 母项取值应 "+useCompareType.get(obj)+" 子项取值，差值：");
					formatterBuilder.append(" \r\n差值：");
//					formatterBuilder.append(DoubleUtil.format(leftVal-rightVal-dblTolerance,(Integer)params[2]));
					formatterBuilder.append(DoubleUtil.format(leftVal-rightVal,(Integer)params[2]));
					formatterBuilder.append("\r\n子项公式\r\n------------------------------------------------------------------\r\n");
				}
				
			}
			else
			{
				formatterBuilder.append("不支持比较操作"+obj.toString());
			}
			
			
		}
		return formatterBuilder.toString();
		

	}

}
