package framework.services.add;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.security.SecurityContext;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;

public class SystemInfoAdd  extends BaseConstructor  implements IAdd{
	
	public SystemInfoAdd(){
		super();
	}
	
	public SystemInfoAdd(Object baseObject){
		super(baseObject);
	}

	public void Add() throws Exception {

		Object tObject = this.getBaseObject();
		Field[] fields = ReflectOperation.getReflectFields(Class.forName(this.getBaseObject().getClass().getName()));
		for(int i=0;i<fields.length;i++){
			if(fields[i].isAnnotationPresent(IColumn.class)){
				IColumn iColumn = fields[i].getAnnotation(IColumn.class);
				if(iColumn.isSystemDate()){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ReflectOperation.setFieldValue(tObject, fields[i].getName(),simpleDateFormat.format(new Date()));
				}
				if(iColumn.isLoginTag()){
					if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
						ReflectOperation.setFieldValue(tObject, fields[i].getName(), SecurityContext.getInstance().getLoginInfo().getTag());
					}
				}
			}
		}
	}
}