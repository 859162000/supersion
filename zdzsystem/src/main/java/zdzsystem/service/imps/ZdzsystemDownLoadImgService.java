package zdzsystem.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQWSNR;
import zdzsystem.dto.AutoDTO_ZDZ_FYSFKZWSNR;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
/**
 * 下载文书
 * @author Administrator
 *
 */
public class ZdzsystemDownLoadImgService extends BaseObjectDaoResultService{

	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute() throws Exception {
		// TODO Auto-generated method stub
		  String tName = RequestManager.getTName();
		  String id = ServletActionContext.getRequest().getParameter("id");
		  IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		  Object resultObject=singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,null});
		  
		  byte[] byteData=null;
		  String fileName="";
		  String fileType="";
		  File file=null;
		  DownloadResult downloadResult=null;
		
		  /**Thread是war包部署在tomcat下面的路径，ServletActionContext是myeclipse自带的tomcat取的路径
			 * myeclipse自带的tomcat无法取得前者路径 */
			String systemPath="";
			systemPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(0).replace("%20", " ");
			
			String tempFile=systemPath;
			File tempTxtFile = new File(tempFile);
			if(!tempTxtFile.exists()){
				systemPath = ServletActionContext.getServletContext().getRealPath("/");
			}
		  
		  
		  if(resultObject!=null){
			  if(tName.equals("zdzsystem.dto.AutoDTO_ZDZ_CXQQNR")){//查询请求内容
				  String WSBH = (String)ReflectOperation.getFieldValue(resultObject, "WSBH");
				  DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_CXQQWSNR.class);
				  detachedCriteria.add(Restrictions.eq("WSBH", WSBH));
				  IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				  List<AutoDTO_ZDZ_CXQQWSNR> CXQQWSNRList = (List<AutoDTO_ZDZ_CXQQWSNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});  
				  
				  File dir = new File(systemPath+WSBH+file.separator);
					if(dir.exists()&&dir.isDirectory()){
						File[] files = dir.listFiles();
						for (File dir1 : files) {
							boolean delete = dir1.delete();
							if(!delete){
								System.gc();
								dir1.delete();
							}
						}
						dir.delete();
					}
					if(!dir.exists()){
						dir.mkdirs();
					}
				  for (AutoDTO_ZDZ_CXQQWSNR autoDTO_ZDZ_CXQQWSNR : CXQQWSNRList) {
					 byteData = (byte[])ReflectOperation.getFieldValue(autoDTO_ZDZ_CXQQWSNR, "WSNR");
					 if(byteData.length<1){
						 continue;
					 }
					  fileName = (String)ReflectOperation.getFieldValue(autoDTO_ZDZ_CXQQWSNR, "WJMC");
					  fileType= (String)ReflectOperation.getFieldValue(autoDTO_ZDZ_CXQQWSNR, "WJLX");
						
					    file = new File(dir,fileName+"."+fileType);
					    if(file.exists()){
					    	 file.delete();
					    }
					    if(!file.exists()){
					    	 file.createNewFile(); 
					    }
					    FileOutputStream out= new FileOutputStream(file);
					    out.write(byteData);
					    out.flush();
					    out.close();
				  	}
				  
				   String zipPath=systemPath+WSBH+".zip";
				    File file2 = new File(zipPath);
				    if(file2.exists()){
				    	file2.delete();
				    }
				    if(!file2.exists()){
				    	file2.createNewFile();
				    }
				    ZipMultiFile(dir.getPath(), zipPath);
				    FileHandler fileHandler = new FileHandler();
				    downloadResult = fileHandler.GetStreamResult(WSBH+".zip", null);
					FileInputStream inputStream = new FileInputStream(zipPath);
				    downloadResult.setInputStream(inputStream);
				  
			  }
			  else if(tName.equals("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR")){//控制请求具体内容
				  String BDHM = (String)ReflectOperation.getFieldValue(resultObject, "BDHM");
				  DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_ZDZ_FYSFKZWSNR.class);
				  detachedCriteria.add(Restrictions.eq("BDHM", resultObject));
				  IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				  List<AutoDTO_ZDZ_FYSFKZWSNR> FYSFKZWSNRList = (List<AutoDTO_ZDZ_FYSFKZWSNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});  
				  
				  File dir = new File(systemPath+BDHM+file.separator);
					if(dir.exists()&&dir.isDirectory()){
						File[] files = dir.listFiles();
						for (File dir1 : files) {
							boolean delete = dir1.delete();
							if(!delete){
								System.gc();
								dir1.delete();
							}
						}
						dir.delete();
					}
					if(!dir.exists()){
						dir.mkdirs();
					}
				  for (AutoDTO_ZDZ_FYSFKZWSNR autoDTO_ZDZ_FYSFKZWSNR : FYSFKZWSNRList) {
					 byteData = (byte[])ReflectOperation.getFieldValue(autoDTO_ZDZ_FYSFKZWSNR, "WSNR");
					 if(byteData.length<1){
						 continue;
					 }
					  fileName = (String)ReflectOperation.getFieldValue(autoDTO_ZDZ_FYSFKZWSNR, "WJMC");
					  fileType= (String)ReflectOperation.getFieldValue(autoDTO_ZDZ_FYSFKZWSNR, "WJLX");
						
					    file = new File(dir,fileName+"."+fileType);
					    if(file.exists()){
					    	 file.delete();
					    }
					    if(!file.exists()){
					    	 file.createNewFile(); 
					    }
					    FileOutputStream out= new FileOutputStream(file);
					    out.write(byteData);
					    out.flush();
					    out.close();
				  	}
				   
				    String zipPath=systemPath+BDHM+".zip";
				    File file2 = new File(zipPath);
				    if(file2.exists()){
				    	file2.delete();
				    }
				    if(!file2.exists()){
				    	file2.createNewFile();
				    }
				    ZipMultiFile(dir.getPath(), zipPath);
				    FileHandler fileHandler = new FileHandler();
				    downloadResult = fileHandler.GetStreamResult(BDHM+".zip", null);
					FileInputStream inputStream = new FileInputStream(zipPath);
				    downloadResult.setInputStream(inputStream);
			  }
			  
			}		
		  if(downloadResult==null){
				MessageResult messageResult = new MessageResult();
				messageResult.setSuccess(false);
				messageResult.getMessageList().add("没有附件,不能下载");
				messageResult.AlertTranslate();
				return messageResult;
		  }else{
				this.setServiceResult(downloadResult);
			}
		  return downloadResult;
		  
	}
	
	public static void ZipMultiFile(String filepath ,String zippath) {
		try {
	        File file = new File(filepath);// 要被压缩的文件夹
	        File zipFile = new File(zippath);
	        byte[] buffer = new byte[1024];
	        InputStream input =null;
	        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
	        zipOut.setEncoding("GBK");
	        if(file.isDirectory()){
	            File[] files = file.listFiles();
	            for(int i = 0; i < files.length; ++i){
	                input = new FileInputStream(files[i]);
	                zipOut.putNextEntry(new ZipEntry(files[i].getName()));
	                int temp = 0;
	                while((temp = input.read(buffer)) >0){
	                    zipOut.write(buffer,0,temp);
	                }
	                input.close();
	            }
	        }
	        zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
