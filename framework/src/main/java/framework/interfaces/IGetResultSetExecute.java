package framework.interfaces;

import java.sql.ResultSet;

import org.hibernate.Session;

public interface IGetResultSetExecute {
	public ResultSet getResultSet(String param,Session session) throws Exception;
}
