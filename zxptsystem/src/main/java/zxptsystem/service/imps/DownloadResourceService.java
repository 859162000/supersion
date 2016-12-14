package zxptsystem.service.imps;

import java.text.SimpleDateFormat;
import java.util.Date;
import coresystem.dto.UserInfo;
import report.dto.DownloadResource;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;

/**
 * 给资源库表新增一条数据，只能下载资源库地址文件下载 
 * @author Administrator
 *
 */
public class DownloadResourceService{
	
	public void DownloadResource(String filePath, String suitCode, String downLoadFileType) throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DownloadResource downloadResource = new DownloadResource();
		downloadResource.setFileType(downLoadFileType);
		downloadResource.setStartTime(simpleDateFormat.format(new Date()));
		
		Suit suit = new Suit();
		suit.setStrSuitCode(suitCode);
		downloadResource.setSuit(suit);
		
		// 如果当前用户为空，则为admin(系统默认最高管理员用户)
		if(SecurityContext.getInstance().getLoginInfo() != null){
			downloadResource.setProposer(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
		}else{
			downloadResource.setProposer("admin");
		}

		downloadResource.setEndTime(simpleDateFormat.format(new Date()));
		downloadResource.setFileName(filePath);
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{downloadResource,null});
	}
}
