package zxptsystem.service.check;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
import framework.show.ShowInstance;

public class MXHCShowSaveCheck implements ICheck {

	@SuppressWarnings("unchecked")
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();

		Map<String,String> mXHCShowSaveCheckMap = ShowContext.getInstance().getShowEntityMap().get("MXHCShowSaveCheck");
		
		if(mXHCShowSaveCheckMap!=null){
			String strKeys = "";
			for (Map.Entry<String, String> entry : mXHCShowSaveCheckMap.entrySet()) {
				if(entry.getKey().contains(RequestManager.getTName())){
					strKeys = entry.getKey();
					break;
				}
			}
			if(!strKeys.equals("")){
				String jcDto = mXHCShowSaveCheckMap.get(RequestManager.getTName());
				
				String currentLevelLevel = SessionManager.getCurrentLevel();
				String id = SessionManager.getLevelIdValue(currentLevelLevel);
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Object jcObject = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SessionManager.getPreviousLevelTName(),id,null});
				List<Field> listObjectMx=ReflectOperation.getOneToManyField(jcObject.getClass());
				
				for (Map.Entry<String, String> entry : mXHCShowSaveCheckMap.entrySet()) {
					if(entry.getValue().equals(jcDto)){
						for (Field field : listObjectMx) {
							String fieldTName = ReflectOperation.getGenericClass(field.getGenericType()).getName();
							if(!strKeys.contains(fieldTName) && entry.getKey().contains(fieldTName)){
								Set<Object> objectSet = (Set<Object>)ReflectOperation.getFieldValue(jcObject, field.getName());
								if(objectSet.size() > 0){
									messageResult.setSuccess(false);
									ShowInstance showInstance = ReflectOperation.getDefaultShowInstance(ReflectOperation.getGenericClass(field.getGenericType()).getName());
									messageResult.setMessage(showInstance.getShowEntityName() + "段中已经存在数据，不能新增。");
									return messageResult;
								}
							}
							
						}
					}
				}
			}
		}
		
		return messageResult;
	}

}
