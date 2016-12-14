package report.service.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coresystem.dto.InstInfo;

import extend.dto.AutoETL_DataSource;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseService;
import framework.show.ShowContext;

public class ReportCheckViewService extends BaseService{

	private String checkInstanceEntity;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		Map<String, String> checkInstanceMap = ShowContext.getInstance().getShowEntityMap().get(checkInstanceEntity);
		 
		IParamObjectResultExecute singleObjectFindByAllDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<InstInfo> instInfo = (List<InstInfo>) singleObjectFindByAllDao.paramObjectResultExecute(new Object[]{InstInfo.class.getName(),null});
		Map<String, String> instInfoMap = new HashMap<String, String>();
		for (int i = 0; i < instInfo.size(); i++) {
			InstInfo inst = instInfo.get(i);
			instInfoMap.put(inst.getStrInstCode(), inst.getStrInstName());
		}
		
		ArrayList<Object> entityList = new ArrayList<Object>();
		entityList.add(checkInstanceMap);
		entityList.add(instInfoMap);
		
		this.setServiceResult(entityList);
	}

	public void setCheckInstanceEntity(String checkInstanceEntity) {
		this.checkInstanceEntity = checkInstanceEntity;
	}

	public String getCheckInstanceEntity() {
		return checkInstanceEntity;
	}
}
