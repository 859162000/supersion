package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;

import zxptsystem.dto.GrzxReportParseLog;

import framework.services.imps.SingleObjectShowListService;

/**
 * 
 * @description <p>显示可手动推送的个人征信报告数据</P>
 * @createTime 2016-9-28 上午10:50:53
 * @updateTime 2016-9-28 上午10:50:53
 * @author Liutao
 * @updater Liutao
 */
public class ShowGRZXReportHandleSendDataService extends SingleObjectShowListService {

	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		List<GrzxReportParseLog> temList = new ArrayList<GrzxReportParseLog>();
		List<GrzxReportParseLog> parseLogList = (List<GrzxReportParseLog>) this.getServiceResult();
		if(parseLogList != null && !parseLogList.isEmpty()) {
			for(GrzxReportParseLog parseLog : parseLogList) {
				if(!"2".equals(parseLog.getSendFlag())) {// 判断是否是解析失败的数据
					temList.add(parseLog);
				}
			}
			
			if(!temList.isEmpty()) {
				parseLogList.removeAll(temList);
			}
			
			this.setServiceResult(parseLogList);
		}
	}

	
	
}
