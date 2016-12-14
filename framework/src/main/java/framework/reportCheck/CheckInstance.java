package framework.reportCheck;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

public class CheckInstance implements IParamObjectResultExecute{
	
	private String strSessionFactory;
	
	public String getStrSessionFactory() {
		return strSessionFactory;
	}

	public void setStrSessionFactory(String strSessionFactory) {
		this.strSessionFactory = strSessionFactory;
	}

	public CheckInstance(){
		this.checkTableList = new ArrayList<CheckTable>();
		this.checkFieldAggregationList = new ArrayList<CheckFieldAggregationList>();
		this.checkFieldConsistentList=new ArrayList<CheckFieldConsistentList>();
		this.checkFieldScopeList=new ArrayList<CheckFieldScopeList>();
		this.checkItemList = new ArrayList<CheckItemList>();
	}

	public List<CheckFieldScopeList> getCheckFieldScopeList() {
		return checkFieldScopeList;
	}

	public void setCheckFieldScopeList(List<CheckFieldScopeList> checkFieldScopeList) {
		this.checkFieldScopeList = checkFieldScopeList;
	}

	public List<CheckFieldConsistentList> getCheckFieldConsistentList() {
		return checkFieldConsistentList;
	}

	public void setCheckFieldConsistentList(
			List<CheckFieldConsistentList> checkFieldConsistentList) {
		this.checkFieldConsistentList = checkFieldConsistentList;
	}

	public List<CheckFieldAggregationList> getCheckFieldAggregationList() {
		return checkFieldAggregationList;
	}

	public void setCheckFieldAggregationList(
			List<CheckFieldAggregationList> checkFieldAggregationList) {
		this.checkFieldAggregationList = checkFieldAggregationList;
	}

	private String instanceName;

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInstanceName() {
		return instanceName;
	}
	
	public void setCheckParam(Map<String, String> checkParam) {
		this.checkParam = checkParam;
	}
	
	public void setInitCheckParam(Map<String,String> checkParam){
		if(checkParam.size() > 0)
			this.checkParam = checkParam;
	}

	public Map<String, String> getCheckParam() {
		return checkParam;
	}
	
	private Map<String, String> checkParam;

	public void setCheckTableList(List<CheckTable> checkTableList) {
		this.checkTableList = checkTableList;
	}

	public List<CheckTable> getCheckTableList() {
		return checkTableList;
	}
	
