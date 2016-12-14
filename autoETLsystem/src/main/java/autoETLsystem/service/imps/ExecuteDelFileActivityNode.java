package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;



import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeForDeleteFile;
import autoETLsystem.dto.AutoETL_ActivityNodeForDeleteFileDetail;
import autoETLsystem.dto.AutoETL_ActivityNodeForFTPTra;
import autoETLsystem.dto.AutoETL_Workflow;
import autoETLsystem.dto.AutoETL_WorkflowPFPV;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import framework.helper.FrameworkFactory;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

public class ExecuteDelFileActivityNode {
	
	private static final String DTDATE_PARAM_KEY="DTDATE";
	
	private Map<String,Object> GetFTPInfo(AutoETL_ActivityNodeForDeleteFile autoETL_ActivityNodeForDeleteFile) throws Exception
	{
		/*AutoETL_Workflow wf=autoETL_ActivityNode.getAutoETL_Workflow();
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowPFPV.class);
		detachedCriteria.add(Restrictions.eq("autoETL_Workflow", wf));
		List<AutoETL_WorkflowPFPV> autoETL_WorkflowPFPVList = (List<AutoETL_WorkflowPFPV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(autoETL_WorkflowPFPVList.size() == 0){
			throw new Exception("未设FTP连接参数");
		}
		
		AutoETL_WorkflowPFPV autoETL_WorkflowPFPV = autoETL_WorkflowPFPVList.get(0);
		String serverPath = autoETL_WorkflowPFPV.getAutoETL_FTP().getStrServerPath();
		String hostServer = "";
		String dictionary = "";
		String userid=autoETL_WorkflowPFPV.getAutoETL_FTP().getStrUserID();
		String pwd=autoETL_WorkflowPFPV.getAutoETL_FTP().getStrPassword();*/
		
		String serverPath = autoETL_ActivityNodeForDeleteFile.getAutoETL_FTP().getStrServerPath();
		String hostServer = "";
		String dictionary = "";
		String userid=autoETL_ActivityNodeForDeleteFile.getAutoETL_FTP().getStrUserID();
		String pwd=autoETL_ActivityNodeForDeleteFile.getAutoETL_FTP().getStrPassword();
		
		if(serverPath.indexOf("/") > -1){
			hostServer = serverPath.substring(0,serverPath.indexOf("/"));
			dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
		}
		else{
			hostServer = serverPath;
		}
		
		
	    Map<String,Object> context=new HashMap<String,Object>();
	    context.put("host", hostServer);
	    context.put("dictionary", dictionary);
	    context.put("userid", userid);
	    context.put("pwd", pwd);
	    return context;
	    
	}

