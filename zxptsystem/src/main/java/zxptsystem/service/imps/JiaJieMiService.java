package zxptsystem.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.cfcc.ecus.eft.CryptAPI;
import com.icfcc.batch.ui.PreConditionCheck;

import framework.helper.ExceptionLog;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

public class JiaJieMiService  implements IObjectResultExecute  {
	/*
	 * 分别对企业征信、个人征信、机构信息进行加密加压，解密解压
	 * */

	private String qyzx_crypConfig;
	
	private String qyzx_crypPublickey;
	
	private String jgxx_crypConfig;
	
	private String jgxx_crypPublickey;
	
	public Object objectResultExecute() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		String outputFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
		if(!outputFileName.endsWith(".txt") && !outputFileName.endsWith(".enc")){
			messageResult.getMessageSet().add("导入文件格式不正确");
		}
		 
		if (messageResult.getMessageSet().size() > 0) {
			messageResult.setMessage("生成失败");
			messageResult.setSuccess(false);
			messageResult.AlertTranslate();
			return messageResult;
		} else {
			return GenerateReport();
		}
	}
	
	private Object GenerateReport() throws Exception {
		DownloadResult downloadResult = null;
		File uploadFile = RequestManager.getUploadFile();
		String infileName = uploadFile.getPath();
		String outputFileName = RequestManager.getReportCheckParam().get("uploadFileFileName");
		String serverFilePath = "";
		//加密
		if(outputFileName.endsWith(".txt")){
			
			String systemPath = ServletActionContext.getServletContext().getRealPath("/");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String datePath = systemPath +"ForZXPTReport/test_" + format.format(new Date());
			//企业
			if(outputFileName.substring(0, outputFileName.length()-4).length()==28 && outputFileName.substring(26, 27).equals("0") && outputFileName.substring(27, 28).equals("0")){
				
				File dateFile = new File(datePath);
				if (!dateFile.exists() && !dateFile.isDirectory()) {
					dateFile.mkdirs();
				}
				String filePath = datePath + "/" + outputFileName;
				File txtFile = new File(filePath);
				if (!txtFile.exists()) {
					txtFile.createNewFile();
				} else {
					txtFile.delete();
					txtFile.createNewFile();
				}
				serverFilePath = filePath;
				FileUtils.copyFile(RequestManager.getUploadFile(), txtFile);
				try{
					CryptAPI.enCryptCompressFile(filePath.replace(
							"\\", "/"), (filePath.replace(".txt", ".enc")).replace(
									"\\", "/"), systemPath + qyzx_crypConfig, systemPath
									+ qyzx_crypPublickey, 1, "1.0");
				}
				catch(Exception ex){
					ApplicationManager.getActionExceptionLog().error("====================================================================================================");
					ApplicationManager.getActionExceptionLog().error("filePath:"+filePath);
					ApplicationManager.getActionExceptionLog().error("filePath.replace('\\', '/'):"+filePath.replace("\\", "/"));
					ApplicationManager.getActionExceptionLog().error("filePath.replace('.txt', '.enc'):"+filePath.replace(".txt", ".enc"));
					ApplicationManager.getActionExceptionLog().error("systemPath + crypConfig:"+systemPath + qyzx_crypConfig);
					ApplicationManager.getActionExceptionLog().error("systemPath+ crypPublickey:"+systemPath+ qyzx_crypPublickey);
					ApplicationManager.getActionExceptionLog().error(ex.getMessage().replace("\'", "").replace("\"", ""));
					ApplicationManager.getActionExceptionLog().error("====================================================================================================");
					ExceptionLog.CreateLog(ex);
					MessageResult messageResult=new MessageResult();
					messageResult.setMessage("文件路径异常");
					messageResult.getMessageSet().add("文件路径:"+filePath);
					messageResult.getMessageSet().add("文件加密路径:"+filePath.replace(".txt", ".enc"));
					messageResult.getMessageSet().add("密钥路径:"+systemPath + qyzx_crypConfig);
					messageResult.getMessageSet().add("公钥路径:"+systemPath+ qyzx_crypPublickey);
					messageResult.getMessageSet().add(ex.getMessage().replace("\'", "").replace("\"", ""));
					messageResult.setSuccess(false);
					messageResult.AlertTranslate();
					return messageResult;
					
				}
			}
			//个人
			else if(outputFileName.substring(0, outputFileName.length()-4).length()==27){
				File dateFile = new File(datePath);
				if (!dateFile.exists() && !dateFile.isDirectory()) {
					dateFile.mkdirs();
				}
				String filePath = datePath + "/" + outputFileName;
				File txtFile = new File(filePath);
				if (!txtFile.exists()) {
					txtFile.createNewFile();
				} else {
					txtFile.delete();
					txtFile.createNewFile();
				}
				serverFilePath = filePath;
				FileUtils.copyFile(RequestManager.getUploadFile(), txtFile);
				
				this.initConfigForQyzx(systemPath);
				PreConditionCheck prc = new PreConditionCheck(systemPath);
				
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
			}
			//机构
			else if(outputFileName.substring(0, outputFileName.length()-4).length()==37 && outputFileName.substring(35, 36).equals("0")){
				File dateFile = new File(datePath);
				if (!dateFile.exists() && !dateFile.isDirectory()) {
					dateFile.mkdirs();
				}
				String filePath = datePath + "/" + outputFileName;
				File txtFile = new File(filePath);
				if (!txtFile.exists()) {
					txtFile.createNewFile();
				} else {
					txtFile.delete();
					txtFile.createNewFile();
				}

				serverFilePath = filePath;
				FileUtils.copyFile(RequestManager.getUploadFile(), txtFile);
				
				try{
					CryptAPI.enCryptCompressFile(filePath.replace(
							"\\", "/"), (filePath.replace(".txt", ".enc")).replace(
									"\\", "/"), systemPath + jgxx_crypConfig, systemPath
									+ jgxx_crypPublickey, 1, "1.0");
				}
				catch(Exception ex){
					ApplicationManager.getActionExceptionLog().error("====================================================================================================");
					ApplicationManager.getActionExceptionLog().error("filePath:"+filePath);
					ApplicationManager.getActionExceptionLog().error("filePath.replace('\\', '/'):"+filePath.replace("\\", "/"));
					ApplicationManager.getActionExceptionLog().error("filePath.replace('.txt', '.enc'):"+filePath.replace(".txt", ".enc"));
					ApplicationManager.getActionExceptionLog().error("systemPath + jgxx_crypConfig:"+systemPath + jgxx_crypConfig);
					ApplicationManager.getActionExceptionLog().error("systemPath+ jgxx_crypPublickey:"+systemPath+ jgxx_crypPublickey);
					ApplicationManager.getActionExceptionLog().error(ex.getMessage().replace("\'", "").replace("\"", ""));
					ApplicationManager.getActionExceptionLog().error("====================================================================================================");
					ExceptionLog.CreateLog(ex);
					MessageResult messageResult=new MessageResult();
					messageResult.setMessage("文件路径异常");
					messageResult.getMessageSet().add("文件路径:"+filePath);
					messageResult.getMessageSet().add("文件加密路径:"+filePath.replace(".txt", ".enc"));
					messageResult.getMessageSet().add("密钥路径:"+jgxx_crypConfig + jgxx_crypConfig);
					messageResult.getMessageSet().add("公钥路径:"+jgxx_crypPublickey+ jgxx_crypPublickey);
					messageResult.getMessageSet().add(ex.getMessage().replace("\'", "").replace("\"", ""));
					messageResult.setSuccess(false);
					messageResult.AlertTranslate();
					return messageResult;
				}
			}
			try{
				FileInputStream inputStream = new FileInputStream(serverFilePath.replace(".txt", ".enc"));
				downloadResult = new FileHandler().GetStreamResultFromInputStream(inputStream, outputFileName.replace(".txt", ".enc"), BUFFER);
			}catch(Exception ex){
				MessageResult messageResult=new MessageResult();
				messageResult.setMessage("导入文件格式不正确");
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
			}
			
		}
		//解密
		else if(outputFileName.endsWith(".enc")){
			outputFileName = RequestManager.getReportCheckParam().get("uploadFileFileName").replace("enc", "txt");//uploadFile1
			//企业
			if(outputFileName.substring(0, outputFileName.length()-4).length()==28 && outputFileName.substring(26, 27).equals("1") && outputFileName.substring(27, 28).equals("0")){
				zxptsystem.helper.QY.MsgDecryptImpl2 cryptor = new zxptsystem.helper.QY.MsgDecryptImpl2();
				cryptor.decryptMsg(infileName, infileName + ".tmp");
				try {
					deCompress(infileName + ".tmp", outputFileName);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			//个人
			else if(outputFileName.substring(0, outputFileName.length()-4).length()==28 && outputFileName.substring(27, 28).equals("1")){
				zxptsystem.helper.GR.MsgDecryptImpl2 cryptor = new zxptsystem.helper.GR.MsgDecryptImpl2();
				cryptor.decryptMsg(infileName, infileName + ".tmp");
				try {
					deCompress(infileName + ".tmp", outputFileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//机构
			else if(outputFileName.substring(0, outputFileName.length()-4).length()==37 && outputFileName.substring(35, 36).equals("1")){
				zxptsystem.helper.QY.MsgDecryptImpl2 cryptor = new zxptsystem.helper.QY.MsgDecryptImpl2();
				cryptor.decryptMsg(infileName, infileName + ".tmp");
				try {
					deCompress(infileName + ".tmp", outputFileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try{
				downloadResult = new FileHandler().GetStreamResultFromInputStream(new FileInputStream(outputFileName), outputFileName, BUFFER);
			}catch(Exception ex){
				MessageResult messageResult=new MessageResult();
				messageResult.setMessage("导入文件格式不正确");
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
			}
		}
		
		return downloadResult;
	}
	
	public static void deCompress(String fileGzipIn, String fileOut) throws IOException{
		FileInputStream gzipIn = new FileInputStream(fileGzipIn);
		FileOutputStream out = new FileOutputStream(fileOut);
		
		GZIPInputStream in = new GZIPInputStream(gzipIn);
		byte buf[] = new byte[BUFFER];
		int num;
		while ((num = in.read(buf, 0, BUFFER)) != -1)
			out.write(buf, 0, num);
		in.close();
		
		out.close();
		gzipIn.close();
	}
	
	private void initConfigForQyzx(String path) {
		com.icfcc.batch.Constants.SYSTEM_CONFIG_FILE_NAME = "batch.xml";
		com.icfcc.batch.Constants.SYSTEM_MESSAGE_DESCRIPTOR_CONFIG_NAME = "message.xml";
		com.icfcc.batch.Constants.SYSTEM_PREPARE_CONFIG_NAME = "prepare.xml";
		com.icfcc.batch.Constants.SYSTEM_DATABASE_CONFIG_NAME = "database.xml";
		com.icfcc.batch.Constants.SYSTEM_DATABASE_PROXY_NAME = "dispatchProxy.xml";
		com.icfcc.batch.Constants.SYSTEM_PUBLIC_KEY = "public.key";
		com.icfcc.batch.Constants.SYSTEM_KEYSORE_NAME = ".keystore";
		com.icfcc.batch.BatchContext.getInstance().setBaseHome(path + "/conf/grzx/");
	}
	
	private static final int BUFFER = 2048;

	public String getQyzx_crypConfig() {
		return qyzx_crypConfig;
	}

	public void setQyzx_crypConfig(String qyzxCrypConfig) {
		qyzx_crypConfig = qyzxCrypConfig;
	}

	public String getQyzx_crypPublickey() {
		return qyzx_crypPublickey;
	}

	public void setQyzx_crypPublickey(String qyzxCrypPublickey) {
		qyzx_crypPublickey = qyzxCrypPublickey;
	}

	public String getJgxx_crypConfig() {
		return jgxx_crypConfig;
	}

	public void setJgxx_crypConfig(String jgxxCrypConfig) {
		jgxx_crypConfig = jgxxCrypConfig;
	}

	public String getJgxx_crypPublickey() {
		return jgxx_crypPublickey;
	}

	public void setJgxx_crypPublickey(String jgxxCrypPublickey) {
		jgxx_crypPublickey = jgxxCrypPublickey;
	}

}
