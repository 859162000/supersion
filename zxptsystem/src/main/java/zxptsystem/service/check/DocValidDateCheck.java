package zxptsystem.service.check;

import java.util.Date;
import java.text.SimpleDateFormat;

import zxptsystem.dto.EIS_AuthorizationDocumentInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class DocValidDateCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		String[] ids = (String[])RequestManager.getIdList();
		MessageResult messageResult= new MessageResult();
		if(null==ids){
			messageResult.setSuccess(false);
			messageResult.getMessageList().add("请选择一条需要重新查询的数据！");
			return messageResult;
		}
		String tName = RequestManager.getTName(); 
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		for(String id:ids){
			Object obj = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{tName,id,null});
			EIS_AuthorizationDocumentInfo doc=(EIS_AuthorizationDocumentInfo)ReflectOperation.getFieldValue(obj, "strDocId");
			if(null!=doc){
				if(null!=doc.getAuthorizationDate() && null!=doc.getAuthorizationEndDate()){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String now = df.format(new Date(System.currentTimeMillis()));
					if(now.compareTo(doc.getAuthorizationDate())<0 || now.compareTo(doc.getAuthorizationEndDate())>0){
						messageResult.setSuccess(false);
						messageResult.getMessageList().add("授权已过期，请申请新的授权！");
					}
				}
			}
		}
		return messageResult;
	}

}
