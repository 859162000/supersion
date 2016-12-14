package framework.services.interfaces.imps;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandler;

public class FileHandler implements IFileHandler{
	
	private static String defaultFileName = "temp";
	private static String defaultFileType = "tmp";
	private static Integer defaultBufferSize = 4096;

	public DownloadResult GetStreamResult(String fileName, Integer bufferSize) throws UnsupportedEncodingException{
		
		DownloadResult downloadResult = new DownloadResult();
		
		if(fileName == null){
			fileName = defaultFileName;
		}
		if(bufferSize == null){
			bufferSize = defaultBufferSize;
		}
	
		downloadResult.setBufferSize(bufferSize);
		
		String fullFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		String fileType = "";
		
		if(fullFileName.indexOf(".") > -1){
			fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
			fileType = fullFileName.substring(fullFileName.lastIndexOf(".") + 1);
		}
		else{
			fileName = fullFileName;
			fileType = defaultFileType;
		}
		downloadResult.setContentDisposition("attachment;filename="+ fileName +"." + fileType);

		if(fileType.endsWith(defaultFileType)){
			downloadResult.setContentType("");
		}
		else if(fileType.endsWith("txt")){
			downloadResult.setContentType("text/plain");
		}
		else if(fileType.endsWith("doc")){
			downloadResult.setContentType("application/msword");
		}
		else if(fileType.endsWith("xls")){
			downloadResult.setContentType("application/vnd.ms-excel");
		}
		else if(fileType.endsWith("jpg")){
			downloadResult.setContentType("image/jpeg");
		}
		else if(fileType.endsWith("zip")){
			downloadResult.setContentType("application/zip");
		}
		return downloadResult;
	}

	public DownloadResult GetStreamResultFromBytes(byte[] byteData, String fileName, Integer bufferSize) throws Exception {
		
		DownloadResult downloadResult = GetStreamResult(fileName, bufferSize);
		
		if(byteData != null){
			InputStream inputStream = new ByteArrayInputStream(byteData);
			downloadResult.setInputStream(inputStream);
		}
		
		return downloadResult;
	}

	public DownloadResult GetStreamResultFromPath(String path, Integer bufferSize) throws Exception {

		String fileName = null;
		if(path.indexOf("/") > -1){
			fileName = path.substring(path.lastIndexOf("/") + 1);
		}
		else{
			fileName = path.substring(path.lastIndexOf("\\") + 1);
		}

		DownloadResult downloadResult = GetStreamResult(fileName, bufferSize);
		downloadResult.setInputStream(ServletActionContext.getServletContext().getResourceAsStream(path));
		
		return downloadResult;
	}
	
	public DownloadResult GetStreamResultFromRealPath(String path, String strFileName,Integer bufferSize) throws Exception {

		String fileName = null;
		if(StringUtils.isBlank(strFileName)){
			if(path.indexOf("/") > -1){
				fileName = path.substring(path.lastIndexOf("/") + 1);
			}
			else{
				fileName = path.substring(path.lastIndexOf("\\") + 1);
			}
		}
		else{
			fileName = strFileName;
		}

		File file = new File(path);

		InputStream inputStream = new FileInputStream(file);
		DownloadResult downloadResult = GetStreamResult(fileName, bufferSize);
		downloadResult.setInputStream(inputStream);
		
		return downloadResult;
	}

	public DownloadResult GetStreamResultFromInputStream(InputStream inputStream, String fileName, Integer bufferSize)throws Exception {
        
		DownloadResult downloadResult = GetStreamResult(fileName, bufferSize);
        downloadResult.setInputStream(inputStream);
		
		return downloadResult;
	}

	// 多个文件(path)打包成zip(fileName)
	public DownloadResult GetStreamResultFromPath(String[] path,
			String fileName, Integer bufferSize)
			throws Exception {

		File outFile = new File(ServletActionContext.getServletContext().getRealPath(fileName)); // 输出文件
        
        if (!outFile.exists()){
        	outFile.createNewFile();
        }

        FileOutputStream fous = new FileOutputStream(outFile);
        ZipOutputStream zipOut = new ZipOutputStream(fous);
        for(String strPath : path) {
        	File file = new File(strPath);
            if (!file.exists()){
            	file.createNewFile();   
            }

        	zipFile(file, zipOut);
        }
        zipOut.close();
        fous.close();
        
		DownloadResult downloadResult = GetStreamResult(fileName, bufferSize);
		//downloadResult.setInputStream(ServletActionContext.getServletContext().getResourceAsStream(fileName));
		InputStream inputStream=new FileInputStream(outFile);
		downloadResult.setInputStream(inputStream);
		return downloadResult;
	}
	
	private void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if(inputFile.exists()) {
                if (inputFile.isFile()) { // 单个文件
                	ouputStream.setEncoding("gbk");
                	FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    
                    // 向压缩文件中输出数据   
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    
                    // 关闭创建的流对象   
                    bins.close();
                    IN.close();
                } else { // 目录则将目录下所有文件递归压缩进去
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
