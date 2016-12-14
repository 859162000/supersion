package szzxpt.service.procese;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

public class FTPTranslateProcese {

	FtpClient ftpclient = null;
	OutputStream os = null;
	FileInputStream is = null;

	/**
	 * 创建目录
	 * 
	 * @param dir
	 *            目录
	 * @param ftpclient
	 *            ftp客户端
	 * @throws Exception
	 */
	private void createdir(String dir, FtpClient ftpclient) throws Exception {
		ftpclient.ascii();
		StringTokenizer s = new StringTokenizer(dir, "/");
		s.countTokens();
		String pathname = "";
		while (s.hasMoreElements()) {
			pathname = pathname + "/" + (String) s.nextElement();
			try {
				ftpclient.sendServer("mkd " + pathname + "\r\n");// 如果服务器上有该目录，不会被创建
			} catch (Exception e) {
				e = null;
			}
			ftpclient.readServerResponse();
		}
		ftpclient.binary();
	}

	/**
	 * 目录是否存在
	 * 
	 * @param dir
	 *            目录
	 * @param ftpclient
	 *            ftp客户端
	 * @return
	 * @throws Exception
	 */
	private boolean isdirexist(String dir, FtpClient ftpclient) throws Exception {
		try {
			ftpclient.cd(dir);
		} catch (Exception e) {
			// todo 自动生成 catch 块
			return false;
		}
		return true;
	}

