package coresystem;


import org.junit.Ignore;
import org.junit.Test;


import framework.test.ActionTestCase;




public class LoginAction2Test extends ActionTestCase {
	
	@Ignore
	@Test
	public void loginTest() throws Exception
	{
		testAction("/coresystem/Login-coresystem.dto.UserInfo.action","login.xls");
	}
	
	@Test
	public void roleSaveTest() throws Exception
	{
		testActions("/framework/Save-%s.action","rolesave.xls");
	}
	
}
