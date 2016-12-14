package framework.dao.imps;

import java.util.Collection;

import org.hibernate.Session;

public class ClearSessionCacheEntitiesDao extends BaseVoidResultDao {
	@Override
	public void voidResultExecute() throws Exception {
		
	}
	
    public void voidResultExecute(Object[] objects) throws Exception {
    	if(objects.length>1)
    	{
    		Object obj=objects[0];
    		Session s=this.getSession(false);
    		if(obj!=null && s!=null)
    		{
    			if(Collection.class.isInstance(obj))
    			{
    				Collection c=(Collection)obj;
    				
    				for(Object o:c)
        				{
    						s.evict(o);
        				}
    				
    				
    			}
    			else if(obj instanceof Object[])
    			{
    				Object[] c=(Object[])obj;
    				
    				for(Object o:c)
        				{
    						s.evict(o);
        				}
    				
    			}
    			else
    			{
    				s.evict(obj);
    			}
    				
    		}
    	}
	}
}
