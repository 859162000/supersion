package framework;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import framework.interfaces.IObjectResultExecute;
import framework.test.SpringBeanTest;

public class SingleObjectShowSaveServiceTest extends SpringBeanTest {
	@Test
	public void testObjectShowSave() {
		IObjectResultExecute iobjectResult=(IObjectResultExecute)this.getBean("singleObjectShowSaveService");
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
