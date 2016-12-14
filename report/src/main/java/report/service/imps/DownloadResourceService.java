package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import report.dto.DownloadResource;
import report.helper.GetFilePath;
import coresystem.dto.UserInfo;
import extend.dto.Suit;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
/**
 * 下载资源库 相关操作
 * @author Administrator
 *
 */
public class DownloadResourceService implements IObjectResultExecute {

    private Integer bufferSize;
    
	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}
	
	/**
	 * 下载按钮链接处理
	 */
	public Object objectResultExecute() throws Exception {
		try{new GetFilePath().getDownloadResourcePath();
			IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			DownloadResource downloadResource = (DownloadResource)byIdDao.paramObjectResultExecute(new Object[]{"report.dto.DownloadResource", RequestManager.getLevelIdValue() , null});
			InputStream inputStream = null;
			String fileName = "";
			try {
				inputStream = new FileInputStream(downloadResource.getFileName());
				fileName = downloadResource.getFileName().split("\\\\")[downloadResource.getFileName().split("\\\\").length-1];
			} catch (Exception e) {
				inputStream = new FileInputStream(new GetFilePath().getDownloadResourcePath()+File.separator+downloadResource.getFileName());
				fileName = downloadResource.getFileName();
			}
			DownloadResult downloadResult = new FileHandler().GetStreamResultFromInputStream(inputStream, fileName, bufferSize);
			if(downloadResult.getInputStream() == null){
				return new MessageResult(false,"下载失败,文件不存在!");
			}
			
			// 如果当前用户为空，则为admin(系统默认最高管理员用户)
			if(SecurityContext.getInstance().getLoginInfo() != null){
				downloadResource.setDownloadOperator(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
			}else{
				downloadResource.setDownloadOperator("admin");
			}

			IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
			singleObjectUpdateDao.paramVoidResultExecute(new Object[]{downloadResource,null});
			return downloadResult;
		}
		catch(Exception ex){
			return new MessageResult(false,ex.getMessage());
		}
	}
	
	/**
	 * 文件放入资源库前操作
	 * @param fileName
	 * @param suitCode
	 */
	@SuppressWarnings("unchecked")
	public void addStart(String fileName, String suitCode){
		try{
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DownloadResource downloadResource = new DownloadResource();
			downloadResource.setFileName(fileName);
			downloadResource.setFileType("1");
			downloadResource.setStartTime(simpleDateFormat.format(new Date()));
			
			if(suitCode!=null && suitCode!=""){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Suit.class);
				detachedCriteria.add(Restrictions.eq("strSuitCode", suitCode));
				List<Object> list = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null}); // 根据配置的主题代码，精准 查询一个主题
				if(list.size()>0){
					downloadResource.setSuit((Suit) list.get(0));
				}
			}
			
			
				// 如果当前用户为空，则为admin(系统默认最高管理员用户)
			
			if(SecurityContext.getInstance().getLoginInfo() != null){
				downloadResource.setProposer(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
			}else{
				downloadResource.setProposer("admin");
			}
			
	
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{downloadResource,null});
		
		}catch (Exception e) {
			ExceptionLog.CreateLog(e);
		}
	}
	
	/**
	 * 文件放入资源库后操作
	 * @param fileName
	 * @param suitCode
	 */
	@SuppressWarnings("unchecked")
	public void addEnd(String fileName, String suitCode){

		try{
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DownloadResource.class);
			detachedCriteria.add(Restrictions.eq("fileName", fileName));
			detachedCriteria.addOrder(Order.desc("startTime"));
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<Object> list = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null}); // 根据配置的主题代码，精准 查询一个主题
			
			DownloadResource downloadResource = new DownloadResource();
			if(list.size()>0){
				downloadResource = (DownloadResource) list.get(0);
			}
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			downloadResource.setEndTime(simpleDateFormat.format(new Date()));
			IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
			singleObjectUpdateDao.paramVoidResultExecute(new Object[]{downloadResource,null});
			
		}catch (Exception e) {
			
		}
	}
	
	/**
	 * 给资源库表新增一条数据，只能下载资源库地址文件下载 
	 * @param filePath
	 * @param suitCode
	 * @param downLoadFileType
	 * @throws Exception
	 */
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
	
	/**
	 * 给资源库表新增一条数据，只能下载资源库地址文件下载 （征信平台）
	 * @param filePath
	 * @param suitCode
	 * @param downLoadFileType
	 * @param fileDiscription
	 * @throws Exception
	 */
	public void DownloadResource(String filePath, String suitCode, String downLoadFileType,String fileDiscription) throws Exception {
		
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
		downloadResource.setFileDiscription(fileDiscription);
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{downloadResource,null});
	}

}
