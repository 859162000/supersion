package jmx.service;

import jmx.task.Cpu_MemJob;
import jmx.task.JobSheduler;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.IObjectResultExecute;

public class JmxTaskService implements IObjectResultExecute{

	@Override
	public Object objectResultExecute() throws Exception {
		String id = ServletActionContext.getRequest().getParameter("id");
		String method = ServletActionContext.getRequest().getParameter("method");
		if("start".equals(method)){
			JobSheduler.getInstance().run(id, Cpu_MemJob.class, false);
			return "启动成功!";
		}
		if("stop".equals(method)){
			JobSheduler.getInstance().shutDown(id);
			return "停止成功!";
		}
		return "无操作!";
	}

}
