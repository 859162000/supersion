package report.service.translate;

import org.hibernate.criterion.DetachedCriteria;

import framework.dao.imps.OrderBySqlFormula;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class MXOrderByYWFSRQTranslate implements ITranslate {

	public void Translate() throws Exception {
		
		Class<?> type = Class.forName(RequestManager.getTName());

		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		String orderbySqlFormula=" case when (this_.extend2='' or this_.extend2 is null) then '20501231' else this_.extend2 end,this_."+ReflectOperation.getPrimaryKeyField(RequestManager.getTName()).getName()+" desc";
		detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
	}
}
