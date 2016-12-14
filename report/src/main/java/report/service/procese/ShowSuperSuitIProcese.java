package report.service.procese;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ShowSuperSuitIProcese implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			if(showFieldValue.getShowField().getFieldName().equals(Suit.class.getSimpleName().toLowerCase())){
				Map<String,String> simpleMap = new LinkedHashMap<String,String>();
				String defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
			    Field primaryKeyField = ReflectOperation.getPrimaryKeyField(Suit.class);
				Field keyNameField = ReflectOperation.getKeyNameField(Suit.class);
				List<Field> listField = ReflectOperation.getColumnFieldList(Suit.class);
				String higerSuit = null;
				for (int i = 0; i < listField.size(); i++) {
					if(listField.get(i).getType().equals(Suit.class)){
						higerSuit = listField.get(i).getName();
					}
				}
				
				DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
				if(higerSuit != null){
					detachedCriteria.add(Restrictions.isNull(higerSuit));
				}
				IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
		    	List<Object> objectList = (List<Object>)defaultLogicDaoDao.paramObjectResultExecute(null);
				
				if(keyNameField != null){
					for(Object object : objectList){
						Object objFieldVal = ReflectOperation.getFieldValue(object, keyNameField.getName());
						if(objFieldVal != null){
							simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(), objFieldVal.toString());
						}
					}
				}
				showFieldValue.setTag(simpleMap);
				break;
			}
		}
		return serviceResult;
	}
}
