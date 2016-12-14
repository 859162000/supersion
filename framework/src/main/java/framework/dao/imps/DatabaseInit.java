package framework.dao.imps;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.jdbc.Work;

public class DatabaseInit implements Work{
	protected final Log logger = LogFactory.getLog(getClass());
	@Override
	public void execute(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		//connection.getMetaData().
		
	}
	
	private void initDB(Connection connection,String scriptDir)
	{
		
	}

}
