package framework.dao.imps;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.hibernate.Session;

import framework.interfaces.IGetResultSetExecute;
import framework.services.interfaces.LogicParamManager;

public class CreateSqlQueryResultSetDao extends BaseObjectResultDao implements IGetResultSetExecute{
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getSqlQuery());
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		//支持sql 语句带参数，格式为，sql语句，参数，sessionfactory
		if(objects.length==3 &&objects[1]!=null)
		{
			return getResult(objects[0].toString(),objects[1]);
		}
		return objectResultExecute(objects[0].toString());
		
	}
	private Object getResult(String strSql,Object params) throws Exception{
		PreparedStatement preparedStatement = this.getSession().connection().prepareStatement(strSql);
		Object[] objParams=null;
		if(params instanceof Object[])
		{
			objParams=(Object[]) params;
		}
		else
		{
			objParams=new Object[]{params};
		}
		
		int i=1;
		for(Object param:objParams)
		{
			
			if(param instanceof Date)
			{
				preparedStatement.setObject(i,new java.sql.Date(((Date)param).getTime()));
			}
			else
			{
				preparedStatement.setObject(i,param);
			}
			i++;
		}
		
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	
	@SuppressWarnings("deprecation")
	public Object objectResultExecute(String strSql) throws Exception{
		PreparedStatement preparedStatement  = this.getSession().connection().prepareStatement(strSql);
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	
	@SuppressWarnings("deprecation")
	public ResultSet getResultSet(String strSql,Session session) throws Exception{
		PreparedStatement preparedStatement  = session.connection().prepareStatement(strSql);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
}