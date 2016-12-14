package eas.service.imps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import report.dto.DownloadResource;
import report.dto.InstCodeMap;
import report.dto.ReportInstInfo;
import report.dto.ReportInstSubInfo;
import report.helper.GetFilePath;
import report.service.imps.DownloadResourceService;

import com.ibm.db2.jcc.b.j;
import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.imps.BaseDTService;
import framework.services.interfaces.IFileHandlerForText;
import framework.services.interfaces.imps.FileHandlerForText;
import framework.show.ShowContext;

@SuppressWarnings("unused")
public class InterfaceDownEastService extends BaseDTService{
	
	private String suitCode;
	private String logPath;
	private String checkDataPath;
	private String execShFile;
	private String isCheck;
	
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public void setExecShFile(String execShFile) {
		this.execShFile = execShFile;
	}

	public void setCheckDataPath(String checkDataPath) {
		this.checkDataPath = checkDataPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public void setSuitCode(String suitCode) {
		this.suitCode = suitCode;
	}

	@Override
	public void initSuccessResult() throws Exception {

		super.initSuccessResult();

		String[] fileList=RequestManager.getReportCheckParam().get("strCheckbox").split(",");	//文件列表
		String reportInstinfo = RequestManager.getReportCheckParam().get("reportInstinfo");  // 因单例模式所限，在此先获取，进行传值
		
		List<String> nbjgh=findInstInfo(reportInstinfo, "strInstCode", "1");
		
		String jrxkzh = "";
		List<String> jrxkzhList = findInstCodeMapField(reportInstinfo, "strInstCode", "1");  // 因单例模式所限，在此先获取，进行传值
		if(jrxkzhList.size()>0){
			jrxkzh = jrxkzhList.get(0);
		}

		String createFormat=RequestManager.getReportCheckParam().get("createFormat");  // 同上
		String endDate=RequestManager.getReportCheckParam().get("endDate");  // 同上
		String startDate=RequestManager.getReportCheckParam().get("startDate");			//开始日期
		SimpleDateFormat simpleName = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "EastReport_"+simpleName.format(new Date())+".zip";
		
		downloadEasReport easThread = new downloadEasReport();
		easThread.setActionContext(ActionContext.getContext());
		easThread.setFileList(fileList);
		easThread.setJrxkzh(jrxkzh);
		easThread.setDateEnd(endDate);
		easThread.setDateStart(startDate);
		easThread.setNbjgh(nbjgh);
		easThread.setFileName(fileName);
		easThread.setCreateFormat(createFormat);
		Thread thr = new Thread(easThread);
		thr.start();
		
		this.setSuccessMessage("执行成功,请耐心等待报文生成完成后下载!");
	}

	class downloadEasReport implements Runnable{

		String[] FileList;
		String Jrxkzh;
		String DateEnd;
		String DateStart;
		List<String> Nbjgh;
		ActionContext actionContext;
		String fileName;
		String createFormat;
		
		public void run() {
			
			try {
				ServletActionContext.setContext(actionContext);
				new DownloadResourceService().addStart(fileName, suitCode); // 新增离线下载记录

				GetFilePath getFilePath = new GetFilePath();
				List<File> fileList = new ArrayList<File>(); // 文件列表，用于文件打包、文件下载
				ArrayList<fileListThread> threadList = new ArrayList<fileListThread>();
				
				for (int i = 0; i < FileList.length; i++) {
					
					StringBuilder tableCode = new StringBuilder();
					String tableName = FileList[i].trim();
					List<Field> fieldList = findFieldList(tableName, tableCode);
					String fileName = Jrxkzh+"-"+(tableCode+"").replace(suitCode+"_", "")+"-"+(DateEnd.replace("-", ""));
					
					String filePath = getFilePath.getTmpFilePath()+"/"+fileName+".txt";
					
					if(checkDataPath!=null && !checkDataPath.equals("")){
						filePath = checkDataPath+"/"+fileName+".txt";;
					}
					
					String statement = montageStat(tableName, fieldList, DateStart, DateEnd, Nbjgh); // 传入表名，返回查询语句
					
					fileListThread fileListThread = new fileListThread();
					fileListThread.setFilePath(filePath);
					fileListThread.setStatement(statement);
					fileListThread.setFieldList(fieldList);
					fileListThread.start();
		
					threadList.add(fileListThread);
					if(i == FileList.length-1 || (i>=9)){
						int j = 0;
						while(threadList.size() != 0){
							fileListThread getFileNameThread = threadList.get(j);
							if(getFileNameThread.isAlive()){ // 判断此线程是否执行完成，未完成， 循环
								if((j != 0 && j%9 == 0) || (j==(threadList.size()-1))){
									Thread.sleep(500);
									j=0;
								}else{
									j++;
								}
							}else{
								getFileNameThread.join();
								File logFileName = writeLog(getFileNameThread.fileName); // 生成日志
								fileList.add(getFileNameThread.fileName); // 形成文件列表
								fileList.add(logFileName);
								threadList.remove(j);
								if(i < FileList.length-1){
									break;
								}
								if(((j != 0) && (j%9 == 0)) || (j==threadList.size())){
									j=0;
								}
							}
						}
					}
				}
				
				if(createFormat!=null && (createFormat.equals("1") || createFormat.equals("2"))){
					SmallTools.compressFilesZip(fileList, getFilePath.getDownloadResourcePath(), fileName); // 将文件打包
				}
				
				if(isCheck!=null && isCheck.equals("true")){
					CBRCClientCheck(fileName);
				}
				
				if(createFormat!=null && createFormat.equals("1")){
					SmallTools.delFileList(fileList);	//清空所有临时子文件
				}

				new DownloadResourceService().addEnd(fileName, suitCode); // 修改结束日期
				
	       } catch (Exception e) {
	    	   e.printStackTrace();
	       }
		}

		public void setFileList(String[] fileList) {
			FileList = fileList;
		}
		public void setJrxkzh(String jrxkzh) {
			Jrxkzh = jrxkzh;
		}
		public void setDateEnd(String dateEnd) {
			DateEnd = dateEnd;
		}
		public void setDateStart(String dateStart) {
			DateStart = dateStart;
		}
		public void setNbjgh(List<String> nbjgh) {
			Nbjgh = nbjgh;
		}
		public void setActionContext(ActionContext actionContext) {
			this.actionContext = actionContext;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getCreateFormat() {
			return createFormat;
		}

		public void setCreateFormat(String createFormat) {
			this.createFormat = createFormat;
		}
	}
 
	/*
	 * 传入表名，返回查询语句	montage(拼接) dimension(维度)
	 */
	public String montageStat(String autoTableId, List<Field> fieldList, String DateStart, String DateEnd, List<String> nbjgh) throws Exception{

		String strNbjgh = "";
		if(nbjgh!= null && nbjgh.size()>0){
			strNbjgh =  " and INSTINFO in('";
	    	for (int i = 0; i < nbjgh.size(); i++) {
	    		if(i<nbjgh.size()-1){
	    			strNbjgh += nbjgh.get(i)+"','";
	    		}else{
	    			strNbjgh += nbjgh.get(i)+"')";
	    		}
			}
		}
		
		String statement = "";
		for (int i = 0; i < fieldList.size(); i++) {
			if(fieldList.get(i)!=null)
			  statement += fieldList.get(i).getName()+",";
		
		}
		statement = statement.substring(0,statement.length()-1);
		
		String strPrefix = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix");
		
		if(strPrefix == null){
			strPrefix = "";
		}
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dbType = (String) objectResultExecute.paramObjectResultExecute(null);
		
		String DTDATE = "DTDATE";
		if(dbType == "oracle"){
			DTDATE = "to_char(DTDATE,'yyyy-MM-dd')";
		}else{
			DTDATE = "convert(varchar(10),DTDATE,121)";
		}
		
		statement = "select "+statement+" from " + strPrefix +autoTableId+" where "+DTDATE+" >='"+DateStart+"' and "+DTDATE+" <= '"+DateEnd +"'"+strNbjgh;	//拼接语句
		return statement;
	}
	
	/*
	 * 传入表名，返回所用字段列表
	 */
	@SuppressWarnings("unchecked")
	public List<Field> findFieldList(String autoTableName,StringBuilder tableCode){
		ReportModel_Table table;
		List<Field> fieldList = new ArrayList<Field>();
		DetachedCriteria detachedCriteria = null;
		try{
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			detachedCriteria.add(Restrictions.eq("strTableName",autoTableName));

			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	    	List<Object> objectList = (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		
	    	if(objectList.size()>0){
	    		table = (ReportModel_Table)objectList.get(0);	//表的集合
	    		tableCode.append(table.getStrTableName());
	    		
	    		detachedCriteria = DetachedCriteria.forClass(ReportModel_Field.class);
    			detachedCriteria.add(Restrictions.eq("reportModel_Table", table));
				detachedCriteria.addOrder(Order.asc("nSeq"));
    			
    			dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    	    	List<ReportModel_Field> objectListSub = (List<ReportModel_Field>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	    	Map<String, String> autoFieldMap = ShowContext.getInstance().getShowEntityMap().get("autoField");	//取配置文件，固定字段
				
	    		for(ReportModel_Field field : objectListSub) {

					if(field.getStrFieldName().equals(autoTableName)) {
						continue;
					}
					
					if(field.getNSeq().intValue()<0)
					{
						continue;
					}

					boolean isFixedField = false;
					for(Map.Entry<String, String> entry: autoFieldMap.entrySet()) {
						String strField = entry.getValue();
						if(strField.indexOf(',') > -1) {
							if(strField.substring(0, strField.indexOf(',')).toUpperCase().equals(field.getStrFieldName().toUpperCase())){
								isFixedField = true;
								break; //skip
							}
						}
					}

					if(!isFixedField && !"AUTOID".equals(field.getStrFieldName().toUpperCase())){
						Class<?> type = Class.forName(ReflectOperation.getAutoDTOTName("sessionFactory", autoTableName));
						fieldList.add(ReflectOperation.getReflectField(type, field.getStrFieldName()));
					}
	    		}
	    	}
		} catch (Exception e) {
			return null;
		}        
		return fieldList;
	}
	
	public File writeLog(File file){
		StringBuffer bf = new StringBuffer();
		bf.append(file.getName()).append("\r\n");
		bf.append(file.length()).append("\r\n");
		bf.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())).append("\r\n");
		bf.append("Y");
		File logPath = new File(file.getPath().replace(".txt", ".log"));
		try{
			FileOutputStream fopt = new FileOutputStream(logPath);  
			fopt.write(bf.toString().getBytes());
			fopt.flush();
			fopt.close();
		}catch (Exception e) {
		}
		return logPath;
	}

	/*
	 * 线程循环生成文件
	 */
	class fileListThread extends Thread{
		public File fileName;
		public String filePath;
		public String statement;
		public List<Field> fieldList;

		public void run() {
			try{
				new FileHandlerForText().WriteFromDatabaseToPath("sessionFactory",statement, fieldList, filePath, ",", 10000, null);// 
			}catch (Exception e) {
				e.printStackTrace();
			}
			fileName = new File(filePath);
		}

		public void setStatement(String statement) {
			this.statement = statement;
		}
		public File getFileName() {
			return fileName;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public void setFieldList(List<Field> fieldList) {
			this.fieldList = fieldList;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> findInstCodeMapField(String reportInstInfo, String returnFieldName, String strContrastType){
		
		try{
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InstCodeMap.class);
			
			Object objReportInstInfo = Class.forName("report.dto.ReportInstInfo").newInstance();
			ReflectOperation.setFieldValue(objReportInstInfo, "strReportInstCode", reportInstInfo);
			
			detachedCriteria.add(Restrictions.eq("reportInstInfo",objReportInstInfo));
			detachedCriteria.add(Restrictions.eq("strContrastType",strContrastType));
			
			List<InstCodeMap> objectList = (List<InstCodeMap>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

			List<String> strInstInfo = new ArrayList<String>();
			for (int i = 0; i < objectList.size(); i++) {
				strInstInfo.add(objectList.get(i).getStrInstCode());
			}
			return strInstInfo;
		}catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findInstInfo(String reportInstInfo, String returnFieldName, String strContrastType){
		
		try{
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstSubInfo.class);
			
			Object objReportInstInfo = Class.forName("report.dto.ReportInstInfo").newInstance();
			ReflectOperation.setFieldValue(objReportInstInfo, "strReportInstCode", reportInstInfo);
			
			detachedCriteria.add(Restrictions.eq("reportInstInfo", objReportInstInfo));
			List<ReportInstSubInfo> objectList = (List<ReportInstSubInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			
			List<String> strInstInfo = new ArrayList<String>();
			for (int i = 0; i < objectList.size(); i++) {
				strInstInfo.add(objectList.get(i).getInstInfo().getStrInstCode());
			}
			return strInstInfo;
		}catch (Exception e) {
			return null;
		}
	}
	

	private void CBRCClientCheck(String fileName){

		GetFilePath getFilePath = new GetFilePath();
		String logFileNameZip = fileName.replace(".zip", ".log.zip");
		DownloadResourceService resourceObj = new DownloadResourceService();
		
		if(checkDataPath==null || checkDataPath.equals("")){
			checkDataPath = getFilePath.getTmpFilePath();
		}
		
		//resourceObj.addStart(logFileNameZip, suitCode, "2"); // 新增离线下载记录
		resourceObj.addStart(logFileNameZip, suitCode);// 新增离线下载记录
		
		try {
			CBRCClientCheck CBRCClientCheck = new CBRCClientCheck(getFilePath.getDownloadResourcePath(), checkDataPath, logPath, logFileNameZip);
			CBRCClientCheck.startCBRCClientCheck(execShFile);
		} catch (Exception e) {
			logFileNameZip = "参数设置错误";
		}
		
		resourceObj.addEnd(logFileNameZip, suitCode); // 修改结束日期
	}
	
}
