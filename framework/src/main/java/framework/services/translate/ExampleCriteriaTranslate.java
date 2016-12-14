package framework.services.translate;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class ExampleCriteriaTranslate extends BaseConstructor implements ITranslate{
	
	public ExampleCriteriaTranslate(){
		super();
	}
	
	public ExampleCriteriaTranslate(Object baseObject){
		super(baseObject);
	}

	public void Translate() throws Exception {

		Object tObject = this.getBaseObject();

		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(tObject.getClass());
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}

		boolean allNullField = false;
		
		Field[] fields = ReflectOperation.getReflectFields(tObject.getClass());
    	for(int i=0;i<fields.length;i++){
    		if(!ReflectOperation.isIdListTargetField(tObject.getClass(), fields[i])){
    			if(fields[i].isAnnotationPresent(Column.class)){
    				if(fields[i].isAnnotationPresent(IColumn.class)){
    					IColumn iColumn = fields[i].getAnnotation(IColumn.class);
    					if(!StringUtils.isBlank(iColumn.tagMethodName()) && !iColumn.isSingleTagHidden()){
    						if(ReflectOperation.getFieldValue(tObject, fields[i].getName()) == null){
    							allNullField = true;
    							break;
    						}
    					}
    				}
    			}
    			else if(fields[i].isAnnotationPresent(JoinColumn.class)){
    				if(ReflectOperation.getFieldValue(tObject, fields[i].getName()) == null){
    					allNullField = true;
    					break;
    				}
    			}
    		}
		}
		
		if(allNullField){
			detachedCriteria.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(tObject.getClass()).getName(), null));
		}
		else{
			List<Field> fieldList = ReflectOperation.getColumnFieldList(tObject.getClass());
			for(Field field : fieldList){
				Object value = ReflectOperation.getFieldValue(tObject, field.getName());
				if(value != null && !StringUtils.isBlank(value.toString())){
					detachedCriteria.add(Restrictions.eq(field.getName(), value));
				}
			}
		}

		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}
}


