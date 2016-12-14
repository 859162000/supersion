package bwdrsystem.service.imps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import bwdrsystem.helper.BusinessDataClearTool;

import framework.services.imps.BaseDTService;
import framework.services.interfaces.MessageResult;

/**
 * 
 * @description <p>清空征信历史业务数据</P>
 * @createTime 2016-8-13 下午05:15:17
 * @updateTime 2016-8-13 下午05:15:17
 * @author Liutao
 * @updater Liutao
 */
public class ClearZXBusinessHistoryDataService extends BaseDTService {
	
	@Override
	public void initSuccessResult() throws Exception {
		try {
			BusinessDataClearTool.deleteBusinessTableHistoryData();
			this.setServiceResult(new MessageResult(true, this.getSuccessMessage()));
		} catch (Exception e) {
			if(!StringUtils.isBlank(e.getMessage())) {
				Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
				Matcher m = p.matcher(e.getMessage());
				if (m.find()) {
					this.setServiceResult(new MessageResult(false, e.getMessage()));
				} else {
					this.setServiceResult(new MessageResult(false, "系统异常"));
				}
			} else {
				
			}
			e.printStackTrace();
		}
	}

	
	
}
