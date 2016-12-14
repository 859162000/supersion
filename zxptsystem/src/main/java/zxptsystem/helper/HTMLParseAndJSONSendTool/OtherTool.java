package zxptsystem.helper.HTMLParseAndJSONSendTool;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.GRZXQueryLog;
import zxptsystem.dto.GR_A1;
import zxptsystem.dto.GR_A2;
import zxptsystem.dto.GrzxReportParseLog;
import zxptsystem.service.interfaces.IGRZXSendInnerInterface;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.MessageResult;

public class OtherTool {
	private static OtherTool otherTool;
	
	synchronized public static OtherTool getInstance() {
		if(otherTool == null) {
			otherTool = new OtherTool();
		}
		return otherTool;
	}
	
	/**
	 * 
	 * @description <p>当本地查询时，新增解析日志，但并不做其他操作</P>
	 * @createTime 2016-9-28 下午03:04:51
	 * @updateTime 2016-9-28 下午03:04:51
	 * @author Liutao
	 * @updater Liutao
	 */
	public GrzxReportParseLog addParseLogOnLocalQuery(String queryLogId, GRZXCreditReportInfo gRZXCreditReportInfo) throws Exception {
		GrzxReportParseLog parseLog = null;
		if(gRZXCreditReportInfo != null && !StringUtils.isBlank(queryLogId)) {
			DetachedCriteria dc = DetachedCriteria.forClass(GRZXQueryLog.class);
			dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(GRZXQueryLog.class).getName(), queryLogId));
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<GRZXQueryLog> queryLogs = (List<GRZXQueryLog>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
			if(queryLogs != null && !queryLogs.isEmpty()) {
				// 获取当前的额日志
				GRZXQueryLog curQueryLog = queryLogs.get(0);
				// 判断当前查询日志是否为空
				if(curQueryLog != null) {
					// 获取授权客户信息
					EIS_PERCustomernBasicInfo cumster = gRZXCreditReportInfo.getStrCustomerID();
					// 判断授权客户是否为空
					if(cumster != null) {
						dc = DetachedCriteria.forClass(GRZXCreditReportInfo.class);
						dc.add(Restrictions.eq("strCustomerID", cumster));
						
						List<GRZXCreditReportInfo> queryInfoList = (List<GRZXCreditReportInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
						
						// 判断查询信息是否为空
						if(queryInfoList != null && !queryInfoList.isEmpty()) {
							dc = DetachedCriteria.forClass(GRZXQueryLog.class);
							dc.addOrder(Order.desc("tmExecTime"));
							dc.add(Restrictions.in("grZXCreditReportInfo", queryInfoList));
							queryLogs = (List<GRZXQueryLog>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
							
							// 获取前一次查询日志
							GRZXQueryLog preQueryLog = null;
							if(queryLogs != null && !queryLogs.isEmpty()) {
								for(GRZXQueryLog log : queryLogs) {
									if(log != null && !log.getId().equals(curQueryLog.getId())) {
										preQueryLog = log;
										break;
									}
								}
							}
							
							GrzxReportParseLog preParseLog = null;// 前一次解析日志
							if(preQueryLog != null) {
								// 获取前一次的解析日志
								dc = DetachedCriteria.forClass(GrzxReportParseLog.class);
								dc.add(Restrictions.eq("grzxQueryLog", preQueryLog));
								
								List<GrzxReportParseLog> parseLogs = (List<GrzxReportParseLog>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
								if(parseLogs != null && !parseLogs.isEmpty()) {
									preParseLog = parseLogs.get(0);
								}
							}
							
							if(preParseLog != null) {
								GrzxReportParseLog newParseLog = new GrzxReportParseLog();
								ReflectOperation.CopyColumnFieldValue(preParseLog, newParseLog);
								newParseLog.setAutoId("");
								newParseLog.setSendFlag("1");// 设置为未推送
								newParseLog.setParseFlag("1");// 设置为已解析
								newParseLog.setExceptionReason("");// 清空原有的异常（或者错误）信息
								curQueryLog.setIntQueryCount(1) ;
								curQueryLog.setStrQueryStatus("成功") ;
								newParseLog.setGrzxQueryLog(curQueryLog);
								
								IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
								singleObjectSaveDao.paramVoidResultExecute(new Object[] {newParseLog, null});
								parseLog = newParseLog;
							}
							
						}
					}// 授权客户判断结束
					
				}// 当前查询日志判断结束
				
			}// 当前查询日志结果判断
			
		}//传入参数判断
		return parseLog;
	}
	
	/**
	 * 
	 * @description <p>获取JSON文件的路径</P>
	 * @createTime 2016-9-28 下午05:10:51
	 * @updateTime 2016-9-28 下午05:10:51
	 * @author Liutao
	 * @updater Liutao
	 */
	public String getJsonFilePath(String JSONFileStoreDir, File htmlFile) {
		String jsonFilePath = null;
		if(!StringUtils.isBlank(JSONFileStoreDir) && htmlFile != null) {
			String subStr = htmlFile.getPath().substring(htmlFile.getPath().indexOf(File.separator+"GR"+File.separator)).replace("html", "json");
			if(JSONFileStoreDir.endsWith(File.separator)) {
				JSONFileStoreDir = JSONFileStoreDir.substring(0, JSONFileStoreDir.length()-1);
			}
				
			jsonFilePath = JSONFileStoreDir+subStr;
		}
		return jsonFilePath;
	}
	
	/**
	 * 
	 * @description <p>JDBC获取数据库链接</P>
	 * @createTime 2016-10-8 上午10:48:43
	 * @updateTime 2016-10-8 上午10:48:43
	 * @author Liutao
	 * @updater Liutao
	 */
	public Connection getJDBCConnection(String dbType, String dbUrl, String userName, String pwd) {
		Connection conn = null;
		try {
			if("oracle".equalsIgnoreCase(dbType)) {
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			}
			
			if("sqlserver".equalsIgnoreCase(dbType)) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			}
			
			if("mysql".equalsIgnoreCase(dbType)) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			}

			if("DB2".equalsIgnoreCase(dbType)) {
				Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			}

			conn = DriverManager.getConnection(dbUrl, userName, pwd);
		} catch (Exception e) {
			ApplicationManager.getActionExceptionLog().error("获取数据库链接异常", e);
		}
		return conn;
	}
	
