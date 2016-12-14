package framework.dao.imps;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;

public class SingleObjectUpdateDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(RequestManager.getTOject());
	}
	
    public void voidResultExecute(Object[] objects) throws Exception {
    	voidResultExecute(objects[0]);
	}
    
    @SuppressWarnings("unchecked")
	public void voidResultExecute(Object tObject) throws Exception {
		List<Field> idListTargetFieldList = ReflectOperation.getIdListTargetFieldList(tObject.getClass());
		if(idListTargetFieldList.size() > 0){
			Object sessionObject = this.getHibernateTemplate().get(tObject.getClass(), (Serializable)ReflectOperation.getPrimaryKeyValue(tObject));
			for(Field idListTargetField : idListTargetFieldList){
				if(ReflectOperation.isCollectionType(idListTargetField.getType().getName())){
					Set<Object> value = (Set<Object>)ReflectOperation.getFieldValue(sessionObject, idListTargetField.getName());
					value.clear();
					Set<Object> insertValue = (Set<Object>)ReflectOperation.getFieldValue(tObject,idListTargetField.getName());
	                for(Object object : insertValue){
	                	value.add(object);
	                }
				}
			}

			ReflectOperation.CopyColumnFieldValue(tObject, sessionObject);

			this.getHibernateTemplate().flush();
		}
		else{
			this.getHibernateTemplate().update(tObject);
		}
	}

}
