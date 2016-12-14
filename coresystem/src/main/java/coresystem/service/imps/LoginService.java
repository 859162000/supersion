package coresystem.service.imps;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

import extend.dto.SystemParam;

import coresystem.dto.ActionExcute;
import coresystem.dto.InstInfo;
import coresystem.dto.RoleFunction;
import coresystem.dto.RoleInfo;
import coresystem.dto.UserInfo;
import coresystem.dto.UserRole;
import framework.helper.AESSecretKey;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.IVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.Function;
import framework.security.SecurityContext;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
import framework.show.ShowNavigation;
import framework.show.ShowNavigationComponent;

public class LoginService extends BaseService {
	
	private String[] logicHandleBeanIdList;
	
	private boolean selfDataSecurity;
	
	private boolean selfInstDataSecurity;
	
	private boolean notNeedPassword;

	@SuppressWarnings({ "unchecked"})
	public void initSuccessResult() throws Exception {

		if(logicHandleBeanIdList != null){
			for(int i=0;i<logicHandleBeanIdList.length;i++){
				IVoidResultExecute voidResultExecute = (IVoidResultExecute)FrameworkFactory.CreateBean(logicHandleBeanIdList[i]);
				voidResultExecute.voidResultExecute();
			}
		}
		
		UserInfo loginUserInfo = (UserInfo)RequestManager.getTOject();
		
		RequestManager.setId(loginUserInfo.getStrUserCode());
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		
		UserInfo userInfo = null;
		try{
			userInfo = (UserInfo)singleObjectFindByIdDao.paramObjectResultExecute(null);
		}
		catch(Exception ex){
			this.setServiceResult(new MessageResult(false, "数据库连接失败"));
		}
		
		if(userInfo != null && userInfo.getStrAllowState().equals("4")){
			userInfo = null;
		}

		// 如果是内置超级用户没找到，则创建并赋管理员权限
		boolean isAdministrator = false;
		String strPassword = "";

		if(userInfo == null && loginUserInfo.getStrUserCode().equals(SecurityContext.getInstance().getSysUserCode())) {
			userInfo = new UserInfo();
			userInfo.setStrUserName(SecurityContext.getInstance().getSysUserCode());
			userInfo.setStrPassword(SecurityContext.getInstance().getSysUserInitPWD());
			userInfo.setStrUserCode(SecurityContext.getInstance().getSysUserCode());
			userInfo.setUpdatePasswordTime(TypeParse.maxDate());
			isAdministrator = true;
			strPassword = userInfo.getStrPassword();
		}
		else{
			if(userInfo != null){
				strPassword = AESSecretKey.DecryString(userInfo.getStrPassword(), null);
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastUpdateTime = null;
		if(userInfo != null)
			lastUpdateTime = userInfo.getUpdatePasswordTime();
		if(lastUpdateTime == null && userInfo != null){
			this.setServiceResult(new MessageResult(false, "最后一次修改密码时间为空"));
		}
		
		IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Date date = new Date();
		int dateNum = 0;
		if(userInfo != null)
			dateNum = (int) ((date.getTime() - lastUpdateTime.getTime())/(24*60*60*1000));
		
		int overdueDate = 0;
		boolean isPasswordRight = strPassword.equals(loginUserInfo.getStrPassword());
		if(notNeedPassword){
			isPasswordRight = true;
		}
		if(userInfo != null && isPasswordRight && !isAdministrator) {
			
			// 成功时，检查用户密码是否过期，提醒
			String id = "1"; // 从参数表(代码1)中取密码过期天数
			Object dayObject = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", id , null});
			if (dayObject == null) {
//				return  new MessageResult(true, "系统参数列表不存在参数：过期天数");
			}
			else{
				try{
					SystemParam codeSet = (SystemParam)dayObject;
					if(codeSet.getStrEnable() != null && codeSet.getStrEnable().equals("1")){
						int dayNum = Integer.parseInt(codeSet.getStrParamValue());
						int dueDays = dateNum-dayNum;
						overdueDate = dayNum - dateNum + 1;
						if(dueDays>0){
							this.setServiceResult(new MessageResult(false,"密码已过期，请联系管理员"));
						}
					}
				}
				catch(Exception ex){
				}
			}
			
			if(!StringUtils.isBlank(userInfo.getStrLockState()) && !userInfo.getStrLockState().equals("1")){
				this.setServiceResult(new MessageResult(false, "用户被锁定，请联系管理员"));
			}
			
			 Object object = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "8" , null});
			 if(object != null) {
				try{
					SystemParam systemParam = (SystemParam)object;
					if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
						int day = Integer.parseInt(systemParam.getStrParamValue());
						Date date1 = TypeParse.parseDate(TypeParse.parseString(new Date(), "yyyy-MM-dd"));
						//Date date2 = new Date(date1.getTime()-day*24*60*60*1000);
						Calendar ca= Calendar.getInstance();
						ca.setTime(date1);
						ca.add(Calendar.DATE, -day);
						Date date2=new Date();
						date2=ca.getTime();
						if(userInfo.getLastLoginTime().getTime() < date2.getTime()){
							IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
							userInfo.setStrLockState("3");
							singleObjectSaveDao.paramVoidResultExecute(new Object[]{userInfo,null});
							this.setServiceResult(new MessageResult(false, "用户有"+day+"天未登录系统被锁定，请联系管理员"));
						}
					}
				}
				catch(Exception ex){
				}
			}
		}
		
