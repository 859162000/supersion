package report.service.procese;

import framework.helper.ReflectOperation;
import framework.interfaces.ActionJumpResult;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.MessageResult;

public class UpdateJumpProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		MessageResult messageResult=(MessageResult)serviceResult;
		if(messageResult.isSuccess()){
			ActionJumpResult actionJumpResult=new ActionJumpResult();
			actionJumpResult.setActionName("FKBWShowCheckUpdateLevelFKBWAutoDTO-" + RequestManager.getTName() + ".action?id=" + ReflectOperation.getPrimaryKeyValue(RequestManager.getTOject()).toString());
			actionJumpResult.setActionNamespace("framework");
			actionJumpResult.setIdValue(ReflectOperation.getPrimaryKeyValue(RequestManager.getTOject()).toString());
			actionJumpResult.setWindowId(RequestManager.getWindowId());
			return actionJumpResult;
		}
		
		return serviceResult;
	}

	
}
