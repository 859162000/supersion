package report.dao.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.RptDto;
import report.dto.TaskModelInst;
import report.dto.TaskRptInst;

import framework.dao.imps.BaseVoidResultDao;
import framework.services.interfaces.LogicParamManager;

public class TaskRptInstUpdateListDao extends BaseVoidResultDao{


	@SuppressWarnings("unchecked")
	@Override
	public void voidResultExecute() throws Exception { 
		List<TaskRptInst> deleteTaskRptInstList= this.getHibernateTemplate().findByCriteria(LogicParamManager.getDetachedCriteria());
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
		if(deleteTaskRptInstList.size() > 0){
			detachedCriteria.add(Restrictions.eq("taskFlow", deleteTaskRptInstList.get(0).getTaskFlow()));
		}
		List<TaskModelInst> deleteTaskModelInst = this.getHibernateTemplate().findByCriteria(detachedCriteria);
			
		Set<TaskRptInst> saveTaskRptInstList = (Set<TaskRptInst>)LogicParamManager.getSaveObjectList();
		List<TaskModelInst> saveTaskModelInst = new ArrayList<TaskModelInst>();
		for(TaskRptInst taskRptInst : saveTaskRptInstList){
			detachedCriteria = DetachedCriteria.forClass(RptDto.class);
			detachedCriteria.add(Restrictions.eq("rptInfo", taskRptInst.getRptInfo()));
			List<RptDto> rptDtoList =  this.getHibernateTemplate().findByCriteria(detachedCriteria);
			for(RptDto rptDto : rptDtoList){
				TaskModelInst taskModelInst = new TaskModelInst();
				taskModelInst.setTaskFlow(taskRptInst.getTaskFlow());
				taskModelInst.setInstInfo(taskRptInst.getInstInfo());
				taskModelInst.setReportModel_Table(rptDto.getTable());
				saveTaskModelInst.add(taskModelInst);
			}
		}
		
		this.getHibernateTemplate().deleteAll(deleteTaskRptInstList);
		this.getHibernateTemplate().deleteAll(deleteTaskModelInst);
		this.getHibernateTemplate().saveOrUpdateAll(saveTaskRptInstList);
		this.getHibernateTemplate().saveOrUpdateAll(saveTaskModelInst);
	}
	
	
}

