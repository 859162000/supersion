package coresystem.service.imps;

import java.util.Properties;   

import org.hibernate.HibernateException;   
import org.hibernate.cfg.Environment;   
import org.hibernate.connection.DriverManagerConnectionProvider;   

import framework.helper.AESSecretKey;

public class CustomDriverManagerConnectionProvider extends DriverManagerConnectionProvider {   

	public CustomDriverManagerConnectionProvider() {   
		super();   
	}   

	@Override  
	public void configure(Properties props) throws HibernateException{   
		String user = props.getProperty(Environment.USER);   
		String password = props.getProperty(Environment.PASS);
		String url=props.getProperty(Environment.URL);
		
		props.setProperty(Environment.USER, AESSecretKey.DecryString(user, null));   
		props.setProperty(Environment.PASS, AESSecretKey.DecryString(password, null));  
		props.setProperty(Environment.URL, AESSecretKey.DecryString(url, null));     
		super.configure(props);   
	}   
}  