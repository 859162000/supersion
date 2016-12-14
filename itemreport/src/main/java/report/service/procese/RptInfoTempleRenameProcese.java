package report.service.procese;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import report.dto.RptInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ActionJumpResult;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

public class RptInfoTempleRenameProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {

		MessageResult messageResult = (MessageResult) serviceResult;
		if (messageResult.isSuccess()) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String oldRptPath = (String) request.getAttribute("oldRptPath");

			Object tObjct = RequestManager.getTOject();
			String curRptPath = ReflectOperation.getFieldShowValue(tObjct,
					"strRptPath");
			if (oldRptPath!=null && oldRptPath!="" &&!oldRptPath.equals(curRptPath)) {
				
				String strTmpRootPath = ShowContext.getInstance()
						.getShowEntityMap().get("sysParam")
						.get("reportTmpPath");
				String strRootPath = ServletActionContext.getServletContext()
						.getRealPath("/");
                
				String filePath = strRootPath + strTmpRootPath + File.separator;
				File fileOld = new File(filePath + oldRptPath + ".htm");
				File fileOldXls = new File(filePath + oldRptPath + ".xls");
				File fileOldTmp = new File(filePath + oldRptPath+ "_tmp.xls");

				if (fileOldXls.exists()) {
					fileOld.renameTo(new File(filePath + curRptPath + ".htm"));
					fileOldXls.renameTo(new File(filePath + curRptPath + ".xls"));
					fileOldTmp.renameTo(new File(filePath + curRptPath+ "_tmp.xls"));
				}
			}
		}

		return serviceResult;
	}

}
