package zxptsystem.service.add;
import java.util.Map;
import java.util.Set;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;
import framework.show.ShowContext;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class ToIntgetFieldAdd implements IAdd {

	/**
	 * 
	 */
//提取小数字段并将小数四舍五入转换成为整数
	public void Add() throws Exception {
		Object object=RequestManager.getTOject();
		String classname=RequestManager.getTName();
		Map<String, String> sswr = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field_ByJE");
		Set<String> newclassName=sswr.keySet();
		
		if(newclassName.contains(classname)){
			String[] Arrayfield=sswr.get(classname).split(",");
			for(String fieldname :Arrayfield){
				Object values=ReflectOperation.getFieldValue(object, fieldname);
			    if(values!=null && !StringUtils.isBlank(values.toString())){
			    	String tempValue="";
			    	if(values.toString().contains(".")){
			    		tempValue=values.toString().substring(0,values.toString().indexOf("."));
			    	}else{
			    		tempValue=values.toString();
			    	}
			    	if(tempValue.length()<=10){
			    		try{
				    		BigDecimal bigDecimal=new BigDecimal(values.toString()).setScale(0,BigDecimal.ROUND_HALF_UP);
				    		ReflectOperation.setFieldValue(object, fieldname,String.valueOf(bigDecimal.intValue()));
				    	}catch(Exception ex){
				    		System.out.println("应为数字");
				    	}
			    	}
				}
			}
		}
	}
}
	
	
