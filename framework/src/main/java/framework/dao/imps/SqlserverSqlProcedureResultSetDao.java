package framework.dao.imps;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import framework.services.interfaces.LogicParamManager;

public class SqlserverSqlProcedureResultSetDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute(objects[0].toString());
	}
	
	@SuppressWarnings("deprecation")
	public Object objectResultExecute(String sql) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
        
		String strParams = sql.substring(sql.indexOf("(") + 1,sql.indexOf(")"));
		String callParam = "";
		if(!StringUtils.isBlank(strParams)){
			String[] params = strParams.split(",");
			for(int i=0;i<params.length;i++){
				if(i != 0){
					callParam += ",";
				}
				callParam += "?";
			}
			sql = sql.replaceAll(strParams, callParam);
		}

		CallableStatement callableStatement = this.getSession().connection().prepareCall(sql);
		
		if(!StringUtils.isBlank(strParams)){
			String[] params = strParams.split(",");
			for(int i=0;i<params.length;i++){
				callableStatement.setObject(i + 1,params[i]);
			}
		}

		callableStatement.execute();
		
		ResultSet resultSet = callableStatement.getResultSet();
		//SQLServerResultSet resultSet = (SQLServerResultSet)callableStatement.executeQuery();

		return resultSet;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery());
	}
}