	private List<CheckTable> checkTableList;
	private List<CheckFieldAggregationList> checkFieldAggregationList;
	private List<CheckFieldConsistentList> checkFieldConsistentList;
	private List<CheckFieldScopeList> checkFieldScopeList;
	private List<CheckItemList> checkItemList;


	
	@SuppressWarnings("unchecked")
	public Object paramObjectResultExecute(Object param,String strCheckClass,String strEntityClass) throws Exception {
		
		List<IParamObjectResultExecute> iCheckList = new ArrayList<IParamObjectResultExecute>();
		if(!StringUtils.isBlank(strCheckClass)){
			
			if(StringUtils.isBlank(strEntityClass)){
				throw new Exception("实体类不能为空");
			}
			if(FrameworkFactory.CreateClass(strEntityClass) == null){
				throw new Exception("未找到类: " + strEntityClass);
			}
			
			String[] strArray=strCheckClass.split(",");
			for(String checkClass:strArray){
				Object objectCheckClass=FrameworkFactory.CreateClass(checkClass);
				if(objectCheckClass==null){
					objectCheckClass=FrameworkFactory.CreateBean(checkClass);
				}
				if(objectCheckClass==null){
					throw new Exception("未找到类: " + checkClass);
				}
				IParamObjectResultExecute activityNodeForICheck = null;
				try{
					activityNodeForICheck = (IParamObjectResultExecute)objectCheckClass;
				}
				catch(Exception ex){
					throw new Exception("类: " + checkClass + " 应继承IParamObjectResultExecute接口");
				}
				iCheckList.add(activityNodeForICheck);
			}
			
		}
		
		
		if(param == null){
			this.setInitCheckParam(RequestManager.getReportCheckParam());
		}
		else{
			this.setInitCheckParam((Map<String,String>)param);
		}


        MessageResult messageResult = new MessageResult();
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("校验时间：" + TypeParse.parseString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		if(this.checkParam != null && this.checkParam.size() > 0){
			messageResult.AddLineSplitFlag();
			messageResult.getMessageList().add("参数：");
			for(Map.Entry<String,String> paramValue : this.checkParam.entrySet()) {
				
				messageResult.getMessageList().add(paramValue.getKey() + "：" + paramValue.getValue());
				
				// 基本校验参数设置
				for(CheckTable checkTable : this.checkTableList){
					checkTable.setStrSessionFactory(this.getStrSessionFactory());
					for(CheckFieldParamField checkFieldParamField : checkTable.getCheckFieldParamFieldList()){
						if(paramValue.getKey().toUpperCase().equals(checkFieldParamField.getParamName().toUpperCase())){
							checkFieldParamField.setValue(paramValue.getValue());
							break;
						}
					}
				}
				
				// 聚合校验参数设置
				for(CheckFieldAggregationList checkfieldaggregation : this.checkFieldAggregationList){
					for(CheckFieldParamField checkFieldParamField : checkfieldaggregation.getCheckFieldParamFieldList()){
						if(paramValue.getKey().toUpperCase().equals(checkFieldParamField.getParamName().toUpperCase())){
							checkFieldParamField.setValue(paramValue.getValue());
							break;
						}
					}
				}
				
				// 一致性校验参数设置
				for(CheckFieldConsistentList checkFieldConsistentList : this.checkFieldConsistentList){
					for(CheckFieldParamField checkFieldParamField : checkFieldConsistentList.getCheckFieldParamFieldList()){
						if(paramValue.getKey().toUpperCase().equals(checkFieldParamField.getParamName().toUpperCase())){
							checkFieldParamField.setValue(paramValue.getValue());
							break;
						}
					}
				}
				
				// 完整性校验参数设置
				for(CheckFieldScopeList checkFieldScopeList : this.checkFieldScopeList){
					for(CheckFieldParamField checkFieldParamField : checkFieldScopeList.getCheckFieldParamFieldList()){
						if(paramValue.getKey().toUpperCase().equals(checkFieldParamField.getParamName().toUpperCase())){
							checkFieldParamField.setValue(paramValue.getValue());
							break;
						}
					}
				}
				
				// 指标校验参数设置
				for(CheckItemList checkItemList : this.checkItemList){
					for(CheckFieldParamField checkFieldParamField : checkItemList.getCheckFieldParamFieldList()){
						if(paramValue.getKey().toUpperCase().equals(checkFieldParamField.getParamName().toUpperCase())){
							checkFieldParamField.setValue(paramValue.getValue());
							break;
						}
					}
				}
				
			}
		}
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("错误统计（条）：");
		int currentSize = messageResult.getMessageList().size();
		int errorCount = 0;
		
		List<Integer> tableStatisticList = new ArrayList<Integer>();
		for(CheckTable checkTable : this.checkTableList){

			messageResult.AddLineSplitFlag();
			messageResult.getMessageList().add("存储过程：" + checkTable.getProcedureDiscription());
			List<Integer> statisticList = checkTable.Check(param,messageResult,iCheckList,strEntityClass); // 表校验:含基本、有效性、CaseWhen、条件、非聚合
			
			if(tableStatisticList.size() == 0){
				tableStatisticList = statisticList;
			}
			else{
				for(int i=0;i<statisticList.size();i++){
					tableStatisticList.set(i, tableStatisticList.get(i) + statisticList.get(i));
				}
			}
		}
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("聚合校验：");
		Integer aggregationStatistic = 0;
		for(CheckFieldAggregationList checkfieldaggregationList : this.checkFieldAggregationList){
			Integer aggregationErrorCount = checkfieldaggregationList.Check(messageResult);
			aggregationStatistic += aggregationErrorCount;
		}
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("一致性校验：");
		Integer consistentStatistic1 = 0; // 一致性校验
		for(CheckFieldConsistentList  checkFieldConsistentList : this.checkFieldConsistentList){
			if(checkFieldConsistentList.getConsistenttype().equals("1")) {
				Integer statisticErrorCount = checkFieldConsistentList.Check(messageResult);
				consistentStatistic1 += statisticErrorCount;
			}
		}
		
		messageResult.AddLineSplitFlag();
		messageResult.getMessageList().add("唯一性校验：");
		Integer consistentStatistic2 = 0; // 唯一性校验
		for(CheckFieldConsistentList  checkFieldConsistentList : this.checkFieldConsistentList){
			if(checkFieldConsistentList.getConsistenttype().equals("2")) {
				Integer statisticErrorCount = checkFieldConsistentList.Check(messageResult);
				consistentStatistic2 += statisticErrorCount;
			}
		}
		
		for(CheckFieldScopeList checkFieldScopeList : this.checkFieldScopeList){
			messageResult.AddLineSplitFlag();
			messageResult.getMessageList().add(checkFieldScopeList.getDiscription());
			checkFieldScopeList.Check(messageResult); // 完整性校验
		}
		
		Integer statisticItemErrorCount = 0;
		for(CheckItemList checkItemList : this.checkItemList) {
			messageResult.AddLineSplitFlag();
			//messageResult.getMessageList().add(checkItemList.getDiscription());
			statisticItemErrorCount = checkItemList.Check(messageResult); // 指标校验
		}
		
		List<Integer> allStatisticList = tableStatisticList;
		allStatisticList.add(aggregationStatistic);
		allStatisticList.add(consistentStatistic1);
		allStatisticList.add(consistentStatistic2);

		for(int i=0;i<allStatisticList.size();i++){
			if(i == 0){
				messageResult.getMessageList().add(currentSize + i,"基本校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 1){
				messageResult.getMessageList().add(currentSize + i,"数据有效性校验：	" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 2){
				messageResult.getMessageList().add(currentSize + i,"CaseWhen校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 3){
				messageResult.getMessageList().add(currentSize + i,"条件校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 4){
				messageResult.getMessageList().add(currentSize + i,"非聚合校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 5){
				messageResult.getMessageList().add(currentSize + i,"聚合校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 6){
				messageResult.getMessageList().add(currentSize + i,"一致性校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
			else if(i == 7){
				messageResult.getMessageList().add(currentSize + i,"唯一性校验：		" + allStatisticList.get(i));
				errorCount += allStatisticList.get(i);
			}
		}
		messageResult.getMessageList().add(currentSize + allStatisticList.size(),"错误总条数：		" + errorCount);

		if(errorCount > 0){
			messageResult.setSuccess(false);
		}
		
		messageResult.setMessage("校验完成。");
		
		
		// 根据返回结果设置填报状态
		String strTaskRptInstID = checkParam.get("strTaskRptInstID");
		if(strTaskRptInstID != null && !strTaskRptInstID.equals("")) {
			messageResult.setIdValue(strTaskRptInstID);
			
			// 查询出此id对象
			IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			Object obj = byIdDao.paramObjectResultExecute(new Object[]{"report.dto.TaskRptInst", strTaskRptInstID , null});
			if(obj != null) {
				if(statisticItemErrorCount > 0) {
					ReflectOperation.setFieldValue(obj, "strCheckState", "3");
				}
				else {
					ReflectOperation.setFieldValue(obj, "strCheckState", "2");
				}
			}
			
			// 更新对象
			IParamVoidResultExecute dao2 = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
			dao2.paramVoidResultExecute(new Object[] {obj, null});
		}

		return messageResult;
	}
	
	public void setCheckItemList(List<CheckItemList> checkItemList) {
		this.checkItemList = checkItemList;
	}

	public List<CheckItemList> getCheckItemList() {
		return checkItemList;
	}

	public Object paramObjectResultExecute(Object param) throws Exception {
		return paramObjectResultExecute(param,null,null);
	}

}
