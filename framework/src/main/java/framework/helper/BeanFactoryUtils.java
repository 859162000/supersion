package framework.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BeanFactoryUtils implements BeanFactoryAware {

	private static BeanFactory beanFactory = null;  
    private static BeanFactoryUtils factoryUtils = null;  
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		BeanFactoryUtils.beanFactory=beanFactory;
		
	}
	
	public static BeanFactoryUtils getInstance(){  
        if(factoryUtils==null){  
            factoryUtils = (BeanFactoryUtils)beanFactory.getBean("beanFactoryUtils");  
            //factoryUtils = new BeanFactoryUtils();  
        }  
        return factoryUtils;  
    }  
    public Object getBean(String name){  
        return beanFactory.getBean(name);  
    }  
    public BeanDefinition getBeanDefine(String beanName)
    {
    	if(beanFactory instanceof ConfigurableListableBeanFactory)
    	{
    		return ((ConfigurableListableBeanFactory) beanFactory).getBeanDefinition(beanName);
    	}
    	return null;
    }
    
}
