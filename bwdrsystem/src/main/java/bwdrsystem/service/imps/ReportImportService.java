package bwdrsystem.service.imps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;

import report.dto.ReportInstInfo;
import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;

import bwdrsystem.dto.ReportContext;
import bwdrsystem.dto.ReportFileImportLog;
import bwdrsystem.helper.ReportContextParse;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.imps.BaseDTService;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

/**
 * 
 * @description <p>报文导入Service</P>
 * @createTime 2016-8-11 下午07:41:26
 * @updateTime 2016-8-11 下午07:41:26
 * @author Liutao
 * @updater Liutao
 */
public class ReportImportService extends BaseDTService {

	@Override
	public void initSuccessResult() throws Exception {
		MessageResult messageResult = new MessageResult();
		try {
			super.initDao();
			Map<String, List<Object>> reportDataMap = null;// 解析之后的报文数据
			List<ReportContext> reportContextList = (List<ReportContext>) LogicParamManager.getSaveObjectList();
			if(reportContextList != null) {
				// 创建报送机构
				createReportInstInfo();
				// 创建任务
				TaskFlow taskFlow = createTask();
				// 保存解析出的报文数据
				reportDataMap = ReportContextParse.translateReportToDTO(reportContextList);
				// 获取报文文件中每个报文数据对应的基础段DTO名称
				String dtoNames = RequestManager.getReportCheckParam().get("dataDTONames");
				if(!StringUtils.isBlank(dtoNames)) {
					((IParamVoidResultExecute)this.getBaseDaoObject()).paramVoidResultExecute(new Object[] {reportDataMap.get("JC"), null});
					((IParamVoidResultExecute)this.getBaseDaoObject()).paramVoidResultExecute(new Object[] {reportDataMap.get("MX"), null});
					
					// 找出基础段DTO对应的表名
					Table table = null;
					List<String> tableNameList = new ArrayList<String>();
					for(String str : dtoNames.split(",")) {
						table = Class.forName(str).getAnnotation(Table.class);
						tableNameList.add(table.name());
					}
					
					// 查询基础段DTO对应模型数据（ReportModel_Table）
					IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria dc = DetachedCriteria.forClass(ReportModel_Table.class);
					dc.add(Restrictions.in("strTableName", tableNameList));
					List<ReportModel_Table> tableList = (List<ReportModel_Table>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
					
					// 获取行内机构信息
					dc = DetachedCriteria.forClass(InstInfo.class);
					dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(InstInfo.class).getName(), RequestManager.getId()));
					List<InstInfo> instInfos = (List<InstInfo>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
					InstInfo instInfo = instInfos.get(0);
					
					Set<TaskModelInst> tmiSet = new HashSet<TaskModelInst>();
					IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
					for(ReportModel_Table modelTable : tableList) {
						TaskModelInst taskModelInst = new TaskModelInst();
						taskModelInst.setTaskFlow(taskFlow);
						taskModelInst.setReportModel_Table(modelTable);
						
						taskModelInst.setInstInfo(instInfo);
						saveDao.paramVoidResultExecute(new Object[] {taskModelInst, null});
						tmiSet.add(taskModelInst);
					}
					taskFlow.setTaskModelInsts(tmiSet);
					IParamVoidResultExecute updateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
					updateDao.paramVoidResultExecute(new Object[] {taskFlow, null});
				}
				
				// 创建导入日志
				createImportReportLog(reportDataMap);
				
				messageResult.setMessage(this.getSuccessMessage());
				messageResult.setSuccess(true);
			} else {
				messageResult.setSuccess(false);
				messageResult.setMessage("当前报文文件中无任何报文数据");
			}
			this.setServiceResult(messageResult);
		} catch (Exception e) {
			e.printStackTrace();
			if(!StringUtils.isBlank(e.getMessage())) {
				Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
				Matcher m = p.matcher(e.getMessage());
				if (m.find()) {
					messageResult.setMessage(e.getMessage());
				} else {
					messageResult.setMessage("系统异常");
				}
			} else {
				messageResult.setMessage("系统异常");
			}
			messageResult.setSuccess(false);
		}
		this.setServiceResult(messageResult);
	}

