package zdzsystem.service.procese;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import zdzsystem.dto.AutoDTO_ZDZ_CXQQNR;
import zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

/**
 * 填充查询请求单号、控制请求单号的下拉框
 * @author Administrator
 *
 */
public class FullBDHMShowSaveOrUpdateProcese implements IProcese{

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,String> hashMap = new HashMap<String,String>();
		DetachedCriteria detachedCriteria=null;
		List<AutoDTO_ZDZ_CXQQNR> resultExecuteCX=null;
		List<AutoDTO_ZDZ_KZQQJTNR> resultExecuteKZ=null;
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		ShowSaveOrUpdate saveOrUpdate=(ShowSaveOrUpdate)serviceResult;
		List<ShowFieldValue> showFieldValueList = saveOrUpdate.getShowFieldValueList();
		for (ShowFieldValue showFieldValue : showFieldValueList) {
			ShowField showField = showFieldValue.getShowField();
			if(showField.getFieldShowName()!=null && showField.getFieldShowName().trim().equals("查询请求单号") && showField.getFieldName().trim().equals("BDHM")){
				 detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_CXQQNR").newInstance().getClass());
	             resultExecuteCX=(List<AutoDTO_ZDZ_CXQQNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for (AutoDTO_ZDZ_CXQQNR autoDTO_ZDZ_CXQQNR :resultExecuteCX) {
					hashMap.put(autoDTO_ZDZ_CXQQNR.getBDHM(),autoDTO_ZDZ_CXQQNR.getBDHM()+"-"+autoDTO_ZDZ_CXQQNR.getXM());
				}
				showFieldValue.setTag(hashMap);
			}
			if(showField.getFieldShowName()!=null && showField.getFieldShowName().trim().equals("控制请求单号") && showField.getFieldName().trim().equals("BDHM")){
				detachedCriteria = DetachedCriteria.forClass(Class.forName("zdzsystem.dto.AutoDTO_ZDZ_KZQQJTNR").newInstance().getClass());
				resultExecuteKZ=(List<AutoDTO_ZDZ_KZQQJTNR>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				for (AutoDTO_ZDZ_KZQQJTNR autoDTO_ZDZ_KZQQJTNR :resultExecuteKZ) {
					hashMap.put(autoDTO_ZDZ_KZQQJTNR.getBDHM(),autoDTO_ZDZ_KZQQJTNR.getBDHM()+"-"+autoDTO_ZDZ_KZQQJTNR.getXM());
				}
				showFieldValue.setTag(hashMap);
			}
			
		}
		return serviceResult;
	}

}
