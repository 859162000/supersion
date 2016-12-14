package report.service.expression.parser;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class WordSpliter {
	
	private  String[] keyWord={"{","}","(",")","+","-","*","/","==",
			                   "!=",">","<","&&","||","if","else",
			                   ">=","<=",",","sumif","countif"};
	private  Set<String> customKeyWord;
	private  Set<String> keyWordSet;
	
	public WordSpliter()
	{
		customKeyWord=new HashSet<String>();
		customKeyWord.add("@item");
		customKeyWord.add("@sql");
		keyWordSet=new HashSet<String>();
		keyWordSet.addAll(Arrays.asList(keyWord));
		
	}
	private boolean isNumber(String str) {
		if (str == null || str.equals(""))
			return false;
		char c = str.charAt(0);
		if (c >= '0' && c <= '9') { // 数字
			return true;
		} else {
			return false;
		}
	}  
	private int getEndIndex(int startindex,String expression)
	{
		Stack<Character> s=new Stack<Character>();
		int i=startindex+1;
		int len=expression.length();
		//s.add("(");
		char curChar;
		for(;i<len;i++)
		{
    		curChar=expression.charAt(i);
			if("(".equals(String.valueOf(curChar)))
			{
				s.push(curChar);
			}
			else if(")".equals(String.valueOf(curChar)))
			{
				s.pop();
				if(s.empty())
				{
					return i;
				}
			}
		}
		return -1;
	}
	public  List<Word> SplitWord(String str) throws Exception
	{
		List<Word> list = new ArrayList<Word>();
		 if (str == null){
		        return list;
		     }
         int order=1;
	     char c;
	     int line =1;
	     
	     int i= 0;
	     int point = 0;
	     while(i<str.length()){
	        c = str.charAt(i);
	       if (c=='"' || c=='\''){//字符串处理        
	     	int index = str.indexOf(c,i + 1);
	     	//处理字符串中的”问题
	         while(index >0 && str.charAt(index - 1) =='\\'){
	         	index = str.indexOf(c,index + 1);
	         }
	         if (index < 0)
	         	throw new Exception("字符串没有关闭");
	         String tempDealStr = str.substring(i,index + 1);
	         //处理 \\，\"的情况
	         String tmpResult = "";
	         int tmpPoint = tempDealStr.indexOf("\\");        
	         while(tmpPoint >=0 ){
	         	tmpResult = tmpResult + tempDealStr.substring(0,tmpPoint);
	         	if(tmpPoint == tempDealStr.length() -1){
	         		throw new Exception("字符串中的" + "\\错误:" + tempDealStr);
	         	}
	         	tmpResult = tmpResult + tempDealStr.substring(tmpPoint + 1 ,tmpPoint + 2);
	         	tempDealStr = tempDealStr.substring(tmpPoint + 2);
	         	tmpPoint = tempDealStr.indexOf("\\");  
	         }
	         tmpResult = tmpResult + tempDealStr;
	         list.add(new Word(tmpResult,order++));

	         if (point < i ){
	             list.add(new Word(str.substring(point,i),order++));
	         }
	         i = index + 1;
	         point = i;
	       }
	       else if(c=='.' && point < i && isNumber(str.substring(point,i))){
	    	   i = i + 1; //小数点的特殊处理
	       }
	       else if(c == ' ' ||c =='\r'|| c =='\n'||c=='\t'||c=='\u000C'){
	    	    if (point < i ){
		             list.add(new Word(str.substring(point,i),order++));
		        }
		        if(c =='\n'){
		         		 line = line + 1;
		        } 
		        i = i + 1;
		        point = i;
	       }
	       else if(c=='@')
		    {
	    	   int leftBrachetIndex=str.indexOf("(", i+1);
	    	  
	    	   if(leftBrachetIndex>-1)
	    	   {
	    		   String expKey=str.substring(i,leftBrachetIndex).toLowerCase();
	    		   if(!customKeyWord.contains(expKey))
	    		   {
	    			   throw new Exception(String.format("不支持表达式%s,第%d列",expKey,i));
	    		   }
	    		   int endIndex=getEndIndex(i+1,str);
			    	if(endIndex>-1)
			    	{
			    		 list.add(new Word(str.substring(point,endIndex+1),order++));
			    	}
			    	else
			    	{
			    		throw new Exception(String.format("表达式%s缺少右括号,第%d列",expKey,i));
			    	}
			    	
			    	point=endIndex+1;
			    	i=point;
	    	   }
	    	   else
	    	   {
	    		   i++;
	    	   }
	    		   
		    	
		   }   	
		   else{
	    	   boolean isFind = false;
	    	   for(String s:keyWord){
	    		   int length = s.length();
	    		   if(i + length <= str.length() && str.substring(i, i+length).equals(s)){
	    			   //处理>、>=、<、<=
	    			   String strPlusOne="";
	    			   if(str.length()>=i+length+1)
	    			   {
	    				   strPlusOne=str.substring(i, i+length+1);
	    			   }
	    			   if (point < i ){
	    		             list.add(new Word(str.substring(point,i),order++));
	    		       }
	    			   if(keyWordSet.contains(strPlusOne))
	    			   {
	    				  
		    			   list.add(new Word(str.substring(i, i+length+1),order++));
		    			   i = i + length+1;
		    			  
	    			   }
	    			   else
	    			   {
	    				   list.add(new Word(str.substring(i, i+length),order++));
		    			   i = i + length;
		    			  
	    			   }
	    			   point = i;
	    			   isFind = true;
	    			   break;
	    			  
	    		   } 
	    	   }
	    	   if(isFind == false){
	    		   i = i+1;
	    	   }
	       }
	     }
		if (point < i) {
			list.add(new Word(str.substring(point, i),order++));
		}
		
		return list;
		
	}
}
