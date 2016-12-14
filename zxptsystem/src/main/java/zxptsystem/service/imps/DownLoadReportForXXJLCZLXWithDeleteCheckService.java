package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

/**
 * 对信息记录操作类型为删除的数据的校验
 * 当删除报文未能匹配到业务数据，则停止生成报文
 * 当删除报文匹配到业务数据为历史数据，则停止生成报文
 * @author xiajieli
 *
 */
public class DownLoadReportForXXJLCZLXWithDeleteCheckService  {

	@SuppressWarnings("unchecked")
	public String getXXJLCZLXWithDeleteMessage(String[] selectReport,Map<String, String> xxjllxMap,Map<String, String> reportMap,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		
		 Map<String,String> QYZYLogicPrimaryKeyForXXJLCZLX_JC=ShowContext.getInstance().getShowEntityMap().get("QYZYLogicPrimaryKeyForXXJLCZLX_JC"); //根据表名得到基础段逻辑主键
		 Map<String,String> QYZYLogicPrimaryKeyForXXJLCZLX_MX=ShowContext.getInstance().getShowEntityMap().get("QYZYLogicPrimaryKeyForXXJLCZLX_MX"); //根据表名得到明细段逻辑主键
		 Map<String,String> QYZXXXJLLXChineseMap=ShowContext.getInstance().getShowEntityMap().get("QYZXXXJLLXChineseMap"); //根据信息记录类型Code得到中文名
		 
		 List<String> strNotMatchList=new ArrayList<String>(); //存储未匹配到的数据
		 List<String> strMatchHistoryDataList=new ArrayList<String>();//存储匹配到历史数据的数据
		 
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if(!report.equals("51")){
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (String xxjllx : xxjllxList) {
					 String reportDto = reportMap.get(xxjllx);
					 String[] reportDtos = reportDto.split(",");
					 String baseDto = reportDtos[0].split("%")[1];
					 
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
					 //查询当前上报的基础段数据
					List<Object> objectJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					List<Field> dtoFieldList=ReflectOperation.getOneToManyField(baseDto);
					for (Object objectjc : objectJCList) {
						if(ReflectOperation.getFieldValue(objectjc, "XXJLCZLX")!=null && ReflectOperation.getFieldValue(objectjc, "XXJLCZLX").equals("4")){//查询出信息记录操作类型为"删除"的
							for (Field field : dtoFieldList) {
								for (int k=0;k<reportDtos.length;k++) {
									if(reportDtos[k].contains(ReflectOperation.getGenericClass(field.getGenericType()).getName())){//为当前信息记录类型的dto
										detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
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
										detachedCriteria.add(Restrictions.eq("FOREIGNID",objectjc));
										
										//查询出信息记录操作类型为"删除"的明细段
										List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
										for (Object objectmx : objectMXList) {
											String YWFSRQ="";//获取信息记录操作类型为"删除"的业务发生日期
											if(ReflectOperation.getFieldValue(objectmx, "extend2")!=null && !ReflectOperation.getFieldValue(objectmx, "extend2").equals("")){
												YWFSRQ=ReflectOperation.getFieldValue(objectmx, "extend2").toString();
											}else{
												YWFSRQ=HelpTool.getBeforeDate("yyyyMMdd");
											}
											
											//查询相匹配的所有已回执的基础段
											detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
											detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
											detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "1"));
											detachedCriteria.add(Restrictions.eq("XXJLCZLX", "1"));
											
											if(QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(baseDto)!=null){
												boolean isFund=false;
												for (Map.Entry<String, String> entryJC:QYZYLogicPrimaryKeyForXXJLCZLX_JC.entrySet()) {
													if(entryJC.getKey().equals(baseDto)){
														String[] entryValues=null;
														if(entryJC.getValue().indexOf(",")>0){
															entryValues=entryJC.getValue().split(",");
														}
														for (String strFieldName : entryValues) {
															detachedCriteria.add(Restrictions.eq(strFieldName, ReflectOperation.getFieldValue(objectjc, strFieldName)));
														}
														isFund=true;
													}
													if(isFund){
														break;
													}
												}
											}
											List<Object> objectJCConditionList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
											if(objectJCConditionList.size()>0){
												//查询相匹配已上报的明细段
												for (Object objectjcCondition : objectJCConditionList) {
													detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
													detachedCriteria.add(Restrictions.eq("FOREIGNID",objectjcCondition));////
													detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
													
													if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(ReflectOperation.getGenericClass(field.getGenericType()).getName())!=null){
														boolean isFund=false;
														for (Map.Entry<String, String> entryMX:QYZYLogicPrimaryKeyForXXJLCZLX_MX.entrySet()) {
															if(entryMX.getKey().equals(ReflectOperation.getGenericClass(field.getGenericType()).getName())){
																String[] entryValues=null;
																String entryValue="";
																if(entryMX.getValue().indexOf(",")>0){
																	entryValues=entryMX.getValue().split(",");
																}else{
																	entryValue=entryMX.getValue();
																}
																
																if(entryMX.getValue().indexOf(",")>0){
																	for (String strFieldName : entryValues) {
																		detachedCriteria.add(Restrictions.eq(strFieldName, ReflectOperation.getFieldValue(objectmx, strFieldName)));
																	}
																}else{
																	detachedCriteria.add(Restrictions.eq(entryValue, ReflectOperation.getFieldValue(objectmx, entryValue)));
																}
																isFund=true;
															}
															if(isFund){
																break;
															}
														}
													}
													List<Object> objectMXConditionList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													if(objectMXConditionList.size()>0){//匹配到
														boolean isMatchData=false;
														boolean isMatchHistoryData=false;
														for (Object objectmxCondition : objectMXConditionList) {
															String YWFSRQCondition="";
															if(ReflectOperation.getFieldValue(objectmxCondition, "extend2")!=null && !ReflectOperation.getFieldValue(objectmxCondition, "extend2").equals("")){
																YWFSRQCondition=ReflectOperation.getFieldValue(objectmxCondition, "extend2").toString();
															}else{
																YWFSRQCondition=HelpTool.getBeforeDate("yyyyMMdd");
															}
															if(YWFSRQCondition.equals(YWFSRQ)){
																isMatchData=true;
															}
															if(TypeParse.parseDate(YWFSRQCondition).after(TypeParse.parseDate(YWFSRQ))){
																isMatchHistoryData=true;
															}
														}
														if(!isMatchData){//未匹配到明细数据
															String xxjllxCh=QYZXXXJLLXChineseMap.get(xxjllx);
															String JRJGDM="";
															if(ReflectOperation.getFieldValue(objectjc, "JRJGDM")!=null){
																JRJGDM=ReflectOperation.getFieldValue(objectjc, "JRJGDM").toString();
															}
															String DKKBM="";
															if(QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(baseDto).contains("DKKBM")){
																DKKBM=ReflectOperation.getFieldValue(objectjc, "DKKBM").toString();
															}else{
																if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(objectmx.getClass().getName())!=null){
																	if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(objectmx.getClass().getName()).contains("DKKBM")){
																		DKKBM=ReflectOperation.getFieldValue(objectmx, "DKKBM").toString();
																	}
																}
															}
															if(!strNotMatchList.contains(xxjllxCh+","+JRJGDM+","+DKKBM)){
																strNotMatchList.add(xxjllxCh+","+JRJGDM+","+DKKBM);
															}
														}
														else{//匹配到的是历史数据
															if(isMatchHistoryData){
																String xxjllxCh=QYZXXXJLLXChineseMap.get(xxjllx);
																String JRJGDM="";
																if(ReflectOperation.getFieldValue(objectjc, "JRJGDM")!=null){
																	JRJGDM=ReflectOperation.getFieldValue(objectjc, "JRJGDM").toString();
																}
																String DKKBM="";
																if(QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(baseDto).contains("DKKBM")){
																	DKKBM=ReflectOperation.getFieldValue(objectjc, "DKKBM").toString();
																}else{
																	if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(objectmx.getClass().getName())!=null){
																		if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(objectmx.getClass().getName()).contains("DKKBM")){
																			if(ReflectOperation.getFieldValue(objectmx, "DKKBM")!=null){
																				DKKBM=ReflectOperation.getFieldValue(objectmx, "DKKBM").toString();
																			}
																		}
																	}
																}
																if(!strMatchHistoryDataList.contains(xxjllxCh+","+JRJGDM+","+DKKBM)){
																	strMatchHistoryDataList.add(xxjllxCh+","+JRJGDM+","+DKKBM);
																}
															}
														}
														
													}else{//没有匹配到明细数据
														String xxjllxCh=QYZXXXJLLXChineseMap.get(xxjllx);
														String JRJGDM="";
														if(ReflectOperation.getFieldValue(objectjc, "JRJGDM")!=null){
															JRJGDM=ReflectOperation.getFieldValue(objectjc, "JRJGDM").toString();
														}
														String DKKBM="";
														if(QYZYLogicPrimaryKeyForXXJLCZLX_JC.get(baseDto).contains("DKKBM")){
															DKKBM=ReflectOperation.getFieldValue(objectjc, "DKKBM").toString();
														}else{
															if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(objectmx.getClass().getName())!=null){
																if(QYZYLogicPrimaryKeyForXXJLCZLX_MX.get(objectmx.getClass().getName()).contains("DKKBM")){
																	if(ReflectOperation.getFieldValue(objectmx, "DKKBM")!=null){
																		DKKBM=ReflectOperation.getFieldValue(objectmx, "DKKBM").toString();
																	}
																}
															}
														}
														strNotMatchList.add(xxjllxCh+","+JRJGDM+","+DKKBM);
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
		
		 String strNotMatch="";
		 if(strNotMatchList.size()>0){
			 strNotMatch="删除报文未能匹配到业务数据，停止生成报文！";
			 strNotMatch += "\r\n----------------------------------------------";
			 strNotMatch += "\r\n信息记录类型        金融机构代码                    贷款卡编码";
				
			 for (String strNotMatchTemp : strNotMatchList) {
				if(strNotMatchTemp.indexOf(",")>0){
					strNotMatch += "\r\n"+strNotMatchTemp.split(",")[0]+"        "+strNotMatchTemp.split(",")[1]+"                "+strNotMatchTemp.split(",")[2];
				}
			}
		 }
		 
		 String strMatchHistoryData="";
		 if(strMatchHistoryDataList.size()>0){
			 String str="\r\n\n";
			 strMatchHistoryData=str+"删除报文匹配到业务数据为历史数据，停止生成报文！";
			 strMatchHistoryData += "\r\n----------------------------------------------";
			 strMatchHistoryData += "\r\n信息记录类型        金融机构代码                    贷款卡编码";
			 for (String strMatchHistoryDataTemp : strMatchHistoryDataList) {
				 if(strMatchHistoryDataTemp.indexOf(",")>0){
					 strMatchHistoryData += "\r\n"+strMatchHistoryDataTemp.split(",")[0]+"        "+strMatchHistoryDataTemp.split(",")[1]+"                "+strMatchHistoryDataTemp.split(",")[2];
				 }
			}
		 }
		 
		 return strNotMatch + strMatchHistoryData;
	}
}
