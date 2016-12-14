package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.FrameworkFactory;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;
/**
 * XML调用校验类
 * @author xiajieli
 *
 */
public class CheckFieldClass {
	
	public List<String> Check(Map<String, Object> mapObject) throws Exception{
		List<String> messageList = new ArrayList<String>();
		MessageResult MessageResult=null;
		if(this.name!=null && !StringUtils.isBlank(this.name)){
			if(FrameworkFactory.CreateClass(this.name) == null){
				throw new Exception("未找到类: " + this.name);
			}
		}
		
		ICheckWithParam check = (ICheckWithParam)FrameworkFactory.CreateClass(this.name);
		MessageResult=check.Check(mapObject);
		
		if(MessageResult.getMessageSet().size()>0){
			if(this.getErrorMsg() != null && !StringUtils.isBlank(this.getErrorMsg())) {
				messageList.add(this.getErrorMsg());
			} else {
				messageList.addAll(MessageResult.getMessageSet());
			}
		}
		return messageList;
	}
	
	private String name;
	private String errorMsg;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
