package framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import framework.interfaces.IParamObjectResultExecute;

import javax.servlet.http.HttpServletRequest;

/**
 * 一些常用的小方法
 * 直接static调用即可
 * @author ctx101
 *
 */
public class SmallTools {

	 
	 /**
	  * 在项目中要更能根据某些查询条件（比如姓名）的首字母作为条件进行查询，比如查一个叫“张三”的人，可以输入‘zs'。写了一个工具类如下：
	 * GB 2312-80 把收录的汉字分成两级。第一级汉字是常用汉字，计 3755 个， 置于 16～55
	 * 区，按汉语拼音字母／笔形顺序排列；第二级汉字是次常用汉字， 计 3008 个，置于 56～87 区，按部首／笔画顺序排列，所以本程序只能查到
	 * 对一级汉字的声母。同时对符合声母（zh，ch，sh）只能取首字母（z，c，s）
	 */

	 // 国标码和区位码转换常量
	 static final int GB_SP_DIFF = 160;
	 // 存放国标一级汉字不同读音的起始区位码
	 static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302,
	     2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
	     4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	 // 存放国标一级汉字不同读音的起始区位码对应读音
	 static final char[] firstLetter = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
	     'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'W', 'X',
	     'Y', 'Z' };
//	 static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
//	     'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
//	     'y', 'z' };

	 // 获取一个字符串的拼音码
	 public static String getFirstLetter(String oriStr) {
	    String str = oriStr.toLowerCase();
	    StringBuffer buffer = new StringBuffer();
	    char ch;
	    char[] temp;
	    for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
	     ch = str.charAt(i);
	     temp = new char[] { ch };
	     byte[] uniCode = new String(temp).getBytes();
	     if (uniCode[0] < 128 && uniCode[0] > 0) {// 非汉字
	      buffer.append(temp);
	     } else {
	      buffer.append(convert(uniCode));
	     }
	    }
	    return buffer.toString();
	 }

	 /**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 */

	 static char convert(byte[] bytes) {

	    char result = '-';
	    int secPosvalue = 0;
	    int i;
	    for (i = 0; i < bytes.length; i++) {
	     bytes[i] -= GB_SP_DIFF;
	    }
	    secPosvalue = bytes[0] * 100 + bytes[1];
	    for (i = 0; i < 23; i++) {
	     if (secPosvalue >= secPosvalueList[i]
	       && secPosvalue < secPosvalueList[i + 1]) {
	      result = firstLetter[i];
	      break;
	     }
	    }
	    return result;
	 }
	 
	 /**删除文件夹
	 *param folderPath 文件夹完整绝对路径
	 **/

	 public static void delFolder(String folderPath) {
	      try {
	         delAllFile(folderPath); //删除完里面所有内容
	         String filePath = folderPath;
	         filePath = filePath.toString();
	         java.io.File myFilePath = new java.io.File(filePath);
	         myFilePath.delete(); //删除空文件夹
	      } catch (Exception e) {
	        e.printStackTrace(); 
	      }
	 }

	 //删除指定文件夹下所有文件
	 //param path 文件夹完整绝对路径
	    public static boolean delAllFile(String path) {
	        boolean flag = false;
	        File file = new File(path);
	        if (!file.exists()) {
	          return flag;
	        }
	        if (!file.isDirectory()) {
	          return flag;
	        }
	        String[] tempList = file.list();
	        File temp = null;
	        for (int i = 0; i < tempList.length; i++) {
	           if (path.endsWith(File.separator)) {
	              temp = new File(path + tempList[i]);
	           } else {
	               temp = new File(path + File.separator + tempList[i]);
	           }
	           if (temp.isFile()) {
	              temp.delete();
	           }
	           if (temp.isDirectory()) {
	              delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	              delFolder(path + "/" + tempList[i]);//再删除空文件夹
	              flag = true;
	           }
	        }
	        return flag;
	 }

	//删除指定文件
	public static void delFileList(List<File> fileList) {
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).exists() && fileList.get(i).isFile()) {
				fileList.get(i).delete();
			}
		}
	}

	 

	/**
	 * 文件打包下载2
	 * @param files
	 * @param URL
	 * @return
	 * @throws Exception
	 */
	public static File compressedFiles(List<File> files, String URL, String name)throws Exception {
	        
        /**这个集合就是你想要打包的所有文件，
         * 这里假设已经准备好了所要打包的文件*/
 
        /**创建一个临时压缩文件，
         * 我们会把文件流全部注入到这个文件中		(File.separator)
         * 这里的文件你可以自定义是.rar还是.zip*/
	 	URL = URL+"/"+name;
        File file = new File(URL);
        if (!file.exists()){   
            file.createNewFile();   
        }
        //创建文件输出流
        FileOutputStream fous = new FileOutputStream(file);   
        /**打包的方法我们会用到ZipOutputStream这样一个输出流,
         * 所以这里我们把输出流转换一下*/
       ZipOutputStream zipOut = new ZipOutputStream(fous);
        /**这个方法接受的就是一个所要打包文件的集合，
         * 还有一个ZipOutputStream*/
       zipFile(files, zipOut);
        zipOut.close();
        fous.close();

    
        /**直到文件的打包已经成功了，
         * 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
         * 稍后会呈现出来，接下来的就是往客户端写数据了*/
       
    	return file;
	}
	
	/**
     * 把接受的全部文件打成压缩包 
     * @param List<File>;  
     * @param org.apache.tools.zip.ZipOutputStream  
     */
	public static void zipFile(List<File> files,ZipOutputStream outputStream) {
	    int size = files.size();
	    for(int i = 0; i < size; i++) {
	        File file = (File) files.get(i);
	        zipFile(file, outputStream);
	    }
	}

	/**  
     * 根据输入的文件与输出流对文件进行打包
     * @param File
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void zipFile(File inputFile,
            ZipOutputStream ouputStream) {
        try {
            if(inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.setEncoding("gbk"); // 解决BCSLinux环境 zip压缩包中的文件中文乱码 LYS
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据   
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象   
                    ouputStream.flush();
                    bins.close();
                    IN.close();
                } else {
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
    
    //解压zip文件	
	public static List<File> unZipFiles(File zipFile,String descDir)throws IOException{
		File pathFile = new File(descDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
    	List<File> fileList = new ArrayList<File>();
		for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir+"/"+zipEntryName).replaceAll("\\*", "/");;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if(!file.exists()){
				file.mkdirs();
			}
			//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if(new File(outPath).isDirectory()){
				continue;
			}
			fileList.add(new File(outPath));
			
			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024*1024];
			int len;
			while((len=in.read(buf1))>0){
				out.write(buf1,0,len);
			}
			in.close();
			out.close();
		}
		return fileList;
	}
	
	/**
	 * zip解压zip xiajieli
	 * @param zipFile zip文件
	 * @param descDir 路径
	 * @param EncodingFormat 编码格式
	 * @return
	 * @throws IOException
	 */
	public static List<File> unZipFiles(File zipFile,String descDir,String EncodingFormat)throws IOException{
		File pathFile = new File(descDir);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile,EncodingFormat);
    	List<File> fileList = new ArrayList<File>();
		for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir+File.separator+zipEntryName).replaceAll("\\*", File.separator);
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
			if(!file.exists()){
				file.mkdirs();
			}
			//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if(new File(outPath).isDirectory()){
				continue;
			}
			fileList.add(new File(outPath));
			
			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024*1024];
			int len;
			while((len=in.read(buf1))>0){
				out.write(buf1,0,len);
			}
			in.close();
			out.close();
		}
		return fileList;
	}
    
    
	/**  
     * 拷贝文件
     * @param File
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void copyFile(String oldPath,String newPath) {
        try {
        	int byteSum = 0;
        	int byteRead = 0;
        	File oldFile = new File(oldPath);
        	if (oldFile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteRead = inStream.read(buffer))!=-1) {
					byteSum +=byteRead;
					fs.write(buffer,0,byteRead);
				}
				inStream.close();
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getIpAddr(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getRemoteAddr();
    	}
    	return ip;
    }
	public String getMAC() {
		String mac = null;
		try {
			Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");
			InputStream is = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String message = br.readLine();
			int index = -1;
			while (message != null) {
				if ((index = message.indexOf("Physical Address")) > 0) {
					mac = message.substring(index + 36).trim();
					break;
				}
				message = br.readLine();
			}
			br.close();
			pro.destroy();
		} catch (IOException e) {
			return null;
		}
		return mac;
	}
	
	public String getMAC(String ip) {
		String str = null;
		String macAddress = null;
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; true;) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str
								.substring(str.indexOf("MAC Address") + 14);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
			return null;
		}
		return macAddress;
	}


	
	/**    
     *  获取当前操作系统名称.    
     *  return 操作系统名称 例如:windows,Linux,Unix等.    
    */      
   public String getMacAddr() {      
//       String os = getOSName();   
	   String os = System.getProperty("os.name").toLowerCase();
       String mac;
       if(os.startsWith("windows")){      
           mac = getWindowsMACAddress();      
       }else if(os.startsWith("linux")){      
             mac = getLinuxMACAddress();      
       }else{      
           mac = getUnixMACAddress();                          
       }   
       return mac;
   }      


           
   /**    
     * 获取Unix网卡的mac地址.    
    * @return mac地址    
    */      
   public String getUnixMACAddress() {      
       String mac = null;      
       BufferedReader bufferedReader = null;      
       Process process = null;      
       try {      
             /**   
              *  Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息     
              */   
           process = Runtime.getRuntime().exec("ifconfig eth0");    
           bufferedReader = new BufferedReader(new InputStreamReader(process      
                   .getInputStream()));      
           String line = null;      
           int index = -1;      
           while ((line = bufferedReader.readLine()) != null) {      
                  /**   
                   *  寻找标示字符串[hwaddr]    
                   */   
               index = line.toLowerCase().indexOf("hwaddr");     
                  /**   
                   * 找到了   
                   */   
               if (index != -1) {      
                      /**   
                       *   取出mac地址并去除2边空格     
                       */   
                   mac = line.substring(index +"hwaddr".length()+ 1).trim();    
                   break;      
               }      
           }      
       } catch (IOException e) {      
           e.printStackTrace();      
       } finally {      
           try {      
               if (bufferedReader != null) {      
                   bufferedReader.close();      
               }      
           } catch (IOException e1) {      
              e1.printStackTrace();      
          }      
           bufferedReader = null;      
           process = null;      
       }      
     
       return mac;      
   }      
         
           
           
       /**    
         * 获取Linux网卡的mac地址.    
        * @return mac地址    
        */      
       public String getLinuxMACAddress() {      
           String mac = null;      
           BufferedReader bufferedReader = null;      
           Process process = null;      
           try {      
                 /**   
                  *  linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息     
                  */   
               process = Runtime.getRuntime().exec("ifconfig eth0");    
               bufferedReader = new BufferedReader(new InputStreamReader(process      
                       .getInputStream()));      
               String line = null;      
               int index = -1;      
               while ((line = bufferedReader.readLine()) != null) {      
                   index = line.toLowerCase().indexOf("硬件地址");     
                      /**   
                       * 找到了   
                       */   
                   if (index != -1) {      
                          /**   
                           *   取出mac地址并去除2边空格     
                           */   
                       mac = line.substring(index+4).trim();    
                       break;      
                   }      
               }      
           } catch (IOException e) {      
               e.printStackTrace();      
           } finally {      
               try {      
                   if (bufferedReader != null) {      
                       bufferedReader.close();      
                   }      
               } catch (IOException e1) {      
                  e1.printStackTrace();      
              }      
               bufferedReader = null;      
               process = null;      
           }    
         
           return mac;      
       }    
           
       /**    
        * 获取widnows网卡的mac地址.    
        * @return mac地址    
        */      
       public String getWindowsMACAddress() {      
           String mac = null;      
           BufferedReader bufferedReader = null;      
           Process process = null;      
           try {      
                 /**   
                  * windows下的命令，显示信息中包含有mac地址信息     
                  */   
               process = Runtime.getRuntime().exec("ipconfig /all");    
               bufferedReader = new BufferedReader(new InputStreamReader(process      
                       .getInputStream()));      
               String line = null;      
               int index = -1;     
               int index2 = -1;      
               while ((line = bufferedReader.readLine()) != null) {      
                      /**   
                       *  寻找标示字符串[physical address]    
                       */   
                   index = line.toLowerCase().indexOf("physical address");
                   index2 = line.toLowerCase().indexOf("物理地址");       
                   if (index != -1) {    
                       index = line.indexOf(":");    
                       if (index != -1) {    
                              /**   
                               *   取出mac地址并去除2边空格   
                               */   
                          mac = line.substring(index + 1).trim();     
                      }    
                       break;      
                   }
                   else if(index2 != -1){    
                       index2 = line.indexOf(":");    
                       if (index2 != -1) {    
                              /**   
                               *   取出mac地址并去除2边空格   
                               */   
                          mac = line.substring(index2 + 1).trim();     
                      }    
                       break;  
                   }
               }    
           } catch (IOException e) {      
               e.printStackTrace();      
           }finally {      
               try {      
                   if (bufferedReader != null) {      
                       bufferedReader.close();      
                     }      
               }catch (IOException e1) {      
                   e1.printStackTrace();      
                 }      
               bufferedReader = null;      
               process = null;      
           }      
         
           return mac;      
       }     
	   public static Date getFreqDate(Date date,String freq)
	   {
		   return getFreqDate(date,freq,-1);
		   
	   }
	   /**
	    * 获取评定日期
	    * @param date
	    * @param freq
	    * @param freqValue 大于0，在给定日期上加上频度值，小于零，在给定日期上减去频度值
	    * @return
	    */
       static public Date getFreqDate(Date date, String freq,int freqValue){
    	   Calendar calendar = Calendar.getInstance();
    	   calendar.setTime(date);
    	   
    	   if(freq.equals("1")){
    		   calendar.add(Calendar.DAY_OF_YEAR, freqValue);
    	   }
    	   else if(freq.equals("2")){
    		   calendar.add(Calendar.WEEK_OF_YEAR, freqValue);
    	   }
    	   else if(freq.equals("3")){    	       
    	       calendar.set(Calendar.DAY_OF_MONTH, 0);
    	       
    	   }
    	   else if(freq.equals("4")){
    		   calendar.add(Calendar.MONTH, 3*freqValue);
    		   calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
    	   }
    	   else if(freq.equals("5")){
    		   calendar.add(Calendar.MONTH, 6*freqValue);
    		   calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
    	   }
    	   else if(freq.equals("6")){
    		   calendar.add(Calendar.YEAR, freqValue);
    	   }

    	   return calendar.getTime();
       }

   	public static int getDayNum(String date){
   		date = date.replace("-", "").replace("/", "");
   		Calendar cal = Calendar.getInstance();
   		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0,4)));
   		cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4,6))-1);
   		int dayNum = cal.getActualMaximum(Calendar.DATE);
   		return dayNum;
   	}
   	
   	// 一个汉字当两个字节处理
   	public static int getStrByte(String str){
	   	int count = 0;   
	   	if(str == null){
	   		return count;
	   	}
		String regEx = "[\\u4e00-\\u9fa5]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(str);   
		while (m.find()) {   
			for (int j = 0; j <= m.groupCount(); j++) {   
				count = count + 1;   
			}   
		}   
		return str.length()+count;
	}

   	/** 
   	 * 不区分压缩软件的压缩方法
   	 * 只压缩文件
     * 把文件压缩成zip格式 
     * @param files         需要压缩的文件 
     * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip"; 
     */ 
   	public static File compressFilesZip(List<File> files, String URL, String name) throws Exception { 
    	URL = URL+"/"+name;
        File zipFile = new File(URL);
        if (!zipFile.exists()){   
        	zipFile.createNewFile();   
        }  
        ZipArchiveOutputStream zaos = null;  
        try {  
            zaos = new ZipArchiveOutputStream(zipFile);  
            zaos.setUseZip64(Zip64Mode.AsNeeded);
            zaos.setEncoding("gbk"); // 解决BCSLinux环境 zip压缩包中的文件中文乱码 LYS
            //将每个文件用ZipArchiveEntry封装  
            //再用ZipArchiveOutputStream写到压缩文件中  
            for(File file : files) {  
                if(file != null) {  
                    ZipArchiveEntry zipArchiveEntry  = new ZipArchiveEntry(file,file.getName());  
                    zaos.putArchiveEntry(zipArchiveEntry);  
                    if(file.isDirectory()){  
                        continue;  
                    }  
                    InputStream is = null;  
                    try {  
                        is = new BufferedInputStream(new FileInputStream(file));  
                        byte[] buffer = new byte[1024 ];   
                        int len = -1;  
                        while((len = is.read(buffer)) != -1) {  
                            //把缓冲区的字节写入到ZipArchiveEntry  
                            zaos.write(buffer, 0, len);  
                        }  
                        zaos.closeArchiveEntry();   
                    }catch(Exception e) {  
                        throw new RuntimeException(e);  
                    }finally {  
                        if(is != null)  
                            is.close();  
                    }  
                }  
            }  
            zaos.finish();  
        }
        finally {  
            if(zaos != null) {  
                zaos.close();  
            }  
        }  
        return zipFile;
    }
   	
   	
   	/**
	    * 获取去年同期日期
	    * @param date
	    * @param freq 1表示日，2表示周
	    * @return
	    */
   	public static Date getLastYearSameTime(Date date,String freq){
    	Calendar calendar = Calendar.getInstance();
  	   	calendar.setTime(date);
  	   
  	   	calendar.add(Calendar.YEAR, -1);
  	   	
  	   	if(Integer.parseInt(freq)>2)	//频度大于2时，需要取月末日，以防止2月29日这种情况
  	   		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

  	   	return calendar.getTime();	
    }
    
    /**
     * 根据数据库类型判断是否对ResultSet执行关闭数据库连接操作
     * 此方法只负责关闭ResultSet的数据库连接，不负责关闭ResultSet
     * 执行此方法前需要先执行ResultSet.close()方法。
     * @param rs ResultSet对象
     * @throws Exception
     */
    public static void closeCursor(ResultSet rs) throws Exception
    {
    	IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
		
    	if(dataBaseType!=null && rs!=null)
		{
			if(dataBaseType.equals("oracle"))
				rs.getStatement().close();	
		}
    }
    
    /**
     * 判断数组中是否包含某个值
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean arrayUtilsContains(Object[] arr, Object targetValue) {
        return ArrayUtils.contains(arr,targetValue);
    }
}
