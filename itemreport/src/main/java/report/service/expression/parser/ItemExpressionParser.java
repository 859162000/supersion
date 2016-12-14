package report.service.expression.parser;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import report.service.expression.ExpressionKey;
public class ItemExpressionParser extends BaseExpressionParser  {


	private static final String PARAM_SPLITER="&";
	private static final String VALUE_SPLITER="=";
	

	
	
	
	//Time=1&ItemCode=A00011A&Extend=1&dtDate=2015-07-07&instInfo=&Curr=AUD&Property=0000
    
/*	private String strInstCode;
	private String dtDate;
    private String strItemCode;
    private String currencyType;
    private String strPropertyCode;
    private String strExtendDimension1;
    private String strExtendDimension2;
    private String strTime;
    private String strFreq;*/
    private Map<String,String> nameValues;
    
    
    public ItemExpressionParser()
    {
       
        nameValues=new HashMap<String,String>();
        
        
        
    	
    }
    
    private void initNameValues()
    {
    	nameValues.clear();
    	nameValues.put(ItemExpParamKey.ITEMCODE_NAME,"");
        nameValues.put(ItemExpParamKey.DTDATE_NAME,"");
        
        nameValues.put(ItemExpParamKey.INSTCODE_NAME,"");
        nameValues.put(ItemExpParamKey.CURRENCY_NAME,"");
        
        nameValues.put(ItemExpParamKey.EXT1_NAME,"");
        nameValues.put(ItemExpParamKey.EXT2_NAME,"");
        
        nameValues.put(ItemExpParamKey.TIME_NAME,"");
        nameValues.put(ItemExpParamKey.PROPERTY_NAME,"");
        
        nameValues.put(ItemExpParamKey.FREQ_NAME,"");
    }
   
	private void parseParam(String expressionValue) throws Exception
	{
		String[] params=expressionValue.split(PARAM_SPLITER);
		String key;
		for(String param:params)
		{
			String[] keyvalue=param.split(VALUE_SPLITER);
			key=keyvalue[0].toLowerCase();
			if(nameValues.containsKey(key))
			{
				nameValues.put(key,keyvalue.length>1?keyvalue[1]:"");
			}

		}
		
	
		
	}
	

	
	
	
	@Override
	public boolean parse(Word word) throws Exception {
		
		if(!StringUtils.isBlank(word.word)
				&& word.word.indexOf(ExpressionKey.ITEM)>-1)
		{
			word.wordType=ExpressionKey.ITEM;
			String param=getParam(word);
			initNameValues();	
			parseParam(param);
			word.setExpParam(nameValues);
			//getVal();
			
		
			return true;		  
		}
		else
			return false;
		
	
	}

	private String getParam(Word word) throws Exception {
		
		int keyEndIndex;
		String expression=word.word;
		
		
		String strParams="";
		
		int keyLen=ExpressionKey.ITEM.length()+ExpressionKey.PARAM_LEFT_SPLITER.length();
		
		keyEndIndex=expression.indexOf(ExpressionKey.PARAM_RIGHT_SPLITER,keyLen);
		if(keyEndIndex>-1)
		{
			strParams=expression.substring(keyLen, keyEndIndex);
		}
		else
			throw new Exception("表达式语法错误，"+word.word+"缺少右括号");
		
		return strParams;
		
	}

	

}
