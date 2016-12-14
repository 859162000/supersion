package zxptsystem.service.imps;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;
import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import report.service.imps.CommonUpdateReportStatusService;
import report.service.imps.DownloadResourceService;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLGX;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLSC;
import zxptsystem.dto.AutoDTO_GRZXSJSC;
import zxptsystem.dto.AutoDTO_GRZXZHBSBG;
import zxptsystem.dto.GRZXWFKBWCL;

import com.icfcc.batch.ui.PreConditionCheck;
import com.opensymphony.xwork2.ActionContext;
import com.sun.source.tree.Tree;

import coresystem.dto.InstInfo;
import coresystem.service.imps.AutoOrderService;
import extend.dto.ReportModel_Table;
import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
import framework.show.ShowContext;
/**
 * 个人征信报文生成重写
 * @author liu@Copy
 *
 */

public class OverRidDownLoadGRZXRportService implements IObjectResultExecute{

	
	private String systemCode;

	private String instType;

	private String strGrJRJGCode;

	private String strGrReportType;

	private String strGrSendType;

	private String feedbackType;

	private String[] selectReport;

	private String strDataVersion;

	private String strProgramVersion;

	private String strGrJRJGCodeRela;

	private String strInstGrReportRela;

	private String contactName;

	private String contactPhone;

	private String reportFilePath;
	
	//报文生成界面新增数据发生年月6位
	private String strSJFSNY;
	
	//报文生成界面新增数据年月
	private String endGRSJFSNY;
	
	private String beginGRSJFSNY;
	
	private List<InstInfo> instInfoSubList;

	private Map<String, String> reportMap;
	
	private Map<String, String> downloadJudgeStatusMap;
	
	private int strBWNJLTS;
	
	private String reportFilePathName;
	
