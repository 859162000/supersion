package report.service.check;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

/**
 * 点击审核未通过的时候，如果报送状态为已报送，则不做任何处理
 * @author xiajieli
 *
 */
public class UpdateRPTVerifyTypeByRPTSendTypeCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		String tName=RequestManager.getTName();	
		
		if(RequestManager.getIdList() != null){//多条审核
			String[] idList = (String[]) RequestManager.getIdList();
			for (String strId : idList) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Object object = (Object)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,strId,null});
				if(RequestManager.getActionName().equals("RptAllowStateCancleUpdateField-report.dto.TaskModelInst")){
					//任务层
					if(ReflectOperation.getFieldValue(object, "RPTSendType")!=null && (ReflectOperation.getFieldValue(object, "RPTSendType").equals("2")|| ReflectOperation.getFieldValue(object, "RPTSendType").equals("5"))){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("存在已报送数据，功能未执行");
						break;
					}
					//基础层
				}else if(RequestManager.getActionName().contains("ReportFalseUpdateFieldLevelAUTODTOSH")){
					if(ReflectOperation.getFieldValue(object, "RPTSendType")!=null && ReflectOperation.getFieldValue(object, "RPTSendType").equals("2")){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("存在已报送数据，功能未执行");
						break;
					}
				}
			}
		}
		else {//单条审核
			Object tObject=RequestManager.getTOject();
			if(ReflectOperation.getFieldValue(tObject, "RPTSendType")!=null && ReflectOperation.getFieldValue(tObject, "RPTSendType").equals("2")){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("存在已报送数据，功能未执行");
			}
			
		}
		return messageResult;
	}

}
