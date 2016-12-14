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

import com.cfcc.ecus.eft.CryptAPI;
import com.cfcc.ecus.eft.checkFile;
import com.cfcc.ecus.eft.cudata.CUCheckResult;
import com.opensymphony.xwork2.ActionContext;

import report.dto.ReportInstInfo;
import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import report.service.imps.CommonUpdateReportStatusService;
import report.service.imps.DownloadResourceService;
import zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC;
import zxptsystem.dto.AutoDTO_JGXXJBXXSC;
import zxptsystem.dto.AutoDTO_JGXXJZCYXXSC;
import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import coresystem.service.imps.AutoOrderService;
import extend.dto.ReportModel_Table;
import extend.dto.SystemParam;
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
 * 机构信息报文生成
 * @author xiajieli
 *
 */
public class DownLoadJGXXRportService implements IObjectResultExecute {

	private String v_file;
	
	private String dic_file;
	
	private String crypConfig;
	
	private String crypPublickey;
	
	private String cryptKeystore;
	
	private String batchFile;
	
	private String systemCode;
	
	private String instType;

	private String strJgJRJGCode;

	private String strJgReportType;

	private String strJgSendType;

	private String feedbackType;

	private String[] selectReport;

	private String strVersion;

	private String strJgJRJGCodeRela;

	private String strInstJgReportRela;

	private String contactName;

	private String contactPhone;

	private String XXGXRQ;
	
	private String reportFilePath;
	
	private Map<String, String> reportMap;
	
	private List<InstInfo> instInfoSubList;
	
	private Map<String, String> downloadJudgeStatusMap;

