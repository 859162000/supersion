package framework.dao.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import framework.dao.imps.DBUtils;

public class DBUtilTest {
	@Test
	@Ignore
	public void initDBTest() throws Exception
	{
		 	Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.200:1521:zxpt", "zxdbinittest", "zxdbinittest");
			try
			{
				DBUtils.initDB(con, "dbscript/zxpt/");
				
			}
			finally
			{
				con.close();
			}
			
			
		

	}
	@Test
	@Ignore
	public void initMysqlDBTest() throws Exception
	{
			
		 	Class.forName("com.mysql.jdbc.Driver");//jdbc:mysql://localhost:3306/javademo
			//Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.200:3306/zxdbinittest?useUnicode=true&characterEncoding=UTF-8", "root2", "Transino123");
		 	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zxdbinittest?useUnicode=true&characterEncoding=UTF-8", "root", "root");
			//int level=con.getTransactionIsolation();
			//con.setAutoCommit(false);
			//Statement s=con.createStatement();
			
			/*s.execute("begin;");
			s.execute("insert into test values(2)");
			s.execute("insert into test values(2)");
			s.execute("insert into test values(2)");
			s.execute("insert into test values(2)");
			con.commit();*/
			try
			{
				DBUtils.initDB(con, "dbscript/zxpt/");
				
			}
			finally
			{
				con.close();
			}
			
			
			
		

	}

	@Test
	@Ignore
	public void initDB2DBTest() throws Exception
	{
			
		 	Class.forName("com.ibm.db2.jcc.DB2Driver");//jdbc:mysql://localhost:3306/javademo
			Connection con = DriverManager.getConnection("jdbc:db2://192.168.0.200:50000/zxtest", "db2admin", "db2admin");
			try
			{
				DBUtils.initDB(con, "dbscript/zxpt/");
				
			}
			finally
			{
				con.close();
			}
			
			
			
		

	}
	
	@Test
	
	public void initSqlServerDBTest() throws Exception
	{
			
		 	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//jdbc:mysql://localhost:3306/javademo
			Connection con = DriverManager.getConnection("jdbc:sqlserver://192.168.0.200:1433;databaseName=zxdbinittest2", "sa", "transino");
			try
			{
				DBUtils.initDB(con, "dbscript/zxpt/,dbscript/dbgs/");
				
			}
			finally
			{
				con.close();
			}
			
			
			
		

	}
	
	


}
