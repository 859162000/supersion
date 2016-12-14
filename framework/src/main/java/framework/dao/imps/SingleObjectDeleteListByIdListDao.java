package framework.dao.imps;

import java.io.Serializable;

import framework.interfaces.RequestManager;

public class SingleObjectDeleteListByIdListDao extends BaseVoidResultDao{
	
	@Override
	public void voidResultExecute() throws Exception {
		voidResultExecute(RequestManager.getTName(),(Object[])RequestManager.getIdList());
	}
	
	@Override
	public void voidResultExecute(Object[] objects) throws Exception {
		voidResultExecute(objects[0].toString(),(Object[])objects[1]);
	}

	public void voidResultExecute(String tName,Object[] idList) throws Exception{
		Class<?> type = Class.forName(tName);
		for(int i=0;i<idList.length;i++){
			this.getHibernateTemplate().delete(this.getHibernateTemplate().get(type, (Serializable)idList[i]));
		}
	}
}