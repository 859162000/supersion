package zxptsystem.service.translate;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.QYZXCreditReportInfo;

import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;

public class SetLogicParamTranslate implements ITranslate {

	@Override
	public void Translate() throws Exception {
		String lastTypeName="";				
		String[] querString =ServletActionContext.getRequest().getQueryString().split("&");
		String query="";
		for(String str:querString){
			if(str.split("=")[0].equals("strC")){
				query=str.split("=")[1];
			}
			if(str.split("=")[0].equals("lastType")){
				lastTypeName=str.split("=")[1];
			}
		}
		Object obj = Class.forName(lastTypeName).newInstance();
		DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
		if(obj instanceof QYZXCreditReportInfo){
			Date today = new Date();
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			detachedCriteria.add(Restrictions.eq("profileType", "1"));
			detachedCriteria.add(Restrictions.le("authorizationDate", dateFormater.format(today)));
			detachedCriteria.add(Restrictions.ge("authorizationEndDate", dateFormater.format(today)));
		}
		if(obj instanceof GRZXCreditReportInfo){
			detachedCriteria.add(Restrictions.eq("profileType", "2"));
		}		
		detachedCriteria.add(Restrictions.eq("strCustomerID",query));
		
	}

}
