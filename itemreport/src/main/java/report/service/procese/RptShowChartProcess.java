package report.service.procese;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import ognl.Ognl;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.RptChartCond;

import com.opensymphony.util.GUID;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.security.DataSecurity;
import framework.security.SecurityContext;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowHeaderName;
import framework.show.ShowInstance;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;

// 组装数据到ShowList对象中
public class RptShowChartProcess implements IProcese{

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		List<ShowFieldCondition> showFieldConditionList = new ArrayList<ShowFieldCondition>();
		Field[] fieldList = ReflectOperation.getReflectFields(RptChartCond.class);
		for(Field f:fieldList)
		{
			if(f.isAnnotationPresent(IColumn.class))
			{
				IColumn ic=f.getAnnotation(IColumn.class);
				ShowFieldCondition showFieldCondition = new ShowFieldCondition();
				showFieldCondition.setFieldName(f.getName());
				showFieldCondition.setFieldShowName(ic.description());
				if(!StringUtils.isBlank(ic.tagMethodName()))
				{
					
					Method method = ReflectOperation.getReflectMethod(RptChartCond.class,ic.tagMethodName());
					showFieldCondition.setTag(method.invoke(RptChartCond.class));
				}
				
				
				showFieldCondition.setMultipleSelect(false);
				if(f.getType().equals(Date.class))
				{
					showFieldCondition.setSingleTag(ApplicationManager.getSingleTagDate());
				}
				else if(ic.isIdListField())
				{
					showFieldCondition.setSingleTag(ApplicationManager.getSingleTagMultipleSelect());
					showFieldCondition.setMultipleSelect(true);
				}
				else if(!StringUtils.isBlank(ic.tagMethodName()))
				{
					
					showFieldCondition.setSingleTag(ApplicationManager.getSingleTagSelect());
				}
				else{
					showFieldCondition.setSingleTag(ApplicationManager.getSingleTagTextfield());
					
				}
				showFieldCondition.setParamName(f.getName());
				
				showFieldConditionList.add(showFieldCondition);
				if("itemProperty".equals(f.getName())||
						"suit".equals(f.getName())||
						"currencyType".equals(f.getName())||
						"instInfo".equals(f.getName()))
				{
					Field relationPK=ReflectOperation.getPrimaryKeyField(f.getType());
					showFieldCondition.setParamName(f.getName()+"."+relationPK.getName());
					showFieldCondition.setSingleTag(ApplicationManager.getSingleTagSelect());
					showFieldCondition.setTag(getSimpleMap(f.getName(),f.getType()));
				}
				
				
				
			}
		}
		ShowList sl=new ShowList();
		sl.setShowCondition(showFieldConditionList);
		List<ShowOperation> showOps = new ArrayList<ShowOperation>();
		ShowOperation op=new ShowOperation();
		op.setOperationName("分析");
		op.setOperationType("Select");
		op.setOperationNamespace("report");
		op.setOperationAction("ShowChart-report.dto.RptChartCond.action");
		showOps.add(op);
		sl.setOperationList(showOps);
		
		return sl;
	}
	
	
	private Map<String,String> getSimpleMap(String fieldName, Class<?> type)  throws Exception{
        
	    Map<String,String> simpleMap = new LinkedHashMap<String,String>();
	    
	   
	    
	    
	    Field primaryKeyField = ReflectOperation.getPrimaryKeyField(type);
		Field keyNameField = ReflectOperation.getKeyNameField(type);
		String defaultLogicDaoBeanId = "singleObjectFindByCriteriaDao";
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
	    
	    //数据权限过滤
	    Map<String, Map<String,String>> dataSecurityMap = SecurityContext.getInstance().getDataSecurityMap();
	    if(dataSecurityMap != null){
	    	for(Map.Entry<String, Map<String,String>> entry : dataSecurityMap.entrySet()){
		    	if(entry.getValue().containsKey(RequestManager.getTName())){
		    		String dataSecurityFieldName = entry.getValue().get(RequestManager.getTName());
		    		if(fieldName.equals(dataSecurityFieldName)){
		    			Set<DataSecurity> dataSecuritySet = SecurityContext.getInstance().getLoginInfo().getDataSecuritySet();
			    		Set<String> currentDataSecurity = new HashSet<String>();
			    		for(DataSecurity dataSecurity : dataSecuritySet){
			    			if(dataSecurity.getClassName().equals(RequestManager.getTName()) && dataSecurity.getFieldName().equals(dataSecurityFieldName)){
			    				currentDataSecurity = dataSecurity.getDataSet();
			    				break;
			    			}
			    		}
			    		if(currentDataSecurity.size() == 0){
			    			detachedCriteria.add(Restrictions.eq(primaryKeyField.getName(), null));
			    		}
			    		else{
			    			detachedCriteria.add(Restrictions.in(primaryKeyField.getName(), currentDataSecurity));
			    		}
		    		}
	    		}
	    	}
	    }

    	LogicParamManager.setDetachedCriteria(detachedCriteria);
    	IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
    	List<Object> objectList = (List<Object>)defaultLogicDaoDao.paramObjectResultExecute(null);
		
		if(keyNameField != null){
			for(Object object : objectList){
				Object objFieldVal = ReflectOperation.getFieldValue(object, keyNameField.getName());
				if(objFieldVal != null)
					simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(), objFieldVal.toString());
			}
		}
		else{
			for(Object object : objectList){
				if(object != null)
					simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(),
					ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString());
			}
		}
		
		return simpleMap;

	}

}
