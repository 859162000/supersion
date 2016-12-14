package autoETLsystem.service.procese;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.TaskFlow;
import report.dto.TaskModelInst;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.show.ShowContext;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;

/**
 * 统计任务层及接口表所有状态
 * 注：传入参数：instInfo，strTableName,校验基础段extend5=1的数据
 * 规则:参数与参数之间用逗号隔开，参数与参数值之间用分号隔开，多个参数值之间用&隔开
 * @author xiajieli
 */
public class AutoDTOCountFlowStatusProcess implements IActivityNodeForJavaExtend{

	@SuppressWarnings("unchecked")
	public String Execute(List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,String strFixParameter,String strSessionFactory) throws Exception {

		String[] params=null;
		if(strFixParameter.contains("\r")){
			strFixParameter=strFixParameter.replace("\r", "");
		}if(strFixParameter.contains("\n")){
			strFixParameter=strFixParameter.replace("\n", "");
		}
		if(strFixParameter.indexOf(",")>0){
			params=strFixParameter.split(",");
		}
		
		String instInfoTemp=params[0];
		if(instInfoTemp.length()>0){
			instInfoTemp=instInfoTemp.substring(9);
		}
		String strTableNameTemp=params[1];
		if(strTableNameTemp.length()>0){
			strTableNameTemp=strTableNameTemp.substring(13);
		}
		
		String[] instInfo=null;
		String[] strTableName=null;
		
		if(instInfoTemp.indexOf("&")>0){
			instInfo=instInfoTemp.split("&");
		}else if(!instInfoTemp.equals("")){
			instInfo=new String[1];
			instInfo[0]=instInfoTemp;
		}
		
		if(strTableNameTemp.indexOf("&")>0){
			strTableName=strTableNameTemp.split("&");
		}else if(!strTableNameTemp.equals("")){
			strTableName = new String[1];
			strTableName[0]=strTableNameTemp;
		}
	
		int checkSuccess=0;
		int checkfalse=0;
		int unCheck=0;
		
		int sendSuccess=0;
		int unSend=0;
		
		int unRPTFeedbackType=0;
		int rPTFeedbackTypeSuccess=0;
		int rPTFeedbackTypefalse=0;
		
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		if(strTableName==null){
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Table"));
			detachedCriteria.add(Restrictions.ilike("strTableName", "%_JC"));
			List<ReportModel_Table> reportModel_TableList = (List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
			strTableName=new String[reportModel_TableList.size()];
			for(int i=0;i<reportModel_TableList.size();i++){
				strTableName[i]=reportModel_TableList.get(i).getStrTableName();
			}
		}
		
		for (String tableName : strTableName) {
			String tableDTOName="AutoDTO_"+tableName;
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = null;
			
			//根据表名获取dto所在命名空间，得到需校验的基础段数据
			Map<String,String> GetNamespaceByEntityNameMap=ShowContext.getInstance().getShowEntityMap().get("GetNamespaceByEntityName");
			for (Entry<String, String> strTable : GetNamespaceByEntityNameMap.entrySet()) {
				if(tableName.contains(strTable.getKey())){
					detachedCriteria = DetachedCriteria.forClass(Class.forName(strTable.getValue() + tableName));
				}
			}

			if(instInfo!=null){
				detachedCriteria.add(Restrictions.in("instInfo.strInstCode", instInfo));
			}
			detachedCriteria.add(Restrictions.eq("extend5", "1"));
			List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});

			Set<String> succesMxKey = new HashSet<String>();
			
			//基础段状态
			List<Field> listObjectMx=null;
			
			for (Entry<String, String> strTable : GetNamespaceByEntityNameMap.entrySet()) {
				if(tableName.contains(strTable.getKey())){
					listObjectMx=ReflectOperation.getOneToManyField(strTable.getValue()+tableName);
				}
			}
			
			Map<String,Map<String,List<Object>>> tableDataMap = new HashMap<String,Map<String,List<Object>>>();
			for (Field field : listObjectMx) {
				DetachedCriteria allDetachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
				List<Object> allList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{allDetachedCriteria,strSessionFactory});
				Map<String,List<Object>> jxListMap = new HashMap<String,List<Object>>();
				for(Object object : allList){
					String mxForeignId = ReflectOperation.getPrimaryKeyValue(ReflectOperation.getFieldValue(object, "FOREIGNID")).toString();
					if(jxListMap.containsKey(mxForeignId)){
						List<Object> mxList = jxListMap.get(mxForeignId);
						mxList.add(object);
						jxListMap.put(mxForeignId, mxList);
					}
					else{
						List<Object> mxList = new ArrayList<Object>();
						mxList.add(object);
						jxListMap.put(mxForeignId, mxList);
					}
				}
				
				tableDataMap.put(ReflectOperation.getGenericClass(field.getGenericType()).getName(), jxListMap);
			}
			
