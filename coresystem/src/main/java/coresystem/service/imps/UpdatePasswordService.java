package coresystem.service.imps;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.ShowUpdatePassword;
import coresystem.dto.UpdatePasswordLog;
import coresystem.dto.UserInfo;
import extend.dto.SystemParam;
import framework.helper.AESSecretKey;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
import freemarker.template.utility.StringUtil;

public class UpdatePasswordService extends BaseVoidDaoResultService{
	
	@SuppressWarnings("unchecked")
	@Override
    public void initSuccessResult() throws Exception {
		
		ShowUpdatePassword showUpdatePassword = (ShowUpdatePassword)RequestManager.getTOject();
		
		if(!showUpdatePassword.getNewPassword1().equals(showUpdatePassword.getNewPassword2())){
			this.setServiceResult(new MessageResult(false,"两次新密码输入不相同"));
		}
		else{
			if(SecurityContext.getInstance().getLoginInfo().isAdministrator()){
				this.setServiceResult(new MessageResult(false,"请在配置文件中修改超级管理员密码"));
			}
			else{
				UserInfo userInfo = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();

				String strPassword = AESSecretKey.DecryString(userInfo.getStrPassword(), null);
				if(!showUpdatePassword.getOldPassword().equals(strPassword)){
					this.setServiceResult(new MessageResult(false,"原密码输入错误"));
				}
				else{
					
					String encryPassword = AESSecretKey.EncryString(showUpdatePassword.getNewPassword1(),null);
					
					String defaultPassword = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("defaultPassword");
					
					if(encryPassword.equals(defaultPassword)){
						this.setServiceResult(new MessageResult(false,"密码不能为初始密码"));
					}
					else{
						
						IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
						Object object = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "4" , null});
						if (object != null) {
							SystemParam systemParam = (SystemParam)object;
							if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
								try{
									int paramCount = Integer.parseInt(systemParam.getStrParamValue());
									
									IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UpdatePasswordLog.class);
									detachedCriteria.add(Restrictions.eq("userInfo", userInfo));
									detachedCriteria.addOrder(Order.desc("actionTime"));
									List<UpdatePasswordLog> objectList = (List<UpdatePasswordLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									
									if(objectList.size() > 0){
										for(int i=0;i<paramCount;i++){
											if(objectList.size()>i)
											{
												if(encryPassword.equals(objectList.get(i).getStrPassword())){
													this.setServiceResult(new MessageResult(false,"密码不能和前" + paramCount + "次密码重复"));
													return;
												}
											}
											
										}
									}
									
								}
								catch(Exception ex){
								}
							}
						}
						
						object = byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "5" , null});
						if (object != null) {
							SystemParam systemParam = (SystemParam)object;
							if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
								try{
									int paramCount = Integer.parseInt(systemParam.getStrParamValue());
									
									IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UpdatePasswordLog.class);
									detachedCriteria.add(Restrictions.eq("userInfo", userInfo));

									String strDate = TypeParse.parseString(new Date(), "yyyy-MM-dd");
									Calendar ca= Calendar.getInstance();
									ca.setTime(TypeParse.parseDate(strDate));
									ca.add(Calendar.DATE, -1);
									Date date=new Date();
									date=ca.getTime();
									//Date date = new Date(TypeParse.parseDate(strDate).getTime()-1*24*60*60*1000);
									String actionTime = TypeParse.parseString(date, "yyyy-MM-dd HH:mm:ss");
									Timestamp timestamp  = TypeParse.parseTimestamp(actionTime);
									
									detachedCriteria.add(Restrictions.ge("actionTime", timestamp));
									detachedCriteria.addOrder(Order.desc("actionTime"));
									List<UpdatePasswordLog> objectList = (List<UpdatePasswordLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});

									for(int i=0;i<objectList.size();i++){
										if(encryPassword.equals(objectList.get(i).getStrPassword())){
											this.setServiceResult(new MessageResult(false,"密码不能和前" + paramCount + "天内密码重复"));
											return;
										}
									}
								}
								catch(Exception ex){
								}
							}
						}

						IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
						UserInfo databaseUserInfo = (UserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserInfo.class.getName(),userInfo.getStrUserCode(),null});

						databaseUserInfo.setStrPassword(encryPassword);
						databaseUserInfo.setUpdatePasswordTime(new Date());

						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{databaseUserInfo,null});
						
						UpdatePasswordLog updatePasswordLog = new UpdatePasswordLog();
						updatePasswordLog.setStrPassword(databaseUserInfo.getStrPassword());
						updatePasswordLog.setUserInfo(databaseUserInfo);
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						updatePasswordLog.setActionTime(simpleDateFormat.format(new Date()));
						
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{updatePasswordLog,null});
						
						userInfo.setStrPassword(encryPassword);
						
						this.setServiceResult(new MessageResult(this.getSuccessMessage()));
					}

				}
			}
		}
		
	}
}
