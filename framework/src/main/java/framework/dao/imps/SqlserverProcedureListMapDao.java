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

import framework.services.interfaces.LogicParamManager;

public class SqlserverProcedureListMapDao extends BaseObjectResultDao{

	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return objectResultExecute(objects[0].toString(),(Map<String,Object>)objects[1]);
	}
	
	@SuppressWarnings("deprecation")
	public Object objectResultExecute(String sql,Map<String,Object> procedureParam) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		CallableStatement callableStatement = this.getSession().connection().prepareCall(sql);
		
		for(Map.Entry<String,Object> entry : procedureParam.entrySet()){
//			if(entry.getValue() == null || StringUtils.isBlank(entry.getValue().toString())){
//				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
//			}
			if("rt".equals(entry.getKey())||"RT".equals(entry.getKey())){
				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
			}
			else{
				callableStatement.setObject(entry.getKey(),entry.getValue());
			}
		}

		ResultSet resultSet = callableStatement.executeQuery();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int fieldCount = resultSetMetaData.getColumnCount();  
		while(resultSet.next()){
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            for (int i = 1; i <= fieldCount; i++) {  
                String fieldName = resultSetMetaData.getColumnName(i);   
                Object object = resultSet.getObject(fieldName);
                valueMap.put(fieldName, object);
            }  
            list.add(valueMap);
		}
		resultSet.close();
	
		return list;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery(),LogicParamManager.getProcedureParam());
	}
}

