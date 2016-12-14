package report.service.procese;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import framework.services.interfaces.IProcese;

//过滤service返回的数据     显示： 任务状态为开始  当前系统时间于任务开始时间与任务结束时间之间      放置于ShowTreeProcese之前 
public class ShowTaskFlowTreeProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		List<Object> objectList = (List<Object>)serviceResult;
		List<Object> objectListTmp = new ArrayList();
		HashSet<String> filterSet = new HashSet<String>();
		for(Object object:objectList){
			TaskModelInst taskModelInst = (TaskModelInst)object;
			TaskFlow taskFlow = taskModelInst.getTaskFlow();
			if(!filterSet.contains(taskFlow.getId())){
				taskFlow.setStrTaskName(taskFlow.getDtTaskDate()+"_"+taskFlow.getStrTaskName());
				filterSet.add(taskFlow.getId());
			}
			if(!taskFlow.getStrTaskState().equals("1") || (taskFlow.getTaskStartData().getTime()>new Date().getTime()) || ((taskFlow.getTaskEndData().getTime()+86400000)<new Date().getTime())){
				objectListTmp.add(object);
			}
		}
		objectList.removeAll(objectListTmp);
		
		return objectList;
	}

}
