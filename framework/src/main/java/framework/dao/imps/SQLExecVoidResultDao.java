package framework.dao.imps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import framework.services.interfaces.LogicParamManager;

// 无返回执行SQL语句; 
public class SQLExecVoidResultDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		String strSQL = LogicParamManager.getSqlQuery();

		if(strSQL == null || strSQL.equals(""))
			return;
		
		// 直接使用系统连接
		this.getSession().createSQLQuery(strSQL).executeUpdate();
	}
	
	private void execOracleSQL(String strSQL) {
		String result = ""; // 查询结果字符串
		String sql = "select * from test"; // SQL 字符串
		// 连接字符串，格式： "jdbc:数据库驱动名称:连接模式:@数据库服务器ip:端口号:数据库SID"
		String url ="jdbc:oracle:thin:@localhost:1521:orcl";
		String username = "scott"; // 用户名
		String password = "tiger"; //密码
		try {
		// 创建oracle数据库驱动实例
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		// 获得与数据库的连接
		Connection conn = DriverManager.getConnection(url, username, password);
		// 创建执行语句对象
		Statement stmt = conn.createStatement();
		// 执行sql语句，返回结果集
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() )
		{
		result += "\n 第一个字段内容：" +rs.getString(1) + "<BR>";
		}
		rs.close(); // 关闭结果集
		stmt.close(); // 关闭执行语句对象
		conn.close(); // 关闭与数据库的连接
		}catch(Exception ex) {
		}
	}
	
	private void execMSSQL(String strSQL) {
		String result = ""; // 查询结果字符串
		String sql = "select * from test"; // SQL 字符串
		// 连接字符串，格式： "jdbc:数据库驱动名称:连接模式:@数据库服务器ip:端口号:数据库SID"
		String url ="jdbc:sqlserver://192.168.3.29:1433;DatabaseName=crm";
		String username = "sa"; // 用户名
		String password = "xxx"; //密码
		try {
		// 创建oracle数据库驱动实例
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		// 获得与数据库的连接
		Connection conn = DriverManager.getConnection(url, username, password);
		// 创建执行语句对象
		Statement stmt = conn.createStatement();
		// 执行sql语句，返回结果集
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next() )
		{
		result += "\n 第一个字段内容：" +rs.getString(1) + "<BR>";
		}
		rs.close(); // 关闭结果集
		stmt.close(); // 关闭执行语句对象
		conn.close(); // 关闭与数据库的连接
		}catch(Exception ex) {
		}
	}

}