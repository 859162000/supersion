package framework.dao.imps;



import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;

import framework.services.interfaces.LogicParamManager;

public class HqlQueryListDao extends BaseObjectResultDao {

	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(new Object[]{LogicParamManager.getSqlQuery()});
	}
	
	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		Query query =(Query)this.getSession().createQuery(objects[0].toString());
		if(objects.length>1 &&objects[1]!=null)
		{
			Object[] objParams;
			if(objects[1] instanceof Object[])
			{
				objParams=(Object[])objects[1];
			}
			else
				objParams=new Object[]{objects[1]};
	
			int i=0;
			for(Object param:objParams)
			{
				query.setParameter(i,param);
     			i++;
			}
		}
		return  query.list();
		
		
	}
	
	public Object objectResultExecute(String strSql) throws DataAccessException, ClassNotFoundException{
		SQLQuery sqlQuery = (SQLQuery) this.getSession().createSQLQuery(strSql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}
}
