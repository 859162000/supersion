package framework.services.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.ICondition.Comparison;
import framework.services.interfaces.IFileHandlerForText;
import framework.services.interfaces.imps.FileHandlerForText;

public class ExportForViewService extends SingleObjectExportDataService {
 
private String strDataSourceType;
	
	private String strViewName;
	
	private String sessionFactory;
	
	private String strExportDataType;
	
	private String strDataSegType;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {

		if(strDataSourceType==null){
			IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
			String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
			if(dataBaseType!=null && dataBaseType.equals("sqlserver")){
				strDataSourceType = "1";
			}
			else if(dataBaseType!=null && dataBaseType.equals("oracle")){
				strDataSourceType = "2";
			}
			else if(dataBaseType!=null && dataBaseType.equals("db2")){
				strDataSourceType = "3";
			}
		}
		
		if(strViewName==null){
			strViewName=RequestManager.getTName();
			strViewName=strViewName.substring(strViewName.lastIndexOf(".")+1);
		}
		
		
		String strSql="";
		String strConditionSql="";
		Object tObject=RequestManager.getTOject();
		String strSelectField="";
		for (Field field : ReflectOperation.getReflectFields(Class.forName(RequestManager.getTName()))) {
			if(!field.getName().equals("serialVersionUID")){
				strSelectField+=field.getName()+",";
			}
		}
		strSelectField=strSelectField.substring(0, strSelectField.length()-1);
		
		strSql="select "+strSelectField+" from " + strViewName+ "";
		
		for(Field field : ReflectOperation.getReflectFields(tObject.getClass())){
			if(!field.getName().equals("serialVersionUID")){
				Object conditionFieldValue = ReflectOperation.getFieldValue(tObject, field.getName());
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
							if(strDataSourceType.equals("1") || strDataSourceType.equals("3")){
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
		
		if(!strConditionSql.equals("")){
			strConditionSql = strConditionSql.substring(0,strConditionSql.length() - 4);
			strSql = strSql + " WHERE " + strConditionSql;
		}
		
		super.initDao();
		
		List<Map<String,String>> objectSList= (List<Map<String,String>>)this.getBaseObjectDao().paramObjectResultExecute(new Object[]{strSql,sessionFactory});
		List<Object> objectDList=new ArrayList<Object>();
		List<Field> fieldList = new ArrayList<Field>();
		int i=0;
		for (Map<String,String> objectMap : objectSList) {
			Object objectD=Class.forName(RequestManager.getTName()).newInstance();
			for (Map.Entry<String,String> entry : objectMap.entrySet()) {
				Field field=null;
				for (Field fieldTemp : ReflectOperation.getReflectFields(objectD.getClass())) {
					if(fieldTemp.getName().toUpperCase().equals(entry.getKey().toUpperCase())){
						field=fieldTemp;
						break;
					}
				}
				if(i == 0 && field != null){
					fieldList.add(field);
				}
				if(entry.getValue() != null){
					if(ReflectOperation.isBaseType(ReflectOperation.getFieldByName(objectD.getClass(), field.getName()).getType())){
						ReflectOperation.setFieldValue(objectD, field.getName(), entry.getValue());
					}else{
						Object foreignObject=ReflectOperation.getFieldByName(objectD.getClass(), field.getName()).getType().newInstance();
						ReflectOperation.setFieldValue(foreignObject, ReflectOperation.getPrimaryKeyField(foreignObject.getClass()).getName(), entry.getValue());
						ReflectOperation.setFieldValue(objectD,field.getName(),foreignObject);
					}
					
				}
			}
			objectDList.add(objectD);
			i++;
		}
		
		if(StringUtils.isBlank(strExportDataType) || "1".equals(strExportDataType)){
			this.setServiceResult(objectDList);
			ExportData();
		}
		else if("2".equals(strExportDataType)){
			IFileHandlerForText fileHandlerForText = new FileHandlerForText();
			this.setServiceResult(fileHandlerForText.GetDownloadResultFromCache(objectDList, fieldList, this.getFileName(), strDataSegType, null, this.getStrDateFormat()));
		}
	}
	
	
	public String getStrDataSourceType() {
		return strDataSourceType;
	}

	public void setStrDataSourceType(String strDataSourceType) {
		this.strDataSourceType = strDataSourceType;
	}

	public String getStrViewName() {
		return strViewName;
	}

	public void setStrViewName(String strViewName) {
		this.strViewName = strViewName;
	}

	public String getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(String sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setStrExportDataType(String strExportDataType) {
		this.strExportDataType = strExportDataType;
	}

	public String getStrExportDataType() {
		return strExportDataType;
	}

	public void setStrDataSegType(String strDataSegType) {
		this.strDataSegType = strDataSegType;
	}

	public String getStrDataSegType() {
		return strDataSegType;
	}

}
