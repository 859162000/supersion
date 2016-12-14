package report.service.procese;

import java.util.List;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowValue;

/*
 * 通过基础段的提交状态判断明细段是否可以修改
 */
public class AutoDTOMXUpdateByJCRPTSubmitStatusProcess implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		ShowList showList=(ShowList)serviceResult;
		String tName=RequestManager.getTName();
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String previousLevelTName=SessionManager.getPreviousLevelTName();
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object object_Jc = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{previousLevelTName,SessionManager.getLevelIdValue(currentLevelLevel),null});
		String RPTSubmitStatus="";
		if(ReflectOperation.getFieldValue(object_Jc, "RPTSubmitStatus")==null){
			RPTSubmitStatus="";
		}else{
			RPTSubmitStatus=ReflectOperation.getFieldValue(object_Jc, "RPTSubmitStatus").toString();
		}
		
		for (int i = 0; i < showList.getValueTable().size(); i++) {
			List<ShowValue> showValueList = showList.getValueTable().get(i);
			List<ShowOperation> operationList = showList.getLinkedList().get(i);
			
			String id = showValueList.get(0).getValue();
			String currDtoName = RequestManager.getTName();
			Object currObj = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{currDtoName, id,null});
			String rptSendTypeValue=ReflectOperation.getFieldValue(currObj, "RPTSendType").toString();
			
			for (ShowOperation showOperation : operationList) {
				if(showOperation.getOperationName().equals("修改")){
					if(rptSendTypeValue.equals("1") && RPTSubmitStatus.equals("2")){
						showOperation.setOperationAction("ShowCheckViewLevelAutoDTOMX-"+tName);
					}else if(rptSendTypeValue.equals("2")){
						showOperation.setOperationAction("ShowCheckViewLevelAutoDTOMX-"+tName);
					}else{
						showOperation.setOperationAction("ShowCheckUpdateLevelAutoDTOMX-"+tName);
					}
					break; 
				}
			}
		}
		
		return serviceResult;
	}

}
