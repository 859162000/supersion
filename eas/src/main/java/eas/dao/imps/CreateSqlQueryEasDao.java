package eas.dao.imps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.connection.ConnectionProvider;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.dao.DataAccessException;

import framework.dao.imps.BaseObjectResultDao;
import framework.services.interfaces.LogicParamManager;
/**
 * 主要为EAS下载，只支持返回第一列
 * @author ctx101
 *根据Sql语句，返回list列表，list包含由1、2、3、4key值相对应map数组。
 */
public class CreateSqlQueryEasDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		String strSQL = LogicParamManager.getSqlQuery();
		if(strSQL == null || strSQL.equals("")) {
			LogicParamManager.setTotalCount(0); // 设置结果条数
			return null;
		}
		
		return objectResultExecute(strSQL);
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		return objectResultExecute(objects[0].toString());
	}
	
	public Object objectResultExecute(String strSql) throws DataAccessException, ClassNotFoundException, SQLException{
		if(strSql == null || strSql.equals("")) {
			return null;
		}
		
		SessionFactoryImpl sf = (SessionFactoryImpl)getSessionFactory();
		ConnectionProvider connProvide = sf.getConnectionProvider();
		Connection conn = connProvide.getConnection();
		
		List list = new ArrayList(); // 存放返回结果的容器
		PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
        	stmt = conn.prepareStatement(strSql);
            rs = stmt.executeQuery();

            while (rs.next()) {
            	list.add(rs.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }
        
		return list;
	}
	
}