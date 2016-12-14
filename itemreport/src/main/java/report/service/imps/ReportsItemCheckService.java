package report.service.imps;

import java.io.File;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.TaskRptInst;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

public class ReportsItemCheckService  extends ReportItemCheckService  {
	
	@Override
	public Object objectResultExecute() throws Exception {
		super.init();
		// 结果文件路径，存在则先删除
		String path = ServletActionContext.getServletContext().getRealPath(checkResultPath);
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		String[] idList = (String[]) RequestManager.getIdList();
		StringBuffer msg = new StringBuffer();
		for(String taskId : idList){
			strTaskRptInstID = taskId;
			String returnMsg="";
			try {// 执行数据校验
				if(StringUtils.isBlank(strTaskRptInstID))
					returnMsg=this.getErrorMessage() + "\r\n" + "参数为空"; 
				else{
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					TaskRptInst taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskRptInst.class.getName(),strTaskRptInstID,null});
					if(null == taskRptInst)
						returnMsg = "任务【"+strTaskRptInstID+"】不存在！";
					else {
						//审核通过的报表不再校验
						if(!("2").equals(taskRptInst.getStrAllowState()))
							returnMsg=DoCheck(taskRptInst);
					}
				}
			} catch(Exception ex){
				ExceptionLog.CreateLog(ex);
				returnMsg=ex.getMessage();
			}
			msg.append(returnMsg);
		}
		MessageResult mr;
		String msgStr = msg.toString();
		if(!StringUtils.isBlank(msgStr))
		{
			mr=new MessageResult(false,msgStr,strTaskRptInstID);
			mr.TxtFileTranslate(checkResultPath);
			mr.setMessage(this.getErrorMessage());
		}
		else
		{
			mr=new MessageResult(true,this.getSuccessMessage(),strTaskRptInstID); 
		}
		return mr;
	}
	
	
}
