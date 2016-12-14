package coresystem.service.imps;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;

import coresystem.dto.NeedDoThing;
import coresystem.dto.ShowUserNeedDoThing;
import coresystem.dto.UserInfo;
import coresystem.dto.UserNeedDoThing;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.security.SecurityContext;
import framework.services.imps.SingleObjectShowListService;

public class ShowUserNeedDoThingService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		UserInfo userInfo = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NeedDoThing.class);
		List<NeedDoThing> needDoThingList = (List<NeedDoThing>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		List<ShowUserNeedDoThing> showUserNeedDoThingList = new ArrayList<ShowUserNeedDoThing>();
		for(NeedDoThing needDoThing : needDoThingList){
			boolean isExsist = false;
			for(UserNeedDoThing userNeedDoThing : needDoThing.getUserNeedDoThings()){
				if(userNeedDoThing.getUserInfo().getStrUserCode().equals(userInfo.getStrUserCode())){
					isExsist = true;
					break;
				}
			}
			if(isExsist){
				Integer count = 0;
				try{
					IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
					ResultSet resultSet = (ResultSet)paramObjectResultExecute.paramObjectResultExecute(new Object[]{needDoThing.getCountSql(),null});
					while (resultSet.next()){
						count = Integer.parseInt(resultSet.getObject(1).toString());
						break;
					}
					resultSet.close();
				}
				catch(Exception ex){
					ExceptionLog.CreateLog(ex);
				}
				if(count > 0){
					ShowUserNeedDoThing showUserNeedDoThing = new ShowUserNeedDoThing();
					showUserNeedDoThing.setThingName(needDoThing.getThingName());
					showUserNeedDoThing.setCount(count);
					showUserNeedDoThing.setLinkedUrl(ApplicationManager.getOpenLinkedUrl() + "连接" + "_" + needDoThing.getStrActionName());
					showUserNeedDoThingList.add(showUserNeedDoThing);
				}
			}
		}
		
		this.setServiceResult(showUserNeedDoThingList);
	}
}
