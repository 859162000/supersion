package framework.dao.imps;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;

import framework.interfaces.RequestManager;

public class SingleObjectFindByIdDao extends BaseObjectResultDao{
	@Override
	public Object objectResultExecute() throws Exception {

		return objectResultExecute(RequestManager.getTName(),RequestManager.getId());
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		return objectResultExecute(objects[0].toString(),objects[1]);
	}
	
	public Object objectResultExecute(String tName,Object id) throws DataAccessException, ClassNotFoundException{
		return this.getHibernateTemplate().get(Class.forName(tName), (Serializable)id);
	}
	
}
