package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.PF_LegalInfo;

import autoETLsystem.dto.AutoETL_Workflow;
import autoETLsystem.dto.AutoETL_WorkflowLog;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;

public class AutoWorkflowService implements Runnable{

	private Integer sleepTime;
	
	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	private String defaultOracleProcedureParamName;
	
	public void setDefaultOracleProcedureParamName(String defaultOracleProcedureParamName) {
		this.defaultOracleProcedureParamName = defaultOracleProcedureParamName;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		while(true){
			try {
				
				Thread.sleep(sleepTime * 60000);
				
				Set<Thread> threadSet = new HashSet<Thread>();
				
				IParamObjectResultExecute singleObjectFindByAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");				
				List<PF_LegalInfo> sesstionFactorys=(List<PF_LegalInfo>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{PF_LegalInfo.class.getName(),null});
				if(null==sesstionFactorys || sesstionFactorys.size()==0){
					if(null==sesstionFactorys){
						sesstionFactorys= new ArrayList<PF_LegalInfo>();	
					}
					PF_LegalInfo e=new PF_LegalInfo();					
					sesstionFactorys.add(e);
				}
				for(PF_LegalInfo pF_LegalInfo:sesstionFactorys){
					String sessionFactory = pF_LegalInfo.getStrSessionFactory();
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_Workflow.class);
					detachedCriteria.add(Restrictions.eq("strEffectiveType", "1"));
					List<AutoETL_Workflow> autoETL_WorkflowList = (List<AutoETL_Workflow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});
					for(AutoETL_Workflow autoETL_Workflow : autoETL_WorkflowList){
						ThreadWorkflowRunnable threadWorkflowRunnable = new ThreadWorkflowRunnable(autoETL_Workflow,defaultOracleProcedureParamName,sessionFactory);
						if(threadWorkflowRunnable.initWorkflowParam()){
							Set<Integer> threadGroupSet = new HashSet<Integer>();
							Set<Integer> groupSet = threadWorkflowRunnable.getGroupSet();
							
							for(Integer group : groupSet){
								List<AutoETL_WorkflowParamMV> groupAutoETL_WorkflowParamMVList = threadWorkflowRunnable.getAutoETL_WorkflowParamMVListByGroup(group);
								String workflowParam = threadWorkflowRunnable.getParamStr(groupAutoETL_WorkflowParamMVList);
								if(initAutoStartThread(autoETL_Workflow,workflowParam,sessionFactory)){
									threadGroupSet.add(group);
								}
							}
							
							if(threadGroupSet.size() >0){
								threadWorkflowRunnable.setGroupSet(threadGroupSet);
								Thread thread = new Thread(threadWorkflowRunnable);
								thread.start();
								threadSet.add(thread);
							}
						}
					}
				}
							
				for(Thread thread : threadSet){
					thread.join();
				}
				for(Thread thread : threadSet){
					thread.interrupt();
				}
			} 
			catch (Exception ex) {
				ExceptionLog.CreateLog(ex);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public boolean initAutoStartThread(AutoETL_Workflow autoETL_Workflow,String workflowParam,String sessionFactory) throws Exception{
		if(StringUtils.isBlank(workflowParam)){
			return false;
		}
		else{
			
			Date currentDate = new Date();
			if(currentDate.getHours() < autoETL_Workflow.getStartTime() || currentDate.getHours() > autoETL_Workflow.getEndTime() - 1){
				return false;
			}
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowLog.class);
			detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
			detachedCriteria.add(Restrictions.eq("strParam", workflowParam));
			detachedCriteria.addOrder(Order.desc("dtDate"));
			List<AutoETL_WorkflowLog> autoETL_WorkflowLogList = (List<AutoETL_WorkflowLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});
			
			for(AutoETL_WorkflowLog autoETL_WorkflowLog : autoETL_WorkflowLogList){
				if(autoETL_WorkflowLog.getStrResultType().equals("1")){
					return false;
				}
			}
			for(AutoETL_WorkflowLog autoETL_WorkflowLog : autoETL_WorkflowLogList){
				if(autoETL_WorkflowLog.getStrResultType().equals("2") || autoETL_WorkflowLog.getStrResultType().equals("3") || autoETL_WorkflowLog.getStrResultType().equals("5")){
					Date excuteDate = autoETL_WorkflowLog.getDtDate();
					if((new Date().getTime() - excuteDate.getTime())/60000 < autoETL_Workflow.getErrorWaitTime()){
						return false;
					}
				}
			}
			for(AutoETL_WorkflowLog autoETL_WorkflowLog : autoETL_WorkflowLogList){
				if(autoETL_WorkflowLog.getStrResultType().equals("4")){
					Date excuteDate = autoETL_WorkflowLog.getDtDate();
					if((new Date().getTime() - excuteDate.getTime())/60000 < autoETL_Workflow.getProceseWaitTime()){
						return false;
					}
				}
			}
		}
		return true;
	}

	
}
