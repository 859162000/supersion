package framework.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

import framework.interfaces.ApplicationManager;

public class Log4jInitServlet extends HttpServlet {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {          
     }  
      
    public void init() throws ServletException {  
		 System.setProperty(ApplicationManager.getRealPath(), getServletContext().getRealPath("/"));
		 java.net.URL thisUrl = Log4jInitServlet.class.getClassLoader().getResource(getInitParameter("configfile"));
		 PropertyConfigurator.configure(thisUrl);  
     }  
}  