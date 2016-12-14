package framework.services.imps;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.LogicParamManager;

public class SingleObjectShowSaveOrUpdateService extends BaseTShowService{

	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute() throws Exception {
		
		super.init();
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		String typeName = RequestManager.getTypeName();
		String tName = RequestManager.getTName();
		
		if(StringUtils.isBlank(typeName)){ // 没有指定type则使用上一级T作为过滤
			typeName = tName;
			tName = SessionManager.getPreviousLevelTName();
		}
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(typeName));
		List<Field> fieldList = ReflectOperation.getJoinColumnFieldList(typeName);
		for(Field field : fieldList){
			if(field.getType().equals(Class.forName(tName))){
				Object object = Class.forName(tName).newInstance();
				ReflectOperation.setFieldValue(object, ReflectOperation.getPrimaryKeyField(tName).getName(), RequestManager.getLevelIdValue());
				detachedCriteria.add(Restrictions.eq(field.getName(), object));
				break;
			}
		}
		
		List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		RequestManager.setActionName(RequestManager.getActionName().replaceAll(RequestManager.getTName(), typeName));
		RequestManager.setTName(typeName);

		if(objectList.size() > 0){ // 如果找到则使用第一个作为操作对象
			RequestManager.setId(ReflectOperation.getPrimaryKeyValue(objectList.get(0)));
		}
		
		return null;
	}
	
}
