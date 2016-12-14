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

import report.dto.ReportInstInfo;
import report.service.imps.CommonUpdateReportStatusService;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLGX;
import zxptsystem.dto.AutoDTO_GRZXZHBSBG;
import zxptsystem.dto.AutoDTO_GR_GRXX_JC;
import zxptsystem.dto.GRZXWFKBWCL;
import zxptsystem.dto.GRZXZHBSBGCJFKBW;
import zxptsystem.dto.GRZXZHBSBGCJFKBWSub;
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
 * 个人征信账户标识变更采集（个人征信历史逾期记录更新）反馈报文
 * @author xiajieli
 *
 */
public class GRZXZHBSBGCJFKBWUploadFileService implements IObjectResultExecute{
	
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
			String JRJGDM=FileName.substring(0, 14);
			List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
			CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
			instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
			String reportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //反馈报文导入时间
			
			String BWLB=FileName.substring(23, 24);
			if(!StringUtils.isBlank(BWLB) && BWLB.equals("4")){//账户标识变更
				
				GRZXZHBSBGCJFKBW gRZXZHBSBGCJFKBW = new GRZXZHBSBGCJFKBW();
				List<GRZXZHBSBGCJFKBWSub> gRZXZHBSBGCJFKBWSubList = new ArrayList<GRZXZHBSBGCJFKBWSub>();
				
				int CCJLHZS=0;//出错记录条数
	 			while((line=bufferedReader.readLine())!=null) {
					row++;
					if(!StringUtils.isBlank(line)){					
						if(row==1){
							gRZXZHBSBGCJFKBW = new GRZXZHBSBGCJFKBW();
							gRZXZHBSBGCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
							if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
								gRZXZHBSBGCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
							}
							gRZXZHBSBGCJFKBW.setStrVersionCode(subStringByByte(line,0,3));
							gRZXZHBSBGCJFKBW.setStrBWSCSJ(subStringByByte(line,3,14));
							gRZXZHBSBGCJFKBW.setStrBWCCWJM(subStringByByte(line,17,27));
							gRZXZHBSBGCJFKBW.setStrGRZXZHBSBGCCXX(subStringByByte(line,44,2));
							
							//根据金融机构代码得到联系人联系电话
							IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
							ReportInstInfo reportInstInfo= (ReportInstInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{ReportInstInfo.class.getName(),JRJGDM,null});
							if(reportInstInfo!=null){
								if(!StringUtils.isBlank(reportInstInfo.getStrReportContact())){
									gRZXZHBSBGCJFKBW.setStrLXR(reportInstInfo.getStrReportContact());
								}
								if(!StringUtils.isBlank(reportInstInfo.getStrReportContactTel())){
									gRZXZHBSBGCJFKBW.setStrLXDH(reportInstInfo.getStrReportContactTel());
								}
							}
							
							IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
							singleObjectSaveDao.paramVoidResultExecute(new Object[]{gRZXZHBSBGCJFKBW,null});
						}
						else{
							CCJLHZS++;
							GRZXZHBSBGCJFKBWSub gRZXZHBSBGCJFKBWSub = new GRZXZHBSBGCJFKBWSub();
							
							gRZXZHBSBGCJFKBWSub.setGRZXZHBSBGCJFKBW(gRZXZHBSBGCJFKBW);
							gRZXZHBSBGCJFKBWSub.setStrGRZXZHBSBGCWDM(subStringByByte(line,0,2));
					
							String strCCXXJL = subStringByByte(line,2,108);
							String strTable ="zxptsystem.dto.AutoDTO_GRZXZHBSBG";						
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
								gRZXZHBSBGCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
								
								gRZXZHBSBGCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
								
								int feedbackTypeFalseCount = 0;
								int feedbackTypeSuccessCount = 0;
								
								List<Field> fieldList=ReflectOperation.getOneToManyField(objectList.get(0).getClass());
								for (Field field : fieldList) {
									Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(objectList.get(0), field.getName());
									for (Object object : fieldObjectList) {
										if(!ReflectOperation.getFieldValue(object, "RPTFeedbackType").equals("2")){
											if(ReflectOperation.getFieldValue(object, "extend1")!=null && ReflectOperation.getFieldValue(object, "extend1").equals(FileName)){
												ReflectOperation.setFieldValue(object, "RPTFeedbackType", "3");
											}
											feedbackTypeFalseCount++;
										}else{
											feedbackTypeSuccessCount++;
										}
									}
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
								
								//统计账户标识变更错误数据的任务层的状态
								commonStatus.commonUpdateReportStatus(objectList, instInfoSubList);

								errorPrimarykeySet.add(ReflectOperation.getPrimaryKeyValue(objectList.get(0)).toString());
							}
							else{
								gRZXZHBSBGCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
							}
							
							gRZXZHBSBGCJFKBWSubList.add(gRZXZHBSBGCJFKBWSub);
							
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
	 			//处理标识变更
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXZHBSBG"));
				detachedCriteria.add(Restrictions.eq("extend1", FileName));
				detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "3"));
				List<AutoDTO_GRZXZHBSBG> autoDTO_GRZXZHBSBGList = (List<AutoDTO_GRZXZHBSBG>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	 			if(autoDTO_GRZXZHBSBGList.size()>0){
	 				for (AutoDTO_GRZXZHBSBG gRZXZHBSBG : autoDTO_GRZXZHBSBGList) {
						ReflectOperation.setFieldValue(gRZXZHBSBG, "RPTFeedbackType", "2");
						
						singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_GRXX_JC.class);
						detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXZHBSBG.getStrOldJRJGCode()));
						detachedCriteria.add(Restrictions.eq("YWH", gRZXZHBSBG.getStrOldYWCode()));
						List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						if(Object_JCList.size()>0){
							for (Object object : Object_JCList) {
								ReflectOperation.setFieldValue(object, "JRJGDM", gRZXZHBSBG.getStrNewJRJGCode());
								ReflectOperation.setFieldValue(object, "YWH", gRZXZHBSBG.getStrNewYWCode());
							}
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
						}
					}
	 			}
	 			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{autoDTO_GRZXZHBSBGList,null});
				
				new RejectReportService().GetSendedRejectReportFeedback(autoDTO_GRZXZHBSBGList,FileName,"22"); //处理已报送报文驳回的回执状态
	 			
	 			//统计账户标识变更正确数据的任务层的状态
				commonStatus.commonUpdateReportStatus(autoDTO_GRZXZHBSBGList, instInfoSubList);
	 
	 			if(gRZXZHBSBGCJFKBWSubList.size()>0){
	 				IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
	 	 			singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXZHBSBGCJFKBWSubList,null});
	 			}
	 			
	 			//对无反馈报文数据处理为已处理
	 			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(GRZXWFKBWCL.class);
				detachedCriteria.add(Restrictions.eq("strBWM", FileName));
				detachedCriteria.add(Restrictions.eq("strGrReportType", "4"));
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
				
			}else if(!StringUtils.isBlank(BWLB) && BWLB.equals("9")){//历史逾期更新
				
				GRZXZHBSBGCJFKBW gRZXZHBSBGCJFKBW = new GRZXZHBSBGCJFKBW();
				List<GRZXZHBSBGCJFKBWSub> gRZXZHBSBGCJFKBWSubList = new ArrayList<GRZXZHBSBGCJFKBWSub>();
				
				int CCJLHZS=0;//出错记录条数
				while((line=bufferedReader.readLine())!=null) {
					row++;
					if(!StringUtils.isBlank(line)){					
						if(row==1){//报文头
							gRZXZHBSBGCJFKBW = new GRZXZHBSBGCJFKBW();
							gRZXZHBSBGCJFKBW.setDtInportTime(new Timestamp(new Date().getTime()));
							if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
								gRZXZHBSBGCJFKBW.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
							}
							
							gRZXZHBSBGCJFKBW.setStrVersionCode(subStringByByte(line,0,3));
							gRZXZHBSBGCJFKBW.setStrBWSCSJ(subStringByByte(line,3,14));
							gRZXZHBSBGCJFKBW.setStrBWCCWJM(subStringByByte(line,17,27));
							gRZXZHBSBGCJFKBW.setStrGRZXZHBSBGCCXX(subStringByByte(line, 44, 2));
							String strLXR=subStringByByte(subStringByByte(line,46,30),30);
							gRZXZHBSBGCJFKBW.setStrLXR(strLXR);
							int begin = 46+strLXR.getBytes("GBK").length;
							int end = begin + 25;
							String strLXDH=subStringByByte(line,begin,end-begin);
							gRZXZHBSBGCJFKBW.setStrLXDH(strLXDH);
							
							IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
							singleObjectSaveDao.paramVoidResultExecute(new Object[]{gRZXZHBSBGCJFKBW,null});
						}
						else{//报文体
							CCJLHZS++;
							GRZXZHBSBGCJFKBWSub gRZXZHBSBGCJFKBWSub = new GRZXZHBSBGCJFKBWSub();
							gRZXZHBSBGCJFKBWSub.setGRZXZHBSBGCJFKBW(gRZXZHBSBGCJFKBW);
							gRZXZHBSBGCJFKBWSub.setStrGRZXZHBSBGCWDM(subStringByByte(line,0,7).trim());

							String strCCXXJL = subStringByByte(line,7,121);
							String strTable ="zxptsystem.dto.AutoDTO_GRZXLSYQJLGX";						
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
								gRZXZHBSBGCJFKBWSub.setStrLinkCCXXJL(ApplicationManager.getOpenLinkedUrl() + "修改数据" + "_" + "framework/FKBWShowCheckUpdateLevelFKBWAutoDTO-"+strTable+".action?id=" + strId);
								gRZXZHBSBGCJFKBWSub.setInstInfo((InstInfo)ReflectOperation.getFieldValue(objectList.get(0), "instInfo"));
								
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
								gRZXZHBSBGCJFKBWSub.setStrLinkCCXXJL("无法定位数据");
							}
							gRZXZHBSBGCJFKBWSubList.add(gRZXZHBSBGCJFKBWSub);
							
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
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXLSYQJLGX"));
				detachedCriteria.add(Restrictions.eq("extend1", FileName));
				detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "3"));
				List<AutoDTO_GRZXLSYQJLGX> autoDTO_GRZXLSYQJLGXList = (List<AutoDTO_GRZXLSYQJLGX>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				if(autoDTO_GRZXLSYQJLGXList.size()>0){
	 				for (AutoDTO_GRZXLSYQJLGX gRZXLSYQJLGX : autoDTO_GRZXLSYQJLGXList) {
						ReflectOperation.setFieldValue(gRZXLSYQJLGX, "RPTFeedbackType", "2");
					}
				}
				
				//统计表任务状态
				commonStatus.commonUpdateReportStatus(autoDTO_GRZXLSYQJLGXList, instInfoSubList);
				
				if(gRZXZHBSBGCJFKBWSubList.size()>0){
					IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
					singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXZHBSBGCJFKBWSubList,null});
				}
				
				/**联动更新基础段逾期记录*/
				for(AutoDTO_GRZXLSYQJLGX gRZXLSYQJLGX : autoDTO_GRZXLSYQJLGXList){
					String JRJGDM1="";
					String YWH="";
					String YQYF="";
					String YQCXYF="";
					
					if(gRZXLSYQJLGX.getJRJGDM()!=null){
						JRJGDM1=gRZXLSYQJLGX.getJRJGDM();
					}
					if(gRZXLSYQJLGX.getYWH()!=null){
						YWH=gRZXLSYQJLGX.getYWH();
					}
					if(gRZXLSYQJLGX.getYQYF()!=null){
						YQYF=gRZXLSYQJLGX.getYQYF();
					}
					if(gRZXLSYQJLGX.getYQCXYS()!=null){
						YQCXYF=gRZXLSYQJLGX.getYQCXYS();
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
							
							Calendar cal_YQYF = Calendar.getInstance();
							Calendar cal_JSYHKRQ = Calendar.getInstance();
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
							cal_JSYHKRQ.setTime(sdf.parse(JSYHKRQ));
							cal_YQYF.setTime(sdf1.parse(YQYF));
							
							int month = cal_JSYHKRQ.get(Calendar.MONTH) - cal_YQYF.get(Calendar.MONTH);//月份相减
							int yearMonth = (cal_JSYHKRQ.get(Calendar.YEAR) - cal_YQYF.get(Calendar.YEAR))*12;//年份相减得到月份
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
							HKZTNew +=YQCXYF;
							HKZTNew +=HKZT.substring(index, HKZT.length());
							
							ReflectOperation.setFieldValue(objectJCList.get(0), "HKZT", HKZTNew);
							
							IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
							singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{objectJCList.get(0),null});
						}
							
					}
					
				}
				
				//对无反馈报文数据处理为已处理
				singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				detachedCriteria = DetachedCriteria.forClass(GRZXWFKBWCL.class);
				detachedCriteria.add(Restrictions.eq("strBWM", FileName));
				detachedCriteria.add(Restrictions.eq("strGrReportType", "9"));
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
