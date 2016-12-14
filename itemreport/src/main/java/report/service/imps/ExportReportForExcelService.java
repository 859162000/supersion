package report.service.imps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.UserInfo;

import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.security.SecurityContext;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
/**
 * 
 * 获取指定分行中所有的数据数据信息
 * 导出到Excel
 */

public class ExportReportForExcelService extends BaseObjectDaoResultService{
	
	/**
	 * 数据范围，
     * 2,整个支行机构所有数据
     * 3,整个任务的所有数据
	 */
	private String error = "下载失败"; 
	private String dataScope = "1";
	private List<TaskRptInst> lstTaskRptInst;
	private StringBuilder errBuilder=new StringBuilder();
	
	
	//数据下载前，判断数据是否是审核过的数据
	private boolean check()
	{
		boolean result=true;
		if(lstTaskRptInst.size()==0)
		{
			errBuilder.append("无报表数据");
			result=false;
		}
		else
		{
			
			Set<String> reportInst=new HashSet<String>();
			for(TaskRptInst task:lstTaskRptInst)
			{
				reportInst.add(task.getInstInfo().getStrInstCode());
				if(!"2".equals(task.getStrAllowState()))
				{
					errBuilder.append(String.format("机构(%s)报表(%s)未通过审核\\r\\n",task.getInstInfo().getStrInstName(),task.getRptInfo().getStrBCode()));
					result=false;
				}
			}
		}
		return result;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public void initSuccessResult() throws Exception {
		//super.initSuccessResult();
		super.initDao();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String reportFileName = "Download/tmp/ExportReportFile_"+((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode()+"_"+df.format(new Date())+".zip";
		List<String> fileList = new ArrayList<String>();
		
		SingleObjectExportReportDataService export = new SingleObjectExportReportDataService();
		// 根据当前条件取得taskRptInst列表
		//查询的当前的树节点对应的Id
		IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		RequestManager.setId(SessionManager.getTreeIdValue(RequestManager.getTName()));
		TaskRptInst taskRptInst =(TaskRptInst) defaultLogicDaoDao.paramObjectResultExecute(null);
		//获取到数据
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		String tName = RequestManager.getTName();
        Class<?> type = Class.forName(tName);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
		//detachedCriteria = DetachedCriteria.forClass(type);
		//数据过滤组装条件
		if("2".equals(dataScope))
		{
		
			detachedCriteria.add(Restrictions.eq("instInfo",taskRptInst.getInstInfo()));
			detachedCriteria.add(Restrictions.eq("taskFlow",taskRptInst.getTaskFlow()));
		}
		else if("3".equals(dataScope))
		{
			
			detachedCriteria.add(Restrictions.eq("taskFlow",taskRptInst.getTaskFlow()));
		}
		 lstTaskRptInst=(List<TaskRptInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(lstTaskRptInst!=null && check())
		{
			for (int i = 0; i < lstTaskRptInst.size(); i++) {
				String fileName = export.exportReportData(lstTaskRptInst.get(i));
             
				/*if(i==0){
					reportFileName = lstTaskRptInst.get(i).getTaskFlow().getStrTaskName()+"_"+lstTaskRptInst.get(i).getInstInfo().getStrInstName()+"_"+((lstTaskRptInst.get(i).getTaskFlow().getDtTaskDate().toString()).replace("-", ""))+".zip";
				}*/
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
			this.setServiceResult(new MessageResult(true,"报文生成请求已提交，请稍后在【下载资源库】中下载")); 
				
		}else{
			this.setServiceResult(new MessageResult(false,"存在未审核报表，请检查审核完成后再处理！"));
		}
		
		
		
	}
	
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getDataScope() {
		return dataScope;
	}
	
			
}
