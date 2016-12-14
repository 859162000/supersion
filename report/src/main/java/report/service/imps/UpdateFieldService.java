package report.service.imps;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;


import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;


public class UpdateFieldService extends BaseService {

	public void init() throws Exception{
		Object tObject=RequestManager.getTOject();
		Object id=ReflectOperation.getPrimaryKeyValue(tObject);
		Object oldID = RequestManager.getId();
		RequestManager.setId(id);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object dbObject = singleObjectFindByIdDao.paramObjectResultExecute(null);
		RequestManager.setId(oldID);
		ReflectOperation.CopyColumnFieldValue(tObject, dbObject);
		RequestManager.setTOject(dbObject);
		super.init();
	}
	public void initSuccessResult() throws Exception {
		
		// 定义操作结果
		String result = null;
		// 定义操作成功与否的标志
		boolean flag = false;
		// 定义修改数据的对象
		IParamVoidResultExecute singleObjectSaveDao = null;
		singleObjectSaveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectUpdateDao");
		
		// 定义response对象
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		
		try {
			singleObjectSaveDao.paramVoidResultExecute(null);
			flag = true;
			result = "保存成功;"+flag+";"+RequestManager.getReportCheckParam().get("fieldvalue").toString();
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			result = "保存失败\r\n系统异常，请联系管理员;"+flag;
			response.getWriter().write(result.toString());
		}
	}
	
	
	
}
