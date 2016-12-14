package zxptsystem.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLSC;
import zxptsystem.dto.AutoDTO_GRZXSJSC;
import zxptsystem.dto.AutoDTO_GR_GRXX_JC;
import zxptsystem.dto.GRZXSJSCCJFKBW;
import zxptsystem.dto.GRZXSJSCCJFKBWSub;
import zxptsystem.dto.GRZXWFKBWCL;
import zxptsystem.dto.I_RPT;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
/**
 * 个人征信数据删除采集（个人征信历史逾期记录删除）反馈报文解析
 * @author xiajieli
 *
 */
public class GRZXSJSCCJFKBWUploadFileService implements IObjectResultExecute{
	
	private Map<String,String> statusMap;

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		try{
			File uploadFile1= (File) ActionContext.getContext().getSession().get("uploadFile1");
			if(uploadFile1==null){
				return new MessageResult(false,"解析文件不能为空");
			}
			String encName=(String)ActionContext.getContext().getSession().get("encName");
			if(!StringUtils.isBlank(encName) && !encName.endsWith(".enc")){
				return new MessageResult(false,"请导入后缀为enc格式的文件");
			}
			String ErrorMessage=(String)ActionContext.getContext().getSession().get("ErrorMessage");
			if(!StringUtils.isBlank(ErrorMessage)){
				return new MessageResult(false,ErrorMessage);
			}
			
			String FileName= (String) ActionContext.getContext().getSession().get("FileName");
			String outputFileName=(String) ActionContext.getContext().getSession().get("outputFileName");
			
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(outputFileName),"GBK"));
			
			String line=null; 
			int row = 0;
			Set<String> errorPrimarykeySet=new HashSet<String>();
			CommonUpdateReportStatusService commonUpdateReportStatusService=new CommonUpdateReportStatusService();
			List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
			String JRJGDM=FileName.substring(0, 14);
			instInfoSubList=commonUpdateReportStatusService.getInstInfoSubList(JRJGDM);
			String reportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //反馈报文导入时间
			
			String BWLB=FileName.substring(23, 24);
			
			if(!StringUtils.isBlank(BWLB) && BWLB.equals("8")){//数据删除
				
				GRZXSJSCCJFKBW gRZXSJSCCJFKBW = new GRZXSJSCCJFKBW();
				List<GRZXSJSCCJFKBWSub> gRZXSJSCCJFKBWSubList = new ArrayList<GRZXSJSCCJFKBWSub>();
				
				String strWSCJLDJSNY=null;
				int CCJLHZS=0;//出错记录条数
				
				while((line=bufferedReader.readLine())!=null) {
					row++;
					if(!StringUtils.isBlank(line)){					
						if(row==1){
							gRZXSJSCCJFKBW = new GRZXSJSCCJFKBW();
							gRZXSJSCCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
							if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
								gRZXSJSCCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
							}
							
							gRZXSJSCCJFKBW.setStrVersionCode(subStringByByte(line,0,3));
							gRZXSJSCCJFKBW.setStrBWSCSJ(subStringByByte(line,3,14));
							gRZXSJSCCJFKBW.setStrBWCCWJM(subStringByByte(line,17,27));
							gRZXSJSCCJFKBW.setStrGRZXSJSCCCXX(subStringByByte(line,44,2));
							String strLXR=subStringByByte(subStringByByte(line,46,30),30);
							gRZXSJSCCJFKBW.setStrLXR(strLXR);
							int begin = 46+strLXR.getBytes("GBK").length;
							int end = begin + 25;
							String strLXDH=subStringByByte(line,begin,end-begin);
							gRZXSJSCCJFKBW.setStrLXDH(strLXDH);
							
							IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
							singleObjectSaveDao.paramVoidResultExecute(new Object[]{gRZXSJSCCJFKBW,null});
						}
						else{
							CCJLHZS++;
							GRZXSJSCCJFKBWSub gRZXSJSCCJFKBWSub = new GRZXSJSCCJFKBWSub();
							gRZXSJSCCJFKBWSub.setGRZXSJSCCJFKBW(gRZXSJSCCJFKBW);
							gRZXSJSCCJFKBWSub.setStrGRZXSJSCCWDM(subStringByByte(line,0,2));
							strWSCJLDJSNY=subStringByByte(line,2,6);
							gRZXSJSCCJFKBWSub.setStrWSCJLDJSNY(strWSCJLDJSNY);
							
							String strCCXXJL = subStringByByte(line,8,70);
							String strTable ="zxptsystem.dto.AutoDTO_GRZXSJSC";						
							String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(strTable);
							
							String strData = strCCXXJL;
							Map<String,String> fieldValue = new HashMap<String,String>();
							Map<String,String> dtoLengthMap = ShowContext.getInstance().getShowEntityMap().get(strTable);
							String[] primaryKeys = primaryKey.split(",");
							
							for(int i=0;i<primaryKeys.length;i++){
								int length = 0;
								for(Map.Entry<String,String> entry : dtoLengthMap.entrySet()){
									if(primaryKeys[i].equals(entry.getKey())){
										int beginLen = 0;
										if(length>0){
											beginLen = subStringByByte(strData, length).getBytes("GBK").length;
										}else{
											beginLen = length;
										}
										String strValue=subStringByByte(strData,beginLen,Integer.parseInt(entry.getValue())).trim();
										fieldValue.put(entry.getKey(), strValue);
										break;
									}
									length += Integer.parseInt(entry.getValue());
								}
							}
							
							IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strTable));
							for(Map.Entry<String,String> entry : fieldValue.entrySet()){
								detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
							}
							List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							String strId = "";
							if(objectList.size() == 1){
								strId = ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString();
								gRZXSJSCCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
								gRZXSJSCCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
								
								int feedbackTypeFalseCount = 0;
								int feedbackTypeSuccessCount = 0;
								int unSend = 0;
								int sendSuccessCount = 0;
								
								List<Field> fieldList=ReflectOperation.getOneToManyField(objectList.get(0).getClass());
								for (Field field : fieldList) {
									Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(objectList.get(0), field.getName());
									for (Object object : fieldObjectList) {
										if(!ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("2")){
											if(ReflectOperation.getFieldValue(object, "extend1")!=null && ReflectOperation.getFieldValue(object, "extend1").equals(FileName)){
												ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
											}
											feedbackTypeFalseCount++;
											unSend++;
										}else{
											feedbackTypeSuccessCount++;
											sendSuccessCount++;
										}
									}
								}
								//统计基础段报送状态
								if(sendSuccessCount==0 && unSend==0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTSendType", "1");
								}else if(sendSuccessCount>0 && unSend==0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTSendType", "2");
								}
								else if(sendSuccessCount==0 && unSend>0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTSendType", "1");
								}
								else if(sendSuccessCount>0 && unSend>0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTSendType", "5");
								}
								
								if(feedbackTypeFalseCount==0 && feedbackTypeSuccessCount==0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTFeedbackType", "3");
								}
								else if(feedbackTypeFalseCount>0 && feedbackTypeSuccessCount==0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTFeedbackType", "3");
								}else if(feedbackTypeFalseCount==0 && feedbackTypeSuccessCount>0){
									ReflectOperation.setFieldValue(objectList.get(0), "RPTFeedbackType", "2");
								}else{
									ReflectOperation.setFieldValue(objectList.get(0), "RPTFeedbackType", "4");
								}
								//回执失败，联动改变基础段的其它状态
								if(statusMap!=null){
									for (Map.Entry<String, String>  entry: statusMap.entrySet()) {
										ReflectOperation.setFieldValue(objectList.get(0), entry.getKey(), entry.getValue());
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});

								errorPrimarykeySet.add(ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString());
							
								//如果某一期记录删除不成功，停止删除该记录之前的所有记录，同时反馈错误代码、该记录的结算年月日及出现错误的删除记录
								int intWSCJLDJSNY=0;
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXSJSC"));
								detachedCriteria.add(Restrictions.eq("extend1", FileName));
								detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "3"));
								List<AutoDTO_GRZXSJSC> autoDTO_GRZXSJSCList = (List<AutoDTO_GRZXSJSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								if(autoDTO_GRZXSJSCList.size()>0){
					 				for (AutoDTO_GRZXSJSC gRZXSJSC : autoDTO_GRZXSJSCList) {
										singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
										detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_GRXX_JC.class);
										if((gRZXSJSC.getQSJSYHKRQ()!=null || !gRZXSJSC.getQSJSYHKRQ().equals("")) && (gRZXSJSC.getJSJSYHKRQ()!=null || !gRZXSJSC.getJSJSYHKRQ().equals(""))){
											intWSCJLDJSNY = Integer.parseInt(strWSCJLDJSNY);
											int intQSJSYHKRQ=Integer.parseInt(gRZXSJSC.getQSJSYHKRQ());
											int intJSJSYHKRQ=Integer.parseInt(gRZXSJSC.getJSJSYHKRQ());
											if(intQSJSYHKRQ>=intWSCJLDJSNY && intWSCJLDJSNY<=intJSJSYHKRQ){
												detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXSJSC.getStrJRJGCode()));
												detachedCriteria.add(Restrictions.eq("YWH", gRZXSJSC.getStrYWCode()));
												detachedCriteria.add(Restrictions.gt("JSYHKRQ", strWSCJLDJSNY));
												detachedCriteria.add(Restrictions.le("JSYHKRQ", gRZXSJSC.getJSJSYHKRQ()));
											}
										}
										List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										if(Object_JCList.size()>0){
											for (Object object : Object_JCList) {
												ReflectOperation.setFieldValue(object, "RPTFeedbackType","5");
												
												//修改当前基础段下所有明细段状态为已删除
												List<Field> fieldListMx=ReflectOperation.getOneToManyField(object.getClass());
												for (Field field : fieldListMx) {
													Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
													
													for (Object objectMX : fieldObjectList) {
														singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
														detachedCriteria = DetachedCriteria.forClass(objectMX.getClass());
														List<Object> objectListMx = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
														for (Object objMx : objectListMx) {
															ReflectOperation.setFieldValue(objMx, "RPTFeedbackType", "5");
														}
													}
												}
											}
											singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
											singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
										}
					 				}
								}
							}
							else{
								gRZXSJSCCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
							}
							gRZXSJSCCJFKBWSubList.add(gRZXSJSCCJFKBWSub);
							
							/**插入个人征信报送流转明细表*/
							GenerAndAnaInsertService insertData=new GenerAndAnaInsertService();
							List<I_RPT> i_RPTList=insertData.GetI_RPTByBWM(FileName);
							if(i_RPTList.size()>0){
								String strBWSCSJ=i_RPTList.get(0).getStrBWSCSJ();
								String strBWLB=i_RPTList.get(0).getStrBWLB();
								insertData.InsertI_RPT_DETAIL(strId, FileName,strBWSCSJ , strBWLB, outputFileName, reportDate, strCCXXJL);
							}
						}//报文体结束
					}
				}
				//处理数据删除
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXSJSC"));
				detachedCriteria.add(Restrictions.eq("extend1", FileName));
				detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "3"));
				List<AutoDTO_GRZXSJSC> autoDTO_GRZXSJSCList = (List<AutoDTO_GRZXSJSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				if(autoDTO_GRZXSJSCList.size()>0){
	 				for (AutoDTO_GRZXSJSC gRZXSJSC : autoDTO_GRZXSJSCList) {
						ReflectOperation.setFieldValue(gRZXSJSC, "RPTFeedbackType", "2");
						
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_GRXX_JC.class);
						//若“起始结算/应还款日期”不为“20000101”，“结束结算/应还款日期”不为空，删除从“起始结算/应还款日期”所在月（含所在月）到“结束结算/应还款日期”所在月（含所在月）之间的账户的交易记录
						if(!gRZXSJSC.getQSJSYHKRQ().equals("20000101") && (gRZXSJSC.getJSJSYHKRQ()!=null || !gRZXSJSC.getJSJSYHKRQ().equals(""))){
							detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXSJSC.getStrJRJGCode()));
							detachedCriteria.add(Restrictions.eq("YWH", gRZXSJSC.getStrYWCode()));
							detachedCriteria.add(Restrictions.ge("JSYHKRQ", gRZXSJSC.getQSJSYHKRQ()));
							detachedCriteria.add(Restrictions.le("JSYHKRQ", gRZXSJSC.getJSJSYHKRQ()));
						}//若“起始结算/应还款日期”不为“20000101”，“结束结算/应还款日期”为空，删除“起始结算/应还款日期”所在月（含所在月）之后的账户的交易记录。
						else if(!gRZXSJSC.getQSJSYHKRQ().equals("20000101") && (gRZXSJSC.getJSJSYHKRQ()==null || gRZXSJSC.getJSJSYHKRQ().equals(""))){
							detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXSJSC.getStrJRJGCode()));
							detachedCriteria.add(Restrictions.eq("YWH", gRZXSJSC.getStrYWCode()));
							detachedCriteria.add(Restrictions.ge("JSYHKRQ", gRZXSJSC.getQSJSYHKRQ()));
						}//若“起始结算/应还款日期”为“20000101”，“结束结算/应还款日期”不为空，删除账户从开立时到“结束结算/应还款日期”所在月（含所在月）的交易记录
						else if(gRZXSJSC.getQSJSYHKRQ().equals("20000101") && (gRZXSJSC.getJSJSYHKRQ()!=null || !gRZXSJSC.getJSJSYHKRQ().equals(""))){
							detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXSJSC.getStrJRJGCode()));
							detachedCriteria.add(Restrictions.eq("YWH", gRZXSJSC.getStrYWCode()));
							detachedCriteria.add(Restrictions.le("JSYHKRQ", gRZXSJSC.getJSJSYHKRQ()));
							
						}//若“起始结算/应还款日期”为“20000101”，“结束结算/应还款日期”为空，删除账户在数据库中的所有信息，包括交易记录和身份信息。
						else if(gRZXSJSC.getQSJSYHKRQ().equals("20000101") && (gRZXSJSC.getJSJSYHKRQ()==null || gRZXSJSC.getJSJSYHKRQ().equals(""))){
							detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXSJSC.getStrJRJGCode()));
							detachedCriteria.add(Restrictions.eq("YWH", gRZXSJSC.getStrYWCode()));
						}
						List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						if(Object_JCList.size()>0){
							for (Object object : Object_JCList) {
								ReflectOperation.setFieldValue(object, "RPTFeedbackType","5");
								
								//修改当前基础段下所有明细段状态为已删除
								List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
								for (Field field : fieldList) {
									Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
									
									for (Object objectMX : fieldObjectList) {
										singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
										detachedCriteria = DetachedCriteria.forClass(objectMX.getClass());
										List<Object> objectListMx = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										for (Object objMx : objectListMx) {
											ReflectOperation.setFieldValue(objMx, "RPTFeedbackType", "5");
										}
									}
								}
							}
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
							
							new RejectReportService().GetSendedRejectReportFeedback(Object_JCList,FileName,"22"); //处理已报送报文驳回的回执状态
							
							//统计明细表任务层的状态
							commonUpdateReportStatusService.commonUpdateReportStatus(Object_JCList,instInfoSubList);
						}
					}
	 			}
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{autoDTO_GRZXSJSCList,null});
				
				//统计数据删除表任务状态
				commonUpdateReportStatusService.commonUpdateReportStatus(autoDTO_GRZXSJSCList, instInfoSubList);
				
				if(gRZXSJSCCJFKBWSubList.size()>0){
					IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXSJSCCJFKBWSubList,null});
				}
				//对无反馈报文数据处理为已处理
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(GRZXWFKBWCL.class);
				detachedCriteria.add(Restrictions.eq("strBWM", FileName));
				detachedCriteria.add(Restrictions.eq("strGrReportType", "8"));
				List<GRZXWFKBWCL> gRZXWFKBWCLList=(List<GRZXWFKBWCL>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(gRZXWFKBWCLList.size()>0){
					for (GRZXWFKBWCL grzxwfkbwcl : gRZXWFKBWCLList) {
						ReflectOperation.setFieldValue(grzxwfkbwcl, "strBWCL", "2");
					}
					IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXWFKBWCLList,null});
				}
	 			
				/**更新个人征信报送流转概况表*/
				GenerAndAnaInsertService updateData=new GenerAndAnaInsertService();
				updateData.UpdateI_RPT(FileName, "是", reportDate, outputFileName, CCJLHZS);
			}
			else if(!StringUtils.isBlank(BWLB) && BWLB.equals("A")){//历史逾期删除
				
				GRZXSJSCCJFKBW gRZXSJSCCJFKBW = new GRZXSJSCCJFKBW();
				List<GRZXSJSCCJFKBWSub> gRZXSJSCCJFKBWSubList = new ArrayList<GRZXSJSCCJFKBWSub>();
				
				int CCJLHZS=0;//出错记录条数
				while((line=bufferedReader.readLine())!=null) {
					row++;
					if(!StringUtils.isBlank(line)){					
						if(row==1){
							gRZXSJSCCJFKBW = new GRZXSJSCCJFKBW();
							gRZXSJSCCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
							if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
								gRZXSJSCCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
							}
							
							gRZXSJSCCJFKBW.setStrVersionCode(subStringByByte(line,0,3));
							gRZXSJSCCJFKBW.setStrBWSCSJ(subStringByByte(line,3,14));
							gRZXSJSCCJFKBW.setStrBWCCWJM(subStringByByte(line,17,27));
							gRZXSJSCCJFKBW.setStrGRZXSJSCCCXX(subStringByByte(line,44,2));
							String strLXR=subStringByByte(subStringByByte(line,46,30),30);
							gRZXSJSCCJFKBW.setStrLXR(strLXR);
							int begin = 46+strLXR.getBytes("GBK").length;
							int end = begin + 25;
							String strLXDH=subStringByByte(line,begin,end-begin);
							gRZXSJSCCJFKBW.setStrLXDH(strLXDH);
							
							IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
							singleObjectSaveDao.paramVoidResultExecute(new Object[]{gRZXSJSCCJFKBW,null});
						}
						else{
							CCJLHZS++;
							GRZXSJSCCJFKBWSub gRZXSJSCCJFKBWSub = new GRZXSJSCCJFKBWSub();
							gRZXSJSCCJFKBWSub.setGRZXSJSCCJFKBW(gRZXSJSCCJFKBW);
							gRZXSJSCCJFKBWSub.setStrGRZXSJSCCWDM(subStringByByte(line,0,7).trim());
							gRZXSJSCCJFKBWSub.setStrWSCJLDJSNY(subStringByByte(line,7,6));
							
							String strCCXXJL = subStringByByte(line,13,115);
							String strTable ="zxptsystem.dto.AutoDTO_GRZXLSYQJLSC";						
							String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(strTable);
							
							String strData = strCCXXJL;
							Map<String,String> fieldValue = new HashMap<String,String>();
							Map<String,String> dtoLengthMap = ShowContext.getInstance().getShowEntityMap().get(strTable);
							String[] primaryKeys = primaryKey.split(",");
							
							for(int i=0;i<primaryKeys.length;i++){
								int length = 0;
								for(Map.Entry<String,String> entry : dtoLengthMap.entrySet()){
									if(primaryKeys[i].equals(entry.getKey())){
										int beginLen = 0;
										if(length>0){
											beginLen = subStringByByte(strData, length).getBytes("GBK").length;
										}else{
											beginLen = length;
										}
										String strValue=subStringByByte(strData,beginLen,Integer.parseInt(entry.getValue())).trim();
										fieldValue.put(entry.getKey(), strValue);
										break;
									}
									length += Integer.parseInt(entry.getValue());
								}
							}
							
							IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strTable));
							for(Map.Entry<String,String> entry : fieldValue.entrySet()){
								detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
							}
							List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							String strId = "";
							if(objectList.size() == 1){
								strId = ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString();
								gRZXSJSCCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
								gRZXSJSCCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
								
								ReflectOperation.setFieldValue(objectList.get(0), "RPTFeedbackType", "3");//定位到数据，回执失败
								
								//回执失败，联动改变基础段的其它状态
								if(statusMap!=null){
									for (Map.Entry<String, String>  entry: statusMap.entrySet()) {
										ReflectOperation.setFieldValue(objectList.get(0), entry.getKey(), entry.getValue());
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});

								errorPrimarykeySet.add(ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString());
							}
							else{
								gRZXSJSCCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
							}
							gRZXSJSCCJFKBWSubList.add(gRZXSJSCCJFKBWSub);
							
							/**插入个人征信报送流转明细表*/
							GenerAndAnaInsertService insertData=new GenerAndAnaInsertService();
							List<I_RPT> i_RPTList=insertData.GetI_RPTByBWM(FileName);
							if(i_RPTList.size()>0){
								String strBWSCSJ=i_RPTList.get(0).getStrBWSCSJ();
								String strBWLB=i_RPTList.get(0).getStrBWLB();
								insertData.InsertI_RPT_DETAIL(strId, FileName,strBWSCSJ , strBWLB, outputFileName, reportDate, strCCXXJL);
							}
						}//报文体结束
					}
				}
				//修改回执成功状态
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXLSYQJLSC"));
				detachedCriteria.add(Restrictions.eq("extend1", FileName));
				detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "3"));
				List<AutoDTO_GRZXLSYQJLSC> autoDTO_GRZXLSYQJLSCList = (List<AutoDTO_GRZXLSYQJLSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				if(autoDTO_GRZXLSYQJLSCList.size()>0){
	 				for (AutoDTO_GRZXLSYQJLSC gRZXLSYQJLSC : autoDTO_GRZXLSYQJLSCList) {
						ReflectOperation.setFieldValue(gRZXLSYQJLSC, "RPTFeedbackType", "2");
					}
				}
				
				//统计表任务状态
				commonUpdateReportStatusService.commonUpdateReportStatus(autoDTO_GRZXLSYQJLSCList, instInfoSubList);
				
				if(gRZXSJSCCJFKBWSubList.size()>0){
					IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXSJSCCJFKBWSubList,null});
				}
				
				/**联动删除基础段逾期状况*/
				for (AutoDTO_GRZXLSYQJLSC gRZXLSYQJLSC : autoDTO_GRZXLSYQJLSCList) {
					
					String JRJGDM1="";
					String YWH="";
					String QSYQYF=null;
					String JSYQYF=null;
					
					if(gRZXLSYQJLSC.getJRJGDM()!=null){
						JRJGDM1=gRZXLSYQJLSC.getJRJGDM();
					}
					if(gRZXLSYQJLSC.getYWH()!=null){
						YWH=gRZXLSYQJLSC.getYWH();
					}
					if(gRZXLSYQJLSC.getQSYQYF()!=null){
						QSYQYF=gRZXLSYQJLSC.getQSYQYF();
					}
					if(gRZXLSYQJLSC.getJSYQYF()!=null){
						JSYQYF=gRZXLSYQJLSC.getJSYQYF();
					}
					
					detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GR_GRXX_JC"));
					detachedCriteria.add(Restrictions.eq("JRJGDM", JRJGDM1));
					detachedCriteria.add(Restrictions.eq("YWH", YWH));
					detachedCriteria.addOrder(Order.desc("JSYHKRQ"));
					List<Object> objectJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(objectJCList.size()>0){
						String JSYHKRQ="";
						String HKZT="";
						if(ReflectOperation.getFieldValue(objectJCList.get(0), "JSYHKRQ")!=null && ReflectOperation.getFieldValue(objectJCList.get(0), "HKZT")!=null){
							JSYHKRQ=ReflectOperation.getFieldValue(objectJCList.get(0), "JSYHKRQ").toString();
							HKZT=ReflectOperation.getFieldValue(objectJCList.get(0), "HKZT").toString();
							
							if(StringUtils.isBlank(QSYQYF) && StringUtils.isBlank(JSYQYF)){//起始逾期月份，结束逾期月份都为空
								for(int i=1;i<=7;i++){
									if(HKZT.contains(String.valueOf(i))){
										HKZT=HKZT.replace(String.valueOf(i), "N");
									}
								}
								ReflectOperation.setFieldValue(objectJCList.get(0), "HKZT", HKZT);
							}
							else if(!StringUtils.isBlank(QSYQYF) && StringUtils.isBlank(JSYQYF)){//起始逾期月份不为空，结束逾期月份为空
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
								Calendar cal_QSYQYF = Calendar.getInstance();
								Calendar cal_JSYHKRQ = Calendar.getInstance();
								cal_JSYHKRQ.setTime(sdf.parse(JSYHKRQ));
								cal_QSYQYF.setTime(sdf1.parse(QSYQYF));
								
								int month = cal_JSYHKRQ.get(Calendar.MONTH) - cal_QSYQYF.get(Calendar.MONTH);//月份相减
								int yearMonth = (cal_JSYHKRQ.get(Calendar.YEAR) - cal_QSYQYF.get(Calendar.YEAR))*12;//年份相减得到月份
								int count=month  + yearMonth ;//总月份
								
								String HKZTNew="";
								int index=0;
								for(int i=0;i<HKZT.length();i++){
									if(i==HKZT.length()-count){
										index=i;
										break;
									}
								}
								
								HKZTNew =HKZT.substring(0, index-1);
								
								String str=HKZT.substring(index-1, HKZT.length());
								for(int i=1;i<=7;i++){
									if(str.contains(String.valueOf(i))){
										str=str.replace(String.valueOf(i), "N");
									}
								}
								
								HKZTNew +=str;
								
								ReflectOperation.setFieldValue(objectJCList.get(0), "HKZT", HKZTNew);
								
							}
							else if(!StringUtils.isBlank(QSYQYF) && !StringUtils.isBlank(JSYQYF)){//起始逾期月份，结束逾期月份都不为空
								Calendar cal_QSYQYF = Calendar.getInstance();
								Calendar cal_JSYQYF = Calendar.getInstance();
								Calendar cal_JSYHKRQ = Calendar.getInstance();
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
								cal_JSYHKRQ.setTime(sdf.parse(JSYHKRQ));
								cal_QSYQYF.setTime(sdf1.parse(QSYQYF));
								cal_JSYQYF.setTime(sdf1.parse(JSYQYF));
								
								int qs_month = cal_JSYHKRQ.get(Calendar.MONTH) - cal_QSYQYF.get(Calendar.MONTH);
								int qs_yearMonth = (cal_JSYHKRQ.get(Calendar.YEAR) - cal_QSYQYF.get(Calendar.YEAR))*12;
								
								int js_month = cal_JSYHKRQ.get(Calendar.MONTH) - cal_JSYQYF.get(Calendar.MONTH);
								int js_yearMonth = (cal_JSYHKRQ.get(Calendar.YEAR) - cal_JSYQYF.get(Calendar.YEAR))*12;
								
								
								int qs_count=qs_month  + qs_yearMonth ;
								int js_count=js_month  + js_yearMonth ;
								
								String HKZTNew="";
								int qs_index=0;
								int js_index=0;
								for(int i=0;i<HKZT.length();i++){
									if(i==HKZT.length()-qs_count){
										qs_index=i;
									}
									if(i==HKZT.length()-js_count){
										js_index=i;
									}
								}
								
								HKZTNew =HKZT.substring(0, qs_index-1);
								String str=HKZT.substring(qs_index-1, js_index);
								for(int i=1;i<=7;i++){
									if(str.contains(String.valueOf(i))){
										str=str.replace(String.valueOf(i), "N");
									}
								}
								
								HKZTNew +=str;
								HKZTNew+=HKZT.substring(js_index,HKZT.length());
								ReflectOperation.setFieldValue(objectJCList.get(0), "HKZT", HKZTNew);
							}
							
							IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{objectJCList.get(0),null});
							
						}
						
					}
					
				}
				
				//对无反馈报文数据处理为已处理
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(GRZXWFKBWCL.class);
				detachedCriteria.add(Restrictions.eq("strBWM", FileName));
				detachedCriteria.add(Restrictions.eq("strGrReportType", "A"));
				List<GRZXWFKBWCL> gRZXWFKBWCLList=(List<GRZXWFKBWCL>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(gRZXWFKBWCLList.size()>0){
					for (GRZXWFKBWCL grzxwfkbwcl : gRZXWFKBWCLList) {
						ReflectOperation.setFieldValue(grzxwfkbwcl, "strBWCL", "2");
					}
					IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXWFKBWCLList,null});
				}
				
				/**更新个人征信报送流转概况表*/
				GenerAndAnaInsertService updateData=new GenerAndAnaInsertService();
				updateData.UpdateI_RPT(FileName, "是", reportDate, outputFileName, CCJLHZS);
				
			}
			
			return new MessageResult("报文解析成功");
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			if(StringUtils.isBlank(ex.getMessage())){
				return new MessageResult(false,"报文解析文件异常");
			}
			else{
				ex.printStackTrace();
				return new MessageResult(false,"报文解析文件异常:" + ex.getMessage());
			}
		}
	}
	
	public static void deCompress(String fileGzipIn, String fileOut) throws IOException{
		FileInputStream gzipIn = new FileInputStream(fileGzipIn);
		FileOutputStream out = new FileOutputStream(fileOut);
		
		GZIPInputStream in = new GZIPInputStream(gzipIn);
		byte buf[] = new byte[BUFFER];
		int num;
		while ((num = in.read(buf, 0, BUFFER)) != -1)
			out.write(buf, 0, num);
		in.close();
		
		out.close();
		gzipIn.close();
	}

	public void setStatusMap(Map<String,String> statusMap) {
		this.statusMap = statusMap;
	}

	public Map<String,String> getStatusMap() {
		return statusMap;
	}

	private String subStringByByte(String str, int len) throws UnsupportedEncodingException {
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes("GBK");
            if (a.length <= len) {
                result = str;
            } else if (len > 0) {
                result = new String(a, 0, len,"GBK");
                int length = result.length();
                if (str.charAt(length - 1) != result.charAt(length - 1)) {
                    if (length < 2) {
                        result = null;
                    } else {
                        result = result.substring(0, length - 1);
                    }
                }
            }
        }
        return result;
    }
	
	private String subStringByByte(String str,int start, int subLen) throws UnsupportedEncodingException {
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes("GBK");
            if (a.length <= subLen) {
                result = str;
            } else if (subLen > 0) {
                result = new String(a, start, subLen,"GBK");
            }
        }
        return result;
    }
	
	private static final int BUFFER = 2048;
}
