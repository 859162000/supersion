package coresystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsSpringTestCase;
import org.apache.struts2.dispatcher.Dispatcher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

import coresystem.actions.imps.LoginAction;

import framework.actions.imps.BaseSAction;
import framework.helper.FrameworkFactory;
import framework.reportCheck.CheckContext;
import framework.security.SecurityContext;
import framework.show.ShowContext;
import framework.test.ActionTestCase;
import framework.test.SpringBeanTest;

public class LoginActionTest extends ActionTestCase {
	
	
	@Test
	public void loginTest() throws Exception
	{
		testAction("/coresystem/Login-coresystem.dto.UserInfo.action","login.xls");
	}
	
}
