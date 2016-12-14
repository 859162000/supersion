package framework.services.procese;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.annotations.Component;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;

import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.imps.SingleObjectShowListService;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowComponentSaveOrUpdate;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowList;


public class ShowComponentSaveOrUpdateProcese extends BaseConstructor implements IProcese{
	
	public Object Procese(Object serviceResult) throws Exception {
		
		ShowSaveOrUpdate showSaveOrUpdate=(ShowSaveOrUpdate)serviceResult;
		ShowComponentSaveOrUpdate showComponentSaveOrUpdate=new ShowComponentSaveOrUpdate();
		showComponentSaveOrUpdate.setShowSaveOrUpdate(showSaveOrUpdate);
		processDetails(showComponentSaveOrUpdate);
		return showComponentSaveOrUpdate;
	}
	
	private SingleObjectShowListService getListService(String dtoName)
	{
		
		SingleObjectShowListService singleObjectShowListService;
		Map<String,String> dtoListServiceMap=LogicParamManager.getDtoListServiceMap();
		if(dtoListServiceMap!=null)
		{
			String listService=dtoListServiceMap.get(dtoName);
			if(!StringUtils.isBlank(listService))
			{
				singleObjectShowListService=(SingleObjectShowListService)FrameworkFactory.CreateBean(listService);
				return singleObjectShowListService;
			}
		}
		
		
		
		singleObjectShowListService=(SingleObjectShowListService)FrameworkFactory.CreateBean("showListForComponentDetailService");
		return singleObjectShowListService;
	}
	private void processDetails(ShowComponentSaveOrUpdate serviceResult) throws Exception 
	{
		String currentTName=RequestManager.getTName();
		String currentAction=RequestManager.getActionName();
		String typeName=RequestManager.getTypeName();
		String levelId=RequestManager.getLevelIdValue();
		Object id=RequestManager.getId();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		ShowInstance showInstance=ReflectOperation.getShowInstance(currentTName, showInstanceName);
		try
		{
			 
			serviceResult.setShowCardList(processDetailForCard(currentTName,showInstance,id));
			hideMasterJoinField(serviceResult,showInstance);
			serviceResult.setShowListList(processDetailForList(currentTName,showInstance, id));
		}
		finally
		{
			RequestManager.setActionName(currentAction);
			RequestManager.setTName(currentTName);
			RequestManager.setTypeName(typeName);
			RequestManager.setLevelIdValue(levelId);
			RequestManager.setId(id);
			ShowParamManager.setShowInstanceName(showInstanceName);
			TActionRule.initActionName();
		}
		
		
		
		
	}
	/**
	 * @param currentTName
	 * @param masterId
	 * @throws Exception
	 */
	private List<ShowSaveOrUpdate> processDetailForCard(String currentTName,ShowInstance showInstance,Object masterId)
			throws Exception{
		String detailDtoName;
		List<ShowSaveOrUpdate> showCardList=new ArrayList<ShowSaveOrUpdate>();
		
		
		List<Field> fieldList=ReflectOperation.getComponentCardList(currentTName,showInstance.getFieldListWithShowCard());
		Map<String,String> dtoShowInstanceMap=LogicParamManager.getDtoShowInstanceMap();
		for (Field field : fieldList) {
			boolean isJoinField=!ReflectOperation.isCollectionFieldWithShowCard(field);
			detailDtoName=ReflectOperation.getDtoName(field);
			
			Object baseObject=this.getBaseObject();
			Object cardObject=getCardObject(currentTName, masterId, detailDtoName,
										isJoinField, field, baseObject);
			
			 
			RequestManager.setTName(detailDtoName);
			RequestManager.setTypeName(currentTName);
			RequestManager.setLevelIdValue(masterId);
			String showInstanceName = null;
			if(dtoShowInstanceMap!=null)
			{
				showInstanceName=dtoShowInstanceMap.get(detailDtoName);
			}
			if(!StringUtils.isBlank(showInstanceName))
			{
				showInstanceName="1";
			}
			ShowParamManager.setShowInstanceName(showInstanceName);
			//TActionRule.initActionName();
			
			IProcese process=new ShowUpdateProcese();
			ShowSaveOrUpdate detailCardShow=(ShowSaveOrUpdate)process.Procese(cardObject);
			processCardParamName(field.getName(),isJoinField,detailCardShow);
			if(!isJoinField)
			{
				hideOneToManyForeignField(currentTName, masterId, detailCardShow);
			}
			
			showCardList.add(detailCardShow);
				
			
			
		}
		return showCardList;
	}

