package zxptsystem.service.imps;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

import zxptsystem.dto.GRZXCJFKBW;
import zxptsystem.dto.GRZXSJSCCJFKBW;
import zxptsystem.dto.GRZXZHBSBGCJFKBW;
import zxptsystem.dto.JGXXCJFKBW;
import zxptsystem.dto.QYZXCJFKBW;
import zxptsystem.dto.QYZXPLXDYWSJSCJGFKBW;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

/**
 * 解析反馈报文时，如果该错误报文已经导入过，则给予提示
 * @author xiajieli
 *
 */
public class ConfirmDialogForUploadReportService implements IObjectResultExecute {

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		//清空session
		if(ActionContext.getContext().getSession().get("ErrorMessage")!=null){
			ActionContext.getContext().getSession().put("ErrorMessage", "");
		}
		if(ActionContext.getContext().getSession().get("uploadFile1")!=null){
			ActionContext.getContext().getSession().put("uploadFile1", null);
		}
		if(ActionContext.getContext().getSession().get("encName")!=null){
			ActionContext.getContext().getSession().put("encName", "");
		}
		if(ActionContext.getContext().getSession().get("FileName")!=null){
			ActionContext.getContext().getSession().put("FileName", "");
		}
		if(ActionContext.getContext().getSession().get("outputFileName")!=null){
			ActionContext.getContext().getSession().put("outputFileName", "");
		}
		
		File uploadFile1 = RequestManager.getUploadFile();
		ActionContext.getContext().getSession().put("uploadFile1", uploadFile1);		
		
