package framework.dao.imps;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

import framework.services.interfaces.LogicParamManager;

public class ExcuteDialectSqlDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(LogicParamManager.getSqlQuery());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0].toString());
	}
	
	public void voidResultExecute(String strSql) throws DataAccessException, ClassNotFoundException, HibernateException, IllegalStateException, SQLException{
		if(getSession().connection().getMetaData().getDriverName().equals("Oracle JDBC driver")) {
			strSql = strSql.replace("IsNull", "nvl"); // 替换IsNull函数
			strSql = strSql.replace(" + ", " || "); // 替换IsNull函数
			strSql="begin "+strSql+" end;";
		}
		
		this.getSession().createSQLQuery(strSql).executeUpdate();
	}
}
