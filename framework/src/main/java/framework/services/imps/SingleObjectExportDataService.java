package framework.services.imps;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandlerForExcel;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.imps.FileHandler;
import framework.services.interfaces.imps.FileHandlerForExcel;
import framework.show.ShowInstance;


// 主要功能：根据当前DTO创建出SQL语句和连接供DAO执行	SingleObjectDownloadService
public class SingleObjectExportDataService extends BaseObjectDaoResultService{
	private String fileName;
	
	private String sheetName;
	
	private boolean showForeignId; // 外键是否只显示id
	
	private boolean showHeader; //  是否首行导出字段名
	
	private Integer startLine; // 内容行偏移
	
	private Integer startCol; // 内容列偏移
	
	private Integer cacheLine; // 每sheet页导出数据行数
	
	private String strDateFormat; // 输出时间格式
	
	private String modelPath;
	
	private String dtoName;
	
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();

		ExportData();
	}

	/**
	 *  按检索条件导出表数据
	 */
	protected void ExportData() throws Exception {
		if(fileName == null || fileName.equals("")) {
			fileName = ServletActionContext.getServletContext().getRealPath("Download/tmp/temp.xlsx");
		}
		ShowInstance showInstance = ReflectOperation.getShowInstance(RequestManager.getTName(), ShowParamManager.getShowInstanceName());
		Class<?> type = Class.forName(RequestManager.getTName());
		Object objRet = getServiceResult();
		if(this.getDtoName()!=null){
			String tName = RequestManager.getTName();
			List<Field> columnFiledForChildren = ReflectOperation.getOneToManyField(Class.forName(tName));
			List<Field> columnFiledForFather = ReflectOperation.getColumnFieldList(Class.forName(tName));
			List<Object> exportList=new ArrayList<Object>();
			showInstance=ReflectOperation.getShowInstance(this.getDtoName(), ShowParamManager.getShowInstanceName());
			type=Class.forName(this.getDtoName());
			if(columnFiledForChildren.size()==1){
				Field field=columnFiledForChildren.get(0);
				for(Object tobjRet:(List<Object>)objRet){
					Object childValue=ReflectOperation.getFieldValue(tobjRet, field.getName());
					if(((Set<Object>)childValue).size()==0){
						Object export=Class.forName(this.getDtoName()).newInstance();
						for(Field fatherField:columnFiledForFather){
							Field tempField=ReflectOperation.getReflectField(export.getClass(), fatherField.getName());
							if(tempField!=null){
									if(ReflectOperation.getFieldValue(tobjRet, tempField.getName())!=null){
									  ReflectOperation.setFieldValue(export, tempField.getName(), ReflectOperation.getFieldValue(tobjRet, tempField.getName()));
									}
								}
						}
						exportList.add(export);
					}else{
						for(Object subObjectValue:(Set<Object>)childValue){
							Object export=Class.forName(this.getDtoName()).newInstance();
							List<Field> columnFiledForChild = ReflectOperation.getColumnFieldList(subObjectValue.getClass());
							for(Field fatherField:columnFiledForFather){
								Field tempField=ReflectOperation.getReflectField(export.getClass(), fatherField.getName());
								if(tempField!=null){
									if(ReflectOperation.getFieldValue(tobjRet, tempField.getName())!=null){
									    ReflectOperation.setFieldValue(export, tempField.getName(), ReflectOperation.getFieldValue(tobjRet, tempField.getName()));
									}
								}
							}
							for(Field childField:columnFiledForChild){
								Field tempField=ReflectOperation.getReflectField(export.getClass(), childField.getName());
								if(tempField!=null){
									if(ReflectOperation.getFieldValue(subObjectValue, tempField.getName())!=null){
									   ReflectOperation.setFieldValue(export, tempField.getName(), ReflectOperation.getFieldValue(subObjectValue, tempField.getName()));
									}
								}
							}
							exportList.add(export);
						}
					}
				}
			}
			objRet=exportList;
		}

    	IFileHandlerForExcel fileHandlerForExcel = new FileHandlerForExcel();
    	String currentName=RequestManager.getTName();
    	RequestManager.setTName(type.getName());
    	InputStream inputStream = fileHandlerForExcel.WriteFromDatabaseToInputStream(showInstance, fileName, sheetName,
    			objRet, type, cacheLine, showForeignId, showHeader, startLine, startCol, strDateFormat,modelPath);
    	RequestManager.setTName(currentName);
		DownloadResult downloadResult = new FileHandler().GetStreamResultFromInputStream(inputStream, fileName, 1024*1024*5);
		if(downloadResult.getInputStream() == null){
			this.setServiceResult(new MessageResult(false,"下载失败"));
		}
		else{
			this.setServiceResult(downloadResult);
		}
	}
	
	/**
	 *  按检索条件导出表数据(传入文件参数)
	 */
	protected void ExportData(String fileNameTemp) throws Exception {
		if(fileNameTemp == null || fileNameTemp.equals("")) {
			fileNameTemp = ServletActionContext.getServletContext().getRealPath("Download/tmp/temp.xlsx");
		}
		ShowInstance showInstance = ReflectOperation.getShowInstance(RequestManager.getTName(), ShowParamManager.getShowInstanceName());
		Class<?> type = Class.forName(RequestManager.getTName());
		Object objRet = getServiceResult();
		if(this.getDtoName()!=null){
			String tName = RequestManager.getTName();
			List<Field> columnFiledForChildren = ReflectOperation.getOneToManyField(Class.forName(tName));
			List<Field> columnFiledForFather = ReflectOperation.getColumnFieldList(Class.forName(tName));
			List<Object> exportList=new ArrayList<Object>();
			showInstance=ReflectOperation.getShowInstance(this.getDtoName(), ShowParamManager.getShowInstanceName());
			type=Class.forName(this.getDtoName());
			if(columnFiledForChildren.size()==1){
				Field field=columnFiledForChildren.get(0);
				for(Object tobjRet:(List<Object>)objRet){
					Object childValue=ReflectOperation.getFieldValue(tobjRet, field.getName());
					if(((Set<Object>)childValue).size()==0){
						Object export=Class.forName(this.getDtoName()).newInstance();
						for(Field fatherField:columnFiledForFather){
							Field tempField=ReflectOperation.getReflectField(export.getClass(), fatherField.getName());
							if(tempField!=null){
									if(ReflectOperation.getFieldValue(tobjRet, tempField.getName())!=null){
									  ReflectOperation.setFieldValue(export, tempField.getName(), ReflectOperation.getFieldValue(tobjRet, tempField.getName()));
									}
								}
						}
						exportList.add(export);
					}else{
						for(Object subObjectValue:(Set<Object>)childValue){
							Object export=Class.forName(this.getDtoName()).newInstance();
							List<Field> columnFiledForChild = ReflectOperation.getColumnFieldList(subObjectValue.getClass());
							for(Field fatherField:columnFiledForFather){
								Field tempField=ReflectOperation.getReflectField(export.getClass(), fatherField.getName());
								if(tempField!=null){
									if(ReflectOperation.getFieldValue(tobjRet, tempField.getName())!=null){
									    ReflectOperation.setFieldValue(export, tempField.getName(), ReflectOperation.getFieldValue(tobjRet, tempField.getName()));
									}
								}
							}
							for(Field childField:columnFiledForChild){
								Field tempField=ReflectOperation.getReflectField(export.getClass(), childField.getName());
								if(tempField!=null){
									if(ReflectOperation.getFieldValue(subObjectValue, tempField.getName())!=null){
									   ReflectOperation.setFieldValue(export, tempField.getName(), ReflectOperation.getFieldValue(subObjectValue, tempField.getName()));
									}
								}
							}
							exportList.add(export);
						}
					}
				}
			}
			objRet=exportList;
		}

    	IFileHandlerForExcel fileHandlerForExcel = new FileHandlerForExcel();
    	String currentName=RequestManager.getTName();
    	RequestManager.setTName(type.getName());
    	InputStream inputStream = fileHandlerForExcel.WriteFromDatabaseToInputStream(showInstance, fileNameTemp, sheetName,
    			objRet, type, cacheLine, showForeignId, showHeader, startLine, startCol, strDateFormat,modelPath);
    	RequestManager.setTName(currentName);
		DownloadResult downloadResult = new FileHandler().GetStreamResultFromInputStream(inputStream, fileNameTemp, 1024*1024*5);
		if(downloadResult.getInputStream() == null){
			this.setServiceResult(new MessageResult(false,"下载失败"));
		}
		else{
			this.setServiceResult(downloadResult);
		}
	}

	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
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
	
	public boolean isShowForeignId() {
		return showForeignId;
	}

	public void setShowForeignId(boolean showForeignId) {
		this.showForeignId = showForeignId;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public Integer getCacheLine() {
		return cacheLine;
	}

	public void setCacheLine(Integer cacheLine) {
		this.cacheLine = cacheLine;
	}

	public void setStrDateFormat(String strDateFormat) {
		this.strDateFormat = strDateFormat;
	}

	public String getStrDateFormat() {
		return strDateFormat;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}

	public String getDtoName() {
		return dtoName;
	}

}







