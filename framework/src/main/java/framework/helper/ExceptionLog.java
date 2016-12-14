package framework.helper;

import framework.interfaces.ApplicationManager;

public class ExceptionLog {
	
	public static void CreateLog(Exception ex){
		ApplicationManager.getActionExceptionLog().error("====================================================================================================");
		ApplicationManager.getActionExceptionLog().error(ex.getMessage(), ex);//.error(ex.toString());
		/*StringBuilder errorBuilder=new StringBuilder(ex.toString());
		
		ex.printStackTrace(s)
		StackTraceElement[] stackTraceElement = ex.getStackTrace();
		if(stackTraceElement != null && stackTraceElement.length > 0){
			
			for(int i=0;i<stackTraceElement.length;i++){
				ApplicationManager.getActionExceptionLog().error(stackTraceElement[i].);
			}
		}*/
	}
}
