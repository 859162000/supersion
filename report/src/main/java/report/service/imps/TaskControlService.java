package report.service.imps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import report.dto.TaskFlow;
import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseObjectDaoResultService;

public class TaskControlService extends BaseObjectDaoResultService{
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {

		ArrayList<ArrayList<ArrayList<String>>> serviceResult = new ArrayList<ArrayList<ArrayList<String>>>();
		
		
		ArrayList<ArrayList<String>> tasks = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> taskRptInsts = new ArrayList<ArrayList<String>>();
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<TaskFlow> taskFlows = (List<TaskFlow>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.TaskFlow",null});
		
		ArrayList<String> title = new ArrayList<String>();
		title.add("");
		title.add("任务名称");
		title.add("任务数");
		title.add("已完成");
		title.add("查看子任务");
		tasks.add(title);
		
		for(TaskFlow taskFlow : taskFlows) { // 生成每个任务的子任务
			ArrayList<String> task = new ArrayList<String>();

			task.add(taskFlow.getId());
			task.add(taskFlow.getStrTaskName());
			Set<TaskRptInst> set = taskFlow.getTaskRptInsts();
			task.add(String.valueOf(set.size()));

			int overSize = 0;
			for( Iterator it = set.iterator(); it.hasNext();){
				TaskRptInst taskRptInst = (TaskRptInst) it.next();
				if("1".equals(taskRptInst.getStrAllowState())){
					overSize++;
				}

				ArrayList<String> taskRptinstList = new ArrayList<String>();
				taskRptinstList.add(taskFlow.getId());
				taskRptinstList.add(taskRptInst.getStrAllowState());
				taskRptinstList.add(taskRptInst.getRptInfo().getStrRptName());
				taskRptinstList.add(taskRptInst.getInstInfo().getStrInstName());
				
				taskRptInsts.add(taskRptinstList);
			}
			task.add(String.valueOf(overSize));
			task.add("*****");

			tasks.add(task);
		}
		
		serviceResult.add(tasks);
		serviceResult.add(taskRptInsts);
		this.setServiceResult(serviceResult);
	}
}
