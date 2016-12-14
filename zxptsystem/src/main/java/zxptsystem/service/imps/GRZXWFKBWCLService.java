package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;

import report.service.imps.CommonUpdateReportStatusService;

import zxptsystem.dto.AutoDTO_GRZXLSYQJLGX;
import zxptsystem.dto.AutoDTO_GRZXLSYQJLSC;
import zxptsystem.dto.AutoDTO_GRZXSJSC;
import zxptsystem.dto.AutoDTO_GRZXZHBSBG;
import zxptsystem.dto.AutoDTO_GR_GRXX_JC;
import zxptsystem.dto.GRZXWFKBWCL;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;
/**
 * 个人征信无反馈报文处理
 * @author xiajieli
 *
 */
public class GRZXWFKBWCLService implements IObjectResultExecute {

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		String[] selectReport = (String[])RequestManager.getIdList();
		if(selectReport==null){
			messageResult.getMessageSet().add("请至少选择一项");
		}else{
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			List<GRZXWFKBWCL> gRZXWFKBWCLList=new ArrayList<GRZXWFKBWCL>();
			for (String gRZXWFKBWCLId : selectReport) {
				GRZXWFKBWCL gRZXWFKBWCL= (GRZXWFKBWCL)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{GRZXWFKBWCL.class.getName(),gRZXWFKBWCLId,null});
				gRZXWFKBWCLList.add(gRZXWFKBWCL);
			}
			if(gRZXWFKBWCLList.size()>0){
				for (GRZXWFKBWCL gRZXWFKBWCL : gRZXWFKBWCLList) {
					boolean isUpdated=false;//是否改变个人征信基础段和明细段的回执状态
					List<Object> object_JcList=new ArrayList<Object>();
					List<AutoDTO_GRZXZHBSBG> gRZXZHBSBGList=new ArrayList<AutoDTO_GRZXZHBSBG>();//更新
					List<AutoDTO_GRZXSJSC> gRZXSJSCList=new ArrayList<AutoDTO_GRZXSJSC>();//删除
					
					List<AutoDTO_GRZXLSYQJLGX> gRZXLSYQJLGXList=new ArrayList<AutoDTO_GRZXLSYQJLGX>();//历史更新
					List<AutoDTO_GRZXLSYQJLSC> gRZXLSYQJLSCList=new ArrayList<AutoDTO_GRZXLSYQJLSC>();//历史删除
					
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
					
					if(gRZXWFKBWCL.getStrBWCL().equals("1")){//未处理
						if(gRZXWFKBWCL.getStrGrReportType().equals("1")){//正常报文
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GR_GRXX_JC"));
							detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
							object_JcList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(object_JcList.size()>0){
								for (Object object : object_JcList) {
									List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
									boolean isHaveMx = false;
									for (Field field : fieldList) {
										Collection<Object> fieldObjectList=(Collection<Object>)ReflectOperation.getFieldValue(object, field.getName());
										
										for (Object objectMX : fieldObjectList) {
											singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
											detachedCriteria = DetachedCriteria.forClass(objectMX.getClass());
											detachedCriteria.add(Restrictions.eq("extend1", gRZXWFKBWCL.getStrBWM()));
											detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
											List<Object> objectListMx = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
											if(objectListMx.size()>0){
												isHaveMx = true;
												for (Object objMx : objectListMx) {
													ReflectOperation.setFieldValue(objMx, "RPTFeedbackType", "2");
													isUpdated=true;
												}
											}
										}
									}
									if(!isHaveMx){
										if(ReflectOperation.getFieldValue(object, "extend1")!=null && ReflectOperation.getFieldValue(object, "extend1").equals(gRZXWFKBWCL.getStrBWM())){
											ReflectOperation.setFieldValue(object, "RPTFeedbackType", "2");
											isUpdated=true;
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{object_JcList,null});
							}
							
						}else if(gRZXWFKBWCL.getStrGrReportType().equals("4")){//标识变更报文
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXZHBSBG"));
							detachedCriteria.add(Restrictions.eq("extend1", gRZXWFKBWCL.getStrBWM()));
							detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
							gRZXZHBSBGList = (List<AutoDTO_GRZXZHBSBG>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(gRZXZHBSBGList.size()>0){
								for (AutoDTO_GRZXZHBSBG gRZXZHBSBG : gRZXZHBSBGList) {
									ReflectOperation.setFieldValue(gRZXZHBSBG, "RPTFeedbackType", "2");
									
									singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
									detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_GRXX_JC.class);
									detachedCriteria.add(Restrictions.eq("JRJGDM", gRZXZHBSBG.getStrOldJRJGCode()));
									detachedCriteria.add(Restrictions.eq("YWH", gRZXZHBSBG.getStrOldYWCode()));
									List<Object> Object_JCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(Object_JCList.size()>0){
										for (Object obj : Object_JCList) {
											ReflectOperation.setFieldValue(obj, "JRJGDM", gRZXZHBSBG.getStrNewJRJGCode());
											ReflectOperation.setFieldValue(obj, "YWH", gRZXZHBSBG.getStrNewYWCode());
										}
										IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
										singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{Object_JCList,null});
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXZHBSBGList,null});
							}
							
						}else if(gRZXWFKBWCL.getStrGrReportType().equals("8")){//数据删除报文
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXSJSC"));
							detachedCriteria.add(Restrictions.eq("extend1", gRZXWFKBWCL.getStrBWM()));
							detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
							gRZXSJSCList = (List<AutoDTO_GRZXSJSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(gRZXSJSCList.size()>0){
								for (AutoDTO_GRZXSJSC autoDTOGRZXSJSC : gRZXSJSCList) {
									ReflectOperation.setFieldValue(autoDTOGRZXSJSC, "RPTFeedbackType", "2");
									
									singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
									detachedCriteria = DetachedCriteria.forClass(AutoDTO_GR_GRXX_JC.class);
									//若“起始结算/应还款日期”不为“20000101”，“结束结算/应还款日期”不为空，删除从“起始结算/应还款日期”所在月（含所在月）到“结束结算/应还款日期”所在月（含所在月）之间的账户的交易记录
									if(!autoDTOGRZXSJSC.getQSJSYHKRQ().equals("20000101") && (autoDTOGRZXSJSC.getJSJSYHKRQ()!=null || !autoDTOGRZXSJSC.getJSJSYHKRQ().equals(""))){
										detachedCriteria.add(Restrictions.eq("JRJGDM", autoDTOGRZXSJSC.getStrJRJGCode()));
										detachedCriteria.add(Restrictions.eq("YWH", autoDTOGRZXSJSC.getStrYWCode()));
										detachedCriteria.add(Restrictions.ge("JSYHKRQ", autoDTOGRZXSJSC.getQSJSYHKRQ()));
										detachedCriteria.add(Restrictions.le("JSYHKRQ", autoDTOGRZXSJSC.getJSJSYHKRQ()));
									}
									//若“起始结算/应还款日期”不为“20000101”，“结束结算/应还款日期”为空，删除“起始结算/应还款日期”所在月（含所在月）之后的账户的交易记录。
									else if(!autoDTOGRZXSJSC.getQSJSYHKRQ().equals("20000101") && (autoDTOGRZXSJSC.getJSJSYHKRQ()==null || autoDTOGRZXSJSC.getJSJSYHKRQ().equals(""))){
										detachedCriteria.add(Restrictions.eq("JRJGDM", autoDTOGRZXSJSC.getStrJRJGCode()));
										detachedCriteria.add(Restrictions.eq("YWH", autoDTOGRZXSJSC.getStrYWCode()));
										detachedCriteria.add(Restrictions.ge("JSYHKRQ", autoDTOGRZXSJSC.getQSJSYHKRQ()));
									}
									//若“起始结算/应还款日期”为“20000101”，“结束结算/应还款日期”不为空，删除账户从开立时到“结束结算/应还款日期”所在月（含所在月）的交易记录
									else if(autoDTOGRZXSJSC.getQSJSYHKRQ().equals("20000101") && (autoDTOGRZXSJSC.getJSJSYHKRQ()!=null || !autoDTOGRZXSJSC.getJSJSYHKRQ().equals(""))){
										detachedCriteria.add(Restrictions.eq("JRJGDM", autoDTOGRZXSJSC.getStrJRJGCode()));
										detachedCriteria.add(Restrictions.eq("YWH", autoDTOGRZXSJSC.getStrYWCode()));
										detachedCriteria.add(Restrictions.le("JSYHKRQ", autoDTOGRZXSJSC.getJSJSYHKRQ()));
									}
									//若“起始结算/应还款日期”为“20000101”，“结束结算/应还款日期”为空，删除账户在数据库中的所有信息，包括交易记录和身份信息。
									else if(autoDTOGRZXSJSC.getQSJSYHKRQ().equals("20000101") && (autoDTOGRZXSJSC.getJSJSYHKRQ()==null || autoDTOGRZXSJSC.getJSJSYHKRQ().equals(""))){
										detachedCriteria.add(Restrictions.eq("JRJGDM", autoDTOGRZXSJSC.getStrJRJGCode()));
										detachedCriteria.add(Restrictions.eq("YWH", autoDTOGRZXSJSC.getStrYWCode()));
									}
									
									object_JcList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(object_JcList.size()>0){
										for (Object object : object_JcList) {
											ReflectOperation.setFieldValue(object, "RPTFeedbackType","5");
											isUpdated=true;
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
										singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{object_JcList,null});
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXSJSCList,null});
							}
						}
						else if(gRZXWFKBWCL.getStrGrReportType().equals("9")){//历史逾期更新报文
							
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXLSYQJLGX"));
							detachedCriteria.add(Restrictions.eq("extend1", gRZXWFKBWCL.getStrBWM()));
							detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
							gRZXLSYQJLGXList = (List<AutoDTO_GRZXLSYQJLGX>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(gRZXLSYQJLGXList.size()>0){
								for (AutoDTO_GRZXLSYQJLGX gRZXLSYQJLGX : gRZXLSYQJLGXList) {
									ReflectOperation.setFieldValue(gRZXLSYQJLGX, "RPTFeedbackType", "2");
									
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
											
											IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
											singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{objectJCList.get(0),null});
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXLSYQJLGXList,null});
							}
						}
						else if(gRZXWFKBWCL.getStrGrReportType().equals("A")){//历史逾期删除报文
							
							DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GRZXLSYQJLSC"));
							detachedCriteria.add(Restrictions.eq("extend1", gRZXWFKBWCL.getStrBWM()));
							detachedCriteria.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3"}));
							gRZXLSYQJLSCList = (List<AutoDTO_GRZXLSYQJLSC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
							if(gRZXLSYQJLSCList.size()>0){
								for (AutoDTO_GRZXLSYQJLSC gRZXLSYQJLSC : gRZXLSYQJLSCList) {
									ReflectOperation.setFieldValue(gRZXLSYQJLSC, "RPTFeedbackType", "2");
									
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
											IParamVoidResultExecute singleObjectSaveOUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
											singleObjectSaveOUpdateAllDao.paramVoidResultExecute(new Object[]{objectJCList.get(0),null});
										}
									}
								}
								IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXLSYQJLSCList,null});
							}
						}
						ReflectOperation.setFieldValue(gRZXWFKBWCL, "strBWCL", "2");
					}
					
