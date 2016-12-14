package report.service.procese;

import java.util.HashSet;
import java.util.List;

import report.dto.TaskFlow;
import report.dto.TaskRptInst;
import framework.services.interfaces.IProcese;
public class ShowTaskFlowNameProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		List<Object> objectList = (List<Object>)serviceResult;
		HashSet<String> filterSet = new HashSet<String>();
		for(Object object:objectList){
			TaskRptInst taskModelInst = (TaskRptInst)object;
			TaskFlow taskFlow = taskModelInst.getTaskFlow();
			if(!filterSet.contains(taskFlow.getId())){
				taskFlow.setStrTaskName(taskFlow.getDtTaskDate()+"_"+taskFlow.getStrTaskName());
				filterSet.add(taskFlow.getId());
			}
		}
		return objectList;
	}

}
