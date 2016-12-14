package zxptsystem.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import zxptsystem.dto.GRZXQueryLog;
import zxptsystem.dto.QYZXQueryLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandlerForExcel;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandlerForExcel;
import framework.show.ShowContext;

public class ZXQueryExportLogService extends BaseObjectDaoResultService{

	private Map<String,String> Cell2FieldMap;
	
	@Override
	public void initSuccessResult() throws Exception {
		
		ExportData();
	}
	private void ExportData() throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		List<Object> data = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{LogicParamManager.getDetachedCriteria(),null});
		
		Object tObj=Class.forName(RequestManager.getTName()).newInstance();
		String contextPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
		String modelPath=contextPath;
		String destPath=contextPath;
		if(tObj instanceof QYZXQueryLog){
			modelPath+="Download"+File.separatorChar+"ZXCX"+File.separatorChar+"QYZXLogTemplate.xls";
			destPath+="Download"+File.separatorChar+"QYZXLogTemplate.xls";
		}else if(tObj instanceof GRZXQueryLog){
			modelPath+="Download"+File.separatorChar+"ZXCX"+File.separatorChar+"GRZXLogTemplate.xls";
			destPath+="Download"+File.separatorChar+"GRZXLogTemplate.xls";
		}
		
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(modelPath)));
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowIndex=0;
		Map<String, String> QueryType=ShowContext.getInstance().getShowEntityMap().get("strQueryType");
		for(Object obj:data){
			rowIndex++;
			for (Map.Entry<String, String> entry : this.getCell2FieldMap().entrySet()) {
				int cellIndex=Integer.parseInt(entry.getKey())-1;
				if(entry.getValue().equals("strQueryType") && null!=ReflectOperation.getFieldValue(obj, "strQueryType")){
					ReflectOperation.setFieldValue(obj, "strQueryType", QueryType.get(ReflectOperation.getFieldValue(obj, "strQueryType").toString()));
				}
				
				String[] fields=entry.getValue().split("\\.");
				Object v=null;
				if(null!=obj){
					for(String field:fields){
						if(null==v){
							v=ReflectOperation.getFieldValue(obj, field);
							if(null==v){
								break;
							}
						}else{
							v=ReflectOperation.getFieldValue(v, field);
						}					
					}
				}
				
				if(null!=v){
					HSSFRow curRow=sheet.getRow(rowIndex);
					if(null==curRow){
						curRow=sheet.createRow(rowIndex);
					}
					HSSFCell curCell=curRow.getCell(cellIndex);
					if(null==curCell){
						curCell=curRow.createCell(cellIndex);
					}
					
					if(v instanceof Timestamp){
						curCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(v));  
					}else{
						curCell.setCellValue(v.toString());
					}
				}				
			}
		}
		
		FileOutputStream out = new FileOutputStream(destPath);  
		wb.write(out);
		out.close();
		
		IFileHandlerForExcel fileHandlerForExcel = new FileHandlerForExcel();    	
    	DownloadResult downloadResult = fileHandlerForExcel.GetStreamResultFromPath("Download"+File.separatorChar+"QYZXLogTemplate.xls", 2*1024*1024);
    	
		if(downloadResult.getInputStream() == null){
			this.setServiceResult(new MessageResult(false,"下载失败"));
		}
		else{
			this.setServiceResult(downloadResult);
		}
	}
	public void setCell2FieldMap(Map<String,String> cell2FieldMap) {
		Cell2FieldMap = cell2FieldMap;
	}
	public Map<String,String> getCell2FieldMap() {
		return Cell2FieldMap;
	}
}
