package jmx.jsch;

import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class Shell {
//	//服务器id
//	private String sid;
//	//监控的应用  key[dir]+val[pid]
//	private Map<String, String> appMap;
	//ssh服务器的ip地址
	private String host;
	//ssh服务器的登入端口
	private int port = 22;
	//ssh服务器的登入用户名
	private String user;
	//ssh服务器的登入密码
	private String password;

	private Session session;
	
    private ChannelExec channel;
    
    private JSch jsch;
    
    public Shell(){
    	jsch = new JSch();
    }

	public Shell(String host, int port, String user, String password) {
		this();
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public Shell(String host, String user, String password) {
		this();
		this.host = host;
		this.user = user;
		this.password = password;
	}

	public Shell(String user, String password) {
		this();
		host = "localhost";
		this.user = user;
		this.password = password;
	}
	

	public InputStream exec(String command) {
		InputStream in = null;
		try {
			channel = (ChannelExec)session.openChannel("exec");
			channel.setCommand(command);
			channel.setInputStream(null);
			channel.setErrStream(System.err);
			in = channel.getInputStream();
			channel.connect();
		} catch (Exception e) {
			System.out.println(e);
			in = null;
		}
		return in;
	}
	
	public void disconnect(){
		if(channel!=null){
            channel.disconnect();
        }
	}
	
	public void connect(int timeOut) throws JSchException{
		if(session == null || !session.isConnected()){
			session = jsch.getSession(user, host, port);
			session.setUserInfo(new LocalUserInfo(password));
			session.connect(timeOut);
		}
	}
	
	/**
     * 关闭SSH远程连接
     */
    public void destroy(){
        if(channel!=null){
            channel.disconnect();
            channel = null;
        }
        if(session!=null){
            session.disconnect();
            session = null;
        }
        if(jsch != null){
        	jsch = null;
        }
    }

	//登入SSH时的控制信息
	//设置不提示输入密码、不显示登入信息等
	public static class LocalUserInfo implements UserInfo {
		String passwd;
		
		public LocalUserInfo(){
		}
		
		public LocalUserInfo(String passwd){
			this.passwd = passwd;
		}
		
		public String getPassword() {
			return passwd;
		}

		public boolean promptYesNo(String str) {
			return true;
		}

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptPassword(String message) {
			return true;
		}

		public void showMessage(String message) {

		}
	}

}