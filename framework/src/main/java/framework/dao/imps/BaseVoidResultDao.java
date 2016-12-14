package framework.dao.imps;

import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.show.ShowContext;

public abstract class BaseVoidResultDao extends BaseHibernateDao implements IParamVoidResultExecute{
    
	public abstract void voidResultExecute() throws Exception;
	
	public void voidResultExecute(Object[] objects) throws Exception {
		
	}
	
	public void paramVoidResultExecute(Object param) throws Exception {
		Map<String, String> map = ShowContext.getInstance().getShowEntityMap().get("DTO2DBMap");
		String tName =null;
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
				voidResultExecute();
        	}else if(null!=key){
				super.initSessionFactory(key);
				voidResultExecute();
        	}
        	else if(null==SecurityContext.getInstance().getLoginInfo()){
				voidResultExecute();
			}
        	else if(null==SecurityContext.getInstance().getLoginInfo().getSessionFactory()){
				voidResultExecute();
			}else{
				super.initSessionFactory(SecurityContext.getInstance().getLoginInfo().getSessionFactory());
				voidResultExecute();
			}			
		}
		else{
			Object[] objects = (Object[])param;
			if(null!=objects[objects.length -1]){
				super.initSessionFactory(objects[objects.length -1]);
			}
			else if(isThreadFlag){
				super.initSessionFactory(objects[objects.length -1]);
			}
			else if(null!=SecurityContext.getInstance().getLoginInfo() && null!=SecurityContext.getInstance().getLoginInfo().getSessionFactory()){
				super.initSessionFactory(SecurityContext.getInstance().getLoginInfo().getSessionFactory());
			}else{
				super.initSessionFactory(objects[objects.length -1]);
			}
			
			voidResultExecute(objects);
		}
	}
}
