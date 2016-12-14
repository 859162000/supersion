package report.service.imps;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import report.dto.AutoTaskFlow;
import report.dto.RptInfo;
import report.dto.TaskFlow;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ReportBaseObjectDaoResultService extends BaseObjectDaoResultService{
	
   @SuppressWarnings("unchecked")
   @Override
	public Object objectResultExecute() throws Exception {
	   ShowSaveOrUpdate processResult=(ShowSaveOrUpdate)super.objectResultExecute();
	   
	   TaskFlow taskFlow = null;
	   AutoTaskFlow autoTaskFlow = null;
	   List<Suit> suits = null;
	   List<Suit> childSuits = null;
	   List<ReportModel_Table> reportModelTables = null;
	   List<RptInfo> rptInfos = null;
	   DetachedCriteria detachedCriteria = null;
	   String currentLevelLevel = SessionManager.getCurrentLevel();
	   // 构造数据库查询对象
	   IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
	   IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	   
	   // 构造查询主题信息的查询条件对象
	   detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.Suit"));
	   // 查询所有源主题信息
	   suits = (List<Suit>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	   
	   for(ShowFieldValue showFieldValue:processResult.getShowFieldValueList()){
		   if(showFieldValue.getShowField().getFieldName().equals("reportModel_Table")){
			   LinkedHashMap<String, String> thisMap = new LinkedHashMap<String, String>();
			   LinkedHashMap<String, String> t1 = (LinkedHashMap<String, String>)showFieldValue.getTag();
			   
			   if(currentLevelLevel.equals("TaskModelInst")){
				   // 查询当前任务的信息（主要是当前任务的主题信息）
				   taskFlow = (TaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskFlow.class.getName(),SessionManager.getLevelIdValue(currentLevelLevel),null});
				   // 设置childSuits的数据
				   childSuits =new OperateSuitObject().getChildSuits(suits, taskFlow.getSuit());
			   }else{
				   autoTaskFlow = (AutoTaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoTaskFlow.class.getName(),SessionManager.getLevelIdValue(currentLevelLevel),null});
				   // 设置childSuits的数据
				   childSuits = new OperateSuitObject().getChildSuits(suits, autoTaskFlow.getSuit());
			   }
			   if(t1.size()>0){
				   // 构造查询明细信息的查询条件
				   detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Table"));
				   detachedCriteria.add(Restrictions.and(Restrictions.in("autoTableID", t1.keySet()), Restrictions.in("suit", childSuits)));
				   detachedCriteria.add(Restrictions.eq("strAddFields", "1"));
				   //明细表暂时不排序
//				   detachedCriteria.addOrder(Order.asc("strTableName"));
				   reportModelTables = (List<ReportModel_Table>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			   }
			   
			   if(reportModelTables!=null && reportModelTables.size() != 0){
				   for(ReportModel_Table rmt:reportModelTables)
				   {
					   thisMap.put(rmt.getAutoTableID(),rmt.getStrShowTableName());
				   }
			   }
			   showFieldValue.setTag(thisMap);
			   break;
		   }
		   else if(showFieldValue.getShowField().getFieldName().equals("rptInfo")){
			   String strTime = null;// 批次
			   LinkedHashMap<String, String> thisMap = new LinkedHashMap<String, String>();
			   LinkedHashMap<String, String> t1 = (LinkedHashMap<String, String>)showFieldValue.getTag();
				
			   // 查询当前任务的信息（主要是频度信息以及主题信息）
			   if(currentLevelLevel.equals("TaskRptInst")){
				   taskFlow = (TaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskFlow.class.getName(),SessionManager.getLevelIdValue(currentLevelLevel),null}); 
				   if(t1.size()>0){
					   // 构造查询条件对象
					   strTime = taskFlow.getStrTime();
					   detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptInfo"));
					   if(StringUtils.isBlank(strTime)) {
						   detachedCriteria.add(Restrictions.isNull("strTime"));
					   } else {
						   detachedCriteria.add(Restrictions.eq("strTime", taskFlow.getStrTime()));
					   }
					   detachedCriteria.add(Restrictions.and(Restrictions.in("strRptCode", t1.keySet()), Restrictions.eq("strFreq", taskFlow.getStrFreq())));
					   detachedCriteria.add(Restrictions.and(Restrictions.le("startDate", taskFlow.getDtTaskDate()), Restrictions.ge("endDate", taskFlow.getDtTaskDate())));
					   detachedCriteria.addOrder(Order.asc("strBCode"));
					   
					   rptInfos = (List<RptInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				   }
				   // 设置childSuits的数据
				   childSuits =new OperateSuitObject().getChildSuits(suits, taskFlow.getSuit());
			   }else{
				   autoTaskFlow = (AutoTaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoTaskFlow.class.getName(),SessionManager.getLevelIdValue(currentLevelLevel),null});
				   if(t1.size()>0){
					   // 构造查询条件对象
					   strTime = autoTaskFlow.getStrTime();
					   detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptInfo"));
					   if(StringUtils.isBlank(strTime)) {
						   detachedCriteria.add(Restrictions.isNull("strTime"));
					   } else {
						   detachedCriteria.add(Restrictions.eq("strTime", autoTaskFlow.getStrTime()));
					   }
					   detachedCriteria.add(Restrictions.and(Restrictions.in("strRptCode", t1.keySet()), Restrictions.eq("strFreq", autoTaskFlow.getStrFreq())));
					   detachedCriteria.add(Restrictions.and(Restrictions.le("startDate", autoTaskFlow.getDtTaskDate()), Restrictions.ge("endDate", autoTaskFlow.getDtTaskDate())));
					   detachedCriteria.addOrder(Order.asc("strBCode"));
					   
					   rptInfos = (List<RptInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
				   }
				   // 设置childSuits的数据
				   childSuits = new OperateSuitObject().getChildSuits(suits, autoTaskFlow.getSuit());
			   }
			   
			   // 将符合条件的RptInfo的主题信息与任务对应祖逖的所有子孙主题信息进行对比
//			   for(RptInfo rptInfo : rptInfos){
//				   if(null != rptInfo.getSuit() && childSuits.contains(rptInfo.getSuit())){
//					   thisMap.put(rptInfo.getStrRptCode(), rptInfo.getDescName());
//				   }
//			   }
			   
			   OperateSuitObject oso = new OperateSuitObject();
			   thisMap = oso.compareSuit(childSuits, rptInfos);
			   oso = null;
			   
			   showFieldValue.setTag(thisMap);
			   break;
		   }
	   }
	   
	   // 这里是在处理在界面中任务信息显示成了下拉列表，而不是隐藏的input，不处理会导致界面中的异步提交获取不到任务ID
	   for(ShowFieldValue showFieldValue:processResult.getShowFieldValueList()){
		   if(showFieldValue.getShowField().getFieldName().equals("taskFlow")){
			   LinkedHashMap<String, String> thisMap = new LinkedHashMap<String, String>();
			   // 查询当前任务的信息（主要是当前任务的主题信息）
			   taskFlow = (TaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskFlow.class.getName(),SessionManager.getLevelIdValue(currentLevelLevel),null});
			   thisMap.put(taskFlow.getId(), taskFlow.getStrTaskName());
			   showFieldValue.setTag(thisMap);
			   showFieldValue.getShowField().setSingleTag("hiddenField");
			   break;
		   }
	   }
	   return processResult;
	}
    private String[] suitListCode;
   
	public void setSuitListCode(String[] suitListCode) {
		this.suitListCode = suitListCode;
	}
	
	public String[] getSuitListCode() {
		return suitListCode;
	}	
}
