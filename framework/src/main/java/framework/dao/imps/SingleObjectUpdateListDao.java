package framework.dao.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import framework.helper.ReflectOperation;
import framework.services.interfaces.LogicParamManager;

public class SingleObjectUpdateListDao extends BaseVoidResultDao{


	@SuppressWarnings("unchecked")
	@Override
	public void voidResultExecute() throws Exception { // 删除和更新需要在同一个事务中处理
		//由于数据库查询出的数据再操作需要同一个连接，不同连接操作需要重新查询，为了避免重复查询，在同一DAO处理相关逻辑
	
		if(LogicParamManager.getDefaultValueMap() == null){
			List<Object> listObject=this.getHibernateTemplate().findByCriteria(LogicParamManager.getDetachedCriteria());
			if(listObject.size()>0)
			{
				this.getHibernateTemplate().deleteAll(listObject);
			}
			
			
			//this.getSession().clear();
			this.getHibernateTemplate().saveOrUpdateAll((Collection)LogicParamManager.getSaveObjectList());
		}
		else{
			
			List oldList = this.getHibernateTemplate().findByCriteria(LogicParamManager.getDetachedCriteria());
			Set<Object> newList = (Set<Object>)LogicParamManager.getSaveObjectList();
			
			List deleteList = new ArrayList<Object>();
			List tempList = new ArrayList<Object>();
			for(Object oldObject : oldList){
				boolean isExsit = true;
				for(Map.Entry<String, String> defaultValue : LogicParamManager.getDefaultValueMap().entrySet()){
					String fieldValue = ReflectOperation.getFieldValue(oldObject, defaultValue.getKey()).toString();
					if(defaultValue.getValue().indexOf(fieldValue) == -1){
						isExsit = false;
					}
				}
				if(isExsit){
					deleteList.add(oldObject);
				}
				else{
					tempList.add(oldObject);
				}
			}
			oldList = tempList;
			
			List logicDeleteList = new ArrayList<Object>();
			Set<Object> saveList = new HashSet<Object>();
			Set<Object> changeList = new HashSet<Object>();
			
			for(Object newObject : newList){
				boolean isExist = false;
				for(Object oldObject : oldList){
					if(isSameObject(oldObject,newObject,LogicParamManager.getDefaultValueMap())){
						changeList.add(oldObject);
						isExist = true;
						break;
					}
				}
				if(!isExist){
					saveList.add(newObject);
				}
			}
			
			for(Object oldObject : oldList){
				boolean isExist = false;
				for(Object newObject : newList){
					if(isSameObject(oldObject,newObject,LogicParamManager.getDefaultValueMap())){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					logicDeleteList.add(oldObject);
				}
			}

			for(Map.Entry<String, String> defaultValue : LogicParamManager.getDefaultValueMap().entrySet()){
				String[] values = defaultValue.getValue().split(",");
	            for(Object saveObject : saveList){
	            	ReflectOperation.setFieldValue(saveObject, defaultValue.getKey(), values[0]);
				}
	            for(Object deleteObject : logicDeleteList){
	            	 // lanyuesheng values只有一个值，则不用分割，不存在values[1], 如：strAllowState字段 在此加以判断
	            	if(values.length>1){
						ReflectOperation.setFieldValue(deleteObject, defaultValue.getKey(), values[1]);
	            	}else{
	            		ReflectOperation.setFieldValue(deleteObject, defaultValue.getKey(), values[0]);
	            	}
				}
			}
			
			this.getHibernateTemplate().deleteAll(deleteList);
            this.getHibernateTemplate().saveOrUpdateAll(logicDeleteList);
            this.getHibernateTemplate().saveOrUpdateAll(saveList);
		}
	}
	
	private boolean isSameObject(Object obj1,Object obj2,Map<String,String> defaultValueMap) throws Exception{
		List<Field> fieldList = ReflectOperation.getColumnFieldList(obj1.getClass());
		for(Field field : fieldList){
			if(!ReflectOperation.isPrimaryKeyField(field) && !defaultValueMap.containsKey(field.getName())){
				Object value1 = ReflectOperation.getFieldValue(obj1, field.getName());
				Object value2 = ReflectOperation.getFieldValue(obj2, field.getName());
				if(value1 == null && value2 == null){
					continue;
				}
				else if(value1 == null || value2 == null){
					return false;
				}
				if(ReflectOperation.isBaseType(field.getType())){
					if(!value1.equals(value2)){
						return false;
					}
				}
				else{
					if(!ReflectOperation.getPrimaryKeyValue(value1).equals(ReflectOperation.getPrimaryKeyValue(value2))){
						return false;
					}
				}
				
			}
		}
		
		return true;
	}

}
