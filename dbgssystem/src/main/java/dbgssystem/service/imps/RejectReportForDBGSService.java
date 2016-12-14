package dbgssystem.service.imps;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import dbgssystem.dto.SendedRejectReportForDBGS;

import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
/**
 * 已报送报文驳回
 * @author xiajieli
 *
 */
public class RejectReportForDBGSService implements IObjectResultExecute {

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		String[] selectReport = (String[])RequestManager.getIdList();
		if(selectReport==null){
			messageResult.getMessageSet().add("请至少选择一项");
		}else{
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			List<SendedRejectReportForDBGS> sendedRejectReportList=new ArrayList<SendedRejectReportForDBGS>();
			for (String sendedRejectReportId : selectReport) {
				SendedRejectReportForDBGS sendedRejectReport= (SendedRejectReportForDBGS)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SendedRejectReportForDBGS.class.getName(),sendedRejectReportId,null});
				sendedRejectReportList.add(sendedRejectReport);
			}
			if(sendedRejectReportList.size()>0){
				boolean isFlag=true;
				for (SendedRejectReportForDBGS sendedRejectReport : sendedRejectReportList) {
					if(!sendedRejectReport.getRPTFeedbackType().equals("1")){
						messageResult.getMessageSet().add("仅可驳回未回执的报文");
						isFlag=false;
						break;
					}
				}
				
				if(isFlag){
					//驳回
					for (SendedRejectReportForDBGS sendedRejectReport : sendedRejectReportList) {
						
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						sendedRejectReport.setRejectDate(TypeParse.parseDate(simpleDateFormat.format(new Date())));
						if(SecurityContext.getInstance().getLoginInfo() != null){
							sendedRejectReport.setRejectUser(((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserName());
						}else{
							sendedRejectReport.setRejectUser("admin");
						}
						
						//区分基础段与明细段
						String fileName=sendedRejectReport.getStrFileName();
						String strSuitCode=sendedRejectReport.getSuit().getStrSuitCode();
						String strReportType="";
						
						String RPTFeedbackType=sendedRejectReport.getRPTFeedbackType();
						
						Map<String, String> DBGSReportType = ShowContext.getInstance().getShowEntityMap().get("DB_ReportType");//根据类型得到code
						
						for (Map.Entry<String, String> entry: DBGSReportType.entrySet()) {
							if(entry.getValue().equals(sendedRejectReport.getStrReportType())){
								strReportType=entry.getKey();
								break;
							}
						}
						
						IParamObjectResultExecute singleObjectFindByCriteriaD = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCri = DetachedCriteria.forClass(Class.forName("dbgssystem.dto.AutoDTO_DB_DBXX_JC"));
						detachedCri.add(Restrictions.ne("RPTSendType", "1"));
						detachedCri.add(Restrictions.eq("RPTFeedbackType", RPTFeedbackType));
						if(strReportType.equals("82") || strReportType.equals("83")){
							detachedCri.add(Restrictions.eq("extend1", fileName));
						}
						List<Object> objectList = (List<Object>)singleObjectFindByCriteriaD.paramObjectResultExecute(new Object[]{detachedCri,null});
						
						for (Object object_Jc : objectList) {
							List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_Jc.getClass());
							
							boolean bool=false;
							for (Field field : listObjectMx) {
								IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								DetachedCriteria allDetachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
								allDetachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
								List<Object> allList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{allDetachedCriteria,null});
								for (Object objectMX : allList) {
									if(ReflectOperation.getFieldValue(objectMX, "extend1")!=null && ReflectOperation.getFieldValue(objectMX, "extend1").equals(fileName)){
										ReflectOperation.setFieldValue(objectMX, "RPTCheckType", "2");
										ReflectOperation.setFieldValue(objectMX, "RPTSendType", "1");
										ReflectOperation.setFieldValue(objectMX, "RPTFeedbackType", "1");
										if(!bool){
											bool=true;
										}
									}
								}
							}
							
							if(bool){
								UpdateStatus(object_Jc);
							}else{
								/*if(baseDtos.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){//是单个基础段上报
									UpdateStatus(object_Jc);
								}*/
							}
							
							if(listObjectMx.size()==0){//没有明细段的情况
								UpdateStatus(object_Jc);
							}
						}
						
						IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
						
						//统计基础段状态
						if(objectList.size()>0){
							
							IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
							for (Object object : objectList) {
								
								int sendSuccess=0;
								int unSend=0;
								int unRPTFeedbackType=0;
								int rPTFeedbackTypeSuccess=0;
								int rPTFeedbackTypefalse=0;
								
								List<Field> listObjectMx=ReflectOperation.getOneToManyField(object.getClass());
								
								for (Field field : listObjectMx) {
									DetachedCriteria detachedCriteria = null;
									
									unSend += findByDetachedCriteriaForJC("RPTSendType","1", detachedCriteria,object,field.getGenericType(),singleObjectFindByCountDao);
									
									sendSuccess += findByDetachedCriteriaForJC("RPTSendType","2", detachedCriteria,object,field.getGenericType(),singleObjectFindByCountDao);
									
									unRPTFeedbackType += findByDetachedCriteriaForJC("RPTFeedbackType","1", detachedCriteria,object,field.getGenericType(),singleObjectFindByCountDao);
									
									rPTFeedbackTypeSuccess += findByDetachedCriteriaForJC("RPTFeedbackType","2", detachedCriteria,object,field.getGenericType(),singleObjectFindByCountDao);
									
									rPTFeedbackTypefalse += findByDetachedCriteriaForJC("RPTFeedbackType","3", detachedCriteria,object,field.getGenericType(),singleObjectFindByCountDao);
									
								}

								String SubSendType="";
								if(sendSuccess==0 && unSend==0){
									SubSendType = "1";
								}else if(sendSuccess>0 && unSend==0){
									SubSendType = "2";
								}
								else if(sendSuccess==0 && unSend>0){
									SubSendType = "1";
								}
								else if(sendSuccess>0 && unSend>0){
									SubSendType = "5";
								}
								ReflectOperation.setFieldValue(object, "RPTSendType", SubSendType);

								String SubRPTFeedbackType="";
									if(ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("1")){
										unRPTFeedbackType+=1;
									}else if(ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("2")){
										rPTFeedbackTypeSuccess+=1;
									}else if(ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("3")){
										rPTFeedbackTypefalse+=1;
									}
								
									if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType>=0){
										SubRPTFeedbackType = "1";
									}else if(rPTFeedbackTypeSuccess>0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType==0){
										SubRPTFeedbackType = "2";
									}
									else if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse>0 && unRPTFeedbackType==0 ){
										SubRPTFeedbackType = "3";
									}
									else{
										SubRPTFeedbackType = "4";
									}
								
								ReflectOperation.setFieldValue(object, "RPTFeedbackType",SubRPTFeedbackType);
								
							}
							
							singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
							
							
							//统计任务层状态
							String JRJGDM="";
							if(strSuitCode.equals("21")){
								JRJGDM=fileName.substring(2, 13);
							}else if(strSuitCode.equals("24")){
								JRJGDM=fileName.substring(11, 22);
							}else if(strSuitCode.equals("22")){
								JRJGDM=fileName.substring(0, 14);
							}
							CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
							List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
							CommonUpdateReportStatusService CommonStatus=new CommonUpdateReportStatusService();
							instInfoSubList=CommonStatus.getInstInfoSubList(JRJGDM);
							commonStatus.commonUpdateReportStatus(objectList,instInfoSubList);
							
						}
						
						IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
						singleObjectUpdateDao.paramVoidResultExecute(new Object[]{sendedRejectReport,null});
					}
				}
			}
		}
		if (messageResult.getMessageSet().size() > 0) {
			messageResult.setMessage("处理失败");
			messageResult.setSuccess(false);
			messageResult.AlertTranslate();
			return messageResult;
		} else {
			messageResult.setMessage("处理成功");
			return messageResult;
		}
	}
	
	/**
	 * 修改状态字段
	 * @param objectJc 基础段对象
	 * @throws Exception 
	 */
	private void UpdateStatus(Object objectJc) throws Exception {
		ReflectOperation.setFieldValue(objectJc, "RPTCheckType", "2");
		ReflectOperation.setFieldValue(objectJc, "RPTSubmitStatus", "1");
		ReflectOperation.setFieldValue(objectJc, "RPTSendType", "1");
		ReflectOperation.setFieldValue(objectJc, "RPTVerifyType", "1");
	}

	/**
	 * 向已报送报文驳回修改批量数据
	 * @param objectList 对象列表
	 * @param FileName 文件名称
	 * @param strSuitCode 主题
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void GetSendedRejectReportFeedback(List<?> objectList,String FileName,String strSuitCode) throws Exception{
		int unRPTFeedbackType=0;
		int feedbackTypeSuccessCount=0;
		int feedbackTypeFalseCount=0;
		int unRPTFeedbackTypeAll=0;
	
		for (Object object_Jc : objectList) {
			
			if(ReflectOperation.getFieldValue(object_Jc, "extend1")!=null && ReflectOperation.getFieldValue(object_Jc, "extend1").equals(FileName)){
				String RPTFeedbackType=ReflectOperation.getFieldValue(object_Jc, "RPTFeedbackType").toString();
				if(RPTFeedbackType.equals("1")){
					unRPTFeedbackType++;
				}else if(RPTFeedbackType.equals("2")){
					feedbackTypeSuccessCount++;
				}else if(RPTFeedbackType.equals("3")){
					feedbackTypeFalseCount++;
				}else{
					unRPTFeedbackTypeAll++;
				}
			}else{
				List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_Jc.getClass());
				
				for (Field field : listObjectMx) {
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");	
					DetachedCriteria allDetachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
					allDetachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
					List<Object> allList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{allDetachedCriteria,null});
					for (Object objectMX : allList) {
						if(ReflectOperation.getFieldValue(objectMX, "extend1")!=null && ReflectOperation.getFieldValue(objectMX, "extend1").equals(FileName)){
							String RPTFeedbackType=ReflectOperation.getFieldValue(object_Jc, "RPTFeedbackType").toString();
							if(RPTFeedbackType.equals("1")){
								unRPTFeedbackType++;
							}else if(RPTFeedbackType.equals("2")){
								feedbackTypeSuccessCount++;
							}else if(RPTFeedbackType.equals("3")){
								feedbackTypeFalseCount++;
							}else{
								unRPTFeedbackTypeAll++;
							}
						}
					}
				}
			}
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaD = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCri = DetachedCriteria.forClass(SendedRejectReportForDBGS.class);
		detachedCri.add(Restrictions.eq("strFileName", FileName));
		detachedCri.add(Restrictions.eq("suit.strSuitCode", strSuitCode));
		List<SendedRejectReportForDBGS> sendedRejectReportList = (List<SendedRejectReportForDBGS>)singleObjectFindByCriteriaD.paramObjectResultExecute(new Object[]{detachedCri,null});
		if(sendedRejectReportList.size()>0){
			for (SendedRejectReportForDBGS sendedRejectReport : sendedRejectReportList) {
				if(unRPTFeedbackType>0 && feedbackTypeSuccessCount==0 && feedbackTypeFalseCount==0 && unRPTFeedbackTypeAll==0){
					sendedRejectReport.setRPTFeedbackType("1");
				}else if(unRPTFeedbackType==0 && feedbackTypeSuccessCount>0 && feedbackTypeFalseCount==0 && unRPTFeedbackTypeAll==0){
					sendedRejectReport.setRPTFeedbackType("2");
				}else if(unRPTFeedbackType==0 && feedbackTypeSuccessCount==0 && feedbackTypeFalseCount>0 && unRPTFeedbackTypeAll==0){
					sendedRejectReport.setRPTFeedbackType("3");
				}else if(unRPTFeedbackType==0 && feedbackTypeSuccessCount==0 && feedbackTypeFalseCount==0 && unRPTFeedbackTypeAll>0){
					sendedRejectReport.setRPTFeedbackType("4");
				}else{
					sendedRejectReport.setRPTFeedbackType("4");
				}
			}
		}
	}
	
	/**
	 * 向已报送报文驳回新增一条数据
	 * @param suitCode 主题
	 * @param strReportType 报文类型
	 * @param strFileName 报文名
	 * @param RPTFeedbackType 回执状态
	 * @throws Exception
	 */
	public void SendedRejectReport(String suitCode, String strReportType,String strFileName,String RPTFeedbackType) throws Exception {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendedRejectReportForDBGS sendedRejectReport = new SendedRejectReportForDBGS();
		sendedRejectReport.setStrReportType(strReportType);
		sendedRejectReport.setStrFileName(strFileName);
		sendedRejectReport.setRPTFeedbackType(RPTFeedbackType);
		
		Suit suit = new Suit();
		suit.setStrSuitCode(suitCode);
		sendedRejectReport.setSuit(suit);
		
		if(SecurityContext.getInstance().getLoginInfo() != null){
			UserInfo ui = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
			if(null==ui.getStrUserName()){
				sendedRejectReport.setOperationUser("admin");
			}else{
				sendedRejectReport.setOperationUser(ui.getStrUserName());
			}			
		}else{
			sendedRejectReport.setOperationUser("admin");
		}
		sendedRejectReport.setOperationUser("admin");
		sendedRejectReport.setDtDate(TypeParse.parseDate(simpleDateFormat.format(new Date())));
		sendedRejectReport.setStrFileName(strFileName);
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{sendedRejectReport,null});
	}
	
	/**
	 * 通过条件统计基础段状态
	 * @param status 状态
	 * @param statusValue 状态值
	 * @param detachedCriteria 条件
	 * @param object_Jc 基础段对象
	 * @param type 类型
	 * @param singleObjectFindByCountDao 统计条数
	 * @return 统计的条数
	 * @throws Exception
	 */
	   public Integer findByDetachedCriteriaForJC(String status, String statusValue, DetachedCriteria detachedCriteria, Object object_Jc,Type type,IParamObjectResultExecute singleObjectFindByCountDao) throws Exception{
		    detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(type));
			detachedCriteria.add(Restrictions.eq("FOREIGNID", object_Jc));
			detachedCriteria.add(Restrictions.eq(status, statusValue));
			return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	   }
}
