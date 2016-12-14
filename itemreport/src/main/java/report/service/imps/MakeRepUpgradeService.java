package report.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.MRepUpgradeInfo;
import report.helper.ZipUtil;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;

public class MakeRepUpgradeService extends BaseService{
	
	/** 升级包路径 **/
	private static String updateDir = "Download/RPTUpdate";
	
	/** 报表模板路径 **/
	private static String excelTmpDir = "Download/formwork";
	
	private IParamVoidResultExecute makeRepUpgradeDao;
	
	IParamVoidResultExecute singleObjectSaveDao;
	
	MRepUpgradeInfo updateHis;
	
	/** 制度临时文件 **/
	private String tempDirName = "";
	
	
	public void init() throws Exception{
		makeRepUpgradeDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("makeRepUpgradeDao");
		singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		updateHis = new MRepUpgradeInfo();
		updateHis.setUpdateDate(new Date());
	}
	
	public void destory() throws Exception{
		if(null != updateHis)
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{updateHis,null});
		File dir = new File(tempDirName);
		if(dir.exists())
			FileUtils.deleteDirectory(dir);
	}
	
	public void makeHistory() throws Exception{
		FileReader reader = null;
		BufferedReader br = null;
		try{
			File info = new File(tempDirName + File.separator + "info.m");
			if(!info.exists()){
				throw new Exception("升级包不存在升级描述文件【info.m】");
			}
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			reader = new FileReader(info);
			br = new BufferedReader(reader);
			updateHis.setPackName(br.readLine());
			updateHis.setMakeDate(sp.parse(br.readLine()));
			updateHis.setStrStatus("0");
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(null != br)
					br.close();
				if(null != reader)
					reader.close();
			}catch(Exception e){
				throw e;
			}
		}
	}
	
	public void copyTemplateFile() throws Exception{
		File srcDir= new File(tempDirName+File.separator+"TEMPLATE");
		File destDir = new File(ServletActionContext.getServletContext().getRealPath(excelTmpDir));
		FileUtils.copyDirectory(srcDir, destDir);
	}
	
	@Override
	public void initSuccessResult() throws Exception {
		try{
			Map<String,String> paramMap = RequestManager.getReportCheckParam();
			String uploadFileFileName = paramMap.get("uploadFileFileName");
			if(StringUtils.isBlank(uploadFileFileName) || !uploadFileFileName.endsWith(".zip")){
				throw new Exception("请上传zip升级包！");
			}
			String destFileName = ServletActionContext.getServletContext().getRealPath(updateDir)+File.separator+uploadFileFileName.replace(".zip", "_tmp.zip");
			File destFile = new File(destFileName);
			File uploadFile = RequestManager.getUploadFile();
			FileUtils.copyFile(uploadFile, destFile);
			tempDirName = destFileName.substring(0,destFileName.lastIndexOf("."));
			ZipUtil.unZip(destFile, tempDirName);
			makeHistory();
			copyTemplateFile();
			makeRepUpgradeDao.paramVoidResultExecute(new Object[]{tempDirName,null});
			updateHis.setStrStatus("1");
			this.setServiceResult(new MessageResult("升级成功！"));
		}catch(Exception e){
			updateHis.setStrStatus("-1");
			ExceptionLog.CreateLog(e);
			this.setServiceResult(new MessageResult(false,"升级失败！"));
		}finally{
			destory();
		}
		
	}
	
	
	
}
