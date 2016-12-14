package framework.services.check;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.ManyToOne;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class LogicPrimaryKeyCheck extends BaseConstructor  implements ICheck, IParamObjectResultExecute{
	
	public LogicPrimaryKeyCheck(){
		super();
	}
	
	public LogicPrimaryKeyCheck(Object baseObject){
		super(baseObject);
	}
	
	public MessageResult Check() throws Exception {
		Object checkResult = paramObjectResultExecute(this.getBaseObject());
		return (MessageResult)checkResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object paramObjectResultExecute(Object param) throws Exception {
		MessageResult messageResult=new MessageResult();
		
		if(LogicParamManager.getLogicPrimaryKey()!=null){
			List<Object> objectList1=null;
			Object tObject=param;
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(tObject.getClass());
			String FieldName="";
			String tName = tObject.getClass().getName();
			String showInstanceName = ShowParamManager.getShowInstanceName();
			ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
			
			for (String field : LogicParamManager.getLogicPrimaryKey()) {
				if(ReflectOperation.getFieldValue(tObject, field)==null || ReflectOperation.getFieldValue(tObject, field).equals("")){
					detachedCriteria.add(Restrictions.or(Restrictions.eq(field, "''"),Restrictions.sqlRestriction(field +" IS NULL")));
				}else{
					detachedCriteria.add(Restrictions.eq(field, ReflectOperation.getFieldValue(tObject, field)));
				}
				
				
				for (ShowField showField : showInstance.getShowFieldList()) {
					if(showField.getFieldName().equals(field)){
						
						Field field1 = ReflectOperation.getReflectField(Class.forName(tName),field);
						if(field1.isAnnotationPresent(ManyToOne.class)){
						  String modelDtoTName=field1.getType().getName();
						  Object ClassName = ReflectOperation.getFieldValue(tObject, field);
						  Field primaryKeyField = ReflectOperation.getPrimaryKeyField(ClassName.getClass());
						  String primaryKeyName = primaryKeyField.getName();
						  Object primaryKeyValue =ReflectOperation.getFieldValue(ClassName, primaryKeyName);
						  
						  DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Class.forName(modelDtoTName).newInstance().getClass());
						  detachedCriteria2.add(Restrictions.eq(primaryKeyName, primaryKeyValue));
						  objectList1 = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria2,null});
						  Field keyNameField = ReflectOperation.getKeyNameField(ClassName.getClass());
						  if(keyNameField!=null){
							  String keyName = keyNameField.getName();
							  Object fieldValue = ReflectOperation.getFieldValue(objectList1.get(0),keyName);
								 FieldName+=showField.getFieldShowName()+"："+fieldValue+"，";
									break;
						  }else{
							  Object fieldValue = ReflectOperation.getFieldValue(objectList1.get(0),primaryKeyName);
								 FieldName+=showField.getFieldShowName()+"："+fieldValue+"，";
									break;
						  }
						  
						}	
						FieldName+=showField.getFieldShowName()+"："+ReflectOperation.getFieldValue(tObject, field)+"，";
						break;
					}
				}
			}
			if(FieldName.length()>0){
				FieldName=FieldName.substring(0,FieldName.length()-1);
			}
			List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			if(ReflectOperation.getPrimaryKeyValue(tObject)==null && ReflectOperation.getPrimaryKeyValue(tObject).equals("")){
				if(objectList.size()>0){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add(FieldName+"， 逻辑主键重复");
				}
			}else{
				if(objectList.size()>0){
					for (Object object : objectList) {
						if(ReflectOperation.getPrimaryKeyValue(object)!=null && !ReflectOperation.getPrimaryKeyValue(object).equals(ReflectOperation.getPrimaryKeyValue(tObject))){
							if(objectList.size()>0){
								messageResult.setSuccess(false);
								messageResult.getMessageSet().add(FieldName+"， 逻辑主键重复");
							}
						}
					}
				}
			}
		}
		return messageResult;
	}

}
