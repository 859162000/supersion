package framework.dao.imps;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import framework.services.interfaces.LogicParamManager;

public class SingleObjectDeleteByCriteriaDao extends BaseVoidResultDao{
	@Override
	public void voidResultExecute() throws Exception {

		voidResultExecute(LogicParamManager.getDetachedCriteria());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute((DetachedCriteria )objects[0]);
	}
	
	public void voidResultExecute(DetachedCriteria criteria) throws DataAccessException, ClassNotFoundException{
		List idList = getHibernateTemplate().findByCriteria(criteria);
		for(int i=0;i<idList.size();i++){
			this.getHibernateTemplate().delete(idList.get(i));
		}
	}
}