package coresystem.service.imps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.JoinColumn;
import javax.servlet.http.HttpServletRequest;

import jxl.write.DateTime;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.RoleInfo;
import coresystem.dto.UserInfo;
import coresystem.dto.UserOperationLog;
import coresystem.dto.UserRole;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowInstance;

public class UserOperationNoteService implements IObjectResultExecute  {

	@SuppressWarnings({ "unchecked" })
	public Object objectResultExecute() throws Exception {
		
		try
		{
			//1.判断当前的Action对应的 按钮的名称
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
			String tempValue=actionName;
			if(actionName.indexOf("_") >-1){
				actionName=actionName.split("_")[1];
		}
		String ActionDesc=null;
		String ip=ServletActionContext.getRequest().getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    		ip = ServletActionContext.getRequest().getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = ServletActionContext.getRequest().getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = ServletActionContext.getRequest().getRemoteAddr();
	    }
		//2.判断类型并作相应的处理（日志类型进行匹配）
	    
	    
		for(Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("actionExcute").entrySet()){
			if(entry.getKey().equals(actionName)|| entry.getKey().equals(tempValue)){
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				UserOperationLog userOperationLog = new UserOperationLog();
				if(SecurityContext.getInstance().getLoginInfo() != null && !SecurityContext.getInstance().getLoginInfo().isAdministrator() && SecurityContext.getInstance().getLoginInfo().getTag()!=null){
					userOperationLog.setStrUserCode(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode());
					userOperationLog.setStrUserName(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
					userOperationLog.setStrInstName((((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getInstInfo()).getStrInstName());
					//IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
					///List<InstInfo> objList = (List<InstInfo>)dao.paramObjectResultExecute(new Object[]{InstInfo.class.getName(),(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getInstInfo()).getStrInstCode(), null});
					//for(Instinfo obje : objList)
					//{
						// userOperationLog.setStrInstName(obje.strInstName);
					    // break;
					//}
				}
				else if(SecurityContext.getInstance().getLoginInfo() != null && SecurityContext.getInstance().getLoginInfo().isAdministrator()){
					userOperationLog.setStrUserCode(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode());
					userOperationLog.setStrUserName(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
					userOperationLog.setStrInstName(null);
				}
				else if(SecurityContext.getInstance().getLoginInfo() == null){
					UserInfo user=new UserInfo();
					if(actionName.toUpperCase().equals("LOGOUT")){
						return null;
					}
					try{	user=(UserInfo)RequestManager.getTOject();
					}
					catch(Exception ex){
						user=null;
					}
					if(user==null){
						user=new UserInfo();
						user.setStrUserCode(((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST)).getParameter("serviceParam.strUserCode"));
						user.setStrUserName(((HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST)).getParameter("serviceParam.strUserName"));
					}
					if(user.getStrUserCode().equals(SecurityContext.getInstance().getSysUserCode())){
						userOperationLog.setStrUserCode(user.getStrUserCode());
						userOperationLog.setStrUserName(user.getStrUserCode());
						userOperationLog.setStrInstName(null);
					}
					else{
						IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
						Object objList = dao.paramObjectResultExecute(new Object[]{UserInfo.class.getName(),user.getStrUserCode(), null});
						if(objList==null){
							userOperationLog.setStrUserCode(user.getStrUserCode());
							userOperationLog.setStrUserName(user.getStrUserCode());
							userOperationLog.setStrInstName(null);
						}
						else{
							userOperationLog.setStrUserCode(((UserInfo)objList).getStrUserCode());
							userOperationLog.setStrUserName(((UserInfo)objList).getStrUserName());
							userOperationLog.setStrInstName(((UserInfo)objList).getInstInfo().getStrInstName());
						}
						
					}
					RequestManager.setTOject(user);
				}
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				userOperationLog.setDtOperationTime(simpleDateFormat.format(new Date()));
				userOperationLog.setStrIpAddress(ip);
				
				
				//3.处理当前类的形式
				boolean isExistClass=false;
				if(actionName.toUpperCase().startsWith("LOG")){
					isExistClass=true;
					userOperationLog.setStrOperationType("1");
				}
				if(!isExistClass){
					for(Map.Entry<String, String> map :  ShowContext.getInstance().getShowEntityMap().get("UserRoleType").entrySet()){
						if(map.getValue().trim().toUpperCase().equals(tName.toUpperCase().trim())){
							isExistClass=true;
							userOperationLog.setStrOperationType("2");
							break;
						}
					}
				}
				if(!isExistClass && !actionName.toUpperCase().endsWith("UPDATELIST")){
					try{
						Object tObject = RequestManager.getTOject();
						Object id=ReflectOperation.getPrimaryKeyValue(tObject);
						IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
						Object objList = dao.paramObjectResultExecute(new Object[]{UserInfo.class.getName(),id, null});
						if(objList !=null){
						
						//表示锁定状态
								if((((UserInfo)objList).getStrLockState().equals("1") && (((UserInfo)RequestManager.getTOject()).getStrLockState().equals("2")
										|| ((UserInfo)RequestManager.getTOject()).getStrLockState().equals("3")))
										||	( ((UserInfo)RequestManager.getTOject()).getStrLockState().equals("1") && (((UserInfo)objList).getStrLockState().equals("2")
												|| ((UserInfo)objList).getStrLockState().equals("3")))){
									isExistClass=true;
									userOperationLog.setStrOperationType("4");
							}
						}
					}
					catch(Exception ex){}
					
					//修改
					if(!isExistClass){
						//主要处理UserInfode的其他类

						for(Map.Entry<String, String> map :  ShowContext.getInstance().getShowEntityMap().get("UserInfoType").entrySet()){
							if(map.getValue().trim().toUpperCase().equals(tName.toUpperCase().trim())){
								isExistClass=true;
								userOperationLog.setStrOperationType("3");
								break;
							}
						}
					}
				}
				
				if(!isExistClass){
					userOperationLog.setStrOperationType("5");
				}
					
				//4.在详细日志信息处,拼接日志的信息
				//
				ActionDesc=entry.getValue();
				
				if(userOperationLog.getStrOperationType().equals("1")){
					if(actionName.toUpperCase().startsWith("LOGIN")){
						ActionDesc  ="用户登录系统";
					}
					else{
						ActionDesc  ="用户退出系统";
					}
				}
				else if(userOperationLog.getStrOperationType().equals("4")){
					UserInfo tObject = (UserInfo)RequestManager.getTOject();
					if(tObject.getStrLockState().equals("1")){
						ActionDesc ="解锁"+tObject.getStrUserCode()+"用户";
					}
					if(tObject.getStrLockState().equals("2")){
						ActionDesc ="密码错误锁定"+tObject.getStrUserCode()+"用户";
					}
					if(tObject.getStrLockState().equals("3")){
						ActionDesc ="超期锁定"+tObject.getStrUserCode()+"用户";
					}
				}
				else if(userOperationLog.getStrOperationType().equals("5") 
						&& !actionName.toUpperCase().startsWith("UPDATE") && !actionName.toUpperCase().endsWith("UPDATE")
						&& !actionName.toUpperCase().startsWith("SAVE")  && !actionName.toUpperCase().endsWith("SAVE")
						&& !actionName.toUpperCase().startsWith("DELETE")  && !actionName.toUpperCase().endsWith("DELETE")){
					if(!actionName.toUpperCase().startsWith("EXCUTEWORKFLOW")){
						ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
						ActionDesc +="表为"+showInstance.getShowEntityName()+"的数据";
					}
				}
				else if(userOperationLog.getStrOperationType().equals("3") && actionName.toUpperCase().startsWith("UPDATEPASSWORD")){
					ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
					ActionDesc ="修改表为"+showInstance.getShowEntityName()+"的用户为{ID}的密码";
					String id=((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
					ActionDesc=ActionDesc.replace("{ID}", id.toString());
				}
				else if(tName != null && actionName.toUpperCase().startsWith("UPDATEFIELD")){
					ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
					String[] idList=(String[])RequestManager.getIdList();
					if(idList.length>0){
						ActionDesc ="重置表为"+showInstance.getShowEntityName()+"的ID为{id}数据";
						String idLists="";
						for(String id:idList){
							idLists +=id+" ";
						}
						idLists=idLists.trim().replaceAll(" ", ",");
						ActionDesc=ActionDesc.replace("{id}",idLists );
					}
				}
				else if(tName != null && !actionName.toUpperCase().startsWith("LOG")){
					ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
					ActionDesc +="表为"+showInstance.getShowEntityName()+"的数据:";
					
					Map<String,Object> map_New=new HashMap<String,Object>();
					if(entry.getKey().toUpperCase().startsWith("SAVE") || entry.getKey().toUpperCase().endsWith("SAVE")){
						ActionDesc=ActionDesc.replaceAll("保存", "新增");
						map_New=returnCreateOrUpdateOrDelete("SAVE");
						for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
							ActionDesc+=entry_Add.getKey()+"|"+entry_Add.getValue()+" ";
						}
					}if(RequestManager.getActionName().equals("Save-coresystem.dto.UserInfo")){
						UserInfo userInfo = (UserInfo)RequestManager.getTOject();
						String[] roleInfoIdList=userInfo.getRoleInfoIdList();
						if(roleInfoIdList.length>0){
							IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RoleInfo.class);
							detachedCriteria.add(Restrictions.in("strRoleCode",roleInfoIdList));
							List<RoleInfo> roleInfoList = (List<RoleInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(roleInfoList.size()>0){
								String roleList="";
								for (RoleInfo roleInfo : roleInfoList) {
									roleList+=roleInfo.getStrRoleName()+" ";
								}
								ActionDesc+="角色名称"+"|"+roleList;
							}
							
						}else{
							ActionDesc+="角色名称"+"|"+" ";
						}
					}
					else if(entry.getKey().toUpperCase().startsWith("UPDATE") || entry.getKey().toUpperCase().endsWith("UPDATE")){
						
						//处理update的方法
						if(!entry.getKey().toUpperCase().endsWith("UPDATELIST")){
							map_New=returnCreateOrUpdateOrDelete("UPDATE");
						
							if(map_New.size()>1){
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().equals("ID")){
										ActionDesc=ActionDesc.replaceAll("数据:", "ID为"+entry_Add.getValue()+"数据:");
									}
									else if(!entry_Add.getKey().toUpperCase().endsWith("_OLD") && !entry_Add.getKey().equals("ID")){
										ActionDesc+=entry_Add.getKey()+"|由({"+entry_Add.getKey()+"})"+"改为("+entry_Add.getValue()+") ";
									}
								}
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().toUpperCase().endsWith("_OLD")){
										if(ActionDesc.indexOf("{"+entry_Add.getKey().split("_")[0]+"}")>-1){
											Object showValue=entry_Add.getValue();
											if(showValue==null){showValue=new Object();showValue="";}
											ActionDesc=ActionDesc.replace("{"+entry_Add.getKey().split("_")[0]+"}",showValue.toString());
										}
									}
								}
							}
							else if(RequestManager.getActionName().equals("Update-coresystem.dto.UserInfo")){
								UserInfo userInfo = (UserInfo)RequestManager.getTOject();
								String[] roleInfoIdList=userInfo.getRoleInfoIdList();

								
								IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RoleInfo.class);
								detachedCriteria.add(Restrictions.in("strRoleCode",roleInfoIdList));
								List<RoleInfo> roleInfoList = (List<RoleInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								List<UserRole> UserRoleList=(List<UserRole>)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("PreUserRoleList");
								
								List<RoleInfo> insertRoleInfo=new ArrayList<RoleInfo>();
								List<RoleInfo> deleteRoleInfo=new ArrayList<RoleInfo>();
							
								for (RoleInfo roleInfo : roleInfoList) {
									boolean isExsist=false;
									for (UserRole userRole : UserRoleList) {
										if(roleInfo.getStrRoleCode().equals(userRole.getRoleInfo().getStrRoleCode())){
											isExsist=true;
											break;
										}
									}
									if(!isExsist){
										insertRoleInfo.add(roleInfo);
									}
								}
								for (UserRole userRole : UserRoleList) {
									boolean isExsist=false;
									for (RoleInfo roleInfo : roleInfoList) {
										if(roleInfo.getStrRoleCode().equals(userRole.getRoleInfo().getStrRoleCode())){
											isExsist=true;
											break;
										}
									}
									if(!isExsist){
										deleteRoleInfo.add(userRole.getRoleInfo());
									}
								}
								
								if(insertRoleInfo.size()>0 || deleteRoleInfo.size()>0){
									String OldValue="";
									String newValue="";
									for (RoleInfo roleInfo : roleInfoList) {
										newValue+=roleInfo.getStrRoleName()+" ";
									}
									for (UserRole userRole : UserRoleList) {
										OldValue+=userRole.getRoleInfo().getStrRoleName() + " ";
									}
									if(roleInfoList.size() > 0){
										newValue = newValue.substring(0,newValue.length() -1);
									}
									if(UserRoleList.size() > 0){
										OldValue = OldValue.substring(0,OldValue.length() -1);
									}
									for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
										if(entry_Add.getKey().equals("ID"))
										{
											ActionDesc=ActionDesc.replaceAll("数据:", "ID为"+entry_Add.getValue()+"数据:");
										}
									}
									ActionDesc+="角色名称"+"|由({"+OldValue+"})"+"改为("+newValue+") ";
								}else{
									for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
										if(entry_Add.getKey().equals("ID"))
										{
											ActionDesc=ActionDesc.replaceAll("数据:", "ID为"+entry_Add.getValue()+"数据:");
										}
									}
									ActionDesc +="无任何内容修改";
								}
								
							}
							else{
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().equals("ID"))
									{
										ActionDesc=ActionDesc.replaceAll("数据:", "ID为"+entry_Add.getValue()+"数据:");
									}
								}
								ActionDesc +="无任何内容修改";
							}
						}
						//处理UpdateList的更新
						else{
							Map<String,Object> oldMap= (Map<String, Object>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("UserOperationUpdateList");
							map_New=returnCreateOrUpdateOrDelete("UPDATELIST");
							//表示为新增
							if(oldMap==null){
								ActionDesc=ActionDesc.replaceAll("修改", "新增");
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().startsWith("ID_")){
										ActionDesc=ActionDesc.replaceAll("数据:", entry_Add.getKey().split("_")[1]+"为"+entry_Add.getValue()+"功能:");
									}
									else
									{
										ActionDesc +=entry_Add.getKey()+"|(";
										List<Object> lisyt=(List<Object>)entry_Add.getValue();
										for(Object l: lisyt)
										{
											ActionDesc +=l+" ";
										}
										ActionDesc=ActionDesc.trim().replaceAll(" ", ",");	
										ActionDesc+=")"; 
									}
								}
							}
							else if(oldMap.size()==0){
								ActionDesc=ActionDesc.replaceAll("修改", "新增").replaceAll("更新", "新增");;
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().startsWith("ID_")){
										ActionDesc=ActionDesc.replaceAll("数据:", entry_Add.getKey().split("_")[1]+"为"+entry_Add.getValue()+"功能:");
									}
									else
									{
										ActionDesc +=entry_Add.getKey()+"|(";
										List<Object> lisyt=(List<Object>)entry_Add.getValue();
										for(Object l: lisyt)
										{
											ActionDesc +=l+" ";
										}
										ActionDesc=ActionDesc.trim().replaceAll(" ", ",");	
										ActionDesc+=")";
									}
								}
							}/*
							else if(map_New.size()==1){
								ActionDesc=ActionDesc.replaceAll("修改", "删除").replaceAll("更新", "删除");;
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().startsWith("ID_")){
										ActionDesc=ActionDesc.replaceAll("数据:", entry_Add.getKey().split("_")[1]+"为"+entry_Add.getValue()+"功能:");
									}
									else{
										ActionDesc +=entry_Add.getKey()+"|(";
										List<Object> lisyt=(List<Object>)entry_Add.getValue();
										for(Object l: lisyt)
										{
											ActionDesc +=l+" ";
										}
										ActionDesc=ActionDesc.trim().replaceAll(" ", ",");	
										ActionDesc+=")";
									}
								}
							}*/
							else{
								boolean isContainOld=false,isContainOther=true;
								for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
									if(entry_Add.getKey().startsWith("ID_")){
										ActionDesc=ActionDesc.replaceAll("数据:", entry_Add.getKey().split("_")[1]+"为"+entry_Add.getValue()+"功能:");
									}
									if(entry_Add.getKey().toUpperCase().endsWith("_OLD")){
										isContainOld=true;
									}
									if(!entry_Add.getKey().startsWith("ID_")){
										isContainOther=false;
									}
								}
								if(!isContainOld && !isContainOther){
									ActionDesc=ActionDesc.replaceAll("修改", "删除").replaceAll("更新", "删除");
									for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
										if(!entry_Add.getKey().startsWith("ID_")){
											ActionDesc +=entry_Add.getKey()+"|(";
											List<Object> lisyt=(List<Object>)entry_Add.getValue();
											for(Object l: lisyt){
												ActionDesc +=l+" ";
											}
											ActionDesc=ActionDesc.trim().replaceAll(" ", ",");	
											ActionDesc+=")";
										}
									}
								}
								else{
									if(isContainOther){
										ActionDesc+="无任何修改。";
									}
									for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
										if(!entry_Add.getKey().toUpperCase().endsWith("_OLD") && !entry_Add.getKey().startsWith("ID_") &&map_New.containsKey(entry_Add.getKey()+"_OLD")){
											ActionDesc +=entry_Add.getKey()+"|由{"+entry_Add.getKey()+"}改为(";
											List<Object> lisyt=(List<Object>)entry_Add.getValue();
											for(Object l: lisyt){
												ActionDesc +=l+" ";
											}
											ActionDesc=ActionDesc.trim().replaceAll(" ", ",");	
											ActionDesc+=")";
										}
									}
									ActionDesc=ActionDesc.trim().replaceAll(" ", ",");	
									for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
										if(entry_Add.getKey().toUpperCase().endsWith("_OLD")){
											String temp="(";
											List<Object> lisyt=(List<Object>)entry_Add.getValue();
											for(Object l: lisyt){
												temp +=l+" ";
											}
											temp=temp.trim().replaceAll(" ", ",");	
											temp+=")";
											if(ActionDesc.indexOf("{"+entry_Add.getKey().split("_")[0]+"}")>-1){
												ActionDesc=ActionDesc.replace("{"+entry_Add.getKey().split("_")[0]+"}", temp);
											}
										}
									}
								}
							}
						}//处理UpdateList的更新
					}
					else if(entry.getKey().toUpperCase().startsWith("DELETE") || entry.getKey().toUpperCase().endsWith("DELETE")){
						if(entry.getKey().toUpperCase().startsWith("DELETELISTBYIDLIST") || entry.getKey().toUpperCase().startsWith("DELETEAPPLY")){
							try{
								ActionDesc=ActionDesc.replaceAll("数据", "ID为");
								String[] idList=(String[])RequestManager.getIdList();
								for(String s : idList){
									ActionDesc=ActionDesc+s+";";
								}
							}
							catch(Exception ex){}
						}
						else{
							map_New=returnCreateOrUpdateOrDelete("DELETE");
							for(Map.Entry<String, Object> entry_Add: map_New.entrySet()){
								if(entry_Add.getKey().equals("ID")){
									ActionDesc=ActionDesc.replaceAll("数据", "ID为"+entry_Add.getValue());
								}
								else{
									ActionDesc+=entry_Add.getKey()+"|"+entry_Add.getValue()+" ";
								}
							}
						}
					}
					
				}
				ActionDesc=ActionDesc.trim().replaceAll(" ", ",");
				userOperationLog.setStrDetailActionDesc(ActionDesc);
				try{
					if(!((framework.services.interfaces.MessageResult)LogicParamManager.getServiceResult()).isSuccess()){
						userOperationLog.setStrReason(((framework.services.interfaces.MessageResult)LogicParamManager.getServiceResult()).getMessage());
					}
				}
				catch(Exception ex){}
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{userOperationLog,null});
				
				}
			}
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			return new MessageResult("系统错误,请联系管理员");
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> returnCreateOrUpdateOrDelete(String Type) throws Exception	{

		Map<String,Object> returnNewContext=null;
		Object tObject = RequestManager.getTOject();
		Object id=ReflectOperation.getPrimaryKeyValue(tObject);
	
		List<Field> fieldList = ReflectOperation.getColumnFieldList(tObject.getClass());
		boolean isUpdate=true;
		if(Type.equals("DELETE") ||Type.equals("SAVE")){
			isUpdate=false;
		}
				
		if(!isUpdate){
			//表示为新增
			returnNewContext=new HashMap<String,Object>();
			for(Field field : fieldList){
				if(!field.getType().equals(byte[].class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,field.getName());
					if(tOjectFieldValue !=null){
						if(!ReflectOperation.isBaseType(tOjectFieldValue))
						{
							tOjectFieldValue=ReflectOperation.getPrimaryKeyValue(tOjectFieldValue);
						}
					}
					if(field.getType().equals(Date.class) || field.getType().equals(DateTime.class)){
						SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd");
						if(tOjectFieldValue !=null){
							tOjectFieldValue=formata.format(tOjectFieldValue).toString();
						}
					}
					boolean isExistIColumn=false;
					if(field.isAnnotationPresent(IColumn.class)){
					IColumn iColumn = field.getAnnotation(IColumn.class);
					if(!StringUtils.isBlank(iColumn.tagMethodName())){
						Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
						Map<Object,Object> mapValue=new LinkedHashMap<Object,Object>();
						try{
							mapValue = (Map<Object,Object>)method.invoke(RequestManager.getTOject().getClass());
						}
						catch(Exception ex){
							for(Object object : (List<Object>)(method.invoke(RequestManager.getTOject().getClass()))){
								Object key=ReflectOperation.getFieldValue(object, "functionCode");
								Object value=ReflectOperation.getFieldValue(object, "functionName");
								mapValue.put(key, value);
							}
						}
						if(mapValue !=null){
							if(tOjectFieldValue !=null){
								if(!StringUtils.isBlank(tOjectFieldValue.toString())){
									tOjectFieldValue = tOjectFieldValue+"("+mapValue.get(tOjectFieldValue)+")";
								}
							}
						}
					}
					if(!StringUtils.isBlank(iColumn.description())){
						isExistIColumn=true;
						
						returnNewContext.put(iColumn.description(), tOjectFieldValue);
						
					}
				}//end if(field.isAnnotationPresent(IColumn.class))
				if(!isExistIColumn){
					returnNewContext.put(field.getName(), tOjectFieldValue);
				}//end if(!isExistIColumn)
			  }
		   }
			
		}
		else
		{
			Map<String,Object> oldMap= (Map<String, Object>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("UserOperationUpdateList");
			returnNewContext=new HashMap<String,Object>();
			if(!Type.equals("UPDATELIST")){
				for(Field field : fieldList){
					if(!ReflectOperation.isPrimaryKeyField(field) && !field.getType().equals(byte[].class)){
						Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,field.getName());
						if(tOjectFieldValue !=null){
							if(!ReflectOperation.isBaseType(tOjectFieldValue))
							{
								tOjectFieldValue=ReflectOperation.getPrimaryKeyValue(tOjectFieldValue);
							}
						}
						if(field.getType().equals(Date.class) || field.getType().equals(DateTime.class)){
							SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd");
							if(tOjectFieldValue !=null){
								tOjectFieldValue=formata.format(tOjectFieldValue).toString();
							}
						}
						if(tOjectFieldValue ==null){tOjectFieldValue="";}
						if(!oldMap.isEmpty()){
							for(Map.Entry<String,Object> entryOld: oldMap.entrySet()){
								if(entryOld.getKey().equals(field.getName())){
									Object dataBaseFieldValue=entryOld.getValue();
									if(dataBaseFieldValue == null){
										dataBaseFieldValue = "";
									}
									if(dataBaseFieldValue !=null){
										if(!ReflectOperation.isBaseType(dataBaseFieldValue))
										{
											dataBaseFieldValue=ReflectOperation.getPrimaryKeyValue(dataBaseFieldValue);
										}
									}
									if(field.getType().equals(Date.class) || field.getType().equals(DateTime.class)){
										SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd");
										if(dataBaseFieldValue !=null&&dataBaseFieldValue.toString().length()>0 ){
											dataBaseFieldValue=formata.format(dataBaseFieldValue).toString();
										}
									}
									if(!tOjectFieldValue.equals(dataBaseFieldValue)){
										boolean isExistIColumn=false;
										if(field.isAnnotationPresent(IColumn.class)){
											IColumn iColumn = field.getAnnotation(IColumn.class);
											if(!StringUtils.isBlank(iColumn.tagMethodName())){
												Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
												Map<Object,Object> mapValue=new LinkedHashMap<Object,Object>();
												try{
													mapValue = (Map<Object,Object>)method.invoke(RequestManager.getTOject().getClass());
												}
												catch(Exception ex){
													for(Object object : (List<Object>)(method.invoke(RequestManager.getTOject().getClass()))){
														Object key=ReflectOperation.getFieldValue(object, "functionCode");
														Object value=ReflectOperation.getFieldValue(object, "functionName");
														mapValue.put(key, value);
													}
												}	
												if(!StringUtils.isBlank(tOjectFieldValue.toString())){
													tOjectFieldValue = mapValue.get(tOjectFieldValue);
												}
												if(!StringUtils.isBlank(dataBaseFieldValue.toString())){
													dataBaseFieldValue = mapValue.get(dataBaseFieldValue);
												}
											}
											if(!StringUtils.isBlank(iColumn.description())){
												isExistIColumn=true;
								
												returnNewContext.put(iColumn.description(), tOjectFieldValue);
												returnNewContext.put(iColumn.description()+"_Old", dataBaseFieldValue);
											}
										}//end if(field.isAnnotationPresent(IColumn.class))
										if(!isExistIColumn){
											returnNewContext.put(field.getName(), tOjectFieldValue);
											returnNewContext.put(field.getName()+"_Old", dataBaseFieldValue);
										}//end if(!isExistIColumn)
									}//end if(tOjectFieldValue.equals(dataBaseFieldValue))
								}
							}
						}
					}
				}
			}
			else{
				//表示新增

				List<Field> fieldJoinList=ReflectOperation.getJoinColumnFieldList(RequestManager.getTOject().getClass());
				List<Field> fieldisIdListFieldList=ReflectOperation.getIdListTargetFieldList(RequestManager.getTOject().getClass());
				List<Field> fieldisIdListFieldByList=getIdListTargetFieldListByUserOper(RequestManager.getTOject().getClass());
				if(oldMap==null){
					//返回主键对应的名称的数据
					//1.选择的主键
					for(Field fieldP : fieldJoinList)
					{
						boolean falg=true;
						JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
						for(Field fieldID : fieldisIdListFieldList){
							if(ijColumn.name().equals(fieldID.getName()))
							{
								falg=false;
								break;
							}
						}
						if(falg)
						{
							//String tOjectFieldValuex=ReflectOperation.getFieldShowValue(tObject, fieldP.getName());
							
							Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,fieldP.getName());
							
							if(!ReflectOperation.isBaseType(tOjectFieldValue)){
								tOjectFieldValue=ReflectOperation.getPrimaryKeyValue(tOjectFieldValue);
							}

							if(fieldP.isAnnotationPresent(IColumn.class)){
								IColumn iColumn = fieldP.getAnnotation(IColumn.class);
								if(!StringUtils.isBlank(iColumn.tagMethodName())){
									Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
									Map<Object,Object> mapValue=new LinkedHashMap<Object,Object>();
									try{
										mapValue = (Map<Object,Object>)method.invoke(RequestManager.getTOject().getClass());
									}
									catch(Exception ex){
										for(Object object : (List<Object>)(method.invoke(RequestManager.getTOject().getClass()))){
											Object key=ReflectOperation.getFieldValue(object, "functionCode");
											Object value=ReflectOperation.getFieldValue(object, "functionName");
											mapValue.put(key, value);
										}
									}
								}
								List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
								for(Field fk : fieldPa){
									if(fk.getName().equals(ijColumn.name())){
										if(fieldP.isAnnotationPresent(IColumn.class)){
											IColumn iColumnfk = fk.getAnnotation(IColumn.class);
											if(iColumnfk==null){
												returnNewContext.put(fk.getName(), tOjectFieldValue);
												break;
											}
											if(!StringUtils.isBlank(iColumnfk.description())){
												returnNewContext.put("ID_"+iColumnfk.description(), tOjectFieldValue);
												break;
											}
											else
											{
												returnNewContext.put("ID_"+ijColumn.name(), tOjectFieldValue);
												break;
											}
										}
										
									}
								}
								
								break;
							}
						}
					}
					//2.其他外键的名称
					for(Field fieldP : fieldisIdListFieldList){
						Object[] tOjectFieldValue= null;
						for(Field fieldFK : fieldisIdListFieldByList){
							IColumn iColumn = fieldFK.getAnnotation(IColumn.class);
							if(fieldP.getName().equals(iColumn.target())){
								 tOjectFieldValue= (Object[])ReflectOperation.getFieldValue(tObject,fieldFK.getName());
								 List<Object> returnObject=new ArrayList<Object>();
								 if(fieldP.isAnnotationPresent(IColumn.class)){
										IColumn iColumn1 = fieldP.getAnnotation(IColumn.class);
										
										if(!StringUtils.isBlank(iColumn1.tagMethodName())){
											Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn1.tagMethodName());
											Map<String,String> mapValue = (Map<String,String>)method.invoke(RequestManager.getTOject().getClass());
											
											for(Object o  :tOjectFieldValue){
												if(o != null){
													o = mapValue.get(o);
												}
												if(!returnObject.contains(o)){
													returnObject.add(o);
												}
											}
										}
										else{
											 for(Object o  :tOjectFieldValue){
												 if(!returnObject.contains(o)){
													returnObject.add(o);
													}
												}
										}
								 }
								 else{
									 for(Object o  :tOjectFieldValue){
										 if(!returnObject.contains(o)){
											returnObject.add(o);
										 }
									 }
								 }
								 
								 
								if(!ReflectOperation.isBaseType(fieldP.getType())){
									JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
									List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
									for(Field fk : fieldPa){
										if(fk.getName().equals(ijColumn.name())){
											if(fieldP.isAnnotationPresent(IColumn.class)){
												IColumn iColumnfk = fk.getAnnotation(IColumn.class);
												if(iColumnfk==null){
													returnNewContext.put(fk.getName(), returnObject);
													break;
												}
												if(!StringUtils.isBlank(iColumnfk.description())){
													returnNewContext.put(iColumnfk.description(), returnObject);
													break;
												}
												else{
														returnNewContext.put(fk.getName(), returnObject);
														break;
												}
											}
											
										}
									}
								}
								else
								{
									if(fieldP.isAnnotationPresent(IColumn.class)){
										IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
										if(!StringUtils.isBlank(iColumnfk.description())){
											returnNewContext.put(iColumnfk.description(), returnObject);
											break;
										}
									}
								}
								break;
							}
						}
						//Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,fieldP.getName());

						/*if(fieldP.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = fieldP.getAnnotation(IColumn.class);
							if(!StringUtils.isBlank(iColumn.tagMethodName())){
								Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
								Map<String,String> mapValue = (Map<String,String>)method.invoke(RequestManager.getTOject().getClass());
								if(tOjectFieldValue != null){
									tOjectFieldValue = mapValue.get(tOjectFieldValue);
								}
							}*/
							//returnNewContext.put(fieldP.getName(), tOjectFieldValue);
						//}
					}
				}
				else{
					
					if(oldMap.size()==0){
						//返回主键对应的名称的数据
						//1.选择的主键
						for(Field fieldP : fieldJoinList)
						{
							boolean falg=true;
							JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
							for(Field fieldID : fieldisIdListFieldList){
								if(ijColumn.name().equals(fieldID.getName()))
								{
									falg=false;
									break;
								}
							}
							if(falg)
							{
								Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,fieldP.getName());
								
								// 选择执行节点时，autoETL_ActivityNode为空，异常  lanyuesheng
								if(tOjectFieldValue == null){
									continue;
								}
								
								 tOjectFieldValue=ReflectOperation.getPrimaryKeyValue(tOjectFieldValue);

								if(fieldP.isAnnotationPresent(IColumn.class)){
									IColumn iColumn = fieldP.getAnnotation(IColumn.class);
									if(!StringUtils.isBlank(iColumn.tagMethodName())){
										Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
										Map<String,String> mapValue = (Map<String,String>)method.invoke(RequestManager.getTOject().getClass());
										if(tOjectFieldValue != null){
											tOjectFieldValue = tOjectFieldValue+"("+mapValue.get(tOjectFieldValue)+")";
											
										}
									}
									List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
									for(Field fk : fieldPa){
										if(fk.getName().equals(ijColumn.name())){
											if(fieldP.isAnnotationPresent(IColumn.class)){
												IColumn iColumnfk = fk.getAnnotation(IColumn.class);
												if(iColumnfk==null){
													returnNewContext.put("ID_"+ijColumn.name(), tOjectFieldValue);
													break;
												}
												if(!StringUtils.isBlank(iColumnfk.description())){
													returnNewContext.put("ID_"+iColumnfk.description(), tOjectFieldValue);
													break;
												}
												else
												{
													returnNewContext.put("ID_"+ijColumn.name(), tOjectFieldValue);
													break;
												}
											}
											
										}
									}
									break;
								}
							}
						}
						//2.其他外键的名称
						for(Field fieldP : fieldisIdListFieldList)
						{
							Object[] tOjectFieldValue= null;
							for(Field fieldFK : fieldisIdListFieldByList){
								IColumn iColumn = fieldFK.getAnnotation(IColumn.class);
								if(fieldP.getName().equals(iColumn.target()))
								{
									 tOjectFieldValue= (Object[])ReflectOperation.getFieldValue(tObject,fieldFK.getName());
									 List<Object> returnObject=new ArrayList<Object>();
									 if(fieldP.isAnnotationPresent(IColumn.class)){
											IColumn iColumn1 = fieldP.getAnnotation(IColumn.class);
											
											if(!StringUtils.isBlank(iColumn1.tagMethodName())){
												Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn1.tagMethodName());
												Object  mapValue = method.invoke(RequestManager.getTOject().getClass()); 
													for(Object o  :tOjectFieldValue){
														if(o != null){
															try{
																Map<Object,Object> mapList=(Map<Object,Object>)mapValue;
																o =o+"("+ mapList.get(o)+")";
															}
															catch(Exception ex){
																for(Object object : (ArrayList<Object>)mapValue){
																	//for(Object ieldValue : (ArrayList<Object>)object){
																		Object ox=ReflectOperation.getFieldValue(object,"functionCode");
																		if(ox!=null && ox.equals(o)){
																			ox=ReflectOperation.getFieldValue(object,"functionName");
																			o =o+"("+ ox+")";
																		}
															}
														}
														returnObject.add(o);
													}
												}
											}
											else
											{
												 for(Object o  :tOjectFieldValue){
													 if(!returnObject.contains(o)){
														returnObject.add(o);
														}
													}
											}
									 }
									 else{
										 for(Object o  :tOjectFieldValue){
											 if(!returnObject.contains(o)){
												returnObject.add(o);
												}
											}
									 }

									 
										if(!ReflectOperation.isBaseType(fieldP.getType()))
										{
											JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
											List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
											for(Field fk : fieldPa){
												if(fk.getName().equals(ijColumn.name())){
													if(fieldP.isAnnotationPresent(IColumn.class)){
														IColumn iColumnfk = fk.getAnnotation(IColumn.class);
														if(!StringUtils.isBlank(iColumnfk.description())){
															returnNewContext.put(iColumnfk.description(), returnObject);
															break;
														}
														else
														{
																returnNewContext.put(fk.getName(), returnObject);
																break;
															}
														}
													
													}
											 }
										}
										else
										{
											if(fieldP.isAnnotationPresent(IColumn.class)){
												IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
												if(!StringUtils.isBlank(iColumnfk.description())){
													returnNewContext.put(iColumnfk.description(), returnObject);
													break;
												}
											}
										}
									break;
								}
							}
						}
					}
					else{
					//处理删除和修改
					//2.1判断是否为修改
					boolean isDeleteflag=false;
					//for(Field fieldP : fieldisIdListFieldList){
					for(Field fieldP : fieldisIdListFieldByList){
						//for(Map.Entry<String,Object> entryIS : oldMap.entrySet()){
							Object[] tOjectFieldValue = (Object[])ReflectOperation.getFieldValue(tObject,fieldP.getName());
							if(tOjectFieldValue.length==0){
								isDeleteflag=true;
								break;
							//}
						}
					}
					//2.2删除
					if(isDeleteflag){
						for(Field fieldP : fieldJoinList){
							boolean falg=true;
							JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
							for(Field fieldID : fieldisIdListFieldList){
								if(ijColumn.name().equals(fieldID.getName()))
								{
									falg=false;
									break;
								}
							}
							if(falg)
							{
								
								Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,fieldP.getName());

								
								tOjectFieldValue=ReflectOperation.getPrimaryKeyValue(tOjectFieldValue);

								if(fieldP.isAnnotationPresent(IColumn.class)){
									IColumn iColumn = fieldP.getAnnotation(IColumn.class);
									if(!StringUtils.isBlank(iColumn.tagMethodName())){
										Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
										Map<Object,Object> mapValue=new LinkedHashMap<Object,Object>();
										try{
											mapValue = (Map<Object,Object>)method.invoke(RequestManager.getTOject().getClass());
										}
										catch(Exception ex){
											for(Object object : (List<Object>)(method.invoke(RequestManager.getTOject().getClass()))){
												Object key=ReflectOperation.getFieldValue(object, "functionCode");
												Object value=ReflectOperation.getFieldValue(object, "functionName");
												mapValue.put(key, value);
											}
										}
										if(tOjectFieldValue != null){
											tOjectFieldValue = tOjectFieldValue+"("+mapValue.get(tOjectFieldValue)+")";
											
										}
									}               
									List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
									for(Field fk : fieldPa){
										if(fk.getName().equals(ijColumn.name())){
											if(fieldP.isAnnotationPresent(IColumn.class)){
												IColumn iColumnfk = fk.getAnnotation(IColumn.class);
												if(iColumnfk==null){
													returnNewContext.put("ID_"+ijColumn.name(), tOjectFieldValue);
													break;
												}
												if(!StringUtils.isBlank(iColumnfk.description())){
													returnNewContext.put("ID_"+iColumnfk.description(), tOjectFieldValue);
													break;
												}
												else
												{
													returnNewContext.put("ID_"+ijColumn.name(), tOjectFieldValue);
													break;
												}
											}
											
										}
									}
									break;
								}
							}
						}
						for(Field fieldP : fieldisIdListFieldList)
						{
							List<Object> tOjectFieldValue= new ArrayList<Object>();
							for(Field fieldFK : fieldisIdListFieldByList){
								Object[] tOjectFieldValuexx = (Object[])ReflectOperation.getFieldValue(tObject,fieldFK.getName());
								if(tOjectFieldValuexx.length>0){
									continue;
								}
								IColumn iColumn = fieldFK.getAnnotation(IColumn.class);
								if(fieldP.getName().equals(iColumn.target())){
									for(Map.Entry<String, Object> enetryL : oldMap.entrySet()){
										Object ocx=ReflectOperation.getFieldValue(enetryL.getValue(),iColumn.target());
										if(!ReflectOperation.isBaseType(ocx)){
											ocx=ReflectOperation.getPrimaryKeyValue(ocx);
										}

										if(!tOjectFieldValue.contains(ocx)){
											tOjectFieldValue.add(ocx);
										}
									}
									
									// tOjectFieldValue= (Object[])ReflectOperation.getFieldValue(oldMap,fieldFK.getName());
									 List<Object> returnObject=new ArrayList<Object>();
									 if(fieldP.isAnnotationPresent(IColumn.class)){
											IColumn iColumn1 = fieldP.getAnnotation(IColumn.class);
											
											if(!StringUtils.isBlank(iColumn1.tagMethodName())){
												Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn1.tagMethodName());
												Map<Object,Object> mapValue=new LinkedHashMap<Object,Object>();
												try{
													mapValue = (Map<Object,Object>)method.invoke(RequestManager.getTOject().getClass());
												}
												catch(Exception ex){
													for(Object object : (List<Object>)(method.invoke(RequestManager.getTOject().getClass()))){
														Object key=ReflectOperation.getFieldValue(object, "functionCode");
														Object value=ReflectOperation.getFieldValue(object, "functionName");
														mapValue.put(key, value);
													}
												}
												for(Object o  :tOjectFieldValue){
													if(o != null){
														o =o+"("+ mapValue.get(o)+")";
													}
													returnObject.add(o);
												}
											}
											else{
												 for(Object o  :tOjectFieldValue){
														returnObject.add(o);
												}
											}
									 }
									 else{
										 for(Object o  :tOjectFieldValue){
												returnObject.add(o);
											}
									 }

									 
										if(!ReflectOperation.isBaseType(fieldP.getType())){
											JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
											List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
											for(Field fk : fieldPa){
												if(fk.getName().equals(ijColumn.name())){
													if(fieldP.isAnnotationPresent(IColumn.class)){
														IColumn iColumnfk = fk.getAnnotation(IColumn.class);
														if(iColumnfk==null){
															returnNewContext.put(fk.getName(), returnObject);
															break;
														}
														if(!StringUtils.isBlank(iColumnfk.description())){
															returnNewContext.put(iColumnfk.description(), returnObject);
															break;
														}
														else{
																returnNewContext.put(fk.getName(), returnObject);
																break;
															}
														}
													
													}
											 }
										}
										else{
											if(fieldP.isAnnotationPresent(IColumn.class)){
												IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
												if(!StringUtils.isBlank(iColumnfk.description())){
													returnNewContext.put(iColumnfk.description(), returnObject);
													break;
												}
											}
										}
									break;
										//}
								}
							}
						}
					}
					//2.3修改
					else{
						for(Field fieldP : fieldJoinList){
							boolean falg=true;
								//JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
							for(Field fieldID : fieldisIdListFieldList){
									//IColumn ijColumn=fieldID.getAnnotation(IColumn.class);
								if(fieldID.getName().equals(fieldP.getName())){
									falg=false;
									break;
								}
							}
							if(falg){
								//Object tOjectFieldValue = ReflectOperation.getFieldValue(oldMap,fieldP.getName());	
								Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,fieldP.getName());
								//if(tOjectFieldValue==null){continue;}
								tOjectFieldValue=ReflectOperation.getPrimaryKeyValue(tOjectFieldValue);


								if(fieldP.isAnnotationPresent(IColumn.class)){
									IColumn iColumn = fieldP.getAnnotation(IColumn.class);
									if(!StringUtils.isBlank(iColumn.tagMethodName())){
										Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
										Map<String,String> mapValue = (Map<String,String>)method.invoke(RequestManager.getTOject().getClass());
										if(tOjectFieldValue != null){
												tOjectFieldValue = mapValue.get(tOjectFieldValue);
											}
										}
										returnNewContext.put("ID_"+fieldP.getName(), tOjectFieldValue);
										break;
									}
								}//end if(falg)
							}
							for(Field fieldP : fieldisIdListFieldList){
								for(Field fieldFK : fieldisIdListFieldByList){
									IColumn iColumn = fieldFK.getAnnotation(IColumn.class);
									if(fieldP.getName().equals(iColumn.target())){
										Object[] tOjectFieldValue = (Object[])ReflectOperation.getFieldValue(tObject,fieldFK.getName());
										List<Object> OldTObjectFieldValue=new ArrayList<Object>();
										for(Map.Entry<String, Object> enetryL : oldMap.entrySet()){
											Object ocx=ReflectOperation.getFieldValue(enetryL.getValue(),iColumn.target());
										
											if(!ReflectOperation.isBaseType(ocx)){
												ocx=ReflectOperation.getPrimaryKeyValue(ocx);
											}
											if(!OldTObjectFieldValue.contains(ocx)){
												OldTObjectFieldValue.add(ocx);
												}
											//OldTObjectFieldValue.add(ocx);
										}
		
										if(fieldP.isAnnotationPresent(IColumn.class)){
											IColumn iColumn1 = fieldP.getAnnotation(IColumn.class);
											List<Object> returnObject=new ArrayList<Object>();
											List<Object> returnOldObject=new ArrayList<Object>();
											if(!StringUtils.isBlank(iColumn1.tagMethodName())){
												Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn1.tagMethodName());
												//Map<String,String> mapValue = (Map<String,String>)method.invoke(RequestManager.getTOject().getClass());
												Map<Object,Object> mapValue=new LinkedHashMap<Object,Object>();
												try{
													mapValue = (Map<Object,Object>)method.invoke(RequestManager.getTOject().getClass());
												}
												catch(Exception ex){
													for(Object object : (List<Object>)(method.invoke(RequestManager.getTOject().getClass()))){
														Object key=ReflectOperation.getFieldValue(object, "functionCode");
														Object value=ReflectOperation.getFieldValue(object, "functionName");
														mapValue.put(key, value);
													}
												}
												
												
												
												for(Object o : tOjectFieldValue){
													boolean isContain=false;
													for(Object tx : OldTObjectFieldValue){
														if(o.equals(tx)){
															isContain=true;
															break;
														}
													}
													if(!isContain)
													{	
														for(Object ox: tOjectFieldValue){
															if(ox != null){
																ox =ox+"("+ mapValue.get(ox)+")";
															}
															if(!returnObject.contains(ox)){
																returnObject.add(ox);
															}
														}
														break;
													}
														
												}
												if(!ReflectOperation.isBaseType(fieldP.getType())){
														JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
														List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
														for(Field fk : fieldPa){
															if(fk.getName().equals(ijColumn.name())){
																if(fieldP.isAnnotationPresent(IColumn.class)){
																	IColumn iColumnfk = fk.getAnnotation(IColumn.class);
																	if(!StringUtils.isBlank(iColumnfk.description()) && returnObject.size()>0){
																		returnNewContext.put(iColumnfk.description(), returnObject);
																		break;
																	}
																	else if(returnObject.size()>0){
																			returnNewContext.put(fk.getName(), returnObject);
																			break;
																	}
																}
																	
															}
														}
													}
													else{
														if(fieldP.isAnnotationPresent(IColumn.class)){
															IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
															if(!StringUtils.isBlank(iColumnfk.description())&& returnObject.size()>0){
																returnNewContext.put(iColumnfk.description(), returnObject);
															
															}
														}
													}
														//returnNewContext.put(fieldP.getName(), returnObject);
													for(Object ox  :OldTObjectFieldValue){
														boolean isContains=false;
														for(Object tx : tOjectFieldValue){
															if(ox.equals(tx)){
																isContains=true;
																break;
															}
														}
														if(!isContains ){
															for(Object oxx  :OldTObjectFieldValue){
																if(oxx != null){
																	oxx =oxx+"("+ mapValue.get(oxx)+")";
																}
																if(!returnOldObject.contains(oxx)){
																	returnOldObject.add(oxx);
																}
															}
															break;
														}
													}

														 
													if(!ReflectOperation.isBaseType(fieldP.getType())){
														JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
														List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
														for(Field fk : fieldPa){
															if(fk.getName().equals(ijColumn.name())){
																if(fieldP.isAnnotationPresent(IColumn.class)){
																	IColumn iColumnfk = fk.getAnnotation(IColumn.class);
																	if(!StringUtils.isBlank(iColumnfk.description()) && returnOldObject.size()>0){
																		returnNewContext.put(iColumnfk.description()+"_OLD", returnOldObject);
																		break;
																	}
																	else if( returnOldObject.size()>0){
																			returnNewContext.put(fk.getName()+"_OLD", returnOldObject);
																			break;
																		}
																	}
																	
																}
														 }
													}
													else{
														if(fieldP.isAnnotationPresent(IColumn.class)){
															IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
															if(!StringUtils.isBlank(iColumnfk.description()) && returnOldObject.size()>0){
																returnNewContext.put(iColumnfk.description()+"_OLD", returnOldObject);
															}
														}
													}
														//returnNewContext.put(fieldP.getName()+"_OLD", returnObject);
												break;
											}
											else{
													/////新
												for(Object o : tOjectFieldValue){
														boolean isContain=false;
														for(Object tx : OldTObjectFieldValue){
															if(o.equals(tx)){
																isContain=true;
																break;
															}
														}
														if(!isContain){
															for(Object ox : tOjectFieldValue){
																if(!returnObject.contains(ox)){
																	returnObject.add(ox);
																}
															}
															break;
														}
												}
												if(!ReflectOperation.isBaseType(fieldP.getType())){
													JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
													List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
													for(Field fk : fieldPa){
														if(fk.getName().equals(ijColumn.name())){
															if(fieldP.isAnnotationPresent(IColumn.class)){
																IColumn iColumnfk = fk.getAnnotation(IColumn.class);
																if(!StringUtils.isBlank(iColumnfk.description()) && returnObject.size()>0){
																	returnNewContext.put(iColumnfk.description(), returnObject);
																	break;
																}
																else if(returnObject.size()>0){
																	returnNewContext.put(fk.getName(), returnObject);
																	break;
																}
														}	
													}
												}
											}
											else{
												if(fieldP.isAnnotationPresent(IColumn.class)){
													IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
													if(!StringUtils.isBlank(iColumnfk.description()) && returnObject.size()>0){
														returnNewContext.put(iColumnfk.description(), returnObject);
														break;
													}
												}
											}
																//returnNewContext.put(fieldP.getName(), returnObject);
											for(Object ox  :OldTObjectFieldValue){
												boolean isContains=false;
												for(Object tx : tOjectFieldValue){
													if(ox.equals(tx)){
														isContains=true;
														break;
													}
												}
												if(!isContains){
													for(Object oxx  :OldTObjectFieldValue){
														if(!returnOldObject.contains(oxx)){
															returnOldObject.add(oxx);
														}
													}
													break;
												}
											}
											if(!ReflectOperation.isBaseType(fieldP.getType())){
												JoinColumn ijColumn = fieldP.getAnnotation(JoinColumn.class);
												List<Field> fieldPa=ReflectOperation.getColumnFieldList(fieldP.getType());
												for(Field fk : fieldPa){
													if(fk.getName().equals(ijColumn.name())){
														if(fieldP.isAnnotationPresent(IColumn.class)){
															IColumn iColumnfk = fk.getAnnotation(IColumn.class);
															if(!StringUtils.isBlank(iColumnfk.description()) && returnOldObject.size()>0){
																returnNewContext.put(iColumnfk.description()+"_OLD", returnOldObject);
																break;
															}
															else if(returnOldObject.size()>0){
																returnNewContext.put(fk.getName()+"_OLD", returnOldObject);
																break;
															}
														}
																			
													}
												}
											}
											else{
												if(fieldP.isAnnotationPresent(IColumn.class)){
													IColumn iColumnfk = fieldP.getAnnotation(IColumn.class);
													if(!StringUtils.isBlank(iColumnfk.description()) && returnOldObject.size()>0){
														returnNewContext.put(iColumnfk.description()+"_OLD", returnOldObject);
														break;
													}
												}
											}
																//returnNewContext.put(fieldP.getName()+"_OLD", returnObject);
												break;
										}
									}//end if(fieldP.isAnnotationPresent(IColumn.class)){
								}
								}
								/*
								if(!tOjectFieldValue.equals(OldTObjectFieldValue)){
									if(fieldP.isAnnotationPresent(IColumn.class)){
										IColumn iColumn = fieldP.getAnnotation(IColumn.class);
										if(!StringUtils.isBlank(iColumn.tagMethodName())){
											Method method = ReflectOperation.getReflectMethod(RequestManager.getTOject().getClass(), iColumn.tagMethodName());
											Map<String,String> mapValue = (Map<String,String>)method.invoke(RequestManager.getTOject().getClass());
											if(tOjectFieldValue != null){
												tOjectFieldValue = mapValue.get(tOjectFieldValue);
											}
											if(OldTObjectFieldValue != null){
												OldTObjectFieldValue = mapValue.get(OldTObjectFieldValue);
											}
										}
										returnNewContext.put(fieldP.getName(), tOjectFieldValue);
										returnNewContext.put(fieldP.getName()+"_OLD", OldTObjectFieldValue);
									}//end if(fieldP.isAnnotationPresent(IColumn.class))
								}//end if(!tOjectFieldValue.equals(OldTObjectFieldValue)){
								
								*/
							}//end for(Field fieldP : fieldisIdListFieldList){
						}//end 修改的else
					}//end 判断当前状态是否存在size
				}//end panuuan null
			}//end if(!Type.equals("UPDATELIST")) 的else
		}//end if(!isUpdate){
		if(Type.equals("DELETE") || Type.equals("UPDATE")){
			for(Field field : fieldList){
				if(ReflectOperation.isPrimaryKeyField(field)){
					returnNewContext.put("ID",id);	
					break;
				}
			}
		}
		
		return returnNewContext;
	}

    public  List<Field> getIdListTargetFieldListByUserOper(Class<?> type) throws Exception{
    	Field[] fields = ReflectOperation.getReflectFields(type);
    	List<Field> fieldList = new ArrayList<Field>();
 
    	for(int i=0;i<fields.length;i++){
            if(fields[i].isAnnotationPresent(IColumn.class)){
            	IColumn iColumn = fields[i].getAnnotation(IColumn.class);
            	if(iColumn.isIdListField()){
    				fieldList.add(ReflectOperation.getReflectField(type,fields[i].getName()));
            	}
			}
		}

    	return fieldList;
	}

    
}
