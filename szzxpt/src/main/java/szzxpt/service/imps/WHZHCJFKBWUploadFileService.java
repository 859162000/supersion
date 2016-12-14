package szzxpt.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import report.service.imps.CommonUpdateReportStatusService;
import szzxpt.dto.AutoDTO_WGJ_DownLoadRecord;
import szzxpt.dto.WHZHCJFKBW;
import szzxpt.dto.WHZHCJFKBWSub;


import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;

import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.DomXMLOperation;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.HandleFileOrFolders;
import framework.helper.ReflectOperation;
import framework.helper.SmallTools;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.services.imps.BaseService;
import framework.services.interfaces.MessageResult;

public class WHZHCJFKBWUploadFileService extends BaseService {
	
	private String whzh_feedBackXMLPath; 
	private Map<String ,String> dataTypeDtoRelaMap;  
	private File uploadFileTemp; 
	private String uploadFileFileName; 
	private Map<String,String> statusMap;
	private List<File> fileList;
	private List<Object> objList;
	private String JRJGDM;

	public void initSuccessResult() throws Exception{
		try{
			MessageResult messageResult =readXML();
			this.setServiceResult(messageResult);
		}catch(Exception ex){
			this.setServiceResult(new MessageResult("解析反馈报文异常"));
		}
	}
	
	@SuppressWarnings("unchecked")
	public MessageResult readXML() throws Exception{
		try{
			uploadFileTemp=(File) ActionContext.getContext().getSession().get("uploadFileTemp");	
			if(uploadFileTemp==null){
				return new MessageResult(false,"解析文件不能为空");
			}
			
			if(ActionContext.getContext().getSession().get("uploadFileFileName")!=null){
				uploadFileFileName=(String) ActionContext.getContext().getSession().get("uploadFileFileName");
			}
			
			String ErrorMessage=(String) ActionContext.getContext().getSession().get("ErrorMessage");
			if(ErrorMessage!=null && !ErrorMessage.equals("")){
				return new MessageResult(false,ErrorMessage);
			}
			
			String msg="";
			
			if(uploadFileFileName!=null){
				if(uploadFileFileName.toUpperCase().endsWith("ERR.XML")){//单个解析
					InputStream inputStream = new FileInputStream (uploadFileTemp);
					msg+=reportMessageXML(inputStream,uploadFileFileName);
					inputStream.close();
				}
				else if(uploadFileFileName.toUpperCase().endsWith("ERR.ZIP")){//多个解析
					fileList = SmallTools.unZipFiles(uploadFileTemp, "");
					msg=jxxml(fileList);
				}
			}
			
			if(!StringUtils.isBlank(msg)){
				return new MessageResult(msg);
			}
			else{
				//统计回执状态
				CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
				List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
				commonStatus.commonUpdateReportStatus(objList, instInfoSubList);
				return new MessageResult("解析反馈报文成功");
			}
		}
		finally{
			if(ActionContext.getContext().getSession().get("uploadFileTemp")!=null){
				if(uploadFileTemp.exists()){
					uploadFileTemp.delete();
				}
				ActionContext.getContext().getSession().put("uploadFileTemp",null);
			}
			if(ActionContext.getContext().getSession().get("uploadFileFileName")!=null){
				ActionContext.getContext().getSession().put("uploadFileFileName",null);
			}
			if(ActionContext.getContext().getSession().get("ErrorMessage")!=null){
				ActionContext.getContext().getSession().put("ErrorMessage",null);
			}
		}
	}
	
