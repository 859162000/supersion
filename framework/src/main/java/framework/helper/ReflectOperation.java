package framework.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.w3c.dom.Element;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.ICondition;
import framework.interfaces.IEntity;
import framework.interfaces.ITab;
import framework.interfaces.TActionRule;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;

public class ReflectOperation {
	private static Map<String, String> dirtyDTOTableMap = new LinkedHashMap<String,String>();
	
	public static Class<?> getGenericClass(Type type) throws Exception{
		return (Class<?>)((ParameterizedType) type).getActualTypeArguments()[0];
	}
	
	public static Field getPrimaryKeyField(String tName) throws Exception{
		return getPrimaryKeyField(Class.forName(tName));
	}
	
	public static Field getPrimaryKeyField(Class<?> type){
		Field primaryKeyField = null;
		
		Field[] fieldList = getReflectFields(type);
		for(int i=0;i<fieldList.length;i++){
			if(fieldList[i].isAnnotationPresent(Id.class)){
				primaryKeyField = fieldList[i];
				break;
			}
		}
		
		return primaryKeyField;
		
	}
	public static List<Field> getComponentCardList(String tName,List<ShowField> cardShowConfigList) throws Exception
	{
		List<Field> returnList=new ArrayList<Field>(); 
		
		List<Field> joinColumnList=getJoinColumnFieldList(tName);
		List<Field> oneToManyList= getOneToManyField(tName);
		joinColumnList.addAll(oneToManyList);
		for(ShowField showField:cardShowConfigList)
		{
			for(Field f:joinColumnList)
			{
				if(f.getName().equals(showField.getFieldName()))
				{
					returnList.add(f);
					break;
				}
				
			}
			
		}
		
		return returnList;
	}
	
