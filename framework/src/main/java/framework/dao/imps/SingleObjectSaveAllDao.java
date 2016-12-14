package framework.dao.imps;

import java.util.ArrayList;

import framework.interfaces.RequestManager;

public class SingleObjectSaveAllDao extends BaseVoidResultDao{

	@Override
	public void voidResultExecute() throws Exception {
		
		voidResultExecute(RequestManager.getTOject());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0]);
	}

	public void voidResultExecute(Object object){
		ArrayList<Object> obj = (ArrayList<Object>) object;
		for (int i = 0; i < obj.size(); i++) {
			this.getHibernateTemplate().save(obj.get(i));
		}
	}
}
