package bwdrsystem.service.imps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coresystem.dto.InstInfo;

import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseDService;
import framework.services.interfaces.MessageResult;

/**
 * 
 * @description <p>获取机构信息的列表Service</P>
 * @createTime 2016-8-22 下午01:49:21
 * @updateTime 2016-8-22 下午01:49:21
 * @author Liutao
 * @updater Liutao
 */
public class GetInstInfoListService extends BaseDService {

	@Override
	public void initSuccessResult() throws Exception {
		try {
			super.initDao();
			Map<String, String> instInfoMap = new HashMap<String, String>();
			List<InstInfo> instInfoList = (List<InstInfo>) ((IParamObjectResultExecute) this.getBaseDaoObject()).paramObjectResultExecute(new Object[]{InstInfo.class.getSimpleName(), null});
			if(instInfoList != null && !instInfoList.isEmpty()) {
				for(InstInfo instInfo : instInfoList) {
					instInfoMap.put(instInfo.getStrInstCode(), instInfo.getStrInstName());
				}
			}
			this.setServiceResult(instInfoMap);
		} catch (Exception e) {
			MessageResult messageResult = new MessageResult(false, "系统异常");
			this.setServiceResult(messageResult);
		}
	}
	
}
