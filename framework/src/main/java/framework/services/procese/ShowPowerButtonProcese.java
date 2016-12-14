package framework.services.procese;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import framework.security.SecurityContext;
import framework.services.interfaces.IProcese;
import framework.show.ShowHeaderName;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

public class ShowPowerButtonProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {

		Set<String> dataSecuritySet = SecurityContext.getInstance().getLoginInfo().getActionNameSet();
		if (!dataSecuritySet.isEmpty() && (serviceResult != null || serviceResult != "")) {
			if (serviceResult instanceof ShowList) {
				ShowList showList = (ShowList) serviceResult;
				List<ShowOperation> showListTwo = new ArrayList<ShowOperation>();
				if(showList.getOperationList()!=null){
					for (int i = 0; i < showList.getOperationList().size() && !showList.getOperationList().isEmpty(); i++) {
						if (isSecurity(showList.getOperationList().get(i).getOperationNamespace(), showList.getOperationList().get(i).getOperationAction(),dataSecuritySet))
							showListTwo.add(showList.getOperationList().get(i));
					}
					showList.setOperationList(showListTwo);
					
					List<List<ShowOperation>> linkedList = new ArrayList<List<ShowOperation>>();
					List<ShowHeaderName> headNameList=new ArrayList<ShowHeaderName>(); 
					for(int k=0;k<showList.getLinkedList().size();k++){
						List<ShowOperation> linked = new ArrayList<ShowOperation>();
						for (int i = 0; i < showList.getLinkedList().get(k).size() && !showList.getLinkedList().get(k).isEmpty(); i++) {
							if (isSecurity(showList.getLinkedList().get(k).get(i).getOperationNamespace(), showList.getLinkedList().get(k).get(i).getOperationAction(),dataSecuritySet)) {
								ShowOperation showOp=showList.getLinkedList().get(k).get(i);
								linked.add(showOp);
								if(k==0)
								{
									headNameList.add(showList.getShowLinkNameList().get(i));
								}
							}
						}
						linkedList.add(linked);
					}
					showList.setLinkedList(linkedList);
					showList.setShowLinkNameList(headNameList);
					
				}
				return showList;
			} else if (serviceResult instanceof ShowSaveOrUpdate) {
				ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate) serviceResult;
				java.util.List<ShowOperation> ShowOperationlist = new ArrayList<ShowOperation>();
				if(showSaveOrUpdate.getOperationList()!=null){
					for (int i = 0; i < showSaveOrUpdate.getOperationList().size() && !showSaveOrUpdate.getOperationList().isEmpty(); i++) {
						if (isSecurity(showSaveOrUpdate.getOperationList().get(i).getOperationNamespace(), showSaveOrUpdate.getOperationList().get(i).getOperationAction(),dataSecuritySet)) {
							ShowOperationlist.add(showSaveOrUpdate.getOperationList().get(i));
						}
					}
					showSaveOrUpdate.setOperationList(ShowOperationlist);
				}
				return showSaveOrUpdate;
			}
		}
		return serviceResult;
	}

	private boolean isSecurity(String buttonNamespace, String buttonAction,Set<String> dataSecuritySet) {
		if (dataSecuritySet.contains(buttonNamespace + "/" + buttonAction)) {
			return true;
		} else {
			for (String configAction : dataSecuritySet) {
				int position = configAction.indexOf("*");
				if (position > -1) {
					if (position == 0) {
						String actionName1 = configAction.substring(1);
						if ((buttonNamespace + "/" + buttonAction).endsWith(actionName1)) {
							return true;
						}
					} else if (position == configAction.length() - 1) {
						String actionName2 = configAction.substring(0,configAction.indexOf("*"));
						if ((buttonNamespace + "/" + buttonAction).startsWith(actionName2)) {
							return true;
						}
					} else {
						String actionName1 = configAction.substring(0,configAction.indexOf("*"));
						String actionName2 = configAction.substring(configAction.indexOf("*") + 1);
						if ((buttonNamespace + "/" + buttonAction).startsWith(actionName1) && (buttonNamespace + "/" + buttonAction).endsWith(actionName2)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
