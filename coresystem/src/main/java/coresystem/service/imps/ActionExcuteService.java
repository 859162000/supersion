package coresystem.service.imps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.ActionExcute;
import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowInstance;

public class ActionExcuteService implements IObjectResultExecute {

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		String initActionName = ActionContext.getContext().getName();
		String actionName = initActionName;
		String tName = null;
		if(actionName.indexOf("-") > -1){
			actionName = initActionName.substring(0,initActionName.indexOf("-"));
			tName = initActionName.substring(initActionName.indexOf("-") + 1);
		}
		if(actionName.indexOf("Level") > -1){
			actionName = actionName.substring(0,actionName.indexOf("Level"));
		}
		
		for(Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("actionExcute").entrySet()){
			if(actionName.equals(entry.getKey())){
				ActionExcute actionExcute = new ActionExcute();
				if(SecurityContext.getInstance().getLoginInfo() != null && !SecurityContext.getInstance().getLoginInfo().isAdministrator()){
					actionExcute.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
				}
				actionExcute.setActionName(initActionName);
				String showName = entry.getValue();
				if(tName != null){
					ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
					showName = showInstance.getShowEntityName() + " " + showName;
				}
				
				actionExcute.setActionShowName(showName);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				actionExcute.setActionTime(simpleDateFormat.format(new Date()));
				
				IParamVoidResultExecute defaultLogicDaoDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		    	RequestManager.setTOject(actionExcute);
		    	try{
		    		defaultLogicDaoDao.paramVoidResultExecute(null);
		    	}
		    	catch(Exception ex){
		    	}

		    	((Map<String,Object>)ServletActionContext.getContext().get("request")).put("actionExcuteObject", actionExcute);
		    	
		    	break;
			}
		}
		
		return null;
	}

}
