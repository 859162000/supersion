package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import report.service.interfaces.ForReportIFileHandlerForExcel;
import report.service.interfaces.imp.ForReportFileHandlerForExcel;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowInstance;

// 主要功能：根据当前DTO创建出SQL语句和连接供DAO执行
public class ForReportImportDataService extends BaseVoidDaoResultService {

    private String tableName = RequestManager.getTName();
	

	private String sheetName;
	
	private boolean showForeignId; // 外键是否只显示id
	
	private boolean showHeader; //  是否首行导出字段名
	
	private Integer startLine; // 内容行偏移
	
	private Integer startCol; // 内容列偏移
	
	private boolean deleteOld; // 是否删除旧数据
	
	private String afterIgnorSeg; // 外键与显示名分隔符
	
	private List<String> insertCheckClassList;
	
	private List<String> insertAddClassList;
	
    public void setInsertCheckClassList(List<String> insertCheckClassList) {
		this.insertCheckClassList = insertCheckClassList;
	}
    
    public void setInsertAddClassList(List<String> insertAddClassList) {
    	this.insertAddClassList = insertAddClassList;
	}
	
	@Override
    public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		File file = RequestManager.getUploadFile();
		
		if(file == null) {
			setServiceResult(new MessageResult(false, "文件不存在"));
			return;
		}

		String filePath = file.getPath(); //文件存放路径
		InputStream inputStream = new FileInputStream(file);
		
		if(inputStream == null) {
			setServiceResult(new MessageResult(false, "文件无法读取"));
			return;
		}
		
		// 解析xls文件
    	ShowInstance showInstance = ReflectOperation.getShowInstance(RequestManager.getTName(), ShowParamManager.getShowInstanceName());

		String[] strSheetNameArray = sheetName.split(",");

    	DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
    	Class<?> targetType = Class.forName(RequestManager.getTName());
    	if(detachedCriteria==null){
			detachedCriteria = DetachedCriteria.forClass(targetType);
    	}
    	
    	ForReportIFileHandlerForExcel fileHandlerForExcel = new ForReportFileHandlerForExcel();
    	String message = fileHandlerForExcel.WriteFromPathToDatabase(showInstance, "sessionFactory", 
    				tableName, filePath, strSheetNameArray, targetType, showForeignId, showHeader, 
    				deleteOld, detachedCriteria, afterIgnorSeg, startLine, startCol, insertCheckClassList,insertAddClassList);
    	
		setServiceResult(new MessageResult(true, message));
	}
	

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setShowForeignId(boolean showForeignId) {
		this.showForeignId = showForeignId;
	}

	public boolean isShowForeignId() {
		return showForeignId;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setStartLine(Integer startLine) {
		this.startLine = startLine;
	}

	public Integer getStartLine() {
		return startLine;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setDeleteOld(boolean deleteOld) {
		this.deleteOld = deleteOld;
	}

	public boolean isDeleteOld() {
		return deleteOld;
	} 

	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getAfterIgnorSeg() {
		return afterIgnorSeg;
	}


	public void setAfterIgnorSeg(String afterIgnorSeg) {
		this.afterIgnorSeg = afterIgnorSeg;
	}

}