	/**
	 * 
	 * @description <p>将解析之后的数据持久化到数据库中</P>
	 * @createTime 2016-9-19 下午06:11:53
	 * @updateTime 2016-9-19 下午06:11:53
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	public void saveDataToDB(Map<String, Object> parsedData) throws Exception {
		if(parsedData != null && !parsedData.isEmpty()) {
			Map<String, List<Object>> dtoDataMap = translateJsonToDTO(parsedData);
			if(dtoDataMap != null && !dtoDataMap.isEmpty()) {
				GR_A1 a1 = (GR_A1) dtoDataMap.get("A1").get(0);
				IParamVoidResultExecute saveAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				if(a1 != null) {
					// 对解析之后的数据进行数据持久化
					String reportNo = a1.getReportNo();
					DetachedCriteria dc = DetachedCriteria.forClass(GR_A1.class);
					dc.add(Restrictions.eq("ReportNo", reportNo));
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					List<GR_A1> a1List = (List<GR_A1>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
					if(a1List != null && !a1List.isEmpty()) {
						// 删除原有数据
						dc = DetachedCriteria.forClass(GR_A1.class);
						dc.add(Restrictions.eq("ReportNo", reportNo));
						IParamVoidResultExecute singleObjectDeleteByCriteriaDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
						singleObjectDeleteByCriteriaDao.paramVoidResultExecute(new Object[] {dc, null});
					}
					
					if(dtoDataMap.get("A1") != null && !dtoDataMap.get("A1").isEmpty()) {
						saveAllDao.paramVoidResultExecute(new Object[] {dtoDataMap.get("A1"), null});
					}
				}
				
				if(dtoDataMap.get("other") != null && !dtoDataMap.get("other").isEmpty()) {
					saveAllDao.paramVoidResultExecute(new Object[] {dtoDataMap.get("other"), null});
				}
			}
		}
	}
	
	/**
	 * 
	 * @description <p>将解析之后的数据存入数据库中做持久化</P>
	 * @createTime 2016-9-19 下午06:21:34
	 * @updateTime 2016-9-19 下午06:21:34
	 * @author Liutao
	 * @updater Liutao
	 */
	private Map<String, List<Object>> translateJsonToDTO(Map<String, Object> parsedData) throws Exception {
		Map<String, List<Object>> dtoDataMap = null;
		if(parsedData != null && !parsedData.isEmpty()) {
			dtoDataMap = new HashMap<String, List<Object>>();
			// 判断当前查询操作所产生的Html文件中是否存在征信数据
			if(parsedData.size() == 1) {
				if(parsedData.containsKey("GR_A2")) {
					GR_A2 A2 = GR_A2.class.newInstance();
					for(Map.Entry<String, String> en : ((Map<String, String>) parsedData.get("GR_A2")).entrySet()) {
						ReflectOperation.setFieldValue(A2, en.getKey(), en.getValue());
					}
					
					List<Object> dataList = new ArrayList<Object>();
					dataList.add(A2);
					dtoDataMap.put("other", dataList);
				}
			} else {
				// 创建报告头信息
				GR_A1 A1 = GR_A1.class.newInstance();
				List<Object> A1List = new ArrayList<Object>();
				List<Object> otherList = new ArrayList<Object>();
				for(Map.Entry<String, String> en : ((Map<String, String>) parsedData.get("GR_A1")).entrySet()) {
					ReflectOperation.setFieldValue(A1, en.getKey(), en.getValue());
				}
				A1List.add(A1);
				dtoDataMap.put("A1", A1List);
				
				parsedData.remove("GR_A1");
				
				for(Map.Entry<String, Object> ens : parsedData.entrySet()) {
					if(ens.getValue() == null) {
						continue;
					}
					
					if(ens.getValue() instanceof Map) {
						if(((Map<String, String>) ens.getValue()).isEmpty()) {
							continue;
						}
						
						Object obj = Class.forName("zxptsystem.dto."+ens.getKey()).newInstance();
						for(Map.Entry<String, String> en : ((Map<String, String>) ens.getValue()).entrySet()) {
							if(!"ReportNo".equals(en.getKey())) {
								ReflectOperation.setFieldValue(obj, en.getKey(), en.getValue());
							}
						}
						ReflectOperation.setFieldValue(obj, "ReportNo", A1);
						otherList.add(obj);
					}
					
					if(ens.getValue() instanceof LinkedList) {
						if(((LinkedList<Map<String, String>>) ens.getValue()).isEmpty()) {
							continue;
						}
						
						for(Object item : (LinkedList<Map<String, String>>) ens.getValue()) {
							if(item == null || ((Map<String, String>) item).isEmpty()) {
								continue;
							}
							
							Object obj = Class.forName("zxptsystem.dto."+ens.getKey()).newInstance();
							for(Map.Entry<String, String> en : ((Map<String, String>) item).entrySet()) {
								if(!"ReportNo".equals(en.getKey())) {
									ReflectOperation.setFieldValue(obj, en.getKey(), en.getValue());
								}
							}
							ReflectOperation.setFieldValue(obj, "ReportNo", A1);
							otherList.add(obj);
						}
					}
				}
				
				if(!otherList.isEmpty()) {
					dtoDataMap.put("other", otherList);
				}
				
			}
			
		}
		return dtoDataMap;
	}

