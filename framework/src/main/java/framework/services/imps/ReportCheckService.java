package framework.services.imps;

import java.io.File;
import org.apache.struts2.ServletActionContext;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.reportCheck.CheckContext;
import framework.reportCheck.CheckInstance;
import framework.services.interfaces.MessageResult;

public class ReportCheckService extends BaseTShowService{
	
	private String checkInstanceType;

	public void setCheckResultPath(String checkResultPath) {
		this.checkResultPath = checkResultPath;
	}
	
	private String checkResultPath;

	@Override
	public Object objectResultExecute() throws Exception {
		
		super.init();
		
		// 结果文件路径，存在则先删除
		String path = ServletActionContext.getServletContext().getRealPath(checkResultPath);
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		
		// 从已加载的CheckInstance中找到当前请求的instance
		
		CheckInstance checkInstance = CheckContext.getInstance().getCheckInstanceMap().get(RequestManager.getTName());
		
		if(checkInstance == null){
			IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("checkInstanceCopyService");
			checkInstance = (CheckInstance)paramObjectResultExecute.paramObjectResultExecute(RequestManager.getTName());
		}
		
		String strTaskRptInstID = RequestManager.getReportCheckParam().get("strTaskRptInstID");
		
		if(checkInstance == null){
			return new MessageResult(this.getErrorMessage(),strTaskRptInstID);
		}

		MessageResult messageResult = null;
		try {// 执行数据校验
			messageResult = (MessageResult)checkInstance.paramObjectResultExecute(null);
		} catch(Exception ex){
			if(messageResult != null)
				messageResult.TxtFileTranslate(checkResultPath); // 写入校验结果信息到结果文件
			
			MessageResult errMsg;
			if(ex.getMessage() != null) {
				String msg = "未处理的异常：" + ex.getMessage().replace("\n", "\\r\\n");
				msg = msg.replace('\'', '|').replace('\"', '|');
				errMsg = new MessageResult(false, msg );
			}
			else 
				errMsg = new MessageResult(false, ex.toString());
			
			ex.printStackTrace();
			errMsg.setIdValue(strTaskRptInstID);
			return errMsg;
		}
		
		if(messageResult != null)
			messageResult.TxtFileTranslate(checkResultPath); // 写入校验结果信息到结果文件

		if(checkInstance.getCheckItemList().size() > 0) // 指标校验时，修改提示信息
			this.setSuccessMessage("校验完成");
		
		if(messageResult != null && messageResult.getIdValue() != null && !messageResult.getIdValue().equals(""))
			return new MessageResult(this.getSuccessMessage(), messageResult.getIdValue()); // 返回成功
		else
			return new MessageResult(this.getSuccessMessage()); // 返回成功
	}

	public void setCheckInstanceType(String checkInstanceType) {
		this.checkInstanceType = checkInstanceType;
	}

	public String getCheckInstanceType() {
		return checkInstanceType;
	}
}
