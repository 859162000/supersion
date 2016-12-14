package report.service.translate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ITranslate;

public class AutoDTOUpdateFlowStatusTranslate implements ITranslate{

	public void Translate() throws Exception {
		Object previousObject=null;
		String previousLevelTName=SessionManager.getPreviousLevelTName();
		String tName=RequestManager.getTName();
		Object[] objectList=(Object[])RequestManager.getIdList();
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object tObject=singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,objectList[0],null});
		
		if(previousLevelTName.contains("AutoDTO_")){
			List<Field> listField=ReflectOperation.getJoinColumnFieldList(tName);
			for (Field field : listField) {
				if(field.getType().equals(Class.forName(previousLevelTName))){
					Object foreignObject=ReflectOperation.getFieldValue(tObject, field.getName());
					String primaryKey_Jc=ReflectOperation.getPrimaryKeyValue(foreignObject).toString();
					previousObject=singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{previousLevelTName,primaryKey_Jc,null});
					break;
				}
			}
		}else{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			String strTableName=tName.substring(tName.indexOf("AutoDTO_")+8);
			detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
			List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			detachedCriteria.add(Restrictions.eq("dtTaskDate", ReflectOperation.getFieldValue(tObject, "dtDate")));
		    List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			
		    detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			detachedCriteria.add(Restrictions.eq("instInfo", ReflectOperation.getFieldValue(tObject, "instInfo")));
			detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
			detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
		    List<TaskModelInst>  taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    previousObject=taskModelInstList.get(0);
		}
		
		
		((Map<String,Object>)ServletActionContext.getContext().get("request")).put("previousObject", previousObject);
	}
}
