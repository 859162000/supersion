package zxptsystem.service.procese;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class SetConditionCustomerNameProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		if(serviceResult instanceof ShowList){
			ShowList list = (ShowList)serviceResult;
			for(ShowFieldCondition sfc :list.getShowCondition()){
				if(sfc.getFieldName().equals("strCustomerID")){
					String[] ss = (String[])sfc.getTag();
					ss[0]="strCustomerID.strCustomerName";
					sfc.setParamName("strCustomerID.strCustomerID");
				}
			}
		}
		return serviceResult;
	}

}
