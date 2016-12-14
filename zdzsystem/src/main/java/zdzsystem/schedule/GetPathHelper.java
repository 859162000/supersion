package zdzsystem.schedule;


import java.io.File;
import java.util.Map;

import framework.show.ShowContext;

/**
 * 获取上传下载相关路径
 * @author Administrator
 */
public class GetPathHelper {

	/**
	 * 获取上传相关目录是否存在
	 * @return
	 */
	public static boolean GetUploadPathIsExsist() {
		Map<String, String> User_Defined_Path;
		if(ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path")!=null){
			User_Defined_Path=ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path");
			
			//上传文件目录
			if(User_Defined_Path.get("uploadPath")==null){
				return false;
			}else{
				String uploadPath=User_Defined_Path.get("uploadPath");
				File file=new File(uploadPath);
				if(!file.isDirectory()){
					return false;
				}
			}
			
			//上传文件临时目录
			if(User_Defined_Path.get("uploadPathTmp")==null){
				return false;
			}
			else{
				String uploadPathTmp=User_Defined_Path.get("uploadPathTmp");
				File file=new File(uploadPathTmp);
				if(!file.isDirectory()){
					return false;
				}
			}
			
			//电子印章路径
			if(User_Defined_Path.get("dzyzPath")==null){
				return false;
			}
			else{
				String dzyzPath=User_Defined_Path.get("dzyzPath");
				File file=new File(dzyzPath);
				if(!file.isFile()){
					return false;
				}
			}
			
		}else{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 获取下载相关目录是否存在
	 * @return
	 */
	public static boolean GetDownloadPathIsExsist() {
		Map<String, String> User_Defined_Path;
		if(ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path")!=null){
			User_Defined_Path=ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path");
			//下载文件原始目录
			if(User_Defined_Path.get("downloadPath")==null){
				return false;
			}
			else{
				String downloadPath=User_Defined_Path.get("downloadPath");
				File file=new File(downloadPath);
				if(!file.isDirectory()){
					return false;
				}
			}
			//下载文件存储目录
			if(User_Defined_Path.get("storagePath")==null){
				return false;
			}
			else{
				String storagePath=User_Defined_Path.get("storagePath");
				File file=new File(storagePath);
				if(!file.isDirectory()){
					return false;
				}
			}
			//下载文件处理失败目录
			if(User_Defined_Path.get("errorPath")==null){
				return false;
			}
			else{
				String errorPath=User_Defined_Path.get("errorPath");
				File file=new File(errorPath);
				if(!file.isDirectory()){
					return false;
				}
			}
			
		}else{
			return false;
		}
		
		return true;
	}

	
	/**
	 * 获取上传或者下载相关目录（有斜线）
	 * @return
	 */
	public static String GetPath(String inpath) {
		
		String outPath=null;
		Map<String, String> User_Defined_Path;
		if(ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path")!=null){
			User_Defined_Path=ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path");
			if(User_Defined_Path.get(inpath)!=null){
				outPath=User_Defined_Path.get(inpath);
				if(!outPath.endsWith(File.separator)){
					outPath=outPath + File.separator;
		      	}
			}
		}
		return outPath;
	}

	/**
	 * 获取上传或者下载相关目录（没有斜线）
	 * @return
	 */
	public static String GetPathNoSlash(String inpath) {
		
		String outPath=null;
		Map<String, String> User_Defined_Path;
		if(ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path")!=null){
			User_Defined_Path=ShowContext.getInstance().getShowEntityMap().get("User_Defined_Path");
			if(User_Defined_Path.get(inpath)!=null){
				outPath=User_Defined_Path.get(inpath);
			}
		}
		return outPath;
	}
	
	
	/**
	 * 获取相关常量
	 * @return
	 */
	public static String GetConst(String consts) {
		
		String outconst=null;
		Map<String, String> User_Defined_Const;
		if(ShowContext.getInstance().getShowEntityMap().get("User_Defined_Const")!=null){
			User_Defined_Const=ShowContext.getInstance().getShowEntityMap().get("User_Defined_Const");
			if(User_Defined_Const.get(consts)!=null){
				outconst=User_Defined_Const.get(consts);
			}
		}
		return outconst;
	}
	
}
