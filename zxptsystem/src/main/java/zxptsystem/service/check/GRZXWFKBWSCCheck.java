package zxptsystem.service.check;

import java.util.ArrayList;
import java.util.List;

import zxptsystem.dto.GRZXWFKBWCL;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/**
 * 个人征信无反馈报文删除校验，已处理的数据不能删除
 * @author Transino
 *
 */
public class GRZXWFKBWSCCheck implements ICheck {

	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		String[] selectReport = (String[])RequestManager.getIdList();
		if(selectReport!=null){
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			List<GRZXWFKBWCL> gRZXWFKBWCLList=new ArrayList<GRZXWFKBWCL>();
			for (String gRZXWFKBWCLId : selectReport) {
				GRZXWFKBWCL gRZXWFKBWCL= (GRZXWFKBWCL)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{GRZXWFKBWCL.class.getName(),gRZXWFKBWCLId,null});
				gRZXWFKBWCLList.add(gRZXWFKBWCL);
			}
			if(gRZXWFKBWCLList.size()>0){
				for (GRZXWFKBWCL gRZXWFKBWCL : gRZXWFKBWCLList) {
					if(gRZXWFKBWCL.getStrBWCL().equals("2")){
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("已处理的数据不能删除");
					}
				}
			}
		}
		return messageResult;
	}

}
