package report.service.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;


public class ShowTaskTreeTranslate implements ITranslate{

	@SuppressWarnings("unchecked")
	public void Translate() throws Exception {

		Class<?> type = Class.forName(RequestManager.getTName());

		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		
		String[] strSuitCode = (String[])((Map<String,Object>)ServletActionContext.getContext().get("request")).get("show_treeSuitCode");
		if(strSuitCode != null && strSuitCode.length > 0){
			if(type.equals(TaskModelInst.class)){
				List<TaskFlow> taskFlowList = new ArrayList<TaskFlow>();
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria tempDetachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Suit suit;
				List<TaskFlow> tempTaskFlowList;
				
				for(int i=0;i<strSuitCode.length;i++){
					suit = (Suit)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{Suit.class.getName(),strSuitCode[i],null});
					tempDetachedCriteria.add(Restrictions.eq("suit", suit));
					tempTaskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{tempDetachedCriteria,null});
					for(TaskFlow taskFlow : tempTaskFlowList){
						taskFlowList.add(taskFlow);
					}
				}
				
				if(taskFlowList.size() == 0){
					detachedCriteria.add(Restrictions.isNull("taskFlow"));
				}
				else{
					detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
				}
			}
		}
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}
}
