package framework.show;

public class ShowNavigation implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String level;
    private String navigationName;
    private String navigationUrl;
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevel() {
		return level;
	}
	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}
	public String getNavigationName() {
		return navigationName;
	}
	public void setNavigationUrl(String navigationUrl) {
		this.navigationUrl = navigationUrl;
	}
	public String getNavigationUrl() {
		return navigationUrl;
	}
}
