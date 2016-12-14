package framework.log;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import framework.helper.ExceptionLog;

public class ActionExceptionLog extends MethodFilterInterceptor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {

		try{
			return arg0.invoke();
		}
		catch(Exception ex){
		
			ExceptionLog.CreateLog(ex);

			return "exception";
		}
	}
}
