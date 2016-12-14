package report.service.interfaces.imp;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;

import report.service.interfaces.ForReportIFileHandlerForExcel;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
import report.service.interfaces.imp.ForReportParserExcelPoi.Excel2003Reader;
import report.service.interfaces.imp.ForReportParserExcelPoi.Excel2007Reader;
import framework.show.ShowField;
import framework.show.ShowInstance;
public class ForReportFileHandlerForExcel extends FileHandler implements ForReportIFileHandlerForExcel{
	
	private String EXCEL03_EXTENSION = ".xls";		//excel2003扩展名  
    private String EXCEL07_EXTENSION = ".xlsx";		//excel2007扩展名
    
	// Excel文件多个Sheet页面导入到数据库表(导入时添加固定字段默认值)
	public String WriteFromPathToDatabase(ShowInstance showInstance, String sessionFactory, String tableName,
			String path, String[] sheetNames, Class<?> targetType, boolean showForeignId, boolean showHeader, 
			boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg, 
			int startLine, int startCol, List<String> checkClassList, List<String> insertAddClassList)throws Exception {
		
		String message = "";
		
		if(sheetNames ==null) {
			message = WriteFromCacheToDatabase(showInstance, sessionFactory, tableName, path, 
					null, targetType, showForeignId, showHeader, deleteOld, detachedCriteria, 
					afterIgnorSeg, startLine, startCol, checkClassList,insertAddClassList);
		} else {
			int sheetIndex=0;
			while(sheetNames.length>sheetIndex){
				String tmpMessage = WriteFromCacheToDatabase(showInstance, sessionFactory, tableName, 
						path, sheetNames[sheetIndex], targetType, showForeignId, showHeader, 
						deleteOld, detachedCriteria, afterIgnorSeg, startLine, 
						startCol, checkClassList,insertAddClassList);
				
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
	
	// 单个Sheet页面导入到数据库表(导入时添加固定字段默认值)
	public String WriteFromCacheToDatabase(ShowInstance showInstance, String sessionFactory, String tableName, 
			String path, String sheetNames, Class<?> targetType, boolean showForeignId, 
			boolean showHeader, boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg, 
			int startLine, int startCol, List<String> checkClassList,List<String> insertAddClassList)throws Exception {
		
	    ArrayList<ShowField> showFields = new ArrayList<ShowField>();
	 
		for(ShowField showField : showInstance.getShowFieldList()){
			
			if(showField.isListVisible()) { // 生成各字段显示名
				showFields.add(showField); //添加显示字段
			}
		}
		 
		ForReportParserExcelPoi forReportParserExcelPoi = new ForReportParserExcelPoi();
		ArrayList<Object> insertDataList;
		String message = "";
		String excelType = getExcelType(path);
		
		if(".xlsx".equals(excelType)) {
			Excel2007Reader excel07 = forReportParserExcelPoi.new Excel2007Reader();
			excel07.process(showFields, sessionFactory, tableName, path, sheetNames, showForeignId, showHeader, afterIgnorSeg, startLine, startCol);
	        insertDataList = excel07.insertDataList;
	        message = forReportParserExcelPoi.error.toString();
		}
		else if(".xls".equals(excelType)){
	    	Excel2003Reader excel03 = forReportParserExcelPoi.new Excel2003Reader();
	        excel03.process(showFields, sessionFactory, tableName, path, sheetNames, showForeignId, showHeader, afterIgnorSeg, startLine, startCol);
	        insertDataList = excel03.insertDataList;
	        message = forReportParserExcelPoi.error.toString();
		}
		else{
			return "文件类型不正确";
		}
	
		int nLine = 1;
		//新增固定字段默认值
		if(message.equals("") && insertDataList.size()>0){
			if(insertAddClassList!=null){
				Object tOldObject = RequestManager.getTOject();
				String tOldName = RequestManager.getTName();
				
				for(Object obj:insertDataList){
					RequestManager.setTOject(obj);
					RequestManager.setTName(targetType.getName());
					for(String str:insertAddClassList){
						IAdd add=(IAdd)FrameworkFactory.CreateClass(str);
						add.Add();
					}
				}
				RequestManager.setTName(tOldName);
				RequestManager.setTOject(tOldObject);
			}
		}
		
		if(message.equals("") && insertDataList.size()>0) {
			// 校验数据
			if(checkClassList != null) {
				Object tOldObject = RequestManager.getTOject();
				String tOldName = RequestManager.getTName();
				
				((Map<String,Object>)ServletActionContext.getContext().get("request")).put("insertDataList", insertDataList);
				
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
   
}
