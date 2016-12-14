package framework.dao.imps;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import framework.services.interfaces.LogicParamManager;

public class OracleProcedureResultSetDao extends BaseObjectResultDao{

	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute(objects[0].toString(),(Map<String,Object>)objects[1]);
	}
	
	@SuppressWarnings("deprecation")
	public Object objectResultExecute(String sql,Map<String,Object> procedureParam) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
		CallableStatement callableStatement = this.getSession().connection().prepareCall(sql);
		String cursonParam = null;
		
		for(Map.Entry<String,Object> entry : procedureParam.entrySet()){
//			if(entry.getValue() == null || StringUtils.isBlank(entry.getValue().toString())){
//				cursonParam = entry.getKey();
//				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
//			}
			if("rt".equals(entry.getKey())||"RT".equals(entry.getKey())){
				cursonParam = entry.getKey();
				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
			}
			else{
				callableStatement.setObject(entry.getKey(),entry.getValue());
			}
		}
		
		callableStatement.execute();
		ResultSet resultSet = (ResultSet)callableStatement.getObject(cursonParam);
		
		return resultSet;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery(),LogicParamManager.getProcedureParam());
	}
}
