package jmx.dao;


import org.hibernate.criterion.DetachedCriteria;

import framework.dao.imps.BaseObjectResultDao;
import framework.services.interfaces.LogicParamManager;

public class SingleObjectFindByLimitCriteriaDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getDetachedCriteria(),100);
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute((DetachedCriteria)objects[0],(Integer)objects[1]);
	}
	
	public Object objectResultExecute(DetachedCriteria detachedCriteria,int limit) throws Exception {
		if(limit==0){
			return this.getHibernateTemplate().findByCriteria(detachedCriteria);
		}
		return this.getHibernateTemplate().findByCriteria(detachedCriteria, 0, limit);
	}
}

