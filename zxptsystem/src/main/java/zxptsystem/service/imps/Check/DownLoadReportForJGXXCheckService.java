package zxptsystem.service.imps.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;
import framework.dao.imps.OrderBySqlFormula;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

/**
 * 机构信息报文级数据校验
 * 成报文时对报文级数据的校验
 * @author xiajieli
 *
 */
public class DownLoadReportForJGXXCheckService  {

	/**
	 * 1031:同一报数机构(金融机构代码)报送的同一机构(贷款卡编码)的股东持股比例之和不能大于100%
	 * @param selectReport 报文序号（选择的报文种类）
	 * @param reportMapForJG 报文信息记录组成
	 * @param instInfoSubList 所属机构
	 * @param downloadJudgeStatusMap 生成报文过滤条件
	 * @return 错误message
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getJGXXCheck_1031(String[] selectReport,Map<String, String> reportMapForJG,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String orderbySqlFormula,Object XXGXRQ) throws Exception {
		
		String strMes="";
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForJG.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			if (report.equals("5100")) {
				if(baseDto.equals("zxptsystem.dto.AutoDTO_JG_JGJBXX_JC")){
					//得到当前待上报的基础段
					List<Object> obj_CurrentSendJCList= HelpChecker.getCurrentSendJCList(baseDto, instInfoSubList, downloadJudgeStatusMap);
					
					for (Object objJC : obj_CurrentSendJCList) {
						//获取待上报及已上报的明细段
						List<Object> objectMXList=HelpChecker.GetMXConditionData(objJC, "zxptsystem.dto.AutoDTO_JG_ZYGD");
						
						List<String> GDZJLXAndGDZJHMList=new ArrayList<String>();
						double sumZGBL=0.00;
						for(Object objectMX : objectMXList){
							
							String GDZJLX="";//证件类型
							String GDZJHM="";//证件号码
							
							if(ReflectOperation.getFieldValue(objectMX, "GDZJLX")!=null && !ReflectOperation.getFieldValue(objectMX, "GDZJLX").toString().equals("")){
								GDZJLX=ReflectOperation.getFieldValue(objectMX, "GDZJLX").toString();
							}
							if(ReflectOperation.getFieldValue(objectMX, "GDZJHM")!=null && !ReflectOperation.getFieldValue(objectMX, "GDZJHM").toString().equals("")){
								GDZJHM=ReflectOperation.getFieldValue(objectMX, "GDZJHM").toString();
							}
							
							if(!GDZJLXAndGDZJHMList.contains(GDZJLX + "-" +GDZJHM)){
								GDZJLXAndGDZJHMList.add(GDZJLX + "-" +GDZJHM);
								
								DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_JG_ZYGD"));
								detachedCriteria.add(Restrictions.eq("GDZJLX", GDZJLX));
								detachedCriteria.add(Restrictions.eq("GDZJHM", GDZJHM));
								detachedCriteria.add(Restrictions.eq("FOREIGNID",objJC));
								detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
								detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
								detachedCriteria.add(Restrictions.le("extend2", XXGXRQ));
								detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(orderbySqlFormula));
								List<Object>  objectMXTmpList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								
								if(objectMXTmpList.size()>0){
									double ZGBL=0.00;
									if(ReflectOperation.getFieldValue(objectMXTmpList.get(0), "GDCZJE")!=null){
										ZGBL=TypeParse.parseDouble(ReflectOperation.getFieldValue(objectMXTmpList.get(0), "GDCZJE").toString());
									}
									sumZGBL=sumZGBL+ZGBL;
								}
								
							}
							
						}
						java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");  
						sumZGBL=Double.parseDouble(df.format(sumZGBL));
						if(sumZGBL > 100){
							strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objJC);
						}
							
					}
					
				}
			}
			
		}
		
		if(strLogicPrmaryKeyValueList.size() >0){
			String getMsgByErrorCode1031=ShowContext.getInstance().getShowEntityMap().get("JG_CCXX").get("1031");
			 strMes+="\r\n\r\n"+getMsgByErrorCode1031;
			 strMes += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				 strMes += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			 }
		 }
			
		return strMes;
	}

	/**
	 * 1028:当客户类型为基本户且基本户状态不为“待审核”时，开户核准号未填写；
	 * @param selectReport
	 * @param reportMapForJG
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getJGXXCheck_1028(String[] selectReport,Map<String, String> reportMapForJG, List<InstInfo> instInfoSubList,
			Map<String, String> downloadJudgeStatusMap) throws Exception {
		
		String strMes="";
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForJG.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			if (report.equals("5100")) {
				if(baseDto.equals("zxptsystem.dto.AutoDTO_JG_JGJBXX_JC")){
					
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
					detachedCriteria.add(Restrictions.in("KHLX", new String[]{"1","3"}));//客户类型为1,3
					detachedCriteria.add(Restrictions.or(Restrictions.isEmpty("KHXKZHZH"), Restrictions.eq("KHXKZHZH", "")));//开户许可证核准号为空
					List<Object> obj_CurrentSendJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					for (Object objectCurrentSendJC : obj_CurrentSendJCList) {
						IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
						detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_JG_JGZT"));
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
						detachedCriteria.add(Restrictions.ne("JBHZT", "4"));//基本户状态不为待审核
						int  objectMXCount=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						
						if(objectMXCount > 0){
							strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
						}
						
					}
					
				}
			}
			
		}
		
		if(strLogicPrmaryKeyValueList.size() >0){
			String getMsgByErrorCode1031=ShowContext.getInstance().getShowEntityMap().get("JG_CCXX").get("1028");
			 strMes+="\r\n\r\n"+getMsgByErrorCode1031;
			 strMes += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				 strMes += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			 }
		 }
			
		return strMes;
	}

	
	
}