		if(userInfo != null && isPasswordRight) {

			Map<String, Map<String,String>> dataSecurityMap = SecurityContext.getInstance().getDataSecurityMap();
			
			if(isAdministrator){
				Set<String> functionCodeSet = new HashSet<String>(); // 功能权限

				for(Function function :SecurityContext.getInstance().getFunctionSet()){
					functionCodeSet.add(function.getFunctionCode());
				}

				SecurityContext.getInstance().setFunctionCodeSet(functionCodeSet); // 设置功能权限
				
				// 设置数据权限
				IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
				for(Map.Entry<String, Map<String,String>> dataSecurityEntry : dataSecurityMap.entrySet()){ // 每个配置的数据安全
					Map<String, String> map = dataSecurityEntry.getValue();
					for(Map.Entry<String,String> entry : map.entrySet()){
						Field field = ReflectOperation.getFieldByName(entry.getKey(), entry.getValue());
						if(field!=null){//20150815 xjl 
							List<Object> objList = (List<Object>)dao.paramObjectResultExecute(new Object[]{field.getType().getName(), null});
							for(Object dataObj : objList) { 
								SecurityContext.getInstance().addDataSecurity(entry.getKey(), entry.getValue(),ReflectOperation.getPrimaryKeyValue(dataObj).toString());
							}
						}
					}
				}
				
				SecurityContext.getInstance().getLoginInfo().setAdministrator(true);
			}
			else{
				//设置功能权限,账号校对正确,查出用户角色
				Set<String> functionCodeSet = new HashSet<String>();
				Set<RoleInfo> roleInfoSet = new HashSet<RoleInfo>();
				for(UserRole userRole : userInfo.getUserRoles()){
					for(RoleFunction roleFunction : userRole.getRoleInfo().getRoleFunctions()){
						functionCodeSet.add(roleFunction.getStrFunctionCode());
					}
					RoleInfo roleInfo = new RoleInfo();
					roleInfo.setStrRoleCode(userRole.getRoleInfo().getStrRoleCode());
					roleInfoSet.add(roleInfo);
				}

				SecurityContext.getInstance().setFunctionCodeSet(functionCodeSet);

				// 设置数据功能
				for(Map.Entry<String, Map<String,String>> dataSecurityEntry : dataSecurityMap.entrySet()){
					Class<?> type = Class.forName(dataSecurityEntry.getKey());
					Field roleField = null;
					Field classField = null;
					Field dateField = null;
					List<Field> fieldList = ReflectOperation.getColumnFieldList(type);
					for(Field field : fieldList){
						if(field.getType().equals(RoleInfo.class)){
							roleField = field;
						}
						else if(!ReflectOperation.isBaseType(field.getType())){
							dateField = field;
						}
						if(field.isAnnotationPresent(IColumn.class)){
							IColumn iColumn = field.getAnnotation(IColumn.class);
							if(!StringUtils.isBlank(iColumn.tagMethodName())){
								classField = field;
							}
						}
					}
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
					if(roleInfoSet.size() == 0){
						detachedCriteria.add(Restrictions.eq(roleField.getName(), null));
					}
					else{
						detachedCriteria.add(Restrictions.in(roleField.getName(), roleInfoSet));
					}

			    	IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			    	List<Object> objectList = (List<Object>)defaultLogicDaoDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			    	for(Object object : objectList) { 
			    		String classValue = ReflectOperation.getFieldValue(object, classField.getName()).toString();
			    		String fieldValue = dataSecurityEntry.getValue().get(classValue);
			    		String dataValue = ReflectOperation.getPrimaryKeyValue(ReflectOperation.getFieldValue(object, dateField.getName())).toString();
						SecurityContext.getInstance().addDataSecurity(classValue, fieldValue, dataValue);
					}
			    	
			    	if(selfDataSecurity){
			    		if(dateField.getType().equals(UserInfo.class)){
			    			Map<String, String> map = dataSecurityEntry.getValue();
							for(Map.Entry<String,String> entry : map.entrySet()){
								SecurityContext.getInstance().addDataSecurity(entry.getKey(), entry.getValue(),userInfo.getStrUserCode());
							}
			    		}
			    	}
			    	
			    	if(selfInstDataSecurity){
			    		if(dateField.getType().equals(InstInfo.class)){
			    			Map<String, String> map = dataSecurityEntry.getValue();
							for(Map.Entry<String,String> entry : map.entrySet()){
								SecurityContext.getInstance().addDataSecurity(entry.getKey(), entry.getValue(),userInfo.getInstInfo().getStrInstCode());
							}
			    		}
			    	}
				}
			}
			
			// 保存当前登录用户信息
			SecurityContext.getInstance().getLoginInfo().setTag(userInfo);
			
            // 隐藏没有权限的菜单
			for(ShowNavigation showNavigation : ShowContext.getInstance().getShowNavigationList()){
				if(SecurityContext.getInstance().getLoginInfo().getActionNameSet().contains(showNavigation.getNavigationUrl()))
				{
					if(ServletActionContext.getContext().getSession().get(ApplicationManager.getShowNavigation(showNavigation.getLevel())) == null){
			   		    List<ShowNavigation> tempNavigationList = new ArrayList<ShowNavigation>();
			   		    tempNavigationList.add(showNavigation);
			   		    ServletActionContext.getContext().getSession().put(ApplicationManager.getShowNavigation(showNavigation.getLevel()), tempNavigationList);
			   		}
			   		else{
			   			List<ShowNavigation> tempNavigationList = (List<ShowNavigation>)ServletActionContext.getContext().getSession().get(ApplicationManager.getShowNavigation(showNavigation.getLevel()));
			   			tempNavigationList.add(showNavigation);
			   		}
				}
			}
			
			List<ShowNavigationComponent> showNavigationComponentList = new ArrayList<ShowNavigationComponent>();
			for(ShowNavigationComponent showNavigationComponent : ShowContext.getInstance().getShowNavigationComponentList()){
				String url = showNavigationComponent.getUrl();
				if(url.equals("Framework/NavigationPage.jsp")){
					url += "?id=" + showNavigationComponent.getId();
				}
				if(SecurityContext.getInstance().getLoginInfo().getActionNameSet().contains(url))
				{
					showNavigationComponentList.add(showNavigationComponent);
				}
			}
			ServletActionContext.getContext().getSession().put(ApplicationManager.getShowNavigation(ShowNavigationComponent.class.getSimpleName()), showNavigationComponentList);
			
			if(!isAdministrator){
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				userInfo.setErrorCount("0");
				userInfo.setLastLoginTime(simpleDateFormat.format(new Date()));
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{userInfo,null});
			}
			// 从参数表中取密码有效期提示时间，到期提示修改密码
			// 从参数表(代码2)中取密码有效期提示时间
			Object tObject = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "2" , null});
			if (tObject == null) {
//				return  new MessageResult(true, "系统参数列表不存在参数：密码有效期");
			}
			else{
				if(overdueDate > 0){
					try{
						SystemParam codeSet = (SystemParam)tObject;
						if(codeSet.getStrEnable() != null && codeSet.getStrEnable().equals("1")){
							int dayNum = Integer.parseInt(codeSet.getStrParamValue());
							int dueDays = dateNum-dayNum;
							if(dueDays>=0){
								this.setServiceResult(new MessageResult(true,"密码还剩"+overdueDate+"天过期,请及时修改密码"));
							}
						}
					}
					catch(Exception ex){
					}
				}
			}
			
			
			tObject = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "3" , null});
			if (tObject != null) {
				SystemParam systemParam = (SystemParam)tObject;
				if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
					// 检查是否是系统默认密码，提示修改密码
					String strFieldType = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("defaultPassword");
					String defaultPassword = AESSecretKey.DecryString(strFieldType, null);
					if(defaultPassword.equals(strPassword)){
						this.setServiceResult(new MessageResult(true,"请及时修改默认密码!"));
					}
				}
			}
			if(this.getServiceResult()==null){
				this.setServiceResult(new MessageResult());
			}
			
		}
		else{
			// 记录登录出错日志，记录MAC,IP,帐号，密码 
			String userCode = loginUserInfo.getStrUserCode();
			String password = loginUserInfo.getStrPassword();

			SmallTools SmallTools = new SmallTools();
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	    	String ipAddr = SmallTools.getIpAddr(request);
			String Mac = SmallTools.getMacAddr();
			
			//写入日志
			ActionExcute logObject = new ActionExcute();
			logObject.setActionName(RequestManager.getActionName());
			logObject.setActionTime(sdf.format(new Date()));
			logObject.setActionShowName("UserInfo 登录错误：用户名："+userCode+"; 密码："+password+"; IP地址"+ipAddr+"; Mac地址："+Mac+";"); //UserInfo 登录
			
			IParamVoidResultExecute saveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			saveDao.paramVoidResultExecute(new Object[]{logObject,null});
	    	
			if(userInfo != null && !isAdministrator){
				Integer errorCount =0 ;
				if(userInfo.getErrorCount() != null){
					errorCount = userInfo.getErrorCount();
				}
				errorCount ++;
				userInfo.setErrorCount(errorCount.toString());
				
				Object object = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "7" , null});
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				if (object != null) {
					SystemParam systemParam = (SystemParam)object;
					if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
						try{
							int count = Integer.parseInt(systemParam.getStrParamValue());
							if(errorCount >= count){
								userInfo.setStrLockState("2");
								singleObjectSaveDao.paramVoidResultExecute(new Object[]{userInfo,null});
								this.setServiceResult(new MessageResult(false, "用户被锁定，请联系管理员"));
							}
							else{
								singleObjectSaveDao.paramVoidResultExecute(new Object[]{userInfo,null});
								this.setServiceResult(new MessageResult(false, "用户名或密码错误，再输入" + (count - errorCount) + "次错误密码后用户将被锁定"));
							}
						}
						catch(Exception ex){
						}
					}
				}

				singleObjectSaveDao.paramVoidResultExecute(new Object[]{userInfo,null});
			}
			
			this.setServiceResult(new MessageResult(false, "用户名或密码错误"));
		}
	}

	public void setLogicHandleBeanIdList(String[] logicHandleBeanIdList) {
		this.logicHandleBeanIdList = logicHandleBeanIdList;
	}

	public void setSelfDataSecurity(boolean selfDataSecurity) {
		this.selfDataSecurity = selfDataSecurity;
	}

	public boolean isSelfDataSecurity() {
		return selfDataSecurity;
	}

	public void setSelfInstDataSecurity(boolean selfInstDataSecurity) {
		this.selfInstDataSecurity = selfInstDataSecurity;
	}

	public boolean isSelfInstDataSecurity() {
		return selfInstDataSecurity;
	}
	
	public void setNotNeedPassword(boolean notNeedPassword) {
		this.notNeedPassword = notNeedPassword;
	}
}
