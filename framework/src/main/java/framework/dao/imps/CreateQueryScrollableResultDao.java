package framework.dao.imps;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import framework.interfaces.IGetScrollableResultsExecute;
import framework.services.interfaces.LogicParamManager;

public class CreateQueryScrollableResultDao  extends BaseObjectResultDao implements IGetScrollableResultsExecute{
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery());
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		return objectResultExecute(objects[0].toString());
	}
	
	public Object objectResultExecute(String strSql) throws DataAccessException, ClassNotFoundException{
		ScrollableResults scrollableResults  = this.getSession().createQuery(strSql).scroll();
		return scrollableResults;
	}
	
	
	public ScrollableResults getScrollableResults(String strSql,Session session) throws Exception {
		
		ScrollableResults scrollableResults  = session.createQuery(strSql).scroll();
		return scrollableResults;
	}
}
