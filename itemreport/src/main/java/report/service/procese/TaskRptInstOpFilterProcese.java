package report.service.procese;

import java.util.ArrayList;
import java.util.List;

import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowOperation;

/**
 * 界面展示Action过滤
 * 
 * @author gechenglian
 * 
 * 
 */
public class TaskRptInstOpFilterProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {

		framework.show.ShowList showList = (framework.show.ShowList) serviceResult;

		String id = RequestManager.getId().toString();
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskRptInst obj = (TaskRptInst) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] {TaskRptInst.class.getName(), id, null });
		String strSuitCode = "";
		if (null != obj) {
			strSuitCode = obj.getRptInfo().getSuit().getStrSuitCode();
		}
		
		if (ShowContext.getInstance().getShowEntityMap().containsKey(
				"makeReportOptShowControl")) {
			if (ShowContext.getInstance().getShowEntityMap().get(
					"makeReportOptShowControl").containsKey(strSuitCode)) {
				
				String actionValue = ShowContext.getInstance().getShowEntityMap().get("makeReportOptShowControl").get(strSuitCode);
				String[] actions = actionValue.split(",");
				List<ShowOperation> operationList = showList.getOperationList();
				for(String action:actions)
				{
					ShowOperation showOperation = new ShowOperation();
					String[] keyVal=action.split("@");
					showOperation.setOperationName(keyVal[0]);
					if(keyVal.length>1)
					{
						String operationValue = keyVal[1]; // 配置的操作如：Get-ShowUpdate
						showOperation.setOperationNamespace(TActionRule.getNamespace(operationValue));
						showOperation.setOperationAction(TActionRule.getCurrentLevelOperationActionName(
										operationValue, RequestManager.getTName()));
						showOperation.setOperationType(TActionRule.getOperationType(operationValue));
					}
					
					operationList.add(showOperation);
				}
				
				
				
				
				/*List<ShowOperation> delOpList=new ArrayList<ShowOperation>();
				for (int i = 0; i < operationList.size(); i++) {
					ShowOperation showOperation = operationList.get(i);
					String value = showOperation.getOperationAction().split("-")[0];

					for (String action : actions) {
						if (action.split("/")[1].equals(value)
								&& action.split("/")[0].equals(showOperation
										.getOperationNamespace())) {
							//operationList.remove(showOperation);
							delOpList.add(showOperation);
							break;
						}
					}
				}
				operationList.removeAll(delOpList);
				delOpList.clear();
				List<List<ShowOperation>> linkedList = showList.getLinkedList();
				for (int j = 0; j < linkedList.size(); j++) {
					for (int k = 0; k < linkedList.get(j).size(); k++) {

						String linkedValue = linkedList.get(j).get(k).getOperationAction().split("-")[0];
						for (String action : actions) {
							if (action.split("/")[1].equals(linkedValue)&& action.split("/")[0].equals(linkedList.get(j).get(k).getOperationNamespace())) {
								
								delOpList.add(linkedList.get(j).get(k));
								break;
							}
						}
					}

				}
				for(List<ShowOperation> show1:linkedList)
				{
					show1.removeAll(delOpList);
				}*/
				
				
			}
		}
		return serviceResult;
	}

}
