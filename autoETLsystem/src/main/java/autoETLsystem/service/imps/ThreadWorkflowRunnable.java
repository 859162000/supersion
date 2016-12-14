package autoETLsystem.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Table;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldV;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVCa;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVCo;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVVCa;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVVCon;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldViewV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCT;
import autoETLsystem.dto.AutoETL_ActivityNodeForCV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCalculate;
import autoETLsystem.dto.AutoETL_ActivityNodeForCheck;
import autoETLsystem.dto.AutoETL_ActivityNodeForExcel;
import autoETLsystem.dto.AutoETL_ActivityNodeForExcelC;
import autoETLsystem.dto.AutoETL_ActivityNodeForFTPTra;
import autoETLsystem.dto.AutoETL_ActivityNodeForFile;
import autoETLsystem.dto.AutoETL_ActivityNodeForFileC;
import autoETLsystem.dto.AutoETL_ActivityNodeForFileF;
import autoETLsystem.dto.AutoETL_ActivityNodeForJava;
import autoETLsystem.dto.AutoETL_ActivityNodeForKettle;
import autoETLsystem.dto.AutoETL_ActivityNodeForLogicPCheck;
import autoETLsystem.dto.AutoETL_ActivityNodeForMail;
import autoETLsystem.dto.AutoETL_ActivityNodeForMsg;
import autoETLsystem.dto.AutoETL_ActivityNodeForP;
import autoETLsystem.dto.AutoETL_ActivityNodeForPro;
import autoETLsystem.dto.AutoETL_ActivityNodeForProceTranslateWithCheck;
import autoETLsystem.dto.AutoETL_ActivityNodeForRptCheck;
import autoETLsystem.dto.AutoETL_ActivityNodeForSql;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlC;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlCR;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlEx;
import autoETLsystem.dto.AutoETL_ActivityNodeForSummary;
import autoETLsystem.dto.AutoETL_ActivityNodeForTotalTableState;
import autoETLsystem.dto.AutoETL_ActivityNodeForWarning;
import autoETLsystem.dto.AutoETL_ActivityNodeLog;
import autoETLsystem.dto.AutoETL_ActivityNodeParam;
import autoETLsystem.dto.AutoETL_ActivityNodeProcP;
import autoETLsystem.dto.AutoETL_ActivityNodeRelationF;
import autoETLsystem.dto.AutoETL_ActivityNodeRelationV;
import autoETLsystem.dto.AutoETL_MAIL;
import autoETLsystem.dto.AutoETL_MSG;
import autoETLsystem.dto.AutoETL_Workflow;
import autoETLsystem.dto.AutoETL_WorkflowAE;
import autoETLsystem.dto.AutoETL_WorkflowLog;
import autoETLsystem.dto.AutoETL_WorkflowPFOPV;
import autoETLsystem.dto.AutoETL_WorkflowPFPV;
import autoETLsystem.dto.AutoETL_WorkflowPFPVDetail;
import autoETLsystem.dto.AutoETL_WorkflowPP;
import autoETLsystem.dto.AutoETL_WorkflowPSDV;
import autoETLsystem.dto.AutoETL_WorkflowParamMV;
import autoETLsystem.dto.AutoETL_WorkflowSQLParam;
import autoETLsystem.helper.HttpClientUtil;
import autoETLsystem.service.interfaces.IActivityNodeForJavaExtend;
import autoETLsystem.service.procese.AutoDTOCountFlowStatusProcess;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import coresystem.dto.UserInfo;
import extend.dto.AutoETL_Param;
import extend.dto.AutoETL_Procedure;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.HandleFileOrFolders;
import framework.helper.ReflectOperation;
import framework.helper.SqlConstructor;
import framework.helper.SqlConstructor.ConditionClass;
import framework.interfaces.ApplicationDataCache;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.reportCheck.CheckContext;
import framework.reportCheck.CheckFieldBasic;
import framework.reportCheck.CheckFieldCaseWhen;
import framework.reportCheck.CheckFieldEffective;
import framework.reportCheck.CheckFieldLine;
import framework.reportCheck.CheckFieldOr;
import framework.reportCheck.CheckFieldParamField;
import framework.reportCheck.CheckInstance;
import framework.reportCheck.CheckTable;
import framework.reportCheck.CheckUniqueField;
import framework.security.SecurityContext;
import framework.services.interfaces.IFileHandlerForExcel;
import framework.services.interfaces.IFileHandlerForText;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.services.interfaces.imps.FileHandlerForExcel;
import framework.services.interfaces.imps.FileHandlerForText;
import framework.show.ShowContext;
import framework.show.ShowInstance;

public class ThreadWorkflowRunnable implements Runnable{
	
	private UserInfo loginUserInfo;

	private AutoETL_Workflow autoETL_Workflow;
	
	private String autoETl_WorkflowId;
	
	private boolean isAuto;
	
	private AutoETL_WorkflowLog autoETL_WorkflowLog;
	
	private List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList;
	
	private Set<Integer> groupSet;
	
	private String strSessionFactory;
	
