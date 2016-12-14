package coresystem;


import org.apache.struts2.ServletActionContext;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import coresystem.service.imps.ActionExcuteService;


import framework.interfaces.IObjectResultExecute;
import framework.test.SpringBeanTest;

public class ActionExcuteServiceTest extends SpringBeanTest {

	@Test 
	@Rollback(true)
	public void testSaveActionExcuteLog() throws Exception
	{
		ServletActionContext.getContext().setName("Save-coresystem.dto.UserInfo");
		IObjectResultExecute objectResultExecute = new ActionExcuteService();
		objectResultExecute.objectResultExecute();
		//throw new Exception("test");
	}
	
}
