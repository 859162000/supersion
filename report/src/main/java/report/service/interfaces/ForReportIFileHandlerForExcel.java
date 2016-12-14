package report.service.interfaces;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import framework.services.interfaces.IFileHandler;
import framework.show.ShowInstance;

public interface ForReportIFileHandlerForExcel extends IFileHandler{
	
	// Excel文件多个Sheet页面导入到数据库表(导入时添加固定字段默认值)
	String WriteFromPathToDatabase(ShowInstance showInstance, String sessionFactory, String tableName, 
			String path, String[] sheetNames, Class<?> targetType, boolean showForeignId, boolean showHeader, 
			boolean deleteOld, DetachedCriteria detachedCriteria, String afterIgnorSeg, 
			int startLine, int startCol, List<String> checkClassList,List<String> insertAddClassList)throws Exception;

}
