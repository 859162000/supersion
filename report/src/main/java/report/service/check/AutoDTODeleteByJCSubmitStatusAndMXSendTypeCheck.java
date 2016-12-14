package report.service.check;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/*
 * 通过基础段的提交状态和明细段的报送状态删除数据
 */
public class AutoDTODeleteByJCSubmitStatusAndMXSendTypeCheck implements ICheck {

	public MessageResult Check() throws Exception {
		MessageResult messageResult=new MessageResult();
		String currentTName=RequestManager.getTName();
		Object[] idList = (Object[]) RequestManager.getIdList();
		//基础段删除（基础段状态为未提交且其所有明细段状态为未报送）
		if(RequestManager.getActionName().contains("ForReportDeleteListByIdListLevelAUTODTO") || RequestManager.getActionName().contains("ForReportDeleteByConditionLevelAUTODTO")){
			
			for (Object objectId : idList) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Object object_Jc = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{currentTName,objectId,null});
				if(!ReflectOperation.getFieldValue(object_Jc, "RPTSubmitStatus").equals("1")){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("存在不可删除的数据，功能未执行");
					break;
				}else{
					List<Field> objectMXList=ReflectOperation.getOneToManyField(object_Jc.getClass());
					for (Field field : objectMXList) {
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						
						DetachedCriteria allDetachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
						allDetachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
						List<Object> allList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{allDetachedCriteria,null});
						for (Object objectMX : allList) {
							if(!ReflectOperation.getFieldValue(objectMX, "RPTSendType").equals("1")){
								messageResult.setSuccess(false);
								messageResult.getMessageSet().add("存在不可删除的数据，功能未执行");
								break;
							}
						}
					}
				}
			}
		}
		//明细段删除（明细段状态为未报送且其基础段的提交状态为未提交）
		else if(RequestManager.getActionName().contains("AutoDTOMXDeleteListByIdListLevelAutoDTOMX") || RequestManager.getActionName().contains("ForReportDeleteByConditionLevelAutoDTOMX")){
			for (Object objectId : idList) {
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Object object_MX = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{currentTName,objectId,null});
				if(!ReflectOperation.getFieldValue(object_MX, "RPTSendType").equals("1")){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("存在不可删除的数据，功能未执行");
					break;
				}else{
					String previousLevelTName=SessionManager.getPreviousLevelTName();
					Object foreinObject = ReflectOperation.getFieldValue(object_MX, "FOREIGNID");
					singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					Object object_Jc = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{previousLevelTName,ReflectOperation.getPrimaryKeyValue(foreinObject),null});
					if(!ReflectOperation.getFieldValue(object_Jc, "RPTSubmitStatus").equals("1")){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("存在不可删除的数据，功能未执行");
						break;
					}
				}
			}
		}
		
		return messageResult;
	}

}
