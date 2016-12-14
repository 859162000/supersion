package zxptsystem.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.EIS_IdManagement;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

public class ImportCustomerInfoService extends BaseVoidDaoResultService {

	private Map<String,String> CellFieldMap;
	List<Object> list;
	EIS_IdManagement idManagement;
	@Override
    public void init() throws Exception {
		super.init();		
		File file = RequestManager.getUploadFile();		
		if(file == null) {
			setServiceResult(new MessageResult(false, "文件不存在"));
			return;
		}
		String filePath = file.getPath(); //文件存放路径
		InputStream inputStream = new FileInputStream(file);
		
		if(inputStream == null) {
			this.setServiceResult(new MessageResult(false, "文件无法读取"));
			return;
		}
		String[] names=RequestManager.getTName().split("\\.");
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		idManagement = (EIS_IdManagement)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
				EIS_IdManagement.class.getName()
				,names[names.length-1]
				       ,null});
		
		int index=0;
		if(null != idManagement){
			index=idManagement.getCurIndex();
		}else{
			idManagement = new EIS_IdManagement();
			idManagement.setStrTableName(names[names.length-1]);
		}
		index++;
		
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		list = new ArrayList<Object>();
		for (int i = 1; sheet.getRow(i)!=null; i++) {
			row = sheet.getRow(i);
			Object newObj = Class.forName(RequestManager.getTName()).newInstance();
			
			java.text.DecimalFormat df = new java.text.DecimalFormat("000000");
			if(newObj instanceof EIS_ENTCustomernBasicInfo){
				ReflectOperation.setFieldValue(newObj, "strCustomerID","OS"+df.format(index));
			}else if(newObj instanceof EIS_PERCustomernBasicInfo){
				ReflectOperation.setFieldValue(newObj, "strCustomerID","OI"+df.format(index));
			}
			index++;
			Iterator<Map.Entry<String,String>> entries = CellFieldMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<String,String> entry = entries.next();
			    int cellIndex=Integer.parseInt(entry.getValue())-1;
			    HSSFCell cell = row.getCell(cellIndex);
			    if(null!=cell){
			    	switch(cell.getCellType()){
				    	case HSSFCell.CELL_TYPE_NUMERIC:
				    		ReflectOperation.setFieldValue(newObj, entry.getKey(), cell.getNumericCellValue());
				    		break;
				    	case HSSFCell.CELL_TYPE_STRING:
				    		String v = cell.getStringCellValue();
				    		if(entry.getKey().equals("strRegistrationType")){
				    			ReflectOperation.setFieldValue(newObj, entry.getKey(), v.split("-")[0]);
				    		}else if(entry.getKey().equals("strCertType")){
				    			ReflectOperation.setFieldValue(newObj, entry.getKey(), v.split("-")[0]);
				    		}else{
				    			ReflectOperation.setFieldValue(newObj, entry.getKey(), cell.getStringCellValue());
				    		}
				    		break;
				    	case HSSFCell.CELL_TYPE_BLANK:
				    		ReflectOperation.setFieldValue(newObj, entry.getKey(), "");
				    		break;
				    	case HSSFCell.CELL_TYPE_BOOLEAN:
				    		ReflectOperation.setFieldValue(newObj, entry.getKey(), cell.getBooleanCellValue());
				    		break;
				    	case HSSFCell.CELL_TYPE_ERROR:
				    		ReflectOperation.setFieldValue(newObj, entry.getKey(), null);
				    		break;
			    	}
			    }
			}
			list.add(newObj);
		}
		idManagement.setCurIndex(index);
	}
public Object objectResultExecute() throws Exception{		
		init();
		
		MessageResult messageResult = new MessageResult();
		
		if(null==list || list.size()==0){
			messageResult.setSuccess(false);
			messageResult.setMessage("文件无有效数据，请确认！");
		}
		if(messageResult.isSuccess()){
			for(Object obj:list){
				RequestManager.setTOject(obj);
				String strCustomerName = ReflectOperation.getFieldValue(obj, "strCustomerName").toString();
				if(this.getDefaultAddClassList() != null){
					for(String str : this.getDefaultAddClassList()){
						IAdd add = (IAdd)FrameworkFactory.CreateClass(str);
						add.Add();
					}
				}
				
				if(this.getDefaultCheckClassList() != null){
					for(String str : this.getDefaultCheckClassList()){
						ICheck check = (ICheck)FrameworkFactory.CreateClass(str);
						MessageResult currentResult = check.Check();
						if(!currentResult.isSuccess()) {
							
							messageResult.setSuccess(false);
							if(currentResult.getErrorFieldList().size()>0){
								for(ErrorField e:currentResult.getErrorFieldList()){
									if(messageResult.getMessage().equals("")){
										messageResult.setMessage(strCustomerName+":"+e.getFieldName()+":"+e.getMessage());
									}
									else{
										messageResult.setMessage(messageResult.getMessage()+"\\n"+strCustomerName+":"+strCustomerName+":"+e.getFieldName()+":"+e.getMessage());
									}
								}
							}
							if(currentResult.getMessageList().size()>0){
								for(String string:currentResult.getMessageList()){
									if(messageResult.getMessage().equals("")){
										messageResult.setMessage(strCustomerName+":"+string);
									}
									else{
										messageResult.setMessage(messageResult.getMessage()+"\\n"+strCustomerName+":"+string);
									}
								}
							}
							if(currentResult.getMessageSet().size()>0){
								for(String set:currentResult.getMessageSet()){
									if(messageResult.getMessage().equals("")){
										messageResult.setMessage(strCustomerName+":"+set);
									}
									else{
										messageResult.setMessage(messageResult.getMessage()+"\\n"+strCustomerName+":"+set);
									}
								}
							}
						}
					}
				}				
			}
		}
		if(messageResult.isSuccess()){
			try{
				IParamVoidResultExecute insertDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				insertDao.paramVoidResultExecute(new Object[]{list,null});
				
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				List<Object> list1= new ArrayList<Object>();
				list1.add(idManagement);
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{ list1, null});
				messageResult.setMessage("保存成功！");
			}catch(Exception e){
				e.printStackTrace();
				messageResult.setMessage("保存失败:\\n"+e.getMessage());
				messageResult.setSuccess(false);
			}
			
		}
		return messageResult;
	}
	public void setCellFieldMap(Map<String,String> cellFieldMap) {
		CellFieldMap = cellFieldMap;
	}

	public Map<String,String> getCellFieldMap() {
		return CellFieldMap;
	}
}
