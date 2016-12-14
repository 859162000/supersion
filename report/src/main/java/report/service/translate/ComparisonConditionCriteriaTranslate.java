package report.service.translate;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.interfaces.ICondition.Comparison;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldCondition;
/*
 * 针对Comparison条件不起作用的，即可加上此类
 */
public class ComparisonConditionCriteriaTranslate implements ITranslate{
	
	private void AddStringRestriction(DetachedCriteria detachedCriteria,String fieldName, Object fieldValue,ShowFieldCondition showFieldCondition){
		if(fieldValue == null){
			return;
		}
		
		String strFieldValue = fieldValue.toString();
		if(StringUtils.isBlank(strFieldValue) || strFieldValue.equals(ApplicationManager.getEmptySelectValue())){
			return;
		}
		if(showFieldCondition.getComparison()!=null){
			if(showFieldCondition.getComparison().equals(Comparison.GT)){
				detachedCriteria.add(Restrictions.gt(showFieldCondition.getTarget(), fieldValue));
			}
			else if(showFieldCondition.getComparison().equals(Comparison.GE)){
				detachedCriteria.add(Restrictions.ge(showFieldCondition.getTarget(), fieldValue));
			}
			else if(showFieldCondition.getComparison().equals(Comparison.LT)){
				detachedCriteria.add(Restrictions.lt(showFieldCondition.getTarget(), fieldValue));
			}
			else if(showFieldCondition.getComparison().equals(Comparison.LE)){
				detachedCriteria.add(Restrictions.le(showFieldCondition.getTarget(), fieldValue));
			}
		}
	}
	
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

		if(condtionObject != null){
	    	if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) != null){
	    		for(ShowFieldCondition showFieldCondition : ReflectOperation.getShowFieldConditionList(type)){ // 每个条件类的属性
	    			Object value = ReflectOperation.getFieldValue(condtionObject, showFieldCondition.getFieldName());
					if(value != null){ // 属性值不为空
						if(value.getClass().equals(String.class)){ // String类
							AddStringRestriction(detachedCriteria,showFieldCondition.getTarget(),value,showFieldCondition);
						}
					}
	    		}
	    	}
		}
		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}	
}
