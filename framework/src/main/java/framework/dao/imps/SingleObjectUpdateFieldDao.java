package framework.dao.imps;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import framework.helper.ReflectOperation;

public class SingleObjectUpdateFieldDao extends BaseVoidResultDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0].toString(),objects[1].toString(),objects[2].toString(),objects[3].toString(),objects[4],objects[5],objects[6],(List<Boolean>)objects[7],(DetachedCriteria)objects[8],(Map<String,String>)objects[9]);
	}

	@SuppressWarnings("unchecked")
	public void voidResultExecute(String tName,String judgeFieldName,String fieldName,String strValue,Object requsetIdList,Object requsetId,Object requestTObject,List<Boolean> checkResultList,DetachedCriteria detachedCriteria,Map<String,String> updateValueMap) throws Exception{
       
		Class<?> type = Class.forName(tName);

		if(detachedCriteria != null){
			List<Object> objectList = this.getHibernateTemplate().findByCriteria(detachedCriteria);
			for(int i=0;i<objectList.size();i++){
				Object object = objectList.get(i);
				Object judgeValue = ReflectOperation.getFieldLevelValue(object, judgeFieldName, false);
				if(strValue.indexOf(",") > -1){ // 多个字段需要更新
					String[] fieldValues = strValue.split(",");
					for(int j = 0;j<fieldValues.length;j++){
						UpdateObject(object,judgeFieldName,fieldName,judgeValue,fieldValues[j],checkResultList.get(i),updateValueMap);
					}
				}
				else{ // 单个字段更新
					UpdateObject(object,judgeFieldName,fieldName,judgeValue,strValue,checkResultList.get(i),updateValueMap);
				}
			}
		}
		else if(requsetIdList != null){ // 删除多项
			Object[] idList = (Object[]) requsetIdList;
			for(int i=0;i<idList.length;i++){
				Object object = this.getHibernateTemplate().get(type, (Serializable)idList[i]);
				Object judgeValue = ReflectOperation.getFieldLevelValue(object, judgeFieldName, false);
				if(strValue.indexOf(",") > -1){ // 多个字段需要更新
					String[] fieldValues = strValue.split(",");
					for(int j = 0;j<fieldValues.length;j++){
						UpdateObject(object,judgeFieldName,fieldName,judgeValue,fieldValues[j],checkResultList.get(i),updateValueMap);
					}
				}
				else{ // 单个字段更新
					UpdateObject(object,judgeFieldName,fieldName,judgeValue,strValue,checkResultList.get(i),updateValueMap);
				}
			}
		}
		else if(requsetId != null){ // 有id则取出id对应type类进行字段更新
			Object object = this.getHibernateTemplate().get(type, (Serializable)requsetId);
			Object judgeValue = ReflectOperation.getFieldLevelValue(object, judgeFieldName, false);
			UpdateObject(object,judgeFieldName,fieldName,judgeValue,strValue,checkResultList.get(0),updateValueMap);
		}
		else{ // 没有id则直接设置TObject
			Object object = requestTObject;
			Object judgeValue = ReflectOperation.getFieldLevelValue(object, judgeFieldName, false);
			UpdateObject(object,judgeFieldName,fieldName,judgeValue,strValue,checkResultList.get(0),updateValueMap);
		}
	}
	
	private void UpdateObject(Object object,String judgeField,String fieldName,Object judgeValue,String strValue,boolean isTrue,Map<String,String> updateValueMap) throws DataAccessException, Exception{
		
		if(updateValueMap != null){
			for(Map.Entry<String, String> entry : updateValueMap.entrySet()){
				if(entry.getValue().equals("SystemDate")){
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					ReflectOperation.setFieldValue(object, entry.getKey(), timestamp);
				}else{
					ReflectOperation.setFieldValue(object, entry.getKey(), entry.getValue());
				}
			}
		}

		if(strValue.indexOf("To") > -1) {
			String oldValue = strValue.substring(0, strValue.indexOf("To"));
			String newValue = strValue.substring(strValue.indexOf("To") + 2);
			if(oldValue.equals("True")){
				if(isTrue){
					if(newValue.equals("Delete")){
						this.getHibernateTemplate().delete(object);
					}
					else{
						ReflectOperation.setFieldValue(object, fieldName, newValue);
						this.getHibernateTemplate().update(object);
					}
				}
			}
			else if(oldValue.equals("False")){
				if(!isTrue){
					if(newValue.equals("Delete")){
						this.getHibernateTemplate().delete(object);
					}
					else{
						ReflectOperation.setFieldValue(object, fieldName, newValue);
						this.getHibernateTemplate().update(object);
					}
				}
			}
			else{
				if(judgeValue!=null && judgeValue.equals(oldValue)){
					if(newValue.equals("Delete")){
						this.getHibernateTemplate().delete(object);
					}
					else{
						ReflectOperation.setFieldValue(object, fieldName, newValue);
						this.getHibernateTemplate().update(object);
					}
				}
			}
		}
		else{
			ReflectOperation.setFieldValue(object, fieldName, strValue);
			this.getHibernateTemplate().update(object);
		}
	}

	@Override
	public void voidResultExecute() throws Exception {

	}
}