package report.service.translate;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class TaskStateFilterTranslate implements ITranslate{
@SuppressWarnings("unchecked")
/*
 * author:gechenglian
 * modify:huanglindong
 * */
 public void Translate() throws Exception {
		DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
		String alianInfo=detachedCriteria.toString();
		if(alianInfo.indexOf("Subcriteria(taskFlow")==-1)
		{
			detachedCriteria.createAlias("taskFlow", "taskFlow");
		}
		detachedCriteria.add(Restrictions.and(Restrictions.ge("taskFlow.taskEndData",new Date()), Restrictions.le("taskFlow.taskStartData",new Date())));
	   
	    
	    LogicParamManager.setDetachedCriteria(detachedCriteria);

    
	   }

}
