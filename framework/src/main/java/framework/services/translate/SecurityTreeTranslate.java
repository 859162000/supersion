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
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;

public class SecurityTreeTranslate extends SecurityTranslate{
	
	@Override
	public void Translate() throws Exception {
		if(ShowParamManager.getTreeServiceMap() != null){
			for(Map.Entry<String, String> entry : ShowParamManager.getTreeServiceMap().entrySet()){
				String[] types = entry.getValue().split(",");
				Class<?> type = Class.forName(types[0]);
				DetachedCriteria detachedCriteria = null;
				if(LogicParamManager.getDetachedCriteria() == null){
					detachedCriteria = DetachedCriteria.forClass(type);
				}
				else{
					detachedCriteria = LogicParamManager.getDetachedCriteria();
				}
				
				Field primaryKey = ReflectOperation.getPrimaryKeyField(type);
				
				SecurityContext sContext = SecurityContext.getInstance();
				LoginInfo loginInfo = sContext.getLoginInfo();
				
				Map<String, Map<String,String>> dataSecurityMap = SecurityContext.getInstance().getDataSecurityMap();
				for(Map.Entry<String, Map<String,String>> dataSecurityEntry : dataSecurityMap.entrySet()){
					if(dataSecurityEntry.getValue().containsKey(RequestManager.getTName())){
						String fieldName = dataSecurityEntry.getValue().get(RequestManager.getTName());
						Set<Object> valueSet = new HashSet<Object>();
						for(DataSecurity security : loginInfo.getDataSecuritySet()){
							if(security.getClassName().equals(RequestManager.getTName()) && security.getFieldName().equals(fieldName)){
								for(String value : security.getDataSet()){
									valueSet.add(value);
								}
							}
						}
						if(valueSet.size() == 0){
							detachedCriteria.add(Restrictions.isNull(primaryKey.getName()));
						}
						else{
							detachedCriteria.add(Restrictions.in(primaryKey.getName(),valueSet));
							LogicParamManager.setTreeDataSecurity(valueSet);
						}
					}
				}

				LogicParamManager.setDetachedCriteria(detachedCriteria);
			}
		}
		else{
		    super.Translate();
		}
	}
}
