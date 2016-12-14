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
import java.util.ArrayList;
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
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;
import zxptsystem.dto.GRZXCJFKBW;
import zxptsystem.dto.GRZXCJFKBWSub;
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
 * 个人征信采集反馈报文
 * @author Transino
 *
 */
public class GRZXCJFKBWUploadFileService implements IObjectResultExecute{
	
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
			GRZXCJFKBW gRZXCJFKBW = new GRZXCJFKBW();
			List<GRZXCJFKBWSub> gRZXCJFKBWSubList = new ArrayList<GRZXCJFKBWSub>();
			int row = 0;
			Set<String> errorPrimarykeySet=new HashSet<String>();
			Set<String> errorPrimarykeyMXSet=new HashSet<String>();
			                                                                                                           
			DownLoadGRZXRportService downLoadGRZXReportService = (DownLoadGRZXRportService)FrameworkFactory.CreateBean("downLoadGRZXReportService");
			Map<String, String> reportMap = downLoadGRZXReportService.getReportMap();
			
			String StrZHJLZS="";//账户记录总数
			String reportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //反馈报文导入时间
			while((line=bufferedReader.readLine())!=null) {
				row++;
				if(!StringUtils.isBlank(line)){					
					if(row==1){
						gRZXCJFKBW = new GRZXCJFKBW();
						gRZXCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
						if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
							gRZXCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
						}
						gRZXCJFKBW.setStrVersionCode(subStringByByte(line,0,3));
						gRZXCJFKBW.setStrJRJGCode(subStringByByte(line,3,14));
						gRZXCJFKBW.setStrBWSCSJ(subStringByByte(line,17,14));
						gRZXCJFKBW.setStrYBWWJM(subStringByByte(line,31,27));
						gRZXCJFKBW.setStrBWCCWJM(subStringByByte(line,58,27));
						StrZHJLZS=subStringByByte(line,85,10);
						gRZXCJFKBW.setStrZHJLZS(StrZHJLZS);
						gRZXCJFKBW.setStrCCYYDM_1(subStringByByte(line,95,2));
						gRZXCJFKBW.setStrCCYYDM_2(subStringByByte(line,97,2));
						gRZXCJFKBW.setStrCCYYDM_3(subStringByByte(line,99,2));
						gRZXCJFKBW.setStrCCYYDM_4(subStringByByte(line,101,2));
						gRZXCJFKBW.setStrCCYYDM_5(subStringByByte(line,103,2));
						gRZXCJFKBW.setStrCBYQ(subStringByByte(line,105,1));
						String strLXR=subStringByByte(subStringByByte(line,106,30),30);
						gRZXCJFKBW.setStrLXR(strLXR);
						int begin = 106+strLXR.getBytes("GBK").length;
						int end = begin + 25;
						String strLXDH=subStringByByte(line,begin,end-begin);
						gRZXCJFKBW.setStrLXDH(strLXDH);
						
						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{gRZXCJFKBW,null});
					}
					else{
						GRZXCJFKBWSub gRZXCJFKBWSub = new GRZXCJFKBWSub();
						
						gRZXCJFKBWSub.setGRZXCJFKBW(gRZXCJFKBW);
						gRZXCJFKBWSub.setStrCCBWWJM(subStringByByte(line,0,27));
						gRZXCJFKBWSub.setStrCCYYXX_1(subStringByByte(line,27,7));
						gRZXCJFKBWSub.setStrCCYYXX_2(subStringByByte(line,34,7));
						gRZXCJFKBWSub.setStrCCYYXX_3(subStringByByte(line,41,7));
						gRZXCJFKBWSub.setStrCCYYXX_4(subStringByByte(line,48,7));
						gRZXCJFKBWSub.setStrCCYYXX_5(subStringByByte(line,55,7));
						String strCCXXJL = subStringByByte(line,62,line.getBytes("GBK").length-62);
						
						String strTable = ShowContext.getInstance().getShowEntityMap().get("BWLXDTO").get("GR");						
						String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(strTable);
						
						String strData =subStringByByte(strCCXXJL,5,strCCXXJL.getBytes("GBK").length-5);
							
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
						detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
						List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						String strId = "";
						if(objectList.size() == 1){
							strId = ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString();
							gRZXCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
							
							gRZXCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
							
							String reportData =subStringByByte(strCCXXJL,4,strCCXXJL.getBytes("GBK").length-4);
								
							String dtoName = objectList.get(0).getClass().getName();
							String entryValue = "";
						    for(Map.Entry<String, String> entry : reportMap.entrySet()){
						    	if(entry.getValue().contains(dtoName)){
						    		entryValue = entry.getValue();
						    		break;
						    	}
						    }
						    String[] entryValues = entryValue.split(",");
						    String tempReportData = subStringByByte(reportData,1,reportData.getBytes("GBK").length-1);
						    	
						    Map<String,String> reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[0].split("%")[1]);
				    		for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
				    			String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
				    			if(strValue==null){
				    				strValue="";
				    			}
				    			tempReportData =subStringByByte(tempReportData,strValue.getBytes("GBK").length,(tempReportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));
				    				
				    		}
				    		if(reportData.startsWith("B")){
				    			reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[1].split("%")[1]);
				    			for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
				    				String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
				    				if(strValue==null){
					    				strValue="";
					    			}
				    				tempReportData =subStringByByte(tempReportData,strValue.getBytes("GBK").length,(tempReportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));
					    		}
				    		}
				    		String reportFlag =subStringByByte(tempReportData,0,1) + "%";    
				    		for(Map.Entry<String, String> entry : reportMap.entrySet()){
							    if(entry.getValue().contains(dtoName) && entry.getValue().contains(reportFlag)){
							    	entryValue = entry.getValue();
							    	break;
							    }
							}
				    		entryValues = entryValue.split(",");

						    //构造反馈报文对象，不区分类型，全放reportObjectList
						    List<Object> reportObjectList = new ArrayList<Object>();

						    for(int i=0;i<entryValues.length;i++){
						    	String mxDto = entryValues[i];
						    	String[] mxDtos = mxDto.split("%");
						    	if(reportData.startsWith(mxDtos[0])){
						    		reportData =subStringByByte(reportData,1,reportData.getBytes("GBK").length-1);

						    		Map<String,String> reportLengthMap = ShowContext.getInstance().getShowEntityMap().get(mxDtos[1]);
						    		Object reportObject= Class.forName(mxDtos[1]).newInstance();
						    		
						    		for(Map.Entry<String,String> entry : reportLengthMap.entrySet()){
						    			String strValue = subStringByByte(reportData,Integer.parseInt(entry.getValue()));
						    			if(strValue==null){
						    				strValue="";
						    			}
						    			reportData =subStringByByte(reportData,strValue.getBytes("GBK").length,(reportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));   
						    			ReflectOperation.setFieldValue(reportObject, entry.getKey(), strValue.trim());
						    		}
						    		reportObjectList.add(reportObject);
						    	}
						    	if(reportData==null){
						    		break;
						    	}
						    	if(reportData.trim().equals("")){
						    		break;
						    	}
						    	if(reportData!=null && reportData.startsWith(mxDtos[0])){
						    		i--;
						    	}
						    }
						    
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
											String primarykeyMX = ReflectOperation.getPrimaryKeyValue(object).toString();
											if(!errorPrimarykeyMXSet.contains(primarykeyMX)){
												//判断是否和反馈报文对象相匹配
												boolean isMarched = false;
												for(Object reportObject : reportObjectList){
													if(reportObject.getClass().equals(object.getClass())){
														Map<String,String> reportLengthMap = ShowContext.getInstance().getShowEntityMap().get(reportObject.getClass().getName());
														isMarched = true;
														for(Map.Entry<String,String> entry : reportLengthMap.entrySet()){
															String reportFieldValue = ReflectOperation.getFieldValue(reportObject, entry.getKey()).toString();
															String objectFieldValue = "";
															Object objectFieldValueO = ReflectOperation.getFieldValue(object, entry.getKey());
															if(objectFieldValueO != null){
																objectFieldValue = objectFieldValueO.toString();
															}
															boolean isNType = false;
															if(ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field_ByJE").containsKey(reportObject.getClass().getName())){
																String ntypeValue = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field_ByJE").get(reportObject.getClass().getName());
																String[] ntypeValues = ntypeValue.split(",");
																for(int k=0;k<ntypeValues.length;k++){
																	if(ntypeValues[k].equals(entry.getKey())){
																		isNType = true;
																		break;
																	}
																}
															}
															if(isNType){
																int currentLenth = objectFieldValue.getBytes("GBK").length;
																for(int k= Integer.parseInt(entry.getValue());k>currentLenth;k--){
																	objectFieldValue = "0" + objectFieldValue;
																}
															}
															if(!reportFieldValue.equals(objectFieldValue)){
																isMarched = false;
																break;
															}
														}
														if(isMarched){
															break;
														}
													}
												}
												if(isMarched){
													ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
													errorPrimarykeyMXSet.add(primarykeyMX);
													//回执失败的明细，联动改变明细段报送状态和校验状态
													ReflectOperation.setFieldValue(object, "RPTSendType", "1");
													ReflectOperation.setFieldValue(object, "RPTCheckType", "1");
												}
											}
										}
										unSend++;
										feedbackTypeFalseCount++;
									}else{
										sendSuccessCount++;
										feedbackTypeSuccessCount++;
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
							
						}
						else{
							gRZXCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
						}
						gRZXCJFKBWSubList.add(gRZXCJFKBWSub);
						
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
			
			String baseDto = "zxptsystem.dto.AutoDTO_GR_GRXX_JC";
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
			//detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
			List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});

			for (Object object : objectList) {
				//如果错误主键集合不包含当前主键，并且为当前上报报文，则设置明细段回执状态为成功
				if(!errorPrimarykeySet.contains(ReflectOperation.getPrimaryKeyValue(object).toString())){
					List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
					for (Field field : fieldList) {
						Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
						for (Object objectMX : fieldObjectList) {
							if(ReflectOperation.getFieldValue(objectMX, "extend1")!=null && ReflectOperation.getFieldValue(objectMX, "extend1").equals(FileName)){
								ReflectOperation.setFieldValue(objectMX, "RPTFeedbackType", "2");
							}
						}
					}
				}
				else{
					//由于存在一条基础段数据对应多条报文，该条明细数据无错误反馈，为回执成功报文。
					List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
					for (Field field : fieldList) {
						Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
						for (Object objectMx : fieldObjectList) {
							String primarykeyMX = ReflectOperation.getPrimaryKeyValue(objectMx).toString();
							if(!errorPrimarykeyMXSet.contains(primarykeyMX)){
								if(ReflectOperation.getFieldValue(objectMx, "extend1")!=null && ReflectOperation.getFieldValue(objectMx, "extend1").equals(FileName)){
									ReflectOperation.setFieldValue(objectMx, "RPTFeedbackType", "2");
								}
							}
						}
					}
				}
			}
			
			//根据明细段，设置基础段状态
			for (Object object : objectList) {
				int feedbackTypeFalseCount = 0;
				int feedbackTypeSuccessCount = 0;
				int unFeedbackTypeCount=0;
				
				if(ReflectOperation.getFieldValue(object, "extend1")!=null && ReflectOperation.getFieldValue(object, "extend1").equals(FileName)){
					if(!ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("2")){
						feedbackTypeFalseCount ++;
					}else{
						feedbackTypeSuccessCount ++;
					}
				}
				List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
				for (Field field : fieldList) {
					Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
					for (Object objectMX : fieldObjectList) {
						if(ReflectOperation.getFieldValue(objectMX, "extend1")!=null && ReflectOperation.getFieldValue(objectMX, "extend1").equals(FileName)){
							if(!ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("2")){
								feedbackTypeFalseCount ++;
							}else{
								feedbackTypeSuccessCount ++;
							}
						}else{
							if(ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("2")){
								feedbackTypeSuccessCount ++;
							}else if(ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("3")){
								feedbackTypeFalseCount ++;
							}else if(ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("1")){
								unFeedbackTypeCount ++;
							}
						}
					}
				}
				
				if(unFeedbackTypeCount>=0 && feedbackTypeFalseCount==0 && feedbackTypeSuccessCount==0){
					ReflectOperation.setFieldValue(object, "RPTFeedbackType", "1");
				}
				else if(unFeedbackTypeCount==0 && feedbackTypeFalseCount>0 && feedbackTypeSuccessCount==0){
					ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
				}else if(unFeedbackTypeCount==0 && feedbackTypeFalseCount==0 && feedbackTypeSuccessCount>0){
					ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
				}else{
					ReflectOperation.setFieldValue(object, "RPTFeedbackType", "4");
				}
				
			}
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
			
			new RejectReportService().GetSendedRejectReportFeedback(objectList,FileName,"22"); //处理已报送报文驳回的回执状态
			
			//统计任务层状态
			String JRJGDM=FileName.substring(0, 14);
			List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
			CommonUpdateReportStatusService CommonStatus=new CommonUpdateReportStatusService();
			instInfoSubList=CommonStatus.getInstInfoSubList(JRJGDM);
			CommonStatus.commonUpdateReportStatus(objectList, instInfoSubList);
			
			if(gRZXCJFKBWSubList.size()>0){
				IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXCJFKBWSubList,null});
			}
			//对无反馈报文数据处理为已处理
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(GRZXWFKBWCL.class);
			detachedCriteria.add(Restrictions.eq("strBWM", FileName));
			detachedCriteria.add(Restrictions.eq("strGrReportType", "1"));
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
			updateData.UpdateI_RPT(FileName, "是", reportDate, outputFileName, TypeParse.parseInt(StrZHJLZS));
			
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
