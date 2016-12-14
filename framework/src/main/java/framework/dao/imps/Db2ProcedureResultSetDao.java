package framework.dao.imps;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import framework.dao.imps.BaseObjectResultDao;
import framework.services.interfaces.LogicParamManager;

public class Db2ProcedureResultSetDao extends BaseObjectResultDao{

	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute(objects[0].toString(),(Map<String,Object>)objects[1]);
	}
	
	@SuppressWarnings("deprecation")
	public ResultSet objectResultExecute(String sql,Map<String,Object> procedureParam) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
		CallableStatement callableStatement = this.getSession().connection().prepareCall(sql);
		int paramIndex=1;
		for(Map.Entry<String,Object> entry : procedureParam.entrySet()){
			if(entry.getValue() != null){
				callableStatement.setString(paramIndex,entry.getValue().toString());
			}
			else{
				callableStatement.setString(paramIndex,"");
			}
			paramIndex++;
		}
		return callableStatement.executeQuery();
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery(),LogicParamManager.getProcedureParam());
	}
}

