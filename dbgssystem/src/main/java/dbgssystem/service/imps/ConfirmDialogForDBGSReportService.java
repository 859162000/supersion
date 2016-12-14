package dbgssystem.service.imps;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import dbgssystem.dto.AutoDTO_DB_DBGSBSBGJL;
import dbgssystem.dto.AutoDTO_DB_DBGSSCQQJL;

import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

/**
 * 点击生成报文时，对数据进行校验，如果不满足条件，则弹出是否框进行提示
 * @author xiajieli 20160608
 *
 */
public class ConfirmDialogForDBGSReportService implements IObjectResultExecute {

	private Map<String, String> downloadJudgeStatusMap;
	
	private Map<String, String> reportMap;
	
	private String[] selectReport;
	
	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		MessageResult messageResult=new MessageResult();
		String alertMessages="";
		String strNoData="\r\n没有数据，是否继续生成报文？";
		String strExistUntreatedData="\r\n存在未处理数据，是否继续生成报文？";
		
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		String key="";
		try{
			//担保公司
			if(RequestManager.getActionName().contains("GetJsonForDBGSDownLoadReport-dbgssystem.dto.DBGSDownload")){
				
				String strReportType = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strReportType");
				String strJRJGCode = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strJRJGCode");
				key="dbgs"+"&"+strReportType+"&"+strJRJGCode;
				
				if(ServletActionContext.getContext().getApplication().get(key) != null){
					if(((String)ServletActionContext.getContext().getApplication().get(key)).equals("1")){
						messageResult.getMessageSet().add("alert报文正在生成，请勿重复操作");
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
				}
				
				ServletActionContext.getContext().getApplication().put(key, "1");
				
				Object objSJBGRQ = ReflectOperation.getFieldValue(RequestManager.getTOject(), "SJBGRQ");
				String SJBGRQ="";
				if(objSJBGRQ !=null && !objSJBGRQ.toString().equals("")){
					SJBGRQ=TypeParse.parseString(TypeParse.parseDate(objSJBGRQ.toString()), "yyyyMMdd");  
				}else{
					SJBGRQ=HelpTool.getBeforeDate("yyyyMMdd");
				}
				
				ActionContext.getContext().getSession().put("strJRJGCode", strJRJGCode);
				ActionContext.getContext().getSession().put("SJBGRQ", SJBGRQ);
				ActionContext.getContext().getSession().put("strReportType", strReportType);
				
				if (!StringUtils.isBlank(strJRJGCode) && !StringUtils.isBlank(strReportType)) {

					CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
					List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(strJRJGCode);

					if(strReportType.equals("15")) {
						selectReport = new String[] {"81"};
					}
					else if(strReportType.equals("16")) {
						selectReport = new String[] {"82"};
					}
					else if(strReportType.equals("17")) {
						selectReport = new String[] {"83"};
					}
					 
					 /**
					  * 校验1：1、没有数据，是否继续生成报文？ 存在未处理数据，是否继续生成报文？
					  */
					if ("15".equals(strReportType)) {
						for (int i = 0; i < selectReport.length; i++) {
							String report = selectReport[i];
							
							String reportDto = reportMap.get(report);
							String[] reportDtos = reportDto.split(",");
							String baseDto = reportDtos[0].split("%")[1];
							
							DetachedCriteria detachedCriteriaTemp = DetachedCriteria.forClass(Class.forName(baseDto));
							detachedCriteriaTemp.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
							detachedCriteriaTemp.add(Restrictions.ne("RPTSendType", "2"));
							detachedCriteriaTemp.add(Restrictions.in("instInfo", instInfoSubList));
							int objectTempList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteriaTemp,null});
							
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
							detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
							for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
								if(entry.getValue().indexOf(",") > -1){
									String[] strValues = entry.getValue().split(",");
									detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
								}
								else{
									detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
								}
							}
							int objectList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(objectList==0){
								ServletActionContext.getContext().getApplication().put(key, null);
								alertMessages += strNoData;
							}
							if(objectTempList> objectList){
								ServletActionContext.getContext().getApplication().put(key, null);
								alertMessages += strExistUntreatedData;
							}
						}
					} else if ("16".equals(strReportType)) {
						DetachedCriteria detachedCriteriaTemp = DetachedCriteria.forClass(AutoDTO_DB_DBGSBSBGJL.class);
						detachedCriteriaTemp.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
						detachedCriteriaTemp.add(Restrictions.ne("RPTSendType", "2"));
						int objectTempList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteriaTemp,null});
						
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBGSBSBGJL.class);
						for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
							if(entry.getValue().indexOf(",") > -1){
								String[] strValues = entry.getValue().split(",");
								detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
							}
							else{
								detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
							}
						}
						int objectList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						if(objectList == 0){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strNoData;
						}
						if(objectTempList> objectList){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strExistUntreatedData;
						}
						
					} else if ("17".equals(strReportType)) {
						DetachedCriteria detachedCriteriaTemp = DetachedCriteria.forClass(AutoDTO_DB_DBGSSCQQJL.class);
						detachedCriteriaTemp.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
						detachedCriteriaTemp.add(Restrictions.ne("RPTSendType", "2"));
						
						int objectTempList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteriaTemp,null});
						
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBGSSCQQJL.class);
						for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
							if(entry.getValue().indexOf(",") > -1){
								String[] strValues = entry.getValue().split(",");
								detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
							}
							else{
								detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
							}
						}
						int objectList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						if(objectList == 0){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strNoData;
						}
						if(objectTempList> objectList){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strExistUntreatedData;
						}
					}
					
				}
			}
		}finally{
			ServletActionContext.getContext().getApplication().put(key, null);
		}
		if(!alertMessages.equals("")){
			return new MessageResult(false,alertMessages);
		}
		else{
			return new MessageResult(true);
		}
		
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}

	public Map<String, String> getReportMap() {
		return reportMap;
	}

	public void setReportMap(Map<String, String> reportMap) {
		this.reportMap = reportMap;
	}

	public String[] getSelectReport() {
		return selectReport;
	}

	public void setSelectReport(String[] selectReport) {
		this.selectReport = selectReport;
	}
}
