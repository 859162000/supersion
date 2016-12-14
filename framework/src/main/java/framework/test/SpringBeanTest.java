package framework.test;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Log4jConfigurer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.opensymphony.xwork2.ActionContext;

import framework.helper.ConfigUtils;
import framework.helper.DomXMLOperation;
import framework.reportCheck.CheckContext;
import framework.security.SecurityContext;
import framework.show.ShowContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional()
public class SpringBeanTest extends AbstractJUnit4SpringContextTests{

	public Object getBean(String beanName)
	{
		return this.applicationContext.getBean(beanName);
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createContext();
		initConfig();
	}
	private static  void createContext()
	{
		Map<String,Object> requestMap = new HashMap<String,Object>();
		requestMap.put("request", new HashMap<String,Object>());
		ActionContext context = new ActionContext(requestMap);
		Map<String,Object> applicatioMap = new HashMap<String,Object>();
		context.setApplication(applicatioMap);
		Map<String,Object> sesssionMap = new HashMap<String,Object>();
		context.setSession(sesssionMap);
		ServletActionContext.setContext(context);
	}
	
	private static void initConfig() throws Exception {

		ShowContext.Init();
		SecurityContext.Init();
		CheckContext.Init();
		ConfigUtils.loadConfig();
		
	}
	
}
