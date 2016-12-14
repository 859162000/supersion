package report.service.imps;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskRptInst;
import framework.Jobs.Task;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.MessageResult;

/*
 * IJ文件生成
 */

public class ExportReportsWithXmlService extends BaseObjectDaoResultService{
	
    /*
     * 数据范围，
     * 1，单个taskrptInst，
     * 2，一个机构下的一个任务 
     * 3,全任务
     */ 
	private String dataScope="1";
	
	
	public String getDataScope()
	{
		return dataScope;
	}
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}
	
	private List<TaskRptInst> lstTaskRptInst;
	private IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");;
	private DetachedCriteria detachedCriteria = null;
	private StringBuilder errBuilder=new StringBuilder();

	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		//super.initSuccessResult();
		//super.initDao();
		getReportInfo();
		if(check())
		{
			makeIJFile();
			this.setServiceResult(new MessageResult(true,"报文生成请求已提交，请稍后在【下载资源库】中下载"));
			
		}
		else
		{
			this.setServiceResult(new MessageResult(false,errBuilder.toString()));
		}
	}
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
	private void getReportInfo() throws Exception
	{
	
		IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		RequestManager.setId(SessionManager.getTreeIdValue(RequestManager.getTName()));
		TaskRptInst object =(TaskRptInst) defaultLogicDaoDao.paramObjectResultExecute(null);
		
		getTaskRptInsts( object);
		
			
	}
	private void getTaskRptInsts(TaskRptInst object)
			throws Exception {
		String tName = RequestManager.getTName();
        Class<?> type = Class.forName(tName);
		detachedCriteria = DetachedCriteria.forClass(type);
		if("1".equals(dataScope))
		{
			detachedCriteria.add(Restrictions.eq("id",RequestManager.getLevelIdValue()));
		}
		else if("2".equals(dataScope))
		{
			detachedCriteria.add(Restrictions.eq("instInfo",object.getInstInfo()));
			detachedCriteria.add(Restrictions.eq("taskFlow",object.getTaskFlow()));
		}
		else if("3".equals(dataScope))
		{
			
			detachedCriteria.add(Restrictions.eq("taskFlow",object.getTaskFlow()));
		}
		
		
		
		lstTaskRptInst = (List<TaskRptInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	}
	
	private void makeIJFile()
	{
		XmlFileBuilder xmlBuilder=new XmlFileBuilder(dataScope,lstTaskRptInst);
		Task.execute(xmlBuilder, "make");
	}
	
}







