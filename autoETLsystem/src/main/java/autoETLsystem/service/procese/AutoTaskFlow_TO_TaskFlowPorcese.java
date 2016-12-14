package autoETLsystem.service.procese;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import report.dto.AutoTaskFlow;
import report.dto.AutoTaskModelInst;
import report.dto.AutoTaskRptInst;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import report.dto.TaskRptInst;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;


/**
 * 根据系统时间把自动报表任务里定义的任务下发下去
 * @author:Liaoxl 
 */

/**
1、读出AutoTaskFlow里所有记录，然后开始循环其中的每条记录		
2、获取AutoTaskFlow里记录X的dtTaskDate和频度strFreq		
3、根据第二步的dtTaskDate和strFreq，以及系统当前日期，通过dtTaskDate加上频度得到下一个频度日期，然后与系统当前时间比对，
	直到找到离系统当前日期最近的那个频度日期X_dtTaskDate		
4、X_dtTaskDate加上X.taskStartDate+X.autoPubTaskTime计算出任务下发的触发时间点X_SubmitTime，如果系统时间与X_SubmitTime相差在24小时以内，
	进入下一步操作，否则continue。		
5、通过X_dtTaskDate,strFreq,strReportSuit和strTime四个属性在TaskFlow表里检查是否存在相同的记录，如果存在就不做处理,否则进入下一步		
	5.1、把记录X的dtTaskDate换成X_dtTaskDate，然后写入TaskFlow表	
	5.2、通过X.ID与AutoTaskRptInst.strTaskID关联，找到此任务下的报表，然后把这些报表信息写入到TaskRptInst表里	
	5.3、通过X.ID与AutoTaskModelInst.strTaskID关联，找到此任务下的报表，然后把这些报表信息写入到TaskModelInst表里	

 */
public class AutoTaskFlow_TO_TaskFlowPorcese implements IActivityNodeForJavaExtend{ 

