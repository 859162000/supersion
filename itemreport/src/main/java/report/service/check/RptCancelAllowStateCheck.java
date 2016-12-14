package report.service.check;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.MergeRule;
import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class RptCancelAllowStateCheck implements ICheck{

	public MessageResult Check() throws Exception {
		
        MessageResult messageResult = new MessageResult();

        String tName = RequestManager.getTName();
        String[] idList=(String[])RequestManager.getIdList();
        String message="";
        boolean fail=false;
        if(tName.equals("report.dto.TaskRptInst"))
        {
        	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskRptInst.class);
			detachedCriteria.add(Restrictions.in("id", idList));
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<TaskRptInst> lstTaskRptInst = (List<TaskRptInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			List<String> newIdList=new ArrayList<String>();
			for(TaskRptInst task:lstTaskRptInst)
			{
				fail=false;
				String merge=task.getRptInfo().getStrMergeInst();
				detachedCriteria = DetachedCriteria.forClass(MergeRule.class);
				
				detachedCriteria.add(Restrictions.eq("instInfo",task.getInstInfo()));
				detachedCriteria.createCriteria("mergeInst").add(Restrictions.eq("strMergeName",merge));
				List<MergeRule> mergeRuleList = (List<MergeRule>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				if(mergeRuleList.size()==1 && mergeRuleList.get(0).getHigherInst()!=null)
				{ 
					 detachedCriteria = DetachedCriteria.forClass(TaskRptInst.class);
					detachedCriteria.add(Restrictions.eq("instInfo", mergeRuleList.get(0).getHigherInst()));
					detachedCriteria.add(Restrictions.eq("strAllowState","2"));
					detachedCriteria.add(Restrictions.eq("taskFlow", task.getTaskFlow()));
					detachedCriteria.add(Restrictions.eq("rptInfo", task.getRptInfo()));
					List<TaskRptInst> parentTaskRptInst = (List<TaskRptInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(parentTaskRptInst.size()>0)
					{
						fail=true;
						message=message+task.getRptInfo().getStrBCode()+"\\r\\n";
					}
					
				}
				if(!fail)
					newIdList.add(task.getId());
				
			}
			String [] strNewIdList=new String[newIdList.size()];
			//RequestManager.setIdList(newIdList.toArray(strNewIdList));
			
		    messageResult.setSuccess(idList.length==strNewIdList.length);
	        if(idList.length!=strNewIdList.length)
	        {
	        	message="以下报表上级机构已审核，不能批量进行该操作。\\r\\n"+message;
	        }
	        messageResult.getMessageList().add(message);
			
        }
     
      
		

		return messageResult;
	}

}
