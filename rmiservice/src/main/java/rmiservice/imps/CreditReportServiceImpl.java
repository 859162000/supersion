package rmiservice.imps;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import rmiservice.interfaces.ICreditReportService;
import com.opensymphony.xwork2.ActionContext;
import coresystem.dto.UserInfo;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.helper.GR.EnquiryParameter;
import zxptsystem.helper.GR.ReturnResult;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.security.LoginInfo;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;

public class CreditReportServiceImpl extends UnicastRemoteObject implements ICreditReportService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreditReportServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public Object getReport(String id, String valStr) throws Exception {
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		ReturnResult returnResult = new ReturnResult();
		
		GRZXCreditReportInfo info = (GRZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
				GRZXCreditReportInfo.class.getName()
				,id
				,null});
		
		if(null==info){
			returnResult.setMessage("给定的ID没有查到具体的数据");
			returnResult.setSuccess(false);
			return returnResult;
		}
		String status = info.getStrQueryStatus();
		if(status.startsWith("1")){
			returnResult.setEnquiryType("01");
			returnResult.setMessage("未查询");
			returnResult.setSuccess(false);
		}
		if(status.startsWith("2")){
			returnResult.setEnquiryType("02");
			returnResult.setMessage("正在查询");
			returnResult.setSuccess(false);
		}
		if(status.startsWith("4")){
			returnResult.setEnquiryType("02");
			returnResult.setMessage("查询失败");
			returnResult.setSuccess(false);
		}
		if(!returnResult.isSuccess()){
			return returnResult;
		}
		String baseUrl="";
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SystemParam.class);
		detachedCriteria.add(Restrictions.eq("strEnable", "1"));
		detachedCriteria.add(Restrictions.eq("strItemCode", "Intranet_GR_GeneralReport"));
		List<SystemParam> SystemParamList = (List<SystemParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(null!=SystemParamList && SystemParamList.size()>0){
			baseUrl=SystemParamList.get(0).getStrParamValue();
		}
		else{
			returnResult.setMessage("参数管理没有开启个人征信报告的离线查询！");
			returnResult.setSuccess(false);
		}
		if(returnResult.isSuccess()){
			returnResult.setEnquiryType("03");
			String url=baseUrl+"/GR/"
				+info.getStrCustomerID().getStrCustomerID()
				+TypeParse.parseString(info.getDtEndDate(),"yyyyMMdd")
				+"/"+"Intranet_GeneralReport"+"/"+"Intranet_GeneralReport.html";
			returnResult.setMessage(url);
		}
		return returnResult;
	}

	@Override
	public MessageResult uploadCustomerInfo(EnquiryParameter object,
			String valStr) throws Exception {
		MessageResult messageResult = new MessageResult();
		
		try{
			ApplicationManager.getActionExcuteLog().info("开始处理"+object.getEnquiryNO());
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						
			UserInfo ui = (UserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
					UserInfo.class.getName()
					,object.getStrEnquiryUser()
					,null});
			if(null==ui){
				ApplicationManager.getActionExcuteLog().error(object.getEnquiryNO()+","+object.getStrEnquiryUser()+"用户不存在!");
				messageResult.setSuccess(false);
				messageResult.setMessage("操作用户不存在！");
				return messageResult;
			}
						
			EIS_PERCustomernBasicInfo ci = (EIS_PERCustomernBasicInfo)singleObjectFindByIdDao.paramObjectResultExecute(
					new Object[]{
							EIS_PERCustomernBasicInfo.class.getName()
					,object.getStrID_NO()
					,null});
			
			if(null==ci){
				ApplicationManager.getActionExcuteLog().info("创建客户信息:"+object.getStrID_NO());
				ci = new EIS_PERCustomernBasicInfo();
				ci.setStrCustomerID(object.getStrID_NO());					
				ci.setStrCustomerName(object.getStrIND_Name());
				ci.setStrCertType(object.getStrID_Type());
				ci.setStrCertNo(object.getStrID_NO());
				ci.setStrUserID(ui);
				ci.setStrInnerInstCode(ui.getInstInfo());
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{ci,null});
				ApplicationManager.getActionExcuteLog().info("创建客户信息:"+object.getStrID_NO()+"成功");
			}			
			
			GRZXCreditReportInfo info = new GRZXCreditReportInfo();
			info.setStrCustomerID(ci);
			info.setId(object.getEnquiryNO());//查询ID
			info.setStrQueryCause(object.getStrEnquiryReason());//查询原因
			info.setStrCreditType(object.getStrEnquiryType());//查询类型
			info.setStrCreditReportType(object.getReportFormat());//信用报告版式
			//info.setStrQueryCause(object.getStrEnquiryType());//查询截止日期
			info.setOperationUser(ui);
			info.setInstInfo(ui.getInstInfo());
			info.setCategoryCode("1");
			info.setStrDocName("1");
			info.setStrQueryStatus("11");
			
			//SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");			
			//info.setEndDate(new Timestamp(sdf.parse(object.getStrEndDate()).getTime()));
			info.setStrSubmitStatus("2");
			info.setStrVerifyType("1");
			Date cDate= new Date(System.currentTimeMillis());
			info.setTimeCreateDate(new Timestamp(cDate.getTime()));
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{info,null});
			
			//mock verify
			String serviceId="gRZXUpdateFieldService";
			
			init();
			
			RequestManager.setTName(GRZXCreditReportInfo.class.getName());
			RequestManager.setTOject(info);
			RequestManager.setId(info.getId());
			RequestManager.setIdList(new String[]{info.getId()});
			framework.show.ShowContext.getInstance();
			
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setTag(ui);
			SessionManager.setLoginInfo(loginInfo);
			
			BaseService baseService = (BaseService)FrameworkFactory.CreateBean(serviceId);
			
			messageResult = (MessageResult)baseService.objectResultExecute();
		}catch(Exception ex){
			messageResult.setSuccess(false);
			ApplicationManager.getActionExcuteLog().error("异常:"+ex.getMessage());
			messageResult.setMessage("未知异常，查看日志！");
		}
		ApplicationManager.getActionExcuteLog().info("打印返回结果:"+object.getEnquiryNO()+messageResult.isSuccess());
		return messageResult;
	}

	private void init() {
		Map<String,Object> contextMap=new HashMap<String,Object>();
		Map<String,Object> requestMap=new HashMap<String,Object>();	
		Map<String,Object> sessionMap=new HashMap<String,Object>();			
		Map<String,Object> applicationMap=new HashMap<String,Object>();			
		contextMap.put("request", requestMap);
		ActionContext actionContext=new ActionContext(contextMap);
		actionContext.setSession(sessionMap);
		actionContext.setApplication(applicationMap);
		ServletActionContext.setContext(actionContext);
	}
}