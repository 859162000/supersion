package framework.dao.imps;

import org.springframework.dao.DataAccessException;

import framework.services.interfaces.LogicParamManager;

public class ExcuteSqlDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(LogicParamManager.getSqlQuery());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0].toString());
	}
	
	public void voidResultExecute(String strSql) throws DataAccessException, ClassNotFoundException{
		this.getSession().createSQLQuery(strSql).executeUpdate();
	}
}