	public Object objectResultExecute() throws Exception {
		
		//校验未通过返回错误文本
		if(ServletActionContext.getContext().getApplication().get("strJGXXCheck")!=null
				&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strJGXXCheck").toString())){//机构
			ZXDowndLoadCheckFileService ZXDowndLoadCheckFileService=new ZXDowndLoadCheckFileService();
			return ZXDowndLoadCheckFileService.ZXWriteCheckFile("jgxx", ServletActionContext.getContext().getApplication().get("strJGXXCheck").toString());
		}

		MessageResult messageResult = new MessageResult();
		this.strJgReportType = (String) ActionContext.getContext().getSession().get("strJgReportType");
		this.strJgJRJGCode = (String) ActionContext.getContext().getSession().get("strJgJRJGCode");
		Object objXXGXRQ=ActionContext.getContext().getSession().get("objXXGXRQ");
		
		String key="jgxx"+"&"+strJgReportType+"&"+strJgJRJGCode;
		
		try{
			ServletActionContext.getContext().getApplication().put(key, "1");
			
			if(objXXGXRQ !=null && !objXXGXRQ.toString().equals("")){
				this.XXGXRQ=TypeParse.parseString(TypeParse.parseDate(objXXGXRQ.toString()), "yyyyMMdd");  
			}else{
				this.XXGXRQ=HelpTool.getBeforeDate("yyyyMMdd");
			}
			
			if(strJgReportType!=null){
				if(strJgReportType.equals("51")) {
					selectReport = new String[] {"5100","5101"};
				}
				else if(strJgReportType.equals("32")) {
					this.strJgSendType = "1";
					selectReport = new String[] {"3200","3201"};
				}
			}
			
			this.feedbackType = "0";
			
			if (StringUtils.isBlank(systemCode)) {
				messageResult.getMessageSet().add("应用系统代码不能为空");
			}
			if (StringUtils.isBlank(instType)) {
				messageResult.getMessageSet().add("机构类型不能为空");
			}
			if (StringUtils.isBlank(strJgJRJGCode)) {
				messageResult.getMessageSet().add("金融机构代码不能为空");
			}
			if (StringUtils.isBlank(strJgReportType)) {
				messageResult.getMessageSet().add("报文文件种类不能为空");
			}
			if (StringUtils.isBlank(feedbackType)) {
				messageResult.getMessageSet().add("反馈类型不能为空");
			}
			if (StringUtils.isBlank(XXGXRQ)) {
				messageResult.getMessageSet().add("信息更新日期不能为空");
			}
			if (selectReport == null || selectReport.length == 0) {
				messageResult.getMessageSet().add("没有选择生成报文");
			}

			if(!StringUtils.isBlank(strJgJRJGCode)){
				instInfoSubList = new ArrayList<InstInfo>();
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstInfo.class);
				detachedCriteria.add(Restrictions.eq("strReportInstCode",strJgJRJGCode));
				detachedCriteria.add(Restrictions.or(Restrictions.in("suit.strSuitCode",new String[]{"21","24"}),Restrictions.isNull("suit.strSuitCode")));
				List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(reportInstInfoList.size() > 0){
				    contactName = reportInstInfoList.get(0).getStrReportContact();
					contactPhone = reportInstInfoList.get(0).getStrReportContactTel();
				}
				
				IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
				detachedCriteriaInstCode.add(Restrictions.eq("reportInstInfo.strReportInstCode",strJgJRJGCode));
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
			Object jgxx_strVersion = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "jgxx_strVersion" , null});
			 if(jgxx_strVersion != null) {
				SystemParam systemParam = (SystemParam)jgxx_strVersion;
				if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
					strVersion=systemParam.getStrParamValue();
				}
			}
			 if(StringUtils.isBlank(strVersion)){
				 messageResult.getMessageSet().add("请在参数管理下配置机构信息版本号");
			 }
			 
			 Object jgxx_reportFilePath = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "jgxx_reportFilePath" , null});
			 if(jgxx_reportFilePath != null) {
				SystemParam systemParam = (SystemParam)jgxx_reportFilePath;
				if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
					reportFilePath=systemParam.getStrParamValue();
				}
			}
			 if(StringUtils.isBlank(reportFilePath)){
				 messageResult.getMessageSet().add("请在参数管理下配置机构信息生成报文路径");
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
			ActionContext.getContext().getSession().put("objXXGXRQ", "");
			ActionContext.getContext().getSession().put("strJgJRJGCode", "");
			ActionContext.getContext().getSession().put("strJgReportType", "");
		}
		
	}

	@SuppressWarnings("unchecked")
	private Object GenerateReport() throws Exception {	
		
		String fileDescription=""; //报文类型描述
		FileHandler fileHandler = new FileHandler();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				
		String fileName = "";
		fileName += systemCode;
		fileName += instType;
		fileName += "000000000" + strJgJRJGCode;
		fileName += TypeParse.parseString(new Date(), "yyMMdd");
		fileName += strJgReportType;
		fileName += strJgSendType;
		AutoOrderService autoOrderService = new AutoOrderService();
		String intOrder = autoOrderService.getAutoOrder(fileName, "0000");
		fileName += intOrder;
		fileName += feedbackType;
		fileName += "0";
		fileName += ".txt";

		DownloadResult downloadResult = fileHandler.GetStreamResult(fileName,null);

		StringBuilder stringBuilder = new StringBuilder();

		List<Object> allReportList = new ArrayList<Object>();
		List<Object> changedObjectListJC = new ArrayList<Object>();
		List<Object> changedObjectListMX = new ArrayList<Object>();
		List<Object> changedObjectListSC = new ArrayList<Object>();
		
		String reportDate= TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
		int strBWNJLTS= 0 ;//报文内记录条数
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String head = "";
			head += "A";
			head += strVersion;
			head += strJgJRJGCode;
			
			head += "         " + reportDate;
			head += strJgReportType;
			
			if(report.equals("5100") || report.equals("3200")){
				head += "0";
			}else if(report.equals("3201") || report.equals("5101")){
				head += "1";
			}
			
			head += feedbackType;
			if(contactName==null){
				contactName="";
			}
			head += formatString(contactName, 30, " ");
			if(contactPhone==null){
				contactPhone="";
			}
			head += formatString(contactPhone, 25, " ");
			head += formatString("", 30, " ");
			stringBuilder.append(head);
			stringBuilder.append(System.getProperty("line.separator"));

			int count = 0;

			if (strJgReportType.equals("32")) {//删除报文
				if (report.equals("3200")) {//机构信息基本信息删除
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_JGXXJBXXSC.class);
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
					
					List<AutoDTO_JGXXJBXXSC> objectList = (List<AutoDTO_JGXXJBXXSC>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
					
					count=SendJGXXJBXXSC(count,objectList,changedObjectListSC,stringBuilder,allReportList);
					strBWNJLTS=count;
				}
				if (report.equals("3201")) {//机构信息家族成员信息删除
					
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_JGXXJZCYXXSC.class);
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
					List<AutoDTO_JGXXJZCYXXSC> objectList = (List<AutoDTO_JGXXJZCYXXSC>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
					
					count=SendJGXXJZCYXXSC(count,objectList,changedObjectListSC,stringBuilder,allReportList);
					strBWNJLTS=count;
				}

			} else {//正常报文
				// 生成基础段信息
				String reportDto = reportMap.get(report);
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
				
				count=SendJGXXZCBW(count,objectList,baseDto,changedObjectListJC,allReportList,reportDtos,changedObjectListMX,stringBuilder);
				strBWNJLTS=count;
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
		//有错误，生成bad文件
		if (currCUCheckResult.getErrorVector().size() > 0) {
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

		}
		//无错误，生成enc文件
		else {
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
		 if (allReportList.size()>0 && this.strJgReportType.equals("51")) {
			for (Object object : changedObjectListJC) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
				if(object.getClass().equals(AutoDTO_JZ_JZCYXX_JC.class)){
					if(ReflectOperation.getFieldValue(object, "XXGXRQ")==null || ReflectOperation.getFieldValue(object, "XXGXRQ").toString().equals("")){
						ReflectOperation.setFieldValue(object, "XXGXRQ", XXGXRQ);
					}
					ReflectOperation.setFieldValue(object, "extend1", fileName);
					ReflectOperation.setFieldValue(object, "extend4", reportDate.substring(0, 8));
				}
			}
			for (Object object : changedObjectListMX) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
				ReflectOperation.setFieldValue(object, "extend1", fileName);
				ReflectOperation.setFieldValue(object, "extend4", reportDate.substring(0, 8));
				Object extendObject=ReflectOperation.getFieldValue(object, "extend2");
				if(extendObject==null || extendObject.toString().equals("")){
					ReflectOperation.setFieldValue(object, "extend2", XXGXRQ);
				}
			}
			
			singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });

		}else if(allReportList.size()>0 && this.strJgReportType.equals("32")){
			for (Object object : changedObjectListSC) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
				ReflectOperation.setFieldValue(object, "extend1", fileName);
				ReflectOperation.setFieldValue(object, "extend4", reportDate.substring(0, 8));
			}
			singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
		}
		
		//统计任务层的报送状态
		for (int i = 0; i < 2; i++) {
			String baseDto = "";
			String bwlx ="";
			
			if (allReportList.size()>0 && this.strJgReportType.equals("51")) {
				if(i==0){
					baseDto ="zxptsystem.dto.AutoDTO_JG_JGJBXX_JC";
					bwlx="JG_JGJBXX_JC";
				}
				else if(i==1){
					baseDto ="zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC";
					bwlx="JZ_JZCYXX_JC";
				}
			}else if(allReportList.size()>0 && this.strJgReportType.equals("32")){
				if(i==0){
					baseDto ="zxptsystem.dto.AutoDTO_JGXXJBXXSC";
					bwlx="JGXXJBXXSC";
				}
				else if(i==1){
					baseDto ="zxptsystem.dto.AutoDTO_JGXXJZCYXXSC";
					bwlx="JGXXJZCYXXSC";
				}
			}
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			List<TaskFlow> taskFlowList = (List<TaskFlow>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

			singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			detachedCriteria.add(Restrictions.eq("strTableName", bwlx));
			List<ReportModel_Table> reportModel_TableList = (List<ReportModel_Table>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
			detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			
			if(instInfoSubList.size()>0 && taskFlowList.size()>0){
				detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
				detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
				detachedCriteria.add(Restrictions.in("reportModel_Table",reportModel_TableList));
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
				singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {listObject, null });
			}		
		}
		
		fileDescription = ShowContext.getInstance().getShowEntityMap().get("QYAndGRReportType").get(strJgReportType);
		new RejectReportService().SendedRejectReport("24", fileDescription, fileName, "1");
		
		new GenerAndAnaInsertService().InsertE_RPT(fileName, strJgJRJGCode, reportDate.substring(0, 8), fileDescription, "正常数据", strBWNJLTS);//上报成功，企业征信报送流转概况表新增数据
		
	 }
		downloadResult = fileHandler.GetStreamResult(fileName.replace(".txt",".zip"), null);
		FileInputStream inputStream = new FileInputStream(filePath.replace(".txt", ".zip"));
		downloadResult.setInputStream(inputStream);
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
		
		new DownloadResourceService().DownloadResource(filePath.replace(".txt", ".zip"), "24", "1",fileDescription); // 下载资源库新增一条数据
		
		return downloadResult;
   }
 
	public static String formatString(String str, int length, String slot) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		int count=0;
		if(str!=null){
		   count = length - str.getBytes("GBK").length;
		}
		
		while (count > 0) {
			sb.append(slot);
			count--;
		}

		return sb.toString();
	}
	//机构信息基本信息删除
	public int SendJGXXJBXXSC(int count,List<AutoDTO_JGXXJBXXSC> objectList,List<Object> changedObjectListSC,StringBuilder stringBuilder,List<Object> allReportList) throws Exception{
		count = objectList.size();
		for (AutoDTO_JGXXJBXXSC jGXXJBXXSC : objectList) {
			changedObjectListSC.add(jGXXJBXXSC);
			String dataLine = "";
			dataLine += "B";
			dataLine += jGXXJBXXSC.getStrKHH();
			for(int j=0;j<40-jGXXJBXXSC.getStrKHH().length();j++){
				dataLine += " ";
			}
			dataLine+=jGXXJBXXSC.getStrXSCDXXLB();
			for(int j=0;j<1-jGXXJBXXSC.getStrXSCDXXLB().length();j++){
				dataLine += " ";
			}
			String strGXRLX=jGXXJBXXSC.getStrGXRLX();
			if(strGXRLX==null || strGXRLX.toString().equals("")){
				strGXRLX="";
			}
			dataLine += strGXRLX;
			for(int j=0;j<1-strGXRLX.length();j++){
				dataLine += " ";
			}
			dataLine += jGXXJBXXSC.getStrXXGXRQ();
			
			String strYLZD=jGXXJBXXSC.getStrYLZD();
			if(strYLZD==null || strYLZD.toString().equals("")){
				strYLZD="";
			}
			dataLine += formatString(strYLZD, 40, " ");
			stringBuilder.append(dataLine);
			stringBuilder.append(System.getProperty("line.separator"));
			allReportList.add(jGXXJBXXSC);
		}
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
		return count;
	}
	//机构信息家族成员信息删除报送
	public int SendJGXXJZCYXXSC(int count,List<AutoDTO_JGXXJZCYXXSC> objectList,List<Object> changedObjectListSC,StringBuilder stringBuilder,List<Object> allReportList) throws Exception{
		count = objectList.size();
		for (AutoDTO_JGXXJZCYXXSC jGXXJZCYXXSC : objectList) {
			changedObjectListSC.add(jGXXJZCYXXSC);
			String dataLine = "";
			dataLine += "B";
			
			dataLine += jGXXJZCYXXSC.getStrZYGXRZJLX();
			for(int j=0;j<2-jGXXJZCYXXSC.getStrZYGXRZJLX().length();j++){
				dataLine += " ";
			}
			dataLine += jGXXJZCYXXSC.getStrZYGXRZJHM();
			for(int j=0;j<20-jGXXJZCYXXSC.getStrZYGXRZJHM().getBytes("GBK").length;j++){
				dataLine += " ";
			}
			dataLine += jGXXJZCYXXSC.getStrJZGX();
			for(int j=0;j<1-jGXXJZCYXXSC.getStrJZGX().length();j++){
				dataLine += " ";
			}
			dataLine += jGXXJZCYXXSC.getStrJZCYZJLX();
			for(int j=0;j<2-jGXXJZCYXXSC.getStrJZCYZJLX().length();j++){
				dataLine += " ";
			}
			dataLine += jGXXJZCYXXSC.getStrJZCYZJHM();
			for(int j=0;j<20-jGXXJZCYXXSC.getStrJZCYZJHM().getBytes("GBK").length;j++){
				dataLine += " ";
			}
			dataLine += jGXXJZCYXXSC.getStrXXGXRQ();
			String strYLZD=jGXXJZCYXXSC.getStrYLZD();
			if(strYLZD==null || strYLZD.toString().equals("")){
				strYLZD="";
			}
			dataLine += formatString(strYLZD, 40, " ");
			
			stringBuilder.append(dataLine);
			stringBuilder.append(System.getProperty("line.separator"));
			allReportList.add(jGXXJZCYXXSC);
		}
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
		return count;
	}
	
	//机构信息正常报文
	public int SendJGXXZCBW(int count,List<Object> objectList,String baseDto,List<Object> changedObjectListJC,List<Object> allReportList,String[] reportDtos,List<Object> changedObjectListMX,StringBuilder stringBuilder) throws Exception{
		Map<String, String> baseDtoFieldMap = ShowContext.getInstance().getShowEntityMap().get(baseDto);
		Map<String, String> n_TYPE_FieldMap = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (Object object : objectList) {
			changedObjectListJC.add(object);
		
			allReportList.add(object);
			
			//同一基础段中存在不同的业务发生日期集合
			Set<String> setYWFSRQ = new HashSet<String>();
			while(true){
			String dataLine = "";
			dataLine += reportDtos[0].split("%")[0];

			for (Map.Entry<String, String> entry : baseDtoFieldMap.entrySet()) {
				Object value = null;
				if(entry.getKey().equals("XXGXRQ")){
					if(object.getClass().equals(AutoDTO_JZ_JZCYXX_JC.class)){
						if(ReflectOperation.getFieldValue(object, "XXGXRQ")!=null && !ReflectOperation.getFieldValue(object, "XXGXRQ").toString().equals("")){
							value=ReflectOperation.getFieldValue(object, "XXGXRQ").toString();
						}else{
							value="********";
						}
					}else{
						value="********";
					}
					
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
					for (int j = 0; j < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; j++) {
						empty += " ";
					}
					dataLine += value.toString() + empty;
				}
			}
			// 循环生成明细段信息
			String mxLine = "";
			// 循环生成明细段信息
			//当前业务发生日期
			String currentXXGXRQ="";
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
				
				detachedCriteria.add(Restrictions.eq(fieldName, object));
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
				for (Object objectMX : objectMXList) {
					Object extend2Obj = ReflectOperation.getFieldValue(objectMX, "extend2");
					String extend2Str = "";
					if(extend2Obj != null && !extend2Obj.toString().equals("")){
						extend2Str = extend2Obj.toString();
					}
					else{
						extend2Str = this.XXGXRQ;
					}
					//第一个未写报文业务发生日期数据入口
					if(!setYWFSRQ.contains(extend2Str)){
						repeatCount++;
						//repeatCount > 1 同一明细段如果出现2次及其以上未写入明细报文的业务发生日期，表示需要再下次循环写入，并当前明细数据肯定不应该写入当前业务发生日期。
						//!currentXXGXRQ("") 其它业务段数据在当前业务发生日期还未写完时，不应该把其它业务发生日期的数据库写入，表示需要再下次循环写入，并当前明细数据肯定不应该写入当前业务发生日期。
						if(repeatCount > 1 || !currentXXGXRQ.equals("")){
							isBreak = false;
							continue;
						}
					}
					//非第一条数据在同一明细段中相同业务发生日期入口
					else{
						//过滤非当前业务发生日期的数据
						if(!extend2Str.equals(currentXXGXRQ)){
							continue;
						}
					}
					currentXXGXRQ = extend2Str;
					setYWFSRQ.add(currentXXGXRQ);
					
					changedObjectListMX.add(objectMX);
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
		}
			
			count++;
			dataLine = dataLine + mxLine;
			if(StringUtils.isBlank(currentXXGXRQ)){
				currentXXGXRQ = this.XXGXRQ;
			}
			dataLine = dataLine.replace("********", currentXXGXRQ);
			stringBuilder.append(dataLine);
			stringBuilder.append(System.getProperty("line.separator"));
				
			if(isBreak){
				break;
			}
		}
	}
	return count;
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

	public List<InstInfo> getInstInfoSubList() {
		return instInfoSubList;
	}

	public void setInstInfoSubList(List<InstInfo> instInfoSubList) {
		this.instInfoSubList = instInfoSubList;
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

	public void setStrJgSendType(String strJgSendType) {
		this.strJgSendType = strJgSendType;
	}

	public String getStrJgSendType() {
		return strJgSendType;
	}

	public void setStrJgReportType(String strJgReportType) {
		this.strJgReportType = strJgReportType;
	}

	public String getStrJgReportType() {
		return strJgReportType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedbackType() {
		return feedbackType;
	}
	
	public void setStrJgJRJGCode(String strJgJRJGCode) {
		this.strJgJRJGCode = strJgJRJGCode;
	}

	public String getStrJgJRJGCode() {
		return strJgJRJGCode;
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

	public void setStrJgJRJGCodeRela(String strJgJRJGCodeRela) {
		this.strJgJRJGCodeRela = strJgJRJGCodeRela;
	}

	public String getStrJgJRJGCodeRela() {
		return strJgJRJGCodeRela;
	}

	public void setStrInstJgReportRela(String strInstJgReportRela) {
		this.strInstJgReportRela = strInstJgReportRela;
	}

	public String getStrInstJgReportRela() {
		return strInstJgReportRela;
	}

	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}

	public Map<String, String> getReportMap() {
		return reportMap;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}

	public void setXXGXRQ(String xXGXRQ) {
		XXGXRQ = xXGXRQ;
	}

	public String getXXGXRQ() {
		return XXGXRQ;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setCryptKeystore(String cryptKeystore) {
		this.cryptKeystore = cryptKeystore;
	}

	public String getCryptKeystore() {
		return cryptKeystore;
	}

	public void setBatchFile(String batchFile) {
		this.batchFile = batchFile;
	}

	public String getBatchFile() {
		return batchFile;
	}

}
