package framework.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


import com.opensymphony.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.util.FileManager;



import framework.reportCheck.CheckContext;
import framework.security.SecurityContext;
import framework.show.ShowContext;

public class ConfigUtils {
	public static final String configName="net.transino.config.properties";
	
	public static void loadConfig() throws IOException
	{
		
		Iterator<URL> urls=ClassLoaderUtil.getResources(configName, ConfigUtils.class, false);
		
		URL url = null;
		 InputStream is = null;
		 
		List<String> showInstanceLocationList =new ArrayList<String>();
		List<String> showNavigationLocationList =new ArrayList<String>();
		List<String> showEntityLocationList =new ArrayList<String>();
		List<String> securityLocationList =new ArrayList<String>();
		List<String> dataSecurityLocationList =new ArrayList<String>();
		List<String> reportCheckLocationList =new ArrayList<String>();
		
        while (urls.hasNext()) {
            try {
                url = urls.next();
                is = FileManager.loadFile(url);
                Properties p = new Properties();   
            	try {
        			p.load(is);
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        			throw e1;
        		}   
            	    
            	String showInstanceLocation = p.getProperty("showInstanceLocation");
            	if(!StringUtils.isBlank(showInstanceLocation))
            	{
            		showInstanceLocationList.addAll(procPath(showInstanceLocation));
            	}
            	
            	String showNavigationLocation =p.getProperty("showNavigationLocation");
            	if(!StringUtils.isBlank(showNavigationLocation))
            	{
            		showNavigationLocationList.addAll(procPath(showNavigationLocation));
            	}
            	String showEntityLocation =p.getProperty("showEntityLocation");
            	if(!StringUtils.isBlank(showEntityLocation))
            	{
            		showEntityLocationList.addAll(procPath(showEntityLocation));
            	}
            	String securityLocation =p.getProperty("securityLocation");
            	if(!StringUtils.isBlank(securityLocation))
            	{
            		securityLocationList.add(securityLocation);
            	}
            		
            		
            	String dataSecurityLocation =p.getProperty("dataSecurityLocation");
            	if(!StringUtils.isBlank(dataSecurityLocation))
            	{
            		dataSecurityLocationList.addAll(procPath(dataSecurityLocation));
            	}
            	String reportCheckLocation =p.getProperty("reportCheckLocation");
            	if(!StringUtils.isBlank(reportCheckLocation))
            	{
            		reportCheckLocationList.addAll(procPath(reportCheckLocation));
            	}
               
               
            } finally {
                if (is != null) {
                    is.close();
                    
                }
            }
        }
		
        if( showInstanceLocationList.size()>0)
        {
        	String[] strArt=new String[showInstanceLocationList.size()]; 
        	
        	ShowContext.setShowInstanceLocation(showInstanceLocationList.toArray(strArt));
        }
    	if(showNavigationLocationList.size()>0)
    	{
    		String[] strArt=new String[showNavigationLocationList.size()]; 
    		ShowContext.setShowNavigationLocation(showNavigationLocationList.toArray(strArt));
    	}
		if(showEntityLocationList.size()>0)
		{
			String[] strArt=new String[showEntityLocationList.size()]; 
			 ShowContext.setShowEntityLocation(showEntityLocationList.toArray(strArt));
		}
		if(securityLocationList.size()>0)
		{
			String[] strArt=new String[securityLocationList.size()]; 
			SecurityContext.setSecurityLocation(securityLocationList.toArray(strArt));
		}
		if(dataSecurityLocationList.size()>0)
		{
			String[] strArt=new String[dataSecurityLocationList.size()]; 
			SecurityContext.setDataSecurityLocation(dataSecurityLocationList.toArray(strArt));
		}
		if(reportCheckLocationList.size()>0)
		{
			String[] strArt=new String[reportCheckLocationList.size()]; 
			CheckContext.setReportCheckLocation(reportCheckLocationList.toArray(strArt));
		}
		

	}
	
	private static List<String> procPath(String path)
	{
		String p=path.replaceAll("\\s", "").replaceAll("\n", "").replaceAll("\r", "");
		return Arrays.asList(p.split(","));
	}

}
