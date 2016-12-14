package report.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.MRepLeUpgradeInfo;
import report.dto.MRepUpgradeInfo;
import report.helper.ZipUtil;
import coresystem.dto.PF_LegalInfo;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;

public class MakeRepLeUpgradeService extends BaseService{
	
	/** 升级包路径 **/
	private static String updateDir = "Download/RPTUpdate";
	
	/** 报表模板路径 **/
	private static String excelTmpDir = "Download/formwork";
	
	private IParamVoidResultExecute makeRepUpgradeDao;
	
	IParamVoidResultExecute singleObjectSaveDao;
	
	MRepUpgradeInfo updateHis;
	
	List<MRepLeUpgradeInfo> upgradeList;
	
	/** 制度临时文件 **/
	private String tempDirName = "";
	
	
	public void init() throws Exception{
		makeRepUpgradeDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("makeRepUpgradeDao");
		singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
	}
	
	public void destory() throws Exception{
		if(upgradeList != null)
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{upgradeList,null});
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
			
			MRepLeUpgradeInfo updateInfo = (MRepLeUpgradeInfo) RequestManager.getTOject();
			String[] packInfoIds = updateInfo.getPackInfoIds();
			String[] legalIds = updateInfo.getLegalIds();
			if(packInfoIds.length==0||legalIds.length==0){
				this.setServiceResult(new MessageResult(false, "请选择一个升级包和多个法人进行升级!"));
				return;
			}
			if(packInfoIds.length>1){
				this.setServiceResult(new MessageResult(false, "只能选择一个升级包进行升级!"));
				return;
			}
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			MRepUpgradeInfo upInfo = (MRepUpgradeInfo) singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{MRepUpgradeInfo.class.getName(),packInfoIds[0],null});
			if(upInfo == null){
				this.setServiceResult(new MessageResult(false, "该升级包不存在!"));
				return;
			}
			String destFileName = ServletActionContext.getServletContext().getRealPath(updateDir)+File.separator+upInfo.getPackName()+ "_tmp.zip";
			File destFile = new File(destFileName);
			if(!destFile.exists()){
				this.setServiceResult(new MessageResult(false, "该升级包文件已被删除，请查看!"));
				return;
			}
			tempDirName = destFileName.substring(0,destFileName.lastIndexOf("."));
			ZipUtil.unZip(destFile, tempDirName);
			upgradeList = new ArrayList<MRepLeUpgradeInfo>();
			for(String legalId:legalIds){
				MRepLeUpgradeInfo upg = new MRepLeUpgradeInfo();
				upg.setPackName(upInfo.getPackName());
				upg.setMakeDate(upInfo.getMakeDate());
				PF_LegalInfo legalInfo = (PF_LegalInfo) singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{PF_LegalInfo.class.getName(),legalId,null});
				upg.setUpdateDate(new Date());
				if(null != legalInfo){
					upg.setLegalInfo(legalInfo);
					try{
						makeRepUpgradeDao.paramVoidResultExecute(new Object[]{tempDirName,legalInfo.getStrSessionFactory()});
					}catch(Exception e){
						upg.setStrStatus("-1");
						upg.setDescript(e.toString());
						ExceptionLog.CreateLog(e);
					}
				}
				upgradeList.add(upg);
			}
			this.setServiceResult(new MessageResult("升级完成！"));
		}catch(Exception e){
			ExceptionLog.CreateLog(e);
			this.setServiceResult(new MessageResult(false,"升级失败！"));
		}finally{
			destory();
		}
		
	}
	
	
	
}
