package report.service.add;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import report.dto.RptInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;
/**
 * 清理查询条件中DTO对象的固定值
 * @author liutao
 *
 */
public class ClearDtoStringDefaultConditions implements IAdd{

	public void Add() throws Exception {
		Object tObjct = RequestManager.getTOject();
		String tName = RequestManager.getTName();
	    Object tNameRpt = FrameworkFactory.CreateClass(tName);
		Boolean isSameTag = true;

		// 获取当前操作的DTO所定义的属性
		Field[] fields = ((RptInfo) tObjct).getClass().getDeclaredFields();
		
		if(tObjct.getClass().getName().equals(tName)){
			Class clazz = tObjct.getClass();
			PropertyDescriptor pd = null;
			for(Field field : fields){
				if(!field.getName().equals("serialVersionUID")){
					try{
						pd = new PropertyDescriptor(field.getName(), clazz);
					}catch(Exception e){
						pd = null;
					}
					if(null != pd){
						Method method = pd.getReadMethod();
						
						Object to = method.invoke(tObjct);
						Object tno = method.invoke(tNameRpt);
						if(null != to && null != tno){
							if(!StringUtils.isBlank(to.toString()) && !StringUtils.isBlank(tno.toString())){
								if(!to.equals(tno)){
									isSameTag = false;
									break;
								}
							}
						}
						else if((null == to && null != tno) || (null != to && null == tno)){
							isSameTag = false;
							break;
						}
					}
				}
			}
		}
		
		if(isSameTag){
			for(Field field : fields){
				if(isSameTag && field.getType().equals(String.class)){
					ReflectOperation.setFieldNullValue(tObjct, field.getName(), field.getType());
				}
			}
		}
		
	}

}
