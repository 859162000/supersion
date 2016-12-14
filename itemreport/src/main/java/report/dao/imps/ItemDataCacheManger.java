package report.dao.imps;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.CurrencyType;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.SimpleItemData;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

@SuppressWarnings("unchecked")
public class ItemDataCacheManger {

	private Cache cache = null;
	private Cache itemCache;
	private Cache dataLoadTimeCache;
	private Cache constCache;
	private CacheManager cacheManager;
	private static ItemDataCacheManger managerInstance = null;

	private ItemDataCacheManger() throws UnsupportedEncodingException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream io = classLoader.getResourceAsStream("framework/configs/ehcache.xml");
		cacheManager = CacheManager.create(io);
		cache = cacheManager.getCache("ItemData");
		itemCache = cacheManager.getCache("Item");
		constCache = cacheManager.getCache("Const");
		dataLoadTimeCache = cacheManager.getCache("DataLoadTime");
		// itemCheckMsgCache=cacheManager.getCache("ItemCheckMsg");
	}

	public String getConstData(String constType, String constKey) {
		String key = getConstKey(constType, constKey);
		Element elemnet;
		Object obj;
		String result = "";
		
		if (constCache.isKeyInCache(key)) 
		{
			elemnet = constCache.get(key);
			if(elemnet != null)
			{
				obj = elemnet.getObjectValue();
				if(obj != null)
					result = obj.toString();
			}
		}
		else
		{
			try {
				loadConstData(constType, constKey);
			} catch (Exception ex) {
				ExceptionLog.CreateLog(ex);
			}
			
			elemnet = constCache.get(key);
			if(elemnet != null)
			{
				obj = elemnet.getObjectValue();
				if(obj != null)
					result = obj.toString();
			}
		}

		return result;
	}

	private void loadConstData(String constType, String constKey) throws Exception {
		if (ConstCacheTypeKey.ItemPropertyConstType.equals(constType)) {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemProperty.class);
			List<ItemProperty> listItemInfo = (List<ItemProperty>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
			for (ItemProperty itemProperty : listItemInfo) {
				constCache.put(new Element(getConstKey(constType, itemProperty.getStrPropertyCode()), itemProperty.getStrPropertyName()));
			}
		}
		else if (ConstCacheTypeKey.CurrencyTypeConstType.equals(constType)) 
		{
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurrencyType.class);
			List<CurrencyType> currencyTypes = (List<CurrencyType>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
			for (CurrencyType currencyType : currencyTypes) {
				constCache.put(new Element(getConstKey(constType, currencyType.getStrCurrencyCode()), currencyType.getStrCurrencyName()));
			}
		} 
		else {
			Map<String, String> consts = ShowContext.getInstance().getShowEntityMap().get(constType);
			for (String key : consts.keySet()) {
				constCache.put(new Element(getConstKey(constType, key), consts.get(key)));
			}
		}
	}

	private String getConstKey(String constType, String constKey) {
		return constType + "_" + constKey;
	}

	public ItemInfo getItem(String strItemCode) throws Exception {

		Element e = itemCache.get(strItemCode);
		if (e != null && e.getObjectValue() != null) {
			return (ItemInfo) itemCache.get(strItemCode).getObjectValue();
		}

		getDBItem(strItemCode);

		e = itemCache.get(strItemCode);
		if (e != null) {
			return (ItemInfo) e.getObjectValue();
		} else
			return null;

	}

	private void getDBItem(String strItemCode) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemInfo.class);
		detachedCriteria.add(Restrictions.sqlRestriction("{alias}.suit=(select suit from iteminfo where stritemcode=?)", strItemCode, Hibernate.STRING));

		List<ItemInfo> listItemInfo = (List<ItemInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		for (ItemInfo itemInfo : listItemInfo) {
			itemCache.putQuiet(new Element(itemInfo.getStrItemCode(), itemInfo));
		}

		ApplicationManager.getActionExcuteLog().debug(String.format("===========缓存指标数量%d,指标主题%s", listItemInfo.size(), strItemCode));

	}

	public void PrintInfo() {
		Statistics s = cache.getStatistics();
		System.out.printf("==============\r\ncachehits=%d\r\ncachemisses=%d\r\nmemoryhits=%d\r\nobjectcnt=%d\r\ndiskhit=%d\r\naccuracy=%d",
				s.getCacheHits(), s.getCacheMisses(), s.getInMemoryHits(),s.getObjectCount(), s.getOnDiskHits(), s.getStatisticsAccuracy());

	}

	public String getItemCheckMsg(String strInstCode, String dtDate, String strItemCode, String currencyType, String strPropertyCode, String strExtendDimension1, String strExtendDimension2,
			String strFreq) throws Exception {
//		ItemDataCache中的key的dtDate字段被设定为了yyyy-MM-dd格式，但此方法输入的dtDate参数的格式却不一定是yyyy-MM-dd格式的，所以这里需要强制转换为yyyy-MM-dd格式
		String key = getKey(strInstCode, TypeParse.parseString(TypeParse.parseDate(dtDate), "yyyy-MM-dd"), strItemCode, currencyType, strPropertyCode, strExtendDimension1, strExtendDimension2, strFreq);
		if (cache.isKeyInCache(key)) {
			if (cache.get(key).getObjectValue() != null) {

				return ((SimpleItemData) cache.getQuiet(key).getObjectValue()).CheckResult;

			}
		}
		if (dataLoadTimeCache.isKeyInCache(getDataLoadTimeCacheKey(strInstCode, dtDate))) {
			loadDBItemDataWithCheckResult(strInstCode, dtDate, strItemCode, currencyType, strPropertyCode, strExtendDimension1, strExtendDimension2, strFreq);

		} else
			getDBItemDataWithCheckResult(strInstCode, dtDate);
		if (cache.isKeyInCache(key))
			return ((SimpleItemData) cache.getQuiet(key).getObjectValue()).CheckResult;
		return "";
	}

	public SimpleItemData getItemData(String strInstCode, String dtDate, String strItemCode, String currencyType, String strPropertyCode, String strExtendDimension1, String strExtendDimension2, String strFreq)
			throws Exception {
				
//		ItemDataCache中的key的dtDate字段被设定为了yyyy-MM-dd格式，但此方法输入的dtDate参数的格式却不一定是yyyy-MM-dd格式的，所以这里需要强制转换为yyyy-MM-dd格式
		String key = getKey(strInstCode, TypeParse.parseString(TypeParse.parseDate(dtDate), "yyyy-MM-dd"), strItemCode, currencyType, strPropertyCode, strExtendDimension1, strExtendDimension2, strFreq);
				
		if (cache.isKeyInCache(key)) {
			Element e1 = cache.get(key);
			if (e1 != null && e1.getObjectValue() != null) {

				return (SimpleItemData) e1.getObjectValue();

			}
		}
		if (dataLoadTimeCache.isKeyInCache(getDataLoadTimeCacheKey(strInstCode, dtDate))) {
			loadDBItemData(strInstCode, dtDate, strItemCode, currencyType, strPropertyCode, strExtendDimension1, strExtendDimension2, strFreq);
		} else
			getDBItemData(strInstCode, dtDate);
		if (cache.isKeyInCache(key))
			return (SimpleItemData) cache.get(key).getObjectValue();
		return new SimpleItemData();

	}

	private void loadDBItemDataWithCheckResult(String strInstCode, String dtDate, String strItemCode, String currencyType, 
			String strPropertyCode, String strExtendDimension1, String strExtendDimension2,String strFreq) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select dtdate,strextenddimension1,strextenddimension2,strvalue,value1,currencytype,strinstcode,stritemcode,strpropertycode,strcheckstate,ItemDataCheckResult.strcheckresult,ItemDataCheckResult.strsumcheckresult,strfreq ");
		sql.append(" from itemdata,ItemDataCheckResult");
		sql.append(" where strinstcode=?");
		sql.append(" and ");
		sql.append(" itemData.id=ItemDataCheckResult.id");
		sql.append(" and ");
		sql.append(" dtdate=?");
		sql.append(" and ");
		sql.append(" strItemCode=?");
		sql.append(" and ");
		sql.append(" currencyType=?");
		sql.append(" and ");
		sql.append(" strPropertyCode=?");
		sql.append(" and ");
		sql.append(" strExtendDimension1=?");
		sql.append(" and ");
		sql.append(" strExtendDimension2=?");
		sql.append(" and ");
		sql.append(" strfreq=?");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("sqlQueryListDao");
		try {
			//wc  2016-06-16  update
			List<Object[]> list = (List<Object[]>) singleObjectFindByCriteriaDao.paramObjectResultExecute(
					new Object[] { sql.toString(),new Object[] { strInstCode, TypeParse.parseDate(dtDate), strItemCode, currencyType, strPropertyCode, strExtendDimension1, strExtendDimension2, strFreq }, null });
			ApplicationManager.getActionExcuteLog().debug(
					String.format("load single data查询数据数==机构:%s日期：%s指标：%s币种：%s属性：%s扩展1：%s扩展2:%s", 
							strInstCode, dtDate, strItemCode, currencyType, strPropertyCode, strExtendDimension1,strExtendDimension2));
			String dtdate, strextenddimension1, strextenddimension2, strvalue,value1, currencytype, strinstcode, stritemcode, strpropertycode, strcheckresult, strsumcheckResult, strcheckstate;
			String key = "";
			for(Object[] objects : list){
				dtdate = objects[0]!= null?TypeParse.parseString((Date)objects[0], "yyyy-MM-dd"):"";
				strextenddimension1 = objects[1]!=null?objects[1]+"":"";
				strextenddimension2 = objects[2]!=null?objects[2]+"":"";
				strvalue = 			objects[3]!=null?objects[3]+"":"";
				value1 = 			objects[4]!=null?objects[4]+"":"";
				currencytype = 		objects[5]!=null?objects[5]+"":"";
				strinstcode = 		objects[6]!=null?objects[6]+"":"";
				stritemcode = 		objects[7]!=null?objects[7]+"":"";
				strpropertycode = 	objects[8]!=null?objects[8]+"":"";
				strcheckstate = 	objects[9]!=null?objects[9]+"":"";
				strcheckresult = 	objects[10]!=null?objects[10]+"":"";
				strsumcheckResult = objects[11]!=null?objects[11]+"":"";
	
				key = getKey(strinstcode, dtdate, stritemcode, currencytype, strpropertycode, strextenddimension1, strextenddimension2, strFreq);
				cache.putQuiet(new Element(key, new SimpleItemData(strvalue, value1,strcheckstate, ItemData.getAllCheckMsg(strcheckresult, strsumcheckResult))));
			}
		} catch (Exception e) {
			ApplicationManager.getActionExcuteLog().error(e);
		} 

	}

	private String getDataLoadTimeCacheKey(String strInstCode, String dtDate) {
		return strInstCode + "_" + dtDate;
	}

	private void getDBItemDataWithCheckResult(String strInstCode, String dtDate) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append("select dtdate,strextenddimension1,strextenddimension2,strvalue,value1,currencytype,strinstcode,stritemcode,strpropertycode,strcheckstate,strfreq,ItemDataCheckResult.strcheckresult,ItemDataCheckResult.strsumcheckresult");
		sql.append(" from itemdata  left outer join ItemDataCheckResult on itemdata.strinstcode=? and itemdata.dtdate=?  and itemdata.id=ItemDataCheckResult.id");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("sqlQueryListDao");
		try{
			//wc  2016-06-16  update
			List<Object[]> list = (List<Object[]>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { sql.toString(), new Object[] { strInstCode, TypeParse.parseDate(dtDate) }, null });
			ApplicationManager.getActionExcuteLog().debug("查询数据数==机构" + strInstCode + "日期" + dtDate);
			String dtdate, strextenddimension1, strextenddimension2, strvalue, value1, currencytype, strinstcode, stritemcode, strpropertycode, strcheckresult, strSumCheckResult, strcheckstate, strfreq;
			String key = "";
			for(Object[] objects : list){
				dtdate = objects[0]!= null?TypeParse.parseString((Date)objects[0], "yyyy-MM-dd"):"";
				strextenddimension1 = objects[1]!=null?objects[1]+"":"";
				strextenddimension2 = objects[2]!=null?objects[2]+"":"";
				strvalue = 			objects[3]!=null?objects[3]+"":"";
				value1 = 			objects[4]!=null?objects[4]+"":"";
				currencytype = 		objects[5]!=null?objects[5]+"":"";
				strinstcode = 		objects[6]!=null?objects[6]+"":"";
				stritemcode = 		objects[7]!=null?objects[7]+"":"";
				strpropertycode = 	objects[8]!=null?objects[8]+"":"";
				strcheckstate = 	objects[9]!=null?objects[9]+"":"";
				strfreq = 			objects[10]!=null?objects[10]+"":"";
				strcheckresult = 	objects[11]!=null?objects[11]+"":"";
				strSumCheckResult = objects[12]!=null?objects[12]+"":"";
	
				key = getKey(strinstcode, dtdate, stritemcode, currencytype, strpropertycode, strextenddimension1, strextenddimension2, strfreq);
				cache.putQuiet(new Element(key, new SimpleItemData(strvalue,value1, strcheckstate, ItemData.getAllCheckMsg(strcheckresult, strSumCheckResult))));
			}
			if (list.size() > 0) {
				dataLoadTimeCache.put(new Element(getDataLoadTimeCacheKey(strInstCode, dtDate), "loaded"));
			}
	
			ApplicationManager.getActionExcuteLog().debug("cache 数量：" + list.size());
		} catch (Exception e) {
			ApplicationManager.getActionExcuteLog().error(e);
		}

	}

	public String getKey(String strInstCode, String dtDate, String strItemCode, String currencyType, String strPropertyCode, String strExtendDimension1, String strExtendDimension2, String strFreq) {
		String result = String.format("%s_%s_%s_%s_%s_%s_%s_%s", strInstCode, dtDate, strItemCode, currencyType, strPropertyCode, strExtendDimension1 == null ? "" : strExtendDimension1, strExtendDimension2 == null ? "" : strExtendDimension2, strFreq == null ? "" : strFreq);
		return result;
	}

	public String getKey(ItemData itemdata) {
		return getKey(itemdata.getInstInfo().getStrInstCode(), TypeParse.parseString(itemdata.getDtDate(), "yyyy-MM-dd"), itemdata.getItemInfo().getStrItemCode(), itemdata.getCurrencyType()
				.getStrCurrencyCode(), itemdata.getItemProperty().getStrPropertyCode(), itemdata.getStrExtendDimension1(), itemdata.getStrExtendDimension2(), itemdata.getStrFreq());
	}

	public void addItemData(ItemData itemdata) {
		String key = getKey(itemdata);
		cache.putQuiet(new Element(key, new SimpleItemData(itemdata.getStrValue(), itemdata.getValue1(),itemdata.getStrCheckState(), itemdata.getAllCheckMsg())));

	}

	class AsyPutItemData implements Runnable {
		private Cache cache;
		private List<ItemData> lstItemData;
		public void run() {
			try {
				for (ItemData itemData : lstItemData) {
					cache.putQuiet(new Element(getKey(itemData), new SimpleItemData(itemData.getStrValue(), itemData.getValue1(), itemData.getStrCheckState(), itemData.getAllCheckMsg())));
				}
			} catch (Exception e) {
				e.printStackTrace();
				ExceptionLog.CreateLog(e);
			}
		}

		public void setCache(Cache cache) {
			this.cache = cache;
		}
		public void setItemDataList(List<ItemData> lstItemData) {
			this.lstItemData = new ArrayList<ItemData>(lstItemData);
		}

	}

	public void AsynAddAll(List<ItemData> itemdatas) {
		AsyPutItemData asyPutItemData = new AsyPutItemData();
		asyPutItemData.setCache(cache);
		asyPutItemData.setItemDataList(itemdatas);
		Thread thr = new Thread(asyPutItemData);
		thr.start();

	}

	public static synchronized ItemDataCacheManger getInsance() throws UnsupportedEncodingException {
		if (managerInstance == null) {
			managerInstance = new ItemDataCacheManger();
		}
		return managerInstance;

	}
	
	
	
	private void loadDBItemData(String strInstCode, String dtDate, String strItemCode, String currencyType, 
			String strPropertyCode, String strExtendDimension1, String strExtendDimension2,String strFreq) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select dtdate,strextenddimension1,strextenddimension2,strvalue,value1,currencytype,strinstcode,stritemcode,strpropertycode,strcheckstate,strfreq ");
		sql.append(" from itemdata");
		sql.append(" where strinstcode=?");
		sql.append(" and ");
		sql.append(" dtdate=?");
		sql.append(" and ");
		sql.append(" strItemCode=?");
		sql.append(" and ");
		sql.append(" currencyType=?");
		sql.append(" and ");
		sql.append(" strPropertyCode=?");
		
		//20160704 Liaoxl repair 因为以下三个属性的值可以为空，直接作为查询条件时，如果这些值为空在ORACLE数据库上就会有问题，得不到预期的值，所以只能先不加这几个条件，查询出记录后再进行比较
		/*sql.append(" and ");
		sql.append(" strExtendDimension1=?");
		sql.append(" and ");
		sql.append(" strExtendDimension2=?");
		sql.append(" and ");
		sql.append(" strfreq=?");*/
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("sqlQueryListDao");
		try {
			//wc  2016-06-16  update
			List<Object[]> list = (List<Object[]>) singleObjectFindByCriteriaDao.paramObjectResultExecute(
//					new Object[] { sql.toString(),new Object[] { strInstCode, TypeParse.parseDate(dtDate), strItemCode, currencyType, strPropertyCode, strExtendDimension1, strExtendDimension2, strFreq }, null });
					new Object[] { sql.toString(),new Object[] { strInstCode, TypeParse.parseDate(dtDate), strItemCode, currencyType, strPropertyCode}, null });
			ApplicationManager.getActionExcuteLog().debug(
					String.format("load single data查询数据数==机构:%s日期：%s指标：%s币种：%s属性：%s扩展1：%s扩展2:%s", 
							strInstCode, dtDate, strItemCode, currencyType, strPropertyCode, strExtendDimension1,strExtendDimension2));
			String dtdate, strextenddimension1, strextenddimension2, strvalue, value1,currencytype, strinstcode, stritemcode, strpropertycode, strcheckstate,strfreq;
			String key = "";
			for(Object[] objects : list){
										
				strextenddimension1 = objects[1]!=null?objects[1]+"":"";
				strextenddimension2 = objects[2]!=null?objects[2]+"":"";
				strfreq = objects[10]!=null?objects[10]+"":"";
				
				if(strextenddimension1.equals(strExtendDimension1) && strextenddimension2.equals(strExtendDimension2) && strfreq.equals(strFreq))
				{
					dtdate = objects[0]!= null?TypeParse.parseString((Date)objects[0], "yyyy-MM-dd"):"";
					strvalue = 			objects[3]!=null?objects[3]+"":"";
					value1 = 			objects[4]!=null?objects[4]+"":"";
					currencytype = 		objects[5]!=null?objects[5]+"":"";
					strinstcode = 		objects[6]!=null?objects[6]+"":"";
					stritemcode = 		objects[7]!=null?objects[7]+"":"";
					strpropertycode = 	objects[8]!=null?objects[8]+"":"";
					strcheckstate = 	objects[9]!=null?objects[9]+"":"";
		
					key = getKey(strinstcode, dtdate, stritemcode, currencytype, strpropertycode, strextenddimension1, strextenddimension2, strFreq);
					cache.putQuiet(new Element(key, new SimpleItemData(strvalue, value1, strcheckstate, null)));
				}
				
				
			}
		} catch (Exception e) {
			ApplicationManager.getActionExcuteLog().error(e);
		} 

	}

	

	private void getDBItemData(String strInstCode, String dtDate) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append("select dtdate,strextenddimension1,strextenddimension2,strvalue,value1,currencytype,strinstcode,stritemcode,strpropertycode,strcheckstate,strfreq");
		sql.append(" from itemdata  where itemdata.strinstcode=? and itemdata.dtdate=? ");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("sqlQueryListDao");
		try{
			//wc  2016-06-16  update
			List<Object[]> list = (List<Object[]>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { sql.toString(), new Object[] { strInstCode, TypeParse.parseDate(dtDate) }, null });
			ApplicationManager.getActionExcuteLog().debug("查询数据数==机构" + strInstCode + "日期" + dtDate);
			String dtdate, strextenddimension1, strextenddimension2, strvalue,value1, currencytype, strinstcode, stritemcode, strpropertycode, strcheckstate, strfreq;
			String key = "";
			for(Object[] objects : list){
				dtdate = objects[0]!= null?TypeParse.parseString((Date)objects[0], "yyyy-MM-dd"):"";
				strextenddimension1 = objects[1]!=null?objects[1]+"":"";
				strextenddimension2 = objects[2]!=null?objects[2]+"":"";
				strvalue = 			objects[3]!=null?objects[3]+"":"";
				value1 = 			objects[4]!=null?objects[4]+"":"";
				currencytype = 		objects[5]!=null?objects[5]+"":"";
				strinstcode = 		objects[6]!=null?objects[6]+"":"";
				stritemcode = 		objects[7]!=null?objects[7]+"":"";
				strpropertycode = 	objects[8]!=null?objects[8]+"":"";
				strcheckstate = 	objects[9]!=null?objects[9]+"":"";
				strfreq = 			objects[10]!=null?objects[10]+"":"";
	
				key = getKey(strinstcode, dtdate, stritemcode, currencytype, strpropertycode, strextenddimension1, strextenddimension2, strfreq);
				cache.putQuiet(new Element(key, new SimpleItemData(strvalue,value1, strcheckstate, null)));
			}
			if (list.size() > 0) {
				dataLoadTimeCache.put(new Element(getDataLoadTimeCacheKey(strInstCode, dtDate), "loaded"));
			}
	
			ApplicationManager.getActionExcuteLog().debug("cache 数量：" + list.size());
		} catch (Exception e) {
			ApplicationManager.getActionExcuteLog().error(e);
		}

	}
}
