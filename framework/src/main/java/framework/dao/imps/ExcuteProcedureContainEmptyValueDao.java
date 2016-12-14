package framework.dao.imps;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import framework.services.interfaces.LogicParamManager;

public class ExcuteProcedureContainEmptyValueDao extends BaseVoidResultDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public void voidResultExecute(Object[] objects) throws Exception{ 
		voidResultExecute(objects[0].toString(),(Map<String,Object>)objects[1]);
	}
	
	@SuppressWarnings("deprecation")
	public void voidResultExecute(String sql,Map<String,Object> procedureParam) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
		CallableStatement callableStatement = this.getSession().connection().prepareCall(sql);
		for(Map.Entry<String,Object> entry : procedureParam.entrySet()){
//			if(entry.getValue() == null){
//				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
//			}
			if("rt".equals(entry.getKey())||"RT".equals(entry.getKey())){
				callableStatement.registerOutParameter(entry.getKey(), oracle.jdbc.OracleTypes.CURSOR);
			}
			else{
				callableStatement.setObject(entry.getKey(),entry.getValue());
			}
		}
		callableStatement.execute();
	}
	
	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(LogicParamManager.getSqlQuery(),LogicParamManager.getProcedureParam());
	}
}
