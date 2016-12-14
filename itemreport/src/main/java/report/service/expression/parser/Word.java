package report.service.expression.parser;

import java.util.HashMap;
import java.util.Map;

public class Word {
	
	public String wordType;
	public String word;
	public String value;
	public String description;
	public int intOrder;
	
	private Map<String,Object> expParam;
	private Map<String,Object> runtimeParam;
	

	
	
	public Word(String aWord,int intOrder){
	   this.word = aWord;
	   this.intOrder=intOrder;
	   runtimeParam=new HashMap<String,Object>();
	   expParam=new HashMap<String,Object>();
	}
	 public void setExpParam(Map expParam)
	 {
		 this.expParam.putAll(expParam);
	 }
	 public Map<String,Object> getExpParam()
	{
			return expParam;
	}
	
	public void setRuntimeParam(Map runtimeParam)
	{
		
			this.runtimeParam.putAll(runtimeParam);
		
	}
	public Map<String,Object> getRuntimeParam()
	{
		return runtimeParam;
	}
	public String toString(){
	  return this.word;
	}
}