package jmx.task;

import java.util.HashMap;
import java.util.Map;

import jmx.dto.J_App;
import jmx.dto.J_Db;
import jmx.dto.J_Server;
import jmx.dto.J_Task;
import jmx.jsch.Shell;
import jmx.service.JmxBaseService;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import framework.helper.FrameworkFactory;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：JobSheduler</P>
 * *********************************<br>
 * <P>类描述：job任务</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-22 上午10:55:11<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-22 上午10:55:11<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class JobSheduler {
	
	private Scheduler sch;
	
	private static JobSheduler instance;
	
	private JobSheduler(){
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			sch = sf.getScheduler();
		} catch (SchedulerException e) {
			instance = null;
		}
	}
	
	public static synchronized JobSheduler getInstance() {
		if (instance == null) {
			instance = new JobSheduler();
		}
		return instance;
	}
	
	

	/**
	 * <p>方法描述: 运行job</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param taskId
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-22 上午9:35:23</p>
	 */
	public void run(String taskId,Class<?> job,boolean stopRun) {
		try {
			if(stopRun)
				shutDown(taskId);
			if(isRunning(taskId))
				return;
			JmxBaseService service = (JmxBaseService) FrameworkFactory.CreateBean("jmxBaseService");
			J_Task task = service.getTaskById(taskId);
			if(task == null)
				return;
			J_Server server = service.getServerById(task.getServer().getUuid());
			int cli = getLi(task);
			if(server != null){
				JobDetail jd = new JobDetail(taskId+"_Detail", Scheduler.DEFAULT_GROUP, job);
				Shell shell = new Shell(server.getHost(),server.getUserName(),server.getPasswd());
				jd.getJobDataMap().put("sid", task.getServer().getUuid());
				jd.getJobDataMap().put("cli", cli);
				jd.getJobDataMap().put("shell", shell);
				Map<String, String> applys = new HashMap<String, String>();
				for(J_Db j:server.getDbs()){
					applys.put(j.getDir(), j.getUuid());
				}
				for(J_App j:server.getApps()){
					applys.put(j.getDir(), j.getUuid());
				}
				jd.getJobDataMap().put("applys", applys);
				Trigger tg = TriggerUtils.makeSecondlyTrigger(cli);
				tg.setName(taskId+"_Trigger");
				sch.scheduleJob(jd, tg);
				sch.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>方法描述:检查是否在任务列表执行计划运行中 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param taskId
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-22 上午10:03:46</p>
	 */
	public boolean isRunning(String taskId){
		try {
			if(sch.getTrigger(taskId+"_Trigger", Scheduler.DEFAULT_GROUP) != null)
				return true;
		} catch (SchedulerException e) {
			return false;
		}
		return false;
	}
	
	private int getLi(J_Task task){
		if(null == task)
			return 10;
		if(task.getCli()==0)
			task.setCli(1);
		if("m".equals(task.getCfq())){
			return 60*task.getCli();
		}else if("h".equals(task.getCfq())){
			return 60*60*task.getCli();
		}else if("d".equals(task.getCfq())){
			return 24*60*60*task.getCli();
		}
		return task.getCli();
	}
	
	/**
	 * <p>方法描述:关闭job </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param taskId
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-22 上午10:04:15</p>
	 */
	public void shutDown(String taskId){
		try {
			sch.unscheduleJob(taskId+"_Trigger", Scheduler.DEFAULT_GROUP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
