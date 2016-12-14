package szzxpt.service.check;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.TaskModelInst;
import zxptsystem.dto.AutoDTO_QY_DKYW_JC;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class JJXXAndZQXXAllCheck extends JJXXAndZQXXCheck implements ICheck {

	@SuppressWarnings("unchecked")
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		String tName=RequestManager.getTName();
		if(tName.equals(TaskModelInst.class.getName())) {
			String [] taskModeIdList=(String [])RequestManager.getIdList();
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
			detachedCriteria.add(Restrictions.in(ReflectOperation.getPrimaryKeyField(tName).getName(), taskModeIdList));
			List<TaskModelInst> taskModeInstList =(List<TaskModelInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			for(TaskModelInst taskModelInst : taskModeInstList) {
				String jc_tname = ReflectOperation.getAutoDTOTName("sessionFactory", taskModelInst.getReportModel_Table().getStrTableName());
				if(jc_tname.equals(AutoDTO_QY_DKYW_JC.class.getName())) {
					 detachedCriteria = DetachedCriteria.forClass(AutoDTO_QY_DKYW_JC.class);
					 detachedCriteria.add(Restrictions.eq("instInfo",taskModelInst.getInstInfo()));
					 detachedCriteria.add(Restrictions.eq("dtDate",taskModelInst.getTaskFlow().getDtTaskDate()));
					 List<AutoDTO_QY_DKYW_JC> autoDTO_QY_DKYW_JCList = (List<AutoDTO_QY_DKYW_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					 super.JJXXAndZQXX(autoDTO_QY_DKYW_JCList,singleObjectFindByCriteriaDao,detachedCriteria, messageResult);
					 Set<String> message=new LinkedHashSet<String>();
					 for(String ss :messageResult.getMessageSet()) {
						 ss = ss.replace("基础段为", "贷款业务基础表中");
						 message.add(ss);
					 }
					 messageResult.setMessageSet(message);
					 messageResult.setMessage(messageResult.getMessage().replace("基础段为", "贷款业务基础表中"));
				}
			}
		}
		return messageResult;
	}

}
