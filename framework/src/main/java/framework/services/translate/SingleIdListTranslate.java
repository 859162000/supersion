package framework.services.translate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.persistence.OneToMany;

import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ITranslate;

public class SingleIdListTranslate  extends BaseConstructor  implements ITranslate{
	
	public SingleIdListTranslate(){
		super();
	}
	
	public SingleIdListTranslate(Object baseObject){
		super(baseObject);
	}

	@SuppressWarnings("unchecked")
	public void Translate() throws Exception {
		
		Object tObject = this.getBaseObject();
		
		List<Field> idListTargetFieldList = ReflectOperation.getIdListTargetFieldList(tObject.getClass());
		for(Field idListTargetField : idListTargetFieldList){
			if(ReflectOperation.isCollectionType(idListTargetField.getType().getName())){
				Class<?> type = ReflectOperation.getGenericClass(idListTargetField.getGenericType());
				Field idListField = ReflectOperation.getIdListField(tObject.getClass(), idListTargetField);
				IColumn idListFieldColumn = idListField.getAnnotation(IColumn.class);
				Field targetField = ReflectOperation.getFieldByName(type,idListFieldColumn.mappedBy());
				
				Set<Object> fieldValueSet = (Set<Object>)ReflectOperation.getFieldValue(tObject, idListTargetField.getName());
				String[] idList = (String[])ReflectOperation.getFieldValue(tObject, idListField.getName());
				
				Field primaryKeyField = ReflectOperation.getPrimaryKeyField(targetField.getType());
				OneToMany oneToMany = idListTargetField.getAnnotation(OneToMany.class);
				String mappedBy = oneToMany.mappedBy();
				for(int i=0;i<idList.length;i++){
					Object fieldValue = type.newInstance();
					Object value = targetField.getType().newInstance();
					ReflectOperation.setFieldValue(value, primaryKeyField.getName(), idList[i]);
					ReflectOperation.setFieldValue(fieldValue, targetField.getName(), value);
					ReflectOperation.setFieldValue(fieldValue, mappedBy, tObject);
					fieldValueSet.add(fieldValue);
				}
			}
		}
		
	}

}
