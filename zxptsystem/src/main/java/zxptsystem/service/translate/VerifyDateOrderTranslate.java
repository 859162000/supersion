package zxptsystem.service.translate;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class VerifyDateOrderTranslate implements ITranslate {

	@Override
	public void Translate() throws Exception {
		DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();		
		detachedCriteria.addOrder(Order.desc("timeVerifyDateTime"));
	}

}
