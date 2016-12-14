package report.service.procese;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public class AutoDTOUpdateFlowStatusProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		String tName=RequestManager.getTName();
		String previousLevelTName=SessionManager.getPreviousLevelTName();
		TaskModelInst taskModelInst=null;
		String tableDTOName=null;
		Object object_Jc=null;
		int checkSuccess=0;
		int checkfalse=0;
		int unCheck=0;
		int sendSuccess=0;
		int unSend=0;
		
		int unRPTFeedbackType=0;
		int rPTFeedbackTypeSuccess=0;
		int rPTFeedbackTypefalse=0;
		
		
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		//明细段
		if(previousLevelTName.contains("AutoDTO_")){
			tableDTOName=previousLevelTName;
			if(RequestManager.getActionName().indexOf("Save")>-1 || RequestManager.getActionName().indexOf("Update")>-1){
				Object tObject=RequestManager.getTOject();
				Object foreinObject = ReflectOperation.getFieldValue(tObject, "FOREIGNID");
				object_Jc = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{previousLevelTName,ReflectOperation.getPrimaryKeyValue(foreinObject),null});
				
				ReflectOperation.setFieldValue(object_Jc, "RPTSubmitStatus", "1");
				ReflectOperation.setFieldValue(object_Jc, "RPTVerifyType", "1");
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{object_Jc,null});
			}
			else if(RequestManager.getActionName().indexOf("Delete")>-1){
				object_Jc=((Map<String,Object>)ServletActionContext.getContext().get("request")).get("previousObject");
			}
			
		}else{//基础段
			tableDTOName=tName;
			if(RequestManager.getIdList() == null){
				object_Jc = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,ReflectOperation.getPrimaryKeyValue(RequestManager.getTOject()),null});
			}
		}
		
		if(object_Jc != null){
			List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_Jc.getClass());
			
			for (Field field : listObjectMx) {
				DetachedCriteria detachedCriteria = null;
				
				unCheck += findByDetachedCriteriaForJC("RPTCheckType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				checkSuccess += findByDetachedCriteriaForJC("RPTCheckType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				checkfalse += findByDetachedCriteriaForJC("RPTCheckType","3", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				unSend += findByDetachedCriteriaForJC("RPTSendType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				sendSuccess += findByDetachedCriteriaForJC("RPTSendType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				unRPTFeedbackType += findByDetachedCriteriaForJC("RPTFeedbackType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				rPTFeedbackTypeSuccess += findByDetachedCriteriaForJC("RPTFeedbackType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
				rPTFeedbackTypefalse += findByDetachedCriteriaForJC("RPTFeedbackType","3", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
				
			}
			
			boolean object_JcCheckState = true;
			//重新校验一遍基础段的状态
			IObjectResultExecute objectResultExecute=(IObjectResultExecute) FrameworkFactory.CreateBean(TActionRule.getServiceBeanName(tableDTOName, "UpdateLevelAUTODTO"));
			if(objectResultExecute==null){
				objectResultExecute=(IObjectResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateLevelAUTODTOService");
			}
			
			if(objectResultExecute!=null){
				BaseVoidDaoResultService baseVoidDaoResultService=(BaseVoidDaoResultService)objectResultExecute;

				List<String> defaultCheckClassList = baseVoidDaoResultService.getDefaultCheckClassList();

				Object currentTObject = RequestManager.getTOject();
				String currentTName = RequestManager.getTName();
				String defaultCheckInstance = LogicParamManager.getDefaultCheckInstance();
				String[] currentLogicPrimaryKeys= LogicParamManager.getLogicPrimaryKey();
				RequestManager.setTOject(object_Jc);
				RequestManager.setTName(object_Jc.getClass().getName());
				
				String dtoName=object_Jc.getClass().getName();
				String strTableName=dtoName.substring(dtoName.indexOf("AutoDTO_")+8);
				LogicParamManager.setDefaultCheckInstance("Check_"+strTableName);
				LogicParamManager.setLogicPrimaryKey(baseVoidDaoResultService.getLogicPrimaryKey());
				//LogicParamManager.setDefaultCheckInstance(LogicParamManager.getDefaultCheckInstance());
				
				if(defaultCheckClassList != null){
					for(String str : defaultCheckClassList){
						ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
						MessageResult currentResult = check.Check();
						if(!currentResult.isSuccess()) {
							object_JcCheckState = false;
							break;
						}
					}
				}
				RequestManager.setTOject(currentTObject);
				RequestManager.setTName(currentTName);
				LogicParamManager.setDefaultCheckInstance(defaultCheckInstance);
				LogicParamManager.setLogicPrimaryKey(currentLogicPrimaryKeys);
			}

			if(object_JcCheckState){
				checkSuccess+=1;
			}
			else{
				checkfalse+=1;
			}

			String SubRPTCheckType="";
			if(checkSuccess==0 && checkfalse==0 && unCheck>0){
				SubRPTCheckType = "1";
			}else if(checkSuccess>0 && checkfalse==0 && unCheck==0){
				SubRPTCheckType = "2";
			}
			else if(checkSuccess==0 && checkfalse>0 && unCheck==0 ){
				SubRPTCheckType = "3";
			}
			else{
				SubRPTCheckType = "4";
			}
			
			ReflectOperation.setFieldValue(object_Jc, "RPTCheckType", SubRPTCheckType);
			
			String SubSendType="";
			if(sendSuccess==0 && unSend==0){
				SubSendType = "1";
			}else if(sendSuccess>0 && unSend==0){
				SubSendType = "2";
			}
			else if(sendSuccess==0 && unSend>0){
				SubSendType = "1";
			}
			else if(sendSuccess>0 && unSend>0){
				SubSendType = "5";
			}
			ReflectOperation.setFieldValue(object_Jc, "RPTSendType", SubSendType);
			
			String SubRPTFeedbackType="";
			
			if(ReflectOperation.getFieldValue(object_Jc, "RPTFeedbackType").equals("1")){
				unRPTFeedbackType+=1;
			}else if(ReflectOperation.getFieldValue(object_Jc, "RPTFeedbackType").equals("2")){
				rPTFeedbackTypeSuccess+=1;
			}else if(ReflectOperation.getFieldValue(object_Jc, "RPTFeedbackType").equals("3")){
				rPTFeedbackTypefalse+=1;
			}
		
			if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType>=0){
				SubRPTFeedbackType = "1";
			}else if(rPTFeedbackTypeSuccess>0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType==0){
				SubRPTFeedbackType = "2";
			}
			else if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse>0 && unRPTFeedbackType==0 ){
				SubRPTFeedbackType = "3";
			}
			else{
				SubRPTFeedbackType = "4";
			}
			
			ReflectOperation.setFieldValue(object_Jc, "RPTFeedbackType",SubRPTFeedbackType);
			
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{object_Jc,null});
		}
		
		//任务状态
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = null;
		if(object_Jc == null){
			taskModelInst=(TaskModelInst)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("previousObject");
		}
		else{
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			String strTableName=tableDTOName.substring(tableDTOName.indexOf("AutoDTO_")+8);
			detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
			List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			detachedCriteria.add(Restrictions.eq("dtTaskDate", ReflectOperation.getFieldValue(object_Jc, "dtDate")));
		    List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			
		    detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			detachedCriteria.add(Restrictions.eq("instInfo", ReflectOperation.getFieldValue(object_Jc, "instInfo")));
			detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
			detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
		    List<TaskModelInst>  taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    taskModelInst=taskModelInstList.get(0);
		}

		 checkSuccess=0;
		 checkfalse=0;
		 unCheck=0;
		 int unAllCheckSuccess=0;
		 
		 sendSuccess=0;
		 unSend=0;
		 int unAllSendSuccess=0;
		 
		int unRPTSubmitStatus=0;
		int rPTSubmitStatusSuccess=0;
		
		int unRPTVerifyType=0;
		int rPTVerifyTypeSuccess=0;
		int rPTVerifyTypefalse=0;
		
		unRPTFeedbackType=0;
		rPTFeedbackTypeSuccess=0;
		rPTFeedbackTypefalse=0;
		int unAllRPTFeedbackTypeSuccess=0;
		//筛选条件时，排除回执状态为5，已删除的数据
		unCheck = findByDetachedCriteria("RPTCheckType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		checkSuccess = findByDetachedCriteria("RPTCheckType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		checkfalse = findByDetachedCriteria("RPTCheckType", "3", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unAllCheckSuccess = findByDetachedCriteria("RPTCheckType", "4", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unSend = findByDetachedCriteria("RPTSendType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		sendSuccess = findByDetachedCriteria("RPTSendType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unAllSendSuccess = findByDetachedCriteria("RPTSendType", "5", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unRPTSubmitStatus = findByDetachedCriteria("RPTSubmitStatus", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		rPTSubmitStatusSuccess = findByDetachedCriteria("RPTSubmitStatus", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unRPTVerifyType = findByDetachedCriteria("RPTVerifyType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		rPTVerifyTypeSuccess = findByDetachedCriteria("RPTVerifyType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
	
		rPTVerifyTypefalse = findByDetachedCriteria("RPTVerifyType", "3", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unRPTFeedbackType = findByDetachedCriteria("RPTFeedbackType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		rPTFeedbackTypeSuccess = findByDetachedCriteria("RPTFeedbackType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		rPTFeedbackTypefalse = findByDetachedCriteria("RPTFeedbackType", "3", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		unAllRPTFeedbackTypeSuccess = findByDetachedCriteria("RPTFeedbackType", "4", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao);
		
		if(checkSuccess==0 && checkfalse==0 && unCheck>=0 && unAllCheckSuccess==0){
			taskModelInst.setStrCheckState("1");
		}else if(checkSuccess>0 && checkfalse==0 && unCheck==0 && unAllCheckSuccess==0){
			taskModelInst.setStrCheckState("2");
		}
		else if(checkSuccess==0 && checkfalse >0 && unCheck==0 && unAllCheckSuccess==0){
			taskModelInst.setStrCheckState("3");
		}else{
			taskModelInst.setStrCheckState("4");
		}
		
		if(sendSuccess==0 && unSend>=0 && unAllSendSuccess==0){
			taskModelInst.setRPTSendType("1");
		}else if(sendSuccess>0 && unSend==0 && unAllSendSuccess==0){
			taskModelInst.setRPTSendType("2");
		}
		else {
			taskModelInst.setRPTSendType("5");
		}
		
		if(unRPTSubmitStatus>=0 && rPTSubmitStatusSuccess==0){
			taskModelInst.setSubmitStatus("1");
		}else if(rPTSubmitStatusSuccess>0 && unRPTSubmitStatus==0){
			taskModelInst.setSubmitStatus("2");
		}
		else {
			taskModelInst.setSubmitStatus("4");
		}
		
		if(unRPTVerifyType>=0 && rPTVerifyTypeSuccess==0 && rPTVerifyTypefalse==0 ){
			taskModelInst.setStrAllowState("1");
		}else if(rPTVerifyTypeSuccess>0 && unRPTVerifyType==0 && rPTVerifyTypefalse==0){
			taskModelInst.setStrAllowState("2");
		}
		else if(rPTVerifyTypeSuccess==0 && unRPTVerifyType==0 && rPTVerifyTypefalse>0){
			taskModelInst.setStrAllowState("3");
		}
		else {
			taskModelInst.setStrAllowState("4");
		}
		
		if(unRPTFeedbackType>=0 && rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unAllRPTFeedbackTypeSuccess==0){
			taskModelInst.setRPTFeedbackType("1");
		}else if(rPTFeedbackTypeSuccess>0 && unRPTFeedbackType==0 && rPTFeedbackTypefalse==0 && unAllRPTFeedbackTypeSuccess==0){
			taskModelInst.setRPTFeedbackType("2");
		}
		else if(rPTFeedbackTypeSuccess==0 && unRPTFeedbackType==0 && rPTFeedbackTypefalse>0 && unAllRPTFeedbackTypeSuccess==0){
			taskModelInst.setRPTFeedbackType("3");
		}
		else {
			taskModelInst.setRPTFeedbackType("4");
		}
		 
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{taskModelInst,null});
	
		return serviceResult;
	}
	
	/**
	 * 通过条件统计
	 * @param status 状态
	 * @param statusValue 状态值
	 * @param detachedCriteria 条件
	 * @param strTableName 表名
	 * @param taskModelInst 任务表对象
	 * @param singleObjectFindByCountDao 查找条数
	 * @return 查找到的条数
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Integer findByDetachedCriteria(String status, String statusValue, DetachedCriteria detachedCriteria, String strTableName, 
			TaskModelInst taskModelInst, IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
		detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
		return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
	}
	
/**
 * 通过条件统计基础段状态
 * @param status 状态
 * @param statusValue 状态值
 * @param detachedCriteria 条件
 * @param object_Jc 基础段对象
 * @param type 类型
 * @param singleObjectFindByCountDao 统计条数
 * @return 统计的条数
 * @throws Exception
 */
   public Integer findByDetachedCriteriaForJC(String status, String statusValue, DetachedCriteria detachedCriteria, Object object_Jc,Type type,IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
	    detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(type));
		detachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
   }
}
