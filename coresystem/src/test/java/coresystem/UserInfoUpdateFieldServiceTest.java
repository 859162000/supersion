package coresystem;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import framework.interfaces.IObjectResultExecute;
import framework.test.SpringBeanTest;

public class UserInfoUpdateFieldServiceTest extends SpringBeanTest {
	@Test
	public void testUpdate() {
		IObjectResultExecute iobjectResult=(IObjectResultExecute)this.getBean("userInfoUpdateFieldService");
		 try {
			Object result=iobjectResult.objectResultExecute();
			Assert.assertTrue(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
