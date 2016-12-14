package coresystem.service.imps;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import extend.dto.AutoETL_View;
import extend.dto.AutoETL_ViewField;

import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;


// 主要功能：读取指定表的结构，ReportModel_Field记录
public class ImportTableStructService extends BaseVoidDaoResultService{

	String getSQL(String DBType, String tableName, String strTablePrefix) {
		String strSQL = "";
		if(DBType.equals("1")) {//SQL Server
			strSQL = " select syscolumns.name as FNAME, upper(systypes.name) as FTYPE," +
			" syscolumns.length as FLENGTH, syscolumns.isnullable as FNULLABLE," +
			" syscolumns.xprec as FPREC, syscolumns.xscale as FSCALE, syscolumns.colorder as FSEQ,"+
			" case when exists(SELECT COLUMN_NAME  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME='" + strTablePrefix + tableName+"' and COLUMN_NAME=syscolumns.name) then 'P' else 'N' end as FPRIMARY"
			+ " from syscolumns," +
			" systypes WHERE syscolumns.xusertype = systypes.xusertype " +
			" and syscolumns.id = object_id('" + strTablePrefix + tableName + "');";
		}
		else if(DBType.equals("2")) {//ORACLE
			strSQL = "select COLUMN_NAME FNAME, DATA_TYPE FTYPE, DATA_LENGTH FLENGTH, " +
			" DATA_PRECISION FPREC, DATA_SCALE FSCALE, CASE NULLABLE WHEN 'Y' THEN 'N' WHEN 'N' THEN 'Y' END FNULLABLE, COLUMN_ID FSEQ, " +
			" (case when exists(select * from user_cons_columns a, user_constraints b " +
			" where a.constraint_name = b.constraint_name and a.table_name='" + strTablePrefix + tableName + "'" +
			" and a.column_name = user_tab_columns.COLUMN_NAME and constraint_type = 'P') then  'P' " +
			" else 'N' END) FPRIMARY " +
			" from user_tab_columns where table_name='" + strTablePrefix + tableName + "'";
		}
		else if(DBType.equals("3")){//DB2
			strSQL="SELECT COLNAME AS FNAME,TYPENAME AS FTYPE,LENGTH AS FLENGTH,CASE WHEN NULLS='N' THEN 0 ELSE 1 END AS FNULLABLE,"
				+"'' AS FPREC,SCALE AS FSCALE,COLNO AS FSEQ,CASE WHEN KEYSEQ IS NOT NULL THEN 'P' ELSE 'N' END AS FPRIMARY "
				+"FROM syscat.COLUMNS WHERE upper(tabname)='"+ tableName.toUpperCase()+"' ORDER BY COLNO";
		}
		else {//mysql
			//strSQL+="SET @cDataBase = DATABASE(); ";
			strSQL+="SELECT column_name AS FNAME,data_type AS FTYPE,IFNULL(character_maximum_length,0) AS FLENGTH,"
				+"CASE is_nullable WHEN 'NO' THEN 0 WHEN 'YES' THEN 1 END AS FNULLABLE"
				+",IFNULL(numeric_precision,0) AS FPREC,IFNULL(numeric_scale,0) AS FSCALE,ordinal_position AS FSEQ"
				+",CASE column_key WHEN 'PRI' THEN 'P' WHEN 'MUL' THEN 'P' ELSE 'N' END AS FPRIMARY FROM information_schema.COLUMNS "
				+"WHERE table_name = '"+strTablePrefix + tableName+"' AND table_schema = '@cDataBase' ORDER BY ordinal_position;";
		}		
		return strSQL;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() {
		// 当前TName为ReportModel_Table
		if(RequestManager.getTName().equals("extend.dto.ReportModel_Table")) {
			// 创建DAO
			String beanID = "singleObjectFindByIdDao";
			IParamObjectResultExecute SingleObjectFindByIdDao =
			(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
			// 调用DAO查询到tObject对象(表)
			Object tObject = null;
			try {
				Object oldId = RequestManager.getId();
				RequestManager.setId(RequestManager.getLevelIdValue());
				tObject = SingleObjectFindByIdDao.paramObjectResultExecute(null);
				RequestManager.setId(oldId);
			} catch (Exception e) {
				setServiceResult(new MessageResult(false, "生成表字段失败！"));
				e.printStackTrace();
			}
			
			if (tObject == null) { // 找不到对象
				setServiceResult(new MessageResult(false, "生成表字段失败！"));
				return;
			}
			
			ReportModel_Table table = (ReportModel_Table)tObject;
			
			if(table.getDataSource() == null) {
				setServiceResult(new MessageResult(false, "没有数据源,不能同步！"));
				return;
			}

			String strSql = "";
			String strPrefix = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("tablePrefix");
			if(strPrefix == null){
				strPrefix = "";
			}
			if(table.getStrAutoDTO() != null && table.getStrAutoDTO().equals("1")){ // 有前缀
				strSql = getSQL(table.getDataSource().getStrDatabaseType(), table.getStrTableName(), strPrefix);
			}
			else{
				strSql = getSQL(table.getDataSource().getStrDatabaseType(), table.getStrTableName(), "");
			}
			
			// 执行sql取出字段属性信息
			beanID = "createSqlQueryResultSetDao"; //createSqlQueryListMapDao
			IParamObjectResultExecute dao =
			(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
			// 调用DAO查询到tObject对象(表)
			ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				ResultSet resultSet;
				if(table.getDataSource().getStrDatabaseType().equals("4")){
					//mysql
					resultSet = (ResultSet)dao.paramObjectResultExecute(new Object[]{"select DATABASE();", table.getDataSource().getSessionFactory()});
					String dbName ="";
					while(resultSet.next()){
						dbName=resultSet.getString(1);
					}
					strSql=strSql.replace("@cDataBase", dbName);
				}
				resultSet = (ResultSet)dao.paramObjectResultExecute(new Object[]{strSql, table.getDataSource().getSessionFactory()});
				if(resultSet != null) {
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int fieldCount = resultSetMetaData.getColumnCount();  
					while(resultSet.next()){
						Map<String,Object> valueMap = new LinkedHashMap();  
			            for (int i = 1; i <= fieldCount; i++) {  
			                String fieldName = resultSetMetaData.getColumnName(i); 
			                if(table.getDataSource().getStrDatabaseType().equals("4")){
			                	fieldName = resultSetMetaData.getColumnLabel(i); 
			                }
			                Object object = resultSet.getObject(fieldName);
			                valueMap.put(fieldName, object);
			            }  
			            list.add(valueMap);
					}
					resultSet.close();
				}
			} catch (Exception e) {
				setServiceResult(new MessageResult(false, "生成表字段失败！"));
				e.printStackTrace();
			}

			if (list == null || list.size()==0) { // 找不到字段
				setServiceResult(new MessageResult(false, "未找到表字段信息！"));
				return;
			}

			// 生成ReportModel_Field
			for(Map<String, Object> map : list)
			{
				// 检查是否有同名字段
				boolean fieldExsit = false;
				for(ReportModel_Field field : table.getReportModel_Fields()) {
					if(field.getStrFieldName().toUpperCase().equals(map.get("FNAME").toString().toUpperCase())) {
						fieldExsit = true;
						break;
					}
				}
				
				if(fieldExsit) continue;
				
				// 添加字段
				ReportModel_Field fieldDate = new ReportModel_Field();
				fieldDate.setIsEnable("1");
				if(map.get("FPRIMARY").toString().equals("P")) {
					fieldDate.setIsKey("1");
				}
				else fieldDate.setIsKey("2");
				if(map.get("FSEQ") != null)
					fieldDate.setNSeq(map.get("FSEQ").toString());
				else
					fieldDate.setNSeq("0");
				fieldDate.setStrDiscription("");
				
				if(map.get("FNULLABLE").equals("Y") || map.get("FNULLABLE").toString().equals("0")) {
					fieldDate.setStrEmptyType("2");
				}
				else {
					fieldDate.setStrEmptyType("1");
				}
				
				fieldDate.setStrLogicEmptyType(fieldDate.getStrEmptyType());
				
				if(map.get("FNAME").toString().equals("INSTINFO"))
					fieldDate.setStrFieldName("instInfo");
				else if(map.get("FNAME").toString().equals("DTDATE"))
					fieldDate.setStrFieldName("dtDate");
				else
					fieldDate.setStrFieldName(map.get("FNAME").toString());
				
				fieldDate.setStrChinaName(fieldDate.getStrFieldName());
				
				Map<String, String> typeMap;
				if(table.getDataSource().getStrDatabaseType().equals("2")) {// Oracle
					typeMap = ShowContext.getInstance().getShowEntityMap().get("fieldTypeOracle");
				}
				else if(table.getDataSource().getStrDatabaseType().equals("3")){//DB2
					typeMap = ShowContext.getInstance().getShowEntityMap().get("fieldTypeDB2");
				}
				else if(table.getDataSource().getStrDatabaseType().equals("1")){ // SQL
					typeMap = ShowContext.getInstance().getShowEntityMap().get("fieldTypeMSSql");
				}
				else if(table.getDataSource().getStrDatabaseType().equals("4")){ // MySQL
					typeMap = ShowContext.getInstance().getShowEntityMap().get("fieldTypeMySql");
				}
				else {
					typeMap = ShowContext.getInstance().getShowEntityMap().get("fieldTypeMSSql");
				}
				for(Map.Entry<String, String> entry : typeMap.entrySet()) {
					if(entry.getValue().toUpperCase().equals(map.get("FTYPE").toString().toUpperCase())) {
						fieldDate.setStrFieldType(entry.getKey());
						break;
					}
				}
				// 在entity中不存在对应类型，通过判断转换成对应类型     1 char ,2 varchar2, 3 Date, 4 integer, 5 DECIMAL
				if(map.get("FTYPE").equals("NCHAR")){
					fieldDate.setStrFieldType("1");
				}
				else if(map.get("FTYPE").equals("NVARCHAR2") || map.get("FTYPE").equals("NVARCHAR") || map.get("FTYPE").equals("VARCHAR")){
					fieldDate.setStrFieldType("2");
				}
				else if(map.get("FTYPE")!=null && map.get("FTYPE").toString().startsWith("TIMESTAMP") || map.get("FTYPE").equals("DATETIME") 
						|| map.get("FTYPE").equals("SMALLDATETIME") || map.get("FTYPE").equals("DATE")){
					fieldDate.setStrFieldType("3");
				}
				else if(map.get("FTYPE").equals("INT") || map.get("FTYPE").equals("BIT") || map.get("FTYPE").equals("SMALLINT") 
						|| map.get("FTYPE").equals("TINYINT")){
					fieldDate.setStrFieldType("4");
				}
				else if(map.get("FTYPE").equals("NUMBER") || map.get("FTYPE").equals("FLOAT") || map.get("FTYPE").equals("REAL")
						|| map.get("FTYPE").equals("NUMERIC") || map.get("FTYPE").equals("DECIMAL") || map.get("FTYPE").equals("MONEY") 
						|| map.get("FTYPE").equals("SMALLMONEY") || map.get("FTYPE").equals("FLOAT")){
					fieldDate.setStrFieldType("5");
				}

				if(map.get("FLENGTH") != null){
					fieldDate.setNLength(map.get("FLENGTH").toString());
				}
				if(map.get("FPREC") != null){
					fieldDate.setNPrecise(map.get("FPREC").toString());
				}
				
				if(table.getDataSource().getStrDatabaseType().equals("2")){ // 如果数据库是oracle，长度除2 lys
					String FLENGTH = map.get("FLENGTH").toString();
					if(map.get("FTYPE").equals("NUMBER") || map.get("FTYPE").equals("FLOAT") || map.get("FTYPE").equals("REAL")){
						if(map.get("FSCALE") != null){
							fieldDate.setNPrecise(map.get("FSCALE").toString());
							if(map.get("FPREC") != null){
								FLENGTH = (Integer.parseInt(map.get("FSCALE").toString())+Integer.parseInt(map.get("FPREC").toString()))+"";
							}
						}
					}
					else if(map.get("FTYPE")!=null && (map.get("FTYPE").toString().startsWith("TIMESTAMP") || map.get("FTYPE").equals("DATE"))){
						FLENGTH = "";
					}
					else{
						FLENGTH = String.valueOf(Integer.parseInt(FLENGTH)/2);
					}
					fieldDate.setNLength(FLENGTH);
				}
				fieldDate.setReportModel_Table(table);

				// 将字段记录保存到字段表
				 IParamVoidResultExecute daoVoid =
					 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
				 Object oldObj = RequestManager.getTOject();
				 RequestManager.setTOject(fieldDate);
				 try {
					 daoVoid.paramVoidResultExecute(null);
				 }
				 catch (Exception e) {
					setServiceResult(new MessageResult(false, "生成表字段失败！"));
					e.printStackTrace();
				}
				finally {
					RequestManager.setTOject(oldObj);
				}
			}
			setServiceResult(new MessageResult(true, "生成表字段成功！"));
			
		}
		else if(RequestManager.getTName().equals("extend.dto.AutoETL_View")) {
			// 创建DAO
			String beanID = "singleObjectFindByIdDao";
			IParamObjectResultExecute SingleObjectFindByIdDao =
			(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
			// 调用DAO查询到tObject对象(视图)
			Object tObject = null;
			try {
				Object oldId = RequestManager.getId();
				RequestManager.setId(RequestManager.getLevelIdValue());
				tObject = SingleObjectFindByIdDao.paramObjectResultExecute(null);
				RequestManager.setId(oldId);
			} catch (Exception e) {
				setServiceResult(new MessageResult(false, "生成视图字段失败！"));
				e.printStackTrace();
			}
			
			if (tObject == null) { // 找不到对象
				setServiceResult(new MessageResult(false, "生成视图字段失败！"));
				return;
			}
			
			AutoETL_View table = (AutoETL_View)tObject;
			
			String strSql = getSQL(table.getDataSource().getStrDatabaseType(), table.getStrTableName(), "");
			
			// 执行sql取出字段属性信息
			beanID = "createSqlQueryResultSetDao";
			IParamObjectResultExecute dao =
			(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
			// 调用DAO查询到tObject对象(表)
			ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				ResultSet resultSet = (ResultSet)dao.paramObjectResultExecute(new Object[]{strSql, table.getDataSource().getSessionFactory()});
				if(resultSet != null) {
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					int fieldCount = resultSetMetaData.getColumnCount();  
					while(resultSet.next()){
						Map<String,Object> valueMap = new LinkedHashMap();  
			            for (int i = 1; i <= fieldCount; i++) {  
			                String fieldName = resultSetMetaData.getColumnName(i);   
			                Object object = resultSet.getObject(fieldName);
			                valueMap.put(fieldName, object);
			            }  
			            list.add(valueMap);
					}
					resultSet.close();
				}
			} catch (Exception e) {
				setServiceResult(new MessageResult(false, "生成表字段失败！"));
				e.printStackTrace();
			}

			if (list == null) { // 找不到字段
				setServiceResult(new MessageResult(false, "未找到表字段信息！"));
				return;
			}

			// 生成AutoETL_ViewField
			for(Map<String, Object> map : list)
			{
				// 检查是否有同名字段
				boolean fieldExsit = false;
				for(AutoETL_ViewField field : table.getAutoETL_ViewFields()) {
					if(field.getStrFieldName().toUpperCase().equals(map.get("FNAME").toString().toUpperCase())) {
						fieldExsit = true;
						break;
					}
				}
				
				if(fieldExsit) continue;
				
				// 添加字段
				AutoETL_ViewField fieldDate = new AutoETL_ViewField();
				fieldDate.setStrChinaName("");
				if(map.get("FSEQ") != null)
					fieldDate.setNSeq(map.get("FSEQ").toString());
				else
					fieldDate.setNSeq("0");

				if(map.get("FNAME").toString().equals("INSTINFO"))
					fieldDate.setStrFieldName("instInfo");
				else if(map.get("FNAME").toString().equals("DTDATE"))
					fieldDate.setStrFieldName("dtDate");
				else
					fieldDate.setStrFieldName(map.get("FNAME").toString());
				
				fieldDate.setAutoETL_View(table);

				// 将字段记录保存到字段表
				 IParamVoidResultExecute daoVoid =
					 (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
				 Object oldObj = RequestManager.getTOject();
				 RequestManager.setTOject(fieldDate);
				 
				 fieldDate.setStrChinaName(fieldDate.getStrFieldName());
				 
				 try {
					 daoVoid.paramVoidResultExecute(null);
				 }
				 catch (Exception e) {
					setServiceResult(new MessageResult(false, "生成视图字段失败！"));
					e.printStackTrace();
				}
				finally {
					RequestManager.setTOject(oldObj);
				}
			}
			setServiceResult(new MessageResult(false, "生成视图字段成功！"));
		}
		
		
	}

}