	private  int count;
	
	
	@Override
	public Object objectResultExecute() throws Exception {
		         
		         
		        //校验未通过返回错误文本
				 if(ServletActionContext.getContext().getApplication().get("strGRXXCheck")!=null
						&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strGRXXCheck").toString()))
				    {//个人
					ZXDowndLoadCheckFileService ZXDowndLoadCheckFileService=new ZXDowndLoadCheckFileService();
					return ZXDowndLoadCheckFileService.ZXWriteCheckFile("grzx", ServletActionContext.getContext().getApplication().get("strGRXXCheck").toString());
			        }
		        
				this.selectReport = (String[]) RequestManager.getIdList();
				this.strGrReportType=(String) ActionContext.getContext().getSession().get("strGrReportType");
				this.strGrJRJGCode=(String) ActionContext.getContext().getSession().get("strGrJRJGCode");
				this.endGRSJFSNY=(String)ActionContext.getContext().getSession().get("endGRSJFSNY");
				this.beginGRSJFSNY=(String)ActionContext.getContext().getSession().get("beginGRSJFSNY");
				String key="grzx"+"&"+strGrReportType+"&"+strGrJRJGCode;
				ServletActionContext.getContext().getApplication().put(key, "1");
				try{
					Object objSJFSNY=ActionContext.getContext().getSession().get("objSJFSNY");
					MessageResult messageResult = new MessageResult();
					
					if(objSJFSNY !=null && !objSJFSNY.toString().equals("")){
						this.strSJFSNY=(String) objSJFSNY;
					}else{
						this.strSJFSNY=HelpTool.getBeforeDate("yyyyMM");
					}
					
					this.feedbackType = "0";
					if(strGrReportType!=null){
						if(strGrReportType.equals("1")){
							selectReport = new String[] {"1"};
						}else if(strGrReportType.equals("4")){
							selectReport = new String[] {"4"};
						}else if(strGrReportType.equals("8")){
							selectReport = new String[] {"8"};
						}//新增历史逾期记录更新报文
						else if(strGrReportType.equals("9")){
							selectReport = new String[] {"9"};
						}//历史逾期记录删除报文
						else if(strGrReportType.equals("A")){
							selectReport = new String[] {"A"};
						}
					}

					//报文头[报文生成时间]年份必须在2004[含]以后
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
					Date reportTime= df.parse(df.format(new Date()));
					Date time=TypeParse.parseDate("20040101");
					if(reportTime.before(time)){
						messageResult.getMessageSet().add("报文头[报文生成时间]年份必须在2004[含]以后");
					}
					
					if (StringUtils.isBlank(systemCode)) {
						messageResult.getMessageSet().add("应用系统代码不能为空");
					}
					if (StringUtils.isBlank(instType)) {
						messageResult.getMessageSet().add("机构类型不能为空");
					}
					if (StringUtils.isBlank(strGrJRJGCode)) {
						messageResult.getMessageSet().add("金融机构代码不能为空");
					}
					if (StringUtils.isBlank(strGrReportType)) {
						messageResult.getMessageSet().add("报文文件种类不能为空");
					}
					if (StringUtils.isBlank(feedbackType)) {
						messageResult.getMessageSet().add("反馈类型不能为空");
					}
					
					if (selectReport == null || selectReport.length == 0) {
						messageResult.getMessageSet().add("没有选择生成报文");
					}

					if(!StringUtils.isBlank(strGrJRJGCode)){
						instInfoSubList = new ArrayList<InstInfo>();
						
						IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstInfo.class);
						detachedCriteriaInstCode.add(Restrictions.eq("strReportInstCode",strGrJRJGCode));
						detachedCriteriaInstCode.add(Restrictions.eq("suit.strSuitCode","22"));
						List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
						if(reportInstInfoList.size() > 0){
						    contactName = reportInstInfoList.get(0).getStrReportContact();
							contactPhone = reportInstInfoList.get(0).getStrReportContactTel();
						}
						singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
						detachedCriteriaInstCode.add(Restrictions.eq("reportInstInfo.strReportInstCode",strGrJRJGCode));
						List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
						if(reportInstSubInfoList.size()>0){
							for (ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList) {
								Object obj=ReflectOperation.getFieldValue(reportInstSubInfo, "instInfo");
								String strInstCode=ReflectOperation.getFieldValue(obj, "strInstCode").toString();
								InstInfo instInfo = new InstInfo();
								instInfo.setStrInstCode(strInstCode);
								instInfoSubList.add(instInfo);
							}
						}
						else{
							messageResult.getMessageSet().add("请在报送机构管理下添加子机构");
						}
					}
					
					if(StringUtils.isBlank(contactName)){
						messageResult.getMessageSet().add("请在报送机构管理下添加联系人");
					}
					if(StringUtils.isBlank(contactPhone)){
						messageResult.getMessageSet().add("请在报送机构管理下添加联系电话");
					}
					
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					
					Object grzx_reportFilePath = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "grzx_reportFilePath" , null});
					 String randomUUID = UUID.randomUUID().toString();
					  randomUUID = randomUUID.replaceAll("-","");
					if(grzx_reportFilePath != null) {
						SystemParam systemParam = (SystemParam)grzx_reportFilePath;
						if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
							reportFilePathName=strGrJRJGCode+beginGRSJFSNY+endGRSJFSNY;
							reportFilePath=systemParam.getStrParamValue()+strGrJRJGCode+beginGRSJFSNY+endGRSJFSNY+"\\";
						}
					}
					 if(StringUtils.isBlank(reportFilePath)){
						 messageResult.getMessageSet().add("请在参数管理下配置个人征信生成报文路径");
					 }
					 
					 Object grzx_dataVersion = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "grzx_dataVersion" , null});
					 if(grzx_dataVersion != null) {
						SystemParam systemParam = (SystemParam)grzx_dataVersion;
						if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
							strDataVersion=systemParam.getStrParamValue();
						}
					}
					 if(StringUtils.isBlank(strDataVersion)){
						 messageResult.getMessageSet().add("请在参数管理下配置个人征信数据格式版本号");
					 }
					 
					 Object grzx_programVersion = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "grzx_programVersion" , null});
					 if(grzx_programVersion != null) {
						SystemParam systemParam = (SystemParam)grzx_programVersion;
						if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
							strProgramVersion=systemParam.getStrParamValue();
						}
					}
					 if(StringUtils.isBlank(strProgramVersion)){
						 messageResult.getMessageSet().add("请在参数管理下配置个人征信上传报文版本号");
					 }
					
					if (messageResult.getMessageSet().size() > 0) {
						messageResult.setMessage("生成失败");
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					} else {
						count=0;
						return ForeachGenerateReport();
					}
				}
				catch(Exception ex){
					ExceptionLog.CreateLog(ex);
					return new MessageResult(false,"生成报文异常");
				}
				finally{
					ServletActionContext.getContext().getApplication().put(key, null);
					ActionContext.getContext().getSession().put("strGrJRJGCode", "");
					ActionContext.getContext().getSession().put("strGrReportType", "");
					ActionContext.getContext().getSession().put("objSJFSNY", "");
				}
	}
	
	@SuppressWarnings("unchecked")
	public  Object ForeachGenerateReport() throws Exception{
		Date datebegin=new SimpleDateFormat("yyyyMMdd").parse(beginGRSJFSNY);
		Date dateEnd=new SimpleDateFormat("yyyyMMdd").parse(endGRSJFSNY);
		//路径下文件夹,zip若存在,删除
		File pathfile=new File(reportFilePath.substring(0, reportFilePath.length()-1));
		SmallTools.delAllFile(reportFilePath.substring(0, reportFilePath.length()-1));
		File zipfile=new File(reportFilePath.substring(0, reportFilePath.length()-1)+".zip");
		if(zipfile.exists()){
			zipfile.delete();
		}
	
		while(datebegin.getTime()!=dateEnd.getTime()){
			strSJFSNY=endGRSJFSNY.substring(0, endGRSJFSNY.length()-2);
			 Object generateReport = GenerateReport();
			 if(generateReport!=null){
				 return generateReport;
			 }
			 beginGRSJFSNY=this.getafterDay("yyyyMMdd", beginGRSJFSNY);
			 datebegin=new SimpleDateFormat("yyyyMMdd").parse(beginGRSJFSNY);
		}
		if(datebegin.getTime()==dateEnd.getTime()){
			strSJFSNY=endGRSJFSNY.substring(0, endGRSJFSNY.length()-2);
			 Object generateReport = GenerateReport();
			 if(generateReport!=null){
				 return generateReport;
			 }
		}
			String zipPath=reportFilePath.substring(0, reportFilePath.length()-1)+".zip";
			deleteDir(new File(reportFilePath));
			ZipMultiFile(reportFilePath, zipPath);
			DownloadResult downloadResult = null;
			FileHandler fileHandler = new FileHandler();
			String fileName=reportFilePathName+".zip";
			downloadResult = fileHandler.GetStreamResult(fileName, null);
			FileInputStream inputStream = new FileInputStream(zipPath);
			downloadResult.setInputStream(inputStream);
		    String	fileDescription = ShowContext.getInstance().getShowEntityMap().get("QYAndGRReportType").get(strGrReportType);
			new DownloadResourceService().DownloadResource(zipPath, "22", "1",fileDescription); // 下载资源库新增一条数据
			return downloadResult;
	
	}
	
	 public static void ZipMultiFile(String filepath ,String zippath) {
			try {
		        File file = new File(filepath);// 要被压缩的文件夹
		        File zipFile = new File(zippath);
		        byte[] buffer = new byte[1024];
		        InputStream input = null;
		        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		        if(file.isDirectory()){
		            File[] files = file.listFiles();
		            for(int i = 0; i < files.length; ++i){
		            	if(files[i].getName().endsWith("zip")){
			                input = new FileInputStream(files[i]);
			                zipOut.putNextEntry(new ZipEntry(files[i].getName()));
			                int temp = 0;
			                while((temp = input.read(buffer)) >0){
			                    zipOut.write(buffer,0,temp);
			                }
			                input.close();
		            	}
		            	
		            }
		        }
		        zipOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	@SuppressWarnings("unchecked")
	private Object GenerateReport() throws Exception {
		String fileDescription=""; //报文类型描述
		DownloadResult downloadResult = null;
		StringBuilder stringBuilder = new StringBuilder();
		List<Object> allReportList = new ArrayList<Object>();
		List<Object> changedObjectListJC = new ArrayList<Object>();
		List<Object> changedObjectListMX = new ArrayList<Object>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		String fileName = "";
		FileHandler fileHandler = new FileHandler();
		String reportDate=TypeParse.parseString(new Date(), "yyyyMMdd");//当前时间
		if ("1".equals(strGrReportType)) {//正常报文
			fileName +=strGrJRJGCode; 
			fileName += this.beginGRSJFSNY;
			AutoOrderService autoOrderService = new AutoOrderService();
			if(count<10){
				fileName += count;
			}else{
				count=count-10;
				fileName += count;
			}
			fileName += "1";
			autoOrderService.getAutoOrder(fileName, "000");
			String lastOrder = autoOrderService.getAutoOrder(fileName, "000");
			fileName += lastOrder;
			fileName += ".txt";

			downloadResult = fileHandler.GetStreamResult(fileName, null);
            
			List<Object> Invalidlist=new ArrayList<Object>();
			for (int i = 0; i < selectReport.length; i++) {
				String report = selectReport[i];
				
				String reportDto = reportMap.get(report);
				String[] reportDtos = reportDto.split(",");
				String baseDto = reportDtos[0].split("%")[1];

				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
				detachedCriteria.add(Restrictions.eq("JSYHKRQ", this.beginGRSJFSNY));//like("JSYHKRQ", this.strSJFSNY+"%")
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
			   
				//数据加入List
				for (Object object : objectList) {
					Invalidlist.add(object);
				}
				SendGRZXZCBW(objectList,stringBuilder,changedObjectListJC,allReportList,reportDtos,baseDto,detachedCriteria,changedObjectListMX,singleObjectFindByCriteriaDao);
			}
			  //无效数据返回
			if(Invalidlist.size()<1){
				return null;
		   }
			
		} else if ("4".equals(strGrReportType)) {//标识变更报文
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_GRZXZHBSBG.class);
			for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
				if(entry.getValue().indexOf(",") > -1){
					String[] strValues = entry.getValue().split(",");
					detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
				}
				else{
					detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
			}
			List<AutoDTO_GRZXZHBSBG> objectList = (List<AutoDTO_GRZXZHBSBG>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
			if(objectList.size()<1){
				return null;
		   }
			fileName=SendGRZXZHBSBG(objectList,fileName,downloadResult,fileHandler,stringBuilder,changedObjectListJC,allReportList);
			
		} else if ("8".equals(strGrReportType)) {//删除报文
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_GRZXSJSC.class);
			for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
				if(entry.getValue().indexOf(",") > -1){
					String[] strValues = entry.getValue().split(",");
					detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
				}
				else{
					detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
			}
			List<AutoDTO_GRZXSJSC> objectList = (List<AutoDTO_GRZXSJSC>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
			
			if(objectList.size()<1){
				return null;
		   }
			fileName=SendGRZXSJSC(objectList,fileName,downloadResult,fileHandler,stringBuilder,changedObjectListJC,allReportList);
		}
		else if ("9".equals(strGrReportType)) {//历史逾期记录更新报文
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_GRZXLSYQJLGX.class);
			for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
				if(entry.getValue().indexOf(",") > -1){
					String[] strValues = entry.getValue().split(",");
					detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
				}
				else{
					detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
			}
			List<AutoDTO_GRZXLSYQJLGX> objectList = (List<AutoDTO_GRZXLSYQJLGX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
			
			if(objectList.size()<1){
				return null;
		   }
			fileName=SendGRZXLSYQJLGX(objectList,fileName,downloadResult,fileHandler,stringBuilder,changedObjectListJC,allReportList);
		}
		else if ("A".equals(strGrReportType)) {//历史逾期记录删除报文
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_GRZXLSYQJLSC.class);
			for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
				if(entry.getValue().indexOf(",") > -1){
					String[] strValues = entry.getValue().split(",");
					detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
				}
				else{
					detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
			}
			List<AutoDTO_GRZXLSYQJLSC> objectList = (List<AutoDTO_GRZXLSYQJLSC>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
			if(objectList.size()<1){
				return null;
		   }
			fileName=SendGRZXLSYQJLSC(objectList,fileName,downloadResult,fileHandler,stringBuilder,changedObjectListJC,allReportList);
		}
		count++;
		String systemPath = ServletActionContext.getServletContext().getRealPath("/");
		String datePath = reportFilePath;
		File dateFile = new File(datePath);
		if (!dateFile.exists() && !dateFile.isDirectory()) {
				dateFile.mkdirs();
		}
		String filePath = datePath + File.separator + fileName;
		File txtFile = new File(filePath);
		if (!txtFile.exists()) {
			txtFile.createNewFile();
		} else {
			txtFile.delete();
		}
		FileOutputStream outStream = new FileOutputStream(txtFile);
		outStream.write(stringBuilder.toString().getBytes("GBK"));
		outStream.close();
		
		File reportFile = new File(filePath);
		this.initConfigForGrzx(systemPath);
		
		PreConditionCheck prc = new PreConditionCheck(systemPath+"conf"+File.separator+"grzx"+File.separator);
		
		String sresult ="";
		
		if ("1".equals(strGrReportType)){//正常报文
			try{
				sresult= prc.checkHead(filePath);
			}
			catch(Exception ex){
				ExceptionLog.CreateLog(ex);
				MessageResult messageResult=new MessageResult();
				messageResult.setMessage("预校验报文头异常");
				messageResult.getMessageSet().add(ex.getMessage().replace("\'", "").replace("\"", ""));
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
			}
		}
		
		File badFile = new File(filePath.replace(".txt", ".bad"));
		if (badFile.exists()) {
			badFile.delete();
		} else {
			badFile.createNewFile();
		}
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(badFile), "GBK");
		BufferedWriter writer = new BufferedWriter(write);
		StringBuilder badStringBuilder = new StringBuilder();
		if (sresult != null && sresult.trim().equals("")) {
			if("1".equals(strGrReportType)){//正常报文
				ArrayList list = this.getBytesFromFile(reportFile);
				ArrayList result = new ArrayList();
				try{
					result=prc.checkrecord(list);
				}
				catch(Exception ex){
					ExceptionLog.CreateLog(ex);
					MessageResult messageResult=new MessageResult();
					messageResult.setMessage("预校验报文体异常");
					messageResult.getMessageSet().add(ex.getMessage().replace("\'", "").replace("\"", ""));
					messageResult.setSuccess(false);
					messageResult.AlertTranslate();
					return messageResult;
				}
				//读取校验结果
				for (int i = 0; i < result.size(); i++) {
					if(result.get(i)!=null&&!(result.get(i).equals(""))){
						String errorCode = result.get(i).toString();
						badStringBuilder.append("第" + (i+3) + "行: " +errorCode+System.getProperty("line.separator"));
					}
				}
			}
		} else {
			badStringBuilder.append("报文头校验失败:" + sresult);
		}
		writer.write(badStringBuilder.toString());
		writer.close();
		
		byte[] buffer = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath.replace(".txt", ".zip")));
		
		if(badStringBuilder.toString() ==null || badStringBuilder.toString().equals("")){//正确的报文，生成enc
			// 压缩
			try{
				if (prc.compressFile(filePath, filePath.replace(".txt", ".tmp"))) {
					System.out.println("压缩成功");
				} 
				else {
					System.out.println("压缩失败");
				}
			// 加密
				if (prc.cryptMsg(filePath.replace(".txt", ".tmp"),filePath.replace(".txt", ".enc"))) {
					System.out.println("加密成功");
				}
				else {
					System.out.println("加密失败");
				}
				//获取参数值，是否生成辅助txt文件
				SystemParam systemParam=HelpTool.getSystemParam("isAutoGenerateTxtFile");
				File encFile = new File(filePath.replace(".txt", ".enc"));
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

				out.close();
			}
			catch(Exception ex){
				ApplicationManager.getActionExceptionLog().error("====================================================================================================");
				ApplicationManager.getActionExceptionLog().error("filePath:"+filePath);
				ApplicationManager.getActionExceptionLog().error("filePath.replace('\\', '/'):"+filePath.replace("\\", "/"));
				ApplicationManager.getActionExceptionLog().error("filePath.replace('.txt', '.enc'):"+filePath.replace(".txt", ".enc"));
				ApplicationManager.getActionExceptionLog().error("====================================================================================================");
				ExceptionLog.CreateLog(ex);
				MessageResult messageResult=new MessageResult();
				messageResult.setMessage("路径出错");
				messageResult.getMessageSet().add("文件路径:"+filePath);
				messageResult.getMessageSet().add("文件加密路径:"+filePath.replace(".txt", ".enc"));
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
			}
			
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			GRZXWFKBWCL gRZXWFKBWCL=new GRZXWFKBWCL();
			gRZXWFKBWCL.setStrJRJGCode(this.strGrJRJGCode);
			gRZXWFKBWCL.setStrBWM(fileName);
			gRZXWFKBWCL.setStrBWSCRQ(reportDate);
			gRZXWFKBWCL.setStrGrReportType(this.strGrReportType);
			gRZXWFKBWCL.setStrBWCL("1");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{gRZXWFKBWCL,null});
			
			//设置明细表报送状态
			String baseDto="";
			String bwlx="";
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			if (this.strGrReportType.equals("8") || this.strGrReportType.equals("4")  || this.strGrReportType.equals("9") || this.strGrReportType.equals("A")) {
				for (Object object : changedObjectListJC) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "2");
					ReflectOperation.setFieldValue(object, "extend1", fileName);
					ReflectOperation.setFieldValue(object, "extend4", reportDate);
				}
			
				singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
				if(this.strGrReportType.equals("4")){
					baseDto = "zxptsystem.dto.AutoDTO_GRZXZHBSBG";
					bwlx = "GRZXZHBSBG";
				}else if(this.strGrReportType.equals("8")){
					baseDto = "zxptsystem.dto.AutoDTO_GRZXSJSC";
					bwlx = "GRZXSJSC";
				}else if(this.strGrReportType.equals("9")){
					baseDto = "zxptsystem.dto.AutoDTO_GRZXLSYQJLGX";
					bwlx = "GRZXLSYQJLGX";
				}else if(this.strGrReportType.equals("A")){
					baseDto = "zxptsystem.dto.AutoDTO_GRZXLSYQJLSC";
					bwlx = "GRZXLSYQJLSC";
				}
				
			}
			else if (this.strGrReportType.equals("1")) {
				Calendar ca= Calendar.getInstance();
				ca.setTime(new Date());
				ca.add(Calendar.DATE, -1);
				for (Object object : changedObjectListJC) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "2");
					ReflectOperation.setFieldValue(object, "extend1", fileName); //报文名称基础段和明细段的extend1都要保存
					ReflectOperation.setFieldValue(object, "extend4", reportDate);
				}
				for (Object object : changedObjectListMX) {
					ReflectOperation.setFieldValue(object, "RPTSendType", "2");
					ReflectOperation.setFieldValue(object, "extend1", fileName);
					ReflectOperation.setFieldValue(object, "extend4", reportDate);
				}
				
				singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] { allReportList, null });

				baseDto = "zxptsystem.dto.AutoDTO_GR_GRXX_JC";
				bwlx = "GR_GRXX_JC";
			}
			
			//统计任务层报送状态
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
					
					CommonUpdateReportStatusService comonStatus=new CommonUpdateReportStatusService();
					
					sendedCout = comonStatus.findByDetachedCriteria("RPTSendType", "2", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
					
					unSendCout = comonStatus.findByDetachedCriteria("RPTSendType", "1", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
					
					unAllSendCout = comonStatus.findByDetachedCriteria("RPTSendType", "5", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
					
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
			
			fileDescription = ShowContext.getInstance().getShowEntityMap().get("QYAndGRReportType").get(strGrReportType);
			new RejectReportService().SendedRejectReport("22", fileDescription, fileName, "1"); //报送成功，新增一条已报送报文驳回数据
			
			new GenerAndAnaInsertService().InsertI_RPT(fileName, strGrJRJGCode, reportDate, fileDescription, strBWNJLTS);//上报成功，个人征信报送流转概况表新增数据
			
		}else{//错误的报文，生成bad
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
		return null;
	}
	
	//删除方法
	private static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	            	if(!children[i].endsWith(".zip")){
	            	 deleteDir(new File(dir, children[i]));
	            	}
	                
	            }
	        }
	        return dir.delete();
	    }
	   
	private void initConfigForGrzx(String path) {
		com.icfcc.batch.Constants.SYSTEM_CONFIG_FILE_NAME = "batch.xml";
		com.icfcc.batch.Constants.SYSTEM_MESSAGE_DESCRIPTOR_CONFIG_NAME = "message.xml";
		com.icfcc.batch.Constants.SYSTEM_PREPARE_CONFIG_NAME = "prepare.xml";
		com.icfcc.batch.Constants.SYSTEM_DATABASE_CONFIG_NAME = "database.xml";
		com.icfcc.batch.Constants.SYSTEM_DATABASE_PROXY_NAME = "dispatchProxy.xml";
		com.icfcc.batch.Constants.SYSTEM_PUBLIC_KEY = "public.key";
		com.icfcc.batch.Constants.SYSTEM_KEYSORE_NAME = ".keystore";
		//System.setProperty("Home",path + "conf/grzx/");
	}
	
	
	
	public String getZZandZWJSYHKRQ(ArrayList<String> JSYHKRQList){
		int i,min,max;
		min=max=Integer.parseInt(JSYHKRQList.get(0).toString());
		for(i=0;i<JSYHKRQList.size();i++){
			if(Integer.parseInt(JSYHKRQList.get(i).toString())>max){
				max=Integer.parseInt(JSYHKRQList.get(i).toString());
			}
			if(Integer.parseInt(JSYHKRQList.get(i).toString())<min){
				min=Integer.parseInt(JSYHKRQList.get(i).toString());
			}
		}
		
		return min+""+max;
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
	
	public void SendGRZXZCBW(List<Object> objectList,StringBuilder stringBuilder,List<Object> changedObjectListJC,List<Object> allReportList,String[] reportDtos,String baseDto,DetachedCriteria detachedCriteria,List<Object> changedObjectListMX,IParamObjectResultExecute singleObjectFindByCriteriaDao) throws Exception{
		int count = 0;
		String head = "";
		head += strDataVersion;
		head += strGrJRJGCode;
		head += beginGRSJFSNY+"000000";
		head += strProgramVersion;
		head += "1";
		head += "1";
		
		String ZZandZWJSYHKRQ = "";
		if(objectList.size()>0){
			ArrayList<String> JSYHKRQList = new ArrayList<String>();
			for (Object object : objectList) {
				String JSYHKRQ = ReflectOperation.getFieldValue(object, "JSYHKRQ")+"";
				JSYHKRQList.add(JSYHKRQ);
			}
			ZZandZWJSYHKRQ = this.getZZandZWJSYHKRQ(JSYHKRQList);
		 }else{
			ZZandZWJSYHKRQ=strSJFSNY+"01"+strSJFSNY+"01";
		 }
			
			count = objectList.size();
			strBWNJLTS=count;
			DecimalFormat df = new DecimalFormat("0000000000");
			head += df.format(count);
			head += ZZandZWJSYHKRQ;
			head += formatString(contactName, 30, " ");
			head += formatString(contactPhone, 25, " ");
			head += formatString("", 30, " ");
	
			stringBuilder.append(head);
			stringBuilder.append(System.getProperty("line.separator"));
			stringBuilder.append(System.getProperty("line.separator"));
			
			Map<String, String> baseDtoFieldMap = ShowContext.getInstance().getShowEntityMap().get(baseDto);
			Map<String, String> n_TYPE_FieldMap = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field");//N型
			
			String[] n_type_fields_jc = null;//获取基础段N型数据 20160701 insert by xiajieli 
			for (Map.Entry<String, String> n_type_entry : n_TYPE_FieldMap.entrySet()) {
				if(n_type_entry.getKey()!=null && n_type_entry.getKey().equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
					n_type_fields_jc = n_TYPE_FieldMap.get("zxptsystem.dto.AutoDTO_GR_GRXX_JC").split(",");
				}
			}
			
			for (Object object : objectList) {//基础段
	
				changedObjectListJC.add(object);
	
				allReportList.add(object);
				String dataLine = "";
		    	dataLine += reportDtos[0].split("%")[0];
		    	
		    	for(Map.Entry<String, String> entry : baseDtoFieldMap.entrySet()){
		    		Object value = ReflectOperation.getFieldValue(object, entry.getKey());
		    		if(value == null || value.toString().equals("")){
		    			String empty = "";
		    			for(int j=0;j<Integer.parseInt(entry.getValue());j++){
		    				empty += " ";
		    			}
		    			dataLine += empty;
		    		}
		    		else{
		    			String empty = "";
		    			
		    			boolean flagisExist=false;
		    			if(n_type_fields_jc!=null && n_type_fields_jc.length>0){
							for(String n_type_field : n_type_fields_jc){
								if(n_type_field.equals(entry.getKey())){
									for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
										empty += "0";
									}
									flagisExist=true;
									dataLine += empty + value.toString();
									break;
						
					              }
				              }
			              }
		    			if(!flagisExist){
							for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
								empty += " ";
							}
							dataLine += value.toString() + empty;
						}
		    		}
		    	}
	
		    	for(int j=1;j<reportDtos.length;j++){
		    		
		    		String dtoMX = reportDtos[j].split("%")[1];
		    		detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMX));
		    		String fieldName = "";
		    		for(Field field :ReflectOperation.getReflectFields(Class.forName(dtoMX))){
		    			if(field.getType().equals(object.getClass())){
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
		    		List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    		if(objectMXList.size() == 1){
		    			dataLine += reportDtos[j].split("%")[0];
		    		}
		    		
		    		Map<String,String> dtoMXFieldMap = ShowContext.getInstance().getShowEntityMap().get(dtoMX);
		    		
		    		String[] n_type_fields = null;
					
					for (Map.Entry<String, String> n_type_entry : n_TYPE_FieldMap.entrySet()) {
						if(n_type_entry.getKey()!=null && n_type_entry.getKey().equals(dtoMX)){
							n_type_fields = n_TYPE_FieldMap.get(dtoMX).split(",");
						}
					}
		    		
		    		for(Object objectMX : objectMXList){
		    			if(objectMXList.size() > 1){
			    			dataLine += reportDtos[j].split("%")[0];
			    		}
		    			changedObjectListMX.add(objectMX);
		    			for(Map.Entry<String, String> entry : dtoMXFieldMap.entrySet()){
				    		Object value = ReflectOperation.getFieldValue(objectMX, entry.getKey());
				    		if(value == null || value.toString().equals("")){
				    			String empty = "";
				    			for(int k=0;k<Integer.parseInt(entry.getValue());k++){
				    				empty += " ";
				    			}
				    			dataLine += empty;
				    		}
				    		else{
				    			String empty = "";
				    			boolean flagisExist=false;
				    			if(n_type_fields!=null && n_type_fields.length>0){
									for(String n_type_field : n_type_fields){
										if(n_type_field.equals(entry.getKey())){
											for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
												empty += "0";
											}
											flagisExist=true;
											dataLine += empty + value.toString();
											break;
								
							              }
						              }
					              }
				    			if(!flagisExist){
									for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
										empty += " ";
									}
									dataLine += value.toString() + empty;
								}
				    		}
				    	}
		    		}
				}
				DecimalFormat dft = new DecimalFormat("0000");
				String dataLinePrivous = dft.format(dataLine.getBytes("GBK").length + 4);
	
				dataLine = dataLinePrivous + dataLine;
				stringBuilder.append(dataLine);
				stringBuilder.append(System.getProperty("line.separator"));
			}
			
		}
	
	private String getafterDay(String from,String dNow){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(from);
		Date dNowD=null;
		try {
			
			dNowD = simpleDateFormat.parse(dNow);
		} catch (ParseException e) {
			e.printStackTrace();
		}//当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNowD);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH,+1);  //设置为后一天
		dBefore = calendar.getTime();   //得到前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat(from); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前一天
		return defaultStartDate;
	}
	
	//个人账户标识
		public String SendGRZXZHBSBG(List<AutoDTO_GRZXZHBSBG> objectList,String fileName,DownloadResult downloadResult,FileHandler fileHandler,StringBuilder stringBuilder,List<Object> changedObjectListJC,List<Object> allReportList) throws Exception{
			fileName += strGrJRJGCode;
			fileName += TypeParse.parseString(new Date(), "yyyyMMdd");
			fileName += "0";
			fileName += "4";
			AutoOrderService autoOrderService = new AutoOrderService();
			String intOrder = autoOrderService.getAutoOrder(fileName, "000");
			fileName += intOrder;
			fileName += ".txt";

			downloadResult = fileHandler.GetStreamResult(fileName, null);

			for (int i = 0; i < selectReport.length; i++) {
				String head = "";
				head += strDataVersion;
				head += TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
				head += strGrJRJGCode;
				head += "4";

				int count = 0;

				count = objectList.size();
				strBWNJLTS=count;
				DecimalFormat df = new DecimalFormat("00000000");
				head += df.format(count);

				stringBuilder.append(head);
				stringBuilder.append(System.getProperty("line.separator"));
				stringBuilder.append(System.getProperty("line.separator"));

				int k=0;
				for (AutoDTO_GRZXZHBSBG gRZXZHBSBG : objectList) {
					String dataLine = "";
					dataLine += gRZXZHBSBG.getStrOldJRJGCode();
					for(int j=0;j<14-gRZXZHBSBG.getStrOldJRJGCode().length();j++){
						dataLine += " ";
					}
					dataLine += gRZXZHBSBG.getStrOldYWCode();
					for(int j=0;j<40-gRZXZHBSBG.getStrOldYWCode().length();j++){
						dataLine += " ";
					}
					dataLine += gRZXZHBSBG.getStrNewJRJGCode();
					for(int j=0;j<14-gRZXZHBSBG.getStrNewJRJGCode().length();j++){
						dataLine += " ";
					}
					dataLine += gRZXZHBSBG.getStrNewYWCode();
					for(int j=0;j<40-gRZXZHBSBG.getStrNewYWCode().length();j++){
						dataLine += " ";
					}
					stringBuilder.append(dataLine);
					
					if(k != objectList.size() - 1){
						stringBuilder.append(System.getProperty("line.separator"));
					}
					k++;

					changedObjectListJC.add(gRZXZHBSBG);
					allReportList.add(gRZXZHBSBG);
				}
			}
			return fileName;
		}
		
		//个人数据删除 
		public String SendGRZXSJSC(List<AutoDTO_GRZXSJSC> objectList,String fileName,DownloadResult downloadResult,FileHandler fileHandler,StringBuilder stringBuilder,List<Object> changedObjectListJC,List<Object> allReportList) throws Exception{
			fileName += strGrJRJGCode;
			fileName += TypeParse.parseString(new Date(), "yyyyMMdd");
			fileName += "0";
			fileName += "8";
			AutoOrderService autoOrderService = new AutoOrderService();
			String intOrder = autoOrderService.getAutoOrder(fileName, "000");
			fileName += intOrder;
			fileName += ".txt";

			downloadResult = fileHandler.GetStreamResult(fileName, null);

			for (int i = 0; i < selectReport.length; i++) {
				String head = "";
				head += strDataVersion;
				head += TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
				head += strGrJRJGCode;
				head += "8";

				int count = objectList.size();
				strBWNJLTS=count;
				DecimalFormat df = new DecimalFormat("00000000");
				head += df.format(count);
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
				stringBuilder.append(System.getProperty("line.separator"));

				int k=0;
				for (AutoDTO_GRZXSJSC gRZXSJSC : objectList) {
					String dataLine = "";
					dataLine += gRZXSJSC.getStrJRJGCode();
					for(int j=0;j<14-gRZXSJSC.getStrJRJGCode().length();j++){
						dataLine += " ";
					}
					dataLine += gRZXSJSC.getStrYWCode();
					for(int j=0;j<40-gRZXSJSC.getStrYWCode().length();j++){
						dataLine += " ";
					}
					dataLine += gRZXSJSC.getQSJSYHKRQ();
					
					String strJSJSYHKRQ=gRZXSJSC.getJSJSYHKRQ();
					if(strJSJSYHKRQ==null || strJSJSYHKRQ.toString().equals("")){
						strJSJSYHKRQ="";
					}
					dataLine += strJSJSYHKRQ;
					for(int j=0;j<8-strJSJSYHKRQ.length();j++){
						dataLine += " ";
					}
					
					stringBuilder.append(dataLine);
					if(k != objectList.size() - 1){
						stringBuilder.append(System.getProperty("line.separator"));
					}
					k++;
					changedObjectListJC.add(gRZXSJSC);
					allReportList.add(gRZXSJSC);
				}
			}
			return fileName;
		}
		
		//历史逾期记录更新
		public String SendGRZXLSYQJLGX(List<AutoDTO_GRZXLSYQJLGX> objectList,String fileName,DownloadResult downloadResult,FileHandler fileHandler,StringBuilder stringBuilder,List<Object> changedObjectListJC,List<Object> allReportList) throws Exception{
			fileName += strGrJRJGCode;
			fileName += TypeParse.parseString(new Date(), "yyyyMMdd");
			fileName += "0";
			fileName += "9";
			AutoOrderService autoOrderService = new AutoOrderService();
			String intOrder = autoOrderService.getAutoOrder(fileName, "000");
			fileName += intOrder;
			fileName += ".txt";

			downloadResult = fileHandler.GetStreamResult(fileName, null);

			for (int i = 0; i < selectReport.length; i++) {
				String head = "";
				head += strDataVersion;
				head += TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
				head += strGrJRJGCode;
				head += "9";

				int count = objectList.size();
				strBWNJLTS=count;
				DecimalFormat df = new DecimalFormat("00000000");
				head += df.format(count);
				if(contactName==null){
					contactName="";
				}
				head += formatString(contactName, 30, " ");
				if(contactPhone==null){
					contactPhone="";
				}
				head += formatString(contactPhone, 25, " ");

				stringBuilder.append(head);
				stringBuilder.append(System.getProperty("line.separator"));
				stringBuilder.append(System.getProperty("line.separator"));

				int k=0;
				for (AutoDTO_GRZXLSYQJLGX GRZXLSYQJLGX : objectList) {
					String dataLine = "";
					
					dataLine += GRZXLSYQJLGX.getXM();
					for(int j=0;j<30-GRZXLSYQJLGX.getXM().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLGX.getZJLX();
					dataLine += GRZXLSYQJLGX.getZJHM();
					for(int j=0;j<18-GRZXLSYQJLGX.getZJHM().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLGX.getJRJGDM();
					for(int j=0;j<14-GRZXLSYQJLGX.getJRJGDM().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLGX.getYWH();
					for(int j=0;j<40-GRZXLSYQJLGX.getYWH().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLGX.getYQYF();
					dataLine += GRZXLSYQJLGX.getCZLX();
					dataLine += GRZXLSYQJLGX.getYQCXYS();
					dataLine += GRZXLSYQJLGX.getYQJE();
					for(int j=0;j<10-GRZXLSYQJLGX.getYQJE().length();j++){
						dataLine += " ";
					}
					
					stringBuilder.append(dataLine);
					if(k != objectList.size() - 1){
						stringBuilder.append(System.getProperty("line.separator"));
					}
					k++;
					changedObjectListJC.add(GRZXLSYQJLGX);
					allReportList.add(GRZXLSYQJLGX);
				}
			}
			return fileName;
		}
		
		public ArrayList getBytesFromFile(File file) throws Exception {
			InputStreamReader read = new InputStreamReader(
					new FileInputStream(file), "GBK");
			BufferedReader bufferedReader = new BufferedReader(read);
			String str;
			int line = 0;
			ArrayList list = new ArrayList();
			int num = 0;
			while ((str = bufferedReader.readLine()) != null) {

				line++;
				if (line == 1 || line == 2) {
					continue;
				} else {
					list.add(num,str.getBytes("GBK"));
					num++;
				}
			}
			return list;
		}
		
		//历史逾期记录删除
		public String SendGRZXLSYQJLSC(List<AutoDTO_GRZXLSYQJLSC> objectList,String fileName,DownloadResult downloadResult,FileHandler fileHandler,StringBuilder stringBuilder,List<Object> changedObjectListJC,List<Object> allReportList) throws Exception{
			fileName += strGrJRJGCode;
			fileName += TypeParse.parseString(new Date(), "yyyyMMdd");
			fileName += "0";
			fileName += "A";
			AutoOrderService autoOrderService = new AutoOrderService();
			String intOrder = autoOrderService.getAutoOrder(fileName, "000");
			fileName += intOrder;
			fileName += ".txt";

			downloadResult = fileHandler.GetStreamResult(fileName, null);

			for (int i = 0; i < selectReport.length; i++) {
				String head = "";
				head += strDataVersion;
				head += TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
				head += strGrJRJGCode;
				head += "A";

				int count = objectList.size();
				strBWNJLTS=count;
				DecimalFormat df = new DecimalFormat("00000000");
				head += df.format(count);
				if(contactName==null){
					contactName="";
				}
				head += formatString(contactName, 30, " ");
				if(contactPhone==null){
					contactPhone="";
				}
				head += formatString(contactPhone, 25, " ");

				stringBuilder.append(head);
				stringBuilder.append(System.getProperty("line.separator"));
				stringBuilder.append(System.getProperty("line.separator"));

				int k=0;
				for (AutoDTO_GRZXLSYQJLSC GRZXLSYQJLSC : objectList) {
					String dataLine = "";
					
					dataLine += GRZXLSYQJLSC.getXM();
					for(int j=0;j<30-GRZXLSYQJLSC.getXM().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLSC.getZJLX();
					dataLine += GRZXLSYQJLSC.getZJHM();
					for(int j=0;j<18-GRZXLSYQJLSC.getZJHM().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLSC.getJRJGDM();
					for(int j=0;j<14-GRZXLSYQJLSC.getJRJGDM().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					dataLine += GRZXLSYQJLSC.getYWH();
					for(int j=0;j<40-GRZXLSYQJLSC.getYWH().getBytes("GBK").length;j++){
						dataLine += " ";
					}
					String strQSYQYF=GRZXLSYQJLSC.getQSYQYF();
					if(strQSYQYF==null || strQSYQYF.toString().equals("")){
						strQSYQYF="";
					}
					dataLine += strQSYQYF;
					for(int j=0;j<6-strQSYQYF.length();j++){
						dataLine += " ";
					}
					
					String strJSYQYF=GRZXLSYQJLSC.getJSYQYF();
					if(strJSYQYF==null || strJSYQYF.toString().equals("")){
						strJSYQYF="";
					}
					dataLine += strJSYQYF;
					for(int j=0;j<6-strJSYQYF.length();j++){
						dataLine += " ";
					}
					
					stringBuilder.append(dataLine);
					if(k != objectList.size() - 1){
						stringBuilder.append(System.getProperty("line.separator"));
					}
					k++;
					changedObjectListJC.add(GRZXLSYQJLSC);
					allReportList.add(GRZXLSYQJLSC);
				}
			}
			return fileName;
		}
		

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public String getStrGrJRJGCode() {
		return strGrJRJGCode;
	}

	public void setStrGrJRJGCode(String strGrJRJGCode) {
		this.strGrJRJGCode = strGrJRJGCode;
	}

	public String getStrGrReportType() {
		return strGrReportType;
	}

	public void setStrGrReportType(String strGrReportType) {
		this.strGrReportType = strGrReportType;
	}

	public String getStrGrSendType() {
		return strGrSendType;
	}

	public void setStrGrSendType(String strGrSendType) {
		this.strGrSendType = strGrSendType;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String[] getSelectReport() {
		return selectReport;
	}

	public void setSelectReport(String[] selectReport) {
		this.selectReport = selectReport;
	}

	public String getStrDataVersion() {
		return strDataVersion;
	}

	public void setStrDataVersion(String strDataVersion) {
		this.strDataVersion = strDataVersion;
	}

	public String getStrProgramVersion() {
		return strProgramVersion;
	}

	public void setStrProgramVersion(String strProgramVersion) {
		this.strProgramVersion = strProgramVersion;
	}

	public String getStrGrJRJGCodeRela() {
		return strGrJRJGCodeRela;
	}

	public void setStrGrJRJGCodeRela(String strGrJRJGCodeRela) {
		this.strGrJRJGCodeRela = strGrJRJGCodeRela;
	}

	public String getStrInstGrReportRela() {
		return strInstGrReportRela;
	}

	public void setStrInstGrReportRela(String strInstGrReportRela) {
		this.strInstGrReportRela = strInstGrReportRela;
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

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getStrSJFSNY() {
		return strSJFSNY;
	}

	public void setStrSJFSNY(String strSJFSNY) {
		this.strSJFSNY = strSJFSNY;
	}

	public List<InstInfo> getInstInfoSubList() {
		return instInfoSubList;
	}

	public void setInstInfoSubList(List<InstInfo> instInfoSubList) {
		this.instInfoSubList = instInfoSubList;
	}

	public Map<String, String> getReportMap() {
		return reportMap;
	}

	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}

	public int getStrBWNJLTS() {
		return strBWNJLTS;
	}

	public void setStrBWNJLTS(int strBWNJLTS) {
		this.strBWNJLTS = strBWNJLTS;
	}

	public String getEndGRSJFSNY() {
		return endGRSJFSNY;
	}

	public void setEndGRSJFSNY(String endGRSJFSNY) {
		this.endGRSJFSNY = endGRSJFSNY;
	}

	public String getBeginGRSJFSNY() {
		return beginGRSJFSNY;
	}

	public void setBeginGRSJFSNY(String beginGRSJFSNY) {
		this.beginGRSJFSNY = beginGRSJFSNY;
	}

}
