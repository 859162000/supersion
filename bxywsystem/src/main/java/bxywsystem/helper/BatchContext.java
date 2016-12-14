package bxywsystem.helper;
import java.io.File;
import java.util.HashMap;

import org.apache.struts2.ServletActionContext;

public class BatchContext {
	private String baseHome;
	private String crypConfig;
	private String crypPublickey;
	private HashMap<String,Object> properites;
	private static BatchContext _instance = null;

	private BatchContext() {
		baseHome = ServletActionContext.getServletContext().getRealPath("/")+"conf"+File.separatorChar+"dbgs"+File.separatorChar;
		properites = new HashMap<String,Object>();		
		crypConfig = baseHome + "Crypt.xml";
		crypPublickey = baseHome + "public.key";
		
	}

	public static synchronized BatchContext getInstance() {
		if (_instance == null)
			_instance = new BatchContext();
		return _instance;
	}

	public void setCrypPublickey(String crypPublickey) {
		this.crypPublickey = crypPublickey;
	}

	public synchronized HashMap<String,Object> getProperites() {
		return properites;
	}

	public String getCrypConfig() {
		return crypConfig;
	}

	public String getCrypPublickey() {
		return crypPublickey;
	}

	public synchronized Object getProperites(String properityName) {
		return properites.get(properityName);
	}

	public synchronized void addPropetiry(String properityName,Object properityObject) {
		properites.put(properityName, properityObject);
	}
}
