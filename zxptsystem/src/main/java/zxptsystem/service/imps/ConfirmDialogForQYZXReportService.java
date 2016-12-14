package zxptsystem.service.imps;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLGX;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLSC;
import zxptsystem.dto.AutoDTO_GRZXSJSC;
import zxptsystem.dto.AutoDTO_GRZXZHBSBG;
import zxptsystem.dto.AutoDTO_JGXXJBXXSC;
import zxptsystem.dto.AutoDTO_JGXXJZCYXXSC;
import zxptsystem.dto.AutoDTO_QYZXPLXDYWSJSC;
import zxptsystem.service.imps.Check.DownLoadReportForGRXXCheckService;
import zxptsystem.service.imps.Check.DownLoadReportForJGXXCheckService;
import zxptsystem.service.imps.Check.DownLoadReportForQYZXCheckThenStopService;
import zxptsystem.service.imps.Check.HelpChecker;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;
/**
 * 点击生成报文时，对数据进行校验，如果不满足条件，则弹出是否框进行提示
 * @author xiajieli
 *
 */
public class ConfirmDialogForQYZXReportService implements IObjectResultExecute {

	private Map<String, String> xxjllxMap;
	
	private Map<String, String> downloadJudgeStatusMap;
	
	private Map<String, String> reportMap;
	
	private Map<String, String> reportMapForGR;
	
	private Map<String, String> reportMapForJG;
	
	private String[] selectReport;
	
