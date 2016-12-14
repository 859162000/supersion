package framework.reportCheck;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public class CheckFieldConsistentList {
	private String discription;
	private String consistenttype;
	private String ignoreVal;
	
	public String getConsistenttype() {
		return consistenttype;
	}
	public void setConsistenttype(String consistenttype) {
		this.consistenttype = consistenttype;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public CheckFieldConsistentList(){
		this.checkFieldConsistentList=new ArrayList<CheckFieldConsistent>();
		this.checkFieldParamFieldList=new ArrayList<CheckFieldParamField>();
		this.consistentGroupList=new ArrayList<ConsisentGroup>();
		
	}
	public List<ConsisentGroup> getConsistentGroupList() {
		return consistentGroupList;
	}
	public void setConsistentGroupList(List<ConsisentGroup> consistentGroupList) {
		this.consistentGroupList = consistentGroupList;
	}
	public List<CheckFieldConsistent> getCheckFieldConsistentList() {
		return checkFieldConsistentList;
	}
	public void setCheckFieldConsistentList(
			List<CheckFieldConsistent> checkFieldConsistentList) {
		this.checkFieldConsistentList = checkFieldConsistentList;
	}
	public List<CheckFieldParamField> getCheckFieldParamFieldList() {
		return checkFieldParamFieldList;
	}
	public void setCheckFieldParamFieldList(
			List<CheckFieldParamField> checkFieldParamFieldList) {
		this.checkFieldParamFieldList = checkFieldParamFieldList;
	}
	private String getPrcedureString(String procedureName,String procedureParam){
		String[] procedureParams = procedureParam.split(",");
		String callprcedure = "";
		
			for(int i =0;i<procedureParams.length;i++){
				if(!StringUtils.isBlank(callprcedure)){
					callprcedure += ",";
				}
				callprcedure += "?";
			}
		
		String prcedureString = "{call " + procedureName +"(" + callprcedure + ")}";
		return prcedureString;
	}
	
	private Map<String,Object> getProcedureParamMap(String procedureParam){
		String[] procedureParams = procedureParam.split(",");
		Map<String,Object> procedureParamMap = new LinkedHashMap<String,Object>();
		if(!StringUtils.isBlank(procedureParam)){
		for(int i =0;i<procedureParams.length;i++){
			boolean isHaveValue = false;
			for(CheckFieldParamField checkFieldParamField : this.checkFieldParamFieldList){
				if(checkFieldParamField.getParamName().equals(procedureParams[i])){
//					if(StringUtils.isBlank(checkFieldParamField.getValue())){
//						procedureParamMap.put("", "参数" + checkFieldParamField.getParamName() + "：未能获取值。");
//						return procedureParamMap;
//					}
					// lanyuesheng  页面参数传空值，默认值为空，则赋空值
					if(StringUtils.isBlank(checkFieldParamField.getValue())
							&& StringUtils.isBlank(checkFieldParamField.getDefaultValue())){
						procedureParamMap.put(procedureParams[i], null);
					}
					if(checkFieldParamField.getType().equals("") || checkFieldParamField.getType().equals("1")){
						procedureParamMap.put(procedureParams[i], checkFieldParamField.getValue());
					}
					else if(checkFieldParamField.getType().equals("2")){
						try{
							Date procedureDate = java.sql.Date.valueOf(checkFieldParamField.getValue());
							procedureParamMap.put(procedureParams[i], procedureDate);
						}
						catch(Exception ex){
							procedureParamMap.put("", "参数" + checkFieldParamField.getParamName() + "：时间类型错误。");
							return procedureParamMap;              
						}
					}
					isHaveValue = true;
					break;
				}
			}
			if(!isHaveValue){
				procedureParamMap.put(procedureParams[i], null);
			}
		}
	}
		return procedureParamMap;
	}
	public Integer Check(MessageResult messageResult) throws Exception{
		
		ServletActionContext.getServletContext().setAttribute("consisttype", this.getConsistenttype());
		ServletActionContext.getServletContext().setAttribute("ignoreVal", this.getIgnoreVal());
		String defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);

		int currentSize = messageResult.getMessageList().size();
		int errorCount = 0;
		for(CheckFieldConsistent checkFieldconsistent : this.checkFieldConsistentList){
			List<List<?>> fieldconsistentDataList = new ArrayList<List<?>>();
			List<?> consisentList=null;
			for(ConsisentGroup consisentGroup : checkFieldconsistent.getConsistentGroupList()){
				if(!StringUtils.isBlank(consisentGroup.getProcedureName()) && !StringUtils.isBlank(consisentGroup.getProcedureParam())){
					String consisentProcedureName = consisentGroup.getProcedureName();
					String consisentProcedureParam = consisentGroup.getProcedureParam();
					
					LogicParamManager.setSqlQuery(getPrcedureString(consisentProcedureName,consisentProcedureParam));
					Map<String,Object> consisentProcedureParamMap = getProcedureParamMap(consisentProcedureParam);
					if(consisentProcedureParamMap.containsKey("")){
						messageResult.AddLineSplitFlag();
						messageResult.getMessageList().add(consisentProcedureParamMap.get("").toString());
					}
					LogicParamManager.setProcedureParam(consisentProcedureParamMap);
					consisentList = (List<?>) objectResultExecute.paramObjectResultExecute(null);			
					fieldconsistentDataList.add(consisentList);
				}
			}
			List<String> messageList = checkFieldconsistent.Check(fieldconsistentDataList);
			for(String message : messageList){
				messageResult.getMessageList().add(message);
			}
			errorCount += messageList.size();
			

				
		}
		messageResult.getMessageList().add(currentSize,"错误条数：" + errorCount);
		
		return errorCount;
	}
	
	
	public void setIgnoreVal(String ignoreVal) {
		this.ignoreVal = ignoreVal;
	}
	public String getIgnoreVal() {
		return ignoreVal;
	}


	private List<CheckFieldConsistent> checkFieldConsistentList;
	private List<CheckFieldParamField> checkFieldParamFieldList;
	private List<ConsisentGroup> consistentGroupList;
}
