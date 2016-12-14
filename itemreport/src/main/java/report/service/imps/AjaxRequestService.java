package report.service.imps;

import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import report.dto.ItemInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseService;

public class AjaxRequestService extends BaseService {

	private String defaultDataCount;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		String ItemCode = ServletActionContext.getContext().getSession().get("ItemCode").toString();
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemInfo.class);
		detachedCriteria.add(Restrictions.like("strItemCode", ItemCode,MatchMode.START));
		detachedCriteria.addOrder(Order.asc("strItemCode"));
		if(ServletActionContext.getContext().getSession().containsKey("SuitCode")){
			Object suitCode = ServletActionContext.getContext().getSession().get("SuitCode");
			if(null!=suitCode && !suitCode.toString().equals("")){
				detachedCriteria.add(Restrictions.eq("suit.strSuitCode", ServletActionContext.getContext().getSession().get("SuitCode").toString()));
			}
		}
		List<ItemInfo> itemInfo= (List<ItemInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		java.util.Map<String,String> map = new java.util.LinkedHashMap<String,String>();
		int max=Integer.parseInt(defaultDataCount);
		for(ItemInfo ii :itemInfo){
			if(max<1)break;
			map.put(ii.getStrItemCode(), ii.getStrItemName());
			max--;
		}
		this.setServiceResult(map);
	}

	public void setDefaultDataCount(String defaultDataCount) {
		this.defaultDataCount = defaultDataCount;
	}

	public String getDefaultDataCount() {
		return defaultDataCount;
	}

}