	/**
	 * 上传
	 * 
	 * @param ftpPath
	 *            前缀
	 * @param localfilefullname
	 *            本地文件全路径
	 * @return
	 * @throws Exception
	 */
	public boolean upload(String ip,int port,String username,String password, String ftpPath, String localfilefullname) throws Exception {
		TelnetInputStream tokenFileRemote = null;
		try {
			String savefilename = localfilefullname;
			// 新建一个ftp客户端连
			ftpclient = new FtpClient();
			ftpclient.openServer(ip, port);
			ftpclient.login(username, password);
			
			if(ftpPath != null && !ftpPath.equals("")){
				tokenFileRemote = isExistFile(ftpPath,"Token.lock", ftpclient);
				if(tokenFileRemote==null){
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ftpclient.append("Token.lock"));
					BufferedWriter bw = new BufferedWriter(outputStreamWriter);
					bw.write("");
					bw.flush();
					bw.close();
				}else{
					return false;
				}
			}
			
			// 打开本地待上传的文件
			File file_in = new File(savefilename);
			processfile(ftpPath, file_in, ftpclient,localfilefullname);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exception e in ftp upload(): " + e.toString());
			return false;
		} finally {
			if(tokenFileRemote == null){
				ftpclient.cd(ftpPath);
				ftpclient.sendServer("dele Token.lock \r\n"); // 删除令牌文件
			}
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
			if (ftpclient != null) {
				ftpclient.closeServer();
			}
		}
	}

	/**
	 * 读取文件中
	 * 
	 * @param ftpPath
	 *            前缀
	 * @param source
	 *            源
	 * @param ftpclient
	 *            ftp客户端
	 * @throws Exception
	 */
	private void processfile(String ftpPath, File source, FtpClient ftpclient, String localfilefullname) throws Exception {
		if (source.exists()) {
			if (source.isDirectory()) {
				String path = ftpPath + source.getPath().substring(localfilefullname.length()).replace("\\", "/");
				if (!isdirexist(path, ftpclient)) {
					createdir(path, ftpclient);
				}
				File[] sourcefile = source.listFiles();
				for (int i = 0; i < sourcefile.length; i++) {
					if (sourcefile[i].exists()) {
						if (sourcefile[i].isDirectory()) {
							this.processfile(ftpPath, sourcefile[i], ftpclient,localfilefullname);
						} else {
							ftpclient.cd(changepath(ftpPath, sourcefile[i].getPath(),localfilefullname));
							ftpclient.binary();
							os = ftpclient.put(sourcefile[i].getName());
							byte[] bytes = new byte[1024];
							is = new FileInputStream(sourcefile[i]);
							// 开始复制
							int c;
							// 暂未考虑中途终止的情况
							while ((c = is.read(bytes)) != -1) {
								os.write(bytes, 0, c);
							}
							is.close();
							os.close();
						}
					}
				}
			} else {
				ftpclient.cd(changepath(ftpPath, source.getPath(),localfilefullname));
				ftpclient.binary();
				os = ftpclient.put(source.getName());
				byte[] bytes = new byte[1024];
				is = new FileInputStream(source);
				// 开始复制
				int c;
				// 暂未考虑中途终止的情况
				while ((c = is.read(bytes)) != -1) {
					os.write(bytes, 0, c);
				}
				is.close();
				os.close();
			}
		} else {
			throw new Exception("此文件或文件夹[" + source.getName() + "]有误或不存在!");
		}
	}

	/**
	 * 改变路径
	 * 
	 * @param ftpPath
	 *            前缀
	 * @param path
	 *            路径
	 * @return
	 * @throws Exception
	 */
	private String changepath(String ftpPath, String path,String localfilefullname) throws Exception {
		path = path.substring(localfilefullname.length()).replace("\\", "/");
		if ("".equals(path)) {
			path = "/";
		} else {
			path = path.substring(0, path.lastIndexOf("/") + 1);
		}
		path = ftpPath + path;
		return path;
	}

	/**
	 * 得到文件大小
	 * 
	 * @param strName
	 *            文件名称
	 * @return
	 */
	public Long getSize(String strName) {
		Long TotalSize = 0L;
		File f = new File(strName);
		if (f.isFile())
			return f.length();
		else {
			if (f.isDirectory()) {
				File[] contents = f.listFiles();
				for (int i = 0; i < contents.length; i++) {
					if (contents[i].isFile())
						TotalSize += contents[i].length();
					else {
						if (contents[i].isDirectory())
							TotalSize += getSize(contents[i].getPath());
					}
				}
			}
		}
		return TotalSize;
	}

	/**
	 * 文件下载
	 * 
	 * @param localPath
	 *            本地路径
	 * @param remotePath
	 *            远程路径
	 */
	@SuppressWarnings("unchecked")
	public void processdownload(String localPath, String ftpPath) {
		
		FileOutputStream outStream = null;
		//List listTemp = null;
		List list=null;
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		try {
			list = getFileList(ftpPath);
			
			ftpclient.binary();
			File temp = null;
			boolean overRead = false;
			for (int i = 0; i < list.size(); i++) {
				// 如果是文件，则直接执行下载
				if(list.get(i).toString().contains("Token.lock")){
					continue;
				}
				
				if (isFile(list.get(i).toString())) {//是否是文件
					if (overRead) {
						continue;
					}
					overRead = true;
					ftpclient.cd(ftpPath);
					List listfileName = getNameList(ftpPath);
					for (int j = 0; j < listfileName.size(); j++) {
						File file = new File(listfileName.get(j).toString());
						temp = new File(localPath + File.separator + file.getName());
						if (temp.isDirectory()) {
							continue;
						}
						if (!temp.exists()) {
							String filePath = temp.getPath();
							String[] fileName = filePath.split(File.separator + "" + File.separator);
							String createFile = "";
							for (int k = 0; k < fileName.length; k++) {
								if (!createFile.equals("")) {
									createFile = createFile + File.separator;
								}
								createFile = createFile + fileName[k];

								File fileTempPath = new File(createFile);
								if (!fileTempPath.exists()) {
									if (k < fileName.length - 1) {
										fileTempPath.mkdirs();
										continue;
									}
									fileTempPath.createNewFile();
								}
							}
						}
						outStream = new FileOutputStream(temp);
						TelnetInputStream is = ftpclient.get(listfileName.get(j).toString());
						byte[] bytes = new byte[1024];
						int c;
						// 暂未考虑中途终止的情况
						while ((c = is.read(bytes)) != -1) {
							outStream.write(bytes, 0, c);
						}
						is.close();
						outStream.close();
					}
				} else if (isDir(list.get(i).toString())){// 是目录
					
					String strFilename=getFileName(list.get(i).toString());
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("szzxpt.dto.WHZHCJFKBW"));
					detachedCriteria.add(Restrictions.eq("strBWCCWJM",strFilename+".XML"));
					int count=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(count>0){
						continue;
					}else{
						temp = new File(localPath + File.separator + getFileName(list.get(i).toString()));
						temp.mkdirs();
						String newRemote = getFileName(list.get(i).toString());
						processdownload(localPath  + File.separator + getFileName(list.get(i).toString()), ftpPath,newRemote);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 得到文件名称
	 * 
	 * @param line
	 * @return
	 */
	public String getFileName(String line) {
		String filename="";
		if(parseLine(line).size()>=8){
			filename = (String) parseLine(line).get(8);
		}else if(parseLine(line).size()>=4){//20151107 update
			filename = (String) parseLine(line).get(3);
		}
		
		return filename;
	}

	/**
	 * 得到名称列表
	 * 
	 * @param remotePath
	 *            远程路径
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getNameList(String remotePath) throws IOException {
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpclient.nameList(remotePath)));
		ArrayList al = new ArrayList();
		String s = "";
		while ((s = dr.readLine()) != null) {
			al.add(s);
		}
		return al;
	}

	/**
	 * 得到文件列表
	 * 
	 * @param remotePath
	 *            远程路径
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getFileList(String remotePath) throws IOException {
		ftpclient.cd(remotePath);
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpclient.list()));
		ArrayList al = new ArrayList();
		String s = "";
		while ((s = dr.readLine()) != null) {
			if(parseLine(s).size()>=8){
				if ((!((String) parseLine(s).get(8)).equals(".")) && (!((String) parseLine(s).get(8)).equals(".."))) {
					al.add(s);
				}
			}else if(parseLine(s).size()>=4){//20151107 update
				if ((!((String) parseLine(s).get(3)).equals(".")) && (!((String) parseLine(s).get(3)).equals(".."))) {
					al.add(s);
				}
			}
			
		}
		return al;
	}

	/**
	 * 是否是目录
	 * 
	 * @param line
	 * @return
	 */
	public boolean isDir(String line) {
		if(((String) parseLine(line).get(2)).contains("DIR")){//20151107 update
			return true;
		}else{
			return ((String) parseLine(line).get(0)).indexOf("d") != -1;
		}
		
	}

	/**
	 * 是否是文件
	 * 
	 * @param line
	 * @return
	 */
	public boolean isFile(String line) {
		return !isDir(line);
	}

	@SuppressWarnings("unchecked")
	private ArrayList parseLine(String line) {
		ArrayList s1 = new ArrayList();
		StringTokenizer st = new StringTokenizer(line, " ");
		while (st.hasMoreTokens()) {
			s1.add(st.nextToken());
		}
		return s1;
	}

	
	/**
	 * FTP下载
	 * @param ip FTP服务器IP地址
	 * @param port FTP端口号
	 * @param username 用户名
	 * @param password 密码
	 * @param localPath 本地路径
	 * @param ftpPath FTP路径
	 * @return
	 * @throws Exception
	 */
	public boolean download(String ip,int port,String username,String password,String localPath, String ftpPath) throws Exception {
		TelnetInputStream tokenFileRemote = null;
		try {
			// 新建一个ftp客户端连
			ftpclient = new FtpClient();
			ftpclient.openServer(ip, port);
			ftpclient.login(username, password);
			
			if(ftpPath != null && !ftpPath.equals("")){
				tokenFileRemote = isExistFile(ftpPath,"Token.lock", ftpclient);
				if(tokenFileRemote==null){
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ftpclient.append("Token.lock"));
					BufferedWriter bw = new BufferedWriter(outputStreamWriter);
					bw.write("");
					bw.flush();
					bw.close();
				}else{
					return false;
				}
			}
			
			ftpclient.cd(ftpPath);
			processdownload(localPath, ftpPath); //下载
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exception e in ftp upload(): " + e.toString());
			return false;
		} finally {
			if(tokenFileRemote == null){
				ftpclient.cd(ftpPath);
				ftpclient.sendServer("dele Token.lock \r\n"); // 删除令牌文件
			}
			
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
			if (ftpclient != null) {
				ftpclient.closeServer();
			}
		}
	}


	/**
	 * 下载中
	 * @param localPath 本地路径
	 * @param ftpPath ftp路径
	 * @param remotePath 远程路径
	 */ 
	@SuppressWarnings("unchecked")
	public void processdownload(String localPath, String ftpPath,String remotePath) {
		FileOutputStream outStream = null;
		List list = null;
		try {
			list = getFileList(ftpPath + remotePath);
			ftpclient.binary();
			File temp = null;
			boolean overRead = false;
			for (int i = 0; i < list.size(); i++) {
				// 如果是文件，则直接执行下载
				if (isFile(list.get(i).toString())) {
					if (overRead) {
						continue;
					}
					overRead = true;
					ftpclient.cd(ftpPath + remotePath);
					List listfileName = getNameList(ftpPath + remotePath);
					for (int j = 0; j < listfileName.size(); j++) {
						File file = new File(listfileName.get(j).toString());
						temp = new File(localPath + File.separator + file.getName());
						if (temp.isDirectory()) {
							continue;
						}
						if (!temp.exists()) {
							String filePath = temp.getPath();
							String[] fileName = filePath.split(File.separator + "" + File.separator);
							String createFile = "";
							for (int k = 0; k < fileName.length; k++) {
								if (!createFile.equals("")) {
									createFile = createFile + File.separator;
								}
								createFile = createFile + fileName[k];

								File fileTempPath = new File(createFile);
								if (!fileTempPath.exists()) {
									if (k < fileName.length - 1) {
										fileTempPath.mkdirs();
										continue;
									}
									fileTempPath.createNewFile();
								}
							}
						}
						outStream = new FileOutputStream(temp);
						TelnetInputStream is = ftpclient.get(listfileName.get(j).toString());
						byte[] bytes = new byte[1024];
						int c;
						// 暂未考虑中途终止的情况
						while ((c = is.read(bytes)) != -1) {
							outStream.write(bytes, 0, c);
						}
						is.close();
						outStream.close();
					}
				} else if (isDir(list.get(i).toString())){// 是目录
					temp = new File(localPath + remotePath + File.separator + getFileName(list.get(i).toString()));
					temp.mkdirs();
					String newRemote = remotePath + File.separator + getFileName(list.get(i).toString());
					processdownload(localPath + remotePath + File.separator + getFileName(list.get(i).toString()), ftpPath,newRemote);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private TelnetInputStream isExistFile(String ftpPath, String fileName, FtpClient ftpClient){
		try {
			ftpClient.cd(ftpPath);
			TelnetInputStream  tokenFileRemote = ftpClient.get(fileName);
			return tokenFileRemote;
		} catch (Exception e) {
			return null;
		}
	}
}
