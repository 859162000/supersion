package zxptsystem.service.check;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.EIS_AuthorizationDocumentInfo;
import zxptsystem.dto.EIS_CertificateInfo;
import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.QYZXCreditReportInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class AuthorizationIntegrityCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		String[] ids = (String[])RequestManager.getIdList();
		
		for(String id:ids){
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(QYZXCreditReportInfo.class);
			detachedCriteria.add(Restrictions.eq("id", id));
			List<QYZXCreditReportInfo> InfoList =((List<QYZXCreditReportInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
			
			if(InfoList.size()>0){
				EIS_ENTCustomernBasicInfo obj = InfoList.get(0).getStrCustomerID();				
				int sum=0;				
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
				//授权书
				detachedCriteria = DetachedCriteria.forClass(EIS_AuthorizationDocumentInfo.class);
				detachedCriteria.add(Restrictions.eq("profileType", "1"));
				detachedCriteria.add(Restrictions.eq("strCustomerID", obj.getStrCustomerID()));
				List<EIS_AuthorizationDocumentInfo> documentList =((List<EIS_AuthorizationDocumentInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
				if(documentList.size()>0){
					sum+=30;
				}
				//证件信息
				detachedCriteria = DetachedCriteria.forClass(EIS_CertificateInfo.class);
				detachedCriteria.add(Restrictions.in("certificateType", new String[]{"E","F","G","H","Z"}));
				detachedCriteria.add(Restrictions.eq("strCustomerID", obj.getStrCustomerID()));
				List<EIS_CertificateInfo> certList =((List<EIS_CertificateInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
				if(certList.size()>0){
					sum+=30;
				}
				if(sum<=60){
					messageResult.getMessageList().add("所选数据的授权信息完整度要求大于60分！");
					messageResult.setSuccess(false);
					break;
				}
			}
		}
		
		return messageResult;
	}
	private boolean IsNullOrEmpty(String v){
		if(null==v || v.equals(""))
			return true;
		return false;
	}
}
