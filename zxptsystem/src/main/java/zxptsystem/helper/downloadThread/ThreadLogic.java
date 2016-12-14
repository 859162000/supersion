package zxptsystem.helper.downloadThread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.MessageResult;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.GRZXQueryLog;
import zxptsystem.dto.GrzxReportParseLog;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.dto.SpareRHAccountInfo;
import zxptsystem.helper.HTMLParseAndJSONSendTool.GRZXReportSendTool;
import zxptsystem.helper.HTMLParseAndJSONSendTool.OtherTool;
import zxptsystem.helper.download.OffLineGRGeneralReportQuery;
import zxptsystem.helper.download.OffLineGeneralReportQuery;
import zxptsystem.helper.download.OffLineQuery;
import zxptsystem.service.imps.CreditReportLogHelper;

public class ThreadLogic implements Runnable{
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private Object object;
	public void setObject(Object object) {
		this.object = object;
	}
	public Object getObject() {
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		List<Object> Parameter=(List<Object>)object;
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		if(Parameter.get(1) instanceof QYZXCreditReportInfo){
			doQYZX(Parameter,singleObjectUpdateDao);
		}else if(Parameter.get(1) instanceof GRZXCreditReportInfo){
			try {
				doGRZX(Parameter,singleObjectUpdateDao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}			
	}
	
	private void doGRZX(List<Object> Parameter,IParamVoidResultExecute singleObjectUpdateDao) throws Exception{
		
		String all_creditFilePath = Parameter.get(0).toString();
		GRZXCreditReportInfo obj=(GRZXCreditReportInfo)Parameter.get(1);
		HashSet<String> reportTypeMap=(HashSet<String>)Parameter.get(2);
		String userid=Parameter.get(3).toString();
		String password=Parameter.get(4).toString();
		String ip=Parameter.get(5).toString();
		String sessionFactory=null;
		MessageResult messageResult  = null ;
		Map<String, Object> parseData = new HashMap<String, Object>();
		Map<String, String> errorMap = new HashMap<String, String>();
		
		if(null!=Parameter.get(6)){
			sessionFactory=Parameter.get(6).toString();
		}		
		UserInfo user = (UserInfo)Parameter.get(7);		
		int vDay = Integer.parseInt(Parameter.get(8).toString());
		String logId="";
		String grzxQueryAcount = Parameter.get(9).toString();
		char[] cs = obj.getStrQueryStatus().toCharArray();	
		if(reportTypeMap.contains("Intranet_GR_GeneralReport")){
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.add(Calendar.DATE, -vDay);
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GRZXCreditReportInfo.class);		
			detachedCriteria.add(Restrictions.ge("dtEndDate",new java.sql.Date(c.getTimeInMillis())));
			detachedCriteria.add(Restrictions.eq("strCustomerID", obj.getStrCustomerID()));
			detachedCriteria.add(Restrictions.like("strQueryStatus", "3",MatchMode.START));
			detachedCriteria.add(Restrictions.eq("strInfoSource", "0"));
			detachedCriteria.addOrder(Order.desc("dtEndDate"));
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			logId=CreditReportLogHelper.LogGrInfo(obj.getId(),"Intranet_GR_GeneralReport",sessionFactory,user);
			try {
				List<GRZXCreditReportInfo> gRZXCreditReportInfos = (List<GRZXCreditReportInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});//
				if(gRZXCreditReportInfos.size()>0){//有缓存
					cs[0]='3';
					obj.setStrSubmitStatus("2");
					obj.setStrVerifyType("2");
					obj.setDtEndDate(gRZXCreditReportInfos.get(0).getDtEndDate());
					
					CreditReportLogHelper.LogGrDetailInfo(logId, userid, password, ip, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "成功", null, "缓存获取", sessionFactory);
					obj.setStrInfoSource("1");
					ThreadManager.getInstance().setQueryTime(new Date().getTime()) ;
					// 新增一条解析日志，但不对数据进行解析
					GRZXReportSendTool.getInstance().grzxReportSendOnLocalQuery(logId, gRZXCreditReportInfos.get(0));
					ThreadManager.getInstance().setEndTime(new Date().getTime()) ;
				}else{
					OffLineGRGeneralReportQuery offLineGRGeneralReportQuery=new OffLineGRGeneralReportQuery();	
					messageResult  = offLineGRGeneralReportQuery.ImplementQuery(obj, userid, password, ip,sessionFactory,user,logId,grzxQueryAcount);
					ThreadManager.getInstance().setQueryTime(new Date().getTime()) ;
					
					if(!messageResult.isSuccess()){						
						detachedCriteria = DetachedCriteria.forClass(SpareRHAccountInfo.class);		
						detachedCriteria.add(Restrictions.eq("status","0"));
						List<SpareRHAccountInfo> spareUsers = (List<SpareRHAccountInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});

						for(SpareRHAccountInfo spareRHAccountInfo: spareUsers){
							userid = spareRHAccountInfo.getUserId();
							password=spareRHAccountInfo.getPwd();
							messageResult  = offLineGRGeneralReportQuery.ImplementQuery(
									obj,userid,password,ip,sessionFactory,user,logId,grzxQueryAcount);
							ThreadManager.getInstance().setQueryTime(new Date().getTime()) ;
							if(messageResult.isSuccess()){
								break;
							}
						}
					}

					if(messageResult.isSuccess()){
						obj.setDtEndDate(new Timestamp(System.currentTimeMillis()));
						
						String downloadPath=all_creditFilePath
							+File.separator+"GR"
							+File.separator+obj.getStrCustomerID().getStrCustomerID()+df.format(obj.getDtEndDate())
							+File.separator+"Intranet_GeneralReport";
						File f = new File(downloadPath);
						f.mkdirs();
						
						File pageFile=new File(downloadPath+File.separator+"Intranet_GeneralReport.html");					
						pageFile.createNewFile();
						FileOutputStream txtfiledxx=new FileOutputStream(pageFile);
						Writer out = new OutputStreamWriter(txtfiledxx, "GBK");
						out.write(messageResult.getMessage());
						out.close();
						txtfiledxx.close();
						
						cs[0]='3';
						obj.setStrInfoSource("0");
						
						// 对HTML文件进行解析并对其做其他相关的处理
						GRZXReportSendTool.getInstance().grzxReportParseOrSendOnPeopleBakQuery(downloadPath+File.separator+"Intranet_GeneralReport.html", logId);
					}else{
						cs[0]='4';
						obj.setStrSubmitStatus("1");
						obj.setStrVerifyType("1");
						
						errorMap.put("code", "104");
						errorMap.put("error", "人行查询异常");
						parseData.put("errorMap", errorMap) ;
						GRZXQueryLog log = new GRZXQueryLog();
						log.setId(logId);
						log.setGrZXCreditReportInfo(obj) ;
						GrzxReportParseLog parseLog = GrzxReportParseLog.class.newInstance() ;
						parseLog.setGrzxQueryLog(log) ;
						parseLog.setExceptionReason("人行查询异常") ;
						OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
					}
					ThreadManager.getInstance().setEndTime(new Date().getTime()) ;
				}
				long sendTime = (ThreadManager.getInstance().getEndTime() - ThreadManager.getInstance().getStartTime())/1000 ;
				long queryTime = (ThreadManager.getInstance().getQueryTime() - ThreadManager.getInstance().getStartTime())/1000 ;
				ApplicationManager.getActionExcuteLog().info(logId + ",该进件总用时" + sendTime + "秒，其中查询用时" + queryTime + "秒" +",线程ID："+Thread.currentThread().getId());
			} catch(java.net.ConnectException ce){
				CreditReportLogHelper.LogGrDetailInfo(logId,userid,password,ip,new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()),"失败","人行链接异常ConnectException","",sessionFactory);
				ApplicationManager.getActionExceptionLog().error("人行链接异常",ce);
				cs[0]='4';
				obj.setStrSubmitStatus("1");
				obj.setStrVerifyType("1");
				
				errorMap.put("code", "104");
				errorMap.put("error", "人行查询异常");
				parseData.put("errorMap", errorMap) ;
				GRZXQueryLog log = new GRZXQueryLog();
				log.setId(logId);
				log.setGrZXCreditReportInfo(obj) ;
				GrzxReportParseLog parseLog = GrzxReportParseLog.class.newInstance() ;
				parseLog.setGrzxQueryLog(log) ;
				parseLog.setExceptionReason("人行查询异常") ;
				OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
			}catch (Exception e) {
				CreditReportLogHelper.LogGrDetailInfo(logId,userid,password,ip,new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()),"失败",e.getMessage(),"",sessionFactory);
				e.printStackTrace();
				cs[0]='4';
				obj.setStrSubmitStatus("1");
				obj.setStrVerifyType("1");
			}
		}			
			
		try {
			obj.setStrQueryStatus(new String(cs));
			CreditReportLogHelper.updateGrLogById(logId, sessionFactory);
			singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj, sessionFactory});
			//等待时间参数化				
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void doQYZX(List<Object> Parameter,IParamVoidResultExecute singleObjectUpdateDao){
		String all_creditFilePath = Parameter.get(0).toString();
		QYZXCreditReportInfo obj=(QYZXCreditReportInfo)Parameter.get(1);
		HashSet<String> reportTypeMap=(HashSet<String>)Parameter.get(2);
		String orgCode=Parameter.get(3).toString();
		String userid=Parameter.get(4).toString();
		String password=Parameter.get(5).toString();
		String ip=Parameter.get(6).toString();
		String sessionFactory=null;
		if(null!=Parameter.get(7)){
			sessionFactory=Parameter.get(7).toString();
		}		
		UserInfo user = (UserInfo)Parameter.get(8);	
		boolean isOfflineDownLoad=false;
		isOfflineDownLoad=Boolean.parseBoolean(Parameter.get(9).toString());
		
		char[] cs = obj.getStrQueryStatus().toCharArray();
				
		if(reportTypeMap.contains("Intranet_QY_GeneralReport")){
			String downloadPath=all_creditFilePath
				+File.separator+"QY"
				+File.separator+obj.getStrCustomerID().getStrCustomerID()+df.format(obj.getTimeCreateDate())
				+File.separator+"Intranet_GeneralReport";
			try {
				OffLineGeneralReportQuery offLineGRQuery= new OffLineGeneralReportQuery();
				
				MessageResult messageResult = offLineGRQuery.OffLineQuery(obj,orgCode,userid,password,ip,sessionFactory,user,isOfflineDownLoad);
				if(messageResult.isSuccess()){
					cs[1]='3';					
					File folder = new File(downloadPath);
					if(!folder.exists()){
						folder.mkdirs();
					}
					
					String fName = "Intranet_GeneralReport.html";
					File file = new File(downloadPath+File.separator+fName);
					OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "GBK");
					BufferedWriter writer = new BufferedWriter(write);
					writer.write(messageResult.getMessage());
					writer.close();
					
				}else{
					cs[1]='4';
				}
			} catch (Exception e) {
				e.printStackTrace();
				cs[1]='4';
			}
		}
		if(cs[1]!='4'){
			if(reportTypeMap.contains("Intranet_QY_DetailReport")){
				String downloadPath=all_creditFilePath
					+File.separator+"QY"
					+File.separator+obj.getStrCustomerID().getStrCustomerID()+df.format(obj.getTimeCreateDate())
					+File.separator+"Intranet_DetailReport";
				try {		
					cs[0]='3';
					OffLineQuery offLineQuery=new OffLineQuery();
					offLineQuery.OffLineQuery(downloadPath,obj,orgCode,userid,password,ip,sessionFactory,user);
				} 
				catch (Exception ex) {
					ex.printStackTrace();
					cs[0]='4';				
				}			
			}
		}else{
			cs[0]='4';
		}			
			
		try {
			obj.setStrQueryStatus( new String(cs));
			singleObjectUpdateDao.paramVoidResultExecute(new Object[] {obj, sessionFactory});
			//等待时间参数化				
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
