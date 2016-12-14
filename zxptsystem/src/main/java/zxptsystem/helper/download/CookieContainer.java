package zxptsystem.helper.download;

import java.util.Hashtable;

public class CookieContainer {
	private CookieContainer(){
		
	}
	private static final CookieContainer container= new CookieContainer();
	
	public static CookieContainer getInstance(){
		return container;
	}
	public final Hashtable<String,String> Data = new Hashtable<String,String>();
	
}
