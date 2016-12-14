package zxptsystem.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import framework.helper.ExceptionLog;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.imps.FileHandler;

/**
 * 生成报文时，报文校验错误，返回错误文本
 * @author xiajieli
 *
 */
public class ZXDowndLoadCheckFileService {
	
	public Object ZXWriteCheckFile(String ZXType,String msgCheck){
		
		DownloadResult downloadResult=null;
		try{
			String fileName="";
			if(ZXType.equals("qyzx")){
				fileName="qyzx_error.txt";
			}
			else if(ZXType.equals("grzx")){
				fileName="grzx_error.txt";
			}
			else if(ZXType.equals("jgxx")){
				fileName="jgxx_error.txt";
			}
			
			String systemPath = ServletActionContext.getServletContext().getRealPath("/")+"zxErrorFile"+File.separator;
				
			File dateFile = new File(systemPath);
			if (!dateFile.exists() && !dateFile.isDirectory()) {
				dateFile.mkdirs();
			}
		 
			String filePath = systemPath + "/" + fileName;
			
			File txtFile = new File(filePath);
			if (!txtFile.exists()) {
				txtFile.createNewFile();
			} else {
				txtFile.delete();
			}
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(msgCheck);
			FileOutputStream outStream = new FileOutputStream(txtFile);
			outStream.write(stringBuilder.toString().getBytes("GBK"));
			outStream.close();
			
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath.replace(".txt", ".zip")));
			out.putNextEntry(new ZipEntry(file.getName()));
			int len;
			byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
			out.close();
			FileHandler fileHandler = new FileHandler();
			downloadResult = fileHandler.GetStreamResult(fileName.replace(".txt",".zip"), null);
			FileInputStream inputStream = new FileInputStream(filePath.replace(".txt", ".zip"));
			downloadResult.setInputStream(inputStream);
			
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
		}
		finally{
			if(ServletActionContext.getContext().getApplication().get("strQYZXCheck")!=null
					&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strQYZXCheck").toString())){//企业
				ServletActionContext.getContext().getApplication().put("strQYZXCheck", null);
			}
			
			if(ServletActionContext.getContext().getApplication().get("strGRXXCheck")!=null
					&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strGRXXCheck").toString())){//个人
				ServletActionContext.getContext().getApplication().put("strGRXXCheck", null);
			}
			
			if(ServletActionContext.getContext().getApplication().get("strJGXXCheck")!=null
					&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strJGXXCheck").toString())){//机构
				ServletActionContext.getContext().getApplication().put("strJGXXCheck", null);
			}
		}
		
		return downloadResult;
	}
	
}
