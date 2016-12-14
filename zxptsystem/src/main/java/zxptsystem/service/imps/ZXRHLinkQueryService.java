package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.UserInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.GRZXGrantUserInfo;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.dto.QYZXGrantUserInfo;
import zxptsystem.helper.downloadThread.ThreadManager;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.MessageResult;

public class ZXRHLinkQueryService extends BaseVoidDaoResultService {

	@Override
	public void initSuccessResult() throws Exception {
		MessageResult messageResult= new MessageResult();
		
		String[] ids =(String[])RequestManager.getIdList();
		if(null == ids){
			messageResult.setSuccess(false);
			messageResult.setMessage("请选择要查询的数据！");
			this.setServiceResult(messageResult);
			return;
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		
		String sessionFactory=SecurityContext.getInstance().getLoginInfo().getSessionFactory();
		SystemParam paramPath = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SystemParam.class.getName(),"all_creditFilePath",sessionFactory});
		if(null==paramPath){
			messageResult = new MessageResult();
			messageResult.setSuccess(false);
			messageResult.setMessage("系统参数里征信报告下载路径没有设置！");
			this.setServiceResult(messageResult);
			return;
		}
		
		String all_creditFilePath = paramPath.getStrParamValue();
		
		String tName =RequestManager.getTName();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SystemParam.class);
		String loginUser = ((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
		
		if(tName.equals("zxptsystem.dto.GRZXCreditReportInfo")){
			detachedCriteria.add(Restrictions.in("strItemCode", new String[]{
					"Intranet_GR_GeneralReport"//内联网个人信用报告
					,"Internet_GR_GeneralReport"//互联网个人信用报告
					}));
		}else{
			detachedCriteria.add(Restrictions.in("strItemCode", new String[]{
					"Internet_QY_GeneralReport"
					,"Intranet_QY_DetailReport"
					,"Intranet_QY_GeneralReport"
					,"Intranet_QY_Online"
					}));
		}		
		
		List<SystemParam> SystemParamList = (List<SystemParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});//要下载报文类型
		HashSet<String> reportTypeMap=new HashSet<String>();
		if(null!=SystemParamList){
			for(SystemParam systemParam:SystemParamList){
				reportTypeMap.add(systemParam.getStrItemCode());
			}
		}	
		String orgCode="";
		String userid="";
		String password="";
		String ip="";
		
		if(tName.equals("zxptsystem.dto.GRZXCreditReportInfo")){
			GRZXGrantUserInfo info = (GRZXGrantUserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{GRZXGrantUserInfo.class.getName(),loginUser,sessionFactory});
			
			if(null==info){
				messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.setMessage("没有设置个人人行征信中心的ip，用户名，密码等信息！");
				this.setServiceResult(messageResult);
				return;
			}			
			SystemParam validay = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
					SystemParam.class.getName(),
					"gr_reportValidDay",sessionFactory});
			int vDay=0;
			if(null!=validay && validay.getStrEnable().equals("1")){
				try{
					vDay=Integer.parseInt(validay.getStrParamValue());
				}catch(Exception e){
					messageResult = new MessageResult();
					messageResult.setSuccess(false);
					messageResult.setMessage("个人报告缓存有效期设置不对！");
					this.setServiceResult(messageResult);
					return;
				}
			}else{
				messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.setMessage("没有设置个人报告缓存有效期！");
				this.setServiceResult(messageResult);
				return;
			}
			//orgCode=info.getStrInstCode();
			userid=info.getUserId();
			password=info.getUserPwd();
			ip=info.getStrPBOCUrl();
			
			for(String id:ids){
				GRZXCreditReportInfo obj = (GRZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,sessionFactory});
				if(null!=obj && obj.getStrVerifyType().equals("2")){
					try {
						List<Object> Parameter=new ArrayList<Object>();
						Parameter.add(all_creditFilePath);//0
						Parameter.add(obj);//1
						Parameter.add(reportTypeMap);//2
						Parameter.add(userid);//3
						Parameter.add(password);//4
						Parameter.add(ip);//5
						Parameter.add(sessionFactory);//6
						Parameter.add(SecurityContext.getInstance().getLoginInfo().getTag());//7
						Parameter.add(vDay);//8
						
						System.out.println("参数设置完毕准备启动线程");
						ThreadManager.getInstance().addWaitQueue(Parameter);
						System.out.println("手动业务添加业务，当前管理器状态："+ThreadManager.getInstance().isRunning());	
						obj.setStrQueryStatus("21");
					} catch (Exception e) {							
						obj.setStrQueryStatus("41");
						e.printStackTrace();									
					}
					singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj, sessionFactory});
					}
				}
		}else{
			SystemParam pOfflineDownLoad = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
					SystemParam.class.getName(),"Intranet_QY_GeneralReport_OffLineDownLoad",sessionFactory});
			boolean isOfflineDownLoad=false;
			if(null!=pOfflineDownLoad && pOfflineDownLoad.getStrEnable().equals("1")){
				isOfflineDownLoad=true;
			}
			QYZXGrantUserInfo info = (QYZXGrantUserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{QYZXGrantUserInfo.class.getName(),loginUser,sessionFactory});
			
			if(null==info){
				messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.setMessage("没有设置企业人行征信中心的ip，用户名，密码，机构代码等信息！");
				this.setServiceResult(messageResult);
				return;
			}
			orgCode=info.getStrInstCode();
			userid=info.getUserId();
			password=info.getUserPwd();
			ip=info.getStrPBOCUrl();
			for(String id:ids){
				QYZXCreditReportInfo obj = (QYZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,null});
				
				if(null!=obj){				
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
				}
				singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj, sessionFactory});			
			}
		}
		
 		super.initSuccessResult();
	}
}
