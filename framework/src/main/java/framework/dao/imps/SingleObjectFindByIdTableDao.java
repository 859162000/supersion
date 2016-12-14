package framework.dao.imps;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.impl.SessionFactoryImpl;


import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;

public class SingleObjectFindByIdTableDao extends BaseObjectResultDao{
	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute() throws Exception {
		String strSQL = LogicParamManager.getSqlQuery();
		SessionFactoryImpl sf = (SessionFactoryImpl)getSessionFactory();
		ConnectionProvider connProvide = sf.getConnectionProvider();
		Connection conn = connProvide.getConnection();
		
		List list = new ArrayList(); // 存放返回结果的容器
		Statement stmt = null;
        ResultSet rs = null;
        try {
        	stmt = conn.createStatement();
            rs = stmt.executeQuery(strSQL);

            ResultSetMetaData metaData = rs.getMetaData(); // 取得结果集的元元素
            int colCount = metaData.getColumnCount(); // 取得所有列的个数
            Class<?> type = Class.forName(RequestManager.getTName());
            Field[] fields = ReflectOperation.getReflectFields(type);// 取得业务对象的属性
            while (rs.next()) {
            	ClassLoader loader = null;//DDLService.class.getClassLoader();
    			Object newInst=loader.loadClass(RequestManager.getTName()).newInstance();
            	//Object newInst = Type.class.newInstance(); // 构造业务对象实例
            	for(int i=1;i<=colCount;i++){  // 每一列
                    try{
                        Object value = rs.getObject(i);
                        for(int j=0;j<fields.length;j++){
                            Field f = fields[j];
                            if(f.getName().equalsIgnoreCase(metaData.getColumnName(i).replaceAll("_",""))) {
                                BeanUtils.copyProperty(newInst, f.getName(), value);
                            }
                        }
                    }catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            	list.add(newInst);
            }
        } catch (SQLException e) {
            // Connection 是当前 Session 的 Connection，不能关闭
            rs.close();
            stmt.close();
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }

		return list;
	}
}
