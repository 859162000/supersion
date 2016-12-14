package coresystem.service.imps;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.NeedDoThing;
import coresystem.dto.ShowUserNeedDoThing;
import coresystem.dto.UserInfo;
import coresystem.dto.UserNeedDoThing;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.security.SecurityContext;


public class HasUserDoThingService implements IObjectResultExecute{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1843834907477336604L;

	@Override
	public Object objectResultExecute() throws Exception {
		UserInfo userInfo = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserNeedDoThing.class);
		detachedCriteria.add(Restrictions.eq("userInfo",userInfo));
		List<UserNeedDoThing> userNeedDoThingList = (List<UserNeedDoThing>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		Integer count = 0;
		try{
			for(UserNeedDoThing userNeedDoThing:userNeedDoThingList )
			{
				IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
				ResultSet resultSet = (ResultSet)paramObjectResultExecute.paramObjectResultExecute(new Object[]{userNeedDoThing.getNeedDoThing().getCountSql(),null});
				while (resultSet.next()){
					count = Integer.parseInt(resultSet.getObject(1).toString());
					break;
				}
				resultSet.close();
			}
			
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
		}
		if(count > 0){
			return true;
		}
		return false;
		
		
	}

}
