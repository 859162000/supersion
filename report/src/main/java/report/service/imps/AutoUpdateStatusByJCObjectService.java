package report.service.imps;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;

/**
 * 统计状态（通过基础段对象统计基础段和任务层的状态）
 * @author Transino
 *
 */
public class AutoUpdateStatusByJCObjectService {
	
	/**
	 * 根据基础段对象统计基础段状态
	 * @param objectJC
	 * @throws Exception 
	 */
	public void getJCStatusByMx(Object object_Jc) throws Exception{
		
		int checkSuccess=0;
		int checkfalse=0;
		int unCheck=0;
		int sendSuccess=0;
		int unSend=0;
		
		int unRPTFeedbackType=0;
		int rPTFeedbackTypeSuccess=0;
		int rPTFeedbackTypefalse=0;
		
		List<Field> dtoListField=ReflectOperation.getOneToManyField(object_Jc.getClass().getName());
		DetachedCriteria detachedCriteria = null; 
		
		for (Field field : dtoListField) {
			
			IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			unCheck += findByDetachedCriteriaForJC("RPTCheckType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			checkSuccess += findByDetachedCriteriaForJC("RPTCheckType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			checkfalse += findByDetachedCriteriaForJC("RPTCheckType","3", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			unSend += findByDetachedCriteriaForJC("RPTSendType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			sendSuccess += findByDetachedCriteriaForJC("RPTSendType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			unRPTFeedbackType += findByDetachedCriteriaForJC("RPTFeedbackType","1", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			rPTFeedbackTypeSuccess += findByDetachedCriteriaForJC("RPTFeedbackType","2", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
			
			rPTFeedbackTypefalse += findByDetachedCriteriaForJC("RPTFeedbackType","3", detachedCriteria,object_Jc,field.getGenericType(),singleObjectFindByCountDao);
		   
		}
		if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("1")){
			unCheck+=1;
		}else if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("2")){
			checkSuccess+=1;
		}
		else if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("3")){
			checkfalse+=1;
		}
		
		String SubRPTCheckType="";
		if(ReflectOperation.getFieldValue(object_Jc, "RPTCheckType").equals("4")){
			SubRPTCheckType = "1";
		}
		else{
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
		}
		ReflectOperation.setFieldValue(object_Jc, "RPTCheckType", SubRPTCheckType);
		
		if(sendSuccess > 0 && unSend==0){
				ReflectOperation.setFieldValue(object_Jc, "RPTSendType", "2");
		}
		else if(sendSuccess == 0 && unSend>0){
			ReflectOperation.setFieldValue(object_Jc, "RPTSendType", "1");
		}
		else if(sendSuccess == 0 && unSend==0){
			
		}else{
			ReflectOperation.setFieldValue(object_Jc, "RPTSendType", "5");
		}
		
		if(rPTFeedbackTypeSuccess>0 && unRPTFeedbackType==0 && rPTFeedbackTypefalse==0){
		 		ReflectOperation.setFieldValue(object_Jc, "RPTFeedbackType", "2");
	 	}else if(rPTFeedbackTypeSuccess==0 && unRPTFeedbackType>0 && rPTFeedbackTypefalse==0){
	 		ReflectOperation.setFieldValue(object_Jc, "RPTFeedbackType", "1");
	 	}
	 	else if(rPTFeedbackTypeSuccess==0 && unRPTFeedbackType==0 && rPTFeedbackTypefalse>0){
	 		ReflectOperation.setFieldValue(object_Jc, "RPTFeedbackType", "3");
	 	}
	 	else{
	 		ReflectOperation.setFieldValue(object_Jc, "RPTFeedbackType", "4");
	 	}
	}
	
	/**
	 * 根据基础段对象统计任务层状态
	 * @param object_Jc
	 * @throws Exception
	 */
	public void getRWStatusByJC(Object object_Jc) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
		String strTableName=object_Jc.getClass().getName().substring((object_Jc.getClass().getName()).indexOf("AutoDTO_")+8);
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
	    TaskModelInst taskModelInst=taskModelInstList.get(0);
	    
	     int checkSuccess=0;
	     int checkfalse=0;
		 int unCheck=0;
		 int unAllCheckSuccess=0;
		 
		 int sendSuccess=0;
		 int unSend=0;
		 int unAllSendSuccess=0;
		 
		int unRPTSubmitStatus=0;
		int rPTSubmitStatusSuccess=0;
		
		int unRPTVerifyType=0;
		int rPTVerifyTypeSuccess=0;
		int rPTVerifyTypefalse=0;
		
		int unRPTFeedbackType=0;
		int rPTFeedbackTypeSuccess=0;
		int rPTFeedbackTypefalse=0;
		int unAllRPTFeedbackTypeSuccess=0;
		//筛选条件时，排除回执状态为5，已删除的数据
		String tableDTOName=object_Jc.getClass().getName();
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
	 * 通过（明细）条件统计基础段状态
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
