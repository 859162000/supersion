package zxptsystem.service.procese;

import org.apache.struts2.ServletActionContext;

import report.dto.TaskModelInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;

public class GetlevelFieldInstInfoValueProcess implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		String idOne=RequestManager.getLevelIdValue();
		String idTwo=SessionManager.getLevelIdValue(SessionManager.getCurrentLevel());
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst=null;
		if(idOne!=null){
			taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),idOne,null});
		}
		else if(idTwo!=null){
			taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),idTwo,null});
		}
		if(taskModelInst!=null){
			String strInstInfoValue=taskModelInst.getInstInfo().getStrInstCode();
			ServletActionContext.getContext().getSession().put("strInstInfoValue", strInstInfoValue);
		}
		return serviceResult;
	}

}
