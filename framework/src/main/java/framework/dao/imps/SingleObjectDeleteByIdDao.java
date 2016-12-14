package framework.dao.imps;

import java.io.Serializable;

import framework.interfaces.RequestManager;

public class SingleObjectDeleteByIdDao extends BaseVoidResultDao{
	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(RequestManager.getTName(),RequestManager.getId());
	}

	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0].toString(),objects[1]);
	}

	public void voidResultExecute(String tName,Object id) throws Exception{
		this.getHibernateTemplate().delete(this.getHibernateTemplate().get(Class.forName(tName), (Serializable)id));
	}
}
