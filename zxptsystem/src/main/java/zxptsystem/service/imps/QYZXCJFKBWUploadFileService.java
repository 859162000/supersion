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
import zxptsystem.dto.E_RPT;
import zxptsystem.dto.QYZXCJFKBW;
import zxptsystem.dto.QYZXCJFKBWSub;

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
 * 企业征信采集反馈报文
 * @author Transino
 *
 */
public class QYZXCJFKBWUploadFileService implements IObjectResultExecute{

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
			QYZXCJFKBW qYZXCJFKBW = new QYZXCJFKBW();
			List<QYZXCJFKBWSub> qYZXCJFKBWSubList = new ArrayList<QYZXCJFKBWSub>();
			Set<String> errorPrimarykeySet=new HashSet<String>();
			Set<String> errorPrimarykeyMXSet=new HashSet<String>();
			Set<String> fkbwTypeSet=new HashSet<String>();
			
			DownLoadRportService downLoadRportService = (DownLoadRportService)FrameworkFactory.CreateBean("downLoadReportService");
			Map<String, String> reportMap = downLoadRportService.getReportMap();
			
			String reportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //反馈报文导入时间
			int strFKCWJLTS=0;//反馈错误记录条数
			String CBYQ="";//重报要求
			while((line=bufferedReader.readLine())!=null) {
				if(!StringUtils.isBlank(line)){
					if(line.startsWith("A")){
						qYZXCJFKBW = new QYZXCJFKBW();
						qYZXCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
						if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
							qYZXCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
						}
						qYZXCJFKBW.setStrVersionCode(subStringByByte(line,1,3));
						qYZXCJFKBW.setStrJRJGCode(subStringByByte(line,4,11));
						qYZXCJFKBW.setStrBWSCSJ(subStringByByte(line,15,14));
						qYZXCJFKBW.setStrBWCCWJM(subStringByByte(line,29,28));
						qYZXCJFKBW.setStrBWLX(subStringByByte(line,57,2));
						qYZXCJFKBW.setStrCCYY(subStringByByte(line,59,20));
						CBYQ=subStringByByte(line,79,1);
						qYZXCJFKBW.setStrCBYQ(CBYQ);
						
						fkbwTypeSet.add(qYZXCJFKBW.getStrBWLX());
						
						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{qYZXCJFKBW,null});
					}
					else if(line.startsWith("Z")){
					  strFKCWJLTS += Integer.parseInt(line.substring(1, line.length()));
						if(qYZXCJFKBWSubList.size() > 0){
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{qYZXCJFKBWSubList,null});
							qYZXCJFKBWSubList = new ArrayList<QYZXCJFKBWSub>();
						}
					}
					else{
						QYZXCJFKBWSub qYZXCJFKBWSub = new QYZXCJFKBWSub();
						
						qYZXCJFKBWSub.setQYZXCJFKBW(qYZXCJFKBW);
						qYZXCJFKBWSub.setStrXXJLCD(subStringByByte(line,0,4));
						qYZXCJFKBWSub.setStrXXJLGZBH(subStringByByte(line,4,20));
						qYZXCJFKBWSub.setStrJRJGDM(subStringByByte(line,24,11));
						String strYWFSRQ=subStringByByte(line,35,8);
						qYZXCJFKBWSub.setStrYWFSRQ(strYWFSRQ);
						qYZXCJFKBWSub.setStrXXJLCZLX(subStringByByte(line,43,1));
						qYZXCJFKBWSub.setStrCCJLWZ(subStringByByte(line,44,10));
						String strCCXXCD = subStringByByte(line,54,4);
						qYZXCJFKBWSub.setStrCCXXCD(strCCXXCD);
						int intCCXX = 58 + Integer.parseInt(strCCXXCD);
						qYZXCJFKBWSub.setStrCCXX(subStringByByte(line,58,intCCXX-58));
						
						String strCCXXJL =subStringByByte(line,intCCXX,line.getBytes("GBK").length-intCCXX);
						
						String strTable = ShowContext.getInstance().getShowEntityMap().get("BWLXDTO").get(qYZXCJFKBW.getStrBWLX());
						
						String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(strTable);
						
						String strData = subStringByByte(strCCXXJL,7,strCCXXJL.getBytes("GBK").length-7);
						
						String strXXJLLX=subStringByByte(strCCXXJL,4,2);
						
						Map<String,String> fieldValue = new HashMap<String,String>();
						Map<String,String> dtoLengthMap = ShowContext.getInstance().getShowEntityMap().get(strTable);
						if(strTable.equals("zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC")){
							dtoLengthMap.put("XXJLLX", "2");
						}
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
									if(strTable.equals("zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC") && entry.getKey().equals("XXJLLX")){
										fieldValue.put(entry.getKey(), strXXJLLX);
									}else{
										String strValue=subStringByByte(strData,beginLen,Integer.parseInt(entry.getValue())).trim();
										fieldValue.put(entry.getKey(), strValue);
									}
									
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
						String primarykeyMXTemp="";
						if(objectList.size() == 1){
							strId = ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString();
							qYZXCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
							qYZXCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
							
							String reportData =subStringByByte(strCCXXJL,6,strCCXXJL.getBytes("GBK").length-6);
							String dtoName = objectList.get(0).getClass().getName();
							
							String entryValue = reportMap.get(strXXJLLX);

						    String[] entryValues = entryValue.split(",");
						    String tempReportData = subStringByByte(reportData,1,reportData.getBytes("GBK").length-1);
						    Map<String,String> reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[0].split("%")[1]);
				    		for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
				    			String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
				    			if(strValue==null){
				    				strValue="";
				    			}
				    			tempReportData = subStringByByte(tempReportData,strValue.getBytes("GBK").length,(tempReportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length)); 
				    		}
				    		if(reportData.startsWith("C")){
				    			reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[1].split("%")[1]);
				    			for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
				    				String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
				    				if(strValue==null){
					    				strValue="";
					    			}
				    				tempReportData = subStringByByte(tempReportData,strValue.getBytes("GBK").length,(tempReportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));
					    		}
				    		}
				    		String reportFlag =subStringByByte(tempReportData,0,1)+ "%";
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
						    			if(strTable.equals("zxptsystem.dto.AutoDTO_QY_JKRCWBB_JC") && entry.getKey().equals("XXJLLX")){
						    				continue;
						    			}
						    			String strValue = subStringByByte(reportData,Integer.parseInt(entry.getValue()));
						    			if(strValue==null){
						    				strValue="";
						    			}
						    			reportData = subStringByByte(reportData,strValue.getBytes("GBK").length,(reportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));   
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
											primarykeyMXTemp=primarykeyMX;
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
															if(ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field").containsKey(reportObject.getClass().getName())){
																String ntypeValue = ShowContext.getInstance().getShowEntityMap().get("N_TYPE_Field").get(reportObject.getClass().getName());
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
													//判断明细段的业务发生日期是否与本条基础段的业务发生日期相同
													if(ReflectOperation.getFieldValue(object, "extend2")!=null && ReflectOperation.getFieldValue(object, "extend2").equals(strYWFSRQ)){
														ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
														ReflectOperation.setFieldValue(object, "extend3", subStringByByte(line,4,20));
														errorPrimarykeyMXSet.add(primarykeyMX);
														//回执失败的明细，联动改变明细段报送状态和校验状态
														ReflectOperation.setFieldValue(object, "RPTSendType", "1");
														ReflectOperation.setFieldValue(object, "RPTCheckType", "1");
														
													  }
													
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
							qYZXCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
						}

						qYZXCJFKBWSubList.add(qYZXCJFKBWSub);
						
						/**插入企业征信报送流转明细表*/
						GenerAndAnaInsertService insertData=new GenerAndAnaInsertService();
						List<E_RPT> e_RPTList=insertData.GetE_RPTByBWM(FileName);
						if(e_RPTList.size()>0){
							String strBWSCSJ=e_RPTList.get(0).getStrBWSCSJ();
							String strBWWJZL=e_RPTList.get(0).getStrBWWJZL();
							String strBWWJSJLX=e_RPTList.get(0).getStrBWWJSJLX();
							
							insertData.InsertE_RPT_DETAIL(primarykeyMXTemp, FileName, strBWSCSJ, strBWWJZL, strBWWJSJLX, outputFileName, reportDate, strCCXXJL);
						}
							
					}//报文体结束
				}
			}

			List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
			String JRJGDM=FileName.substring(2, 13);
			List<List<Object>> objectALLXXJLLXList=new ArrayList<List<Object>>();
			for(String strBWLX : fkbwTypeSet){
				String reportMapKey = downLoadRportService.getXxjllxMap().get(strBWLX);
				String[] reportMapKeys = reportMapKey.split(",");
				
				for(int i=0;i<reportMapKeys.length;i++){
					String reportDto = reportMap.get(reportMapKeys[i]);
					String[] reportDtos = reportDto.split(",");
					String baseDto = reportDtos[0].split("%")[1];
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
					List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					for (Object object : objectList) {
						//基础段：主键不在错误主键之内，为回执成功的报文
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
							//明细段：由于存在一条基础段数据对应多条报文，该条明细数据无错误反馈，为回执成功报文。
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

					for (Object object : objectList) {
						int unFeedbackTypeCount=0;
						int feedbackTypeFalseCount = 0;
						int feedbackTypeSuccessCount = 0;
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
					
					if(objectList.size()>0){
						IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
						
						new RejectReportService().GetSendedRejectReportFeedback(objectList,FileName,"21"); //处理已报送报文驳回的回执状态

						//统计任务层状态
						CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
						instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
						commonStatus.commonUpdateReportStatus(objectList, instInfoSubList);
						objectALLXXJLLXList.add(objectList);
					}
				}
			} 
			
			//统计反馈成功的删除报文
			HashSet h = new HashSet(objectALLXXJLLXList);  
			objectALLXXJLLXList.clear();  
			objectALLXXJLLXList.addAll(h); 
			for (List<Object> objectTempList : objectALLXXJLLXList) {
				UpdateFeedbackToDeletedService updateFeedbackToDeleted=new UpdateFeedbackToDeletedService();
				updateFeedbackToDeleted.UpdateFeedbackToDeleted(objectTempList,FileName);
			}
			
			/**更新企业征信报送流转概况表*/
			GenerAndAnaInsertService updateData=new GenerAndAnaInsertService();
			String strFKBWSFTSSBCW="是";//反馈报文是否提示上报错误
			if(!StringUtils.isBlank(CBYQ) && CBYQ.equals("1")){
				strFKBWSFTSSBCW="否";
			}
			updateData.UpdateE_RPT(FileName, "是", reportDate, outputFileName, strFKBWSFTSSBCW,strFKCWJLTS);
			
			return new MessageResult("报文解析成功");
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			if(StringUtils.isBlank(ex.getMessage())){
				return new MessageResult(false,"报文解析文件异常");
			}
			else{
				return new MessageResult(false,"报文解析文件异常:" + ex.getMessage());
			}
		}
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

	private static final int BUFFER = 2048;
}
