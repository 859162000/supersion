package coresystem.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;


import coresystem.dto.UserRole;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;

import framework.services.interfaces.MessageResult;

public class UserOperationUpdateService implements IObjectResultExecute{
	
	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {

		try
		{
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

		Map<String,Object> returnMap=new HashMap<String,Object>();
		
		String id="";
		List<Field> fieldList = ReflectOperation.getColumnFieldList(Class.forName(tName));
		for(Field field : fieldList){
			if(ReflectOperation.isPrimaryKeyField(field)){
				id=((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST)).getParameter("serviceParam."+field.getName());
				if(id!=null)
				{
					returnMap.put(field.getName(),id);
					break;
				}
			}
	   }
		
		if(initActionName!=null && initActionName.equals("Update-coresystem.dto.UserInfo")){
			if(id !=null && !StringUtils.isBlank(id)){
				IParamObjectResultExecute singleObjectFindByCriteriaDaoRole = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteriaRole = DetachedCriteria.forClass(UserRole.class);
				detachedCriteriaRole.add(Restrictions.eq("userInfo.strUserCode",id));
				List<UserRole> UserRoleList = (List<UserRole>)singleObjectFindByCriteriaDaoRole.paramObjectResultExecute(new Object[]{detachedCriteriaRole,null});
				((Map<String,Object>)ServletActionContext.getContext().get("request")).put("PreUserRoleList", UserRoleList);
			}
	    }
		
		if(id !=null && !StringUtils.isBlank(id))
		{
			//查询数据
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			Object objList = dao.paramObjectResultExecute(new Object[]{tName,id, null});
			for(Field field : fieldList){
				if(!ReflectOperation.isPrimaryKeyField(field) && !field.getType().equals(byte[].class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(objList,field.getName());
					returnMap.put(field.getName(),tOjectFieldValue );
				}
			}

			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("UserOperationUpdateList", returnMap);
		}
		else
		{
			
			List<Field> fieldJoinList=ReflectOperation.getJoinColumnFieldList(Class.forName(tName));
			List<Field> fieldisIdListFieldList=ReflectOperation.getIdListTargetFieldList(Class.forName(tName));
			for(Field field : fieldJoinList){
				boolean falg=true;
				JoinColumn iColumn = field.getAnnotation(JoinColumn.class);
				for(Field fieldID : fieldisIdListFieldList){
					if(iColumn.name().equals(fieldID.getName()))
					{
						falg=false;
						break;
					}
				}
				if(falg)
				{
					
					List<Field> fieldPList = ReflectOperation.getColumnFieldList(field.getType());
					
					String Pa="";
					for(Field fieldp : fieldPList){
						if(ReflectOperation.isPrimaryKeyField(fieldp)){
							Pa=fieldp.getName();
							break;
						}
					}
					id=((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST)).getParameter("serviceParam."+field.getName()+"."+Pa);
					
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
					detachedCriteria.createAlias(field.getName(), "f");
					detachedCriteria.add(Restrictions.eq("f."+Pa,id));
					List<Object> objectList=(List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					int t=0;
					for(Object o : objectList)
					{
						
						returnMap.put(String.valueOf(t),o);
						t++;
					}
					
					//break;
				}
				((Map<String,Object>)ServletActionContext.getContext().get("request")).put("UserOperationUpdateList", returnMap);
				
		   }
			
		}
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			return new MessageResult("系统错误,请联系管理员");
		}
		return null;
	}


}
