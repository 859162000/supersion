package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPFileOperator extends FileOperator {

	private String host;
	private String userid;
	private String pwd;
	private String dictionary;
	private int port;
	private ChannelSftp client;
	
	private void OpenServer() throws Exception
	{
		if(context!=null)
		{
			host=context.get("host").toString();
			userid=context.get("userid").toString();
			pwd=context.get("pwd").toString();
			dictionary=context.get("dictionary").toString();
			port=22;
			client=connect(host,port,userid,pwd);
			if(!dictionary.equals("")){
				client.cd(dictionary);
			}
			
		}
		else
		{
			throw new Exception("缺少FTP参数");
		}
	}
	
	public ChannelSftp connect(String host, int port, String username, String password) throws Exception {
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		jsch.getSession(username, host, port);
		Session sshSession = jsch.getSession(username, host, port);
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		return sftp;
	}
	@Override
	public void Del(String dirPath, List<String> delFileList) throws Exception {
		try
		{
			
			
			OpenServer();
			client.cd(dirPath);
			for(String filename:delFileList)
			{
				client.rm(filename);
			}
		}
		finally
		{
			client.disconnect();
			client.exit();
		}
		
	}

	public String getFileName(String s) {   
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
	public List<String> GetFileList(String dirPath) throws Exception {
		List<String> fileList=new ArrayList<String>();
		try
		{
			
			
			OpenServer();
			Vector fileVector=client.ls(dirPath);
			for(Object obj:fileVector)
			{
				String fileinfo=obj.toString();
				if(!"d".equalsIgnoreCase(fileinfo.substring(0,1)))
				{
				
					
						fileList.add(getFileName(fileinfo));
					
					
				}
				 
				
				
			}
			
			 
		
		}
		finally
		{
			client.disconnect();
			client.exit();
		}
		
		// TODO Auto-generated method stub
		return fileList;
		
	}

	

}
