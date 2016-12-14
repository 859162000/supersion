package framework.dao.imps;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.show.ShowContext;

public abstract class BaseObjectResultDao extends BaseHibernateDao implements IParamObjectResultExecute{
	
	public abstract Object objectResultExecute() throws Exception;
	
	public Object objectResultExecute(Object[] objects) throws Exception{ 
		return null;
	}
	
	public Object paramObjectResultExecute(Object param) throws Exception {
		Map<String, String> map = ShowContext.getInstance().getShowEntityMap().get("DTO2DBMap");
		String tName = null;
		boolean isThreadFlag =false;
		try
		{
			tName = RequestManager.getTName();
			if(null!=param){
				Object[] objs = (Object[])param;
				if(!(objs[0] instanceof DetachedCriteria)){
					tName=objs[0].toString();
				}
			}
		}
		catch(Exception e){
			isThreadFlag =true;
		}
		
		String key=null;
		if(null != tName && map.containsKey(tName)){
			key=map.get(tName);
		}

        if(param == null){
        	if(isThreadFlag){
        		return objectResultExecute();
        	}
        	else if(null!=key){
        		super.initSessionFactory(key);
				return objectResultExecute();
        	}
        	else if(null==SecurityContext.getInstance().getLoginInfo()){
        		return objectResultExecute();
        	}        	
        	else if(null==SecurityContext.getInstance().getLoginInfo().getSessionFactory()){
				return objectResultExecute();
			}else{
				super.initSessionFactory(SecurityContext.getInstance().getLoginInfo().getSessionFactory());
				return objectResultExecute();
			}
		}
		else{
			Object[] objects = (Object[])param;
			if(null!=objects[objects.length -1]){
				super.initSessionFactory(objects[objects.length -1]);
				return objectResultExecute(objects);
			}
			if(null!=key){
        		super.initSessionFactory(key);
				return objectResultExecute(objects);
			}
			
			if(isThreadFlag){
				super.initSessionFactory(objects[objects.length -1]);
			}
			else if(null!=SecurityContext.getInstance().getLoginInfo() && null!=SecurityContext.getInstance().getLoginInfo().getSessionFactory()){
				super.initSessionFactory(SecurityContext.getInstance().getLoginInfo().getSessionFactory());
			}else{
				super.initSessionFactory(objects[objects.length -1]);
			}
			
			return objectResultExecute(objects);
		}
	}
}
