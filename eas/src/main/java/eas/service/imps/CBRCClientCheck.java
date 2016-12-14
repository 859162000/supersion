package eas.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import framework.helper.SmallTools;

public class CBRCClientCheck {

	private String sourceFilePath; // 下载资源库路径
	private String checkDataPath; // 待校验文件路径
	private String logPath; // 校验日志存放路径
	private String logFileNameZip; // 校验文件压缩文件名
	private String send_name = "DATA_CHECK_SEND.log";
	private String main_name = "DATA_CHECK_MAIN.log";

	public CBRCClientCheck(String sourceFilePath, String checkDataPath, String logPath, String logFileNameZip){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		this.sourceFilePath = sourceFilePath;
		this.checkDataPath = checkDataPath;
		this.logPath = logPath + File.separator + format.format(new Date());
		this.logFileNameZip = logFileNameZip;
		
		System.out.println("下载资源库路径："+sourceFilePath+"待校验报文路径："+checkDataPath+"校验日志存放路径："+logPath+"日志ZIP文件名："+logFileNameZip);
	}
	
	public void startCBRCClientCheck(String execShFile) throws Exception{
		
		delAllFile(); // 清空日志路径下所有文件
		exec(execShFile); // 执行校验
		isEndCheck(); // 校验是否完成
		List<File> files = getLogFiles(); // 取得所有日志文件
		compressFilesZip(files);  // 将所有日志文件打包
	}
	
	private void exec(String execShFile) throws IOException{
        
		if(execShFile==null){
			execShFile = "sh $CHECK_HOME/data_check.sh ";
		}
		
		String strExec = execShFile+checkDataPath;
		System.out.println("执行命令："+strExec);
		Runtime.getRuntime().exec(strExec);
	}
	
	@SuppressWarnings("static-access")
	private void isEndCheck() throws Exception{
		
		long sleepMillis = 1000;
		String encoding = "GB2312";
		String sendStartStr = "本次待校验文件个数:[";
		String sendEndStr = "]";
		String mainStartStr = "所有文件校验完成,共[";
		String mainEndStr = "]";
		
		String send_log = ""; // 待校验文本内容
		String main_log = "";

		File sendLogFile = new File(this.logPath + File.separator + send_name);
		File mainLogFile = new File(this.logPath + File.separator + main_name);
		
		while(true){
	        if (sendLogFile!=null && sendLogFile.exists()){
	        	send_log = getIndex(sendLogFile, sendStartStr, sendEndStr, encoding);  // 读取文本文件内容，截取待校验个数   
	        	break;
	        }
			Thread.currentThread().sleep(sleepMillis);
		}
		
		while(true){
	        if (mainLogFile!=null && mainLogFile.exists()){
	        	main_log =  getIndex(mainLogFile, mainStartStr, mainEndStr, encoding);  // 读取文本文件内容，截取已校验个数   
	        }
			if(send_log.equals(main_log)){
				break;
			}
			Thread.currentThread().sleep(sleepMillis);
		}
	}
	
	private void compressFilesZip(List<File> files) throws Exception{
		SmallTools.compressFilesZip(files, sourceFilePath, logFileNameZip); // 将文件打包
	}
	
	private void delAllFile() throws Exception{
		SmallTools.delAllFile(this.logPath); // 将文件打包
	}
	
	private List<File> getLogFiles(){

		File dir = new File(logPath);
		File[] fileArr = dir.listFiles();
		List<File> files = new ArrayList<File>();

		Collections.addAll(files, fileArr);
		
		return files;
	}
	
	private String getIndex(File file, String startStr, String endStr, String encoding) throws Exception{

		String logStr = "";
		
    	InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while((lineTxt = bufferedReader.readLine()) != null){
        	logStr += lineTxt;
        }
        read.close();
        
        int startIndex = logStr.lastIndexOf(startStr)+startStr.length();
        String strTmp = logStr.substring(startIndex);
        int endIndex = startIndex+strTmp.indexOf(endStr);
        
        logStr = logStr.substring(startIndex, endIndex);
        
        return logStr;
	}
}
