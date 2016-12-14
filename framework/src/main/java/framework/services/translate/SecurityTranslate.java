package framework.services.translate;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.security.DataSecurity;
import framework.security.LoginInfo;
import framework.security.SecurityContext;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

// 根据用户数据权限过滤ShowList数据
public class SecurityTranslate implements ITranslate{
	
	public void Translate() throws Exception {

		// 取条件对象
		Class<?> type = Class.forName(RequestManager.getTName());
		
		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		
		Field primaryKey = ReflectOperation.getPrimaryKeyField(RequestManager.getTName());
		
		// 取当前用户数据权限
		SecurityContext sContext = SecurityContext.getInstance();
		LoginInfo loginInfo = sContext.getLoginInfo();
		
		if(loginInfo == null) { // 没有loginInfo,不出数据
			
			detachedCriteria.add(Restrictions.isNull(primaryKey.getName()));
			LogicParamManager.setDetachedCriteria(detachedCriteria);
			return;
		}
		
		Map<String, Map<String,String>> dataSecurityMap = SecurityContext.getInstance().getDataSecurityMap();
		for(Map.Entry<String, Map<String,String>> dataSecurityEntry : dataSecurityMap.entrySet()){
			if(dataSecurityEntry.getValue().containsKey(RequestManager.getTName())){
				String fieldName = dataSecurityEntry.getValue().get(RequestManager.getTName());
				Class<?> fieldType = ReflectOperation.getFieldByName(RequestManager.getTName(), fieldName).getType();
				String fieldPrimaryKeyName = ReflectOperation.getPrimaryKeyField(fieldType).getName();
				Set<Object> valueSet = new HashSet<Object>();
				for(DataSecurity security : loginInfo.getDataSecuritySet()){
					if(security.getClassName().equals(RequestManager.getTName()) && security.getFieldName().equals(fieldName)){
						for(String value : security.getDataSet()){
							Object foreignObject = fieldType.newInstance();
							ReflectOperation.setFieldValue(foreignObject, fieldPrimaryKeyName, value);
							valueSet.add(foreignObject);
						}
					}
				}
				if(valueSet.size() == 0){
					detachedCriteria.add(Restrictions.isNull(primaryKey.getName()));
				}
				else{
					detachedCriteria.add(Restrictions.in(fieldName,valueSet));
				}
			}
		}

		
		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}

}
