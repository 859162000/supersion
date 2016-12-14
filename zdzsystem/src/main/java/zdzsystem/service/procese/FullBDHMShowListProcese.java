package zdzsystem.service.procese;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;
import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

/**
 * 填充查询请求单号、控制请求单号的下拉框
 * @author Administrator
 *
 */
public class FullBDHMShowListProcese implements IProcese{

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		HashMap<String,String> hashMap = new HashMap<String,String>();
		DetachedCriteria detachedCriteria=null;
		List<AutoDTO_ZDZ_CXQQNR> resultExecuteCX=null;
		List<AutoDTO_ZDZ_KZQQJTNR> resultExecuteKZ=null;
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		ShowList showList=(ShowList)serviceResult;
		List<ShowFieldCondition> showCondition = showList.getShowCondition();
		for (ShowFieldCondition showFieldCondition : showCondition) {
			if(showFieldCondition.getFieldName()!=null && showFieldCondition.getFieldName().equals("BDHM") && showFieldCondition.getFieldShowName().trim().equals("查询请求单号")){
				 detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_CXQQNR").newInstance().getClass());
	             resultExecuteCX=(List<AutoDTO_ZDZ_CXQQNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for (AutoDTO_ZDZ_CXQQNR autoDTO_ZDZ_CXQQNR :resultExecuteCX) {
					hashMap.put(autoDTO_ZDZ_CXQQNR.getBDHM(),autoDTO_ZDZ_CXQQNR.getBDHM()+"-"+autoDTO_ZDZ_CXQQNR.getXM());
				}
				showFieldCondition.setTag(hashMap);
			}
			
			if(showFieldCondition.getFieldName()!=null && showFieldCondition.getFieldName().equals("BDHM") && showFieldCondition.getFieldShowName().trim().equals("控制请求单号")){
				 detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR").newInstance().getClass());
				 resultExecuteKZ =(List<AutoDTO_ZDZ_KZQQJTNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for (AutoDTO_ZDZ_KZQQJTNR autoDTO_ZDZ_KZQQJTNR: resultExecuteKZ) {
					hashMap.put(autoDTO_ZDZ_KZQQJTNR.getBDHM(),autoDTO_ZDZ_KZQQJTNR.getBDHM()+"-"+autoDTO_ZDZ_KZQQJTNR.getXM());
				}
				showFieldCondition.setTag(hashMap);
			}
			
		}
		return serviceResult;
	}

}
