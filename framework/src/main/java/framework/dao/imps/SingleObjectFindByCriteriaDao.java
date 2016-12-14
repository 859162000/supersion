package framework.dao.imps;

import org.hibernate.criterion.DetachedCriteria;

import framework.services.interfaces.LogicParamManager;

public class SingleObjectFindByCriteriaDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getDetachedCriteria());
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute((DetachedCriteria)objects[0]);
	}
	
	public Object objectResultExecute(DetachedCriteria detachedCriteria) throws Exception {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
