package report.service.imps;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

/*
 * 批量导出
 * 主要功能：根据当前DTO创建出SQL语句和连接供DAO执行	SingleObjectDownloadService
 */

public class ExportReportsService extends BaseObjectDaoResultService{
	
	private String error = "下载失败"; 
//	private String XLSX =".xlsx";
//	private String XLS =".xls";
	String dimension1 = "";
	String dimension2 = "";
	
	// 根据当前DTO取出查询条件并组装DetachedCriteria
	DetachedCriteria detachedCriteria = null;
	IParamObjectResultExecute dao = null;
	List<Object> objectList = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		super.initDao();

		SingleObjectExportReportDataService export = new SingleObjectExportReportDataService();
		// 根据当前条件取得taskRptInst列表
		List<String> fileList = new ArrayList<String>();
		String[] idList = (String[]) RequestManager.getIdList();
		String reportFileName = "reportFile.zip";
		List<TaskRptInst> taskRptInstList;
		
		// 取出当前条件
		Object condtionObject = RequestManager.getTOject();
		Class<?> type = Class.forName(RequestManager.getTName());
		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		
		// 确保是takRptInst
		if(!condtionObject.getClass().getName().equals("report.dto.condition.TaskRptInst_Condition"))
			return;
		if(idList!=null){
			detachedCriteria.add(Restrictions.in("id", idList));
		}
			
			
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		taskRptInstList = (List<TaskRptInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		
		if(taskRptInstList!=null)
		{
			for (int i = 0; i < taskRptInstList.size(); i++) {
				String fileName = export.exportReportData(taskRptInstList.get(i));

				if(i==0){
					reportFileName = taskRptInstList.get(i).getTaskFlow().getStrTaskName()+"_"+taskRptInstList.get(i).getInstInfo().getStrInstName()+"_"+((taskRptInstList.get(i).getTaskFlow().getDtTaskDate().toString()).replace("-", ""))+".zip";
				}
				if(!StringUtils.isBlank(fileName)){
					
					fileList.add(ServletActionContext.getServletContext().getRealPath(fileName));
				}
			}
			if(fileList.size()>0)
			{
				String[] strAryFileName=new String[fileList.size()];
				fileList.toArray(strAryFileName);
				DownloadResult downloadResult = new FileHandler().GetStreamResultFromPath(strAryFileName, reportFileName, 1024*1024*5);
				if(downloadResult.getInputStream() == null){
					this.setServiceResult(new MessageResult(false,error));
				}
				else{
					this.setServiceResult(downloadResult);
				}
				return;
			}
			
		}
		this.setServiceResult(new MessageResult(false,"无数据可导出")); 
	}

}







