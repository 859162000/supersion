package framework.services.translate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.interfaces.ICondition.Comparison;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowFieldCondition;

public class ConditionCriteriaTranslate implements ITranslate{

	public class OrderField{
		private String fieldName;
		private boolean ASC;
		private int order;
		private String targetFieldName;
		public void setTargetFieldName(String targetFieldName) {
			this.targetFieldName = targetFieldName;
		}
		
		public String getTargetFieldName() {
			return targetFieldName;
		}
		
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		
		public void setASC(boolean aSC) {
			ASC = aSC;
		}
		public boolean isASC() {
			return ASC;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public int getOrder() {
			return order;
		}
	}
	
	private Set<String> aliasSet;
	
	private void AddStringRestriction(DetachedCriteria detachedCriteria,String fieldName, Object fieldValue,ShowFieldCondition showFieldCondition){
		if(fieldValue == null){
			return;
		}
		
		String strFieldValue = fieldValue.toString();
		if(StringUtils.isBlank(strFieldValue) || strFieldValue.equals(ApplicationManager.getEmptySelectValue())){
			return;
		}
		
		if(showFieldCondition.getComparison() == null || showFieldCondition.getComparison().equals(Comparison.NONE) || showFieldCondition.getComparison().equals(Comparison.ANYWHERE)){
			detachedCriteria.add(Restrictions.like(fieldName, strFieldValue, MatchMode.ANYWHERE));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.EQ)){
			detachedCriteria.add(Restrictions.eq(fieldName, strFieldValue));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.START)){
			detachedCriteria.add(Restrictions.like(fieldName, strFieldValue, MatchMode.START));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.END)){
			detachedCriteria.add(Restrictions.like(fieldName, strFieldValue, MatchMode.END));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.EXACT)){
			detachedCriteria.add(Restrictions.like(fieldName, strFieldValue, MatchMode.EXACT));
		}
	}
	
	public void Translate() throws Exception {
		
		aliasSet = new HashSet<String>();

		Object condtionObject = RequestManager.getTOject();
		Class<?> type = Class.forName(RequestManager.getTName());
		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}

		List<ShowFieldCondition> showFieldConditionList = null;
		if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) != null){
			// 解析排序条件
			showFieldConditionList = ReflectOperation.getShowFieldConditionList(type);
			List<OrderField> orderFieldList = new ArrayList<OrderField>();
			for(ShowFieldCondition showFieldCondition : showFieldConditionList){
				if(showFieldCondition.getOrder() > 0){
					OrderField orderField = new OrderField();
					orderField.setFieldName(showFieldCondition.getTarget());
					orderField.setTargetFieldName(showFieldCondition.getTargetField());
					orderField.setASC(showFieldCondition.isASC());
					orderField.setOrder(showFieldCondition.getOrder());
					
					int index = 0;
					int i = 0;
					for(OrderField tempOrderField : orderFieldList){
						if(orderField.getOrder() > tempOrderField.getOrder()){
							index = i + 1;
						}
						else{
							break;
						}
						i++;
					}
					orderFieldList.add(index, orderField);
				}
			}
            for(OrderField orderField : orderFieldList){
    			if(!StringUtils.isBlank(orderField.getTargetFieldName()))
    			{
    				Field f=ReflectOperation.getFieldByName(type, orderField.getFieldName());
    				if(f.isAnnotationPresent(JoinColumn.class))
    				{
    					
    					if(!aliasSet.contains(f.getName()))
    					{
    						detachedCriteria.createCriteria(f.getName(),orderField.getFieldName(),CriteriaSpecification.LEFT_JOIN);
    						aliasSet.add(f.getName());
    					}
    					
    					if(orderField.isASC())
    					{
    						detachedCriteria.addOrder(Order.asc(orderField.getFieldName()+"."+orderField.getTargetFieldName()));
    						
    					}
    					else
    					{
    						detachedCriteria.addOrder(Order.desc(orderField.getFieldName()+"."+orderField.getTargetFieldName()));
    						
    					}
    					
    					continue;
    				}
    			}
    			
    			
				if(orderField.isASC()){
	    			detachedCriteria.addOrder(Order.asc(orderField.getFieldName()));
    			}
    			else{
    				detachedCriteria.addOrder(Order.desc(orderField.getFieldName()));
    			}
    			
    			
    			
    		}
		}

		if(condtionObject != null){
	    	if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) == null){
	    		// 没有找到条件类，根据类自身来设置过滤条件
	    		List<Field> fieldList = ReflectOperation.getColumnFieldList(condtionObject.getClass());
				for(Field field : fieldList){ // 每个属性
					Object value = ReflectOperation.getFieldValue(condtionObject, field.getName());
					if(value != null){ // 直接创建的对象，取到值则进行过滤(基本数据类型均会被默认值过滤!)
						if(value.getClass().equals(String.class)){ // String类型的属性使用like来检索
							if(!StringUtils.isBlank(value.toString()) && !value.toString().equals(ApplicationManager.getEmptySelectValue()) && value.toString().indexOf(",")<0){
								detachedCriteria.add(Restrictions.like(field.getName(), value.toString(), MatchMode.ANYWHERE));
							}
						}
						else{ // 有关联注解，设置关联字段条件检索
							if(field.isAnnotationPresent(JoinColumn.class)){
								Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
								if(primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue()) || primaryKeyValue.toString().indexOf(",")>-1){
									continue;
								}
							}
							detachedCriteria.add(Restrictions.eq(field.getName(), value));
						}
					}
				}
	    	}
	    	else{ // 找到条件类
	    		for(ShowFieldCondition showFieldCondition : showFieldConditionList){ // 每个条件类的属性
	    			
	    			if(ReflectOperation.getReflectField(Class.forName(RequestManager.getTName()), showFieldCondition.getTarget()).isAnnotationPresent(Transient.class)){
	    				continue;
	    			}

	    			Object value = ReflectOperation.getFieldValue(condtionObject, showFieldCondition.getFieldName());
	    			//////////////////////zhouqin 20150527
	    			if(value!=null){
	    				Field[] fields = Class.forName(RequestManager.getTName()).getDeclaredFields();
		    			for(Field f : fields){
		    				if(f.getName().equals(showFieldCondition.getFieldName())){
		    					if(f.getType().equals(Date.class)){
		    						value=TypeParse.parseDate(value.toString());
		    					}
		    				}
		    			}
	    			}
	    			
	    			//////////////////////////////////////
					if(value != null){ // 属性值不为空
						if(value.getClass().equals(String.class)){
							if(ReflectOperation.getReflectField(type,showFieldCondition.getTarget()).getType().equals(Date.class) || ReflectOperation.getReflectField(type,showFieldCondition.getTarget()).getType().equals(Timestamp.class)){
								if(!StringUtils.isBlank(value.toString())){
									//value=TypeParse.parseDate(value.toString())
									//ZK modify 20140630  
									//处理如果是时间类型并且为小于等于时，因取值小于具体的时间，故修改。
									//例如：查询时间为2014-06-01 ~ 2014-06-30之间操作的数据输入该条件后，查询的值只是2014-06-01 ~ 2014-06-29的数据
									if(showFieldCondition.getComparison()!=null){
										if(showFieldCondition.getComparison().equals(Comparison.LT) || showFieldCondition.getComparison().equals(Comparison.LE)){
											SimpleDateFormat simpleFormat=new SimpleDateFormat("yyyy-MM-dd");
											Calendar ca= Calendar.getInstance();
											ca.setTime(TypeParse.parseDate(value.toString()));
											ca.add(Calendar.DATE,1);
											Date date2=new Date();
											date2=ca.getTime();

											value =TypeParse.parseDate(simpleFormat.format(date2));
											//value =TypeParse.parseDate(simpleFormat.format(new Date(TypeParse.parseDate(value.toString()).getTime()+1*24*60*60*1000)));
										}
										else
										{
											value=TypeParse.parseDate(value.toString());
										}
									}
								}
							}
						}

						if(value.getClass().equals(String.class)){ // String类
							value = value.toString().trim();
							if(value.toString().indexOf(",")<0){
								AddStringRestriction(detachedCriteria,showFieldCondition.getTarget(),value,showFieldCondition);
							}
						}
						else if(value.getClass().equals(Integer.class) || value.getClass().equals(Double.class) || value.getClass().equals(BigDecimal.class)// 整数、浮点
								|| value.getClass().equals(Date.class) || value.getClass().equals(Timestamp.class)){// 时间
							AddNoStrBaseTypeRestriction(detachedCriteria,showFieldCondition,showFieldCondition.getTarget(),value);
						}
						else{ // 其它类型
							if(showFieldCondition.getTargetField() == null){ // 无目标字段
								Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
								if(primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue()) || primaryKeyValue.toString().indexOf(",")>-1){
									continue; // 主键为空串时，不作检索条件
								}
								detachedCriteria.add(Restrictions.eq(showFieldCondition.getTarget(), value));
							}
							else{ // 有目标字段,用于关联表
								String targetAlias = showFieldCondition.getTarget();
								if(!aliasSet.contains(targetAlias)){ // 没有添加过，则添加
									detachedCriteria.createAlias(showFieldCondition.getTarget(), targetAlias, CriteriaSpecification.LEFT_JOIN);
									aliasSet.add(targetAlias);
								}
								Object aliasValue = ReflectOperation.getFieldValue(value, showFieldCondition.getTargetField());
								if(aliasValue!=null)
								{
									if(!ReflectOperation.isBaseType(aliasValue))
									{  
										Object pkVal=ReflectOperation.getPrimaryKeyValue(aliasValue);
										if(!ApplicationManager.getEmptySelectValue().equals(pkVal))
										{
											detachedCriteria.add(Restrictions.eq(targetAlias + "." + showFieldCondition.getTargetField(), aliasValue));
										}
										
									}
									else if(aliasValue instanceof Date)
									{
										AddNoStrBaseTypeRestriction(detachedCriteria,showFieldCondition,targetAlias + "." + showFieldCondition.getTargetField(),aliasValue);
									}
									else
									{
										if(aliasValue.toString().indexOf(",")<0){
											AddStringRestriction(detachedCriteria,targetAlias + "." + showFieldCondition.getTargetField(),aliasValue,showFieldCondition);
										}
									}
										
								}
								
							}
						}
					}
	    		}
	    	}

		}

		int currentPage = 0;
		if(RequestManager.getCurrentPage() != null)
			currentPage = RequestManager.getCurrentPage();
		int pageSize = 0;
		if(ShowParamManager.getPageSize() != null)
			pageSize = ShowParamManager.getPageSize();
		ShowParamManager.setFirstResult((currentPage - 1) * pageSize);
		
		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}

	private void AddNoStrBaseTypeRestriction(DetachedCriteria detachedCriteria,
			ShowFieldCondition showFieldCondition,String fieldName, Object value) {
		if(showFieldCondition.getComparison() == null || showFieldCondition.getComparison().equals(Comparison.NONE) || showFieldCondition.getComparison().equals(Comparison.EQ)){
			detachedCriteria.add(Restrictions.eq(fieldName, value));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.GT)){
			detachedCriteria.add(Restrictions.gt(fieldName, value));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.GE)){
			detachedCriteria.add(Restrictions.ge(fieldName, value));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.LT)){
			detachedCriteria.add(Restrictions.lt(fieldName, value));
		}
		else if(showFieldCondition.getComparison().equals(Comparison.LE)){
			detachedCriteria.add(Restrictions.le(fieldName, value));
		}
	}	

	// 自定义表，直接设置SQL语句条件
	@SuppressWarnings("unused")
	private void TableTranslate() {
		//Object condtionObject = RequestManager.getTOject();
		
		String strSQL = "select * from " + RequestManager.getTName();
		LogicParamManager.setSqlQuery(strSQL);
	}
}
