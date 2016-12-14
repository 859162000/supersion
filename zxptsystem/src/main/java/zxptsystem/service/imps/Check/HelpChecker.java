package zxptsystem.service.imps.Check;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.InstInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;

/**
 * 报文级校验帮助类
 * @author xiajieli
 *
 */
public  class HelpChecker {

	
	/**
	 * 查询当前待上报的明细段对象列表
	 * @param objectCurrentSendJC
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public  static List<Object> getCurrentSendMXList(Object objectCurrentSendJC,Field field,Map<String, String> downloadJudgeStatusMap) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
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
			List<Object> objectCurrentSendMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		 
		 return objectCurrentSendMXList;
	}
	
	/**
	 * 查询当前待上报的明细段通过dtoName
	 * @param objectCurrentSendJC
	 * @param downloadJudgeStatusMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public  static List<Object> getCurrentSendMXList(Object objectCurrentSendJC,String dtoName,Map<String, String> downloadJudgeStatusMap) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoName));
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
		List<Object> objectCurrentSendMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	 
		return objectCurrentSendMXList;
	}
	

	/**
	 * 查询当前待上报的基础段
	 * @param baseDto
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getCurrentSendJCList(String baseDto,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
		List<Object> objectCurrentSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		return objectCurrentSendList;
	}
	
	/**
	 * 查询当前待上报的基础段通过信息记录操作类型
	 * @param baseDto
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getCurrentSendJCByXXJLCZLXList(String baseDto,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,String[] XXJLCZLX) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
		detachedCriteria.add(Restrictions.in("XXJLCZLX", XXJLCZLX));
		List<Object> objectCurrentSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		return objectCurrentSendList;
	}
	
	
	/**
	 * 根据条件查询当前待上报的基础段
	 * @param baseDto
	 * @param instInfoSubList
	 * @param downloadJudgeStatusMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getCurrentSendJCByConditionList(String baseDto,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Map<String,Object> fieldValueMap) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
		for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
				detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		List<Object> objectCurrentSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		return objectCurrentSendList;
	}

	/**
	 * 获取已上报及待上报的基础段
	 * @param dtoName
	 * @param instInfoSubList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> GetJCConditionData(String dtoName,List<InstInfo> instInfoSubList)  throws Exception  {
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoName));
		 detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
		 detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
		 detachedCriteria.add(Restrictions.eq("RPTSubmitStatus", "2"));
		 detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
		 detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
		 List<Object> objectJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		 return objectJCList;
	 }
	
	/**
	 * 根据条件获取已上报及待上报的基础段
	 * @param dtoName
	 * @param instInfoSubList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> GetJCSendAndSendedConditionData(String dtoName,List<InstInfo> instInfoSubList,Map<String,Object> fieldValueMap)  throws Exception  {
		 IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoName));
		 detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
		 detachedCriteria.add(Restrictions.eq("RPTVerifyType", "2"));
		 detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
		 for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
			detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		 }
		 List<Object> objectJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		 return objectJCList;
	 }


	 /**
	  * 获取已上报及待上报的明细段列表
	  * @param objectJC
	  * @param dtoMXName
	  * @return
	  */
		@SuppressWarnings("unchecked")
		public  static List<Object> GetMXConditionData(Object objectJC, String dtoMXName) throws Exception {
			List<Object> objectMXList =null;
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMXName));
			detachedCriteria.add(Restrictions.eq("FOREIGNID",objectJC));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
			objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectMXList;
	      }
		

		/**
		  * 获取已上报及待上报的明细段条数
		  * @param objectJC
		  * @param dtoMXName
		  * @return
		  */
			public  static int GetMXConditionDataCount(Object objectJC, String dtoMXName) throws Exception {
				IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMXName));
				detachedCriteria.add(Restrictions.eq("FOREIGNID",objectJC));
				detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
				detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
				return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		      }
		
		/**
		 * 根据给定条件查询当前待上报的明细段
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public  static List<Object> getCurrentSendMXByConditionList(Object objectCurrentSendJC,Map<String,String> fieldValueMap,Field field,Map<String, String> downloadJudgeStatusMap) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
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
				for (Map.Entry<String, String> entry : fieldValueMap.entrySet()) {
					detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
				List<Object> objectCurrentSendMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			 
			 return objectCurrentSendMXList;
		}
		
		/**
		 * 获取未处理的（未提交或者未审核）数据的条数（企业）
		 * @param objectName
		 * @param instInfoSubList
		 * @return
		 * @throws Exception
		 */
		public static int GetNoSendDataCount(String objectName, List<InstInfo> instInfoSubList)  throws Exception{
			 IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			 DetachedCriteria detachedCriteriaTemp = DetachedCriteria.forClass(Class.forName(objectName));
			 detachedCriteriaTemp.add(Restrictions.in("instInfo", instInfoSubList));
			 detachedCriteriaTemp.add(Restrictions.in("RPTFeedbackType", new String[]{"1","3","4"}));
			 detachedCriteriaTemp.add(Restrictions.ne("RPTSendType", "2"));
			 int SendedDataCount=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteriaTemp,null});
			return SendedDataCount;
		}
		
		/**
		 * 获取当前待上报数据的条数（企业）
		 * @param objectName
		 * @param instInfoSubList
		 * @return
		 * @throws Exception
		 */
		public static int GetCurrentSendDataCount(String objectName, List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap)  throws Exception{
			IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(objectName));
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
				int objectCurrentSendDataCount=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectCurrentSendDataCount;
		}
		
		
		/**
		 * 根据给定条件查询明细段
		 * @param fieldValueMap
		 * @param mxObject
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public  static List<Object> getMXByConditionList(Map<String,Object> fieldValueMap,String mxObject) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(mxObject));
				for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
					detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
				}
				List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			 
			 return objectMXList;
		}
		
		
		/**
		  * 获取已上报的明细段列表
		  * @param objectJC
		  * @param dtoMXName
		  * @return
		 */
		@SuppressWarnings("unchecked")
		public  static List<Object> GetMXSendedData(Object objectJC, String dtoMXName) throws Exception {
		    List<Object> objectMXList =null;
		    IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMXName));
			detachedCriteria.add(Restrictions.eq("FOREIGNID",objectJC));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));
			detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
			objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectMXList;
	     }

		
		/**
		  * 根据条件获取已上报的基础段列表
		  * @param objectJC
		  * @param dtoMXName
		  * @return
		 */
		@SuppressWarnings("unchecked")
		public  static List<Object> GetJCSendedDataByCondition(String dtoMXName,Map<String,Object> fieldValueMap) throws Exception {
		    IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMXName));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));
			detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
			for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
				detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}
			List<Object> objectJCList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectJCList;
	     }

		
		/**
		  * 根据条件获取已上报的明细段列表
		  * @param objectJC
		  * @param dtoMXName
		  * @return
		 */
		@SuppressWarnings("unchecked")
		public  static List<Object> GetMXSendedDataByCondition(Object objectJC, String dtoMXName,Map<String,Object> fieldValueMap) throws Exception {
		    IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(dtoMXName));
			detachedCriteria.add(Restrictions.eq("FOREIGNID",objectJC));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.eq("RPTSendType", "2"));
			detachedCriteria.add(Restrictions.eq("RPTFeedbackType", "2"));
			for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
				detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}
			List<Object> objectMXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectMXList;
	     }

		/**
		 * 根据借据编号和业务发生日期查询待上报的贷款业务展期信息
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @param strJJBH
		 * @param jJBHYWFSRQ
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> GetDKYW_ZQXXListByJJBHAndYWFSRQ(Object objectCurrentSendJC,Map<String, String> downloadJudgeStatusMap, 
				String strJJBH,String JJBH_YWFSRQ) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
			detachedCriteria.add(Restrictions.eq("JJBH", strJJBH));
			detachedCriteria.add(Restrictions.eq("extend2", JJBH_YWFSRQ));
			List<Object> objectZQXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return  objectZQXXList;
		}

		/**
		 * 根据融资业务编号和业务发生日期得到待上报的贸易融资展期信息
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @param strRZYWBH
		 * @param rZYWXXYWFSRQ
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> getMYRZ_ZQXXByRZYWBHAndYWFSRQ(Object objectCurrentSendJC,Map<String, String> downloadJudgeStatusMap, 
				String strRZYWBH,String RZYWXX_YWFSRQ) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX"));
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
			detachedCriteria.add(Restrictions.eq("RZYWBH", strRZYWBH));
			detachedCriteria.add(Restrictions.eq("extend2", RZYWXX_YWFSRQ));
			List<Object> objectZQXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectZQXXList;
		}

		/**
		 * 根据借据编号得到贷款业务展期信息（待上报及已上报的数据列表）
		 * @param objectCurrentSendJC
		 * @param strJJBH
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> GetDKYW_ZQXXListByJJBH(
				Object objectCurrentSendJC, String strJJBH) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_DKYW_ZQXX"));
			detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
			detachedCriteria.add(Restrictions.eq("JJBH", strJJBH));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "2"));
			List<Object> objectZQXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return  objectZQXXList;
		}

		/**
		 * 根据融资业务编号得到融资业务展期信息（待上报及已上报的数据列表）
		 * @param objectCurrentSendJC
		 * @param strRZYWBH
		 * @return
		 * @throws ClassNotFoundException 
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> getMYRZ_ZQXXByRZYWBH(
				Object objectCurrentSendJC, String strRZYWBH) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_MYRZ_ZQXX"));
			detachedCriteria.add(Restrictions.eq("FOREIGNID",objectCurrentSendJC));
			detachedCriteria.add(Restrictions.eq("RZYWBH", strRZYWBH));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "2"));
			List<Object> objectZQXXList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectZQXXList;
		}
		
		/**
		 * 查询当前待上报的个人征信基础段列表（业务种类为贷款和信用卡的）
		 * @param baseDto
		 * @param instInfoSubList
		 * @param downloadJudgeStatusMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> getCurrentSendGRZXJC054List(String baseDto,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
			 detachedCriteria.add(Restrictions.in("YWZL", new String[]{"1","2"}));
			List<Object> objectCurrentSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectCurrentSendList;
		}
		
		/**
		 * 查询当前待上报的个人征信基础段列表（账户拥有者信息为“2-新账户开立”）
		 * @param baseDto
		 * @param instInfoSubList
		 * @param downloadJudgeStatusMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> getCurrentSendGRZXJC008_065List(String baseDto,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
			 detachedCriteria.add(Restrictions.eq("ZHYYZXXTS", "2"));
			List<Object> objectCurrentSendList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			return objectCurrentSendList;
		}
		
		/**
		 * 查询当前待上报的个人征信基础段列表
		 * @param baseDto
		 * @param instInfoSubList
		 * @param downloadJudgeStatusMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> getCurrentSendGRZXJCList(String baseDto,List<InstInfo> instInfoSubList,Map<String, String> downloadJudgeStatusMap,Object objSJFSNY) throws Exception {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
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
			
			List<Object> objectList = (List<Object>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null });
			return objectList;
		}
		
		/**
		 * 查询当前待上报的明细段的条数
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @return
		 * @throws Exception 
		 */
		public  static int getCurrentSendMXCount(Object objectCurrentSendJC,Field field,Map<String, String> downloadJudgeStatusMap) throws Exception {
			IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReflectOperation.getGenericClass(field.getGenericType()));
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
			
			return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		}
		
		/**
		 * 查询当前待上报的明细段的条数
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @return
		 * @throws Exception 
		 */
		public  static int getCurrentSendMXCount(Object objectCurrentSendJC,String strTableName,Map<String, String> downloadJudgeStatusMap) throws Exception {
			IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strTableName));
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
			
			return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		}
		
		/**
		 * 根据给定条件查询当前待上报的明细段的条数
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @return
		 * @throws Exception 
		 */
		public  static int getCurrentSendMXByConditionCount(Object objectCurrentSendJC,Map<String,Object> fieldValueMap,String strMXTableName,Map<String, String> downloadJudgeStatusMap) throws Exception {
			IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strMXTableName));
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
			for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
				detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}
			return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		 
		}
		
		/**
		 * 根据给定条件查询当前待上报以及已上报的明细段的条数
		 * @param objectCurrentSendJC
		 * @param downloadJudgeStatusMap
		 * @return
		 * @throws Exception 
		 */
		public  static int getCurrentSendAndSendedMXByConditionCount(Object objectCurrentSendJC,Map<String,Object> fieldValueMap,String strMXTableName) throws Exception {
			IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strMXTableName));
			detachedCriteria.add(Restrictions.eq("RPTCheckType", "2"));
			detachedCriteria.add(Restrictions.ne("RPTFeedbackType", "5"));
			detachedCriteria.add(Restrictions.eq("FOREIGNID", objectCurrentSendJC));
			for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
				detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}
			return (Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		 
		}

		/**
		 * 通过基础段对象得到提示的逻辑主键信息
		 * @param objectCurrentSendJC
		 * @return
		 * @throws Exception 
		 */
		public static List<String> getStrLogicPrmaryKeyValueList(Map<String,String> pKeyMap,Object objectCurrentSendJC) throws Exception {
			List<String> strLogicPrmaryKeyValueList=new ArrayList<String>();
			String pKeys=pKeyMap.get(objectCurrentSendJC.getClass().getName());
			
			String keyMsg="";
			if(pKeys.indexOf(",")>0){
				String[] strArray=pKeys.split(",");
				for (String str : strArray) {
					String strFieldDis= ((Field) ReflectOperation.getFieldByName(objectCurrentSendJC.getClass(), str)).getAnnotation(IColumn.class).description().toString();
					if(ReflectOperation.getFieldValue(objectCurrentSendJC, str)!=null){
						String strFieldValue=ReflectOperation.getFieldValue(objectCurrentSendJC, str).toString();
						keyMsg+=strFieldDis +":"+strFieldValue+",";
					}
					
				}
				if(keyMsg.endsWith(",")){
					keyMsg=keyMsg.substring(0, keyMsg.length()-1);
				}
				if(!strLogicPrmaryKeyValueList.contains(keyMsg)){
					strLogicPrmaryKeyValueList.add(keyMsg);
				}
			}
			return strLogicPrmaryKeyValueList;
		}
		
		
}
