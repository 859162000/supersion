package framework.reportCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

public class CheckFieldConsistent {
	private List<ConsisentGroup> consistentGroupList;
	private String consistenttype;

	public String getConsistenttype() {
		return consistenttype;
	}

	public void setConsistenttype(String consistenttype) {
		this.consistenttype = consistenttype;
	}

	public CheckFieldConsistent(){
		setConsistentGroupList(new ArrayList<ConsisentGroup>());
	}

	@SuppressWarnings("unchecked")
	public List<String> Check(List<List<?>> fieldconsistentDataList) {
		List<String> messageList = new ArrayList<String>();
		Map<Object,Object> mapConsistent = new HashMap<Object,Object>(); // 记录出现过的ID,Value对
		Set<Object> containSet = new HashSet<Object>(); // 已判断为出错的key集合
		for(int i = 0; i<fieldconsistentDataList.size();i++){ // 每行数据
			List<?> list = fieldconsistentDataList.get(i);
			for(Object object : list){
				Map<String,Object> mapObject = (Map<String,Object>)object;
				String keyString = consistentGroupList.get(i).getKeyField();
				String[] keyStrings = keyString.split(",");
				boolean isKeyNull = false;
				String keyStr = "";
				for(int j=0;j<keyStrings.length;j++){ // 生成标识列（各列连接）
					String key = keyStrings[j];
					Object keyValue = mapObject.get(key.toUpperCase());
					if(keyValue==null || StringUtils.isBlank(keyValue.toString())){
						isKeyNull = true;
						break;
					}
				
					if(!StringUtils.isBlank(keyStr)){
						keyStr += "+";
					}
					keyStr += keyValue.toString();
				}
				if(isKeyNull){
					continue;
				}
				
				String nameString = consistentGroupList.get(i).getNameField();
				String[] nameStrings = nameString.split(",");
				String nameStr = "";
				for(int j=0;j<nameStrings.length;j++){ // 生成值列（各列连接）
					String name = nameStrings[j];
					Object nameValue = mapObject.get(name.toUpperCase());
					if(nameValue==null || StringUtils.isBlank(nameValue.toString())){
						nameValue = "null";
					}
					if(!StringUtils.isBlank(nameStr)){
						nameStr += " + ";
					}
					nameStr += nameValue.toString();
				}
	
				// 没保存过此ID的数据，且此ID没有判断为出错，则进行校验
				if(mapConsistent.containsKey(keyStr) && !containSet.contains(keyStr)){
					Object consisttype=ServletActionContext.getServletContext().getAttribute("consisttype");
					Object ignoreVal = ServletActionContext.getServletContext().getAttribute("ignoreVal");
					
					if(StringUtils.isBlank(consisttype.toString())||consisttype.toString().equals("1")){ // 一致性校验
						if(!mapConsistent.get(keyStr).equals(nameStr)){ // 保存的值与本条记录的值比较,不同则出错
							
							// 生成出错消息
							containSet.add(keyStr); // 标示为出错
							String message="";
							for(ConsisentGroup consisentGroup : consistentGroupList){
								if(!StringUtils.isBlank(message)){
									message += " 与 ";
								}
								message +=  "存储过程" + consisentGroup.getProcedureName() + "相同" + consisentGroup.getKeyDiscription() + "(" + keyStr + ")" + "对应的" + consisentGroup.getNameDiscription();
							}
							
							message += " 应该一致";
							messageList.add(message);
						}
					}
					
					if(consisttype.toString().equals("2")){ // 唯一性校验
						if(mapConsistent.get(keyStr).equals(nameStr) // 保存的值与本条记录的值比较,不同则出错
								&& !nameStr.equals("null")){ // 空值忽略
							if((ignoreVal == null || !nameStr.equals(ignoreVal.toString()))
									) { // 检查是否需要忽略
								containSet.add(keyStr); // 标示为出错
								
								// 生成出错消息
								String message="";
								for(ConsisentGroup consisentGroup : consistentGroupList){
									if(!StringUtils.isBlank(message)){
										message += " 与 ";
									}
									message +=  "存储过程" + consisentGroup.getProcedureName() + "相同" + consisentGroup.getKeyDiscription() + "(" + keyStr + ")" + "对应的" + consisentGroup.getNameDiscription();
								}
								
								message += "应该不重复";
								messageList.add(message);
							}
						}
					}
				}
				else{ // 从没存过此ID的值，直接保存
					mapConsistent.put(keyStr, nameStr);
				}
			}
		}
		List<String> strMessageList = new ArrayList<String>();
		if(messageList.size()>0){
			for (String strMessage : messageList) {
				if(strMessage.contains("!=") || strMessage.contains(">=") || strMessage.contains("<=") || strMessage.contains("<") || strMessage.contains(">") || strMessage.contains("=") || strMessage.contains("notNull") || strMessage.contains("null")){
					strMessageList.add(strMessage.replace("!=","不等于").replace(">=", "大于等于").replace("<=", "小于等于").replace("<", "小于").replace(">", "大于").replace("=", "等于").replace("notNull", "非空").replace("null", "空"));
				}
				else {
					strMessageList.add(strMessage);
				}
			}
		}
		
		return strMessageList;
	}
	
	

	public void setConsistentGroupList(List<ConsisentGroup> consistentGroupList) {
		this.consistentGroupList = consistentGroupList;
	}


	public List<ConsisentGroup> getConsistentGroupList() {
		return consistentGroupList;
	}
}