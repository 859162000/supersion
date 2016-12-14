package framework.struts.ext;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.Id;

import com.opensymphony.xwork2.conversion.impl.DefaultObjectTypeDeterminer;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.reflection.ReflectionProvider;

public class OneToManyElementTypeDeterminer extends
		DefaultObjectTypeDeterminer {

	 private ReflectionProvider reflectionProvider;
	 private XWorkConverter xworkConverter;
	 private static Map<String,String> keyPropertyMap=new Hashtable<String,String>();
    @Inject
	public OneToManyElementTypeDeterminer(@Inject XWorkConverter conv,
			@Inject ReflectionProvider prov) {
		super(conv, prov);
		
		// TODO Auto-generated constructor stub
	}
	public String getKeyProperty(Class parentClass, String property) {
    	String key=parentClass.getName()+"_"+property;
		String val=keyPropertyMap.get(key);
		if(val==null)
		{
			Class<?> clazz = super.getElementClass(parentClass, property, true);
			if(clazz!=null)
			{
				Field[] fieldList=clazz.getDeclaredFields();
				for(Field f:fieldList)
				{
					if(f.isAnnotationPresent(Id.class))
					{
						val=f.getName();
						keyPropertyMap.put(key,val);
						break;
					}
				}
				
			}
			else
			{
				val=super.getKeyProperty(parentClass,property);
			}
			
		}
		return val;
    }
}
