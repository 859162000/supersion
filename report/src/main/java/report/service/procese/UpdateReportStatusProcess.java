package report.service.procese;

import java.lang.reflect.Field;
import java.util.List;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;

public class UpdateReportStatusProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		
		List<Field> onetomanyFieldList=ReflectOperation.getOneToManyField(Class.forName(RequestManager.getTName()));
		if(onetomanyFieldList.size()>0){
			Object tobject=RequestManager.getTOject();
			ReflectOperation.setFieldValue(tobject, "RPTSubmitStatus", "1");
			ReflectOperation.setFieldValue(tobject, "RPTVerifyType", "1");
			
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{tobject,null});
		}
		else{
			String currentLevelLevel = SessionManager.getCurrentLevel();
			String id = SessionManager.getLevelIdValue(currentLevelLevel);
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			Object object = (Object)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SessionManager.getPreviousLevelTName(),id,null});
			ReflectOperation.setFieldValue(object, "RPTSubmitStatus", "1");
			ReflectOperation.setFieldValue(object, "RPTVerifyType", "1");
			
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{object,null});
		
		}
		
		return serviceResult;
	}

}