	@Override
	public String Execute(List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,String strFixParameter) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//1.
		IParamObjectResultExecute singleObjectFindByCriteriaDao_Task = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = null;
		detachedCriteria = DetachedCriteria.forClass(report.dto.AutoTaskFlow.class);
		detachedCriteria.addOrder(Order.asc("suit.strSuitCode"));
		detachedCriteria.addOrder(Order.asc("strFreq"));
		detachedCriteria.addOrder(Order.asc("strTime"));
		List<AutoTaskFlow> autoTaskFlowList = (List<AutoTaskFlow>)singleObjectFindByCriteriaDao_Task.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		
		for(AutoTaskFlow autoTaskFlow : autoTaskFlowList){
			
			//2.
			Date dtTaskDate = autoTaskFlow.getDtTaskDate();
			String strFreq = autoTaskFlow.getStrFreq();
			String strTime = autoTaskFlow.getStrTime();
			String strTaskId = autoTaskFlow.getId();
			Suit reportSuit = autoTaskFlow.getSuit();
		
			//3、
			Date X_dtTaskDate = dtTaskDate;
			Calendar cal = Calendar.getInstance();
			cal.setTime(dtTaskDate);
			Date currentDate = new Date();
			long diffNum = Math.abs((dtTaskDate.getTime()-currentDate.getTime())/(24*60*60*1000));
			long temp = diffNum;
			
			switch(Integer.parseInt(strFreq)){
				case 6:	//年										
					do{
						diffNum = temp;
						cal.add(Calendar.YEAR, 1);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					cal.set(Calendar.DAY_OF_YEAR, 0);
					X_dtTaskDate = cal.getTime();
					break;
					
				case 5:	//半年
					do{
						diffNum = temp;
						cal.add(Calendar.MONTH, 6);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					
					cal.add(Calendar.MONTH, -5);
					cal.set(Calendar.DAY_OF_MONTH, 0);
					X_dtTaskDate = cal.getTime();
					break;
					
				case 4:	//季				
					do{
						diffNum = temp;
						cal.add(Calendar.MONTH, 3);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					
					cal.add(Calendar.MONTH, -2);
					cal.set(Calendar.DAY_OF_MONTH, 0);
					X_dtTaskDate = cal.getTime();
					break;
									
				case 3:	//月
					do{
						diffNum = temp;
						cal.add(Calendar.MONTH, 1);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					
					cal.set(Calendar.DAY_OF_MONTH, 0);
					X_dtTaskDate = cal.getTime();
					break;
					
				case 2:	//周
					do{
						diffNum = temp;
						cal.add(Calendar.DAY_OF_YEAR, 7);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					
					cal.add(Calendar.DAY_OF_YEAR, -7);
					X_dtTaskDate = cal.getTime();
					break;
					
				case 7:	//旬
					do{
						diffNum = temp;
						cal.add(Calendar.DAY_OF_YEAR, 10);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					
					cal.set(Calendar.DAY_OF_YEAR, -10);
					X_dtTaskDate = cal.getTime();
					break;
					
				default:	//日，日频度需要单独处理一下
				/*	do{
						diffNum = temp;
						cal.add(Calendar.DAY_OF_YEAR, 1);
						temp = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(24*60*60*1000));
					}
					while(diffNum > temp);
					
					cal.add(Calendar.DAY_OF_YEAR, -1);*/
					
					cal.setTime(currentDate);
					cal.add(Calendar.DAY_OF_YEAR, -Integer.parseInt(autoTaskFlow.getTaskStartDate()));
					
					X_dtTaskDate = cal.getTime();
					break;
					
			}
			
		
			//4、cal相当于X_SubmitTime
			if(strFreq.equals("1") )
			{				
				cal.setTime(sdf.parse(sdf.format(currentDate)));	//把时间设置为系统当前日期的0点。
				cal.add(Calendar.HOUR, Integer.parseInt(autoTaskFlow.getAutoPubTaskTime()));
			}
			else
			{
				cal.setTime(X_dtTaskDate);
				cal.add(Calendar.DAY_OF_YEAR,Integer.parseInt(autoTaskFlow.getTaskStartDate()));
				cal.add(Calendar.HOUR, Integer.parseInt(autoTaskFlow.getAutoPubTaskTime()));
			}
			
			
			//调试时方便查看用
//			Date tempDate = cal.getTime();
//			diffNum = Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(60*60*1000)); 
			
			if(Math.abs((cal.getTimeInMillis()-currentDate.getTime())/(60*60*1000)) <= 24)
			{
				//5
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskFlow"));
				detachedCriteria.add(Restrictions.eq("dtTaskDate", X_dtTaskDate));
				detachedCriteria.add(Restrictions.eq("strFreq", strFreq));
				detachedCriteria.add(Restrictions.eq("strTime", strTime));
				detachedCriteria.add(Restrictions.eq("suit.strSuitCode", reportSuit.getStrSuitCode()));
				
				
				List<TaskFlow> TaskFlowList=(List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				if(TaskFlowList.size()<=0){
					IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					
					//5.1
					TaskFlow taskFlow = new TaskFlow();
					
					
					taskFlow.setDtTaskDate(sdf.format(X_dtTaskDate));
					
					taskFlow.setStrFreq(strFreq);
					taskFlow.setStrTime(strTime);
					taskFlow.setSuit(reportSuit);
					
					taskFlow.setStrTaskName(autoTaskFlow.getStrTaskName());
					taskFlow.setStrTaskState(autoTaskFlow.getStrTaskState());
						
					taskFlow.setTaskStartData(sdf.format(cal.getTime()));
					
					cal.setTime(X_dtTaskDate);
					cal.add(Calendar.DAY_OF_YEAR,Integer.parseInt(autoTaskFlow.getTaskEndDate()));
					
					taskFlow.setTaskEndData(sdf.format(cal.getTime()));
					
					List<Object> objList = new ArrayList();
					objList.add(taskFlow);
					
					singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objList,null});
					
									
					
					//先把刚刚插入的那条记录查出来，需要它的ID号，因为查询条件没有变化，直接用上面刚写的查询条件
					TaskFlowList=(List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
					
					if(TaskFlowList.size() == 1)
					{
						TaskFlow newTaskFlow = TaskFlowList.get(0);
											
						//5.2
						TaskRptInst taskRptInst;
						List<TaskRptInst> taskRptInstList = new ArrayList();
						
						detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.AutoTaskRptInst"));
						detachedCriteria.add(Restrictions.eq("autoTaskFlow.id",strTaskId));
						List<AutoTaskRptInst> AutoTaskRptInstList=(List<AutoTaskRptInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for (AutoTaskRptInst autoTaskRptInst : AutoTaskRptInstList) {
							taskRptInst = new TaskRptInst();
							
							taskRptInst.setInstInfo(autoTaskRptInst.getInstInfo());
							taskRptInst.setRptInfo(autoTaskRptInst.getRptInfo());
							taskRptInst.setTaskFlow(newTaskFlow);
							
							taskRptInst.setSubmitStatus("1");
							taskRptInst.setStrCheckState("1");
							taskRptInst.setStrAllowState("1");
							taskRptInst.setRPTSendType("1");
					
							taskRptInstList.add(taskRptInst);
						}
						
						//5.3
						TaskModelInst taskModelInst;
						List<TaskModelInst> taskModelInstList = new ArrayList(); 
						
						detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.AutoTaskModelInst"));
						detachedCriteria.add(Restrictions.eq("autoTaskFlow.id",strTaskId));
						List<AutoTaskModelInst> AutoTaskModelInstList=(List<AutoTaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for (AutoTaskModelInst autoTaskModelInst : AutoTaskModelInstList) {
							taskModelInst = new TaskModelInst();
							
							taskModelInst.setInstInfo(autoTaskModelInst.getInstInfo());
							taskModelInst.setReportModel_Table(autoTaskModelInst.getReportModel_Table());
							taskModelInst.setTaskFlow(newTaskFlow);
							
							taskModelInst.setSubmitStatus("1");
							taskModelInst.setStrCheckState("1");
							taskModelInst.setStrAllowState("1");
							taskModelInst.setRPTSendType("1");
							
							taskModelInstList.add(taskModelInst);
						}

						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{taskRptInstList,null});
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{taskModelInstList,null});

					}
				}
			}
		}
		
		return "成功";
	}

	
}

