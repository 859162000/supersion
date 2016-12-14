package report.service.check;

import java.util.ArrayList;
import java.util.List;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class FilterReMaskValueCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		String[] ids = (String[])RequestManager.getIdList();
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		List<String> newV = new ArrayList<String>();
		MessageResult messageResult = new MessageResult();
		for(String id:ids){
			Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{RequestManager.getTName(),id,null});
			Object v = ReflectOperation.getFieldValue(object, "strRemark");
			if(isNotBlank(v)){
				newV.add(id);
			}
		}
		if(newV.size()==0){
			messageResult.setSuccess(false);
			messageResult.setMessage("没有需要强制提交的数据或者强制提交的数据的备注没有填写！");
		}else{
			ids=new String[newV.size()];
			newV.toArray(ids);
			RequestManager.setIdList(ids);
		}
		
		return messageResult;
	}

	private static boolean isNotBlank(Object v){
		boolean flag=true;
		if(null ==v || v.equals("")){
			flag=false;
		}
		return flag;
	}
}
