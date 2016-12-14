package framework.dao.imps;

import framework.interfaces.RequestManager;

public class SingleObjectSaveDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		
		voidResultExecute(RequestManager.getTOject());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0]);
	}

	public void voidResultExecute(Object object){
		this.getHibernateTemplate().save(object);
	}
}