	/**
	 * <p>方法描述: 获取 ITab标签属性</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param tName
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-1 上午10:39:40</p>
	 */
	public static List<Field> getFieldTabList(String tName) throws Exception{
		Field[] fields = ReflectOperation.getReflectFields(Class.forName(tName));
    	List<Field> tabFields = new ArrayList<Field>();
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(ITab.class)){
            	tabFields.add(fields[i]);
			}
		}
    	return tabFields;
	}
	
	/**
	 * <p>方法描述:根据配置过滤Tab标签 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param tName
	 * @param tabShowConfigList
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-1 上午10:41:55</p>
	 */
	public static List<Field> getFieldTabList(String tName,List<ShowField> tabShowConfigList) throws Exception{
		List<Field> returnList=new ArrayList<Field>(); 
		List<Field> tabFields=getFieldTabList(tName);
		for(ShowField showField:tabShowConfigList)
		{
			for(Field f:tabFields)
			{
				if(f.getName().equals(showField.getFieldName()))
				{
					returnList.add(f);
					break;
				}
			}
		}
		return returnList;
	}
	
	public static List<Field> getComponentDetailList(String tName,List<ShowField> listShowConfigList) throws Exception
	{
		List<Field> returnList=new ArrayList<Field>(); 
		List<Field> onToManyList=getOneToManyField(tName);
		for(Field f:onToManyList)
		{
			for(ShowField showField:listShowConfigList)
			{
				if(f.getName().equals(showField.getFieldName()))
				{
					returnList.add(f);
					break;
				}
			}
		}
		return returnList;
	}
	public static Constructor<?> getConstructor(String className,Class<?>... parameterTypes)
	{
		Constructor<?> constructorMethod=null;
		try {
			constructorMethod=Class.forName(className).getConstructor(Object.class);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return constructorMethod;
	}
	public static Method getPrimaryKeyMethod(String tName) throws Exception{
		return getPrimaryKeyMethod(Class.forName(tName));
	}
	
	public static Method getPrimaryKeyMethod(Class<?> type) throws Exception{
		Method primaryKeyMethod = null;
		
		Method[] methodList = ReflectOperation.getReflectMethods(type);
		for(int i=0;i<methodList.length;i++){
			if(methodList[i].isAnnotationPresent(Id.class)){
				primaryKeyMethod = methodList[i];
				break;
			}
		}
		
		return primaryKeyMethod;
	}
	
	public static Object getPrimaryKeyValue(Object object) throws Exception{
		Field primaryKeyField = getPrimaryKeyField(object.getClass());
		PropertyDescriptor primaryKeyDescriptor = new PropertyDescriptor(primaryKeyField.getName(), object.getClass());
		return primaryKeyDescriptor.getReadMethod().invoke(object);
	}
	
	public static Object getPropertyDescriptorFieldValue(Object object,Field field) throws Exception{
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
		return propertyDescriptor.getReadMethod().invoke(object);
	}
	
	public static Object getFieldValue(Object object,String fieldName) throws Exception{
		String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		Method method = ReflectOperation.getReflectMethod(object.getClass(),methodName);
		return method.invoke(object);
	}
	
	@SuppressWarnings("unchecked")
	public static String getFieldShowValue(Object object,String fieldName) throws Exception{
		Object value = getFieldValue(object,fieldName);
		if(value == null){
			return "";
		}
		else if(isBaseType(value)){
			if(value.getClass().equals(Date.class)){
				return TypeParse.parseString((Date)value, "yyyy-MM-dd");
			}
			else{
				Field field = ReflectOperation.getReflectField(object.getClass(),fieldName);
				if(field.isAnnotationPresent(IColumn.class)){
					IColumn iColumn = field.getAnnotation(IColumn.class);
					if(!StringUtils.isBlank(iColumn.tagMethodName())){
						Method method = ReflectOperation.getReflectMethod(object.getClass(),iColumn.tagMethodName());
						Map<String,String> map = (Map<String,String>)method.invoke(object.getClass());
						return map.get(value.toString());
					}
					else{
						return value.toString();
					}
				}
				else{
					return value.toString();
				}
			}
		}
		else{
			Field keyNameField = getKeyNameField(value.getClass());
			Object keyNameFieldValue = getFieldValue(value,keyNameField.getName());
			return keyNameFieldValue.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object getFieldLevelValue(Object object,String fieldName, boolean isAutoDTO) throws Exception{
		while(fieldName.indexOf(".") > -1){
			String fieldName1 = fieldName.substring(0,fieldName.indexOf("."));
			object = getFieldValue(object,fieldName1);

			if( object instanceof java.lang.Iterable) {
				Set set = (Set<Object>)object;
				
				if(set.size() < 1) return "";
				object = set.toArray()[0];
			}
			
			fieldName = fieldName.substring(fieldName.indexOf(".") + 1);
		}

		object = getFieldValue(object, fieldName);

		if(isAutoDTO) {
			if(ReflectOperation.getDirtyDTOTableMap().get("AutoDTO_"+object.toString()) != null)
				return "";
			
			try {
				String targetTName = getAutoDTOTName("sessionFactory", object.toString());
				Class.forName(targetTName).newInstance();
			}
			catch(Exception ex){
				return "";
			}
		}
		
		return object;
	}
	
	public static void setFieldValue(Object object,String fieldName,Object value) throws Exception{
		String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		Method method = null;
		try{
			method = ReflectOperation.getReflectMethod(object.getClass(), methodName, value.getClass());
			method.invoke(object,value);
		}
		catch(Exception ex){
		    method = ReflectOperation.getReflectMethod(object.getClass(), methodName, String.class);
		    if(method != null){
		    	if(value!=null)
			    {
			    	method.invoke(object,String.valueOf(value));	
			    }
			    else
			    {
			    	method.invoke(object,new Object[] {null});
			    }
		    }	
		}
	}
	
	public static void setFieldNullValue(Object object,String fieldName,Class<?> type) throws Exception{
		String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		Method method = ReflectOperation.getReflectMethod(object.getClass(), methodName, type);
		method.invoke(object,new Object[] {null});
	}
	
	public static Object setPropertyDescriptorFieldValue(Object object,Field field,Object value) throws Exception{
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), object.getClass());
		return propertyDescriptor.getWriteMethod().invoke(object,value);
	}
	
	public static List<Field> getColumnFieldList(String tName) throws Exception{
		return getColumnFieldList(Class.forName(tName));
	}
	
    public static List<Field> getColumnFieldList(Class<?> type) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	List<Field> fieldList = new ArrayList<Field>();
    	for(int i=0;i<fields.length;i++){
			if(fields[i].isAnnotationPresent(Column.class)){
				fieldList.add(fields[i]);
			}
			else if(fields[i].isAnnotationPresent(JoinColumn.class)){
				fieldList.add(fields[i]);
			}
		}
    	return fieldList;
	}
    
    public static List<Field> getJoinColumnFieldList(String tName) throws Exception{
		return getJoinColumnFieldList(Class.forName(tName));
	}
	
    public static List<Field> getJoinColumnFieldList(Class<?> type) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	List<Field> fieldList = new ArrayList<Field>();
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(JoinColumn.class)){
				fieldList.add(fields[i]);
			}
		}
    	return fieldList;
	}
    
    public static List<Field> getIdListTargetFieldList(String tName) throws Exception{
		return getIdListTargetFieldList(Class.forName(tName));
	}
	
    public static List<Field> getIdListTargetFieldList(Class<?> type) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	List<Field> fieldList = new ArrayList<Field>();
 
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(IColumn.class)){
            	IColumn iColumn = fields[i].getAnnotation(IColumn.class);
            	if(iColumn.isIdListField()){
    				fieldList.add(ReflectOperation.getReflectField(type,iColumn.target()));
            	}
			}
		}

    	return fieldList;
	}
    
    public static List<Field> getHaveMethodNameList(Class<?> type) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	List<Field> fieldList = new ArrayList<Field>();
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(IColumn.class)){
            	IColumn iColumn = fields[i].getAnnotation(IColumn.class);
            	if(!StringUtils.isBlank(iColumn.tagMethodName())){
    				fieldList.add(fields[i]);
            	}
			}
		}
    	return fieldList;
	}
    
    public static List<Field> getHaveMethodNameList(String tName) throws Exception{
		return getHaveMethodNameList(Class.forName(tName));
	}
    
    public static boolean isIdListTargetField(Class<?> type, Field filed) throws Exception{
    	if(getIdListField(type, filed) == null){
    		return false;
    	}
    	else{
    		return true;
    	}
	}
    
    public static boolean isFileTargetField(Class<?> type, Field filed) throws Exception{
    	if(getFileField(type, filed) == null){
    		return false;
    	}
    	else{
    		return true;
    	}
	}
    
    public static Field getFieldByName(String tName,String fieldName) throws Exception{
		return getFieldByName(Class.forName(tName),fieldName);
	}
	
    public static Field getFieldByName(Class<?> type,String fieldName) throws Exception{
    	Field[] fields =  getReflectFields(type);
    	Field field = null;
    	for(int i=0;i<fields.length;i++){
            if(fields[i].getName().equals(fieldName)){
            	field = fields[i];
        		break;
			}
		}
    	return field;
	}
    
    public static Field getIdListField(String tName, Field targetField) throws Exception{
		return getIdListField(Class.forName(tName), targetField);
	}
	
    public static Field getIdListField(Class<?> type, Field targetField) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	Field idListField = null;
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(IColumn.class)){
            	IColumn iColumn = fields[i].getAnnotation(IColumn.class);
            	if(iColumn.isIdListField() && iColumn.target().equals(targetField.getName())){
            		idListField = fields[i];
            		break;
            	}
			}
		}
    	return idListField;
	}
    
    public static Field getFileField(Class<?> type, Field targetField) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	Field showFileField = null;
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(IColumn.class)){
            	IColumn iColumn = fields[i].getAnnotation(IColumn.class);
            	if(iColumn.isFileField() && iColumn.target().equals(targetField.getName())){
            		showFileField = fields[i];
            		break;
            	}
			}
		}
    	return showFileField;
	}
    
    public static Field getKeyNameField(String tName) throws Exception{
		return getKeyNameField(Class.forName(tName));
	}
	
    public static Field getKeyNameField(Class<?> type) throws Exception{
    	Field field = null;
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	for(int i=0;i<fields.length;i++){
			if(fields[i].isAnnotationPresent(IColumn.class)){
				IColumn iColumn = fields[i].getAnnotation(IColumn.class);
				if(iColumn.isKeyName() == true){
					field = fields[i];
					break;
				}
			}
		}
    	if(field == null){
    		return getPrimaryKeyField(type);
    	}
    	return field;
	}
    
    public static ShowInstance getInitDefaultShowInstance(String tName) throws Exception{
    	if(tName==null){
    		System.out.println();
    	}
		return getInitDefaultShowInstance(Class.forName(tName));
	}
    
    public static ShowInstance getInitDefaultShowInstance(Class<?> type) throws Exception{
    	
    	ShowInstance showInstance = new ShowInstance();

    	if(type.isAnnotationPresent(IEntity.class)){
    		IEntity iEntity = type.getAnnotation(IEntity.class);
    		if(!StringUtils.isBlank(iEntity.description())){
    			showInstance.setShowEntityName(iEntity.description());
			}
    		if(!StringUtils.isBlank(iEntity.navigationName())){
    			showInstance.setNavigationName(iEntity.navigationName());
			}
    	}
    	if(showInstance.getShowEntityName() == null){
    		showInstance.setShowEntityName(type.getSimpleName());
    	}
    	if(showInstance.getNavigationName() == null){
    		showInstance.setNavigationName(type.getSimpleName());
    	}
    	
    	
		Field[] fields = ReflectOperation.getReflectFields(type);
		for(int i=0;i<fields.length;i++){
			ShowField showField = new ShowField();
			
			if(fields[i].isAnnotationPresent(Column.class)){
				Column column = fields[i].getAnnotation(Column.class);
				showField.setFieldName(fields[i].getName());
				if(fields[i].isAnnotationPresent(IColumn.class)){
					IColumn iColumn = fields[i].getAnnotation(IColumn.class);
					if(!StringUtils.isBlank(iColumn.description())){
						showField.setFieldShowName(iColumn.description());
					}
					if(!StringUtils.isBlank(iColumn.tagMethodName())){
						showField.setFieldTargetPrimaryKey(showField.getFieldName());
					}
				}
				if(showField.getFieldShowName() == null){
					showField.setFieldShowName(column.name());
				}
				if(isFileTargetField(type, fields[i])){
					Field fileField = ReflectOperation.getFileField(type,  fields[i]);
					showField.setParamName(fileField.getName());
					showField.setSingleTag(ApplicationManager.getSingleTagFile());
				}
				else{
					if(isIdListTargetField(type, fields[i])){
						Field idListField = ReflectOperation.getIdListField(type,  fields[i]);
						showField.setFieldTargetPrimaryKey(idListField.getName());
						showField.setParamName(idListField.getName());
						showField.setSingleTag(ApplicationManager.getSingleTagMultipleSelect());
					}
					else{
						showField.setParamName(showField.getFieldName());
						
						if(fields[i].isAnnotationPresent(IColumn.class)){
							IColumn iColumn = fields[i].getAnnotation(IColumn.class);
							if(iColumn.isSystemDate() || iColumn.isSingleTagHidden()){
								showField.setSingleTag(ApplicationManager.getSingleTagHidden());
							}
						}
						if(StringUtils.isBlank(showField.getSingleTag())){
							if(showField.getFieldTargetPrimaryKey() != null){
								showField.setSingleTag(ApplicationManager.getSingleTagSelect());
							}
							else{
								if(fields[i].getType().equals(Date.class)){
									showField.setSingleTag(ApplicationManager.getSingleTagDate());
								}
								else{
									Column columnInfo = fields[i].getAnnotation(Column.class);
									if(columnInfo.length() >= 300){
										showField.setSingleTag(ApplicationManager.getSingleTagMultipleTextField());
									}
									else{
										showField.setSingleTag(ApplicationManager.getSingleTagTextfield());
									}
								}
							}
						}
					}
					if(ReflectOperation.isPrimaryKeyField(fields[i])){
						showField.setUpdateReadOnly(true);
					}
					if(!fields[i].isAnnotationPresent(GeneratedValue.class)){
						showField.setListVisible(true);
					}
					else{
						showField.setSingleTag(ApplicationManager.getSingleTagHidden());
					}
				}
			}
			else if(fields[i].isAnnotationPresent(JoinColumn.class)){
				JoinColumn joinColumn = fields[i].getAnnotation(JoinColumn.class);
				showField.setFieldName(fields[i].getName());
				if(isIdListTargetField(type, fields[i])){
					Field idListField = ReflectOperation.getIdListField(type,  fields[i]);
					showField.setFieldTargetPrimaryKey(idListField.getName());
					showField.setParamName(idListField.getName());
					Field keyNameField = ReflectOperation.getKeyNameField(fields[i].getType());
					if(keyNameField != null){
						showField.setFieldTargetPrimaryKeyName(keyNameField.getName());
						if(keyNameField.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = keyNameField.getAnnotation(IColumn.class);
							if(iColumn.description() != null && !iColumn.description().equals("")){
								showField.setFieldShowName(iColumn.description());
							}
						}
					}
					else{
						showField.setFieldTargetPrimaryKeyName(idListField.getName());
					}

					showField.setSingleTag(ApplicationManager.getSingleTagMultipleSelect());
				}
				else{					
					Field targetPrimaryField = ReflectOperation.getPrimaryKeyField(fields[i].getType());
					showField.setFieldTargetPrimaryKey(targetPrimaryField.getName());
					showField.setParamName(showField.getFieldName() + "." + targetPrimaryField.getName());
					Field keyNameField = ReflectOperation.getKeyNameField(fields[i].getType());
					if(keyNameField != null){
						showField.setFieldTargetPrimaryKeyName(keyNameField.getName());
						if(keyNameField.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = keyNameField.getAnnotation(IColumn.class);
							if(iColumn.description() != null && !iColumn.description().equals("")){
								showField.setFieldShowName(iColumn.description());
							}
						}
					}
					else{
						showField.setFieldTargetPrimaryKeyName(targetPrimaryField.getName());
					}
					
					if(fields[i].isAnnotationPresent(IColumn.class)){
						IColumn iColumn = fields[i].getAnnotation(IColumn.class);
						if(iColumn.isLoginTag() || iColumn.isSingleTagHidden()){
							showField.setSingleTag(ApplicationManager.getSingleTagHidden());
						}
						else{
							showField.setSingleTag(ApplicationManager.getSingleTagSelect());
						}
					}
					else{
						showField.setSingleTag(ApplicationManager.getSingleTagSelect());
					}
				}
				if(showField.getFieldShowName() == null){
					showField.setFieldShowName(joinColumn.name());
				}
				
				if(fields[i].isAnnotationPresent(IColumn.class)){
					IColumn joinIColumn = fields[i].getAnnotation(IColumn.class);
					if(!StringUtils.isBlank(joinIColumn.description())){
						showField.setFieldShowName(joinIColumn.description());
					}
				}
				
				showField.setListVisible(true);
			}
            else if(fields[i].isAnnotationPresent(OneToMany.class)){
            	if(isIdListTargetField(type, fields[i])){
    				showField.setFieldName(fields[i].getName());
    				Field idListField = ReflectOperation.getIdListField(type, fields[i]);
    				IColumn idListFieldColumn = idListField.getAnnotation(IColumn.class);
    				Field targetField = ReflectOperation.getFieldByName(ReflectOperation.getGenericClass(fields[i].getGenericType()), idListFieldColumn.mappedBy());
    				showField.setFieldTargetPrimaryKey(idListField.getName());
    				showField.setParamName(idListField.getName());
    				
    				Field keyNameField = ReflectOperation.getKeyNameField(targetField.getType());
    				if(keyNameField != null){
    					if(keyNameField.isAnnotationPresent(IColumn.class)){
    						IColumn keyNameColumn = keyNameField.getAnnotation(IColumn.class);
    						if(keyNameColumn.description() != null && !keyNameColumn.description().equals("")){
    							showField.setFieldShowName(keyNameColumn.description());
    						}
    					}
    				}

    				if(showField.getFieldShowName() == null){
    					showField.setFieldShowName(fields[i].getName());
    				}
    				
					showField.setSingleTag(ApplicationManager.getSingleTagMultipleSelect());
    				
            	}
			}
            else if(fields[i].isAnnotationPresent(IColumn.class)){
            	IColumn iColumn = fields[i].getAnnotation(IColumn.class);
            	if(iColumn.isListShow()){
            		showField.setFieldName(fields[i].getName());
					if(!StringUtils.isBlank(iColumn.description())){
						showField.setFieldShowName(iColumn.description());
					}
					else{
						showField.setFieldShowName(fields[i].getName());
					}
					showField.setSingleTag(ApplicationManager.getSingleTagHidden());
					showField.setListVisible(true);
					showField.setParamName(fields[i].getName());
				}
			}
			
			if(showField.getFieldName() != null){
				showInstance.getShowFieldList().add(showField);
			}
		}
		showInstance.setShowInstanceName(ApplicationManager.getDefaultInstanceName(type.getName()));
		
		return showInstance;
	}
    
    public static ShowInstance getDefaultShowInstance(String tName) throws Exception{
		return getDefaultShowInstance(Class.forName(tName));
	}
    
    public static ShowInstance getDefaultShowInstance(Class<?> type) throws Exception{
    	
    	ShowInstance showInstance = null;
    	
    	List<ShowInstance> showInstanceList = ShowContext.getInstance().getShowInstanceMap().get(ApplicationManager.getShowInstanceListName(type.getName()));
		if(showInstanceList == null){
			showInstanceList = new ArrayList<ShowInstance>();
		}
		
	    for(ShowInstance tempShowInstance : showInstanceList){
	    	if(tempShowInstance.getShowInstanceName().equals(ApplicationManager.getDefaultInstanceName(type.getName()))){
	    		showInstance = tempShowInstance;
	    		break;
	    	}
	    }
	    
		if(showInstance != null){
			return showInstance;
		}

		showInstance = getInitDefaultShowInstance(type);
		
		showInstanceList.add(showInstance);
		ShowContext.getInstance().getShowInstanceMap().put(ApplicationManager.getShowInstanceListName(type.getName()), showInstanceList);
		
		return showInstance;
	}
    
    public static ShowInstance getShowInstance(Class<?> type, String showInstanceName) throws Exception{
		
    	ShowInstance showInstance = null;
    	
    	List<ShowInstance> showInstanceList = ShowContext.getInstance().getShowInstanceMap().get(type.getName());
		if(showInstanceList == null){
			return getDefaultShowInstance(type);
		}
		
	    for(ShowInstance tempShowInstance : showInstanceList){
	    	if(tempShowInstance.getShowInstanceName().equals(showInstanceName)){
	    		showInstance = tempShowInstance;
	    		break;
	    	}
	    }
	    
		if(showInstance == null){
			return getDefaultShowInstance(type);
		}
		
    	return showInstance;
	}
    
    public static ShowInstance getShowInstance(String tName, String showInstanceName) throws Exception{
    	return getShowInstance(Class.forName(tName), showInstanceName);
	}
    
    public static boolean isPrimaryKeyField(Field field) throws Exception{
    	return field.isAnnotationPresent(Id.class);
    }
    
    public static boolean isKeyNameField(Field field) throws Exception{
    	boolean isKeyNameField = false;
    	if(field.isAnnotationPresent(IColumn.class)){
    		IColumn iColumn = field.getAnnotation(IColumn.class);
    		if(iColumn.isKeyName()){
    			isKeyNameField = true;
    		}
    	}
    	return isKeyNameField;
    }
  
    public static boolean isSpecialCharCheck(Field field) throws Exception{
    	boolean isSpecCharCheck = false;
    	if(field.isAnnotationPresent(IColumn.class)){
    		IColumn iColumn = field.getAnnotation(IColumn.class);
    		if(iColumn.isSpecialCharCheck()){
    			isSpecCharCheck = true;
    		}
    	}
    	return isSpecCharCheck;
    }
    
    public static void CopyColumnFieldValue(Object objectSource,Object objectDestination) throws Exception{
    	List<Field> columnFieldList = getColumnFieldList(objectDestination.getClass());
    	for(Field field : columnFieldList){
    		Object value = getFieldValue(objectSource, field.getName());
    		if(value != null){
        		setFieldValue(objectDestination, field.getName(), value);
    		}
    	}
    }
    
    public static void CopyFieldValue(Object objectSource,Object objectDestination) throws Exception{
    	Field[] fieldList = ReflectOperation.getReflectFields(objectDestination.getClass());
    	for(Field field : fieldList){
    		if(!isSerialVersionUIDField(field.getName()) && !isCollectionType(field.getType().getName())){
    			/**
    			 * 20161116
    			 * zhouqin
    			 * 排除没get,set方法的字段
    			 */
    			String getMethodName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
    			Method getMethod = ReflectOperation.getReflectMethod(objectDestination.getClass(),getMethodName);
    			if(null!=getMethod){
    				Object value = getFieldValue(objectSource, field.getName());
    				
    				String setMethodName = "set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
        			if(value != null){
        				Method setMethod = getReflectMethod(objectDestination.getClass(),setMethodName,value.getClass());
                		if(null!=setMethod){
                			setFieldValue(objectDestination, field.getName(), value);
                		}else{
                			setMethod = ReflectOperation.getReflectMethod(objectDestination.getClass(), setMethodName, String.class);
                			if(null!=setMethod){
                				setFieldValue(objectDestination, field.getName(), value);
                			}
                		}
        			}    				
    			}    			
    		}
    	}
    }
    
    public static boolean isAllNullField(Object object) throws Exception{
    	boolean isAllNullField = true;
    	Field[] fields = ReflectOperation.getReflectFields(object.getClass());
    	for(int i=0;i<fields.length;i++){
    		if(isSerialVersionUIDField(fields[i].getName()) || isCollectionType(fields[i].getType().getName())){
    			continue;
    		}
    		Object value = getFieldValue(object, fields[i].getName());
    		if(value != null){
    			isAllNullField = false;
    			break;
    		}
    	}
    	return isAllNullField;
    }
    
    public static List<ShowFieldCondition> getShowFieldConditionList(Class<?> type,List<ShowFieldValue> showFieldValueList) throws Exception{
    	
    	List<ShowFieldCondition> showFieldConditionList = new ArrayList<ShowFieldCondition>();
    	
    	if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) == null){ // 没有条件类的
    		Field primaryKeyField = ReflectOperation.getPrimaryKeyField(type);
    		Field keyNameField = ReflectOperation.getKeyNameField(type);
    		if(primaryKeyField!=null)
    		{
    			for(ShowFieldValue showFieldValue : showFieldValueList){
        			if(showFieldValue.getShowField().getSingleTag() == ApplicationManager.getSingleTagTextfield() || showFieldValue.getShowField().getSingleTag().equals(ApplicationManager.getModelField())
        					|| showFieldValue.getShowField().getSingleTag() == ApplicationManager.getSingleTagSelect()){
        				if(showFieldValue.getShowField().getFieldName().equals(primaryKeyField.getName()) || (keyNameField != null && showFieldValue.getShowField().getFieldName().equals(keyNameField.getName())) || showFieldValue.getShowField().getFieldTargetPrimaryKey() != null){
        					ShowFieldCondition showFieldCondition = new ShowFieldCondition();
        					showFieldCondition.setFieldName(showFieldValue.getShowField().getFieldName());
        					showFieldCondition.setFieldShowName(showFieldValue.getShowField().getFieldShowName());
        					showFieldCondition.setTarget(showFieldValue.getShowField().getFieldTargetPrimaryKey());
        					showFieldCondition.setTag(showFieldValue.getTag());
        					showFieldCondition.setParamName(showFieldValue.getShowField().getParamName());
        					showFieldCondition.setSingleTag(showFieldValue.getShowField().getSingleTag());
        					showFieldCondition.setMultipleSelect(showFieldValue.getShowField().isMultipleSelect());
        					showFieldConditionList.add(showFieldCondition);
        				}

        			}
        		}

    		}
        	}
    	else{ // 有条件类
    		showFieldConditionList = getShowFieldConditionList(type);
    		for(ShowFieldCondition showFieldCondition : showFieldConditionList){
    			for(ShowFieldValue showFieldValue : showFieldValueList){
    				if(showFieldCondition.getTarget().equals(showFieldValue.getShowField().getFieldName())){
    					
    					if(showFieldCondition.getFieldName().equals(showFieldCondition.getTarget()) && !showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagHidden())){
    						if(showFieldCondition.isVisible() && showFieldValue.getShowField().getSingleTag().equals(ApplicationManager.getSingleTagHidden())){
    						}
    						else{
    							showFieldCondition.setSingleTag(showFieldValue.getShowField().getSingleTag());
    						}
    					}
    					else if(ReflectOperation.getReflectField(type,showFieldValue.getShowField().getFieldName()).getType().equals(Date.class) || ReflectOperation.getReflectField(type,showFieldValue.getShowField().getFieldName()).getType().equals(Timestamp.class)){
    						if(!showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagHidden())){
        						showFieldCondition.setSingleTag(ApplicationManager.getSingleTagDate());
    						}
    					}
    					
    					if(StringUtils.isBlank(showFieldCondition.getFieldShowName())){
    						showFieldCondition.setFieldShowName(showFieldValue.getShowField().getFieldShowName());
    					}
    					
    					String paramName = null;
    					if(showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagSelect())
    							|| showFieldCondition.getSingleTag().equals(ApplicationManager.getModelField())){
    						showFieldCondition.setTag(showFieldValue.getTag());
    						Field targetField=ReflectOperation.getReflectField(type,showFieldValue.getShowField().getFieldName());
    						if(ReflectOperation.isBaseType(targetField.getType())){
    							paramName = showFieldCondition.getFieldName();
    						}
    						else{
    							Field  targetFieldField=ReflectOperation.getReflectField(targetField.getType(),showFieldCondition.getTargetField());
    							if(targetFieldField!=null&& !isBaseType(targetFieldField.getType()))
    							{
    								showFieldCondition.setTag(showFieldValue.getTag());
    								Field pkField=ReflectOperation.getPrimaryKeyField(targetFieldField.getType());
    								paramName = showFieldCondition.getFieldName() + "." +showFieldCondition.getTargetField()+"."+ pkField.getName();
    							}
    							else
    							{
    								showFieldCondition.setTargetField(showFieldValue.getShowField().getFieldTargetPrimaryKey());
        							paramName = showFieldCondition.getFieldName() + "." +showFieldCondition.getTargetField();
    							}
    							
    						}
    					}
    					else if(showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagTextfield())
						|| showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagDate())
						|| showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagDateNoSlash())
						|| showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagDateMonth())
						|| showFieldCondition.getSingleTag().equals(ApplicationManager.getSingleTagDateHMS())){
    						if(showFieldCondition.getTargetField() == null){
        						paramName = showFieldCondition.getFieldName();
    						}
    						else{
    							paramName = showFieldCondition.getFieldName() + "." +showFieldCondition.getTargetField();
    						}
    					}
    					showFieldCondition.setParamName(paramName);
    					
    					break;
    				}
    			}
    		}
    	}

    	return showFieldConditionList;
    }
    
    public static List<ShowFieldCondition> getShowFieldConditionList(String tName,List<ShowFieldValue> showFieldValueList) throws Exception{
    	return getShowFieldConditionList(Class.forName(tName), showFieldValueList);
    }
    
    public static List<ShowFieldCondition> getShowFieldConditionList(Class<?> type) throws Exception{
    	List<ShowFieldCondition> showFieldConditionList = new ArrayList<ShowFieldCondition>();
    	if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) != null){
    		Field[] fieldList = ReflectOperation.getReflectFields(Class.forName(TActionRule.getConditionClassName(type.getName())));
    		for(int i=0;i<fieldList.length;i++){
    			
    			if(fieldList[i].getName().equals("serialVersionUID"))
    			{
    				continue;
    			}
    			
				ShowFieldCondition showFieldCondition = new ShowFieldCondition();
				showFieldCondition.setFieldName(fieldList[i].getName());
    			
    			if(fieldList[i].isAnnotationPresent(ICondition.class)){
    				ICondition iCondition = fieldList[i].getAnnotation(ICondition.class);
       				showFieldCondition.setVisible(iCondition.isVisible());
       				showFieldCondition.setOrder(iCondition.order());
       				showFieldCondition.setASC(iCondition.isASC());
       				showFieldCondition.setComparison(iCondition.comparison());
       				if(!StringUtils.isBlank(iCondition.description())){
       					showFieldCondition.setFieldShowName(iCondition.description());
       				}
    				if(!StringUtils.isBlank(iCondition.target())){
    					showFieldCondition.setTarget(iCondition.target());
    					if(!StringUtils.isBlank(iCondition.targetField())){
    						showFieldCondition.setTargetField(iCondition.targetField());
        				}
    				}
    			}
    			else{
    				showFieldCondition.setVisible(true);
    			}
    			
    			if(showFieldCondition.getTarget() == null){
    				showFieldCondition.setTarget(showFieldCondition.getFieldName());
    			}
    			if(showFieldCondition.getTargetField() != null){
    				ShowInstance showInstance = getShowInstance(fieldList[i].getType(), ShowParamManager.getShowInstanceName());
    			    for(ShowField showField	: showInstance.getShowFieldList()){
    			    	if(showField.getFieldName().equals(showFieldCondition.getTargetField())){
    			    		//优先使用条件字段的字段显示名称
    			    		if(StringUtils.isBlank(showFieldCondition.getFieldShowName()))
    			    		{
    			    			showFieldCondition.setFieldShowName(showField.getFieldShowName());
    			    		}
    			    		
    			    		showFieldCondition.setSingleTag(showField.getSingleTag());
    			    		break;
    			    	}
    			    }
				}
    			
    			if(showFieldCondition.isVisible()){
    				if(isBaseType(fieldList[i].getType())){
    					if(fieldList[i].getType().equals(Date.class) || fieldList[i].getType().equals(Timestamp.class)){
    						showFieldCondition.setSingleTag(ApplicationManager.getSingleTagDate());
    					}
    					else{
    						if(ReflectOperation.getReflectField(type,showFieldCondition.getTarget()).isAnnotationPresent(IColumn.class)){
    							IColumn column = ReflectOperation.getReflectField(type,showFieldCondition.getTarget()).getAnnotation(IColumn.class);
    							if(StringUtils.isBlank(column.tagMethodName())){
    								showFieldCondition.setSingleTag(ApplicationManager.getSingleTagTextfield());
    							}
    							else{
    								showFieldCondition.setSingleTag(ApplicationManager.getSingleTagSelect());
    							}
    						}
    						else{
    							showFieldCondition.setSingleTag(ApplicationManager.getSingleTagTextfield());
    						}
    					}

    				}
    				else{
    					if(showFieldCondition.getTargetField() == null){
    						showFieldCondition.setSingleTag(ApplicationManager.getSingleTagSelect());
    					}
    					//else{
    					//	showFieldCondition.setSingleTag(ApplicationManager.getSingleTagTextfield());
    					//}
    				}
    			}
    			else{
    				showFieldCondition.setSingleTag(ApplicationManager.getSingleTagHidden());
    			}
    			
    			ShowInstance showInstance = getShowInstance(type, ShowParamManager.getShowInstanceName());
    			for(ShowField showField	: showInstance.getShowFieldList()){
			    	if(showField.getFieldName().equals(showFieldCondition.getFieldName()) || showField.getFieldName().equals(showFieldCondition.getTargetField())){
			    		showFieldCondition.setMultipleSelect(showField.isMultipleSelect());
			    		break;
			    	}
			    }
				showFieldConditionList.add(showFieldCondition);
    		}
    	}
    	return showFieldConditionList;
    }
    
    public static boolean isBaseType(Object object){
    	Class<?> type = object.getClass();
    	return isBaseType(type);
    }
    
    public static boolean isBaseType(Class<?> type){
    	if(type.equals(String.class) || type.equals(Integer.class) || type.equals(java.math.BigInteger.class) || type.equals(java.sql.Time.class) || type.equals(Long.class) || type.equals(Float.class) || type.equals(Double.class) || type.equals(BigDecimal.class) ||  type.equals(Date.class)|| type.equals(java.sql.Date.class)|| type.equals(Timestamp.class) || type.equals(java.sql.Timestamp.class)){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isCollectionType(String typeName){
    	if(typeName.equals("java.util.Set") || typeName.equals("java.util.List")){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isSerialVersionUIDField(String fieldName){
    	if(fieldName.equals("serialVersionUID")){
    		return true;
    	}
    	return false;
    }

    public static List<Field> getOneToManyField(String tName) throws Exception{
		return getOneToManyField(Class.forName(tName));
	}
	
	public static List<Field> getOneToManyField(Class<?> type){
		List<Field> oneToManyField = new ArrayList<Field>();
		
		Field[] fieldList = ReflectOperation.getReflectFields(type);
		for(int i=0;i<fieldList.length;i++){
			if(fieldList[i].isAnnotationPresent(OneToMany.class)){
				oneToManyField.add(fieldList[i]);
			}
		}
		
		return oneToManyField;
	}
	
	public static Object getOneToManySize(Object object, String fieldName) throws Exception {
		Object objectField = getFieldValue(object, fieldName);
		String methodName = "size";
		Method method = ReflectOperation.getReflectMethod(objectField.getClass(),methodName);
		return method.invoke(objectField);
	}
	
	// 找出树的上一级属性字段
	public static Field getTreeField(String tName) throws Exception{
		return getTreeField(Class.forName(tName));
	}
	
	public static Field getTreeField(Class<?> type){
		Field treeField = null;
		
		Field[] fieldList = ReflectOperation.getReflectFields(type);
		for(int i=0;i<fieldList.length;i++){
			if(fieldList[i].getType() == type) {
				treeField = fieldList[i];
				break;
			}
		}
		
		return treeField;
		
	}

	public static Map<String, String> getDirtyDTOTableMap() {
		return dirtyDTOTableMap;
	}
	
	// 取配置中指定sessionFactory的第一个DTO扫描路径
	public static String getAutoDtoPath(String sessionFactory) {
		/*if(appRootPath == null || appRootPath.isEmpty())
			appRootPath = ServletActionContext.getServletContext().getRealPath("/");
		String strClassPath = appRootPath + "WEB-INF/classes/";*/
		
		String strClassPath = "";
		if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
			strClassPath = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
		}
		else{
			strClassPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(0).replace("%20", " ");
		}

		/*SessionFactoryImpl annotationSessionFactoryBean = (SessionFactoryImpl)FrameworkFactory.CreateBean(sessionFactory);
		//AnnotationSessionFactoryBean annotationSessionFactoryBean = (AnnotationSessionFactoryBean)FrameworkFactory.CreateBean("sessionFactory");
		
		try {
			List<String> list = (List<String>)ReflectOperation.getFieldValue(annotationSessionFactoryBean, "packagesToScan");
			if(list.size() > 0){
				String strDTOPath = "";
				String[] strArray = list.get(0).split("[.]");
				for(String strDir : strArray)
					strDTOPath += strDir + '/';
				
				return strClassPath + strDTOPath;
			}
		} catch (Exception e) {
			return null;
		}*/
		
		
		Element beansRootElement = null;
		if(ShowContext.getInstance().getShowEntityMap().get("sessionFactoryBeanPath")!=null){
			for (Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("sessionFactoryBeanPath").entrySet()) {
				if(entry.getKey()!=null){
					beansRootElement = DomXMLOperation.getElementByName(entry.getKey(), "beans");
				}else{
					beansRootElement = DomXMLOperation.getElementByName("extend/configs/dao.extend.applicationContext.xml", "beans");
				}
			}
		}
		else{
			beansRootElement = DomXMLOperation.getElementByName("extend/configs/dao.extend.applicationContext.xml", "beans");
		}
		Element[] beansElement = DomXMLOperation.getElementsByName(beansRootElement, "bean");
		for(int i=0;i<beansElement.length;i++){
			if(beansElement[i].getAttribute("id").equals(sessionFactory)) {
				Element[] propertyElements = DomXMLOperation.getElementsByName(beansElement[i], "property");
				for(int j=0;j<propertyElements.length;j++){
					if(propertyElements[j].getAttribute("name").equals("packagesToScan")) {
						Element list = DomXMLOperation.getElementByName(propertyElements[j], "list");
						String value = DomXMLOperation.getElementsByName(list, "value")[0].getTextContent();
						
						String strDTOPath = "";
						String[] strArray = value.split("[.]");
						for(String strDir : strArray)
							strDTOPath += strDir + '/';
						
						return strClassPath + strDTOPath;
					}
				}
			}
		}
		
		
		return null;
	}
	
	
	
	
	public static Field[] getReflectFields(Class<?> type){
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = type.getDeclaredFields();
		for(Field field : fields){
			fieldList.add(field);
		}
		while(type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)){
			type = type.getSuperclass();
			Field[] tempFields = type.getDeclaredFields();
			for(Field field : tempFields){
				fieldList.add(field);
			}
			
		}
		return fieldList.toArray(new Field[]{});
	}
	
    public static Field getReflectField(Class<?> type, String fieldName) throws Exception{
		Field field  = null;
		try{
			field = type.getDeclaredField(fieldName);
		}
		catch(Exception ex){
		}
		
		if(field == null){
			while(type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)){
				type = type.getSuperclass();
				try{
					field = type.getDeclaredField(fieldName);
				}
				catch(Exception ex){
					
				}
				if(field != null){
					break;
				}
			}
		}
    	return field;
	}
    
    public static Field getReflectFieldIgnorCase(Class<?> type, String fieldName) throws Exception{
        Field field = null;
    	for(Field tempField : getReflectFields(type)){
        	if(tempField.getName().toUpperCase().equals(fieldName.toUpperCase())){
        		field = tempField;
        		break;
        	}
        }
    	return field;
	}
    
    public static Method[] getReflectMethods(Class<?> type) throws Exception{
    	List<Method> methodList = new ArrayList<Method>();
    	Method[] methods = type.getDeclaredMethods();
		for(Method method : methods){
			methodList.add(method);
		}
		while(type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)){
			type = type.getSuperclass();
			Method[] tempMethods = type.getDeclaredMethods();
			for(Method field : tempMethods){
				methodList.add(field);
			}
			
		}
		return methodList.toArray(new Method[]{});
	}
    
    public static Method getReflectMethod(Class<?> type, String methodName) throws Exception{
    	Method method  = null;
    	try{
    		method  = type.getDeclaredMethod(methodName);
    	}
    	catch(Exception ex){
    		System.out.println();
    	}
		if(method == null){
			while(type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)){
				type = type.getSuperclass();
				try{
		    		method  = type.getDeclaredMethod(methodName);
		    	}
		    	catch(Exception ex){
		    		
		    	}
				if(method != null){
					break;
				}
			}
		}
    	return method;
	}
    
    public static Method getReflectMethod(Class<?> type, String methodName,Class<?> valueClass) throws Exception{
    	Method method  = null;
    	try{
    		method  = type.getDeclaredMethod(methodName,valueClass);
    	}
    	catch(Exception ex){
    		
    	}
		if(method == null){
			while(type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)){
				type = type.getSuperclass();
				try{
		    		method  = type.getDeclaredMethod(methodName,valueClass);
		    	}
		    	catch(Exception ex){
		    		
		    	}
				if(method != null){
					break;
				}
			}
		}
    	return method;
	}
	
	//根据表取DTO名："package.dto.Autodto_xxx"
	public static String getAutoDTOTName(String sessionFactory, String tableName){

		String tName = null;
		Integer index = 0;
		
		Map<String, String> constMap = ShowContext.getInstance().getShowEntityMap().get("GetNamespaceByEntityName");
		if(constMap!=null)
		{
			String prefix=tableName;
			int firstLineIndex =tableName.indexOf("_");
			if(firstLineIndex>0)
			{
				prefix=tableName.substring(0,firstLineIndex+1);
			}
			tName=constMap.get(prefix);
			if(tName!=null)
			{
				tName=tName+tableName;
				Object object = FrameworkFactory.CreateClass(tName);
				if(object==null)
				{
					tName=null;
				}
				
			}
			
			
		}
		if(tName==null)
		{
			BeanDefinition beanDefine=BeanFactoryUtils.getInstance().getBeanDefine(sessionFactory);
			 MutablePropertyValues pvs = beanDefine.getPropertyValues();
			 PropertyValue pv = pvs.getPropertyValue("packagesToScan");
			 if(pv!=null)
			 {
				 Object pvValue=pv.getValue();
				 if(pvValue!=null && pvValue instanceof List<?>)
				 {
					 List<Object> listDto=(List<Object>)pvValue;
					 for(Object obj:listDto)
					 {
						 TypedStringValue tsv=(TypedStringValue)obj;
						 String packageName=tsv.getValue();
						 
						 tName = packageName + ".AutoDTO_" + tableName;
						 Object object = FrameworkFactory.CreateClass(tName);
						 if(object != null){
								break;
						 }
						 else
							tName =null;
					 }
					 
				 } 
			 }
		}
		return tName==null?"":tName;
		
		
	}
	
	public static boolean isCollectionFieldWithShowCard(Field field)
	{
		boolean result=false;
		if(field.getAnnotation(OneToMany.class) != null)
		{
			result=true;
		}
		else if(field.getAnnotation(JoinColumn.class)==null)
		{
			result=false;
		}
		return result;
	}
    public static String getDtoName(Field field) throws Exception
    {
    	boolean isCollection=isCollectionFieldWithShowCard(field);
    	String result;
    	if(isCollection)
    	{
    		result=ReflectOperation.getGenericClass(field.getGenericType()).getName();
    	}
    	else
    	{
    		result= field.getType().getName();
    	}
    	return result;
    	
    }
    public static Object getValueWithShowCardField(Field field,Object obj) throws Exception
    {
    	boolean isCollection=isCollectionFieldWithShowCard(field);
    	Object result=null;
    	if(isCollection)
    	{
    		Collection<?> fieldValue=(Collection<?>)ReflectOperation.getFieldValue(obj, field.getName());
    		if(fieldValue.size()>0)
    		{
    			for(Object objVal:fieldValue)
    			{
    				result=objVal;
    				break;
    			}
    			
    		}
    		
    	}
    	else
    	{
    		result=ReflectOperation.getFieldValue(obj, field.getName());
    	}
    	return result;
    }

	/**
	 * <p>方法描述: 获取实体注解所对应的表名（不包含schema）</p>
	 * <p>方法备注: </p>
	 * @param clazz
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-4-29 上午9:03:55</p>
	 */
	public static String getTableName(Class clazz) {
        Table annotation = (Table)clazz.getAnnotation(Table.class);
        if(annotation != null){
            return annotation.name();
        }
        return null;
    }
}
