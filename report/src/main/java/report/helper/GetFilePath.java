package report.helper;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import framework.show.ShowContext;

public class GetFilePath {

	public String getWebRootPath()
	{
		String rootPath;
		try {
			rootPath = ServletActionContext.getServletContext().getRealPath("/");
		} catch (Exception e) {
			rootPath= GetFilePath.class.getProtectionDomain().getCodeSource().getLocation().getPath();

			int index=rootPath.indexOf("WEB-INF");
			if(index>0)
			{
				rootPath=rootPath.substring(0, index);
			}
			
			
		}
		return rootPath;
		
	}
	// 下载资源库存放文件路径	
	public String getDownloadResourcePath() {
		String downloadResourcePath = "";
		Map<String,String> downloadResourcePathMap = ShowContext.getInstance().getShowEntityMap().get("downloadResourcePath");
		if(downloadResourcePathMap!=null){
			if(downloadResourcePathMap.containsKey("2")){
				downloadResourcePath = downloadResourcePathMap.get("2");
			}else{
				try {
					downloadResourcePath = ServletActionContext.getServletContext().getRealPath(downloadResourcePathMap.get("1"));
				} catch (Exception e) {
					String appRootPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath().substring(0).replace("%20", " ");
					appRootPath = appRootPath.substring(0, appRootPath.length()-17);
					downloadResourcePath = appRootPath+downloadResourcePathMap.get("1");
				}
				
			}
		}
		return downloadResourcePath;
	}
	 
	// 存放临时文件路径
	public String getTmpFilePath() {
		String tmpFilePath = "";
		Map<String,String> tmpFilePathMap = ShowContext.getInstance().getShowEntityMap().get("tmpFilePath");
		if(tmpFilePathMap!=null){
			if(tmpFilePathMap.containsKey("2")){
				tmpFilePath = tmpFilePathMap.get("2");
			}else{
				try{
					tmpFilePath = ServletActionContext.getServletContext().getRealPath(tmpFilePathMap.get("1"));
				} catch (Exception e) {
					String appRootPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath().substring(0).replace("%20", " ");
					appRootPath = appRootPath.substring(0, appRootPath.length()-17);
					tmpFilePath = appRootPath+tmpFilePathMap.get("1");
				}
			}
		}
		return tmpFilePath;
	}
	 
	// 存放临时文件路径
	public String getModelFilePath(String modelFilePath) {
		String tmpFilePath = "";
		Map<String,String> tmpFilePathMap = ShowContext.getInstance().getShowEntityMap().get(modelFilePath);
		if(tmpFilePathMap!=null){
			if(tmpFilePathMap.containsKey("2")){
				tmpFilePath = tmpFilePathMap.get("2");
			}else{
				try{
					tmpFilePath = ServletActionContext.getServletContext().getRealPath(tmpFilePathMap.get("1"));
				} catch (Exception e) {
					String appRootPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath().substring(0).replace("%20", " ");
					appRootPath = appRootPath.substring(0, appRootPath.length()-17);
					tmpFilePath = appRootPath+tmpFilePathMap.get("1");
				}
			}
		}
		return tmpFilePath;
	}
}
