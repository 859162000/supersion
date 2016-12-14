package dbgssystem.actions.interfaces;

import java.rmi.Remote;
import dbgssystem.dto.condition.DBGSDownload_Condition;
import framework.services.interfaces.MessageResult;

public interface IRemoteService extends Remote {
	
	/**
	 * 保存和更新dto对象,3个状态我们赋值
	 * @param object 担保dto对象，限制为dbgssystem.dto这个包下面的有效的dto类,可为list集合
	 * @param val_str 验证字符串，以相同的算法和密钥确保本次调用是合法有效的
	 * @return MessageResult里面的success属性若为true，代表执行成功，message如不为空表示提示信息
	 * 							  success属性若为false，代表执行成败，message表示失败的具体信息
	 * @throws RemoteException
	 */
	public MessageResult saveOrUpdate(Object object,String val_str) throws Exception;
	
	/**
	 * 生成担保报文
	 * @param condition 报文生成条件，只需给这个对象的SJBGRQ(数据报告日期)、strJRJGCode(报送金融机构代码)设置相应的值
	 * @param val_str 验证字符串，以相同的算法和密钥确保本次调用是合法有效的
	 * @return 如果返回MessageResult对象，表示报文生成失败，message属性为失败详细信息
	 * 			       返回DownloadResult对象，表示报文生成成功，inputstream为文件流信息
	 * @throws RemoteException
	 */
	public Object download(DBGSDownload_Condition condition,String val_str) throws Exception;
	
	/**
	 * 上传解析报文
	 * @param fileUrl 上传文件的url位置
	 * @param val_str 验证字符串，以相同的算法和密钥确保本次调用是合法有效的
	 * @return 如果失败为MessageResult对象，成功待讨论.
	 * @throws RemoteException
	 */
	public Object upload(String fileUrl,String fileName,String val_str) throws Exception;
	
	/**
	 * 删除功能，当这条数据的基础段下无额外明细数据后会删除对应基础段
	 * @param object
	 * @param val_str
	 * @return
	 * @throws Exception
	 */
	public MessageResult delete(Object object,String val_str) throws Exception;	
}
