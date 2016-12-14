package autoETLsystem.service.copyService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.OneToMany;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.context.ContextLoader;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import extend.dto.AutoETL_DataSource;
import extend.dto.AutoETL_Param;
import extend.dto.AutoETL_Procedure;
import extend.dto.AutoETL_View;
import extend.dto.AutoETL_ViewField;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.MessageResult;
import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldV;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVC;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVCa;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVCo;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVVCa;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVVCon;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldViCa;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldViewV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCT;
import autoETLsystem.dto.AutoETL_ActivityNodeForCV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCalculate;
import autoETLsystem.dto.AutoETL_ActivityNodeForCheck;
import autoETLsystem.dto.AutoETL_ActivityNodeForDTS;
import autoETLsystem.dto.AutoETL_ActivityNodeForExcel;
import autoETLsystem.dto.AutoETL_ActivityNodeForExcelC;
import autoETLsystem.dto.AutoETL_ActivityNodeForFTPTra;
import autoETLsystem.dto.AutoETL_ActivityNodeForFile;
import autoETLsystem.dto.AutoETL_ActivityNodeForFileC;
import autoETLsystem.dto.AutoETL_ActivityNodeForFileF;
import autoETLsystem.dto.AutoETL_ActivityNodeForJava;
import autoETLsystem.dto.AutoETL_ActivityNodeForP;
import autoETLsystem.dto.AutoETL_ActivityNodeForPro;
import autoETLsystem.dto.AutoETL_ActivityNodeForSql;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlC;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlCR;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlEx;
import autoETLsystem.dto.AutoETL_ActivityNodeForSummary;
import autoETLsystem.dto.AutoETL_ActivityNodeForView;
import autoETLsystem.dto.AutoETL_ActivityNodeLog;
import autoETLsystem.dto.AutoETL_ActivityNodeParam;
import autoETLsystem.dto.AutoETL_ActivityNodeProcP;
import autoETLsystem.dto.AutoETL_ActivityNodeRelationF;
import autoETLsystem.dto.AutoETL_ActivityNodeRelationV;
import autoETLsystem.dto.AutoETL_FTP;
import autoETLsystem.dto.AutoETL_FTPTrans;
import autoETLsystem.dto.AutoETL_Workflow;
import autoETLsystem.dto.AutoETL_WorkflowAE;
import autoETLsystem.dto.AutoETL_WorkflowLog;
import autoETLsystem.dto.AutoETL_WorkflowPFPV;
import autoETLsystem.dto.AutoETL_WorkflowPFPVDetail;
import autoETLsystem.dto.AutoETL_WorkflowPP;
import autoETLsystem.dto.AutoETL_WorkflowPSDV;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.dto.AutoETL_WorkflowSQLParam;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;

public class SingleObjectCopyAboutAutoETLSeivice implements IActivityNodeForJavaExtend{

	private String defaultFromSessionFactoryIdorName;
	private String defaultToSessionFactoryIdorName;
	private List<String>  defaultCopyWorkflowNameList;
	
	Map<String,Object> mapAutoETL_DataSource=new LinkedHashMap<String,Object>();
	Map<String,Object> mapReportModel_Table=new LinkedHashMap<String,Object>();
	Map<String,Object> mapReportModel_Field=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_View=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_ViewField=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_Procedure=new LinkedHashMap<String,Object>();
	
	Map<String,Object> mapAutoETL_DataSource_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapReportModel_Table_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapReportModel_Field_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_View_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_ViewField_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_Procedure_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_Workflow_Target=new LinkedHashMap<String,Object>();
	Map<String,Object> mapAutoETL_ActivityNode_Target=new LinkedHashMap<String,Object>();

	Map<Object, Set<String>> mapTableIntOrder=new LinkedHashMap<Object,Set<String>>();
	
	Map<String,List<Object>> value=new LinkedHashMap<String,List<Object>>();
	Map<String,List<Object>> valueObject=new LinkedHashMap<String,List<Object>>();
	
	
	
	public String getDefaultFromSessionFactoryIdorName() {
		return defaultFromSessionFactoryIdorName;
	}

	public void setDefaultFromSessionFactoryIdorName(String defaultFromSessionFactoryIdorName) {
		this.defaultFromSessionFactoryIdorName = defaultFromSessionFactoryIdorName;
	}

	public String getDefaultToSessionFactoryIdorName() {
		return defaultToSessionFactoryIdorName;
	}

	public void setDefaultToSessionFactoryIdorName(String defaultToSessionFactoryIdorName) {
		this.defaultToSessionFactoryIdorName = defaultToSessionFactoryIdorName;
	}


	public List<String> getDefaultCopyWorkflowNameList() {
		return defaultCopyWorkflowNameList;
	}

	public void setDefaultCopyWorkflowNameList(List<String> defaultCopyWorkflowNameList) {
		this.defaultCopyWorkflowNameList = defaultCopyWorkflowNameList;
	}



