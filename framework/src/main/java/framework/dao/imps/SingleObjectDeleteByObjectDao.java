package framework.dao.imps;

import framework.interfaces.RequestManager;

public class SingleObjectDeleteByObjectDao extends BaseVoidResultDao{
	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(RequestManager.getTOject());
	}

	public void voidResultExecute(Object object) throws Exception{
		this.getHibernateTemplate().delete(object);
	}
}
