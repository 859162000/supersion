package report.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class RptCalAndCheckInstanceEmptyCheck implements ICheck {
	public MessageResult Check() throws Exception {

		MessageResult messageResult = new MessageResult();
		Object tObject = RequestManager.getTOject();
		
			String[] t = (String[]) ReflectOperation.getFieldValue(tObject,"calculationRuleIdList");
			if (t != null) {
				if (t.length != 0) {
					messageResult.setSuccess(true);
				} else {
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("实例名称不能为空");
				}

			} else {
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("实例名称不能为空");
			}

		return messageResult;
	}

}
