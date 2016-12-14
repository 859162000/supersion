package report.service.imps;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskFlow;
import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

public class SelectShowTaskRptInstService extends BaseObjectDaoResultService{

	@Override
	public void initSuccessResult() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		String taskID = RequestManager.getReportCheckParam().get("TaskID");

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
		
		if(taskID != null && !"".equals(taskID) ) {
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			TaskFlow taskFlow = (TaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.TaskFlow", taskID, null});
			
			detachedCriteria.add(Restrictions.eq("taskFlow", taskFlow));
		}
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    	List<TaskRptInst> objectListTaskRptInstInfo = (List<TaskRptInst>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	
		StringBuffer ItemInfos = new StringBuffer();
		ItemInfos.append("状态,机构名称,报表名称;");
		for(TaskRptInst taskRptInst : objectListTaskRptInstInfo) {
			ItemInfos.append(taskRptInst.getStrAllowState());
			ItemInfos.append(",");
			ItemInfos.append(taskRptInst.getInstInfo().getStrInstName());
			ItemInfos.append(",");
			ItemInfos.append(taskRptInst.getRptInfo().getStrRptName());
			ItemInfos.append(";");
		}
		if(ItemInfos==null){
			ItemInfos.append("");
		}
		response.getWriter().write(ItemInfos.toString());
			
	}
}
