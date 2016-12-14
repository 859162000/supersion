package framework.dao.imps;

import framework.interfaces.RequestManager;

public class SingleObjectFindAllDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		String tName = Class.forName(RequestManager.getTName()).getSimpleName();
		return this.getHibernateTemplate().find("from " + tName);
	}
	
	@Override
	public Object objectResultExecute(Object[] param) throws Exception {
		if(param == null)
			return objectResultExecute();
		
		return this.getHibernateTemplate().find("from " + param[0].toString());
	}
}
