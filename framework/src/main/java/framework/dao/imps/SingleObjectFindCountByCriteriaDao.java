package framework.dao.imps;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;

import framework.services.interfaces.LogicParamManager;

public class SingleObjectFindCountByCriteriaDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute(Object[] objects) throws Exception {
		return objectResultExecute((DetachedCriteria)objects[0]);
	}
	
	public Object objectResultExecute(DetachedCriteria detachedCriteria) throws Exception, IllegalAccessException{
		Criteria criteria=detachedCriteria.getExecutableCriteria(this.getSession());
		java.lang.reflect.Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
		field.setAccessible(true);  
		List<?> orderEntrys = (List<?>) field.get(criteria);
		field.set(criteria,new ArrayList<Object>());  
		int totalCount = -1;
		Criteria i = criteria.setProjection(Projections.rowCount());
		try {
			totalCount = (Integer)i.uniqueResult();
		}
		catch(Exception ex){
		} 
		field.set(criteria,orderEntrys);  
		criteria.setProjection(null);
		return totalCount;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		return objectResultExecute(LogicParamManager.getDetachedCriteria());
	}
}