	public Object objectResultExecute() throws Exception {
		
		//清空session
		if(ServletActionContext.getContext().getApplication().get("strQYZXCheck")!=null
				&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strQYZXCheck").toString())){//企业
			ServletActionContext.getContext().getApplication().put("strQYZXCheck", null);
		}
		
		if(ServletActionContext.getContext().getApplication().get("strGRXXCheck")!=null
				&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strGRXXCheck").toString())){//个人
			ServletActionContext.getContext().getApplication().put("strGRXXCheck", null);
		}
		
		if(ServletActionContext.getContext().getApplication().get("strJGXXCheck")!=null
				&& !StringUtils.isBlank(ServletActionContext.getContext().getApplication().get("strJGXXCheck").toString())){//机构
			ServletActionContext.getContext().getApplication().put("strJGXXCheck", null);
		}
		
		MessageResult messageResult=new MessageResult();
		String alertMessages="";
		String strNoData="\r\n没有数据，是否继续生成报文？";
		String strExistUntreatedData="\r\n存在未处理数据，是否继续生成报文？";
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		String key="";
		try{
			//企业征信
			if(RequestManager.getActionName().contains("GetJsonForDownLoadReport-zxptsystem.dto.QYZXDownload")){
				
				String strReportType = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strReportType");
				String strJRJGCode = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strJRJGCode");
				key="qyzx"+"&"+strReportType+"&"+strJRJGCode;
				
				if(ServletActionContext.getContext().getApplication().get(key) != null){
					if(((String)ServletActionContext.getContext().getApplication().get(key)).equals("1")){
						messageResult.getMessageSet().add("alert报文正在生成，请勿重复操作");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
				}
				
				ServletActionContext.getContext().getApplication().put(key, "1");
				
				Object objYwfssj = ReflectOperation.getFieldValue(RequestManager.getTOject(), "YWFSRQ");
				String YWFSRQ="";
				if(objYwfssj !=null && !objYwfssj.toString().equals("")){
					YWFSRQ=TypeParse.parseString(TypeParse.parseDate(objYwfssj.toString()), "yyyyMMdd");
					Date dNow = new Date();
					if(TypeParse.parseDate(YWFSRQ).after(dNow)){
						messageResult.getMessageSet().add("alert业务发生日期不能大于报文生成时间");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
				}else{
					YWFSRQ=HelpTool.getBeforeDate("yyyyMMdd");
				}
				
				ActionContext.getContext().getSession().put("strJRJGCode", strJRJGCode);
				ActionContext.getContext().getSession().put("YWFSRQ", YWFSRQ);
				ActionContext.getContext().getSession().put("strReportType", strReportType);
				
				if (!StringUtils.isBlank(strJRJGCode) && !StringUtils.isBlank(strReportType)) {

					CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
					List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(strJRJGCode);
					
					if(instInfoSubList==null || instInfoSubList.size()==0){
						messageResult.getMessageSet().add("alert请在报送机构管理下选择子机构");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}

					if(strReportType.equals("11")) {
						selectReport = new String[] {"01","02","03","04"};
					}
					else if(strReportType.equals("12")) {
						selectReport = new String[] {"11","12","13","14","15","16","17","18","19","20","21"};
					}
			        else if(strReportType.equals("14")) {
			        	selectReport = new String[] {"61"};
					}
			        else if(strReportType.equals("31")) {
			        	selectReport = new String[] {"51"};
					}
					
					
					/**  * 校验1：删除 报文未能匹配到业务数据，停止生成报文！ 删除报文匹配到业务数据为历史信息，停止生成报文！*/
					 DownLoadReportForXXJLCZLXWithDeleteCheckService xXJLCZLXWithDeleteCheck=new DownLoadReportForXXJLCZLXWithDeleteCheckService();
					 String strXXJLCZLXWithDelete=xXJLCZLXWithDeleteCheck.getXXJLCZLXWithDeleteMessage(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 if(!strXXJLCZLXWithDelete.equals("")){
						 	messageResult.getMessageSet().add("check"+strXXJLCZLXWithDelete);//注：check不能删
							messageResult.setSuccess(false);
							messageResult.AlertTranslate();
							return messageResult;
					  }
					
					 /**  * 其它校验*/
					 String orderbySqlFormula=" case when (this_.extend2='' or this_.extend2 is null) then '"+YWFSRQ+"' else this_.extend2 end desc";
					 String orderbySqlFormulaASC=" case when (this_.extend2='' or this_.extend2 is null) then '"+YWFSRQ+"' else this_.extend2 end asc";
					 
					 String strQYZXCheck="";
					 DownLoadReportForQYZXCheckThenStopService  checkThenStop=new DownLoadReportForQYZXCheckThenStopService();
					 strQYZXCheck += checkThenStop.getQYZXCheck_3006(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_3009And3012(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4010(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4044(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4043(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormulaASC,YWFSRQ);
					 //strQYZXCheck += checkThenStop.getQYZXCheck_4046(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4063(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4065(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4172(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4174(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4175And4176(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4178(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4180(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4184(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4186(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4188(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4202(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4204(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4208(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4210(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4212(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4234(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4236(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4238(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4240(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4242(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4244(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4248(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4252(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4264(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4266(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormulaASC,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4270(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4272(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 strQYZXCheck += checkThenStop.getQYZXCheck_4274(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					//strQYZXCheck += checkThenStop.getQYZXCheck_4004(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap);
					 //strQYZXCheck += checkThenStop.getQYZXCheck_4298(selectReport, xxjllxMap, reportMap, instInfoSubList, downloadJudgeStatusMap,YWFSRQ);
					 

					 if(!StringUtils.isBlank(strQYZXCheck)){
						   String mes="";
						   ServletActionContext.getContext().getApplication().put("strQYZXCheck", strQYZXCheck);
						   if(strQYZXCheck.length()>501){
							   mes=strQYZXCheck.substring(0, 501) + "...";
						   }else{
							   mes=strQYZXCheck;
						   }
						   
						   mes+="\r\n";
						   return new MessageResult(false,mes+"校验未通过，停止生成报文！点击【确定】可查看详细校验错误文本！");
					  }
					
					
					/*** 校验3：信贷业务与担保业务匹配校验*/
					 
					 DownLoadReportForQYZXCheckService downLoadReportCheck=new DownLoadReportForQYZXCheckService();
					 String messageResultForXDYWAndDBYW =downLoadReportCheck.getXDYWAndDBYWMessage(selectReport,xxjllxMap,reportMap,instInfoSubList,downloadJudgeStatusMap,YWFSRQ);
					 if(!messageResultForXDYWAndDBYW.equals("")){
						 alertMessages += messageResultForXDYWAndDBYW;
					 }
					 
					 /*** 校验4：3、没有数据，是否继续生成报文？ 存在未处理数据，是否继续生成报文？*/
					int dtoCount=0;
					int dtoTotalCount = 0;
					 boolean isFlag=false;
					 for (int i = 0; i < selectReport.length; i++) {
							String report = selectReport[i];
							if(!report.equals("51")){
								String[] xxjllxList = xxjllxMap.get(report).split(",");
								dtoTotalCount+=xxjllxList.length;
							}
						}
					 
					 for (int i = 0; i < selectReport.length; i++) {
						 String report = selectReport[i];
						 if (report.equals("51")) {
							 int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_QYZXPLXDYWSJSC.class.getName(),instInfoSubList);
							 
							 int qYZXPLXDYWSJSCList=HelpChecker.GetCurrentSendDataCount(AutoDTO_QYZXPLXDYWSJSC.class.getName(),instInfoSubList,downloadJudgeStatusMap);
								
							 if(qYZXPLXDYWSJSCList<1){
									ServletActionContext.getContext().getApplication().put(key, null);
									alertMessages += strNoData;
									
								}
								if(objectTempList > qYZXPLXDYWSJSCList){
									ServletActionContext.getContext().getApplication().put(key, null);
									alertMessages += strExistUntreatedData;
								}
								
						 }else {
							 String[] xxjllxList = xxjllxMap.get(report).split(",");
							 for (String xxjllx : xxjllxList) {
								 String reportDto = reportMap.get(xxjllx);
								 String[] reportDtos = reportDto.split(",");
								 String baseDto = reportDtos[0].split("%")[1];
								
								 int objectList=HelpChecker.GetCurrentSendDataCount(baseDto,instInfoSubList,downloadJudgeStatusMap);
								 
							    if(objectList<1){
									dtoCount++;
									if(dtoTotalCount==dtoCount){
										ServletActionContext.getContext().getApplication().put(key, null);
										alertMessages += strNoData;
									}
								}
							 }
							 
						 }
					 }
					 
					 for (int i = 0; i < selectReport.length; i++) {
						 String report = selectReport[i];
						 if (!report.equals("51")) {
							 String[] xxjllxList = xxjllxMap.get(report).split(",");
							 for (String xxjllx : xxjllxList) {
								 
								 String reportDto = reportMap.get(xxjllx);
								 String[] reportDtos = reportDto.split(",");
								 String baseDto = reportDtos[0].split("%")[1];
								 
								 int objectTempList=HelpChecker.GetNoSendDataCount(baseDto,instInfoSubList);
								 
								 int objectList=HelpChecker.GetCurrentSendDataCount(baseDto,instInfoSubList,downloadJudgeStatusMap);
								    
							    if(objectTempList> objectList && !isFlag){
							    	isFlag=true;
							    	ServletActionContext.getContext().getApplication().put(key, null);
							    	alertMessages += strExistUntreatedData;
							    }
							 }
						 }
					 }
					 
					 /*** 校验4：重大关注信息校验 */
					 String messageResultsForZDGXXX =downLoadReportCheck.GetZDGXXXMessage(selectReport,xxjllxMap,reportMap,instInfoSubList,downloadJudgeStatusMap);
					 if(!messageResultsForZDGXXX.equals("")){
						 alertMessages += messageResultsForZDGXXX;
					 }
					
				}
				
			}//个人征信
			else if(RequestManager.getActionName().contains("GetJsonForDownLoadReport-zxptsystem.dto.GRZXDownload")){
				
				String strGrReportType = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strGrReportType");
				String strGrJRJGCode = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strGrJRJGCode");
				key="grzx"+"&"+strGrReportType+"&"+strGrJRJGCode;
				
				if(ServletActionContext.getContext().getApplication().get(key) != null){
					if(((String)ServletActionContext.getContext().getApplication().get(key)).equals("1")){
						messageResult.getMessageSet().add("alert报文正在生成，请勿重复操作");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
				}
				
				ServletActionContext.getContext().getApplication().put(key, "1");
				
				Object objSJFSNY = ReflectOperation.getFieldValue(RequestManager.getTOject(), "strGRSJFSNY");
				String strSJFSNY="";
				if(objSJFSNY !=null && !objSJFSNY.toString().equals("")){
					strSJFSNY=(String) objSJFSNY;
				}else{
					strSJFSNY=HelpTool.getBeforeDate("yyyyMM");
				}
				
				ActionContext.getContext().getSession().put("strGrJRJGCode", strGrJRJGCode);
				ActionContext.getContext().getSession().put("strGrReportType", strGrReportType);
				ActionContext.getContext().getSession().put("objSJFSNY", objSJFSNY);
				
				if (!StringUtils.isBlank(strGrJRJGCode) && !StringUtils.isBlank(strGrReportType)) {
					if(strGrReportType.equals("1")){
						selectReport = new String[] {"1"};
					}else if(strGrReportType.equals("4")){
						selectReport = new String[] {"4"};
					}else if(strGrReportType.equals("8")){
						selectReport = new String[] {"8"};
					}else if(strGrReportType.equals("9")){
						selectReport = new String[] {"9"};
					}else if(strGrReportType.equals("A")){
						selectReport = new String[] {"A"};
					}
					
					CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
					List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(strGrJRJGCode);
					
					if(instInfoSubList==null || instInfoSubList.size()==0){
						messageResult.getMessageSet().add("alert请在报送机构管理下选择子机构");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
					
					if ("1".equals(strGrReportType)) {
						
						/*** 个人征信报文级校验 */
						 DownLoadReportForGRXXCheckService checkService=new DownLoadReportForGRXXCheckService();
						 String strGRXXCheck =checkService.getGRXXCheck_003(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_004(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_005(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_008(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_015(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_065(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_027(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 strGRXXCheck += checkService.getGRXXCheck_054(selectReport, reportMapForGR, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
						 
						 if(!StringUtils.isBlank(strGRXXCheck)){
							   String mes="";
							   ServletActionContext.getContext().getApplication().put("strGRXXCheck", strGRXXCheck);
							   
							   if(strGRXXCheck.length()>501){
								   mes=strGRXXCheck.substring(0, 501) + "...";
							   }else{
								   mes=strGRXXCheck;
							   }
							   mes+="\r\n";
							   return new MessageResult(false,mes+"校验未通过，停止生成报文！点击【确定】可查看详细校验错误文本！");
						  }
						
						for (int i = 0; i < selectReport.length; i++) {
							String report = selectReport[i];
							
							String reportDto = reportMapForGR.get(report);
							String[] reportDtos = reportDto.split(",");
							String baseDto = reportDtos[0].split("%")[1];
							
							DetachedCriteria detachedCriteriaTemp = DetachedCriteria.forClass(Class.forName(baseDto));
							detachedCriteriaTemp.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
							detachedCriteriaTemp.add(Restrictions.ne("RPTSendType", "2"));
							detachedCriteriaTemp.add(Restrictions.like("JSYHKRQ", strSJFSNY+"%"));
							detachedCriteriaTemp.add(Restrictions.in("instInfo", instInfoSubList));
							int objectTempList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteriaTemp,null});
							
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
							detachedCriteria.add(Restrictions.like("JSYHKRQ", strSJFSNY+"%"));
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
						
					} else if ("4".equals(strGrReportType)) {
						
						 int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_GRZXZHBSBG.class.getName(),instInfoSubList);
						 
						 int objectList=HelpChecker.GetCurrentSendDataCount(AutoDTO_GRZXZHBSBG.class.getName(),instInfoSubList,downloadJudgeStatusMap);
						
						if(objectList == 0){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strNoData;
						}
						if(objectTempList> objectList){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strExistUntreatedData;
						}
						
					} else if ("8".equals(strGrReportType)) {
						
						int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_GRZXSJSC.class.getName(),instInfoSubList);
						 
						int objectList=HelpChecker.GetCurrentSendDataCount(AutoDTO_GRZXSJSC.class.getName(),instInfoSubList,downloadJudgeStatusMap);
						
						if(objectList == 0){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strNoData;
						}
						if(objectTempList> objectList){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strExistUntreatedData;
						}
					}
					else if ("9".equals(strGrReportType)) {//个人征信历史逾期记录更新
						
						int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_GRZXLSYQJLGX.class.getName(),instInfoSubList);
						 
						int objectList=HelpChecker.GetCurrentSendDataCount(AutoDTO_GRZXLSYQJLGX.class.getName(),instInfoSubList,downloadJudgeStatusMap);
						
						if(objectList == 0){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strNoData;
						}
						if(objectTempList> objectList){
							ServletActionContext.getContext().getApplication().put(key, null);
							alertMessages += strExistUntreatedData;
						}
					}
					else if ("A".equals(strGrReportType)) {//个人征信历史逾期记录删除
						
						int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_GRZXLSYQJLSC.class.getName(),instInfoSubList);
						 
						int objectList=HelpChecker.GetCurrentSendDataCount(AutoDTO_GRZXLSYQJLSC.class.getName(),instInfoSubList,downloadJudgeStatusMap);
						
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
				
			}//机构信息
			else if(RequestManager.getActionName().contains("GetJsonForDownLoadReport-zxptsystem.dto.JGXXDownload")){
				
				String strJgReportType = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strJgReportType");
				String strJgJRJGCode = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strJgJRJGCode");
				Object objXXGXRQ = ReflectOperation.getFieldValue(RequestManager.getTOject(), "XXGXRQ");
				
				key="jgxx"+"&"+strJgReportType+"&"+strJgJRJGCode;
				
				if(ServletActionContext.getContext().getApplication().get(key) != null){
					if(((String)ServletActionContext.getContext().getApplication().get(key)).equals("1")){
						messageResult.getMessageSet().add("alert报文正在生成，请勿重复操作");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
				}
				
				ServletActionContext.getContext().getApplication().put(key, "1");
				
				ActionContext.getContext().getSession().put("objXXGXRQ", objXXGXRQ);
				ActionContext.getContext().getSession().put("strJgJRJGCode", strJgJRJGCode);
				ActionContext.getContext().getSession().put("strJgReportType", strJgReportType);
				
				if (!StringUtils.isBlank(strJgJRJGCode) && !StringUtils.isBlank(strJgReportType)) {
					
					if(strJgReportType.equals("51")) {
						selectReport = new String[] {"5100","5101"};
					}
					else if(strJgReportType.equals("32")) {
						selectReport = new String[] {"3200","3201"};
					}
					
					CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
					List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(strJgJRJGCode);
					
					int dtoCount=0;
					int countTemp=0;
					boolean isFlag=false;
					
					if(instInfoSubList==null || instInfoSubList.size()==0){
						messageResult.getMessageSet().add("alert请在报送机构管理下选择子机构");//注：alert不能删
						messageResult.setSuccess(false);
						messageResult.AlertTranslate();
						return messageResult;
					}
					
					/** * 机构信息校验 */
					String orderbySqlFormula=" case when (this_.extend2='' or this_.extend2 is null) then '"+objXXGXRQ+"' else this_.extend2 end desc";
					DownLoadReportForJGXXCheckService checkService=new DownLoadReportForJGXXCheckService();
					String strJGXXCheck=checkService.getJGXXCheck_1028(selectReport , reportMapForJG, instInfoSubList, downloadJudgeStatusMap);
					strJGXXCheck += checkService.getJGXXCheck_1031(selectReport , reportMapForJG, instInfoSubList, downloadJudgeStatusMap,orderbySqlFormula,objXXGXRQ);
					 
					 if(!StringUtils.isBlank(strJGXXCheck)){
						   String mes="";
						   ServletActionContext.getContext().getApplication().put("strJGXXCheck", strJGXXCheck);
						   if(strJGXXCheck.length()>501){
							   mes=strJGXXCheck.substring(0, 501) + "...";
						   }else{
							   mes=strJGXXCheck;
						   }
						   
						   mes+="\r\n";
						   return new MessageResult(false,mes+"校验未通过，停止生成报文！点击【确定】可查看详细校验错误文本！");
					  }
					
					for (int i = 0; i < selectReport.length; i++) {
						String report = selectReport[i];
						if (strJgReportType.equals("32")) {//删除
							if (report.equals("3200")) {
								
								 int JGXXJBXXSCList=HelpChecker.GetCurrentSendDataCount(AutoDTO_JGXXJBXXSC.class.getName(), instInfoSubList, downloadJudgeStatusMap);
								 
								 if(JGXXJBXXSCList==0){
									countTemp++;
									if(countTemp==2){
										ServletActionContext.getContext().getApplication().put(key, null);
										alertMessages += strNoData;
									}
								}
							}
							if (report.equals("3201")) {
								
								int JGXXJZCYXXSCList=HelpChecker.GetCurrentSendDataCount(AutoDTO_JGXXJZCYXXSC.class.getName(), instInfoSubList, downloadJudgeStatusMap);
								
								if(JGXXJZCYXXSCList==0){
									countTemp++;
									if(countTemp==2){
										ServletActionContext.getContext().getApplication().put(key, null);
										alertMessages += strNoData;
									}
			 					}
							}
						}else {//采集
							String reportDto = reportMapForJG.get(report);
							String[] reportDtos = reportDto.split(",");
							String baseDto = reportDtos[0].split("%")[1];
						
							int JGXXObjectList=HelpChecker.GetCurrentSendDataCount(baseDto, instInfoSubList, downloadJudgeStatusMap);
							
							if(JGXXObjectList<1){
								dtoCount++;
								if(dtoCount==2){
									ServletActionContext.getContext().getApplication().put(key, null);
									alertMessages += strNoData;
								}
							}
						}
					}
					
					for (int i = 0; i < selectReport.length; i++) {
						String report = selectReport[i];
						if (strJgReportType.equals("32")) {//删除
							if (report.equals("3200")) {
								
								int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_JGXXJBXXSC.class.getName(), instInfoSubList);
								int JGXXJBXXSCList=HelpChecker.GetCurrentSendDataCount(AutoDTO_JGXXJBXXSC.class.getName(), instInfoSubList, downloadJudgeStatusMap);
							
								if(objectTempList> JGXXJBXXSCList && !isFlag){
									isFlag=true;
									ServletActionContext.getContext().getApplication().put(key, null);
									alertMessages += strExistUntreatedData;
								}
							}
							if (report.equals("3201")) {
								
								int objectTempList=HelpChecker.GetNoSendDataCount(AutoDTO_JGXXJZCYXXSC.class.getName(), instInfoSubList);
								int JGXXJZCYXXSCList=HelpChecker.GetCurrentSendDataCount(AutoDTO_JGXXJZCYXXSC.class.getName(), instInfoSubList, downloadJudgeStatusMap);
							
								if(objectTempList> JGXXJZCYXXSCList && !isFlag){
									isFlag=true;
									ServletActionContext.getContext().getApplication().put(key, null);
									alertMessages += strExistUntreatedData;
								}
							}
						}else{//采集
							String reportDto = reportMapForJG.get(report);
							String[] reportDtos = reportDto.split(",");
							String baseDto = reportDtos[0].split("%")[1];

							int objectTempList=HelpChecker.GetNoSendDataCount(baseDto, instInfoSubList);
							int JGXXObjectList=HelpChecker.GetCurrentSendDataCount(baseDto, instInfoSubList, downloadJudgeStatusMap);
							
							if(objectTempList > JGXXObjectList && !isFlag){
								isFlag=true;
								ServletActionContext.getContext().getApplication().put(key, null);
								alertMessages += strExistUntreatedData;
							}
						}
					}
					
				}
			}
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			return new MessageResult(false,ex.getMessage());
		}
		finally{
			ServletActionContext.getContext().getApplication().put(key, null);
		}
		if(!StringUtils.isBlank(alertMessages)){
			return new MessageResult(false,alertMessages);
		}
		else{
			return new MessageResult(true);
		}
		
	}

	public Map<String, String> getXxjllxMap() {
		return xxjllxMap;
	}

	public void setXxjllxMap(Map<String, String> xxjllxMap) {
		this.xxjllxMap = xxjllxMap;
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

	public Map<String, String> getReportMapForGR() {
		return reportMapForGR;
	}

	public void setReportMapForGR(Map<String, String> reportMapForGR) {
		this.reportMapForGR = reportMapForGR;
	}

	public Map<String, String> getReportMapForJG() {
		return reportMapForJG;
	}

	public void setReportMapForJG(Map<String, String> reportMapForJG) {
		this.reportMapForJG = reportMapForJG;
	}
	
}
