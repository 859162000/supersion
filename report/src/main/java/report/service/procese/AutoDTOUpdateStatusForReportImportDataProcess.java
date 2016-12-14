package report.service.procese;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;

public class AutoDTOUpdateStatusForReportImportDataProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		String tName=RequestManager.getTName();
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),id,null});
		
		String previousLevelLevelTName  = SessionManager.getPreviousLevelTName();
		Object foreignObject = FrameworkFactory.CreateClass(previousLevelLevelTName);
		
		String currentLevelLevelMX = SessionManager.getCurrentLevel();
		String PreviousId = SessionManager.getLevelIdValue(currentLevelLevelMX);
		IParamObjectResultExecute singleObjectFindByIdDaoMX = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object object_JC = (Object)singleObjectFindByIdDaoMX.paramObjectResultExecute(new Object[]{foreignObject.getClass().getName(),PreviousId,null});
		
		Set<String> importPrimaryKeySet = new HashSet<String>();
		
		if(taskModelInst==null){
			tName = previousLevelLevelTName;
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = null;
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			String strTableName=previousLevelLevelTName.substring(previousLevelLevelTName.indexOf("AutoDTO_")+8);
			detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
			List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			detachedCriteria.add(Restrictions.eq("dtTaskDate", ReflectOperation.getFieldValue(object_JC, "dtDate")));
		    List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			
		    detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			detachedCriteria.add(Restrictions.eq("instInfo", ReflectOperation.getFieldValue(object_JC, "instInfo")));
			detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
			detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
		    List<TaskModelInst>  taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    taskModelInst=taskModelInstList.get(0);
		    
		    
		    detachedCriteria = DetachedCriteria.forClass(Class.forName(previousLevelLevelTName));
			detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
			detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		    List<Object>  objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		    List<Field> listField=ReflectOperation.getOneToManyField(previousLevelLevelTName);
		    
		    List<Object> listMX=(List<Object>)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("insertDataList");
		    if(listMX!=null){
		    	if(listMX.size() > 0){
					Field field = null;
					
					for(Field tempField:ReflectOperation.getJoinColumnFieldList(listMX.get(0).getClass())){
						if(tempField.getType().getName().equals(previousLevelLevelTName)){
							field = tempField;
							break;
						}
					}
					for (Object objectMX : listMX) {
						Object foreignObjectJC = ReflectOperation.getFieldValue(objectMX, field.getName());
						if(foreignObjectJC!=null){
							String foreignObjectPrimaryKey = ReflectOperation.getPrimaryKeyValue(foreignObjectJC).toString();
							if(!importPrimaryKeySet.contains(foreignObjectPrimaryKey)){
								importPrimaryKeySet.add(foreignObjectPrimaryKey);
							}
						}
					}
				}
		    }
		    
		    for (Object object : objectList) {
		    	
		    	if(importPrimaryKeySet.contains(ReflectOperation.getPrimaryKeyValue(object).toString())){
		    		ReflectOperation.setFieldValue(object, "RPTSubmitStatus", "1");
		    		ReflectOperation.setFieldValue(object, "RPTVerifyType", "1");
		    	}
		    	
		    	int RPTCheckTypeSuccess=0;
				int RPTCheckTypeFalse=0;
				int unRPTCheckTypeCount=0;
				
				int RPTSendTypeMXSuccess=0;
				int unRPTSendTypeMXCount=0;
				
				int feedbackSuccess=0;
				int feedbackFalse=0;
				int unFeedbackCount=0;
		    	
				for (Field field : listField) {
					
					IParamObjectResultExecute singleObjectFindCountByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));	
					detachedCriteria.add(Restrictions.in("RPTCheckType",new String[]{"2"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					RPTCheckTypeSuccess += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTCheckType",new String[]{"1"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					unRPTCheckTypeCount += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTCheckType",new String[]{"3"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					RPTCheckTypeFalse += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				   
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTSendType",new String[]{"2"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					RPTSendTypeMXSuccess += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTSendType",new String[]{"1"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					unRPTSendTypeMXCount += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				   
				   

					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTFeedbackType",new String[]{"2"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					feedbackSuccess += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTFeedbackType",new String[]{"1"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					unFeedbackCount += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					detachedCriteria.add(Restrictions.in("RPTFeedbackType",new String[]{"3"}));
					detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
					feedbackFalse += (Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				   
				}
				if(ReflectOperation.getFieldValue(object, "RPTCheckType").equals("1")){
					unRPTCheckTypeCount+=1;
				}else if(ReflectOperation.getFieldValue(object, "RPTCheckType").equals("2")){
					RPTCheckTypeSuccess+=1;
				}
				else if(ReflectOperation.getFieldValue(object, "RPTCheckType").equals("3")){
					RPTCheckTypeFalse+=1;
				}
				
				String SubRPTCheckType="";
				if(ReflectOperation.getFieldValue(object, "RPTCheckType").equals("4")){
					SubRPTCheckType = "1";
				}
				else{
					if(RPTCheckTypeSuccess==0 && RPTCheckTypeFalse==0 && unRPTCheckTypeCount>0){
						SubRPTCheckType = "1";
					}else if(RPTCheckTypeSuccess>0 && RPTCheckTypeFalse==0 && unRPTCheckTypeCount==0){
						SubRPTCheckType = "2";
					}
					else if(RPTCheckTypeSuccess==0 && RPTCheckTypeFalse>0 && unRPTCheckTypeCount==0 ){
						SubRPTCheckType = "3";
					}
					else{
						SubRPTCheckType = "4";
					}
				}
				ReflectOperation.setFieldValue(object, "RPTCheckType", SubRPTCheckType);
				
				
				if(RPTSendTypeMXSuccess > 0 && unRPTSendTypeMXCount==0){
						ReflectOperation.setFieldValue(object, "RPTSendType", "2");
				}
				else if(RPTSendTypeMXSuccess == 0 && unRPTSendTypeMXCount>0){
					ReflectOperation.setFieldValue(object, "RPTSendType", "1");
				}
				else if(RPTSendTypeMXSuccess == 0 && unRPTSendTypeMXCount==0){
					
				}else{
					ReflectOperation.setFieldValue(object, "RPTSendType", "5");
				}
				
				
				if(feedbackSuccess>0 && unFeedbackCount==0 && feedbackFalse==0){
				 		ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
			 	}else if(feedbackSuccess==0 && unFeedbackCount>0 && feedbackFalse==0){
			 		ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
			 	}
			 	else if(feedbackSuccess==0 && unFeedbackCount==0 && feedbackFalse>0){
			 		ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
			 	}
			 	else{
			 		ReflectOperation.setFieldValue(object, "RPTFeedbackType", "4");
			 	}
			}
		    
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{objectList,null});
		}
		
		int RPTSubmitStatusCount=0;
		int unRPTSubmitStatusCount=0;
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTSubmitStatus",new String[]{"2"}));
	    RPTSubmitStatusCount = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
	    detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTSubmitStatus",new String[]{"1"}));
		unRPTSubmitStatusCount = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	 
		if(RPTSubmitStatusCount > 0 && unRPTSubmitStatusCount>0){
			taskModelInst.setSubmitStatus("4");
		}
		else if(RPTSubmitStatusCount > 0 && unRPTSubmitStatusCount==0){
			taskModelInst.setSubmitStatus("2");
		}
		else if(RPTSubmitStatusCount == 0 && unRPTSubmitStatusCount>0){
			taskModelInst.setSubmitStatus("1");
		}
		else if(RPTSubmitStatusCount == 0 && unRPTSubmitStatusCount==0){
			taskModelInst.setSubmitStatus("1");
		}
		
		int RPTVerifyTypeSuccess=0;
		int RPTVerifyTypeFalse=0;
		int unRPTVerifyTypeCount=0;
		
	    detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTVerifyType",new String[]{"2"}));
		RPTVerifyTypeSuccess = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTVerifyType",new String[]{"1"}));
		unRPTVerifyTypeCount = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTVerifyType",new String[]{"3"}));
		RPTVerifyTypeFalse = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
		if(RPTVerifyTypeSuccess > 0 && unRPTVerifyTypeCount==0 && RPTVerifyTypeFalse==0){
			taskModelInst.setStrAllowState("2");
		}
		else if(RPTVerifyTypeSuccess == 0 && unRPTVerifyTypeCount>0 && RPTVerifyTypeFalse==0){
			taskModelInst.setStrAllowState("1");
		}
		else if(RPTVerifyTypeSuccess == 0 && unRPTVerifyTypeCount==0 && RPTVerifyTypeFalse>0){
			taskModelInst.setStrAllowState("3");
		}else{
			taskModelInst.setStrAllowState("4");
		}
		
		
		int RPTCheckTypeSuccess=0;
		int RPTCheckTypeFalse=0;
		int unRPTCheckTypeCount=0;
		
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTCheckType",new String[]{"2"}));
		RPTCheckTypeSuccess = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTCheckType",new String[]{"1"}));
		unRPTCheckTypeCount = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	   
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTCheckType",new String[]{"3"}));
		RPTCheckTypeFalse = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		if(RPTCheckTypeSuccess > 0 && unRPTCheckTypeCount==0 && RPTCheckTypeFalse==0){
			taskModelInst.setStrCheckState("2");
		}
		else if(RPTCheckTypeSuccess == 0 && unRPTCheckTypeCount>0 && RPTCheckTypeFalse==0){
			taskModelInst.setStrCheckState("1");
		}
		else if(RPTCheckTypeSuccess == 0 && unRPTCheckTypeCount==0 && RPTCheckTypeFalse>0){
			taskModelInst.setStrCheckState("3");
		}else{
			taskModelInst.setStrCheckState("4");
		}
		
		int sendedCount=0;
		int unSendCount=0;
		IParamObjectResultExecute singleObjectFindCountByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo",taskModelInst.getInstInfo()));
	 	detachedCriteria.add(Restrictions.eq("dtDate",taskModelInst.getTaskFlow().getDtTaskDate()));
	 	detachedCriteria.add(Restrictions.in("RPTSendType",new String[]{"2"}));
	 	sendedCount=(Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	 	
	 	detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo",taskModelInst.getInstInfo()));
	 	detachedCriteria.add(Restrictions.eq("dtDate",taskModelInst.getTaskFlow().getDtTaskDate()));
	 	detachedCriteria.add(Restrictions.in("RPTSendType",new String[]{"1"}));
	 	unSendCount=(Integer)singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	 	
	 	if(sendedCount>0 && unSendCount==0){
	 		taskModelInst.setRPTSendType("2");
	 	}else if(sendedCount==0 && unSendCount>0){
	 		taskModelInst.setRPTSendType("1");
	 	}
	 	else{
	 		taskModelInst.setRPTSendType("5");
	 	}
	 	
	 	int feedbackSuccess=0;
	 	int feedbackFalse=0;
		int unFeedbackCount=0;
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTFeedbackType",new String[]{"2"}));
		feedbackSuccess = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTFeedbackType",new String[]{"1"}));
		unFeedbackCount = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.in("RPTFeedbackType",new String[]{"3"}));
		feedbackFalse = (Integer)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	   
	 	if(feedbackSuccess>0 && unFeedbackCount==0 && feedbackFalse==0){
	 		taskModelInst.setRPTFeedbackType("2");
	 	}else if(feedbackSuccess==0 && unFeedbackCount>0 && feedbackFalse==0){
	 		taskModelInst.setRPTFeedbackType("1");
	 	}
	 	else if(feedbackSuccess==0 && unFeedbackCount==0 && feedbackFalse>0){
	 		taskModelInst.setRPTFeedbackType("3");
	 	}else{
	 		taskModelInst.setRPTFeedbackType("4");
	 	}
	 	
	 	IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{taskModelInst,null});
		
		return serviceResult;
	}

}
