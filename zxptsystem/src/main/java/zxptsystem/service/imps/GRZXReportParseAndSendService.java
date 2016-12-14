package zxptsystem.service.imps;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

/**
 * 
 * @description <p>手动推送功能处理类</P>
 * @createTime 2016-9-30 下午04:08:15
 * @updateTime 2016-9-30 下午04:08:15
 * @author Liutao
 * @updater Liutao
 */
public class GRZXReportParseAndSendService implements IObjectResultExecute{
	@Override
	public Object objectResultExecute() throws Exception {
		MessageResult messageResult = null;
		try {
			if(RequestManager.getIdList() == null || ((String[]) RequestManager.getIdList()).length == 0) {
				messageResult = new MessageResult(false, "请选择需要推送的数据");
			} else {
				// 获取系统参数“个人征信信用报告解析”
				DetachedCriteria dc = DetachedCriteria.forClass(SystemParam.class);
				dc.add(Restrictions.eq("strItemCode", "grzxReportParse"));
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				List<SystemParam> paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
				
				if(paramList != null && !paramList.isEmpty() && paramList.get(0) != null && paramList.get(0).getStrEnable().equals("1")) {
					// 检测系统参数“个人征信信用报告解析数据推送”是否配置并启用
					dc = DetachedCriteria.forClass(SystemParam.class);
					dc.add(Restrictions.eq("strItemCode", "grzxParseDataSend"));
					paramList = (List<SystemParam>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
					if(paramList == null || paramList.isEmpty() || paramList.get(0) == null || !paramList.get(0).getStrEnable().equals("1")) {
						messageResult = new MessageResult(false, "“个人征信信用报告解析数据推送”系统参数未配置或未启用");
					} else {
						if(!StringUtils.isBlank(paramList.get(0).getStrParamValue())) {
							Object serviceBean = FrameworkFactory.CreateBean(paramList.get(0).getStrParamValue());
							if(null != serviceBean) {
								Method method = ReflectOperation.getReflectMethod(serviceBean.getClass(), "handleSend",Object.class);
								if(method != null) {
									messageResult = (MessageResult) method.invoke(serviceBean,"");
								} else {
									messageResult = new MessageResult(false, "获取推送功能实现类中的手动推送数据方法失败");
								}
							} else {
								messageResult = new MessageResult(false, "获取推送功能实现类bean失败");
							}
						} else {
							messageResult = new MessageResult(false, "系统参数“个人征信信用报告解析数据推送”参数值未设置");
						}
					}
				} else {
					messageResult = new MessageResult(false, "系统参数“个人征信信用报告解析”未配置或者未启用");
				}
			}
		} catch (Exception e) {
			messageResult = new MessageResult(false, "推送数据时发生异常");
			ApplicationManager.getActionExceptionLog().error("推送数据时发生异常", e);
		}
		return messageResult;
	}

}
