 package zxptsystem.service.procese;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.UserInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.GRZXGrantUserInfo;
import zxptsystem.dto.GRZXQueryLog;
import zxptsystem.dto.GrzxReportParseLog;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.dto.QYZXGrantUserInfo;
import zxptsystem.helper.HTMLParseAndJSONSendTool.OtherTool;
import zxptsystem.helper.downloadThread.ThreadManager;
import zxptsystem.service.imps.CreditReportLogHelper;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.MessageResult;

public class CreateCreditReportFolderProcese implements IProcese{

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		MessageResult messageResult;
		
		if(serviceResult instanceof MessageResult){
			messageResult = (MessageResult)serviceResult;
			if(!messageResult.isSuccess()){
				return messageResult;
			}
		}
		
		String sessionFactory=SecurityContext.getInstance().getLoginInfo().getSessionFactory();
		String[] ids = (String[])RequestManager.getIdList();
		String tName = RequestManager.getTName();
		Object tObj = Class.forName(tName).newInstance();
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SystemParam.class);		
		detachedCriteria.add(Restrictions.eq("strEnable", "1"));
		String orgCode="";
		String userid="";
		String password="";
		String ip="";
		int vDay=1;
		if(tObj instanceof QYZXCreditReportInfo){
			detachedCriteria.add(Restrictions.in("strItemCode", new String[]{
					"Internet_QY_GeneralReport"//互联网企业信用报告
					,"Intranet_QY_DetailReport"//内联网企业明细信息
					,"Intranet_QY_GeneralReport"//内联网企业信用报告
					,"Intranet_QY_Online"//企业在线报告
					}));
			String loginUser = ((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
			QYZXGrantUserInfo info = (QYZXGrantUserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{QYZXGrantUserInfo.class.getName(),loginUser,sessionFactory});
			
			if(null==info){
				messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.setMessage("没有设置企业人行征信中心的ip，用户名，密码，机构代码等信息！");
				return messageResult;
			}
			orgCode=info.getStrInstCode();
			userid=info.getUserId();
			password=info.getUserPwd();
			ip=info.getStrPBOCUrl();	
			
		}else{
			detachedCriteria.add(Restrictions.in("strItemCode", new String[]{
					"Intranet_GR_GeneralReport"//内联网个人信用报告
					,"Internet_GR_GeneralReport"//互联网个人信用报告
					}));
			
			String loginUser = ((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
			GRZXGrantUserInfo info = (GRZXGrantUserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{GRZXGrantUserInfo.class.getName(),loginUser,sessionFactory});
			
			if(null==info){
				messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.setMessage("没有设置个人人行征信中心的ip，用户名，密码等信息！");
				return messageResult;
			}			
			SystemParam validay = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
					SystemParam.class.getName(),
					"gr_reportValidDay",sessionFactory});
			if(null!=validay && validay.getStrEnable().equals("1")){
				try{
					vDay=Integer.parseInt(validay.getStrParamValue());
				}catch(Exception e){
					messageResult = new MessageResult();
					messageResult.setSuccess(false);
					messageResult.setMessage("个人报告缓存有效期设置不对！");
					return messageResult;
				}
			}else{
				messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.setMessage("没有设置个人报告缓存有效期！");
				return messageResult;
			}
			//orgCode=info.getStrInstCode();
			userid=info.getUserId();
			password=info.getUserPwd();
			ip=info.getStrPBOCUrl();
		}
		
		List<SystemParam> SystemParamList = (List<SystemParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});//要下载报文类型
		HashSet<String> reportTypeMap=new HashSet<String>();
		if(null!=SystemParamList){
			for(SystemParam systemParam:SystemParamList){
				reportTypeMap.add(systemParam.getStrItemCode());
			}
		}
				
		SystemParam paramPath = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SystemParam.class.getName(),"all_creditFilePath",sessionFactory});
		if(null==paramPath){
			messageResult = new MessageResult();
			messageResult.setSuccess(false);
			messageResult.setMessage("系统参数里征信报告下载路径没有设置！");
			return messageResult;
		}
		
		String all_creditFilePath = paramPath.getStrParamValue();			
		
		SystemParam pOfflineDownLoad = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
				SystemParam.class.getName(),"Intranet_QY_GeneralReport_OffLineDownLoad",sessionFactory});
		boolean isOfflineDownLoad=false;
		if(null!=pOfflineDownLoad && pOfflineDownLoad.getStrEnable().equals("1")){
			isOfflineDownLoad=true;
		}
		
		for(String id :ids){		
			if(tObj instanceof QYZXCreditReportInfo){
				QYZXCreditReportInfo obj = (QYZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,null});
				
				if(null!=obj){
					detachedCriteria = DetachedCriteria.forClass(QYZXCreditReportInfo.class);		
					detachedCriteria.add(Restrictions.eq("timeCreateDate", obj.getTimeCreateDate()));
					detachedCriteria.add(Restrictions.eq("strCustomerID", obj.getStrCustomerID()));
					detachedCriteria.add(Restrictions.ne("strQueryStatus", "11"));
					
					//List<QYZXCreditReportInfo> qYZXCreditReportInfos = (List<QYZXCreditReportInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});//
					String tmpStatus="11";
					if(reportTypeMap.contains("Intranet_QY_DetailReport") && reportTypeMap.contains("Intranet_QY_GeneralReport")){
						tmpStatus="22";
					}else if(reportTypeMap.contains("Intranet_QY_DetailReport")){
						tmpStatus="21";
					}else if(reportTypeMap.contains("Intranet_QY_GeneralReport")){
						tmpStatus="12";
					}else{
						tmpStatus="33";
					}					
					try {
						List<Object> Parameter=new ArrayList<Object>();
						Parameter.add(all_creditFilePath);
						Parameter.add(obj);//申请时间 作为下载参数中的截止时间
						Parameter.add(reportTypeMap);
						Parameter.add(orgCode);
						Parameter.add(userid);
						Parameter.add(password);
						Parameter.add(ip);
						Parameter.add(sessionFactory);
						Parameter.add(SecurityContext.getInstance().getLoginInfo().getTag());
						Parameter.add(Boolean.toString(isOfflineDownLoad));
						
						System.out.println("参数设置完毕准备启动线程");
						ThreadManager.getInstance().addWaitQueue(Parameter);
						System.out.println("手动业务添加业务，当前管理器状态："+ThreadManager.getInstance().isRunning());		
						obj.setStrQueryStatus(tmpStatus);
					} catch (Exception e1) {							
						obj.setStrQueryStatus("44");
						e1.printStackTrace();									
					}
//					if(qYZXCreditReportInfos.size()==0){
//						
//					}					
				}
				singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj, sessionFactory});
			}else{
				GRZXCreditReportInfo obj = (GRZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,sessionFactory});
				if(null!=obj){
					try {
						String grzxQueryAcount = obj.getStrCustomerID().getGrzxQueryAcount() ;
						String nowDate = TypeParse.parseString(new java.util.Date(), "yyyyMMdd") ;
						List<Object> Parameter=new ArrayList<Object>();
						
						DetachedCriteria dc = DetachedCriteria.forClass(SystemParam.class);
						dc = DetachedCriteria.forClass(SystemParam.class);
						dc.add(Restrictions.eq("strItemCode", "grzxQueryCount"));
						List<SystemParam> paramList = (List<SystemParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
						if(paramList != null && !paramList.isEmpty() && paramList.get(0) != null && paramList.get(0).getStrEnable().equals("1")){
							if(grzxQueryAcount == null || grzxQueryAcount.equals("") || grzxQueryAcount.indexOf(nowDate) == -1){
								grzxQueryAcount = nowDate + paramList.get(0).getStrParamValue() ;
							}
						}else{
							messageResult = new MessageResult();
							messageResult.setSuccess(false);
							messageResult.setMessage("“个人征信重复查询次数”系统参数未配置或未启用");
							return messageResult;
						}
						if(grzxQueryAcount.substring(8).equals("0")){
							obj.setStrQueryStatus("11") ;
							obj.setStrSubmitStatus("1") ;
							obj.setStrVerifyType("1") ;
							messageResult = new MessageResult();
							messageResult.setSuccess(false);
							messageResult.setMessage("重复查询次数已达上限，查询未执行");
							
							Map<String, String> errorMap = new HashMap<String, String>();
							Map<String, Object> parseData = new HashMap<String, Object>();
							errorMap.put("code", "106");
							errorMap.put("error", "当天重复查询次数达到上限");
							parseData.put("errorMap", errorMap) ;
							GRZXQueryLog log = new GRZXQueryLog();
							String logId=CreditReportLogHelper.LogGrInfo(obj.getId(),"Intranet_GR_GeneralReport",sessionFactory,(UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
							log.setId(logId);
							log.setGrZXCreditReportInfo(obj) ;
							GrzxReportParseLog parseLog = GrzxReportParseLog.class.newInstance() ;
							parseLog.setGrzxQueryLog(log) ;
							parseLog.setExceptionReason("当天重复查询次数达到上限") ;
							OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
							return messageResult;
						}
						
						Parameter.add(all_creditFilePath);
						Parameter.add(obj);
						Parameter.add(reportTypeMap);
						Parameter.add(userid);
						Parameter.add(password);
						Parameter.add(ip);
						Parameter.add(sessionFactory);
						Parameter.add(SecurityContext.getInstance().getLoginInfo().getTag());
						Parameter.add(vDay);
						Parameter.add(grzxQueryAcount);
						
						System.out.println("参数设置完毕准备启动线程");
						ThreadManager.getInstance().addWaitQueue(Parameter);
						System.out.println("手动业务添加业务，当前管理器状态："+ThreadManager.getInstance().isRunning());	
						ThreadManager.getInstance().setStartTime(new Date(System.currentTimeMillis()).getTime()) ;
						obj.setStrQueryStatus("21");
					} catch (Exception e) {							
						obj.setStrQueryStatus("41");
						e.printStackTrace();									
					}
					singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj, sessionFactory});
					}
				}				
			}
		return serviceResult;
	}
}