	/**
	 * @param currentTName
	 * @param masterId
	 * @param detailDtoName
	 * @param isJoinField
	 * @param field
	 * @param baseObject
	 * @return
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Object getCardObject(String currentTName, Object masterId,
			String detailDtoName, boolean isJoinField, Field field,
			Object baseObject) throws Exception
			 {
		Object cardObject=null;
		if(isJoinField)
		{
			if(baseObject!=null)
			{
				cardObject=ReflectOperation.getFieldValue(baseObject, field.getName());
			}
		}
		else
		{
			DetachedCriteria detachedCriteria = DetachedCriteria.forEntityName(detailDtoName);
			if(masterId!=null &&!StringUtils.isBlank(masterId.toString()))
			{
				for(Field fi : ReflectOperation.getReflectFields(Class.forName(detailDtoName))){
					if(fi.getType().equals(Class.forName(currentTName))){
						Object object = fi.getType().newInstance();
						ReflectOperation.setFieldValue(object, ReflectOperation.getPrimaryKeyField(fi.getType()).getName(), masterId.toString());
						detachedCriteria.add(Restrictions.eq(fi.getName(), object));
						break;
					}
				}
				IParamObjectResultExecute  findByCriteria= (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				List<Object> result=(List<Object> )findByCriteria.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(result.size()>0)
				{
					cardObject=result.get(0);
				}
			}
			
			
		}
		return cardObject;
	}
	private void hideMasterJoinField(ShowComponentSaveOrUpdate showComponent,ShowInstance showInstance) throws Exception
	{
		ShowSaveOrUpdate masterCard=showComponent.getShowSaveOrUpdate();
		List<Field> fieldList=ReflectOperation.getComponentCardList(masterCard.getTName(),showInstance.getFieldListWithShowCard());
		List<ShowFieldValue> masterShowFieldList=masterCard.getShowFieldValueList();
		List<ShowFieldValue> hiddenShowFieldList=new ArrayList<ShowFieldValue>();
		for (Field field : fieldList) 
		{
			
			for(ShowFieldValue showFieldValue:masterShowFieldList)
			{
				if(showFieldValue.getShowField().getFieldName().equals(field.getName()))
				{
					hiddenShowFieldList.add(showFieldValue);
				}
			}
		}
		masterCard.getShowFieldValueList().removeAll(hiddenShowFieldList);
	}
	
	private void processCardParamName(String joinFieldName,boolean isJoinField,ShowSaveOrUpdate detailCardShow) throws Exception
	{
		
		for(ShowFieldValue showFieldValue:detailCardShow.getShowFieldValueList())
		{
			ShowField showField=showFieldValue.getShowField();
			ShowField newShowField=showField.Copy();
			String paramName=showField.getParamName();
			if(isJoinField)
			{
				newShowField.setParamName(joinFieldName+"."+paramName);
			}
			else
			{
				newShowField.setParamName(joinFieldName+".makeNew[0]."+paramName);
			}
			
			showFieldValue.setShowField(newShowField);
			
			 
		}
	}
	
	private void hideOneToManyForeignField(String curTName,Object materId,ShowSaveOrUpdate detailCardShow) throws Exception
	{
		
		for(ShowFieldValue showFieldValue:detailCardShow.getShowFieldValueList())
		{
			ShowField showField=showFieldValue.getShowField();
			
			Field f=ReflectOperation.getFieldByName(detailCardShow.getTName(),showField.getFieldName());
			if(f.getType().getName().equals(curTName))
			{
				showField.setSingleTag(ApplicationManager.getSingleTagHidden());
				showFieldValue.setFieldValue(materId);
			}
			
			 
		}
	}
	
	
	/**
	 * @param currentTName
	 * @param masterId
	 * @throws Exception
	 */
	private List<ShowList> processDetailForList(String currentTName,ShowInstance showInstance, Object masterId)
			throws Exception{
		String detailDtoName;
		String actionName="ShowComponentDetail-";
		List<ShowList> showListList=new ArrayList<ShowList>();
		List<ShowField> listShowConfigList=showInstance.getFieldListWithShowList();
		List<Field> fieldList=ReflectOperation.getComponentDetailList(currentTName, listShowConfigList);
		Map<String,String> dtoShowInstanceMap=LogicParamManager.getDtoShowInstanceMap();
		for (Field field : fieldList) 
		{
			Class<?> fieldType=ReflectOperation.getGenericClass(field.getGenericType());
			detailDtoName=fieldType.getName();
			
			SingleObjectShowListService singleObjectShowListService=getListService(detailDtoName);
			RequestManager.setActionName(actionName+detailDtoName);
			RequestManager.setTName(detailDtoName);
			RequestManager.setTypeName(currentTName);
			RequestManager.setLevelIdValue(masterId);
			
			TActionRule.initActionName();
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(fieldType);
			if(masterId==null){
				detachedCriteria.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(fieldType).getName(), null));
			}else{
				for(Field fi : ReflectOperation.getReflectFields(fieldType)){
					if(fi.getType().equals(Class.forName(currentTName))){
						Object object = fi.getType().newInstance();
						ReflectOperation.setFieldValue(object, ReflectOperation.getPrimaryKeyField(fi.getType()).getName(), masterId.toString());
						detachedCriteria.add(Restrictions.eq(fi.getName(), object));
						break;
					}
				}
			}
			LogicParamManager.setDetachedCriteria(detachedCriteria);
			RequestManager.setCurrentPage(1);
			if(dtoShowInstanceMap != null){
				singleObjectShowListService.setShowInstanceName(dtoShowInstanceMap.get(detailDtoName));
			}
			ShowList showList=(ShowList)singleObjectShowListService.objectResultExecute();
			
			showListList.add(showList);
			
			
		}
		return showListList;
	}
	
	

}
