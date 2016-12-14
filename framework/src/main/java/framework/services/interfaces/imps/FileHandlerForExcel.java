package framework.services.interfaces.imps;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.IFileHandlerForExcel;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.ParserExcelPoi.Excel2003Reader;
import framework.services.interfaces.imps.ParserExcelPoi.Excel2007Reader;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowInstance;
public class FileHandlerForExcel extends FileHandler implements IFileHandlerForExcel{

	private static String defaultSheetName = "Sheet1";
	private static Integer defaultCacheLine = 10000;
	
	private String EXCEL03_EXTENSION = ".xls";		//excel2003扩展名  
    private String EXCEL07_EXTENSION = ".xlsx";		//excel2007扩展名
    
	// 单个ObjectList导出到Excel
	public DownloadResult GetDownloadResultFromObjectList(ShowInstance showInstance, List<Object> sourceObjectList, 
			String fileName, String sheetName, boolean showForeignId, boolean showHeader,
			int startLine, int startCol, String strDateFormat,String modelPath) throws Exception {

		sheetName = getSheetName(sheetName);
		
		String path ="";
		if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
			path = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
		}
		else{
			path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1).replace("%20", " ");
		}
		
		path = path + fileName;
		WriteFromDatabaseToPath(showInstance, fileName, sheetName,
				sourceObjectList, null, null, showForeignId,
				showHeader, startLine, startCol, strDateFormat,modelPath);
		InputStream inputStream = new FileInputStream(path);
		DownloadResult downloadResult = GetStreamResultFromInputStream(inputStream, fileName, null);
		File file = new File(path);
		file.deleteOnExit();

		return downloadResult;
	}

	// 多个ObjectList导出到Excel不同页面
	public DownloadResult GetDownloadResultFromObjectList(List<ShowInstance> showInstanceList,
			List<List<Object>> sourceObjectLists, String fileName, String[] sheetNames,
			boolean showForeignId, boolean showHeader,int startLine, int startCol, 
			String strDateFormat,String modelPath) throws Exception {

		try{
			String path="";
			if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
				path = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
			}
			else{
				path =Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1).replace("%20", " ");
			}
			
			path = path + fileName;

			for(int i=0;i<showInstanceList.size();i++){
				String sheetName = getSheetName(sheetNames, i);
				
				WriteFromDatabaseToPath(showInstanceList.get(i), fileName, sheetName,
						sourceObjectLists.get(i), null, null, showForeignId,
						showHeader, startLine, startCol, strDateFormat,modelPath);
			}

			InputStream inputStream = new FileInputStream(path);
			DownloadResult downloadResult = GetStreamResultFromInputStream(inputStream, fileName, null);
			File file = new File(path);
			file.deleteOnExit();

			return downloadResult;
		}
		finally{

		}
	}
	
	// 数据库表导出到Excel文件
	public int WriteFromDatabaseToPath(ShowInstance showInstance, String path, String sheetName,
			Object sourceObjectList, Class<?> targetType, Integer cacheLine,
			boolean showForeignId, boolean showHeader, int startLine, int startCol, 
			String strDateFormat,String modelPath)throws Exception {
		if(cacheLine == null || cacheLine<1)
			cacheLine = defaultCacheLine;

		int rowNum = 0;
		ParserExcelPoi parserExcelPoi = new ParserExcelPoi();
		if(path.endsWith(".xls"))
			rowNum = parserExcelPoi.writeDataTo03Excel(showInstance, path, sheetName, sourceObjectList,
					targetType, cacheLine, showForeignId, showHeader, startLine, startCol, null, strDateFormat,modelPath);
		if(path.endsWith(".xlsx"))
			rowNum = parserExcelPoi.writeDataTo07Excel(showInstance, path, sheetName, sourceObjectList, 
					targetType, cacheLine, showForeignId, showHeader, startLine, startCol, null, strDateFormat,modelPath);

		return rowNum;
	}
	
	public int WriteFromMapDatabaseToPath(ShowInstance showInstance, String path, String sheetName,
			List<Map<String,Object>> sourceObjectList, Class<?> targetType, Integer cacheLine,
			boolean showForeignId, boolean showHeader, int startLine, int startCol, 
			String strDateFormat,String modelPath)throws Exception {
		if(cacheLine == null || cacheLine<1)
			cacheLine = defaultCacheLine;

		int rowNum = 0;
		ParserExcelPoi parserExcelPoi = new ParserExcelPoi();
		if(path.endsWith(".xls"))
			rowNum = parserExcelPoi.writeMapDataTo03Excel(showInstance, path, sheetName, sourceObjectList,
					targetType, cacheLine, showForeignId, showHeader, startLine, startCol, null, strDateFormat,modelPath);
		if(path.endsWith(".xlsx"))
			rowNum = parserExcelPoi.writeMapDataTo07Excel(showInstance, path, sheetName, sourceObjectList, 
					targetType, cacheLine, showForeignId, showHeader, startLine, startCol, null, strDateFormat,modelPath);

		return rowNum;
	}
	
	// 数据库表hsql导出到Excel文件
	public int WriteFromDatabaseToPath(ShowInstance showInstance, String sessionFactory, String path, String sheetName,
    		String hSql, Class<?> targetType, Integer cacheLine, boolean showForeignId,
			boolean showHeader, int startLine, int startCol, String strDateFormat,String modelPath)throws Exception {

		if(cacheLine == null || cacheLine < 1)
			cacheLine = defaultCacheLine;
    	int lines = 0;
    	
		try{
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
			ResultSet resultSet = (ResultSet)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{hSql,sessionFactory});
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
			int fieldCount = resultSetMetaData.getColumnCount();
			int i = 0;
			int cacheCount = 0;
			
			while (resultSet.next()){
				lines ++;
				Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
	            for (int j = 1; j <= fieldCount; j++) {  
	                String fieldName = resultSetMetaData.getColumnName(j);   
	                Object object = resultSet.getObject(fieldName);
	                valueMap.put(fieldName, object);
	            } 
	            sourceObjectList.add(valueMap);
				i++;
				if(i > cacheLine - 1){
					WriteFromMapDatabaseToPath(showInstance, path, sheetName,sourceObjectList, targetType, null, showForeignId, showHeader, startLine, startCol, strDateFormat,modelPath);
					sourceObjectList = new ArrayList<Map<String,Object>>();
					cacheCount++;
					i = 0;
				}
			}
			if(sourceObjectList.size() > 0){
				WriteFromMapDatabaseToPath(showInstance, path, sheetName,sourceObjectList, targetType, null, showForeignId, showHeader, startLine, startCol, strDateFormat,modelPath);
			}
			
			resultSet.close();
		}
		finally{
		}
		
		return lines;
    }
    

	// 单个Sheet页面导入到数据库表
	public String WriteFromCacheToDatabase(ShowInstance showInstance, String sessionFactory, String tableName, 
			String path, String sheetNames, Class<?> targetType, boolean showForeignId, 
			boolean showHeader, boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg, 
			int startLine, int startCol, List<String> checkClassList)throws Exception {
		
	    ArrayList<ShowField> showFields = new ArrayList<ShowField>();
		for(ShowField showField : showInstance.getShowFieldList()){
			Field field = ReflectOperation.getReflectField(targetType,showField.getFieldName());
			if(showField.isListVisible()) { // 生成各字段显示名
				showFields.add(showField); //添加显示字段
			}
//			else if(field.isAnnotationPresent(Column.class)){
//				Column column = field.getAnnotation(Column.class);
//				if(!column.nullable()){
//					return sheetNames+":”"+showField.getFieldShowName()+"“字段列不存在，此字段不能为空。";
//				}
//			}
		}
		
		ParserExcelPoi parserExcelPoi = new ParserExcelPoi();
		ArrayList<Object> insertDataList;
		String message = "";
		String excelType = getExcelType(path);
		
		if(".xlsx".equals(excelType)) {
			Excel2007Reader excel07 = parserExcelPoi.new Excel2007Reader();
			excel07.process(showFields, sessionFactory, tableName, path, sheetNames, showForeignId, showHeader, afterIgnorSeg, startLine, startCol);
	        insertDataList = excel07.insertDataList;
	        message = parserExcelPoi.error.toString();
		}
		else if(".xls".equals(excelType)){
	    	Excel2003Reader excel03 = parserExcelPoi.new Excel2003Reader();
	        excel03.process(showFields, sessionFactory, tableName, path, sheetNames, showForeignId, showHeader, afterIgnorSeg, startLine, startCol);
	        insertDataList = excel03.insertDataList;
	        message = parserExcelPoi.error.toString();
		}
		else{
			return "文件类型不正确";
		}
		
		int nLine = 1;
		if(message.equals("") && insertDataList.size()>0) {
			// 校验数据
			if(checkClassList != null) {
				Object tOldObject = RequestManager.getTOject();
				String tOldName = RequestManager.getTName();
				
				for(Object obj: insertDataList) {
					RequestManager.setTOject(obj);
					RequestManager.setTName(targetType.getName());
					for(String str : checkClassList) {
						ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
						MessageResult currentResult = check.Check();
						if(!currentResult.isSuccess()) {
							currentResult.AlertTranslate();
							return sheetNames + "行:" + (nLine + startLine+1) + " " + currentResult.getMessage();
						}
					}
					
					nLine++;
				}
				
				nLine--;
				
				RequestManager.setTName(tOldName);
				RequestManager.setTOject(tOldObject);
			}
			
			Field primaryKeyField = ReflectOperation.getPrimaryKeyField(targetType);
			boolean isCheckId = false;
			if(!primaryKeyField.isAnnotationPresent(GenericGenerator.class)){
				isCheckId = true;
			}
			else{
				if(!primaryKeyField.getAnnotation(GenericGenerator.class).strategy().equals("uuid.hex")){
					isCheckId = true;
				}
			}
			if(isCheckId){
				Map<Object,String> repeatPrimaryKeyMap = new HashMap<Object,String>();
				for(int i=0;i<insertDataList.size();i++){
					Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(insertDataList.get(i));
					if(primaryKeyValue!=null){
						if(!repeatPrimaryKeyMap.containsKey(primaryKeyValue)){
							repeatPrimaryKeyMap.put(primaryKeyValue, String.valueOf(i + 1));
						}
						else{
							return sheetNames + "行" + (Integer.parseInt(repeatPrimaryKeyMap.get(primaryKeyValue))+1) + "与行" + String.valueOf(i + 2) + "主键重复";
						}
					}
				}
			}

			if(deleteOld){ // 先删除原表相关记录
				if(detachedCriteria == null) {
					detachedCriteria = DetachedCriteria.forClass(targetType);
				}
				
				try {
					IParamVoidResultExecute dao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");
			    	dao.paramVoidResultExecute(new Object[]{detachedCriteria, null});
				}
				catch (ConstraintViolationException ex) {
					//ConstraintViolationException exCons = (ConstraintViolationException)ex.getCause();
					String msg = "删除原记录失败：" +ex.getSQLException().getLocalizedMessage().replace("\r\n", "\\r\\n");
					msg = msg.replace('\'', '|').replace('\"', '|').replace("\n", "\\r\\n");
					return msg;
				}
				catch (Exception ex) {
						return "删除原记录失败," + ex.getMessage();
					}
			}
			
			// 导入每行数据到每个属性并写入数据库
			
			List<Object> insertList=new ArrayList<Object>();
			List<Object> updateList=new ArrayList<Object>();
			for (Object object : insertDataList) {
				if(ReflectOperation.getPrimaryKeyValue(object)==null || ReflectOperation.getPrimaryKeyValue(object).toString().equals("")){
					insertList.add(object);
				}
				else{
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					Object dataBaseObject  = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{object.getClass().getName(),ReflectOperation.getPrimaryKeyValue(object),null});
					if(dataBaseObject == null){
						insertList.add(object);
					}
					else{
						ReflectOperation.CopyFieldValue(object, dataBaseObject);
						updateList.add(dataBaseObject);
					}
				}
					
			}
			IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
			singleObjectSaveAllDao.paramVoidResultExecute(new Object[]{insertList,null});
			
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{updateList,null});
			return "" + (insertDataList.size());
		}
		else{
			return message;
		}
	}

	// Excel文件多个Sheet页面导入到数据库表
	public String WriteFromPathToDatabase(ShowInstance showInstance, String sessionFactory, String tableName,
			String path, String[] sheetNames, Class<?> targetType, boolean showForeignId, boolean showHeader, 
			boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg, 
			int startLine, int startCol, List<String> checkClassList)throws Exception {
		
		String message = "";
		
		if(sheetNames ==null) {
			message = WriteFromCacheToDatabase(showInstance, sessionFactory, tableName, path, 
					null, targetType, showForeignId, showHeader, deleteOld, detachedCriteria, 
					afterIgnorSeg, startLine, startCol, checkClassList);
		} else {
			int sheetIndex=0;
			while(sheetNames.length>sheetIndex){
				String tmpMessage = WriteFromCacheToDatabase(showInstance, sessionFactory, tableName, 
						path, sheetNames[sheetIndex], targetType, showForeignId, showHeader, 
						deleteOld, detachedCriteria, afterIgnorSeg, startLine, 
						startCol, checkClassList);
				
				try {
					int importLines = Integer.parseInt(tmpMessage);
					message += sheetNames[sheetIndex]+"导入"+importLines+"行;";
				} catch (Exception e) {
					if(tmpMessage.equals("")){
						message += sheetNames[sheetIndex]+"导入失败;";
					}else{
						message += tmpMessage;
					}
				}
				sheetIndex++;
	       }
		}
		return message;
	}
	  
	/**
	 * 导出Excel   
	 * 存入内存
	 */
	public ByteArrayInputStream  WriteFromDatabaseToInputStream(ShowInstance showInstance, String fileName, String sheetName,
		Object sourceObjectList, Class<?> targetType, Integer cacheLine, boolean showForeignId, 
		boolean showHeader, int startLine, int startCol, String strDateFormat,String modelPath) throws Exception {
		if(cacheLine == null || cacheLine < 1)
			cacheLine = defaultCacheLine;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ParserExcelPoi parserExcelPoi = new ParserExcelPoi();

		if(fileName.endsWith(".xls"))
			parserExcelPoi.writeDataTo03Excel(showInstance, fileName, sheetName, sourceObjectList, targetType,
					cacheLine, showForeignId, showHeader, startLine, startCol, baos, strDateFormat,modelPath);
		if(fileName.endsWith(".xlsx"))
			parserExcelPoi.writeDataTo07Excel(showInstance, fileName, sheetName, sourceObjectList, targetType,
					cacheLine, showForeignId, showHeader, startLine, startCol, baos, strDateFormat,modelPath);

		byte[] ba = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		return bais;
	}
	
    /** 
     * 读取Excel文件，判断是03或07版本 
     */
    public String getExcelType(String path) throws Exception{ 
        
    	String str;
    	try{
        	OPCPackage.open(path);
        	str = EXCEL07_EXTENSION;
        }catch (Exception e) {
        	try{
        		new POIFSFileSystem(new FileInputStream(path)); 
	        	str = EXCEL03_EXTENSION;
            }catch (Exception er) {
            	str = "";
            }
        }
        return str;
    }
    
	private String getSheetName(String sheetName){
		if(StringUtils.isBlank(sheetName)){
			return defaultSheetName;
		}
		else{
			return sheetName;
		}
	}
	
	private String getSheetName(String[] sheetNames,int index){
		if(sheetNames == null){
			return defaultSheetName + index;
		}
		else{
			if(index > sheetNames.length -1 || StringUtils.isBlank(sheetNames[index])){
				return getSheetName(sheetNames[sheetNames.length -1]) + (index - sheetNames.length);
			}
			else{
				return sheetNames[index];
			}
		}
	}
}
