package autoETLsystem.service.imps;

import framework.interfaces.IObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;

public class ExcuteWorkflowService implements IObjectResultExecute{

    private String defaultOracleProcedureParamName;
	
	public void setDefaultOracleProcedureParamName(String defaultOracleProcedureParamName) {
		this.defaultOracleProcedureParamName = defaultOracleProcedureParamName;
	}
	
	public Object objectResultExecute() throws Exception {
		String sessionFactory = SecurityContext.getInstance().getLoginInfo().getSessionFactory();
		ThreadWorkflowRunnable threadWorkflowRunnable = new ThreadWorkflowRunnable(RequestManager.getId().toString(),defaultOracleProcedureParamName,sessionFactory);
		if(threadWorkflowRunnable.initWorkflowParam()){
			Thread thread=new Thread(threadWorkflowRunnable);
			thread.start();
		}

		return new MessageResult("执行成功,并等待工作流执行完成后查看日志");
	}
}
