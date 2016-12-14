package autoETLsystem.service.procese;

import java.util.HashMap;
import java.util.Map;

import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_Workflow;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.services.procese.SelectTagSortByNameProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class AutoETL_WorkflowAEProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_Workflow autoETL_Workflow = (AutoETL_Workflow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_Workflow.class.getName(),id,null});

		if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
				if(showFieldValue.getShowField().getFieldName().equals("autoETL_ActivityNode")){
					Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					
					//1.获取活动结点的工作组
					for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_Workflow.getAutoETL_ActivityNodes()){
						if(tagMap.containsKey(autoETL_ActivityNode.getAutoActivityNodeID())){
							String strGroup = autoETL_ActivityNode.getStrOrderGroup();
							if(strGroup == null){
								strGroup = "空顺序组";
							}
							strGroup += "-";
							temPTagMap.put(autoETL_ActivityNode.getAutoActivityNodeID(), strGroup +autoETL_ActivityNode.getIntOrder()+"-"+ autoETL_ActivityNode.getStrActivityNodeName());
						}
					}
					
					
					//2.工作组排序
					SelectTagSortByNameProcese SelectTagSortByNameProcese=new SelectTagSortByNameProcese();
					temPTagMap=SelectTagSortByNameProcese.sortMapByValue(temPTagMap);
					//3.重新设置值
					for(Map.Entry<String, String> entry : temPTagMap.entrySet())
					{
						entry.setValue(entry.getValue().split("-")[0]+"-"+entry.getValue().split("-")[2]);
					}
					showFieldValue.setTag(temPTagMap);
					break;
				}	
			}
			
		}

		return serviceResult;
	}
}
