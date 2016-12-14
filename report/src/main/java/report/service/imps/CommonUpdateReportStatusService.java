package report.service.imps;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;

import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
/**
 * 统计报表任务状态通用类
 * @author Transino
 * tableDTONameList dto名字列表
 * Object_JCListTemp 数据对象列表
 * instInfoSubList 行内机构列表
 */
public class CommonUpdateReportStatusService {

	public void commonUpdateReportStatus(List<?> Object_JCListTemp,List<InstInfo> instInfoSubList) throws Exception{

		if(Object_JCListTemp.size() > 0){
			String strDtoName= Object_JCListTemp.get(0).getClass().getName();
			String strTableName=strDtoName.substring(strDtoName.indexOf("AutoDTO_")+8);
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			
			strTableName=strDtoName.substring(strDtoName.indexOf("AutoDTO_")+8);
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strDtoName));
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
			List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			detachedCriteria.add(Restrictions.eq("dtTaskDate", ReflectOperation.getFieldValue(Object_JCListTemp.get(0), "dtDate")));	
			
			List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			List<TaskModelInst>  taskModelInstList=new ArrayList<TaskModelInst>();
			
			detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			if(instInfoSubList!=null && instInfoSubList.size()>0){
				detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
			}
			if(taskFlowList.size()>0){
				detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
			}
			if(reportModel_TableList.size()>0){
				detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
			}
			
		    taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		    
		    if(taskModelInstList.size()>0){
		    	TaskModelInst taskModelInst=taskModelInstList.get(0);
		    	
		    	int checkSuccess=0;
		    	int checkfalse=0;
		    	int unCheck=0;
				int unAllCheckSuccess=0;
				
				int unRPTSubmitStatus=0;
				int rPTSubmitStatusSuccess=0;
				
				int unRPTVerifyType=0;
				int rPTVerifyTypeSuccess=0;
				int rPTVerifyTypefalse=0;
				
				int  sendSuccess=0;
				int  unSend=0;
				int  unAllSendSuccess=0;
				 
		    	int unRPTFeedbackType=0;
				int rPTFeedbackTypeSuccess=0;
				int rPTFeedbackTypefalse=0;
				int unAllRPTFeedbackTypeSuccess=0;
				
				unCheck = findByDetachedCriteria("RPTCheckType", "1", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				checkSuccess = findByDetachedCriteria("RPTCheckType", "2", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				checkfalse = findByDetachedCriteria("RPTCheckType", "3", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unAllCheckSuccess = findByDetachedCriteria("RPTCheckType", "4", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unSend = findByDetachedCriteria("RPTSendType", "1", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				sendSuccess=findByDetachedCriteria("RPTSendType", "2", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unAllSendSuccess=findByDetachedCriteria("RPTSendType", "5", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unRPTSubmitStatus=findByDetachedCriteria("RPTSubmitStatus", "1", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				rPTSubmitStatusSuccess=findByDetachedCriteria("RPTSubmitStatus", "2", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unRPTVerifyType=findByDetachedCriteria("RPTVerifyType", "1", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				rPTVerifyTypeSuccess=findByDetachedCriteria("RPTVerifyType", "2", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				rPTVerifyTypefalse=findByDetachedCriteria("RPTVerifyType", "3", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unRPTFeedbackType=findByDetachedCriteria("RPTFeedbackType", "1", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				rPTFeedbackTypeSuccess=findByDetachedCriteria("RPTFeedbackType", "2", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				rPTFeedbackTypefalse=findByDetachedCriteria("RPTFeedbackType", "3", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
				unAllRPTFeedbackTypeSuccess=findByDetachedCriteria("RPTFeedbackType", "4", detachedCriteria, strDtoName, taskModelInst, singleObjectFindByCountDao);
				
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
			
		}
	}
	
	/**
	 * 通过条件统计任务状态
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
   /**
    * 获取行内机构代码列表通用方法
    * @param JRJGDM 金融机构代码
    * @return 行内机构的列表
    * @throws Exception
    */
    public List<InstInfo> getInstInfoSubList(String JRJGDM) throws Exception{
	    List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
		IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
		detachedCriteriaInstCode.add(Restrictions.eq("reportInstInfo.strReportInstCode",JRJGDM));
		List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
	
		if(reportInstSubInfoList.size()>0){
			for (ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList) {
				Object obj=ReflectOperation.getFieldValue(reportInstSubInfo, "instInfo");
				String strInstCode=ReflectOperation.getFieldValue(obj, "strInstCode").toString();
				InstInfo instInfo = new InstInfo();
				instInfo.setStrInstCode(strInstCode);
				instInfoSubList.add(instInfo);
			}
		}
		return instInfoSubList;
    }
}
