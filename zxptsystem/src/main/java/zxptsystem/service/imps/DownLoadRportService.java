package zxptsystem.service.imps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import report.service.imps.CommonUpdateReportStatusService;
import report.service.imps.DownloadResourceService;

import zxptsystem.dto.AutoDTO_QYZXPLXDYWSJSC;

import com.cfcc.ecus.eft.CryptAPI;
import com.cfcc.ecus.eft.checkFile;
import com.cfcc.ecus.eft.cudata.CUCheckResult;
import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import coresystem.service.imps.AutoOrderService;
import extend.dto.ReportModel_Table;
import extend.dto.SystemParam;
import framework.dao.imps.OrderBySqlFormula;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
import framework.show.ShowContext;
/**
 * 企业征信报文生成
 * @author zhangqiu & xiajieli
 *
 */
public class DownLoadRportService implements IObjectResultExecute {

	private String systemCode;

	private String instType;

	private String strJRJGCode;

	private String strReportType;

	private String strSendType;

	private String feedbackType;

	private String[] selectReport;

	private String strVersion;

	private String strJRJGCodeRela;

	private String strInstReportRela;

	private String crypConfig;

	private String crypPublickey;

	private String cryptKeystore;

	private String v_file;

	private String dic_file;

	private String batchFile;

	private String reportFilePath;

	private Map<String, String> reportMap;

	private Map<String, String> xxjllxMap;
	
	private String YWFSRQ;
	
	private List<InstInfo> instInfoSubList;
	
	private Map<String, String> downloadJudgeStatusMap;
	
	private List<String> mxOneToOneDtoNameList;
	
	private Map<String, String> reportMapMatchToQYZXBGLX;//reportMap匹配企业征信变更类型

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		//校验未通过返回错误文本
		if(ServletActionContext.getContext().getApplication().get("strQYZXCheck")!=null
				&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strQYZXCheck").toString())){//企业
			ZXDowndLoadCheckFileService ZXDowndLoadCheckFileService=new ZXDowndLoadCheckFileService();
			return ZXDowndLoadCheckFileService.ZXWriteCheckFile("qyzx", ServletActionContext.getContext().getApplication().get("strQYZXCheck").toString());
		}
		
		MessageResult messageResult = new MessageResult();
		
		this.strReportType=(String) ActionContext.getContext().getSession().get("strReportType");
		this.strJRJGCode=(String) ActionContext.getContext().getSession().get("strJRJGCode");
		this.YWFSRQ=(String) ActionContext.getContext().getSession().get("YWFSRQ");
		
		String key="qyzx"+"&"+strReportType+"&"+strJRJGCode;
		
