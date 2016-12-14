package report.service.procese;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;

import report.dto.TaskModelInst;
import report.dto.TaskRptInst;
import report.service.imps.CommonUpdateReportStatusService;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.MessageResult;

public class ReportAllAllowStateBySubmitStatusProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		@SuppressWarnings("unused")
		String[] idList = (String[]) RequestManager.getIdList();
		String actionName=RequestManager.getActionName();
		if(actionName.contains("RptAllowStateCancleUpdateField-report.dto.TaskModelInst") || actionName.contains("RptAllowStateUpdateField-report.dto.TaskModelInst")){
			for (String string : idList) {
				boolean isUpdate=false;
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),string,null});
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				String strTableName=ReflectOperation.getAutoDTOTName("sessionFactory", taskModelInst.getReportModel_Table().getStrTableName());
				
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableName));
				detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
				detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
				detachedCriteria.add(Restrictions.eq("RPTSubmitStatus", "2"));
				List<Object> objectList=new ArrayList<Object>();
				
				if(RequestManager.getActionName().contains("RptAllowStateUpdateField-report.dto.TaskModelInst")){
					detachedCriteria.add(Restrictions.in("RPTVerifyType", new String[]{"1","3"}));
					objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for (Object tObject : objectList) {
						ReflectOperation.setFieldValue(tObject, "RPTVerifyType", "2");
						isUpdate=true;
					}
				}
				else if(RequestManager.getActionName().contains("RptAllowStateCancleUpdateField-report.dto.TaskModelInst")){
					detachedCriteria.add(Restrictions.in("RPTVerifyType", new String[]{"1","2","3"}));
					objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for (Object tObject : objectList) {
						ReflectOperation.setFieldValue(tObject, "RPTVerifyType", "3");
						ReflectOperation.setFieldValue(tObject, "RPTSubmitStatus", "1");
						isUpdate=true;
					}
				}
				
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{objectList,null});
				
				List<InstInfo> instInfoSubList=new ArrayList<InstInfo>();
				instInfoSubList.add(taskModelInst.getInstInfo());
				
				if(isUpdate){
					CommonUpdateReportStatusService UpdateReportStatus=new CommonUpdateReportStatusService();
					UpdateReportStatus.commonUpdateReportStatus(objectList, instInfoSubList);
				}
			}
		}
		else if(actionName.contains("RptAllowStateUpdateField-report.dto.TaskRptInst"))
		{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
			detachedCriteria.add(Restrictions.in("id",idList ));
			detachedCriteria.add(Restrictions.ne("strAllowState","2"));
			List<TaskRptInst> objectList = (List<TaskRptInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(objectList.size()>0 &&objectList.size()<idList.length)
			{
				String message="部分审核成功(已提交的数据才会审核)\\r\\n处理失败的报表代码如下：\\r\\n";
				for(TaskRptInst task:objectList)
				{
					message+=task.getRptInfo().getStrBCode()+"\\r\\n";
				}
				serviceResult=new MessageResult(false,message);
				
			}
			else if(objectList.size()==idList.length)
			{
				serviceResult=new MessageResult(false,"审核失败(已提交的数据才会审核)");
				
			}
			
			
		}
	
		return serviceResult;
	}

}
