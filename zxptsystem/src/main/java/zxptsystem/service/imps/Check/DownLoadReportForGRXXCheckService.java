package zxptsystem.service.imps.Check;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import zxptsystem.service.imps.Check.HelpChecker;

import coresystem.dto.InstInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

/**
 * 个人征信报文级数据校验（生成报文时对报文级数据的校验）
 * @author xiajieli
 *
 */
public class DownLoadReportForGRXXCheckService  {
	
	
	/**
	 * 最早结算应还款日期
	 * @param JSYHKRQList
	 * @return
	 */
	public int getZZJSYHKRQ(ArrayList<String> JSYHKRQList){
		int i,min;
		min=Integer.parseInt(JSYHKRQList.get(0).toString());
		for(i=0;i<JSYHKRQList.size();i++){
			if(Integer.parseInt(JSYHKRQList.get(i).toString())<min){
				min=Integer.parseInt(JSYHKRQList.get(i).toString());
			}
		}
		return min;
	}

	/**
	 * 最晚结算应还款日期
	 * @param JSYHKRQList
	 * @return
	 */
	public int getZWJSYHKRQ(ArrayList<String> JSYHKRQList){
		int i,max;
		max=Integer.parseInt(JSYHKRQList.get(0).toString());
		for(i=0;i<JSYHKRQList.size();i++){
			if(Integer.parseInt(JSYHKRQList.get(i).toString())>max){
				max=Integer.parseInt(JSYHKRQList.get(i).toString());
			}
		}
		return max;
	}
	
	
	