	@SuppressWarnings("unchecked")
	public String Execute(List<AutoETL_WorkflowParamMV> activeNodeETLWorkflowParamMVList,String strFixParameter)throws Exception {
		
		//1.处理数据源不能为空
		if(!(defaultFromSessionFactoryIdorName!=null && !StringUtils.isBlank(defaultFromSessionFactoryIdorName))){
			return "源数据源不能为空";
		}
		if(!(defaultToSessionFactoryIdorName!=null && !StringUtils.isBlank(defaultToSessionFactoryIdorName))){
			return "目标数据源不能为空";
		}
		mapAutoETL_DataSource.clear();
		mapReportModel_Table.clear();
		mapReportModel_Field.clear();
		mapAutoETL_View.clear();
		mapAutoETL_ViewField.clear();
		mapAutoETL_Procedure.clear();
		
		mapAutoETL_DataSource_Target.clear();
		mapReportModel_Table_Target.clear();
		mapReportModel_Field_Target.clear();
		mapAutoETL_View_Target.clear();
		mapAutoETL_ViewField_Target.clear();
		mapAutoETL_Procedure_Target.clear();
		mapAutoETL_Workflow_Target.clear();
		mapAutoETL_ActivityNode_Target.clear();
		mapTableIntOrder.clear();
		value.clear();
		valueObject.clear();

		//2.判断是XML否存在对应的数据源的sessionFactory
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_DataSource autoETL_DataSource = (AutoETL_DataSource)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_DataSource.class.getName(),defaultFromSessionFactoryIdorName,null});
		if(autoETL_DataSource !=null && !StringUtils.isBlank(autoETL_DataSource.getSessionFactory())){
			defaultFromSessionFactoryIdorName=autoETL_DataSource.getSessionFactory();
		}
		autoETL_DataSource = (AutoETL_DataSource)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_DataSource.class.getName(),defaultFromSessionFactoryIdorName,null});
		if(autoETL_DataSource!=null && !StringUtils.isBlank(autoETL_DataSource.getSessionFactory())){
			defaultToSessionFactoryIdorName=autoETL_DataSource.getSessionFactory();
		}
		Object fromSessionFactory =ContextLoader.getCurrentWebApplicationContext().getBean(defaultFromSessionFactoryIdorName);
		Object toSessionFactory=ContextLoader.getCurrentWebApplicationContext().getBean(defaultToSessionFactoryIdorName);
		if(fromSessionFactory ==null){
			throw new Exception("未找到相应的源数据源");
		}
		if(toSessionFactory ==null){
			throw new Exception("未找到相应的目标数据源"); 
		}
		//3.获取拷贝的工作流的名称是否为空
		if(!(defaultCopyWorkflowNameList !=null && defaultCopyWorkflowNameList.size()>=1)){
			return null;
		}
		//4.设置删除和插入的顺序
		SetIntOrder();
		
        //5.查询出目标数据库中的所有的数据源、表名、表字段、试图、试图字段、存储过程
		MessageResult mes=SetTargetMapping();
		if(!mes.isSuccess()){
			throw new Exception(mes.getMessage());
		}
		
		
		//6.依据对应的关系设置相应的
		List<Field> fieldList=getOneToManyField(AutoETL_Workflow.class);
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		for(String workflowName:defaultCopyWorkflowNameList){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_Workflow.class);
			detachedCriteria.add(Restrictions.eq("strWorkflowName", workflowName));
			List<AutoETL_Workflow> autoETL_WorkflowList = (List<AutoETL_Workflow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultFromSessionFactoryIdorName});
			for(AutoETL_Workflow autoETL_Workflow: autoETL_WorkflowList){
				if(value.containsKey(AutoETL_Workflow.class.getName()) && !value.get(AutoETL_Workflow.class.getName()).contains(autoETL_Workflow)){
					value.get(AutoETL_Workflow.class.getName()).add(autoETL_Workflow);
				}
				else{
					List<Object> object=new ArrayList<Object>();
					object.add(autoETL_Workflow);
					value.put(AutoETL_Workflow.class.getName(), object);
				}
				mes=getTempValueSize(fieldList,autoETL_Workflow,singleObjectFindByCriteriaDao,detachedCriteria);
				if(!mes.isSuccess()){
					throw new Exception(mes.getMessage());
				}
			}
		}
		for(String workflowName:defaultCopyWorkflowNameList){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_Workflow.class);
			detachedCriteria.add(Restrictions.eq("strWorkflowName", workflowName));
			List<AutoETL_Workflow> autoETL_WorkflowList = (List<AutoETL_Workflow>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultToSessionFactoryIdorName});
			for(AutoETL_Workflow autoETL_Workflow: autoETL_WorkflowList){
				getTargetValueSize(fieldList,autoETL_Workflow,singleObjectFindByCriteriaDao,detachedCriteria);
			}
		}
		SpecialTable(singleObjectFindByCriteriaDao);
		//7.设置目标数据库中AutoETL_Workflow和AutoETL_ActivityNode的值
		SetWorkflowAndActivityNode();
		deleteTargetValueSize(singleObjectFindByCriteriaDao);
		//8.设置关于AutoETL_Workflow和AutoETL_ActivityNode的替换
		for(int i=1;i<=mapTableIntOrder.size();i++){
			for(String className: mapTableIntOrder.get(i)){
				SetMappingWorkflowAndActivityNode(className);
			}
		}
		
		
		//9.删除目标数据库对应的数据
		IParamVoidResultExecute singleObjectDeleteListByIdListDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteListByIdListDao");
		for(int i=mapTableIntOrder.size();i>0;i--){
			for(String className : mapTableIntOrder.get(i)){
				List<Object> objectList=new ArrayList<Object>();
				if(!className.equals(AutoETL_Workflow.class.getName()) && !className.equals(AutoETL_Param.class.getName())
						&& !className.equals(AutoETL_FTP.class.getName()) && !className.equals(AutoETL_FTPTrans.class.getName())
						&& !className.equals(AutoETL_ActivityNode.class.getName())){
					if(valueObject.containsKey(className)){
						Field fieldPK=ReflectOperation.getPrimaryKeyField(Class.forName(className));
						for(Object object : valueObject.get(className)){
							Object objectValue=ReflectOperation.getFieldValue(object, fieldPK.getName());
							if(!objectList.contains(objectValue)){
								objectList.add(objectValue);
							}
						}
						Object[] objectListx=new Object[objectList.size()];
						for(int ix=0;ix<objectList.size();ix++){
							objectListx[ix]=objectList.get(ix);
						}
						singleObjectDeleteListByIdListDao.paramVoidResultExecute(new Object[] {className,objectListx, defaultToSessionFactoryIdorName});
					}
					
					
				}
			}
		}
		
		
		//10.插入数据
		IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		//IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		for(Map.Entry<Object, Set<String>> map : mapTableIntOrder.entrySet()){
			for(String className : map.getValue()){
				if(!className.equals(AutoETL_Workflow.class.getName()) && !className.equals(AutoETL_Param.class.getName())
						&& !className.equals(AutoETL_FTP.class.getName()) && !className.equals(AutoETL_FTPTrans.class.getName())
						&& !className.equals(AutoETL_ActivityNode.class.getName())){
					if(value.containsKey(className)){
						List<Object> objectList=new ArrayList<Object>();
						for(Object object : value.get(className)){
							Object objectDestination = object.getClass().newInstance();
							ReflectOperation.CopyFieldValue(object, objectDestination);
							if(!objectList.contains(objectDestination)){
								objectList.add(objectDestination);
							}			
						}
						singleObjectSaveAllDao.paramVoidResultExecute(new Object[] {objectList, defaultToSessionFactoryIdorName});
						//singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {objectList, defaultToSessionFactoryIdorName});
					}
				}
				else{
					Field field=ReflectOperation.getKeyNameField(Class.forName(className));
					Field fieldPK=ReflectOperation.getPrimaryKeyField(Class.forName(className));
					List<Object> UpdateList=new ArrayList<Object>();
					List<Object> SaveList=new ArrayList<Object>();
					if(value.containsKey(className)){
						for(Object object : value.get(className)){
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(className));
							Object objectValue=ReflectOperation.getFieldValue(object, field.getName());
							detachedCriteria.add(Restrictions.eq(field.getName(), objectValue));
							List<Object> ObjectList=(List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultToSessionFactoryIdorName});
							if(ObjectList.size()>0){
								Object objectDestination = object.getClass().newInstance();
								ReflectOperation.CopyFieldValue(ObjectList.get(0), objectDestination);
								
								ReflectOperation.CopyColumnFieldValue(object,ObjectList.get(0));
								ReflectOperation.setFieldValue(ObjectList.get(0), fieldPK.getName(),ReflectOperation.getFieldValue(objectDestination, ReflectOperation.getPrimaryKeyField(object.getClass()).getName()));
								if(!UpdateList.contains(ObjectList.get(0))){
									UpdateList.add(ObjectList.get(0));
								}
							}
							else{
								Object objectDestination = object.getClass().newInstance();
								ReflectOperation.CopyFieldValue(object, objectDestination);
								if(!SaveList.contains(objectDestination)){
									SaveList.add(objectDestination);
								}			
							}
						}
						if(UpdateList.size()>0){
							for(Object object:UpdateList){
								singleObjectUpdateDao.paramVoidResultExecute(new Object[] {object, defaultToSessionFactoryIdorName});
							}
							//singleObjectUpdateListDao.paramVoidResultExecute(new Object[] {UpdateList, defaultToSessionFactoryIdorName});
						}
						if(SaveList.size()>0){
						
							singleObjectSaveAllDao.paramVoidResultExecute(new Object[] {SaveList, defaultToSessionFactoryIdorName});
							//singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {SaveList, defaultToSessionFactoryIdorName});
						}
					}
				}
				
			}
		}
		return "";
	}
	
	
	 private List<Field> getOneToManyField(Class<?> type){
		 List<Field> oneToManyField = new ArrayList<Field>();
		
		Field[] fieldList = ReflectOperation.getReflectFields(type);
		for(int i=0;i<fieldList.length;i++){
			if(fieldList[i].isAnnotationPresent(OneToMany.class)){
				oneToManyField.add(fieldList[i]);
			}
		}
		
		return oneToManyField;
	}
	
	 @SuppressWarnings("unchecked")
	 private MessageResult getTempValueSize(List<Field> fieldList,Object Value,IParamObjectResultExecute singleObjectFindByCriteriaDao,DetachedCriteria detachedCriteria) throws Exception{
		 for(Field field : fieldList){
				Object ox =((Class)((ParameterizedTypeImpl)field.getGenericType()).getActualTypeArguments()[0]).getName();
				detachedCriteria = DetachedCriteria.forClass(Class.forName(ox.toString()));
				OneToMany oneToMany=field.getAnnotation(OneToMany.class);
				detachedCriteria.add(Restrictions.eq(oneToMany.mappedBy(), Value));
				List<Object> objectList =(List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultFromSessionFactoryIdorName});
				List<Field> fieldListx=getOneToManyField(Class.forName(ox.toString()));
				for(Object object: objectList){
					MessageResult br=setMapping(object);
					if(!br.isSuccess()){
						return br;
					}
					getTempValueSize(fieldListx,object,singleObjectFindByCriteriaDao,detachedCriteria);
				}
				
			}
		 return new MessageResult();
	 }
	 
	 private MessageResult setMapping(Object ox) throws Exception{
			List<Field> fieldList = ReflectOperation.getJoinColumnFieldList(ox.getClass());
			for(Field field : fieldList){
				String Name="";
				if(field.getType().equals(AutoETL_DataSource.class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(ox,field.getName());
					if(tOjectFieldValue !=null){
						Name=((AutoETL_DataSource)tOjectFieldValue).getSessionFactory();
						if(!mapAutoETL_DataSource_Target.containsKey(Name)){
							return new MessageResult(false,"目标数据库中不存在"+Name+"会话工厂");
						}
						else{
							ReflectOperation.setFieldValue(ox, field.getName(), mapAutoETL_DataSource_Target.get(Name));
						}
					}
				}
				else if(field.getType().equals(AutoETL_View.class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(ox,field.getName());
					if(tOjectFieldValue !=null){
						Name=((AutoETL_View)tOjectFieldValue).getDataSource().getSessionFactory()+"|"+((AutoETL_View)tOjectFieldValue).getStrChinaName();
						if(mapAutoETL_View_Target.containsKey(Name)){
							new MessageResult(false,"目标数据库中"+Name.split("|")[0]+"会话工厂不存在试图"+Name.split("|")[1]);
						}
						else {
							ReflectOperation.setFieldValue(ox, field.getName(), mapAutoETL_View_Target.get(Name));
						}
					}
				}
				else if(field.getType().equals(AutoETL_ViewField.class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(ox,field.getName());
					if(tOjectFieldValue !=null){
						Name=((AutoETL_ViewField)tOjectFieldValue).getAutoETL_View().getDataSource().getSessionFactory()+"|"+((AutoETL_ViewField)tOjectFieldValue).getAutoETL_View().getStrTableName()+"|"+((AutoETL_ViewField)tOjectFieldValue).getStrFieldName();
						if(!mapAutoETL_ViewField_Target.containsKey(Name) ){
							new MessageResult(false,"目标数据库中"+Name.split("|")[0]+"会话工厂下试图"+Name.split("|")[1]+"的不存在字段"+Name.split("|")[2]);
						}
						else{
							ReflectOperation.setFieldValue(ox, field.getName(), mapAutoETL_ViewField_Target.get(Name));
						}
						
					}
				}
				else if(field.getType().equals(AutoETL_Procedure.class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(ox,field.getName());
					if(tOjectFieldValue !=null){
						Name=((AutoETL_Procedure)tOjectFieldValue).getAutoETL_DataSource().getSessionFactory()+"|"+((AutoETL_Procedure)tOjectFieldValue).getStrProcedureName();
						if(!mapAutoETL_Procedure_Target.containsKey(Name)){
							new MessageResult(false,"目标数据库中"+Name.split("|")[0]+"会话工厂不存在存储过程"+Name.split("|")[1]);
						}
						else{
							ReflectOperation.setFieldValue(ox, field.getName(), mapAutoETL_Procedure_Target.get(Name));
						}
					}
				}
				else if(field.getType().equals(ReportModel_Table.class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(ox,field.getName());
					if(tOjectFieldValue !=null){
						Name=((ReportModel_Table)tOjectFieldValue).getDataSource().getSessionFactory()+"|"+((ReportModel_Table)tOjectFieldValue).getStrChinaName();
						if(!mapReportModel_Table_Target.containsKey(Name)){
							new MessageResult(false,"目标数据库中"+Name.split("|")[0]+"会话工厂不存在表"+Name.split("|")[1]);
						}
						else{
							ReflectOperation.setFieldValue(ox, field.getName(), mapReportModel_Table_Target.get(Name));
						}
					}
				}
				else if(field.getType().equals(ReportModel_Field.class)){
					Object tOjectFieldValue = ReflectOperation.getFieldValue(ox,field.getName());
					if(tOjectFieldValue !=null){
						Name=((ReportModel_Field)tOjectFieldValue).getReportModel_Table().getDataSource().getSessionFactory()+"|"+((ReportModel_Field)tOjectFieldValue).getReportModel_Table().getStrChinaName()+"|"+((ReportModel_Field)tOjectFieldValue).getStrFieldName();
						if(!mapReportModel_Field_Target.containsKey(Name)){
							new MessageResult(false,"目标数据库中"+Name.split("|")[0]+"会话工厂下表"+Name.split("|")[1]+"的不存在字段"+Name.split("|")[2]);
						}
						else{
							ReflectOperation.setFieldValue(ox, field.getName(), mapReportModel_Field_Target.get(Name));
						}
					}
				}
			}
			if(value.containsKey(ox.getClass().getName()) && !value.get(ox.getClass().getName()).contains(ox)){
				value.get(ox.getClass().getName()).add(ox);
			}
			else{
				List<Object> objectx=new ArrayList<Object>();
				objectx.add(ox);
				value.put(ox.getClass().getName(), objectx);
			}
			return new MessageResult();
	 }
	 
	 private void SetIntOrder(){
		 //1.设置最先插入的
		 Set<String> firstInsert=new LinkedHashSet<String>();
		 Set<String> sencondInsert=new LinkedHashSet<String>();
		 Set<String> thirdInsert=new LinkedHashSet<String>();
		 Set<String> forthInsert=new LinkedHashSet<String>();
		 Set<String> fifthInsert=new LinkedHashSet<String>();
		 Set<String> sixthInsert=new LinkedHashSet<String>();
		 ////初次插入
		 firstInsert.add(AutoETL_Workflow.class.getName());
		 firstInsert.add(AutoETL_Param.class.getName());
		 firstInsert.add(AutoETL_FTP.class.getName());
		 /////AutoETL_Workflow
		 sencondInsert.add(AutoETL_ActivityNode.class.getName());
		 sencondInsert.add(AutoETL_WorkflowParamMV.class.getName());
		 sencondInsert.add(AutoETL_WorkflowPFPV.class.getName());
		 sencondInsert.add(AutoETL_WorkflowPP.class.getName());
		 sencondInsert.add(AutoETL_WorkflowPSDV.class.getName());
		 sencondInsert.add(AutoETL_WorkflowSQLParam.class.getName());
		 sencondInsert.add(AutoETL_FTPTrans.class.getName());
		 
		 ////AutoETL_ActivityNode
		 thirdInsert.add(AutoETL_ActivityNodeProcP.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForCT.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForCV.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForDTS.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForFile.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForFTPTra.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForJava.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForPro.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForP.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeParam.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForSqlEx.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForSql.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForExcel.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForView.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForCheck.class.getName());
		 thirdInsert.add(AutoETL_WorkflowAE.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForCalculate.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeForSummary.class.getName());
		 thirdInsert.add(AutoETL_ActivityNodeFieldVC.class.getName());
		 ////AutoETL_WorkflowPFPV
		 thirdInsert.add(AutoETL_WorkflowPFPVDetail.class.getName());
		 ////AutoETL_ActivityNodeForCT
		 forthInsert.add(AutoETL_ActivityNodeRelationF.class.getName());
		 forthInsert.add(AutoETL_ActivityNodeFieldV.class.getName());
		 /////AutoETL_ActivityNodeForCV
		 forthInsert.add(AutoETL_ActivityNodeRelationV.class.getName());
		 forthInsert.add(AutoETL_ActivityNodeFieldViewV.class.getName());
		 ////AutoETL_ActivityNodeForFile
		 forthInsert.add(AutoETL_ActivityNodeForFileC.class.getName());
		 forthInsert.add(AutoETL_ActivityNodeForFileF.class.getName());
		 ////AutoETL_ActivityNodeForSql
		 forthInsert.add(AutoETL_ActivityNodeForSqlC.class.getName());
		 ////AutoETL_ActivityNodeForExcel
		 forthInsert.add(AutoETL_ActivityNodeForExcelC.class.getName());
		 ////AutoETL_ActivityNodeFieldV
		 fifthInsert.add(AutoETL_ActivityNodeFieldVCa.class.getName());
		 ////AutoETL_ActivityNodeFieldViewV
		 fifthInsert.add(AutoETL_ActivityNodeFieldViCa.class.getName());
		 fifthInsert.add(AutoETL_ActivityNodeFieldVVCa.class.getName());
		 ////AutoETL_ActivityNodeForSqlC
		 fifthInsert.add(AutoETL_ActivityNodeForSqlCR.class.getName());
		 ////AutoETL_ActivityNodeFieldVCa
		 sixthInsert.add(AutoETL_ActivityNodeFieldVCo.class.getName());
		 ////AutoETL_ActivityNodeFieldVVCa
		 sixthInsert.add(AutoETL_ActivityNodeFieldVVCon.class.getName());
		 mapTableIntOrder.put(1, firstInsert);
		 mapTableIntOrder.put(2, sencondInsert);
		 mapTableIntOrder.put(3, thirdInsert);
		 mapTableIntOrder.put(4, forthInsert);
		 mapTableIntOrder.put(5, fifthInsert);
		 mapTableIntOrder.put(6, sixthInsert);
		 
	 }
  
	 @SuppressWarnings("unchecked")
	 private MessageResult SetTargetMapping() throws Exception{
		 IParamObjectResultExecute singleObjectFindByAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		 
		 List<AutoETL_DataSource> autoETL_DataSourceList=(List<AutoETL_DataSource>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_DataSource.class.getName(),defaultFromSessionFactoryIdorName});
		 for(AutoETL_DataSource autoETL_DataSource : autoETL_DataSourceList){
			 String Key=autoETL_DataSource.getSessionFactory();
			 if(mapAutoETL_DataSource.containsKey(Key) && !mapAutoETL_DataSource_Target.containsValue(autoETL_DataSource)){
				 return new MessageResult(false,"原数据库中存在相同的回话工厂");
			 }
			 else if(!mapAutoETL_DataSource.containsKey(Key)){
				 mapAutoETL_DataSource.put(Key, autoETL_DataSource);
			 }
		 }
		 List<ReportModel_Table> reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{ReportModel_Table.class.getName(),defaultFromSessionFactoryIdorName});
		 for(ReportModel_Table reportModel_Table : reportModel_TableList){
			 String Key=reportModel_Table.getDataSource().getSessionFactory()+"|"+reportModel_Table.getStrChinaName();
			 if(mapReportModel_Table.containsKey(Key) && !mapReportModel_Table.containsValue(reportModel_Table)){
				 return new MessageResult(false,"原数据库中同一回话工厂下存在相同的数据表");
			 }
			 else if(!mapReportModel_Table.containsKey(Key)){
				 mapReportModel_Table.put(Key, reportModel_Table);
			 }
		 }
		 List<ReportModel_Field> reportModel_FieldList=(List<ReportModel_Field>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{ReportModel_Field.class.getName(),defaultFromSessionFactoryIdorName});
		 for(ReportModel_Field reportModel_Field : reportModel_FieldList){
			 String Key=reportModel_Field.getReportModel_Table().getDataSource().getSessionFactory()+"|"+reportModel_Field.getReportModel_Table().getStrChinaName()+"|"+reportModel_Field.getStrFieldName();
			 if(mapReportModel_Field.containsKey(Key) && !mapReportModel_Field.containsValue(reportModel_Field)){
				 return new MessageResult(false,"原数据库中同一数据表下存在相同的数据字段");
			 }
			 else if(!mapReportModel_Field.containsKey(Key)){
				 mapReportModel_Field.put(Key, reportModel_Field);
			 }
		 }
		 List<AutoETL_View> autoETL_ViewList=(List<AutoETL_View>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_View.class.getName(),defaultFromSessionFactoryIdorName});
		 for(AutoETL_View autoETL_View : autoETL_ViewList){
			 String Key=autoETL_View.getDataSource().getSessionFactory()+"|"+autoETL_View.getStrChinaName();
			 if(mapAutoETL_View.containsKey(Key) && !mapAutoETL_View.containsValue(autoETL_View)){
				 return new MessageResult(false,"原数据库中同一回话工厂下存在相同的试图");
			 }
			 else if(!mapAutoETL_View.containsKey(Key)){
				 mapAutoETL_View.put(Key, autoETL_View);
			 }
		 }
		 List<AutoETL_ViewField> autoETL_ViewFieldList=(List<AutoETL_ViewField>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_ViewField.class.getName(),defaultFromSessionFactoryIdorName});
		 for(AutoETL_ViewField autoETL_ViewField : autoETL_ViewFieldList){
			 String Key=autoETL_ViewField.getAutoETL_View().getDataSource().getSessionFactory()+"|"+autoETL_ViewField.getAutoETL_View().getStrChinaName()+"|"+autoETL_ViewField.getStrChinaName();
			 if(mapAutoETL_ViewField.containsKey(Key) && !mapAutoETL_ViewField.containsValue(autoETL_ViewField)){
				 return new MessageResult(false,"原数据库中同一试图下存在相同的试图字段");
			 }
			 else if(!mapAutoETL_ViewField.containsKey(Key)){
				 mapAutoETL_ViewField.put(Key, autoETL_ViewField);
			 }
		 }
		 List<AutoETL_Procedure> autoETL_ProcedureList=(List<AutoETL_Procedure>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_Procedure.class.getName(),defaultFromSessionFactoryIdorName});
		 for(AutoETL_Procedure autoETL_Procedure : autoETL_ProcedureList){
			 String Key=autoETL_Procedure.getAutoETL_DataSource().getSessionFactory()+"|"+autoETL_Procedure.getStrProcedureName();
			 if(mapAutoETL_Procedure.containsKey(Key) && !mapAutoETL_Procedure.containsValue(autoETL_Procedure)){
				 return new MessageResult(false,"原数据库中同一回话工厂下存在相同的存储过程");
			 }
			 else if(!mapAutoETL_Procedure.containsKey(Key)){
				 mapAutoETL_Procedure.put(Key, autoETL_Procedure);
			 }
		 }
		 
		 autoETL_DataSourceList=(List<AutoETL_DataSource>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_DataSource.class.getName(),defaultToSessionFactoryIdorName});
		 for(AutoETL_DataSource autoETL_DataSource : autoETL_DataSourceList){
			 String Key=autoETL_DataSource.getSessionFactory();
			 if(mapAutoETL_DataSource_Target.containsKey(Key) && !mapAutoETL_DataSource_Target.containsValue(autoETL_DataSource)){
				 return new MessageResult(false,"目标数据库中存在相同的回话工厂");
			 }
			 else if(!mapAutoETL_DataSource_Target.containsKey(Key)){
				 mapAutoETL_DataSource_Target.put(Key, autoETL_DataSource);
			 }
		 }
		 reportModel_TableList=(List<ReportModel_Table>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{ReportModel_Table.class.getName(),defaultToSessionFactoryIdorName});
		 for(ReportModel_Table reportModel_Table : reportModel_TableList){
			 String Key=reportModel_Table.getDataSource().getSessionFactory()+"|"+reportModel_Table.getStrChinaName();
			 if(mapReportModel_Table_Target.containsKey(Key) && !mapReportModel_Table_Target.containsValue(reportModel_Table)){
				 return new MessageResult(false,"目标数据库中同一回话工厂下存在相同的数据表");
			 }
			 else if(!mapReportModel_Table_Target.containsKey(Key)){
				 mapReportModel_Table_Target.put(Key, reportModel_Table);
			 }
		 }
		 reportModel_FieldList=(List<ReportModel_Field>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{ReportModel_Field.class.getName(),defaultToSessionFactoryIdorName});
		 for(ReportModel_Field reportModel_Field : reportModel_FieldList){
			 String Key=reportModel_Field.getReportModel_Table().getDataSource().getSessionFactory()+"|"+reportModel_Field.getReportModel_Table().getStrChinaName()+"|"+reportModel_Field.getStrFieldName();
			 if(mapReportModel_Field_Target.containsKey(Key) && !mapReportModel_Field_Target.containsValue(reportModel_Field)){
				 return new MessageResult(false,"目标数据库中同一数据表下存在相同的数据字段");
			 }
			 else if(!mapReportModel_Field_Target.containsKey(Key)){
				 mapReportModel_Field_Target.put(Key, reportModel_Field);
			 }
		 }
		 
		 
		 autoETL_ViewList=(List<AutoETL_View>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_View.class.getName(),defaultToSessionFactoryIdorName});
		 for(AutoETL_View autoETL_View : autoETL_ViewList){
			 String Key=autoETL_View.getDataSource().getSessionFactory()+"|"+autoETL_View.getStrChinaName();
			 if(mapAutoETL_View_Target.containsKey(Key) && !mapAutoETL_View_Target.containsValue(autoETL_View)){
				 return new MessageResult(false,"目标数据库中同一回话工厂下存在相同的试图");
			 }
			 else if(!mapAutoETL_View_Target.containsKey(Key)){
				 mapAutoETL_View_Target.put(Key, autoETL_View);
			 }
		 }
		
		 
		 autoETL_ViewFieldList=(List<AutoETL_ViewField>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_ViewField.class.getName(),defaultToSessionFactoryIdorName});
		 for(AutoETL_ViewField autoETL_ViewField : autoETL_ViewFieldList){
			 String Key=autoETL_ViewField.getAutoETL_View().getDataSource().getSessionFactory()+"|"+autoETL_ViewField.getAutoETL_View().getStrChinaName()+"|"+autoETL_ViewField.getStrChinaName();
			 if(mapAutoETL_ViewField_Target.containsKey(Key) && !mapAutoETL_ViewField_Target.containsValue(autoETL_ViewField)){
				 return new MessageResult(false,"目标数据库中同一试图下存在相同的试图字段");
			 }
			 else if(!mapAutoETL_ViewField_Target.containsKey(Key)){
				 mapAutoETL_ViewField_Target.put(Key, autoETL_ViewField);
			 }
		 }
		
		 autoETL_ProcedureList=(List<AutoETL_Procedure>)singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{AutoETL_Procedure.class.getName(),defaultToSessionFactoryIdorName});
		 for(AutoETL_Procedure autoETL_Procedure : autoETL_ProcedureList){
			 String Key=autoETL_Procedure.getAutoETL_DataSource().getSessionFactory()+"|"+autoETL_Procedure.getStrProcedureName();
			 if(mapAutoETL_Procedure_Target.containsKey(Key) && !mapAutoETL_Procedure_Target.containsValue(autoETL_Procedure)){
				 return new MessageResult(false,"目标数据库中同一回话工厂下存在相同的存储过程");
			 }
			 else if(!mapAutoETL_Procedure_Target.containsKey(Key)){
				 mapAutoETL_Procedure_Target.put(Key, autoETL_Procedure);
			 }
		 }
		
		 return new MessageResult();
	 }
	 
	 @SuppressWarnings("unchecked")
	 private void SpecialTable(IParamObjectResultExecute singleObjectFindByCriteriaDao ) throws Exception{
		 //AutoETL_Param/AutoETL_FTP/AutoETL_FTPTrans/AutoETL_ActivityNodeForCalculate/AutoETL_ActivityNodeForSummary
		 if(value.containsKey(AutoETL_ActivityNodeForFTPTra.class.getName())){
			 for(Object object: value.get(AutoETL_ActivityNodeForFTPTra.class.getName())){
				 if(value.containsKey(AutoETL_FTP.class.getName()) && !value.get(AutoETL_FTP.class.getName()).contains(((AutoETL_ActivityNodeForFTPTra)object).getAutoETL_FTPTrans().getAutoETL_FTP())){
					 value.get(AutoETL_FTP.class.getName()).add(((AutoETL_ActivityNodeForFTPTra)object).getAutoETL_FTPTrans().getAutoETL_FTP());
				 }
				 else if(!value.containsKey(AutoETL_FTP.class.getName())){
				 //value.get(AutoETL_FTP.class.getName()).add();
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_ActivityNodeForFTPTra)object).getAutoETL_FTPTrans().getAutoETL_FTP());
					 value.put(AutoETL_FTP.class.getName(), objectlist);
				 }
			 
				 if(value.containsKey(AutoETL_FTPTrans.class.getName()) && !value.get(AutoETL_FTPTrans.class.getName()).contains(((AutoETL_ActivityNodeForFTPTra)object).getAutoETL_FTPTrans())){
					 value.get(AutoETL_FTPTrans.class.getName()).add(((AutoETL_ActivityNodeForFTPTra)object).getAutoETL_FTPTrans());
				 }
				 else if(!value.containsKey(AutoETL_FTPTrans.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_ActivityNodeForFTPTra)object).getAutoETL_FTPTrans());
					 value.put(AutoETL_FTP.class.getName(), objectlist);
				 }
			 }
		 }
		 if(value.containsKey(AutoETL_WorkflowParamMV.class.getName())){
			 for(Object object : value.get(AutoETL_WorkflowParamMV.class.getName())){
				 if(value.containsKey(AutoETL_Param.class.getName()) && !value.get(AutoETL_Param.class.getName()).contains(((AutoETL_WorkflowParamMV)object).getAutoETL_Param())){
					 value.get(AutoETL_Param.class.getName()).add(((AutoETL_WorkflowParamMV)object).getAutoETL_Param());
				 }
				 else if(!value.containsKey(AutoETL_Param.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_WorkflowParamMV)object).getAutoETL_Param());
					 value.put(AutoETL_Param.class.getName(), objectlist);
				 }
			 }
		 }
		 if(value.containsKey(AutoETL_ActivityNodeParam.class.getName())){
			 for(Object object : value.get(AutoETL_ActivityNodeParam.class.getName())){
				 if(value.containsKey(AutoETL_Param.class.getName()) && !value.get(AutoETL_Param.class.getName()).contains(((AutoETL_ActivityNodeParam)object).getAutoETL_Param())){
					 value.get(AutoETL_Param.class.getName()).add(((AutoETL_ActivityNodeParam)object).getAutoETL_Param());
				 }
				 else if(!value.containsKey(AutoETL_Param.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_ActivityNodeParam)object).getAutoETL_Param());
					 value.put(AutoETL_Param.class.getName(), objectlist);
				 }
			 }
		 }
		 if(value.containsKey(AutoETL_ActivityNodeProcP.class.getName())){
			 for(Object object : value.get(AutoETL_ActivityNodeProcP.class.getName())){
				 if(value.containsKey(AutoETL_Param.class.getName()) && !value.get(AutoETL_Param.class.getName()).contains(((AutoETL_ActivityNodeProcP)object).getAutoETL_Param())){
					 value.get(AutoETL_Param.class.getName()).add(((AutoETL_ActivityNodeProcP)object).getAutoETL_Param());
				 }
				 else if(!value.containsKey(AutoETL_Param.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_ActivityNodeProcP)object).getAutoETL_Param());
					 value.put(AutoETL_Param.class.getName(), objectlist);
				 }
			 }
		 }
		 if(value.containsKey(AutoETL_WorkflowPFPVDetail.class.getName())){
			 for(Object object : value.get(AutoETL_WorkflowPFPVDetail.class.getName())){
				 if(value.containsKey(AutoETL_Param.class.getName()) && !value.get(AutoETL_Param.class.getName()).contains(((AutoETL_WorkflowPFPVDetail)object).getAutoETL_Param())){
					 value.get(AutoETL_Param.class.getName()).add(((AutoETL_WorkflowPFPVDetail)object).getAutoETL_Param());
				 }
				 else if(!value.containsKey(AutoETL_Param.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_WorkflowPFPVDetail)object).getAutoETL_Param());
					 value.put(AutoETL_Param.class.getName(), objectlist);
				 }
			 }
		 }
		 if(value.containsKey(AutoETL_WorkflowPP.class.getName())){
			 for(Object object : value.get(AutoETL_WorkflowPP.class.getName())){
				 if(value.containsKey(AutoETL_Param.class.getName()) && !value.get(AutoETL_Param.class.getName()).contains(((AutoETL_WorkflowPP)object).getAutoETL_Param())){
					 value.get(AutoETL_Param.class.getName()).add(((AutoETL_WorkflowPP)object).getAutoETL_Param());
				 }
				 else if(!value.containsKey(AutoETL_Param.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_WorkflowPP)object).getAutoETL_Param());
					 value.put(AutoETL_Param.class.getName(), objectlist);
				 }
			 }
		 }
		 if(value.containsKey(AutoETL_WorkflowPSDV.class.getName())){
			 for(Object object : value.get(AutoETL_WorkflowPSDV.class.getName())){
				 if(value.containsKey(AutoETL_Param.class.getName()) && !value.get(AutoETL_Param.class.getName()).contains(((AutoETL_WorkflowPSDV)object).getAutoETL_Param())){
					 value.get(AutoETL_Param.class.getName()).add(((AutoETL_WorkflowPSDV)object).getAutoETL_Param());
				 }
				 else if(!value.containsKey(AutoETL_Param.class.getName())){
					 List<Object> objectlist=new ArrayList<Object>();
					 objectlist.add(((AutoETL_WorkflowPSDV)object).getAutoETL_Param());
					 value.put(AutoETL_Param.class.getName(), objectlist);
				 }
			 }
		 }

		
		 
		 if(value.containsKey(AutoETL_ActivityNode.class.getName())){
			 Field fieldPKC=ReflectOperation.getJoinColumnFieldList(AutoETL_ActivityNodeForCalculate.class).get(0);
			 for(Object object : value.get(AutoETL_ActivityNode.class.getName())){
			 		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForCalculate.class);
			 		detachedCriteria.add(Restrictions.eq(fieldPKC.getName(), object));
			 		List<Object> autoETL_ActivityNodeForCalculateList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultFromSessionFactoryIdorName});
			 		if(!value.containsKey(AutoETL_ActivityNodeForCalculate.class.getName())){
			 			List<Object> objectList=new ArrayList<Object>();
			 			for(Object o : autoETL_ActivityNodeForCalculateList){
			 				objectList.add((AutoETL_ActivityNodeForCalculate)o);
			 			}
			 		}
			 		else{
			 			for(Object o : autoETL_ActivityNodeForCalculateList){
			 				value.get(AutoETL_ActivityNodeForCalculate.class.getName()).add((AutoETL_ActivityNodeForCalculate)o);
			 			}
			 		}
				
				
			 		detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSummary.class);
			 		Field fieldPKS=ReflectOperation.getJoinColumnFieldList(AutoETL_ActivityNodeForSummary.class).get(1);
			 		detachedCriteria.add(Restrictions.eq(fieldPKS.getName(), object));
			 		List<Object> autoETL_ActivityNodeForSummaryList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultFromSessionFactoryIdorName});
			 		if(!value.containsKey(AutoETL_ActivityNodeForCalculate.class.getName())){
			 			List<Object> objectList=new ArrayList<Object>();
			 			for(Object o : autoETL_ActivityNodeForSummaryList){
			 				objectList.add((AutoETL_ActivityNodeForSummary)o);
			 			}
			 			value.put(AutoETL_ActivityNodeForCalculate.class.getName(), objectList);
			 		}
			 		else{
			 			for(Object o : autoETL_ActivityNodeForSummaryList){
			 				value.get(AutoETL_ActivityNodeForSummary.class.getName()).add((AutoETL_ActivityNodeForSummary)o);
			 			}
			 		}
			 }
		 }
	 }

	 @SuppressWarnings("unchecked")
	 private void SetWorkflowAndActivityNode() throws Exception{
		 IParamObjectResultExecute singleObjectFindAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		 List<AutoETL_Workflow> autoETL_WorkflowList=(List<AutoETL_Workflow>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{AutoETL_Workflow.class.getName(),defaultToSessionFactoryIdorName});
		 for(AutoETL_Workflow autoETL_Workflow : autoETL_WorkflowList){
			 mapAutoETL_Workflow_Target.put(autoETL_Workflow.getStrWorkflowName(), autoETL_Workflow);
		 }
		 List<AutoETL_ActivityNode> autoETL_ActivityNodeList=(List<AutoETL_ActivityNode>)singleObjectFindAllDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),defaultToSessionFactoryIdorName});
		 for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeList){
			 mapAutoETL_ActivityNode_Target.put(autoETL_ActivityNode.getAutoETL_Workflow().getStrWorkflowName()+"|"+autoETL_ActivityNode.getStrActivityNodeName(), autoETL_ActivityNode);
		 }
	 }
	
	 private void SetMappingWorkflowAndActivityNode(String className) throws Exception{
		List<Field> ManytoOnelist = ReflectOperation.getJoinColumnFieldList(className);
		for(Field field :ManytoOnelist){
			if(field.getType().equals(AutoETL_ActivityNode.class) && value.containsKey(className)){
				for(Object object : value.get(className)){
					Object objectx=ReflectOperation.getFieldValue(object, field.getName());
					String Key=((AutoETL_ActivityNode)objectx).getStrActivityNodeName()+"|"+((AutoETL_ActivityNode)objectx).getStrActivityNodeName();
					if(!mapAutoETL_ActivityNode_Target.isEmpty() && mapAutoETL_ActivityNode_Target.containsKey(Key)){
						ReflectOperation.setFieldValue(objectx, ReflectOperation.getPrimaryKeyField(AutoETL_ActivityNode.class).getName(), ((AutoETL_ActivityNode)mapAutoETL_ActivityNode_Target.get(Key)).getAutoActivityNodeID());
						ReflectOperation.setFieldValue(object, field.getName(),objectx);
					}
				}
			}
			if(field.getType().equals(AutoETL_Workflow.class) && value.containsKey(className)){
				for(Object object : value.get(className)){
					Object objectx=ReflectOperation.getFieldValue(object, field.getName());
					String Key=((AutoETL_Workflow)objectx).getStrWorkflowName();
					if(!mapAutoETL_Workflow_Target.isEmpty() && mapAutoETL_Workflow_Target.containsKey(Key)){
						ReflectOperation.setFieldValue(objectx, ReflectOperation.getPrimaryKeyField(AutoETL_Workflow.class).getName(), ((AutoETL_Workflow)mapAutoETL_Workflow_Target.get(Key)).getAutoWorkflowID());
						ReflectOperation.setFieldValue(object, field.getName(),objectx);
					}
				}
			}
		}
	}
	 
	 @SuppressWarnings("unchecked")
	 private void getTargetValueSize(List<Field> fieldList,Object Value,IParamObjectResultExecute singleObjectFindByCriteriaDao,DetachedCriteria detachedCriteria) throws Exception{
		 for(Field field : fieldList){
				Object ox =((Class)((ParameterizedTypeImpl)field.getGenericType()).getActualTypeArguments()[0]).getName();
				detachedCriteria = DetachedCriteria.forClass(Class.forName(ox.toString()));
				OneToMany oneToMany=field.getAnnotation(OneToMany.class);
				detachedCriteria.add(Restrictions.eq(oneToMany.mappedBy(), Value));
				List<Object> objectList =(List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultToSessionFactoryIdorName});
				List<Field> fieldListx=getOneToManyField(Class.forName(ox.toString()));
				for(Object object: objectList){
					if(valueObject.containsKey(object.getClass().getName())){
						valueObject.get(object.getClass().getName()).add(object);
					}
					else{
						List<Object> objectx=new ArrayList<Object>();
						objectx.add(object);
						valueObject.put(object.getClass().getName(), objectx);
					}
					getTempValueSize(fieldListx,object,singleObjectFindByCriteriaDao,detachedCriteria);
				}
				
			}
	 }
	 
	 @SuppressWarnings("unchecked")
	private void deleteTargetValueSize(IParamObjectResultExecute singleObjectFindByCriteriaDao) throws Exception{
		 Set<Object> fieldName=new LinkedHashSet<Object>();
		 if(value.containsKey(AutoETL_ActivityNode.class.getName())){
			 for(Object object : value.get(AutoETL_ActivityNode.class.getName())){
				 String name=((AutoETL_ActivityNode)object).getAutoETL_Workflow().getStrWorkflowName().toString()+((AutoETL_ActivityNode)object).getStrActivityNodeName().toString();
				 fieldName.add(name);
			 }
		 }
		 if(valueObject.containsKey(AutoETL_ActivityNode.class.getName())){
			 Field fieldPKC=ReflectOperation.getJoinColumnFieldList(AutoETL_ActivityNodeForCalculate.class).get(0);
			 for(Object object : valueObject.get(AutoETL_ActivityNode.class.getName())){
				 	String name=((AutoETL_ActivityNode)object).getAutoETL_Workflow().getStrWorkflowName().toString()+((AutoETL_ActivityNode)object).getStrActivityNodeName().toString();
				 	if(!fieldName.contains(name)){
				 		continue;
				 	}
			 		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForCalculate.class);
			 		detachedCriteria.add(Restrictions.eq(fieldPKC.getName(), object));
			 		List<Object> autoETL_ActivityNodeForCalculateList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultToSessionFactoryIdorName});
			 		if(!valueObject.containsKey(AutoETL_ActivityNodeForCalculate.class.getName())){
			 			List<Object> objectList=new ArrayList<Object>();
			 			for(Object o : autoETL_ActivityNodeForCalculateList){
			 				objectList.add((AutoETL_ActivityNodeForCalculate)o);
			 			}
			 			if(autoETL_ActivityNodeForCalculateList.size()>0){
			 				valueObject.put(AutoETL_ActivityNodeForCalculate.class.getName(), objectList);
			 			}
			 		}
			 		else{
			 			for(Object o : autoETL_ActivityNodeForCalculateList){
			 				valueObject.get(AutoETL_ActivityNodeForCalculate.class.getName()).add((AutoETL_ActivityNodeForCalculate)o);
			 			}
			 		}
				
				
			 		detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSummary.class);
			 		Field fieldPKS=ReflectOperation.getJoinColumnFieldList(AutoETL_ActivityNodeForSummary.class).get(1);
			 		detachedCriteria.add(Restrictions.eq(fieldPKS.getName(), object));
			 		List<Object> autoETL_ActivityNodeForSummaryList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,defaultToSessionFactoryIdorName});
			 		if(!valueObject.containsKey(AutoETL_ActivityNodeForSummary.class.getName())){
			 			List<Object> objectList=new ArrayList<Object>();
			 			for(Object o : autoETL_ActivityNodeForSummaryList){
			 				objectList.add((AutoETL_ActivityNodeForSummary)o);
			 			}
			 			if(autoETL_ActivityNodeForSummaryList.size()>0){
			 				valueObject.put(AutoETL_ActivityNodeForSummary.class.getName(), objectList);
			 			}
			 		}
			 		else{
			 			for(Object o : autoETL_ActivityNodeForSummaryList){
			 				valueObject.get(AutoETL_ActivityNodeForSummary.class.getName()).add((AutoETL_ActivityNodeForSummary)o);
			 			}
			 		}
			 }
		 }
	 }
}
