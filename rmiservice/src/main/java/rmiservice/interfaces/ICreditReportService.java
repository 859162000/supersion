package rmiservice.interfaces;

import java.rmi.Remote;
import zxptsystem.helper.GR.EnquiryParameter;
import framework.services.interfaces.MessageResult;

public interface ICreditReportService extends Remote {
	
	/**
	 * 个人征信报告的查询入口
	 * @param object 需查询客户身份信息集合（需附带报告的唯一id）
	 * @param val_str 验证字符串，以相同的算法和密钥确保本次调用是合法有效的
	 * @return MessageResult里面的success属性若为true，代表本次查询任务被成功接收
	 * 							  success属性若为false，代表本次查询任务被拒绝，需重新提交任务
	 * @throws RemoteException
	 */
	public MessageResult uploadCustomerInfo(EnquiryParameter object,String val_str) throws Exception;
	
	/**
	 * 获取客户征信报告
	 * @param id 报告id
	 * @param val_str 验证字符串，以相同的算法和密钥确保本次调用是合法有效的
	 * @return Object 如果返回MessageResult对象，表示报文生成失败，message属性为失败详细信息
	 * 			       返回DownloadResult对象，表示报文生成成功，inputstream为文件流信息
	 * @throws Exception
	 */
	public Object getReport(String id,String val_str) throws Exception;
}

