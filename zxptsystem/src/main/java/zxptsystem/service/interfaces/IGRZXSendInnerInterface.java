package zxptsystem.service.interfaces;

import framework.services.interfaces.MessageResult;
/**
 * 
 * @description <p>个人征信数据托送内部接口</P>
 * @createTime 2016-9-19 下午02:09:13
 * @updateTime 2016-9-19 下午02:09:13
 * @author Liutao
 * @updater Liutao
 */
public interface IGRZXSendInnerInterface {
	/**
	 * 
	 * @description <p>HTML数据自动推送流程</P>
	 * @createTime 2016-10-8 下午12:29:44
	 * @updateTime 2016-10-8 下午12:29:44
	 * @author Liutao
	 * @updater Liutao
	 */
	MessageResult autoSend(Object obj) throws Exception;
}
