package framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;


import coresystem.service.imps.ActionExcuteService;
import framework.bean.context.ServiceContext;
import framework.bean.context.ServiceContextList;
import framework.interfaces.IObjectResultExecute;
import framework.services.imps.BaseTShowService;
import framework.test.SpringBeanTest;

public class ServiceContextListTest extends SpringBeanTest {
	@Test 
	public void testInsertPropertys() throws Exception
	{
		Integer prop1Pos=1;
		Integer prop2Pos=3;
		registerBeanDefine(prop1Pos,prop2Pos);
		ServiceContextList scl=(ServiceContextList)getBean("serviceBeanContextList");
		ServiceContext sc=(ServiceContext)getBean("testServiceContext");
		scl.getSCList().add(sc);
		scl.setSCList(scl.getSCList());
		BaseTShowService showSaveService=(BaseTShowService)getBean("singleObjectShowSaveService"); 
		scl.meragePropertys("singleObjectShowSaveService", "TestBean",showSaveService);
		Assert.assertEquals("framework.services.procese.ShowSaveProcese1",showSaveService.getDefaultProceseClassList().get(prop2Pos));
		int i=0;
		for(String key:showSaveService.getOperationMap().keySet())
		{
			if(i==prop1Pos)
			{
				Assert.assertEquals("删除",key );
				break;
			}
			i++;
		}
		if(i!=prop1Pos)
		{
			Assert.fail();
		}
		
		
		
		
	}
	private void registerBeanDefine(Integer prop1Pos,Integer prop2Pos)
	{
		 ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		    
		    // 获取bean工厂并转换为DefaultListableBeanFactory
		    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
		        .getBeanFactory();
		    
		    // 通过BeanDefinitionBuilder创建bean定义
		    BeanDefinitionBuilder builder = BeanDefinitionBuilder
		        .genericBeanDefinition(ServiceContext.class);
		    // 设置此属性引用已经定义的
		    builder.addPropertyValue("beanId", "singleObjectShowSaveService");
		    Map<String,Object> property=new HashMap<String,Object>();
		    List<String> classList=new ArrayList<String>();
		    classList.add("framework.services.procese.ShowSaveProcese1");
		    property.put("defaultProceseClassList",classList);
		    Map<String,String> opMap=new HashMap<String,String>();
		    opMap.put("删除", "Post-DeleteById");
		    property.put("operationMap", opMap);
		    builder.addPropertyValue("beanProperty",property );
		    builder.addPropertyValue("dtoMatchRule", "TestBean");
		    Map<String,Integer> firstInsertList=new HashMap<String,Integer>();
		    firstInsertList.put("operationMap",prop1Pos);
		    firstInsertList.put("defaultProceseClassList",prop2Pos);
		    builder.addPropertyValue("insertPropMap", firstInsertList);
		    
		    
		    // 注册bean
		    defaultListableBeanFactory.registerBeanDefinition("testServiceContext",
		        builder.getRawBeanDefinition());
	}
}
