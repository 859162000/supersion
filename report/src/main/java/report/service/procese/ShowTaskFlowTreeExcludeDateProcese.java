package report.service.procese;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import framework.services.interfaces.IProcese;

/**
 * 数据填报左侧的任务不加日期，除了指标报表之外的业务
 * @author Administrator
 *
 */
public class ShowTaskFlowTreeExcludeDateProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		List<Object> objectList = (List<Object>)serviceResult;
		List<Object> objectListTmp = new ArrayList();
		for(Object object:objectList){
			TaskModelInst taskModelInst = (TaskModelInst)object;
			TaskFlow taskFlow = taskModelInst.getTaskFlow();
			if(!taskFlow.getStrTaskState().equals("1") || (taskFlow.getTaskStartData().getTime()>new Date().getTime()) || ((taskFlow.getTaskEndData().getTime()+86400000)<new Date().getTime())){
				objectListTmp.add(object);
			}
		}
		objectList.removeAll(objectListTmp);
		
		return objectList;
	}

}