		try{
			ServletActionContext.getContext().getApplication().put(key, "1");
			
			if(strReportType!=null){
				if (strReportType.equals("31")) {
					this.strSendType = "1";
				} 
				this.feedbackType = "0";
				if(strReportType.equals("11")) {
					selectReport = new String[] {"01","02","03","04"};
				}
				else if(strReportType.equals("12")) {
					selectReport = new String[] {"11","12","13","14","15","16","17","18","19","20","21"};
				}
		        else if(strReportType.equals("14")) {
		        	selectReport = new String[] {"61"};
				}
		        else if(strReportType.equals("31")) {
		        	selectReport = new String[] {"51"};
				}
			}

			if (StringUtils.isBlank(systemCode)) {
				messageResult.getMessageSet().add("应用系统代码不能为空");
			}
			if (StringUtils.isBlank(instType)) {
				messageResult.getMessageSet().add("机构类型不能为空");
			}
			if (StringUtils.isBlank(strJRJGCode)) {
				messageResult.getMessageSet().add("金融机构代码不能为空");
			}
			if (StringUtils.isBlank(strReportType)) {
				messageResult.getMessageSet().add("报文文件种类不能为空");
			}
			if (StringUtils.isBlank(feedbackType)) {
				messageResult.getMessageSet().add("反馈类型不能为空");
			}
			
			if (selectReport == null || selectReport.length == 0) {
				messageResult.getMessageSet().add("没有选择生成报文");
			}

			if(!StringUtils.isBlank(strJRJGCode)){
				instInfoSubList = new ArrayList<InstInfo>();
				IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
				detachedCriteriaInstCode.add(Restrictions.eq("reportInstInfo.strReportInstCode",strJRJGCode));
				List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
				if(reportInstSubInfoList.size()>0){
					for (ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList) {
						Object obj=ReflectOperation.getFieldValue(reportInstSubInfo, "instInfo");
						String strInstCode=ReflectOperation.getFieldValue(obj, "strInstCode").toString();
						InstInfo instInfo = new InstInfo();
						instInfo.setStrInstCode(strInstCode);
						instInfoSubList.add(instInfo);
					}
				}else{
					messageResult.getMessageSet().add("请在报送机构管理下添加子机构");
				}
			}
			
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			Object qyzx_strVersion = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "qyzx_strVersion" , null});
			 if(qyzx_strVersion != null) {
				SystemParam systemParam = (SystemParam)qyzx_strVersion;
				if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
					strVersion=systemParam.getStrParamValue();
				}
			}
			 if(StringUtils.isBlank(strVersion)){
				 messageResult.getMessageSet().add("请在参数管理下配置企业征信版本号");
			 }
			 
			 Object qyzx_reportFilePath = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "qyzx_reportFilePath" , null});
			 if(qyzx_reportFilePath != null) {
				SystemParam systemParam = (SystemParam)qyzx_reportFilePath;
				if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
					reportFilePath=systemParam.getStrParamValue();
				}
			}
			 if(StringUtils.isBlank(reportFilePath)){
				 messageResult.getMessageSet().add("请在参数管理下配置企业征信生成报文路径");
			 }
			 
			 ((Map<String,Object>)ServletActionContext.getContext().get("request")).put("instInfoSubList", instInfoSubList);
			 ((Map<String,Object>)ServletActionContext.getContext().get("request")).put("downloadJudgeStatusMap", downloadJudgeStatusMap);
			 
			if (messageResult.getMessageSet().size() > 0) {
				messageResult.setMessage("生成失败");
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
			} else {
				return GenerateReport();
			}
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			return new MessageResult(false,"生成报文异常");
		}
		finally{
			ServletActionContext.getContext().getApplication().put(key, null);
			ActionContext.getContext().getSession().put("strJRJGCode", "");
			ActionContext.getContext().getSession().put("YWFSRQ", "");
			ActionContext.getContext().getSession().put("strReportType", "");
		}
	}

	@SuppressWarnings("unchecked")
	private Object GenerateReport() throws Exception {
		
		String fileDescription=""; //报表类型描述
		FileHandler fileHandler = new FileHandler();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");

		String fileName = "";
		fileName += systemCode;
		fileName += instType;
		fileName += strJRJGCode;
		fileName += TypeParse.parseString(new Date(), "yyMMdd");
		fileName += strReportType;
		fileName += strSendType;
		AutoOrderService autoOrderService = new AutoOrderService();
		String intOrder = autoOrderService.getAutoOrder(fileName, "0000");
		fileName += intOrder;
		fileName += feedbackType;
		fileName += "0";
		fileName += ".txt";

		DownloadResult downloadResult = null;

		StringBuilder stringBuilder = new StringBuilder();
		
		List<Object> changedObjectListJC = new ArrayList<Object>();
		List<Object> changedObjectListMX = new ArrayList<Object>();
		List<Object> allReportList = new ArrayList<Object>();
		
		String reportDate=TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
		//报文内记录条数
		int strBWNJLTS=0;
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String head = "";
			head += "A";
			head += strVersion;
			head += strJRJGCode;
			
			head += reportDate;
			head += report;
			if (strReportType.equals("31")) {
				if (report.equals("51")) {
					head += "  ";
				}
			}
			head += "                              ";
			stringBuilder.append(head);
			stringBuilder.append(System.getProperty("line.separator"));

			int count = 0;
			if (report.equals("51")) {//删除报文（批量信贷业务数据删除请求）
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_QYZXPLXDYWSJSC.class);
				detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
				for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
					if(entry.getValue().indexOf(",") > -1){
						String[] strValues = entry.getValue().split(",");
						detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
					}
					else{
						detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
					}
				}
				List<AutoDTO_QYZXPLXDYWSJSC> objectList = (List<AutoDTO_QYZXPLXDYWSJSC>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				
				count=SendQYZXPLXDYWSJSC(objectList,count,stringBuilder,changedObjectListJC,allReportList);
				strBWNJLTS=count;
			} else {//正常报文
				String[] xxjllxList = xxjllxMap.get(report).split(",");
				for (String xxjllx : xxjllxList) {
					// 生成基础段信息
					String reportDto = reportMap.get(xxjllx);
					String[] reportDtos = reportDto.split(",");
					String baseDto = reportDtos[0].split("%")[1];
					
				    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
				    detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
				    for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
						if(entry.getValue().indexOf(",") > -1){
							String[] strValues = entry.getValue().split(",");
							detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
						}
						else{
							detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
						}
					}

					List<Object> objectList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
					
					count=SendZXPTZCBW(count,objectList,changedObjectListJC,allReportList,baseDto,reportDtos,changedObjectListMX,xxjllx,stringBuilder);
					strBWNJLTS +=count;
				}
			}

			String end = "";
			end += "Z";
			DecimalFormat df = new DecimalFormat("0000000000");
			end += df.format(count);

			stringBuilder.append(end);

			if (i != selectReport.length - 1) {
				stringBuilder.append(System.getProperty("line.separator"));
				stringBuilder.append(System.getProperty("line.separator"));
			}
		}

		
		String systemPath = ServletActionContext.getServletContext().getRealPath("/");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String datePath = reportFilePath + format.format(new Date());
		File dateFile = new File(datePath);
		if (!dateFile.exists() && !dateFile.isDirectory()) {
			dateFile.mkdirs();
		}
		String filePath = datePath + "/" + fileName;
		File txtFile = new File(filePath);
		if (!txtFile.exists()) {
			txtFile.createNewFile();
		} else {
			txtFile.delete();
		}
		FileOutputStream outStream = new FileOutputStream(txtFile);
		outStream.write(stringBuilder.toString().getBytes("GBK"));
		outStream.close();

		CUCheckResult currCUCheckResult = new CUCheckResult();
		try{
			currCUCheckResult = checkFile.checkall(systemPath + v_file, systemPath+ dic_file, filePath);
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			MessageResult messageResult=new MessageResult();
			messageResult.setMessage("预校验报文体错误");
			messageResult.getMessageSet().add(ex.getMessage().replace("\'", "").replace("\"", ""));
			messageResult.setSuccess(false);
			messageResult.AlertTranslate();
			return messageResult;
		}
		byte[] buffer = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath.replace(".txt", ".zip")));
		if (currCUCheckResult.getErrorVector().size() > 0) {//有错误，生成bad文件
			File badFile = new File(filePath.replace(".txt", ".bad"));
			if (badFile.exists()) {
				badFile.delete();
			} else {
				badFile.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(badFile), "gbk");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(currCUCheckResult.dump());
			writer.close();
			File[] files = { txtFile, badFile };
			for (int i = 0; i < files.length; i++) {
				FileInputStream fis = new FileInputStream(files[i]);
				out.putNextEntry(new ZipEntry(files[i].getName()));
				int len;
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();

		} else {//生成enc文件
			try{
				boolean isPass = CryptAPI.enCryptCompressFile(filePath.replace(
						"\\", "/"), (filePath.replace(".txt", ".enc")).replace(
								"\\", "/"), systemPath + crypConfig, systemPath
								+ crypPublickey, 1, "1.0");
				if (isPass) {
					File encFile = new File(filePath.replace(".txt", ".enc"));
					//获取参数值，是否生成辅助txt文件
					SystemParam systemParam=HelpTool.getSystemParam("isAutoGenerateTxtFile");
					//需要生成辅助txt文件
					if(systemParam!=null && systemParam.getStrEnable().equals("1")){
						File[] files = { txtFile, encFile };
						for (int i = 0; i < files.length; i++) {
							FileInputStream fis = new FileInputStream(files[i]);
							out.putNextEntry(new ZipEntry(files[i].getName()));
							int len;
							while ((len = fis.read(buffer)) > 0) {
								out.write(buffer, 0, len);
							}
							out.closeEntry();
							fis.close();
						}
					}
					//不需要生成辅助txt文件（默认为不需要）
					else{
						File files=encFile;
						FileInputStream fis = new FileInputStream(files);
						out.putNextEntry(new ZipEntry(files.getName()));
						int len;
						while ((len = fis.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						out.closeEntry();
						fis.close();
					}
				}

				out.close();
			}
			catch(Exception ex){
				ApplicationManager.getActionExceptionLog().error("====================================================================================================");
				ApplicationManager.getActionExceptionLog().error("filePath:"+filePath);
				ApplicationManager.getActionExceptionLog().error("filePath.replace('\\', '/'):"+filePath.replace("\\", "/"));
				ApplicationManager.getActionExceptionLog().error("filePath.replace('.txt', '.enc'):"+filePath.replace(".txt", ".enc"));
				ApplicationManager.getActionExceptionLog().error("systemPath + crypConfig:"+systemPath + crypConfig);
				ApplicationManager.getActionExceptionLog().error("systemPath+ crypPublickey:"+systemPath+ crypPublickey);
				ApplicationManager.getActionExceptionLog().error(ex.getMessage().replace("\'", "").replace("\"", ""));
				ApplicationManager.getActionExceptionLog().error("====================================================================================================");
				ExceptionLog.CreateLog(ex);
				//throw new Exception(ex);
				MessageResult messageResult=new MessageResult();
				messageResult.setMessage("文件路径异常");
				messageResult.getMessageSet().add("文件路径:"+filePath);
				messageResult.getMessageSet().add("文件加密路径:"+filePath.replace(".txt", ".enc"));
				messageResult.getMessageSet().add("密钥路径:"+systemPath + crypConfig);
				messageResult.getMessageSet().add("公钥路径:"+systemPath+ crypPublickey);
				messageResult.getMessageSet().add(ex.getMessage().replace("\'", "").replace("\"", ""));
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
				
			}
			//设置明细表报送状态
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			if(this.strReportType.equals("31")){
				for (Object object : changedObjectListJC) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "2");
					ReflectOperation.setFieldValue(object, "extend1", fileName);
					ReflectOperation.setFieldValue(object, "extend4", reportDate.substring(0, 8));
				}
				singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
			}
			else if (this.strReportType.equals("11")||this.strReportType.equals("12")||this.strReportType.equals("14")) {
				
				for (Object object : changedObjectListJC) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "2");
				}
				for (Object object : changedObjectListMX) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "2");
					ReflectOperation.setFieldValue(object, "extend1", fileName);
					ReflectOperation.setFieldValue(object, "extend4", reportDate.substring(0, 8));
					Object extendObject=ReflectOperation.getFieldValue(object, "extend2");
					if(extendObject==null || extendObject.toString().equals("")){
						ReflectOperation.setFieldValue(object, "extend2", YWFSRQ);
					}
					
				}
				singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
				
			}
			//统计任务层报送状态
			Map<String, String> bwlxMap = ShowContext.getInstance().getShowEntityMap().get("BWLXDTO");
			for (int i = 0; i < selectReport.length; i++) {
				String baseDto = bwlxMap.get(selectReport[i]);
				String bwlx = baseDto.substring(baseDto.indexOf("QY"));

				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
				List<TaskFlow> taskFlowList = (List<TaskFlow>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

				singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
				detachedCriteria.add(Restrictions.eq("strTableName", bwlx));
				List<ReportModel_Table> reportModel_TableList = (List<ReportModel_Table>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
				detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
				
				if(instInfoSubList.size()>0 && taskFlowList.size()>0){
					detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
					
					if(taskFlowList.size()>0){
						detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
					}
					if(reportModel_TableList.size()>0){
						detachedCriteria.add(Restrictions.in("reportModel_Table",reportModel_TableList));
					}
					
					List<TaskModelInst> listObject = (List<TaskModelInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

					for (TaskModelInst taskModelInst : listObject) {
						int sendedCout = 0;
						int unSendCout = 0;
						int unAllSendCout = 0;
						
						IParamObjectResultExecute singleObjectFindCountByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
						CommonUpdateReportStatusService CommonStatus=new CommonUpdateReportStatusService();
						sendedCout= CommonStatus.findByDetachedCriteria("RPTSendType", "2", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
						
						unSendCout= CommonStatus.findByDetachedCriteria("RPTSendType", "1", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
						
						unAllSendCout= CommonStatus.findByDetachedCriteria("RPTSendType", "5", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
					
						if(sendedCout==0 && unSendCout>=0 && unAllSendCout==0){
							taskModelInst.setRPTSendType("1");
						}else if(sendedCout>0 && unSendCout==0 && unAllSendCout==0){
							taskModelInst.setRPTSendType("2");
						}
						else {
							taskModelInst.setRPTSendType("5");
						}
					}
					
					singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {listObject, null });
				}
			}
			
			fileDescription = ShowContext.getInstance().getShowEntityMap().get("QYAndGRReportType").get(strReportType);
			new RejectReportService().SendedRejectReport("21", fileDescription, fileName, "1");  //上报成功，已报送报文驳回表新增数据
			
			new GenerAndAnaInsertService().InsertE_RPT(fileName, strJRJGCode, reportDate.substring(0, 8), fileDescription, "正常数据", strBWNJLTS);//上报成功，企业征信报送流转概况表新增数据
		}

		downloadResult = fileHandler.GetStreamResult(fileName.replace(".txt",".zip"), null);
		FileInputStream inputStream = new FileInputStream(filePath.replace(".txt", ".zip"));
		downloadResult.setInputStream(inputStream);

		new DownloadResourceService().DownloadResource(filePath.replace(".txt", ".zip"), "21", "1",fileDescription); // 下载资源库新增一条数据
		
		return downloadResult;
	}
	/**
	 * 上报企业征信批量信贷业务数据删除
	 * @param objectList 上报的数据对象列表
	 * @param count 数据的条数
	 * @param stringBuilder
	 * @param changedObjectListJC 基础段对象列表（用于后面改变状态使用）
	 * @param allReportList 所有数据对象列表
	 * @return 数据的条数
	 * @throws Exception
	 */
	public int SendQYZXPLXDYWSJSC(List<AutoDTO_QYZXPLXDYWSJSC> objectList,int count,StringBuilder stringBuilder,List<Object> changedObjectListJC,List<Object> allReportList) throws Exception{
		for (AutoDTO_QYZXPLXDYWSJSC qYZXPLXDYWSJSC : objectList) {
			count++;
			String dataLine = "";
			dataLine += qYZXPLXDYWSJSC.getStrDeleteBusiType();
			dataLine += qYZXPLXDYWSJSC.getStrJRJGCode();
			for(int j=0;j< 11- qYZXPLXDYWSJSC.getStrJRJGCode().length();j++){
				dataLine += " ";
			}
			dataLine += qYZXPLXDYWSJSC.getStrDKKCode();
			for(int j=0;j< 16- qYZXPLXDYWSJSC.getStrDKKCode().length();j++){
				dataLine += " ";
			}
			String strZHTBH=qYZXPLXDYWSJSC.getStrZHTBH();
			if(strZHTBH==null || strZHTBH.toString().equals("")){
				strZHTBH="";
			}
			dataLine += strZHTBH;
			for(int j=0;j< 60- strZHTBH.getBytes("GBK").length;j++){
				dataLine += " ";
			}
			stringBuilder.append(dataLine);
			stringBuilder.append(System.getProperty("line.separator"));
			changedObjectListJC.add(qYZXPLXDYWSJSC);
			allReportList.add(qYZXPLXDYWSJSC);
		}
		return count;
	}
	/**
	 * 上报征信平台正常报文	
	 * @param count 数据的条数
	 * @param objectList 上报的数据对象列表
	 * @param changedObjectListJC 基础段对象列表（用于后面改变状态使用）
	 * @param allReportList 所有数据对象列表
	 * @param baseDto 基础段dto
	 * @param reportDtos 报送的dto
	 * @param changedObjectListMX 明细段对象列表（用于后面改变状态使用）
	 * @param xxjllx 信息记录类型
	 * @param stringBuilder
	 * @return 数据的条数
	 * @throws Exception
	 */
	public int SendZXPTZCBW(int count,List<Object> objectList,List<Object> changedObjectListJC,List<Object> allReportList,String baseDto,String[] reportDtos,List<Object> changedObjectListMX,String xxjllx,StringBuilder stringBuilder) throws Exception{
		
		Map<String, String> baseDtoFieldMap = ShowContext.getInstance().getShowEntityMap().get(baseDto);
		Map<String, String> n_TYPE_FieldMap = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (Object object : objectList) {//循环基础段
			changedObjectListJC.add(object);
			allReportList.add(object);
			
			String[] n_type_fields_jc = null;//获取基础段的N型数据 20160701 insert by xiajieli 
			for (Map.Entry<String, String> n_type_entry : n_TYPE_FieldMap.entrySet()) {
				if(n_type_entry.getKey()!=null && n_type_entry.getKey().equals(object.getClass().getName())){
					n_type_fields_jc = n_TYPE_FieldMap.get(object.getClass().getName()).split(",");
				}
			}
			
			//报文对应明细段只允许一条数据的情况，记录当前明细数据索引
			
			Map<String,Integer> mapOneToOneMx = new HashMap<String,Integer>();
			Set<Object> setOneToOneMx = new HashSet<Object>();
			
			while(true){
				
				//报文对应明细段只允许一条数据的情况，中断标识
				boolean indexBreak = true;
				
				//同一基础段中存在不同的业务发生日期集合
				Set<String> setYWFSRQ = new HashSet<String>();

				while(true){
					String dataLine = "";
					dataLine += reportDtos[0].split("%")[0];

					for (Map.Entry<String, String> entry : baseDtoFieldMap.entrySet()) {
						Object value = null;
						if(entry.getKey().equals("YWFSRQ")){
							value = "********";
						}else{
							value = ReflectOperation.getFieldValue(object,entry.getKey());
						}
						
						if (value == null || value.toString().equals("")) {
							String empty = "";
							for (int j = 0; j < Integer.parseInt(entry.getValue()); j++) {
								empty += " ";
							}
							dataLine += empty;
						} else {
							String empty = "";
			    			boolean flagisExist=false;
			    			if(n_type_fields_jc!=null && n_type_fields_jc.length>0){
								for(String n_type_field : n_type_fields_jc){
									if(n_type_field.equals(entry.getKey())){
										for (int j = 0; j < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; j++) {
											empty += "0";
										}
										flagisExist=true;
										dataLine += empty + value.toString();
										break;
							
						              }
					              }
				              }
			    			if(!flagisExist){
								for (int j = 0; j < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; j++) {
									empty += " ";
								}
								dataLine += value.toString() + empty;
							}
			    		}
							
					}
					String mxLine = "";
					// 循环生成明细段信息
					//当前业务发生日期
					String currentYWFSRQ="";
					//中断标识
					boolean isBreak = true;
					for (int j = 1; j < reportDtos.length; j++) {
						String dtoMX = reportDtos[j].split("%")[1];
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMX));
						String fieldName = "";
						for (Field field : ReflectOperation.getReflectFields(Class.forName(dtoMX))) {
							if (field.getType().equals(object.getClass())) {
								fieldName = field.getName();
								break;
							}
						}
						
						for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
							if(entry.getKey().equals("RPTCheckType") || entry.getKey().equals("RPTSendType") || entry.getKey().equals("RPTFeedbackType")){
								if(entry.getValue().indexOf(",") > -1){
									String[] strValues = entry.getValue().split(",");
									detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
								}
								else{
									detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
								}
							}
						}
						
						detachedCriteria.add(Restrictions.eq(fieldName,object));
						//根据业务发生日期排序
						String orderbySqlFormulaASC=" case when (this_.extend2='' or this_.extend2 is null) then '"+YWFSRQ+"' else this_.extend2 end asc";
						detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormulaASC));

						//报文对应明细段只允许一条数据的情况，过滤非当前明细索引的数据。
						List<Object> objectMXList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });

						Map<String, String> dtoMXFieldMap = ShowContext.getInstance().getShowEntityMap().get(dtoMX);
						
						String[] n_type_fields = null;
						for (Map.Entry<String, String> n_type_entry : n_TYPE_FieldMap.entrySet()) {
							if(n_type_entry.getKey().equals(dtoMX)){
								n_type_fields = n_TYPE_FieldMap.get(dtoMX).split(",");
							}
						}
						
						//同一明细DTO中，业务发生日期个数
						int repeatCount = 0;
						for (Object objectMX : objectMXList) {//循环明细段
							
							if(mxOneToOneDtoNameList.contains(dtoMX) && setOneToOneMx.contains(objectMX)){
								continue;
							}

							Object extend2Obj = ReflectOperation.getFieldValue(objectMX, "extend2");
							String extend2Str = "";
							if(extend2Obj != null && !extend2Obj.toString().equals("")){
								extend2Str = extend2Obj.toString();
							}
							else{
								extend2Str = this.YWFSRQ;
							}
							//第一个未写报文业务发生日期数据入口
							if(!setYWFSRQ.contains(extend2Str)){
								repeatCount++;
								//repeatCount > 1 同一明细段如果出现2次及其以上未写入明细报文的业务发生日期，表示需要再下次循环写入，并当前明细数据肯定不应该写入当前业务发生日期。
								//!currentYWFSRQ.equals("") 其它业务段数据在当前业务发生日期还未写完时，不应该把其它业务发生日期的数据库写入，表示需要再下次循环写入，并当前明细数据肯定不应该写入当前业务发生日期。
								if(repeatCount > 1 || !currentYWFSRQ.equals("")){
									isBreak = false;
									continue;
								}
							}
							//非第一条数据在同一明细段中相同业务发生日期入口
							else{
								//过滤非当前业务发生日期的数据
								if(!extend2Str.equals(currentYWFSRQ)){
									continue;
								}
							}
							currentYWFSRQ = extend2Str;
							setYWFSRQ.add(currentYWFSRQ);

							changedObjectListMX.add(objectMX);
							
							//20160222 insert by xiajieli 
							String BGLX="";//变更类型取值
							Boolean isAdd=true;//是否添加当前明细段
							if(j==1){//标识变更段
								if(ReflectOperation.getFieldValue(objectMX, "BGLX")!=null){
									BGLX=ReflectOperation.getFieldValue(objectMX, "BGLX").toString();
								}
								if(BGLX!=null && reportMapMatchToQYZXBGLX.get(xxjllx)!=null && !reportMapMatchToQYZXBGLX.get(xxjllx).contains((BGLX) )){//与配置的相匹配，就加标识变更段
									isAdd=false;
								}
							}
							
							if(isAdd){
								if (objectMXList.size() > 0) {
									mxLine += reportDtos[j].split("%")[0];
								}
								for (Map.Entry<String, String> entry : dtoMXFieldMap.entrySet()) {
									
									Object value = ReflectOperation.getFieldValue(objectMX, entry.getKey());
									
									if (value == null || value.toString().equals("")) {
										String empty = "";
										boolean flagisExist=false;
										
										if(!flagisExist){
											for (int k = 0; k < Integer.parseInt(entry.getValue()); k++) {
													empty += " ";
											}
										}
										mxLine += empty;
									} else {
										String empty = "";
										boolean flagisExist=false;
										if(n_type_fields!=null && n_type_fields.length>0){
											for(String n_type_field : n_type_fields){
												if(n_type_field.equals(entry.getKey())){
													for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
														empty += "0";
													}
													flagisExist=true;
													mxLine += empty + value.toString();
													break;
												}
												
											}
											
										}
										if(!flagisExist){
											for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
												empty += " ";
											}
											mxLine += value.toString() + empty;
										}
										
									}
								}
							}
							
							if(mxOneToOneDtoNameList.contains(dtoMX)){
								if(!setOneToOneMx.contains(objectMX)){
									setOneToOneMx.add(objectMX);
								}
								if(mapOneToOneMx.get(dtoMX) == null){
									mapOneToOneMx.put(dtoMX, 1);
								}
								else{
									Integer value = mapOneToOneMx.get(dtoMX);
									value = value + 1;
									mapOneToOneMx.put(dtoMX, value);
								}
								if(mapOneToOneMx.get(dtoMX) < objectMXList.size()){
									indexBreak = false;
								}
								else{
									indexBreak = true;
								}
								break;
							}
						}
					}
					
					if(mxLine.equals("")){
						dataLine = "";
						//count = count-1;
					}else{//mxLine.startsWith("C")表示‘标识变更段’，mxLine.getBytes().length=62表示‘标识变更段’的长度
						if(!mxLine.startsWith("C") || mxLine.getBytes().length != 62){
							count++;
							DecimalFormat df = new DecimalFormat("0000");
							dataLine = dataLine + mxLine;
							String dataLinePrivous = df.format(dataLine.getBytes("GBK").length + 6);
							dataLinePrivous += xxjllx;

							dataLine = dataLinePrivous + dataLine;
							dataLine = dataLine.replace("********", currentYWFSRQ);
							stringBuilder.append(dataLine);
							stringBuilder.append(System.getProperty("line.separator"));
						}
					}
					
					if(isBreak){
						break;
					}
				}
				
				if(indexBreak){
					break;
				}
			}
		}
		return count;
		
	}
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public String getInstType() {
		return instType;
	}

	public void setStrSendType(String strSendType) {
		this.strSendType = strSendType;
	}

	public String getStrSendType() {
		return strSendType;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public String getStrReportType() {
		return strReportType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setStrJRJGCode(String strJRJGCode) {
		this.strJRJGCode = strJRJGCode;
	}

	public String getStrJRJGCode() {
		return strJRJGCode;
	}

	public void setSelectReport(String[] selectReport) {
		this.selectReport = selectReport;
	}

	public String[] getSelectReport() {
		return selectReport;
	}

	public void setStrVersion(String strVersion) {
		this.strVersion = strVersion;
	}

	public String getStrVersion() {
		return strVersion;
	}

	public void setStrJRJGCodeRela(String strJRJGCodeRela) {
		this.strJRJGCodeRela = strJRJGCodeRela;
	}

	public String getStrJRJGCodeRela() {
		return strJRJGCodeRela;
	}

	public void setStrInstReportRela(String strInstReportRela) {
		this.strInstReportRela = strInstReportRela;
	}

	public String getStrInstReportRela() {
		return strInstReportRela;
	}

	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}

	public Map<String, String> getReportMap() {
		return reportMap;
	}

	public Map<String, String> getXxjllxMap() {
		return xxjllxMap;
	}

	public void setXxjllxMap(Map<String, String> xxjllxMap) {
		this.xxjllxMap = xxjllxMap;
	}

	public String getCrypConfig() {
		return crypConfig;
	}

	public void setCrypConfig(String crypConfig) {
		this.crypConfig = crypConfig;
	}

	public String getCrypPublickey() {
		return crypPublickey;
	}

	public void setCrypPublickey(String crypPublickey) {
		this.crypPublickey = crypPublickey;
	}

	public String getCryptKeystore() {
		return cryptKeystore;
	}

	public void setCryptKeystore(String cryptKeystore) {
		this.cryptKeystore = cryptKeystore;
	}

	public String getV_file() {
		return v_file;
	}

	public void setV_file(String vFile) {
		v_file = vFile;
	}

	public String getDic_file() {
		return dic_file;
	}

	public void setDic_file(String dicFile) {
		dic_file = dicFile;
	}

	public String getBatchFile() {
		return batchFile;
	}

	public void setBatchFile(String batchFile) {
		this.batchFile = batchFile;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public void setYWFSRQ(String yWFSRQ) {
		YWFSRQ = yWFSRQ;
	}

	public String getYWFSRQ() {
		return YWFSRQ;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}

	public void setMxOneToOneDtoNameList(List<String> mxOneToOneDtoNameList) {
		this.mxOneToOneDtoNameList = mxOneToOneDtoNameList;
	}

	public List<String> getMxOneToOneDtoNameList() {
		return mxOneToOneDtoNameList;
	}

	public Map<String, String> getReportMapMatchToQYZXBGLX() {
		return reportMapMatchToQYZXBGLX;
	}

	public void setReportMapMatchToQYZXBGLX(
			Map<String, String> reportMapMatchToQYZXBGLX) {
		this.reportMapMatchToQYZXBGLX = reportMapMatchToQYZXBGLX;
	}
	
	
}
