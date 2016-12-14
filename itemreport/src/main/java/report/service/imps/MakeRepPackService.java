package report.service.imps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.MRepPackInfo;
import report.helper.ZipUtil;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：MakeRepZipService</P>
 * *********************************<br>
 * <P>类描述：制度包制作Service</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-18 上午11:35:21<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-18 上午11:35:21<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class MakeRepPackService extends BaseService{
	
	/** 升级包路径 **/
	private static String updateDir = "Download/RPTUpdate";
	
	/** 报表模板路径 **/
	private static String excelTmpDir = "Download/formwork";
	
	/** 下载路径 **/
	private String downLoadDir = "";
	
	/** 制度临时文件目录 **/
	private File tempDir;
	
	/** 制度临时文件 **/
	private String tempDirName = "";
	
	private MRepPackInfo packInfo;
	
	private IParamVoidResultExecute makeRepPackDao;
	
	IParamVoidResultExecute singleObjectSaveDao;
	
	public void init() throws Exception{
		makeRepPackDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("makeRepPackDao");
		singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		downLoadDir = ServletActionContext.getServletContext().getRealPath(updateDir);
		Date mkDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String packName = sdf.format(mkDate)+"_制度包";
		tempDirName = downLoadDir+File.separator+packName;
		tempDir = new File(tempDirName);
		FileUtils.deleteDirectory(tempDir);
		tempDir.mkdir();
		packInfo = new MRepPackInfo();
		packInfo.setMakeDate(mkDate);
		packInfo.setPackName(packName);
		packInfo.setAuthor(SecurityContext.getInstance().getSysUserCode());
	}
	
	public void destory() throws Exception{
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{packInfo,null});
		if(tempDir != null ){
			FileUtils.deleteDirectory(tempDir);
		}
	}
	
	public void makeUpdate() throws Exception{
		makeRepPackDao.paramVoidResultExecute(tempDirName);
	}
	
	public void copyTemplateFile() throws Exception{
		File srcDir = new File(ServletActionContext.getServletContext().getRealPath(excelTmpDir));
		File destDir = new File(tempDir.getAbsolutePath()+File.separator+"TEMPLATE");
		FileUtils.copyDirectory(srcDir, destDir);
	}
	
	public void makeInfo() throws Exception{
		FileWriter writer = null;
		BufferedWriter bw = null;
		try{
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			File info = new File(tempDirName + File.separator + "info.m");
			writer = new FileWriter(info);
			bw = new BufferedWriter(writer);
			bw.write(packInfo.getPackName());
			bw.newLine();
			bw.write(sp.format(packInfo.getMakeDate()));
			bw.newLine();
			bw.write(packInfo.getAuthor()+"");
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(null != bw)
					bw.close();
				if(null != writer)
					writer.close();
			}catch(Exception e){
				throw e;
			}
		}
	}
	
	@Override
	public void initSuccessResult() throws Exception {
		try{
			//模板复制
			copyTemplateFile();
			//制度生成信息
			makeInfo();
			//制作制度包
			makeUpdate();
			String zipFileName = tempDirName+".zip";
			//压缩包生成
			ZipUtil.zip(tempDir, zipFileName);
			packInfo.setDescript("制度包制作成功!");
			this.setServiceResult(new MessageResult("生成成功！"));
		} catch(Exception e){
			packInfo.setDescript("制度包制作失败!");
			ExceptionLog.CreateLog(e);
			this.setServiceResult(new MessageResult(false,"生成失败！"));
		}finally{
			destory();
		}
		return;
	}
	
	
}
