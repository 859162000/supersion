package dbgssystem.service.imps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.cfcc.ecus.eft.CryptAPI;
import com.cfcc.ecus.eft.checkFile;
import com.cfcc.ecus.eft.cudata.CUCheckResult;
import com.opensymphony.xwork2.ActionContext;

import report.dto.ReportInstSubInfo;
import report.dto.TaskFlow;
import report.dto.TaskModelInst;
import report.service.imps.CommonUpdateReportStatusService;
import report.service.imps.DownloadResourceService;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import coresystem.service.imps.AutoOrderService;
import dbgssystem.dto.AutoDTO_DB_BFJNGKXX;
import dbgssystem.dto.AutoDTO_DB_BFJNMXXX;
import dbgssystem.dto.AutoDTO_DB_DBXX_JC;
import dbgssystem.dto.AutoDTO_DB_DCGKXX;
import dbgssystem.dto.AutoDTO_DB_DCMXXX;
import dbgssystem.dto.AutoDTO_DB_SJZBZRXX;
import dbgssystem.dto.AutoDTO_DB_DBGSBSBGJL;
import dbgssystem.dto.AutoDTO_DB_DBGSSCQQJL;
import extend.dto.ReportModel_Table;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;
import framework.show.ShowContext;

/**
 * 担保业务生成报文
 * @author xiajieli
 *
 */
public class DownLoadDBGSRportService implements IObjectResultExecute {
	private String systemCode;

	private String instType;

	private String strDbJRJGCode;

	private String strDbReportType;

	private String strVersion;

	private String feedbackType;

	private String[] selectReport;

	private String crypConfig;

	private String crypPublickey;

	private String cryptKeystore;

	private String v_file;

	private String dic_file;

	private String batchFile;

	private String reportFilePath;
	
	private String SJBGRQ;

	private Map<String, String> reportMap;
	
	private List<InstInfo> instInfoSubList;
	
	private Map<String, String> downloadJudgeStatusMap;
	
	private List<String> mxOneToOneDtoNameList;


