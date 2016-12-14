package report.service.check;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskModelInst;
import report.dto.TaskRptInst;


import framework.bean.context.ServiceContextList;
import framework.helper.BeanFactoryUtils;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;

import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/*
 * 扩展check wrapper,目前用于明细任务审核提交check，
 *
 */
public class ExtPointWrapperForTaskCheck implements ICheck {

	private List<String> extendCheckList;
	public List<String> getExtendCheckList() {
		return extendCheckList;
	}
	public void setExtendCheckList(List<String> extendCheckList) {
		this.extendCheckList = extendCheckList;
	}
	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		String[] ids=(String[]) RequestManager.getIdList();
		if(TaskModelInst.class.getName().equals(RequestManager.getTName())&&
				ids!=null)
		{
			
			
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			
			detachedCriteria.add(Restrictions.in("id",ids ));
			List<TaskModelInst> taskModelInstList = (List<TaskModelInst>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			ExtPointWrapperForTaskCheck extendCheck=null;
			for (TaskModelInst taskModelInst : taskModelInstList) {
				extendCheck=(ExtPointWrapperForTaskCheck) BeanFactoryUtils.getInstance().getBean("extendPointWrapperCheck");
				merageServiceProperty("extendPointWrapperCheck",taskModelInst.getReportModel_Table().getStrTableName(),extendCheck);
				List<String> extendCheckList=extendCheck.getExtendCheckList();
				if(extendCheckList != null){
					for(String str : extendCheckList){
						ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
						MessageResult currentResult = check.Check();
						if(!currentResult.isSuccess()) {
							messageResult = currentResult;
							break;
						}
					}
				}
			}
			
		}
		else if(!TaskRptInst.class.getName().equals(RequestManager.getTName()))
		{
			ExtPointWrapperForTaskCheck extendCheck=(ExtPointWrapperForTaskCheck) BeanFactoryUtils.getInstance().getBean("extendPointWrapperCheck");
			merageServiceProperty("extendPointWrapperCheck",RequestManager.getTName(),extendCheck);
			List<String> extendCheckList=extendCheck.getExtendCheckList();
			if(extendCheckList != null){
				for(String str : extendCheckList){
					ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
					MessageResult currentResult = check.Check();
					if(!currentResult.isSuccess()) {
						messageResult = currentResult;
						break;
					}
				}
			}
		}
		
		
		return messageResult;
	}
	private void merageServiceProperty(String beanId,String dtoName,Object check) throws Exception
	{
		ServiceContextList contextList=(ServiceContextList)FrameworkFactory.CreateBean("serviceBeanContextList");
		contextList.meragePropertys(beanId, dtoName, check);
	}
}
