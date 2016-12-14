package framework.dao.imps;

import java.util.Collection;

import framework.services.interfaces.LogicParamManager;

public class SingleObjectSaveOrUpdateAllDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(LogicParamManager.getSaveObjectList());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0]);
	}

	public void voidResultExecute(Object object){
		this.getHibernateTemplate().saveOrUpdateAll((Collection<?>)object);
	}

}
