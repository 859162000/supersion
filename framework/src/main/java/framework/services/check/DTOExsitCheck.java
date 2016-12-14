package framework.services.check;

import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class DTOExsitCheck implements ICheck{

	public MessageResult Check() throws Exception {
		
        MessageResult messageResult = new MessageResult();

		// 检测T是否存在
		try {
			String targetTName = RequestManager.getTName();
			Class.forName(targetTName).newInstance();
		}
		catch(Exception ex){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("数据表对应的映射类不存在！");
		}

		return messageResult;
	}

}
