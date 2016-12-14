package zxptsystem.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;
/**
 * 漏报新增校验，业务发生日期不能为空
 * @author Transino
 *
 */
public class HistorySaveCheck implements ICheck{

	public MessageResult Check() throws Exception {
		MessageResult messageResult=new MessageResult();
		Object tObject=RequestManager.getTOject();
		if(ReflectOperation.getFieldValue(tObject, "extend2")==null || ReflectOperation.getFieldValue(tObject, "extend2").toString().equals("")){
			messageResult.setSuccess(false);
			messageResult.getErrorFieldList().add(new ErrorField("extend2", "red", "不能为空"));
		}
		return messageResult;
	} 
}
