package framework.dao.imps;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;

import framework.services.interfaces.LogicParamManager;

public class CreateSqlQueryListMapDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery());
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		return objectResultExecute(objects[0].toString());
	}
	
	public Object objectResultExecute(String strSql) throws DataAccessException, ClassNotFoundException{
		SQLQuery sqlQuery = (SQLQuery) this.getSession().createSQLQuery(strSql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}
}
