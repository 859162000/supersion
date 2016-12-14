package dbgssystem.service.imps;

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

import com.opensymphony.xwork2.ActionContext;

import report.service.imps.CommonUpdateReportStatusService;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import dbgssystem.dto.AutoDTO_DB_DBXX_JC;
import dbgssystem.dto.DBGSCJFKBW;
import dbgssystem.dto.DBGSCJFKBWSub;
import dbgssystem.dto.AutoDTO_DB_DBGSSCQQJL;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

/**
 * 担保业务采集反馈报文
 * @author xiajieli
 *
 */
public class DBGSCJFKBWUploadFileService implements IObjectResultExecute{

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
			DBGSCJFKBW dBGSCJFKBW = null;
			List<DBGSCJFKBWSub> dBGSCJFKBWSubList = new ArrayList<DBGSCJFKBWSub>();
			
			Set<String> errorPrimarykeySet=new HashSet<String>();
			Set<String> errorPrimarykeyMXSet=new HashSet<String>();
			Set<String> fkbwTypeSet=new HashSet<String>();
			DownLoadDBGSRportService downLoadRportService = (DownLoadDBGSRportService)FrameworkFactory.CreateBean("downLoadDBGSReportService");
			Map<String, String> reportMap = downLoadRportService.getReportMap();
			boolean isSuccess=true;
			
