package framework.services.procese;

import framework.services.interfaces.IProcese;
import org.apache.struts2.ServletActionContext;

public class ShowModelListSessionProceses implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		// TODO Auto-generated method stub
		String[] uParas=ServletActionContext.getRequest().getQueryString().split("&");
		for(String para : uParas){
			String pName =para.split("=")[0];
			if(pName.equals("relation")){
				ServletActionContext.getContext().getSession().put("relation", para);
			}
			if(pName.equals("strC")){
				ServletActionContext.getContext().getSession().put("strC", para);
			}
		}
		return serviceResult;
	}

}
