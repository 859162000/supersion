package report.service.imps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import framework.helper.ExceptionLog;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class CopyRptInfoSaveService extends BaseVoidDaoResultService {

	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();

		// 获取模块名称
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;

		// HttpSession session = ServletActionContext.getRequest().getSession();
		// String parentId = (String)session.getAttribute("code");
		HttpServletRequest request = ServletActionContext.getRequest();
		String strRptPath = (String) request.getAttribute("SourceTemplePath");

		// 读取模板文件
		String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
		String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
		File fileOld = new File(strRootPath + strTmpRootPath + File.separator+ strRptPath + ".htm");
		File fileOldXls = new File(strRootPath + strTmpRootPath+ File.separator + strRptPath + ".xls");
		File fileOldTmp = new File(strRootPath + strTmpRootPath+ File.separator + strRptPath + "_tmp.xls");
		List<File> oldList = new ArrayList<File>();
		List<File> newList = new ArrayList<File>();
		Object tObjct = RequestManager.getTOject();
		String sonPath = ReflectOperation.getFieldShowValue(tObjct,"strRptPath");
		if(fileOldXls.exists())
		{
			oldList.add(fileOldXls);
			File fileNew = new File(strRootPath + strTmpRootPath + File.separator + sonPath + ".xls");
			newList.add(fileNew);
		}
		
		if(fileOld.exists())
		{
			oldList.add(fileOld);
			File fileNew = new File(strRootPath + strTmpRootPath + File.separator + sonPath + ".htm");
			newList.add(fileNew);
		}
		
		if(fileOldTmp.exists())
		{
			File fileNewTmp = new File(strRootPath + strTmpRootPath + File.separator + sonPath + "_tmp.xls");
			oldList.add(fileOldTmp);
			newList.add(fileNewTmp);
		}
		
		
		

		// 文件复制
		for (int i = 0; i < oldList.size(); i++) {
			try {
				inBuff = new BufferedInputStream(new FileInputStream(oldList.get(i)));
				outBuff = new BufferedOutputStream(new FileOutputStream(newList.get(i)));
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = inBuff.read(b)) != -1) {
					outBuff.write(b, 0, len);
				}
				// 刷新此缓冲的输出流
				outBuff.flush();
			} 
			finally {
				// 关闭流
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			}
		}
		ApplicationManager.getActionExceptionLog().info("新建了" + sonPath + "的模板");//将信息保存在日志文件中
			

	
		MessageResult messageResult = new MessageResult();
		messageResult.setMessage("保存成功");
		this.setServiceResult(messageResult);

	}

	@Override
	public void extendErrorResult() throws Exception {
		MessageResult messageResult = (MessageResult) this.getServiceResult();
		messageResult.AlertTranslate();
	}
}