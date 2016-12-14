package dbgssystem.service.imps;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

import dbgssystem.dto.DBGSCJFKBW;

import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

/**
 * 解析担保公司反馈报文时，如果该错误报文已经导入过，则给予提示
 * @author xiajieli
 *
 */
public class ConfirmDialogForDBGSUploadReportService implements IObjectResultExecute {

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		ActionContext.getContext().getSession().put("ErrorMessage", "");//清空session
		ActionContext.getContext().getSession().put("uploadFile1", null);
		ActionContext.getContext().getSession().put("encName", "");
		ActionContext.getContext().getSession().put("FileName", "");
		ActionContext.getContext().getSession().put("outputFileName", "");
		
		File uploadFile1 = RequestManager.getUploadFile();
		ActionContext.getContext().getSession().put("uploadFile1", uploadFile1);		
		
		if(uploadFile1 != null){
			String infileName = uploadFile1.getPath();
			
			String encName=RequestManager.getReportCheckParam().get("uploadFileFileName");
			ActionContext.getContext().getSession().put("encName", encName);
			
			if(RequestManager.getReportCheckParam().get("uploadFileFileName").endsWith(".enc")){
				String outputFileName = RequestManager.getReportCheckParam().get("uploadFileFileName").replace("enc", "txt");//uploadFile1
				
				//担保
				if(outputFileName.substring(0, outputFileName.length()-4).length()==31 && outputFileName.substring(29, 30).equals("1")){
					//正常反馈报文
					if(RequestManager.getActionName().contains("ConfirmDialogForUploadDBGSBWJX-dbgssystem.dto.DBGSCJFKBW")){
						dbgssystem.helper.MsgDecryptImpl2 cryptor = new dbgssystem.helper.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							DBGSCJFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}		
						String	FileName = outputFileName.substring(0,29) +"0"+ outputFileName.substring(30)+".txt";
						ActionContext.getContext().getSession().put("FileName", FileName);
						ActionContext.getContext().getSession().put("outputFileName", outputFileName);
						
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DBGSCJFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<DBGSCJFKBW> dBGSCJFKBWList = (List<DBGSCJFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(dBGSCJFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
				}
				else{
					String ErrorMessage="导入文件内容格式不对，请重新导入！";
					ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
				}
			}
		}
		
	    return new MessageResult(true);
	}

}
