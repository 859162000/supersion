package framework.services.interfaces;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import framework.show.ShowInstance;

public interface IFileHandlerForExcel extends IFileHandler{
	
	// 单个ObjectList导出到Excel
	DownloadResult GetDownloadResultFromObjectList(ShowInstance showInstance, List<Object> sourceObjectList, 
			String fileName, String sheetName, boolean showForeignId, boolean showHeader,
			int startLine, int startCol, String strDateFormat,String modelPath) throws Exception;
	
	// 多个ObjectList导出到Excel不同页面
	DownloadResult GetDownloadResultFromObjectList(List<ShowInstance> showInstanceList,
			List<List<Object>> sourceObjectLists, String fileName,
			String[] sheetNames, boolean showForeignId, boolean showHeader, int startLine,
			int startCol, String strDateFormat,String modelPath) throws Exception;
	
	
	// 数据库表导出到Excel文件
	int WriteFromDatabaseToPath(ShowInstance showInstance, String path, String sheetName,
			Object sourceObjectList, Class<?> targetType, Integer cacheLine, boolean showForeignId,
			boolean showHeader, int startLine, int startCol, String strDateFormat,String modelPath) throws Exception;
	
	// 数据库表hsql导出到Excel文件
	int WriteFromDatabaseToPath(ShowInstance showInstance, String sessionFactory, String path, String sheetName,
			String hSql, Class<?> targetType, Integer cacheLine, 
			boolean showForeignId, boolean showHeader, int startLine, int startCol,
			String strDateFormat,String modelPath)throws Exception;

	// 数据库表导出到InputStream
	ByteArrayInputStream WriteFromDatabaseToInputStream(ShowInstance showInstance, String fileName, String sheetName,
			Object sourceObjectList, Class<?> targetType, Integer cacheLine,
			boolean showForeignId, boolean showHeader, int startLine, int startCol, 
			String strDateFormat,String modelPath) throws Exception;
	
	// 单个Sheet页面导入到数据库表
	String WriteFromCacheToDatabase(ShowInstance showInstance, String sessionFactory, String tableName, 
			String path, String sheetName, Class<?> targetType, boolean showForeignId, boolean showHeader,
			boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg,
			int startLine, int startCol, List<String> checkClassList) throws Exception;
	
	// Excel文件多个Sheet页面导入到数据库表
	String WriteFromPathToDatabase(ShowInstance showInstance, String sessionFactory, String tableName, 
			String path, String[] sheetNames, Class<?> targetType, boolean showForeignId, boolean showHeader, 
			boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg, 
			int startLine, int startCol, List<String> checkClassList)throws Exception;

}
