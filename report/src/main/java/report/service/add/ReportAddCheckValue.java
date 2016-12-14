package report.service.add;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class ReportAddCheckValue implements IAdd{

	public void Add() throws Exception {
		Object tObjct = RequestManager.getTOject();
		ReflectOperation.setFieldValue(tObjct, "RPTCheckType","2");
		ReflectOperation.setFieldValue(tObjct, "RPTSendType","1");
		ReflectOperation.setFieldValue(tObjct, "RPTFeedbackType","1");
	}
}
