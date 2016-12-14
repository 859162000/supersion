package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.ICondition.Comparison;
import framework.services.imps.BaseService;

public class JKRGKPositionService extends BaseService{

	private String strViewName;
	
	@Override
	public void initSuccessResult() throws Exception {

		String strDataSourceType;
		
 		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
		if(dataBaseType!=null && dataBaseType.equals("sqlserver")){
			strDataSourceType = "1";
		}
		else if(dataBaseType!=null && dataBaseType.equals("db2")){
			strDataSourceType = "3";
		}
		else if(dataBaseType!=null && dataBaseType.equals("mySql")){
			strDataSourceType = "4";
		}
		else{
			strDataSourceType = "2";
		}
		
		Object object = RequestManager.getTOject();
		Field tmpField = ReflectOperation.getPrimaryKeyField(object.getClass());
		ReflectOperation.setFieldValue(object, tmpField.getName(), RequestManager.getLevelIdValue());
		
		
		String strSql="";
		String strConditionSql="";
		String strSelectField="";
		for (Field field : ReflectOperation.getReflectFields(object.getClass())) {
			if(!field.getName().equals("serialVersionUID")){
				strSelectField+=field.getName()+",";
			}
		}
		strSelectField=strSelectField.substring(0, strSelectField.length()-1);
		String primaryKeyName=ReflectOperation.getPrimaryKeyField(Class.forName(RequestManager.getTName())).getName();
		if(strDataSourceType.equals("1")){
			strSql="select "+strSelectField+" from ( SELECT ROW_NUMBER() OVER (ORDER BY " + primaryKeyName + " ASC) AS 'ROWNUMBER', * FROM " + strViewName+ ") as View_AutoDTO_DB_DBXX_JC where ";
		}else if(strDataSourceType.equals("2")){
			strSql="SELECT "+strSelectField+" FROM ( SELECT A.*,ROWNUM ROWNUMBER FROM (SELECT "+strSelectField+" FROM "+strViewName+") A ) WHERE ";
		}
		else if(strDataSourceType.equals("3")){
			strSql="select "+strSelectField+" from (select rownumber() over(order by "+primaryKeyName+") as ROWNUMBER,"+strSelectField +"  from "+strViewName+" )as a where ";
		}
		if(strDataSourceType.equals("4")){
			strSql="SELECT "+strSelectField+" FROM ( SELECT A.*,FOUND_ROWS() ROWNUMBER FROM (SELECT "+strSelectField+" FROM "+strViewName +") A ) B WHERE ";
		}
		
		
		for(Field field : ReflectOperation.getReflectFields(object.getClass())){
			if(!field.getName().equals("serialVersionUID")){
				Object conditionFieldValue = ReflectOperation.getFieldValue(object, field.getName());
				if(ReflectOperation.isBaseType(field.getType())){
					if(conditionFieldValue != null && !conditionFieldValue.toString().equals("")){
						
						String fieldName = field.getName();
						String strCompareType=" = ";				
						String strValue = "";
						if(conditionFieldValue.getClass().equals(String.class)){
							strCompareType = " like ";
						}
								
						if(field.isAnnotationPresent(ICondition.class)){
							ICondition condition = field.getAnnotation(ICondition.class);
							if(!StringUtils.isBlank(condition.target())){
								fieldName = condition.target();
							}
							if(condition.comparison().equals(Comparison.GE)){
								strCompareType = " >= ";
							}
							else if(condition.comparison().equals(Comparison.GT)){
								strCompareType = " > ";
							}
							else if(condition.comparison().equals(Comparison.LE)){
								strCompareType = " <= ";
							}
							else if(condition.comparison().equals(Comparison.LT)){
								strCompareType = " < ";
							}
						}

						if(conditionFieldValue.getClass().equals(Date.class) || conditionFieldValue.getClass().equals(java.sql.Date.class)){
							if(strDataSourceType.equals("1") || strDataSourceType.equals("3") || strDataSourceType.equals("4")){
								strValue = TypeParse.parseString((Date)conditionFieldValue, "yyyy-MM-dd");
							}
							else if(strDataSourceType.equals("2")){
								strValue = "to_date('" +TypeParse.parseString((Date)conditionFieldValue, "yyyy-MM-dd") + "','yyyy-MM-dd')";
							}
						}
						else{
							strValue = conditionFieldValue.toString();
						}
						
						if(strCompareType.equals(" like ")){
							strConditionSql += fieldName + strCompareType + "'%" + strValue + "%' and ";
						}
						else{
							strConditionSql += fieldName + strCompareType + "'" + strValue + "' and ";
						}
					}
				}
				else{
					if(conditionFieldValue != null){
						String primaryKeyValue = ReflectOperation.getPrimaryKeyValue(conditionFieldValue).toString();
						if(!StringUtils.isBlank(primaryKeyValue)){
							strConditionSql += field.getName() + "=" + "'" + primaryKeyValue + "' and ";
						}
					}
				}
			}
		}

		Object instInfoCode = ((Map<String, Object>)ServletActionContext.getContext().getSession()).get("ZXTBShowViewStatistical-"+RequestManager.getTName()+"instinfo");
		strConditionSql += "INSTINFO = '"+instInfoCode+"' and ";
		
		if(!strConditionSql.equals("")){
			strConditionSql = strConditionSql.substring(0,strConditionSql.length() - 4);
			strSql = strSql + strConditionSql;
		}else{
			strSql = strSql.substring(0,strSql.length() - 6);
		}
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryListMapDao");
		@SuppressWarnings("unchecked")
		List<Map<String,String>> objectSList= (List<Map<String,String>>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{strSql, null});
		for (Map<String,String> objectMap : objectSList) {
			for (Map.Entry<String,String> entry : objectMap.entrySet()) {
				if(entry.getValue() != null){
					Field field=null;
					for (Field fieldTemp : ReflectOperation.getReflectFields(object.getClass())) {
						if(fieldTemp.getName().toUpperCase().equals(entry.getKey().toUpperCase())){
							field=fieldTemp;
							break;
						}
					}
					
					
					if(ReflectOperation.isBaseType(ReflectOperation.getFieldByName(object.getClass(), field.getName()).getType())){
						ReflectOperation.setFieldValue(object, field.getName(), entry.getValue());
					}else{
						Object foreignObject=ReflectOperation.getFieldByName(object.getClass(), field.getName()).getType().newInstance();
						ReflectOperation.setFieldValue(foreignObject, ReflectOperation.getPrimaryKeyField(foreignObject.getClass()).getName(), entry.getValue());
						ReflectOperation.setFieldValue(object,field.getName(),foreignObject);
					}
					
				}
			}
			break;
		}
		
		
		List<Field> fieldList= ReflectOperation.getColumnFieldList(object.getClass());
		for (int i = 0; i < fieldList.size(); i++) {
			String fieldName = fieldList.get(i).getName();
			Object fieldValue = ReflectOperation.getFieldValue(object, fieldName);
			ServletActionContext.getContext().getSession().put(object.getClass().getName()+"."+fieldName, fieldValue);
		}
		ServletActionContext.getContext().getSession().put(object.getClass().getName()+".JKRGKPosition", "true");
	}
	
	public String getStrViewName() {
		return strViewName;
	}

	public void setStrViewName(String strViewName) {
		this.strViewName = strViewName;
	}
}
