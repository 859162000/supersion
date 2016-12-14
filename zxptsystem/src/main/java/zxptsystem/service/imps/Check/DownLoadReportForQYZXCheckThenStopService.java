package zxptsystem.service.imps.Check;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.AutoDTO_QY_JKRGKXX;

import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import framework.dao.imps.OrderBySqlFormula;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

/**
 * 企业征信报文级数据校验（不满足条件停止生成报文）
 * @author xiajieli
 *
 */
public class DownLoadReportForQYZXCheckThenStopService  {
	
	/**
	 * 3009对含有“展期标志”的信息记录，当数据项“展期标志”为：“1－是”，并且“信息记录操作类型”为 “1-新增”、“2-业务变更”或“3－修改”时，
	 *    在信贷业务信息报文文件中的贷款（融资）业务信息采集报文或者数据库中必须有与上述记录相对应的展期信息记录存在
	 * 3012对含有“展期标志”的信息记录，当数据项“展期标志”为：“2－否”时，那么在信贷业务信息报文文件中的贷款（融资）
	 *    业务信息采集报文中以及数据库中不能有与上述记录相对应的展期信息记录存在
	 * @param selectReport 报文序号（选择的报文种类）
	 * @param xxjllxMap 信息记录类型映射
	 * @param reportMap 报文信息记录组成
	 * @param instInfoSubList 所属机构
	 * @param downloadJudgeStatusMap 生成报文过滤条件
	 * @return 错误message
	 * @throws Exception 
	 */
	public String getQYZXCheck_3009And3012(String[] selectReport,Map<String, String> xxjllxMap,Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String YWFSRQ) throws Exception {
		
		 List<String> strDKYWKeyValueList=new ArrayList<String>(); //存储贷款业务的逻辑主键值
		 List<String> strDKYWKeyValueList1=new ArrayList<String>(); //存储贷款业务的逻辑主键值
		 
		 List<String> strMYRZYWKeyValueList=new ArrayList<String>(); //存储贸易融资业务的逻辑主键值
		 List<String> strMYRZYWKeyValueList1=new ArrayList<String>(); //存储贸易融资业务的逻辑主键值
		 
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 
		for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11") || report.equals("14")){//贷款业务 和 融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("09") ||  xxjllx.equals("15")){ //借据和融资
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 
							for (Object objectCurrentSendJC : objectCurrentSendList) {
								
								//查询上报的明细段
								List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectCurrentSendJC.getClass());
								for (Field field : dtoFieldList) {
									String dtoName=ReflectOperation.getGenericClass(field.getGenericType()).getName();
									if(dtoName.equals("zxptsystem.dto.AutoDTO_QY_JJXX") || dtoName.equals("zxptsystem.dto.AutoDTO_QY_RZYWXX")){
										//得到当前待上报的所有明细段
										List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,field,downloadJudgeStatusMap);
										
										for (Object objectCurrentSendMx : objectCurrentSendMXList) {
											if(ReflectOperation.getFieldValue(objectCurrentSendMx, "ZQBZ")!=null){
												String strZQBZ=ReflectOperation.getFieldValue(objectCurrentSendMx, "ZQBZ").toString();
												
												if(strZQBZ.equals("1")){//展期标志为1，必须有记录存在
													
													if(dtoName.equals("zxptsystem.dto.AutoDTO_QY_JJXX")){//借据
														
														String strJJBH="";
														if(ReflectOperation.getFieldValue(objectCurrentSendMx, "JJBH")!=null){
															strJJBH=ReflectOperation.getFieldValue(objectCurrentSendMx, "JJBH").toString();
														}
														
														//查询同一借据且业务发生日期相同的待上报展期信息记录
														List<Object> objectZQXXList=HelpChecker.GetDKYW_ZQXXListByJJBH(objectCurrentSendJC,strJJBH);
														
														if(objectZQXXList.size()==0){
															strDKYWKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
														}
														
													}else if(dtoName.equals("zxptsystem.dto.AutoDTO_QY_RZYWXX")){//融资业务
														String strRZYWBH="";
														if(ReflectOperation.getFieldValue(objectCurrentSendMx, "RZYWBH")!=null){
															strRZYWBH=ReflectOperation.getFieldValue(objectCurrentSendMx, "RZYWBH").toString();
														}
														
														//查询同一同一融资编号且业务发生日期相同的待上报展期信息记录
														List<Object> objectZQXXList = HelpChecker.getMYRZ_ZQXXByRZYWBH(objectCurrentSendJC,strRZYWBH);
														
														if(objectZQXXList.size()==0){
															strMYRZYWKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
														}
														
													}
													
												}
												//展期标志为2，不能有记录存在 2-否
												else if(strZQBZ.equals("2")){
													if(dtoName.equals("zxptsystem.dto.AutoDTO_QY_JJXX")){//借据
														String strJJBH="";
														if(ReflectOperation.getFieldValue(objectCurrentSendMx, "JJBH")!=null){
															strJJBH=ReflectOperation.getFieldValue(objectCurrentSendMx, "JJBH").toString();
														}
														
														//查询同一借据的展期信息记录
														List<Object> objectZQXXList=HelpChecker.GetDKYW_ZQXXListByJJBH(objectCurrentSendJC,strJJBH);
														
														if(objectZQXXList.size()>0){
															boolean isFlag=false;
															for (Object zqxx : objectZQXXList) {
																Date ZQXX_YWFSRQ=null;
																if(ReflectOperation.getFieldValue(zqxx, "extend2")!=null && !ReflectOperation.getFieldValue(zqxx, "extend2").equals("")){
																	ZQXX_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(zqxx, "extend2").toString());
																}else{
																	ZQXX_YWFSRQ=TypeParse.parseDate(YWFSRQ);
																}
																
																Date JJXX_YWFSRQ=null;
																if(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2")!=null && !ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2").equals("")){
																	JJXX_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2").toString());
																}else{
																	JJXX_YWFSRQ=TypeParse.parseDate(YWFSRQ);
																}
																//展期业务发生日期小于借据业务发生日期
																if(ZQXX_YWFSRQ.before(JJXX_YWFSRQ)){
																	isFlag=true;
																}
															}
															
															if(isFlag){
																strDKYWKeyValueList1 =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
															}
															
														}
														
														
													}else if(dtoName.equals("zxptsystem.dto.AutoDTO_QY_RZYWXX")){//融资业务
														String strRZYWBH="";
														if(ReflectOperation.getFieldValue(objectCurrentSendMx, "RZYWBH")!=null){
															strRZYWBH=ReflectOperation.getFieldValue(objectCurrentSendMx, "RZYWBH").toString();
														}
														
														//查询同一融资编号展期信息记录
														List<Object> objectZQXXList = HelpChecker.getMYRZ_ZQXXByRZYWBH(objectCurrentSendJC,strRZYWBH);
														
														if(objectZQXXList.size()>0){
															boolean isFlag=false;
															for (Object zqxx : objectZQXXList) {
																Date ZQXX_YWFSRQ=null;
																if(ReflectOperation.getFieldValue(zqxx, "extend2")!=null && !ReflectOperation.getFieldValue(zqxx, "extend2").equals("")){
																	ZQXX_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(zqxx, "extend2").toString());
																}else{
																	ZQXX_YWFSRQ=TypeParse.parseDate(YWFSRQ);
																}
																
																Date RZYWXX_YWFSRQ=null;
																if(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2")!=null && !ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2").equals("")){
																	RZYWXX_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2").toString());
																}else{
																	RZYWXX_YWFSRQ=TypeParse.parseDate(YWFSRQ);
																}
																
																//展期业务发生日期小于融资业务发生日期
																if(ZQXX_YWFSRQ.before(RZYWXX_YWFSRQ)){
																	isFlag=true;
																}
																
															}
															if(isFlag){
																strMYRZYWKeyValueList1 =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
															}
															
														}
													}
												}
												
											}
												
										}
									}
									
								}
								
							}
							 
					 }
					
				 }
			 }
			 
		}
		
		String strMes="";
		String getMsgByErrorCode3009=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("3009");
		String getMsgByErrorCode3012=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("3012");
		
		if(strDKYWKeyValueList.size() >0){
			strMes="\r\n"+getMsgByErrorCode3009;
			strMes += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strDKYWKeyValueList.size();i++) {
				strMes += "\r\n("+ (i+1) +")"+strDKYWKeyValueList.get(i)+"\r\n";
			}
			 
		 }
		 
		if(strMYRZYWKeyValueList.size()>0){
			strMes="\r\n"+getMsgByErrorCode3009;
			strMes += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strMYRZYWKeyValueList.size();i++) {
				strMes += "\r\n("+ (i+1) +")"+strMYRZYWKeyValueList.get(i)+"\r\n";
			}
			
		}
	
		 if(strDKYWKeyValueList1.size() >0){
		    strMes="\r\n"+getMsgByErrorCode3012;
			strMes += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strDKYWKeyValueList1.size();i++) {
				strMes += "\r\n("+ (i+1) +")"+strDKYWKeyValueList1.get(i)+"\r\n";
			}
			 
		 }
		 
		if(strMYRZYWKeyValueList1.size()>0){
		   strMes="\r\n"+getMsgByErrorCode3012;
			strMes += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strMYRZYWKeyValueList1.size();i++) {
				strMes += "\r\n("+ (i+1) +")"+strMYRZYWKeyValueList1.get(i)+"\r\n";
			}
		}
		
		return strMes;
	}

	/**
	 * 4044:当信息记录的记录操作类型为“4－删除”时，该信息记录不能包含标识变更段
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4044(String[] selectReport,Map<String, String> xxjllxMap,Map<String, String> reportMap,
		List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割)
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(!report.equals("51")){
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 String reportDto = reportMap.get(xxjllx);
					 String[] reportDtos = reportDto.split(",");
					 String baseDto = reportDtos[0].split("%")[1];
					 
					//得到待上报的所有基础段
					 String[] strXXJLCZLX=new String[]{"4"};
					 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
					 for (Object objectCurrentSendJC : objectCurrentSendList) {
						List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectCurrentSendJC.getClass());
						for (Field field : dtoFieldList) {
							//得到当前待上报的标识明细段
							String mxDtoName=ReflectOperation.getGenericClass(field.getGenericType()).getName();
							if(mxDtoName.startsWith("zxptsystem.dto.AutoDTO_QY_") && mxDtoName.endsWith("_BS")){
								List<Object> objectCurrentSendBSMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,field,downloadJudgeStatusMap);
								if(objectCurrentSendBSMXList.size()>0){
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
								
							}
							
						}
						
				 }
					 
			 }
				
		 }
	 }
	
	   String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4044");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	
	/**
	 * 4043:当贷款合同（融资协议）信息记录的记录操作类型为“4-删除”且该信息记录的业务发生日期小于等于该记录所属业务在库中的最早业务发生日期时，
	 * 该合同项下必须没有借据（融资业务）记录存在（彻底删除合同前，必须先删除合同下的借据）；当存在担保的主业务信息记录的记录操作类型为“4-删除”
	 * 且该信息记录的业务发生日期小于等于该记录所属业务在库中的最早业务发生日期时，主业务项下必须没有担保记录存在
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4043(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap, String orderbySqlFormulaASC,String YWFSRQ) throws Exception {
		
		//待上报及已上报数据状态过滤
		Map<String, String> downloadJudgeStatusTempMap=new HashMap<String,String>();
		downloadJudgeStatusTempMap.put("RPTCheckType", "2");
		downloadJudgeStatusTempMap.put("RPTSubmitStatus", "2");
		downloadJudgeStatusTempMap.put("RPTVerifyType", "2");
		downloadJudgeStatusTempMap.put("RPTFeedbackType", "1,2,3,4");
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割)
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11") || report.equals("14")){//贷款业务 和 融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("08") || xxjllx.equals("14")){ //贷款合同 和 融资协议
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"4"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
							String dtoJCName=objectCurrentSendJC.getClass().getName();
							if(dtoJCName.equals("zxptsystem.dto.AutoDTO_QY_DKYW_JC")){//贷款业务
								List<Object> objCurrentMXList=HelpChecker.getCurrentSendMXList(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_HTXX", downloadJudgeStatusMap);
								for (Object htxx : objCurrentMXList) {
									
									Date HTXX_YWFSRQ=null;
									String DBBZ="";
									if(ReflectOperation.getFieldValue(htxx, "extend2")!=null && !ReflectOperation.getFieldValue(htxx, "extend2").equals("")){
										HTXX_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(htxx, "extend2").toString());
									}else{
										HTXX_YWFSRQ=TypeParse.parseDate(YWFSRQ);
									}
									if(ReflectOperation.getFieldValue(htxx, "DBBZ")!=null){
										DBBZ=ReflectOperation.getFieldValue(htxx, "DBBZ").toString();
									}
									
									//得到已上报的明细
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_HTXX"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormulaASC));
									List<Object> mxSendedList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									
									if(mxSendedList.size()>0){
										Date Min_YWFSRQ=null;
										if(ReflectOperation.getFieldValue(mxSendedList.get(0), "extend2")!=null){
											Min_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(mxSendedList.get(0), "extend2").toString());
										}
										
										if(HTXX_YWFSRQ.before(Min_YWFSRQ) || HTXX_YWFSRQ.equals(Min_YWFSRQ)){
											boolean isFlag=false;
											if(DBBZ.equals("1")){//担保标志为“是”
												isFlag=true;
											}else{
												//查询待上报及已上报的借据
												int JJXXCount=HelpChecker.GetMXConditionDataCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_JJXX");
												if(JJXXCount > 0){
													isFlag=true;
												}
											}
											if(isFlag){
												strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
											}
											
										}
									}
										
								}
								
							}
							else if(dtoJCName.equals("zxptsystem.dto.AutoDTO_QY_MYRZ_JC")){//贸易融资
								
								List<Object> objCurrentMXList=HelpChecker.getCurrentSendMXList(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_RZXYXX", downloadJudgeStatusMap);
								for (Object rzxyxx : objCurrentMXList) {
									
									Date RZXYXX_YWFSRQ=null;
									String DBBZ="";
									if(ReflectOperation.getFieldValue(rzxyxx, "extend2")!=null && !ReflectOperation.getFieldValue(rzxyxx, "extend2").equals("")){
										RZXYXX_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(rzxyxx, "extend2").toString());
									}else{
										RZXYXX_YWFSRQ=TypeParse.parseDate(YWFSRQ);
									}
									if(ReflectOperation.getFieldValue(rzxyxx, "DBBZ")!=null){
										DBBZ=ReflectOperation.getFieldValue(rzxyxx, "DBBZ").toString();
									}
									
									//得到已上报的明细
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZXYXX"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormulaASC));
									List<Object> mxSendedList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									
									if(mxSendedList.size()>0){
										Date Min_YWFSRQ=null;
										if(ReflectOperation.getFieldValue(mxSendedList.get(0), "extend2")!=null){
											Min_YWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(mxSendedList.get(0), "extend2").toString());
										}
										
										if(RZXYXX_YWFSRQ.before(Min_YWFSRQ) || RZXYXX_YWFSRQ.equals(Min_YWFSRQ)){
											Boolean isFlag=false;
											if(DBBZ.equals("1")){//担保标志为“是”
												isFlag=true;
											}else{
												//查询待上报及已上报的融资
												int RZYWXXCount=HelpChecker.GetMXConditionDataCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_RZYWXX");
												if(RZYWXXCount > 0){
													isFlag=true;
												}
											}
											if(isFlag){
												strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
											}
											
										}
									}
										
								}
								
							}
								
						}
						
					 }
					
				 }
			 }
			 
		}
		
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4043");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4063:如果上报了股票信息段，则借款人概况信息段中上市公司标志必须为“1－是”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4063(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula,String YWFSRQ) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("01")){//借款人概况
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("01") ){ //股票信息
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
									
					 		//得到当前待上报的所有明细段
							int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXCount(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_GPXX",downloadJudgeStatusMap);
							if(objectCurrentSendMXCount > 0){
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JKRGKXX"));
								detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
								detachedCriteria.add(Restrictions.le("extend2",YWFSRQ));
								detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
								List<AutoDTO_QY_JKRGKXX> objJKRGKXXList = (List<AutoDTO_QY_JKRGKXX>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								if(objJKRGKXXList.size()>0){
									if(!objJKRGKXXList.get(0).getSSGSBZ().equals("1")){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
								}
								
							}
								 
						}
						
					 }
					
				 }
			 }
		}
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4063");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	
	/**
	 * 4065:在借款人财务报表信息中，如果信息记录类型为“46-事业单位资产负债表信息记录”、 “47-事业单位收入支出表信息记录” 时，
	 * 借款人的性质只能为“04-事业单位”；若信息记录类型为“03-2002版资产负债表信息记录”、 “04-2002版利润及利润分配表信息记录”、
	 * “05-2002版现金流量表信息记录”、“43-2007版资产负债表信息记录”、“44-2007版利润及利润分配表信息记录”、
	 * “45-2007版现金流量表信息记录时，借款人的性质只能为“01-企业法人”、“02-企业非法人”、“03-个体工商户”和“99-其他”之一
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4065(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("03")){//借款人财务报表
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 String reportDto = reportMap.get(xxjllx);
					 String[] reportDtos = reportDto.split(",");
					 String baseDto = reportDtos[0].split("%")[1];
					 
					//得到待上报的所有基础段
					 String[] strXXJLCZLX=new String[]{"1","2","3"};
					 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
					 for (Object objectCurrentSendJC : objectCurrentSendList) {
						 
						 String DKKBM="";
						 if(ReflectOperation.getFieldValue(objectCurrentSendJC, "DKKBM")!=null){
							 DKKBM=ReflectOperation.getFieldValue(objectCurrentSendJC, "DKKBM").toString();
						 }
						 
						 	//查询借款人概况待上报数据
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JKRGK_JC"));
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
							detachedCriteria.add(Restrictions.in("XXJLCZLX", strXXJLCZLX));
							detachedCriteria.add(Restrictions.eq("DKKBM", DKKBM));
							List<Object> objectJKRGKSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							 
							 for (Object JKRGKJC : objectJKRGKSendList) {
								 	String dtoName="zxptsystem.dto.AutoDTO_QY_JKRGKXX";
								 	List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(JKRGKJC,dtoName,downloadJudgeStatusMap);
									 for (Object mx : objectCurrentSendMXList) {
										String JKRXZ="";
										if(ReflectOperation.getFieldValue(mx, "JKRXZ")!=null){
											JKRXZ=ReflectOperation.getFieldValue(mx, "JKRXZ").toString();
										}
										 if(xxjllx.equals("46") || xxjllx.equals("47") ){ //事业单位
											if(!JKRXZ.equals("04")){
												strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
											}
										 }else if(xxjllx.equals("03") || xxjllx.equals("04") || xxjllx.equals("05")
												 || xxjllx.equals("43") || xxjllx.equals("44") || xxjllx.equals("45")){//非事业单位
											 if(JKRXZ.equals("04")){
												 strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
											  }
											 
										 } 
										
									}
							}
													
					}
					
				 }
			 }
		}
		
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4065");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4172:对于同一借据，“借据金额”－“借据余额”＝∑“借据还款金额”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4172(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String YWFSRQ) throws Exception {
		 
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("09")){ //借据
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_JJXX",downloadJudgeStatusMap);
								for (Object jjxx : objectCurrentSendMXList) {
									double JJJE = 0.00,JJYE = 0.00,sum=0.00,HKJESum=0.00;
									if(ReflectOperation.getFieldValue(jjxx, "DKJJJE")!=null){
										JJJE=Double.parseDouble(ReflectOperation.getFieldValue(jjxx, "DKJJJE").toString()) ;
									}
									if(ReflectOperation.getFieldValue(jjxx, "DKJJYE")!=null){
										JJYE=Double.parseDouble(ReflectOperation.getFieldValue(jjxx, "DKJJYE").toString());
									}
									sum=JJJE - JJYE;
									java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
									sum=Double.parseDouble(df.format(sum));
									
									//业务发生日期
									String JJXX_YWFSRQ=YWFSRQ;
									if(ReflectOperation.getFieldValue(jjxx, "extend2")!=null && !StringUtils.isBlank(ReflectOperation.getFieldValue(jjxx, "extend2").toString())){
										JJXX_YWFSRQ=ReflectOperation.getFieldValue(jjxx, "extend2").toString();
									}
									String orderbySql_HKXX=" case when (this_.extend2='' or this_.extend2 is null) then '"+YWFSRQ+"' else this_.extend2 end desc";
									
									Object ForeignID=ReflectOperation.getFieldValue(jjxx, "FOREIGNID");
									Object JJBH=ReflectOperation.getFieldValue(jjxx, "JJBH");
									
									//查询还款信息待上报及已上报
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_HKXX"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID",ForeignID));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
									detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
									detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
									detachedCriteria.add(Restrictions.le("extend2",JJXX_YWFSRQ));//小于等于借据的业务发生日期
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySql_HKXX));
									List<Object> objectHKXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									
									for (Object hkxx : objectHKXXList) {
										double HKJE = 0.00;
										if(ReflectOperation.getFieldValue(hkxx, "HKJE")!=null){
											HKJE = Double.parseDouble(ReflectOperation.getFieldValue(hkxx, "HKJE").toString()) ;
										}
										HKJESum= HKJESum + HKJE;
									}
									HKJESum=Double.parseDouble(df.format(HKJESum));
									
									if(sum != HKJESum){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
									
								}
								
						 }
						 
					 }
				 }
			 }
		 }
			 
		 
		String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4172");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	

	/**
	 * 4174:借据 “展期次数”>1时，“展期次数”＝历史“展期次数”＋1
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4174(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula,String YWFSRQ) throws Exception {
		
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("11")){ //展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX",downloadJudgeStatusMap);
								
								for (Object object : objectCurrentSendMXList) {
									String JJBH="";
									Integer ZQCS=0;
									Object FOREIGNID=null;
									if(ReflectOperation.getFieldValue(object, "JJBH")!=null){
										JJBH=ReflectOperation.getFieldValue(object, "JJBH").toString();
									}
									if(ReflectOperation.getFieldValue(object, "ZQCS")!=null){
										ZQCS=TypeParse.parseInt(ReflectOperation.getFieldValue(object, "ZQCS").toString());
									}
									if(ReflectOperation.getFieldValue(object, "FOREIGNID")!=null){
										FOREIGNID=ReflectOperation.getFieldValue(object, "FOREIGNID");
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", FOREIGNID));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objMXList.size()>1){
										Integer ZQCS1=TypeParse.parseInt(ReflectOperation.getFieldValue(objMXList.get(1), "ZQCS").toString());  //获取业务发生日期倒序排序的第二条
										if(ZQCS1+1 !=ZQCS){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
									
								}
									
						  }
					 }
				 }
			 }
		 }
			 
		String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4174");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4175:同一借据本次上报“还款日期” >历史最新“还款日期”
	 * 4176:同一借据本次上报“还款次数”>历史最新“还款次数”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4175And4176(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		List<String> strLogicPrmaryKeyValueList4175=new ArrayList<String>();
		 List<String> strLogicPrmaryKeyValueList4176=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("10")){ //还款
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_HKXX",downloadJudgeStatusMap);
								for (Object hkxx : objectCurrentSendMXList) {
									Date HKRQ=null;
									String JJBH="";
									Integer HKCS=0;
									if(ReflectOperation.getFieldValue(hkxx, "HKCS")!=null){
										HKCS=TypeParse.parseInt(ReflectOperation.getFieldValue(hkxx, "HKCS").toString());
									}
									if(ReflectOperation.getFieldValue(hkxx, "HKRQ")!=null){
										HKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(hkxx, "HKRQ").toString());
									}
									if(ReflectOperation.getFieldValue(hkxx, "JJBH")!=null){
										JJBH=ReflectOperation.getFieldValue(hkxx, "JJBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_HKXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objMXList.size()>0){
										//还款日期
										Date lsHKRQ=null;
										
										if(ReflectOperation.getFieldValue(objMXList.get(0), "HKRQ")!=null){
											lsHKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objMXList.get(0), "HKRQ").toString());
										}
										
										if(HKRQ.before(lsHKRQ) || HKRQ.equals(lsHKRQ)){
											strLogicPrmaryKeyValueList4175 =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
										
										//还款次数
										Integer lsHKCS=0;
										
										if(ReflectOperation.getFieldValue(objMXList.get(0), "HKCS")!=null){
											lsHKCS=TypeParse.parseInt(ReflectOperation.getFieldValue(objMXList.get(0), "HKCS").toString());
										}
										
										if(HKCS <= lsHKCS ){
											strLogicPrmaryKeyValueList4176 =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
										
									}
									
								}
									
						}
						 
					 }
				 }
			 }
		 }
			 
		String strmsg="";
		if(strLogicPrmaryKeyValueList4175.size()>0){
 			String getMsgByErrorCode4175=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4175");
 			strmsg +="\r\n"+getMsgByErrorCode4175;
 			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
 			for (int i=0;i<strLogicPrmaryKeyValueList4175.size();i++) {
 				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList4175.get(i)+"\r\n";
 			}
 		}
		
		if(strLogicPrmaryKeyValueList4176.size()>0){
 			String getMsgByErrorCode4176=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4176");
 			strmsg +="\r\n"+getMsgByErrorCode4176;
 			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
 			for (int i=0;i<strLogicPrmaryKeyValueList4176.size();i++) {
 				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList4176.get(i)+"\r\n";
 			}
 		}
		
		return strmsg;
	}
	
	/***
	 * 4178:“贷款合同生效日期”≤“借据放款日期”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4178(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("09")){ // 借据
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_JJXX",downloadJudgeStatusMap);
								for (Object jjxx : objectCurrentSendMXList) {
									Date JJFKRQ=null;
									
									if(ReflectOperation.getFieldValue(jjxx, "JJFKRQ")!=null){
										JJFKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(jjxx, "JJFKRQ").toString());
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_HTXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objHTXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objHTXXList.size()>0){
										Date HTSXRQ=null;
										if(ReflectOperation.getFieldValue(objHTXXList.get(0), "HTSXRQ")!=null){
											HTSXRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objHTXXList.get(0), "HTSXRQ").toString());
										}
										
										if(HTSXRQ.after(JJFKRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
						 }
						 
					 }
				 }
			 }
		 }

		String strmsg="";
		 		
 		if(strLogicPrmaryKeyValueList.size()>0){
 			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4178");
 			strmsg="\r\n"+getMsgByErrorCode;
 			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
 			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
 				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
 			}
 		}
 		return strmsg;
	}

	/**
	 * 4180:“借据放款日期”≤“还款日期”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4180(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("10")){ //还款
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
							//得到当前待上报的所有明细段
							List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_HKXX",downloadJudgeStatusMap);
							for (Object hkxx : objectCurrentSendMXList) {
								
								Date HKRQ=null;
								String JJBH=null;
								
								if(ReflectOperation.getFieldValue(hkxx, "HKRQ")!=null){
									HKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(hkxx, "HKRQ").toString());
								}
								if(ReflectOperation.getFieldValue(hkxx, "JJBH")!=null){
									JJBH=ReflectOperation.getFieldValue(hkxx, "JJBH").toString();
								}
								
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
								detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
								detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
								detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
								detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
								detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
								List<Object> objJJXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								if(objJJXXList.size()>0){
									Date JJFKRQ=null;
									if(ReflectOperation.getFieldValue(objJJXXList.get(0), "JJFKRQ")!=null){
										JJFKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objJJXXList.get(0), "JJFKRQ").toString());
									}
									
									if(JJFKRQ.after(HKRQ)){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
								}
							}
							
						 }
						 
					 }
				 }
			 }
		 }
		 
		String strmsg="";
 		
 		if(strLogicPrmaryKeyValueList.size()>0){
 			String getMsgByErrorCode4180=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4180");
 			strmsg="\r\n"+getMsgByErrorCode4180;
 			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
 			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
 				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
 			}
 		}
 		return strmsg;
	}

	/**
	 * 4184:“展期金额”≤“借据金额”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4184(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("11")){ // 展期   
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
							//得到当前待上报的所有明细段
							List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX",downloadJudgeStatusMap);
							for (Object zqxx : objectCurrentSendMXList) {
								
								double ZQJE=0.00;
								String JJBH="";
								
								if(ReflectOperation.getFieldValue(zqxx, "ZQJE")!=null){
									ZQJE=TypeParse.parseDouble(ReflectOperation.getFieldValue(zqxx, "ZQJE").toString());
								}
								if(ReflectOperation.getFieldValue(zqxx, "JJBH")!=null){
									JJBH=ReflectOperation.getFieldValue(zqxx, "JJBH").toString();
								}
								
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
								detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
								detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
								detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
								detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
								detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
								List<Object> objJJXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								if(objJJXXList.size()>0){
									double JJJE=0.00;
									if(ReflectOperation.getFieldValue(objJJXXList.get(0), "DKJJJE")!=null){
										JJJE=TypeParse.parseDouble(ReflectOperation.getFieldValue(objJJXXList.get(0), "DKJJJE").toString());
									}
									
									if(ZQJE > JJJE){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
								}
							}
							
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4184");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4186:“借据放款日期”≤“展期起始日期”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4186(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("09") || xxjllx.equals("11")){ //借据 和 展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
							if(xxjllx.equals("11")){//展期
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX",downloadJudgeStatusMap);
								for (Object zqxx : objectCurrentSendMXList) {
									
									Date ZQQSRQ=null;
									String JJBH="";
									if(ReflectOperation.getFieldValue(zqxx, "ZQQSRQ")!=null){
										ZQQSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(zqxx, "ZQQSRQ").toString());
									}
									if(ReflectOperation.getFieldValue(zqxx, "JJBH")!=null){
										JJBH=ReflectOperation.getFieldValue(zqxx, "JJBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objJJXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objJJXXList.size()>0){
										
										Date JJFKRQ=null;
										if(ReflectOperation.getFieldValue(objJJXXList.get(0), "JJFKRQ")!=null){
											JJFKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objJJXXList.get(0), "JJFKRQ").toString());
										}
										
										if(JJFKRQ.after(ZQQSRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
								
							}
							else if(xxjllx.equals("09")){//借据
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_JJXX",downloadJudgeStatusMap);
								for (Object jjxx : objectCurrentSendMXList) {
									Date JJFKRQ=null;
									String JJBH="";
									if(ReflectOperation.getFieldValue(jjxx, "JJFKRQ")!=null){
										JJFKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(jjxx, "JJFKRQ").toString());
									}
									
									if(ReflectOperation.getFieldValue(jjxx, "JJBH")!=null){
										JJBH=ReflectOperation.getFieldValue(jjxx, "JJBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objZQXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objZQXXList.size()>0){
										
										Date ZQQSRQ=null;
										if(ReflectOperation.getFieldValue(objZQXXList.get(0), "ZQQSRQ")!=null){
											ZQQSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objZQXXList.get(0), "ZQQSRQ").toString());
										}
										
										if(JJFKRQ.after(ZQQSRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
							}
									
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4186");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4188:对于贷款业务展期信息记录，当展期次数为1，
	 *      同时该展期信息记录的对应的贷款业务借据信息记录下无还款信息记录时，展期金额等于该展期信息记录对应的最新贷款业务借据记录的借据余额
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param YWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4188(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("11")){ //展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
							//得到当前待上报的所有明细段
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX"));
							detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
							for(Map.Entry<String, String> entryTemp : downloadJudgeStatusMap.entrySet()){
								if(entryTemp.getKey().equals("RPTCheckType") || entryTemp.getKey().equals("RPTSendType") || entryTemp.getKey().equals("RPTFeedbackType")){
									if(entryTemp.getValue().indexOf(",") > -1){
										String[] strValues = entryTemp.getValue().split(",");
										detachedCriteria.add(Restrictions.in(entryTemp.getKey(),strValues));
									}
									else{
										detachedCriteria.add(Restrictions.eq(entryTemp.getKey(), entryTemp.getValue()));
									}
								}
							}
							detachedCriteria.add(Restrictions.eq("ZQCS", "1"));
							List<Object> objectCurrentSendMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						 
							for (Object zqxx : objectCurrentSendMXList) {
								
								String JJBHTemp="";
								double ZQJE=0.00;
								
								if(ReflectOperation.getFieldValue(zqxx, "JJBH")!=null){
									JJBHTemp=ReflectOperation.getFieldValue(zqxx, "JJBH").toString();
								}
								if(ReflectOperation.getFieldValue(zqxx, "ZQJE")!=null){
									ZQJE=TypeParse.parseDouble(ReflectOperation.getFieldValue(zqxx, "ZQJE").toString());
								}
								
								Map<String,Object> fieldValueMap=new HashMap<String,Object>();
								fieldValueMap.put("JJBH", JJBHTemp);
								int objHKXXCount = HelpChecker.getCurrentSendAndSendedMXByConditionCount(objectCurrentSendJC,fieldValueMap,"zxptsystem.dto.AutoDTO_QY_HKXX");
								
								if(objHKXXCount == 0){
									detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
									detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBHTemp));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objJJXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objJJXXList.size()>0){
										double JJYE=0.00;
										if(ReflectOperation.getFieldValue(objJJXXList.get(0), "DKJJYE")!=null){
											JJYE=TypeParse.parseDouble(ReflectOperation.getFieldValue(objJJXXList.get(0), "DKJJYE").toString());
										}
										if(ZQJE != JJYE){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
										
									}
									
								}
								
							}
								
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4188");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	
	/**
	 * 4202:如果上报还款信息记录，必须同时上报借据信息记录，且还款信息记录和借据信息记录的业务发生日期一致
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4202(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("10")){ //还款
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
											
							//得到当前待上报的所有明细段
							List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_HKXX",downloadJudgeStatusMap);
							for (Object hkxx : objectCurrentSendMXList) {
								
								String JJBH="";
								if(ReflectOperation.getFieldValue(hkxx, "JJBH")!=null){
									JJBH=ReflectOperation.getFieldValue(hkxx, "JJBH").toString();
								}
								
								String HKXX_YWFSRQ="";
								if(ReflectOperation.getFieldValue(hkxx, "extend2")!=null && !ReflectOperation.getFieldValue(hkxx, "extend2").toString().equals("")){
									HKXX_YWFSRQ=ReflectOperation.getFieldValue(hkxx, "extend2").toString();
								}
								
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
								for(Map.Entry<String, String> entryTemp : downloadJudgeStatusMap.entrySet()){
									if(entryTemp.getKey().equals("RPTCheckType") || entryTemp.getKey().equals("RPTSendType") || entryTemp.getKey().equals("RPTFeedbackType")){
										if(entryTemp.getValue().indexOf(",") > -1){
											String[] strValues = entryTemp.getValue().split(",");
											detachedCriteria.add(Restrictions.in(entryTemp.getKey(),strValues));
										}
										else{
											detachedCriteria.add(Restrictions.eq(entryTemp.getKey(), entryTemp.getValue()));
										}
									}
								}
								detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
								detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
								detachedCriteria.add(Restrictions.eq("extend2", HKXX_YWFSRQ));
								int objJJXXCount = (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								if(objJJXXCount == 0){
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
								
							}
								
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4202");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4204:当信息记录的记录操作类型为“1-新增”、“2-业务变更”或“3-修改”，且展期次数为1，则必须上报与展期信息记录业务发生日期一致的借据信息记录，
	 *     或库中已存在该笔借据信息记录和相应的展期信息
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4204(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割) 
		 
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("11")){//展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
							List<Object> objectMXList=HelpChecker.getCurrentSendMXList(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX", downloadJudgeStatusMap);
							for (Object objMX : objectMXList) {
								String ZQCS="";
								String JJBH="";
								if(ReflectOperation.getFieldValue(objMX, "ZQCS")!=null){
									ZQCS=ReflectOperation.getFieldValue(objMX, "ZQCS").toString();
								}
								if(ReflectOperation.getFieldValue(objMX, "JJBH")!=null){
									JJBH=ReflectOperation.getFieldValue(objMX, "JJBH").toString();
								}
								String ZQXX_YWFSRQ="";
								if(ReflectOperation.getFieldValue(objMX, "extend2")!=null && !ReflectOperation.getFieldValue(objMX, "extend2").equals("") ){
									ZQXX_YWFSRQ=ReflectOperation.getFieldValue(objMX, "extend2").toString();
								}else{
									ZQXX_YWFSRQ=YWFSRQ;
								}
								
								if(ZQCS.equals("1")){
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
									detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
									detachedCriteria.add(Restrictions.eq("JJBH", JJBH));
									detachedCriteria.add(Restrictions.eq("ZQBZ", "1"));
									detachedCriteria.add(Restrictions.le("extend2",ZQXX_YWFSRQ));
									List<Object> objectJJXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objectJJXXList.size()==0){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
									
								}
							}
							
					 }
				}
			 }
				
		 }
	 }
	
	 	String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4204");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	
	/**
	 * 4208:借据信息记录、还款信息记录、展期信息记录、担保信息记录不能包含金融机构代码和合同号码的标识变更段
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param YWFSRQ
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4208(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap, String YWFSRQ) throws Exception {
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割) 
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("09") || xxjllx.equals("10") || xxjllx.equals("11")){//借据 还款  展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
						 		
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKYW_BS"));
							detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
							for(Map.Entry<String, String> entryTemp : downloadJudgeStatusMap.entrySet()){
								if(entryTemp.getKey().equals("RPTCheckType") || entryTemp.getKey().equals("RPTSendType") || entryTemp.getKey().equals("RPTFeedbackType")){
									if(entryTemp.getValue().indexOf(",") > -1){
										String[] strValues = entryTemp.getValue().split(",");
										detachedCriteria.add(Restrictions.in(entryTemp.getKey(),strValues));
									}
									else{
										detachedCriteria.add(Restrictions.eq(entryTemp.getKey(), entryTemp.getValue()));
									}
								}
							}
							detachedCriteria.add(Restrictions.in("BGLX", new String[]{"1","2"}));//金融机构代码和合同号码
							int objectMXCount=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							 
							if (objectMXCount > 0) {
								int objectJJXXCount=HelpChecker.getCurrentSendMXCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_JJXX", downloadJudgeStatusMap);
								int objectHKXXCount=HelpChecker.getCurrentSendMXCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_HKXX", downloadJudgeStatusMap);
								int objectZQXXCount=HelpChecker.getCurrentSendMXCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX", downloadJudgeStatusMap);
								if(objectJJXXCount >0 || objectHKXXCount >0 || objectZQXXCount >0){
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
									
							}
					 }
				}
			 }
				
		 }
	 }
	
		 String strmsg="";
			
			if(strLogicPrmaryKeyValueList.size()>0){
				String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4208");
				strmsg="\r\n"+getMsgByErrorCode;
				strmsg += "\r\n--------------------------------------------------------------------------------------------------";
				for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
					strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
				}
			}
			return strmsg;
	}
	
	
	
	/**
	 * 4210:还款信息记录、展期信息记录不能包含借据编号的标识变更段
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4210(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		 
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("10") || xxjllx.equals("11")){ //还款 和 展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 //得到当前待上报的所有明细段
						     Map<String,Object> fieldValueMap=new HashMap<String,Object>();
						     fieldValueMap.put("BGLX","3");
						     int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXByConditionCount(objectCurrentSendJC,fieldValueMap,"zxptsystem.dto.AutoDTO_QY_DKYW_BS",downloadJudgeStatusMap);
						     
						     if (objectCurrentSendMXCount > 0) {
								strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
						     }
						 }
						 
					 }
				 }
			 }
		 }
		 
	    String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4210");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4212:贷款业务借据信息记录对应的合同信息记录必须在数据库中已存在
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4212(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("09")){ //借据
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
											
							//得到当前待上报的所有借据明细段
							int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXCount(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_JJXX",downloadJudgeStatusMap);
							if (objectCurrentSendMXCount >0) {
								
								//获取已上报及待上报的合同信息
								int objHTXXCount = HelpChecker.GetMXConditionDataCount(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_HTXX");
								
								if(objHTXXCount == 0){
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
								
							}
								
						 }
						 
					 }
				 }
			 }
		 }
		 
	    String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4212");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4234:对于同一融资业务，“融资余额”＝“融资金额”－∑“融资还款金额”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4234(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("15")){ ///融资业务
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
							//得到当前待上报的所有明细段
							List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYWXX",downloadJudgeStatusMap);
							for (Object rzyw : objectCurrentSendMXList) {
								double RZJE = 0.00,RZYE = 0.00,HKJESum=0.00;
								String RZYWBH="";
									
								if(ReflectOperation.getFieldValue(rzyw, "RZJE")!=null){
									RZJE=Double.parseDouble(ReflectOperation.getFieldValue(rzyw, "RZJE").toString()) ;
								}
								if(ReflectOperation.getFieldValue(rzyw, "RZYE")!=null){
									RZYE=Double.parseDouble(ReflectOperation.getFieldValue(rzyw, "RZYE").toString());
								}
								if(ReflectOperation.getFieldValue(rzyw, "RZYWBH")!=null){
									RZYWBH=ReflectOperation.getFieldValue(rzyw, "RZYWBH").toString();
								}
								
								//业务发生日期
								String RZYW_YWFSRQ=YWFSRQ;
								if(ReflectOperation.getFieldValue(rzyw, "extend2")!=null && !StringUtils.isBlank(ReflectOperation.getFieldValue(rzyw, "extend2").toString())){
									RZYW_YWFSRQ=ReflectOperation.getFieldValue(rzyw, "extend2").toString();
								}
								String orderbySql_HKXX=" case when (this_.extend2='' or this_.extend2 is null) then '"+YWFSRQ+"' else this_.extend2 end desc";
								
								//查询还款信息待上报及已上报
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWHKXX"));
								detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
								detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
								detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
								detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
								detachedCriteria.add(Restrictions.le("extend2",RZYW_YWFSRQ));//小于等于融资业务的业务发生日期
								detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySql_HKXX));
								List<Object> objectHKXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								for (Object hkxx : objectHKXXList) {
									double HKJE = 0.00;
									if(ReflectOperation.getFieldValue(hkxx, "HKJE")!=null){
										HKJE = Double.parseDouble(ReflectOperation.getFieldValue(hkxx, "HKJE").toString()) ;
									}
									HKJESum= HKJESum + HKJE;
								}
								
								double tempSum = RZJE - HKJESum;
								java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
								tempSum=Double.parseDouble(df.format(tempSum));
								
								if(tempSum != RZYE){
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
								
							}
										
								
						 }
						 
					 }
				 }
			 }
		 }
			 
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4234");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4236：融资业务“展期次数”>1时，融资“展期次数”＝历史“展期次数”＋1
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param YWFSRQ
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4236(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula,String YWFSRQ) throws Exception {
		
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//贸易融资
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("17")){ //展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
							//得到当前待上报的所有明细段
							List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX",downloadJudgeStatusMap);
							for (Object object : objectCurrentSendMXList) {
								String RZYWBH="";
								Integer ZQCS=0;
								if(ReflectOperation.getFieldValue(object, "RZYWBH")!=null){
									RZYWBH=ReflectOperation.getFieldValue(object, "RZYWBH").toString();
								}
								if(ReflectOperation.getFieldValue(object, "ZQCS")!=null){
									ZQCS=TypeParse.parseInt(ReflectOperation.getFieldValue(object, "ZQCS").toString());
								}
								
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX"));
								detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
								detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
								detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
								detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
								detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
								List<Object> objMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								if(objMXList.size()>1){
									Integer ZQCS1=TypeParse.parseInt(ReflectOperation.getFieldValue(objMXList.get(1), "ZQCS").toString());  //获取业务发生日期倒序排序的第二条
									if(ZQCS1+1 !=ZQCS){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
								}
							}
								
						  }
					 }
				 }
			 }
		 }
			 
		String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4236");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	
	/**
	 * 4238:同一融资业务，本次上报“还款次数” >历史最新“还款次数” （注：还款次数可以不连续）
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4238(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("16")){ //还款
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYWHKXX",downloadJudgeStatusMap);
								for (Object hkxx : objectCurrentSendMXList) {
									String RZYWBH="";
									Integer HKCS=0;
									if(ReflectOperation.getFieldValue(hkxx, "HKCS")!=null){
										HKCS=TypeParse.parseInt(ReflectOperation.getFieldValue(hkxx, "HKCS").toString());
									}
									if(ReflectOperation.getFieldValue(hkxx, "RZYWBH")!=null){
										RZYWBH=ReflectOperation.getFieldValue(hkxx, "RZYWBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWHKXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objMXList.size()>0){
										
										//还款次数
										Integer lsHKCS=0;
										if(ReflectOperation.getFieldValue(objMXList.get(0), "HKCS")!=null){
											lsHKCS=TypeParse.parseInt(ReflectOperation.getFieldValue(objMXList.get(0), "HKCS").toString());
										}
										
										if(HKCS <= lsHKCS ){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
										
									}
									
								}
								
						}
						 
					 }
				 }
			 }
		 }
			 
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4238");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 4240：“融资业务发放日期”≤“还款日期”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4240(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("16")){ //还款
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYWHKXX",downloadJudgeStatusMap);
								for (Object hkxx : objectCurrentSendMXList) {
									
									Date HKRQ=null;
									String RZYWBH="";
									if(ReflectOperation.getFieldValue(hkxx, "HKRQ")!=null){
										HKRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(hkxx, "HKRQ").toString());
									}
									if(ReflectOperation.getFieldValue(hkxx, "RZYWBH")!=null){
										RZYWBH=ReflectOperation.getFieldValue(hkxx, "RZYWBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objRZYWList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objRZYWList.size()>0){
										Date RZYWFSRQ=null;
										if(ReflectOperation.getFieldValue(objRZYWList.get(0), "RZYWFSRQ")!=null){
											RZYWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objRZYWList.get(0), "RZYWFSRQ").toString());
										}
										
										if(RZYWFSRQ.after(HKRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
								
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4240");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	
	/**
	 * 4242:“融资业务发放日期”≤“展期起始日期”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4242(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("17")){ //展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYW_ZQXX",downloadJudgeStatusMap);
								for (Object zqxx : objectCurrentSendMXList) {
									
									Date ZQQSRQ=null;
									String RZYWBH="";
									if(ReflectOperation.getFieldValue(zqxx, "ZQQSRQ")!=null){
										ZQQSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(zqxx, "ZQQSRQ").toString());
									}
									if(ReflectOperation.getFieldValue(zqxx, "RZYWBH")!=null){
										RZYWBH=ReflectOperation.getFieldValue(zqxx, "RZYWBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objRZYWXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objRZYWXXList.size()>0){
										
										Date RZYWFSRQ=null;
										if(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "RZYWFSRQ")!=null){
											RZYWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "RZYWFSRQ").toString());
										}
										
										if(RZYWFSRQ.after(ZQQSRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
									
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4242");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4244:“展期金额”≤“融资金额”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param YWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4244(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("17")){ //展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX",downloadJudgeStatusMap);
								for (Object zqxx : objectCurrentSendMXList) {
									
									double ZQJE=0.00;
									String RZYWBH=null;
									if(ReflectOperation.getFieldValue(zqxx, "ZQJE")!=null){
										ZQJE=TypeParse.parseDouble(ReflectOperation.getFieldValue(zqxx, "ZQJE").toString());
									}
									if(ReflectOperation.getFieldValue(zqxx, "RZYWBH")!=null){
										RZYWBH=ReflectOperation.getFieldValue(zqxx, "RZYWBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objRZYWXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objRZYWXXList.size()>0){
										double RZJE=0.00;
										if(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "RZJE")!=null){
											RZJE=TypeParse.parseDouble(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "RZJE").toString());
										}
										
										if(ZQJE > RZJE){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
								
						 }
						 
					 }
				 }
			 }
		 }
		 

		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4244");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	
	/**
	 * 4248:“融资协议生效日期”≤“融资业务发放时间”
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4248(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao"); 
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("15")){ //业务
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYWXX",downloadJudgeStatusMap);
								for (Object rzyw : objectCurrentSendMXList) {
									Date RZYWFSRQ=null;
									if(ReflectOperation.getFieldValue(rzyw, "RZYWFSRQ")!=null){
										RZYWFSRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(rzyw, "RZYWFSRQ").toString());
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZXYXX"));
									detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objRZXYXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objRZXYXXList.size()>0){
										Date XYSXRQ=null;
										if(ReflectOperation.getFieldValue(objRZXYXXList.get(0), "XYSXRQ")!=null){
											XYSXRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(objRZXYXXList.get(0), "XYSXRQ").toString());
										}
										
										if(XYSXRQ.after(RZYWFSRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
									}
								}
								
						 }
						 
					 }
				 }
			 }
		 }
			 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4248");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	
	

	/**
	 * 4252:对于融资业务展期信息记录，当展期次数为1，同时该展期信息记录的对应的融资业务信息记录下无还款信息记录时，
	 *      展期金额等于该展期信息记录对应的最新融资业务信息记录的融资余额
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param WFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4252(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("17")){ //展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							 
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX",downloadJudgeStatusMap);
								for (Object zqxx : objectCurrentSendMXList) {
									
									Integer ZQCS=0;
									String RZYWBH="";
									double ZQJE=0.00;
									
									if(ReflectOperation.getFieldValue(zqxx, "RZYWBH")!=null){
										RZYWBH=ReflectOperation.getFieldValue(zqxx, "RZYWBH").toString();
									}
									if(ReflectOperation.getFieldValue(zqxx, "ZQCS")!=null){
										ZQCS=TypeParse.parseInt(ReflectOperation.getFieldValue(zqxx, "ZQCS").toString());
									}
									if(ReflectOperation.getFieldValue(zqxx, "ZQJE")!=null){
										ZQJE=TypeParse.parseDouble(ReflectOperation.getFieldValue(zqxx, "ZQJE").toString());
									}
									
									if(ZQCS==1){
										
										Map<String,Object> fieldValueMap=new HashMap<String,Object>();
										fieldValueMap.put("RPTFeedbackType", "2");
										fieldValueMap.put("FOREIGNID", objectCurrentSendJC);
										fieldValueMap.put("RZYWBH", RZYWBH);
										int objHKXXCount = HelpChecker.getCurrentSendMXByConditionCount(objectCurrentSendJC, fieldValueMap, "zxptsystem.dto.AutoDTO_QY_RZYWHKXX", downloadJudgeStatusMap);
										
										if(objHKXXCount == 0){
											DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
											detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
											detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
											detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
											detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
											detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
											List<Object> objRZYWXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
											if(objRZYWXXList.size()>0){
												double RZYE=0.00;
												if(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "RZYE")!=null){
													RZYE=TypeParse.parseDouble(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "RZYE").toString());
												}
												
												if(RZYE != ZQJE){
													strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
												}
											}
										}
									}
									
								}
										
								
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4252");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	

	/**
	 * 4264:如果上报融资业务还款信息记录，必须同时上报融资业务信息记录，且融资还款信息记录和融资业务信息记录的业务发生日期一致
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param orderbySqlFormula
	 * @param YWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4264(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula, String YWFSRQ) throws Exception {
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("16")){	 //还款
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
											
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYWHKXX",downloadJudgeStatusMap);
								for (Object hkxx : objectCurrentSendMXList) {
									
									String RZYWBH="";
									if(ReflectOperation.getFieldValue(hkxx, "RZYWBH")!=null){
										RZYWBH=ReflectOperation.getFieldValue(hkxx, "RZYWBH").toString();
									}
									
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
									for(Map.Entry<String, String> entryTemp : downloadJudgeStatusMap.entrySet()){
										if(entryTemp.getKey().equals("RPTCheckType") || entryTemp.getKey().equals("RPTSendType") || entryTemp.getKey().equals("RPTFeedbackType")){
											if(entryTemp.getValue().indexOf(",") > -1){
												String[] strValues = entryTemp.getValue().split(",");
												detachedCriteria.add(Restrictions.in(entryTemp.getKey(),strValues));
											}
											else{
												detachedCriteria.add(Restrictions.eq(entryTemp.getKey(), entryTemp.getValue()));
											}
										}
									}
									detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
									detachedCriteria.add(Restrictions.le("extend2", YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
									List<Object> objRZYWXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(objRZYWXXList.size()>0){
										String HKXX_YWFSRQ="";
										String RZYWXX_YWFSRQ="";
										if(ReflectOperation.getFieldValue(hkxx, "extend2")!=null && !ReflectOperation.getFieldValue(hkxx, "extend2").toString().equals("")){
											HKXX_YWFSRQ=ReflectOperation.getFieldValue(hkxx, "extend2").toString();
										}else{
											HKXX_YWFSRQ=YWFSRQ;
										}
										
										if(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "extend2")!=null && !StringUtils.isBlank(ReflectOperation.getFieldValue(objRZYWXXList.get(0), "extend2").toString())){
											RZYWXX_YWFSRQ=ReflectOperation.getFieldValue(objRZYWXXList.get(0), "extend2").toString();
										}else{
											RZYWXX_YWFSRQ=YWFSRQ;
										}
										
										if(!HKXX_YWFSRQ.equals(RZYWXX_YWFSRQ)){
											strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
										}
										
									}else{
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
									
								}
								
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4264");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	
	/**
	 * 4266:当融资业务展期信息记录的记录操作类型为“1-新增”、“2-业务变更”或“3－修改”，且展期次数为1，则必须上报与展期信息记录业务发生日期一致的融资业务信息记录，
	 *      或库中已存在该笔融资业务信息记录和相应的展期信息
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param yWFSRQ
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4266(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormulaASC, String YWFSRQ) throws Exception {
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割) 
		 
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 
		 IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("17")){//展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
							
							List<Object> objectMXList=HelpChecker.getCurrentSendMXList(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX", downloadJudgeStatusMap);
							for (Object objMX : objectMXList) {
								String ZQCS="";
								String RZYWBH="";
								if(ReflectOperation.getFieldValue(objMX, "ZQCS")!=null){
									ZQCS=ReflectOperation.getFieldValue(objMX, "ZQCS").toString();
								}
								if(ReflectOperation.getFieldValue(objMX, "RZYWBH")!=null){
									RZYWBH=ReflectOperation.getFieldValue(objMX, "RZYWBH").toString();
								}
								
								String ZQXX_YWFSRQ="";
								if(ReflectOperation.getFieldValue(objMX, "extend2")!=null && !ReflectOperation.getFieldValue(objMX, "extend2").equals("") ){
									ZQXX_YWFSRQ=ReflectOperation.getFieldValue(objMX, "extend2").toString();
								}else{
									ZQXX_YWFSRQ=YWFSRQ;
								}
								
								if(ZQCS.equals("1")){
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
									detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
									detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
									detachedCriteria.add(Restrictions.eq("RZYWBH", RZYWBH));
									detachedCriteria.add(Restrictions.eq("ZQBZ", "1"));
									detachedCriteria.add(Restrictions.le("extend2",ZQXX_YWFSRQ));
									detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormulaASC));
									int objectRZYWXXCount = (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									
									if(objectRZYWXXCount == 0){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
									
								}
							}
								
					 }
				}
			 }
				
		 }
	 }
	
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4266");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4270:融资业务信息记录、融资业务还款信息记录、融资业务展期信息记录、担保信息记录不能包含金融机构代码和融资协议编号的标识变更段
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param YWFSRQ
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4270(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap, String YWFSRQ) throws Exception {
		
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割) 
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("15") || xxjllx.equals("16") || xxjllx.equals("17")){//融资 还款 展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
						 	
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_MYRZ_BS"));
							detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
							for(Map.Entry<String, String> entryTemp : downloadJudgeStatusMap.entrySet()){
								if(entryTemp.getKey().equals("RPTCheckType") || entryTemp.getKey().equals("RPTSendType") || entryTemp.getKey().equals("RPTFeedbackType")){
									if(entryTemp.getValue().indexOf(",") > -1){
										String[] strValues = entryTemp.getValue().split(",");
										detachedCriteria.add(Restrictions.in(entryTemp.getKey(),strValues));
									}
									else{
										detachedCriteria.add(Restrictions.eq(entryTemp.getKey(), entryTemp.getValue()));
									}
								}
							}
							detachedCriteria.add(Restrictions.in("BGLX", new String[]{"1","2"}));//金融机构代码和融资协议编号
							int  objectMXCount = (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							
							if(objectMXCount>0){
								//得到当前上报的融资还款展期
								int objectRZYWXXList=HelpChecker.getCurrentSendMXCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_RZYWXX", downloadJudgeStatusMap);
								int objectHKXXList=HelpChecker.getCurrentSendMXCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_RZYWHKXX", downloadJudgeStatusMap);
								int objectZQXXList=HelpChecker.getCurrentSendMXCount(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX", downloadJudgeStatusMap);
								if(objectRZYWXXList > 0 || objectHKXXList > 0 || objectZQXXList > 0){
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
							}
					 }
				}
			 }
				
		 }
	 }
	
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4270");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4272:融资业务还款信息记录、融资业务展期信息记录不能包含融资业务编号的标识变更段
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4272(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("16") || xxjllx.equals("17")){ //还款 和 展期
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
								//得到当前待上报的所有明细段
							 	Map<String,Object> fieldValueMap=new HashMap<String,Object>();
							 	fieldValueMap.put("BGLX", "3");//融资业务编号
								int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXByConditionCount(objectCurrentSendJC,fieldValueMap,"zxptsystem.dto.AutoDTO_QY_MYRZ_BS",downloadJudgeStatusMap);
								if (objectCurrentSendMXCount >0 ) {
									strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
								}
						 }
						 
					 }
				 }
			 }
		 }
		 
		 String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4272");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}

	/**
	 * 4274:融资业务信息记录对应的融资协议信息记录必须在数据库中已存在
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4274(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("14")){//融资业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("15")){ //融资
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 String[] strXXJLCZLX=new String[]{"1","2","3"};
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
						 for (Object objectCurrentSendJC : objectCurrentSendList) {
											
								//得到当前待上报的所有明细段
								int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXCount(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZYWXX",downloadJudgeStatusMap);
								if(objectCurrentSendMXCount > 0){
									//获取已上报及待上报的明细段
									int objRZXYXXCount = HelpChecker.GetMXConditionDataCount(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_RZXYXX");
									if(objRZXYXXCount == 0){
										strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
								}
											
						 }
						 
					 }
				 }
			 }
		 }
		 

		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4274");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	

	/**
	 * 4010:信息记录中的“业务发生日期”≤报文头中的“报文生成时间”，且为合法的日期
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @param yWFSRQ
	 * @return
	 */
	public String getQYZXCheck_4010(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap, String YWFSRQ) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割) 
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键 
		
		 Date YWFSRQDate=TypeParse.parseDate(YWFSRQ);//业务发生日期
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(!report.equals("51")){
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 String reportDto = reportMap.get(xxjllx);
					 String[] reportDtos = reportDto.split(",");
					 String baseDto = reportDtos[0].split("%")[1];
					 
					//得到待上报的所有基础段
					 String[] strXXJLCZLX=new String[]{"1","2","3"};
					 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
					 for (Object objectCurrentSendJC : objectCurrentSendList) {
						 
					 	List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectCurrentSendJC.getClass());
						for (Field field : dtoFieldList) {
							String dtoName=ReflectOperation.getGenericClass(field.getGenericType()).getName();
							
							//得到当前待上报的所有明细段
							List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,dtoName,downloadJudgeStatusMap);
							if(objectCurrentSendMXList.size()>0){
								for (Object currentSendObjectMX : objectCurrentSendMXList) {
									Date extend2=null;
									if(ReflectOperation.getFieldValue(currentSendObjectMX, "extend2")!=null){
										if(!StringUtils.isBlank(ReflectOperation.getFieldValue(currentSendObjectMX, "extend2").toString())){
											extend2=TypeParse.parseDate(ReflectOperation.getFieldValue(currentSendObjectMX, "extend2").toString());
											if(extend2.after(YWFSRQDate)){
												strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
											}
										}
									}
								}
							}
							
						}						
						
				 }
					 
			 }
				
		 }
	 }
	
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4010");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
 }

	/**
	 * 4046:当信息记录的记录操作类型为“1-新增”、“2-业务变更”或“3－修改”，且包含标识变更段，则要求变更前的信息在库中存在，变更后的信息在库中不存在
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_4046(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割) 
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(!report.equals("51")){
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 String reportDto = reportMap.get(xxjllx);
					 String[] reportDtos = reportDto.split(",");
					 String baseDto = reportDtos[0].split("%")[1];
					 
					//得到待上报的所有基础段
					 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCList(baseDto,instInfoSubList,downloadJudgeStatusMap);
					 for (Object objectCurrentSendJC : objectCurrentSendList) {
						 
						 boolean isFlag=false;
						 
						 String XXJLCZLX="";
						 if(ReflectOperation.getFieldValue(objectCurrentSendJC, "XXJLCZLX")!=null){
							 XXJLCZLX=ReflectOperation.getFieldValue(objectCurrentSendJC, "XXJLCZLX").toString();
						 }
						 if(XXJLCZLX.equals("1") || XXJLCZLX.equals("2") || XXJLCZLX.equals("3")){
							 List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectCurrentSendJC.getClass());
								for (Field field : dtoFieldList) {
									
									String tName=ReflectOperation.getGenericClass(field.getGenericType()).getName();
									if(tName.endsWith("_BS")){//标识变更段
										//得到当前待上报的所有明细段
										List<Object> objMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,tName,downloadJudgeStatusMap);
										if(objMXList.size()>0){
											
											//////////////////////////////////////////////////
											String BGLX="";
											String YWBSH="";//业务标识号
											if(ReflectOperation.getFieldValue(objMXList.get(0), "BGLX")!=null){
												BGLX=ReflectOperation.getFieldValue(objMXList.get(0), "BGLX").toString();
											}
											if(ReflectOperation.getFieldValue(objMXList.get(0), "YWBSH")!=null){
												YWBSH=ReflectOperation.getFieldValue(objMXList.get(0), "YWBSH").toString();
											}
											
											//变更类型为1是通用的
											if(BGLX.equals("1")){//1-金融机构代码变更
												
												Map<String,Object> fieldValueMap=new HashMap<String,Object>();
												fieldValueMap.put("JRJGDM", YWBSH);
												List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
												
												if(objectBGQJCList.size()==0){
													isFlag=true;
												}
												
												String BGH_JRJGDM="";
												if(ReflectOperation.getFieldValue(objectCurrentSendJC, "JRJGDM")!=null){
													BGH_JRJGDM=ReflectOperation.getFieldValue(objectCurrentSendJC, "JRJGDM").toString();
												}
												
												Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
												fieldValueMap1.put("JRJGDM", BGH_JRJGDM);
												List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
												
												if(objectBGHJCList.size()>0){
													isFlag=true;
												}
											}
											
											//非金融机构代码
											if(tName.equals("zxptsystem.dto.AutoDTO_QY_BHYW_BS")){//保函业务
												
												 if(BGLX.equals("2")){//2-保函号码变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("BHHM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_BHHM="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "BHHM")!=null){
														BGH_BHHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "BHHM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("BHHM", BGH_BHHM);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
												
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_BLXDZCCL_BS")){//不良信贷资产处置
												 if(BGLX.equals("2")){//2-业务编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("YWBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_YWBH="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "YWBH")!=null){
														BGH_YWBH=ReflectOperation.getFieldValue(objectCurrentSendJC, "YWBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("YWBH", BGH_YWBH);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_BLYW_BS")){//保理业务
											    if(BGLX.equals("2")){//2-保理协议编号变更
											    	
											    	Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("BLXYBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_BLXYBH="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "BLXYBH")!=null){
														BGH_BLXYBH=ReflectOperation.getFieldValue(objectCurrentSendJC, "BLXYBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("BLXYBH", BGH_BLXYBH);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_DBXX_BS")){//担保信息
												if(BGLX.equals("2")){//2-保证合同编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("BZHTBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DBHTXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DBHTXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_BZHTBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "BZHTBH")!=null){
														BGH_BZHTBH=ReflectOperation.getFieldValue(objectMXList.get(0), "BZHTBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("BZHTBH", BGH_BZHTBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DBHTXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
												else if(BGLX.equals("3")){//3-抵押合同编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("DYHTBM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DYHTXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DYHTXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_DYHTBM="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "DYHTBM")!=null){
														BGH_DYHTBM=ReflectOperation.getFieldValue(objectMXList.get(0), "DYHTBM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("DYHTBM", BGH_DYHTBM);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DYHTXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
												if(BGLX.equals("4")){//4-质押合同编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("ZYHTBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_ZYHTBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "ZYHTBH")!=null){
														BGH_ZYHTBH=ReflectOperation.getFieldValue(objectMXList.get(0), "ZYHTBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("ZYHTBH", BGH_ZYHTBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
												else if(BGLX.equals("5")){//5-自然人保证合同编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("BZHTBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_BZHTBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "BZHTBH")!=null){
														BGH_BZHTBH=ReflectOperation.getFieldValue(objectMXList.get(0), "BZHTBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("BZHTBH", BGH_BZHTBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
												if(BGLX.equals("6")){//6-自然人抵押合同编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("DYHTBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_DYHTBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "DYHTBH")!=null){
														BGH_DYHTBH=ReflectOperation.getFieldValue(objectMXList.get(0), "DYHTBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("DYHTBH", BGH_DYHTBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRBZHTXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
												else if(BGLX.equals("7")){//7-自然人质押合同编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("ZYHTBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_ZYHTBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "ZYHTBH")!=null){
														BGH_ZYHTBH=ReflectOperation.getFieldValue(objectMXList.get(0), "ZYHTBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("ZYHTBH", BGH_ZYHTBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_ZRRZYHTXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_DKXX_BS")){//垫款信息
											    if(BGLX.equals("2")){//2-垫款业务号码
											    	Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("DKYWHM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_DKYWHM="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "DKYWHM")!=null){
														BGH_DKYWHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "DKYWHM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("DKYWHM", BGH_DKYWHM);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_DKYW_BS")){//贷款业务
												if(BGLX.equals("2")){//2-贷款合同号码变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("DKHTHM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_DKHTHM="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "DKHTHM")!=null){
														BGH_DKHTHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "DKHTHM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("DKHTHM", BGH_DKHTHM);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
												else if(BGLX.equals("3")){//3-借据编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("JJBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_JJXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_JJBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "JJBH")!=null){
														BGH_JJBH=ReflectOperation.getFieldValue(objectMXList.get(0), "JJBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("JJBH", BGH_JJBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_JJXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_GKSX_BS")){//公开授信
												if(BGLX.equals("2")){//2-授信协议号码变更
													
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("SXXYHM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_SXXYHM="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "SXXYHM")!=null){
														BGH_SXXYHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "SXXYHM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("SXXYHM", BGH_SXXYHM);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_JKRGZ_BS")){//借款人关注
											    if(BGLX.equals("2")){//2-被起诉记录流水号
											    	Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("BQSJLLSH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_SSXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_SSXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_BQSJLLSH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "BQSJLLSH")!=null){
														BGH_BQSJLLSH=ReflectOperation.getFieldValue(objectMXList.get(0), "BQSJLLSH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("BQSJLLSH", BGH_BQSJLLSH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_SSXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
												else if(BGLX.equals("3")){//3-大事信息记录流水号
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("DSXXJLLSH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DSXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DSXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_DSXXJLLSH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "DSXXJLLSH")!=null){
														BGH_DSXXJLLSH=ReflectOperation.getFieldValue(objectMXList.get(0), "DSXXJLLSH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("DSXXJLLSH", BGH_DSXXJLLSH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_DSXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_MYRZ_BS")){//贸易融资
												if(BGLX.equals("2")){//2-融资协议编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("RZXYBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_RZXYBH="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "RZXYBH")!=null){
														BGH_RZXYBH=ReflectOperation.getFieldValue(objectCurrentSendJC, "RZXYBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("RZXYBH", BGH_RZXYBH);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
													
												}
												else if(BGLX.equals("3")){//3-融资业务编号变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("RZYWBH", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_RZYWXX", fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_RZYWXX"));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
													detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
													detachedCriteria.add(Restrictions.eq("RPTSendType", "1"));
													detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
													List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													String BGH_RZYWBH="";
													if(ReflectOperation.getFieldValue(objectMXList.get(0), "RZYWBH")!=null){
														BGH_RZYWBH=ReflectOperation.getFieldValue(objectMXList.get(0), "RZYWBH").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("RZYWBH", BGH_RZYWBH);
													List<Object> objectBGHJCList =HelpChecker.GetMXSendedDataByCondition(objectCurrentSendJC, "zxptsystem.dto.AutoDTO_QY_RZYWXX", fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_XYZYW_BS")){//信用证业务
												if(BGLX.equals("2")){//2-信用证号码变更
													Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("XYZHM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_XYZHM="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "XYZHM")!=null){
														BGH_XYZHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "XYZHM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("XYZHM", BGH_XYZHM);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
													
												}
											}
											else if(tName.equals("zxptsystem.dto.AutoDTO_QY_YHCDHP_BS")){//银行承兑汇票业务
											    if(BGLX.equals("3")){//3-汇票号码变更
											    	Map<String,Object> fieldValueMap=new HashMap<String,Object>();
													fieldValueMap.put("HPHM", YWBSH);
													List<Object> objectBGQJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap);
													
													if(objectBGQJCList.size()==0){
														isFlag=true;
													}
													
													String BGH_HPHM="";
													if(ReflectOperation.getFieldValue(objectCurrentSendJC, "HPHM")!=null){
														BGH_HPHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "HPHM").toString();
													}
													
													Map<String,Object> fieldValueMap1=new HashMap<String,Object>();
													fieldValueMap1.put("HPHM", BGH_HPHM);
													List<Object> objectBGHJCList = HelpChecker.GetJCSendedDataByCondition(objectCurrentSendJC.getClass().getName(), fieldValueMap1);
													
													if(objectBGHJCList.size()>0){
														isFlag=true;
													}
												}
											}
										}
											
									}
									
								}
						 }
						 
						 if(isFlag){
							 strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
						 }
				 }
					 
			 }
				
		 }
	 }
	

	String strmsg="";
	 		
	if(strLogicPrmaryKeyValueList.size()>0){
		String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4046");
		strmsg="\r\n"+getMsgByErrorCode;
		strmsg += "\r\n--------------------------------------------------------------------------------------------------";
		for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
			strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
		}
	}
	 return strmsg;
	}

	/**
	 * 3006:进行担保信息记录报送时，若在信贷业务信息报文文件中或数据库中的最新记录与担保合同对应的“担保标志”为“2－否”的主业务信息记录时，
	 *    担保信息的“合同有效状态”必须为“2－否”
	 *    (报送信贷主业务（贷款合同、保理、融资协议、信用证、保函、汇票业务）且担保标志为否，若相同主合同编号担保信息的任意明细信息
	 *    （保证、抵押、质押、自然人保证、自然人抵押、自然人质押）最新记录（含待上报）中合同有效状态为是则提示)
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getQYZXCheck_3006(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String YWFSRQ) throws Exception {
		String alterMessageXDYWAndDBYW="";//存在信贷业务与担保业务无法匹配数据信息提示
		 
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 Map<String,String> GetYWZLCodeByDto=ShowContext.getInstance().getShowEntityMap().get("GetYWZLCodeByDto"); //通过dto得到业务种类类型
		 Map<String,String> XDYWZLForJCField=ShowContext.getInstance().getShowEntityMap().get("XDYWZLForJCField"); //担保对应信贷业务种类基础段字段
		 Map<String,String> XDYWZLForMXField=ShowContext.getInstance().getShowEntityMap().get("XDYWZLForMXField"); //担保对应信贷业务种类明细段字段
		 Map<String,String> GetDtoByYWZLCode=ShowContext.getInstance().getShowEntityMap().get("GetDtoByYWZLCode"); //通过业务种类类型得到dto
		 Map<String,String> GetYWZLByCode=ShowContext.getInstance().getShowEntityMap().get("GetYWZLByCode"); //通过业务种类类型得到dto中文描述
		 List<String> strYWZLAndZHTBHList=new ArrayList<String>();
		 
		 //业务发生日期倒序排序
		 String orderbySqlFormula=" case when (this_.extend2='' or this_.extend2 is null) then '"+YWFSRQ+"' else this_.extend2 end desc";
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if (report.equals("11") || report.equals("12") || report.equals("14") || report.equals("15") || 
					 report.equals("16") || report.equals("17")) {//贷款合同、保理、融资协议、信用证、保函、汇票业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (Map.Entry<String, String> entryYWZL : GetYWZLCodeByDto.entrySet()) {
					 for (String xxjllx : xxjllxList) {
						 if(xxjllx.equals("08") || xxjllx.equals("12") || xxjllx.equals("14") || xxjllx.equals("18")
								 || xxjllx.equals("19") || xxjllx.equals("20")){ //贷款合同、保理、融资协议、信用证、保函、汇票业务
							 
							 String reportDto = reportMap.get(xxjllx);
							 String[] reportDtos = reportDto.split(",");
							 String baseDto = reportDtos[0].split("%")[1];
							 
							 if(entryYWZL.getKey().equals(baseDto)){//如果dto为需要判断的业务类型
								 DetachedCriteria detachedCriteria = null;
								 //得到待上报的所有基础段
								 String[] strXXJLCZLX=new String[]{"1","2","3"};
								 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCByXXJLCZLXList(baseDto,instInfoSubList,downloadJudgeStatusMap,strXXJLCZLX);
								
								for (Object objectCurrentSendJC : objectCurrentSendList) {
									
									List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectCurrentSendJC.getClass());
									for (Field field : dtoFieldList) {
										for (Map.Entry<String, String> mxField : XDYWZLForMXField.entrySet()) {
											if(mxField.getKey().contains(ReflectOperation.getGenericClass(field.getGenericType()).getName())){//ReflectOperation.getGenericClass(field.getGenericType()).getName().equals(mxField.getKey())
												String[] fieldMx=null;
												if(mxField.getValue()!=null && mxField.getValue().contains(",")){
													fieldMx=mxField.getValue().split(",");
												}
												
												//得到当前待上报的所有明细段
												List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,field,downloadJudgeStatusMap);
												
												for (Object objectCurrentSendMx : objectCurrentSendMXList) {
													
													//是否担保（担保标志）为"否"
													if(ReflectOperation.getFieldValue(objectCurrentSendMx, "DBBZ")!=null && ReflectOperation.getFieldValue(objectCurrentSendMx, "DBBZ").equals("2")){
														String currentYWFSRQ="";//当前明细段的业务发生日期
														if(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2")!=null && !StringUtils.isBlank(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2").toString())){
															currentYWFSRQ=(String) ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2");
														}else{
															currentYWFSRQ=HelpTool.getBeforeDate("yyyyMMdd");
														}
															
														//查询担保业务待上报及已上报的基础段
														List<Object> objectAllDBYWMXList =new ArrayList<Object>();
														 detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DBXX_JC"));
														 detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
														 detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
														 for (Map.Entry<String, String> jcFieldName : XDYWZLForJCField.entrySet()) {
																if(jcFieldName.getKey().equals(GetDtoByYWZLCode.get(entryYWZL.getValue()))){
																	String[] fieldJc=jcFieldName.getValue().split(",");
																	for(String strFieldJc:fieldJc){
																		if(strFieldJc.split("-")[0].equals("XDYWZL")){
																			detachedCriteria.add(Restrictions.eq(strFieldJc.split("-")[0],GetYWZLCodeByDto.get(baseDto)));
																		}else{
																			detachedCriteria.add(Restrictions.eq(strFieldJc.split("-")[0],ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[1])));
																		}
																		
																	}
																	break;
																}
														  }
														 //得到担保业务基础段
														 List<Object> objectDBYWAllJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
															
														 List<Object> tempDBYWAllJCList = new ArrayList<Object>();//存储信息记录操作类型为‘1-新增,2-业务变更,3-修改’的担保基础对象
															
															for (Object objectDBYWJC : objectDBYWAllJCList) {
																 
																 String DBYW_XXJLCZLX="";//信息记录类型
																 if(ReflectOperation.getFieldValue(objectDBYWJC, "XXJLCZLX")!=null){
																	 DBYW_XXJLCZLX=ReflectOperation.getFieldValue(objectDBYWJC, "XXJLCZLX").toString();
																 }
																 
																 if(DBYW_XXJLCZLX.equals("1") || DBYW_XXJLCZLX.equals("2") || DBYW_XXJLCZLX.equals("3") ){//信息记录操作类型为‘1-新增,2-业务变更,3-修改’
																	 if(!tempDBYWAllJCList.contains(objectDBYWJC)){
																		 tempDBYWAllJCList.add(objectDBYWJC);
																	 }
																	 
																 }
															}
															
															 boolean isMatch=false; //判断是否和担保标志相匹配
															 for (Object objectDBYWJC : tempDBYWAllJCList) {//信息记录操作类型为‘1-新增,2-业务变更,3-修改’
																 List<Field> dtoDBYWFieldList=ReflectOperation.getOneToManyField(objectDBYWJC.getClass());
																 for (Field fieldForDBYW : dtoDBYWFieldList) {
																	 for (Map.Entry<String, String> mxDBYWField : XDYWZLForMXField.entrySet()) {
																		 if(mxDBYWField.getKey().contains(ReflectOperation.getGenericClass(fieldForDBYW.getGenericType()).getName())){//ReflectOperation.getGenericClass(fieldForDBYW.getGenericType()).getName().equals(mxDBYWField.getKey())
																			 detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(fieldForDBYW.getGenericType()));
																			 detachedCriteria.add(Restrictions.eq("FOREIGNID",objectDBYWJC));
																			 detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
																			 detachedCriteria.add(Restrictions.le("extend2",currentYWFSRQ));
																			 detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
																			 
																			 if(fieldMx!=null){
																				 for(String strFieldMx:fieldMx){
																					 detachedCriteria.add(Restrictions.eq(strFieldMx.split("-")[0],ReflectOperation.getFieldValue(objectCurrentSendMx,strFieldMx.split("-")[1])));
																				 }
																			 }
																			 //得到担保业务所有明细段(根据业务发生日期倒序排序)
																			 objectAllDBYWMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
																			 
																			 if(objectAllDBYWMXList.size()>0){
																				 if(ReflectOperation.getFieldValue(objectAllDBYWMXList.get(0), "HTYXZT").equals("1")){//如果最新的担保业务合同有效状态是“是”
																					 isMatch=true;
																					 break; 	
																				 }
																			 }
																		 }
																	 }
																	 if(isMatch){
																		 break;
																	 }
																 }
																 if(isMatch){
																	 break;
																 }
															  }
															 
															 if(isMatch){//如果匹配到
																 String strYWZL=entryYWZL.getValue();
																 String strZHTBH="";
																 for (Map.Entry<String, String> jcFieldName : XDYWZLForJCField.entrySet()) {
																		if(jcFieldName.getKey().equals(baseDto)){
																			String[] fieldJc=jcFieldName.getValue().split(",");
																			for(String strFieldJc:fieldJc){
																				if(strFieldJc.split("-")[0].equals("ZHTBH")){
																					if(ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[1])!=null){
																						strZHTBH=ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[1]).toString();
																					}
																					break;
																				}
																			}
																			break;
																		}
																	}
																 
																 String strYWLXAndZHTBH=strYWZL+","+ strZHTBH;
																 strYWZLAndZHTBHList.add(strYWLXAndZHTBH);
															 }
														
														}
													}
													
												break;
											}
										}
									}
									
								}
									
							 }
							 
						 break;
							 
						 }
					}
				 }
			 }
		 }
		 String msg="";
		 if(strYWZLAndZHTBHList.size()>0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("3006");
			 String strXDYWAndDBYW="\r\n"+getMsgByErrorCode;
			 alterMessageXDYWAndDBYW += "\r\n--------------------------------------------------------------------------------------------------";
			 alterMessageXDYWAndDBYW+="\r\n业务类型                                   合同编号                    ";
			 for(String str:strYWZLAndZHTBHList){
				 
				 String strYWLX=GetYWZLByCode.get(str.split(",")[0]);
				 for (int k = strYWLX.length(); k<6; k++) {
					 strYWLX += "   ";
				 }
				 if(str.split(",").length==2){
					 alterMessageXDYWAndDBYW+="\r\n"+strYWLX+"                               "+str.split(",")[1]+""; 
				 }else{
					 alterMessageXDYWAndDBYW+="\r\n"+strYWLX+"                               "+"                 "+"";
				 }
			    
			 }
			 msg=strXDYWAndDBYW+alterMessageXDYWAndDBYW;
		 }
		 
		return msg;
	}

	

	/**
	 * 4004:同一错误记录跟踪编号除填20个9的错误编号以外，其他错误跟踪编号在一个重报报文中只能出现一次
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4004(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		
		List<String> XXJLGZBHList=new ArrayList<String>();
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段逻辑主键值(先以*分割在以逗号分割)
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(!report.equals("51")){
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 String reportDto = reportMap.get(xxjllx);
					 String[] reportDtos = reportDto.split(",");
					 String baseDto = reportDtos[0].split("%")[1];
					 
					//得到待上报的所有基础段
					 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCList(baseDto,instInfoSubList,downloadJudgeStatusMap);
					 for (Object objectCurrentSendJC : objectCurrentSendList) {
						 String XXJLGZBH="";
						 if(ReflectOperation.getFieldValue(objectCurrentSendJC, "XXJLGZBH")!=null){
							 XXJLGZBH=ReflectOperation.getFieldValue(objectCurrentSendJC, "XXJLGZBH").toString();
							 if(!XXJLGZBH.equals("00000000000000000000") && !XXJLGZBH.equals("99999999999999999999")){
								 if(!XXJLGZBHList.contains(XXJLGZBH)){
									 XXJLGZBHList.add(XXJLGZBH);
								 }else{
									 strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
									}
									 
								 }
							 }
							 
						 }
					 }
				 }
			 }
		
		
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4004");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	
	/**
	 * 4298:当“主合同编码”填为“QBZHT”时，“担保合同编码”是系统中存在的担保合同号码；否则，“主合同编码”是系统中存在的信贷业务合同/协议号码
	 * 		(此条校验的是：担保明细段的合同签订日期>=贷款合同生效日期)
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	public String getQYZXCheck_4298(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String YWFSRQ) throws Exception {
		
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		 
		 List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(report.equals("11")){//贷款业务
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 if(xxjllx.equals("08")){ //合同
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						 
						//得到待上报的所有基础段
						 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCList(baseDto,instInfoSubList,downloadJudgeStatusMap);
						 if(baseDto.equals("zxptsystem.dto.AutoDTO_QY_DKYW_JC")){
							 for (Object objectCurrentSendJC : objectCurrentSendList) {
								//得到当前待上报的所有明细段
								List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_QY_HTXX",downloadJudgeStatusMap);
								for (Object htxx : objectCurrentSendMXList) {
									Date HTSXRQ=null;
									if(ReflectOperation.getFieldValue(htxx, "HTSXRQ")!=null){
										HTSXRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(htxx, "HTSXRQ").toString());
										
										String dkyw_extend2="";
										if(ReflectOperation.getFieldValue(htxx, "extend2")!=null){
											dkyw_extend2=ReflectOperation.getFieldValue(htxx, "extend2").toString();
										}else{
											dkyw_extend2=YWFSRQ;
										}
										//对应担保信息记录
										String DKHTHM="";
										if(ReflectOperation.getFieldValue(objectCurrentSendJC, "DKHTHM")!=null){
											DKHTHM=ReflectOperation.getFieldValue(objectCurrentSendJC, "DKHTHM").toString();
										}
										
										Map<String,Object> fieldValueMap=new HashMap<String,Object>();
										fieldValueMap.put("XDYWZL", "1");//信贷业务种类，贷款业务是1
										fieldValueMap.put("ZHTBH", DKHTHM);
										//查询待上报的担保数据
										List<Object> objectCurrentSendDBXXList =HelpChecker.getCurrentSendJCByConditionList("zxptsystem.dto.AutoDTO_QY_DBXX_JC", instInfoSubList, downloadJudgeStatusMap, fieldValueMap);
										for (Object dbxxjc : objectCurrentSendDBXXList) {
											List<Field> dtoFieldList=ReflectOperation.getOneToManyField(objectCurrentSendJC.getClass());
											for (Field field : dtoFieldList) {
												List<Object> objMX=HelpChecker.getCurrentSendMXList(dbxxjc, field, downloadJudgeStatusMap);
												for (Object dbmx : objMX) {
													String dbyw_extend2="";
													Date HTQDRQ=null;
													if(ReflectOperation.getFieldValue(dbmx, "extend2")!=null){
														dbyw_extend2=ReflectOperation.getFieldValue(dbmx, "extend2").toString();
													}else{
														dbyw_extend2=YWFSRQ;
													}
													
													if(ReflectOperation.getFieldValue(dbmx, "HTQDRQ")!=null){
														HTQDRQ=TypeParse.parseDate(ReflectOperation.getFieldValue(dbmx, "HTQDRQ").toString());
													}
													
													if(dkyw_extend2.equals(dbyw_extend2)){
														if(HTQDRQ.before(HTSXRQ)){
															strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
														}
													}
													
												}
												
											}
											
										}
										
									}
								}
									
							}
							 
						 }
						
					 }
				 }
			 }
		 }
			 
		 
		String strmsg="";
			
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("CCXX").get("4298");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
		
	}
	
}

	