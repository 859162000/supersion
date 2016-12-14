package coresystem;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import coresystem.dto.UserInfo;

import framework.interfaces.IObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.security.SecurityContext;
import framework.services.interfaces.MessageResult;
import framework.test.SpringBeanTest;

public class UserRoleShowSaveServiceTest extends  SpringBeanTest {
	//@Before
	public void login() throws Exception
	{
		UserInfo u=new UserInfo();
		u.setStrUserCode("admin");
		u.setStrPassword("123");
		RequestManager.setTName("coresystem.dto.UserInfo");
		RequestManager.setTOject(u);
		IObjectResultExecute iobjectResult=(IObjectResultExecute)this.getBean("loginService");
		MessageResult result=(MessageResult)iobjectResult.objectResultExecute();
		
		Assert.assertEquals(result.getMessage(), true, result.isSuccess());
	}
	
	@Test
	@Ignore
	public void testObjectShowSave() throws Exception {
		
		login();
		RequestManager.setActionName("showSave-coresystem.dto.UserRole.action");
		RequestManager.setTName("coresystem.dto.UserRole");
		TActionRule.initActionName();
		IObjectResultExecute iobjectResult=(IObjectResultExecute)this.getBean("singleObjectShowSaveService");
		 try {
			
			Object result=iobjectResult.objectResultExecute();
			Assert.assertNotNull(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}
