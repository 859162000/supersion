package ncr.dao.imps;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.connection.ConnectionProvider;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.dao.DataAccessException;

import framework.dao.imps.BaseObjectResultDao;
/**
 * 
 * @author ctx101
 *根据Sql语句，返回list列表，list包含由1、2、3、4key值相对应map数组。
 */
public class CreateSqlQueryNcrDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		return null;
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
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); // 存放返回结果的容器
		Statement stmt = null;
        ResultSet rs = null;
        try {
        	stmt = conn.createStatement();
            rs = stmt.executeQuery(strSql);

            ResultSetMetaData metaData = rs.getMetaData(); // 取得结果集的元元素
            int colCount = metaData.getColumnCount(); // 取得所有列的个数
            
            while (rs.next()) {
            	Map<String, Object> m= new HashMap<String, Object>();
            	for(int i=1;i<=colCount;i++){  // 每一列
            		if(rs.getObject(i)==null){
            			m.put(String.valueOf(i), rs.getObject(i));
            		}else{
            			if((metaData.getColumnType(i)==2)){
            				m.put(String.valueOf(i), rs.getBigDecimal(i,metaData.getScale(i))+"");
            			}else{
            				m.put(String.valueOf(i), rs.getObject(i));
            			}
            			
            		}
                    
                }
                list.add(m);
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