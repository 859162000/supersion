package zxptsystem.helper.HTMLParseAndJSONSendTool;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.GRZXQueryLog;
import zxptsystem.dto.GR_A1;
import zxptsystem.dto.GrzxReportParseLog;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
/**
 * 
 * @description <p>个人征信报告数据推送工具类</P>
 * @createTime 2016-9-30 下午04:54:42
 * @updateTime 2016-10-17 下午04:22:33
 * @author Liutao
 * @updater Liuziqun
 */
public class GRZXReportSendTool {
	private static GRZXReportSendTool grzxReportSendTool;
	
	synchronized public static GRZXReportSendTool getInstance() {
		if(grzxReportSendTool == null) {
			grzxReportSendTool = new GRZXReportSendTool();
		}
		return grzxReportSendTool;
	}
	
	/**
	 * 
	 * @description <p>个人征信人行查询时的解析及数据推送过程方法，即：人行查询时调用</P>
	 * @createTime 2016-9-19 下午04:24:16
	 * @updateTime 2016-9-19 下午04:24:16
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	public void grzxReportParseOrSendOnPeopleBakQuery(String htmlFilePath, String logId) throws Exception {
		GrzxReportParseLog parseLog = null;
		Map<String, String> errorMap = new HashMap<String, String>();
		Map<String, Object> parseData = new HashMap<String, Object>();
		if(!StringUtils.isBlank(logId) && !StringUtils.isBlank(htmlFilePath)) {
			try {
				// 查看是否配置有“个人征信信用报告解析”参数
				DetachedCriteria dc = DetachedCriteria.forClass(SystemParam.class);
				dc.add(Restrictions.eq("strItemCode", "grzxReportParse"));
				dc.add(Restrictions.eq("strEnable", "1"));
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				List<SystemParam> paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
				if(paramList != null && !paramList.isEmpty()) {
					dc = DetachedCriteria.forClass(GRZXQueryLog.class);
					dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(GRZXQueryLog.class).getName(), logId));
					List<GRZXQueryLog> queryLogList = (List<GRZXQueryLog>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
					
					if(queryLogList != null && !queryLogList.isEmpty()) {// 是否有查询日志
						dc = DetachedCriteria.forClass(GrzxReportParseLog.class);
						dc.add(Restrictions.eq("grzxQueryLog",queryLogList.get(0))) ;
						List<GrzxReportParseLog> grzxReportParseList = (List<GrzxReportParseLog>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
						
						if(grzxReportParseList == null || grzxReportParseList.isEmpty()){
							parseLog = GrzxReportParseLog.class.newInstance();
							parseLog.setParseFlag("1");
							parseLog.setSendFlag("1");
							parseLog.setHtmlFilePath(htmlFilePath);
							parseLog.setGrzxQueryLog(queryLogList.get(0));
							// 新增最初始状态的解析日志
							IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
							singleObjectSaveDao.paramVoidResultExecute(new Object[] {parseLog, null});
						}else{
							parseLog = grzxReportParseList.get(0) ;
						}
						// 查看是否配置有“个人征信报告解析生成JSON文件目录”参数
						dc = DetachedCriteria.forClass(SystemParam.class);
						dc.add(Restrictions.eq("strItemCode", "grzxJsonFilePath"));
						dc.add(Restrictions.eq("strEnable", "1"));
						paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
						
						// html数据解析
						try {
							if(paramList == null || paramList.isEmpty() || StringUtils.isBlank(paramList.get(0).getStrParamValue())) {
								//parseData = HTMLParseTool.parseHTMLData(htmlFilePath, parseLog);
								throw new Exception("没有配置“个人征信报告解析生成JSON文件目录”");
							} else {
								File htmlFile = new File(htmlFilePath);
								String jsonFilePath = OtherTool.getInstance().getJsonFilePath(paramList.get(0).getStrParamValue(), htmlFile);
								parseData = HTMLParseTool.parseHTMLData(htmlFilePath, jsonFilePath, parseLog);
							}
						} catch (Exception e) {
							errorMap.put("ReportNo", "fail");
							errorMap.put("code", "102");
							errorMap.put("error", "解析HTML文件失败");
							parseData.put("errorMap", errorMap) ;
							parseLog.setParseFlag("2");
							parseLog.setExceptionReason("HTML文件解析异常，"+e.getMessage());
							ApplicationManager.getActionExceptionLog().error("HTML文件解析异常", e);
							OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
						}
						
						// 解析之后获取到需要的数据
						Map<String, String> a1Data = new HashMap<String, String>() ;
						if(parseData != null && parseData.get("GR_A1") !=null){
							// 添加报告编号到解析日志中进行保存
							a1Data = (Map<String, String>) parseData.get("GR_A1");
							if(a1Data != null && !a1Data.isEmpty() && a1Data.containsKey("ReportNo")) {
								parseLog.setReportNo(a1Data.get("ReportNo"));
							}
							parseLog.setParseFlag("3");
							// 对解析之后的数据进行数据持久化
							try {
								OtherTool.getInstance().saveDataToDB(parseData);
							} catch (Exception e) {
								parseLog.setExceptionReason("保存 解析数据至数据库中异常");
								ApplicationManager.getActionExceptionLog().error("保存 解析数据至数据库中异常", e);
							}
						}else{
							parseLog.setParseFlag("2");
							parseLog.setExceptionReason("解析HTML文件之后获取的数据为空");
						}
						parseData.put(GR_A1.class.getSimpleName(), a1Data);
						OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
					} else {// 查询征信查询日志结束
						ApplicationManager.getActionExceptionLog().error("当前征信查询操作对应的查询日志不存在");
					}				
				}
			} catch (Exception e) {
				ApplicationManager.getActionExceptionLog().error("系统未知异常", e);
			} finally {
				// 更新解析日志信息
				if(parseLog != null) {
					IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
					singleObjectUpdateDao.paramVoidResultExecute(new Object[] {parseLog, null});
				}
			}
		} else {
			ApplicationManager.getActionExceptionLog().error("当前征信报告的查询日志或者对应的查询报告（HTML文件）为空");
		}
	}

	/**
	 * 
	 * @description <p>个人征信本地查询时的数据处理过程，即：本地查询时调用</P>
	 * @createTime 2016-10-8 上午09:38:06
	 * @updateTime 2016-10-8 上午09:38:06
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	public void grzxReportSendOnLocalQuery(String queryLogId, GRZXCreditReportInfo gRZXCreditReportInfo) throws Exception {
		GrzxReportParseLog parseLog = null;
		Map<String, Object> parseData = new HashMap<String, Object>() ;
		Map<String, Object> errorMap = new HashMap<String, Object>() ;
		OtherTool otherTool = OtherTool.getInstance();
		try {
			// 添加解析日志
			parseLog = otherTool.addParseLogOnLocalQuery(queryLogId, gRZXCreditReportInfo);
			
			// 检查是否配置了解析系统参数，且是否启用
			DetachedCriteria dc = DetachedCriteria.forClass(SystemParam.class);
			dc.add(Restrictions.eq("strItemCode", "grzxReportParse"));
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<SystemParam> paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
			
			if(paramList != null && !paramList.isEmpty() && paramList.get(0) != null && paramList.get(0).getStrEnable().equals("1")) {
				// 查看是否配置有“个人征信报告解析生成JSON文件目录”参数
				dc = DetachedCriteria.forClass(SystemParam.class);
				dc.add(Restrictions.eq("strItemCode", "grzxJsonFilePath"));
				paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
				
				// html数据解析
				try {
					if(paramList == null || paramList.isEmpty() || 
							!paramList.get(0).getStrEnable().equals("1") || 
							StringUtils.isBlank(paramList.get(0).getStrParamValue())) {
						parseData = HTMLParseTool.parseHTMLData(parseLog.getHtmlFilePath(), parseLog);
					} else {	
						File htmlFile = new File(parseLog.getHtmlFilePath());
						String jsonFilePath = OtherTool.getInstance().getJsonFilePath(paramList.get(0).getStrParamValue(), htmlFile);
						parseData = HTMLParseTool.parseHTMLData(parseLog.getHtmlFilePath(), jsonFilePath, parseLog);
					}
				} catch (Exception e) {
					errorMap.put("ReportNo", "fail");
					errorMap.put("code", "102");
					errorMap.put("error", "解析HTML文件失败");
					parseData.put("errorMap", errorMap) ;
					parseLog.setParseFlag("2");
					parseLog.setExceptionReason("HTML文件解析异常，"+e.getMessage());
					ApplicationManager.getActionExceptionLog().error("HTML文件解析异常", e);
					OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
				}
				
				
				// 解析之后获取到需要的数据
				Map<String, String> a1Data = new HashMap<String, String>() ;
				if(parseData != null && parseData.get("GR_A1") !=null){
					// 添加报告编号到解析日志中进行保存
					a1Data = (Map<String, String>) parseData.get("GR_A1");
					if(a1Data != null && !a1Data.isEmpty() && a1Data.containsKey("ReportNo")) {
						parseLog.setReportNo(a1Data.get("ReportNo"));
						
					}
					parseLog.setParseFlag("3");
					// 对解析之后的数据进行数据持久化
					try {
						OtherTool.getInstance().saveDataToDB(parseData);
					} catch (Exception e) {
						parseLog.setExceptionReason("保存 解析数据至数据库中异常");
						ApplicationManager.getActionExceptionLog().error("保存 解析数据至数据库中异常", e);
					}
				}else{
					parseLog.setParseFlag("2");
					parseLog.setExceptionReason("解析HTML文件之后获取的数据为空");
				}
				parseData.put(GR_A1.class.getSimpleName(), a1Data);
				OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
			} else {
				parseLog.setExceptionReason("系统参数“个人征信信用报告解析”未设置或未启用");
			}// 解析系统从参数是否配置判断完毕
		} catch (Exception e) {
			errorMap.put("ReportNo", "fail");
			errorMap.put("code", "105");
			errorMap.put("error", "个人征信报告本地查询时，存放解析日志或者调用远程数据库异常");
			parseData.put("errorMap", errorMap) ;
			OtherTool.getInstance().sendDataToDB(parseData ,parseLog);
			ApplicationManager.getActionExceptionLog().error("个人征信报告本地查询时，存放解析日志或者调用远程数据库异常", e);
		} finally {
			// 更新解析日志信息
			if(parseLog != null) {
				IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
				singleObjectUpdateDao.paramVoidResultExecute(new Object[] {parseLog, null});
			}
		}
	}

	
	
	
	
	


}
