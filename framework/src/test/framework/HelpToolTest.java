package framework;




import static org.junit.Assert.*;

import java.util.Map;


import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import extend.helper.HelpTool;
import framework.test.SpringBeanTest;

public class HelpToolTest extends SpringBeanTest {

	@Test
	public void testGetSystemConstSet()
	{
		try
		{
			Map<String, String> a = HelpTool.getSystemConstSet();
			assertNotNull(a);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			fail(ex.getMessage());
		}
		
	}

}