	/**
	 * 
	 * @description <p>推送解析状态</P>
	 * @createTime 2016-11-06 19:18:02
	 * @updateTime 2016-11-06 19:18:08
	 * @author Liuziqun
	 * @throws Exception 
	 * @updater Liuziqun
	 */
	public void sendDataToDB(Map<String, Object> parseData ,GrzxReportParseLog parseLog) throws Exception {
		Map<String, String> sendMap = new HashMap<String, String>() ;
		// 检查是否配置了推送系统参数，且是否启用
		DetachedCriteria dc = DetachedCriteria.forClass(SystemParam.class);
		dc.add(Restrictions.eq("strItemCode", "grzxParseDataSend"));
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		List<SystemParam> paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
		
		if(paramList != null && !paramList.isEmpty()) {
			if(paramList.get(0).getStrEnable().equals("1")) {
				String implBeanName = paramList.get(0).getStrParamValue();
				if(!StringUtils.isBlank(implBeanName)) {
					IGRZXSendInnerInterface innerIterface = null;
					try {
						innerIterface = (IGRZXSendInnerInterface) FrameworkFactory.CreateBean(implBeanName);
					} catch (Exception e) {
						innerIterface = null;
						parseLog.setExceptionReason("获取推送功能实现类失败，"+e.getMessage());
						ApplicationManager.getActionExceptionLog().error("获取推送功能实现类失败", e);
					}
					
					if(innerIterface != null) {
						try {
							if(parseData != null && !parseData.isEmpty()){
								if(parseData.get("GR_A1") != null){
									sendMap.put("ReportNo", ((Map<String, String>) parseData.get("GR_A1")).get("ReportNo"));
								}
								if(parseData.get("errorMap") != null){
									sendMap = (Map<String, String>) parseData.get("errorMap") ;
									sendMap.put("ReportNo" ,"fail");
								}
							} else {
								parseLog.setParseFlag("2");
								parseLog.setExceptionReason("从HTML文件中获取的解析数据为空");
								sendMap.put("ReportNo", "fail");
								sendMap.put("code", "103");
								sendMap.put("error", "当前征信报告的查询日志或者对应的查询报告（HTML文件）为空");
							}
						} catch (Exception e) {
							parseLog.setSendFlag("2");
							parseLog.setExceptionReason("执行推送功能时发生系统未知异常，"+e.getMessage());
							ApplicationManager.getActionExceptionLog().error("执行推送功能时发生系统未知异常", e);
						} finally {
							sendMap.put("app_no", parseLog.getGrzxQueryLog().getGrZXCreditReportInfo().getId());
							
							MessageResult messageResult = innerIterface.autoSend(sendMap);
							if(messageResult.isSuccess()) {
								parseLog.setSendFlag("3");
								if(sendMap.get("code") != null && (sendMap.get("code").equals("102") || sendMap.get("code").equals("105"))){
									parseLog.setSendFlag("2");
								}
							} else {
								parseLog.setSendFlag("2");
								parseLog.setExceptionReason(messageResult.getMessage());
							}
							// 更新解析日志信息
							if(parseLog != null) {
								if(parseLog.getHtmlFilePath() == null){
									parseLog.setHtmlFilePath("") ;
								}
								if(parseLog.getParseFlag() == null){
									parseLog.setParseFlag("1") ;
								}
								if(parseLog.getAutoId() == null){
									IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
									singleObjectSaveDao.paramVoidResultExecute(new Object[] {parseLog, null});
								}else{
									IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
									singleObjectUpdateDao.paramVoidResultExecute(new Object[] {parseLog, null});
								}
							}
						}
					}
				} else {
					parseLog.setExceptionReason("系统参数“个人征信信用报告数据推送”中的参数值中未配置推送功能实现类对应的Bean的名称 ");
				}
			} else {
				parseLog.setExceptionReason("系统参数“个人征信信用报告数据推送”未启用");
			}
		} else {
			parseLog.setExceptionReason("系统参数“个人征信信用报告数据推送”未设置");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