	/**
	 * 003每条账户记录最多只能有一个身份信息段、职业信息段、居住地址信息段、交易标识变更段、特殊交易段
	 * @param selectReport 报文序号（选择的报文种类）
	 * @param reportMapForGR 报文信息记录组成
	 * @param instInfoSubList 所属机构
	 * @param downloadJudgeStatusMap 生成报文过滤条件
	 * @return
	 * @throws Exception
	 */
	public String getGRXXCheck_003(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		String[] strMXTableNames=new String[]{"zxptsystem.dto.AutoDTO_GR_GRSFXX","zxptsystem.dto.AutoDTO_GR_ZYXX","zxptsystem.dto.AutoDTO_GR_JZDZ","zxptsystem.dto.AutoDTO_GR_JYBSBG","zxptsystem.dto.AutoDTO_GR_TSJY"};
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报的所有基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJCList(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					
					//得到当前上报的所有明细段
					for (String strMXTableName : strMXTableNames) {
						int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXCount(objectCurrentSendJC,strMXTableName,downloadJudgeStatusMap);
						
						if(objectCurrentSendMXCount > 1){
							strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
						 }
					}
					
				}
				
			}
			
		}
		
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("0000003");
			strmsg="\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	
	/**
	 * 004同一条账户记录中若包含多个担保信息段，任意两个担保信息段的“姓名”、“证件类型、“证件号码”不能完全相同
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	public String getGRXXCheck_004(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报的所有基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJCList(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				
				String strMXTableName="zxptsystem.dto.AutoDTO_GR_DBXX";
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					
					//得到当前上报的所有明细段
					List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,strMXTableName,downloadJudgeStatusMap);
					
					if(objectCurrentSendMXList.size()>1){
						for(Object mxobj:objectCurrentSendMXList){
							String XM = null;
							String ZJLX = null;
							String ZJHM = null;
							if(ReflectOperation.getFieldShowValue(mxobj, "XM")!=null){
								XM=ReflectOperation.getFieldShowValue(mxobj, "XM").toString();
							}
							if(ReflectOperation.getFieldShowValue(mxobj, "ZJLX")!=null){
								ZJLX=ReflectOperation.getFieldShowValue(mxobj, "ZJLX").toString();
							}
							if(ReflectOperation.getFieldShowValue(mxobj, "ZJHM")!=null){
								ZJHM=ReflectOperation.getFieldShowValue(mxobj, "ZJHM").toString();
							}
							
							Map<String,Object> fieldValueMap=new HashMap<String,Object>();
							fieldValueMap.put("XM", XM);
							fieldValueMap.put("ZJLX", ZJLX);
							fieldValueMap.put("ZJHM", ZJHM);
							int objectCurrentSendMXByConditionCount = HelpChecker.getCurrentSendMXByConditionCount(objectCurrentSendJC,fieldValueMap,strMXTableName,downloadJudgeStatusMap);
							
							if(objectCurrentSendMXByConditionCount > 1){
								strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
							}
						}
							
					  }
					
				}
				
			}
			
		}
		
		String strmsg="";
		
		if(strLogicPrmaryKeyValueList.size()>0){
			String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("0000004");
			strmsg+="\r\n\r\n"+getMsgByErrorCode;
			strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			}
		}
		return strmsg;
	}
	
	/**
	 * 005若报文含有交易标识变更段，则交易标识变更段中“金融机构代码”+“业务号”不能和基础段中“金融机构代码”+“业务号”相同
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	public String getGRXXCheck_005(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报的所有基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJCList(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				
				String strMXTableName="zxptsystem.dto.AutoDTO_GR_JYBSBG";
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					
					//得到当前上报的所有明细段
					List<Object> objectCurrentSendMXList = HelpChecker.getCurrentSendMXList(objectCurrentSendJC,strMXTableName,downloadJudgeStatusMap);
					
					for(Object mxobj:objectCurrentSendMXList){
						String mxJRJGDM="";
						String mxYWH="";
						if(ReflectOperation.getFieldValue(mxobj, "JRJGDM")!=null){
							mxJRJGDM=ReflectOperation.getFieldValue(mxobj, "JRJGDM").toString();
						}
						if(ReflectOperation.getFieldValue(mxobj, "YWH")!=null){
							mxYWH=ReflectOperation.getFieldValue(mxobj, "YWH").toString();
						}
						String mxJRJGDMAndYWH=mxJRJGDM +","+mxYWH;
						
						String jcJRJGDM="";
						String jcYWH="";
						if(ReflectOperation.getFieldValue(objectCurrentSendJC, "JRJGDM")!=null){
							jcJRJGDM=ReflectOperation.getFieldValue(objectCurrentSendJC, "JRJGDM").toString();
						}
						if(ReflectOperation.getFieldValue(objectCurrentSendJC, "YWH")!=null){
							jcYWH=ReflectOperation.getFieldValue(objectCurrentSendJC, "YWH").toString();
						}
						String jcJRJGDMAndYWH=jcJRJGDM +","+jcYWH;
						
						if(mxJRJGDMAndYWH.equals(jcJRJGDMAndYWH)){
							strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
						}

					}
								
				}
				
			}
			
		}
		
		String strmsg="";
		 if(strLogicPrmaryKeyValueList.size() >0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("0000005");
			 strmsg+="\r\n\r\n"+getMsgByErrorCode;
			 strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			 }
		 }
		return strmsg;
	}
	
	
	
	/**
	 * 008当基础段中的账户拥有者信息为“2-新账户开立”时，不能包含交易标识变更段
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	public String getGRXXCheck_008(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报账户拥有者信息为“2-新账户开立”的基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJC008_065List(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
						
					//得到当前上报的所有明细段
					int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXCount(objectCurrentSendJC,"zxptsystem.dto.AutoDTO_GR_JYBSBG",downloadJudgeStatusMap);
					
					if(objectCurrentSendMXCount > 0){
						strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
					}
					
				}
				
			}
			
		}
		
		String strmsg="";
		 if(strLogicPrmaryKeyValueList.size() >0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("0000008");
			 strmsg+="\r\n\r\n"+getMsgByErrorCode;
			 strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			 }
		 }
		return strmsg;
	}
	
	/**
	 * 015:基础段中“开户日期”<=“报文生成时间”中的日期
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getGRXXCheck_015(String[] selectReport,Map<String, String> reportMapForGR, List<InstInfo> instInfoSubList,
			Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		String strReportDate=TypeParse.parseString(new Date(),"yyyyMMdd"); //报文生成时间
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报的所有基础段
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(baseDto));
				detachedCriteria.add(Restrictions.like("JSYHKRQ", objSJFSNY+"%"));  
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
				detachedCriteria.add(Restrictions.gt("KHRQ", strReportDate));
				List<Object> objectCurrentSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
				}
				
			}
			
		}
		
		String strmsg="";
		 if(strLogicPrmaryKeyValueList.size() >0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("2101015");
			 strmsg+="\r\n\r\n"+getMsgByErrorCode;
			 strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			 }
		 }
		return strmsg;
	}
	
	
	/**
	 * 065当基础段“账户拥有者信息提示”字段值为“2-新账户开立”时，账户记录必须包括身份信息段、职业信息段、居住地址段
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	public String getGRXXCheck_065(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		String[] strMXTableNames=new String[]{"zxptsystem.dto.AutoDTO_GR_GRSFXX","zxptsystem.dto.AutoDTO_GR_ZYXX","zxptsystem.dto.AutoDTO_GR_JZDZ"};
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询账户拥有者信息为“2-新账户开立”上报的所有基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJC008_065List(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					//得到当前上报的所有明细段
					for (String strMXTableName : strMXTableNames) {
						int objectCurrentSendMXCount = HelpChecker.getCurrentSendMXCount(objectCurrentSendJC,strMXTableName,downloadJudgeStatusMap);
						
						if(objectCurrentSendMXCount == 0){
							strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
						}
						
					}
									
				}
				
			}
			
		}
	
		String strmsg="";
		 if(strLogicPrmaryKeyValueList.size() >0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("0000065");
			 strmsg+="\r\n\r\n"+getMsgByErrorCode;
			 strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			  }
		 }
		return strmsg;
	}
	
	
	/**
	 * 027基础段中“结算/应还款日期”>=“最早结算/应还款日期”, “结算/应还款日期”<=“最晚结算/应还款日期”
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	public String getGRXXCheck_027(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报的所有基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJCList(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					
					String JSYHKRQ="";//结算应还款日期
					if(ReflectOperation.getFieldValue(objectCurrentSendJC, "JSYHKRQ")!=null){
						JSYHKRQ=ReflectOperation.getFieldValue(objectCurrentSendJC, "JSYHKRQ").toString();
					}
					
					int ZZJSYHKRQ = 0;
					int ZWJSYHKRQ = 0;
					
					if(objectCurrentSendList.size()>0){
						ArrayList<String> JSYHKRQList = new ArrayList<String>();
						for (Object object : objectCurrentSendList) {
							String JSYHKRQTemp = ReflectOperation.getFieldValue(object, "JSYHKRQ").toString();
							JSYHKRQList.add(JSYHKRQTemp);
						}
						
						ZZJSYHKRQ = this.getZZJSYHKRQ(JSYHKRQList);
						ZWJSYHKRQ=this.getZWJSYHKRQ(JSYHKRQList);
					 }
					
					if(TypeParse.parseInt(JSYHKRQ) < ZZJSYHKRQ || TypeParse.parseInt(JSYHKRQ) > ZWJSYHKRQ){
						strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
					}
						
				}
				
			}
			
		}
		
		String strmsg="";
		 if(strLogicPrmaryKeyValueList.size() >0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("2301027");
			 strmsg+="\r\n\r\n"+getMsgByErrorCode;
			 strmsg += "\r\n--------------------------------------------------------------------------------------------------";
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
				strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
			  }
		 }
		return strmsg;
	}

	/**
	 * 054当基础段中业务种类为“贷款”时，数据库中账户记录前最近一个月的账户状态必须为“结清”以外的值；
	 *       当基础段中业务种类为“信用卡”时，账户记录和数据库中账户记录最近一个月的账户状态不能同为“销户”
	 * @param selectReport
	 * @param reportMapForGR
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getGRXXCheck_054(String[] selectReport,Map<String, String> reportMapForGR,
			List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
		
		List<String> strLogicPrmaryKeyValueList=new ArrayList<String>(); //存储基础段的逻辑主键值
		Map<String,String> pKeyMap=ShowContext.getInstance().getShowEntityMap().get("ZXPTSystemLogicPrimaryKey");//根据得到基础段逻辑主键
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		for (int i = 0; i < selectReport.length; i++) {
			String report = selectReport[i];
			String reportDto = reportMapForGR.get(report);
			String[] reportDtos = reportDto.split(",");
			String baseDto = reportDtos[0].split("%")[1];
			
			if(baseDto.equals("zxptsystem.dto.AutoDTO_GR_GRXX_JC")){
				//查询上报的所有基础段
				List<Object> objectCurrentSendList= HelpChecker.getCurrentSendGRZXJC054List(baseDto, instInfoSubList, downloadJudgeStatusMap,objSJFSNY);
				for (Object objectCurrentSendJC : objectCurrentSendList) {
					
					String YWH="";//业务号 
					String JRJGDM="";//金融机构代码 
					String YWZL="";//业务种类
					String JSYHKRQ="";//结算应还款日期
					if(ReflectOperation.getFieldValue(objectCurrentSendJC, "YWH")!=null){
						YWH=ReflectOperation.getFieldValue(objectCurrentSendJC, "YWH").toString();
					}
					if(ReflectOperation.getFieldValue(objectCurrentSendJC, "JRJGDM")!=null){	
						JRJGDM=ReflectOperation.getFieldValue(objectCurrentSendJC, "JRJGDM").toString();
					}
					
					if(ReflectOperation.getFieldValue(objectCurrentSendJC, "YWZL")!=null){
						YWZL=ReflectOperation.getFieldValue(objectCurrentSendJC, "YWZL").toString();
					}
					
					if(ReflectOperation.getFieldValue(objectCurrentSendJC, "JSYHKRQ")!=null){
						JSYHKRQ=ReflectOperation.getFieldValue(objectCurrentSendJC, "JSYHKRQ").toString();
					}
					
					String autoID=ReflectOperation.getPrimaryKeyValue(objectCurrentSendJC).toString();
					
					 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_GR_GRXX_JC"));
					 detachedCriteria.add(Restrictions.eq("YWH", YWH));
					 detachedCriteria.add(Restrictions.eq("JRJGDM", JRJGDM));
					 detachedCriteria.add(Restrictions.eq("YWZL", YWZL));
					 detachedCriteria.add(Restrictions.ne("autoID", autoID));
					 detachedCriteria.addOrder(Order.desc("JSYHKRQ"));
					 detachedCriteria.add(Restrictions.lt("JSYHKRQ", JSYHKRQ));
					 detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
					 detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
					 List<Object> objectJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
					 if(objectJCList.size()>0){
						 String LS_ZHZT="";//账户状态
						 if(ReflectOperation.getFieldValue(objectJCList.get(0), "ZHZT")!=null){
							 LS_ZHZT=ReflectOperation.getFieldValue(objectJCList.get(0), "ZHZT").toString();
						 }
						 
						if(YWZL.equals("1")){//业务种类为“贷款” 
							if(LS_ZHZT.equals("3")){//账户状态为‘结清’
								strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
							}
						}
						else if(YWZL.equals("2")){//业务种类为“信用卡”
							if(LS_ZHZT.equals("4")){//账户状态为‘销户’
								strLogicPrmaryKeyValueList =HelpChecker.getStrLogicPrmaryKeyValueList(pKeyMap, objectCurrentSendJC);
							}
							
						}	
					 }
					
				}
				
			}
			
		}
		
		String strmsg="";
		 if(strLogicPrmaryKeyValueList.size() >0){
			 String getMsgByErrorCode=ShowContext.getInstance().getShowEntityMap().get("GR_CCXX").get("xxxx054");
			 strmsg+="\r\n\r\n"+getMsgByErrorCode;
			 strmsg += "\r\n--------------------------------------------------------------------------------------------------";
				
			 for (int i=0;i<strLogicPrmaryKeyValueList.size();i++) {
					strmsg += "\r\n("+ (i+1) +")"+strLogicPrmaryKeyValueList.get(i)+"\r\n";
				}
		 }
		return strmsg;
	}

	
}
