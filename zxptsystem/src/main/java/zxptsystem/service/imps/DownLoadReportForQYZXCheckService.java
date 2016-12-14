package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.service.imps.Check.HelpChecker;


import coresystem.dto.InstInfo;
import extend.helper.HelpTool;
import framework.dao.imps.OrderBySqlFormula;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

/**
 * 企业征信报文级数据校验（询问是否继续生成报文）
 * 1、当存在信贷业务与担保业务无法匹配数据，询问是否继续生成报文
 * 2、当存在重大关注信息，询问是否继续生成报文
 * @author xiajieli
 *
 */
public class DownLoadReportForQYZXCheckService  {

	/**
	 * 校验1：信贷业务与担保业务匹配校验
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getXDYWAndDBYWMessage(String[] selectReport,Map<String, String> xxjllxMap,Map<String, String> reportMap,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String YWFSRQ) throws Exception {
		
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
					 report.equals("16") || report.equals("17") || report.equals("19")) {//贷款合同、保理、融资协议、信用证、保函、汇票业务、担保
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 for (Map.Entry<String, String> entryYWZL : GetYWZLCodeByDto.entrySet()) {
					 for (String xxjllx : xxjllxList) {
						 String reportDto = reportMap.get(xxjllx);
						 String[] reportDtos = reportDto.split(",");
						 String baseDto = reportDtos[0].split("%")[1];
						
						 if(entryYWZL.getKey().equals(baseDto)){//如果dto为需要判断的业务类型
							 DetachedCriteria detachedCriteria = null;
							//得到待上报的所有基础段
							 List<Object> objectCurrentSendList =HelpChecker.getCurrentSendJCList(baseDto,instInfoSubList,downloadJudgeStatusMap);
							
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
												String currentYWFSRQ="";//当前明细段的业务发生日期
												if(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2")!=null && !StringUtils.isBlank(ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2").toString())){
													currentYWFSRQ=(String) ReflectOperation.getFieldValue(objectCurrentSendMx, "extend2");
												}else{
													currentYWFSRQ=HelpTool.getBeforeDate("yyyyMMdd");
												}
												
												if(!entryYWZL.getValue().equals("0")){//信贷（业务种类不为0）
													
													List<Object> objectAllDBYWMXList =new ArrayList<Object>();
													singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
													 detachedCriteria = DetachedCriteria.forClass(Class.forName(GetDtoByYWZLCode.get("0")));
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
													 //得到担保业务所有基础段
													 List<Object> objectDBYWAllJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
													
													if(ReflectOperation.getFieldValue(objectCurrentSendMx, "DBBZ")!=null && 
															ReflectOperation.getFieldValue(objectCurrentSendMx, "DBBZ").equals("1")){//是否担保为"是"
														 
														 boolean isMatch=false; //判断是否和担保标志相匹配
														 for (Object objectDBYWJC : objectDBYWAllJCList) {
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
																		 //得到担保业务所有明细段
																		 objectAllDBYWMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
																		 String strDBBZ="";//担保业务担保标志
																		 
																		 if(objectAllDBYWMXList.size()>0){
																			 if(ReflectOperation.getFieldValue(objectAllDBYWMXList.get(0), "HTYXZT")!=null){
																				 strDBBZ=ReflectOperation.getFieldValue(objectAllDBYWMXList.get(0), "HTYXZT").toString();
																			 }
																			 if(strDBBZ.equals("1")){
																				 isMatch=true;
																			 }																			 
																		 }
																	 }
																 }
															 }
														  }
														 if(!isMatch){//如果匹配失败
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
													}if(ReflectOperation.getFieldValue(objectCurrentSendMx, "DBBZ")!=null && 
															ReflectOperation.getFieldValue(objectCurrentSendMx, "DBBZ").equals("2")){//是否担保为"否"（匹配到担保标志为‘是’的会提示）
														
														List<Object> tempDBYWAllJCList1 = new ArrayList<Object>();
														List<Object> tempDBYWAllJCList2 = new ArrayList<Object>();
														
														for (Object objectDBYWJC : objectDBYWAllJCList) {
															 
															 String XXJLCZLX=String.valueOf(ReflectOperation.getFieldValue(objectDBYWJC, "XXJLCZLX"));
															 if(XXJLCZLX==null){
																 XXJLCZLX = "";
															 }
															 String RPTSendType = String.valueOf(ReflectOperation.getFieldValue(objectDBYWJC, "RPTSendType"));
															 if(RPTSendType==null){
																 RPTSendType = "";
															 }
															 String RPTFeedbackType = String.valueOf(ReflectOperation.getFieldValue(objectDBYWJC, "RPTFeedbackType"));
															 if(RPTFeedbackType==null){
																 RPTFeedbackType = "";
															 }
															 
															 if(!RPTFeedbackType.equals("5")){
																 if(XXJLCZLX.equals("4")){
																	 
																	 boolean isCerrect = true; 
																	 for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
																			String typeValue="";
																			 if(ReflectOperation.getFieldValue(objectDBYWJC, entry.getKey())!=null){
																				 typeValue = ReflectOperation.getFieldValue(objectDBYWJC, entry.getKey()).toString(); 
																			 }
																		    
																			if(entry.getValue().indexOf(",") > -1){
																				if(!entry.getValue().contains(typeValue)){
																					isCerrect = false;
																					break;
																				}
																			}
																			else{
																				if(!typeValue.equals(entry.getValue())){
																					isCerrect = false;
																					break;
																				}
																			}
																		}
																	 if(isCerrect){
																		tempDBYWAllJCList1.add(objectDBYWJC);
																	 }
																 }
																 else if(!XXJLCZLX.equals("4")){
																	tempDBYWAllJCList2.add(objectDBYWJC);
																 }
															 }
														}
														
														 boolean isMatch=false; //判断是否和担保标志相匹配
														 int mxCount = 0;
														 for (Object objectDBYWJC : tempDBYWAllJCList2) {
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
																		 //得到担保业务所有明细段
																		 objectAllDBYWMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
																		 
																		 if(objectAllDBYWMXList.size()>0){
																			 if(ReflectOperation.getFieldValue(objectAllDBYWMXList.get(0), "HTYXZT").equals("1")){
																				 isMatch=true;
																				 mxCount = objectAllDBYWMXList.size();
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
														 
														 if(isMatch && tempDBYWAllJCList1.size() > 0){
															 isMatch = false;
														 }
														 
														 if(!isMatch){
															 if(tempDBYWAllJCList1.size() > 0){
																 List<Field> dtoDBYWFieldList=ReflectOperation.getOneToManyField(tempDBYWAllJCList1.get(0).getClass());
																 for (Field fieldForDBYW : dtoDBYWFieldList) {
																	 for (Map.Entry<String, String> mxDBYWField : XDYWZLForMXField.entrySet()) {
																		 if(mxDBYWField.getKey().contains(ReflectOperation.getGenericClass(fieldForDBYW.getGenericType()).getName())){
																			 Collection<Object> objectList = (Collection<Object>)ReflectOperation.getFieldValue(tempDBYWAllJCList1.get(0), fieldForDBYW.getName());
																			 
																			 int objectCount = 0;
																			 
																			 boolean isJCCorrect = true;
																			 for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
																				    String typeValue = ReflectOperation.getFieldValue(tempDBYWAllJCList1.get(0), entry.getKey()).toString();
																					if(entry.getValue().indexOf(",") > -1){
																						if(entry.getValue().contains(typeValue)){
																							isJCCorrect = false;
																							break;
																						}
																						
																					}
																					else{
																						if(!typeValue.equals(entry.getValue())){
																							isJCCorrect = false;
																							break;
																						}
																					}
																				}
																			 
																			 if(isJCCorrect){
																				 for(Object o : objectList){
																					 boolean isTypeCorrect = true;
																					 for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
																						 if(entry.getKey().equals("RPTCheckType") || entry.getKey().equals("RPTSendType") || entry.getKey().equals("RPTFeedbackType")){
																								
																								 String typeValue="";
																								 if(ReflectOperation.getFieldValue(o, entry.getKey())!=null){
																									 typeValue = ReflectOperation.getFieldValue(o, entry.getKey()).toString();
																								 }
																							  
																								if(entry.getValue().indexOf(",") > -1){
																									if(!entry.getValue().contains(typeValue)){
																										isTypeCorrect = false;
																										break;
																									}
																									
																								}
																								else{
																									if(!typeValue.equals(entry.getValue())){
																										isTypeCorrect = false;
																										break;
																									}
																								}
																							}
																					}
																					 if(isTypeCorrect){
																						 objectCount ++;
																					 }
																				 }
																			 }
																			 
																			 if(objectCount > 0 && mxCount != objectCount){
																				 isMatch=true;
																				 break;
																			 }
																		 }
																	 }
																	 if(isMatch){
																		 break;
																	 }
																 }
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
												}else{//担保业务（业务种类为0）
													
													 String XXJLCZLX=String.valueOf(ReflectOperation.getFieldValue(objectCurrentSendJC, "XXJLCZLX"));
													 if(XXJLCZLX==null){
														 XXJLCZLX = "";
													 }
													 if(!XXJLCZLX.equals("4")){
														 List<Object> objectAllXDYWMXList =new ArrayList<Object>();
														 String strCurrentSendDBBZ="";
														 if(ReflectOperation.getFieldValue(objectCurrentSendMx, "HTYXZT")!=null){
															 strCurrentSendDBBZ=ReflectOperation.getFieldValue(objectCurrentSendMx, "HTYXZT").toString();//当前上报担保业务担保标志
														 }
														 
														 singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
														 detachedCriteria = DetachedCriteria.forClass(Class.forName(GetDtoByYWZLCode.get(ReflectOperation.getFieldValue(objectCurrentSendJC, "XDYWZL"))));
														 
														 for (Map.Entry<String, String> jcFieldName : XDYWZLForJCField.entrySet()) {
																if(jcFieldName.getKey().equals(GetDtoByYWZLCode.get(ReflectOperation.getFieldValue(objectCurrentSendJC, "XDYWZL")))){
																	String[] fieldJc=jcFieldName.getValue().split(",");
																	for(String strFieldJc:fieldJc){
																		if(!strFieldJc.split("-")[0].equals("XDYWZL")){
																			detachedCriteria.add(Restrictions.eq(strFieldJc.split("-")[1],ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[0])));
																		}
																	}
																	break;
																}
														  }
														 
														 List<Object> objectXDYWJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
														 boolean isMatchYES=false;
														 boolean isMatchNo=false;
														 for (Object objectXDYWJC : objectXDYWJCList) {
															 List<Field> dtoXDYWFieldList=ReflectOperation.getOneToManyField(objectXDYWJC.getClass());
															 for (Field fieldForXDYW : dtoXDYWFieldList) {
																 for (Map.Entry<String, String> mxXDYWField : XDYWZLForMXField.entrySet()) {
																	 if(mxXDYWField.getKey().contains(ReflectOperation.getGenericClass(fieldForXDYW.getGenericType()).getName())){//ReflectOperation.getGenericClass(fieldForXDYW.getGenericType()).getName().equals(mxXDYWField.getKey())
																		 detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(fieldForXDYW.getGenericType()));
																		 detachedCriteria.add(Restrictions.eq("FOREIGNID",objectXDYWJC));
																		 detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
																		 //detachedCriteria.add(Restrictions.eq("RPTSendType","2"));
																		 detachedCriteria.add(Restrictions.le("extend2",currentYWFSRQ));
																		 detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
																		 
																		 if(fieldMx!=null){
																			 for(String strFieldMx:fieldMx){
																				 detachedCriteria.add(Restrictions.eq(strFieldMx.split("-")[0],ReflectOperation.getFieldValue(objectCurrentSendMx,strFieldMx.split("-")[1])));
																			 }
																		 }
																		 
																		 objectAllXDYWMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
																		 String strDBBZ="";
																		 if(objectAllXDYWMXList.size()>0){
																			 if(ReflectOperation.getFieldValue(objectAllXDYWMXList.get(0), "DBBZ")!=null){
																				 strDBBZ=ReflectOperation.getFieldValue(objectAllXDYWMXList.get(0), "DBBZ").toString();
																			 }
																			 
																			 if(strCurrentSendDBBZ.equals("1")){//担保业务 是否担保为"是"
																				 if(strDBBZ.equals("1")){//信贷业务是否担保为"是"
																					 isMatchYES=true;
																				 }
																			 }else if(strCurrentSendDBBZ.equals("2")){//担保业务  是否担保为"否"
																				 if(strDBBZ.equals("2")){//信贷业务是否担保为"是"
																					 isMatchNo=true;
																				  }
																			 }
																		 }
																	 }
																 }
															 }
														 }
														 if(strCurrentSendDBBZ.equals("1")){
															 if(!isMatchYES){
																 String strYWZL=entryYWZL.getValue();
																 String strZHTBH="";
																 for (Map.Entry<String, String> jcFieldName : XDYWZLForJCField.entrySet()) {
																		if(jcFieldName.getKey().equals(baseDto)){
																			String[] fieldJc=jcFieldName.getValue().split(",");
																			for(String strFieldJc:fieldJc){
																				if(strFieldJc.split("-")[1].equals("ZHTBH")){
																					if(ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[0])!=null){
																						strZHTBH=ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[0]).toString();
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
														 }else  if(strCurrentSendDBBZ.equals("2")){
															 if(!isMatchNo){
																 String strYWZL=entryYWZL.getValue();
																 String strZHTBH="";
																 for (Map.Entry<String, String> jcFieldName : XDYWZLForJCField.entrySet()) {
																		if(jcFieldName.getKey().equals(baseDto)){
																			String[] fieldJc=jcFieldName.getValue().split(",");
																			for(String strFieldJc:fieldJc){
																				if(strFieldJc.split("-")[0].equals("ZHTBH")){
																					if(ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[0])!=null){
																						strZHTBH=ReflectOperation.getFieldValue(objectCurrentSendJC, strFieldJc.split("-")[0]).toString();
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
												}
											}
											break;
										}
									}
								}
							}
							break;
						}
						 break;
					}
				 }
			 }
		 }
		 String msg="";
		 if(strYWZLAndZHTBHList.size()>0){
			 String strXDYWAndDBYW="\r\n存在信贷业务与担保业务无法匹配数据，是否继续生成报文？";
			 alterMessageXDYWAndDBYW+="\r\n-------------------------------------------------";
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
	 * 校验4：重大关注信息校验
	 * @param selectReport
	 * @param xxjllxMap
	 * @param reportMap
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String GetZDGXXXMessage(String[] selectReport,Map<String, String> xxjllxMap, Map<String, String> reportMap,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		 
		 String strZDGZXX="\r\n存在重大关注信息，是否继续生成报文？";
		 String alterMessageZDGZXX=""; //存在重大关注信息提示
		 boolean isCount=false; //统计是否统计过标题
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		 
		//重大关注信息提示
		 Map<String,String> ZDGZXXWithTableName=ShowContext.getInstance().getShowEntityMap().get("ZDGZXXWithTableName");
		 Map<String,String> ZDGZXXWithTableNameForWJFL=ShowContext.getInstance().getShowEntityMap().get("ZDGZXXWithTableNameForWJFL");
		 
		 for (int i = 0; i < selectReport.length; i++) {
			 String report = selectReport[i];
			 if (!report.equals("51")) {
				 String[] xxjllxList = xxjllxMap.get(report).split(",");
				 String reportDto = reportMap.get(xxjllxList[0]);
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
				List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});

				for (Object objectjc : objectList) {
					List<Field> dtoFieldList=ReflectOperation.getOneToManyField(baseDto);
					 for (Field field : dtoFieldList) {
						 int objectMXListTemp=0; //统计明细总条数
						 if(ZDGZXXWithTableName.containsKey(ReflectOperation.getGenericClass(field.getGenericType()).getName())){
							
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
							 for (Map.Entry<String, String> entryWJFL : ZDGZXXWithTableNameForWJFL.entrySet()) {
								 if(ReflectOperation.getGenericClass(field.getGenericType()).getName().equals(entryWJFL.getKey())){
									 detachedCriteria.add(Restrictions.in("WJFL", new String[]{"2","3","4","5"}));
								 }
							 }
							
						    objectMXListTemp =(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						    if(objectMXListTemp>0){
								if(!isCount){
									isCount=true;
									alterMessageZDGZXX+="\r\n-------------------------------------------------";
									alterMessageZDGZXX+="\r\n业务类型                                               编号                    ";
								}
								
								String strValue =ZDGZXXWithTableName.get(ReflectOperation.getGenericClass(field.getGenericType()).getName()).split(",")[0];   //entry.getValue().split(",")[0];
								String ZHTBH="";
								if(ReflectOperation.getFieldValue(objectjc, ZDGZXXWithTableName.get(ReflectOperation.getGenericClass(field.getGenericType()).getName()).split(",")[1])!=null){
									ZHTBH=ReflectOperation.getFieldValue(objectjc, ZDGZXXWithTableName.get(ReflectOperation.getGenericClass(field.getGenericType()).getName()).split(",")[1]).toString();
								}
								String strAlter="";
								for (int k = strValue.length(); k<8; k++) {
									strValue += "   ";
								}
								strAlter="\r\n"+strValue+"                         "+ZHTBH+"          ";
									alterMessageZDGZXX+=strAlter;
						    }
						}
						
					}
				}
					  
			 }
		 }
		String msg="";
		if(!alterMessageZDGZXX.equals("")){
			msg=strZDGZXX+alterMessageZDGZXX;
		}
		 
		return msg;
	}

	
}
