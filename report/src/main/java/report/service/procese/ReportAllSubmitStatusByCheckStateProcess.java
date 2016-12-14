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

public class ReportAllSubmitStatusByCheckStateProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		@SuppressWarnings("unused")
		String[] idList = (String[]) RequestManager.getIdList();
		String actionName=RequestManager.getActionName();
		if(actionName.contains("RptSubmitStatusALLUpdateField-report.dto.TaskModelInst")){
			for (String string : idList) {
				boolean isUpdate=false;
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),string,null});
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				String strTableName=ReflectOperation.getAutoDTOTName("sessionFactory", taskModelInst.getReportModel_Table().getStrTableName());
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableName));
				detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
				detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
				List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
				for (Object tObject : objectList) {
					if(ReflectOperation.getFieldValue(tObject, "RPTCheckType").equals("2") && !ReflectOperation.getFieldValue(tObject, "RPTSubmitStatus").equals("2")){
						ReflectOperation.setFieldValue(tObject, "RPTSubmitStatus", "2");
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
		else if(actionName.contains("RptSubmitStatusALLUpdateField-report.dto.TaskRptInst"))
		{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
			detachedCriteria.add(Restrictions.in("id",idList ));
			detachedCriteria.add(Restrictions.ne("SubmitStatus","2"));
			List<TaskRptInst> objectList = (List<TaskRptInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(objectList.size()>0 && objectList.size()<idList.length)
			{
				String message="部分提交成功(校验成功数据才会提交)\\r\\n处理失败的报表代码如下：\\r\\n";
				for(TaskRptInst task:objectList)
				{
					message+=task.getRptInfo().getStrBCode()+"\\r\\n";
				}
				serviceResult=new MessageResult(false,message);
				
			}
			else if(objectList.size()==idList.length)
			{
				serviceResult=new MessageResult(false,"提交失败(校验成功数据才会提交)");
				
			}
		}
		else if(actionName.contains("RptSubmitStatusALLForceUpdateField-TaskRptInfo"))
		{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
			detachedCriteria.add(Restrictions.in("id",idList ));
			detachedCriteria.add(Restrictions.ne("SubmitStatus","2"));
			List<TaskRptInst> objectList = (List<TaskRptInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(objectList.size()>0 &&  objectList.size()<idList.length)
			{
				String message="部分提交成功(强制提交必须备注原因)\\r\\n处理失败的报表代码如下：\\r\\n";
				for(TaskRptInst task:objectList)
				{
					message+=task.getRptInfo().getStrBCode()+"\\r\\n";
				}
				serviceResult=new MessageResult(false,message);
				
			}
			else if(objectList.size()==idList.length)
			{
				serviceResult=new MessageResult(false,"提交失败(强制提交必须备注原因)");
				
			}
		}
			
		return serviceResult;
	}
}
