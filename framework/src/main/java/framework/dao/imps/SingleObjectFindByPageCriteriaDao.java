package framework.dao.imps;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;

import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;

public class SingleObjectFindByPageCriteriaDao extends BaseObjectResultDao{

	@Override
	public Object objectResultExecute() throws Exception {
		
		int firstResult = ShowParamManager.getFirstResult();
		int maxResults = ShowParamManager.getPageSize();
		Object result = this.getHibernateTemplate().findByCriteria(LogicParamManager.getDetachedCriteria(), firstResult, maxResults);

		if(((List<?>)result).size() > 0){
			Criteria criteria=LogicParamManager.getDetachedCriteria().getExecutableCriteria(this.getSession());
			criteria.setFirstResult(0);
			java.lang.reflect.Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);  
			List<?> orderEntrys = (List<?>) field.get(criteria);
			field.set(criteria,new ArrayList<Object>());  
			int totalCount = 0;
			Criteria i = criteria.setProjection(Projections.rowCount());
			
			try {
				totalCount = (Integer)i.uniqueResult();
			}catch(Exception ex){
			} 
			field.set(criteria,orderEntrys);  
			criteria.setProjection(null);
			LogicParamManager.setTotalCount(totalCount);
		}
		else{
			LogicParamManager.setTotalCount(firstResult);
		}

		return result;
	}
}

