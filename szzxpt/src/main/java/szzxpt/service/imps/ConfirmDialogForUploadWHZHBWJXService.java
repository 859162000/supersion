package szzxpt.service.imps;

import java.io.File;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;



import com.opensymphony.xwork2.ActionContext;

import framework.helper.FrameworkFactory;
import framework.helper.SmallTools;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

/*
 * 解析反馈报文时，如果该错误报文已经导入过，则给予提示
 */
public class ConfirmDialogForUploadWHZHBWJXService implements IObjectResultExecute {

	@Override
	public Object objectResultExecute() throws Exception {
		
		File uploadFile = RequestManager.getUploadFile();
		if(uploadFile != null){
			File uploadFileTemp = new File(uploadFile.getPath()+".tmp");
			if(!uploadFileTemp.exists()){
				uploadFileTemp.createNewFile(); 
				SmallTools.copyFile(uploadFile.getPath(), uploadFileTemp.getPath());
			}
			ActionContext.getContext().getSession().put("uploadFileTemp", uploadFileTemp);	
			
			if(uploadFileTemp != null){
				String uploadFileFileName=RequestManager.getReportCheckParam().get("uploadFileFileName");
				
				if(uploadFileFileName!=null){
					ActionContext.getContext().getSession().put("uploadFileFileName", uploadFileFileName);	
					
					IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
					if(uploadFileFileName.toUpperCase().endsWith("ERR.XML")){//单个解析
						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("szzxpt.dto.WHZHCJFKBW"));
						detachedCriteria.add(Restrictions.eq("strBWCCWJM",uploadFileFileName));
						int count=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
						if(count>0){
							String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
							ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
						    return new MessageResult(true);
						}
					}
					else if(uploadFileFileName.toUpperCase().endsWith("ERR.ZIP")){//多个解析
						List<File> fileList = SmallTools.unZipFiles(uploadFileTemp, "");
						for (File file : fileList) {
							if(file.exists()){
								if(file.getName().toString().toUpperCase().endsWith("ERR.XML")){
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("szzxpt.dto.WHZHCJFKBW"));
									detachedCriteria.add(Restrictions.eq("strBWCCWJM",file.getName()));
									int count=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
									if(count>0){
										String ErrorMessage="该反馈报文已经导入，请勿重复操作！";
										ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
									    return new MessageResult(true);
									}
								}
							}
						}
					}else{
						String ErrorMessage="导入文件内容格式不对，请重新导入！";
						ActionContext.getContext().getSession().put("ErrorMessage", ErrorMessage);
					    return new MessageResult(true);
					}
				}
			}
		}
		return new MessageResult(true);
	}
		
}