			for (Object object_JC : objectList) {
				
				String primaryKey = ReflectOperation.getPrimaryKeyValue(object_JC).toString();
				if(succesMxKey.contains(primaryKey)){
					continue;
				}
				
				checkSuccess=0;
				checkfalse=0;
				unCheck=0;
				
				sendSuccess=0;
				unSend=0;
				
				unRPTFeedbackType=0;
				rPTFeedbackTypeSuccess=0;
				rPTFeedbackTypefalse=0;
				
				for (Field field : listObjectMx) {
					detachedCriteria = null;
					
					unCheck += findByTableDataForJC("RPTCheckType","1",object_JC,field.getGenericType(),tableDataMap);
					
					checkSuccess += findByTableDataForJC("RPTCheckType","2",object_JC,field.getGenericType(),tableDataMap);
					
					checkfalse += findByTableDataForJC("RPTCheckType","3",object_JC,field.getGenericType(),tableDataMap);
					
					unSend += findByTableDataForJC("RPTSendType","1",object_JC,field.getGenericType(),tableDataMap);
					
					sendSuccess += findByTableDataForJC("RPTSendType","2",object_JC,field.getGenericType(),tableDataMap);
					
					unRPTFeedbackType += findByTableDataForJC("RPTFeedbackType","1",object_JC,field.getGenericType(),tableDataMap);
					
					rPTFeedbackTypeSuccess += findByTableDataForJC("RPTFeedbackType","2",object_JC,field.getGenericType(),tableDataMap);
					
					rPTFeedbackTypefalse += findByTableDataForJC("RPTFeedbackType","3",object_JC,field.getGenericType(),tableDataMap);
					
				}
				if(listObjectMx.size()>0){
					if(ReflectOperation.getFieldValue(object_JC, "RPTCheckType").equals("2")){
						checkSuccess+=1;
					}
					else{
						checkfalse+=1; 
					}
					
					String SubRPTCheckType="";
					if(checkSuccess==0 && checkfalse==0 && unCheck>0){
						SubRPTCheckType = "1";
					}else if(checkSuccess>0 && checkfalse==0 && unCheck==0){
						SubRPTCheckType = "2";
					}
					else if(checkSuccess==0 && checkfalse>0 && unCheck==0 ){
						SubRPTCheckType = "3";
					}
					else{
						SubRPTCheckType = "4";
					}

					ReflectOperation.setFieldValue(object_JC, "RPTCheckType", SubRPTCheckType);
					
					String SubSendType="";
					if(sendSuccess==0 && unSend==0){
						SubSendType = ReflectOperation.getFieldValue(object_JC, "RPTSendType").toString();
					}
					else if(sendSuccess>0 && unSend==0){
						SubSendType = "2";
					}
					else if(sendSuccess==0 && unSend>0){
						SubSendType = "1";
					}
					else if(sendSuccess>0 && unSend>0){
						SubSendType = "5";
					}
					
					ReflectOperation.setFieldValue(object_JC, "RPTSendType", SubSendType);
					
					String SubRPTFeedbackType="";
					if(ReflectOperation.getFieldValue(object_JC, "RPTFeedbackType").equals("1")){
						unRPTFeedbackType+=1;
					}else if(ReflectOperation.getFieldValue(object_JC, "RPTFeedbackType").equals("2")){
						rPTFeedbackTypeSuccess+=1;
					}else if(ReflectOperation.getFieldValue(object_JC, "RPTFeedbackType").equals("3")){
						rPTFeedbackTypefalse+=1;
					}
				
					if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType>=0){
						SubRPTFeedbackType = "1";
					}else if(rPTFeedbackTypeSuccess>0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType==0){
						SubRPTFeedbackType = "2";
					}
					else if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse>0 && unRPTFeedbackType==0 ){
						SubRPTFeedbackType = "3";
					}
					else{
						SubRPTFeedbackType = "4";
					}
					
					ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType",SubRPTFeedbackType);
				}
				ReflectOperation.setFieldValue(object_JC, "extend5", "2");//更新基础段extend5值为‘2’；
				
			}
			singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,strSessionFactory});
			if(objectList.size()>0){
				//任务状态
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = null;
				
				detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
				detachedCriteria.add(Restrictions.eq("strTableName", tableName));
				List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
				
				detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			    List<TaskFlow>  taskFlowList = (List<TaskFlow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});

			    if(instInfo!=null){
			    	for (String inst : instInfo) {
			    		detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);
			    		detachedCriteria.add(Restrictions.eq("instInfo.strInstCode", inst));
			    		
			    		detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
						detachedCriteria.add(Restrictions.eq("reportModel_Table", reportModel_TableList.get(0)));
					    List<TaskModelInst>  taskModelInstList = (List<TaskModelInst>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
					    if(taskModelInstList.size()>0){
					    	TaskModelInst taskModelInst=taskModelInstList.get(0);
					    	
					    	 checkSuccess=0;
							 checkfalse=0;
							 unCheck=0;
							 int unAllCheckSuccess=0;
							 
							 sendSuccess=0;
							 unSend=0;
							 int unAllSendSuccess=0;
							 
							int unRPTSubmitStatus=0;
							int rPTSubmitStatusSuccess=0;
							
							int unRPTVerifyType=0;
							int rPTVerifyTypeSuccess=0;
							int rPTVerifyTypefalse=0;
							
							unRPTFeedbackType=0;
							rPTFeedbackTypeSuccess=0;
							rPTFeedbackTypefalse=0;
							int unAllRPTFeedbackTypeSuccess=0;
							 
							//筛选条件时，排除回执状态为5，已删除的数据
							unCheck = findByDetachedCriteria("RPTCheckType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							checkSuccess = findByDetachedCriteria("RPTCheckType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							checkfalse = findByDetachedCriteria("RPTCheckType", "3", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unAllCheckSuccess = findByDetachedCriteria("RPTCheckType", "4", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unSend = findByDetachedCriteria("RPTSendType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							sendSuccess = findByDetachedCriteria("RPTSendType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unAllSendSuccess = findByDetachedCriteria("RPTSendType", "5", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unRPTSubmitStatus = findByDetachedCriteria("RPTSubmitStatus", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							rPTSubmitStatusSuccess = findByDetachedCriteria("RPTSubmitStatus", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unRPTVerifyType = findByDetachedCriteria("RPTVerifyType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							rPTVerifyTypeSuccess = findByDetachedCriteria("RPTVerifyType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
						
							rPTVerifyTypefalse = findByDetachedCriteria("RPTVerifyType", "3", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unRPTFeedbackType = findByDetachedCriteria("RPTFeedbackType", "1", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							rPTFeedbackTypeSuccess = findByDetachedCriteria("RPTFeedbackType", "2", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							rPTFeedbackTypefalse = findByDetachedCriteria("RPTFeedbackType", "3", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);
							
							unAllRPTFeedbackTypeSuccess = findByDetachedCriteria("RPTFeedbackType", "4", detachedCriteria, tableDTOName, taskModelInst, singleObjectFindByCountDao,strSessionFactory,GetNamespaceByEntityNameMap);

							
							if(checkSuccess==0 && checkfalse==0 && unCheck>=0 && unAllCheckSuccess==0){
								taskModelInst.setStrCheckState("1");
							}else if(checkSuccess>0 && checkfalse==0 && unCheck==0 && unAllCheckSuccess==0){
								taskModelInst.setStrCheckState("2");
							}
							else if(checkSuccess==0 && checkfalse >0 && unCheck==0 && unAllCheckSuccess==0){
								taskModelInst.setStrCheckState("3");
							}else{
								taskModelInst.setStrCheckState("4");
							}
							
							if(sendSuccess==0 && unSend>=0 && unAllSendSuccess==0){
								taskModelInst.setRPTSendType("1");
							}else if(sendSuccess>0 && unSend==0 && unAllSendSuccess==0){
								taskModelInst.setRPTSendType("2");
							}
							else {
								taskModelInst.setRPTSendType("5");
							}
							
							if(unRPTSubmitStatus>=0 && rPTSubmitStatusSuccess==0){
								taskModelInst.setSubmitStatus("1");
							}else if(rPTSubmitStatusSuccess>0 && unRPTSubmitStatus==0){
								taskModelInst.setSubmitStatus("2");
							}
							else {
								taskModelInst.setSubmitStatus("4");
							}
							
							if(unRPTVerifyType>=0 && rPTVerifyTypeSuccess==0 && rPTVerifyTypefalse==0 ){
								taskModelInst.setStrAllowState("1");
							}else if(rPTVerifyTypeSuccess>0 && unRPTVerifyType==0 && rPTVerifyTypefalse==0){
								taskModelInst.setStrAllowState("2");
							}
							else if(rPTVerifyTypeSuccess==0 && unRPTVerifyType==0 && rPTVerifyTypefalse>0){
								taskModelInst.setStrAllowState("3");
							}
							else {
								taskModelInst.setStrAllowState("4");
							}
							
							if(unRPTFeedbackType>=0 && rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unAllRPTFeedbackTypeSuccess==0){
								taskModelInst.setRPTFeedbackType("1");
							}else if(rPTFeedbackTypeSuccess>0 && unRPTFeedbackType==0 && rPTFeedbackTypefalse==0 && unAllRPTFeedbackTypeSuccess==0){
								taskModelInst.setRPTFeedbackType("2");
							}
							else if(rPTFeedbackTypeSuccess==0 && unRPTFeedbackType==0 && rPTFeedbackTypefalse>0 && unAllRPTFeedbackTypeSuccess==0){
								taskModelInst.setRPTFeedbackType("3");
							}
							else {
								taskModelInst.setRPTFeedbackType("4");
							}

							IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
							singleObjectUpdateDao.paramVoidResultExecute(new Object[]{taskModelInst,strSessionFactory});
					    }
					}
			    }
			    
			}
		}
	
		return "成功";
	}
	

	/**
	 * 通过条件统计
	 * @param status 状态
	 * @param statusValue 状态值
	 * @param detachedCriteria 条件
	 * @param strTableName 表名
	 * @param taskModelInst 任务表对象
	 * @param singleObjectFindByCountDao 查找条数
	 * @return 查找到的条数
	 * @throws Exception
	 */
	public Integer findByDetachedCriteria(String status, String statusValue, DetachedCriteria detachedCriteria, String strTableName, 
			TaskModelInst taskModelInst, IParamObjectResultExecute singleObjectFindByCountDao,String strSessionFactory,Map<String,String> GetNamespaceByEntityNameMap) throws Exception{
		
		for (Entry<String, String> strTable : GetNamespaceByEntityNameMap.entrySet()) {
			if(strTableName.contains(strTable.getKey())){
				detachedCriteria = DetachedCriteria.forClass(Class.forName(strTable.getValue()+strTableName.substring(8)));
			}
		}
		
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		detachedCriteria.add(Restrictions.eq("dtDate", taskModelInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
		return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
		
	}

	/**
	 * 通过条件统计基础段状态
	 * @param status 状态
	 * @param statusValue 状态值
	 * @param detachedCriteria 条件
	 * @param object_Jc 基础段对象
	 * @param type 类型
	 * @param singleObjectFindByCountDao 统计条数
	 * @return 统计的条数
	 * @throws Exception
	 */
    public Integer findByDetachedCriteriaForJC(String status, String statusValue, DetachedCriteria detachedCriteria, Object object_Jc,Type type,IParamObjectResultExecute singleObjectFindByCountDao,String strSessionFactory) throws Exception{
	    detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(type));
		detachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
		detachedCriteria.add(Restrictions.eq(status, statusValue));
		return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,strSessionFactory});
    }
    
    public Integer findByTableDataForJC(String status, String statusValue,Object object_Jc,Type type,Map<String,Map<String,List<Object>>> tableDataMap) throws Exception{
	    String genericType = ReflectOperation.getGenericClass(type).getName();
	    Map<String,List<Object>> tableMap = tableDataMap.get(genericType);
	    String jc_Id = ReflectOperation.getPrimaryKeyValue(object_Jc).toString();
	    List<Object> objectList = tableMap.get(jc_Id);
	    int count  = 0;
	    if(objectList != null){
	    	 for(Object object : objectList){
	    		 Object objectStata = ReflectOperation.getFieldValue(object, status);
 	    		 if(objectStata != null){
 	    			if(objectStata.toString().equals(statusValue)){
 		    			count++;
 		    		}
 	    		}
	 	    }
	    }

    	return count;
    }

	public String Execute(
			List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,String strFixParameter) throws Exception {
		return Execute(activeNodeETLWorkflowParamMVList,strFixParameter,null);
	}
    
}
