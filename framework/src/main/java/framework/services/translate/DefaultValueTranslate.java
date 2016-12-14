package framework.services.translate;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.TypeParse;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

// 用默认值对查询数据进行过滤
public class DefaultValueTranslate extends BaseConstructor implements ITranslate{
	
	public DefaultValueTranslate(){
		super();
	}
	
	public DefaultValueTranslate(Object baseObject){
		super(baseObject);
	}
	

	private Object getObjValue(String value){
		if(value.equals("SystemDate")){
			return new Date();
		}
		else{
			return value;
		}
	}
	
	public void Translate() throws Exception {

		if(LogicParamManager.getDefaultValueMap() != null){
			Object tObject = this.getBaseObject();

			DetachedCriteria detachedCriteria = null;
			if(LogicParamManager.getDetachedCriteria() == null){
				detachedCriteria = DetachedCriteria.forClass(tObject.getClass());
			}
			else{
				detachedCriteria = LogicParamManager.getDetachedCriteria();
			}

			for(Map.Entry<String, String> defaultValue : LogicParamManager.getDefaultValueMap().entrySet()){
				String value = defaultValue.getValue();
				Object objValue;
				
				if(value.indexOf(",") > -1){
					String[] values = value.split(",");
					detachedCriteria.add(Restrictions.in(defaultValue.getKey(), values));
				}
				else if(value.startsWith("LessOrEqual")){
					value = value.substring("LessOrEqual".length());
					objValue = getObjValue(value);
					detachedCriteria.add(Restrictions.le(defaultValue.getKey(), objValue));
				}
				else if(value.startsWith("GreaterOrEqual")){
					value = value.substring("GreaterOrEqual".length());
					objValue = getObjValue(value);
					detachedCriteria.add(Restrictions.ge(defaultValue.getKey(), objValue));
				}
                else if(value.startsWith("Less")){
                	value = value.substring("Less".length());
                	objValue = getObjValue(value);
					detachedCriteria.add(Restrictions.lt(defaultValue.getKey(), objValue));
				}
                else if(value.startsWith("Greater")){
                	value = value.substring("Greater".length());
                	
                	if(value.startsWith("-")) { // 过滤为大于目前日期减n天到现在的数据
                		value = value.substring("-".length());
                		Integer days = TypeParse.parseInt(value.toString());
                		Calendar calendar = Calendar.getInstance();
                	    calendar.setTime(new Date());
                	    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - days);
                		objValue = calendar.getTime();
                		
                		String tmp[] = defaultValue.getKey().toString().split("[.]");
                		String target = tmp[0];
                		//String field = tmp[1];
                		
                		detachedCriteria.createAlias(target, target, CriteriaSpecification.LEFT_JOIN);
                		detachedCriteria.add(Restrictions.gt(defaultValue.getKey(), objValue));
                	}
                	else {
	                	objValue = getObjValue(value);
	                	detachedCriteria.add(Restrictions.gt(defaultValue.getKey(), objValue));
                	}
				}
                else{
                	detachedCriteria.add(Restrictions.eq(defaultValue.getKey(), value));
                }
			}
			
			LogicParamManager.setDetachedCriteria(detachedCriteria);
		}
	}
}


