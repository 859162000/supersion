package framework.services.translate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldCondition;

/**
 * 组装下拉框多选字段的查询条件
 * 
 */
public class ConditionMultipleCriteriaTranslate implements ITranslate{

	public void Translate() throws Exception {
		
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
			showFieldConditionList = ReflectOperation.getShowFieldConditionList(type);
		}

		if(condtionObject != null){
	    	if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) == null){
	    		// 没有找到条件类，根据类自身来设置过滤条件
	    		List<Field> fieldList = ReflectOperation.getColumnFieldList(condtionObject.getClass());
				for(Field field : fieldList){ // 每个属性
					Object value = ReflectOperation.getFieldValue(condtionObject, field.getName());
					if(value != null){ // 直接创建的对象，取到值则进行过滤(基本数据类型均会被默认值过滤!)
						if(value.getClass().equals(String.class)){ // String类型的属性使用like来检索
							if(!StringUtils.isBlank(value.toString()) && !value.toString().equals(ApplicationManager.getEmptySelectValue()) && value.toString().indexOf(",")>-1){
								String[] values = value.toString().replaceAll(" ", "").split(",");
								detachedCriteria.add(Restrictions.in(field.getName(), values));
							}
						}
						else{ // 有关联注解，设置关联字段条件检索
							if(field.isAnnotationPresent(JoinColumn.class)){
								Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
								if(primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue())){
									continue;
								}
								if(primaryKeyValue.toString().indexOf(",")>-1){
									List<Object> values = new ArrayList<Object>();
									String[] strByte = primaryKeyValue.toString().replaceAll(" ", "").split(",");
									for (int i = 0; i < strByte.length; i++) {
										Object tmpValue = Class.forName(value.getClass().getName()).newInstance();
										String PrimaryKeyName = ReflectOperation.getPrimaryKeyField(tmpValue.getClass()).getName();
										ReflectOperation.setFieldValue(tmpValue, PrimaryKeyName, strByte[i]);
										values.add(tmpValue);
									}
									detachedCriteria.add(Restrictions.in(field.getName(), values));
								}
							}
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

					if(value != null){ // 属性值不为空

						if(value.getClass().equals(String.class)){ // String类
							if(value.toString().indexOf(",")>-1){
								String[] values = value.toString().replaceAll(" ", "").split(",");
								detachedCriteria.add(Restrictions.in(showFieldCondition.getTarget(), values));
							}
						}
						else if(value.getClass().equals(Integer.class) || value.getClass().equals(Double.class) || value.getClass().equals(BigDecimal.class)// 整数、浮点
								|| value.getClass().equals(Date.class) || value.getClass().equals(Timestamp.class)){// 时间
							continue;
						}
						else{ // 其它类型
							if(showFieldCondition.getTargetField() == null){ // 无目标字段
								Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
								if(primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue())){
									continue; // 主键为空串时，不作检索条件
								}
								if(primaryKeyValue.toString().indexOf(",")>-1){
									List<Object> values = new ArrayList<Object>();
									String[] strByte = primaryKeyValue.toString().replaceAll(" ", "").split(",");
									for (int i = 0; i < strByte.length; i++) {
										Object tmpValue = Class.forName(value.getClass().getName()).newInstance();
										String PrimaryKeyName = ReflectOperation.getPrimaryKeyField(tmpValue.getClass()).getName();
										ReflectOperation.setFieldValue(tmpValue, PrimaryKeyName, strByte[i]);
										values.add(tmpValue);
									}
									detachedCriteria.add(Restrictions.in(showFieldCondition.getTarget(), values));
									/*String PrimaryKeyName = ReflectOperation.getPrimaryKeyField(value.getClass()).getName();
									ReflectOperation.setFieldValue(value, PrimaryKeyName, "[CNY, DKK, CAD]");*/
								}
							}
							else{ // 有目标字段,用于关联表
								String targetAlias = showFieldCondition.getTarget();
								Object aliasValue = ReflectOperation.getFieldValue(value, showFieldCondition.getTargetField());
								if(aliasValue!=null &&aliasValue.toString().indexOf(",")>-1){
									String[] values = aliasValue.toString().replaceAll(" ", "").split(",");
									detachedCriteria.add(Restrictions.in(targetAlias + "." + showFieldCondition.getTargetField(), values));
								}
							}
						}
					}
	    		}
	    	}

		}

		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}	
}
