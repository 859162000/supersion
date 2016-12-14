package framework.services.check;

import org.apache.commons.lang.xwork.StringUtils;

import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class IdListNullCheck implements ICheck{

	public MessageResult Check() throws Exception {
		
        MessageResult messageResult = new MessageResult();
        String type=RequestManager.getTypeName();
        if(StringUtils.isBlank(type)) //复合表单明细删除有主表限制，所以不需要做idlist空值判断
        {
        	if(RequestManager.getIdList() == null){
    			messageResult.setSuccess(false);
    			messageResult.getMessageSet().add("请至少勾选一条数据");
    		}

        }
		
		return messageResult;
	}

}