			while((line=bufferedReader.readLine())!=null) { 
				if(!StringUtils.isBlank(line)){
					if(line.startsWith("A")){
						dBGSCJFKBW = new DBGSCJFKBW();
						dBGSCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
						if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
							dBGSCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
						}
						dBGSCJFKBW.setStrVersionCode(line.substring(1,4));
						dBGSCJFKBW.setStrBWSCSJ(line.substring(4,18));
						dBGSCJFKBW.setStrBWLX(line.substring(18,20));
						dBGSCJFKBW.setStrCCBWM(line.substring(20,48));
						
						fkbwTypeSet.add(dBGSCJFKBW.getStrBWLX());
						
						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{dBGSCJFKBW,null});
					}
					else if(line.startsWith("Z")){
						if(dBGSCJFKBWSubList.size() > 0){
							dBGSCJFKBW.setStrXXJLS(line.substring(1,11));
							
							IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
							singleObjectUpdateDao.paramVoidResultExecute(new Object[]{dBGSCJFKBW,null});
							
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{dBGSCJFKBWSubList,null});
							dBGSCJFKBWSubList = new ArrayList<DBGSCJFKBWSub>();
						}
					}
					else{
						isSuccess=false;
						DBGSCJFKBWSub dBGSCJFKBWSub = new DBGSCJFKBWSub();
						
						dBGSCJFKBWSub.setDBGSCJFKBW(dBGSCJFKBW);
						dBGSCJFKBWSub.setStrDBJGDM(line.substring(0,14));
						String strYWFSRQ=line.substring(14,22);
						dBGSCJFKBWSub.setStrSJBGRQ(strYWFSRQ);
						dBGSCJFKBWSub.setStrCCJLWZ(line.substring(22,32));
						dBGSCJFKBWSub.setStrCCXXCD(line.substring(32,36));
		
						String strCCXXCD = line.substring(32,36);
						dBGSCJFKBWSub.setStrCCXXCD(strCCXXCD);
						int intCCXX = 36 + Integer.parseInt(strCCXXCD);
						dBGSCJFKBWSub.setStrCCXX(line.substring(36,intCCXX));
						
						String strCCXXJL = line.substring(intCCXX);
						
						String strTable = ShowContext.getInstance().getShowEntityMap().get("DBGSBWLXDTO").get(dBGSCJFKBW.getStrBWLX());
						
						String primaryKey = ShowContext.getInstance().getShowEntityMap().get("DBGSSystemLogicPrimaryKey").get(strTable);
						
						String strData = strCCXXJL.substring(7);
						for (String strBWLX : fkbwTypeSet) {
							String reportDto = reportMap.get(strBWLX);
							String[] reportDtos = reportDto.split(",");
							String baseDto = reportDtos[0].split("%")[1];
							
							IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
							
							List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							if(strBWLX.equals("81")){
								Map<String,String> fieldValue = new HashMap<String,String>();
								Map<String,String> dtoLengthMap = ShowContext.getInstance().getShowEntityMap().get(strTable);
								String[] primaryKeys = primaryKey.split(",");
								
								for(int i=0;i<primaryKeys.length;i++){
									int length = 0;
									for(Map.Entry<String,String> entry : dtoLengthMap.entrySet()){
										if(primaryKeys[i].equals(entry.getKey())){
											int beginLen = 0;
											if(length>0){
												beginLen = subStringByByte(strData, length).length();
											}else{
												beginLen = length;
											}
											String strValue = strData.substring(beginLen,beginLen + Integer.parseInt(entry.getValue())).trim();
											String strValueTemp=subStringByByte(strValue, (beginLen + Integer.parseInt(entry.getValue()))-beginLen);
											fieldValue.put(entry.getKey(), strValueTemp.trim());
											break;
										}
										length += Integer.parseInt(entry.getValue());
									}
								}
								
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(Class.forName(strTable));
								for(Map.Entry<String,String> entry : fieldValue.entrySet()){
									detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue().trim()));
								}
								objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								String strId = "";
								if(objectList.size() == 1){
									strId = ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString();
									dBGSCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
									dBGSCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
									
									//新增,定位明细
									String reportData = strCCXXJL.substring(6);
									String dtoName = objectList.get(0).getClass().getName();
									String entryValue = "";
								    for(Map.Entry<String, String> entry : reportMap.entrySet()){
								    	if(entry.getValue().contains(dtoName)){
								    		entryValue = entry.getValue();
								    		break;
								    	}
								    }
								    String[] entryValues = entryValue.split(",");
								    String tempReportData = reportData.substring(1);
								    Map<String,String> reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[0].split("%")[1]);
						    		for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
						    			String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
						    			tempReportData = tempReportData.substring(strValue.length());
						    		}
						    		if(reportData.startsWith("D")){
						    			reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[1].split("%")[1]);
						    			for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
						    				String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
							    			tempReportData = tempReportData.substring(strValue.length());
							    		}
						    		}
						    		String reportFlag = tempReportData.substring(0,1) + "%";
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
								    		reportData = reportData.substring(1);

								    		Map<String,String> reportLengthMap = ShowContext.getInstance().getShowEntityMap().get(mxDtos[1]);
								    		Object reportObject= Class.forName(mxDtos[1]).newInstance();
								    		
								    		for(Map.Entry<String,String> entry : reportLengthMap.entrySet()){
								    			String strValue = subStringByByte(reportData,Integer.parseInt(entry.getValue()));
								    			reportData = reportData.substring(strValue.length());
								    			ReflectOperation.setFieldValue(reportObject, entry.getKey(), strValue.trim());

								    		}
								    		reportObjectList.add(reportObject);
								    	}
								    	if(reportData.trim().equals("")){
								    		break;
								    	}
								    	if(reportData.startsWith(mxDtos[0])){
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
																	if(ShowContext.getInstance().getShowEntityMap().get("DBGS_N_TYPE_Field").containsKey(reportObject.getClass().getName())){
																		String ntypeValue = ShowContext.getInstance().getShowEntityMap().get("DBGS_N_TYPE_Field").get(reportObject.getClass().getName());
																		String[] ntypeValues = ntypeValue.split(",");
																		for(int k=0;k<ntypeValues.length;k++){
																			if(ntypeValues[k].equals(entry.getKey())){
																				isNType = true;
																				break;
																			}
																		}
																	}
																	if(isNType){
																		int currentLenth = objectFieldValue.length();
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
																errorPrimarykeyMXSet.add(primarykeyMX);
																//回执失败的明细，联动改变明细段报送状态
																ReflectOperation.setFieldValue(object, "RPTSendType", "1");
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
									dBGSCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
								}
								
								dBGSCJFKBWSubList.add(dBGSCJFKBWSub);
							}else if(strBWLX.equals("83") || strBWLX.equals("82")){
								for (Object object : objectList) {
									ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
									ReflectOperation.setFieldValue(object, "RPTSendType", "1");
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
							}
						}
					}
				}
			}
			//如果报文是成功的报文，统计状态
			if(isSuccess){
				List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
				String JRJGDM=FileName.substring(4, 18);
				
				for(String strBWLX : fkbwTypeSet){
					String reportDto = reportMap.get(strBWLX);
					String[] reportDtos = reportDto.split(",");
					String baseDto = reportDtos[0].split("%")[1];
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
					
					List<Object> successObjectList =new ArrayList<Object>();
					List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					//正常采集报文
					if(strBWLX.equals("81")){
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
							int feedbackTypeFalseCount = 0;
							int feedbackTypeSuccessCount = 0;
							List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
							for (Field field : fieldList) {
								Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
								for (Object objectMX : fieldObjectList) {
									if(!ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("2")){
										feedbackTypeFalseCount ++;
									}else{
										feedbackTypeSuccessCount ++;
									}
								}
							}
							
							if(feedbackTypeFalseCount==0 && feedbackTypeSuccessCount==0){
								ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
							}
							else if(feedbackTypeFalseCount>0 && feedbackTypeSuccessCount==0){
								ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
							}else if(feedbackTypeFalseCount==0 && feedbackTypeSuccessCount>0){
								ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
							}else{
								ReflectOperation.setFieldValue(object, "RPTFeedbackType", "4");
							}
						}
						
						IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});

						//统计采集报文任务层状态
						CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
						instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
						commonStatus.commonUpdateReportStatus(objectList, instInfoSubList);
						
					}//标识变更报文
					else if(strBWLX.equals("82")){
						for (Object object : objectList) {
							ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
							successObjectList.add(object);
						}
						
					}//删除报文
					else if(strBWLX.equals("83")){
						for (Object object : objectList) {
							ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
							successObjectList.add(object);
						}
						
						List<AutoDTO_DB_DBGSSCQQJL> dBGSSCQQJLList = (List<AutoDTO_DB_DBGSSCQQJL>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						for (AutoDTO_DB_DBGSSCQQJL dBGSSCQQJL : dBGSSCQQJLList) {
							if(dBGSSCQQJL.getSCLX().equals("1")){//1-整笔业务删除
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBXX_JC.class);
								detachedCriteria.add(Restrictions.eq("DBJGDM", dBGSSCQQJL.getDBJGDM()));
								detachedCriteria.add(Restrictions.eq("DBYWBH", dBGSSCQQJL.getDBYWBH()));
								List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								for (Object object_JC : Object_JCList) {
									ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType", "5");
									List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
									for (Field field : listObjectMx) {
										detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
										detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
										List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										for (Object object : objectMXList) {
											ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
								
								//统计采集报文任务层状态
								CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
								instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
								commonStatus.commonUpdateReportStatus(Object_JCList, instInfoSubList);
								
							}else if(dBGSSCQQJL.getSCLX().equals("2")){//2-在保（代偿）责任信息删除
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBXX_JC.class);
								detachedCriteria.add(Restrictions.eq("DBJGDM", dBGSSCQQJL.getDBJGDM()));
								detachedCriteria.add(Restrictions.eq("DBYWBH", dBGSSCQQJL.getDBYWBH()));
								List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								for (Object object_JC : Object_JCList) {
									List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
									for (Field field : listObjectMx) {
										if(field.getName().equals("OneToMany_DB_SJZBZRXX")){
											detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
											detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
											detachedCriteria.add(Restrictions.gt("extend2", dBGSSCQQJL.getSJBGRQ()));
											List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
											for (Object object : objectMXList) {
												ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
											}
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
								
								//统计采集报文任务层状态
								CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
								instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
								commonStatus.commonUpdateReportStatus(Object_JCList, instInfoSubList);
								
							}else if(dBGSSCQQJL.getSCLX().equals("3")){//3-代偿信息删除
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBXX_JC.class);
								detachedCriteria.add(Restrictions.eq("DBJGDM", dBGSSCQQJL.getDBJGDM()));
								detachedCriteria.add(Restrictions.eq("DBYWBH", dBGSSCQQJL.getDBYWBH()));
								List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								for (Object object_JC : Object_JCList) {
									List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
									for (Field field : listObjectMx) {
										if(field.getName().equals("OneToMany_DB_DCGKXX") || field.getName().equals("OneToMany_DB_DCMXXX")){
											detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
											detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
											detachedCriteria.add(Restrictions.gt("extend2", dBGSSCQQJL.getSJBGRQ()));
											List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
											for (Object object : objectMXList) {
												ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
											}
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
								
								//统计采集报文任务层状态
								CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
								instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
								commonStatus.commonUpdateReportStatus(Object_JCList, instInfoSubList);
								
							}else if(dBGSSCQQJL.getSCLX().equals("4")){//4-保费缴纳信息删除
								singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
								detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBXX_JC.class);
								detachedCriteria.add(Restrictions.eq("DBJGDM", dBGSSCQQJL.getDBJGDM()));
								detachedCriteria.add(Restrictions.eq("DBYWBH", dBGSSCQQJL.getDBYWBH()));
								List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								for (Object object_JC : Object_JCList) {
									List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
									for (Field field : listObjectMx) {
										if(field.getName().equals("OneToMany_DB_BFJNGKXX") || field.getName().equals("OneToMany_DB_BFJNMXXX")){
											detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
											detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
											detachedCriteria.add(Restrictions.gt("extend2", dBGSSCQQJL.getSJBGRQ()));
											List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
											for (Object object : objectMXList) {
												ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
											}
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
								
								//统计采集报文任务层状态
								CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
								instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
								commonStatus.commonUpdateReportStatus(Object_JCList, instInfoSubList);
							}
						}
					}
					IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
					singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{successObjectList,null});
				}
			}
			
			return new MessageResult("报文解析成功");
		}
		catch(Exception ex){
			if(StringUtils.isBlank(ex.getMessage())){
				return new MessageResult(false,"报文解析文件异常");
			}
			else{
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
	
	private static final int BUFFER = 2048;
	
	public void setStatusMap(Map<String,String> statusMap) {
		this.statusMap = statusMap;
	}

	public Map<String,String> getStatusMap() {
		return statusMap;
	}
}