					if(isUpdated){
						//统计基础段的回执状态
						if(object_JcList.size()>0){
							for (Object object : object_JcList) {
								int unRPTFeedbackType=0;
								int rPTFeedbackTypeSuccess=0;
								int rPTFeedbackTypefalse=0;
								List<Field> fieldList=ReflectOperation.getOneToManyField(object.getClass());
								for (Field field : fieldList) {
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GR_GRXX_JC"));
									
									CommonUpdateReportStatusService CommonStatus=new CommonUpdateReportStatusService();
									rPTFeedbackTypeSuccess += CommonStatus.findByDetachedCriteriaForJC("RPTFeedbackType", "2", detachedCriteria, object, field.getGenericType(), singleObjectFindByCountDao);
									
									rPTFeedbackTypefalse += CommonStatus.findByDetachedCriteriaForJC("RPTFeedbackType", "3", detachedCriteria, object, field.getGenericType(), singleObjectFindByCountDao);
									
									unRPTFeedbackType += CommonStatus.findByDetachedCriteriaForJC("RPTFeedbackType", "1", detachedCriteria, object, field.getGenericType(), singleObjectFindByCountDao);
								}
								if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType==0){
									continue;
								}
								else if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType>0){
									ReflectOperation.setFieldValue(object, "RPTFeedbackType","1");
								}else if(rPTFeedbackTypeSuccess>0 && rPTFeedbackTypefalse==0 && unRPTFeedbackType==0){
									ReflectOperation.setFieldValue(object, "RPTFeedbackType","2");
								}
								else if(rPTFeedbackTypeSuccess==0 && rPTFeedbackTypefalse>0 && unRPTFeedbackType==0 ){
									ReflectOperation.setFieldValue(object, "RPTFeedbackType","3");
								}
								else{
									ReflectOperation.setFieldValue(object, "RPTFeedbackType","4");
								}
							}
							IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
							singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{object_JcList,null});
						}
						
						//统计任务层的状态
						String JRJGDM=gRZXWFKBWCL.getStrBWM().substring(0, 14);
						List<InstInfo> instInfoSubList = new ArrayList<InstInfo>();
						CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
						instInfoSubList=commonStatus.getInstInfoSubList(JRJGDM);
						if(gRZXWFKBWCL.getStrGrReportType().equals("1")){
							commonStatus.commonUpdateReportStatus(object_JcList, instInfoSubList);
						}else if(gRZXWFKBWCL.getStrGrReportType().equals("4")){
							commonStatus.commonUpdateReportStatus(gRZXZHBSBGList, instInfoSubList);
						}else if(gRZXWFKBWCL.getStrGrReportType().equals("8")){
							commonStatus.commonUpdateReportStatus(gRZXSJSCList, instInfoSubList);
							commonStatus.commonUpdateReportStatus(object_JcList, instInfoSubList);
						}
						else if(gRZXWFKBWCL.getStrGrReportType().equals("9")){
							commonStatus.commonUpdateReportStatus(gRZXLSYQJLGXList, instInfoSubList);
						}
						else if(gRZXWFKBWCL.getStrGrReportType().equals("A")){
							commonStatus.commonUpdateReportStatus(gRZXLSYQJLSCList, instInfoSubList);
						}
					}
				}
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{gRZXWFKBWCLList,null});
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
}
