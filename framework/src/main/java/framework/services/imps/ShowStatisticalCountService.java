package framework.services.imps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowInstance;

public class ShowStatisticalCountService extends SingleObjectShowListService{
	
	private String[] tNameList;
	
	private String[] showFieldList;
	
	private List<String> statisticalTranslateClassList;
	
	private boolean showNullCondtion;
	
	private String freq;
	
	private String startDate;

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}
	
	private Map<String,Object> groupValueMap;

	@SuppressWarnings("deprecation")
	@Override
	public void initSuccessResult() throws Exception {
		
		groupValueMap = new HashMap<String,Object>();
		
		List<Object> objectList = new ArrayList<Object>();
		
		Object condition = RequestManager.getTOject();
		
		int firstResult = 0;
		if(RequestManager.getCurrentPage() != null){
			firstResult = (RequestManager.getCurrentPage() - 1) * ShowParamManager.getPageSize();
		}
		
		if(condition != null || showNullCondtion){
			boolean isNullCondition = false;
			if(condition == null){
				isNullCondition = true;
			}
			else{
				for(int i =0;i<showFieldList.length;i++){
					String[] fieldSplit = showFieldList[i].split(",");
					if(fieldSplit[0].equals("2")){
						Object fieldValue = ReflectOperation.getFieldValue(condition, fieldSplit[1]);
						if(fieldValue == null || fieldValue.toString().equals("")){
							isNullCondition = true;
							break;
						}
					}
				}
			}

			if(!isNullCondition  || showNullCondtion){
				IParamObjectResultExecute singleObjectFindCountByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				
				String currentTName = RequestManager.getTName();
				Object currentTObject = RequestManager.getTOject();
				
				if(StringUtils.isBlank(freq)){
					HandleDate(currentTName,condition,singleObjectFindCountByCriteriaDao,objectList,firstResult,null,true);
				}
				else{
					List<String> dateString = new ArrayList<String>();
					
					if(freq.split(",")[0].equals("1")){
						Date currentDate = new Date();
						Date stratDate = TypeParse.parseDate(startDate);
						Calendar calendar = Calendar.getInstance();     
					    calendar.setTime(currentDate);  
					    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - 1);
					    currentDate = calendar.getTime();
					    dateString.add(TypeParse.parseString(currentDate,"yyyy-MM-dd"));
					    
					    calendar = Calendar.getInstance();  
					    calendar.setTime(stratDate); 
					    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + 1);
					    stratDate = calendar.getTime();
					    
					    while(currentDate.compareTo(stratDate) > 0){
					    	calendar = Calendar.getInstance();     
						    calendar.setTime(currentDate);  
						    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - 1);
						    currentDate = calendar.getTime();
					    	dateString.add(TypeParse.parseString(currentDate,"yyyy-MM-dd"));
					    }
					}
					else if(freq.split(",")[0].equals("2")){
						Date currentDate = new Date();
						Date stratDate = TypeParse.parseDate(startDate);
						Date date = new Date(currentDate.getYear(),currentDate.getMonth(),1);
					    Calendar calendar = Calendar.getInstance();     
					    calendar.setTime(date);  
					    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - 1);
					    date = calendar.getTime();
					    dateString.add(TypeParse.parseString(date,"yyyy-MM-dd"));
					    
					    while(date.compareTo(stratDate) > 0){
					    	date = new Date(date.getYear(),date.getMonth(),1);
					    	calendar = Calendar.getInstance();     
						    calendar.setTime(date);  
						    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - 1);
						    date = calendar.getTime();
					    	dateString.add(TypeParse.parseString(date,"yyyy-MM-dd"));
					    }
					}

					for(int i=0;i<dateString.size();i++){
						if(i == 0){
							HandleDate(currentTName,condition,singleObjectFindCountByCriteriaDao,objectList,firstResult,dateString.get(i),true);
						}
						else{
							HandleDate(currentTName,condition,singleObjectFindCountByCriteriaDao,objectList,firstResult,dateString.get(i),false);
						}
					}
				}

				RequestManager.setTName(currentTName);
				RequestManager.setTOject(currentTObject);
			}
		}
		
		LogicParamManager.setTotalCount(objectList.size());

		List<Object> pageList = new ArrayList<Object>();

		for(int i=firstResult;i<firstResult + ShowParamManager.getPageSize();i++){
			if(i > objectList.size() - 1){
				break;
			}
			pageList.add(objectList.get(i));
		}
		
		this.setServiceResult(pageList);
	}
	
	@SuppressWarnings("unchecked")
	private void HandleDate(String currentTName,Object condition,IParamObjectResultExecute singleObjectFindCountByCriteriaDao,List<Object> objectList,int firstResult,String freqValue,boolean showEmpty) throws Exception{
		for(String tName : tNameList){
			
			Object object = Class.forName(currentTName).newInstance();
			boolean emptyObject = true;
			
			RequestManager.setTName(tName);
			Object tObject = Class.forName(tName).newInstance();
			
			Object conditionObject = FrameworkFactory.CreateClass(TActionRule.getConditionClassName(tName));
			if(conditionObject == null){
				conditionObject = tObject;
			}
			for(int i =0;i<showFieldList.length;i++){
				String[] fieldSplit = showFieldList[i].split(",");
				if(fieldSplit[0].equals("2")){
					if(condition != null){
						Object fieldValue = ReflectOperation.getFieldValue(condition, fieldSplit[1]);
						if(fieldValue != null && !fieldValue.toString().equals("")){
							ReflectOperation.setFieldValue(conditionObject, fieldSplit[2], fieldValue);
						}
					}
				}
			}
			RequestManager.setTOject(conditionObject);

			/*DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
			LogicParamManager.setDetachedCriteria(detachedCriteria);
			for(String str : this.statisticalTranslateClassList){
				ITranslate translate = (ITranslate)FrameworkFactory.CreateClass(str);
				translate.Translate();
			}*/
			
			for(int i =0;i<showFieldList.length;i++){
				String[] fieldSplit = showFieldList[i].split(",");
				if(fieldSplit[0].equals("3")){
					String strCondtionStr = fieldSplit[2];
					String key = tName + strCondtionStr;
					if(!groupValueMap.containsKey(key)){
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(tName));
						LogicParamManager.setDetachedCriteria(detachedCriteria);
						for(String str : this.statisticalTranslateClassList){
							ITranslate translate = (ITranslate)FrameworkFactory.CreateClass(str);
							translate.Translate();
						}
						if(strCondtionStr.indexOf("=") > -1){
							String strFieldName = strCondtionStr.substring(0,strCondtionStr.indexOf("="));
							//String strValue = strCondtionStr.substring(strCondtionStr.indexOf("=") + 1);
							String strValue=strCondtionStr.substring(strCondtionStr.indexOf("=")+1);
							
							String[] strConValue=strValue.split("%");
							if(strConValue.length>1)
							{
								detachedCriteria.add(Restrictions.in(strFieldName, strConValue));
							}else
							{
								detachedCriteria.add(Restrictions.eq(strFieldName, strValue));
							}
							
							if(!StringUtils.isBlank(freqValue) && !StringUtils.isBlank(freq) ){
								ProjectionList projectionList = Projections.projectionList();
								projectionList.add(Property.forName(freq.split(",")[1]).group());
								projectionList.add(Projections.rowCount());
								detachedCriteria.setProjection(projectionList);
								Object value = singleObjectFindCountByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								groupValueMap.put(key, value);
							}
							else{
								IParamObjectResultExecute criteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
								Object value = criteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
								groupValueMap.put(key, value);
							}
						}
					}
					
					
				}
			}

			for(int i =0;i<showFieldList.length;i++){
				String[] fieldSplit = showFieldList[i].split(",");
				
				if(fieldSplit[0].equals("1")){
					ShowInstance showInstance = ReflectOperation.getDefaultShowInstance(tName);
					ReflectOperation.setFieldValue(object, fieldSplit[1], showInstance.getShowEntityName());
				}
				else if(fieldSplit[0].equals("2")){
					if(condition != null){
						Object fieldValue = ReflectOperation.getFieldValue(condition, fieldSplit[1]);
						if(fieldValue != null && !fieldValue.toString().equals("")){
							ReflectOperation.setFieldValue(object, fieldSplit[1], fieldValue);
						}
					}
				}
				else if(fieldSplit[0].equals("3")){
					String strCondtionStr = fieldSplit[2];
					String key = tName + strCondtionStr;
					
					Object rowObject = groupValueMap.get(key);
					if(rowObject.getClass().equals(Integer.class)){
						ReflectOperation.setFieldValue(object, fieldSplit[1], rowObject);
					}
					else{
						List<Object> rowList = (List<Object>)groupValueMap.get(key);
						boolean isExsist = false;
						for(Object data : rowList){
							Object[] columns = (Object[])data;
							if(TypeParse.parseString((Date)columns[0], "yyyy-MM-dd").equals(freqValue)){
								isExsist = true;
								int count = (Integer)columns[1];
								if(count > 0){
									emptyObject = false;
								}
								ReflectOperation.setFieldValue(object, fieldSplit[1], count);
								break;
							}
						}
						if(!isExsist){
							ReflectOperation.setFieldValue(object, fieldSplit[1], 0);
						}
					}	
				}
				else if(fieldSplit[0].equals("4")){
					ReflectOperation.setFieldValue(object, fieldSplit[1], freqValue);
				}
			}
			
			if(showEmpty){
				objectList.add(object);
			}
			else{
				if(!emptyObject){
					objectList.add(object);
				}
			}
			
		}
	}
	
	public boolean isShowNullCondtion() {
		return showNullCondtion;
	}

	public void setShowNullCondtion(boolean showNullCondtion) {
		this.showNullCondtion = showNullCondtion;
	}

	public String[] getTNameList() {
		return tNameList;
	}

	public void setTNameList(String[] tNameList) {
		this.tNameList = tNameList;
	}

	public String[] getShowFieldList() {
		return showFieldList;
	}

	public void setShowFieldList(String[] showFieldList) {
		this.showFieldList = showFieldList;
	}

	public void setStatisticalTranslateClassList(
			List<String> statisticalTranslateClassList) {
		this.statisticalTranslateClassList = statisticalTranslateClassList;
	}

	public List<String> getStatisticalTranslateClassList() {
		return statisticalTranslateClassList;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}
}
