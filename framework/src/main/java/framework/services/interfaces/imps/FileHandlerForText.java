package framework.services.interfaces.imps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandlerForText;
import framework.show.ShowContext;

public class FileHandlerForText extends FileHandler implements IFileHandlerForText{

	private static Integer defaultCacheLine = 10000;
	private static String defaultSeg = ",";
	
	public void WriteFromCacheToPath(List<Object> sourceObjectList,List<Field> fieldList,String path,String seg,String strDateFormat) throws Exception{
		if(sourceObjectList != null && sourceObjectList.size() > 0){
			
			if(seg == null){
				seg = defaultSeg;
			}
			
			BufferedWriter bufferedWriter = null;
			try{
				bufferedWriter = new BufferedWriter(new FileWriter(path));
				WriteFromCacheToBuffer(bufferedWriter,sourceObjectList,fieldList,seg,strDateFormat);
			}
			finally{
				if(bufferedWriter != null){
					bufferedWriter.close();
				}
			}
		}
	}

	public void WriteFromCacheToDatabase(String sessionFactory,List<String[]> sourceStringList,List<Field> fieldList,Class<?> targetType) throws Exception{
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		WriteFromCacheToDatabase(sessionFactory,sourceStringList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
	}
	
	private void WriteFromCacheToDatabase(String sessionFactory,List<String[]> sourceStringList,List<Field> fieldList,Class<?> targetType,IParamVoidResultExecute singleObjectSaveOrUpdateAllDao) throws Exception{
		if(sourceStringList != null && sourceStringList.size() > 0){
			if(fieldList == null){
				fieldList = ReflectOperation.getColumnFieldList(targetType);
			}
			List<Object> targetObjectList = new ArrayList<Object>();
			for(String[] sourceStrings : sourceStringList){
				Object targetObject = targetType.newInstance();
				for (int j = 0; j < sourceStrings.length && (fieldList.get(j))!=null; j++) { 
					if(ReflectOperation.isBaseType(fieldList.get(j).getType())){
						ReflectOperation.setFieldValue(targetObject, fieldList.get(j).getName(),sourceStrings[j]);
                	}
                	else{
                		Object objectValue = fieldList.get(j).getType().newInstance();
                		ReflectOperation.setFieldValue(objectValue, ReflectOperation.getPrimaryKeyField(objectValue.getClass()).getName(), sourceStrings[j]);
                		ReflectOperation.setFieldValue(targetObject, fieldList.get(j).getName(), objectValue);
                	}
	            } 
				targetObjectList.add(targetObject);
			}
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{targetObjectList,sessionFactory});
		}
	}
	
	private void WriteFromMapCacheToDatabase(String sessionFactory,List<Map<String,Object>> sourceStringList,List<Field> fieldList,Class<?> targetType,IParamObjectResultExecute singleObjectSaveOrUpdateAllDao) throws Exception{
		if(sourceStringList != null && sourceStringList.size() > 0){
			if(fieldList == null){
				fieldList = ReflectOperation.getColumnFieldList(targetType);
			}
			List<Object> targetObjectList = new ArrayList<Object>();
			for(Map<String,Object> sourceString : sourceStringList){
				Object targetObject = targetType.newInstance();
				for (int j = 0; j < sourceString.size() && (fieldList.get(j))!=null; j++) { 
	                ReflectOperation.setFieldValue(targetObject, fieldList.get(j).getName(),sourceString.get(j));
	            } 
				targetObjectList.add(targetObject);
			}
			singleObjectSaveOrUpdateAllDao.paramObjectResultExecute(new Object[]{targetObjectList,sessionFactory});
		}
	}

    public int WriteFromPathToDatabase1(String sessionFactory,String tableName,List<Field> fieldList , String path, String seg,Integer cacheLine)throws Exception {
		
		if(cacheLine == null){
			cacheLine = defaultCacheLine;
		}
		if(seg == null){
			seg = defaultSeg;
		}
		
		if(seg.startsWith("ASCII")){
			seg = seg.replace("ASCII", "");
			if(seg.startsWith("0X") || seg.startsWith("0x")){
				seg = seg.replace("0X", "");
				seg = seg.replace("0x", "");
				seg = String.valueOf((char)Integer.parseInt(seg,16));
			}else{
				seg = String.valueOf((char)Integer.parseInt(seg));
			}			
		}
		
		
		BufferedReader bufferedReader = null;
		int lines = 0;
		try{
			String line=null; 
			
			if(ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat") != null){
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat")));
			}
			else{
				bufferedReader = new BufferedReader(new FileReader(path)); 
			}
			
			Class<?> targetType = null;
			if(sessionFactory != null){
				targetType = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName));  
			}
			else{
				targetType = Class.forName(tableName);
			}
			
