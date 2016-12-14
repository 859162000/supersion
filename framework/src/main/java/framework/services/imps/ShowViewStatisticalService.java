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
import framework.interfaces.SessionManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;



public class ShowViewStatisticalService extends SingleObjectShowListService {
	
	protected String strDataSourceType;
	
	protected String strViewName;
	
	private String sessionFactory;
	
	@Override
	public void initSuccessResult() throws Exception {
		super.initDao();
		
		if(RequestManager.isFirstShowData() != null && RequestManager.isFirstShowData()){
			this.setServiceResult(new ArrayList<Object>());
			return;
		}
		
		if(strViewName==null){
			strViewName=RequestManager.getTName();
			strViewName=strViewName.substring(strViewName.lastIndexOf(".")+1);
		}
		
		setDataSourceType(); // 如果配置未配置数据库参数值，则动态设置数据库类型
		String conditionSql = getCondition(); // 拼接查询条件
		int totalCount = getTotalCount(conditionSql); // 根据条件统计条数
		setCurrentPage(totalCount); // 如果getFirstResult大于总条数，则初始
		List<Map<String,String>> objectSList= getDataList(conditionSql); // 根据条件查询数据
		List<Object> objectDList= getObjectList(objectSList); // 将查询出的数据列表，设置到视图对象列表
		
		this.setServiceResult(objectDList);
		
		LogicParamManager.setTotalCount(totalCount);
	}
	
	protected void setDataSourceType() throws Exception{
		
		if(strDataSourceType==null){
			
			IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
			String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
			
			if(dataBaseType!=null){
				if(dataBaseType.equals("sqlserver")){
					strDataSourceType = "sqlserver";
				}
				else if(dataBaseType.equals("oracle")){
					strDataSourceType = "oracle";
				}
				else if(dataBaseType.equals("db2")){
					strDataSourceType = "db2";
				}else if(dataBaseType.equals("mySql")){
					strDataSourceType = "mySql";
				}
				
			}
		}
	}
	
	protected void setCurrentPage(int totalCount){
		if(ShowParamManager.getFirstResult()>totalCount){
			SessionManager.setCurrentPage(1, RequestManager.getTName());
			RequestManager.setCurrentPage(1);
			ShowParamManager.setFirstResult(0);
		}
	}
	protected List<Map<String,String>> getDataList(String conditionSql) throws Exception{

		String execSql = "";
		String strSelectField="";
		int PageSize = ShowParamManager.getPageSize();
		String primaryKeyName=ReflectOperation.getPrimaryKeyField(Class.forName(RequestManager.getTName())).getName();
		
		for (Field field : ReflectOperation.getReflectFields(Class.forName(RequestManager.getTName()))) {
			if(!field.getName().equals("serialVersionUID")){
				strSelectField+=field.getName()+",";
			}
		}
		
		strSelectField = strSelectField.substring(0, strSelectField.length()-1);
		
		if(this.strDataSourceType.equals("sqlserver")){
			execSql="select "+strSelectField+" from ( SELECT ROW_NUMBER() OVER (ORDER BY " + primaryKeyName + " ASC) AS 'ROWNUMBER', * FROM " + strViewName + conditionSql + ") as View_AutoDTO_DB_DBXX_JC where ROWNUMBER between "+ (ShowParamManager.getFirstResult()+1) +" and "+ (ShowParamManager.getFirstResult()+PageSize) +"";
		}else if(this.strDataSourceType.equals("oracle")){
			execSql="SELECT "+strSelectField+" FROM ( SELECT A.*,ROWNUM ROWNUMBER FROM (SELECT "+strSelectField+" FROM "+strViewName + conditionSql + ") A ) WHERE ROWNUMBER BETWEEN "+(ShowParamManager.getFirstResult()+1) +" AND "+(ShowParamManager.getFirstResult()+PageSize)+"";
		}
		else if(this.strDataSourceType.equals("db2")){
			execSql="select "+strSelectField+" from (select rownumber() over(order by "+primaryKeyName+") as ROWNUMBER,"+strSelectField +"  from " + strViewName + conditionSql + " )as a where a.ROWNUMBER between "+ (ShowParamManager.getFirstResult()+1) +" and "+ (ShowParamManager.getFirstResult()+PageSize) +"";
		}else if(this.strDataSourceType.equals("mySql")){
			execSql="SELECT "+strSelectField+" FROM "+strViewName + conditionSql+" LIMIT "+ShowParamManager.getFirstResult()+","+(ShowParamManager.getFirstResult()+PageSize);
			//execSql="SELECT "+strSelectField+" FROM ( SELECT A.*,FOUND_ROWS() ROWNUMBER FROM (SELECT "+strSelectField+" FROM "+strViewName + conditionSql + ") A ) B WHERE ROWNUMBER BETWEEN "+(ShowParamManager.getFirstResult()+1) +" AND "+(ShowParamManager.getFirstResult()+PageSize)+"";
		}

		@SuppressWarnings("unchecked")
		List<Map<String,String>> objectSList= (List<Map<String,String>>)this.getBaseObjectDao().paramObjectResultExecute(new Object[]{execSql,sessionFactory});
		
		return objectSList;
	}
	
