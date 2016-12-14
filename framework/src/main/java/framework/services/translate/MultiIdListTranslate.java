package framework.services.translate;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class MultiIdListTranslate extends BaseConstructor implements ITranslate{
	
	public MultiIdListTranslate(){
		super();
	}
	
	public MultiIdListTranslate(Object baseObject){
		super(baseObject);
	}
	
	public void Translate() throws Exception {

		Object tObject = this.getBaseObject();
		List<List<Map.Entry<Object, String>>> objectLists = getIdListFieldsObject(tObject);
		// 检测是否存在空字段，与任务下发有关
		if(tObject.getClass().getSimpleName().equals("TaskRptInst") || tObject.getClass().getSimpleName().equals("TaskModelInst")) {
			checkHasNullValeField(objectLists);
		}
		
		Set<Object> saveObjectList = new HashSet<Object>();
		if(objectLists.size()>0)
		{
			Field pk=ReflectOperation.getPrimaryKeyField(tObject.getClass());
			Object pkVal=ReflectOperation.getFieldValue(tObject, pk.getName());
			boolean isPkNull=true;
			if(pkVal!=null)
			{
				isPkNull=StringUtils.isBlank(pkVal.toString());
			}
			/* idlist字段的笛卡尔积操作
			 *（1）每次最后一列向下移一个单元格，就是lastCol指向的那列。
			 *（2）如果lastCol列到尾部了，则这列index重置为0，lastCol,向前移动一列，同时该列 index加一。
			 * （3）重复第二步，直到到第零列。 
			 */
			int recCnt=1;
			int[] colRowCnt=new int[objectLists.size()];
			int[] curRowIndex=new int [objectLists.size()];
			int curCol=0;
			for(List<Map.Entry<Object,String>> list:objectLists)
			{
				
				recCnt*=list.size();
				colRowCnt[curCol]=list.size()-1;
				curRowIndex[curCol]=0;
				curCol++;
				
			}
			
			int colCnt=objectLists.size();
			int lastCol=colCnt-1;
			List<String> notNullFieldNameList = new ArrayList<String>();// 与任务下发有关
			for(int i=0;i<recCnt;i++)
			{
				Object saveObject = tObject.getClass().newInstance();
				ReflectOperation.CopyColumnFieldValue(tObject, saveObject);
				if(isPkNull)
				{
					ReflectOperation.setFieldNullValue(saveObject, pk.getName(),pk.getType());
				}
				for(int j=0;j<colCnt;j++)
				{
					Object curVal=objectLists.get(j).get(curRowIndex[j]).getKey();
					String fieldName=objectLists.get(j).get(curRowIndex[j]).getValue();
					ReflectOperation.setFieldValue(saveObject,fieldName, curVal);
					
					// 与任务下发有关
					if(!notNullFieldNameList.contains(fieldName)) {
						notNullFieldNameList.add(fieldName);
					}
				}
				curRowIndex[lastCol]++;
				if(curRowIndex[lastCol]>colRowCnt[lastCol]&& lastCol>0)
				{
					curRowIndex[lastCol]=0;
					int end=lastCol-1;
					for(int k=lastCol-1;k>=end;k--)
					{
						curRowIndex[k]++;
						if(curRowIndex[k]>colRowCnt[k])
						{	
							curRowIndex[k]=0;
							end=k-1;
							if(end<0)
							{
								break;
							}
							
						}
					}
				}	
				saveObjectList.add(saveObject);
			}
					
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(tObject.getClass());
			List<Field> fieldList = ReflectOperation.getColumnFieldList(tObject.getClass());
			for(Field field : fieldList){
				Object value = ReflectOperation.getFieldValue(tObject, field.getName());
				if(value != null && !StringUtils.isBlank(value.toString())){
					notNullFieldNameList.add(field.getName());// 与任务下发有关
					detachedCriteria.add(Restrictions.eq(field.getName(), value));
				}
			}
			LogicParamManager.setDetachedCriteria(detachedCriteria);
			
			// 任务下发有关，查询当前任务下的统计报表任务数据
			if(tObject.getClass().getSimpleName().equals("TaskRptInst") || tObject.getClass().getSimpleName().equals("TaskModelInst")) {
				if(saveObjectList != null && !saveObjectList.isEmpty()) {
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					List<Object> oldList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(null);
					if(oldList != null && !oldList.isEmpty() && 
							notNullFieldNameList != null && !notNullFieldNameList.isEmpty()) {
						setSaveObjectList(saveObjectList, oldList, notNullFieldNameList);
					} else {
						List<Object> saveList = new ArrayList<Object>(saveObjectList);
						setDeaultValue(saveList);
						RequestManager.setTOject(saveList);
					}
				}
			}
			
		} else {// 针对任务下发时，当选择的数据存在空值时，或者未选择数据则将数据库中数据删除
			if(tObject.getClass().getSimpleName().equals("TaskRptInst") || tObject.getClass().getSimpleName().equals("TaskModelInst")) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(tObject.getClass());
				List<Field> fieldList = ReflectOperation.getColumnFieldList(tObject.getClass());
				boolean flag = false;// 设置一个标志，标识是否找到查询条件（即：SQL语句中where子句中的内容），如果有查询条件则删除数据，否则不予以删除，避免无子句时删除不必要的数据
				for(Field field : fieldList){
					Object value = ReflectOperation.getFieldValue(tObject, field.getName());
					
					if(value != null && (value.getClass().getSimpleName().equals("TaskFlow") || value.getClass().getSimpleName().equals("AutoTaskFlow"))){
						detachedCriteria.add(Restrictions.eq(field.getName(), value));
						flag = true;
						break;
					}
				}
				
				if(flag) {
					LogicParamManager.setDetachedCriteria(detachedCriteria);
					IParamVoidResultExecute singleObjectDeleteByCriteriaDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
					singleObjectDeleteByCriteriaDao.paramVoidResultExecute(null);
				}
			}
		}
		
		LogicParamManager.setSaveObjectList(saveObjectList);
		if(objectLists.isEmpty()) {
			RequestManager.setTOject(new ArrayList<Object>());
		}
	}

	/**
	 * 
	 * <P>将界面提交上的数据与数据库中的数据做对比<br>任务下发有关</P>
	 * @author Liutao
	 * @throws Exception 
	 */
	private void setSaveObjectList(Set<Object> saveObjectList, List<Object> oldList, List<String> notNullFieldNameList) throws Exception {
		boolean sameFlag = true;
		//Set<Object> saveObjectListTem = new HashSet<Object>();
		List<Object> saveList = new ArrayList<Object>();
		List<Object> tempList = new ArrayList<Object>();
		Field[] fields = ReflectOperation.getReflectFields(RequestManager.getTOject().getClass());
		for(Object saveObject : saveObjectList) {
			for(Object oldObject : oldList) {
				sameFlag = isSameObject(saveObject, oldObject, fields, notNullFieldNameList);
				if(sameFlag) {
					tempList.add(oldObject);
					break;
				} else {
					if(oldList.indexOf(oldObject) == oldList.size()-1) {
						saveList.add(saveObject);
					}
				}
			}
		}
		
		if(!tempList.isEmpty()) {
			oldList.removeAll(tempList);
			if(!oldList.isEmpty()) {
				IParamVoidResultExecute singleObjectDeleteByObjectDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByObjectDao");
				for(Object delObj : oldList) {
					RequestManager.setTOject(delObj);
					singleObjectDeleteByObjectDao.paramVoidResultExecute(null);
				}
			}
		}
		
		if(!saveList.isEmpty()) {
			setDeaultValue(saveList);
			RequestManager.setTOject(saveList);
		} else {
			RequestManager.setTOject(new ArrayList<Object>());
		}
	}
	
	/**
	 * 
	 * <P>针对任务下发，任务下发时设置任务的状态</P>
	 * @author Liutao
	 * @throws Exception 
	 */
	private void setDeaultValue(List<Object> saveList) throws Exception {
		String fieldName = null;
		Set<String> keySet = null;
		Map<String, String> defaultValueMap = LogicParamManager.getDefaultValueMap();
		Field[] fields = ReflectOperation.getReflectFields(this.getBaseObject().getClass());
		
		keySet = defaultValueMap.keySet();
		for(Object saveObj : saveList) {
			for(Field field : fields) {
				if(keySet.contains(field.getName())) {
					fieldName = field.getName();
					ReflectOperation.setFieldValue(saveObj, field.getName(), defaultValueMap.get(fieldName));
				}
			}
		}
		
	}
	
	/**
	 * 
	 * <P>比较当前的保存数据是否在数据库中存在<br>任务下发有关</P>
	 * @author Liutao
	 * @throws Exception 
	 */
	private boolean isSameObject(Object saveObject, Object oldObject, Field[] fields, List<String> notNullFieldNameList) throws Exception {
		boolean flag = true;// 默认两个对象是相同的
		Object oldFieldVal = null;
		Object saveFieldVal = null;
		Object oldFieldKeyValue = null;
		Object saveFieldKeyValue = null;
		for(Field field : fields) {
			if(notNullFieldNameList.contains(field.getName())) {
				oldFieldVal = ReflectOperation.getFieldValue(oldObject, field.getName());
				saveFieldVal = ReflectOperation.getFieldValue(saveObject, field.getName());
				if(ReflectOperation.isBaseType(oldFieldVal)) {
					if(oldFieldVal != null && saveFieldVal != null &&
							!oldFieldVal.toString().equals(saveFieldVal.toString()) ) {
						flag = false;
						break;
					}
				} else {
					oldFieldKeyValue = ReflectOperation.getPrimaryKeyValue(oldFieldVal);
					saveFieldKeyValue = ReflectOperation.getPrimaryKeyValue(saveFieldVal);
					
					if(oldFieldKeyValue != null && saveFieldKeyValue != null &&
							!oldFieldKeyValue.toString().equals(saveFieldKeyValue.toString())) {
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * <P>当下发任务时，判断机构字段和表字段是否存在空值<br>任务下发有关</P>
	 * @author Liutao
	 */
	private void checkHasNullValeField(List<List<Map.Entry<Object, String>>> objectLists) {
		boolean hasNullFalg = false;
		for(List<Map.Entry<Object, String>> objectList : objectLists) {
			if(objectList == null || objectList.isEmpty()) {
				hasNullFalg = true;
				break;
			}
		}
		
		if(hasNullFalg) {
			objectLists.clear();
		}
	}
	
	
	/**
	 * @param tObject
	 * @return
	 * @throws Exception
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private List<List<Map.Entry<Object, String>>> getIdListFieldsObject(Object tObject) throws Exception {
		List<Field> idListTargetFieldList = ReflectOperation.getIdListTargetFieldList(tObject.getClass());
		List<List<Map.Entry<Object,String>>> objectLists = new ArrayList<List<Map.Entry<Object,String>>>();
		Class<?> type = tObject.getClass();
		for(Field idListTargetField : idListTargetFieldList){
			List<Map.Entry<Object,String>> objectList=new ArrayList<Map.Entry<Object,String>>();

			Field idListField = ReflectOperation.getIdListField(type, idListTargetField);
			if(ReflectOperation.isBaseType(idListTargetField.getType())){
				String[] idList = (String[])ReflectOperation.getFieldValue(tObject, idListField.getName());
				for(int i=0;i<idList.length;i++){
					Object object = type.newInstance();
					ReflectOperation.CopyColumnFieldValue(tObject, object);
					objectList.add(new AbstractMap.SimpleEntry<Object,String>(idList[i], idListTargetField.getName()));
				}
			}
			else{
				String[] idList = (String[])ReflectOperation.getFieldValue(tObject, idListField.getName());
				Field primaryKeyField = ReflectOperation.getPrimaryKeyField(idListTargetField.getType());
				for(int i=0;i<idList.length;i++){
					Object object = type.newInstance();
					ReflectOperation.CopyColumnFieldValue(tObject, object);
					Object value = idListTargetField.getType().newInstance();
					ReflectOperation.setFieldValue(value, primaryKeyField.getName(), idList[i]);
					objectList.add(new AbstractMap.SimpleEntry<Object,String>(value, idListTargetField.getName()));
					
				}
			}
			
			objectLists.add(objectList);
		}
		return objectLists;
	}

}
