package framework.bean.context;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServiceContextListTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatch() {
		List<ServiceContext> scs=new ArrayList<ServiceContext>();
		ServiceContext sc=new ServiceContext();
		sc.setBeanId("nodtoBean");
		Map<String,Object> beanProperty=new HashMap<String,Object>();
		beanProperty.put("dependOnTable", new String[]{"InstInfo","RptInfo","TaskRptInst"});
		sc.setBeanProperty(beanProperty);
		scs.add(sc);
		
		sc=new ServiceContext();
		sc.setBeanId("nodtoBean");
		beanProperty=new HashMap<String,Object>();
		beanProperty.put("dependOnTable", new String[]{"kk","dd","kl"});
		sc.setBeanProperty(beanProperty);
		scs.add(sc);
		ServiceContextList scList=new ServiceContextList(scs);
		List<ServiceContext> aa = scList.match("nodtoBean", "Inst");
		Assert.assertEquals(2, aa.size());
		aa = scList.match("nodtoBean","");
		Assert.assertEquals(2, aa.size());
		
	}

}
