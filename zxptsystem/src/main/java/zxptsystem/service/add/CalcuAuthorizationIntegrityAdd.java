package zxptsystem.service.add;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import zxptsystem.dto.EIS_CertificateInfo;
import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.EIS_AuthorizationDocumentInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class CalcuAuthorizationIntegrityAdd implements IAdd {

	@Override
	public void Add() throws Exception {
		Object obj = RequestManager.getTOject();
		if(obj instanceof EIS_ENTCustomernBasicInfo){
			String strCustomerID=ReflectOperation.getFieldValue(obj, "strCustomerID").toString();
			String strCUSCreditInstitutionsCode=ReflectOperation.getFieldValue(obj, "strCUSCreditInstitutionsCode").toString();
			String strInCode=ReflectOperation.getFieldValue(obj, "strInCode").toString();
			String strOrganizationCode=ReflectOperation.getFieldValue(obj, "strOrganizationCode").toString();
			String strTaxpayerIdentLandNo=ReflectOperation.getFieldValue(obj, "strTaxpayerIdentLandNo").toString();
			String strTaxpayerIdentStateNo=ReflectOperation.getFieldValue(obj, "strTaxpayerIdentStateNo").toString();
			String strRegistrationType=ReflectOperation.getFieldValue(obj, "strRegistrationType").toString();

			int sum=0;
			
			if(!IsNullOrEmpty(strCUSCreditInstitutionsCode)){
				sum+=5;
			}
			
			if(!IsNullOrEmpty(strInCode)){
				sum+=5;
			}
			
			if(!IsNullOrEmpty(strOrganizationCode)){
				sum+=15;
			}
			
			if(null != strRegistrationType){
				sum+=5;
			}
			
			if(!IsNullOrEmpty(strTaxpayerIdentLandNo)){
				sum+=5;
			}
			
			if(!IsNullOrEmpty(strTaxpayerIdentStateNo)){
				sum+=5;
			}
			IParamObjectResultExecute singleObjectFindByCriteriaDao 
				= (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			
			//授权书
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EIS_AuthorizationDocumentInfo.class);
			detachedCriteria.add(Restrictions.eq("profileType", "1"));
			detachedCriteria.add(Restrictions.eq("strCustomerID", strCustomerID));
			List<EIS_AuthorizationDocumentInfo> documentList =((List<EIS_AuthorizationDocumentInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
			if(documentList.size()>0){
				sum+=30;
			}
			//证件信息
			detachedCriteria = DetachedCriteria.forClass(EIS_CertificateInfo.class);
			detachedCriteria.add(Restrictions.in("certificateType", new String[]{"E","F","G","H","Z"}));
			detachedCriteria.add(Restrictions.eq("strCustomerID", strCustomerID));
			List<EIS_CertificateInfo> certList =((List<EIS_CertificateInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
			if(certList.size()>0){
				sum+=30;
			}
			ReflectOperation.setFieldValue(obj, "AuthorizationIntegrity", sum);
		}
	}
	private boolean IsNullOrEmpty(String v){
		if(null==v || v.equals(""))
			return true;
		return false;
	}
}
