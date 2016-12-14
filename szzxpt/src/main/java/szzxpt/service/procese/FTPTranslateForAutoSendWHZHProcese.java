package szzxpt.service.procese;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.HandleFileOrFolders;

import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;
/**
 * 自动上传外汇报文
 * @author Transino
 *
 */
public class FTPTranslateForAutoSendWHZHProcese implements IActivityNodeForJavaExtend{

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
		
		SystemParam sPReportFilePath = HelpTool.getSystemParam("whzh_reportFilePath"); //报文生成文件路径
		SystemParam sPReportHistoryFilePath = HelpTool.getSystemParam("whzh_SendHistoryFilePath"); //报文历史存放路径
		if(sPReportFilePath!=null){
			String WHZHSendPath=sPReportFilePath.getStrParamValue();
			
			List<File> fileList=new ArrayList<File>();
			HandleFileOrFolders.readfile(WHZHSendPath,fileList);
			if(fileList.size()>0){
				//生成令牌文件Token.lock
				File tokenFileLocal =new File(WHZHSendPath+"\\"+"Token.lock");
				if(tokenFileLocal.exists()){//如果存在令牌文件，则停止传输文件
					return "报文生成还未完成";
				}
				else{//FTP传输到MTS系统
					String ip = params[0].split(":")[1];// ftp服务器的IP地址
					String username = params[1].split(":")[1];// 用户名
					String password = params[2].split(":")[1];// 密码
					int port =  Integer.parseInt(params[3].split(":")[1]);// 默认端口
					String ftpPath=params[4].split(":")[1]; //FTP路径
					
					//自动上传报送文件
					FTPTranslateProcese FTPTranslate=new FTPTranslateProcese();
					boolean isUpload=FTPTranslate.upload(ip, port, username, password, ftpPath, WHZHSendPath);
					
					//上传成功之后，将本地报文移动到历史文件夹下
					if(isUpload){
						if(sPReportHistoryFilePath!=null){
							String WHZHSendHistortPath=sPReportHistoryFilePath.getStrParamValue();

							File[] fs = new File(WHZHSendPath).listFiles();
							for (int i = 0; i < fs.length; i++) {
								HelpTool.copyFolderWithSelf(fs[i].getPath(), WHZHSendHistortPath+File.separator);
							}
							//移动成功之后，删除报送文件夹下文件
							File[] fsTemp = new File(WHZHSendPath).listFiles();
							for(int i=0;i<fsTemp.length;i++){
								HelpTool.deleteFile(fsTemp[i]);
							}
							
						}else{
							return "请在系统参数管理下配置外汇账户上报报文历史存放路径 ";
						}
					}
				}
			}
			
		}else{
			return "请在系统参数管理下配置外汇账户生成报文路径   ";
		}
		
		return "成功";
	}

	
}
