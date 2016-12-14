package szzxpt.service.procese;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;


import szzxpt.service.imps.WHZHCJFKBWUploadFileService;


import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.HandleFileOrFolders;

import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;

/**
 * 自动下载解析外汇报文
 * @author Transino
 *
 */
public class FTPTranslateForAutoAnalyticalWHZHProcese implements IActivityNodeForJavaExtend{

	public String Execute(List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,String strFixParameter) throws Exception {
		
		String[] params=null;
		if(strFixParameter.contains("\r")){
			strFixParameter=strFixParameter.replace("\r", "");
		}if(strFixParameter.contains("\n")){
			strFixParameter=strFixParameter.replace("\n", "");
		}
		if(strFixParameter.indexOf(",")>0){
			params=strFixParameter.split(",");
		}
		
		String ip = params[0].split(":")[1];// ftp服务器的IP地址
		String username = params[1].split(":")[1];// 用户名
		String password = params[2].split(":")[1];// 密码
		int port =  Integer.parseInt(params[3].split(":")[1]);// 默认端口
		String ftpPath=params[4].split(":")[1]; //FTP路径
		
		SystemParam systemParamFeedBackXMLPath = HelpTool.getSystemParam("whzh_feedBackXMLPath"); //反馈报文存放路径
		
		if(systemParamFeedBackXMLPath!=null){
			String whzhfeedBackXMLPath=systemParamFeedBackXMLPath.getStrParamValue();
			String msg="";
			if(ftpPath.indexOf(";")>0){
				String[] strFTPPath=ftpPath.split(";");
				for (int k = 0; k < strFTPPath.length; k++) {
					msg=GetResult( strFTPPath[k], ip, username, password, port, whzhfeedBackXMLPath);
				}
			}else{
				msg=GetResult( ftpPath, ip, username, password, port, whzhfeedBackXMLPath);
			}
			if(!StringUtils.isBlank(msg)){
				return msg;
			}
		}else{
			return "请在系统参数管理下配置外汇账户反馈报文存放路径";
		}
		
		return "成功";
	}

	private String GetResult(String strFTPPath,String ip,String username,String password,int port,String whzhfeedBackXMLPath) throws Exception{
		SystemParam systemParamReportHistoryFilePath = HelpTool.getSystemParam("whzh_FeedbackHistoryFilePath"); //反馈报文历史存放路径
		
		List<File> fileList=new ArrayList<File>();
		HandleFileOrFolders.readfile(strFTPPath,fileList);
		if(fileList.size()>0){
			//执行FTP下载
			FTPTranslateProcese FTPTranslate=new FTPTranslateProcese();
			boolean isDownLoad=FTPTranslate.download(ip, port, username, password, whzhfeedBackXMLPath, strFTPPath);
			
			if(isDownLoad){
				//自动解析反馈报文，解析成功后将反馈报文自动放在历史文件夹下
				WHZHCJFKBWUploadFileService wHZHCJFKBWUploadFileService=new WHZHCJFKBWUploadFileService();
				boolean isAutoRead=wHZHCJFKBWUploadFileService.autoReadXML();
				
				//解析成功之后，将本地报文移动到历史文件夹下
				if(isAutoRead){
					if(systemParamReportHistoryFilePath!=null){
						String whzhHistortPath=systemParamReportHistoryFilePath.getStrParamValue();
						
						File[] fs = new File(whzhfeedBackXMLPath).listFiles();
						for (int i = 0; i < fs.length; i++) {
							HelpTool.copyFolderWithSelf(fs[i].getPath(), whzhHistortPath+File.separator);
						}
						//移动成功之后，删除报送文件夹下文件
						File[] fsTemp = new File(whzhfeedBackXMLPath).listFiles();
						for(int i=0;i<fsTemp.length;i++){
							HelpTool.deleteFile(fsTemp[i]);
						}
					}else{
						return "请在系统参数管理下配置外汇账户反馈报文历史存放路径";
					}
					
				}else{
					return "自动解析报文失败";
				}
			}else{
				return "自动下载失败";
			}
		}
		return "";
			
	}
}
