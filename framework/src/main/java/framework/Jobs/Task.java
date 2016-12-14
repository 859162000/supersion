package framework.Jobs;

import org.apache.struts2.ServletActionContext;

public class Task {
  public static void execute(Object jobObj,String methodName)
  {
	  TaskExecutor te=new TaskExecutor(jobObj,methodName,ServletActionContext.getContext());
	  Thread thr = new Thread(te);
	  thr.start();
  }
}
