package framework.dao.imps;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;
import framework.services.interfaces.LogicParamManager;

public class SqlserverProcedureResultSetDao extends BaseObjectResultDao{

	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute(objects[0].toString(),(Map<String,Object>)objects[1]);
	}
	
	public Object objectResultExecute(String sql,Map<String,Object> procedureParam) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
		
		CallableStatement callableStatement = ((org.hibernate.engine.SessionFactoryImplementor)this.getSessionFactory()).getConnectionProvider().getConnection().prepareCall(sql);
		
		for(Map.Entry<String,Object> entry : procedureParam.entrySet()){
			if("rt".equals(entry.getKey())||"RT".equals(entry.getKey())){
				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
			}
			else{
				callableStatement.setObject(entry.getKey(),entry.getValue());
			}
		}

		ResultSet resultSet = callableStatement.executeQuery();
	
		return resultSet;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery(),LogicParamManager.getProcedureParam());
	}
}