	protected String getCondition() throws Exception{

		String strConditionSql = " where ";
		
		Object tObject=RequestManager.getTOject();
		if(tObject == null){
			tObject = FrameworkFactory.CreateClass(RequestManager.getTName());
		}
		
		for(Field field : ReflectOperation.getReflectFields(tObject.getClass())){
			if(!field.getName().equals("serialVersionUID")){
				Object conditionFieldValue = ReflectOperation.getFieldValue(tObject, field.getName());
				if(conditionFieldValue!=null && conditionFieldValue.getClass().equals(String.class)){
					conditionFieldValue=conditionFieldValue.toString().trim();
				}
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
							if(strDataSourceType.equals("sqlserver") || strDataSourceType.equals("db2") || strDataSourceType.equals("mySql")){
								strValue = TypeParse.parseString((Date)conditionFieldValue, "yyyy-MM-dd");
							}
							else if(strDataSourceType.equals("oracle")){
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

		strConditionSql += getFixedFieldConditionSql();
		
		return strConditionSql;
	}
	
	@SuppressWarnings("unchecked")
	protected int getTotalCount(String conditionSql) throws Exception{
		
		String strTotalCountSql="select count(*) as TOTALCOUNT from "+strViewName+conditionSql;
		List<Map<String,String>> objectCountSqlList = (List<Map<String,String>>)this.getBaseObjectDao().paramObjectResultExecute(new Object[]{strTotalCountSql,sessionFactory});

		Object strTotalCount = objectCountSqlList.get(0).get("TOTALCOUNT");
		int totalCount = Integer.parseInt(strTotalCount.toString());
		
		return totalCount;
	}
	
	protected List<Object> getObjectList(List<Map<String,String>> objectSList) throws Exception{

		List<Object> objectDList=new ArrayList<Object>();
		for (Map<String,String> objectMap : objectSList) {
			Object objectD=Class.forName(RequestManager.getTName()).newInstance();
			for (Map.Entry<String,String> entry : objectMap.entrySet()) {
				if(entry.getValue() != null){
					Field field=null;
					for (Field fieldTemp : ReflectOperation.getReflectFields(objectD.getClass())) {
						if(fieldTemp.getName().toUpperCase().equals(entry.getKey().toUpperCase())){
							field=fieldTemp;
							break;
						}
					}
					
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
		}
		return objectDList;
	}
	
	protected String getFixedFieldConditionSql(){
		return "";
	}

	public void setStrViewName(String strViewName) {
		this.strViewName = strViewName;
	}

	public String getStrViewName() {
		return strViewName;
	}

	public void setSessionFactory(String sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getSessionFactory() {
		return sessionFactory;
	}

	public void setStrDataSourceType(String strDataSourceType) {
		this.strDataSourceType = strDataSourceType;
	}

	public String getStrDataSourceType() {
		return strDataSourceType;
	}
}
