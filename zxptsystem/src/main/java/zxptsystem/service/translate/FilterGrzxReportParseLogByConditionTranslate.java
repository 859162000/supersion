package zxptsystem.service.translate;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class FilterGrzxReportParseLogByConditionTranslate implements ITranslate{

	@Override
	public void Translate() throws Exception {
		Class<?> type = Class.forName(RequestManager.getTName());
		String actionName = RequestManager.getActionName();
		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		detachedCriteria.add(Restrictions.like("sendFlag", "2", MatchMode.ANYWHERE));
		
	}

}