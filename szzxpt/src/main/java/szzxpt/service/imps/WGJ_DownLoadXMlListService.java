package szzxpt.service.imps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import report.service.imps.DownloadResourceService;

import zxptsystem.service.imps.RejectReportService;

import com.opensymphony.util.GUID;
import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;

import coresystem.service.imps.AutoOrderService;
import extend.dto.ReportModel_Table;
import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.HandleFileOrFolders;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;

public class WGJ_DownLoadXMlListService implements IObjectResultExecute {

	private String[] downloadXMLServiceBeanList;
	private String tempXMlPath;
	private String targetXMLPath;
	private String downloadType;

	private String strJRJGCode;
	private String[] requstTNameList;
	
	private List<InstInfo> instInfoSubList;
	private Map<String, String> downloadJudgeStatusMap;
	
	
	public class ControlFileClass{
		private String path;
		private String intOrder;
		private String applicationType;
		private String controlType;
		private List<String> fileList;
		public void setPath(String path) {
			this.path = path;
		}
		public String getPath() {
			return path;
		}
		public void setIntOrder(String intOrder) {
			this.intOrder = intOrder;
		}
		public String getIntOrder() {
			return intOrder;
		}
		public void setApplicationType(String applicationType) {
			this.applicationType = applicationType;
		}
		public String getApplicationType() {
			return applicationType;
		}
		public void setControlType(String controlType) {
			this.controlType = controlType;
		}
		public String getControlType() {
			return controlType;
		}
		public void setFileList(List<String> fileList) {
			this.fileList = fileList;
		}
		public List<String> getFileList() {
			return fileList;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		try{
			requstTNameList = new String[]{"WGJ_ACC_CA","WGJ_ACC_CB"};
			MessageResult messageResult = RequestCheck();
			if(!messageResult.isSuccess()){
				return messageResult;
			}
			
			 List<Object> dataList=new ArrayList<Object>();
			
			String downloadPath = getDownloadPath(dataList);

			if(downloadType == null || downloadType.equals("1")){
				 return new DownloadResult();
			}
			else{
				SystemParam systemParam = HelpTool.getSystemParam("whzh_reportFilePath");
				if(systemParam!=null){
					targetXMLPath=systemParam.getStrParamValue();
				}else{
					return new MessageResult("请在系统参数管理下配置报文生成的路径！");
				}
				
				File targetFile = new File(targetXMLPath);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
				File srcFile = new File(downloadPath);
				
				//生成令牌文件Token.lock
				File tokenFile=new File(targetXMLPath+"\\"+"Token.lock");
				if(tokenFile.exists())
				{
				   tokenFile.delete();
				   tokenFile.createNewFile();
				}else
				{
					tokenFile.createNewFile();
				}
				
				FileUtils.copyDirectory(srcFile, targetFile);
				
				String[] tempList = srcFile.list();
				List<File> files = new ArrayList<File>();
				String zipFileName = srcFile.getName();
				for (int i = 0; i < tempList.length; i++) {
					files.add(new File(srcFile.getPath() +"\\"+ tempList[i]));
					zipFileName = tempList[0];
				}
				File tempFile = SmallTools.compressedFiles(files, targetXMLPath, zipFileName+".zip");//压缩成zip
				
				//删除令牌文件Token.lock
				tokenFile.delete();
				
				//删除临时文件夹
				HandleFileOrFolders.DeleteFolder(downloadPath);
				
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{dataList,null});
				
				List<Object> instInfoSubMap=(List<Object>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("instInfoSubList");
				
				//统计任务层报送状态
				updateRPTSendType(requstTNameList,dataList,instInfoSubMap);
				
				new DownloadResourceService().DownloadResource(targetXMLPath+ File.separator +tempFile.getName(), "WHZH", "1","WHZH-外汇账户数据报文"); // 下载资源库新增一条数据
				
				new RejectReportService().SendedRejectReport("WHZH", "外汇账户数据报文", zipFileName, "1");  //已报送报文驳回表新增数据
				
				return new MessageResult("下载成功");
			}
		}
		finally{
			ActionContext.getContext().getSession().put("strJRJGCode", null);
		}
	}

