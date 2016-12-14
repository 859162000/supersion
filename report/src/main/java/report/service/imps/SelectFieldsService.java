package report.service.imps;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import report.dto.ItemInfo;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.show.ShowContext;

public class SelectFieldsService  extends BaseObjectDaoResultService{

	@Override
	public void initSuccessResult() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		String AutoTableId = RequestManager.getReportCheckParam().get("AutoTableID");
		if(AutoTableId == null) {
			String suitID = RequestManager.getReportCheckParam().get("SuitID");
			String tmpName = RequestManager.getReportCheckParam().get("TmpName");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.ItemInfo"));
			
			if(suitID != null && !"".equals(suitID) ) {
	//			RequestManager.setId(AutoTableId);
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Suit suit = (Suit)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.Suit", suitID, null});
				
				detachedCriteria.add(Restrictions.eq("suit", suit));
				detachedCriteria.addOrder(Order.asc("strItemCode"));
			}
			
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	    	List<ItemInfo> objectListItemInfo = (List<ItemInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
	    	
			StringBuffer ItemInfos = new StringBuffer();
			for(ItemInfo info : objectListItemInfo) {
				String filterItem = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("filterBindItem");
				if(filterItem != null && filterItem.equals("true") 
						&& !info.getStrItemCode().startsWith(tmpName)) continue;
				ItemInfos.append(info.getStrItemCode());
				ItemInfos.append(",");
				ItemInfos.append(info.getStrItemName());
				ItemInfos.append(";");
			}
			
			if(ItemInfos==null){
				ItemInfos.append("");
			}
			response.getWriter().write(ItemInfos.toString());
			
		}
		else {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Field"));
			
			if(!"".equals(AutoTableId)) {
	//			RequestManager.setId(AutoTableId);
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				ReportModel_Table table = (ReportModel_Table)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.ReportModel_Table",AutoTableId,null});
				
				detachedCriteria.add(Restrictions.eq("reportModel_Table", table));
				detachedCriteria.addOrder(Order.asc("nSeq"));
			}
			IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	    	List<ReportModel_Field> objectListField = (List<ReportModel_Field>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	    	
			StringBuffer fields = new StringBuffer();
			for(ReportModel_Field code : objectListField) {
				fields.append(code.getStrChinaName());
				fields.append(",");
				fields.append(code.getStrFieldName());
				fields.append(",");
				fields.append(code.getAutoFieldID());
				fields.append(",");
				fields.append(code.getReportModel_Table().getAutoTableID());
				fields.append(";");
			}
			if(fields==null){
				fields.append("");
			}
			response.getWriter().write(fields.toString());
		}
		
	}
}
