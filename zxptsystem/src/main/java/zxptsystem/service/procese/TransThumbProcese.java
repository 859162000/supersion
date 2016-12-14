package zxptsystem.service.procese;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.EIS_AuthorizationDocumentInfo;
import zxptsystem.dto.EIS_CertificateInfo;
import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.QYZXCreditReportInfo;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowValue;

public class TransThumbProcese implements IProcese {

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		if(null != serviceResult){
			if (serviceResult instanceof ShowList) {
				ShowList showList = (ShowList)serviceResult;
				List<List<ShowOperation>> lists=showList.getLinkedList();
				int index=0;
				String tName =RequestManager.getTName();
				Object obj = Class.forName(tName).newInstance();
				String parentClass =SessionManager.getPreviousLevelTName();
				for(ShowOperation showOperation:showList.getOperationList()){
					if(showOperation.getOperationName().equals("返回")){
						String[] actions =showOperation.getOperationAction().split("-");
						String newAction = actions[0]+"-"+parentClass;
						showOperation.setOperationAction(newAction);
					}
				}
				if(obj instanceof EIS_AuthorizationDocumentInfo 
						|| obj instanceof EIS_CertificateInfo){
					for(List<ShowOperation> list :lists){
						for(ShowOperation so:list){
							if(so.getOperationType().equals("GetPic")){
								ShowValue sv = showList.getValueTable().get(index).get(0);
								String id= sv.getValue();
								String url="framework/ShowImg-"+tName+".action?id="+id+"&type=";
								so.setImageUrl(url);
							}
						}
						index++;
					}
					if(obj instanceof EIS_CertificateInfo){
						List<ShowOperation> showOperationList= showList.getOperationList();
						String className=SessionManager.getPreviousLevelTName();
						for(ShowOperation showOperation:showOperationList){
							if(showOperation.getOperationAction().indexOf("zxptsystem.dto.EIS_ENTCustomernBasicInfo")>-1
									|| showOperation.getOperationAction().indexOf("zxptsystem.dto.EIS_PERCustomernBasicInfo")>-1){
								showOperation.setOperationAction(showOperation.getOperationAction().split("-")[0]+"-"+className);
							}
						}
					}
				}else if(obj instanceof QYZXCreditReportInfo 
						|| obj instanceof GRZXCreditReportInfo){
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EIS_CertificateInfo.class);
					for(List<ShowOperation> list :lists){
						ShowValue sv = showList.getValueTable().get(index).get(0);
						String id= sv.getValue();
						Object object= singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,null});
						for(ShowOperation so:list){
							if(so.getOperationType().equals("GetPic")){
								if(so.getOperationName().equals("授权书")){
									String strDocId="";
									if(null!=ReflectOperation.getFieldValue(object, "strDocId")){
										strDocId=((EIS_AuthorizationDocumentInfo)ReflectOperation.getFieldValue(object, "strDocId")).getId();
									}									
									String url="framework/ShowImg-zxptsystem.dto.EIS_AuthorizationDocumentInfo.action?id="+strDocId+"&type=";
									so.setImageUrl(url);
								}
								if(so.getOperationName().equals("证照")){
									String strCustomerId="";
									if(obj instanceof QYZXCreditReportInfo){
										strCustomerId = ((EIS_ENTCustomernBasicInfo)ReflectOperation.getFieldValue(object, "strCustomerID")).getStrCustomerID();
										detachedCriteria.add(Restrictions.in("certificateType", new String[]{"E","F","G","H","Z"}));
									}else{
										strCustomerId = ((EIS_PERCustomernBasicInfo)ReflectOperation.getFieldValue(object, "strCustomerID")).getStrCustomerID();
										detachedCriteria.add(Restrictions.in("certificateType", new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","X"}));
									}				
									detachedCriteria.add(Restrictions.eq("strCustomerID", strCustomerId));
									
									List<EIS_CertificateInfo> certList = (List<EIS_CertificateInfo>)singleObjectFindByCriteriaDao
											.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									String urls = "";
									int count=3;
									for(EIS_CertificateInfo eis_ci:certList){
										if(count>0){
											urls+="framework/ShowImg-zxptsystem.dto.EIS_CertificateInfo.action?id="+eis_ci.getId()+"&type="+"@";
										}else{
											break;
										}
										count--;
									}
									so.setImageUrl(urls);
								}								
							}
						}
						index++;
					}
				}				
			}
		}
		return serviceResult;
	}

}