	private MessageResult RequestCheck() throws Exception{
		MessageResult msgResult = new MessageResult();
		
		this.strJRJGCode =(String) ActionContext.getContext().getSession().get("strJRJGCode");

		if(requstTNameList == null || requstTNameList.length ==0){
			msgResult.getMessageSet().add("请选择需要生成的报文");
		}
		
		if(StringUtils.isBlank(strJRJGCode)){
			msgResult.getMessageSet().add("报送金融机构不能为空");
		}else{
			instInfoSubList = new ArrayList<InstInfo>();
			IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
			detachedCriteriaInstCode.add(Restrictions.eq("reportInstInfo.strReportInstCode",strJRJGCode));
			List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
			if(reportInstSubInfoList.size()>0){
				for (ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList) {
					instInfoSubList.add((InstInfo)ReflectOperation.getFieldValue(reportInstSubInfo, "instInfo"));
				}
			}else{
				msgResult.getMessageSet().add("请在报送机构管理下添加子机构");
			}
		}
		
		 if(msgResult.getMessageSet().size()>0)
		{
			msgResult.AlertTranslate();
			msgResult.setSuccess(false);
		}else{
			 ((Map<String,Object>)ServletActionContext.getContext().get("request")).put("instInfoSubList", instInfoSubList);
			 ((Map<String,Object>)ServletActionContext.getContext().get("request")).put("downloadJudgeStatusMap", downloadJudgeStatusMap);
		}
		
		return msgResult;
	} 
	
	
	private String getDownloadPath(List<Object> dataList) throws Exception{
		String path = getTempDownloadDictionary(tempXMlPath);
		path += "\\" + GUID.generateGUID();
		
		File file = new File(path);
		file.mkdirs();
		
		SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
		String strDate = f.format((new Date()).getTime());
		
		AutoOrderService autoOrderService = new AutoOrderService();
		String intOrder = autoOrderService.getAutoOrder(strDate, "00");
		
		Map<String,ControlFileClass> controlMap = new HashMap<String,ControlFileClass>();
		String strUNO=GUID.generateGUID();
		
		for (int i = 0; i < requstTNameList.length; i++) {
			for (int j = 0; j < downloadXMLServiceBeanList.length; j++) {
				WGJ_DownLoadXMlService wGJ_DownLoadXMlService = (WGJ_DownLoadXMlService) FrameworkFactory.CreateBean(downloadXMLServiceBeanList[j]);
				String strdtoName=wGJ_DownLoadXMlService.getDtoName().substring(wGJ_DownLoadXMlService.getDtoName().indexOf("AutoDTO_")+8);
				if (requstTNameList[i].equals(strdtoName)) {
					
					String typePath = path + "\\"+ wGJ_DownLoadXMlService.getApplicationType() + wGJ_DownLoadXMlService.getControlType();
					typePath +=strJRJGCode+ strDate + intOrder;
					
					File typeFile = new File(typePath);
					if(!typeFile.exists()){
						typeFile.mkdirs();
						
						ControlFileClass controlFileClass = new ControlFileClass();
						controlFileClass.setPath(typePath);
						controlFileClass.setApplicationType(wGJ_DownLoadXMlService.getApplicationType());
						controlFileClass.setControlType(wGJ_DownLoadXMlService.getControlType());
						List<String> tempfileList = new ArrayList<String>();
						controlFileClass.setFileList(tempfileList);
						controlFileClass.setIntOrder(intOrder);
						
						controlMap.put(typePath, controlFileClass);
					}
					
					ControlFileClass controlFileClass = controlMap.get(typePath);

					List<String> xmlFileList = wGJ_DownLoadXMlService.GenerateServiceTempXML(dataList,typePath, intOrder,strJRJGCode, strUNO);
					for(String str : xmlFileList){
						controlFileClass.getFileList().add(str);
					}

					break;
				}
			}
		}
		
		for(Map.Entry<String,ControlFileClass> entry: controlMap.entrySet()){
			ControlFileClass controlFileClass = entry.getValue();
			initControlXML(controlFileClass.getPath(),controlFileClass.getIntOrder(),controlFileClass.getApplicationType(),controlFileClass.getControlType(),controlFileClass.getFileList());
		}

		return path;
	}
	
	public void initControlXML(String path,String intOrder,String applicationType,String controlType,List<String> fileList) throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("MSG");
		document.appendChild(root);
		Element apptype = document.createElement("APPTYPE");
		apptype.setTextContent(applicationType);
		root.appendChild(apptype);
		Element currentfile = document.createElement("CURRENTFILE");
		currentfile.setTextContent(applicationType+controlType);
		root.appendChild(currentfile);
		Element inout = document.createElement("INOUT");
		inout.setTextContent("IN");
		root.appendChild(inout);
		Element totalFiles = document.createElement("TOTALFILES");
		totalFiles.setTextContent(String.valueOf(fileList.size()));
		root.appendChild(totalFiles);
		Element recordsNode = document.createElement("FILES");
		for (int i = 0; i < fileList.size(); i++) {

			Element fileName = document.createElement("FILENAME");
			fileName.setTextContent(fileList.get(i));
			recordsNode.appendChild(fileName);
		}
		root.appendChild(recordsNode);