		if(uploadFile1 != null){
			String infileName = uploadFile1.getPath();
			
			String encName=RequestManager.getReportCheckParam().get("uploadFileFileName");
			ActionContext.getContext().getSession().put("encName", encName);
			
			if(RequestManager.getReportCheckParam().get("uploadFileFileName").endsWith(".enc")){
				String outputFileName = RequestManager.getReportCheckParam().get("uploadFileFileName").replace("enc", "txt");//uploadFile1
				
				//企业
				if(outputFileName.substring(0, outputFileName.length()-4).length()==28 && outputFileName.substring(26, 27).equals("1") && 
						outputFileName.substring(27, 28).equals("0")){
					//企业采集
					if(RequestManager.getActionName().contains("ConfirmDialogForUploadQYZXBWJX-zxptsystem.dto.QYZXCJFKBW")){
						zxptsystem.helper.QY.MsgDecryptImpl2 cryptor = new zxptsystem.helper.QY.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							QYZXCJFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}		
					    String	FileName = outputFileName.substring(0,26) + "0" + outputFileName.substring(27);
					    ActionContext.getContext().getSession().put("FileName", FileName);
					    ActionContext.getContext().getSession().put("outputFileName", outputFileName);
					    
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(QYZXCJFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<QYZXCJFKBW> qYZXCJFKBWList = (List<QYZXCJFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(qYZXCJFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
					//企业批量删除
					else if(RequestManager.getActionName().contains("ConfirmDialogForUploadQYZXBWJX-zxptsystem.dto.QYZXPLXDYWSJSCJGFKBW")){
						zxptsystem.helper.QY.MsgDecryptImpl2 cryptor = new zxptsystem.helper.QY.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							QYZXPLXDYWSJSCJGFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}
						String FileName = outputFileName.substring(0,26) + "0" + outputFileName.substring(27);
					    ActionContext.getContext().getSession().put("FileName", FileName);
					    ActionContext.getContext().getSession().put("outputFileName", outputFileName);
					    
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(QYZXPLXDYWSJSCJGFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<QYZXPLXDYWSJSCJGFKBW> qYZXPLXDYWSJSCJGFKBWList = (List<QYZXPLXDYWSJSCJGFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(qYZXPLXDYWSJSCJGFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
				}
				//个人
				else if(outputFileName.substring(0, outputFileName.length()-4).length()==28 && outputFileName.substring(27, 28).equals("1")){
					//个人正常反馈报文
					if(RequestManager.getActionName().contains("ConfirmDialogForUploadQYZXBWJX-zxptsystem.dto.GRZXCJFKBW")){
						zxptsystem.helper.GR.MsgDecryptImpl2 cryptor = new zxptsystem.helper.GR.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							GRZXCJFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}		
						String	FileName = outputFileName.substring(0,27) + ".txt";
						ActionContext.getContext().getSession().put("FileName", FileName);
						ActionContext.getContext().getSession().put("outputFileName", outputFileName);
						
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GRZXCJFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<GRZXCJFKBW> gRZXCJFKBWList = (List<GRZXCJFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(gRZXCJFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
					//个人账户标识变更反馈报文
					else if(RequestManager.getActionName().contains("ConfirmDialogForUploadQYZXBWJX-zxptsystem.dto.GRZXZHBSBGCJFKBW")){
						zxptsystem.helper.GR.MsgDecryptImpl2 cryptor = new zxptsystem.helper.GR.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							GRZXZHBSBGCJFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}		
						String	FileName = outputFileName.substring(0,27) + ".txt";
						ActionContext.getContext().getSession().put("FileName", FileName);
						ActionContext.getContext().getSession().put("outputFileName", outputFileName);
						
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GRZXZHBSBGCJFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<GRZXZHBSBGCJFKBW> gRZXZHBSBGCJFKBWList = (List<GRZXZHBSBGCJFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(gRZXZHBSBGCJFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
					//个人数据删除反馈报文
					else if(RequestManager.getActionName().contains("ConfirmDialogForUploadQYZXBWJX-zxptsystem.dto.GRZXSJSCCJFKBW")){
						zxptsystem.helper.GR.MsgDecryptImpl2 cryptor = new zxptsystem.helper.GR.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							GRZXSJSCCJFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}		
						String	FileName = outputFileName.substring(0,27) + ".txt";
						ActionContext.getContext().getSession().put("FileName", FileName);
						ActionContext.getContext().getSession().put("outputFileName", outputFileName);
						
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GRZXSJSCCJFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<GRZXSJSCCJFKBW> gRZXSJSCCJFKBWList = (List<GRZXSJSCCJFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(gRZXSJSCCJFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
					
				}
				//机构
				else if(outputFileName.substring(0, outputFileName.length()-4).length()==37 && outputFileName.substring(35, 36).equals("1")){
					if(RequestManager.getActionName().contains("ConfirmDialogForUploadQYZXBWJX-zxptsystem.dto.JGXXCJFKBW")){//机构信息反馈报文
						zxptsystem.helper.QY.MsgDecryptImpl2 cryptor = new zxptsystem.helper.QY.MsgDecryptImpl2();
						try{
							cryptor.decryptMsg(infileName, infileName + ".tmp");
						}catch(Exception ex){
							ex.printStackTrace();
							String ErrorMessage="导入文件内容格式不对，请重新导入！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
						
						try {
							JGXXCJFKBWUploadFileService.deCompress(infileName + ".tmp", outputFileName);
						} catch (IOException e) {
							e.printStackTrace();
						}	
						String FileName = outputFileName.substring(0,35) + "0" + outputFileName.substring(36);
						ActionContext.getContext().getSession().put("FileName", FileName);
						ActionContext.getContext().getSession().put("outputFileName", outputFileName);
						
						IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(JGXXCJFKBW.class);
						detachedCriteria.add(Restrictions.eq("strBWCCWJM", FileName.substring(0, FileName.length()-4)));
						List<JGXXCJFKBW> jGXXCJFKBWList = (List<JGXXCJFKBW>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					    if(jGXXCJFKBWList.size()>0){
					    	String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
					    }
					}
				}else{
					String ErrorMessage="导入文件内容格式不对，请重新导入！";
					ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
				}
			}
		}
		
	    return new MessageResult(true);
	}

}