	//自动解析
	public boolean autoReadXML() throws Exception{
		SystemParam feedBackXMLPath = HelpTool.getSystemParam("whzh_feedBackXMLPath");
		if(feedBackXMLPath!=null){
			whzh_feedBackXMLPath=feedBackXMLPath.getStrParamValue();
		}else{
			return false;
		}
		
		List<File> fileList=new ArrayList<File>();
		HandleFileOrFolders.readfile(whzh_feedBackXMLPath,fileList);
		if(fileList.size()<=0){
			return false;
		}
		String msg=jxxml(fileList);
	
		if(!StringUtils.isBlank(msg)){
			return false;
		}
		else{
			//统计回执状态
			CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
			List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
			commonStatus.commonUpdateReportStatus(objList, instInfoSubList);
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String jxxml(List<File> fileList) throws Exception{
		if(dataTypeDtoRelaMap==null){
			dataTypeDtoRelaMap=new HashMap<String ,String>();
			dataTypeDtoRelaMap.put("ACCCA", "szzxpt.dto.AutoDTO_WGJ_ACC_CA");
			dataTypeDtoRelaMap.put("ACCCB", "szzxpt.dto.AutoDTO_WGJ_ACC_CB"); 
		}
		String msg="";
		String strUNo=null;
		Set<String> set=new HashSet<String>();
		
		for (File file : fileList) {
			if(file.exists()){
				if(file.getName().toString().toUpperCase().endsWith("ERR.XML")){
					String intOrder=file.getName().substring(file.getName().indexOf("ERR")-8,file.getName().indexOf("ERR"));
					String dtoName=dataTypeDtoRelaMap.get(file.getName().substring(0,5));
					set.add(dtoName);
					if(StringUtils.isBlank(strUNo)){
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			    		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_WGJ_DownLoadRecord.class);
			    		detachedCriteria.add(Restrictions.eq("strDownLoadNo", intOrder));
			    		List<AutoDTO_WGJ_DownLoadRecord> autoDTO_WGJ_DownLoadRecordList = (List<AutoDTO_WGJ_DownLoadRecord>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
			    		if(autoDTO_WGJ_DownLoadRecordList.size()>0){
			    			strUNo=autoDTO_WGJ_DownLoadRecordList.get(0).getStrUNO();
			    		}
					}
					
					InputStream inputStream = new FileInputStream (file);
					msg+=reportMessageXML( inputStream, file.getName());
				}else{
					//接口控制文件有错，未能成功上报
					WHZHCJFKBW wHZHCJFKBW=new WHZHCJFKBW();
					wHZHCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
					wHZHCJFKBW.setStrBWCCWJM(file.getName().toString());
					wHZHCJFKBW.setStrDiscription("接口控制文件有错");
					if(wHZHCJFKBW!=null){
						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{wHZHCJFKBW,null});
					}
				}
			}
		}
		
		if(!StringUtils.isBlank(strUNo)){
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_WGJ_DownLoadRecord.class);
    		detachedCriteria.add(Restrictions.eq("strUNO", strUNo));
    		List<AutoDTO_WGJ_DownLoadRecord> objList = (List<AutoDTO_WGJ_DownLoadRecord>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
    		
    		for(AutoDTO_WGJ_DownLoadRecord obj :objList){
    			if(!set.contains(obj.getStrDtoName())){
    				 singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    	    		 detachedCriteria = DetachedCriteria.forClass(Class.forName(obj.getStrDtoName()));
    	    		 detachedCriteria.add(Restrictions.eq("XZXH", obj.getStrDownLoadNo()));
    	    		 detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));//已报送
    	    		 List<Object> feedlist = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
    	    		
    	    		for(Object feedObj :feedlist){
    	    			ReflectOperation.setFieldValue(feedObj, "RPTFeedbackType", "2");//回执成功
    	    		}
    	    		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
    				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{feedlist,null});
    			}
    		}
		}
		return msg;
	}

	@SuppressWarnings({ "unchecked" })
	public String reportMessageXML(InputStream inputStream,String fileName) throws Exception{
		
		JRJGDM=fileName.substring(0, 13);
		WHZHCJFKBW wHZHCJFKBW=new WHZHCJFKBW();
		wHZHCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
		wHZHCJFKBW.setStrBWCCWJM(fileName);
		String msg="";
		
		Document document = null;
    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder documentBuilder = null;
		
    	try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(inputStream);
		}
		catch (Exception ex) {
			ExceptionLog.CreateLog(ex);
		}
		Element msgNameElement = (Element)document.getElementsByTagName("MSG").item(0);
		
		String strBS=fileName.substring(0,5);
		if(strBS.indexOf("ACCTT")>-1){//控制文件
			
			Element currentTOTALFILES = DomXMLOperation.getElementByName(msgNameElement,"TOTALFILES");
			String strTOTALFILESValue=DomXMLOperation.getElementValue(currentTOTALFILES);//总文件数
			String strDiscription="总文件数："+strTOTALFILESValue+",";
			
			Element currentFILES = DomXMLOperation.getElementByName(msgNameElement,"FILES");
			Element[] currentFILENAME= DomXMLOperation.getElementsByName(currentFILES,"FILENAME");
			
			String strFILENAMEValue="";
			if(currentFILENAME!=null && currentFILENAME.length>0){
				for (int i = 0; i < currentFILENAME.length; i++) {
					strFILENAMEValue += DomXMLOperation.getElementValue(currentFILENAME[i])+",";//文件名
				}
			}
			if(!strTOTALFILESValue.equals("0")){
				strDiscription+="文件名："+strFILENAMEValue;
				
				if(strTOTALFILESValue.equals("1")){
					
					Map<String ,String> dataTypeDtoRelaMapTemp=new HashMap<String, String>();
					for (Map.Entry<String, String> entry:dataTypeDtoRelaMap.entrySet()) {
						dataTypeDtoRelaMapTemp.put(entry.getKey(), entry.getValue());
					}
					
					dataTypeDtoRelaMapTemp.remove(strFILENAMEValue.substring(0, 5));
					
					for (Map.Entry<String, String> entry : dataTypeDtoRelaMapTemp.entrySet()) {
						
						String intOrder=fileName.substring(fileName.indexOf("ERR")-8,fileName.indexOf("ERR"));
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(entry.getValue()));
						detachedCriteria.add(Restrictions.eq("XZXH", intOrder));
						detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));
						objList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
						
						for (Object obj : objList) {
							ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "2");	
						}
						
						if(objList.size()>0){
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objList,null});
						}
					}
				}
				
			}else if(strTOTALFILESValue.equals("0")){//改变所有数据状态为回执成功
				String intOrder=fileName.substring(fileName.indexOf("ERR")-8,fileName.indexOf("ERR"));
				
				for (Map.Entry<String, String> entry:dataTypeDtoRelaMap.entrySet()) {
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(entry.getValue()));
					detachedCriteria.add(Restrictions.eq("XZXH", intOrder));
					detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));
					objList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
					
					for (Object obj : objList) {
						ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "2");	
					}
					
					if(objList.size()>0){
						IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objList,null});
					}
				}
			} 
			
			if(strDiscription.endsWith(",")){
				strDiscription=strDiscription.substring(0, strDiscription.length()-1);
			}
			
			wHZHCJFKBW.setStrDiscription(strDiscription);
			//20151107 update
			if(wHZHCJFKBW!=null){
 				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
 				singleObjectSaveDao.paramVoidResultExecute(new Object[]{wHZHCJFKBW,null});
 			}
			
		}else{//数据文件
			
			String intOrder=fileName.substring(fileName.indexOf("ERR")-8,fileName.indexOf("ERR"));
			Element currentfileNameElement = DomXMLOperation.getElementByName(msgNameElement,"CURRENTFILE");
			String currentfile=DomXMLOperation.getElementValue(currentfileNameElement);//文件类型
			
			String[] strBussNo=new String[]{};
			if(currentfile.equals("ACCCA")){
				strBussNo =new String[]{"BRANCH_CODE","accountNO","CURRENCY_code"};
			}else if(currentfile.equals("ACCCB")){
				strBussNo =new String[]{"BRANCH_CODE","accountNO","CURRENCY_code","deal_date"};
			}							
			
			String dtoName="";
			if(dataTypeDtoRelaMap.containsKey(currentfile)){
				 dtoName=dataTypeDtoRelaMap.get(currentfile); //得到表名
			}else {
				return "";
			}
			
			Element formaterrsElement = DomXMLOperation.getElementByName(msgNameElement,"FORMATERRS");
			int formaterrNum=0;
			if(DomXMLOperation.getElementValue(formaterrsElement)!=null){
				formaterrNum=Integer.parseInt(DomXMLOperation.getElementValue(formaterrsElement));//文件格式错误数
			}
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoName));
			detachedCriteria.add(Restrictions.eq("XZXH", intOrder));
			detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));
			objList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });

			Map<String ,Object> objErrList=new HashMap<String, Object>();
			
			String errMsg="";//错误Meg
			if(formaterrNum>0){//存在文件格式错误
				Element formatsElement = DomXMLOperation.getElementByName(msgNameElement,"FORMATS");//文件格式错误
	         	Element[] formatElement = DomXMLOperation.getElementsByName(formatsElement,"FORMAT");
	         	for(int a=0;a<formatElement.length;a++){
	         		if(a!=0){
	     				errMsg+=",";
	     			}
	         		String errdesc=DomXMLOperation.getElementValue(formatElement[a]);
	         		errMsg+=errdesc;
	         	}
	         	String strDiscription="文件格式错误数："+formaterrNum+",文件格式错误描述："+errMsg;
	         	if(strDiscription.endsWith(",")){
	         		strDiscription=strDiscription.substring(0, strDiscription.length()-1);
	         	}
	         		
	         	wHZHCJFKBW.setStrDiscription(strDiscription);

	         	//20151107 update
				if(wHZHCJFKBW!=null){
	 				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
	 				singleObjectSaveDao.paramVoidResultExecute(new Object[]{wHZHCJFKBW,null});
	 			}
	         	
	    		for(Object obj:objList){//回执失败,联动改变其它状态
	    			ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "3");
	    			if(statusMap!=null){
						for (Map.Entry<String, String>  entry: statusMap.entrySet()) {
							ReflectOperation.setFieldValue(obj, entry.getKey(), entry.getValue());
						}
					}
	     		}
	         	
			}else{//文件格式正确
				Element TOTALRECORDS = DomXMLOperation.getElementByName(msgNameElement,"TOTALRECORDS");
				int intTOTALRECORDS=Integer.parseInt(DomXMLOperation.getElementValue(TOTALRECORDS));//总记录数
				
				Element SUCRECORDS = DomXMLOperation.getElementByName(msgNameElement,"SUCRECORDS");
				int intSUCRECORDS=0;
				if(SUCRECORDS!=null){
					intSUCRECORDS=Integer.parseInt(DomXMLOperation.getElementValue(SUCRECORDS));//成功记录数
				}
				
				Element FALRECORDS = DomXMLOperation.getElementByName(msgNameElement,"FALRECORDS");
				int intFALRECORDS=0;
				if(FALRECORDS!=null){
					intFALRECORDS=Integer.parseInt(DomXMLOperation.getElementValue(FALRECORDS));//失败记录数
				}
				
				String strDiscription="总记录数："+intTOTALRECORDS+",成功记录数："+intSUCRECORDS+",失败记录数:"+intFALRECORDS;
				wHZHCJFKBW.setStrDiscription(strDiscription);
				
				//20151107 update
				if(wHZHCJFKBW!=null){
	 				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
	 				singleObjectSaveDao.paramVoidResultExecute(new Object[]{wHZHCJFKBW,null});
	 			}
				
				if(intFALRECORDS>0){
					Element recodesNameElement = DomXMLOperation.getElementByName(msgNameElement,"ERRRECORDS");
		         	Element[] recShowsElement = DomXMLOperation.getElementsByName(recodesNameElement,"REC");

		         	for(int j=0;j<recShowsElement.length;j++){
		         		Element bussnoNameElement = DomXMLOperation.getElementByName(recShowsElement[j],"BUSSNO");
		         		String bussno=DomXMLOperation.getElementValue(bussnoNameElement);//数据自编码
		         		Element errfieldsNameElement = DomXMLOperation.getElementByName(recShowsElement[j],"ERRFIELDS");
		         		Element[] errElement = DomXMLOperation.getElementsByName(errfieldsNameElement,"ERR");

		         		for(int i=0;i<errElement.length;i++){
		         			Element errfieldElement = DomXMLOperation.getElementByName(errElement[i],"ERRFIELD");
		         			Element errfieldcnElement = DomXMLOperation.getElementByName(errElement[i],"ERRFIELDCN");
		         			Element errdescElement = DomXMLOperation.getElementByName(errElement[i],"ERRDESC");
		         			
		         			String errfield=DomXMLOperation.getElementValue(errfieldElement);
		         			String errfieldcn=DomXMLOperation.getElementValue(errfieldcnElement);
		         			String errdesc=DomXMLOperation.getElementValue(errdescElement);
		         			
		         			WHZHCJFKBWSub wHZHCJFKBWSub=new WHZHCJFKBWSub();
		         			wHZHCJFKBWSub.setWHZHCJFKBW(wHZHCJFKBW);
		         			wHZHCJFKBWSub.setBUSSNO(bussno);
		         			wHZHCJFKBWSub.setERRFIELD(errfield);
		         			wHZHCJFKBWSub.setERRFIELDCN(errfieldcn);
		         			wHZHCJFKBWSub.setERRDESC(errdesc);
		         			
		         			if(wHZHCJFKBWSub.getWHZHCJFKBW()!=null){
		         				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		         				singleObjectSaveDao.paramVoidResultExecute(new Object[]{wHZHCJFKBWSub,null});
		         			}
		             	}
		         		
		         		for(Object obj:objList){
		         			String values="";
		         			for(String strField:strBussNo){
		         				values += ReflectOperation.getFieldValue(obj,strField).toString()+",";
		         			}
		         			if(values.endsWith(",")){
		         				values=values.substring(0, values.length()-1);
		         			}
		         			
		         			if(values.equals(bussno)){
		         				objErrList.put(values ,obj);
		         				ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "3");
         						if(statusMap!=null){
         							for (Map.Entry<String, String>  entry: statusMap.entrySet()) {
         								ReflectOperation.setFieldValue(obj, entry.getKey(), entry.getValue());
         							}
         						}
         						
         						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
         						detachedCriteria = DetachedCriteria.forClass(WHZHCJFKBWSub.class);
         						detachedCriteria.add(Restrictions.eq("BUSSNO", bussno));
         						List<WHZHCJFKBWSub> wHZHCJFKBWSubList = (List<WHZHCJFKBWSub>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
         						
         						String strId = ReflectOperation.getPrimaryKeyValue(obj).toString();
         						for (WHZHCJFKBWSub whzhcjfkbwSub : wHZHCJFKBWSubList) {
         							whzhcjfkbwSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+dtoName+".action?id=" + strId);
         							
         							IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
         							singleObjectUpdateDao.paramVoidResultExecute(new Object[]{whzhcjfkbwSub,null});
         						}
		         			}
		         		}
		         	}
				}
				
	         	for(Object varObj:objList){
	         		String values="";
	         		for(String strFieldName:strBussNo){
	         			values +=ReflectOperation.getFieldValue(varObj,strFieldName).toString()+",";
	         		}
	         		if(values.endsWith(",")){
	         			values=values.substring(0, values.length()-1);
	         		}
	         		
	         		if(!objErrList.containsKey(values)){
         				ReflectOperation.setFieldValue(varObj, "RPTFeedbackType", "2");	
         			}
	         	}
			}	
			if(objList.size()>0){
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objList,null});
			}
		}
		
		return msg;
	}

	public void setDataTypeDtoRelaMap(Map<String ,String> dataTypeDtoRelaMap) {
		this.dataTypeDtoRelaMap = dataTypeDtoRelaMap;
	}

	public Map<String ,String> getDataTypeDtoRelaMap() {
		return dataTypeDtoRelaMap;
	}

	public String getWhzh_feedBackXMLPath() {
		return whzh_feedBackXMLPath;
	}

	public void setWhzh_feedBackXMLPath(String whzhFeedBackXMLPath) {
		whzh_feedBackXMLPath = whzhFeedBackXMLPath;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public Map<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	
}