		after(path,document,intOrder,applicationType,controlType);
	
	}
	
	public void after(String path,Document document,String intOrder,String applicationType,String controlType) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(document);
		transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
		transformer.setOutputProperty(OutputKeys.ENCODING, "gb18030");

		// 数据类型 + 12 位机构号 + 6 位日期YYMMDD + 2 位序号，文件扩展名为XML
		// 数据类型的编制规则：应用类型代码+1 位或2 位接口文件类型代码

		SimpleDateFormat f = new SimpleDateFormat("yyMMdd");
		String dtDate = f.format((new Date()).getTime());
		String fileName = applicationType + controlType  +strJRJGCode+ dtDate + intOrder + ".XML";
		
		String filePath = path + "\\" + fileName;
		PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));

		StreamResult result = new StreamResult(pw);
		transformer.transform(source, result);
		pw.close();
	}
	
	private String getTempDownloadDictionary(String relativePath){
		
		try{
			relativePath = ServletActionContext.getServletContext().getRealPath(relativePath);
		} catch (Exception e) {
			String appRootPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath().substring(0).replace("%20", " ");
			appRootPath = appRootPath.substring(0, appRootPath.length()-17);
			relativePath = appRootPath+relativePath;
		}
		return relativePath;
	}
	

	public String[] getDownloadXMLServiceBeanList() {
		return downloadXMLServiceBeanList;
	}

	public void setDownloadXMLServiceBeanList(String[] downloadXMLServiceBeanList) {
		this.downloadXMLServiceBeanList = downloadXMLServiceBeanList;
	}

	public String getTargetXMLPath() {
		return targetXMLPath;
	}

	public void setTargetXMLPath(String targetXMLPath) {
		this.targetXMLPath = targetXMLPath;
	}

	public String getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	

	public String[] getRequstTNameList() {
		return requstTNameList;
	}

	public void setRequstTNameList(String[] requstTNameList) {
		this.requstTNameList = requstTNameList;
	}

	public void setTempXMlPath(String tempXMlPath) {
		this.tempXMlPath = tempXMlPath;
	}

	public String getTempXMlPath() {
		return tempXMlPath;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}
	//统计报送状态
	private void updateRPTSendType(String[] requstTNameList,List<Object> dataList,List<Object> instInfoSubMap) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		for (String strTableName : requstTNameList) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("szzxpt.dto.AutoDTO_"+strTableName));
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
			List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			if(dataList!=null && dataList.size()>0){
				detachedCriteria.add(Restrictions.eq("dtTaskDate", ReflectOperation.getFieldValue(dataList.get(0), "dtDate")));
			}
			List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			List<TaskModelInst>  taskModelInstList=null;
			
			detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			if(instInfoSubList!=null && instInfoSubList.size()>0){
				detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
			}
			detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
			detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
		    taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		    
		    if(taskModelInstList.size()>0){
		    	TaskModelInst taskModelInst=taskModelInstList.get(0);
		    	
				int  sendSuccess=0;
				int  unSend=0;
				int  unAllSendSuccess=0;
			
				unSend = findByDetachedCriteria("RPTSendType", "1", detachedCriteria, strTableName, taskModelInst, singleObjectFindByCountDao);
				
				sendSuccess=findByDetachedCriteria("RPTSendType", "2", detachedCriteria, strTableName, taskModelInst, singleObjectFindByCountDao);
				
				unAllSendSuccess=findByDetachedCriteria("RPTSendType", "5", detachedCriteria, strTableName, taskModelInst, singleObjectFindByCountDao);
				
				if(sendSuccess==0 && unSend>=0 && unAllSendSuccess==0){
					taskModelInst.setRPTSendType("1");
				}else if(sendSuccess>0 && unSend==0 && unAllSendSuccess==0){
					taskModelInst.setRPTSendType("2");
				}
				else {
					taskModelInst.setRPTSendType("5");
				}
				
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{taskModelInst,null});
		    }
			
		}
	}
	
	/**
	 * 通过条件统计
	 * @param status 状态
	 * @param statusValue 状态值
	 * @param detachedCriteria 条件
	 * @param strTableName 表名
	 * @param taskModelInst 任务表对象
	 * @param singleObjectFindByCountDao 查找条数
	 * @return 查找到的条数
	 * @throws Exception
	 */
	private Integer findByDetachedCriteria(String status, String statusValue, DetachedCriteria detachedCriteria, String strTableName, TaskModelInst taskModelInst, IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
		detachedCriteria = DetachedCriteria.forClass(Class.forName("szzxpt.dto.AutoDTO_"+strTableName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
		return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
	}
}
