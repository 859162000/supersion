package report.service.translate;

import org.hibernate.criterion.DetachedCriteria;

import framework.dao.imps.OrderBySqlFormula;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class OrderBySqlFormulaTranslate implements ITranslate {

	public void Translate() throws Exception {
		
		Class<?> type = Class.forName(RequestManager.getTName());

		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		
		String orderbySqlFormula=" case when this_.RPTSendType='2' then '5' when this_.RPTSendType='5' then '2' else   this_.RPTSendType end,case when this_.RPTCheckType='3' then '2' when this_.RPTCheckType='4' then '3' when this_.RPTCheckType='2' then '4' else   this_.RPTCheckType end,this_."+ReflectOperation.getPrimaryKeyField(RequestManager.getTName()).getName();
		detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
				
	}

}
