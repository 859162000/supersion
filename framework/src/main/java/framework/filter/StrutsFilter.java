package framework.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import framework.dao.imps.DBUtils;
import framework.helper.BeanFactoryUtils;
import framework.helper.ConfigUtils;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IVoidResultExecute;
import framework.reportCheck.CheckContext;
import framework.security.SecurityContext;
import framework.show.ShowContext;
import framework.show.ShowNavigation;
import framework.show.ShowNavigationComponent;

public class StrutsFilter extends StrutsPrepareAndExecuteFilter{
	
	private static String encoding = "UTF-8";

	@SuppressWarnings("unchecked")
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		
		String encodingParam = filterConfig.getInitParameter("encoding");
		if (encodingParam != null && encodingParam.trim().length() != 0) {
			encoding = encodingParam;
		}
		
		ServletContext servletContext = filterConfig.getServletContext();
		
		
	
		workflowThreadServiceBeanId = filterConfig.getInitParameter("workflowThreadServiceBeanId");
		initAutoDTOServiceBeanId = filterConfig.getInitParameter("initAutoDTOServiceBeanId");
		charsetType = filterConfig.getInitParameter("charsetType");
		
		try {
			ConfigUtils.loadConfig();
		} catch (IOException e1) {
			throw new ServletException(e1);
		}
		
		servletContext.setAttribute(ApplicationManager.getEmptySelectValueName(), ApplicationManager.getEmptySelectValue());
		for(ShowNavigation showNavigation : ShowContext.getInstance().getShowNavigationList()){
			if(servletContext.getAttribute(ApplicationManager.getShowNavigation(showNavigation.getLevel())) == null){
	   		    List<ShowNavigation> tempNavigationList = new ArrayList<ShowNavigation>();
	   		    tempNavigationList.add(showNavigation);
	   		    servletContext.setAttribute(ApplicationManager.getShowNavigation(showNavigation.getLevel()), tempNavigationList);
	   		}
	   		else{
	   			List<ShowNavigation> tempNavigationList = (List<ShowNavigation>)servletContext.getAttribute(ApplicationManager.getShowNavigation(showNavigation.getLevel()));
	   			tempNavigationList.add(showNavigation);
	   		}
		}
		servletContext.setAttribute(ApplicationManager.getShowNavigation(ShowNavigationComponent.class.getSimpleName()), ShowContext.getInstance().getShowNavigationComponentList());
		
		if(workflowThreadServiceBeanId != null){
			Runnable workflowThreadServiceBean = (Runnable)FrameworkFactory.CreateBean(workflowThreadServiceBeanId);
			if(workflowThreadServiceBean != null) {
				Thread thread= new Thread(workflowThreadServiceBean);
				thread.start();
			}
		}
		
		if(initAutoDTOServiceBeanId != null) {
			IVoidResultExecute initAutoDTOServiceBean = (IVoidResultExecute)FrameworkFactory.CreateBean(initAutoDTOServiceBeanId);
			if(initAutoDTOServiceBean != null) {
				try {
					initAutoDTOServiceBean.voidResultExecute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if(charsetType != null){
			servletContext.setAttribute(ApplicationManager.getCharsetType(), charsetType);
		}
		String isInitDB=filterConfig.getInitParameter("initDB");
		String dbscriptPath=filterConfig.getInitParameter("dbScriptPath");
		if("true".equals(isInitDB))
		{
			initDB(dbscriptPath);
		}
		
	}
	public void initDB(String dbScriptPath) throws ServletException
	{
		String sessionFactoryBeanName="sessionFactory";
		SessionFactory sf=(SessionFactory) BeanFactoryUtils.getInstance().getBean(sessionFactoryBeanName);
		Session session=SessionFactoryUtils.getSession(sf, true);
		try {
			DBUtils.initDB(session.connection(), dbScriptPath);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("database init fail..........");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		super.doFilter(request, response, chain);
	}


	
	private String workflowThreadServiceBeanId;
	private String initAutoDTOServiceBeanId; 
	private String charsetType;
}
