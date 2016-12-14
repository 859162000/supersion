package framework.dao.imps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class UpdateSqlListDao extends BaseVoidResultDao{
	
	@SuppressWarnings("deprecation")
	public void objectResultExecute(final String strSql,final List<List<String>> list) throws Exception{
		org.hibernate.Session s = this.getSession();
		
		s.doWork(new Work(){
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(strSql);
				for(int i =0;i<list.size();i++){
					
					List<String> tmpList = list.get(i);
					for (int j = 0; j < tmpList.size(); j++) {
						ps.setObject(j+1, tmpList.get(j));
					}
					ps.addBatch();
				}
				ps.executeBatch();
				ps.close();
				ps=null;
			}});		
	}
	
	@SuppressWarnings("deprecation")
	public void getResultSet(String strSql, List<List<String>> list, Session session) throws Exception{
		PreparedStatement preparedStatement  = session.connection().prepareStatement(strSql);
		for(int i =0;i<list.size();i++){
			List<String> tmpList = list.get(i);
			for (int j = 0; j < tmpList.size(); j++) {
				preparedStatement.setObject(j, tmpList.get(j));
			}
		}
		preparedStatement.executeBatch();
		preparedStatement.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		objectResultExecute(objects[0].toString(), (List<List<String>>)objects[1]);
	}

	@Override
	public void voidResultExecute() throws Exception {
		// TODO Auto-generated method stub
		
	}

}