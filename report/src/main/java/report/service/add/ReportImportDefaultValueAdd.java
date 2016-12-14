package report.service.add;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskModelInst;
import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.security.SecurityContext;
import framework.services.interfaces.IAdd;
/**
 * 数据填报功能报文导入设置固定字段默认值
 * @author xiajieli
 *
 */
public class ReportImportDefaultValueAdd implements IAdd {

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
		
		String tName=RequestManager.getTName();
		Object object = RequestManager.getTOject();
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),id,null});
		
		if(taskModelInst!=null){//基础段
			Date date=new Date();	
			String timestamp = TypeParse.parseString(date, "yyyy-MM-dd HH:mm:ss");
			Timestamp lastUpdateDate  = TypeParse.parseTimestamp(timestamp);
			
			if(ReflectOperation.getFieldByName(tName, "RPTSubmitStatus")!=null){
				ReflectOperation.setFieldValue(object, "RPTSubmitStatus", "1");
			}
			if(ReflectOperation.getFieldByName(tName, "RPTVerifyType")!=null){
				ReflectOperation.setFieldValue(object, "RPTVerifyType", "1");
			}
			if(ReflectOperation.getFieldByName(tName, "lastUpdateDate")!=null){
				ReflectOperation.setFieldValue(object, "lastUpdateDate", lastUpdateDate);
			}
			if(ReflectOperation.getFieldByName(tName, "instInfo")!=null){
				ReflectOperation.setFieldValue(object, "instInfo", taskModelInst.getInstInfo());
			}
			if(ReflectOperation.getFieldByName(tName, "dtDate")!=null){
				ReflectOperation.setFieldValue(object, "dtDate", TypeParse.parseString(taskModelInst.getTaskFlow().getDtTaskDate(),"yyyy-MM-dd"));
			}
			if(ReflectOperation.getFieldByName(tName, "operationUser")!=null){
				if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
					ReflectOperation.setFieldValue(object, "operationUser", (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
				}
			}
			if(ReflectOperation.getFieldByName(tName, "XXJLGZBH")!=null){
				ReflectOperation.setFieldValue(object, "XXJLGZBH", "00000000000000000000");
			}
			
		}else{//明细段
			
			String previousLevelTName  = SessionManager.getPreviousLevelTName();
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(previousLevelTName));
			detachedCriteria.add(Restrictions.eq("autoID",id));
			List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(objectList.size()>0){
				ReflectOperation.setFieldValue(object, "FOREIGNID", objectList.get(0));
			}
		}
		
		if(ReflectOperation.getFieldByName(tName, "RPTCheckType")!=null){
			ReflectOperation.setFieldValue(object, "RPTCheckType", "1");
		}
		if(ReflectOperation.getFieldByName(tName, "RPTSendType")!=null){
			ReflectOperation.setFieldValue(object, "RPTSendType", "1");
		}
		if(ReflectOperation.getFieldByName(tName, "RPTFeedbackType")!=null){
			ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
		}
	}

}
