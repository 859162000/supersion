package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;

import extend.helper.HelpTool;import zxptsystem.dto.QYZXDownload;
import zxptsystem.dto.condition.QYZXDownload_Condition;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;

public class ShowDownLoadRportService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<QYZXDownload> qYZXDownloadList = (List<QYZXDownload>)this.getServiceResult();
		
		String strVersion="";
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object qyzx_strVersion = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "qyzx_strVersion" , null});
		 if(qyzx_strVersion != null) {
			SystemParam systemParam = (SystemParam)qyzx_strVersion;
			if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
				strVersion=systemParam.getStrParamValue();
			}
		}
		if(!StringUtils.isBlank(strVersion)){
			if(strVersion.equals("2.2")){
				List delList = new ArrayList();
				for (QYZXDownload qyzxDownload : qYZXDownloadList) {
					if(qyzxDownload.getStrReportCode().equals("01") || qyzxDownload.getStrReportCode().equals("02")){
						delList.add(qyzxDownload);
					}
				}
				qYZXDownloadList.removeAll(delList);
			}
		}
		
		QYZXDownload_Condition qYZXDownload_Condition = (QYZXDownload_Condition)RequestManager.getTOject();
		
		if(qYZXDownload_Condition == null || StringUtils.isBlank(qYZXDownload_Condition.getStrReportType())){
			qYZXDownloadList = new ArrayList<QYZXDownload>();
		}
		
		QYZXDownload_Condition objectDestination = (QYZXDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("qYZXDownload_Condition");
		
		if(objectDestination != null){
			
			qYZXDownload_Condition.setYWFSRQ(objectDestination.getYWFSRQ());
			qYZXDownload_Condition.setStrJRJGCode(objectDestination.getStrJRJGCode());
			qYZXDownload_Condition.setStrReportCode(objectDestination.getStrReportCode());
			
			if(qYZXDownload_Condition.getYWFSRQ()==null){
				qYZXDownload_Condition.setYWFSRQ(HelpTool.getBeforeDate("yyyy-MM-dd"));
			}
			
			if(qYZXDownload_Condition.getStrJRJGCode()==null){
				qYZXDownload_Condition.setStrJRJGCode(ReportInstUtils.getCurrentUserInJRJGCode("21,24"));
			}
		}
		
		this.setServiceResult(qYZXDownloadList);
	}
}
