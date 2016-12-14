package report.service.expression.converter;

import report.service.expression.ExpressionKey;

public class JsItemExpressionConverter extends JsBaseExpressionConverter {

	private static final String TYPE_FLAG="item";
	
	
	//Time=1&ItemCode=A00011A&Extend=1&dtDate=2015-07-07&instInfo=&Curr=AUD&Property=0000
    

    
    public JsItemExpressionConverter()
    {
      
    	
    }
	@Override
	protected boolean convert(String expressionType, String expressionValue) throws Exception  {
		if(TYPE_FLAG.equalsIgnoreCase(expressionType))
		   {
			   
			   this.result=ExpressionKey.ITEM+ExpressionKey.PARAM_LEFT_SPLITER+expressionValue+ExpressionKey.PARAM_RIGHT_SPLITER;
			   return true;
		   }
		return false;
	}
	





}
