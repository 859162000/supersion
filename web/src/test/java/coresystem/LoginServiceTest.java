package coresystem;

import org.junit.Assert;
import org.junit.Test;

import coresystem.dto.UserInfo;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.RequestManager;

import framework.services.interfaces.MessageResult;
import framework.test.SpringBeanTest;

public class LoginServiceTest extends SpringBeanTest {
	@Test
	public void testLogin() throws Exception {
		
		UserInfo u=new UserInfo();
		u.setStrUserCode("admin");
		u.setStrPassword("123");
		RequestManager.setTName("coresystem.dto.UserInfo");
		RequestManager.setTOject(u);
		IObjectResultExecute iobjectResult=(IObjectResultExecute)this.getBean("loginService");
		IObjectResultExecute iobjectResult2=(IObjectResultExecute)this.getBean("loginService2");
		
		
		MessageResult result=(MessageResult)iobjectResult.objectResultExecute();
		
		Assert.assertEquals(result.getMessage(), true, result.isSuccess());
		/*try {
			 SecurityContext.getInstance().initLoginInfo();
			Object result=iobjectResult.objectResultExecute();
			Assert.assertTrue(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}*/
	}

}
