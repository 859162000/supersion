package framework.services.translate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;

import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class ComponentAllTranslate extends BaseConstructor implements ITranslate{

	public ComponentAllTranslate(){
		super();
	}
	
	public ComponentAllTranslate(Object baseObject){
		super(baseObject);
	}
	private List<String> getTranslateList(String dtoName)
	{
		List<String> translateList=null;
		if(LogicParamManager.getDtoTranslateMap()!=null)
		{
			translateList=LogicParamManager.getDtoTranslateMap().get(dtoName);
		}
		else{
			translateList=LogicParamManager.getDefaultComponentTranslateClassList();
		}
		
		if(translateList==null)
		{
			translateList=new ArrayList<String>();
		}
		return translateList;
		
	}
	public void Translate() throws Exception {
	
		String dtoName=RequestManager.getTName();
		setBaseObject(RequestManager.getTOject());
		
		List<String> translateList=getTranslateList(dtoName);
		for (String strTranslateClass : translateList) {
			ITranslate translate =null;
			Constructor<?> constructorMethod=ReflectOperation.getConstructor(strTranslateClass,Object.class);
			
			if(constructorMethod!=null)
			{
				translate = (ITranslate)constructorMethod.newInstance(this.getBaseObject());
			}
			else
			{
				translate = (ITranslate)FrameworkFactory.CreateClass(strTranslateClass);
			}
			
			translate.Translate();
		}
		
		
		List<Field> fieldList=ReflectOperation.getOneToManyField(dtoName);
		Object masterObject=RequestManager.getTOject();
		String tName=RequestManager.getTName();
		try
		{
			for (Field field : fieldList) {
				if(masterObject!=null)
				{
					Collection<?> fieldValue=(Collection<?>)ReflectOperation.getFieldValue(this.getBaseObject(), field.getName());
					dtoName=ReflectOperation.getGenericClass(field.getGenericType()).getName();
					
					translateList=getTranslateList(dtoName);
					RequestManager.setTName(dtoName);
					for (Object object : fieldValue) {
						RequestManager.setTOject(object);
						for (String strTranslateClass : translateList) {
							ITranslate translate =null;
							Constructor<?> constructorMethod=ReflectOperation.getConstructor(strTranslateClass,Object.class);
							if(constructorMethod!=null)
							{
								translate = (ITranslate)constructorMethod.newInstance(this.getBaseObject());
							}
							else
							{
								translate = (ITranslate)FrameworkFactory.CreateClass(strTranslateClass);
							}
							
							translate.Translate();
						}	
					}
				}
				
			}
		}
		finally
		{
			RequestManager.setTName(tName);
			RequestManager.setTOject(masterObject);
		}
		
		
		
	}

}
