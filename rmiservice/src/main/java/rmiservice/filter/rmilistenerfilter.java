package rmiservice.filter;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class rmilistenerfilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		super.doFilter(req, res, chain);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
		super.init(filterConfig);

		try{
			String RMIAddressList=filterConfig.getInitParameter("RMIAddressList");
			if(null!=RMIAddressList){
				String[] items = RMIAddressList.split(";");
				for(String item :items){
					String[] element = item.split(",");
					String url=element[0];
					int port=Integer.parseInt(element[1]);
					String cls = element[2];
					
					Remote obj = (Remote)Class.forName(cls).newInstance();				
					LocateRegistry.createRegistry(port);			
					Naming.rebind(url, obj);
					System.out.println(url+" running!......");			
				}
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}

}
