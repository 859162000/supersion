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

import com.opensymphony.xwork2.ActionContext;

import report.service.imps.CommonUpdateReportStatusService;

import zxptsystem.dto.AutoDTO_JG_JGJBXX_JC;
import zxptsystem.dto.E_RPT;
import zxptsystem.dto.JGXXCJFKBW;
import zxptsystem.dto.JGXXCJFKBWSub;
import zxptsystem.dto.AutoDTO_JGXXJBXXSC;
import zxptsystem.dto.AutoDTO_JGXXJZCYXXSC;
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
 * 机构信息采集反馈报文
 * @author Transino
 *
 */
public class JGXXCJFKBWUploadFileService implements IObjectResultExecute{
	
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
			JGXXCJFKBW jGXXCJFKBW = new JGXXCJFKBW();
			List<JGXXCJFKBWSub> jGXXCJFKBWSubList = new ArrayList<JGXXCJFKBWSub>();
			int row = 0;
			Set<String> errorPrimarykeySet=new HashSet<String>();
			
			String strCCYYDM=null;
			String strCCXXJL=null;
			String strJRJGCode=null;
			String strFKBWLX=FileName.substring(28,30);
			String[] fkbwTypeSet=null;
			if(strFKBWLX.equals("51")){
				fkbwTypeSet=new String[]{"5100","5101"};
			}else if(strFKBWLX.equals("32")){
				fkbwTypeSet=new String[]{"3200","3201"};
			}
			
			List<Object> JGXXJBXXSCNoDeleteList = new ArrayList<Object>();
			List<Object> JGXXJZCYXXSCNoDeleteList = new ArrayList<Object>();
			DownLoadJGXXRportService downLoadJGXXRportService = (DownLoadJGXXRportService)FrameworkFactory.CreateBean("downLoadJGXXReportService");
			Map<String, String> reportMap = downLoadJGXXRportService.getReportMap();
			Set<String> errorPrimarykeyMXSet=new HashSet<String>();

			CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
			List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
			String JRJGDM=FileName.substring(11, 22);
			instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
			
