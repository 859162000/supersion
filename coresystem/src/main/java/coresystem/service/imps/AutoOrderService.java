package coresystem.service.imps;

import java.text.DecimalFormat;

import coresystem.dto.AutoOrder;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;

public class AutoOrderService {
	public String getAutoOrder(String key,String format) throws Exception{
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoOrder autoOrder = (AutoOrder) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] {AutoOrder.class.getName(), key, null });
		if(autoOrder == null){
			
			AutoOrder saveAutoOrder = new AutoOrder();
			saveAutoOrder.setStrkey(key);
			saveAutoOrder.setAutoOrder(1);
			
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveDao");
	        singleObjectSaveDao.paramVoidResultExecute(new Object[] {saveAutoOrder, null });
			
	        
	        DecimalFormat df=new DecimalFormat(format);
	        return df.format(1);
		}
		else{
			
			autoOrder.setAutoOrder(autoOrder.getAutoOrder() + 1);
			
			IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
	        singleObjectUpdateDao.paramVoidResultExecute(new Object[] {autoOrder, null });
			
	        DecimalFormat df=new DecimalFormat(format);
	        return df.format(autoOrder.getAutoOrder());
		}
	}
}
