package framework.helper;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import framework.interfaces.ApplicationManager;
import framework.show.ShowNavigationComponent;

public class HttpUtils {
	
	@SuppressWarnings("unchecked")
	public static List<ShowNavigationComponent> GetShowNavigationComponent(){
		List<ShowNavigationComponent> showNavigationComponentList 
		= (List<ShowNavigationComponent>)ServletActionContext.getContext().getSession().get(ApplicationManager.getShowNavigation(ShowNavigationComponent.class.getSimpleName()));
		if(null==showNavigationComponentList){
			showNavigationComponentList = new ArrayList<ShowNavigationComponent>();
		}
		return showNavigationComponentList;
	}
}
