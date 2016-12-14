package report.service.translate;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class TaskHistoryTranslate implements ITranslate{
	
	@SuppressWarnings("unchecked")
	public void Translate() throws Exception {
		
		DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
	
		detachedCriteria.add(Restrictions.lt("taskFlow.taskEndData",new Date()));
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);
	
	   }

}