			String dataEncode = null;
			if(ShowContext.getInstance().getShowEntityMap().get("sysStringFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat") != null){
				dataEncode = ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat");
			}
			IParamObjectResultExecute singleObjectSaveOrUpdateAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			ResultSet resultSet = (ResultSet)singleObjectSaveOrUpdateAllDao.paramObjectResultExecute(new Object[]{tableName,sessionFactory});
			//IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
			//ResultSet resultSet = (ResultSet)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{tableName,sessionFactory});
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
			List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
			int i = 0;
			int fieldCount = resultSetMetaData.getColumnCount();  
			
			while (resultSet.next()){
				if(dataEncode != null){
					line = new String(line.getBytes(),dataEncode);
				}
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
					
					WriteFromMapCacheToDatabase(sessionFactory,sourceObjectList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
					sourceObjectList = new ArrayList<Map<String,Object>>();
					i = 0;
				}
			}
			
			WriteFromMapCacheToDatabase(sessionFactory,sourceObjectList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
			
			resultSet.close();
			
		}
		finally{
			if(bufferedReader != null){
				bufferedReader.close();
			}
		}
		
		return lines;
		
	}

   public int WriteFromPathToDatabase(String sessionFactory,String tableName,List<Field> fieldList , String path, String seg,Integer cacheLine)throws Exception {
		
		if(cacheLine == null){
			cacheLine = defaultCacheLine;
		}
		if(seg == null){
			seg = defaultSeg;
		}
		
		if(seg.startsWith("ASCII")){
			seg = seg.replace("ASCII", "");
			if(seg.startsWith("0X") || seg.startsWith("0x")){
				seg = seg.replace("0X", "");
				seg = seg.replace("0x", "");
				seg = String.valueOf((char)Integer.parseInt(seg,16));
			}else{
				seg = String.valueOf((char)Integer.parseInt(seg));
			}			
		}
		
		int lines = 0;
		int i = 0;
		BufferedReader bufferedReader = null;
		try{ 

			List<String[]> sourceStringList = new ArrayList<String[]>();
			String line=null; 
			
			if(ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat") != null){
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat")));
			}
			else{
				bufferedReader = new BufferedReader(new FileReader(path)); 
			}
			
			
			Class<?> targetType = null;
			if(sessionFactory != null){
				targetType = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName));  
			}
			else{
				targetType = Class.forName(tableName);
			}
			
			String dataEncode = null;
			if(ShowContext.getInstance().getShowEntityMap().get("sysStringFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat") != null){
				dataEncode = ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat");
			}
			
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			while((line=bufferedReader.readLine())!=null) { 
				if(!StringUtils.isBlank(line)){
					if(dataEncode != null){
						line = new String(line.getBytes(),dataEncode);//line = new String(line.getBytes(dataEncode),dataEncode);
					}
					lines ++;
					i++;
					String[] values =  line.split(seg);
					sourceStringList.add(values);
					if(i > cacheLine - 1){
						WriteFromCacheToDatabase(sessionFactory,sourceStringList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
						sourceStringList  = new ArrayList<String[]>();
						i = 0;
					}
				}
			} 
			
			WriteFromCacheToDatabase(sessionFactory,sourceStringList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
		}
		finally{
			if(bufferedReader != null){
				bufferedReader.close();
			}
		}
		
		return lines;
		
	}

	public int WriteFromDatabaseToPath(String sessionFactory,String hSql,List<Field> fieldList, String path,String seg,Integer cacheLine,String strDateFormat)throws Exception {
		
		if(cacheLine == null){
			cacheLine = defaultCacheLine;
		}
		if(seg == null){
			seg = defaultSeg;
		}
		
		BufferedWriter bufferedWriter = null;
		int lines = 0;
		try{
			
			File file = new File(path);
			file.deleteOnExit();
			file.createNewFile();
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
			ResultSet resultSet = (ResultSet)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{hSql,sessionFactory});
            FileOutputStream fos = new FileOutputStream(path);
			 
			String codeFormat = "ANSI"; 
			if(ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat") != null){
				 codeFormat = ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat");
				 if(codeFormat == null){
					 codeFormat = "ANSI";
				 }
			 }
			
			if(codeFormat.equals("UTF-8")){
				 byte[] bomUTF = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF }; 
			     fos.write(bomUTF); 
			     bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, codeFormat)); 
			      
			 }
			 else if(codeFormat.equals("Unicode")){
				 byte[] bomUnicode = new byte[] { (byte)0xFF, (byte)0xFE}; 
			     fos.write(bomUnicode); 
			     bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, codeFormat));
			 }
			 else if(codeFormat.equals("GBK")){				
			     bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, codeFormat));	
			 }	
			 else{
				 bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
			 }
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
			List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
			int i = 0;
			int fieldCount = resultSetMetaData.getColumnCount();  
			
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
					WriteFromMapCacheToBuffer(bufferedWriter,sourceObjectList,fieldList,seg,strDateFormat);
					bufferedWriter.flush();
					sourceObjectList = new ArrayList<Map<String,Object>>();
					i = 0;
				}
			}
			
			WriteFromMapCacheToBuffer(bufferedWriter,sourceObjectList,fieldList,seg,strDateFormat);
			
			resultSet.close();
			
		}
		finally{
			if(bufferedWriter != null){
				bufferedWriter.close();
			}
		}
		
		return lines;
	}
	
	public DownloadResult GetDownloadResultFromCache(List<Object> sourceObjectList,List<Field> fieldList, String fileName, String seg, Integer bufferSize, String strDateFormat)throws Exception {
		DownloadResult downloadResult = GetStreamResult(fileName, bufferSize);
		if(sourceObjectList != null && sourceObjectList.size() > 0){
			if(seg == null){
				seg = defaultSeg;
			}
			if(fieldList == null){
				fieldList = ReflectOperation.getColumnFieldList(sourceObjectList.get(0).getClass());
			}
			StringBuilder stringBuilder = new StringBuilder();
			for(Object object : sourceObjectList){
				stringBuilder.append(TranslateToString(object,fieldList,seg, strDateFormat));
			}							
			downloadResult.setInputStream(new ByteArrayInputStream(stringBuilder.toString().getBytes()));
		}
		else{
			downloadResult.setInputStream(new ByteArrayInputStream("".getBytes()));
		}
		return downloadResult;
	}
	
	public DownloadResult GetDownloadResultFromCacheTextFile(List<Object> sourceObjectList,List<Field> fieldList, String fileName, String seg, Integer bufferSize, String strDateFormat)throws Exception {
		BufferedWriter bufferedWriter = null;
		DownloadResult downloadResult= null;
		File tempFile=null;
		try{
			tempFile = new File(fileName);
			FileOutputStream fos = new FileOutputStream(fileName);
			 
			String codeFormat = "ANSI"; 
			if(ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat") != null){
				 codeFormat = ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat");
				 if(codeFormat == null){
					 codeFormat = "ANSI";
				 }
			 }
			
			if(codeFormat.equals("UTF-8")){
				 byte[] bomUTF = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF }; 
			     fos.write(bomUTF); 
			     bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, codeFormat));
			      
			 }
			 else if(codeFormat.equals("Unicode")){
				 byte[] bomUnicode = new byte[] { (byte)0xFF, (byte)0xFE}; 
			     fos.write(bomUnicode); 
			     bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, codeFormat));
			 }
			 else if(codeFormat.equals("GBK")){				
			     bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos, codeFormat));	
			 }	
			 else{
				 bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
			 }
			
			if(sourceObjectList != null && sourceObjectList.size() > 0){
				if(seg == null){
					seg = defaultSeg;
				}
				if(fieldList == null){
					fieldList = ReflectOperation.getColumnFieldList(sourceObjectList.get(0).getClass());
				}
				for(Object object : sourceObjectList){
					bufferedWriter.write(TranslateToString(object,fieldList,seg, strDateFormat));
				}	
				
			}
			downloadResult= GetStreamResultFromRealPath(tempFile.getAbsolutePath(), fileName,bufferSize);
			return downloadResult;
			
		}finally{
			if(bufferedWriter!=null){
				bufferedWriter.close();
			}
			if(tempFile != null){
				if(tempFile.exists()){
					tempFile.delete();
				}
			}
		}

		
	}

	@SuppressWarnings("unchecked")
	private String TranslateToString(Object object,List<Field> fieldList,String seg,String strDateFormat) throws Exception{
		String line = "";
		for(int j=0;j<fieldList.size();j++){
			
			Object fieldValue = null;
			if(object.getClass().getName().equals("java.util.LinkedHashMap")){
				fieldValue = ((Map<String,Object>)object).get(fieldList.get(j).getName());
			}
			else{
				fieldValue = ReflectOperation.getFieldValue(object, fieldList.get(j).getName());
			}
			if(fieldValue == null){
				fieldValue = "";
			}
			if(StringUtils.isBlank(strDateFormat)){
				fieldValue = fieldValue.toString();
			}
			else{
				if(fieldValue.getClass().equals(Date.class) || fieldValue.getClass().equals(java.sql.Date.class) ){
					fieldValue = TypeParse.parseString((Date)fieldValue, strDateFormat);
				}
				else if(ReflectOperation.isBaseType(fieldValue.getClass())){
					fieldValue = fieldValue.toString();
				}
				else{
					fieldValue = ReflectOperation.getPrimaryKeyValue(fieldValue).toString();
				}
			}
			
			line += fieldValue;
			if(j != fieldList.size() -1){
				line += seg;
			}
		}
		//line +="\r\n";
		line += System.getProperty("line.separator");
		return line;
	}
	

	
	private void WriteFromCacheToBuffer(BufferedWriter bufferedWriter,List<Object> sourceObjectList,List<Field> fieldList,String seg, String strDateFormat) throws Exception{
		if(sourceObjectList != null && sourceObjectList.size() > 0){
			if(fieldList == null){
				fieldList = ReflectOperation.getColumnFieldList(sourceObjectList.get(0).getClass());
			}
			for(Object object : sourceObjectList){
				String line = TranslateToString(object,fieldList,seg,strDateFormat);
				if(ShowContext.getInstance().getShowEntityMap().get("sysStringFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat") != null){
					line = new String(line.getBytes(), ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat"));
				}
				bufferedWriter.write(line);
			}
		}
	}
	
	private void WriteFromMapCacheToBuffer(BufferedWriter bufferedWriter,List<Map<String,Object>> sourceObjectList,List<Field> fieldList,String seg, String strDateFormat) throws Exception{
		if(sourceObjectList != null && sourceObjectList.size() > 0){
			if(fieldList == null){
				fieldList = ReflectOperation.getColumnFieldList(sourceObjectList.get(0).getClass());
			}
			for(Map<String,Object> object : sourceObjectList){
				String line = TranslateToString(object,fieldList,seg,strDateFormat);
				if(ShowContext.getInstance().getShowEntityMap().get("sysStringFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat") != null){
					line = new String(line.getBytes(), ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat"));
				}
				bufferedWriter.write(line);
			}
		}
	}


	public int WriteFromPathToDatabase(String sessionFactory,String tableName,List<Field> fieldList , String path, String seg,Integer cacheLine, String charset)throws Exception {
		
		if(cacheLine == null){
			cacheLine = defaultCacheLine;
		}
		if(seg == null){
			seg = defaultSeg;
		}
		//////////ZHOUQIN Begin//////////
		if(seg.startsWith("ASCII")){
			seg = seg.replace("ASCII", "");
			if(seg.startsWith("0X") || seg.startsWith("0x")){
				seg = seg.replace("0X", "");
				seg = seg.replace("0x", "");
				seg = String.valueOf((char)Integer.parseInt(seg,16));
			}else{
				seg = String.valueOf((char)Integer.parseInt(seg));
			}			
		}
		//////////ZHOUQIN End////////////
		int lines = 0;
		int i = 0;
		BufferedReader bufferedReader = null;
		try{ 

			List<String[]> sourceStringList = new ArrayList<String[]>();
			String line=null; 
			
			if(charset != null){
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),charset));
			}
			else if(ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat") != null){
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),ShowContext.getInstance().getShowEntityMap().get("sysFileCodeFormat").get("codeFormat")));
			}
			else{
				bufferedReader = new BufferedReader(new FileReader(path)); 
			}
			
			
			Class<?> targetType = null;
			if(sessionFactory != null){
				targetType = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName));  
			}
			else{
				targetType = Class.forName(tableName);
			}
			
			String dataEncode = null;
			if(ShowContext.getInstance().getShowEntityMap().get("sysStringFormat") != null && ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat") != null){
				dataEncode = ShowContext.getInstance().getShowEntityMap().get("sysStringFormat").get("stringFormat");
			}
			
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			while((line=bufferedReader.readLine())!=null) { 
				if(!StringUtils.isBlank(line)){
					if(dataEncode != null){
						line = new String(line.getBytes(),dataEncode);
					}
					lines ++;
					i++;
					String[] values =  line.split(seg);
					sourceStringList.add(values);
					if(i > cacheLine - 1){
						WriteFromCacheToDatabase(sessionFactory,sourceStringList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
						sourceStringList  = new ArrayList<String[]>();
						i = 0;
					}
				}
			} 
			
			WriteFromCacheToDatabase(sessionFactory,sourceStringList,fieldList,targetType,singleObjectSaveOrUpdateAllDao);
		}
		finally{
			if(bufferedReader != null){
				bufferedReader.close();
			}
		}
		
		return lines;
		
	}

}
