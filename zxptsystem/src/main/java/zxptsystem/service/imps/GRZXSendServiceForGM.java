package zxptsystem.service.imps;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;
import zxptsystem.dto.GrzxReportParseLog;
import zxptsystem.helper.HTMLParseAndJSONSendTool.HTMLParseTool;
import zxptsystem.helper.HTMLParseAndJSONSendTool.OtherTool;
import zxptsystem.service.interfaces.IGRZXSendInnerInterface;
import zxptsystem.service.interfaces.IGRZXSendOuterInterfaceForGM;

public class GRZXSendServiceForGM implements IGRZXSendInnerInterface, IGRZXSendOuterInterfaceForGM {
	private String dbType;
	private String dbUrl;
	private String userName;
	private String pwd;
	
	@Override
	public MessageResult autoSend(Object obj) throws Exception {
		return sendToCustomer(obj);
	}
	
	/**
	 * 
	 * @description <p>HTML文件数据手动推送流程</P>
	 * @createTime 2016-10-8 下午12:31:08
	 * @updateTime 2016-10-8 下午12:31:08
	 * @author Liutao
	 * @updater Liutao
	 */
	public MessageResult handleSend(Object obj) throws Exception {
		List<SystemParam> paramList = null;
		MessageResult messageResult = new MessageResult();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		// 查看系统是否配置有推送参数
		DetachedCriteria dc = DetachedCriteria.forClass(SystemParam.class);
		dc = DetachedCriteria.forClass(SystemParam.class);
		dc.add(Restrictions.eq("strItemCode", "grzxJsonFilePath"));
		paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
		if(paramList != null && !paramList.isEmpty() && paramList.get(0) != null && paramList.get(0).getStrEnable().equals("1")) {
			Map<String, Object> parseData = null;
			OtherTool otherTool = OtherTool.getInstance();
			
			dc = DetachedCriteria.forClass(GrzxReportParseLog.class);
			dc.add(Restrictions.in(ReflectOperation.getPrimaryKeyField(GrzxReportParseLog.class).getName(), (String[])RequestManager.getIdList()));
			List<GrzxReportParseLog> parseLogList = (List<GrzxReportParseLog>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
			IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
			
			for(GrzxReportParseLog parseLog : parseLogList) {
				parseData = HTMLParseTool.parseHTMLData(parseLog.getHtmlFilePath(), parseLog);
				Map<String, String> sendMap = new HashMap<String ,String>() ;
				// 解析之后获取到需要的数据
				if(parseData != null && !parseData.isEmpty()){
					if(parseData.get("GR_A1") !=null) {
						// 添加报告编号到解析日志中进行保存
						Map<String, String> a1Data = (Map<String, String>) parseData.get("GR_A1");
						if(a1Data != null && !a1Data.isEmpty() && a1Data.containsKey("ReportNo")) {
							parseLog.setReportNo(a1Data.get("ReportNo"));
						}
						parseLog.setParseFlag("3");
						sendMap.put("ReportNo", a1Data.get("ReportNo"));
						// 对解析之后的数据进行数据持久化
						try {
							OtherTool.getInstance().saveDataToDB(parseData);
						} catch (Exception e) {
							parseLog.setExceptionReason("保存 解析数据至数据库中异常");
							ApplicationManager.getActionExceptionLog().error("保存 解析数据至数据库中异常", e);
						}
					}
					if(parseData != null && parseData.get("errorMap") != null){
						parseLog.setParseFlag("2");
						sendMap = (Map<String, String>) parseData.get("errorMap") ;
						sendMap.put("ReportNo", "fail") ;
					}
				} else {
					parseLog.setParseFlag("2");
					parseLog.setExceptionReason("从HTML文件中获取的解析数据为空");
					sendMap.put("ReportNo", "fail") ;
					sendMap.put("code", "103");
					sendMap.put("error", "当前征信报告的查询日志或者对应的查询报告（HTML文件）为空");
				}
				sendMap.put("app_no", parseLog.getGrzxQueryLog().getGrZXCreditReportInfo().getId());
				
				messageResult = sendToCustomer(sendMap);
				if(messageResult.isSuccess()) {
					parseLog.setSendFlag("3");
					parseLog.setExceptionReason(null) ;
					messageResult.setSuccess(true);
					messageResult.setMessage("解析数据推送成功");
					if(sendMap.get("code") != null && (sendMap.get("code").equals("102") || sendMap.get("code").equals("105"))){
						parseLog.setSendFlag("2");
						parseLog.setExceptionReason(messageResult.getMessage());
					}
				} else {
					parseLog.setSendFlag("2");
					parseLog.setExceptionReason(messageResult.getMessage());
				}
				singleObjectUpdateDao.paramVoidResultExecute(new Object[] {parseLog, null});
			}
		} else {
			messageResult.setSuccess(false);
			messageResult.setMessage("“个人征信信用报告解析”系统参数未配置或未启用");
		}
		
		return messageResult;
	}
	
	@Override
	public MessageResult sendToCustomer(Object obj) throws Exception {
		MessageResult messageResult = new MessageResult(true);
		OtherTool otherTool = OtherTool.getInstance();
		Connection conn = otherTool.getJDBCConnection(dbType, dbUrl, userName, pwd);
		
		if(conn != null) {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				String sql = null;
				Map<String ,String> sendMap = (Map<String ,String>) obj ;
				if(sendMap.containsKey("code")) {
					sql = "insert into t_bop_reportno (reportNo, data, app_no, status) values ('%s','%s','%s',1);";
					String code = sendMap.get("code");
					String error = sendMap.get("error");
					sql = String.format(sql, sendMap.get("ReportNo"), "{\"code\":" + code + ",\"error\":\"" +error+"\"}", sendMap.get("app_no"));
				} else {
					sql = "insert into t_bop_reportno (reportNo, app_no, status) values ('%s','%s',1);";
					sql = String.format(sql, sendMap.get("ReportNo"), sendMap.get("app_no"));
				}
				stmt.execute(sql);
			} catch (Exception e) {
				messageResult.setSuccess(false);
				messageResult.setMessage("操作数据库异常");
				ApplicationManager.getActionExceptionLog().error("操作数据库异常", e);
			} finally {
				try {
					if(stmt != null) {
						stmt.close();
					}
					conn.close();
				} catch (Exception e) {
					messageResult.setSuccess(false);
					messageResult.setMessage("关闭数据库链接失败");
					ApplicationManager.getActionExceptionLog().error("关闭数据库链接失败", e);
				}
			}
		} else {
			messageResult.setSuccess(false);
			messageResult.setMessage("获取数据库链接失败");
		}
		
		return messageResult;
	}


	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
