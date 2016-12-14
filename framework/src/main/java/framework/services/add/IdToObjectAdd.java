package framework.services.add;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;


public class IdToObjectAdd implements IAdd{

	public void Add() throws Exception {
		if(RequestManager.getId() != null){
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
            Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),RequestManager.getId(),null});
            RequestManager.setTOject(object);
		}
		if(RequestManager.getLevelIdValue() != null){
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
            Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),RequestManager.getLevelIdValue(),null});
            RequestManager.setTOject(object);
            RequestManager.setId(RequestManager.getLevelIdValue());
		} 
	}
}