	/**
	 * 
	 * @description <p>创建导入报文日志</P>
	 * @createTime 2016-8-15 下午06:02:01
	 * @updateTime 2016-8-15 下午06:02:01
	 * @author Liutao
	 * @updater Liutao
	 */
	private void createImportReportLog(Map<String, List<Object>> reportDataMap) throws Exception {
		DetachedCriteria dc = DetachedCriteria.forClass(ReportFileImportLog.class);
		dc.add(Restrictions.eq("result", "1"));
		dc.add(Restrictions.eq("reportFileName", RequestManager.getReportCheckParam().get("uploadFileFileName")));
		IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		List<ReportFileImportLog> logList = (List<ReportFileImportLog>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
		if(logList != null && !logList.isEmpty()) {
			IParamVoidResultExecute deleteByCriteriaDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
			deleteByCriteriaDao.paramVoidResultExecute(new Object[] {dc, null});
		}
		
		String reportInstInfoCode = "";
		String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
		ReportFileImportLog reportFileImportLog = new ReportFileImportLog();
		String reportFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
		
		if(zxFlag.equals("GR")) {
			reportInstInfoCode = reportFileName.substring(0, 14);
		}
		
		if(zxFlag.equals("QY")) {
			reportInstInfoCode = reportFileName.substring(2, 13);
		}
		
		if(zxFlag.equals("JG")) {
			reportInstInfoCode = reportFileName.substring(2, 22).replaceAll("000000000", "").trim();
		}
		
		reportFileImportLog.setStrReportInstCode(reportInstInfoCode);
		
		Date importDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		reportFileImportLog.setImportDate(sdf.format(importDate));
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		reportFileImportLog.setDtDate(sdf.format(importDate));
		
		Object userInfo = SecurityContext.getInstance().getLoginInfo().getTag();
		if(userInfo != null && !((UserInfo) userInfo).getStrUserCode().equals("admin")) {
			reportFileImportLog.setOperator(((UserInfo) userInfo));
		}
		
		int notNullReportCount = reportDataMap.get("JC").size()+reportDataMap.get("MX").size();
		int nullReportCount = Integer.parseInt(RequestManager.getReportCheckParam().get("nullReportDataCount"));
		reportFileImportLog.setImportDataCount(notNullReportCount);
		reportFileImportLog.setRemark("导入空报文数目为"+nullReportCount+"个，非空报文数目为"+
				reportDataMap.get("JC").size()+"个，包括"+reportDataMap.get("JC").size()+"条基础段数据和"+reportDataMap.get("MX").size()+"条明细段数据");
		
		reportFileImportLog.setResult("1");
		reportFileImportLog.setReportFileName(reportFileName);
		setReportTypeAndName(reportFileImportLog);
		reportFileImportLog.setId(UUID.randomUUID().toString().replaceAll("-", "").trim());
		
		IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
		saveDao.paramVoidResultExecute(new Object[]{reportFileImportLog, null});
	}
	
	/**
	 * 
	 * @description <p>设置导入日志的报文名称及报文类型字段值</P>
	 * @createTime 2016-8-16 下午04:12:36
	 * @updateTime 2016-8-16 下午04:12:36
	 * @author Liutao
	 * @updater Liutao
	 */
	private void setReportTypeAndName(ReportFileImportLog reportFileImportLog) {
		String reportName = "";
		String reportTypes = "";
		Map<String, String> reportTypeMap = null;
		String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
		String curReportType = RequestManager.getReportCheckParam().get("reportType");
		List<ReportContext> reportContextList = (List<ReportContext>) LogicParamManager.getSaveObjectList();
		
		if(zxFlag.equals("GR")) {
			// 机构征信报文名和报文类型一致
			reportName = ShowContext.getInstance().getShowEntityMap().get("GrReportType").get(curReportType);
			reportFileImportLog.setReportType(reportName);
		}
		
		if(zxFlag.equals("QY")) {
			if(curReportType.equals("31")) {
				reportTypeMap = ShowContext.getInstance().getShowEntityMap().get("PLXDYWSJBWLX");
			} else {
				reportTypeMap = ShowContext.getInstance().getShowEntityMap().get("BWLX");
			}
			
			reportName = ShowContext.getInstance().getShowEntityMap().get("ReportType").get(curReportType);
			for(ReportContext reportContext : reportContextList) {
				if(!reportContext.getBodyList().isEmpty()) {
					if(StringUtils.isBlank(reportContext.getHeader())) {
						reportTypes += reportTypeMap.get(reportContext.getHeader().substring(29, 31)) + "、";
					} else {
						if(reportTypes.indexOf(reportContext.getHeader().substring(29, 31)) == -1) {
							reportTypes += reportTypeMap.get(reportContext.getHeader().substring(29, 31)) + "、";
						}
					}
				}
			}
		}
		
		if(zxFlag.equals("JG")) {
			reportTypeMap = ShowContext.getInstance().getShowEntityMap().get("JG_BWLX");
			reportName = ShowContext.getInstance().getShowEntityMap().get("JgReportType").get(curReportType);
			for(ReportContext reportContext : reportContextList) {
				if(!reportContext.getBodyList().isEmpty()) {
					if(StringUtils.isBlank(reportContext.getHeader())) {
						reportTypes += reportTypeMap.get(reportContext.getHeader().substring(40, 41)) + "、";
					} else {
						if(reportTypes.indexOf(reportContext.getHeader().substring(40, 41)) == -1) {
							reportTypes += reportTypeMap.get(reportContext.getHeader().substring(40, 41)) + "、";
						}
					}
				}
			}
		}
		
		reportFileImportLog.setReportName(reportName);
		if(!StringUtils.isBlank(reportTypes)) {
			reportTypes = reportTypes.substring(0, reportTypes.length()-1);
			reportFileImportLog.setReportType(reportTypes);
		}
	}
	
	/**
	 * 
	 * @description <p>创建任务</P>
	 * @createTime 2016-8-13 下午08:09:30
	 * @updateTime 2016-8-13 下午08:09:30
	 * @author Liutao
	 * @updater Liutao
	 */
	private TaskFlow createTask() throws Exception {
		// 新建任务
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TaskFlow taskFlow = TaskFlow.class.newInstance();
		String curReportType = RequestManager.getReportCheckParam().get("reportType");
		Map<String, String> taskInfoMap = ShowContext.getInstance().getShowEntityMap().get("taskInfo");
		String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
		Map<String, String> reportFileTypeNameMap = null;
		if(zxFlag.equals("GR")) {
			reportFileTypeNameMap = ShowContext.getInstance().getShowEntityMap().get("GrReportType");
		}
		
		if(zxFlag.equals("QY")) {
			reportFileTypeNameMap = ShowContext.getInstance().getShowEntityMap().get("ReportType");
		}

		if(zxFlag.equals("JG")) {
			reportFileTypeNameMap = ShowContext.getInstance().getShowEntityMap().get("JgReportType");
		}
		String reportFileTypeName = reportFileTypeNameMap.get(curReportType);
		
		// 删除当前导入报文文件类型对应的任务（即：同一个报文文件进行导入时，只能建立一个任务）
		String taskName = reportFileTypeName+taskInfoMap.get("strTaskName")+sdf.format(date);
		DetachedCriteria dc = DetachedCriteria.forClass(TaskFlow.class);
		dc.add(Restrictions.eq("strTaskName", taskName));
		IParamVoidResultExecute deleteByCriteriaDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
		deleteByCriteriaDao.paramVoidResultExecute(new Object[] {dc, null});
		
		taskFlow.setStrTaskState("1");
		taskFlow.setStrFreq(taskInfoMap.get("strFreq"));
		
		taskFlow.setStrTaskName(taskName);
		
		sdf = new SimpleDateFormat(taskInfoMap.get("dtTaskDate"));
		taskFlow.setDtTaskDate(sdf.format(date));
		RequestManager.getReportCheckParam().put("dtDate", sdf.format(date));
		
		sdf = new SimpleDateFormat(taskInfoMap.get("taskStartData"));
		taskFlow.setTaskStartData(sdf.format(date));
		
		sdf = new SimpleDateFormat(taskInfoMap.get("taskEndData"));
		taskFlow.setTaskEndData(sdf.format(date));
		
		dc = DetachedCriteria.forClass(Suit.class);
		IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		if(zxFlag.equals("GR")) {
			dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(Suit.class).getName(), "22"));
		}
		if(zxFlag.equals("QY")) {
			dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(Suit.class).getName(), "21"));
		}
		if(zxFlag.equals("JG")) {
			dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(Suit.class).getName(), "24"));
		}
		
		List<Suit> suitList = (List<Suit>) findByCriteriaDao.paramObjectResultExecute(new Object[]{dc, null});
		IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
		if(suitList == null || suitList.isEmpty()) {
			Suit suit = new Suit();
			if(zxFlag.equals("GR")) {
				suit.setStrSuitCode("22");
				suit.setStrSuitName("个人征信");
			}
			if(zxFlag.equals("QY")) {
				suit.setStrSuitCode("21");
				suit.setStrSuitName("企业征信");
			}
			if(zxFlag.equals("JG")) {
				suit.setStrSuitCode("24");
				suit.setStrSuitName("机构信息");
			}
			saveDao.paramVoidResultExecute(new Object[]{suit, null});
			taskFlow.setSuit(suit);
		} else {
			taskFlow.setSuit(suitList.get(0));
		}
		saveDao.paramVoidResultExecute(new Object[] {taskFlow, null});
		return taskFlow;
	}
	
	/**
	 * 
	 * @description <p>创建及报送机构</P>
	 * @createTime 2016-8-22 下午03:43:42
	 * @updateTime 2016-8-22 下午03:43:42
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private void createReportInstInfo() throws Exception {
		String reportInstInfoCode = "";
		String zxFlag = RequestManager.getReportCheckParam().get("zxFlag");
		String reportFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
		IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
		IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		IParamVoidResultExecute deleteByCriteriaDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
		if(zxFlag.equals("GR")) {
			reportInstInfoCode = reportFileName.substring(0, 14);
		}
		
		if(zxFlag.equals("QY")) {
			reportInstInfoCode = reportFileName.substring(2, 13);
		}
		
		if(zxFlag.equals("JG")) {
			reportInstInfoCode = reportFileName.substring(2, 22).replaceAll("000000000", "").trim();
		}
		
		// 删除原有关联的行内机构数据
		DetachedCriteria dc = DetachedCriteria.forClass(ReportInstSubInfo.class);
		dc.add(Restrictions.eq("reportInstInfo.strReportInstCode", reportInstInfoCode));
		deleteByCriteriaDao.paramVoidResultExecute(new Object[] {dc, null});
		// 获取行内机构信息
		dc = DetachedCriteria.forClass(InstInfo.class);
		dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(InstInfo.class).getName(), RequestManager.getId()));
		List<InstInfo> instInfos = (List<InstInfo>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
		// 获取报送机构信息
		dc = DetachedCriteria.forClass(ReportInstInfo.class);
		dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(ReportInstInfo.class).getName(), reportInstInfoCode));
		List<ReportInstInfo> reportInstInfos = (List<ReportInstInfo>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
		// 重新关联行内机构
		ReportInstSubInfo subInstInfo = new ReportInstSubInfo();
		subInstInfo.setInstInfo(instInfos.get(0));
		subInstInfo.setReportInstInfo(reportInstInfos.get(0));
		saveDao.paramVoidResultExecute(new Object[] {subInstInfo, null});
	}
	
	
}
