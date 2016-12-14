package framework.helper;

public class FrameworkFactory {
	
	public static Object CreateBean(String beanName){
		try{
			return BeanFactoryUtils.getInstance().getBean(beanName);
		}
		catch(Exception ex){
			return null;
		}
	}
	
	public static Object CreateClass(String className){
		try{
			return Class.forName(className).newInstance();
		}
		catch(Exception ex){
			return null;
		}
	}
}