	public  String Execute(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception
	{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForDeleteFile.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForDeleteFile> autoETL_ActivityNodeForDeleteFileList = (List<AutoETL_ActivityNodeForDeleteFile>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(autoETL_ActivityNodeForDeleteFileList.size() == 0){
			throw new Exception("未设置结点");
		}
		AutoETL_ActivityNodeForDeleteFile autoETL_ActivityNodeForDeleteFile = autoETL_ActivityNodeForDeleteFileList.get(0);

		
		FileOperator fo=null;
		
		if(autoETL_ActivityNodeForDeleteFile.getStrServiceType()!=null && autoETL_ActivityNodeForDeleteFile.getStrServiceType().equals("2"))
		{
			fo=new FTPFileOperator();
			fo.setContext(GetFTPInfo(autoETL_ActivityNodeForDeleteFile));
		}
		else if(autoETL_ActivityNodeForDeleteFile.getStrServiceType()!=null && autoETL_ActivityNodeForDeleteFile.getStrServiceType().equals("3"))
		{
			fo=new SFTPFileOperator();
			fo.setContext(GetFTPInfo(autoETL_ActivityNodeForDeleteFile));
		}
		else{
			fo=new LocalFileOperator();
		}
		List<String> allfileList=fo.GetFileList(autoETL_ActivityNodeForDeleteFile.getStrValue());
		List<String> delFileList=GetDelFileList(autoETL_ActivityNodeForDeleteFile,activeNodeETL_WorkflowParamMVList,allfileList);
		fo.Del(autoETL_ActivityNodeForDeleteFile.getStrValue(),delFileList);
		return "";
	}
	
	private List<String> GetDelFileList(AutoETL_ActivityNodeForDeleteFile activityNodeDelInfo,
			List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList,List<String> fileList)
			throws Exception
	{
		String fileType = ShowContext.getInstance()
									.getShowEntityMap()
									.get("fileType")
									.get(activityNodeDelInfo.getStrFileType());

		
		List<String> paramFileName = FilterFileType(fileType,fileList);
		Map<String,String> paramNameValue=new HashMap<String,String>();
		List<AutoETL_ActivityNodeForDeleteFileDetail> autoETL_ActivityNodeForDeleteFileDetailList = GetDeleteFileDetailInfo(activityNodeDelInfo);

		for(AutoETL_ActivityNodeForDeleteFileDetail paramName:autoETL_ActivityNodeForDeleteFileDetailList)
		{
			paramNameValue.put(paramName.getAutoETL_Param().getStrParamName(), "");
			
		}
		if(activeNodeETL_WorkflowParamMVList != null){
			String paramName="";
        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
        		paramName=autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName();
        		if(paramNameValue.containsKey(paramName))
        				paramNameValue.put(paramName, autoETL_WorkflowParamMV.getStrValue()) ;
    		}
        }
		
		
		
		
		if(paramNameValue.containsKey(DTDATE_PARAM_KEY))
		{
			String strRealDtDate = GetFileEndDate(paramNameValue.get(DTDATE_PARAM_KEY),
													activityNodeDelInfo.getStrFreq(),
													activityNodeDelInfo.getStrFreqValue());
			paramNameValue.put(DTDATE_PARAM_KEY,strRealDtDate);
		}
		
		String compareFileName=GetCompareFileName(activityNodeDelInfo, paramNameValue,
				autoETL_ActivityNodeForDeleteFileDetailList);

		List<String> delFileList=new ArrayList<String>();
		int intNameParamCnt=autoETL_ActivityNodeForDeleteFileDetailList.size();
		for(int i=0;i<paramFileName.size();i++){
			
			String fileName ="";
			int extIndex=paramFileName.get(i).indexOf(".");
			if(extIndex>-1)
			{
				fileName=paramFileName.get(i).substring(0,extIndex);
			}
			else
			{
				fileName=paramFileName.get(i);
			}
				
			String[] fileSectionAry=fileName.split(activityNodeDelInfo.getStrFileNameSeg());
			
			if(compareFileName.compareTo(fileName)==0 && 
					fileName.length()==compareFileName.length()&& 
					fileSectionAry.length==intNameParamCnt)
			{
				delFileList.add(paramFileName.get(i));
			}

		}

		return delFileList;
	}

	private String GetCompareFileName(
			AutoETL_ActivityNodeForDeleteFile activityNodeDelInfo,
			Map<String, String> paramNameValue,
			List<AutoETL_ActivityNodeForDeleteFileDetail> autoETL_ActivityNodeForDeleteFileDetailList) {
		StringBuilder sbCompareFileName=new StringBuilder();
		for(AutoETL_ActivityNodeForDeleteFileDetail paramName:autoETL_ActivityNodeForDeleteFileDetailList)
		{
			sbCompareFileName.append(paramNameValue.get(paramName.getAutoETL_Param().getStrParamName()));
			sbCompareFileName.append(activityNodeDelInfo.getStrFileNameSeg());
		}
		sbCompareFileName.delete(sbCompareFileName.length()-1, sbCompareFileName.length());
		return sbCompareFileName.toString();
	}

	private String GetFileEndDate(String strDtDate,String strFreq,String freqValue) {
		Date dtDate=TypeParse.parseDate(strDtDate);
		int intFreq=TypeParse.parseInt(freqValue);
		Date realDTDate=SmallTools.getFreqDate(dtDate,strFreq,-1*intFreq);
		String strRealDtDate=TypeParse.parseString(realDTDate, "yyyyMMdd");
		return strRealDtDate;
	}

	private List<String> FilterFileType(String fileType,
			List<String> fileList) {
		List<String> paramFileName = new ArrayList<String>();
		for(int i=0;i<fileList.size();i++){
			
			if(fileType.equals("*")){
				paramFileName.add(fileList.get(i));
			}
			else{
				if(fileList.get(i).endsWith(fileType)){
					paramFileName.add(fileList.get(i));
				}
			}
		}
		return paramFileName;
	}

	private List<AutoETL_ActivityNodeForDeleteFileDetail> GetDeleteFileDetailInfo(
			AutoETL_ActivityNodeForDeleteFile activityNodeDelInfo)
			throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForDeleteFileDetail.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForDeleteFile", activityNodeDelInfo));
		detachedCriteria.addOrder(Order.asc("intOrder"));
		List<AutoETL_ActivityNodeForDeleteFileDetail> autoETL_ActivityNodeForDeleteFileDetailList = (List<AutoETL_ActivityNodeForDeleteFileDetail>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(autoETL_ActivityNodeForDeleteFileDetailList.size() == 0){
			throw new Exception("未设置参数取值");
		}
		return autoETL_ActivityNodeForDeleteFileDetailList;
	}
	
}
