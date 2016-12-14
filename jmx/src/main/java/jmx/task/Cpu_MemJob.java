package jmx.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jmx.dto.J_S_CPU;
import jmx.dto.J_S_MEM;
import jmx.jsch.Shell;
import jmx.jsch.Top;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamVoidResultExecute;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：Cpu_MemJob</P>
 * *********************************<br>
 * <P>类描述：cpu和mem采集  job</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-22 上午11:14:55<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-22 上午11:14:55<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class Cpu_MemJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
			JobDataMap params = context.getJobDetail().getJobDataMap();
			if(params.get("shell") != null){
				List<J_S_CPU> cpuList = new ArrayList<J_S_CPU>();
				List<J_S_MEM> memList = new ArrayList<J_S_MEM>();
				String sid = (String) params.get("sid");
				Shell shell = (Shell) params.get("shell");
				int cli = (Integer) params.get("cli");
				Map<String, String> applys = (Map<String, String>) params.get("applys");
				Top.get(sid,cli,shell,applys,cpuList,memList);
				IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				saveDao.paramVoidResultExecute(new Object[]{cpuList,null});
				saveDao.paramVoidResultExecute(new Object[]{memList,null});
			}
		}catch(Exception e){
			ExceptionLog.CreateLog(e);
		}
	}
	
}
