package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.service.imps.AutoUpdateStatusByJCObjectService;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.show.ShowContext;

public class UpdateFeedbackToDeletedService {

	IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	Map<String,String> QYZYLogicPrimaryKeyForXXJLCZLX_JC=ShowContext.getInstance().getShowEntityMap().get("QYZYLogicPrimaryKeyForXXJLCZLX_JC"); //根据表名得到基础段逻辑主键
	Map<String,String> QYZYLogicPrimaryKeyForXXJLCZLX_MX=ShowContext.getInstance().getShowEntityMap().get("QYZYLogicPrimaryKeyForXXJLCZLX_MX"); //根据表名得到明细段逻辑主键
	
	@SuppressWarnings("unchecked")
	public void UpdateFeedbackToDeleted(List<Object> objectList, String fileName) throws Exception {
		List<Object> objDeletedDtoNameList=new ArrayList<Object>();
		
		for (Object object : objectList) {
			boolean isFlag=false;
			//信息记录操作类型为“删除”
			if(ReflectOperation.getFieldValue(object, "XXJLCZLX")!=null && ReflectOperation.getFieldValue(object, "XXJLCZLX").equals("4")){
				if(ReflectOperation.getFieldValue(object, "RPTFeedbackType")!=null && (ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("2") || ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("4"))){
					List<Field> dtoFieldList=ReflectOperation.getOneToManyField(object.getClass().getName());
					for (Field field : dtoFieldList) {
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
						detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));//回执成功
						detachedCriteria.add(Restrictions.eq("extend1",fileName));//当前报文
						detachedCriteria.add(Restrictions.eq("FOREIGNID",object));
						List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						if(objectMXList.size()>0){
							for (Object objectMX : objectMXList) {
								String YWFSRQ="";
								if(ReflectOperation.getFieldValue(objectMX, "extend2")!=null && !ReflectOperation.getFieldValue(objectMX, "extend2").equals("")){
									YWFSRQ=ReflectOperation.getFieldValue(objectMX, "extend2").toString();
								}
								//查询逻辑主键相对应的基础段（信息记录操作类型为“新增”）
								detachedCriteria = DetachedCriteria.forClass(object.getClass());
								detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));//已回执
								detachedCriteria.add(Restrictions.eq("XXJLCZLX", "1"));//新增
								//匹配基础逻辑主键
								if(QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(object.getClass().getName())!=null){
									if(QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(object.getClass().getName()).indexOf(",")>0){
										String[] strFieldNames=QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(object.getClass().getName()).split(",");
										for (String strFieldName : strFieldNames) {
											detachedCriteria.add(Restrictions.eq(strFieldName,ReflectOperation.getFieldValue(object, strFieldName)));
										}
									}
								}
								List<Object> objectJCConditionList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								if(objectJCConditionList.size()>0){
									for (Object objectJCCondition : objectJCConditionList) {
										//查询逻辑主键相对应的明细段
										detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
										detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));//回执成功
										//匹配明细逻辑主键
										if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(object.getClass().getName())!=null){
											if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(object.getClass().getName()).indexOf(",")>0){
												String[] strFieldNames=QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(object.getClass().getName()).split(",");
												for (String strFieldName : strFieldNames) {
													detachedCriteria.add(Restrictions.eq(strFieldName,ReflectOperation.getFieldValue(objectMX, strFieldName)));
												}
											}else{
												String strFieldName=QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(object.getClass().getName());
												detachedCriteria.add(Restrictions.eq(strFieldName,ReflectOperation.getFieldValue(objectMX, strFieldName)));
											}
										}
										detachedCriteria.add(Restrictions.eq("FOREIGNID",objectJCCondition));
										detachedCriteria.add(Restrictions.eq("extend2",YWFSRQ));
										
										List<Object> objectMXConditionList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										for (Object objectMXCondition : objectMXConditionList) {
											ReflectOperation.setFieldValue(objectMXCondition, "RPTFeedbackType", "5");//已删除
											if(!isFlag){
												isFlag=true;
												objDeletedDtoNameList.add(objectJCCondition);
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		}
		
		if(objDeletedDtoNameList.size()>0){//保存对象
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{objDeletedDtoNameList,null});
		}
		
		//明细段删除成功之后，如果该基础段下所有明细段都“已删除”，那么就删除基础段
		for (Object objectJC : objDeletedDtoNameList) {
			List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectJC.getClass().getName());
			int k=0;
			for (Field fieldMx : dtoFieldList) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(fieldMx.getGenericType()));
				detachedCriteria.add(Restrictions.eq("FOREIGNID", objectJC));
				detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
				List<Object> objectAllDeletedMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				if(objectAllDeletedMXList.size()<=0){
					k++;
					if(dtoFieldList.size()==k){
						ReflectOperation.setFieldValue(objectJC, "RPTFeedbackType", "5");
						IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
						singleObjectUpdateDao.paramVoidResultExecute(new Object[]{objectJC,null});
					}
				}
			}
		}
		
		List<Object> tempObjectList=new ArrayList<Object>();
		for (Object objectTemp : objDeletedDtoNameList) {
			if(ReflectOperation.getFieldValue(objectTemp, "RPTFeedbackType").equals("5")){
				tempObjectList.add(objectTemp);
			}
		}
		if(tempObjectList.size()>0){
			objDeletedDtoNameList.removeAll(tempObjectList);
		}
		
		//统计状态
		for (Object objectJC : objDeletedDtoNameList) {
			AutoUpdateStatusByJCObjectService autoUpdate=new AutoUpdateStatusByJCObjectService();
			autoUpdate.getJCStatusByMx(objectJC);//统计基础段状态
		}
		
		if(objDeletedDtoNameList.size()>0){//保存基础对象
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{objDeletedDtoNameList,null});
		}
		
		//统计状态
		for (Object objectJC : objDeletedDtoNameList) {
			AutoUpdateStatusByJCObjectService autoUpdate=new AutoUpdateStatusByJCObjectService();
			autoUpdate.getRWStatusByJC(objectJC);//统计任务层状态
		}
		
	}
	
}
