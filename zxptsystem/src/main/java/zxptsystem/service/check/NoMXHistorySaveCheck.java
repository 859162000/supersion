package zxptsystem.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;
/**
 * 漏报新增时，信息更新日期不能为空
 * @author Transino
 *
 */
public class NoMXHistorySaveCheck implements ICheck{

	public MessageResult Check() throws Exception {
		MessageResult messageResult=new MessageResult();
		Object tObject=RequestManager.getTOject();
		if(ReflectOperation.getFieldValue(tObject, "XXGXRQ")==null || ReflectOperation.getFieldValue(tObject, "XXGXRQ").toString().equals("")){
			messageResult.setSuccess(false);
			messageResult.getErrorFieldList().add(new ErrorField("XXGXRQ", "red", "不能为空"));
		}
		return messageResult;
	} 
}
