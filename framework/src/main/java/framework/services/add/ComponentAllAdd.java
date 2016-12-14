package framework.services.add;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class ComponentAllAdd extends BaseConstructor implements IAdd {
	
	public ComponentAllAdd(){
		super();
	}
	
	public ComponentAllAdd(Object baseObject){
		super(baseObject);
	}

	private void setRelation(String dtoName) throws Exception
	{
		List<Field> fieldList=ReflectOperation.getOneToManyField(dtoName);
		Object curO=this.getBaseObject();
		for (Field field : fieldList) {
			Class<?> t=ReflectOperation.getGenericClass(field.getGenericType());
			List<Field> joinColumnList=ReflectOperation.getJoinColumnFieldList(t);
			Collection<?> fieldValue=(Collection<?>)ReflectOperation.getFieldValue(curO, field.getName());
			if(fieldValue!=null)
			{
				for (Object object : fieldValue) {
					for(Field jf:joinColumnList)
					{
						
						if(jf.getType().isInstance(curO) )
						{
							ReflectOperation.setFieldValue(object, jf.getName(), curO);
						}
					}
				}
			}
				
		}
		
	}
	private void setDtoDefaultValue(String dtoName)
	{
		Map<String,Map<String,String>> dtoDefaultValue=LogicParamManager.getDtoDefaultValue();
		if(dtoDefaultValue!=null)
		{
			LogicParamManager.setDefaultValueMap(dtoDefaultValue.get(dtoName));
		}
	}
	private List<String> getAddList(String dtoName)
	{
		List<String> addList=null;
		if(LogicParamManager.getDtoAddMap()!=null)
		{
			addList=LogicParamManager.getDtoAddMap().get(dtoName);
		}
		else{
			addList=LogicParamManager.getDefaultComponentAddClassList();
		}
		if(addList==null)
		{
			addList=new ArrayList<String>();
		}
		return addList;
		
	}
	
	public void Add() throws Exception {
		
		Object tObject=RequestManager.getTOject();
		if(tObject!=null)
		{
			String dtoName=RequestManager.getTName();
			String showInstanceName=ShowParamManager.getShowInstanceName();
			ShowInstance showInstance=ReflectOperation.getShowInstance(dtoName, showInstanceName);
			//String dtoName=tObject.getClass().getName();
			processList(tObject,dtoName, showInstance);
			
			processCard(tObject, dtoName, showInstance);
	
			processMasterCard(dtoName);
			
			setRelation(dtoName);
		}
		
		
		
	}

	/**
	 * @param dtoName
	 
	 * @throws Exception
	 */
	private void processMasterCard(String dtoName)	throws Exception {
		setDtoDefaultValue(dtoName);
		List<String> addList=getAddList(dtoName);
		for (String strAddClass : addList) {
			IAdd add =null;
			Constructor<?> constructorMethod=ReflectOperation.getConstructor(strAddClass,Object.class);
			
			if(constructorMethod!=null)
			{
				add = (IAdd)constructorMethod.newInstance(this.getBaseObject());
			}
			else
			{
				add = (IAdd)FrameworkFactory.CreateClass(strAddClass);
			}
			add.Add();
		}
	}

	/**
	 * @param tObject
	 * @param dtoName
	 * @param showInstance
	 * @throws Exception
	 
	 */
	private void processCard(Object tObject, String dtoName,ShowInstance showInstance) throws Exception
	{
		
		List<Field> cardList= ReflectOperation.getComponentCardList(dtoName, showInstance.getFieldListWithShowCard());
		for(Field field:cardList)
		{
			
			String cardDtoName=ReflectOperation.getDtoName(field);
			setDtoDefaultValue(cardDtoName);
			List<String> addList=getAddList(cardDtoName);
			
			Object cardObject=ReflectOperation.getValueWithShowCardField(field,tObject);
			if(cardObject!=null)
			{
				Field pkField=ReflectOperation.getPrimaryKeyField(cardDtoName);
				Object pkObj=ReflectOperation.getFieldValue(cardObject, pkField.getName());
				if(pkObj!=null && StringUtils.isBlank(pkObj.toString()))
				{
					ReflectOperation.setFieldNullValue(cardObject, pkField.getName(), pkField.getType());
				}
				for (String strAddClass : addList) {
					IAdd add = (IAdd)Class.forName(strAddClass).getConstructor(Object.class).newInstance(cardObject);
					add.Add();
				}
			}
				
		}
	}

	/**
	 * @param dtoName
	 * @param showInstance
	 * @throws Exception
	
	 */
	private void processList(Object tObject,String dtoName, ShowInstance showInstance)	throws Exception
	{
		List<ShowField> listShowConfigList=showInstance.getFieldListWithShowList();
		List<Field> fieldList=ReflectOperation.getComponentDetailList(dtoName, listShowConfigList);
		for (Field field : fieldList) 
		{
		    Collection<?> fieldValue=(Collection<?>)ReflectOperation.getFieldValue(tObject, field.getName());
			String detailDtoName=ReflectOperation.getGenericClass(field.getGenericType()).getName();
			setDtoDefaultValue(detailDtoName);
			List<String> addList=getAddList(detailDtoName);
			for (Object object : fieldValue) 
			{
				
				for (String strAddClass : addList) 
				{
					IAdd add = (IAdd)Class.forName(strAddClass).getConstructor(Object.class).newInstance(object);
					add.Add();
				}	
			}
		}
	}

}
