package framework.services.translate;

import java.lang.reflect.Field;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;


public class LevelDataTranslate implements ITranslate{

	public void Translate() throws Exception {
		
		if(SessionManager.getCurrentLevel() != null){

			String currentLevelLevel = SessionManager.getCurrentLevel();
			if(SessionManager.getLevelTName(currentLevelLevel) != null && SessionManager.getLevelIdValue(currentLevelLevel) != null){
				
				Class<?> type = Class.forName(RequestManager.getTName());
				DetachedCriteria detachedCriteria = null;
				if(LogicParamManager.getDetachedCriteria() == null){
					detachedCriteria = DetachedCriteria.forClass(type);
				}
				else{
					detachedCriteria = LogicParamManager.getDetachedCriteria();
				}
				
				// 构造上一级对象用来过滤数据
				String previousLevelLevelTName  = SessionManager.getPreviousLevelTName();
				Object foreignObject = FrameworkFactory.CreateClass(previousLevelLevelTName);
				Field primaryField = ReflectOperation.getPrimaryKeyField(previousLevelLevelTName);
				ReflectOperation.setFieldValue(foreignObject, primaryField.getName(), SessionManager.getLevelIdValue(currentLevelLevel));
				
				// 当外键为特殊值(未赋值)时，设置外键对象为空
				if(SessionManager.getLevelIdValue(currentLevelLevel).equals(ApplicationManager.getEmptySelectValue()))
					foreignObject = null;
				
				Field[] fields = ReflectOperation.getReflectFields(Class.forName(RequestManager.getTName()));
				for(int j=0;j<fields.length;j++){
					if(fields[j].getType().equals(Class.forName(previousLevelLevelTName))){ // 与上一级类型相同的属性
						if(foreignObject == null)
							detachedCriteria.add(Restrictions.isNull(fields[j].getName()));
						else {
							detachedCriteria.add(Restrictions.eq(fields[j].getName(), foreignObject));
						
							Object tObject = RequestManager.getTOject();
							if(tObject!=null)
							{
								if(tObject.getClass().getName().equals(RequestManager.getTName())) // TObject与TName不同，应是有condition时TObject为条件类了
									ReflectOperation.setFieldValue(tObject, fields[j].getName(), foreignObject);// 设置条件类对象的上级外键
							}
							
						}
						
						break;
					}
				}
				
				// 层级字段过滤下一级数据(目前用于显示填报时，某任务下的数据)
				Map<String,String> levelFieldMap = ShowParamManager.getLevelFieldMap();
				if(levelFieldMap != null){
					Object currentId = RequestManager.getId();
					String currentTName = RequestManager.getTName();
					RequestManager.setId(SessionManager.getLevelIdValue(currentLevelLevel));
					RequestManager.setTName(previousLevelLevelTName);
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					Object previousLevelObject = singleObjectFindByIdDao.paramObjectResultExecute(null);
					Field[] previousLevelFields = ReflectOperation.getReflectFields(previousLevelObject.getClass());
					for(Map.Entry<String, String> entry : levelFieldMap.entrySet()){
						if(entry.getKey().indexOf(".") > -1){
							String fieldName1 = entry.getKey().substring(0,entry.getKey().indexOf("."));
							for(int i=0;i<previousLevelFields.length;i++){
								if(previousLevelFields[i].getName().equals(fieldName1)){
									Object value = ReflectOperation.getFieldLevelValue(previousLevelObject, entry.getKey(), false);
									detachedCriteria.add(Restrictions.eq(entry.getValue(), value));
									break;
								}
							}
						}
						else{
							for(int i=0;i<previousLevelFields.length;i++){
								if(previousLevelFields[i].getName().equals(entry.getKey())){
									detachedCriteria.add(Restrictions.eq(entry.getValue(), ReflectOperation.getFieldValue(previousLevelObject, entry.getKey())));
									break;
								}
							}
						}
					}
					RequestManager.setId(currentId);
					RequestManager.setTName(currentTName);
				}
				
				LogicParamManager.setDetachedCriteria(detachedCriteria);
			}
		}
	}
}
