package eas.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseService;
import framework.show.ShowContext;


public class InterfaceDownEastViewService extends BaseService{

	private String suitCode;
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {

		super.initSuccessResult();

		DetachedCriteria suitDc = DetachedCriteria.forClass(Suit.class);
		suitDc.add(Restrictions.eq("strSuitCode", suitCode));

		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		ArrayList<Suit> suits = (ArrayList<Suit>) dao.paramObjectResultExecute(new Object[]{suitDc,null});
		
		if(suits == null || suits.size() < 1) return;
		Suit suit = suits.get(0);
		
		DetachedCriteria tableDc = DetachedCriteria.forClass(ReportModel_Table.class);
		tableDc.add(Restrictions.eq("suit", suit));

		
    	List<ReportModel_Table> tableList = (List<ReportModel_Table>)dao.paramObjectResultExecute(new Object[]{tableDc,null});
    	
    	DetachedCriteria reportInstDc = DetachedCriteria.forClass(ReportInstInfo.class);
    	reportInstDc.add(Restrictions.eq("suit", suit));
    	List<ReportInstInfo> reportInstList = (List<ReportInstInfo>)dao.paramObjectResultExecute(new Object[]{reportInstDc,null});
    	
    	
		ArrayList<List<String>> tableL = new ArrayList<List<String>>();
		
		for(ReportModel_Table code : tableList) {
			ArrayList<String> Arr1 = new ArrayList<String>();
			Arr1.add(code.getStrTableName());
			Arr1.add(code.getStrChinaName());
			tableL.add(Arr1);
		}

		ArrayList<List<String>> rptInstL = new ArrayList<List<String>>();
		for(ReportInstInfo reportInstInfo : reportInstList) {
			ArrayList<String> Arr2 = new ArrayList<String>();
			Arr2.add(reportInstInfo.getStrReportInstCode());
			Arr2.add(reportInstInfo.getStrReportInstName());
			rptInstL.add(Arr2);
		}
		
		ArrayList<List<String>> createFormatL = new ArrayList<List<String>>();
		Map<String, String> eas_createFormatMap = ShowContext.getInstance().getShowEntityMap().get("eas_createFormat");
		 for (String key : eas_createFormatMap.keySet()) {
			 ArrayList<String> tmpArr = new ArrayList<String>();
			 tmpArr.add(key);
			 tmpArr.add(eas_createFormatMap.get(key));
			 createFormatL.add(tmpArr);
          }

		
		ArrayList<ArrayList<List<String>>> arr = new ArrayList<ArrayList<List<String>>>();
		arr.add(tableL);
		arr.add(rptInstL);
		arr.add(createFormatL);
		
		this.setServiceResult(arr);
		return;
	}
	public void setSuitCode(String suitCode) {
		this.suitCode = suitCode;
	}


	public String getSuitCode() {
		return suitCode;
	}
}