	public Object objectResultExecute() throws Exception {
		
		this.strDbReportType=(String) ActionContext.getContext().getSession().get("strReportType");
		this.strDbJRJGCode=(String) ActionContext.getContext().getSession().get("strJRJGCode");
		this.SJBGRQ=(String) ActionContext.getContext().getSession().get("SJBGRQ");
		
		String key="dbgs"+"&"+strDbReportType+"&"+strDbJRJGCode;
		
		try{
			ServletActionContext.getContext().getApplication().put(key, "1");
			
			if(strDbReportType.equals("15")) {
				selectReport = new String[] {"81"};
			}
			else if(strDbReportType.equals("16")) {
				selectReport = new String[] {"82"};
			}
			else if(strDbReportType.equals("17")) {
				selectReport = new String[] {"83"};
			}
			
			this.feedbackType = "0";

			MessageResult messageResult = new MessageResult();

			if (StringUtils.isBlank(systemCode)) {
				messageResult.getMessageSet().add("应用系统代码不能为空");
			}
			if (StringUtils.isBlank(instType)) {
				messageResult.getMessageSet().add("机构类型不能为空");
			}
			if (StringUtils.isBlank(strDbJRJGCode)) {
				messageResult.getMessageSet().add("报数机构代码不能为空");
			}
			if(!StringUtils.isBlank(strDbJRJGCode)){
				instInfoSubList = new ArrayList<InstInfo>();
				IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
				detachedCriteriaInstCode.add(Restrictions.eq("reportInstInfo.strReportInstCode",strDbJRJGCode));
				List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
				if(reportInstSubInfoList.size()>0){
					for (ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList) {
						Object obj=ReflectOperation.getFieldValue(reportInstSubInfo, "instInfo");
						String strInstCode=ReflectOperation.getFieldValue(obj, "strInstCode").toString();
						InstInfo instInfo = new InstInfo();
						instInfo.setStrInstCode(strInstCode);
						instInfoSubList.add(instInfo);
					}
				}else{
					messageResult.getMessageSet().add("请在报送机构管理下添加子机构");
				}
			}
			
			if (StringUtils.isBlank(strDbReportType)) {
				messageResult.getMessageSet().add("报文文件种类不能为空");
			}
			if (StringUtils.isBlank(feedbackType)) {
				messageResult.getMessageSet().add("反馈类型不能为空");
			}
			if(this.SJBGRQ==null){
				messageResult.getMessageSet().add("数据报告日期不能为空");
			}
			 
			 IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			 Object dbyw_reportFilePath = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "dbyw_reportFilePath" , null});
		 	 if(dbyw_reportFilePath != null) {
				SystemParam systemParam = (SystemParam)dbyw_reportFilePath;
				if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
					reportFilePath=systemParam.getStrParamValue();
				}
			}
			 if(StringUtils.isBlank(reportFilePath)){
				 messageResult.getMessageSet().add("请在参数管理下配置担保业务生成报文路径");
			 }
			
			if(this.strDbReportType.equals("16")){
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBGSBSBGJL.class);// DBYWBSBG
				List<AutoDTO_DB_DBGSBSBGJL> dBGSBSBGJLList = (List<AutoDTO_DB_DBGSBSBGJL>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao_JC = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_DBXX_JC"));
				List<AutoDTO_DB_DBXX_JC> autoDTO_DB_DBXX_JCList = (List<AutoDTO_DB_DBXX_JC>) singleObjectFindByCriteriaDao_JC.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				
				List<String> DBYWBHList = new ArrayList<String>();
			
				for (AutoDTO_DB_DBXX_JC autoDTODBDBXXJC : autoDTO_DB_DBXX_JCList) {
					DBYWBHList.add(autoDTODBDBXXJC.getDBYWBH());
					
				}
				
				for (AutoDTO_DB_DBGSBSBGJL dBGSBSBGJL : dBGSBSBGJLList) {
					if(!DBYWBHList.contains(dBGSBSBGJL.getYDBYWBH())){
						messageResult.getMessageSet().add("当上报标识变更记录时，要求变更前的标识在库中存在(担保业务编号在库中不存在)。");
					}
					if(DBYWBHList.contains(dBGSBSBGJL.getBGHDDBYWBH())){
						messageResult.getMessageSet().add("当上报标识变更记录时，要求变更后的标识在库中不存在(担保业务编号在库中存在)。");
					}
				}
			}
			if(this.strDbReportType.equals("17")){
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBGSSCQQJL.class);
				List<AutoDTO_DB_DBGSSCQQJL> DBGSSCQQJLList = (List<AutoDTO_DB_DBGSSCQQJL>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				
				IParamObjectResultExecute singleObjectFindByCriteriaDao_JC = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_DBXX_JC"));
				List<AutoDTO_DB_DBXX_JC> autoDTO_DB_DBXX_JCList = (List<AutoDTO_DB_DBXX_JC>) singleObjectFindByCriteriaDao_JC.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				
				List<String> nameList = new ArrayList<String>();
				for (AutoDTO_DB_DBXX_JC autoDTODBDBXXJC : autoDTO_DB_DBXX_JCList) {
					nameList.add(autoDTODBDBXXJC.getDBYWBH());
				}
				for (AutoDTO_DB_DBGSSCQQJL dBGSSCQQJL : DBGSSCQQJLList) {
					 singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					 detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_DBXX_JC"));
					 detachedCriteria.add(Restrictions.eq("DBYWBH",dBGSSCQQJL.getDBYWBH()));
					 List<AutoDTO_DB_DBXX_JC> AutoDTO_DB_DBXX_JCList = (List<AutoDTO_DB_DBXX_JC>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
					 
					 if(!nameList.contains(dBGSSCQQJL.getDBYWBH())){
						messageResult.getMessageSet().add("当上报删除请求记录时，要求删除的担保业务编号在库中存在。");
					}else{
						if(dBGSSCQQJL.getSCLX().equals("2")){
							 singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							 detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_SJZBZRXX"));
							 if(AutoDTO_DB_DBXX_JCList.size()==1){
								 detachedCriteria.add(Restrictions.eq("FOREIGNID",AutoDTO_DB_DBXX_JCList.get(0)));
							 }
							List<AutoDTO_DB_SJZBZRXX> autoDTO_DB_SJZBZRXXList = (List<AutoDTO_DB_SJZBZRXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
							
							if(autoDTO_DB_SJZBZRXXList.size()==0){
								messageResult.getMessageSet().add("当上报删除请求记录类型为在保责任信息删除时，要求库中存在在保责任信息段。");
							}
						}
						else if(dBGSSCQQJL.getSCLX().equals("3")){
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							 detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_DCGKXX"));
							 if(AutoDTO_DB_DBXX_JCList.size()==1){
								 detachedCriteria.add(Restrictions.eq("FOREIGNID",AutoDTO_DB_DBXX_JCList.get(0)));
							 }
							List<AutoDTO_DB_DCGKXX> autoDTO_DB_DCGKXXList = (List<AutoDTO_DB_DCGKXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
							if(autoDTO_DB_DCGKXXList.size()==0){
								messageResult.getMessageSet().add("当上报删除请求记录类型为代偿信息删除时，要求库中存在代偿概况信息段。");
							}
							
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							 detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_DCMXXX"));
							 if(AutoDTO_DB_DBXX_JCList.size()==1){
								 detachedCriteria.add(Restrictions.eq("FOREIGNID",AutoDTO_DB_DBXX_JCList.get(0)));
							 }
							List<AutoDTO_DB_DCMXXX> autoDTO_DB_DCMXXXList = (List<AutoDTO_DB_DCMXXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
							if(autoDTO_DB_DCMXXXList.size()==0){
								messageResult.getMessageSet().add("当上报删除请求记录类型为代偿信息删除时，要求库中存在代偿明细信息段。");
							}
						}
						else if(dBGSSCQQJL.getSCLX().equals("4")){
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							 detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_BFJNGKXX"));
							 if(AutoDTO_DB_DBXX_JCList.size()==1){
								 detachedCriteria.add(Restrictions.eq("FOREIGNID",AutoDTO_DB_DBXX_JCList.get(0)));
							 }
							List<AutoDTO_DB_BFJNGKXX> autoDTO_DB_BFJNGKXXList = (List<AutoDTO_DB_BFJNGKXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
							if(autoDTO_DB_BFJNGKXXList.size()==0){
								messageResult.getMessageSet().add("当上报删除请求记录类型为保费缴纳信息删除时，要求库中存在保费缴纳概况信息段。");
							}
							
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							 detachedCriteria = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_BFJNMXXX"));
							 if(AutoDTO_DB_DBXX_JCList.size()==1){
								 detachedCriteria.add(Restrictions.eq("FOREIGNID",AutoDTO_DB_DBXX_JCList.get(0)));
							 }
							List<AutoDTO_DB_BFJNMXXX> autoDTO_DB_BFJNMXXXList = (List<AutoDTO_DB_BFJNMXXX>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
							if(autoDTO_DB_BFJNMXXXList.size()==0){
								messageResult.getMessageSet().add("当上报删除请求记录类型为保费缴纳信息删除时，要求库中存在保费缴纳明细信息段。");
							}
						}
					}
				}
			}
			
			if (selectReport == null || selectReport.length == 0) {
				messageResult.getMessageSet().add("没有选择生成报文");
			}

			if (messageResult.getMessageSet().size() > 0) {
				messageResult.setMessage("生成失败");
				messageResult.setSuccess(false);
				messageResult.AlertTranslate();
				return messageResult;
			} else {
				return GenerateReport();
			}

		}
		finally{
			ServletActionContext.getContext().getApplication().put(key, null);
			ActionContext.getContext().getSession().put("strJRJGCode", "");
			ActionContext.getContext().getSession().put("SJBGRQ", "");
			ActionContext.getContext().getSession().put("strReportType", "");
		}
	
	}

	@SuppressWarnings("unchecked")
	private DownloadResult GenerateReport() throws Exception {
		FileHandler fileHandler = new FileHandler();

		String fileName = "";
		fileName += systemCode;
		fileName += instType;
		String tempFileName="";
		if (strDbJRJGCode.length() < 14) {
			for (int i = strDbJRJGCode.length(); i < 14; i++) {
				tempFileName+=0;
			}
		}
		fileName +=tempFileName+strDbJRJGCode;
		fileName += TypeParse.parseString(new Date(), "yyMMdd");
		fileName += strDbReportType;
		fileName += "1";
		AutoOrderService autoOrderService = new AutoOrderService();
		String intOrder = autoOrderService.getAutoOrder(fileName, "0000");
		fileName += intOrder;
		fileName += feedbackType;
		fileName += "0";
		fileName += ".txt";

		DownloadResult downloadResult = fileHandler.GetStreamResult(fileName,null);

		StringBuilder stringBuilder = new StringBuilder();
		List<Object> allReportList = new ArrayList<Object>();
		List<Object> changedObjectListBGBS = new ArrayList<Object>();
		List<Object> changedObjectListSCQQ = new ArrayList<Object>();
		List<Object> changedObjectListJC = new ArrayList<Object>();
		List<Object> changedObjectListMX = new ArrayList<Object>();
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String head = "";
			head += "A";
			head += strVersion;
			String db_jrjgCode = "";
			if (SecurityContext.getInstance().getLoginInfo().isAdministrator()) {
				db_jrjgCode = strDbJRJGCode;
			} else {
				String strInstCode = ((UserInfo) SecurityContext.getInstance().getLoginInfo().getTag()).getInstInfo().getStrInstCode();
				IParamObjectResultExecute singleObjectFindByCriteriaDaoInstCode = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteriaInstCode = DetachedCriteria.forClass(ReportInstSubInfo.class);
				detachedCriteriaInstCode.add(Restrictions.eq("instInfo.strInstCode",strInstCode));
				List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDaoInstCode.paramObjectResultExecute(new Object[]{detachedCriteriaInstCode,null});
				if(reportInstSubInfoList.size()==1){
					for (ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList) {
						db_jrjgCode = reportInstSubInfo.getReportInstInfo().getStrReportInstCode();
					}
				}else{
					db_jrjgCode = strDbJRJGCode;
				}
			}
			
			String tempStr="";
			if (db_jrjgCode.length() < 14) {
				for (int j = db_jrjgCode.length(); j < 14; j++) {
					tempStr+="0";
				}
			}
			
			head += tempStr+db_jrjgCode;
			head += TypeParse.parseString(new Date(), "yyyyMMddHHmmss");
			head += report;
			head += "                              ";
			stringBuilder.append(head);
			stringBuilder.append("\r\n");
			int count = 0;
			if (report.equals("82")) {
				// 82－担保业务信息采集标识变更报文
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBGSBSBGJL.class);// DBYWBSBG
				detachedCriteria.add(Restrictions.in("RPTSendType",	new String[] { "1"}));
				List<AutoDTO_DB_DBGSBSBGJL> objectList = (List<AutoDTO_DB_DBGSBSBGJL>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				count = objectList.size();
				for (AutoDTO_DB_DBGSBSBGJL bSBGJL : objectList) {
					changedObjectListBGBS.add(bSBGJL);
					allReportList.add(bSBGJL);
					String dataLine = "C";
					for (Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("dbgssystem.dto.AutoDTO_DB_DBGSBSBGJL").entrySet()) {
						ReflectOperation.setFieldValue(bSBGJL, "SJBGRQ", this.SJBGRQ);
						String fieldValue=null;
						if(entry.getKey().equals("SJBGRQ")){
							fieldValue=this.SJBGRQ;
						}else{
							fieldValue = ReflectOperation.getFieldValue(bSBGJL, entry.getKey()).toString();
						}
						int entryValue = Integer.parseInt(entry.getValue());
						dataLine += fieldValue;
						int valueLength = fieldValue.getBytes().length;
						while (valueLength < entryValue) {
							dataLine += " ";
							valueLength++;
						}
					}
					stringBuilder.append(dataLine);
					stringBuilder.append("\r\n");
				}
			} else if (report.equals("83")) {
				// 83－担保业务信息采集删除报文
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBGSSCQQJL.class);// DBYWSC
				detachedCriteria.add(Restrictions.in("RPTSendType",	new String[] { "1"}));
				List<AutoDTO_DB_DBGSSCQQJL> objectList = (List<AutoDTO_DB_DBGSSCQQJL>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
				count = objectList.size();
				for (AutoDTO_DB_DBGSSCQQJL sCQQJL : objectList) {
					changedObjectListSCQQ.add(sCQQJL);
					allReportList.add(sCQQJL);
					String dataLine = "S";
					for (Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("dbgssystem.dto.AutoDTO_DB_DBGSSCQQJL").entrySet()) {
						ReflectOperation.setFieldValue(sCQQJL, "SJBGRQ", this.SJBGRQ);
						String fieldValue=null;
						if(entry.getKey().equals("SJBGRQ")){
							fieldValue=this.SJBGRQ;
						}else{
							fieldValue= ReflectOperation.getFieldValue(sCQQJL, entry.getKey()).toString();
						}
						int entryValue = Integer.parseInt(entry.getValue());
						dataLine += fieldValue;
						int valueLength = fieldValue.getBytes().length;
						while (valueLength < entryValue) {dataLine += " ";
							valueLength++;
						}
					}
					stringBuilder.append(dataLine);
					stringBuilder.append("\r\n");
				}
			} else {
				// 81－担保业务信息采集正常报文
				String reportDto = reportMap.get(report);
				if(reportDto!=null){
					String[] reportDtos = reportDto.split(",");
					String baseDto = reportDtos[0].split("%")[1];
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
					for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
						if(entry.getValue().indexOf(",") > -1){
							String[] strValues = entry.getValue().split(",");
							detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
						}
						else{
							detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
						}
					}

					detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));

					List<Object> objectList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
					//count = objectList.size();

					Map<String, String> baseDtoFieldMap = ShowContext.getInstance().getShowEntityMap().get(baseDto);
					
					Map<String, String> n_TYPE_FieldMap = ShowContext.getInstance().getShowEntityMap().get("DBGS_N_TYPE_Field");
					
					for (Object object : objectList) {
						changedObjectListJC.add(object);
						allReportList.add(object);
						
						//报文对应明细段只允许一条数据的情况，记录当前明细数据索引
						int index = 0;
						while(true){
							//报文对应明细段只允许一条数据的情况，中断标识
							boolean indexBreak = true;
							
							//同一基础段中存在不同的数据报告日期集合
							Set<String> setSJBGRQ = new HashSet<String>();
							while(true){
								String dataLine = "";
								dataLine += reportDtos[0].split("%")[0];
								for (Map.Entry<String, String> entry : baseDtoFieldMap.entrySet()) {
									Object value = null;
									if(entry.getKey().equals("SJBGRQ")){
										value = "********";
									}else{
										value = ReflectOperation.getFieldValue(object,entry.getKey());
									}
									
									if (value == null || value.toString().equals("")) {
										String empty = "";
										for (int j = 0; j < Integer.parseInt(entry.getValue()); j++) {
											empty += " ";
										}
										dataLine += empty;
									} else {
										String empty = "";
										for (int j = 0; j < Integer.parseInt(entry.getValue())-  value.toString().getBytes("GBK").length; j++) {
											empty += " ";
										}
										dataLine += value.toString() + empty;
									}
								}
								
								String mxLine = "";
								// 循环生成明细段信息
								//当前数据报告日期
								String currentSJBGRQ="";
								//中断标识
								boolean isBreak = true;
								
								for (int j = 1; j < reportDtos.length; j++) {
									String dtoMX = reportDtos[j].split("%")[1];
									
									DetachedCriteria detachedCriteriaMx = DetachedCriteria.forClass(Class.forName(dtoMX));
									String fieldName = "";
									for (Field field : ReflectOperation.getReflectFields(Class.forName(dtoMX))) {
										if (field.getType().equals(object.getClass())) {
											fieldName = field.getName();
											break;
										}
									}
									
									for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
										if(entry.getKey().equals("RPTCheckType") || entry.getKey().equals("RPTSendType") || entry.getKey().equals("RPTFeedbackType")){
											if(entry.getValue().indexOf(",") > -1){
												String[] strValues = entry.getValue().split(",");
												detachedCriteriaMx.add(Restrictions.in(entry.getKey(),strValues));
											}else{
												detachedCriteriaMx.add(Restrictions.eq(entry.getKey(), entry.getValue()));
											}
										}
									}
									
									detachedCriteriaMx.add(Restrictions.eq(fieldName,object));
									
									//报文对应明细段只允许一条数据的情况，过滤非当前明细索引的数据。
									List<Object> objectMXListTemp = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteriaMx, null });
									List<Object> objectMXList = new ArrayList<Object>();
									
									if(mxOneToOneDtoNameList.contains(dtoMX) && objectMXListTemp.size() > 0){
										objectMXList.add(objectMXListTemp.get(index));
										if(objectMXListTemp.size() > index + 1){
											indexBreak = false;
											index++;
										}
									}
									else{
										indexBreak = true;
										objectMXList = objectMXListTemp;
									}
									
									Map<String, String> dtoMXFieldMap = ShowContext.getInstance().getShowEntityMap().get(dtoMX);
									String[] n_type_fields = null;
									
									for (Map.Entry<String, String> n_type_entry : n_TYPE_FieldMap.entrySet()) {
										if(n_type_entry.getKey().equals(dtoMX)){
											n_type_fields = n_TYPE_FieldMap.get(dtoMX).split(",");
										}
									}
									
									//同一明细DTO中，数据报告日期个数
									int repeatCount = 0;
									for (Object objectMX : objectMXList) {
										Object extend2Obj = ReflectOperation.getFieldValue(objectMX, "extend2");
										String extend2Str = "";
										if(extend2Obj != null && !extend2Obj.toString().equals("")){
											extend2Str = extend2Obj.toString();
										}
										else{
											extend2Str = this.SJBGRQ;
										}
										//第一个未写报文数据报告日期数据入口
										if(!setSJBGRQ.contains(extend2Str)){
											repeatCount++;
											//repeatCount > 1 同一明细段如果出现2次及其以上未写入明细报文的数据报告日期，表示需要再下次循环写入，并当前明细数据肯定不应该写入当前数据报告日期。
											//!currentYWFSRQ.equals("") 其它业务段数据在当前数据报告日期还未写完时，不应该把其它数据报告日期的数据库写入，表示需要再下次循环写入，并当前明细数据肯定不应该写入当前数据报告日期。
											if(repeatCount > 1 || !currentSJBGRQ.equals("")){
												isBreak = false;
												continue;
											}
										}
										//非第一条数据在同一明细段中相同数据报告日期入口
										else{
											//过滤非当前数据报告日期的数据
											if(!extend2Str.equals(currentSJBGRQ)){
												continue;
											}
										}
										
										currentSJBGRQ = extend2Str;
										setSJBGRQ.add(currentSJBGRQ);

										changedObjectListMX.add(objectMX);
										
										if (objectMXList.size() > 0) {
											mxLine += reportDtos[j].split("%")[0];
										}
										
										for (Map.Entry<String, String> entry : dtoMXFieldMap.entrySet()) {
											Object value = ReflectOperation.getFieldValue(objectMX, entry.getKey());
											
											if (value == null || value.toString().equals("")) {
												String empty = "";
												boolean flagisExist=false;
												
												if(!flagisExist){
													for (int k = 0; k < Integer.parseInt(entry.getValue()); k++) {
															empty += " ";
													}
												}
												mxLine += empty;
											} else {
												String empty = "";
												boolean flagisExist=false;
												if(n_type_fields!=null && n_type_fields.length>0){
													for(String n_type_field : n_type_fields){
														if(n_type_field.equals(entry.getKey())){
															for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
																empty += "0";
															}
															flagisExist=true;
															mxLine += empty + value.toString();
															break;
														}
														
													}
													
												}
												if(!flagisExist){
													for (int k = 0; k < Integer.parseInt(entry.getValue()) - value.toString().getBytes("GBK").length; k++) {
														empty += " ";
													}
													mxLine += value.toString() + empty;
												}
											}
										}
									}
								}
								if(mxLine.equals("")){
									dataLine = "";
								}else{
									count++;
									DecimalFormat df = new DecimalFormat("0000");
									dataLine = dataLine + mxLine;
									String dataLinePrivous = df.format(dataLine.getBytes("GBK").length + 6);
									dataLinePrivous += 62;

									dataLine = dataLinePrivous + dataLine;
									dataLine = dataLine.replace("********", currentSJBGRQ);
									stringBuilder.append(dataLine);
									stringBuilder.append(System.getProperty("line.separator"));
								}
								if(isBreak){
									break;
								}
							}
							if(indexBreak){
								break;
							}
						}
					}
				}
			}
			String end = "";
			end += "Z";
			DecimalFormat df = new DecimalFormat("0000000000");
			end += df.format(count);

			stringBuilder.append(end);

			if (i != selectReport.length - 1) {
				stringBuilder.append(System.getProperty("line.separator"));
				stringBuilder.append(System.getProperty("line.separator"));
			}
		}
	
		/**Thread是war包部署在tomcat下面的路径，ServletActionContext是myeclipse自带的tomcat取的路径
		 * myeclipse自带的tomcat无法取得前者路径 */
		String systemPath="";
		systemPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(0).replace("%20", " ");
		systemPath = systemPath.substring(0, systemPath.length()-16)+"/";
		
		String tempFile=systemPath + v_file;
		File tempTxtFile = new File(tempFile);
		if(!tempTxtFile.exists()){
			systemPath = ServletActionContext.getServletContext().getRealPath("/");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String datePath = reportFilePath + format.format(new Date());
		File dateFile = new File(datePath);
		if (!dateFile.exists() && !dateFile.isDirectory()) {
			dateFile.mkdirs();
		}
		String filePath = datePath + "/" + fileName;
		File txtFile = new File(filePath);
		if (!txtFile.exists()) {
			txtFile.createNewFile();
		} else {
			txtFile.delete();
		}
		FileOutputStream outStream = new FileOutputStream(txtFile);
		outStream.write(stringBuilder.toString().getBytes("GBK"));
		outStream.close();

		CUCheckResult currCUCheckResult = new CUCheckResult();
		currCUCheckResult = checkFile.checkall(systemPath + v_file, systemPath + dic_file, filePath);
		byte[] buffer = new byte[1024];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath.replace(".txt", ".zip")));
		if (currCUCheckResult.getErrorVector().size() > 0) {
			File badFile = new File(filePath.replace(".txt", ".bad"));
			if (badFile.exists()) {
				badFile.delete();
			} else {
				badFile.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(badFile), "gbk");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(currCUCheckResult.dump());
			writer.close();
			File[] files = { txtFile, badFile };
			for (int i = 0; i < files.length; i++) {
				FileInputStream fis = new FileInputStream(files[i]);
				out.putNextEntry(new ZipEntry(files[i].getName()));
				int len;
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
			downloadResult.setSuccess(false);//生成bad文件

		} else {
		    boolean isPass = CryptAPI.enCryptCompressFile(filePath.replace("\\", "/"), (filePath.replace(".txt", ".enc")).replace("\\", "/"), systemPath + crypConfig, systemPath+ crypPublickey, 1, "1.0");
			if (isPass) {
				File encFile = new File(filePath.replace(".txt", ".enc"));
				File[] files = { txtFile, encFile };
				for (int i = 0; i < files.length; i++) {
					FileInputStream fis = new FileInputStream(files[i]);
					out.putNextEntry(new ZipEntry(files[i].getName()));
					int len;
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				}
				out.close();
				downloadResult.setSuccess(true);//生成enc文件
			}
		}
		boolean flag =downloadResult.isSuccess();
		downloadResult = fileHandler.GetStreamResult(fileName.replace(".txt",".zip"), null);
		FileInputStream inputStream = new FileInputStream(filePath.replace(".txt", ".zip"));
		downloadResult.setInputStream(inputStream);
		downloadResult.setSuccess(flag);
		if (this.strDbReportType.equals("15") && currCUCheckResult.getErrorVector().size()<=0) {

			for (Object object : changedObjectListJC) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
			}
			for (Object object : changedObjectListMX) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
				ReflectOperation.setFieldValue(object, "extend1", fileName);
				ReflectOperation.setFieldValue(object, "extend2", SJBGRQ);
				
			}

			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskFlow.class);
			List<TaskFlow> taskFlowList = (List<TaskFlow>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

			singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
			detachedCriteria.add(Restrictions.eq("strTableName", "DB_DBXX_JC"));
			List<ReportModel_Table> reportModel_TableList = (List<ReportModel_Table>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
			detachedCriteria = DetachedCriteria.forClass(TaskModelInst.class);

			if(instInfoSubList.size()>0 && taskFlowList.size()>0){
				detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
				detachedCriteria.add(Restrictions.in("taskFlow", taskFlowList));
				detachedCriteria.add(Restrictions.in("reportModel_Table",reportModel_TableList));
				List<TaskModelInst> listObject = (List<TaskModelInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

				for (TaskModelInst taskModelInst : listObject) {
					
					int sendedCout = 0;
					int unSendCout = 0;
					int unAllSendCout = 0;
					String baseDto="dbgssystem.dto.AutoDTO_DB_DBXX_JC";
					
					IParamObjectResultExecute singleObjectFindCountByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
					CommonUpdateReportStatusService CommonStatus=new CommonUpdateReportStatusService();
					
					sendedCout= CommonStatus.findByDetachedCriteria("RPTSendType", "2", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
					
					unSendCout= CommonStatus.findByDetachedCriteria("RPTSendType", "1", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
					
					unAllSendCout= CommonStatus.findByDetachedCriteria("RPTSendType", "5", detachedCriteria, baseDto, taskModelInst, singleObjectFindCountByCriteriaDao);
				
					if(sendedCout==0 && unSendCout>=0 && unAllSendCout==0){
						taskModelInst.setRPTSendType("1");
					}else if(sendedCout>0 && unSendCout==0 && unAllSendCout==0){
						taskModelInst.setRPTSendType("2");
					}
					else {
						taskModelInst.setRPTSendType("5");
					}
				}
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {listObject, null });
			}
		}else if(this.strDbReportType.equals("16") || this.strDbReportType.equals("17") && currCUCheckResult.getErrorVector().size()<=0) {
			for (Object object : changedObjectListBGBS) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
			}
			for (Object object : changedObjectListSCQQ) {
				ReflectOperation.setFieldValue(object, "RPTSendType", "2");
			}
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] {allReportList, null });
		}

		String fileDescription="";
		if(strDbReportType.equals("15")){
			fileDescription = ShowContext.getInstance().getShowEntityMap().get("DB_ReportType").get("15");
		}else if(strDbReportType.equals("16")){
			fileDescription = ShowContext.getInstance().getShowEntityMap().get("DB_ReportType").get("16");
		}else if(strDbReportType.equals("17")){
			fileDescription = ShowContext.getInstance().getShowEntityMap().get("DB_ReportType").get("17");
		}
		
		new DownloadResourceService().DownloadResource(filePath.replace(".txt", ".zip"), "23", "1",fileDescription); // 下载资源库新增一条数据
		if(currCUCheckResult.getErrorVector().size()<=0){
			new RejectReportForDBGSService().SendedRejectReport("23", fileDescription, fileName, "1");  //上报成功，已报送报文驳回表新增数据
		}
		
		return downloadResult;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public String getStrDbJRJGCode() {
		return strDbJRJGCode;
	}

	public void setStrDbJRJGCode(String strDbJRJGCode) {
		this.strDbJRJGCode = strDbJRJGCode;
	}

	public String getStrDbReportType() {
		return strDbReportType;
	}

	public void setStrDbReportType(String strDbReportType) {
		this.strDbReportType = strDbReportType;
	}


	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String[] getSelectReport() {
		return selectReport;
	}

	public void setSelectReport(String[] selectReport) {
		this.selectReport = selectReport;
	}

	public Map<String, String> getReportMap() {
		return reportMap;
	}

	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}

	public void setStrVersion(String strVersion) {
		this.strVersion = strVersion;
	}

	public String getStrVersion() {
		return strVersion;
	}

	public String getCrypConfig() {
		return crypConfig;
	}

	public void setCrypConfig(String crypConfig) {
		this.crypConfig = crypConfig;
	}

	public String getCrypPublickey() {
		return crypPublickey;
	}

	public void setCrypPublickey(String crypPublickey) {
		this.crypPublickey = crypPublickey;
	}

	public String getCryptKeystore() {
		return cryptKeystore;
	}

	public void setCryptKeystore(String cryptKeystore) {
		this.cryptKeystore = cryptKeystore;
	}

	public String getV_file() {
		return v_file;
	}

	public void setV_file(String vFile) {
		v_file = vFile;
	}

	public String getDic_file() {
		return dic_file;
	}

	public void setDic_file(String dicFile) {
		dic_file = dicFile;
	}

	public String getBatchFile() {
		return batchFile;
	}

	public void setBatchFile(String batchFile) {
		this.batchFile = batchFile;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}

	public void setSJBGRQ(String sJBGRQ) {
		SJBGRQ =sJBGRQ;
	}

	public String getSJBGRQ() {
		return SJBGRQ;
	}

	public List<InstInfo> getInstInfoSubList() {
		return instInfoSubList;
	}

	public void setInstInfoSubList(List<InstInfo> instInfoSubList) {
		this.instInfoSubList = instInfoSubList;
	}

	public List<String> getMxOneToOneDtoNameList() {
		return mxOneToOneDtoNameList;
	}

	public void setMxOneToOneDtoNameList(List<String> mxOneToOneDtoNameList) {
		this.mxOneToOneDtoNameList = mxOneToOneDtoNameList;
	}
}
