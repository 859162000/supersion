package report.service.expression.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import report.dto.ItemRuleDetail;

import report.service.expression.ExpressionContextKey;
import report.service.expression.interfaces.IExpressionBuilder;
import report.service.expression.interfaces.IExpressionConverter;

public class JsExpressionBuilder implements IExpressionBuilder {

	protected Map<String,Object> context=null;
	protected List<IExpressionConverter> converters=null;
	public JsExpressionBuilder()
	{
		this.context=new HashMap<String,Object>();
	    this.converters=new ArrayList<IExpressionConverter>();
	}
	
	public String build() throws Exception {
		StringBuilder expressionBuilder=new StringBuilder();
		Object temp= context.get(ExpressionContextKey.DATA_PARAM_KEY);
		if(temp!=null)
		{
			if(temp instanceof List<?>)
			{				
				List<ItemRuleDetail> dataList=(List<ItemRuleDetail>)temp; 
				String expression;
				for(ItemRuleDetail ic: dataList)
				{
					expression=ic.getStrItemType()+ExpressionContextKey.KEY_VALUE_SPLITER+ic.getStrItemValue();
					for(IExpressionConverter convert:converters)
					{
					   convert.setContext(context);
					   if(convert.convert(expression))
					   {
						   expressionBuilder.append(convert.getResult());
						   break;
					   }
						
					}
					
				}

			}
	
		}
		return expressionBuilder.toString();
		 
		
	}

	@Override
	public void setContext(Map<String, Object> context) {
           if(context!=null)
           {
        	   this.context=context;
        	   
           }
          
		
	}
	@Override
	public void setConverters(List<IExpressionConverter> converts) {
		if(converts!=null)
		{
		   this.converters=converts;		
			
		}
		
	}

}
