package framework.Jobs;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import framework.helper.ExceptionLog;
import framework.helper.ReflectOperation;

public class TaskExecutor implements Runnable{
	

	private Object jobObj;
	private String methodName;
	private ActionContext actionContext;
	
    public TaskExecutor(Object jobObj,String methodName,ActionContext actionContext){
    	this.jobObj=jobObj;
    	this.methodName=methodName;
    	this.actionContext=new ActionContext(actionContext.getContextMap());
    	
    }
	@Override
	public void run() {
		ServletActionContext.setContext(actionContext);
		if(jobObj!=null && !StringUtils.isBlank(methodName))
		{
			try {
				Method method = ReflectOperation.getReflectMethod(jobObj.getClass(), methodName);
				if(method != null) {
					method.invoke(jobObj);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				ExceptionLog.CreateLog(e1);
			}
			
			
		}
		
	}
}
