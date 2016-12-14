package autoETLsystem.service.imps;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import framework.helper.ExceptionLog;

//import sun.net.ftp.FtpClient;



import autoETLsystem.dto.AutoETL_ActivityNodeForDeleteFile;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;

public class FTPFileOperator extends FileOperator {

	private String host;
	private String userid;
	private String pwd;
	private String dictionary;
	private String port;
	private FTPClient client;

	private void OpenServer() throws Exception
	{
		if(context!=null)
		{
			host=context.get("host").toString();
			userid=context.get("userid").toString();
			pwd=context.get("pwd").toString();
			dictionary=context.get("dictionary").toString();
			port="21";
			client = new FTPClient();
			client.setControlEncoding("UTF-8");
			client.connect(host);
			client.login(userid, pwd);
			client.setConnectTimeout(120000);
			client.setFileType(FTP.BINARY_FILE_TYPE);
			if(!dictionary.equals("")){
				client.changeWorkingDirectory(dictionary);
			}
		}
		else
		{
			throw new Exception("缺少FTP参数");
		}
	}
	
	public String getFileName(String s) throws UnsupportedEncodingException {   
	    String   tempStr="";   
	    int count = 0;
	    StringTokenizer   st=new   StringTokenizer(s);   
	    tempStr=st.nextToken(); 
	    while(st.hasMoreTokens()) {
	  	tempStr=st.nextToken();
	    	count++;
	    	if(count == 8)  //跳过前面的8个属性
	    	break;	     	
	    }
	   // String fileName= s.substring(s.indexOf(tempStr));
	      return tempStr; 
	}
    
	@Override
	public List<String> GetFileList(String dirPath)
			throws Exception {
		List<String> fileList=new ArrayList<String>();
		try
		{
			OpenServer();
			client.changeWorkingDirectory(dirPath);
			BufferedReader br = null;
			FTPFile[] files = client.listFiles();
			for(FTPFile f : files){
				br=new BufferedReader(new InputStreamReader(client.retrieveFileStream(f.getName())));
				String fileName=br.readLine();
				while(fileName!=null)
				{
					if(!"d".equalsIgnoreCase(fileName.substring(0,1)))
					{
						fileList.add(getFileName(fileName));
					}
					fileName=br.readLine();
				}
				br.close();
			}
		}
		finally
		{
			//client.closeServer();
			client.logout();
			client.disconnect();
		}
		return fileList;
	}

	@Override
	public void Del(String dirPath, List<String> delFileList) throws Exception {
		try
		{
			
			
			OpenServer();
			//client.cd(dirPath);
			client.changeWorkingDirectory(dirPath);
			for(String filename:delFileList)
			{
				//client.sendServer("DELE "+filename+"\r\n");
				client.sendCommand("DELE "+filename+"\r\n");
				//int status=client.readServerResponse();
				/*if(status!=250)
				{
					ExceptionLog.CreateLog(new Exception(filename+"删除失败"));
				}*/
			}
		}
		finally
		{
			//client.closeServer();
			client.logout();
			client.disconnect();
		}
	}

}