			int strFKCWJLTS=0;//反馈错误记录条数
			String reportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //反馈报文导入时间
			while((line=bufferedReader.readLine())!=null) {
				row++;
				if(!StringUtils.isBlank(line)){					
					if(line.startsWith("A")){
						jGXXCJFKBW = new JGXXCJFKBW();
						jGXXCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
						if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
							jGXXCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
						}
						jGXXCJFKBW.setStrVersionCode(subStringByByte(line,1,3));
						strJRJGCode=subStringByByte(line,4,20);
						jGXXCJFKBW.setStrJRJGCode(strJRJGCode);
						jGXXCJFKBW.setStrBWSCSJ(subStringByByte(line,24,14));
						jGXXCJFKBW.setStrBWCCWJM(subStringByByte(line,38,37));
						strCCYYDM=subStringByByte(line,75,2);
						jGXXCJFKBW.setStrCCYYDM(strCCYYDM);
						jGXXCJFKBW.setStrLXR(subStringByByte(line,77,30));
						jGXXCJFKBW.setStrLXDH(subStringByByte(line,107,25));
						jGXXCJFKBW.setStrYLZD(subStringByByte(line,132,30));
						//报文类型根据报文名得到
						jGXXCJFKBW.setStrBWLX(FileName.substring(28,30));

						IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
						singleObjectSaveDao.paramVoidResultExecute(new Object[]{jGXXCJFKBW,null});
					}else if(line.startsWith("Z")){
						strFKCWJLTS += Integer.parseInt(line.substring(1, line.length()));
						if(jGXXCJFKBWSubList.size() > 0){
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{jGXXCJFKBWSubList,null});
							jGXXCJFKBWSubList = new ArrayList<JGXXCJFKBWSub>();
						}else{
							//报文体为空，证明请求删除报文成功
							IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
							DetachedCriteria detachedCriteria=null;
							List<Object> Object_JCList = null;
							
							if((strCCYYDM.equals("") || StringUtils.isBlank(strCCYYDM))){
								//机构基本信息删除报文
									detachedCriteria = DetachedCriteria.forClass(AutoDTO_JGXXJBXXSC.class);
									detachedCriteria.add(Restrictions.eq("extend1", FileName));
									List<AutoDTO_JGXXJBXXSC> jGXXJBXXSCList = (List<AutoDTO_JGXXJBXXSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (AutoDTO_JGXXJBXXSC jgxxjbxxsc : jGXXJBXXSCList) {
										String strKHH=jgxxjbxxsc.getStrKHH();
										String strXSCDXXLB=jgxxjbxxsc.getStrXSCDXXLB();
										String strGXRLX=jgxxjbxxsc.getStrGXRLX();
										String strXXGXRQ=jgxxjbxxsc.getStrXXGXRQ();
										
										String strTableNameByType = ShowContext.getInstance().getShowEntityMap().get("JGXXDTOType").get(strXSCDXXLB);
										
										detachedCriteria = DetachedCriteria.forClass(Class.forName(ShowContext.getInstance().getShowEntityMap().get("JGXXDTOType").get("B")));
										detachedCriteria.add(Restrictions.eq("KHH", strKHH));
										Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										
										for (Object object_JC : Object_JCList) {
											if(strXSCDXXLB.equals("B")){//基础段
												if(strXXGXRQ.equals("20120101")){
													ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType", "5");
												}
												List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
												for (Field field : listObjectMx) {
													detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
													detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
													if(!strXXGXRQ.equals("20120101")){
														detachedCriteria.add(Restrictions.eq("extend2", strXXGXRQ));
													}
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													for (Object object : objectMXList) {
														ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
													}
												}
											}else if(strXSCDXXLB.equals("F")){//高管及主要关系人段
												detachedCriteria = DetachedCriteria.forClass(Class.forName(ShowContext.getInstance().getShowEntityMap().get("JGXXDTOType").get("F")));
												detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
												detachedCriteria.add(Restrictions.eq("GGXXGXRQ", strXXGXRQ));
												if(strGXRLX.equals("") || StringUtils.isBlank(strGXRLX)){
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													for (Object object : objectMXList) {
														ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
													}
												}else{
													detachedCriteria.add(Restrictions.eq("GGZWLX", strGXRLX));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													for (Object object : objectMXList) {
														ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
													}
												}
											}else {//其它段
												detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableNameByType));
												detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
												
												if(strXSCDXXLB.equals("C") || strXSCDXXLB.equals("D")){
													detachedCriteria.add(Restrictions.eq("QTXXGXRQ", strXXGXRQ));
												}else if(strXSCDXXLB.equals("E")){
													detachedCriteria.add(Restrictions.eq("LLXXGXRQ", strXXGXRQ));
												}else if(strXSCDXXLB.equals("F")){
													detachedCriteria.add(Restrictions.eq("GGXXGXRQ", strXXGXRQ));
												}else if(strXSCDXXLB.equals("G")){
													detachedCriteria.add(Restrictions.eq("GDXXGXRQ", strXXGXRQ));
												}else if(strXSCDXXLB.equals("H")){
													detachedCriteria.add(Restrictions.eq("XXGXRQ", strXXGXRQ));
												}else if(strXSCDXXLB.equals("I")){
													detachedCriteria.add(Restrictions.eq("SJJGXXGXRQ", strXXGXRQ));
												}
												
												List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
												for (Object object : objectMXList) {
													ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
												}
											}
										}
										IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
										singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
										
										//统计机构基本信息删除涉及的基础段的状态
										commonStatus.commonUpdateReportStatus(Object_JCList,instInfoSubList);

										ReflectOperation.setFieldValue(jgxxjbxxsc, "RPTFeedbackType", "2");
									}
									IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
									singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{jGXXJBXXSCList,null});
									
									//统计机构基本信息删除成功的报文的状态
									commonStatus.commonUpdateReportStatus(jGXXJBXXSCList,instInfoSubList);

									detachedCriteria = DetachedCriteria.forClass(AutoDTO_JGXXJZCYXXSC.class);
									detachedCriteria.add(Restrictions.eq("extend1", FileName));
									List<AutoDTO_JGXXJZCYXXSC> jGXXJZCYXXSCList = (List<AutoDTO_JGXXJZCYXXSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (AutoDTO_JGXXJZCYXXSC jgxxjzcyxxsc : jGXXJZCYXXSCList) {
										detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC"));
										detachedCriteria.add(Restrictions.eq("ZYGXRZJLX", jgxxjzcyxxsc.getStrZYGXRZJLX()));
										detachedCriteria.add(Restrictions.eq("ZJHM_1", jgxxjzcyxxsc.getStrZYGXRZJHM()));
										detachedCriteria.add(Restrictions.eq("JZGX", jgxxjzcyxxsc.getStrJZGX()));
										detachedCriteria.add(Restrictions.eq("JZCYZJLX", jgxxjzcyxxsc.getStrJZCYZJLX()));
										detachedCriteria.add(Restrictions.eq("ZJHM_2", jgxxjzcyxxsc.getStrJZCYZJHM()));
										detachedCriteria.add(Restrictions.eq("XXGXRQ", jgxxjzcyxxsc.getStrXXGXRQ()));
										
										Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										for (Object object_JC : Object_JCList) {
											ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType", "5");
										}
										ReflectOperation.setFieldValue(jgxxjzcyxxsc, "RPTFeedbackType", "2");
										
										singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
										singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
										
										//统计家族成员信息删除涉及的基础段的状态
										commonStatus.commonUpdateReportStatus(Object_JCList,instInfoSubList);
									}
								singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{jGXXJZCYXXSCList,null});
								
								//统计机构家族成员信息删除成功的报文的任务层的状态
								commonStatus.commonUpdateReportStatus(jGXXJZCYXXSCList,instInfoSubList);
							}else{
								Object[] objectList=new Object[]{AutoDTO_JGXXJBXXSC.class,AutoDTO_JGXXJZCYXXSC.class};
								for (Object objectSC : objectList) {
									detachedCriteria = DetachedCriteria.forClass(objectSC.getClass());
									detachedCriteria.add(Restrictions.eq("extend1", FileName));
									List<Object> objectSCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (Object obj : objectSCList) {
										ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "3");
									}
									IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
									singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectSCList,null});
									//统计机构和家族删除失败的报文的任务层的状态
									commonStatus.commonUpdateReportStatus(objectSCList,instInfoSubList);
								}
							}
						}
					}
					else{
						//报文体
						JGXXCJFKBWSub jGXXCJFKBWSub = new JGXXCJFKBWSub();
						jGXXCJFKBWSub.setJGXXCJFKBW(jGXXCJFKBW);
						jGXXCJFKBWSub.setStrCCJLWZ(subStringByByte(line,0,10));
						String strTempCCXX=subStringByByte(line,10,line.split("B")[0].getBytes("GBK").length-10);
						jGXXCJFKBWSub.setStrCCXX(strTempCCXX);
						strCCXXJL =subStringByByte(line,line.indexOf("B"),(line.getBytes("GBK").length)-(line.indexOf("B")));
							
						String strTable=null;
						if(strFKBWLX.equals("32")){
							if(strCCXXJL.getBytes("GBK").length==91){
								strTable="zxptsystem.dto.AutoDTO_JGXXJBXXSC";
							}else if(strCCXXJL.getBytes("GBK").length==94){
								strTable="zxptsystem.dto.AutoDTO_JGXXJZCYXXSC";
							}
						}else if(strFKBWLX.equals("51")){
							if(strCCXXJL.getBytes("GBK").length==254){
								strTable="zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC";
							}else{
								strTable="zxptsystem.dto.AutoDTO_JG_JGJBXX_JC";
							}
						}
							
						String primaryKey = ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey").get(strTable);
						String strData =subStringByByte(strCCXXJL,1,strCCXXJL.getBytes("GBK").length-1);     
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
						if(strFKBWLX.equals("32")){
							if((strCCYYDM.equals("") || StringUtils.isBlank(strCCYYDM))){
								detachedCriteria.add(Restrictions.eq("extend1", FileName));
							}
						}else if(strFKBWLX.equals("51")){
							if(strCCXXJL.getBytes("GBK").length==254){
								detachedCriteria.add(Restrictions.eq("extend1", FileName));
							}
						}
						for(Map.Entry<String,String> entry : fieldValue.entrySet()){
							detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
						}
						List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						String strId = "";
						
						String primarykeyMXTemp="";
						if(objectList.size() == 1){
							if(strFKBWLX.equals("32")){
								if(strCCXXJL.getBytes("GBK").length==91){
									JGXXJBXXSCNoDeleteList.addAll(objectList);
								}else if(strCCXXJL.getBytes("GBK").length==94){
									JGXXJZCYXXSCNoDeleteList.addAll(objectList);
								}
							}
							strId = ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString();
							jGXXCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
							jGXXCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
							
							String dtoName = objectList.get(0).getClass().getName();
							if(dtoName.equals("zxptsystem.dto.AutoDTO_JG_JGJBXX_JC")){
								String reportData = strCCXXJL;
								String entryValue = "";
							    for(Map.Entry<String, String> entry : reportMap.entrySet()){
							    	if(entry.getValue().contains(dtoName)){
							    		entryValue = entry.getValue();
							    		break;
							    	}
							    }
							    String[] entryValues = entryValue.split(",");
							    String tempReportData =subStringByByte(reportData,1,reportData.getBytes("GBK").length-1);    
							    Map<String,String> reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[0].split("%")[1]);
					    		for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
					    			String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
					    			if(strValue==null){
					    				strValue="";
					    			}
					    			tempReportData =subStringByByte(tempReportData,strValue.getBytes("GBK").length,(tempReportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));   
					    		}
					    		if(reportData.startsWith("C")){
					    			reportLengthBMap = ShowContext.getInstance().getShowEntityMap().get(entryValues[1].split("%")[1]);
					    			for(Map.Entry<String,String> entry : reportLengthBMap.entrySet()){
					    				String strValue = subStringByByte(tempReportData,Integer.parseInt(entry.getValue()));
					    				if(strValue==null){
						    				strValue="";
						    			}
					    				tempReportData =subStringByByte(tempReportData,strValue.getBytes("GBK").length,(tempReportData.getBytes("GBK").length)-(strValue.getBytes("GBK").length));  
						    		}
					    		}
					    		if(tempReportData!=null && !tempReportData.equals("")){
					    			String reportFlag =subStringByByte(tempReportData,0,1)+ "%";
						    		for(Map.Entry<String, String> entry : reportMap.entrySet()){
									    if(entry.getValue().contains(dtoName) && entry.getValue().contains(reportFlag)){
									    	entryValue = entry.getValue();
									    	break;
									    }
									}
					    		}
					    		entryValues = entryValue.split(",");

							    //构造反馈报文对象，不区分类型，全放reportObjectList
							    List<Object> reportObjectList = new ArrayList<Object>();

							    for(int i=0;i<entryValues.length;i++){
							    	String mxDto = entryValues[i];
							    	String[] mxDtos = mxDto.split("%");
							    	if(reportData.startsWith(mxDtos[0])){
							    		reportData = subStringByByte(reportData,1,reportData.getBytes("GBK").length-1);  

							    		Map<String,String> reportLengthMap = ShowContext.getInstance().getShowEntityMap().get(mxDtos[1]);
							    		Object reportObject= Class.forName(mxDtos[1]).newInstance();
							    		
							    		for(Map.Entry<String,String> entry : reportLengthMap.entrySet()){
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
														ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
														ReflectOperation.setFieldValue(object, "extend3",subStringByByte(line, 4, 20));
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
							}
							//回执失败，联动改变基础段的其它状态	
							if(statusMap!=null){
								for (Map.Entry<String, String>  entry: statusMap.entrySet()) {
									if(entry.getKey().equals("RPTCheckType")){
										if(!objectList.get(0).getClass().getName().equals("zxptsystem.dto.AutoDTO_JG_JGJBXX_JC")){
											ReflectOperation.setFieldValue(objectList.get(0), entry.getKey(), entry.getValue());
										}
									}else{
										ReflectOperation.setFieldValue(objectList.get(0), entry.getKey(), entry.getValue());
									}
								}
							}
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
							errorPrimarykeySet.add(ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString());
						
						}else{
							jGXXCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
						}
						jGXXCJFKBWSubList.add(jGXXCJFKBWSub);
						
						
						/**插入企业征信报送流转明细表*/
						String entityName=objectList.get(0).getClass().getName();
						GenerAndAnaInsertService insertData=new GenerAndAnaInsertService();
						List<E_RPT> e_RPTList=insertData.GetE_RPTByBWM(FileName);
						if(e_RPTList.size()>0){
							String strBWSCSJ=e_RPTList.get(0).getStrBWSCSJ();
							String strBWWJZL=e_RPTList.get(0).getStrBWWJZL();
							String strBWWJSJLX=e_RPTList.get(0).getStrBWWJSJLX();
							
							if(entityName.equals("zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC") 
									|| entityName.equals("zxptsystem.dto.AutoDTO_JGXXJBXXSC") 
									|| entityName.equals("zxptsystem.dto.AutoDTO_JGXXJZCYXXSC")){
								insertData.InsertE_RPT_DETAIL(strId, FileName, strBWSCSJ, strBWWJZL, strBWWJSJLX, outputFileName, reportDate, strCCXXJL);
							}else{
								insertData.InsertE_RPT_DETAIL(primarykeyMXTemp, FileName, strBWSCSJ, strBWWJZL, strBWWJSJLX, outputFileName, reportDate, strCCXXJL);
							}
						}
						
					}//报文体结束
				}
			}
			
			if(strFKBWLX.equals("32")){
			    //删除反馈成功之后的数据
			    IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria=null;
				List<Object> Object_JCList = new ArrayList<Object>();
				
				if((strCCYYDM.equals("") || StringUtils.isBlank(strCCYYDM))){
					if(!StringUtils.isBlank(strJRJGCode)){
						instInfoSubList=commonStatus.getInstInfoSubList(strJRJGCode);
					}
					//机构基本信息删除报文
					detachedCriteria = DetachedCriteria.forClass(AutoDTO_JGXXJBXXSC.class);
					detachedCriteria.add(Restrictions.eq("extend1", FileName));
					List<AutoDTO_JGXXJBXXSC> jGXXJBXXSCList = (List<AutoDTO_JGXXJBXXSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(JGXXJBXXSCNoDeleteList.size()>0){
						jGXXJBXXSCList.removeAll(JGXXJBXXSCNoDeleteList);
					}
					
					for (AutoDTO_JGXXJBXXSC jgxxjbxxsc : jGXXJBXXSCList) {
						String strKHH=jgxxjbxxsc.getStrKHH();
						String strXSCDXXLB=jgxxjbxxsc.getStrXSCDXXLB();
						String strGXRLX=jgxxjbxxsc.getStrGXRLX();
						String strXXGXRQ=jgxxjbxxsc.getStrXXGXRQ();
						detachedCriteria = DetachedCriteria.forClass(Class.forName(ShowContext.getInstance().getShowEntityMap().get("JGXXDTOType").get("B")));
						detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
						detachedCriteria.add(Restrictions.eq("KHH", strKHH));
						Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						String strTableNameByType = ShowContext.getInstance().getShowEntityMap().get("JGXXDTOType").get(strXSCDXXLB);
						for (Object object_JC : Object_JCList) {
							if(strXSCDXXLB.equals("B")){//基础段
								if(strXXGXRQ.equals("20120101")){
									ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType", "5");
								}
								List<Field> listObjectMx=ReflectOperation.getOneToManyField(object_JC.getClass());
								for (Field field : listObjectMx) {
									detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
									if(!strXXGXRQ.equals("20120101")){
										detachedCriteria.add(Restrictions.eq("extend2", strXXGXRQ));
									}
									List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (Object object : objectMXList) {
										ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
									}
								}
							}else if(strXSCDXXLB.equals("F")){//高管及主要关系人段
								detachedCriteria = DetachedCriteria.forClass(Class.forName(ShowContext.getInstance().getShowEntityMap().get("JGXXDTOType").get("F")));
								detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
								detachedCriteria.add(Restrictions.eq("GGXXGXRQ", strXXGXRQ));
								if(strGXRLX.equals("") || StringUtils.isBlank(strGXRLX)){
									List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (Object object : objectMXList) {
										ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
									}
								}else{
									detachedCriteria.add(Restrictions.eq("GGZWLX", strGXRLX));
									List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									for (Object object : objectMXList) {
										ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
									}
								}
							}
							else{//其它段
								detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableNameByType));
								detachedCriteria.add(Restrictions.eq("FOREIGNID", object_JC));
								
								if(strXSCDXXLB.equals("C") || strXSCDXXLB.equals("D")){
									detachedCriteria.add(Restrictions.eq("QTXXGXRQ", strXXGXRQ));
								}else if(strXSCDXXLB.equals("E")){
									detachedCriteria.add(Restrictions.eq("LLXXGXRQ", strXXGXRQ));
								}else if(strXSCDXXLB.equals("F")){
									detachedCriteria.add(Restrictions.eq("GGXXGXRQ", strXXGXRQ));
								}else if(strXSCDXXLB.equals("G")){
									detachedCriteria.add(Restrictions.eq("GDXXGXRQ", strXXGXRQ));
								}else if(strXSCDXXLB.equals("H")){
									detachedCriteria.add(Restrictions.eq("XXGXRQ", strXXGXRQ));
								}else if(strXSCDXXLB.equals("I")){
									detachedCriteria.add(Restrictions.eq("SJJGXXGXRQ", strXXGXRQ));
								}
								
								List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								for (Object object : objectMXList) {
									ReflectOperation.setFieldValue(object, "RPTFeedbackType", "5");
								}
							}
						}
						ReflectOperation.setFieldValue(jgxxjbxxsc, "RPTFeedbackType", "2");
						if(Object_JCList.size()>0){
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
							
							//统计机构基本信息删除涉及的基础段的状态
							commonStatus.commonUpdateReportStatus(Object_JCList,instInfoSubList);
						}
					}
					IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{jGXXJBXXSCList,null});
					
					//统计机构基本信息删除成功的报文的状态
					commonStatus.commonUpdateReportStatus(jGXXJBXXSCList,instInfoSubList);

					//家族成员信息删除报文
					detachedCriteria = DetachedCriteria.forClass(AutoDTO_JGXXJZCYXXSC.class);
					detachedCriteria.add(Restrictions.eq("extend1", FileName));
					List<AutoDTO_JGXXJZCYXXSC> jGXXJZCYXXSCList = (List<AutoDTO_JGXXJZCYXXSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(JGXXJZCYXXSCNoDeleteList.size()>0){
						jGXXJZCYXXSCList.removeAll(JGXXJZCYXXSCNoDeleteList);
					}
					
					for (AutoDTO_JGXXJZCYXXSC jgxxjzcyxxsc : jGXXJZCYXXSCList) {
						detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC"));
						detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
						detachedCriteria.add(Restrictions.eq("ZYGXRZJLX", jgxxjzcyxxsc.getStrZYGXRZJLX()));
						detachedCriteria.add(Restrictions.eq("ZJHM_1", jgxxjzcyxxsc.getStrZYGXRZJHM()));
						detachedCriteria.add(Restrictions.eq("JZGX", jgxxjzcyxxsc.getStrJZGX()));
						detachedCriteria.add(Restrictions.eq("JZCYZJLX", jgxxjzcyxxsc.getStrJZCYZJLX()));
						detachedCriteria.add(Restrictions.eq("ZJHM_2", jgxxjzcyxxsc.getStrJZCYZJHM()));
						detachedCriteria.add(Restrictions.eq("XXGXRQ", jgxxjzcyxxsc.getStrXXGXRQ()));
						Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						for (Object object_JC : Object_JCList) {
							ReflectOperation.setFieldValue(object_JC, "RPTFeedbackType", "5");
						}
						singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
						
						//统计家族成员信息删除涉及的基础段的状态
						commonStatus.commonUpdateReportStatus(Object_JCList,instInfoSubList);
						
						ReflectOperation.setFieldValue(jgxxjzcyxxsc, "RPTFeedbackType", "2");
					}
					singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{jGXXJZCYXXSCList,null});
					
					//统计机构家族成员信息删除成功的报文的任务层的状态
					commonStatus.commonUpdateReportStatus(jGXXJZCYXXSCList,instInfoSubList);
				}else{
					Object[] objectList=new Object[]{AutoDTO_JGXXJBXXSC.class,AutoDTO_JGXXJZCYXXSC.class};
					for (Object objectSC : objectList) {
						detachedCriteria = DetachedCriteria.forClass(objectSC.getClass());
						detachedCriteria.add(Restrictions.eq("extend1", FileName));
						List<Object> objectSCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						for (Object obj : objectSCList) {
							ReflectOperation.setFieldValue(obj, "RPTFeedbackType", "3");
						}
						
						IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
						singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectSCList,null});
						
						//统计机构和家族删除失败的报文的任务层的状态
						commonStatus.commonUpdateReportStatus(objectSCList,instInfoSubList);
					}
				}
		   }
			
			for(String strBWLX : fkbwTypeSet){
				String reportDto = reportMap.get(strBWLX);
				String[] reportDtos = reportDto.split(",");
				String baseDto = reportDtos[0].split("%")[1];
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
				if(!baseDto.equals("zxptsystem.dto.AutoDTO_JG_JGJBXX_JC")){
					detachedCriteria.add(Restrictions.eq("extend1", FileName));
				}
				
				List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for (Object object : objectList) {
					int feedbackTypeFalseCount = 0;
					int feedbackTypeSuccessCount = 0;
					if(!errorPrimarykeySet.contains(ReflectOperation.getPrimaryKeyValue(object).toString())){
						List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
						for (Field field : fieldList) {
							Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
							for (Object objectMX : fieldObjectList) {
								if(ReflectOperation.getFieldValue(objectMX, "extend1")!=null && ReflectOperation.getFieldValue(objectMX, "extend1").equals(FileName)){
									ReflectOperation.setFieldValue(objectMX, "RPTFeedbackType", "2");
								}
								if(!ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("2")){
									feedbackTypeFalseCount ++;
								}else{
									feedbackTypeSuccessCount ++;
								}
							}
						}
					}else {//如果包含错误主键
						List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
						for (Field field : fieldList) {
							Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
							for (Object objectMX : fieldObjectList) {
								if(ReflectOperation.getFieldValue(objectMX, "extend1")!=null && ReflectOperation.getFieldValue(objectMX, "extend1").equals(FileName)){
									//如果包含明细段错误主键
									if(errorPrimarykeyMXSet.contains(ReflectOperation.getPrimaryKeyValue(objectMX).toString())){
										ReflectOperation.setFieldValue(objectMX, "RPTFeedbackType", "3");
									}else{
										ReflectOperation.setFieldValue(objectMX, "RPTFeedbackType", "2");
									}
								}
								if(!ReflectOperation.getFieldValue(objectMX, "RPTFeedbackType").equals("2")){
									feedbackTypeFalseCount ++;
								}else{
									feedbackTypeSuccessCount ++;
								}
							}
						}
					}
					if(feedbackTypeFalseCount==0 && feedbackTypeSuccessCount==0){
						//如果是家族，及机构删除和家族删除，并且不包含错误主键，设置基础段为回执成功
						if(!object.getClass().equals(AutoDTO_JG_JGJBXX_JC.class)){
							if(!errorPrimarykeySet.contains(ReflectOperation.getPrimaryKeyValue(object).toString())){
								ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
							}else{
								ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
							}
						}else{
							ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
						}
					}
					else if(feedbackTypeFalseCount>0 && feedbackTypeSuccessCount==0){
						ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
					}else if(feedbackTypeFalseCount==0 && feedbackTypeSuccessCount>0){
						ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
					}else{
						ReflectOperation.setFieldValue(object, "RPTFeedbackType", "4");
					}
				}
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{objectList,null});
				
				new RejectReportService().GetSendedRejectReportFeedback(objectList,FileName,"24"); //处理已报送报文驳回的回执状态
				
				//统计任务层状态
				commonStatus.commonUpdateReportStatus(objectList, instInfoSubList);
			}
			
			/**更新企业征信报送流转概况表*/
			GenerAndAnaInsertService updateData=new GenerAndAnaInsertService();
			String strFKBWSFTSSBCW="是";//反馈报文是否提示上报错误
			if((StringUtils.isBlank(strCCYYDM))){
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
				ex.printStackTrace();
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

	public void setStatusMap(Map<String,String> statusMap) {
		this.statusMap = statusMap;
	}

	public Map<String,String> getStatusMap() {
		return statusMap;
	}

	private static final int BUFFER = 2048;
	
	public void handleSuccessReportStatus(){
		
	}
}
