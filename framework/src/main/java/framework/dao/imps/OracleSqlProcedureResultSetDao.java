package framework.dao.imps;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import framework.services.interfaces.LogicParamManager;

public class OracleSqlProcedureResultSetDao extends BaseObjectResultDao{

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

		int cursonParam = 0;
		CallableStatement callableStatement = this.getSession().connection().prepareCall(sql);
		
		if(!StringUtils.isBlank(strParams)){
			String[] params = strParams.split(",");
			for(int i=0;i<params.length;i++){
				if(params[i].indexOf("@") > -1){
					cursonParam = i+1;
					callableStatement.registerOutParameter(i+1, oracle.jdbc.OracleTypes.CURSOR);
				}
				else{
					callableStatement.setObject(i + 1,params[i]);
				}
			}
		}
		
		callableStatement.execute();
		ResultSet resultSet = (ResultSet)callableStatement.getObject(cursonParam);
		
		return resultSet;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery());
	}
}
