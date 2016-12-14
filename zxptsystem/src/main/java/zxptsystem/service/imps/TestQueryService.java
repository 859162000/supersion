package zxptsystem.service.imps;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.helper.EnterpriseQueryTemplate;
import zxptsystem.helper.Field;
import zxptsystem.helper.PersonQueryTemplate;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandler;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
import framework.show.ShowField;
import framework.show.ShowFieldValue;

public class TestQueryService implements IObjectResultExecute{

	private Object serviceResult;
	private String pReporttype;
	
	protected Object getServiceResult() {
		return serviceResult;
	}

	protected void setServiceResult(Object serviceResult) {
		this.serviceResult = serviceResult;
	}

	public Object objectResultExecute() throws Exception {
		String id=RequestManager.getId().toString();
		String tName = RequestManager.getTName();
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object obj = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,null});
		SystemParam paramPath = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SystemParam.class.getName(),"all_creditFilePath",null});
		String qyOrgr = "";
		if(null!=obj){
			Object customerInfo = ReflectOperation.getFieldValue(obj, "strCustomerID");			
			String customCode = ReflectOperation.getFieldValue(customerInfo, "strCustomerID").toString();
			DateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			String date = sdf.format(ReflectOperation.getFieldValue(obj, "timeCreateDate"));  
			String path="";
			if(obj instanceof QYZXCreditReportInfo){
				qyOrgr="QY";				
			}else{
				qyOrgr="GR";
			}
			path=paramPath.getStrParamValue()+File.separator+qyOrgr+File.separator+customCode+date;
			if(pReporttype.equals("Internet_QY_GeneralReport")){
				path+=File.separator+"Internet_GeneralReport"+File.separator+"Internet_GeneralReport.html";
			}else if(pReporttype.equals("Intranet_QY_DetailReport")){
				path+=File.separator+"Intranet_DetailReport"+File.separator+"Intranet_DetailReport.html";
			}else if(pReporttype.equals("Intranet_QY_GeneralReport")){
				path+=File.separator+"Intranet_GeneralReport"+File.separator+"Intranet_GeneralReport.html";
			}else if(pReporttype.equals("Internet_GR_GeneralReport")){
				path+=File.separator+"Internet_GeneralReport"+File.separator+"Internet_GeneralReport.html";
			}else if(pReporttype.equals("Intranet_GR_GeneralReport")){
				path+=File.separator+"Intranet_GeneralReport"+File.separator+"Intranet_GeneralReport.html";
			}

			File file = new File(path);  
			long fileSize=file.length();
			
			FileInputStream fis = new FileInputStream(file);
			DownloadResult downloadResult = new DownloadResult();
			 byte[] buffer = new byte[(int) fileSize];
			 int offset = 0;  
			 int numRead = 0;  
			 while (offset < buffer.length && (numRead = fis.read(buffer, offset, buffer.length - offset)) >= 0) {  
				 offset += numRead;  
			 }  
			 fis.close();
			InputStream inputStream = new ByteArrayInputStream(buffer);
			downloadResult.setInputStream(inputStream);
			
			if(downloadResult.getInputStream() == null){
				this.setServiceResult(new MessageResult(false,"没有附件,不能下载"));
			}
			else{
				this.setServiceResult(downloadResult);
			}
		}
		return serviceResult;	
	}

	public void setpReporttype(String pReporttype) {
		this.pReporttype = pReporttype;
	}

	public String getpReporttype() {
		return pReporttype;
	}
}
