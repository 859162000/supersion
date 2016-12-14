package zxptsystem.service.imps;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import zxptsystem.dto.EIS_CertificateInfo;
import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.MessageResult;

public class EIS_AuthorizationDocumentInfoUpdateLevelDocService extends BaseVoidDaoResultService {

	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		//String id = RequestManager.getId().toString();	
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		
		MessageResult messageResult=(MessageResult)this.getServiceResult();
		if(messageResult.isSuccess()){
			IParamObjectResultExecute singleObjectFindByCriteria = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
//			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EIS_AuthorizationDocumentInfo.class);
//			detachedCriteria.add(Restrictions.eq("id",id));
//			EIS_AuthorizationDocumentInfo info =((List<EIS_AuthorizationDocumentInfo>)singleObjectFindByCriteria.paramObjectResultExecute(new Object[] {detachedCriteria, null })).get(0);

			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EIS_ENTCustomernBasicInfo.class);
			detachedCriteria.add(Restrictions.eq("strCustomerID",id));
			List<EIS_ENTCustomernBasicInfo> objList = ((List<EIS_ENTCustomernBasicInfo>)singleObjectFindByCriteria.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
			if(objList.size()>0){
				EIS_ENTCustomernBasicInfo obj = objList.get(0);
				
				int sum=30;
				
				if(!IsNullOrEmpty(obj.getStrCUSCreditInstitutionsCode())){
					sum+=5;
				}
				
				if(!IsNullOrEmpty(obj.getStrInCode())){
					sum+=5;
				}
				
				if(!IsNullOrEmpty(obj.getStrOrganizationCode())){
					sum+=15;
				}
				
				if(null != obj.getStrRegistrationType()){
					sum+=5;
				}
				
				if(!IsNullOrEmpty(obj.getStrTaxpayerIdentLandNo())){
					sum+=5;
				}
				
				if(!IsNullOrEmpty(obj.getStrTaxpayerIdentStateNo())){
					sum+=5;
				}
				
				//证件信息
				detachedCriteria = DetachedCriteria.forClass(EIS_CertificateInfo.class);
				detachedCriteria.add(Restrictions.in("certificateType", new String[]{"E","F","G","H","Z"}));
				detachedCriteria.add(Restrictions.eq("strCustomerID", id));
				List<EIS_CertificateInfo> certList =((List<EIS_CertificateInfo>)singleObjectFindByCriteria.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
				if(certList.size()>0){
					sum+=30;
				}
				obj.setAuthorizationIntegrity(String.valueOf(sum));
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{obj,null});
			}
		}
	}
	private boolean IsNullOrEmpty(String v){
		if(null==v || v.equals(""))
			return true;
		return false;
	}
}
