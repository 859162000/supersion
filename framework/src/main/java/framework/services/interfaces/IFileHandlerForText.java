package framework.services.interfaces;

import java.lang.reflect.Field;
import java.util.List;

public interface IFileHandlerForText extends IFileHandler{
	void WriteFromCacheToPath(List<Object> sourceObjectList,List<Field> fieldList,String path,String seg, String strDateFormat) throws Exception;
	void WriteFromCacheToDatabase(String sessionFactory,List<String[]> sourceStringList,List<Field> fieldList ,Class<?> targetType) throws Exception;
	int WriteFromDatabaseToPath(String sessionFactory,String hSql,List<Field> fieldList, String path,String seg, Integer cacheLine, String strDateFormat)throws Exception;
	int WriteFromPathToDatabase(String sessionFactory,String tableName,List<Field> fieldList, String path, String seg,Integer cacheLine)throws Exception;
	int WriteFromPathToDatabase(String sessionFactory,String tableName,List<Field> fieldList, String path, String seg,Integer cacheLine,String charset)throws Exception;
	DownloadResult GetDownloadResultFromCache(List<Object> sourceObjectList,List<Field> fieldList,String fileName, String seg,Integer bufferSize, String strDateFormat) throws Exception;
	DownloadResult GetDownloadResultFromCacheTextFile(List<Object> sourceObjectList,List<Field> fieldList,String fileName, String seg,Integer bufferSize, String strDateFormat) throws Exception;
}