	public Set<Integer> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Set<Integer> groupSet) {
		this.groupSet = groupSet;
	}
	
	private List<AutoETL_WorkflowParamMV> currentAutoETL_WorkflowParamMVList;

	private String currentWorkflowParam;
	
	private String defaultOracleProcedureParamName;
		
	@SuppressWarnings("unchecked")
	public boolean initWorkflowParam() throws Exception{
		try{
			this.autoETL_WorkflowParamMVList = initWorkflowParamMVList();
			if(this.isAuto && this.autoETL_WorkflowParamMVList.size() == 0){
				return false;
			}
			
			this.groupSet = new HashSet<Integer>();
			for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : autoETL_WorkflowParamMVList){
				this.groupSet.add(autoETL_WorkflowParamMV.getIntGroup());
			}
			if(this.groupSet.size() == 0){
				this.groupSet.add(1);
			}
			
			return true;
		}
		catch(Exception ex){
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowLog.class);
			detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
			detachedCriteria.add(Restrictions.isNull("strParam"));
			detachedCriteria.add(Restrictions.eq("strResultType", "3"));
			detachedCriteria.addOrder(Order.desc("dtDate"));
			List<AutoETL_WorkflowLog> autoETL_WorkflowLogList = (List<AutoETL_WorkflowLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			boolean isCreateLog = false;
			if(autoETL_WorkflowLogList.size() == 0){
				isCreateLog = true;
			}
			else{
				if((new Date().getTime() - autoETL_WorkflowLogList.get(0).getDtDate().getTime())/60000 >= autoETL_Workflow.getErrorWaitTime()){
					isCreateLog = true;
				}
			}
			
			if(isCreateLog){
				autoETL_WorkflowLog = new AutoETL_WorkflowLog();
				autoETL_WorkflowLog.setAutoETL_Workflow(autoETL_Workflow);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				autoETL_WorkflowLog.setDtDate(simpleDateFormat.format(new Date()));
				autoETL_WorkflowLog.setStrContent("获取参数值异常");
				if(this.isAuto){
					autoETL_WorkflowLog.setStrOperationType("2");
					autoETL_WorkflowLog.setStrUserName("应用服务器");
				}
				else{
					autoETL_WorkflowLog.setStrOperationType("1");
					autoETL_WorkflowLog.setStrUserName(loginUserInfo.getStrUserName());
				}
				autoETL_WorkflowLog.setStrResultType("3");
				
				if(!StringUtils.isBlank(ex.getMessage())){
					autoETL_WorkflowLog.setStrDiscription(ex.getMessage());
				}
				else{
					ExceptionLog.CreateLog(ex);
				}
				
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{autoETL_WorkflowLog,this.strSessionFactory});
			}
			
			return false;
		}
	}

	public String getParamStr(List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList){
		String strParam = "";
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : autoETL_WorkflowParamMVList){
			strParam += autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName() + ":" + autoETL_WorkflowParamMV.getStrValue() + ",";
		}
		if(autoETL_WorkflowParamMVList.size() > 0){
			strParam = strParam.substring(0, strParam.length() - 1);
		}
		if(StringUtils.isBlank(strParam)){
			strParam = null;
		}
		return strParam;
	}
	
	public List<AutoETL_WorkflowParamMV> getAutoETL_WorkflowParamMVListByGroup (Integer intGroup){
		List<AutoETL_WorkflowParamMV> groupAutoETL_WorkflowParamMVList = new ArrayList<AutoETL_WorkflowParamMV>();
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : autoETL_WorkflowParamMVList){
			if(autoETL_WorkflowParamMV.getIntGroup().equals(intGroup)){
				groupAutoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
			}
		}
		return groupAutoETL_WorkflowParamMVList;
	}
	
	private String getProcedureSql(AutoETL_Procedure autoETL_Procedure,List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList,boolean isSelect){

	    String strProcedureParam = "";
	    int procedureParamCount = 0;
	    if(autoETL_WorkflowParamMVList == null || autoETL_WorkflowParamMVList.size() == 0){
	    	
	    }
	    else{
	    	procedureParamCount += autoETL_WorkflowParamMVList.size();
	    }
	    if(autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("2") && isSelect){
	    	procedureParamCount ++;
	    }
	    if(procedureParamCount > 0){
	    	for(int i=0;i<procedureParamCount;i++){
		    	strProcedureParam += "?,";
		    }
	    	strProcedureParam = strProcedureParam.substring(0,strProcedureParam.length() -1);
	    }
		
	    String strProcedureSql = "{call "+ autoETL_Procedure.getStrProcedureName() +"(" + strProcedureParam + ")}";
	    
		return strProcedureSql;
	}
	
	private Map<String,Object> getProcedureParam(AutoETL_Procedure autoETL_Procedure,List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList,boolean isSelect){
		Map<String,Object> procedureParam = new HashMap<String,Object>();
		if(autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("2") && isSelect){//rt
			procedureParam.put(defaultOracleProcedureParamName, null);
		}
		if(autoETL_WorkflowParamMVList != null){
			for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : autoETL_WorkflowParamMVList){
				procedureParam.put(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
			}
		}
		
		return procedureParam;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> getProcedureResult(AutoETL_Procedure autoETL_Procedure,List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute procedureListMapDao = null;
		String strProcedureSql = getProcedureSql(autoETL_Procedure,autoETL_WorkflowParamMVList,true);
		Map<String,Object> procedureParam = getProcedureParam(autoETL_Procedure,autoETL_WorkflowParamMVList,true);
		if(autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("1")
			|| autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("3")
			|| autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("4")){
			procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("sqlserverProcedureListMapDao");
		}
		else if(autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("2")){
			procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("oracleProcedureListMapDao");
		}
		List<Map<String,Object>> list = (List<Map<String,Object>>)procedureListMapDao.paramObjectResultExecute(new Object[]{strProcedureSql,procedureParam,autoETL_Procedure.getAutoETL_DataSource().getSessionFactory()});
		return list;
	}
	
	private ResultSet getProcedureResultSet(AutoETL_Procedure autoETL_Procedure,List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute procedureListMapDao = null;
		String strProcedureSql = getProcedureSql(autoETL_Procedure,autoETL_WorkflowParamMVList,true);
		Map<String,Object> procedureParam = getProcedureParam(autoETL_Procedure,autoETL_WorkflowParamMVList,true);
		if(autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("1")
				|| autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("3")
				|| autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("4")){
			procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("sqlserverProcedureResultSetDao");
		}
		else if(autoETL_Procedure.getAutoETL_DataSource().getStrDatabaseType().equals("2")){
			procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("oracleProcedureResultSetDao");
		}
		ResultSet resultSet = (ResultSet)procedureListMapDao.paramObjectResultExecute(new Object[]{strProcedureSql,procedureParam,autoETL_Procedure.getAutoETL_DataSource().getSessionFactory()});
		return resultSet;
	}
	
	private void doProcedureResult(AutoETL_Procedure autoETL_Procedure,List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList) throws Exception{
		IParamVoidResultExecute doprocedureDao = null;
		String strProcedureSql = getProcedureSql(autoETL_Procedure,autoETL_WorkflowParamMVList,false);
		Map<String,Object> procedureParam = getProcedureParam(autoETL_Procedure,autoETL_WorkflowParamMVList,false);
		doprocedureDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("excuteProcedureDao");
		doprocedureDao.paramVoidResultExecute(new Object[]{strProcedureSql,procedureParam,autoETL_Procedure.getAutoETL_DataSource().getSessionFactory()});
	}
	

	@SuppressWarnings("unchecked")
	private List<AutoETL_WorkflowParamMV> initWorkflowParamMVList() throws Exception{
		if(this.isAuto){
			List<AutoETL_WorkflowParamMV> autoETL_WorkflowParamMVList = new ArrayList<AutoETL_WorkflowParamMV>();
			//存储过程工作流
			if(this.autoETL_Workflow.getStrParamValueType().equals("1")){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowPP.class);
				detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
				List<AutoETL_WorkflowPP> autoETL_WorkflowPPList = (List<AutoETL_WorkflowPP>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				if(autoETL_WorkflowPPList.size() == 0){
					throw new Exception("未设置存储过程取参数值");
				}
				
				String autoProcedureID = null;
				AutoETL_Procedure autoETL_Procedure = null;
				for(AutoETL_WorkflowPP autoETL_WorkflowPP : autoETL_WorkflowPPList){
					if(autoProcedureID == null){
						autoProcedureID = autoETL_WorkflowPP.getAutoETL_Procedure().getAutoProcedureID();
						autoETL_Procedure = autoETL_WorkflowPP.getAutoETL_Procedure();
					}
					else{
						if(!autoProcedureID.equals(autoETL_WorkflowPP.getAutoETL_Procedure().getAutoProcedureID())){
							throw new Exception("只允许一个存储过程取值");
						}
					}
				}

				List<Map<String,Object>> list = getProcedureResult(autoETL_Procedure,null);
				
				for(int i=0;i<list.size();i++){
					for(AutoETL_WorkflowPP autoETL_WorkflowPP : autoETL_WorkflowPPList){
						AutoETL_WorkflowParamMV autoETL_WorkflowParamMV = new AutoETL_WorkflowParamMV();
						autoETL_WorkflowParamMV.setAutoETL_Param(autoETL_WorkflowPP.getAutoETL_Param());
						autoETL_WorkflowParamMV.setIntGroup(((Integer)(i + 1)).toString());
						Object value = list.get(i).get(autoETL_WorkflowPP.getAutoETL_Param().getStrParamName());
						if(value == null){
							throw new Exception("参数:" + autoETL_WorkflowPP.getAutoETL_Param().getStrParamName() + " 未能从存储过程返回结果获取到值");
						}
						else{
							autoETL_WorkflowParamMV.setStrValue(value.toString());
						}
						autoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
					}
				}
				
			}
			//系统时间工作流
			else if(this.autoETL_Workflow.getStrParamValueType().equals("2")){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowPSDV.class);
				detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
				List<AutoETL_WorkflowPSDV> autoETL_WorkflowPSDVList = (List<AutoETL_WorkflowPSDV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				for(AutoETL_WorkflowPSDV autoETL_WorkflowPSDV : autoETL_WorkflowPSDVList){
					AutoETL_WorkflowParamMV autoETL_WorkflowParamMV = new AutoETL_WorkflowParamMV();
					autoETL_WorkflowParamMV.setAutoETL_Param(autoETL_WorkflowPSDV.getAutoETL_Param());
					autoETL_WorkflowParamMV.setIntGroup("1");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.DAY_OF_MONTH, autoETL_WorkflowPSDV.getStrValue());
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					autoETL_WorkflowParamMV.setStrValue(simpleDateFormat.format(calendar.getTime()));
					
					autoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
				}
			}
			//文件路径参数
			else if(this.autoETL_Workflow.getStrParamValueType().equals("3")){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowPFPV.class);
				detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
				List<AutoETL_WorkflowPFPV> autoETL_WorkflowPFPVList = (List<AutoETL_WorkflowPFPV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				if(autoETL_WorkflowPFPVList.size() == 0){
					throw new Exception("未设置文件路径取参数值");
				}
				
				AutoETL_WorkflowPFPV autoETL_WorkflowPFPV = autoETL_WorkflowPFPVList.get(0);

				List<String> allFileName = new ArrayList<String>();
				
				if(autoETL_WorkflowPFPV.getStrPathType().equals("1")){
					if(StringUtils.isBlank(autoETL_WorkflowPFPV.getStrValue())){
						throw new Exception("未设置本地文件夹路径");
					}
					File dir =new File(autoETL_WorkflowPFPV.getStrValue());  
					if(!dir .exists() || !dir .isDirectory()){
						throw new Exception("本地文件夹路径不存在");
					}
					File[] files = dir.listFiles();
					for(int i=0;i<files.length;i++){
						if(files[i].isFile()){
							allFileName.add(files[i].getName());
						}
					}
				}
				else if(autoETL_WorkflowPFPV.getStrPathType().equals("2")){
					if(autoETL_WorkflowPFPV.getAutoETL_FTP() == null){
						throw new Exception("未设置FTP路径");
					}
					FTPClient ftpClient = null;
					try{
						ftpClient = new FTPClient();
						String serverPath = autoETL_WorkflowPFPV.getAutoETL_FTP().getStrServerPath();
						
						String hostServer = "";
						String dictionary = "";
						if(serverPath.indexOf("/") > -1){
							hostServer = serverPath.substring(0,serverPath.indexOf("/"));
							dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
						}
						else{
							hostServer = serverPath;
						}
						ftpClient.connect(hostServer);
						ftpClient.login(autoETL_WorkflowPFPV.getAutoETL_FTP().getStrUserID(), autoETL_WorkflowPFPV.getAutoETL_FTP().getStrPassword());
						ftpClient.setControlEncoding("UTF-8");
						if(!dictionary.equals("")){
							ftpClient.changeWorkingDirectory(dictionary);
						}
						
						FTPFile[] files = ftpClient.listFiles();
						String fileName = null;
						for(FTPFile f : files){
							if(f.isFile()){
								fileName = f.getName();
								if(StringUtils.isNotBlank(fileName) && fileName.indexOf(".") > -1){
									allFileName.add(fileName);
								}
							}
						}
					}
					finally{
						if(ftpClient != null){
							ftpClient.logout();
							ftpClient.disconnect();
						}
					}
				}
				
				List<String> paramFileName = new ArrayList<String>();
				for(int i=0;i<allFileName.size();i++){
					String fileType = ShowContext.getInstance().getShowEntityMap().get("fileType").get(autoETL_WorkflowPFPV.getStrFileType());
					if(fileType.equals("*")){
						paramFileName.add(allFileName.get(i));
					}
					else{
						if(allFileName.get(i).endsWith(fileType)){
							paramFileName.add(allFileName.get(i));
						}
					}
				}
				
				Set<String> paramSet = new HashSet<String>();
				for(int i=0;i<paramFileName.size();i++){
					String fileName = paramFileName.get(i).substring(0,paramFileName.get(i).indexOf("."));
					String[] params = fileName.split(autoETL_WorkflowPFPV.getStrFileNameSeg());
					singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowPFPVDetail.class);
					detachedCriteria.add(Restrictions.eq("autoETL_WorkflowPFPV", autoETL_WorkflowPFPV));
					detachedCriteria.addOrder(Order.asc("intOrder"));
					List<AutoETL_WorkflowPFPVDetail> autoETL_WorkflowPFPVDetailList = (List<AutoETL_WorkflowPFPVDetail>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
					if(autoETL_WorkflowPFPVDetailList.size() == 0){
						throw new Exception("未设置参数取值");
					}
					/**
					 * lanyuesheng  当扫描到不符合文件定义规则文件时，跳过。是否符合？
					 */
					if(autoETL_WorkflowPFPVDetailList.size()>params.length){
						continue;
					}
					
					if(autoETL_WorkflowPFPV.getStrOrderType().equals("2")){
						String[] tempParams = new String[params.length];
						int k =0;
						for(int j=params.length-1;j>=0;j--){
							tempParams[k] = params[j];
							k++;
						}	
						params = tempParams;
					}
					int paramLenth = params.length;
					if(paramLenth > autoETL_WorkflowPFPVDetailList.size()){
						paramLenth = autoETL_WorkflowPFPVDetailList.size();
					}
					String paramSetStr = "";
					for(int j=0;j<paramLenth;j++){
						paramSetStr += params[j] + "**********";
					}
					if(paramSet.contains(paramSetStr)){
						continue;
					}
					else{
						paramSet.add(paramSetStr);
					}
					
					for(int j=0;j<autoETL_WorkflowPFPVDetailList.size();j++){
						/**
						 * lanyuesheng 上面做了判断，此处是否可以删除?
						if(j>params.length -1){
							throw new Exception("参数:" + autoETL_WorkflowPFPVDetailList.get(j).getAutoETL_Param().getStrParamName() + " 未能从文件 " + paramFileName.get(i) + " 获取到值");
						}
						else{
							AutoETL_WorkflowParamMV autoETL_WorkflowParamMV = new AutoETL_WorkflowParamMV();
							autoETL_WorkflowParamMV.setAutoETL_Param(autoETL_WorkflowPFPVDetailList.get(j).getAutoETL_Param());
							autoETL_WorkflowParamMV.setIntGroup(((Integer)(i + 1)).toString());
							autoETL_WorkflowParamMV.setStrValue(params[j]);
							autoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
						}  */
						AutoETL_WorkflowParamMV autoETL_WorkflowParamMV = new AutoETL_WorkflowParamMV();
						autoETL_WorkflowParamMV.setAutoETL_Param(autoETL_WorkflowPFPVDetailList.get(j).getAutoETL_Param());
						autoETL_WorkflowParamMV.setIntGroup(((Integer)(i + 1)).toString());
						autoETL_WorkflowParamMV.setStrValue(params[j]);
						autoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
					}
				}
			}
			//SQL语句工作流参数
			else if(this.autoETL_Workflow.getStrParamValueType().equals("4")){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowSQLParam.class);
				detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
				List<AutoETL_WorkflowSQLParam> autoETL_WorkflowSQLParamList = (List<AutoETL_WorkflowSQLParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				if(autoETL_WorkflowSQLParamList.size() == 0){
					throw new Exception("未设置SQL语句取参数值");
				}
				AutoETL_WorkflowSQLParam autoETL_WorkflowSQLParam = autoETL_WorkflowSQLParamList.get(0);
				
				detachedCriteria = DetachedCriteria.forClass(AutoETL_Param.class);
				List<AutoETL_Param> autoETL_ParamList = (List<AutoETL_Param>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				
				IParamObjectResultExecute createSqlQueryResultSetDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
				ResultSet resultSet = (ResultSet)createSqlQueryResultSetDao.paramObjectResultExecute(new Object[]{autoETL_WorkflowSQLParam.getStrDataSourceSql(),autoETL_WorkflowSQLParam.getAutoETL_DataSource().getSessionFactory()});
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int i = 0;
				int fieldCount = resultSetMetaData.getColumnCount();  
				while (resultSet.next()){
		            for (int j = 1; j <= fieldCount; j++) {  
		                String fieldName = resultSetMetaData.getColumnName(j);   
		                boolean isExsist = false;
		                for(AutoETL_Param autoETL_Param : autoETL_ParamList){
		                	if(autoETL_Param.getStrParamName().toUpperCase().equals(fieldName.toUpperCase())){
		                		
		                		AutoETL_WorkflowParamMV autoETL_WorkflowParamMV = new AutoETL_WorkflowParamMV();
								autoETL_WorkflowParamMV.setAutoETL_Param(autoETL_Param);
								autoETL_WorkflowParamMV.setIntGroup(((Integer)(i + 1)).toString());
								Object value = resultSet.getObject(fieldName);
								if(value == null || StringUtils.isBlank(value.toString())){
									throw new Exception("参数返回字段:" + fieldName + " 为空");
								}
								else{
									autoETL_WorkflowParamMV.setStrValue(value.toString());
								}
								autoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
		                		
		                		isExsist = true;
		                		break;
		                	}
		                }
		                if(!isExsist){
		                	throw new Exception("未定义参数:" + fieldName);
		                }

		            } 
					i++;
				}

				resultSet.close();
			}
			//文件夹路径参数
			else if(this.autoETL_Workflow.getStrParamValueType().equals("5")){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowPFOPV.class);
				detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
				List<AutoETL_WorkflowPFOPV> autoETL_WorkflowPFOPVList = (List<AutoETL_WorkflowPFOPV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				if(autoETL_WorkflowPFOPVList.size() == 0){
					throw new Exception("未设置文件夹路径取参数值");
				}
				
				AutoETL_WorkflowPFOPV autoETL_WorkflowPFOPV = autoETL_WorkflowPFOPVList.get(0);

				List<String> allFolderName = new ArrayList<String>();
				
				if(autoETL_WorkflowPFOPV.getStrPathType().equals("1")){
					if(StringUtils.isBlank(autoETL_WorkflowPFOPV.getStrValue())){
						throw new Exception("未设置本地文件夹路径");
					}
					File dir =new File(autoETL_WorkflowPFOPV.getStrValue());  
					if(!dir .exists() || !dir .isDirectory()){
						throw new Exception("本地文件夹路径不存在");
					}
					
					/*File[] files = dir.listFiles();
					for(int i=0;i<files.length;i++){
						if(files[i].isFile()){
							allFolderName.add(files[i].getName());
						}
					}*/
					
					List<File> fileList=new ArrayList<File>();
					HandleFileOrFolders.readfile(autoETL_WorkflowPFOPV.getAutoETL_FTP().getStrServerPath(),fileList);
					for (File file : fileList) {
						allFolderName.add(file.getName());
					}
					
				}
				else if(autoETL_WorkflowPFOPV.getStrPathType().equals("2")){
					if(autoETL_WorkflowPFOPV.getAutoETL_FTP() == null){
						throw new Exception("未设置FTP路径");
					}
				
					FTPClient ftpClient = null;
					try{
						ftpClient = new FTPClient();
						String serverPath = autoETL_WorkflowPFOPV.getAutoETL_FTP().getStrServerPath();
						
						String hostServer = "";
						String dictionary = "";
						if(serverPath.indexOf("/") > -1){
							hostServer = serverPath.substring(0,serverPath.indexOf("/"));
							dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
						}
						else{
							hostServer = serverPath;
						}
						ftpClient.connect(hostServer);
						ftpClient.login(autoETL_WorkflowPFOPV.getAutoETL_FTP().getStrUserID(), autoETL_WorkflowPFOPV.getAutoETL_FTP().getStrPassword());
						ftpClient.setControlEncoding("UTF-8");
						if(!dictionary.equals("")){
							ftpClient.changeWorkingDirectory(dictionary);
						}
						String[] fileNames = ftpClient.listNames();
						for(String fn : fileNames){
							BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ftpClient.retrieveFileStream(fn)));
							String fileName = "";
							
							while ((fileName = bufferedReader.readLine()) != null) {
								allFolderName.add(fileName);
							}
						}
					}
					finally{
						if(ftpClient != null){
							ftpClient.logout();
							ftpClient.disconnect();
						}
					}
				}
				List<AutoETL_WorkflowLog> autoETL_WorkflowLogList =new ArrayList<AutoETL_WorkflowLog>();
				if(allFolderName.size()>0){
					singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowLog.class);
					detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
					detachedCriteria.add(Restrictions.eq("strResultType", "1"));
					autoETL_WorkflowLogList = (List<AutoETL_WorkflowLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				}
				
				for (String str : allFolderName) {
					boolean isNotMarchParams=false;
					if(autoETL_WorkflowLogList.size()>0){
							boolean isNotMarchParam=false;
							int i=0;
							for (AutoETL_WorkflowLog autoETLWorkflowLog : autoETL_WorkflowLogList) {
								if(!autoETLWorkflowLog.getStrParam().contains(str)){
									i++;
									if(i==autoETL_WorkflowLogList.size()){
										isNotMarchParam=true;
									}
								}
							}
							if(isNotMarchParam){
								isNotMarchParams=true;
							}
					}else{
						isNotMarchParams=true;
					}
					if(isNotMarchParams){
						AutoETL_WorkflowParamMV autoETL_WorkflowParamMV = new AutoETL_WorkflowParamMV();
						autoETL_WorkflowParamMV.setAutoETL_Param(autoETL_WorkflowPFOPV.getAutoETL_Param());
						autoETL_WorkflowParamMV.setIntGroup(((Integer)(1)).toString());
						autoETL_WorkflowParamMV.setStrValue(str);
						autoETL_WorkflowParamMVList.add(autoETL_WorkflowParamMV);
					}
				}
				
			}
			
			return autoETL_WorkflowParamMVList;
		}
		else{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowParamMV.class);
			detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
			
			return (List<AutoETL_WorkflowParamMV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		}
	}
	
	public ThreadWorkflowRunnable(){
		
	}
	
	public ThreadWorkflowRunnable(String autoWorkflowID,String defaultOracleProcedureParamName,boolean isInit,String sessionFactory) throws Exception{
		this.strSessionFactory=sessionFactory;
		this.isAuto = false;
		this.autoETl_WorkflowId = autoWorkflowID;
		this.defaultOracleProcedureParamName = defaultOracleProcedureParamName;
		if(SecurityContext.getInstance().getLoginInfo() != null){
			loginUserInfo = new UserInfo();
			loginUserInfo.setStrUserCode(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode());
			loginUserInfo.setStrUserName(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
		}
	}
	
	public ThreadWorkflowRunnable(String autoWorkflowID,String defaultOracleProcedureParamName,String sessionFactory) throws Exception{
		this.strSessionFactory=sessionFactory;
		this.isAuto = false;
		if(SecurityContext.getInstance().getLoginInfo() != null){
	    	loginUserInfo = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
		}
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		autoETL_Workflow = (AutoETL_Workflow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_Workflow.class.getName(),autoWorkflowID,this.strSessionFactory});
		this.defaultOracleProcedureParamName = defaultOracleProcedureParamName;
	}
	
	public ThreadWorkflowRunnable(AutoETL_Workflow autoETL_Workflow,String defaultOracleProcedureParamName,String sessionFactory) throws Exception{
		this.strSessionFactory=sessionFactory;
		this.isAuto = true;
		this.autoETL_Workflow = autoETL_Workflow;
		this.defaultOracleProcedureParamName = defaultOracleProcedureParamName;
	}
	
	public void ThreadWorkflowRunnable1(String autoWorkflowID,String defaultOracleProcedureParamName,UserInfo loginUser,String sessionFactory) throws Exception{					
		this.strSessionFactory=sessionFactory;
		this.isAuto = false;			
		loginUserInfo=loginUser;			
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");			
		autoETL_Workflow = (AutoETL_Workflow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_Workflow.class.getName(),autoWorkflowID,this.strSessionFactory});			
		this.defaultOracleProcedureParamName = defaultOracleProcedureParamName;			
	}				
	
	private StatisticalCount ExcuteActivityNodes(List<AutoETL_ActivityNode> autoETL_ActivityNodeList,int activityNodeOrder,StatisticalCount previousStatisticalCount) throws Exception{
		StatisticalCount statisticalCount = new StatisticalCount();
		for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeList){
			if(ExcuteActivityNode(autoETL_ActivityNode,activityNodeOrder,previousStatisticalCount)){
				int correctCount = statisticalCount.getCorrectCount();
				correctCount ++;
				statisticalCount.setCorrectCount(correctCount);
			}
			else{
				int errorCount = statisticalCount.getErrorCount();
				errorCount ++;
				statisticalCount.setErrorCount(errorCount);
			}
			activityNodeOrder ++;
		}
		return statisticalCount;
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean ExcuteActivityNode(AutoETL_ActivityNode autoETL_ActivityNode,int activityNodeOrder,StatisticalCount previousStatisticalCount) throws Exception{
		
		boolean result = false;
		AutoETL_ActivityNodeLog autoETL_ActivityNodeLog = new AutoETL_ActivityNodeLog();
		
		try{
			autoETL_ActivityNodeLog.setAutoETL_ActivityNode(autoETL_ActivityNode);
			autoETL_ActivityNodeLog.setAutoETL_WorkflowLog(autoETL_WorkflowLog);
			autoETL_ActivityNodeLog.setIntOrder(activityNodeOrder);
			autoETL_ActivityNodeLog.setStrActivityNodeExcuteType("1");
			
			Date startDate = new Date();
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeParam.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
			List<AutoETL_ActivityNodeParam> autoETL_ActivityNodeParamList = (List<AutoETL_ActivityNodeParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});

			List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList = new ArrayList<AutoETL_WorkflowParamMV>();
			for(AutoETL_ActivityNodeParam autoETL_ActivityNodeParam : autoETL_ActivityNodeParamList){
				boolean isExsit = false;
				for(AutoETL_WorkflowParamMV currentAutoETL_WorkflowParamMV : currentAutoETL_WorkflowParamMVList){
					if(autoETL_ActivityNodeParam.getAutoETL_Param().getAutoParamID().equals(currentAutoETL_WorkflowParamMV.getAutoETL_Param().getAutoParamID())){
						activeNodeETL_WorkflowParamMVList.add(currentAutoETL_WorkflowParamMV);
						isExsit = true;
						break;
					}
				}
				if(!isExsit){
					throw new Exception("参数:" + autoETL_ActivityNodeParam.getAutoETL_Param().getStrParamName() + " 未能获取值");
				}
			}
			
			String strParam = getParamStr(activeNodeETL_WorkflowParamMVList);
			autoETL_ActivityNodeLog.setStrParam(strParam);
			
			boolean isStartRepeatType = false;
			if(autoETL_ActivityNode.getStrRepeatType().equals("1")){
				isStartRepeatType = true;
				 autoETL_ActivityNodeLog.setStrContent("执行 " + ShowContext.getInstance().getShowEntityMap().get("activityNodeType").get(autoETL_ActivityNode.getStrActivityNodeType())  +" 活动结点成功");
			}
			
			boolean isRepeatPreviousSuccess = false;
			
            if(autoETL_ActivityNode.getStrRepeatType().equals("2")){
            	IParamObjectResultExecute autoETL_ActivityNodeLogDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    			detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeLog.class);
    			detachedCriteria.add(Restrictions.eq("strResultType", "1"));
    			if(!StringUtils.isBlank(strParam)){
    				detachedCriteria.add(Restrictions.eq("strParam", strParam));
    			}
    			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
    			List<AutoETL_ActivityNodeLog> autoETL_ActivityNodeLogList = (List<AutoETL_ActivityNodeLog>)autoETL_ActivityNodeLogDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
    			if(autoETL_ActivityNodeLogList.size() == 0){
    				isStartRepeatType = true;
    				autoETL_ActivityNodeLog.setStrContent("执行 " + ShowContext.getInstance().getShowEntityMap().get("activityNodeType").get(autoETL_ActivityNode.getStrActivityNodeType())  +" 活动结点成功");
    			}
    			else{
    				autoETL_ActivityNodeLog.setStrContent("上次执行 " + ShowContext.getInstance().getShowEntityMap().get("activityNodeType").get(autoETL_ActivityNode.getStrActivityNodeType())  +" 活动结点成功, 本次未执行");
    				autoETL_ActivityNodeLog.setStrActivityNodeExcuteType("2");
    				if(previousStatisticalCount.getErrorCount() > 0){
    					isRepeatPreviousSuccess = true;
    				}
    			}
			}
			String message="";
            if(isStartRepeatType){
            	
    			boolean isStartConditionType = false;
    			if(autoETL_ActivityNode.getStrStartConditionType().equals("1")){
    				isStartConditionType = true;
    			}
    			else if(autoETL_ActivityNode.getStrStartConditionType().equals("2")){
    				if(autoETL_ActivityNode.getAutoETL_Procedure() == null){
    					throw new Exception("未满足启动条件:未定义存储过程");
    				}
    				IParamObjectResultExecute autoETL_ActivityNodeProcPDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    				detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeProcP.class);
    				detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
    				List<AutoETL_ActivityNodeProcP> autoETL_ActivityNodeProcPList = (List<AutoETL_ActivityNodeProcP>)autoETL_ActivityNodeProcPDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
    				if(autoETL_ActivityNodeProcPList.size() == 0){
    					throw new Exception("未满足启动条件:未定义存储过程 " + autoETL_ActivityNode.getAutoETL_Procedure().getStrProcedureName()+ " 启动参数");
    				}
    				
    				List<AutoETL_WorkflowParamMV> procedureETL_WorkflowParamMVList = new ArrayList<AutoETL_WorkflowParamMV>();
    				for(AutoETL_ActivityNodeProcP autoETL_ActivityNodeProcP : autoETL_ActivityNodeProcPList){
    					boolean isExsit = false;
    					for(AutoETL_WorkflowParamMV currentAutoETL_WorkflowParamMV : currentAutoETL_WorkflowParamMVList){
    						if(autoETL_ActivityNodeProcP.getAutoETL_Param().getAutoParamID().equals(currentAutoETL_WorkflowParamMV.getAutoETL_Param().getAutoParamID())){
    							procedureETL_WorkflowParamMVList.add(currentAutoETL_WorkflowParamMV);
    							isExsit = true;
    							break;
    						}
    					}
    					if(!isExsit){
    						throw new Exception("启动条件存储过程 " + autoETL_ActivityNode.getAutoETL_Procedure().getStrProcedureName() + " 参数:" + autoETL_ActivityNodeProcP.getAutoETL_Param().getStrParamName() + " 未能获取值");
    					}
    				}
    				
    				List<Map<String,Object>> list = getProcedureResult(autoETL_ActivityNode.getAutoETL_Procedure(),procedureETL_WorkflowParamMVList);
    				if(list.size() > 0){
    					isStartConditionType = true;
    				}
    			}
    			else if(autoETL_ActivityNode.getStrStartConditionType().equals("22")){
    				if(StringUtils.isBlank(autoETL_ActivityNode.getStrSqlCondition())){
    					throw new Exception("未满足启动条件:未定义SQL启动条件");
    				}
    				
    				String strSql = autoETL_ActivityNode.getStrSqlCondition();
    				if(activeNodeETL_WorkflowParamMVList != null){
    		        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
    		    			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
    		    		}
    		        }
    				
    				IParamObjectResultExecute createSqlQueryResultSetDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
    				String sessionFactory = null;
    				if(autoETL_ActivityNode.getAutoETL_DataSource() != null){
    					sessionFactory = autoETL_ActivityNode.getAutoETL_DataSource().getSessionFactory();
    				}
    				if(sessionFactory == null){
    					throw new Exception("未满足启动条件:未选择SQL条件源会话工厂");
    				}
    				
    				if(strSql.indexOf("#PROCEDURE") > -1){
    					IParamObjectResultExecute procedureListMapDao = null;
    					String strProcedureSql = strSql.replaceAll("#PROCEDURE", "");
    					strProcedureSql = strProcedureSql.substring(strProcedureSql.indexOf("{"),strProcedureSql.indexOf("}") + 1);
    					
    					strProcedureSql = strProcedureSql.replaceAll("\r\n", " ");
    					
    					Map<String,Object> procedureParam = new HashMap<String,Object>();
    					if(autoETL_ActivityNode.getAutoETL_DataSource().getStrDatabaseType().equals("1")
    							|| autoETL_ActivityNode.getAutoETL_DataSource().getStrDatabaseType().equals("3")
    							|| autoETL_ActivityNode.getAutoETL_DataSource().getStrDatabaseType().equals("4")){
    						procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("sqlserverSqlProcedureResultSetDao");
    					}
    					else if(autoETL_ActivityNode.getAutoETL_DataSource().getStrDatabaseType().equals("2")){
    						procedureListMapDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("oracleSqlProcedureResultSetDao");
    					}
    					ResultSet resultSet = (ResultSet)procedureListMapDao.paramObjectResultExecute(new Object[]{strProcedureSql,procedureParam,sessionFactory});
    					while (resultSet.next()){
    						isStartConditionType = true;
    						break;
    					}
    					resultSet.close();
    				}
    				else{
    					ResultSet resultSet = (ResultSet)createSqlQueryResultSetDao.paramObjectResultExecute(new Object[]{strSql,sessionFactory});
    					while (resultSet.next()){
    						isStartConditionType = true;
    						break;
    					}
    					resultSet.close();
    				}
    				
    			}
    			else if(autoETL_ActivityNode.getStrStartConditionType().equals("3")){
    				if(previousStatisticalCount.getErrorCount() == 0){
    					isStartConditionType = true;
    				}
    			}
    			else if(autoETL_ActivityNode.getStrStartConditionType().equals("4")){
    				if(previousStatisticalCount.getCorrectCount() > 0 && previousStatisticalCount.getErrorCount() > 0){
    					isStartConditionType = true;
    				}
    			}
    			else if(autoETL_ActivityNode.getStrStartConditionType().equals("5")){
    				if(previousStatisticalCount.getCorrectCount() == 0 && previousStatisticalCount.getErrorCount() > 0){
    					isStartConditionType = true;
    				}
    			}
    			if(!isStartConditionType){
    				throw new Exception("未满足启动条件:" + ShowContext.getInstance().getShowEntityMap().get("startConditionType").get(autoETL_ActivityNode.getStrStartConditionType()));
    			}
    			
    			//SQL传输数据
            	if(autoETL_ActivityNode.getStrActivityNodeType().equals("1")){
            		 int lines = ExceuteSqlTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
            		 autoETL_ActivityNodeLog.setStrLines(((Integer)lines).toString());
    			}
            	//SQL语句执行
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("2")){
    				ExceuteSqlExcuteTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    			}
            	//存储过程传输数据
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("3")){
    				int lines = ExceuteProceTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrLines(((Integer)lines).toString());
    			}
            	//存储过程执行
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("4")){
    				ExceuteProcedureTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    			}
            	//FTP传输
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("5")){
    				ExceuteFtpTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    			}
            	//文本文件数据传输
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("6")){
    				int lines = ExceuteFileTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrLines(((Integer)lines).toString());
    			}
            	//EXCEL数据传输
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("7")){
    				int lines = ExceuteExcelTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrLines(((Integer)lines).toString());
    			}
            	//表数据传输
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("8")){
    				int lines = ExceuteTableTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrLines(((Integer)lines).toString());
    			}
            	//视图数据传输
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("9")){
    				int lines = ExceuteViewTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrLines(((Integer)lines).toString());
    			}
            	//明细表校验实例
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("10")){
    				message = ExceuteCheckTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//计算实例
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("11")){
    				message = ExceuteCalculateTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//汇总实例
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("12")){
    				message = ExceuteSummaryTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//Kettle调用
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("13")){
    				message = ExceuteKettleTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//删除文件
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("14")){
    				message = ExceuteDeleteFiles(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//存储过程传输数据及校验
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("15")){
    				StatisticalCount statisticalCount = ExceuteProceTranslateWithCheck(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrLines(((Integer)statisticalCount.getCorrectCount()).toString());
    				autoETL_ActivityNodeLog.setStrDiscription(statisticalCount.getMessage());
    			}
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("16")){
    				ExceuteFtpUploadTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    			}
            	//指标报表校验实例
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("17")){
    				message = ExceuteRptCheckTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//Mail邮件发送
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("18")){
    				ExceuteMailSend(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    			}
            	//统计表状态（校验，提交，审核，报送，回执）
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("19")){
    				message = ExceuteTotalTableState(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//逻辑主键校验
				else if(autoETL_ActivityNode.getStrActivityNodeType().equals("20")){
    				message = ExceuteCheckTableLogicP(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//MSG信息发送
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("22")){
    				message = ExceuteMSGSend(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//预警模型分析
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("23")){
    				message = ExceuteModelWarning(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            	//JAVA程序
    			else if(autoETL_ActivityNode.getStrActivityNodeType().equals("100")){
    				message = ExceuteJavaTranslate(autoETL_ActivityNode,activeNodeETL_WorkflowParamMVList);
    				autoETL_ActivityNodeLog.setStrDiscription(message);
    			}
            }
			
			Date endDate = new Date();
			if(isRepeatPreviousSuccess){
				autoETL_ActivityNodeLog.setStrResultType("3");
			}
			else{
				autoETL_ActivityNodeLog.setStrResultType("1");
			}
			
			autoETL_ActivityNodeLog.setStrDiscription(message+" 运行时间：" + String.valueOf((endDate.getTime() - startDate.getTime())/1000) + "秒");
			if(autoETL_ActivityNode.getStrActivityNodeType().equals("100")){
				if(!message.equals("成功")){
					result = false;
					autoETL_ActivityNodeLog.setStrResultType("3");
					autoETL_ActivityNodeLog.setStrContent("执行 " + ShowContext.getInstance().getShowEntityMap().get("activityNodeType").get(autoETL_ActivityNode.getStrActivityNodeType())  +" 活动结点失败");
				}else{
					result = true;
				}
			}else{
				result = true;
			}
		}
		catch(Exception ex){
			autoETL_ActivityNodeLog.setStrContent("失败,请查看描述信息");
			autoETL_ActivityNodeLog.setStrResultType("3");
			if(ex.getMessage()!=null && ex.getMessage().startsWith("未满足启动条件")){
				autoETL_ActivityNodeLog.setStrResultType("5");
			}

			if(!StringUtils.isBlank(ex.getMessage())){
				try{
				     
				     String errorMessage=ex.getCause().getMessage();
				     String returnMessage=ex.getCause().getMessage();
				     if(ex.getMessage().indexOf(errorMessage) == -1 && errorMessage.indexOf(ex.getMessage()) ==-1)
				     {
				      returnMessage +=ex.getMessage();
				     }
				     else if(ex.getMessage().indexOf(errorMessage) >-1)
				     {
				      returnMessage =ex.getMessage();
				     }
				     autoETL_ActivityNodeLog.setStrDiscription(returnMessage);
				    }
				    catch(Exception ex2){
				     autoETL_ActivityNodeLog.setStrDiscription(ex.getMessage());
				    }
			}
			else{
				ExceptionLog.CreateLog(ex);
			}
			result = false;
		}
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{autoETL_ActivityNodeLog,this.strSessionFactory});
		
		return result;
	}
	

	

	@SuppressWarnings("unchecked")
	private void ExcuteWorkflow() throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_WorkflowAE.class);
		detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
		List<AutoETL_WorkflowAE> autoETL_WorkflowAEList = (List<AutoETL_WorkflowAE>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});

		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	    detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNode.class);
		int autoETL_WorkflowAEListCount = autoETL_WorkflowAEList.size();
	    if(!this.isAuto && autoETL_WorkflowAEListCount > 0){
			String[] strAutoETL_ActivityNodeIdList = new String[autoETL_WorkflowAEList.size()];
			for(int i=0;i<autoETL_WorkflowAEList.size();i++){
				strAutoETL_ActivityNodeIdList[i] = autoETL_WorkflowAEList.get(i).getAutoETL_ActivityNode().getAutoActivityNodeID();
				IParamVoidResultExecute singleObjectDeleteByIdDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteByIdDao");
				singleObjectDeleteByIdDao.paramVoidResultExecute(new Object[]{AutoETL_WorkflowAE.class.getName(),autoETL_WorkflowAEList.get(i).getId(),this.strSessionFactory});
			}
			detachedCriteria.add(Restrictions.in("autoActivityNodeID", strAutoETL_ActivityNodeIdList));
		}
	    detachedCriteria.add(Restrictions.eq("autoETL_Workflow", autoETL_Workflow));
	    detachedCriteria.add(Restrictions.ne("strEffectiveType", "2"));
		detachedCriteria.addOrder(Order.asc("strOrderGroup"));
		List<AutoETL_ActivityNode> autoETL_ActivityNodeSet = (List<AutoETL_ActivityNode>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(!this.isAuto && autoETL_WorkflowAEListCount > 0){
			for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeSet){
				autoETL_ActivityNode.setStrRepeatType("1");
			}
		}
		
		Set<String> strOrderGroup = new LinkedHashSet<String>();
		for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeSet){
			strOrderGroup.add(autoETL_ActivityNode.getStrOrderGroup());
		}
		
		List<List<AutoETL_ActivityNode>> autoETL_ActivityNodeSetList = new ArrayList<List<AutoETL_ActivityNode>>();
		for(String group : strOrderGroup){
			List<AutoETL_ActivityNode> autoETL_ActivityNodeList = new ArrayList<AutoETL_ActivityNode>();
			for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeSet){
				if(group == null){
					if(autoETL_ActivityNode.getStrOrderGroup() == null){
						autoETL_ActivityNodeList.add(autoETL_ActivityNode);
					}
				}
				else{
					if(group.equals(autoETL_ActivityNode.getStrOrderGroup())){
						autoETL_ActivityNodeList.add(autoETL_ActivityNode);
					}
				}
			}
			autoETL_ActivityNodeSetList.add(autoETL_ActivityNodeList);
		}
		
		
		StatisticalCount allStatisticalCount = new StatisticalCount();
		StatisticalCount statisticalCount = new StatisticalCount();
		int activityNodeOrder = 1;
		
		for(List<AutoETL_ActivityNode> autoETL_ActivityNodeListGroup : autoETL_ActivityNodeSetList){
			while(autoETL_ActivityNodeListGroup.size() != 0){
				
				AutoETL_ActivityNode currentAutoETL_ActivityNode = null;
				for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeListGroup){
					if(currentAutoETL_ActivityNode == null){
						currentAutoETL_ActivityNode = autoETL_ActivityNode;
					}
					else{
						if(currentAutoETL_ActivityNode.getIntOrder() > autoETL_ActivityNode.getIntOrder()){
							currentAutoETL_ActivityNode = autoETL_ActivityNode;
						}
					}
				}
				
				List<AutoETL_ActivityNode> autoETL_ActivityNodeList = new ArrayList<AutoETL_ActivityNode>();
				for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeListGroup){
					if(currentAutoETL_ActivityNode.getIntOrder().equals(autoETL_ActivityNode.getIntOrder())){
						autoETL_ActivityNodeList.add(autoETL_ActivityNode);
					}
				}
				statisticalCount = ExcuteActivityNodes(autoETL_ActivityNodeList,activityNodeOrder,statisticalCount);
				
				allStatisticalCount.setCorrectCount(allStatisticalCount.getCorrectCount() + statisticalCount.getCorrectCount());
				allStatisticalCount.setErrorCount(allStatisticalCount.getErrorCount() + statisticalCount.getErrorCount());
				
				for(AutoETL_ActivityNode autoETL_ActivityNode : autoETL_ActivityNodeList){
					autoETL_ActivityNodeListGroup.remove(autoETL_ActivityNode);
					activityNodeOrder ++;
				}
			}
		}

		if(allStatisticalCount.getCorrectCount() == 0 && allStatisticalCount.getErrorCount() == 0){
			this.autoETL_WorkflowLog.setStrResultType("1");
			this.autoETL_WorkflowLog.setStrContent("不存在活动结点");
		}
		else if(allStatisticalCount.getCorrectCount() > 0 && allStatisticalCount.getErrorCount() == 0){
			this.autoETL_WorkflowLog.setStrResultType("1");
			this.autoETL_WorkflowLog.setStrContent("成功,详情请查看结点日志");
		}
		else if(allStatisticalCount.getCorrectCount() > 0 && allStatisticalCount.getErrorCount() > 0){
			this.autoETL_WorkflowLog.setStrResultType("2");
			this.autoETL_WorkflowLog.setStrContent("部分成功,详情请查看结点日志");
		}
		else if(allStatisticalCount.getCorrectCount() == 0 && allStatisticalCount.getErrorCount() > 0){
			this.autoETL_WorkflowLog.setStrResultType("3");
			this.autoETL_WorkflowLog.setStrContent("失败,详情请查看结点日志");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try{
			ApplicationDataCache.getInstance().StartUse();
			if(StringUtils.isNotBlank(this.autoETl_WorkflowId)){
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				this.autoETL_Workflow = (AutoETL_Workflow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_Workflow.class.getName(),this.autoETl_WorkflowId,this.strSessionFactory});
				if(!this.initWorkflowParam()){
					return;
				}
			}
			
			for(Integer intGroup : groupSet){
				this.currentAutoETL_WorkflowParamMVList = this.getAutoETL_WorkflowParamMVListByGroup(intGroup);
				this.currentWorkflowParam = getParamStr(currentAutoETL_WorkflowParamMVList);
				
				autoETL_WorkflowLog = new AutoETL_WorkflowLog();
				autoETL_WorkflowLog.setStrParam(currentWorkflowParam);
				autoETL_WorkflowLog.setAutoETL_Workflow(autoETL_Workflow);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				autoETL_WorkflowLog.setDtDate(simpleDateFormat.format(new Date()));
				autoETL_WorkflowLog.setStrContent("工作流执行中，请耐心等待，如果等待时间超过预期，请重新执行");
				if(this.isAuto){
					autoETL_WorkflowLog.setStrOperationType("2");
					autoETL_WorkflowLog.setStrUserName("应用服务器");
				}
				else{
					autoETL_WorkflowLog.setStrOperationType("1");
					if(loginUserInfo != null){
						autoETL_WorkflowLog.setStrUserName(loginUserInfo.getStrUserName());
					}	
				}
				autoETL_WorkflowLog.setStrResultType("4");
				
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{autoETL_WorkflowLog,this.strSessionFactory});

				Date startDate = new Date();
				try{
					ExcuteWorkflow();
					Date endDate = new Date();
					autoETL_WorkflowLog.setStrDiscription("运行时间：" + String.valueOf((endDate.getTime() - startDate.getTime())/1000) + "秒");
				}
				catch(Exception ex){
					autoETL_WorkflowLog.setStrContent("失败,请查看描述信息");
					autoETL_WorkflowLog.setStrResultType("3");
					
					if(!StringUtils.isBlank(ex.getMessage())){
						autoETL_WorkflowLog.setStrDiscription(ex.getMessage());
					}
					else{
						ExceptionLog.CreateLog(ex);
					}
				}
				
				IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
				singleObjectUpdateDao.paramVoidResultExecute(new Object[]{autoETL_WorkflowLog,this.strSessionFactory});
				
				if(ShowContext.getInstance().getShowEntityMap().get("ActivityNodeGroup")!=null){
					if(autoETL_WorkflowLog.getStrResultType().equals("2")){
						boolean flag=false;
						for (Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("ActivityNodeGroup").entrySet()) {
							if(flag){
								break;
							}
							IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeLog.class);
							detachedCriteria.add(Restrictions.eq("autoETL_WorkflowLog", this.autoETL_WorkflowLog));
							List<AutoETL_ActivityNodeLog> autoETL_ActivityNodeLogList = (List<AutoETL_ActivityNodeLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
							if(autoETL_WorkflowLog.getAutoETL_Workflow().getStrWorkflowName().equals(entry.getValue())){
								String entryKey=entry.getKey();
								for(int i=0;i<entryKey.split(",").length;i++){
									boolean isFund=false;
									for (AutoETL_ActivityNodeLog autoETLActivityNodeLog : autoETL_ActivityNodeLogList) {
										if(autoETLActivityNodeLog.getAutoETL_ActivityNode().getStrActivityNodeName().equals(entryKey.split(",")[i])){
											isFund=true;
											if(!autoETLActivityNodeLog.getStrResultType().equals("1")){
												flag=true;
											}
											break;
										}
									}
									if(isFund==false){
										flag=true;
									}
									if(flag){
										break;
									}
								}
								if(!flag){
									List<AutoETL_ActivityNodeLog> deleteAutoETL_ActivityNodeLogList=new ArrayList<AutoETL_ActivityNodeLog>();
									
									for (AutoETL_ActivityNodeLog autoETLActivityNodeLog : autoETL_ActivityNodeLogList) {
										boolean isfind=false;
										for(int i=0;i<entryKey.split(",").length;i++){
											if(autoETLActivityNodeLog.getAutoETL_ActivityNode().getStrActivityNodeName().equals(entryKey.split(",")[i])){
												isfind=true;
												break;
											}
										}
										if(!isfind){
											deleteAutoETL_ActivityNodeLogList.add(autoETLActivityNodeLog);
										}
									}
									
									String[] idList = new String[deleteAutoETL_ActivityNodeLogList.size()];
									for(int i=0;i<deleteAutoETL_ActivityNodeLogList.size();i++){
										idList[i] = deleteAutoETL_ActivityNodeLogList.get(i).getAutoActivityNodeLogID();
									}
									IParamVoidResultExecute singleObjectDeleteListByIdListDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteListByIdListDao");
									singleObjectDeleteListByIdListDao.paramVoidResultExecute(new Object[]{AutoETL_ActivityNodeLog.class.getName(),idList,this.strSessionFactory});
									
									autoETL_WorkflowLog.setStrContent("成功,详情请查看结点日志");
									autoETL_WorkflowLog.setStrResultType("1");
									singleObjectUpdateDao.paramVoidResultExecute(new Object[]{autoETL_WorkflowLog,this.strSessionFactory});
									flag=true;
								}
							}
						 } 
					}
				}
			}
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
		}
		finally{
			ApplicationDataCache.getInstance().EndUse();
		}
	}
	
	private void ExceuteSqlDataTranslate(List<Map<String,Object>> sourceObjectList,ResultSetMetaData resultSetMetaData,String sessionFactory,String tableName) throws Exception{
		if(ReflectOperation.getAutoDTOTName(sessionFactory, tableName)==null || ReflectOperation.getAutoDTOTName(sessionFactory, tableName).equals("")){
			throw new Exception("没有找到类，请先在表管理下点击创建表");
		}
		Class<?> targetType = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName));
		int fieldCount = resultSetMetaData.getColumnCount();  
		Map<String,String> fieldNameMap = new HashMap<String,String>();
		Field[] fields = ReflectOperation.getReflectFields(targetType);
		for(int i=0;i<fields.length;i++){
			for (int j = 1; j <= fieldCount; j++) { 
				String fieldName = resultSetMetaData.getColumnName(j);  
			    if(fieldName.toUpperCase().equals(fields[i].getName().toUpperCase())){
			    	fieldNameMap.put(fieldName, fields[i].getName());
			    	break;
			    }
			}
		}
		
		if(sourceObjectList != null && sourceObjectList.size() > 0){
			List<Object> targetObjectList = new ArrayList<Object>();
			for(Map<String,Object> sourceObject : sourceObjectList){
				Object targetObject = targetType.newInstance();
				
				for (int j = 1; j <= fieldCount; j++) {  
	                String fieldName = resultSetMetaData.getColumnName(j);   
	                Object sourceValue = sourceObject.get(fieldName);
	                if(sourceValue != null && fieldNameMap.get(fieldName) != null){
	                	if(ReflectOperation.isBaseType(ReflectOperation.getReflectField(targetType,fieldNameMap.get(fieldName)).getType())){
	                		ReflectOperation.setFieldValue(targetObject, fieldNameMap.get(fieldName), sourceValue);
	                	}
	                	else{
	                		Object objectValue = ReflectOperation.getReflectField(targetType, fieldNameMap.get(fieldName)).getType().newInstance();
	                		ReflectOperation.setFieldValue(objectValue, ReflectOperation.getPrimaryKeyField(objectValue.getClass()).getName(), sourceValue);
	                		ReflectOperation.setFieldValue(targetObject, fieldNameMap.get(fieldName), objectValue);
	                	}
	                }
	            } 
				
				targetObjectList.add(targetObject);
			}
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{targetObjectList,sessionFactory});
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getExceuteSql(AutoETL_ActivityNodeForSql autoETL_ActivityNodeForSql,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		
        String strSql = autoETL_ActivityNodeForSql.getStrDataSourceSql();
		
        if(activeNodeETL_WorkflowParamMVList != null){
        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
    			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
    		}
        }

		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSqlC.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForSql", autoETL_ActivityNodeForSql));
		List<AutoETL_ActivityNodeForSqlC> autoETL_ActivityNodeForSqlCList = (List<AutoETL_ActivityNodeForSqlC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		
		for(AutoETL_ActivityNodeForSqlC autoETL_ActivityNodeForSqlC : autoETL_ActivityNodeForSqlCList){
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSqlCR.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForSqlC", autoETL_ActivityNodeForSqlC));
			List<AutoETL_ActivityNodeForSqlCR> autoETL_ActivityNodeForSqlCRList = (List<AutoETL_ActivityNodeForSqlCR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			AutoETL_ActivityNodeForSqlCR elseAutoETL_ActivityNodeForSqlCR = null;
			for(AutoETL_ActivityNodeForSqlCR autoETL_ActivityNodeForSqlCR : autoETL_ActivityNodeForSqlCRList){
				if(StringUtils.isBlank(autoETL_ActivityNodeForSqlCR.getStrWhenValue())){
					if(elseAutoETL_ActivityNodeForSqlCR == null){
						elseAutoETL_ActivityNodeForSqlCR = autoETL_ActivityNodeForSqlCR;
					}
					else{
						throw new Exception("字段:" + autoETL_ActivityNodeForSqlC.getStrFieldName() + " 只允许一条数据的When值为空");
					}
				}
			}
			String replaceSql = "CASE " + autoETL_ActivityNodeForSqlC.getStrFieldName();
			for(AutoETL_ActivityNodeForSqlCR autoETL_ActivityNodeForSqlCR : autoETL_ActivityNodeForSqlCRList){
				if(!StringUtils.isBlank(autoETL_ActivityNodeForSqlCR.getStrWhenValue())){
					replaceSql += " WHEN " + autoETL_ActivityNodeForSqlCR.getStrWhenValue() + " THEN " + autoETL_ActivityNodeForSqlCR.getStrThenValue();
				}
			}
			if(elseAutoETL_ActivityNodeForSqlCR != null){
				replaceSql += " ELSE " + elseAutoETL_ActivityNodeForSqlCR.getStrThenValue();
			}
			replaceSql += " END";
			
			strSql = strSql.replace("#" + autoETL_ActivityNodeForSqlC.getStrFieldName(), replaceSql);
		}
		
		return strSql;
	}
	
	@SuppressWarnings({ "unchecked" })
	private int ExceuteSqlTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSql.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForSql> autoETL_ActivityNodeForSqlList = (List<AutoETL_ActivityNodeForSql>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForSqlList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForSql autoETL_ActivityNodeForSql = autoETL_ActivityNodeForSqlList.get(0);

		String strSql = getExceuteSql(autoETL_ActivityNodeForSql,activeNodeETL_WorkflowParamMVList);

		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
		ResultSet resultSet = (ResultSet)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{strSql,autoETL_ActivityNodeForSql.getAutoETL_DataSource().getSessionFactory()});
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
		int i = 0;
		int fieldCount = resultSetMetaData.getColumnCount();  
		int lines = 0;
		while (resultSet.next()){
			lines ++;
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            for (int j = 1; j <= fieldCount; j++) {  
                String fieldName = resultSetMetaData.getColumnName(j);   
                Object object = resultSet.getObject(fieldName);
                valueMap.put(fieldName, object);
            } 
            sourceObjectList.add(valueMap);
			i++;
			if(i > autoETL_ActivityNodeForSql.getCacheLine() - 1){
				ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForSql.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForSql.getAutoETL_TargetTable().getStrTableName());
				sourceObjectList = new ArrayList<Map<String,Object>>();
				i = 0;
			}
		}
		
		ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForSql.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForSql.getAutoETL_TargetTable().getStrTableName());
		
		resultSet.close();
		
		return lines;
	}
	
	@SuppressWarnings({ "unchecked" })
	private void ExceuteSqlExcuteTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSqlEx.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForSqlEx> autoETL_ActivityNodeForSqlExcuteList = (List<AutoETL_ActivityNodeForSqlEx>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForSqlExcuteList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForSqlEx autoETL_ActivityNodeForSqlExcute = autoETL_ActivityNodeForSqlExcuteList.get(0);
	
		IParamVoidResultExecute excuteSqlDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("excuteSqlDao");
		String strSql = autoETL_ActivityNodeForSqlExcute.getStrDataSourceSql();
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		
		excuteSqlDao.paramVoidResultExecute(new Object[]{strSql,autoETL_ActivityNodeForSqlExcute.getAutoETL_DataSource().getSessionFactory()});
	}
	
	@SuppressWarnings({ "unchecked" })
	private int ExceuteProceTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForP.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForP> autoETL_ActivityNodeForPList = (List<AutoETL_ActivityNodeForP>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForPList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForP autoETL_ActivityNodeForP = autoETL_ActivityNodeForPList.get(0);
	
		ResultSet resultSet = getProcedureResultSet(autoETL_ActivityNodeForP.getAutoETL_Procedure(),activeNodeETL_WorkflowParamMVList);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
		int i = 0;
		int fieldCount = resultSetMetaData.getColumnCount();  
		int lines = 0;
		while (resultSet.next()){
			lines ++;
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            for (int j = 1; j <= fieldCount; j++) {  
                String fieldName = resultSetMetaData.getColumnName(j);   
                Object object = resultSet.getObject(fieldName);
                valueMap.put(fieldName, object);
            } 
            sourceObjectList.add(valueMap);
			i++;
			if(i > autoETL_ActivityNodeForP.getCacheLine() - 1){
				ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForP.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForP.getAutoETL_TargetTable().getStrTableName());
				sourceObjectList = new ArrayList<Map<String,Object>>();
				i = 0;
			}
		}

		ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForP.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForP.getAutoETL_TargetTable().getStrTableName());
		resultSet.close();
		
		return lines;
	}
	
	@SuppressWarnings("unchecked")
	private void ExceuteProcedureTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForPro.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForPro> autoETL_ActivityNodeForProList = (List<AutoETL_ActivityNodeForPro>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForProList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForPro autoETL_ActivityNodeForPro = autoETL_ActivityNodeForProList.get(0);
		
		doProcedureResult(autoETL_ActivityNodeForPro.getAutoETL_Procedure(),activeNodeETL_WorkflowParamMVList);
	}
	
	@SuppressWarnings("unchecked")
	private void ExceuteFtpTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForFTPTra.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForFTPTra> autoETL_ActivityNodeForFTPTraList = (List<AutoETL_ActivityNodeForFTPTra>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForFTPTraList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForFTPTra autoETL_ActivityNodeForFTPTra = autoETL_ActivityNodeForFTPTraList.get(0);
		if(autoETL_ActivityNodeForFTPTra.getStrFTPTraType() != null && autoETL_ActivityNodeForFTPTra.getStrFTPTraType().equals("2")){
			ExceuteSftpTranslate(autoETL_ActivityNodeForFTPTra,activeNodeETL_WorkflowParamMVList);
		}else{
			FTPClient ftpClient = null;
			InputStream flagInputStream = null;
			InputStream dataInputStream = null;
			FileOutputStream fileOutputStream = null;
			try{
				ftpClient = new FTPClient();
				
				String serverPath = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrServerPath();
				String hostServer = "";
				String dictionary = "";
				if(serverPath.indexOf("/") > -1){
					hostServer = serverPath.substring(0,serverPath.indexOf("/"));
					dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
				}
				else{
					hostServer = serverPath;
				}
				
				ftpClient.connect(hostServer);
				ftpClient.login(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrUserID(), autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrPassword());
				if(!dictionary.equals("")){
					ftpClient.changeWorkingDirectory(dictionary);
				}
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.setControlEncoding("UTF-8");
				
				String strPram = "";
				String strSeg="";
				String localFileName = "";
				
				if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrParamRule())){
					strSeg = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(0,autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#"));
					
					String[] strParams = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#") + 1).split(",");
					for(int i = 0;i<strParams.length;i++){
						String param = "";
						for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
							if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
								param = autoETL_WorkflowParamMV.getStrValue();
							}
						}
						if(StringUtils.isBlank(param)){
							throw new Exception("参数规则定义错误");
						}
						strPram += param + strSeg;
					}
				}
				if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrFileName())){
	
					String downloadFileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile(); // 现在只存在“.dat”,可以直接加入文件名的后缀，以后可以去除“.”，直接在界面定义后缀名，然后修改程序中多余的代码
					String tmpLocalFileName = autoETL_ActivityNodeForFTPTra.getStrFileName();
					if(tmpLocalFileName.indexOf("#")>0){
						strSeg = tmpLocalFileName.substring(0,tmpLocalFileName.indexOf("#"));
						String[] strParams = tmpLocalFileName.substring(tmpLocalFileName.indexOf("#") + 1).split(",");
						for(int i = 0;i<strParams.length;i++){
							String param = "";
							for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
								if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
									param = autoETL_WorkflowParamMV.getStrValue();
								}
							}
							if(StringUtils.isBlank(param)){
								throw new Exception("文件名参数规则定义错误");
							}
							localFileName += param + strSeg;
						}
						localFileName = localFileName.substring(0, localFileName.length()-1)+downloadFileSuffix;
					}else{
						localFileName = localFileName+downloadFileSuffix;
					}
				}
				
				String DataFileName="";
				if(autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("A")){
					String tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0];
					String tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[1];
					if(tmpfileName.trim().isEmpty()){
						flagInputStream = ftpClient.retrieveFileStream(strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix);
					}else{
						flagInputStream = ftpClient.retrieveFileStream(tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix);
					}
					tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0];
					tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[1];
					if(tmpfileName.trim().isEmpty()){
						DataFileName=strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
						dataInputStream = ftpClient.retrieveFileStream(DataFileName);
					}else{
						DataFileName=tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
						dataInputStream = ftpClient.retrieveFileStream(DataFileName);
					}				
				}else if (autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("B")){
					if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0].trim().isEmpty()){
						flagInputStream = ftpClient.retrieveFileStream(strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile());
					}else{
						flagInputStream = ftpClient.retrieveFileStream(strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile());
					}
					if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0].trim().isEmpty()){
						DataFileName=strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
						dataInputStream = ftpClient.retrieveFileStream(DataFileName);
					}else{
						DataFileName=strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
						dataInputStream = ftpClient.retrieveFileStream(DataFileName);
					}				
				}			
				else{
					flagInputStream = ftpClient.retrieveFileStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile());
					DataFileName=autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
					dataInputStream = ftpClient.retrieveFileStream(DataFileName);
				}			
				fileOutputStream = new FileOutputStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrLocalPath()+File.separator+localFileName); 
				
				byte[] bytes = new byte[1024*1024];
				int data;
		        while ((data = dataInputStream.read(bytes)) != -1) {
		        	 fileOutputStream.write(bytes, 0, data);
		        }
			}
			finally{
				if(ftpClient != null){
					ftpClient.logout();
					ftpClient.disconnect();
				}
				if(flagInputStream != null){
					flagInputStream.close();
				}
				if(dataInputStream != null){
					dataInputStream.close();
				}
				if(fileOutputStream != null){
					fileOutputStream.close();
				}
			}
		}
	}
	
	private String getFilePath(String path,String strSystemDateFormat,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList){
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			path = path.replaceAll("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		if(path.indexOf("@SYSTEMDATE") > -1) {
			Date now = new Date(System.currentTimeMillis());
			if(StringUtils.isBlank(strSystemDateFormat)){
				strSystemDateFormat = "yyyy-MM-dd HH-mm-ss";
			}
			String sysdate = new SimpleDateFormat(strSystemDateFormat).format(now);
			path = path.replaceAll("@SYSTEMDATE", sysdate);
		}
		
		int dictoryPath=0;
		if(path.indexOf("/") > -1){
			dictoryPath = path.lastIndexOf("/");
		}
		else if(path.indexOf("\\") > -1){
			dictoryPath = path.lastIndexOf("\\");
		}
		else{
			dictoryPath = path.length();
		}
		File fileTxtDictory = new File(path.substring(0,dictoryPath));
		if(!fileTxtDictory.exists()) {
			fileTxtDictory.mkdirs();
		}
		
		return path;
	}
	
	private String getFileSql(Class<?> type,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList,List<AutoETL_ActivityNodeForFileC> autoETL_ActivityNodeForFileCList){
		
		Table table=type.getAnnotation(Table.class);
		
		String hSql = "SELECT * FROM " + table.name();
		
		if(autoETL_ActivityNodeForFileCList != null && autoETL_ActivityNodeForFileCList.size() > 0){
			hSql += " WHERE ";
			
			List<ConditionClass> conditionClassList = new ArrayList<ConditionClass>();
			
			for(AutoETL_ActivityNodeForFileC autoETL_ActivityNodeForFileC : autoETL_ActivityNodeForFileCList){
				ConditionClass conditionClass = new ConditionClass();
				if(autoETL_ActivityNodeForFileC.getAutoRelationFieldID() != null){
					conditionClass.setFieldName(autoETL_ActivityNodeForFileC.getAutoRelationFieldID().getStrFieldName());
				}
				conditionClass.setCompareType(autoETL_ActivityNodeForFileC.getStrConditionType());
				conditionClass.setStrValue(autoETL_ActivityNodeForFileC.getStrValue());
				conditionClass.setConditionJoinType(autoETL_ActivityNodeForFileC.getStrConditionJoinType());
				conditionClassList.add(conditionClass);
			}
			
			hSql += SqlConstructor.getConditionSql(conditionClassList);
		}
		
		if(activeNodeETL_WorkflowParamMVList != null){
        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
        		hSql = hSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
    		}
        }

		return hSql;
	}
	
	private String getExcelSql(Class<?> type,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList,List<AutoETL_ActivityNodeForExcelC> autoETL_ActivityNodeForExcelCList){
		
		Table table=type.getAnnotation(Table.class);
		
		String hSql = "SELECT * FROM " + table.name();
		
		if(autoETL_ActivityNodeForExcelCList != null && autoETL_ActivityNodeForExcelCList.size() > 0){
			hSql += " WHERE ";
			
			List<ConditionClass> conditionClassList = new ArrayList<ConditionClass>();
			
			for(AutoETL_ActivityNodeForExcelC autoETL_ActivityNodeForExcelC : autoETL_ActivityNodeForExcelCList){
				ConditionClass conditionClass = new ConditionClass();
				if(autoETL_ActivityNodeForExcelC.getAutoRelationFieldID() != null){
					conditionClass.setFieldName(autoETL_ActivityNodeForExcelC.getAutoRelationFieldID().getStrFieldName());
				}
				conditionClass.setCompareType(autoETL_ActivityNodeForExcelC.getStrConditionType());
				conditionClass.setStrValue(autoETL_ActivityNodeForExcelC.getStrValue());
				conditionClass.setConditionJoinType(autoETL_ActivityNodeForExcelC.getStrConditionJoinType());
				conditionClassList.add(conditionClass);
			}
			
			hSql += SqlConstructor.getConditionSql(conditionClassList);
		}
		
		if(activeNodeETL_WorkflowParamMVList != null){
        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
        		hSql = hSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
    		}
        }

		return hSql;
	}
	

	@SuppressWarnings("unchecked")
	private int ExceuteFileTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForFile.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
			List<AutoETL_ActivityNodeForFile> autoETL_ActivityNodeForFileList = (List<AutoETL_ActivityNodeForFile>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			if(autoETL_ActivityNodeForFileList.size() == 0){
				throw new Exception("未设置结点");
			}
			
			AutoETL_ActivityNodeForFile autoETL_ActivityNodeForFile = autoETL_ActivityNodeForFileList.get(0);

			String strFilePath = getFilePath(autoETL_ActivityNodeForFile.getStrPath(),autoETL_ActivityNodeForFile.getStrSystemDateFormat(),activeNodeETL_WorkflowParamMVList);
			
			int lines = 0;
			
			String sessionFactory = autoETL_ActivityNodeForFile.getAutoETL_Table().getDataSource().getSessionFactory();
			String tableName = autoETL_ActivityNodeForFile.getAutoETL_Table().getStrTableName();
			Class<?> type = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName));  
			
			List<Field> fieldList = new ArrayList<Field>();
			
			detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForFileF.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForFile", autoETL_ActivityNodeForFile));
			detachedCriteria.addOrder(Order.asc("intOrder"));
			List<AutoETL_ActivityNodeForFileF> autoETL_ActivityNodeForFileFList = (List<AutoETL_ActivityNodeForFileF>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			
			for(AutoETL_ActivityNodeForFileF autoETL_ActivityNodeForFileF : autoETL_ActivityNodeForFileFList){
				fieldList.add(ReflectOperation.getReflectField(type,autoETL_ActivityNodeForFileF.getAutoETL_SourceField().getStrFieldName()));
			}

			IFileHandlerForText fileHandlerForText = new FileHandlerForText();

			if(autoETL_ActivityNodeForFile.getStrIOType().equals("1")){
				lines = fileHandlerForText.WriteFromPathToDatabase(sessionFactory, tableName, fieldList, strFilePath, autoETL_ActivityNodeForFile.getStrDataSegType(), autoETL_ActivityNodeForFile.getCacheLine());
			}
			else if(autoETL_ActivityNodeForFile.getStrIOType().equals("2")){
				
				detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForFileC.class);
				detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForFile", autoETL_ActivityNodeForFile));
				detachedCriteria.addOrder(Order.asc("intOrder"));
				List<AutoETL_ActivityNodeForFileC> autoETL_ActivityNodeForFileCList = (List<AutoETL_ActivityNodeForFileC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
				
				String hSql = getFileSql(type,activeNodeETL_WorkflowParamMVList,autoETL_ActivityNodeForFileCList);
				lines = fileHandlerForText.WriteFromDatabaseToPath(sessionFactory, hSql,fieldList, strFilePath, autoETL_ActivityNodeForFile.getStrDataSegType(), autoETL_ActivityNodeForFile.getCacheLine(),autoETL_ActivityNodeForFile.getStrDateFormat());
			}
			return lines;
	}
	
	@SuppressWarnings("unchecked")
	private int ExceuteExcelTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForExcel.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForExcel> autoETL_ActivityNodeForExcelList = (List<AutoETL_ActivityNodeForExcel>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForExcelList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForExcel autoETL_ActivityNodeForExcel = autoETL_ActivityNodeForExcelList.get(0);

		String strFilePath = getFilePath(autoETL_ActivityNodeForExcel.getStrPath(),autoETL_ActivityNodeForExcel.getStrSystemDateFormat(),activeNodeETL_WorkflowParamMVList);

		int lines = 0;
		
		String sessionFactory = autoETL_ActivityNodeForExcel.getAutoETL_Table().getDataSource().getSessionFactory();
		String tableName = autoETL_ActivityNodeForExcel.getAutoETL_Table().getStrTableName();
		Class<?> type = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName));

		IFileHandlerForExcel fileHandlerForExcel = new FileHandlerForExcel();

		if(autoETL_ActivityNodeForExcel.getStrIOType().equals("1")){
			//lines = fileHandlerForExcel.WriteFromPathToDatabase(showInstance, sessionFactory, tableName, path, sheetNames, targetType, afterIgnorSeg, cacheLine, startLine, startCol);
			ShowInstance showInstance = ReflectOperation.getShowInstance(type, autoETL_ActivityNodeForExcel.getShowInstanceName());
			boolean showForeignId = false;
			if(autoETL_ActivityNodeForExcel.getStrKeyType().equals("1")){
				showForeignId = true;
			}
			boolean showHeader = false;
			if(autoETL_ActivityNodeForExcel.getStrTitleType().equals("1")){
				showHeader = true;
			}
			
			// 导入
			String strErr = fileHandlerForExcel.WriteFromPathToDatabase(showInstance, sessionFactory, ReflectOperation.getAutoDTOTName(sessionFactory, tableName), 
					strFilePath, null, type, showForeignId, showHeader, false, null, ",", 0, 0, null);
			
			try {
				lines = Integer.parseInt(strErr);
			} catch (Exception ex) {
				lines = 0;
				throw new Exception(strErr);
			}
		}
		else if(autoETL_ActivityNodeForExcel.getStrIOType().equals("2")){
			
			detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForExcelC.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForExcel", autoETL_ActivityNodeForExcel));
			detachedCriteria.addOrder(Order.asc("intOrder"));
			List<AutoETL_ActivityNodeForExcelC> autoETL_ActivityNodeForExcelCList = (List<AutoETL_ActivityNodeForExcelC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			
			String tableName2 = autoETL_ActivityNodeForExcel.getAutoETL_Table().getStrTableName();
			Class<?> type2 = Class.forName(ReflectOperation.getAutoDTOTName(sessionFactory, tableName2));  
			String hSql = getExcelSql(type,activeNodeETL_WorkflowParamMVList,autoETL_ActivityNodeForExcelCList);
			String[] sheetNames = autoETL_ActivityNodeForExcel.getStrSheetNames().split(",");
			boolean showForeignId = false;
			if(autoETL_ActivityNodeForExcel.getStrKeyType().equals("1")){
				showForeignId = true;
			}
			boolean showHeader = false;
			if(autoETL_ActivityNodeForExcel.getStrTitleType().equals("1")){
				showHeader = true;
			}
			ShowInstance showInstance = ReflectOperation.getShowInstance(type, autoETL_ActivityNodeForExcel.getShowInstanceName());
			//lines = fileHandlerForExcel.WriteFromDatabaseToPath(showInstance, sessionFactory, hSql, strFilePath, 
			//		strFilePath, sheetNames, showForeignId, autoETL_ActivityNodeForExcel.getCacheLine(), showHeader, 
			//		autoETL_ActivityNodeForExcel.getStartLine(), autoETL_ActivityNodeForExcel.getStartCol());
			
			// 导出
			lines = fileHandlerForExcel.WriteFromDatabaseToPath(showInstance, sessionFactory, strFilePath, 
					autoETL_ActivityNodeForExcel.getStrSheetNames(), hSql, type2, null, showForeignId, showHeader, 
					autoETL_ActivityNodeForExcel.getStartLine(),
					autoETL_ActivityNodeForExcel.getStartCol(), 
					autoETL_ActivityNodeForExcel.getStrDateFormat(),null);
		}
		
		return lines;
	}
	
	@SuppressWarnings("unchecked")
	public String getExceuteTableSql(AutoETL_ActivityNodeForCT autoETL_ActivityNodeForCT,List<AutoETL_ActivityNodeFieldV> autoETL_ActivityNodeFieldVList,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = null;
		DetachedCriteria detachedCriteria = null;
		
		String sourceTName = ReflectOperation.getAutoDTOTName(autoETL_ActivityNodeForCT.getAutoETL_SourceTable().getDataSource().getSessionFactory(), autoETL_ActivityNodeForCT.getAutoETL_SourceTable().getStrTableName());
		
		String tableName = "";
		try{
			Class<?> sourceType = Class.forName(sourceTName);
			Table table = sourceType.getAnnotation(Table.class);
			tableName = table.name();
		}
		catch(Exception ex){
			tableName = autoETL_ActivityNodeForCT.getAutoETL_SourceTable().getStrTableName();
		}
		
		String strTable = " FROM " + tableName;
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeRelationF.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForCT", autoETL_ActivityNodeForCT));
		detachedCriteria.addOrder(Order.asc("intOrder"));
		List<AutoETL_ActivityNodeRelationF> autoETL_ActivityNodeRelationFList = (List<AutoETL_ActivityNodeRelationF>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		String condition = "";
		if(autoETL_ActivityNodeRelationFList.size() > 0){
			
			condition += " WHERE ";
			
			List<ConditionClass> conditionClassList = new ArrayList<ConditionClass>();
			
			for(AutoETL_ActivityNodeRelationF autoETL_ActivityNodeRelationF : autoETL_ActivityNodeRelationFList){
				ConditionClass conditionClass = new ConditionClass();
				if(autoETL_ActivityNodeRelationF.getAutoRelationFieldID() != null){
					conditionClass.setFieldName(autoETL_ActivityNodeRelationF.getAutoRelationFieldID().getStrFieldName());
				}
				conditionClass.setCompareType(autoETL_ActivityNodeRelationF.getStrConditionType());
				conditionClass.setStrValue(autoETL_ActivityNodeRelationF.getStrValue());
				conditionClass.setConditionJoinType(autoETL_ActivityNodeRelationF.getStrConditionJoinType());
				conditionClassList.add(conditionClass);
			}

			condition += SqlConstructor.getConditionSql(conditionClassList);
		}
		
		String strCaseWhen = "SELECT ";
		
		int fieldVCount = 0;
		for(AutoETL_ActivityNodeFieldV autoETL_ActivityNodeFieldV : autoETL_ActivityNodeFieldVList){
			
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeFieldVCa.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeFieldV", autoETL_ActivityNodeFieldV));
			List<AutoETL_ActivityNodeFieldVCa> autoETL_ActivityNodeFieldVCaseList = (List<AutoETL_ActivityNodeFieldVCa>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			
			if(autoETL_ActivityNodeFieldVCaseList.size() == 0){
				if(StringUtils.isBlank(autoETL_ActivityNodeFieldV.getStrValue())){
					strCaseWhen += autoETL_ActivityNodeFieldV.getAutoETL_SourceField().getStrFieldName() + " AS " + autoETL_ActivityNodeFieldV.getAutoETL_TargetField().getStrFieldName();
				}
				else{
					strCaseWhen += autoETL_ActivityNodeFieldV.getStrValue() + " AS " + autoETL_ActivityNodeFieldV.getAutoETL_TargetField().getStrFieldName();
				}
			}
			else{
				
				strCaseWhen += " CASE ";
				
				for(AutoETL_ActivityNodeFieldVCa autoETL_ActivityNodeFieldVCase : autoETL_ActivityNodeFieldVCaseList){
					singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeFieldVCo.class);
					detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeFieldVCase", autoETL_ActivityNodeFieldVCase));
					List<AutoETL_ActivityNodeFieldVCo> autoETL_ActivityNodeFieldVCoList = (List<AutoETL_ActivityNodeFieldVCo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
					if(autoETL_ActivityNodeFieldVCoList.size() == 0){
						strCaseWhen += " WHEN " + autoETL_ActivityNodeFieldV.getAutoETL_SourceField().getStrFieldName() + "=" + autoETL_ActivityNodeFieldVCase.getStrWhenValue() + " THEN " + autoETL_ActivityNodeFieldVCase.getStrThenValue();
					}
					else{
						String whenCondtion = "";
						
						List<ConditionClass> conditionClassList = new ArrayList<ConditionClass>();
						
						for(AutoETL_ActivityNodeFieldVCo autoETL_ActivityNodeFieldVCo : autoETL_ActivityNodeFieldVCoList){
							ConditionClass conditionClass = new ConditionClass();
							if(autoETL_ActivityNodeFieldVCo.getAutoRelationFieldID() != null){
								conditionClass.setFieldName(autoETL_ActivityNodeFieldVCo.getAutoRelationFieldID().getStrFieldName());
							}
							conditionClass.setCompareType(autoETL_ActivityNodeFieldVCo.getStrConditionType());
							conditionClass.setStrValue(autoETL_ActivityNodeFieldVCo.getStrValue());
							conditionClass.setConditionJoinType(autoETL_ActivityNodeFieldVCo.getStrConditionJoinType());
							conditionClassList.add(conditionClass);
						}
						
						whenCondtion += SqlConstructor.getConditionSql(conditionClassList);

						strCaseWhen += " WHEN " + whenCondtion + " THEN " + autoETL_ActivityNodeFieldVCase.getStrThenValue();
					}
				}
				
				strCaseWhen += " END AS " + autoETL_ActivityNodeFieldV.getAutoETL_TargetField().getStrFieldName();
			}
			
			if(fieldVCount != autoETL_ActivityNodeFieldVList.size() -1){
				strCaseWhen += ",";
			}
			fieldVCount++;
		}
		
		String strSql = strCaseWhen + strTable + condition;
		
        if(activeNodeETL_WorkflowParamMVList != null){
        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
    			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
    		}
        }
		
		return strSql;
	}
	
	@SuppressWarnings({ "unchecked" })
	private int ExceuteTableTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForCT.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForCT> autoETL_ActivityNodeForCTList = (List<AutoETL_ActivityNodeForCT>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForCTList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForCT autoETL_ActivityNodeForCT = autoETL_ActivityNodeForCTList.get(0);
		
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeFieldV.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForCT", autoETL_ActivityNodeForCT));
		List<AutoETL_ActivityNodeFieldV> autoETL_ActivityNodeFieldVList = (List<AutoETL_ActivityNodeFieldV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeFieldVList.size() == 0){
			throw new Exception("未设置映射");
		}
		
		String strSql = getExceuteTableSql(autoETL_ActivityNodeForCT,autoETL_ActivityNodeFieldVList,activeNodeETL_WorkflowParamMVList);
		
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
		ResultSet resultSet = (ResultSet)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{strSql,autoETL_ActivityNodeForCT.getAutoETL_SourceTable().getDataSource().getSessionFactory()});
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
		int i = 0;
		int fieldCount = resultSetMetaData.getColumnCount();  
		int lines = 0;
		while (resultSet.next()){
			lines ++;
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            for (int j = 1; j <= fieldCount; j++) {  
                String fieldName = resultSetMetaData.getColumnName(j);   
                Object object = resultSet.getObject(fieldName);
                valueMap.put(fieldName, object);
            } 
            sourceObjectList.add(valueMap);
			i++;
			if(i > autoETL_ActivityNodeForCT.getCacheLine() - 1){
				ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForCT.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForCT.getAutoETL_TargetTable().getStrTableName());
				sourceObjectList = new ArrayList<Map<String,Object>>();
				i = 0;
			}
		}
		
		ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForCT.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForCT.getAutoETL_TargetTable().getStrTableName());
		
		resultSet.close();
		
		return lines;
	}
	
	@SuppressWarnings("unchecked")
	public String getExceuteViewSql(AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV,List<AutoETL_ActivityNodeFieldViewV> autoETL_ActivityNodeFieldViewVList,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = null;
		DetachedCriteria detachedCriteria = null;
		
		String strTable = " FROM " + autoETL_ActivityNodeForCV.getAutoETL_SourceView().getStrTableName();
		
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeRelationV.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForCV", autoETL_ActivityNodeForCV));
		detachedCriteria.addOrder(Order.asc("intOrder"));
		List<AutoETL_ActivityNodeRelationV> autoETL_ActivityNodeRelationVList = (List<AutoETL_ActivityNodeRelationV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		String condition = "";
		if(autoETL_ActivityNodeRelationVList.size() > 0){
			
            condition += " WHERE ";
			
			List<ConditionClass> conditionClassList = new ArrayList<ConditionClass>();
			
			for(AutoETL_ActivityNodeRelationV autoETL_ActivityNodeRelationV : autoETL_ActivityNodeRelationVList){
				ConditionClass conditionClass = new ConditionClass();
				if(autoETL_ActivityNodeRelationV.getAutoSourceViewFieldID() != null){
					conditionClass.setFieldName(autoETL_ActivityNodeRelationV.getAutoSourceViewFieldID().getStrFieldName());
				}
				conditionClass.setCompareType(autoETL_ActivityNodeRelationV.getStrConditionType());
				conditionClass.setStrValue(autoETL_ActivityNodeRelationV.getStrValue());
				conditionClass.setConditionJoinType(autoETL_ActivityNodeRelationV.getStrConditionJoinType());
				conditionClassList.add(conditionClass);
			}

			condition += SqlConstructor.getConditionSql(conditionClassList);
			
		}
		
		String strCaseWhen = "SELECT ";
		
		int fieldVCount = 0;
		for(AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldViewV : autoETL_ActivityNodeFieldViewVList){
			
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeFieldVVCa.class);
			detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeFieldViewV", autoETL_ActivityNodeFieldViewV));
			List<AutoETL_ActivityNodeFieldVVCa> autoETL_ActivityNodeFieldVVCaList = (List<AutoETL_ActivityNodeFieldVVCa>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
			
			if(autoETL_ActivityNodeFieldVVCaList.size() == 0){
				if(StringUtils.isBlank(autoETL_ActivityNodeFieldViewV.getStrValue())){
					strCaseWhen += autoETL_ActivityNodeFieldViewV.getAutoETL_SourceViewField().getStrFieldName() + " AS " + autoETL_ActivityNodeFieldViewV.getReportModel_TagetField().getStrFieldName();
				}
				else{
					strCaseWhen += autoETL_ActivityNodeFieldViewV.getStrValue() + " AS " + autoETL_ActivityNodeFieldViewV.getReportModel_TagetField().getStrFieldName();
				}
			}
			else{
				
				strCaseWhen += " CASE ";
				
				for(AutoETL_ActivityNodeFieldVVCa autoETL_ActivityNodeFieldVVCa : autoETL_ActivityNodeFieldVVCaList){
					singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeFieldVVCon.class);
					detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeFieldVVCa", autoETL_ActivityNodeFieldVVCa));
					detachedCriteria.addOrder(Order.asc("intOrder")); //新增排序
					List<AutoETL_ActivityNodeFieldVVCon> autoETL_ActivityNodeFieldVVConList = (List<AutoETL_ActivityNodeFieldVVCon>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
					if(autoETL_ActivityNodeFieldVVConList.size() == 0){
						strCaseWhen += " WHEN " + autoETL_ActivityNodeFieldViewV.getAutoETL_SourceViewField().getStrFieldName() + "=" + autoETL_ActivityNodeFieldVVCa.getStrWhenValue() + " THEN " + autoETL_ActivityNodeFieldVVCa.getStrThenValue();
					}
					else{
						String whenCondtion = "";
						
						List<ConditionClass> conditionClassList = new ArrayList<ConditionClass>();
						
						for(AutoETL_ActivityNodeFieldVVCon autoETL_ActivityNodeFieldVVCon : autoETL_ActivityNodeFieldVVConList){
							ConditionClass conditionClass = new ConditionClass();
							if(autoETL_ActivityNodeFieldVVCon.getAutoRelationFieldViewID() != null){
								conditionClass.setFieldName(autoETL_ActivityNodeFieldVVCon.getAutoRelationFieldViewID().getStrFieldName());
							}
							conditionClass.setCompareType(autoETL_ActivityNodeFieldVVCon.getStrConditionType());
							conditionClass.setStrValue(autoETL_ActivityNodeFieldVVCon.getStrValue());
							conditionClass.setConditionJoinType(autoETL_ActivityNodeFieldVVCon.getStrConditionJoinType());
							conditionClassList.add(conditionClass);
						}
						
						whenCondtion += SqlConstructor.getConditionSql(conditionClassList);

						strCaseWhen += " WHEN " + whenCondtion + " THEN " + autoETL_ActivityNodeFieldVVCa.getStrThenValue();
					}
				}
				
				strCaseWhen += " END AS " + autoETL_ActivityNodeFieldViewV.getReportModel_TagetField().getStrFieldName();
			}
			
			if(fieldVCount != autoETL_ActivityNodeFieldViewVList.size() -1){
				strCaseWhen += ",";
			}
			fieldVCount++;
		}
		
		String strSql = strCaseWhen + strTable + condition;
		
        if(activeNodeETL_WorkflowParamMVList != null){
        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
    			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
    		}
        }
		
		return strSql;
	}
	
	@SuppressWarnings({ "unchecked" })
	private int ExceuteViewTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForCV.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForCV> autoETL_ActivityNodeForCVList = (List<AutoETL_ActivityNodeForCV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForCVList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV = autoETL_ActivityNodeForCVList.get(0);
		
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeFieldViewV.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForCV", autoETL_ActivityNodeForCV));
		List<AutoETL_ActivityNodeFieldViewV> autoETL_ActivityNodeFieldViewVList = (List<AutoETL_ActivityNodeFieldViewV>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeFieldViewVList.size() == 0){
			throw new Exception("未设置映射");
		}
		
		String strSql = getExceuteViewSql(autoETL_ActivityNodeForCV,autoETL_ActivityNodeFieldViewVList,activeNodeETL_WorkflowParamMVList);
		
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
		ResultSet resultSet = (ResultSet)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{strSql,autoETL_ActivityNodeForCV.getAutoETL_SourceView().getDataSource().getSessionFactory()});
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
		int i = 0;
		int fieldCount = resultSetMetaData.getColumnCount();  
		int lines = 0;
		while (resultSet.next()){
			lines ++;
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            for (int j = 1; j <= fieldCount; j++) {  
                String fieldName = resultSetMetaData.getColumnName(j);   
                Object object = resultSet.getObject(fieldName);
                valueMap.put(fieldName, object);
            } 
            sourceObjectList.add(valueMap);
			i++;
			if(i > autoETL_ActivityNodeForCV.getCacheLine() - 1){
				ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getStrTableName());
				sourceObjectList = new ArrayList<Map<String,Object>>();
				i = 0;
			}
		}
		
		ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getDataSource().getSessionFactory(),autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getStrTableName());
		
		resultSet.close();
		
		return lines;
	}
	
	@SuppressWarnings({ "unchecked" })
	private String ExceuteCheckTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForCheck.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForCheck> autoETL_ActivityNodeForCheckList = (List<AutoETL_ActivityNodeForCheck>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForCheckList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForCheck autoETL_ActivityNodeForCheck = autoETL_ActivityNodeForCheckList.get(0);
		
		CheckInstance checkInstance = null;
		if(autoETL_ActivityNodeForCheck.getStrInstanceSourceType().equals("1")){
			checkInstance = CheckContext.getInstance().getCheckInstanceMap().get(autoETL_ActivityNodeForCheck.getStrCheckName());

		}
		else if(autoETL_ActivityNodeForCheck.getStrInstanceSourceType().equals("2")){
			IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("checkInstanceCopyService");
			checkInstance = (CheckInstance)paramObjectResultExecute.paramObjectResultExecute(autoETL_ActivityNodeForCheck.getStrCheckName());
		}
		
		if(checkInstance == null){
			throw new Exception("校验实例不存在");
		}
		
		for(CheckTable checkTable: checkInstance.getCheckTableList()){
			if(checkTable.getCheckUniqueFieldList()==null || checkTable.getCheckUniqueFieldList().size()==0){
				throw new Exception("请在校验实例下表校验下添加唯一键");
			}
		}
		
		Map<String,String> paramMap = new HashMap<String,String>();
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			paramMap.put(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		checkInstance.setStrSessionFactory(this.getStrSessionFactory());
		MessageResult messageResult = (MessageResult)checkInstance.paramObjectResultExecute(paramMap,autoETL_ActivityNodeForCheck.getStrCheckClass(),autoETL_ActivityNodeForCheck.getStrEntityClass());
		String message = "";
		if(messageResult.getMessageList().size()>200){
			List<String> messageList =new ArrayList<String>();
			for (int i = 0; i < 200; i++) {
				messageList.add(messageResult.getMessageList().get(i));
			}
			messageResult.setMessageList(messageList);
		}
		messageResult.AlertTranslate();
		if(messageResult.getMessage().length() > 1998){
			message = messageResult.getMessage().substring(0, 1998);
		}
		else{
			message = messageResult.getMessage();
		}
		if(autoETL_ActivityNodeForCheck.getStrActivityNodeErrorType().equals("1")){
			return message;
		}
		else{
			if(messageResult.isSuccess()){
				return message;
			}
			else{
				throw new Exception(message);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private String ExceuteCalculateTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForCalculate.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForCalculate> autoETL_ActivityNodeForCalculateList = (List<AutoETL_ActivityNodeForCalculate>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForCalculateList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForCalculate autoETL_ActivityNodeForCalculate = autoETL_ActivityNodeForCalculateList.get(0);
		
		IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("reportMergeCopyService");
		
		Map<String,String> paramMap = new LinkedHashMap<String,String>();
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			paramMap.put(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		
		Object object =  paramObjectResultExecute.paramObjectResultExecute(new Object[]{
				autoETL_ActivityNodeForCalculate.getStrCalculateName(),
				paramMap,
				autoETL_ActivityNodeForCalculate.getIntPrecise(),
				autoETL_ActivityNodeForCalculate.getStrFreq()});
		MessageResult messageResult=(MessageResult)object;

		String message = "";
		messageResult.AlertTranslate();
		if(messageResult.getMessage()!=null&&messageResult.getMessage().length() > 1998){
			message = messageResult.getMessage().substring(0, 1998);
		}
		else{
			message = messageResult.getMessage();
		}
		
		if(autoETL_ActivityNodeForCalculate.getStrActivityNodeErrorType().equals("1")){
			return message;
		}
		else{
			if(messageResult.isSuccess()){
				return message;
			}
			else{
				throw new Exception(message);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private String ExceuteRptCheckTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForRptCheck.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForRptCheck> autoETL_ActivityNodeForCheckList = (List<AutoETL_ActivityNodeForRptCheck>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForCheckList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForRptCheck autoETL_ActivityNodeForCheck = autoETL_ActivityNodeForCheckList.get(0);
		
		IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("reportItemCheckWFService");
		
		Map<String,String> paramMap = new LinkedHashMap<String,String>();
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			paramMap.put(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		
		Object object =  paramObjectResultExecute.paramObjectResultExecute(new Object[]{autoETL_ActivityNodeForCheck.getStrCalculateName(),
																					paramMap,
																					autoETL_ActivityNodeForCheck.getIntPrecise(),
																					autoETL_ActivityNodeForCheck.getStrFreq()});
		MessageResult messageResult=(MessageResult)object;

		String message = "";
		messageResult.AlertTranslate();
		if(messageResult.getMessage().length() > 1998){
			message = messageResult.getMessage().substring(0, 1998);
		}
		else{
			message = messageResult.getMessage();
		}
		
		if(autoETL_ActivityNodeForCheck.getStrActivityNodeErrorType().equals("1")){
			return message;
		}
		else{
			if(messageResult.isSuccess()){
				return message;
			}
			else{
				throw new Exception(message);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private String ExceuteSummaryTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSummary.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForSummary> autoETL_ActivityNodeForSummaryList = (List<AutoETL_ActivityNodeForSummary>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForSummaryList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForSummary autoETL_ActivityNodeForSummary = autoETL_ActivityNodeForSummaryList.get(0);
		

		IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("reportSummaryCopyService");
		
		Map<String,String> paramMap = new LinkedHashMap<String,String>();
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			paramMap.put(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		
		Object object =  paramObjectResultExecute.paramObjectResultExecute(new Object[]{autoETL_ActivityNodeForSummary.getStrSummaryName(),paramMap,autoETL_ActivityNodeForSummary.getInstInfo().getStrInstCode(),autoETL_ActivityNodeForSummary.getIntPrecise()});
		MessageResult messageResult=(MessageResult)object;

		String message = "";
		messageResult.AlertTranslate();
		if(messageResult.getMessage().length() > 1998){
			message = messageResult.getMessage().substring(0, 1998);
		}
		else{
			message = messageResult.getMessage();
		}
		
		if(autoETL_ActivityNodeForSummary.getStrActivityNodeErrorType().equals("1")){
			return message;
		}
		else{
			if(messageResult.isSuccess()){
				return message;
			}
			else{
				throw new Exception(message);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private String ExceuteKettleTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForKettle.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForKettle> autoETL_ActivityNodeForJavaList = (List<AutoETL_ActivityNodeForKettle>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForJavaList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForKettle autoETL_ActivityNodeForKettle = autoETL_ActivityNodeForJavaList.get(0);
		
		String kettleUrl= autoETL_ActivityNodeForKettle.getStrKettlePath();
		if(kettleUrl==null || kettleUrl.equals("")){
			throw new Exception("kettle文件地址为空");
		}
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			kettleUrl = kettleUrl.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		try {
			KettleEnvironment.init();
		} catch (KettleException e) {
			e.printStackTrace();
			ExceptionLog.CreateLog(e);
		}
		
		String bigWordKettleUrl = new String(kettleUrl);
		if(bigWordKettleUrl.toLowerCase().endsWith(".ktr")){
			Trans trans = runTransformationFromFileSystem(kettleUrl,activeNodeETL_WorkflowParamMVList);
			LoggingBuffer appender = KettleLogStore.getAppender();
			String logText = appender.getBuffer(trans.getLogChannelId(), false).toString();
			System.out.println(logText);
			return "success";
		}else if(bigWordKettleUrl.toLowerCase().endsWith(".kjb")){
			Job job = runJobFromFileSystem(kettleUrl,activeNodeETL_WorkflowParamMVList);
			LoggingBuffer appender = KettleLogStore.getAppender();
			String logText = appender.getBuffer(job.getLogChannelId(), false).length() > 0 ? appender.getBuffer(job.getLogChannelId(), false).toString() : null;
			System.out.println(logText);
			return "success";
		}
		//征信版本合并
		else{
			throw new Exception("文件格式错误");
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private String ExceuteDeleteFiles(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		new ExecuteDelFileActivityNode().Execute(autoETL_ActivityNode, activeNodeETL_WorkflowParamMVList);
		return "Delete file Success";
	}
	
	private String getUniqueStr(Map<String,Object> mapObject, List<CheckUniqueField> checkUniqueFieldList){
		String uniqueStr = "唯一属性，";
		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			uniqueStr += checkUniqueField.getDiscription() + "：" + mapObject.get(checkUniqueField.getName().toUpperCase()) + "，";
		}
		return uniqueStr;
	}
	
	private String getLogicPStr(Map<String,Object> mapObject, List<CheckUniqueField> checkUniqueFieldList){
		String str = "数据，";
		for(CheckUniqueField checkUniqueField: checkUniqueFieldList){
			str += checkUniqueField.getDiscription() + "：" + mapObject.get(checkUniqueField.getName().toUpperCase()) + "，";
		}
		return str;
	}
	
	@SuppressWarnings("unchecked")
	private void CheckInstanceWithList(CheckInstance checkInstance,List<Map<String,Object>> list,MessageResult messageResult) throws Exception{
		if(list.size() > 0){
			Map errorStateMap = new HashMap<String,Boolean>();
			checkInstance.setStrSessionFactory(this.getStrSessionFactory());
			Map<String,Map<String,String>> valueMap = new HashMap<String,Map<String,String>>();
			for(CheckTable checkTable : checkInstance.getCheckTableList()){
		
				for(Object object : list){
					for(CheckFieldBasic checkFieldBasic : checkTable.getCheckFieldBasicList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						
						String uniqueStr = getUniqueStr(mapObject, checkTable.getCheckUniqueFieldList());
						
						List<String> messageList = checkFieldBasic.Check(mapObject,uniqueStr);
						
						for(String message : messageList){
							if(messageResult.getErrorFieldList().size() < 200){
								messageResult.getErrorFieldList().add(new ErrorField(checkFieldBasic.getName(), MessageResult.COLORRED, message));
							}
						}
						if(messageList.size() > 0){
							errorStateMap.put(uniqueStr, false);
						}
					}
				}
				
				for(Object object : list){
					for(CheckFieldEffective checkFieldEffective : checkTable.getCheckFieldEffectiveList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						String uniqueStr = getUniqueStr(mapObject, checkTable.getCheckUniqueFieldList());
						List<String> messageList = checkFieldEffective.Check(mapObject,uniqueStr,new HashMap<String,String>());
						for(String message : messageList){
							if(messageResult.getErrorFieldList().size() < 200){
								messageResult.getErrorFieldList().add(new ErrorField(checkFieldEffective.getName(), MessageResult.COLORRED, message));
							}
						}
						if(messageList.size() > 0){
							errorStateMap.put(uniqueStr, false);
						}
					}
				}
				
				for(Object object : list){
					for(CheckFieldCaseWhen checkFieldCaseWhen : checkTable.getCheckFieldCaseWhenList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						String uniqueStr = getUniqueStr(mapObject, checkTable.getCheckUniqueFieldList());
						List<String> messageList = checkFieldCaseWhen.Check(mapObject,uniqueStr,valueMap);
						for(String message : messageList){
							if(messageResult.getErrorFieldList().size() < 200){
								messageResult.getErrorFieldList().add(new ErrorField(checkFieldCaseWhen.getName(), MessageResult.COLORRED, message));
							}
						}
						if(messageList.size() > 0){
							errorStateMap.put(uniqueStr, false);
						}
					}
				}

				for(Object object : list){
					for(CheckFieldOr checkFieldOr : checkTable.getCheckFieldOrList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						String uniqueStr = getUniqueStr(mapObject, checkTable.getCheckUniqueFieldList());
						List<String> messageList = checkFieldOr.Check(mapObject,uniqueStr);

						for(String message : messageList){
							if(messageResult.getMessageSet().size() < 200){
								messageResult.getMessageSet().add(message);
							}

						}
						if(messageList.size() > 0){
							errorStateMap.put(uniqueStr, false);
						}
					}
				}
				
				for(CheckFieldLine checkFieldLine : checkTable.getCheckFieldLineList()){					
					for(Object object : list){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						String uniqueStr = getUniqueStr(mapObject, checkTable.getCheckUniqueFieldList());
						List<String> messageList = checkFieldLine.Check(mapObject,uniqueStr);
						for(String message : messageList){
							if(messageResult.getMessageSet().size() < 200){
								messageResult.getMessageSet().add(message);
							}
						}
						if(messageList.size() > 0){
							errorStateMap.put(uniqueStr, false);
						}
					}
				}
				
				for(Object object : list){
					Map<String,Object> mapObject = (Map<String,Object>)object;
					String uniqueStr = getUniqueStr(mapObject, checkTable.getCheckUniqueFieldList());
					if(errorStateMap.containsKey(uniqueStr)){
						mapObject.put(checkTable.getErrorStateField(), checkTable.getErrorStateValue());
					}
					else{
						mapObject.put(checkTable.getErrorStateField(), checkTable.getSuccessStateValue());
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private StatisticalCount ExceuteProceTranslateWithCheck(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		
		StatisticalCount statisticalCount = new StatisticalCount();
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForProceTranslateWithCheck.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForProceTranslateWithCheck> activityNodeForProceTranslateWithCheckList = (List<AutoETL_ActivityNodeForProceTranslateWithCheck>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(activityNodeForProceTranslateWithCheckList.size() == 0){
			throw new Exception("未设置结点");
		}

		AutoETL_ActivityNodeForProceTranslateWithCheck activityNodeForProceTranslateWithCheck = activityNodeForProceTranslateWithCheckList.get(0);
	
		CheckInstance checkInstance = null;
		if(activityNodeForProceTranslateWithCheck.getStrInstanceSourceType().equals("1")){
			checkInstance = CheckContext.getInstance().getCheckInstanceMap().get(activityNodeForProceTranslateWithCheck.getStrCheckName());

		}
		else if(activityNodeForProceTranslateWithCheck.getStrInstanceSourceType().equals("2")){
			IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("checkInstanceCopyService");
			checkInstance = (CheckInstance)paramObjectResultExecute.paramObjectResultExecute(activityNodeForProceTranslateWithCheck.getStrCheckName());
		}
		
		if(checkInstance == null){
			throw new Exception("校验实例不存在");
		}
		
		for(CheckTable checkTable: checkInstance.getCheckTableList()){
			if(checkTable.getCheckUniqueFieldList()==null || checkTable.getCheckUniqueFieldList().size()==0){
				throw new Exception("请在校验实例下表校验下添加唯一键");
			}
		}
			
		checkInstance.setStrSessionFactory(this.getStrSessionFactory());
		ResultSet resultSet = getProcedureResultSet(activityNodeForProceTranslateWithCheck.getAutoETL_Procedure(),activeNodeETL_WorkflowParamMVList);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		MessageResult messageResult = new MessageResult();
		List<Map<String,Object>> sourceObjectList = new ArrayList<Map<String,Object>>();
		int i = 0;
		int fieldCount = resultSetMetaData.getColumnCount();  
		int lines = 0;
		while (resultSet.next()){
			lines ++;
			Map<String,Object> valueMap = new LinkedHashMap<String, Object>();  
            for (int j = 1; j <= fieldCount; j++) {  
                String fieldName = resultSetMetaData.getColumnName(j);   
                Object object = resultSet.getObject(fieldName);
                valueMap.put(fieldName.toUpperCase(), object);
            } 
            sourceObjectList.add(valueMap);
			i++;
			if(i > activityNodeForProceTranslateWithCheck.getCacheLine() - 1){

				CheckInstanceWithList(checkInstance,sourceObjectList,messageResult);

				ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,activityNodeForProceTranslateWithCheck.getAutoETL_TargetTable().getDataSource().getSessionFactory(),activityNodeForProceTranslateWithCheck.getAutoETL_TargetTable().getStrTableName());
				sourceObjectList = new ArrayList<Map<String,Object>>();
				i = 0;
			}
		}

		CheckInstanceWithList(checkInstance,sourceObjectList,messageResult);
		ExceuteSqlDataTranslate(sourceObjectList,resultSetMetaData,activityNodeForProceTranslateWithCheck.getAutoETL_TargetTable().getDataSource().getSessionFactory(),activityNodeForProceTranslateWithCheck.getAutoETL_TargetTable().getStrTableName());
		
		resultSet.close();
		
		if(messageResult.getMessageSet().size() > 0 || messageResult.getErrorFieldList().size() > 0){
			messageResult.setSuccess(false);
		}
		
		String message = "";
		messageResult.AlertTranslate();
		if(messageResult.getMessage().length() > 1998){
			message = messageResult.getMessage().substring(0, 1998);
		}
		else{
			message = messageResult.getMessage();
		}
		if(activityNodeForProceTranslateWithCheck.getStrActivityNodeErrorType().equals("1")){
			statisticalCount.setMessage(message);
		}
		else{
			if(messageResult.isSuccess()){
				statisticalCount.setMessage(message);
			}
			else{
				throw new Exception(message);
			}
		}

		statisticalCount.setCorrectCount(lines);

		return statisticalCount;
	}
	
	/**
	 * 统计接口表所有状态值
	 * @author xiajieli
	 * @param autoETL_ActivityNode 活动结点
	 * @param activeNodeETL_WorkflowParamMVList 工作流参数
	 * @return result
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	private String ExceuteTotalTableState(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForTotalTableState.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForTotalTableState> autoETL_ActivityNodeForTotalTableStateList = (List<AutoETL_ActivityNodeForTotalTableState>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForTotalTableStateList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		List<AutoETL_ActivityNodeForTotalTableState> autoETL_ActivityNodeForTotalTableState = autoETL_ActivityNodeForTotalTableStateList;
		String instInfos="instInfo:";
		String reportModel_Tables="strTableName:";
		
		for (AutoETL_ActivityNodeForTotalTableState autoETLActivityNodeForTotalTableState : autoETL_ActivityNodeForTotalTableState) {
			if(autoETLActivityNodeForTotalTableState.getInstInfo()!=null && !instInfos.contains(autoETLActivityNodeForTotalTableState.getInstInfo().getStrInstCode().toString())){
				instInfos += autoETLActivityNodeForTotalTableState.getInstInfo().getStrInstCode() + "&";
			}
			if(autoETLActivityNodeForTotalTableState.getReportModel_Table()!=null && !reportModel_Tables.contains(autoETLActivityNodeForTotalTableState.getReportModel_Table().getStrTableName().toString())){
				reportModel_Tables += autoETLActivityNodeForTotalTableState.getReportModel_Table().getStrTableName() + "&";
			}
		}
		instInfos=instInfos.substring(0,instInfos.length()-1);
		reportModel_Tables=reportModel_Tables.substring(0, reportModel_Tables.length()-1);
		
		String strFixParameter=instInfos + "," + reportModel_Tables;
		
		AutoDTOCountFlowStatusProcess autoDTOCountFlowStatusProcess=new AutoDTOCountFlowStatusProcess();
		return autoDTOCountFlowStatusProcess.Execute(activeNodeETL_WorkflowParamMVList, strFixParameter,this.getStrSessionFactory());
	}

	/**
	 * <p>方法描述:校验表逻辑主键 </p>
	 * <p>方法备注: 
	 * 	1、逻辑主键校验只校验本数据库中的DTO表信息，数据源默认为应用配置的数据源
	 *  2、校验中取校验实体的第一个Table所对应的存储过程所查询出来的数据作为被校验数据
	 *  3、将整个实体表的信息查询出来放入hashmap中做全量数据进行对比，【实体表的逻辑主键作为key,主键作为value】
	 * </p>
	 * @param autoETL_ActivityNode
	 * @param activeNodeETL_WorkflowParamMVList
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-4-28 上午11:24:26</p>
	 */
	private String ExceuteCheckTableLogicP(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForLogicPCheck.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForLogicPCheck> activityNodeForLogicPCheckList = (List<AutoETL_ActivityNodeForLogicPCheck>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(activityNodeForLogicPCheckList.size() == 0){
			throw new Exception("未设置结点");
		}
		AutoETL_ActivityNodeForLogicPCheck activityNodeForLogicPCheck = activityNodeForLogicPCheckList.get(0);
		//实体对应的数据库表名
		String tableName = ReflectOperation.getTableName(Class.forName(activityNodeForLogicPCheck.getStrEntityClass()));
		String logicPrimaryKeyStr = "";
		if(ShowContext.getInstance().getShowEntityMap().get("AutoETL_LogicPrimaryKey")!=null){
			logicPrimaryKeyStr += ShowContext.getInstance().getShowEntityMap().get("AutoETL_LogicPrimaryKey").get(activityNodeForLogicPCheck.getStrEntityClass());
			if(StringUtils.isEmpty(logicPrimaryKeyStr)){
				throw new Exception("校验实体未定义逻辑主键!");
			}
		}else{
			throw new Exception("逻辑主键未定义!");
		}
		
		String[] logicPrimaryKeys = logicPrimaryKeyStr.split(",");
		//校验实体类
		CheckInstance checkInstance = null;
		if(activityNodeForLogicPCheck.getStrInstanceSourceType().equals("1")){
			checkInstance = CheckContext.getInstance().getCheckInstanceMap().get(activityNodeForLogicPCheck.getStrCheckName());

		}
		else if(activityNodeForLogicPCheck.getStrInstanceSourceType().equals("2")){
			IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("checkInstanceCopyService");
			checkInstance = (CheckInstance)paramObjectResultExecute.paramObjectResultExecute(activityNodeForLogicPCheck.getStrCheckName());
		}
		
		if(checkInstance == null){
			throw new Exception("校验实例不存在");
		}
		checkInstance.setStrSessionFactory(this.getStrSessionFactory());
		//TODO 判断实体类与存储过程的表是否对应
		
		int errorCount = 0;
		String mesStr = "";
		
		ResultSet proResultset = null;
		ResultSet tableResultset = null;
		Map<String, Integer> cacheMap = new HashMap<String, Integer>();
		try{
			String tableSql = "SELECT "+logicPrimaryKeyStr+" FROM  "+ tableName;
			
			//校验表信息
			CheckTable checkTable = checkInstance.getCheckTableList().get(0);
			
			List<CheckFieldParamField> checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
			for(AutoETL_WorkflowParamMV param:activeNodeETL_WorkflowParamMVList){
				CheckFieldParamField checkFieldParamField = new CheckFieldParamField();
				checkFieldParamField.setParamName(param.getAutoETL_Param().getStrParamName());
				checkFieldParamField.setType("1");
				checkFieldParamField.setValue(param.getStrValue());
				checkFieldParamFieldList.add(checkFieldParamField);
			}
			checkTable.setCheckFieldParamFieldList(checkFieldParamFieldList);
			
			//校验成功sql更新语句
			String successPreSql= checkTable.successPreSql();
			List<List<String>> successList = new ArrayList<List<String>>();
			//校验失败sql更新语句
			String errorPreSql= checkTable.errorPreSql();
			List<List<String>> errorList = new ArrayList<List<String>>();
			StringBuffer message = new StringBuffer();
			int totalCount=0;
			proResultset = checkTable.getProcdureResulsetData();
			if(proResultset.next()){ 
				//将实体表的所以数据保存到HashTable中
				IParamObjectResultExecute createSqlQueryResultSetDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
				tableResultset = (ResultSet) createSqlQueryResultSetDao.paramObjectResultExecute(new Object[]{tableSql,this.strSessionFactory});
				ResultSetMetaData tableResultsetMetaData = tableResultset.getMetaData();
				while(tableResultset.next()){
					String key = "";
					for(int c= 1; c<=tableResultsetMetaData.getColumnCount();c++){
						key += tableResultset.getObject(c)+"^";
					}
					if(cacheMap.get(key)==null){
						cacheMap.put(key, 1);
					}
					else{
						cacheMap.put(key, cacheMap.get(key)+1);
					}
				}
				//过滤重复逻辑主键，如果 chacheMap中该值大于1者代表重复
				ResultSetMetaData proResultsetMetaData = proResultset.getMetaData();
				do{
					totalCount++;
					String key = "";
					for(int k= 0;k<logicPrimaryKeys.length;k++){
						key += proResultset.getObject(logicPrimaryKeys[k])+"^";
					}
					Map<String, Object> valueMap = new LinkedHashMap<String, Object>();
					for (int c = 1; c <= proResultsetMetaData.getColumnCount(); c++) {
						valueMap.put(proResultsetMetaData.getColumnName(c).toUpperCase(), proResultset.getObject(c));
					}
					Integer value = cacheMap.get(key);
					if(value != null && value>1){
						//代表该逻辑主键重复
						errorList.add(checkTable.writeErrorMsg(valueMap,"Error"));
						message.append(getLogicPStr(valueMap, checkTable.getCheckUniqueFieldList())+"重复！");
						errorCount++;
					}else{
						successList.add(checkTable.writeSuccessMsg(valueMap));
					}
				}while(proResultset.next());
				checkTable.ExcuteSql(successPreSql, successList); 
				checkTable.ExcuteSql(errorPreSql, errorList);
				successList.clear();
				errorList.clear();
			}
			message.insert(0, "总计："+totalCount+"条，重复"+errorCount+"条。");
			mesStr = message.toString();
			if(mesStr.length()>1998){
				mesStr = mesStr.substring(0,1998);
			}
			if(activityNodeForLogicPCheck.getStrActivityNodeErrorType().equals("1")){
				return mesStr;
			}else{
				if(errorCount>0){
					throw new Exception(mesStr);
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(proResultset != null){
				proResultset.close();
			}
			if(tableResultset != null){
				tableResultset.close();
			}
			cacheMap.clear();
			cacheMap = null;
		}
		return mesStr;
	}
		
	
	@SuppressWarnings({ "unchecked" })
	private String ExceuteJavaTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForJava.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForJava> autoETL_ActivityNodeForJavaList = (List<AutoETL_ActivityNodeForJava>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForJavaList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForJava autoETL_ActivityNodeForJava = autoETL_ActivityNodeForJavaList.get(0);
		
		Object objectClass = FrameworkFactory.CreateClass(autoETL_ActivityNodeForJava.getStrClass());
		if(objectClass == null){
			objectClass = FrameworkFactory.CreateBean(autoETL_ActivityNodeForJava.getStrClass());
			if(objectClass == null){
				throw new Exception("未找到类: " + autoETL_ActivityNodeForJava.getStrClass());
			}
		}
		
		String strFixParameter=autoETL_ActivityNodeForJava.getStrFixParameter();
		
		IActivityNodeForJavaExtend activityNodeForJavaExtend = null;
		try{
			activityNodeForJavaExtend = (IActivityNodeForJavaExtend)objectClass;
		}
		catch(Exception ex){
			throw new Exception("类: " + autoETL_ActivityNodeForJava.getStrClass() + " 应继承IActivityNodeForJavaExtend接口");
		}
		
		return activityNodeForJavaExtend.Execute(activeNodeETL_WorkflowParamMVList,strFixParameter);
	}
	
	private void ExceuteMailSend(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForMail.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForMail> autoETL_ActivityNodeForMailList = (List<AutoETL_ActivityNodeForMail>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForMailList.size() == 0){
			throw new Exception("未设置结点");
		}
		AutoETL_ActivityNodeForMail autoETL_ActivityNodeForMail = autoETL_ActivityNodeForMailList.get(0);
		//邮件服务器
		AutoETL_MAIL autoETL_MAIL = autoETL_ActivityNodeForMail.getAutoETL_MAIL();
		String mailReceivers = autoETL_ActivityNodeForMail.getStrReceivers();
		String mailSubject = autoETL_ActivityNodeForMail.getStrMailSubject();
		String mailContent = autoETL_ActivityNodeForMail.getStrMailContent();
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			mailSubject = mailSubject.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
			mailContent = mailContent.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		
		SendMailService sendMailService = new SendMailService(autoETL_MAIL.getStrServerUrl(),autoETL_MAIL.getStrUserID(),autoETL_MAIL.getStrPassword());
		try{
			//邮件节点的sql参数 用 @MAIL_FILD表示 查询结果的第一条第n列
			Map<String,String> nodeParmas = getMailNodeSqlFirstRow(autoETL_ActivityNodeForMail, activeNodeETL_WorkflowParamMVList);
			for(String key : nodeParmas.keySet()){
				mailSubject = mailSubject.replaceAll("@MAIL_"+key , nodeParmas.get(key));
				mailContent = mailContent.replaceAll("@MAIL_"+key,  nodeParmas.get(key));
			}
			sendMailService.sendMail(mailReceivers, mailSubject, mailContent);
		}catch(Exception e){
			//当出现异常时不忽略异常
			if(!autoETL_ActivityNodeForMail.getStrActivityNodeErrorType().equals("1")){
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * <p>方法描述: 获取邮件节点的sql语句的第一条结果集数据</p>
	 * <p>方法备注: </p>
	 * @param autoETL_ActivityNodeForMail
	 * @param activeNodeETL_WorkflowParamMVList
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-4-26 下午2:55:08</p>
	 *
	 */
	private Map<String,String> getMailNodeSqlFirstRow(AutoETL_ActivityNodeForMail autoETL_ActivityNodeForMail,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(autoETL_ActivityNodeForMail.getStrSqlCondition())){
			String strSql = autoETL_ActivityNodeForMail.getStrSqlCondition();
			if(activeNodeETL_WorkflowParamMVList != null){
	        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
	    			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
	    		}
	        }
			IParamObjectResultExecute createSqlQueryResultSetDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
			String sessionFactory = null;
			if(autoETL_ActivityNodeForMail.getAutoETL_DataSource() != null){
				sessionFactory = autoETL_ActivityNodeForMail.getAutoETL_DataSource().getSessionFactory();
			}
			if(sessionFactory == null){
				throw new Exception("未满足启动条件:未选择SQL条件源会话工厂");
			}
			ResultSet resultSet =  (ResultSet)createSqlQueryResultSetDao.paramObjectResultExecute(new Object[]{strSql,sessionFactory});
			if(resultSet !=null && resultSet.next()){
				ResultSetMetaData rsMetaData = resultSet.getMetaData();
				for(int i=1;i<=rsMetaData.getColumnCount();i++){
					map.put(rsMetaData.getColumnName(i),resultSet.getObject(i)+"");
				}
				resultSet.close();
			}
		}
		return map;
	}
	
	//模板参数格式[ 收件人/@MSG_RECEIVES,内容/@MSG_DATA,发送时间/@MSG_SENDTIME ]
	private String ExceuteMSGSend(AutoETL_ActivityNode autoETL_ActivityNode, List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception {
		
		String msg = "";
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForMsg.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForMsg> autoETL_ActivityNodeForMsgList = (List<AutoETL_ActivityNodeForMsg>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForMsgList.size() == 0){
			throw new Exception("未设置结点");
		}
		AutoETL_ActivityNodeForMsg autoETL_ActivityNodeForMsg = autoETL_ActivityNodeForMsgList.get(0);
		//短信服务器
		AutoETL_MSG autoETL_MSG = autoETL_ActivityNodeForMsg.getAutoETL_MSG();
		String postUrl = autoETL_MSG.getIspServer();
		String charset = autoETL_MSG.getCharset();
		String postEntity = autoETL_MSG.getMsgTemplete();
		String receives = autoETL_ActivityNodeForMsg.getStrReceives();
		String msgData = autoETL_ActivityNodeForMsg.getStrMsgData();
		String sendTime = autoETL_ActivityNodeForMsg.getStrSendTime();
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			receives = receives.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
			msgData = msgData.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
			sendTime = sendTime.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		try{
			//邮件节点的sql参数 用 @MAIL_FILD表示 查询结果的第一条第n列
			Map<String,String> nodeParmas = getMsgNodeSqlFirstRow(autoETL_ActivityNodeForMsg, activeNodeETL_WorkflowParamMVList);
			for(String key : nodeParmas.keySet()){
				receives = receives.replaceAll("@MSG_"+key , nodeParmas.get(key));
				msgData = msgData.replaceAll("@MSG_"+key , nodeParmas.get(key));
				sendTime = sendTime.replaceAll("@MSG_"+key , nodeParmas.get(key));
			}
			postEntity = postEntity.replaceAll("@MSG_RECEIVES", receives).replaceAll("@MSG_DATA", msgData).replaceAll("@MSG_SENDTIME", sendTime);
			msg = HttpClientUtil.doPostText(postUrl, postEntity, charset);
			if(!msg.toUpperCase().contains("SUCCESS")){
				throw new Exception("发送失败:"+msg);
			}
		}catch(Exception e){
			//当出现异常时不忽略异常
			if(!autoETL_ActivityNodeForMsg.getStrActivityNodeErrorType().equals("1")){
				e.printStackTrace();
				throw e;
			}
			msg = e.toString();
		}
		
		return msg;
	}
	
	//模型预警分析
	private String ExceuteModelWarning(AutoETL_ActivityNode autoETL_ActivityNode, List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForWarning.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForWarning> autoETL_ActivityNodeForWarningList = (List<AutoETL_ActivityNodeForWarning>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForWarningList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForWarning autoETL_ActivityNodeForWarning = autoETL_ActivityNodeForWarningList.get(0);
		IParamObjectResultExecute excute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("repItemWarningService");
		Map<String,String> paramMap = new LinkedHashMap<String,String>();
		
		for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
			paramMap.put(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
		}
		
		Object object =  excute.paramObjectResultExecute(new Object[]{autoETL_ActivityNodeForWarning.getStrWaringModel(),paramMap});
		MessageResult messageResult=(MessageResult)object;

		String message = "";
		messageResult.AlertTranslate();
		if(messageResult.getMessage().length() > 1998){
			message = messageResult.getMessage().substring(0, 1998);
		}
		else{
			message = messageResult.getMessage();
		}
		
		if(messageResult.isSuccess()){
			return message;
		}
		else{
			throw new Exception(message);
		}
	}
	

	/**
	 * <p>方法描述: 获取信息节点的sql语句的第一条结果集数据</p>
	 * <p>方法备注: </p>
	 * @param autoETL_ActivityNodeForMsg
	 * @param activeNodeETL_WorkflowParamMVList
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-5-12 上午11:01:00</p>
	 *
	 */
	private Map<String,String> getMsgNodeSqlFirstRow(AutoETL_ActivityNodeForMsg autoETL_ActivityNodeForMsg,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(autoETL_ActivityNodeForMsg.getStrSqlCondition())){
			String strSql = autoETL_ActivityNodeForMsg.getStrSqlCondition();
			if(activeNodeETL_WorkflowParamMVList != null){
	        	for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV : activeNodeETL_WorkflowParamMVList){
	    			strSql = strSql.replace("@" + autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETL_WorkflowParamMV.getStrValue());
	    		}
	        }
			IParamObjectResultExecute createSqlQueryResultSetDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
			String sessionFactory = null;
			if(autoETL_ActivityNodeForMsg.getAutoETL_DataSource() != null){
				sessionFactory = autoETL_ActivityNodeForMsg.getAutoETL_DataSource().getSessionFactory();
			}
			if(sessionFactory == null){
				throw new Exception("未满足启动条件:未选择SQL条件源会话工厂");
			}
			ResultSet resultSet =  (ResultSet)createSqlQueryResultSetDao.paramObjectResultExecute(new Object[]{strSql,sessionFactory});
			if(resultSet !=null && resultSet.next()){
				ResultSetMetaData rsMetaData = resultSet.getMetaData();
				for(int i=1;i<=rsMetaData.getColumnCount();i++){
					map.put(rsMetaData.getColumnName(i),resultSet.getObject(i)+"");
				}
				resultSet.close();
			}
		}
		return map;
	}
	
	
	public class StatisticalCount{
		public StatisticalCount(){
			this.correctCount = 0;
			this.errorCount = 0;
		}
		
		private int correctCount;
		private int errorCount;
		private String message;
		public void setCorrectCount(int correctCount) {
			this.correctCount = correctCount;
		}
		public int getCorrectCount() {
			return correctCount;
		}
		public void setErrorCount(int errorCount) {
			this.errorCount = errorCount;
		}
		public int getErrorCount() {
			return errorCount;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
	}
	
	/**
	 * This method executes a transformation defined in a ktr file
	 * 
	 * It demonstrates the following:
	 * 
	 * - Loading a transformation definition from a ktr file
	 * - Setting named parameters for the transformation
	 * - Setting the log level of the transformation
	 * - Executing the transformation, waiting for it to finish
	 * - Examining the result of the transformation
	 * 
	 * @param filename the file containing the transformation to execute (ktr file)
	 * @return the transformation that was executed, or null if there was an error
	 * @throws Exception 
	 */          
	public Trans runTransformationFromFileSystem(String filename,List<AutoETL_WorkflowParamMV> kettleParameterList ) throws Exception{
		
		try {
			TransMeta transMeta = new TransMeta(filename, (Repository) null);
			List<String> declaredParameters = Arrays.asList(transMeta.listParameters());
			if(kettleParameterList.size()>0 && declaredParameters.size()>0)
			for (AutoETL_WorkflowParamMV autoETLWorkflowParamMV : kettleParameterList) {
				if(declaredParameters.contains(autoETLWorkflowParamMV.getAutoETL_Param().getStrParamName()))
				transMeta.setParameterValue(autoETLWorkflowParamMV.getAutoETL_Param().getStrParamName(), autoETLWorkflowParamMV.getStrValue());
			}
			
			Trans transformation = new Trans(transMeta);
			transformation.execute(new String[0]);
			transformation.waitUntilFinished();
			
			return transformation;

		} catch (Exception e) {
			e.printStackTrace();
			ExceptionLog.CreateLog(e);
			throw e; 
		}
		
	}
	
	/**
	 * This method executes a job defined in a kjb file
	 * 
	 * It demonstrates the following:
	 * 
	 * - Loading a job definition from a kjb file
	 * - Setting named parameters for the job
	 * - Setting the log level of the job
	 * - Executing the job, waiting for it to finish
	 * - Examining the result of the job
	 * 
	 * @param filename the file containing the job to execute (kjb file)
	 * @return the job that was executed, or null if there was an error
	 * @throws Exception 
	 */
	public Job runJobFromFileSystem(String filename,List<AutoETL_WorkflowParamMV> kettleParameterList) throws Exception {
		try {
			JobMeta jobMeta = new JobMeta(filename, null);
			List<String> declaredParametersList = Arrays.asList(jobMeta.listParameters());
			for (int i = 0; i < kettleParameterList.size() && declaredParametersList.size()>0; i++) {
				String parameterName = kettleParameterList.get(i).getAutoETL_Param().getStrParamName();
				if(declaredParametersList.contains(parameterName))
				jobMeta.setParameterValue(parameterName, kettleParameterList.get(i).getStrValue());	
			}
			Job job = new Job(null, jobMeta);
			job.start();
			job.waitUntilFinished();
			return job;
		} catch (Exception e) {
			ExceptionLog.CreateLog(e);
			e.printStackTrace();
			throw e;
		} 
	}

	private void ExceuteFtpUploadTranslate(AutoETL_ActivityNode autoETL_ActivityNode,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForFTPTra.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNode", autoETL_ActivityNode));
		List<AutoETL_ActivityNodeForFTPTra> autoETL_ActivityNodeForFTPTraList = (List<AutoETL_ActivityNodeForFTPTra>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,this.strSessionFactory});
		if(autoETL_ActivityNodeForFTPTraList.size() == 0){
			throw new Exception("未设置结点");
		}
		
		AutoETL_ActivityNodeForFTPTra autoETL_ActivityNodeForFTPTra = autoETL_ActivityNodeForFTPTraList.get(0);
		if(autoETL_ActivityNodeForFTPTra.getStrFTPTraType() != null && autoETL_ActivityNodeForFTPTra.getStrFTPTraType().equals("2")){
			ExceuteSftpUploadTranslate(autoETL_ActivityNodeForFTPTra,activeNodeETL_WorkflowParamMVList);
		}else{
			FTPClient ftpClient = null;
			FTPClient flagFtpClient = null;
			OutputStream flagOutputStream = null;
			OutputStream dataOutputStream = null;
			FileInputStream fileInputStream = null;
			FileInputStream flagFileInputStream = null;
			try{
				ftpClient = new FTPClient();
				flagFtpClient = new FTPClient();
				
				String serverPath = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrServerPath();
				String hostServer = "";
				String dictionary = "";
				if(serverPath.indexOf("/") > -1){
					hostServer = serverPath.substring(0,serverPath.indexOf("/"));
					dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
				}
				else{
					hostServer = serverPath;
				}
				
				ftpClient.connect(hostServer);
				ftpClient.login(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrUserID(), autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrPassword());
				ftpClient.setControlEncoding("UTF-8");
				if(!dictionary.equals("")){
					ftpClient.changeWorkingDirectory(dictionary);
				}
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				
				flagFtpClient.connect(hostServer);
				flagFtpClient.login(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrUserID(), autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrPassword());
				flagFtpClient.setControlEncoding("UTF-8");
				if(!dictionary.equals("")){
					flagFtpClient.changeWorkingDirectory(dictionary);
				}
				flagFtpClient.setFileType(FTP.BINARY_FILE_TYPE);
				
				String strPram = "";
				String strSeg="";
				String localFileName = "";
				String localFlagFileName = "";
				
				if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrParamRule())){
					strSeg = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(0,autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#"));
					
					String[] strParams = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#") + 1).split(",");
					for(int i = 0;i<strParams.length;i++){
						String param = "";
						for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
							if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
								param = autoETL_WorkflowParamMV.getStrValue();
							}
						}
						if(StringUtils.isBlank(param)){
							throw new Exception("参数规则定义错误");
						}
						strPram += param + strSeg;
					}
				}
				if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrFileName())){
	
					String downloadFileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile(); // 现在只存在“.dat”,可以直接加入文件名的后缀，以后可以去除“.”，直接在界面定义后缀名，然后修改程序中多余的代码
					String tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
					String tmpLocalFileName = autoETL_ActivityNodeForFTPTra.getStrFileName();
					if(tmpLocalFileName.indexOf("#")>0){
						strSeg = tmpLocalFileName.substring(0,tmpLocalFileName.indexOf("#"));
						String[] strParams = tmpLocalFileName.substring(tmpLocalFileName.indexOf("#") + 1).split(",");
						for(int i = 0;i<strParams.length;i++){
							String param = "";
							for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
								if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
									param = autoETL_WorkflowParamMV.getStrValue();
								}
							}
							if(StringUtils.isBlank(param)){
								throw new Exception("文件名参数规则定义错误");
							}
							localFileName += param + strSeg;
							localFlagFileName += param + strSeg;
						}
						localFileName = localFileName.substring(0, localFileName.length()-1)+downloadFileSuffix;
						localFlagFileName = localFlagFileName.substring(0, localFlagFileName.length()-1)+tmpfileSuffix;
					}else{
						localFileName = localFileName+downloadFileSuffix;
						localFlagFileName = localFlagFileName+tmpfileSuffix;
					}
				}
				
				String DataFileName;
				String FlagFileName;
				if(autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("A")){
					String tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0];
					String tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[1];
					if(tmpfileName.trim().isEmpty()){
						FlagFileName = strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
					}else{
						FlagFileName = tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
					}
					tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0];
					tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[1];
					if(tmpfileName.trim().isEmpty()){
						DataFileName = strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
					}else{
						DataFileName = tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
					}				
				}else if (autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("B")){
					if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0].trim().isEmpty()){
						FlagFileName = strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
					}else{
						FlagFileName = strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
					}
					if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0].trim().isEmpty()){
						DataFileName = strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
					}else{
						DataFileName = strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
					}				
				}			
				else{
					FlagFileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
					DataFileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
				}
				
				flagOutputStream = flagFtpClient.storeFileStream(FlagFileName);
				dataOutputStream = flagFtpClient.storeFileStream(DataFileName);
				
				fileInputStream = new FileInputStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrLocalPath()+File.separator+localFileName);
				flagFileInputStream = new FileInputStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrLocalPath()+File.separator+localFlagFileName);
				
				byte[] bytes = new byte[1024*1024];
				int data;
		        while ((data = fileInputStream.read(bytes)) != -1) {
		        	dataOutputStream.write(bytes, 0, data);
		        }
		        while ((data = flagFileInputStream.read(bytes)) != -1) {
		        	flagOutputStream.write(bytes, 0, data);
		        }
		        
		        dataOutputStream.flush();
		        flagOutputStream.flush();
			}
			finally{
				if(dataOutputStream != null){
					dataOutputStream.close();
				}
				if(flagOutputStream != null){
					flagOutputStream.close();
				}
				if(fileInputStream != null){
					fileInputStream.close();
				}
				if(flagFileInputStream != null){
					flagFileInputStream.close();
				}
				if(ftpClient != null){
					ftpClient.logout();
					ftpClient.disconnect();
				}
				if(flagFtpClient != null){
					flagFtpClient.logout();
					flagFtpClient.disconnect();
				}
			}
		}
	}
	
	private void ExceuteSftpUploadTranslate(AutoETL_ActivityNodeForFTPTra autoETL_ActivityNodeForFTPTra,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		
		ChannelSftp  sftpClient = null;
		ChannelSftp  flagsFtpClient = null;
		OutputStream flagOutputStream = null;
		OutputStream dataOutputStream = null;
		FileInputStream fileInputStream = null;
		FileInputStream flagFileInputStream = null;
		try{
			String serverPath = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrServerPath();
			String hostServer = "";
			String dictionary = "";
			if(serverPath.indexOf("/") > -1){
				hostServer = serverPath.substring(0,serverPath.indexOf("/"));
				dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
			}
			else{
				hostServer = serverPath;
			}
			
			sftpClient = connect(hostServer, 22, autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrUserID(), autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrPassword());
			if(!dictionary.equals("")){
				sftpClient.cd(dictionary);
			}
			
			flagsFtpClient = connect(hostServer, 22, autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrUserID(), autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrPassword());
			if(!dictionary.equals("")){
				flagsFtpClient.cd(dictionary);
			}
			
			String strPram = "";
			String strSeg="";
			String localFileName = "";
			String localFlagFileName = "";
			
			if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrParamRule())){
				strSeg = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(0,autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#"));
				
				String[] strParams = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#") + 1).split(",");
				for(int i = 0;i<strParams.length;i++){
					String param = "";
					for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
						if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
							param = autoETL_WorkflowParamMV.getStrValue();
						}
					}
					if(StringUtils.isBlank(param)){
						throw new Exception("参数规则定义错误");
					}
					strPram += param + strSeg;
				}
			}
			if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrFileName())){

				String downloadFileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile(); // 现在只存在“.dat”,可以直接加入文件名的后缀，以后可以去除“.”，直接在界面定义后缀名，然后修改程序中多余的代码
				String tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
				String tmpLocalFileName = autoETL_ActivityNodeForFTPTra.getStrFileName();
				if(tmpLocalFileName.indexOf("#")>0){
					strSeg = tmpLocalFileName.substring(0,tmpLocalFileName.indexOf("#"));
					String[] strParams = tmpLocalFileName.substring(tmpLocalFileName.indexOf("#") + 1).split(",");
					for(int i = 0;i<strParams.length;i++){
						String param = "";
						for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
							if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
								param = autoETL_WorkflowParamMV.getStrValue();
							}
						}
						if(StringUtils.isBlank(param)){
							throw new Exception("文件名参数规则定义错误");
						}
						localFileName += param + strSeg;
						localFlagFileName += param + strSeg;
					}
					localFileName = localFileName.substring(0, localFileName.length()-1)+downloadFileSuffix;
					localFlagFileName = localFlagFileName.substring(0, localFlagFileName.length()-1)+tmpfileSuffix;
				}else{
					localFileName = localFileName+downloadFileSuffix;
					localFlagFileName = localFlagFileName+tmpfileSuffix;
				}
			}
			
			String DataFileName;
			String FlagFileName;
			if(autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("A")){
				String tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0];
				String tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[1];
				if(tmpfileName.trim().isEmpty()){
					FlagFileName = strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
				}else{
					FlagFileName = tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
				}
				tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0];
				tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[1];
				if(tmpfileName.trim().isEmpty()){
					DataFileName = strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
				}else{
					DataFileName = tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
				}				
			}else if (autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("B")){
				if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0].trim().isEmpty()){
					FlagFileName = strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
				}else{
					FlagFileName = strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
				}
				if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0].trim().isEmpty()){
					DataFileName = strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
				}else{
					DataFileName = strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
				}				
			}			
			else{
				FlagFileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile();
				DataFileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
			}
			flagOutputStream = flagsFtpClient.put(FlagFileName);
			dataOutputStream = sftpClient.put(DataFileName);
			
			fileInputStream = new FileInputStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrLocalPath()+File.separator+localFileName);
			flagFileInputStream = new FileInputStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrLocalPath()+File.separator+localFlagFileName);
			
			byte[] bytes = new byte[1024*1024];
			int data;
	        while ((data = fileInputStream.read(bytes)) != -1) {
	        	dataOutputStream.write(bytes, 0, data);
	        }
	        while ((data = flagFileInputStream.read(bytes)) != -1) {
	        	flagOutputStream.write(bytes, 0, data);
	        }
	        
	        dataOutputStream.flush();
	        flagOutputStream.flush();
		}
		finally{
			if(dataOutputStream != null){
				dataOutputStream.close();
			}
			if(flagOutputStream != null){
				flagOutputStream.close();
			}
			if(fileInputStream != null){
				fileInputStream.close();
			}
			if(flagFileInputStream != null){
				flagFileInputStream.close();
			}

			try {
				sftpClient.getSession().disconnect(); //如果没有sesstion的disconnect，程序不会退出
            } catch (JSchException e) {
                e.printStackTrace();
            }
			sftpClient.disconnect();
			sftpClient.exit();

			try {
				flagsFtpClient.getSession().disconnect(); //如果没有sesstion的disconnect，程序不会退出
            } catch (JSchException e) {
                e.printStackTrace();
            }
			flagsFtpClient.disconnect();
			flagsFtpClient.exit();
		}
	}
	
	private void ExceuteSftpTranslate(AutoETL_ActivityNodeForFTPTra autoETL_ActivityNodeForFTPTra,List<AutoETL_WorkflowParamMV> activeNodeETL_WorkflowParamMVList) throws Exception{
		ChannelSftp sftpClient = null;
		InputStream flagInputStream = null;
		InputStream dataInputStream = null;
		FileOutputStream fileOutputStream = null;
		try{
			
			String serverPath = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrServerPath();
			String hostServer = "";
			String dictionary = "";
			if(serverPath.indexOf("/") > -1){
				hostServer = serverPath.substring(0,serverPath.indexOf("/"));
				dictionary = serverPath.substring(serverPath.indexOf("/") + 1);
			}
			else{
				hostServer = serverPath;
			}

			sftpClient = connect(hostServer, 22, autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrUserID(), autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getAutoETL_FTP().getStrPassword());
			if(!dictionary.equals("")){
				sftpClient.cd(dictionary);
			}
			
			String strPram = "";
			String strSeg="";
			String localFileName = "";
			
			if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrParamRule())){
				strSeg = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(0,autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#"));
				
				String[] strParams = autoETL_ActivityNodeForFTPTra.getStrParamRule().substring(autoETL_ActivityNodeForFTPTra.getStrParamRule().indexOf("#") + 1).split(",");
				for(int i = 0;i<strParams.length;i++){
					String param = "";
					for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
						if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
							param = autoETL_WorkflowParamMV.getStrValue();
						}
					}
					if(StringUtils.isBlank(param)){
						throw new Exception("参数规则定义错误");
					}
					strPram += param + strSeg;
				}
			}
			if(!StringUtils.isBlank(autoETL_ActivityNodeForFTPTra.getStrFileName())){

				String downloadFileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile(); // 现在只存在“.dat”,可以直接加入文件名的后缀，以后可以去除“.”，直接在界面定义后缀名，然后修改程序中多余的代码
				String tmpLocalFileName = autoETL_ActivityNodeForFTPTra.getStrFileName();
				if(tmpLocalFileName.indexOf("#")>0){
					strSeg = tmpLocalFileName.substring(0,tmpLocalFileName.indexOf("#"));
					String[] strParams = tmpLocalFileName.substring(tmpLocalFileName.indexOf("#") + 1).split(",");
					for(int i = 0;i<strParams.length;i++){
						String param = "";
						for(AutoETL_WorkflowParamMV autoETL_WorkflowParamMV:activeNodeETL_WorkflowParamMVList){
							if(autoETL_WorkflowParamMV.getAutoETL_Param().getStrParamName().equals(strParams[i])){
								param = autoETL_WorkflowParamMV.getStrValue();
							}
						}
						if(StringUtils.isBlank(param)){
							throw new Exception("文件名参数规则定义错误");
						}
						localFileName += param + strSeg;
					}
					localFileName = localFileName.substring(0, localFileName.length()-1)+downloadFileSuffix;
				}else{
					localFileName = localFileName+downloadFileSuffix;
				}
			}
			
			String DataFileName="";
			if(autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("A")){
				String tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0];
				String tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[1];
				if(tmpfileName.trim().isEmpty()){
					flagInputStream = sftpClient.get(strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix);
				}else{
					flagInputStream = sftpClient.get(tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix);
				}
				tmpfileName = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0];
				tmpfileSuffix = autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[1];
				if(tmpfileName.trim().isEmpty()){
					DataFileName=strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
					dataInputStream = sftpClient.get(DataFileName);
				}else{
					DataFileName=tmpfileName+strSeg+strPram.substring(0, strPram.length()-1)+"."+tmpfileSuffix;
					dataInputStream = sftpClient.get(DataFileName);
				}				
			}else if (autoETL_ActivityNodeForFTPTra.getStrParamPosition().equals("B")){
				if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile().split("\\.")[0].trim().isEmpty()){
					flagInputStream = sftpClient.get(strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile());
				}else{
					flagInputStream = sftpClient.get(strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile());
				}
				if(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile().split("\\.")[0].trim().isEmpty()){
					DataFileName=strPram.substring(0, strPram.length()-1)+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
					dataInputStream = sftpClient.get(DataFileName);
				}else{
					DataFileName=strPram+autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
					dataInputStream = sftpClient.get(DataFileName);
				}				
			}			
			else{
				flagInputStream = sftpClient.get(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrFlagFile());
				DataFileName=autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrDataFile();
				dataInputStream = sftpClient.get(DataFileName);
			}			
			fileOutputStream = new FileOutputStream(autoETL_ActivityNodeForFTPTra.getAutoETL_FTPTrans().getStrLocalPath()+File.separator+localFileName); 
			
			byte[] bytes = new byte[1024*1024];
			int data;
	        while ((data = dataInputStream.read(bytes)) != -1) {
	        	 fileOutputStream.write(bytes, 0, data);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				sftpClient.getSession().disconnect(); //如果没有sesstion的disconnect，程序不会退出
            } catch (JSchException e) {
                e.printStackTrace();
            }
			sftpClient.disconnect();
			sftpClient.exit();

			if(flagInputStream != null){
				flagInputStream.close();
			}
			if(dataInputStream != null){
				dataInputStream.close();
			}
			if(fileOutputStream != null){
				fileOutputStream.close();
			}
		}
	}
	
	public ChannelSftp connect(String host, int port, String username, String password) throws Exception {
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		jsch.getSession(username, host, port);
		Session sshSession = jsch.getSession(username, host, port);
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		return sftp;
	}

	public void setStrSessionFactory(String strSessionFactory) {
		this.strSessionFactory = strSessionFactory;
	}

	public String getStrSessionFactory() {
		return strSessionFactory;
	}
}
