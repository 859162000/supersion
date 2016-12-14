package framework.dao.imps;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

public class QueryDataBaseEditionDao extends BaseObjectResultDao{

	private String driverName;
	
	protected void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	@Override
	public String objectResultExecute() throws Exception {
		return dataBaseEdition();
	}
	
	@Override
	public String objectResultExecute(Object[] objects) throws Exception {
		return dataBaseEdition();
	}
	
	public String dataBaseEdition() throws Exception {		
		org.hibernate.Session s = this.getSession();		
		s.doWork(new Work(){
			@Override
			public void execute(Connection connection) throws SQLException {
				String name=connection.getMetaData().getDriverName();
				setDriverName(name);
			}});
		
		if(driverName.equals("Oracle JDBC driver")) {
			return "oracle";
		}
		else if(driverName.startsWith("Microsoft SQL Server")){
			return "sqlserver";
		}
		else if(driverName.startsWith("MySQL-AB JDBC Driver")){
			return "mySql";
		}
		else{
			return "db2";
		}
		
	}

}
